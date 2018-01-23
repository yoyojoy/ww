package com.cs.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.cs.model.system.SysLog;
import com.cs.utils.PageInfo;

/**
 *
 * SysLog 表数据服务层接口
 *
 */
public interface ISysLogService extends IService<SysLog> {

    void selectDataGrid(PageInfo pageInfo);

}