package com.bin.business.controller;

import com.bin.system.common.ActiveUser;
import com.bin.system.common.Constant;
import com.bin.system.common.ResultObj;
import com.bin.business.domain.Customer;
import com.bin.business.service.CustomerService;
import com.bin.business.vo.CustomerVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/19 10:56
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    /**
     *查询
     * @params [customerVo]
     * @return java.lang.Object
     */
    @RequestMapping("/loadAllCustomer")
    public Object loadAllNotrice(CustomerVo customerVo){
        return customerService.queryAllCustomer(customerVo);
    }

    /**
     *添加
     * @params [customer]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/addCustomer")
    public ResultObj addCustomer(Customer customer){
        try {
           customer.setAvailable(Constant.AVAILABLE_TRUE);
            this.customerService.saveCustomer(customer);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     *修改
     * @params [customer]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/updateCustomer")
    public ResultObj updateCustomer(Customer customer){
        try {
            customerService.updateCustomerById(customer);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     *删除
     * @params [id]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("deleteCustomer")
    public ResultObj deleteLoginfo(Integer id){
        try {
            customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteCustomer")
    public ResultObj batchDeleteCustomer(Integer[] ids){
        try {
            if (ids!=null&&ids.length>0){
                List<Integer> list = Arrays.asList(ids);
                customerService.removeByIds(list);
                return ResultObj.DELETE_SUCCESS;
            }else {
                return new ResultObj(-1,"传入ID不能为空");
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_SUCCESS;
        }
    }

    @GetMapping("/getAllAvailableCustomer")
    public Object getAllAvailableCustomer(){
        return this.customerService.getAllAvailableCustomer();
    }
}
