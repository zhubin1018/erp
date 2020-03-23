package com.bin.system.controller;

import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import com.bin.system.common.ResultObj;
import com.bin.system.domain.Menu;
import com.bin.system.service.MenuService;
import com.bin.system.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/19 17:04
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 查询所有菜单和权限
     *
     * @return java.lang.Object
     * @params [menuVo]
     */
    @RequestMapping("/loadAllMenu")
    public Object loadAllMenu(MenuVo menuVo) {
        return this.menuService.queryAllMenu(menuVo);
    }

    @RequestMapping("/loadMenu")
    public Object loadMenu(MenuVo menuVo){
        List<Menu> menus = this.menuService.queryAllMenuForList();
        return new DataGridView(Long.valueOf(menus.size()),menus);

    }

    /**
     * 查找菜单和权限最大排序码
     *
     * @return com.bin.system.common.DataGridView
     * @params []
     */
    @GetMapping("/queryMenuMaxOrderNum")
    public Object queryMenuMaxOrderNum() {
        Integer maxValue = this.menuService.queryMenuMaxOrderNum();
        return new DataGridView(maxValue + 1);
    }


    /**
     * 新增菜单和权限
     *
     * @return com.bin.system.common.ResultObj
     * @params [menu]
     */
    @PostMapping("/addMenu")
    public ResultObj addMenu(Menu menu) {
        try {
            if (menu.getType().equals("topmenu")){
                menu.setPid(0);
            }
            menu.setSpread(Constant.SPREAD_TRUE);
            menu.setAvailable(Constant.AVAILABLE_TRUE);
            this.menuService.saveMenu(menu);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     *
     * @return com.bin.system.common.ResultObj
     * @params [menu]
     */
    @PostMapping("/updateMenu")
    public ResultObj updateMenu(Menu menu) {
        try {
            this.menuService.updateMenu(menu);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 根据ID获取菜单和权限名称
     *
     * @return java.lang.Object
     * @params [id]
     */
    @GetMapping("/getMenuById")
    public Object getMenuById(Integer id) {
        return new DataGridView(this.menuService.getById(id));

    }


    /**
     * 根据菜单和权限ID查询子菜单和权限数目
     *
     * @return java.lang.Object
     * @params [id]
     */
    @GetMapping("/getMenuChildrenCountById")
    public Object getMenuChildrenById(Integer id) {
        Integer count = this.menuService.getMenuChildrenCountById(id);
        return new DataGridView(count);
    }


    /**
     * 根据菜单和权限删除
     *
     * @return com.bin.system.common.ResultObj
     * @params [id]
     */
    @RequestMapping("/deleteMenu")
    public ResultObj deleteMenu(Integer id) {
        try {
            this.menuService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
