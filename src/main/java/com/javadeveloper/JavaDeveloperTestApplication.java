package com.javadeveloper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaDeveloperTestApplication  implements CommandLineRunner{

	private static Logger LOG = LoggerFactory.getLogger(JavaDeveloperTestApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(JavaDeveloperTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");
	}

}
