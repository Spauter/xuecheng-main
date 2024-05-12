package com.bloducspauter.media.entities.dto;

import lombok.Data;
import lombok.ToString;

/**
 *被上传的文件参数类
 * @author Bloduc Spauter
 *
 */
@Data
@ToString
public class UploadFileParamsDto {

    /**
     * 文件名称
     */
    private String filename;


    /**
     * 文件类型（文档，音频，视频）
     */
    private String fileType;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 标签
     */
    private String tags;
    /**
     * 上传人
     */
    private String username;
    /**
     * 备注
     */
    private String remark;

}
