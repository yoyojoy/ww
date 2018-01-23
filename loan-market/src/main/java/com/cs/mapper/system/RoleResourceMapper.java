package com.cs.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cs.model.system.RoleResource;
import org.apache.ibatis.annotations.Param;

/**
 * RoleResource 表数据库控制层接口
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    Long selectIdListByRoleId(@Param("id") Long id);

}