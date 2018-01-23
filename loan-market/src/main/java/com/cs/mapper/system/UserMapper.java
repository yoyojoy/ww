package com.cs.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.cs.model.system.User;
import com.cs.model.system.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * User 表数据库控制层接口
 */
public interface UserMapper extends BaseMapper<User> {

    UserVo selectUserVoById(@Param("id") Long id);

    List<UserVo> selectUserVoPage(Pagination page, Map<String, Object> params);
}