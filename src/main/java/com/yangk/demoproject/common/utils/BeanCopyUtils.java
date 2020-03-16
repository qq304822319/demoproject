package com.yangk.demoproject.common.utils;

import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.exception.ProException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * 类属性值复制工具类
 *
 * @author yangk
 */
public class BeanCopyUtils {

    /**
     * 类属性值复制
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 类属性值复制
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNoNullProperties(target));
    }

    /**
     * 类属性值复制
     *
     * @param source 源对象
     * @param clazz  目标对象类型
     * @param <T>    目标对象
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, Class<T> clazz, String... ignoreProperties) {
        T target;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ProException(ResponseCode.ERROR.getCode(), "生成DTO对象失败！");
        }
    }

    /**
     * @param target 目标源数据
     * @return 将目标源中不为空的字段取出
     */
    private static String[] getNoNullProperties(Object target) {
        BeanWrapper srcBean = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> noEmptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            if (value != null) {
                noEmptyName.add(p.getName());
            }
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }

}
