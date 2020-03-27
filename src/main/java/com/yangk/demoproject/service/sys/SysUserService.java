package com.yangk.demoproject.service.sys;


import cn.hutool.core.util.RandomUtil;
import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.config.shiro.ShiroConfig;
import com.yangk.demoproject.dao.sys.SysUserDao;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
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
    @Autowired
    private SysAutoCodeService sysAutoCodeService;

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
    public String insertSysUser(SysUser sysUser,
                                LoginUserDto loginUserDto) throws Exception {
        //验证用户名
        if (existByUserName(sysUser)) {
            throw new ProException(ResponseCode.USER_NAME_IS_HAVE);
        }
        //加密盐,6位随机数
        sysUser.setSalt(RandomUtil.randomString(6));
        //apache.shiro.SimpleHash 加密
        sysUser.setPassword(new SimpleHash(
                ShiroConfig.HASH_ALGORITHM_NAME,
                sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getSalt()),
                ShiroConfig.HASH_ITERATIONS
        ).toHex());
        sysUser.setUserNumber(sysAutoCodeService.getAutoCode("SYS_USER_NO"));
        sysUser.setCreateBy(loginUserDto.getId());
        sysUser.setCreateTime(new Date());
        //保存
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
    public String updateSysUser(SysUser sysUser,
                                LoginUserDto loginUserDto) throws Exception {
        //验证用户名
        if (existByUserName(sysUser)) {
            throw new ProException(ResponseCode.USER_NAME_IS_HAVE);
        }
        //加密盐,6位随机数
        sysUser.setSalt(RandomUtil.randomString(6));
        sysUser.setPassword(new SimpleHash(
                ShiroConfig.HASH_ALGORITHM_NAME,
                sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getSalt()),
                ShiroConfig.HASH_ITERATIONS
        ).toHex());
        sysUser.setCreateBy(loginUserDto.getId());
        sysUser.setCreateTime(new Date());
        //修改
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


    /**
     * 判断用户userName是否存在
     *
     * @param userName
     * @param id
     * @return boolean
     * @author yangk
     * @date 2020/3/16
     */
    public boolean existByUserName(SysUser sysUser) {
        int count = sysUserDao.countByUserName(sysUser);
        if(count == 0){
            return false;
        }
        return true;
    }

    /**
     * 通过用户名查询
     *
     * @param username
     * @return com.yangk.demoproject.model.sys.SysUser
     * @author yangk
     * @date 2020/3/16
     */
    public SysUser findByUserName(String username){
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", username);
        List<SysUser> sysUsers = sysUserDao.selectByExample(example);
        if (null == sysUsers || sysUsers.size() == 0) {
            return null;
        }
        return sysUsers.get(0);
    }
}
