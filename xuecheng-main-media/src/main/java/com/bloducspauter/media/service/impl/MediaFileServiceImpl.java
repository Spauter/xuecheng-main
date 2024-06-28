package com.bloducspauter.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bloducspauter.base.exceptions.InsertEntityFailedException;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.base.model.RestResponse;
import com.bloducspauter.media.entities.dto.QueryMediaParamsDto;
import com.bloducspauter.media.entities.dto.UploadFileParamsDto;
import com.bloducspauter.media.entities.dto.UploadFileResultDto;
import com.bloducspauter.media.entities.po.MediaFiles;
import com.bloducspauter.media.mapper.MediaFilesMapper;
import com.bloducspauter.media.service.MediaService;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Bloduc Spauter
 */
@Slf4j
@Service
public class MediaFileServiceImpl implements MediaService {

    @Resource
    MediaFilesMapper mediaFilesMapper;

    @Resource
    MinioClient minioClient;

    //存储普通文件
    @Value("${minio.bucket.files}")
    private String bucket_mediafiles;

    //存储视频
    @Value("${minio.bucket.videofiles}")
    private String bucket_video;

    @Override
    public PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {

        //构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();

        //分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        return new PageResult<>(list, total, pageParams.getPageSize());
    }

    //根据扩展名获取文件类型那个
    private String getMimeType(String extension) {
        if (extension == null) {
            extension = "";
        }
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        //通用mimeType，字节流
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }

    /**
     * 将文件上传到minio
     *
     * @param localFilePath 文件本地路径
     * @param mimeType      媒体类型
     * @param bucket        桶
     * @param objectName    对象名
     * @return boolean
     */
    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    //桶
                    .bucket(bucket)
                    //指定本地文件路径
                    .filename(localFilePath)
                    //对象名 放在子目录下
                    .object(objectName)
                    //设置媒体文件类型
                    .contentType(mimeType)
                    .build();
            //上传文件
            minioClient.uploadObject(uploadObjectArgs);
            log.debug("上传文件到minio成功,bucket:{},objectName:{}", bucket, objectName);
            return true;
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //获取文件默认存储目录路径 年/月/日
    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).replace("-", "/") + "/";
    }

    //获取文件的md5
    private String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) throws InsertEntityFailedException {

        //文件名
        String filename = uploadFileParamsDto.getFilename();
        //先得到扩展名
        String extension = filename.substring(filename.lastIndexOf("."));

        //得到mimeType
        String mimeType = getMimeType(extension);

        //子目录
        String defaultFolderPath = getDefaultFolderPath();
        //文件的md5值
        String fileMd5 = getFileMd5(new File(localFilePath));
        String objectName = defaultFolderPath + fileMd5 + extension;
        //上传文件到minio
        boolean result = addMediaFilesToMinIO(localFilePath, mimeType, bucket_mediafiles, objectName);
        if (!result) {
            throw new InsertEntityFailedException();
        }
        //入库文件信息
        MediaFiles mediaFiles = addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_mediafiles, objectName);
        if (mediaFiles == null) {
            throw new InsertEntityFailedException("上传后保存文件信息失败");
        }
        //准备返回的对象
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);

        return uploadFileResultDto;
    }


    /**
     * 将文件信息添加到文件表
     *
     * @param companyId           机构id
     * @param fileMd5             文件md5值
     * @param uploadFileParamsDto 上传文件的信息
     * @param bucket              桶
     * @param objectName          对象名称
     */
    @Transactional
    @Override
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
        //将文件信息保存到数据库
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            mediaFiles = new MediaFiles();
            BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
            //文件id
            mediaFiles.setId(fileMd5);
            //机构id
            mediaFiles.setCompanyId(companyId);
            //桶
            mediaFiles.setBucket(bucket);
            //file_path
            mediaFiles.setFilePath(objectName);
            //file_id
            mediaFiles.setFileId(fileMd5);
            //url
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            //上传时间
            mediaFiles.setCreateDate(LocalDateTime.now());
            //状态
            mediaFiles.setStatus("1");
            //审核状态
            mediaFiles.setAuditStatus("002003");
            //插入数据库
            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert <= 0) {
                log.debug("向数据库保存文件失败,bucket:{},objectName:{}", bucket, objectName);
                return null;
            }
            return mediaFiles;

        }
        return mediaFiles;

    }

    @Override
    public RestResponse<Boolean> checkFile(String Md5) {
        MediaFiles mediaFiles = mediaFilesMapper.selectById(Md5);
        if (mediaFiles != null) {
            //查找桶
            String bucket = mediaFiles.getBucket();
//          //查找文件路径
            String filePath = mediaFiles.getFilePath();
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build();
            //查询远程服务获取的一个对象
            try {
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                if (inputStream != null) {
                    //文件已经存在
                    return RestResponse.success(true);
                }
            } catch (Exception e) {
                log.error("Exception thread in main:{}:{}",e.getClass().getSimpleName(),e.getMessage());
            }

        }
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> uploadChunk(String md5, int chunk, String localChunkFilePath) {
        //分块文件的路径
        String chunkFilePath = getChunkFileFolderPath(md5) + chunk;
        //获取mimeType
        String mimeType = getMimeType(null);
        //将分块文件上传到minio
        boolean b = addMediaFilesToMinIO(localChunkFilePath, mimeType, bucket_video, chunkFilePath);
        if(!b){
            return RestResponse.validfail(false,"上传分块文件失败");
        }
        //上传成功
        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> mergeChunk(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {
        //分块文件所在目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //找到所有的分块文件
        List<ComposeSource> sources = Stream.iterate(0, i -> ++i).limit(chunkTotal).map(i -> ComposeSource.builder().bucket(bucket_video).object(chunkFileFolderPath + i).build()).collect(Collectors.toList());
        //源文件名称
        String filename = uploadFileParamsDto.getFilename();
        //扩展名
        String extension = filename.substring(filename.lastIndexOf("."));
        //合并后文件的objectname
        String objectName = getFilePathByMd5(fileMd5, extension);
        //指定合并后的objectName等信息
        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket(bucket_video)
                //合并后的文件的objectname
                .object(objectName)
                //指定源文件
                .sources(sources)
                .build();
        //===========合并文件============
        //报错size 1048576 must be greater than 5242880，minio默认的分块文件大小为5M
        try {
            minioClient.composeObject(composeObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("合并文件出错,bucket:{},objectName:{},错误信息:{}",bucket_video,objectName,e.getMessage());
            return RestResponse.validfail(false,"合并文件异常");
        }

        //===========校验合并后的和源文件是否一致，视频上传才成功===========
        //先下载合并后的文件
        File file = downloadFileFromMinio(bucket_video, objectName);
        try(FileInputStream fileInputStream = new FileInputStream(file)){
            //计算合并后文件的md5
            String mergeFileMd5 = DigestUtils.md5Hex(fileInputStream);
            //比较原始md5和合并后文件的md5
            if(!fileMd5.equals(mergeFileMd5)){
                log.error("校验合并文件md5值不一致,原始文件:{},合并文件:{}",fileMd5,mergeFileMd5);
                return RestResponse.validfail(false,"文件校验失败");
            }
            //文件大小
            uploadFileParamsDto.setFileSize(file.length());
        }catch (Exception e) {
            return RestResponse.validfail(false,"文件校验失败");
        }

        //将文件信息入库
        MediaFiles mediaFiles =addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_video, objectName);
        if(mediaFiles == null){
            return RestResponse.validfail(false,"文件入库失败");
        }
        //清理分块文件
        clearChunkFiles(chunkFileFolderPath,chunkTotal);

        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunk) {
        //根据md5得到分块文件所在目录的路径
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);

        //如果数据库存在再查询 minio
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucket_video)
                .object(chunkFileFolderPath+chunk)
                .build();
        //查询远程服务获取到一个流对象
        try {
            FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
            if(inputStream!=null){
                //文件已存在
                return RestResponse.success(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //文件不存在
        return RestResponse.success(false);
    }

    /**
     * 清除分块文件
     * @param chunkFileFolderPath 分块文件路径
     * @param chunkTotal 分块文件总数
     */
    private void clearChunkFiles(String chunkFileFolderPath,int chunkTotal){
        Iterable<DeleteObject> objects =  Stream.iterate(0, i -> ++i).limit(chunkTotal).map(i -> new DeleteObject(chunkFileFolderPath+ i)).collect(Collectors.toList());;
        RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder().bucket(bucket_video).objects(objects).build();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
        //要想真正删除
        results.forEach(f->{
            try {
                DeleteError deleteError = f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
    /**
     * 从minio下载文件
     * @param bucket 桶
     * @param objectName 对象名称
     * @return 下载后的文件
     */
    public File downloadFileFromMinio(String bucket, String objectName){
        //临时文件
        File minioFile = null;
        FileOutputStream outputStream = null;
        try{
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            //创建临时文件
            minioFile=File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IOUtils.copy(stream,outputStream);
            return minioFile;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 得到合并后的文件的地址
     * @param fileMd5 文件id即md5值
     * @param fileExt 文件扩展名
     */
    private String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }

    //得到分块文件的目录
    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/" + "chunk" + "/";
    }
}
