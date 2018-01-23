package com.cs.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.cs.model.system.SysConfig;
import com.cs.utils.PageInfo;

/**
 * 后台管理端进行app中的服务缓存配置参数管理
 */
public interface ISysConfigService extends IService<SysConfig> {

    public final String CONFIG_KEY = "KEY";

    public boolean add(SysConfig config);

    public SysConfig getById(String id);

    public boolean updateById(SysConfig config);

    public boolean deleteById(String id);

    public void querySysConfigList(PageInfo pageInfo);

    public  void refreshAll();
}
