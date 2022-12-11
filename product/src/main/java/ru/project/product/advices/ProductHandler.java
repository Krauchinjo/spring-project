package ru.project.product.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.product.advices.dto.IncorrectOrderDto;
import ru.project.product.advices.dto.KafkaExceptionDto;
import ru.project.product.advices.dto.ProductExceptionDto;
import ru.project.product.exception.IncorrectOrderException;
import ru.project.product.exception.KafkaException;
import ru.project.product.exception.ProductNotFoundException;

@ControllerAdvice
public class ProductHandler {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ProductExceptionDto> notFoundException(ProductNotFoundException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(new ProductExceptionDto(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ProductExceptionDto> errorHandlingDeserializer(IllegalStateException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(new ProductExceptionDto(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectOrderException.class)
    public ResponseEntity<IncorrectOrderDto> incorrectOrderDtoException (IncorrectOrderException e){
        String message = e.getMessage();
        return new ResponseEntity<>(new IncorrectOrderDto(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = KafkaException.class)
    public ResponseEntity<KafkaExceptionDto> kafkaException (KafkaException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(new KafkaExceptionDto(message), HttpStatus.CONFLICT);
    }

}