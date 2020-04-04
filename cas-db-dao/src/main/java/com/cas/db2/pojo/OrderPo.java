package com.cas.db2.pojo;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * 订单表(Order)实体类
 *
 * @author xianglong
 * @since 2020-04-04 14:27:37
 */
@Data
public class OrderPo implements Serializable {
    private static final long serialVersionUID = -15251519664523762L;
    /**
    * 主键ID
    */
    private Long id;
    
    private Date createTime;
    
    private String number;
    /**
    * 订单状态
    */
    private Date status;
    /**
    * 产品ID
    */
    private String productId;
    
    private Double totalAmount;
    /**
    * 数量
    */
    private Integer count;
    /**
    * 用户ID
    */
    private String userId;


}