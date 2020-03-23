package com.bin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bin.business.domain.Provider;
import com.bin.system.common.DataGridView;
import com.bin.system.vo.DeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.system.mapper.DeptMapper;
import com.bin.system.domain.Dept;
import com.bin.system.service.DeptService;
/**
 *
 *@author 朱彬
 *@date 2020/3/19 16:58
 *
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService{

    @Autowired
     private DeptMapper deptMapper;
    @Override
    public DataGridView queryAllDept(DeptVo deptVo) {
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.orderByAsc("ordernum");
        List<Dept> depts = this.deptMapper.selectList(qw);
        return new DataGridView(Long.valueOf(depts.size()),depts);
    }

    @Override
    public Integer queryDeptMaxOrderNum() {
        return this.deptMapper.queryDeptMaxOrderNum();
    }

    @Override
    @CachePut(cacheNames = "com.bin.system.service.impl.DeptServiceImpl",key = "#result.id")
    public Dept saveDept(Dept dept) {
        this.deptMapper.insert(dept);
        return dept;
    }

    @Override
    @CachePut(cacheNames = "com.bin.system.service.impl.DeptServiceImpl",key = "#result.id")
    public Dept update(Dept dept) {
        Dept deptNew = this.deptMapper.selectById(dept.getId());
        BeanUtil.copyProperties(dept,deptNew, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        this.deptMapper.updateById(deptNew);
        return deptNew;
    }

    @Override
    public Integer getDeptChildrenCountById(Integer id) {
        return this.deptMapper.getDeptChildrenCountById(id);
    }

    @Override
    @CacheEvict(cacheNames = "com.bin.system.service.impl.DeptServiceImpl",key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Cacheable(cacheNames = "com.bin.system.service.impl.DeptServiceImpl",key = "#id")
    public Dept getById(Serializable id) {
        return super.getById(id);
    }
}
