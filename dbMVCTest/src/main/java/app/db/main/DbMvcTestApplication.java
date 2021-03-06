package app.db.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@EntityScan("app.db.entity")
@SpringBootApplication(scanBasePackages = "app.db")
public class DbMvcTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbMvcTestApplication.class, args);
	}

}
