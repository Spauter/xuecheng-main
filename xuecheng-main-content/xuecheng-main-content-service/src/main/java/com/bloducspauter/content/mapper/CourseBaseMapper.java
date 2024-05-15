package com.bloducspauter.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bloducspauter.content.model.po.CourseBase;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Bloduc Spauter
 */
public interface CourseBaseMapper extends BaseMapper<CourseBase> {
/*
Mybatis优点:
    1.基于sql语句编程，相当灵活，消除了JDBC大量冗余代码，不需要手动进行数据库链接
    2.能与各种数据库兼容
    3.能集成Spring
    4.能提供映射标签
缺点：
    1.对SQL语句有一定要求
    2.数据库移植性比较差
#{}与${}
    前者是预编译小狐狸，后者是字符替换
    前者在处理时会变成?然后使用PreparedStatement的set注入
    后者直接替换
    前者能防止SQL注入，提高系统安全性
 */
}
