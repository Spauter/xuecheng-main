package com.bloducspauter.content.controller;


import com.bloducspauter.content.model.dto.SaveTeachPlanDto;
import com.bloducspauter.content.model.dto.TeachPlanDto;
import com.bloducspauter.content.service.TeachPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 课程计划管理相关的接口
 *
 * @author Bloduc Spauter
 */
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口")
@RestController
@RequestMapping("content/")
public class TeachPlanController {

    @Resource
    TeachPlanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    //查询课程计划  GET /teachplan/22/tree-nodes
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachPlanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.findTeachPlanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachPlanDto teachplan) {
        teachplanService.saveTeachPlan(teachplan);
    }

}
