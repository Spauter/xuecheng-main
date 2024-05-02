package com.bloducspauter.system.service;

import java.util.List;
import com.bloducspauter.content.model.dto.CourseCategoryTreeDto;
public interface CourseCategoryService {
    List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
