package com.example.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    private Integer orderId;

    private Integer orderNumber;

    private Integer productCode;

    private Integer quantityOrdered;

    private Double priceEach;


    public OrderDetailDto(Integer orderNumber, Integer productCode, Integer quantityOrdered, Double priceEach)
    {
        super();
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
    }

    public OrderDetailDto()
    {
        // TODO Auto-generated constructor stub
    }


}
