package com.bin.business.service;

import com.bin.business.domain.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.business.vo.CustomerVo;
import com.bin.system.common.DataGridView;

/**
 * @author 朱彬
 * @date 2020/3/22 9:15
 */
public interface CustomerService extends IService<Customer> {


    DataGridView queryAllCustomer(CustomerVo customerVo);

    Customer saveCustomer(Customer customer);

    Customer updateCustomerById(Customer customer);

    DataGridView getAllAvailableCustomer();
}
