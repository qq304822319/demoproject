package com.yangk.demoproject.controller.workflow.model;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.common.utils.PageUtils;
import com.yangk.demoproject.model.sys.SysRole;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysRoleService;
import com.yangk.demoproject.service.sys.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 流程选人
 *
 * @author yangk
 * @date 2020/3/24
 */
@RestController
@RequestMapping("/activiti")
public class ActivitiUserRolesController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/userList/page")
    @ApiOperation(value = "分页查询所有用户", notes = "分页返回用户列表")
    public Response userPage(@RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);
        SysUser sysUser = JSON.parseObject(JSON.toJSONString(conditions), SysUser.class);
        List<SysUser> list = sysUserService.selectSysUsers(sysUser);
        return Response.returnData(list, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @GetMapping("/roleList/page")
    @ApiOperation(value = "分页查询所有角色", notes = "分页返回角色列表")
    public Response rolesPage(@RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);
        SysRole sysRole = JSON.parseObject(JSON.toJSONString(conditions), SysRole.class);
        List<SysRole> list = sysRoleService.selectSysRoles(sysRole);
        return Response.returnData(list, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @GetMapping("/user/userListByRoleId")
    @ApiOperation(value = "分页通过角色id查询用户列表", notes = "分页返回该角色id下用户列表")
    public Response getUserListByRoleId(@RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);
        SysUser sysUser = JSON.parseObject(JSON.toJSONString(conditions), SysUser.class);
        sysUser.setSysRoleId(conditions.get("roleId").toString());
        List<SysUser> list = sysUserService.selectSysUsers(sysUser);
        return Response.returnData(list, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

}
