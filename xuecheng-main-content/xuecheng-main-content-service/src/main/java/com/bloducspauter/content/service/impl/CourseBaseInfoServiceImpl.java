package com.bloducspauter.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bloducspauter.base.enums.CommonError;
import com.bloducspauter.base.exceptions.IllegalParamException;
import com.bloducspauter.base.exceptions.InsertEntityFailedException;
import com.bloducspauter.base.model.PageParams;
import com.bloducspauter.base.model.PageResult;
import com.bloducspauter.content.mapper.CourseBaseMapper;
import com.bloducspauter.content.mapper.CourseMarketMapper;
import com.bloducspauter.content.model.dto.AddCourseDto;
import com.bloducspauter.content.model.dto.CourseBaseInfoDto;
import com.bloducspauter.content.model.dto.EditCourseDto;
import com.bloducspauter.content.model.dto.QueryCourseParamsDto;
import com.bloducspauter.content.model.po.CourseBase;
import com.bloducspauter.content.model.po.CourseMarket;
import com.bloducspauter.content.service.CourseBaseInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 关于{@link QueryCourseParamsDto#setAuditStatus(String)}，请在数据库输入查询语句
 * {@code select * from db_mybatis.dictionary where code=202}
 *
 * @author Bloduc Spauter
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Resource
    private CourseBaseMapper courseBaseMapper;

    @Resource
    private CourseMarketMapper courseMarketMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //根据名称模糊查询,在sql中拼接 course_base.name like '%值%'
        queryCourseParamsDto=queryCourseParamsDto==null?new QueryCourseParamsDto():queryCourseParamsDto;
        String getCourseName=queryCourseParamsDto.getCourseName();
        boolean name=StringUtils.isNotEmpty(getCourseName);
        String courseName=queryCourseParamsDto.getCourseName();
        queryWrapper.like(name,CourseBase::getName,courseName);
        //根据课程审核状态查询 course_base.audit_status = ?
        boolean auditStatus=StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus());
        String status=queryCourseParamsDto.getAuditStatus();
        queryWrapper.eq(auditStatus, CourseBase::getAuditStatus,status);

        long pageNo = pageParams.getPageNo();
        long pageSize = pageParams.getPageSize();
        Page<CourseBase> courseBasePage = new Page<>(pageNo, pageSize);
        //开始进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(courseBasePage, queryWrapper);
        //数据列表
        List<CourseBase> courseBaseList = pageResult.getRecords();
        long total = courseBasePage.getTotal();
        return new PageResult<>(courseBaseList, total, pageSize);
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) throws InsertEntityFailedException {
        //向课程基本信息表course_base写入数据
        CourseBase courseBaseNew = new CourseBase();
        //将传入的页面的参数放到courseBaseNew对象
        //上边的从原始对象中get拿数据向新对象set，比较复杂
        //只要属性名称一致就可以拷贝
        BeanUtils.copyProperties(dto, courseBaseNew);
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //审核状态默认为未提交
        courseBaseNew.setAuditStatus("202002");
        //发布状态为未发布
        courseBaseNew.setStatus("203001");
        //插入数据库
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert <= 0) {
            throw new InsertEntityFailedException("添加课程失败");
        }

        //向课程营销系courese_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();
        //将页面输入的数据拷贝到courseMarketNew
        BeanUtils.copyProperties(dto, courseMarketNew);
        //课程的id
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        //保存营销信息
        saveCourseMarket(courseMarketNew);
        //从数据库查询课程的详细信息，包括两部分

        return getCourseBaseInfo(courseId);
    }

    //查询课程信息
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId){

        //从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            return null;
        }
        //从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        //组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket!=null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }
        //通过courseCategoryMapper查询分类信息，将分类名称放在courseBaseInfoDto对象
        //todo：课程分类的名称设置到courseBaseInfoDto
        return courseBaseInfoDto;
    }

    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) throws InsertEntityFailedException {

        //拿到课程id
        Long courseId = editCourseDto.getId();
        //查询课程信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            IllegalParamException.cast(CommonError.valueOf("课程不存在"));
        }

        //数据合法性校验
        //根据具体的业务逻辑去校验
        //本机构只能修改本机构的课程
        if (!companyId.equals(courseBase.getCompanyId())) {
            IllegalParamException.cast(CommonError.valueOf("本机构只能修改本机构的课程"));
        }

        //封装数据
        BeanUtils.copyProperties(editCourseDto, courseBase);
        //修改时间
        courseBase.setChangeDate(LocalDateTime.now());

        //更新数据库
        int i = courseBaseMapper.updateById(courseBase);
        if (i <= 0) {
            throw new InsertEntityFailedException("修改课程失败");
        }
        //更新营销信息
        //todo:更新营销信息
        //查询课程信息

        return getCourseBaseInfo(courseId);
    }

    //单独写一个方法保存营销信息，逻辑：存在则更新，不存在则添加
    private int saveCourseMarket(CourseMarket courseMarketNew) {

        //参数的合法性校验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isBlank(charge)) {
            IllegalParamException.cast(CommonError.OBJECT_NULL,"收费规则为空");
        }
        //如果课程收费，价格没有填写也需要抛出异常
        if (charge.equals("201001")) {
            if (courseMarketNew.getPrice() == null || courseMarketNew.getPrice() <= 0) {
                IllegalParamException.cast(CommonError.PARAMS_ERROR,"课程的价格不能为空并且必须大于0");
            }
        }
        //从数据库查询营销信息,存在则更新，不存在则添加
        Long id = courseMarketNew.getId();//主键
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket == null) {
            //插入数据库
            return courseMarketMapper.insert(courseMarketNew);
        } else {
            //将courseMarketNew拷贝到courseMarket
            BeanUtils.copyProperties(courseMarketNew, courseMarket);
            courseMarket.setId(courseMarketNew.getId());
            //更新
            return courseMarketMapper.updateById(courseMarket);
        }


    }
}
