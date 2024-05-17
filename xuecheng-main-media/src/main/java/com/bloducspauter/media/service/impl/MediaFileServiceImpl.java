package com.bloducspauter.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bloducspauter.base.enums.CommonError;
import com.bloducspauter.base.exceptions.IllegalParamException;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.media.entities.dto.QueryMediaParamsDto;
import com.bloducspauter.media.entities.dto.UploadFileParamsDto;
import com.bloducspauter.media.entities.dto.UploadFileResultDto;
import com.bloducspauter.media.entities.po.MediaFiles;
import com.bloducspauter.media.mapper.MediaFilesMapper;
import com.bloducspauter.media.service.MediaService;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Bloduc Spauter
 * 
 */
@Slf4j
@Service
public class MediaFileServiceImpl implements MediaService {

    @Resource
    MediaFilesMapper mediaFilesMapper;

    @Resource
    MinioClient minioClient;

    @Resource
    MediaService currentProxy;

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
    private String getMimeType(String extension){
        if(extension == null){
            extension = "";
        }
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        //通用mimeType，字节流
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(extensionMatch!=null){
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }

    /**
     * 将文件上传到minio
     * @param localFilePath 文件本地路径
     * @param mimeType 媒体类型
     * @param bucket 桶
     * @param objectName 对象名
     * @return boolean
     */
    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName){
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
            log.debug("上传文件到minio成功,bucket:{},objectName:{}",bucket,objectName);
            return true;
        } catch (Exception e) {
           log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}",bucket,objectName,e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //获取文件默认存储目录路径 年/月/日
    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).replace("-", "/")+"/";
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
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {

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
        String objectName = defaultFolderPath+fileMd5+extension;
        //上传文件到minio
        boolean result = addMediaFilesToMinIO(localFilePath, mimeType, bucket_mediafiles, objectName);
        if(!result){
            IllegalParamException.cast(CommonError.valueOf("上传文件失败"));
        }
        //入库文件信息
        MediaFiles mediaFiles = currentProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_mediafiles, objectName);
        if(mediaFiles==null){
            IllegalParamException.cast(CommonError.valueOf("文件上传后保存信息失败"));
        }
        //准备返回的对象
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles,uploadFileResultDto);

        return uploadFileResultDto;
    }


    /**
     * @param companyId  机构id
     * @param fileMd5  文件md5值
     * @param uploadFileParamsDto  上传文件的信息
     * @param bucket  桶
     * @param objectName 对象名称
     */
    @Transactional
    @Override
    public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName){
        //将文件信息保存到数据库
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if(mediaFiles == null){
            mediaFiles = new MediaFiles();
            BeanUtils.copyProperties(uploadFileParamsDto,mediaFiles);
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
            mediaFiles.setUrl("/"+bucket+"/"+objectName);
            //上传时间
            mediaFiles.setCreateDate(LocalDateTime.now());
            //状态
            mediaFiles.setStatus("1");
            //审核状态
            mediaFiles.setAuditStatus("002003");
            //插入数据库
            int insert = mediaFilesMapper.insert(mediaFiles);
            if(insert<=0){
                log.debug("向数据库保存文件失败,bucket:{},objectName:{}",bucket,objectName);
                return null;
            }
            return mediaFiles;

        }
        return mediaFiles;

    }
}
