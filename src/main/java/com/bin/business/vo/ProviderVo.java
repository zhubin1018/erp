package com.bin.business.vo;

import com.bin.system.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 朱彬
 * @date 2020/3/19 10:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVo extends BaseVo {
    private String phone;
    private String providername;
    private String connectionperson;

}
