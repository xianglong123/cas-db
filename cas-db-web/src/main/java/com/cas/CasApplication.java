package com.cas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author: xianglong
 * @date: 2019/8/13 18:36
 * @version: V1.0
 * @review: xiang_long
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.cas")
//@MapperScan(basePackages = "com.cas")
@EnableCaching
public class CasApplication {

    public static void main(String[] args) {
        try {
            System.out.println("ok");
            SpringApplication.run(CasApplication.class, args);
            
            System.out.println("#############################################");
            System.out.println("#####-----启动成功-----#####");
            System.out.println("#############################################");
        } catch (Exception e) {
            System.out.println("#############################################");
            System.out.println("#####启动失败#####" + e);
            System.out.println("#############################################");
        }
    }
}
