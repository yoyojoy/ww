package com.cs.model.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cs.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源
 */
@TableName("sys_resource")
public class Resource implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 资源名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 资源路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * 打开方式 ajax,iframe
     */
    @TableField(value = "open_mode")
    private String openMode;

    /**
     * 资源介绍
     */
    @TableField(value = "description")
    private String description;

    /**
     * 资源图标
     */
    @JsonProperty("iconCls")
    @TableField(value = "icon")
    private String icon;

    /**
     * 父级资源id
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 排序
     */
    @TableField(value = "seq")
    private Integer seq;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 资源类别
     */
    @TableField(value = "resource_type")
    private Integer resourceType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpenMode() {
        return openMode;
    }

    public void setOpenMode(String openMode) {
        this.openMode = openMode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return JsonUtil.getJsonForObject(this);
    }
}
