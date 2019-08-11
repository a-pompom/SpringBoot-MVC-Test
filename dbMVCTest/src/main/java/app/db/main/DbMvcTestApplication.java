package app.db.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("app.db")
@EntityScan("app.db.entity")
@SpringBootApplication
public class DbMvcTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbMvcTestApplication.class, args);
	}

}
