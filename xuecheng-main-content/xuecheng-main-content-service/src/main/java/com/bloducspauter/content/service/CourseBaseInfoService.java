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
 */
public interface CourseBaseInfoService {
    /**
     * 筛选分页查询结果
     * Mybatis使用RowBounds分页，它是针对ResultSet结果集进行内存分页，而非物理分页
     * 可以在SQL内直接拼接带有物理分页的参数来完成物理分页功能,也可以通过分页插件进行分页
     * 比如MySQL的limit.
     * <p>
     * 分页插件的基本原理是用Mybatis提供的插件接口,实现自定义插件,
     * 在插件的拦截方法拦截执行的SQL然后重写SQl
     * 根据Dialect方言,添加对物理分页语句和分页参数
     * </p>
     *
     * @param pageParams           分页数据
     * @param queryCourseParamsDto 查询条件
     * @return PageResult
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 新增课程
     *
     * @param id           课程ID
     * @param addCourseDto AddCourseDto
     * @return CourseBaseInfoDto
     */
    CourseBaseInfoDto createCourseBase(Long id, AddCourseDto addCourseDto) throws InsertEntityFailedException;


    /**
     * 根据课程id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程详细信息
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 修改课程
     *
     * @param companyId     机构id
     * @param editCourseDto 修改课程信息
     * @return 课程详细信息
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) throws InsertEntityFailedException;
}
