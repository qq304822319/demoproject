package com.yangk.demoproject.service.workflow;

import com.yangk.demoproject.annotation.LoginUser;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class FlowTaskService {
    @Resource
    TaskService taskService;

    @Resource
    SysUserService sysUserService;

    /**
     * 任务签收
     *
     * @param loginUserDto
     * @param taskId
     * @return void
     * @author yangk
     * @date 2020/3/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void claim(@LoginUser LoginUserDto loginUserDto, String taskId) {
        taskService.claim(taskId, loginUserDto.getUsername());
        log.info(loginUserDto.getUsername() + "签收任务ID：" + taskId);
    }

    /**
     * 任务委托
     *
     * @param loginUserDto
     * @param taskId
     * @param userId
     * @return void
     * @author yangk
     * @date 2020/3/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void delegateTask(@LoginUser LoginUserDto loginUserDto, String taskId, String userId) {
        SysUser sysUser = sysUserService.selectSysUserByPrimaryKey(userId);
        taskService.delegateTask(taskId, sysUser.getUsername());
        log.info(loginUserDto.getUsername() + "委托任务ID：" + taskId + "到用户：" + sysUser.getUsername());
    }
}
