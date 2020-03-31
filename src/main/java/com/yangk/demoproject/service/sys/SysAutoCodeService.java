package com.yangk.demoproject.service.sys;


import com.yangk.demoproject.dao.sys.SysAutoCodeDao;
import com.yangk.demoproject.model.sys.SysAutoCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自动编码Service
 *
 * @author yangk
 * @date 2020/03/26
 */
@Slf4j
@Service
public class SysAutoCodeService {

    @Resource
    private SysAutoCodeDao sysAutoCodeDao;

    /**
     * 查询自动编码
     *
     * @param sysAutoCode
     * @return java.util.List<SysAutoCode>
     * @author yangk
     * @date 2020/03/26
     */
    public List<SysAutoCode> selectSysAutoCodes(SysAutoCode sysAutoCode){
        return sysAutoCodeDao.select(sysAutoCode);
    }

    /**
     * 通过主键查询自动编码
     *
     * @param key
     * @return SysAutoCode
     * @author yangk
     * @date 2020/03/26
     */
    public SysAutoCode selectSysAutoCodeByPrimaryKey(Object key){
        return sysAutoCodeDao.selectByPrimaryKey(key);
    }

    /**
     * 保存自动编码
     *
     * @param sysAutoCode
     * @return java.lang.String
     * @author yangk
     * @date 2020/03/26
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveSysAutoCode(SysAutoCode sysAutoCode) {
        if (StringUtils.isEmpty(sysAutoCode.getId())) {
            SimpleDateFormat sdf = new SimpleDateFormat(sysAutoCode.getDateFormat());
            String dateFormat = sdf.format(new Date());
            sysAutoCode.setCurrentDateStr(dateFormat);
            return this.insertSysAutoCode(sysAutoCode);
        } else {
            return this.updateSysAutoCode(sysAutoCode);
        }
    }
    
    /**
     * 新增自动编码
     *
     * @param sysAutoCode
     * @return java.lang.String
     * @author yangk
     * @date 2020/03/26
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertSysAutoCode(SysAutoCode sysAutoCode){
        sysAutoCodeDao.insertSelective(sysAutoCode);
        return sysAutoCode.getId();
    }

    /**
     * 修改自动编码
     *
     * @param sysAutoCode
     * @return java.lang.String
     * @author yangk
     * @date 2020/03/26
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateSysAutoCode(SysAutoCode sysAutoCode){
        sysAutoCodeDao.updateByPrimaryKeySelective(sysAutoCode);
        return sysAutoCode.getId();
    }

    /**
     * 删除自动编码
     *
     * @param keys
     * @return int
     * @author yangk
     * @date 2020/03/26
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysAutoCode(Object[] keys){
        int count = 0;
        for (Object key : keys) {
            sysAutoCodeDao.deleteByPrimaryKey(key);
            count++;
        }
        return count;
    }

    public String getAutoCode(String autoCodeKey){
        //获取自动编码信息
        SysAutoCode sysAutoCode = sysAutoCodeDao.getSysAutoCodeByKey(autoCodeKey);

        /*
         * 根据当前时间格式化字符串与目标自动编码对比
         * true:  序号+1,生成自动编码
         * false: 更新目标自动编码当前日期,更新目标当前序号
         */
        SimpleDateFormat sdf = new SimpleDateFormat(sysAutoCode.getDateFormat());

        String autoCode = sysAutoCode.getPrefix();         //自动编码
        int currentSerialNumber = 0;           //当前序号
        String dateFormat = sdf.format(new Date());

        if(dateFormat.equals(sysAutoCode.getCurrentDateStr())){
            //当前序号
            currentSerialNumber = sysAutoCode.getCurrentSerialNumber() + 1;
            //格式化序号
            String serialFormat = String.format("%0" + sysAutoCode.getSerialNumberDigit() + "d", currentSerialNumber);
            //自动编码
            autoCode = autoCode + dateFormat + serialFormat;
            //修改需要
            sysAutoCode.setCurrentSerialNumber(currentSerialNumber);
            this.saveSysAutoCode(sysAutoCode);
        } else {
            //格式化序号
            String serialFormat = String.format("%0" + sysAutoCode.getSerialNumberDigit() + "d", currentSerialNumber);
            //自动编码
            autoCode = autoCode + dateFormat + serialFormat;
            sysAutoCode.setCurrentDateStr(dateFormat);
            sysAutoCode.setCurrentSerialNumber(currentSerialNumber);
            this.saveSysAutoCode(sysAutoCode);
        }

        return autoCode;
    }
}