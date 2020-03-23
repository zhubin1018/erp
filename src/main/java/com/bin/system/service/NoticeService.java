package com.bin.system.service;

import com.bin.system.common.DataGridView;
import com.bin.system.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.system.vo.NoticeVo;

/**
 *
 *@author 朱彬
 *@date 2020/3/19 10:29
 *
 */
public interface NoticeService extends IService<Notice>{


        DataGridView queryAllNotice(NoticeVo noticeVo);
    }
