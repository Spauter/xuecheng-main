package com.bloducspauter.content.model.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程基本信息
 * </p>
 *
 * @author Bloduc Spauter
 */
@Data
@TableName("course_base")
@ApiModel("用户实体类")
@NoArgsConstructor
public class CourseBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 机构ID
     */
    @ApiModelProperty("机构ID")
    private Long companyId;

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String companyName;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String name;

    /**
     * 适用人群
     */
    @ApiModelProperty("适用人群")
    private String users;

    /**
     * 课程标签
     */
    @ApiModelProperty("课程标签")
    private String tags;

    /**
     * 大分类
     */
    @ApiModelProperty("大分类")
    private String mt;

    /**
     * 小分类
     */
    @ApiModelProperty("小分类")
    private String st;

    /**
     * 课程等级
     */
    @ApiModelProperty("课程等级")
    private String grade;

    /**
     * 教育模式(common普通，record 录播，live直播等）
     */
    @ApiModelProperty("教育模式")
    private String teachmode;

    /**
     * 课程介绍
     */
    @ApiModelProperty("课程介绍")
    private String description;

    /**
     * 课程图片
     */
    @ApiModelProperty("课程图片")
    private String pic;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeDate;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createPeople;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String changePeople;

    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private String auditStatus;

    /**
     * 课程发布状态 未发布  已发布 下线
     */
    @ApiModelProperty("课程发布状态")
    private String status;


}
