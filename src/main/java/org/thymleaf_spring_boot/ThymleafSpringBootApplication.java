package org.thymleaf_spring_boot;

import org.thymleaf_spring_boot.entities.Product;
import org.thymleaf_spring_boot.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThymleafSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThymleafSpringBootApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            Product product1 = Product.builder().name("p1").price(10.20).description("pppppp11111").build();
            Product product2 = Product.builder().name("p2").price(50.0).description("pppppp22222").build();
            Product product3 = Product.builder().name("p3").price(70.0).description("pppppp33333").build();
            productRepository.save(product1);
            productRepository.save(product2);
        };
    }

}
