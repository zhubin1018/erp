package com.bin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import com.bin.system.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.system.mapper.RoleMapper;
import com.bin.system.domain.Role;
import com.bin.system.service.RoleService;

/**
 * @author 朱彬
 * @date 2020/3/20 14:22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private RoleMapper roleMapper;


    @Override
    public DataGridView queryAllRole(RoleVo roleVo) {
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq(roleVo.getAvailable() != null, "available", roleVo.getAvailable());
        qw.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
        qw.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        this.roleMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Role saveRole(Role role) {
        this.roleMapper.insert(role);
        return role;
    }

    @Override
    public boolean removeById(Serializable id) {

        //* 根据介绍ID删除角色和菜单之间的关系
        roleMapper.deleteRoleMenuByRid(id);
        // roleMapper.deleteRoleMenuByMid(id);

        //* 根据介绍ID删除角色与用户之间的关系
        roleMapper.deleteRoleUserByRid(id);
        // roleMapper.deleteRoleUserByUid(id);

        return super.removeById(id);
    }

    @Override
    public Role update(Role role) {
        this.roleMapper.updateById(role);
        return role;
    }

    @Override
    public List<Integer> queryMenuIdsByRid(Integer id) {
        return this.roleMapper.queryMenuIdsByRid(id);
    }

    @Override
    public void saveRoleMenu(Integer rid, Integer[] mids) {
        //根据rid删除sys_role_menu里面的数据
        this.roleMapper.deleteRoleMenuByRid(rid);
        if (mids != null && mids.length > 0) {
            for (Integer mid : mids) {
                this.roleMapper.insertRoleMenu(rid, mid);
            }
        }
    }

    @Override
    public DataGridView loadAllAvailableRoleNoPage(RoleVo roleVo) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq(roleVo.getAvailable() != null, "available", roleVo.getAvailable());
        List<Role> roles = this.roleMapper.selectList(qw);

        //根据用户ID查询已拥有的角ID
        List<Integer> roleIds = this.roleMapper.queryRoleIdsByUid(roleVo.getUserId());

        List<Map<String, Object>> lists = new ArrayList<>();

        for (Role role : roles) {
            Boolean LAY_CHECKED = false;
            for (Integer roleId : roleIds) {
                if (role.getId().equals(roleId)) {
                    LAY_CHECKED = true;
                    break;
                }
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", role.getId());
            map.put("name", role.getName());
            map.put("remark", role.getRemark());
            map.put("LAY_CHECKED", LAY_CHECKED);
            lists.add(map);
        }
        return new DataGridView(Long.valueOf(lists.size()), lists);
    }

    @Override
    public List<String> queryRoleNamesByUserId(Integer id) {
        List<String> roles = new ArrayList<>();
        //根据用户ID查询所有角色ID的集合
        List<Integer> roleIds = this.roleMapper.queryRoleIdsByUid(id);

        if (roleIds!=null&&roleIds.size()>0){
            QueryWrapper<Role> qw = new QueryWrapper<>();
            qw.eq("available", Constant.AVAILABLE_TRUE);
            qw.in("id",roleIds);
            List<Role> roleList = this.roleMapper.selectList(qw);
            for (Role role : roleList) {
                roles.add(role.getName());
            }
            return roles;
        }else {
            return roles;
        }
    }
}
