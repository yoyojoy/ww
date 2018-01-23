package com.cs.controller.system;

import com.cs.controller.BaseController;
import com.cs.model.system.Role;
import com.cs.service.system.IRoleService;
import com.cs.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description：权限管理
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    /**
     * 权限管理页
     *
     * @return
     */
    @GetMapping("/init")
    public String manager() {
        return "sys/role";
    }

    /**
     * 权限列表
     *
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @PostMapping("/dataGrid")
    @ResponseBody
    public Object dataGrid(Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String, Object> condition = new HashMap<String, Object>();
        pageInfo.setCondition(condition);

        roleService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    /**
     * 权限树
     *
     * @return
     */
    @PostMapping("/tree")
    @ResponseBody
    public Object tree() {
        return roleService.selectTree();
    }

    /**
     * 添加权限页
     *
     * @return
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "sys/roleAdd";
    }

    /**
     * 添加权限
     *
     * @param role
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Object add(Role role) {
        roleService.insert(role);
        return renderSuccess("添加成功！");
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id) {
        roleService.deleteById(id);
        return renderSuccess("删除成功！");
    }

    /**
     * 编辑权限页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, Long id) {
        Role role = roleService.selectById(id);
        model.addAttribute("role", role);
        return "sys/roleEdit";
    }

    /**
     * 修改权限
     *
     * @param role
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(Role role) {
        roleService.updateById(role);
        return renderSuccess("编辑成功！");
    }

    /**
     * 授权页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/grantPage")
    public String grantPage(Model model, Long id) {
        model.addAttribute("id", id);
        return "sys/roleGrant";
    }

    /**
     * 授权页面页面根据角色查询资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/findResourceIdListByRoleId")
    @ResponseBody
    public Object findResourceByRoleId(Long id) {
        List<Long> resources = roleService.selectResourceIdListByRoleId(id);
        return renderSuccess(resources);
    }

    /**
     * 授权
     *
     * @param id
     * @param resourceIds
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Object grant(Long id, String resourceIds) {
        roleService.updateRoleResource(id, resourceIds);
        return renderSuccess("授权成功！");
    }

}
