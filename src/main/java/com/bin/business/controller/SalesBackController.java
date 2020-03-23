package com.bin.business.controller;

import com.bin.business.domain.Salesback;
import com.bin.business.service.SalesbackService;
import com.bin.business.vo.SalesbackVo;
import com.bin.system.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱彬
 * @date 2020/3/19 10:56
 */
@RestController
@RequestMapping("/salesback")
public class SalesBackController {
    @Autowired
    private SalesbackService salesbackService;

    /**
     * 查询
     *
     * @return java.lang.Object
     * @params [salesbackVo]
     */
    @RequestMapping("/loadAllSalesback")
    public Object loadAllSalesback(SalesbackVo salesbackVo) {
        return salesbackService.queryAllSalesback(salesbackVo);
    }

    /**
     * 添加退货信息
     *
     * @return com.bin.system.common.ResultObj
     * @params [salesback]
     */
    @RequestMapping("/addSalesback")
    public ResultObj addSalesback(Salesback salesback) {
        try {

            this.salesbackService.saveSalesback(salesback);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

//    /**
//     * 修改
//     *
//     * @return com.bin.system.common.ResultObj
//     * @params [salesback]
//     */
//    @RequestMapping("/updateSalesback")
//    public ResultObj updateSalesback(Salesback salesback) {
//        try {
//            salesbackService.updateSalesbackById(salesback);
//            return ResultObj.UPDATE_SUCCESS;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultObj.UPDATE_ERROR;
//        }
//    }
//
//    /**
//     * 删除
//     *
//     * @return com.bin.system.common.ResultObj
//     * @params [id]
//     */
//    @RequestMapping("deleteSalesback")
//    public ResultObj deleteSalesback(Integer id) {
//        try {
//            salesbackService.removeById(id);
//            return ResultObj.DELETE_SUCCESS;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultObj.DELETE_ERROR;
//        }
//    }

}
