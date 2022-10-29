package com.sathish.ProductService.command.api.aggregate;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sathish.ProductService.command.api.commands.CreateProductCommand;
import com.sathish.ProductService.command.api.events.ProductCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){

    }


    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand)
    {
       //Perform all the validations
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }
    @EventSourcingHandler
    public void on( ProductCreatedEvent productCreatedEvent)
    {
        this.quantity = productCreatedEvent.getQuantity();
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.name = productCreatedEvent.getName();
    }

}
