package com.sathish.ProductService.command.api.events;


import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sathish.ProductService.command.api.commands.CreateProductCommand;
import com.sathish.ProductService.command.api.data.Product;
import com.sathish.ProductService.command.api.data.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
public class ProductEventsHandler {

    private ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on( ProductCreatedEvent productCreatedEvent) throws Exception {
        Product product =
                new Product();

        BeanUtils.copyProperties(productCreatedEvent,product);
        productRepository.save(product);
        //throw new Exception("Exception occured");
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
    
    
    
}
