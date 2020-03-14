package com.yangk.demoproject.common.mapper;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 通用Dao
 *
 * @author yangk
 * @date 2020/3/9
 */
public interface TkMybatisMapper<T> extends IdsMapper<T>, Mapper<T> {

}
