package ru.wm.WorkManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class WorkManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkManagerApplication.class, args);
	}

}
