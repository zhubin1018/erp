package com.bin.system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 朱彬
 * @date 2020/3/19 10:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeVo extends BaseVo {
    private String title;
    private String opername;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
}
