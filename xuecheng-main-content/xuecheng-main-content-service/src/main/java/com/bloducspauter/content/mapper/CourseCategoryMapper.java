package com.bloducspauter.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bloducspauter.content.model.dto.CourseCategoryTreeDto;
import com.bloducspauter.content.model.po.CourseCategory;


import java.util.List;

/**
 * @author Bloduc Spauter
 *
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    //使用递归查询分类
    public List<CourseCategoryTreeDto> selectTreeNodes(String id);

}
