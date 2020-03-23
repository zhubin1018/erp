package com.bin.business.service;

import com.bin.business.domain.Sales;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.business.vo.SalesVo;
import com.bin.system.common.DataGridView;

/**
 * @author 朱彬
 * @date 2020/3/23 0:07
 */
public interface SalesService extends IService<Sales> {


    DataGridView queryAllSales(SalesVo salesVo);

    Sales saveSales(Sales sales);

    Sales updateSalesById(Sales sales);
}
