package com.yangk.demoproject.service.sys;

import com.yangk.demoproject.dao.sys.SysDataDictionaryDao;
import com.yangk.demoproject.model.sys.SysDataDictionary;
import com.yangk.demoproject.model.sys.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 数据字典Service
 *
 * @author yangk
 * @date dateString
 */
@Slf4j
@Service
public class SysDataDictionaryService {

    @Autowired
    private SysDataDictionaryDao sysDataDictionaryDao;

    /**
     * 查询数据字典
     *
     * @param sysDataDictionary
     * @return java.util.List<SysDataDictionary>
     * @author yangk
     * @date dateString
     */
    public List<SysDataDictionary> selectSysDataDictionarys(SysDataDictionary sysDataDictionary) {
        return sysDataDictionaryDao.select(sysDataDictionary);
    }

    /**
     * 通过主键查询数据字典
     *
     * @param key
     * @return SysDataDictionary
     * @author yangk
     * @date dateString
     */
    public SysDataDictionary selectSysDataDictionaryByPrimaryKey(Object key) {
        return sysDataDictionaryDao.selectByPrimaryKey(key);
    }

    /**
     * 保存数据字典
     *
     * @param sysDataDictionary
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveSysDataDictionary(SysDataDictionary sysDataDictionary) {
        if (StringUtils.isEmpty(sysDataDictionary.getId())) {
            return this.insertSysDataDictionary(sysDataDictionary);
        } else {
            return this.updateSysDataDictionary(sysDataDictionary);
        }
    }

    /**
     * 新增数据字典
     *
     * @param sysDataDictionary
     * @param sysUser
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertSysDataDictionary(SysDataDictionary sysDataDictionary) {
        sysDataDictionaryDao.insertSelective(sysDataDictionary);
        return sysDataDictionary.getId();
    }

    /**
     * 修改数据字典
     *
     * @param sysDataDictionary
     * @param sysUser
     * @return java.lang.String
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateSysDataDictionary(SysDataDictionary sysDataDictionary) {
        sysDataDictionaryDao.updateByPrimaryKeySelective(sysDataDictionary);
        return sysDataDictionary.getId();
    }

    /**
     * 删除数据字典
     *
     * @param keys
     * @return int
     * @author yangk
     * @date dateString
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysDataDictionary(Object[] keys) {
        int count = 0;
        for (Object key : keys) {
            sysDataDictionaryDao.deleteByPrimaryKey(key);
            count++;
        }
        return count;
    }

}