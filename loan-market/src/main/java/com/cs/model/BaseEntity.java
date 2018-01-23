package com.cs.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by joy on 2017/11/23.
 */
public class BaseEntity implements Serializable {

    private String id;
    private Date createTime;
    private Date editTime;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
