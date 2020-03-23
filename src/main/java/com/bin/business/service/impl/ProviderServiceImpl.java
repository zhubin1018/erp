package com.bin.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.business.domain.Provider;
import com.bin.business.mapper.ProviderMapper;
import com.bin.business.vo.ProviderVo;
import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.business.mapper.ProviderMapper;
import com.bin.business.domain.Provider;
import com.bin.business.service.ProviderService;
/**
 *
 *@author 朱彬
 *@date 2020/3/22 11:42
 *
 */
@Service
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService{
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public DataGridView queryAllProvider(ProviderVo providerVo) {
        IPage<Provider> page = new Page<>();
        QueryWrapper<Provider> qw = new QueryWrapper<>();
        qw.like(providerVo.getAvailable()!=null,"avaliable",providerVo.getAvailable());
        qw.like(StringUtils.isNotBlank(providerVo.getProvidername()),"providername",providerVo.getProvidername());
        qw.like(StringUtils.isNotBlank(providerVo.getConnectionperson()),"connectionperson",providerVo.getConnectionperson());
        if (StringUtils.isNotBlank(providerVo.getPhone())) {

            qw.like(StringUtils.isNotBlank(providerVo.getPhone()), "phone", providerVo.getPhone())
                    .or().like(StringUtils.isNotBlank(providerVo.getPhone()), "telephone", providerVo.getPhone());

        }

        this.providerMapper.selectPage(page,qw);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @CachePut(cacheNames = "com.bin.business.service.impl.ProviderServiceImpl",key = "#result.id")
    @Override
    public Provider saveProvider(Provider provider) {
        this.providerMapper.insert(provider);
        return provider;
    }

    @CachePut(cacheNames = "com.bin.business.service.impl.ProviderServiceImpl",key = "#result.id")
    @Override
    public Provider updateProviderById(Provider provider) {
        Provider providerNew = this.providerMapper.selectById(provider.getId());
        BeanUtil.copyProperties(provider,providerNew, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        this.providerMapper.updateById(providerNew);
        return providerNew;
    }

    @Cacheable(cacheNames = "com.bin.business.service.impl.ProviderServiceImpl",key = "#id")
    @Override
    public Provider getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(cacheNames = "com.bin.business.service.impl.ProviderServiceImpl",key = "#id")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public DataGridView getAllAvailableProvider() {
        QueryWrapper<Provider> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Provider> providers = this.providerMapper.selectList(qw);
        return new DataGridView(providers);
    }
}
