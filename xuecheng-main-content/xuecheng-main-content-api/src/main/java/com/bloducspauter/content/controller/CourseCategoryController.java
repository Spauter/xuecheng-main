package com.bloducspauter.content.controller;




import com.bloducspauter.content.model.dto.CourseCategoryTreeDto;
import com.bloducspauter.system.service.CourseCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Bloduc Spauter
 *
 */
@RestController
public class CourseCategoryController {

    //暂时调用service模块里面的CourseCategoryService
    @Resource
    CourseCategoryService courseCategoryService;


    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return courseCategoryService.selectTreeNodes("1");
    }

}
