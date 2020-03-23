package com.bin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.business.domain.Provider;
import com.bin.business.service.ProviderService;
import com.bin.business.vo.GoodsVo;
import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import com.bin.system.common.SpringContextUtil;
import com.bin.system.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.business.mapper.GoodsMapper;
import com.bin.business.domain.Goods;
import com.bin.business.service.GoodsService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 朱彬
 * @date 2020/3/22 14:43
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProviderService providerService;

    @CachePut(cacheNames = "com.bin.business.service.impl.GoodsServiceImpl", key = "#result.id")
    @Override
    public Goods updateGoodsById(Goods goods) {
        this.goodsMapper.updateById(goods);
        return goods;
    }

    @Override
    public DataGridView getAllAvailableGoods() {
        QueryWrapper<Goods> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Goods> goods = this.goodsMapper.selectList(qw);
        return new DataGridView(goods);
    }

    @Override
    public DataGridView getGoodsByProviderId(Integer providerid) {
        if (providerid != null) {
            QueryWrapper<Goods> qw = new QueryWrapper<>();
            qw.eq("available",Constant.AVAILABLE_TRUE);
            qw.eq("providerid",providerid);
            List<Goods> goods = this.goodsMapper.selectList(qw);
            return new DataGridView(goods);
        }
        return null;
    }

    @CachePut(cacheNames = "com.bin.business.service.impl.GoodsServiceImpl", key = "#result.id")
    @Override
    public Goods saveGoods(Goods goods) {
        this.goodsMapper.insert(goods);
        return goods;
    }


    @Override
    public DataGridView queryAllGoods(GoodsVo goodsVo) {
        IPage<Goods> page = new Page<>(goodsVo.getPage(), goodsVo.getLimit());
        QueryWrapper<Goods> qw = new QueryWrapper<>();
        qw.eq(goodsVo.getAvailable() != null, "available", goodsVo.getAvailable());
        qw.eq(goodsVo.getProviderid() != null, "providerid", goodsVo.getProviderid());
        qw.like(StringUtils.isNotBlank(goodsVo.getGoodsname()), "goodsname", goodsVo.getGoodsname());
        qw.like(StringUtils.isNotBlank(goodsVo.getSize()), "size", goodsVo.getSize());
        qw.like(StringUtils.isNotBlank(goodsVo.getProductcode()), "productcode", goodsVo.getProductcode());
        qw.like(StringUtils.isNotBlank(goodsVo.getPromitcode()), "promitcode", goodsVo.getPromitcode());
        qw.like(StringUtils.isNotBlank(goodsVo.getDescription()), "description", goodsVo.getDescription());
        this.goodsMapper.selectPage(page, qw);
        List<Goods> records = page.getRecords();
        for (Goods record : records) {
            if (null != record.getProviderid()) {
                Integer id = record.getProviderid();
                Provider provider = providerService.getById(id);
                record.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(page.getTotal(), page.getRecords());

    }

    @Cacheable(cacheNames = "com.bin.business.service.impl.GoodsServiceImpl", key = "#id")
    @Override
    public Goods getById(Serializable id) {
        return super.getById(id);
    }


    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @CacheEvict(cacheNames = "com.bin.business.service.impl.GoodsServiceImpl", key = "#id")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }


}
