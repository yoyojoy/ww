package com.cs.service.system.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cs.mapper.system.UserRoleMapper;
import com.cs.model.system.UserRole;
import com.cs.service.system.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * UserRole 表数据服务层接口实现类
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}