package com.bin.system.controller;

import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import com.bin.system.common.ResultObj;
import com.bin.system.domain.Dept;
import com.bin.system.service.DeptService;
import com.bin.system.vo.DeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱彬
 * @date 2020/3/19 17:04
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询所有部门
     *
     * @return java.lang.Object
     * @params [deptVo]
     */
    @RequestMapping("/loadAllDept")
    public Object loadAllDept(DeptVo deptVo) {
        return this.deptService.queryAllDept(deptVo);
    }

    /**
     * 查找部门最大排序码
     *
     * @return com.bin.system.common.DataGridView
     * @params []
     */
    @GetMapping("/queryDeptMaxOrderNum")
    public Object queryDeptMaxOrderNum() {
        Integer maxValue = this.deptService.queryDeptMaxOrderNum();
        return new DataGridView(maxValue + 1);
    }


    /**
     * 新增部门
     *
     * @return com.bin.system.common.ResultObj
     * @params [dept]
     */
    @PostMapping("/addDept")
    public ResultObj addDept(Dept dept) {
        try {
            dept.setSpread(Constant.SPREAD_TRUE);
            dept.setAvailable(Constant.AVAILABLE_TRUE);
            this.deptService.saveDept(dept);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     * @return com.bin.system.common.ResultObj
     * @params [dept]
     */
    @PostMapping("/updateDept")
    public ResultObj updateDept(Dept dept) {
        try {
            this.deptService.update(dept);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }



    /**
     *根据ID获取部门名称
     * @params [id]
     * @return java.lang.Object
     */
    @GetMapping("/getDeptById")
    public Object getDeptById(Integer id){
        return new DataGridView(this.deptService.getById(id));

    }



    /**
     *根据部门ID查询子部门数目
     * @params [id]
     * @return java.lang.Object
     */
    @GetMapping("/getDeptChildrenCountById")
    public Object getDeptChildrenById(Integer id){
        Integer count = this.deptService.getDeptChildrenCountById(id);
        return new DataGridView(count);
    }



    /**
     *根据部门删除
     * @params [id]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/deleteDept")
    public ResultObj deleteDept(Integer id){
        try {
            this.deptService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
