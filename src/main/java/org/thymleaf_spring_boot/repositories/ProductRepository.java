package org.thymleaf_spring_boot.repositories;

import org.thymleaf_spring_boot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
