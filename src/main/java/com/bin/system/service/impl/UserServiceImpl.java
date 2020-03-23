package com.bin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.system.common.Constant;
import com.bin.system.common.DataGridView;
import com.bin.system.common.SpringContextUtil;
import com.bin.system.domain.Dept;
import com.bin.system.mapper.RoleMapper;
import com.bin.system.service.DeptService;
import com.bin.system.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.system.domain.User;
import com.bin.system.mapper.UserMapper;
import com.bin.system.service.UserService;

import java.io.Serializable;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User queryUserByLoginName(String loginname) {
        UserMapper userMapper = this.getBaseMapper();

        QueryWrapper<User> qw = new QueryWrapper<>();
        if (StringUtils.isBlank(loginname)) {
            log.error("用户名不能为空");
        }
        qw.eq("loginname", loginname);
        User user = userMapper.selectOne(qw);
        return user;
    }

    @Override
    public DataGridView queryAllUser(UserVo userVo) {
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("type", Constant.USER_TYPE_NORMAL);
        qw.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        qw.like(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        qw.like(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        qw.like(StringUtils.isNotBlank(userVo.getRemark()),"remark",userVo.getRemark());
        this.userMapper.selectPage(page,qw);
        List<User> records = page.getRecords();
        DeptService deptService = SpringContextUtil.getBean(DeptService.class);
        for (User record : records) {
            Dept dept = deptService.getById(record.getDeptid());
            record.setDeptname(dept.getTitle());
        }
        return new DataGridView(page.getTotal(),records);
    }

    @Override
    public User saveUser(User user) {
        this.userMapper.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        this.userMapper.updateById(user);
        return user;
    }

    @Override
    public Integer queryDeptMaxOrderNum() {
        return this.userMapper.queryDeptMaxOrderNum();
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] rids) {
        //根据用户ID删除角色和用户中间表的数据
        roleMapper.deleteRoleUserByUid(uid);
        if (rids!=null&&rids.length>0){
            for (Integer rid : rids) {
                this.userMapper.saveUserRole(uid,rid);
            }
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户ID删除角色与用户中间表是数据
        roleMapper.deleteRoleUserByUid(id);
        return super.removeById(id);
    }
}
