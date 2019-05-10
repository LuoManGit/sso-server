package com.man.ssoserver;

import com.man.ssoserver.controller.SSOController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SsoServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(SsoServerApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(SsoServerApplication.class, args);
		logger.info("SSOSever 服务启动成功");
	}

}
