package com.yangk.demoproject.common.utils;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.Map;

/**
 * github.PageHelper分页
 *
 * @author yangk
 * @date 2020/3/24
 */
public class PageUtils {

    public static Page getPage(Map<String, Object> conditions) {

        Integer page = 1;
        Integer pageSize = 10;
        String sortBy = "";
        String orderBy = "asc";

        if (!StrUtil.isBlankIfStr(conditions.get("page"))) {
            page = Integer.parseInt(conditions.get("page").toString());
        }

        if (!StrUtil.isBlankIfStr(conditions.get("pageSize"))) {
            pageSize = Integer.parseInt(conditions.get("pageSize").toString());
        }

        if (!StrUtil.isBlankIfStr(conditions.get("sortBy"))) {
            sortBy = conditions.get("sortBy").toString();
        }

        if (!StrUtil.isBlankIfStr(conditions.get("orderBy"))) {
            orderBy = conditions.get("orderBy").toString();
        }


        if (StrUtil.isEmpty(sortBy)) {
            return PageHelper.startPage(page, pageSize);
        } else {
            String orderBySql = sortBy + " "+ orderBy;
            return PageHelper.startPage(page, pageSize, orderBySql);
        }
    }

}
