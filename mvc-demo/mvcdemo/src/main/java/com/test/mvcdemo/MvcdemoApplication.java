package com.test.mvcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RequestMapping("/api/v1")
@RestController
public class MvcdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcdemoApplication.class, args);
	}

	@GetMapping("/info")
	public String info(){
		return "123";
	}

}
