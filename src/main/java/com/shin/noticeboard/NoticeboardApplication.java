package com.shin.noticeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
@SpringBootApplication
public class NoticeboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoticeboardApplication.class, args);
	}

}
