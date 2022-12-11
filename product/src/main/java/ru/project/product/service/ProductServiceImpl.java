package ru.project.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.product.dto.CreateOrderDto;
import ru.project.product.dto.ProductRequest;
import ru.project.product.exception.ProductNotFoundException;
import ru.project.product.model.Product;
import ru.project.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> personalDealProducts(String productsId) {
        String[] stringProdId = productsId.split(",");
        List<Integer> id = new ArrayList<>();
        for (String num : stringProdId) {
            id.add(Integer.parseInt(num.trim()));
        }

        List<Product> discountProducts = productRepository.findAll().stream().
                filter(product -> !id.contains(product.getId())).
                sorted(Comparator.comparing(product -> product.getCount(), Comparator.reverseOrder())).
                limit(2).
                collect(Collectors.toList());
        for (Product x : discountProducts) {
            x.setPrice(Math.floor((x.getPrice() * 0.7D)*10)/10);
        }
        return discountProducts;
    }

    @Override
    public void addProducts(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .count(request.getCount())
                .price(request.getPrice())
                .build();
        productRepository.save(product);
    }

    @Override
    public Product getById(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow
                (() -> new ProductNotFoundException(String.format("Product with id %s not found", id)));
    }


    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public void changeProduct(Integer id, ProductRequest request) {
        Product product = new Product();
        product.setId(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCount(request.getCount());
        product.setPrice(request.getPrice());
        productRepository.save(product);
    }

    @Override
    public void changeProductCount(Integer id, Integer count, CreateOrderDto.Sign sign) {
        Product product = productRepository.findById(id).orElseThrow
                (() -> new ProductNotFoundException(String.format("Product with id %s not found", id)));
        if (sign== CreateOrderDto.Sign.add) {
            product.setCount(product.getCount() + count);
        } else {
            product.setCount(product.getCount() - count);
        }
        productRepository.save(product);
    }

}
