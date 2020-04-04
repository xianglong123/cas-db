package com.cas.db1.pojo;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * 账户余额表(Account)实体类
 *
 * @author makejava
 * @since 2020-04-04 13:37:12
 */
@Data
public class AccountPo implements Serializable {
    private static final long serialVersionUID = -35318010697293062L;
    /**
    * 主键ID
    */
    private String id;
    /**
    * 用户ID
    */
    private String userId;
    /**
    * 用户余额
    */
    private Double balance;
    /**
    * 冻结金额，扣款暂存余额
    */
    private Double freezeAmount;
    
    private Date createTime;
    
    private Date updateTime;


}