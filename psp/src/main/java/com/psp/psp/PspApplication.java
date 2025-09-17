package com.psp.psp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PspApplication {

	public static void main(String[] args) {

        System.out.println(System.getProperty("java.home"));
        SpringApplication.run(PspApplication.class, args);
	}

}
