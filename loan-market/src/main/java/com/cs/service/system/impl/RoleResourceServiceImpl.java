package com.cs.service.system.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cs.mapper.system.RoleResourceMapper;
import com.cs.model.system.RoleResource;
import com.cs.service.system.IRoleResourceService;
import org.springframework.stereotype.Service;

/**
 * RoleResource 表数据服务层接口实现类
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {


}