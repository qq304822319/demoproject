package com.yangk.demoproject.dao.sys;

import com.yangk.demoproject.common.mapper.TkMybatisMapper;
import com.yangk.demoproject.model.sys.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author yangk
 * @date 2020/3/14
 */
@Repository
public interface SysUserDao extends TkMybatisMapper<SysUser> {

    /**
     * 查询用户名是否存在
     *
     * @param sysUser
     * @return int
     * @author yangk
     * @date 2020/3/27
     */
    int countByUserName(SysUser sysUser);
}
