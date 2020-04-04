package com.cas.controller;

import com.cas.db2.domain.Order;
import com.cas.db2.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单表(Order)表控制层
 *
 * @author xianglong
 * @since 2020-04-04 17:59:45
 */
@RestController
@RequestMapping("order")
public class OrderController {
    /**
     * 服务对象
     */
    @Autowired
    private OrderService orderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Order selectOne(Long id) {
        return orderService.queryById(id);
    }

}