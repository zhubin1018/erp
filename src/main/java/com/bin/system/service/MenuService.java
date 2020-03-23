package com.bin.system.service;

import com.bin.system.common.DataGridView;
import com.bin.system.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.system.vo.MenuVo;

import java.util.List;

public interface MenuService extends IService<Menu> {

    /**
     *超级管理员全查询菜单
     * @param 
     * @return 
     */ 
    List<Menu> queryAllMenuForList();

    /**
     *根据用户ID查询菜单
     * @param 
     * @return 
     */
    List<Menu> queryMenuForListByUserId(Integer id);

    DataGridView queryAllMenu(MenuVo menuVo);

    Integer queryMenuMaxOrderNum();

    Menu saveMenu(Menu menu);

    Integer getMenuChildrenCountById(Integer id);

    Menu updateMenu(Menu menu);

    List<String> queryPermissionCodeByUserId(Integer id);
}



