package com.vastio;

import com.vastio.controller.base.BaseController;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * spring boot 入口
 *
 * @author xlch
 * @Date 2018-03-02 9:53
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.vastio.mapper")
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.debug("server start success ...");
    }

}
