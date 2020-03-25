package com.yangk.demoproject.controller.workflow.flow;

import com.github.pagehelper.Page;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.common.utils.PageUtils;
import com.yangk.demoproject.dto.workflow.FlowProcinstDto;
import com.yangk.demoproject.service.workflow.FlowProinstService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/flow/procinst")
@Api(tags = "工作流-流程实例相关接口")
public class FlowProcinstController {

    @Autowired
    private FlowProinstService flowProinstService;

    @GetMapping("/page")
    @ApiOperation(value = "流程实例列表", notes = "流程实例分页列表接口")
    public Response page(@RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);
        List<FlowProcinstDto> procinstList = flowProinstService.selectProcinst(conditions);
        return Response.returnData(procinstList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除流程实例", notes = "删除流程实例")
    public Response delete(@Valid String procInstanceId, String deleteReason) {
        log.debug("删除流程实例");
        flowProinstService.delete(procInstanceId, deleteReason);
        return Response.ok();
    }

    @PostMapping("/suspend/{id}")
    @ApiOperation(value = "挂起流程实例", notes = "挂起流程实例")
    public Response suspend(@PathVariable String id) {
        log.debug("挂起流程实例");
        flowProinstService.suspend(id);
        return Response.ok();
    }
}