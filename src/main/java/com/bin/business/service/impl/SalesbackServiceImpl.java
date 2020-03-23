package com.bin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.business.domain.*;
import com.bin.business.mapper.SalesMapper;
import com.bin.business.service.CustomerService;
import com.bin.business.service.GoodsService;
import com.bin.business.service.SalesService;
import com.bin.business.vo.SalesbackVo;
import com.bin.system.common.ActiveUser;
import com.bin.system.common.DataGridView;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.business.mapper.SalesbackMapper;
import com.bin.business.service.SalesbackService;
/**
 *
 *@author 朱彬
 *@date 2020/3/23 0:42
 *
 */
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements SalesbackService{

    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SalesbackMapper salesbackMapper;

    @Override
    public Salesback saveSalesback(Salesback salesback) {
        //保存退货信息
        Integer salesid=salesback.getSalesid();
        Sales sales = this.salesMapper.selectById(salesid);
        salesback.setGoodsid(sales.getGoodsid());
        salesback.setPaytype(sales.getPaytype());
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        salesback.setSalesbacktime(new Date());
        salesback.setSalebackprice(sales.getSaleprice());
        salesback.setOperateperson(activeUser.getUser().getName());
        salesback.setCustomerid(sales.getCustomerid());

        this.salesbackMapper.insert(salesback);

        //增加库存
        Goods goods = this.goodsService.getById(sales.getGoodsid());
        goods.setNumber(goods.getNumber()-sales.getNumber());
        this.goodsService.updateGoodsById(goods);

        //更新销售
        sales.setNumber(sales.getNumber()-salesback.getNumber());
        salesMapper.updateById(sales);

        return salesback;
    }

    @Override
    public DataGridView queryAllSalesback(SalesbackVo salesbackVo) {
        IPage<Salesback> page = new Page<>(salesbackVo.getPage(),salesbackVo.getLimit());
        QueryWrapper<Salesback> qw = new QueryWrapper<>();
        System.out.println(salesbackVo.getGoodsid());
        qw.eq(salesbackVo.getGoodsid()!=null,"goodsid",salesbackVo.getGoodsid());
        qw.eq(salesbackVo.getCustomerid()!=null,"customerid",salesbackVo.getCustomerid());
        qw.eq(salesbackVo.getStartTime()!=null,"salesbacktime",salesbackVo.getStartTime());
        qw.eq(salesbackVo.getEndTime()!=null,"salesbacktime",salesbackVo.getEndTime());

        qw.orderByAsc("salesbacktime");
        this.salesbackMapper.selectPage(page,qw);
        List<Salesback> records = page.getRecords();
        for (Salesback record : records) {
            if (record.getGoodsid()!=null){
                Goods goods = this.goodsService.getById(record.getGoodsid());
                record.setGoodsname(goods.getGoodsname());
                record.setSize(goods.getSize());
            }
            if (record.getCustomerid()!=null){
                Customer provider = this.customerService.getById(record.getCustomerid());
                record.setCustomername(provider.getCustomername());
            }
        }

        return  new DataGridView(page.getTotal(),page.getRecords());
    }

}
