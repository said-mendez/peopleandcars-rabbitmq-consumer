package iuresti.training.peopleandcars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class PeopleAndCarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeopleAndCarsApplication.class, args);
	}

}
