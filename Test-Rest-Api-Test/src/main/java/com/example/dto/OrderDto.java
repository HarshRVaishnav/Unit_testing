package com.example.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.entity.Customer;
import com.example.entity.OrderStatusEnum;
import com.example.vo.OrderStatusVo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class OrderDto {

    private Integer orderNumber;

    private LocalDate orderDate;

    private Date shippedDate;

    private OrderStatusVo status;

    private String comments;

    @JsonIgnore
    private Customer customer;
    private Integer customerNumber;


    private List<OrderDetailDto> orderDetails;

    public List<OrderDetailDto> getOrderDetails()
    {
        if (orderDetails == null)
        {
            orderDetails = new ArrayList<OrderDetailDto>();
        }
        return orderDetails;
    }


}
