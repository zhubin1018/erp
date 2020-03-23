package com.bin.business.service;

import com.bin.business.domain.Sales;
import com.bin.business.domain.Salesback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.business.vo.SalesbackVo;
import com.bin.system.common.DataGridView;

/**
 *
 *@author 朱彬
 *@date 2020/3/23 0:42
 *
 */
public interface SalesbackService extends IService<Salesback>{


        Salesback saveSalesback(Salesback salesback);

        DataGridView queryAllSalesback(SalesbackVo salesbackVo);
    }
