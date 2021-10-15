package com.yangk.demoproject.service.sys;

import com.yangk.demoproject.dao.sys.SysUserDao;
import com.yangk.demoproject.model.sys.SysUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author yangk
 * @date 2021/10/15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class SysUserServiceTest {

    @SpyBean
    SysUserService sysUserService;

    @MockBean
    SysUserDao sysUserDao;
    @MockBean
    SysAutoCodeService sysAutoCodeService;

    List<SysUser> sysUsers;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        sysUsers = new ArrayList<>();
        SysUser sysUser = new SysUser();
        sysUser.setUsername("unit test");
        sysUsers.add(sysUser);
    }

    @Test
    public void selectSysUsersTest(){
        when(sysUserDao.select(any())).thenReturn(sysUsers);
        List<SysUser> sysUsers = sysUserService.selectSysUsers(new SysUser());

        Assert.assertEquals(sysUsers.size(), 1);
    }
}
