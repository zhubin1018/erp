package com.bin.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 朱彬
 * @date 2020/3/18 17:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginfoVo extends BaseVo{
    private String loginname;
    private String loginip;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
}
