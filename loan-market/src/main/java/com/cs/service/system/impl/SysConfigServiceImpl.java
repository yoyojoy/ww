package com.cs.service.system.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cs.mapper.system.SysConfigMapper;
import com.cs.model.system.SysConfig;
import com.cs.redis.RedisUtils;
import com.cs.service.system.ISysConfigService;
import com.cs.utils.BeanUtils;
import com.cs.utils.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2017/7/3.
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ISysConfigService.class);

    @Autowired
    private SysConfigMapper systemConfigMapper;

    @Override
    public boolean add(SysConfig config) {
        return systemConfigMapper.add(config) > 0;
    }

    @Override
    public SysConfig getById(String id) {
        return systemConfigMapper.getById(id);
    }

    @Override
    public boolean updateById(SysConfig entity) {
        return systemConfigMapper.updateById(entity) > 0;
    }

    @Override
    public boolean deleteById(String id) {
        return systemConfigMapper.deleteById(id) > 0;
    }

    @Override
    public void querySysConfigList(PageInfo pageInfo) {
        try {
            List<SysConfig> resultList = new ArrayList<>();
            Page<SysConfig> page = new Page<>(pageInfo.getNowpage(), pageInfo.getSize());
            List<SysConfig> res = systemConfigMapper.querySystemConfigList(page, pageInfo.getCondition());
            if (res != null && !res.isEmpty()) {
                SysConfig sysConfigInfo = null;
                for (SysConfig loanInfo : res) {
                    sysConfigInfo = new SysConfig();
                    BeanUtils.copy(loanInfo, sysConfigInfo);
                    resultList.add(sysConfigInfo);
                }
            }
            pageInfo.setRows(resultList);
            pageInfo.setTotal(page.getTotal());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    //TODO
    @Override
    public void refreshAll() {
        if(RedisUtils.exists("")){
            RedisUtils.del("");
        }
        SysConfig in = new SysConfig();
        in.setState(true);
        List<SysConfig> configs = systemConfigMapper.loadAll(in);
        if(configs != null && configs.size() > 0){
            for(SysConfig en : configs){
                RedisUtils.addHash("", en.getSysKey(), en.getSysValue());
            }
        }
    }
}
