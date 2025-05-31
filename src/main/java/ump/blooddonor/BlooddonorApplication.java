package ump.blooddonor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BlooddonorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlooddonorApplication.class, args);
	}

}
