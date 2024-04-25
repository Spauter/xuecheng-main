package com.bloducspauter.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果
 * @author Bloduc Spauter
 *
 */
@Data
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private List<T> items;
    private long counts;
    private long pageSize;

}
