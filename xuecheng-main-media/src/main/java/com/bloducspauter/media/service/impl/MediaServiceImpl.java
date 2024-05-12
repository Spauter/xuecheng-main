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
import org.springframework.beans.factory.annotation.Value;
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
