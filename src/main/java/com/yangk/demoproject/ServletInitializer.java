package com.yangk.demoproject;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 生成war包注册启动类
 * tomcat启动war包程序
 *
 * @author yangk
 * @date 2020/3/20
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoprojectApplication.class);
	}

}
