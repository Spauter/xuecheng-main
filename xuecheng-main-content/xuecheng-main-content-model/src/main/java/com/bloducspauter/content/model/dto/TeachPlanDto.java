package com.bloducspauter.content.model.dto;


import com.bloducspauter.content.model.po.Teachplan;
import com.bloducspauter.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 课程计划信息模型类
 * @author Bloduc Spauter
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class TeachPlanDto extends Teachplan {
  //与媒资管理的信息
   private TeachplanMedia teachplanMedia;

  //小章节list
   private List<TeachPlanDto> teachPlanTreeNodes;
}
