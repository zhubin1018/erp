package com.bin.business.controller;

import com.bin.business.domain.Sales;
import com.bin.business.service.SalesService;
import com.bin.business.vo.SalesVo;
import com.bin.system.common.ActiveUser;
import com.bin.system.common.ResultObj;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 朱彬
 * @date 2020/3/19 10:56
 */
@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private SalesService salesService;


    /**
     * 查询
     *
     * @return java.lang.Object
     * @params [salesVo]
     */
    @RequestMapping("/loadAllSales")
    public Object loadAllSales(SalesVo salesVo) {
        return salesService.queryAllSales(salesVo);
    }

    /**
     * 添加
     *
     * @return com.bin.system.common.ResultObj
     * @params [sales]
     */
    @RequestMapping("/addSales")
    public ResultObj addSales(Sales sales) {
        try {
            ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
            sales.setOperateperson(activeUser.getUser().getName());
            sales.setSalestime(new Date());
            this.salesService.saveSales(sales);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     *
     * @return com.bin.system.common.ResultObj
     * @params [sales]
     */
    @RequestMapping("/updateSales")
    public ResultObj updateSales(Sales sales) {
        try {
            salesService.updateSalesById(sales);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     *
     * @return com.bin.system.common.ResultObj
     * @params [id]
     */
    @RequestMapping("deleteSales")
    public ResultObj deleteSales(Integer id) {
        try {
            salesService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}
