package com.bin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.system.domain.Dept;

/**
 *
 *@author 朱彬
 *@date 2020/3/19 16:58
 *
 */
public interface DeptMapper extends BaseMapper<Dept> {
    Integer queryDeptMaxOrderNum();

    Integer getDeptChildrenCountById(Integer id);
}