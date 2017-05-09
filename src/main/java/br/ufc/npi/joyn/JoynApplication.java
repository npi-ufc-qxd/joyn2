package br.ufc.npi.joyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
//@ComponentScan("br.ufc.quixada.npi.service")
@SpringBootApplication
public class JoynApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoynApplication.class, args);
	}
}
