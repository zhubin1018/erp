package com.bin.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 朱彬
 * @date 2020/3/19 17:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends BaseVo {

    private String name;
    private String remark;
    private String address;
    private Integer deptid;
}
