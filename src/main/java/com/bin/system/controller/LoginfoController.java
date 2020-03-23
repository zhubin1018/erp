package com.bin.system.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.bin.system.common.ResultObj;
import com.bin.system.service.LoginfoService;
import com.bin.system.vo.LoginfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/18 17:32
 */

@RestController
@RequestMapping("/loginfo")
public class LoginfoController {
    @Autowired
    private LoginfoService loginfoService;


    /**
     * 查询所有登录日志
     * @return java.lang.Object
     * @params [loginfoVo]
     */
    @RequestMapping("/loadAllLoginfo")
    public Object loadAllLoginfo(LoginfoVo loginfoVo) {
        return this.loginfoService.queryAllLoginfo(loginfoVo);
    }




    /**
     *删除
     * @params [id]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
      try {
        loginfoService.removeById(id);
        return ResultObj.DELETE_SUCCESS;
      }catch (Exception e){
          e.printStackTrace();
          return ResultObj.DELETE_ERROR;
      }
    }


    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(Integer[] ids){
        try {
            if (ids!=null&&ids.length>0){
                List<Integer> list = Arrays.asList(ids);
                loginfoService.removeByIds(list);
                return ResultObj.DELETE_SUCCESS;
            }else {
                return new ResultObj(-1,"传入ID不能为空");
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_SUCCESS;
        }
    }




}
