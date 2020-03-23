package com.bin.system.service;

import com.bin.system.common.DataGridView;
import com.bin.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.system.vo.DeptVo;

/**
 * @author 朱彬
 * @date 2020/3/19 16:58
 */
public interface DeptService extends IService<Dept> {


    DataGridView queryAllDept(DeptVo deptVo);

    Integer queryDeptMaxOrderNum();

    Dept saveDept(Dept dept);

    Dept update(Dept dept);

    Integer getDeptChildrenCountById(Integer id);
}
