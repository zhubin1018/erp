package com.bin.system.service;

import com.bin.system.common.DataGridView;
import com.bin.system.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.system.vo.UserVo;

public interface UserService extends IService<User>{

    /*
    * 根据用户名查询用户信息
    * */
  

    User queryUserByLoginName(String loginname);

    DataGridView queryAllUser(UserVo userVo);

    User saveUser(User user);

    User update(User user);

    Integer queryDeptMaxOrderNum();

    void saveUserRole(Integer uid, Integer[] rids);
}
