package com.bin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.business.domain.*;
import com.bin.business.service.CustomerService;
import com.bin.business.service.GoodsService;
import com.bin.business.service.ProviderService;
import com.bin.business.vo.SalesVo;
import com.bin.system.common.DataGridView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.business.mapper.SalesMapper;
import com.bin.business.domain.Sales;
import com.bin.business.service.SalesService;
/**
 *
 *@author 朱彬
 *@date 2020/3/23 0:07
 *
 */
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService{
    @Autowired
    private CustomerService customerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SalesMapper salesMapper;
    @Override
    public Sales saveSales(Sales sales) {
        this.salesMapper.insert(sales);
        //更新库存
        Integer goodsId=sales.getGoodsid();
        Goods goods = this.goodsService.getById(goodsId);
        goods.setNumber(goods.getNumber()-sales.getNumber());
        this.goodsService.updateGoodsById(goods);
        return sales;
    }

    @Override
    public Sales updateSalesById(Sales sales) {
        Sales oldObj = this.salesMapper.selectById(sales.getId());
        Goods goods = this.goodsService.getById(oldObj.getGoodsid());
        goods.setNumber(goods.getNumber()+oldObj.getNumber()-sales.getNumber());
        this.goodsService.updateGoodsById(goods);
        this.salesMapper.updateById(sales);
        return sales;
    }

    @Override
    public boolean removeById(Serializable id) {
        Sales sales = this.salesMapper.selectById(id);
        Goods goods = this.goodsService.getById(sales.getGoodsid());
        goods.setNumber(goods.getNumber()+sales.getNumber());
        this.goodsService.updateGoodsById(goods);
        return super.removeById(id);
    }

    @Override
    public DataGridView queryAllSales(SalesVo salesVo) {
        IPage<Sales> page = new Page<>(salesVo.getPage(),salesVo.getLimit());
        QueryWrapper<Sales> qw = new QueryWrapper<>();
        qw.eq(salesVo.getGoodsid()!=null,"goodsid",salesVo.getGoodsid());
        qw.eq(salesVo.getCustomerid()!=null,"providerid",salesVo.getCustomerid());
        qw.ge(salesVo.getStartTime()!=null,"salestime",salesVo.getStartTime());
        qw.le(salesVo.getEndTime()!=null,"salestime",salesVo.getEndTime());
        qw.orderByDesc("salestime");

        this.salesMapper.selectPage(page,qw);
        List<Sales> records = page.getRecords();
        for (Sales record : records) {
            if(null!=record.getGoodsid()){
                Goods goods = this.goodsService.getById(record.getGoodsid());
                record.setGoodsname(goods.getGoodsname());
                record.setSize(goods.getSize());
            }
            if(null!= record.getCustomerid()){
                Customer customer = this.customerService.getById(record.getCustomerid());
                record.setCustomername(customer.getCustomername());
            }
        }
        return new DataGridView(page.getTotal(),records);

    }
}
