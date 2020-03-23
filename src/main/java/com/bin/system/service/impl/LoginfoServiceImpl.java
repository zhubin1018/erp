package com.bin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.system.common.DataGridView;
import com.bin.system.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.system.domain.Loginfo;
import com.bin.system.mapper.LoginfoMapper;
import com.bin.system.service.LoginfoService;
/**
 *
 *@author 朱彬
 *@date 2020/3/18 16:58
 *
 */
@Service
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService{

    @Autowired
    private LoginfoMapper loginfoMapper;
    @Override
    public DataGridView queryAllLoginfo(LoginfoVo loginfoVo) {
        System.out.println("=================================================");
        System.out.println("loginfoVo = " + loginfoVo);
        IPage<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        QueryWrapper<Loginfo> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        qw.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        qw.ge(loginfoVo.getStartTime() != null,"logintime",loginfoVo.getStartTime());
        qw.le(loginfoVo.getEndTime() != null,"logintime",loginfoVo.getEndTime());
        qw.orderByDesc("logintime");

        this.loginfoMapper.selectPage(page,qw);

        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
