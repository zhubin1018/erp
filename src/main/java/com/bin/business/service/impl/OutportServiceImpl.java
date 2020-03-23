package com.bin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.business.domain.Goods;
import com.bin.business.domain.Inport;
import com.bin.business.domain.Provider;
import com.bin.business.mapper.InportMapper;
import com.bin.business.service.GoodsService;
import com.bin.business.service.ProviderService;
import com.bin.business.vo.OutportVo;
import com.bin.system.common.ActiveUser;
import com.bin.system.common.DataGridView;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.business.mapper.OutportMapper;
import com.bin.business.domain.Outport;
import com.bin.business.service.OutportService;
/**
 *
 *@author 朱彬
 *@date 2020/3/22 22:21
 *
 */
@Service
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService{

    @Autowired
    private InportMapper inportMapper;
    @Autowired
    private OutportMapper outportMapper;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ProviderService providerService;
    @Override
    public Outport saveOutport(Outport outport) {
        //保存退货信息
        Integer inportId=outport.getInportid();
        Inport inport = inportMapper.selectById(inportId);
        outport.setGoodsid(inport.getGoodsid());
        outport.setNumber(outport.getNumber());
        outport.setPaytype(inport.getPaytype());
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        outport.setOperateperson(activeUser.getUser().getName());
        outport.setOutporttime(new Date());
        outport.setOutportprice(inport.getInportprice());
        outport.setProviderid(inport.getProviderid());

        this.outportMapper.insert(outport);

        //减少库存
        Goods goods = this.goodsService.getById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-outport.getNumber());
        this.goodsService.updateGoodsById(goods);

        return outport;
    }

    @Override
    public DataGridView queryAllOutport(OutportVo outportVo) {
        IPage<Outport> page = new Page<>(outportVo.getPage(),outportVo.getLimit());
        QueryWrapper<Outport> qw = new QueryWrapper<>();
        qw.eq(outportVo.getGoodsid()!=null,"goodsid",outportVo.getGoodsid());
        qw.eq(outportVo.getProviderid()!=null,"providerid",outportVo.getProviderid());
        qw.eq(outportVo.getStartTime()!=null,"outporttime",outportVo.getStartTime());
        qw.eq(outportVo.getEndTime()!=null,"outporttime",outportVo.getEndTime());

        qw.orderByAsc("outporttime");
        this.outportMapper.selectPage(page,qw);
        List<Outport> records = page.getRecords();
        for (Outport record : records) {
            if (record.getGoodsid()!=null){
                Goods goods = this.goodsService.getById(record.getGoodsid());
                record.setGoodsname(goods.getGoodsname());
                record.setSize(goods.getSize());
            }
            if (record.getProviderid()!=null){
                Provider provider = this.providerService.getById(record.getProviderid());
                record.setProvidername(provider.getProvidername());
            }
        }

        return  new DataGridView(page.getTotal(),page.getRecords());
    }
}
