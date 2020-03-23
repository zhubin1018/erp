package com.bin.business.controller;

import com.bin.business.domain.Outport;
import com.bin.business.service.OutportService;
import com.bin.business.vo.OutportVo;
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
@RequestMapping("/outport")
public class OutportController {
    @Autowired
    private OutportService outportService;

    /**
     * 查询
     *
     * @return java.lang.Object
     * @params [outportVo]
     */
    @RequestMapping("/loadAllOutport")
    public Object loadAllOutport(OutportVo outportVo) {
        return outportService.queryAllOutport(outportVo);
    }

    /**
     * 添加退货信息
     *
     * @return com.bin.system.common.ResultObj
     * @params [outport]
     */
    @RequestMapping("/addOutport")
    public ResultObj addOutport(Outport outport) {
        try {

            this.outportService.saveOutport(outport);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


}
