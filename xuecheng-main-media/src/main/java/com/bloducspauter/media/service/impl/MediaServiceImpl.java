package com.bloducspauter.media.service.impl;

import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.media.entities.dto.QueryMediaParamsDto;
import com.bloducspauter.media.entities.dto.UploadFileParamsDto;
import com.bloducspauter.media.entities.dto.UploadFileResultDto;
import com.bloducspauter.media.entities.po.MediaFiles;
import com.bloducspauter.media.mapper.MediaFilesMapper;
import com.bloducspauter.media.service.MediaService;
import io.minio.MinioClient;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MediaServiceImpl implements MediaService {

    @Resource
    MediaFilesMapper mediaFilesMapper;

    @Resource
    MinioClient minioClient;

    @Resource
    MediaService currentProxy;


    //存储普通文件
    @Value("${minio.bucket.files}")
    private String bucket_media_files;

    //存储视频
    @Value("${minio.bucket.videofiles}")
    private String bucket_video;

    //根据扩展名获取mimeType
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

    @Override
    public PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {
        return null;
    }

    @Override
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {
        return null;
    }

    @Override
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
        return null;
    }
}
