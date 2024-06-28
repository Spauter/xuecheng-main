package com.bloducspauter.media.service;

import com.bloducspauter.base.exceptions.InsertEntityFailedException;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.base.model.RestResponse;
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

    /**
     * 检测文件是否存在
     * @param Md5 文件md5
     */
    RestResponse<Boolean>checkFile(String Md5);

    /**
     * 上传分块
     * @param md5 MD5
     * @param chunk 分块数量
     * @param localChunkFilePath 本地分块路径
     * @return
     */
    RestResponse<Boolean> uploadChunk(String md5,int chunk,String localChunkFilePath);

    /**
     * 合并分块
     * @param companyId 机构Id
     * @param fileMd5 文件MD5
     * @param chunkTotal 分块总和
     * @param uploadFileParamsDto 文件信息
     */
    RestResponse<Boolean>mergeChunk(Long companyId,String fileMd5,int chunkTotal,UploadFileParamsDto uploadFileParamsDto);

    /**
     * 检测分块是否存在
     * @param fileMd5 文件MD5
     * @param chunk 分块数量
     */
    RestResponse<Boolean> checkChunk(String fileMd5, int chunk);
}
