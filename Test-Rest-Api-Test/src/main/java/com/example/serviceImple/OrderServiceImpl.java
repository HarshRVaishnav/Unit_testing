package com.example.serviceImple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetails;
import com.example.entity.Product;
import com.example.exception.OrderNotFoundException;
import com.example.exception.ProductNotFoundException;
import com.example.repository.IOrderRepo;
import com.example.service.ICustomerService;
import com.example.service.IOrderDetailService;
import com.example.service.IOrderService;
import com.example.service.IProductService;

@Service
public class OrderServiceImpl implements IOrderService
{

    @Autowired
    private IOrderRepo orderRepo;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProductService iProductService;


    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private IOrderDetailService detailService;

    @Override
    public Order createOrder(Order order)
    {

        order.setComments(order.getComments());
        Customer customer = customerService.getCustomerById(order.getCustomerNumber());
        order.setCustomerNumber(customer.getCustomerNumber());
        order.setCustomer(customer);
        order.setStatus(order.getStatus());

        orderRepo.save(order);

        List<OrderDetails> orderDetailsDtoList = order.getOrderDetails();

        List<Integer> pid = orderDetailsDtoList.stream().map(OrderDetails::getProductCode).collect(Collectors.toList());

        List<Product> products = iProductService.getlistbyproductCode(pid);

        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductCode, product -> product));

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetails orderDetailsDto : orderDetailsDtoList)
        {
            Product product = productMap.get(orderDetailsDto.getProductCode());

            if (product.getQuantityInStock() < orderDetailsDto.getQuantityOrdered())
            {
                throw new IllegalArgumentException("Product is out of stock");
            }
            iProductService.updateProductQuantityInStock(product.getProductCode(),
                    orderDetailsDto.getQuantityOrdered());
            OrderDetails orderDetails = modelMapper.map(orderDetailsDto, OrderDetails.class);
            orderDetails.setProduct(product);
            orderDetails.setQuantityOrdered(orderDetailsDto.getQuantityOrdered());
            orderDetails.setPriceEach(product.getPrice());
            orderDetails.setOrderNumber(order.getOrderNumber());
            orderDetailsList.add(orderDetails);
        }
        detailService.saveAll(orderDetailsList);
        return order;
    }

    @Override
    public Order findOrderById(@Valid Integer orderNumber)
    {
        return orderRepo.findById(orderNumber).orElseThrow(() -> new OrderNotFoundException("Order does not exist"));
    }

    @Override
    public String deleteOrder(Integer orderNumber)
    {
        try
        {
            if (orderNumber != null)
            {
                orderRepo.deleteById(orderNumber);
            }
            return "One Customer deleted " + orderNumber;
        } catch (RuntimeException e)
        {
            throw new OrderNotFoundException("Id does Not Exist");
        }
    }

    @Override
    public Order updateShippingDate(@Valid Integer orderNumber, @Valid Order order)
    {
        try
        {
            Order orderEntity = new Order();
            if (orderNumber != null)
            {
                orderEntity.setShippedDate(order.getShippedDate());
            }
            return orderRepo.save(orderEntity);
        } catch (RuntimeException e)
        {
            throw new OrderNotFoundException("Shipping date not updated as given order number not found");
        }
    }

    @Override
    public Order updateOrderStatus(Integer orderNumber, Order order)
    {
        Optional<Order> foundOrder = orderRepo.findById(orderNumber);
        if (foundOrder.isPresent())
        {
            //order.setStatus(order.getStatus());
            order.setStatus(order.getStatus());
            return orderRepo.save(order);
        } else
        {
            throw new OrderNotFoundException("Comment of given order could not be updated");
        }
    }


    @Override
    public List<Order> getAllOrder()
    {
        return orderRepo.findAll();
    }

    @Override
    public Order getOrderByID(Integer orderNumber)
    {
        return orderRepo.findById(orderNumber).orElseThrow();
    }

    @Override
    public Order findOrder(Integer orderNumber)
    {
        return orderRepo.findById(orderNumber).orElseThrow();
    }

    @Override
    public Order cancelOrder(Integer orderNumber)
    {

        Order order = new Order();

        List<OrderDetails> orderDetailsList = order.getOrderDetails();
        for (OrderDetails orderDetails : orderDetailsList)
        {
            Product product = orderDetails.getProduct();
            iProductService.updateProductQuantityInStock(product.getProductCode(), orderDetails.getQuantityOrdered());
        }
        return null;
    }
}
