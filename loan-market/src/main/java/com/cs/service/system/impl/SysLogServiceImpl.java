package com.cs.service.system.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cs.mapper.system.SysLogMapper;
import com.cs.model.system.SysLog;
import com.cs.service.system.ISysLogService;
import com.cs.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysLog 表数据服务层接口实现类
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;
    
    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<SysLog> page = new Page<SysLog>(pageInfo.getNowpage(), pageInfo.getSize());
        List<SysLog> list = sysLogMapper.selectSysLogList(page);
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

}