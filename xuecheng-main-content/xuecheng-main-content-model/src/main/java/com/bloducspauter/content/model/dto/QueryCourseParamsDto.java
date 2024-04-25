package com.bloducspauter.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 课程查询条件模拟类
 * @author Bloduc Spauter
 *
 */
@Data
@ToString
public class QueryCourseParamsDto {
    private String courseName;
    private String publishStatus;
    private String auditStatus;
}
