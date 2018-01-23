package com.cs.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cs.common.result.Result;
import com.cs.common.shiro.ShiroUser;
import com.cs.utils.PageInfo;
import com.cs.utils.StringEscapeEditor;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description：基础 controller
 */
public abstract class BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected static String INIT_ID = UUID.randomUUID().toString().replace("-","");

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
    }

    /**
     * 获取当前登录用户对象
     * @return {ShiroUser}
     */
    public ShiroUser getShiroUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户id
     * @return {Long}
     */
    public Long getUserId() {
        return this.getShiroUser().getId();
    }

    /**
     * 获取当前登录用户名
     * @return {String}
     */
    public String getStaffName() {
        return this.getShiroUser().getName();
    }

    /**
     * ajax失败
     * @param msg 失败的消息
     * @return {Object}
     */
    public Object renderError(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @return {Object}
     */
    public Object renderSuccess() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    /**
     * ajax成功
     * @param msg 消息
     * @return {Object}
     */
    public Object renderSuccess(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * @param obj 成功时的对象
     * @return {Object}
     */
    public Object renderSuccess(Object obj) {
        Result result = new Result();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }
    
    public <T> Page<T> getPage(int current, int size, String sort, String order){
        Page<T> page = new Page<T>(current, size, sort);
        if ("desc".equals(order)) {
            page.setAsc(false);
        } else {
            page.setAsc(true);
        }
        return page;
    }
    
    public <T> PageInfo pageToPageInfo(Page<T> page) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(page.getRecords());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setOrder(page.getOrderByField());
        return pageInfo;
    }

}
