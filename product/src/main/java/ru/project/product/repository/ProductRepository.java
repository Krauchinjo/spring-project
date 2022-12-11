package ru.project.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.product.model.Product;
import ru.project.product.service.ProductService;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


}
