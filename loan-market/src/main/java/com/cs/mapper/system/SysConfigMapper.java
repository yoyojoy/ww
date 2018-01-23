package com.cs.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cs.model.system.SysConfig;

import java.util.List;
import java.util.Map;

public interface SysConfigMapper extends BaseMapper<SysConfig> {

    public Integer add(SysConfig info);

    public SysConfig getById(String id);

    public Integer updateById(SysConfig info);

    public Integer deleteById(String id);

    public List<SysConfig> querySystemConfigList(Pagination page, Map<String, Object> params);

    public List<SysConfig> loadAll(SysConfig config);
}
