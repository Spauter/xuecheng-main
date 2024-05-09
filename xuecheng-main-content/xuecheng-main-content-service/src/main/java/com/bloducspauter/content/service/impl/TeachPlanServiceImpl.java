package com.bloducspauter.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bloducspauter.content.mapper.TeachPlanMapper;
import com.bloducspauter.content.model.dto.SaveTeachPlanDto;
import com.bloducspauter.content.model.dto.TeachPlanDto;
import com.bloducspauter.content.model.po.Teachplan;
import com.bloducspauter.content.service.TeachPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Bloduc Spauter
 *
 */
@Service
public class TeachPlanServiceImpl implements TeachPlanService {

    @Resource
    TeachPlanMapper teachplanMapper;

    @Override
    public List<TeachPlanDto> findTeachPlanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    private int getTeachPlanCount(Long courseId, Long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(Teachplan::getCourseId, courseId).eq(Teachplan::getParentid, parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return  count+1;
    }
    @Override
    public void saveTeachPlan(SaveTeachPlanDto saveTeachPlanDto) {
        //通过课程计划id判断是新增和修改
        Long teachPlanId = saveTeachPlanDto.getId();
        if(teachPlanId ==null){
            //新增
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachPlanDto,teachplan);
            //确定排序字段，找到它的同级节点个数，排序字段就是个数加1  select count(1) from teachplan where course_id=117 and parentid=268
            Long parentid = saveTeachPlanDto.getParentid();
            Long courseId = saveTeachPlanDto.getCourseId();
            int teachplanCount = getTeachPlanCount(courseId, parentid);
            teachplan.setOrderby(teachplanCount);
            teachplanMapper.insert(teachplan);

        }else{
            //修改
            Teachplan teachplan = teachplanMapper.selectById(teachPlanId);
            //将参数复制到teachplan
            BeanUtils.copyProperties(saveTeachPlanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }

    }
}
