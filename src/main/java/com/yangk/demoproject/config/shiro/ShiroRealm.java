package com.yangk.demoproject.config.shiro;

import cn.hutool.core.util.StrUtil;

import com.yangk.demoproject.dao.sys.SysUserDao;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;
/*
    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysResourcesDao sysResourcesDao;*/

    /**
     * 权限验证时调用
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();

        SysUser sysUser = sysUserService.findByUserName(username);
        if (sysUser == null) {
            throw new AuthorizationException();
        }

        //角色名的集合
//        Set<String> roles = new HashSet<>();
//        List<SysRole> sysRoleList = sysRoleDao.findUserRoles(sysUser.getId());
//        for (SysRole sysRole:sysRoleList) {
//            roles.add(sysRole.getCode());
//        }

        //权限名的集合
/*        Set<String> resources = new HashSet<>();
        List<SysResources> sysResourcesList = sysResourcesDao.findUserResources(sysUser.getId());
        for (SysResources sysResources:sysResourcesList) {
            if (!StrUtil.isEmptyIfStr(sysResources.getCode())) {
                resources.add(sysResources.getCode());
            }
        }*/

        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.addRoles(roles);
//                resources.add("sys:log");
//        authorizationInfo.addStringPermissions(resources);

        return authorizationInfo;
    }

    /**
     * 用户验证
     *
     * @param authenticationToken 账户数据
     * @return
     * @throws AuthenticationException 根据账户数据查询账户。根据账户状态抛出对应的异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("【###########################################Shiro 用户认证 Start...#########################################】");
        String username = (String) authenticationToken.getPrincipal();

        SysUser sysUser = sysUserService.findByUserName(username);
        if (sysUser == null) {
            throw new UnknownAccountException();
        }

        //账户禁用
       /* if (!sysUser.getIsEnable()) {
            throw new DisabledAccountException();
        }*/

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser.getUsername(), //用户名
                sysUser.getPassword(), //密码
                ByteSource.Util.bytes(sysUser.getSalt()),
                getName()
        );
        return authenticationInfo;
    }

}
