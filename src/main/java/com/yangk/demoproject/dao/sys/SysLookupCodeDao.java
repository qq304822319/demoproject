package com.yangk.demoproject.dao.sys;

import com.yangk.demoproject.common.mapper.TkMybatisMapper;
import com.yangk.demoproject.model.sys.SysLookupCode;

import java.util.List;
import java.util.Map;

/**
 * @author yangk
 * @date 2020/3/18
 */
public interface SysLookupCodeDao extends TkMybatisMapper<SysLookupCode> {

    /**
     * 根据通用代码类型获取通用代码列表
     *
     * @param lookupType
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author yangk
     * @date 2020/3/18
     */
    List<Map<String, Object>> selectSysLookupCodeByType(String lookupType);

}
