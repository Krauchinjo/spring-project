package ru.project.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.project.product.dto.ProductRequest;
import ru.project.product.model.Product;
import ru.project.product.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public Product getProductById(@RequestParam int id) {
        return productService.getById(id);
    }

    @GetMapping("/personal")
    public List<Product> saleProducts(@RequestParam String id) {
        return productService.personalDealProducts(id);
    }

    @GetMapping("/products")
    public List<Product> products() {
        return productService.allProducts();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/product")
    public void createProd(@RequestBody ProductRequest request) {
        productService.addProducts(request);
    }

    @DeleteMapping("/product")
    public void delProd(@RequestParam Integer id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/product")
    public void changeProd(@RequestParam int id, @RequestBody ProductRequest request) {
        productService.changeProduct(id, request);
    }


}