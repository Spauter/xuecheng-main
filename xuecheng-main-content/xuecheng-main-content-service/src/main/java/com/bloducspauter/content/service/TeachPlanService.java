package com.bloducspauter.content.service;



import com.bloducspauter.content.model.dto.SaveTeachPlanDto;
import com.bloducspauter.content.model.dto.TeachPlanDto;

import java.util.List;

/**
 * @author Bloduc Spauter
 *
 */
public interface TeachPlanService {
 /**
  * 根据课程id查询课程计划
  * @param courseId 课程计划
  * @return
  */
 List<TeachPlanDto> findTeachPlanTree(Long courseId);

 /**
  * 新增/修改/保存课程计划
  * @param saveTeachPlanDto 课程
  */
 void saveTeachPlan(SaveTeachPlanDto saveTeachPlanDto);
}
