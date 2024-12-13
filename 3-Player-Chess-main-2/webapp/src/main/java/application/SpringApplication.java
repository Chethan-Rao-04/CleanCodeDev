package application;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication - web application
 **/
@SpringBootApplication
public class SpringApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        System.out.println("http://localhost:8080");
    }
}
