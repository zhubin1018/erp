package com.bin.business.controller;

import com.bin.business.domain.Goods;
import com.bin.business.service.GoodsService;
import com.bin.business.vo.GoodsVo;
import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
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
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


    /**
     *查询
     * @params [goodsVo]
     * @return java.lang.Object
     */
    @RequestMapping("/loadAllGoods")
    public Object loadAllGoods(GoodsVo goodsVo){
        return goodsService.queryAllGoods(goodsVo);
    }

    /**
     *添加
     * @params [goods]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/addGoods")
    public ResultObj addGoods(Goods goods){
        try {
           goods.setAvailable(Constant.AVAILABLE_TRUE);
            this.goodsService.saveGoods(goods);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     *修改
     * @params [goods]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/updateGoods")
    public ResultObj updateGoods(Goods goods){
        try {
            goodsService.updateGoodsById(goods);
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
    @RequestMapping("deleteGoods")
    public ResultObj deleteGoods(Integer id){
        try {
            goodsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteGoods")
    public ResultObj batchDeleteGoods(Integer[] ids){
        try {
            if (ids!=null&&ids.length>0){
                List<Integer> list = Arrays.asList(ids);
                goodsService.removeByIds(list);
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
     *查询所有商品
     * @params []
     * @return java.lang.Object
     */
    @GetMapping("/getAllAvailableGoods")
    public Object getAllAvailableGoods(){
        return this.goodsService.getAllAvailableGoods();
    }
    @GetMapping("/getGoodsByProviderId")
    public Object getGoodsByProviderId(Integer providerid){
        return this.goodsService.getGoodsByProviderId(providerid);
    }

    @GetMapping("/getGoodsByGoodId")
    public Object getGoodsByGoodId(Integer goodsid){
        return new DataGridView(this.goodsService.getById(goodsid));
    }
}
