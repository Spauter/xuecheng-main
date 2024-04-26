package com.bloducspauter.content.model.dto;


import com.bloducspauter.content.model.po.CourseCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Bloduc Spauter
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseCategoryTreeDto extends CourseCategory implements java.io.Serializable {

   //子节点
   List<CourseCategoryTreeDto> childrenTreeNodes;

}
