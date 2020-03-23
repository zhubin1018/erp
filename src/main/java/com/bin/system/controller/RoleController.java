package com.bin.system.controller;

import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import com.bin.system.common.ResultObj;
import com.bin.system.domain.Role;
import com.bin.system.service.RoleService;
import com.bin.system.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/19 17:04
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     *
     * @return java.lang.Object
     * @params [roleVo]
     */
    @RequestMapping("/loadAllRole")
    public Object loadAllRole(RoleVo roleVo) {
        return this.roleService.queryAllRole(roleVo);
    }

    /**
     * 查询所有可用角色不分页
     *
     * @return java.lang.Object
     * @params [roleVo]
     */
    @RequestMapping("/loadAllAvailableRoleNoPage")
    public Object loadAllAvailableRoleNoPage(RoleVo roleVo) {
        roleVo.setAvailable(Constant.AVAILABLE_TRUE);
        return this.roleService.loadAllAvailableRoleNoPage(roleVo);
    }


    /**
     * 新增角色
     *
     * @return com.bin.system.common.ResultObj
     * @params [role]
     */
    @PostMapping("/addRole")
    public ResultObj addRole(Role role) {
        try {
            role.setCreatetime(new Date());
            role.setAvailable(Constant.AVAILABLE_TRUE);
            this.roleService.saveRole(role);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     *
     * @return com.bin.system.common.ResultObj
     * @params [role]
     */
    @PostMapping("/updateRole")
    public ResultObj updateRole(Role role) {
        try {
            this.roleService.update(role);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 根据角色删除
     *
     * @return com.bin.system.common.ResultObj
     * @params [id]
     */
    @RequestMapping("/deleteRole")
    public ResultObj deleteRole(Integer id) {
        try {
            this.roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     *根据角色id查询角色拥有的菜单和权限
     * @params [id]
     * @return java.lang.Object
     */
    @RequestMapping("/queryMenuIdsByRid")
    public Object queryMenuIdsByRid(Integer id) {
        List<Integer> mids = this.roleService.queryMenuIdsByRid(id);
        return new DataGridView(mids);

    }


    /**
     *保存角色和菜单之间的关系
     * @params [rid, mids]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/saveRoleMenu")
    public ResultObj saveRoleMenu(Integer rid,Integer[] mids){
        try {
            this.roleService.saveRoleMenu(rid,mids);
            return ResultObj.DISPATCH_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }
}
