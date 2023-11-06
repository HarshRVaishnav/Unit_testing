package com.example.service;

import java.util.List;

import javax.validation.Valid;

import com.example.entity.Order;
import com.example.entity.OrderDetails;

public interface IOrderService
{


    public List<Order> getAllOrder();

    public Order getOrderByID(Integer orderNumber);

    public Order findOrder(Integer orderNumber);

    public Order createOrder(Order orderDto);

    public Order cancelOrder(Integer orderNumber);

    Order findOrderById(Integer orderNumber);

    String deleteOrder(Integer orderNumber);

    Order updateShippingDate(@Valid Integer orderNumber, @Valid Order order);

    Order updateOrderStatus(Integer orderNumber, Order order);

}
