package com.cs.controller.system;

import com.cs.controller.BaseController;
import com.cs.model.system.SysConfig;
import com.cs.redis.RedisUtils;
import com.cs.service.system.ISysConfigService;
import com.cs.utils.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by joy on 2017/7/20.
 */
@Controller
@RequestMapping("/config")
public class SysConfigController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigController.class);

    @Autowired
    private ISysConfigService sysConfigService;


    /**
     * 查询app服务的配置表中所有配置值
     */
    @GetMapping("/init")
    public String manager() {
        return "sys/systemConfigList";
    }


    /**
     * dataGrid 缓存配置列表查询
     * @param config
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @PostMapping("/dataGrid")
    @ResponseBody
    public Object dataGrid(SysConfig config, Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows);
        Map<String, Object> condition = new HashMap<>();
        if (StringUtils.isNotEmpty(config.getSysKey())) {
            condition.put("sysKey", config.getSysKey());
        }
        if (StringUtils.isNotEmpty(config.getSysValue())) {
            condition.put("sysValue", config.getSysValue());
        }
        if (sort != null && StringUtils.isNotBlank(sort)) {
            condition.put("sortBy", sort);
            if (order != null && StringUtils.isNotBlank(order)) {
                condition.put("orderBy", order);
            } else {
                condition.put("orderBy", "asc");
            }
        }
        try {
            pageInfo.setCondition(condition);
            sysConfigService.querySysConfigList(pageInfo);
        } catch (Exception e) {
            LOGGER.warn("===== SysConfigController => dataGrid: failed to query system config data =====");
            LOGGER.error(e.getMessage());
        }
        return pageInfo;
    }

    /**
     * 新增配置页
     *
     * @return
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "sys/configAdd";
    }

    /**
     * 添加资源
     *
     * @param config
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(SysConfig config) {
        config.setCreateTime(new Date());
        config.setId(INIT_ID);
        boolean f = sysConfigService.add(config);
        if(f){
            if(config.getState()){
                if(RedisUtils.getHash(sysConfigService.CONFIG_KEY, config.getSysKey()) != null){
                    RedisUtils.delHash(sysConfigService.CONFIG_KEY, config.getSysKey());
                }
                RedisUtils.addHash(sysConfigService.CONFIG_KEY, config.getSysKey(), config.getSysValue());
            }
        }
        return renderSuccess("添加成功！");
    }

    /**
     * 编辑配置项页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, String id) {
        SysConfig config = sysConfigService.getById(id);
        model.addAttribute("config", config);
        return "sys/configEdit";
    }

    /**
     * 编辑配置项
     *
     * @param config
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(SysConfig config) {
        boolean f = sysConfigService.updateById(config);
        if(f){
            if(RedisUtils.getHash(sysConfigService.CONFIG_KEY, config.getSysKey()) != null){
                RedisUtils.delHash(sysConfigService.CONFIG_KEY, config.getSysKey());
            }
            if(config.getState()){
                RedisUtils.addHash(sysConfigService.CONFIG_KEY, config.getSysKey(), config.getSysValue());
            }
        }
        return renderSuccess("编辑成功！");
    }

    /**
     * 删除配置
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
        SysConfig config = sysConfigService.getById(id);
        if(config != null){
            sysConfigService.deleteById(id);
            if(RedisUtils.getHash(sysConfigService.CONFIG_KEY, config.getSysKey()) != null){
                RedisUtils.delHash(sysConfigService.CONFIG_KEY, config.getSysKey());
            }
        }
        return renderSuccess("删除成功！");
    }

    /**
     * 删除配置
     *
     * @param id
     * @return
     */
    @RequestMapping("/enable")
    @ResponseBody
    public Object enable(String id) {
        SysConfig config = sysConfigService.selectById(id);
        config.setState(true);
        boolean f = sysConfigService.updateById(config);
        if(f){
            if(RedisUtils.getHash(sysConfigService.CONFIG_KEY, config.getSysKey()) != null){
                RedisUtils.delHash(sysConfigService.CONFIG_KEY, config.getSysKey());
            }
            RedisUtils.addHash(sysConfigService.CONFIG_KEY, config.getSysKey(), config.getSysValue());
        }
        return renderSuccess("启用成功！");
    }

    /**
     * 禁用配置
     *
     * @param id
     * @return
     */
    @RequestMapping("/disable")
    @ResponseBody
    public Object disable(String id) {
        SysConfig config = sysConfigService.selectById(id);
        config.setState(false);
        boolean f = sysConfigService.updateById(config);
        if(f){
            if(RedisUtils.getHash(sysConfigService.CONFIG_KEY, config.getSysKey()) != null){
                RedisUtils.delHash(sysConfigService.CONFIG_KEY, config.getSysKey());
            }
        }
        return renderSuccess("禁用成功！");
    }

    /**
     * 刷新所有可用配置到redis
     */
    @RequestMapping("/redisRefresh")
    @ResponseBody
    public Object refreshRedis() {
        logger.info("=============开始刷新Redis所有缓存数据===============");
        sysConfigService.refreshAll();//系统中各个系统配置项(表yzd_sys_config_info)
        logger.info("=============刷新Redis所有缓存数据完成===============");
        return renderSuccess("刷新成功！");
    }
}
