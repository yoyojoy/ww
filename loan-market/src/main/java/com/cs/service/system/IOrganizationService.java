package com.cs.service.system;

import com.baomidou.mybatisplus.service.IService;
import com.cs.common.result.Tree;
import com.cs.model.system.Organization;

import java.util.List;

/**
 * Organization 表数据服务层接口
 */
public interface IOrganizationService extends IService<Organization> {

    List<Tree> selectTree();

    List<Organization> selectTreeGrid();

}