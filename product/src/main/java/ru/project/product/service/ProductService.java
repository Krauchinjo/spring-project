package ru.project.product.service;

import ru.project.product.dto.CreateOrderDto;
import ru.project.product.dto.ProductRequest;
import ru.project.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> allProducts();

    void addProducts(ProductRequest request);

    Product getById(Integer id);

    void deleteProduct(Integer id);

    void changeProduct(Integer id, ProductRequest request);

    void changeProductCount(Integer id, Integer count, CreateOrderDto.Sign sign);

    List<Product> personalDealProducts (String productsId);

}
