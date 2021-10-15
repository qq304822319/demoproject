package com.yangk.demoproject.controller.sys;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yangk
 * @date 2021/10/15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class SysUserControllerTest {
    @Autowired
    SysUserController sysUserController;

    @MockBean
    SysUserService sysUserService;

    MockMvc mockMvc;
    List<SysUser> list;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(sysUserController).build();
        MockitoAnnotations.initMocks(this);

        list = new ArrayList<>();
        SysUser sysUser = new SysUser();
        sysUser.setUsername("unit test");
        list.add(sysUser);
    }

    @Test
    public void getSysUsersTest() throws Exception {
        when(sysUserService.selectSysUsers(any())).thenReturn(list);

        mockMvc.perform(post("/sysUser/getSysUsers")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{}"))
        .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("0"))
        .andDo(print());
    }
}
