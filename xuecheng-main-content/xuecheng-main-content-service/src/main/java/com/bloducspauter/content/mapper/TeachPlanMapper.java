package com.bloducspauter.content.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bloducspauter.content.model.dto.TeachPlanDto;
import com.bloducspauter.content.model.po.Teachplan;

import java.util.List;

/**
 * @author Bloduc Spauter
 *
 */
public interface TeachPlanMapper extends BaseMapper<Teachplan> {

    List<TeachPlanDto> selectTreeNodes(Long courseId);
}
