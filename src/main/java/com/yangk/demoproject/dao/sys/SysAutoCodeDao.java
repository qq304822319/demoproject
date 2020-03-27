package com.yangk.demoproject.dao.sys;

import com.yangk.demoproject.common.mapper.TkMybatisMapper;
import com.yangk.demoproject.model.sys.SysAutoCode;
import org.springframework.stereotype.Repository;

/**
 * 自动编码Dao
 *
 * @author yangk
 * @date 2020/3/26
 */
@Repository
public interface SysAutoCodeDao extends TkMybatisMapper<SysAutoCode> {

    /**
     * 根据autoCodeKey查询自动编码
     *
     * @param autoCodeKey
     * @return com.yangk.demoproject.model.sys.SysAutoCode
     * @author yangk
     * @date 2020/3/26
     */
    SysAutoCode getSysAutoCodeByKey(String autoCodeKey);
}
