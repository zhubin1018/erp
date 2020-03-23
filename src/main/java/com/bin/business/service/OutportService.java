package com.bin.business.service;

import com.bin.business.domain.Outport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.business.vo.OutportVo;
import com.bin.system.common.DataGridView;

/**
 *
 *@author 朱彬
 *@date 2020/3/22 22:21
 *
 */
public interface OutportService extends IService<Outport>{


        Outport saveOutport(Outport outport);

        DataGridView queryAllOutport(OutportVo outportVo);
    }
