package com.test.demowyd.louzai.transaction;


import com.test.demowyd.louzai.transaction.entity.TmspSysUser;
import com.test.demowyd.louzai.transaction.service.TmspSysUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program: spring-wyd
 * @description: 事务学习应用
 * @author: Stone
 * @create: 2023-11-02 14:10
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.test.demowyd.louzai.transaction.dao"})
public class TransactionTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TransactionTestApplication.class, args);
        TmspSysUserService tmspSysUserService = applicationContext.getBean(TmspSysUserService.class);

        tmspSysUserService.updateInTransactional(1678658241810853890L, "wyd-2-update", false);
    }
}
