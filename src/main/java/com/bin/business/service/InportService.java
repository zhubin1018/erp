package com.bin.business.service;

import com.bin.business.domain.Goods;
import com.bin.business.domain.Inport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.business.vo.GoodsVo;
import com.bin.business.vo.InportVo;
import com.bin.system.common.DataGridView;

/**
 * @author 朱彬
 * @date 2020/3/22 17:10
 */
public interface InportService extends IService<Inport> {


    Inport saveInport(Inport inport);

    Inport updateInportById(Inport inport);

    DataGridView queryAllInport(InportVo inportVo);
}
