package com.bin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.business.domain.Goods;
import com.bin.business.domain.Provider;
import com.bin.business.service.GoodsService;
import com.bin.business.service.ProviderService;
import com.bin.business.vo.GoodsVo;
import com.bin.business.vo.InportVo;
import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.business.mapper.InportMapper;
import com.bin.business.domain.Inport;
import com.bin.business.service.InportService;
/**
 *
 *@author 朱彬
 *@date 2020/3/22 17:10
 *
 */
@Service
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService{

    @Autowired
    private ProviderService providerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private InportMapper inportMapper;
    @Override
    public Inport saveInport(Inport inport) {
        this.inportMapper.insert(inport);
        //更新库存
        Integer goodsId=inport.getGoodsid();
        Goods goods = this.goodsService.getById(goodsId);
        goods.setNumber(goods.getNumber()+inport.getNumber());
        this.goodsService.updateGoodsById(goods);
        return inport;
    }

    @Override
    public Inport updateInportById(Inport inport) {
        Inport oldObj = this.inportMapper.selectById(inport.getId());
        Goods goods = this.goodsService.getById(oldObj.getGoodsid());
        goods.setNumber(goods.getNumber()-oldObj.getNumber()+inport.getNumber());
        this.goodsService.updateGoodsById(goods);
        this.inportMapper.updateById(inport);
        return inport;
    }

    @Override
    public boolean removeById(Serializable id) {
        Inport inport = this.inportMapper.selectById(id);
        Goods goods = this.goodsService.getById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-inport.getNumber());
        this.goodsService.updateGoodsById(goods);
        return super.removeById(id);
    }

    @Override
    public DataGridView queryAllInport(InportVo inportVo) {
        IPage<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());
        QueryWrapper<Inport> qw = new QueryWrapper<>();
        System.out.println("===========================================================");
        System.out.println("goodsid===="+inportVo.getGoodsid());
        System.out.println("providerid===="+inportVo.getProviderid());
        qw.eq(inportVo.getGoodsid()!=null,"goodsid",inportVo.getGoodsid());
        qw.eq(inportVo.getProviderid()!=null,"providerid",inportVo.getProviderid());
        qw.ge(inportVo.getStartTime()!=null,"inporttime",inportVo.getStartTime());
        qw.le(inportVo.getEndTime()!=null,"inporttime",inportVo.getEndTime());
        qw.orderByDesc("inporttime");

        this.inportMapper.selectPage(page,qw);
        List<Inport> records = page.getRecords();
        for (Inport record : records) {
            if(null!=record.getGoodsid()){
                Goods goods = this.goodsService.getById(record.getGoodsid());
                record.setGoodsname(goods.getGoodsname());
                record.setSize(goods.getSize());
            }
            if(null!= record.getProviderid()){
                Provider provider = this.providerService.getById(record.getProviderid());
                record.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(page.getTotal(),records);

    }
}
