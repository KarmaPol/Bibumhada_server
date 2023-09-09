package com.bibum_server;

import com.bibum_server.global.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:database/application-database.yml"}, factory = YamlPropertySourceFactory.class)
public class BibumServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibumServerApplication.class, args);
	}

}
