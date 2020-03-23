package com.bin.business.controller;

import com.bin.business.domain.Inport;
import com.bin.business.service.InportService;
import com.bin.business.vo.InportVo;
import com.bin.system.common.ActiveUser;
import com.bin.system.common.Constant;
import com.bin.system.common.ResultObj;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/19 10:56
 */
@RestController
@RequestMapping("/inport")
public class InportController {
    @Autowired
    private InportService inportService;


    /**
     * 查询
     *
     * @return java.lang.Object
     * @params [inportVo]
     */
    @RequestMapping("/loadAllInport")
    public Object loadAllInport(InportVo inportVo) {
        return inportService.queryAllInport(inportVo);
    }

    /**
     * 添加
     *
     * @return com.bin.system.common.ResultObj
     * @params [inport]
     */
    @RequestMapping("/addInport")
    public ResultObj addInport(Inport inport) {
        try {
            ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
            inport.setOperateperson(activeUser.getUser().getName());
            inport.setInporttime(new Date());
            this.inportService.saveInport(inport);
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
     * @params [inport]
     */
    @RequestMapping("/updateInport")
    public ResultObj updateInport(Inport inport) {
        try {
            inportService.updateInportById(inport);
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
    @RequestMapping("deleteInport")
    public ResultObj deleteInport(Integer id) {
        try {
            inportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}
