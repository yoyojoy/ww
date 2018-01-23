package com.cs.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.cs.common.result.Tree;
import com.cs.common.shiro.ShiroUser;
import com.cs.model.system.Resource;

import java.util.List;

/**
 *
 * Resource 表数据服务层接口
 *
 */
public interface IResourceService extends IService<Resource> {

    List<Resource> selectAll();

    List<Tree> selectAllMenu();

    List<Tree> selectAllTree();

    List<Tree> selectTree(ShiroUser shiroUser);

}