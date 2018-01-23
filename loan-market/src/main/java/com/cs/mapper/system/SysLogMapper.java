package com.cs.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cs.model.system.SysLog;

import java.util.List;

/**
 * SysLog 表数据库控制层接口
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    List<SysLog> selectSysLogList(Pagination page);

}