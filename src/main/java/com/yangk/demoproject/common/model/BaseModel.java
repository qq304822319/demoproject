package com.yangk.demoproject.common.model;

import com.yangk.demoproject.common.model.base.UUIdGenId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author yangk
 * @date 2020/3/10
 */
@MappedSuperclass
public class BaseModel implements Serializable {
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

