package com.bin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.system.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/20 14:22
 */
public interface RoleMapper extends BaseMapper<Role> {
    void deleteRoleMenuByRid(Serializable id);

    void deleteRoleMenuByMid(Serializable id);

    void deleteRoleUserByRid(Serializable id);

    void deleteRoleUserByUid(Serializable id);

    List<Integer> queryMenuIdsByRid(@Param("id") Integer id);

    void insertRoleMenu(@Param("rid") Integer rid, @Param("mid") Integer mid);

    List<Integer> queryRoleIdsByUid(Integer userId);


}