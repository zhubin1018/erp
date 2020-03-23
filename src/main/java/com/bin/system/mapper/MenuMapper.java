package com.bin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.system.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    Integer queryMenuMaxOrderNum();

    Integer getMenuChildrenCountById(Integer id);

    List<Integer> querMenuIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);

}