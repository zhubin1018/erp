package com.bin.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.business.domain.Provider;
import com.bin.business.vo.CustomerVo;
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
import java.util.function.Consumer;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.business.domain.Customer;
import com.bin.business.mapper.CustomerMapper;
import com.bin.business.service.CustomerService;

/**
 * @author 朱彬
 * @date 2020/3/22 9:15
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public DataGridView queryAllCustomer(CustomerVo customerVo) {
        IPage<Customer> page = new Page<>();
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.like(customerVo.getAvailable() != null, "avaliable", customerVo.getAvailable());
        qw.like(StringUtils.isNotBlank(customerVo.getCustomername()), "customername", customerVo.getCustomername());
        qw.like(StringUtils.isNotBlank(customerVo.getConnectionperson()), "connectionperson", customerVo.getConnectionperson());
        if (StringUtils.isNotBlank(customerVo.getPhone())) {

            qw.like(StringUtils.isNotBlank(customerVo.getPhone()), "phone", customerVo.getPhone())
                    .or().like(StringUtils.isNotBlank(customerVo.getPhone()), "telephone", customerVo.getPhone());

        }

        this.customerMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @CachePut(cacheNames = "com.bin.business.service.impl.CustomerServiceImpl", key = "#result.id")
    @Override
    public Customer saveCustomer(Customer customer) {
        this.customerMapper.insert(customer);
        return customer;
    }

    @CachePut(cacheNames = "com.bin.business.service.impl.CustomerServiceImpl", key = "#result.id")
    @Override
    public Customer updateCustomerById(Customer customer) {
        Customer customerNew = this.customerMapper.selectById(customer.getId());
        BeanUtil.copyProperties(customer,customerNew, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        this.customerMapper.updateById(customerNew);
        return customerNew;
    }

    @Override
    public DataGridView getAllAvailableCustomer() {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.like("available", Constant.AVAILABLE_TRUE);
        return new DataGridView(this.customerMapper.selectList(qw));
    }

    @Cacheable(cacheNames = "com.bin.business.service.impl.CustomerServiceImpl", key = "#id")
    @Override
    public Customer getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(cacheNames = "com.bin.business.service.impl.CustomerServiceImpl", key = "#id")
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
