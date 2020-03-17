package com.yangk.demoproject.service.sys;


import com.yangk.demoproject.dao.sys.SysLookupCodeDao;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysLookupCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.tree.VoidDescriptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用代码Service
 *
 * @author yangk
 * @date dateString
 */
@Slf4j
@Service
public class SysLookupCodeService {

    @Autowired
    private SysLookupCodeDao sysLookupCodeDao;

    /**
     * 查询通用代码
     *
     * @param sysLookupCode
     * @return java.util.List<SysLookupCode>
     * @author yangk
     * @date dateString
     */
    public List<SysLookupCode> selectSysLookupCodes(SysLookupCode sysLookupCode){
        return sysLookupCodeDao.select(sysLookupCode);
    }

    /**
     * 通过主键查询通用代码
     *
     * @param key
     * @return SysLookupCode
     * @author yangk
     * @date dateString
     */
    public SysLookupCode selectSysLookupCodeByPrimaryKey(Object key){
        return sysLookupCodeDao.selectByPrimaryKey(key);
    }

    /**
     * 批量保存通用代码
     *
     * @param sysLookupCodes
	 * @param loginUserDto
     * @return void
     * @author yangk
     * @date 2020/3/18
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveSysLookupCodes(List<SysLookupCode> sysLookupCodes,
                                   LoginUserDto loginUserDto){
        for (SysLookupCode sysLookupCode : sysLookupCodes) {
            this.saveSysLookupCode(sysLookupCode, loginUserDto);
        }
    }

    /**
     * 保存通用代码
     *
     * @param sysLookupCode
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveSysLookupCode(SysLookupCode sysLookupCode, 
                          LoginUserDto loginUserDto) {
        if (StringUtils.isEmpty(sysLookupCode.getId())) {
            sysLookupCode.setCreateBy(loginUserDto.getUsername());
            sysLookupCode.setCreateTime(new Date());
            return this.insertSysLookupCode(sysLookupCode);
        } else {
            sysLookupCode.setUpdateBy(loginUserDto.getId());
            sysLookupCode.setUpdateTime(new Date());
            return this.updateSysLookupCode(sysLookupCode);
        }
    }
    
    /**
     * 新增通用代码
     *
     * @param sysLookupCode
     * @param loginUserDto
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertSysLookupCode(SysLookupCode sysLookupCode){
        sysLookupCodeDao.insertSelective(sysLookupCode);
        String id = sysLookupCode.getId();
        return id;
    }

    /**
     * 修改通用代码
     *
     * @param sysLookupCode
     * @param loginUserDto
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateSysLookupCode(SysLookupCode sysLookupCode){
        sysLookupCodeDao.updateByPrimaryKeySelective(sysLookupCode);
        String id = sysLookupCode.getId();
        return id;
    }

    /**
     * 删除通用代码
     *
     * @param keys
     * @return int
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysLookupCode(Object[] keys){
        int count = 0;
        for (Object key : keys) {
            sysLookupCodeDao.deleteByPrimaryKey(key);
            count++;
        }
        return count;
    }

}