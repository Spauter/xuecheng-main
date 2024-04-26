package com.bloducspauter.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.content.model.dto.QueryCourseParamsDto;
import com.bloducspauter.content.model.po.CourseBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.bloducspauter.content.mapper.CourseBaseMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseBaseMapperTest {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Test
    public void testSelectById() {
        CourseBase courseBase = courseBaseMapper.selectById(1);
        System.out.println(courseBase);
        Assertions.assertNotNull(courseBase);
    }

    @Test
    public void testPageResult() {
        PageParams pageParams = new PageParams();
        pageParams.setPageOn(1L);
        pageParams.setPageSize(10L);
        QueryCourseParamsDto queryCourseParamsDto = new QueryCourseParamsDto();
        queryCourseParamsDto.setCourseName("java");
        queryCourseParamsDto.setAuditStatus(String.valueOf(202004));
        PageResult<CourseBase>pageResult=queryCourseBaseList(pageParams, queryCourseParamsDto);
        System.out.println(pageResult);
        Assertions.assertNotNull(pageResult);
    }

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