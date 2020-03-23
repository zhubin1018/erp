package com.bin.system.service;

import com.bin.system.common.DataGridView;
import com.bin.system.domain.Loginfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.system.vo.LoginfoVo;

/**
 *
 *@author 朱彬
 *@date 2020/3/18 16:58
 *
 */
public interface LoginfoService extends IService<Loginfo>{

    DataGridView queryAllLoginfo(LoginfoVo loginfoVo);
}
