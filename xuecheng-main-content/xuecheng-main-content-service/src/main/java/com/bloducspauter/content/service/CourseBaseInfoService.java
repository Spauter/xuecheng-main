package com.bloducspauter.content.service;


import com.bloducspauter.base.exceptions.InsertEntityFailedException;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.content.model.dto.AddCourseDto;
import com.bloducspauter.content.model.dto.CourseBaseInfoDto;
import com.bloducspauter.content.model.dto.EditCourseDto;
import com.bloducspauter.content.model.dto.QueryCourseParamsDto;
import com.bloducspauter.content.model.po.CourseBase;

/**
 * @author Bloduc Spauter
 *
 */
public interface CourseBaseInfoService {
    /**
     *筛选分页查询结果
     * @param pageParams 分页数据
     * @param queryCourseParamsDto 查询条件
     * @return PageResult
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 新增课程
     * @param id 课程ID
     * @param addCourseDto AddCourseDto
     * @return CourseBaseInfoDto
     */
    CourseBaseInfoDto createCourseBase(Long id,AddCourseDto addCourseDto) throws InsertEntityFailedException;


    /**
     * 根据课程id查询课程信息
     * @param courseId 课程id
     * @return 课程详细信息
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 修改课程
     * @param companyId 机构id
     * @param editCourseDto 修改课程信息
     * @return 课程详细信息
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) throws InsertEntityFailedException;
}
