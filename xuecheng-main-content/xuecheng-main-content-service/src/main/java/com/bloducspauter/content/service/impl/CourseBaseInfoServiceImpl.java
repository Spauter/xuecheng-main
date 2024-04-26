package com.bloducspauter.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.content.mapper.CourseBaseMapper;
import com.bloducspauter.content.model.dto.QueryCourseParamsDto;
import com.bloducspauter.content.model.po.CourseBase;
import com.bloducspauter.content.service.CourseBaseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *关于{@link QueryCourseParamsDto#setAuditStatus(String)}，请在数据库输入查询语句
 * {@code select * from db_mybatis.dictionary where code=202}
 * @author Bloduc Spauter
 *
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Resource
    private CourseBaseMapper courseBaseMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //根据名字模糊查询
        queryWrapper.like(StringUtils.isNotBlank(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());
        //根据审核状态准确查询
        queryWrapper.eq(StringUtils.isNotBlank(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        //获取分页参数
        long pageNo= pageParams.getPageOn();
        long pageSize=pageParams.getPageSize();
        Page<CourseBase> courseBasePage = new Page<>(pageNo,pageSize);
        //开始进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(courseBasePage, queryWrapper);
        //数据列表
        List<CourseBase> courseBaseList=pageResult.getRecords();
        long total = courseBasePage.getTotal();
        return new PageResult<>(courseBaseList, total, pageSize);
    }
}
