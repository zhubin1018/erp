package com.bin.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 朱彬
 * @date 2020/3/22 17:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "bus_inport")
public class Inport implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "paytype")
    private String paytype;


    @TableField(value = "inporttime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inporttime;

    @TableField(value = "operateperson")
    private String operateperson;

    @TableField(value = "number")
    private Integer number;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "inportprice")
    private Double inportprice;

    @TableField(value = "providerid")
    private Integer providerid;

    @TableField(exist = false)
    private String providername;

    @TableField(value = "goodsid")
    private Integer goodsid;

    @TableField(exist = false)
    private String goodsname;

    @TableField(exist = false)
    private String size;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PAYTYPE = "paytype";

    public static final String COL_INPORTTIME = "inporttime";

    public static final String COL_OPERATEPERSON = "operateperson";

    public static final String COL_NUMBER = "number";

    public static final String COL_REMARK = "remark";

    public static final String COL_INPORTPRICE = "inportprice";

    public static final String COL_PROVIDERID = "providerid";

    public static final String COL_GOODSID = "goodsid";
}