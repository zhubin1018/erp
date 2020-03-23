package com.bin.business.service;

import com.bin.business.domain.Provider;
import com.bin.business.domain.Provider;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.business.vo.ProviderVo;
import com.bin.system.common.DataGridView;

/**
 * @author 朱彬
 * @date 2020/3/22 11:42
 */
public interface ProviderService extends IService<Provider> {
    DataGridView queryAllProvider(ProviderVo providerVo);

    Provider saveProvider(Provider provider);

    Provider updateProviderById(Provider provider);

    DataGridView getAllAvailableProvider();
}
