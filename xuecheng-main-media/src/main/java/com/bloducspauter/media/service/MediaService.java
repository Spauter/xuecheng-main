package com.bloducspauter.media.service;

import com.bloducspauter.base.exceptions.InsertEntityFailedException;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.media.entities.po.MediaFiles;
import com.bloducspauter.media.entities.dto.*;

/**
 * @author Bloduc Spauter
 */
public interface MediaService {

    /**
     * 分页查询
     *
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.bloducspauter.base.model.PageResult<com.bloducspauter.media.model.po.MediaFiles>
     */
    PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

    /**
     * 上传文件
     *
     * @param companyId           机构id
     * @param uploadFileParamsDto 文件信息
     * @param localFilePath       文件本地路径
     * @return UploadFileResultDto
     */
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) throws InsertEntityFailedException;

    MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);
}
