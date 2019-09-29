package app.test.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("app.db.entity")
@SpringBootApplication(scanBasePackages = "app.db.dao")
public class DaoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaoTestApplication.class, args);
	}

}

