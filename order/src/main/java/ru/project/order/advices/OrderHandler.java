package ru.project.order.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.order.advices.dto.OrderExceptionDto;
import ru.project.order.exception.BadRequestException;
import ru.project.order.exception.OrderNotFoundException;
import ru.project.order.exception.ProductNotFoundException;
import ru.project.order.exception.UncorrectProductCountException;

@ControllerAdvice
public class OrderHandler {

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<OrderExceptionDto> notFoundException(OrderNotFoundException e) {
        return new ResponseEntity<>(new OrderExceptionDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UncorrectProductCountException.class)
    public ResponseEntity<OrderExceptionDto> uncorrectedCountException(UncorrectProductCountException e) {
        return new ResponseEntity<>(new OrderExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<OrderExceptionDto> notFoundProductException(ProductNotFoundException e) {
        return new ResponseEntity<>(new OrderExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<OrderExceptionDto> badRequestException(BadRequestException e){
        return new ResponseEntity<>(new OrderExceptionDto(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = HttpMessageNotReadableException.class)
//    public ResponseEntity<OrderExceptionDto> jsonError(HttpMessageNotReadableException e) {
//        return new ResponseEntity<>(new OrderExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
//    }

}
