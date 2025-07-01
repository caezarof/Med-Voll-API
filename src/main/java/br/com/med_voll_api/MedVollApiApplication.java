package br.com.med_voll_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"br.com.med_voll_api", "other.package"})
public class MedVollApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedVollApiApplication.class, args);
	}
}
