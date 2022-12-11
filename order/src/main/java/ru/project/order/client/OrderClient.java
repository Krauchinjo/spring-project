package ru.project.order.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.project.order.dto.ProductDto;
import ru.project.order.exception.BadRequestException;
import ru.project.order.exception.ProductNotFoundException;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderClient {
    private final RestTemplate restTemplate;
    @Value("${url.product-link}")
    private String productUrl;

    public ProductDto getProduct(int id) {
        String url = productUrl + "/product?id=" + id;
        try {
            return restTemplate.getForObject(new URI(url), ProductDto.class);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Bad request for product with id:%s", id));
        }
    }

    public List<ProductDto> getSaleProducts(String id){
        String url = productUrl + "/personal?id=" + id;
        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return responseEntity.getBody();
    }

    public List<ProductDto> getAllProducts(){
        String url = productUrl + "/products";
        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return responseEntity.getBody();
    }

}
