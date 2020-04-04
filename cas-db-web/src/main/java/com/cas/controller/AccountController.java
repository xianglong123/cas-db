package com.cas.controller;

import com.cas.db1.domain.Account;
import com.cas.db1.service.AccountService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * 账户余额表(Account)表控制层
 *
 * @author xianglong
 * @since 2020-04-04 14:17:39
 */
@RestController
@RequestMapping("account")
public class AccountController {
    /**
     * 服务对象
     */
    @Autowired
    private AccountService accountService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Account selectOne(String id) {
        return accountService.queryById(id);
    }

    @GetMapping("update")
    public Account update(String id) {
        Account account = new Account();
        account.setId(id);
//        account.setUpdateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        return accountService.update(account);
    }

}