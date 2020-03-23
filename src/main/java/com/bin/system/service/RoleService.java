package com.bin.system.service;

import com.bin.system.common.DataGridView;
import com.bin.system.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.system.vo.RoleVo;

import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/20 14:22
 */
public interface RoleService extends IService<Role> {


    DataGridView queryAllRole(RoleVo roleVo);

    Role saveRole(Role role);

    Role update(Role role);

    List<Integer> queryMenuIdsByRid(Integer id);

    void saveRoleMenu(Integer rid, Integer[] mids);

    DataGridView loadAllAvailableRoleNoPage(RoleVo roleVo);

    List<String> queryRoleNamesByUserId(Integer id);
}
