package edu.mateusmercon.productsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ProductsApiApplication {

	// Dotenv dotenv = Dotenv.load();

	// String dbUrl = dotenv.get("DB_URL");
	// String dbUsername = dotenv.get("DB_USERNAME");
	// String dbPassword = dotenv.get("DB_PASSWORD");

	public static void main(String[] args) {
		SpringApplication.run(ProductsApiApplication.class, args);
	}

}
