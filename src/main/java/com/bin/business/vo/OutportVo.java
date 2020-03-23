package com.bin.business.vo;

import com.bin.system.vo.BaseVo;
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
public class OutportVo extends BaseVo {
    private Integer providerid;
    private Integer goodsid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

}
