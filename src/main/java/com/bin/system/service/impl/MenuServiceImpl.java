package com.bin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import com.bin.system.domain.Menu;
import com.bin.system.mapper.MenuMapper;
import com.bin.system.mapper.RoleMapper;
import com.bin.system.service.MenuService;
import com.bin.system.vo.MenuVo;
import jdk.internal.org.objectweb.asm.tree.IincInsnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Menu> queryAllMenuForList() {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        qw.and(new Consumer<QueryWrapper<Menu>>() {
            @Override
            public void accept(QueryWrapper<Menu> menuQueryWrapper) {
                menuQueryWrapper.eq("type", Constant.MENU_TYPE_TOP)
                        .or().eq("type", Constant.MENU_TYPE_LEFT);
            }
        });
        qw.orderByAsc("ordernum");
        return this.menuMapper.selectList(qw);

    }

    @Override
    public List<Menu> queryMenuForListByUserId(Integer id) {
        List<Menu> menuList = new ArrayList<>();

        //根据userId查询角色ID集合
        List<Integer> roleIds = this.roleMapper.queryRoleIdsByUid(id);

        //根据角色ID集合查询菜单ID集合
        if (roleIds != null && roleIds.size() > 0) {
            List<Integer> menuIds = this.menuMapper.querMenuIdsByRoleIds(roleIds);
            if (menuIds != null && menuIds.size() > 0) {
                QueryWrapper<Menu> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.and(new Consumer<QueryWrapper<Menu>>() {
                    @Override
                    public void accept(QueryWrapper<Menu> menuQueryWrapper) {
                        menuQueryWrapper.eq("type", Constant.MENU_TYPE_TOP)
                                .or().eq("type", Constant.MENU_TYPE_LEFT);
                    }
                });
                qw.in("id", menuIds);
                qw.orderByAsc("ordernum");
                menuList = this.menuMapper.selectList(qw);
                return menuList;
            } else {
                return menuList;
            }
        } else {
            return menuList;
        }

    }

    @Override
    public DataGridView queryAllMenu(MenuVo menuVo) {
        QueryWrapper<Menu> qw = new QueryWrapper();
        qw.eq(menuVo.getAvailable() != null, "available", menuVo.getAvailable());
        qw.orderByAsc("ordernum");
        List<Menu> menus = this.menuMapper.selectList(qw);
        return new DataGridView(Long.valueOf(menus.size()), menus);
    }

    @Override
    public Integer queryMenuMaxOrderNum() {
        return this.menuMapper.queryMenuMaxOrderNum();
    }

    @Override
    public Menu saveMenu(Menu menu) {
        this.menuMapper.insert(menu);
        return menu;
    }

    @Override
    public Integer getMenuChildrenCountById(Integer id) {
        return this.menuMapper.getMenuChildrenCountById(id);
    }

    @Override
    public Menu updateMenu(Menu menu) {
        this.menuMapper.updateById(menu);
        return menu;
    }

    @Override
    public List<String> queryPermissionCodeByUserId(Integer id) {
        List<String> permissions = new ArrayList<>();
        //根据userId查询角色ID集合
        List<Integer> roleIds = this.roleMapper.queryRoleIdsByUid(id);

        //根据角色ID集合查询菜单ID集合
        if (roleIds != null && roleIds.size() > 0) {
            List<Integer> menuIds = this.menuMapper.querMenuIdsByRoleIds(roleIds);
            if (menuIds != null && menuIds.size() > 0) {
                QueryWrapper<Menu> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.eq("type", Constant.MENU_TYPE_PERMISSION);
                qw.in("id", menuIds);
                qw.orderByAsc("ordernum");
                List<Menu> menus = this.menuMapper.selectList(qw);
                for (Menu menu : menus) {
                    permissions.add(menu.getTypecode());
                }
                return permissions;
            }
            return permissions;
        }
        return permissions;
    }

    @Override
    public Menu getById(Serializable id) {
        return super.getById(id);
    }
}



