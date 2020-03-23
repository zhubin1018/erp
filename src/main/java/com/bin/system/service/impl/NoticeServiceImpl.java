package com.bin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.system.common.DataGridView;
import com.bin.system.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.system.mapper.NoticeMapper;
import com.bin.system.domain.Notice;
import com.bin.system.service.NoticeService;

/**
 * @author 朱彬
 * @date 2020/3/19 10:29
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public DataGridView queryAllNotice(NoticeVo noticeVo) {
        IPage<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit());
        QueryWrapper<Notice> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        qw.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        qw.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        qw.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        qw.orderByDesc("createtime");
        noticeMapper.selectPage(page,qw);
        List<Notice> records = page.getRecords();
        long total = page.getTotal();
        return new DataGridView(total,records);
    }
}
