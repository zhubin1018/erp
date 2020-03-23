package com.bin.business.service;

import com.bin.business.domain.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.business.vo.GoodsVo;
import com.bin.system.common.DataGridView;

/**
 *
 *@author 朱彬
 *@date 2020/3/22 14:43
 *
 */
public interface GoodsService extends IService<Goods>{


    DataGridView queryAllGoods(GoodsVo goodsVo);

    Goods saveGoods(Goods goods);

    Goods updateGoodsById(Goods goods);

    DataGridView getAllAvailableGoods();

    DataGridView getGoodsByProviderId(Integer providerid);
}
