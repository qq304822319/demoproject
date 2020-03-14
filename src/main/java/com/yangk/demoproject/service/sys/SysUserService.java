package com.yangk.demoproject.service.sys;


import com.yangk.demoproject.dao.sys.SysUserDao;
import com.yangk.demoproject.model.sys.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户Service
 *
 * @author yangk
 * @date 2020/3/14
 */
@Slf4j
@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 查询用户
     *
     * @param sysUser
     * @return java.util.List<SysUser>
     * @author yangk
     * @date 2020/3/14
     */
    public List<SysUser> selectSysUsers(SysUser sysUser) {
        return sysUserDao.select(sysUser);
    }

    /**
     * 通过主键查询用户
     *
     * @param key
     * @return SysUser
     * @author yangk
     * @date 2020/3/14
     */
    public SysUser selectSysUserByPrimaryKey(Object key) {
        return sysUserDao.selectByPrimaryKey(key);
    }

    /**
     * 新增用户
     *
     * @param sysUser
     * @param loginUserDto
     * @return java.lang.String
     * @author yangk
     * @date 2020/3/14
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertSysUser(SysUser sysUser) {
        sysUserDao.insertSelective(sysUser);
        String id = sysUser.getId();
        return id;
    }

    /**
     * 修改用户
     *
     * @param sysUser
     * @param loginUserDto
     * @return java.lang.String
     * @author yangk
     * @date 2020/3/14
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateSysUser(SysUser sysUser) {
        sysUserDao.updateByPrimaryKeySelective(sysUser);
        String id = sysUser.getId();
        return id;
    }

    /**
     * 删除用户
     *
     * @param keys
     * @return int
     * @author yangk
     * @date 2020/3/14
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysUser(Object[] keys) {
        int count = 0;
        for (Object key : keys) {
            sysUserDao.deleteByPrimaryKey(key);
            count++;
        }
        return count;
    }
}
