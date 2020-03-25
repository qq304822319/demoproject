package com.yangk.demoproject.service.sys;

import com.yangk.demoproject.dao.sys.SysRoleDao;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统角色Service
 *
 * @author yangk
 * @date dateString
 */
@Slf4j
@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 查询系统角色
     *
     * @param sysRole
     * @return java.util.List<SysRole>
     * @author yangk
     * @date dateString
     */
    public List<SysRole> selectSysRoles(SysRole sysRole) {
        return sysRoleDao.select(sysRole);
    }

    /**
     * 通过主键查询系统角色
     *
     * @param key
     * @return SysRole
     * @author yangk
     * @date dateString
     */
    public SysRole selectSysRoleByPrimaryKey(Object key) {
        return sysRoleDao.selectByPrimaryKey(key);
    }

    /**
     * 保存系统角色
     *
     * @param sysRole
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveSysRole(SysRole sysRole,
                              LoginUserDto loginUserDto) {
        if (StringUtils.isEmpty(sysRole.getId())) {
            return this.insertSysRole(sysRole);
        } else {
            return this.updateSysRole(sysRole);
        }
    }

    /**
     * 新增系统角色
     *
     * @param sysRole
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertSysRole(SysRole sysRole) {
        sysRoleDao.insertSelective(sysRole);
        String id = sysRole.getId();
        return id;
    }

    /**
     * 修改系统角色
     *
     * @param sysRole
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateSysRole(SysRole sysRole) {
        sysRoleDao.updateByPrimaryKeySelective(sysRole);
        String id = sysRole.getId();
        return id;
    }

    /**
     * 删除系统角色
     *
     * @param keys
     * @return int
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysRole(Object[] keys) {
        int count = 0;
        for (Object key : keys) {
            sysRoleDao.deleteByPrimaryKey(key);
            count++;
        }
        return count;
    }

}