package com.bin.system.controller;

import com.bin.system.common.*;
import com.bin.system.common.upload.UploadProperties;
import com.bin.system.common.upload.UploadService;
import com.bin.system.domain.User;
import com.bin.system.service.UserService;
import com.bin.system.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 朱彬
 * @date 2020/3/19 17:04
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;



    /**
     * 查询所有用户
     *
     * @return java.lang.Object
     * @params [userVo]
     */
    @RequestMapping("/loadAllUser")
    public Object loadAllUser(UserVo userVo) {
        return this.userService.queryAllUser(userVo);
    }


    /**
     * 新增用户
     *
     * @return com.bin.system.common.ResultObj
     * @params [user]
     */
    @PostMapping("/addUser")
    public ResultObj addUser(User user) {
        try {
            user.setType(Constant.USER_TYPE_NORMAL);
            user.setSalt(MD5Utils.creatUUID());
            user.setPwd(MD5Utils.md5(Constant.DEFAULT_PWD, user.getSalt(), 2));
            user.setAvailable(Constant.AVAILABLE_TRUE);
            user.setImgpath(Constant.DEFAULT_TITLE_IMAGE);
            this.userService.saveUser(user);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     *
     * @return com.bin.system.common.ResultObj
     * @params [user]
     */
    @PostMapping("/updateUser")
    public ResultObj updateUser(User user) {
        try {
            this.userService.update(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 重置密码
     *
     * @return com.bin.system.common.ResultObj
     * @params [user]
     */
    @PostMapping("/resetUserPwd")
    public ResultObj resetUserPwd(Integer id) {
        try {
            User user = new User();
            user.setId(id);
            user.setSalt(MD5Utils.creatUUID());
            user.setPwd(MD5Utils.md5(Constant.DEFAULT_PWD, user.getSalt(), 2));
            this.userService.update(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }


    /**
     * 根据用户删除
     *
     * @return com.bin.system.common.ResultObj
     * @params [id]
     */
    @RequestMapping("/deleteUser")
    public ResultObj deleteUser(Integer id) {
        try {
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 查找部门最大排序码
     *
     * @return com.bin.system.common.DataGridView
     * @params []
     */
    @GetMapping("/queryUserMaxOrderNum")
    public Object queryDeptMaxOrderNum() {
        Integer maxValue = this.userService.queryDeptMaxOrderNum();
        return new DataGridView(maxValue + 1);
    }

    @RequestMapping("/saveUserRole")
    public ResultObj saveUserRole(Integer uid, Integer[] rids) {
        try {
            this.userService.saveUserRole(uid, rids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * 查询当前登录用户
     *
     * @return java.lang.Object
     * @params []
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser() {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        return new DataGridView(activeUser.getUser());
    }



}
