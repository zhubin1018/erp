package com.bin.business.controller;

import com.bin.business.domain.Provider;
import com.bin.business.service.ProviderService;
import com.bin.business.vo.ProviderVo;
import com.bin.system.common.Constant;
import com.bin.system.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/19 10:56
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;


    /**
     *查询
     * @params [providerVo]
     * @return java.lang.Object
     */
    @RequestMapping("/loadAllProvider")
    public Object loadAllNotrice(ProviderVo providerVo){
        return providerService.queryAllProvider(providerVo);
    }

    /**
     *添加
     * @params [provider]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/addProvider")
    public ResultObj addProvider(Provider provider){
        try {
           provider.setAvailable(Constant.AVAILABLE_TRUE);
            this.providerService.saveProvider(provider);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     *修改
     * @params [provider]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/updateProvider")
    public ResultObj updateProvider(Provider provider){
        try {
            providerService.updateProviderById(provider);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     *删除
     * @params [id]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("deleteProvider")
    public ResultObj deleteLoginfo(Integer id){
        try {
            providerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteProvider")
    public ResultObj batchDeleteProvider(Integer[] ids){
        try {
            if (ids!=null&&ids.length>0){
                List<Integer> list = Arrays.asList(ids);
                providerService.removeByIds(list);
                return ResultObj.DELETE_SUCCESS;
            }else {
                return new ResultObj(-1,"传入ID不能为空");
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_SUCCESS;
        }
    }

    /**
     *查询所有可用供应商，不分页
     * @params []
     * @return java.lang.Object
     */
    @GetMapping("getAllAvailableProvider")
    public Object getAllAvailableProvider(){
        return this.providerService.getAllAvailableProvider();

    }
}
