package com.example.controller;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customeResponse.CustomResponse;
import com.example.dto.OrderDto;

import com.example.entity.Order;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.OrderNotFoundException;
import com.example.exception.ProductNotFoundException;
import com.example.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController
{

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private ModelMapper modelMapper;


    private String code;

    private Object data;

    @SuppressWarnings("finally")
    @PostMapping
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderDto orderDTO)
    {
        try
        {
            Order order = modelMapper.map(orderDTO, Order.class);
            Order order2 = iOrderService.createOrder(order);
            OrderDto order3 = modelMapper.map(order2, OrderDto.class);
            data = order2;
            code = "CREATED";
        } catch (OrderNotFoundException orderNotFoundException)
        {
            code = "DATA_NOT_FOUND";
        } catch (CustomerNotFoundException customerNotFoundException)
        {
            code = "CUSTOMER_NOT_FOUND_ERROR";
        } catch (ProductNotFoundException customerNotFoundException)
        {
            code = "PRODUCT_NOT_FOUND_ERROR";
        } catch (IllegalArgumentException argumentException)
        {
            code = "OUT_OF_STOCK";
        } catch (RuntimeException exception)
        {
            code = "RUNTIME_EXCEPTION";
        } catch (Exception e)
        {
            code = "EXCEPTION";
        } finally
        {
            return CustomResponse.response(code, data);
        }
    }


    @SuppressWarnings("finally")
    @DeleteMapping("/delete/{orderNumber}")
    public ResponseEntity<Object> cancelOrder(@PathVariable Integer orderNumber)
    {
        try
        {
            Order order = iOrderService.cancelOrder(orderNumber);
            if (order == null)
            {
                data = order;
                code = "SUCCESS";
            }
        } catch (OrderNotFoundException orderNotFoundException)
        {
            code = "DATA_NOT_CREATED";
        } catch (RuntimeException order)
        {
            code = "RUNTIME_EXCEPTION";
        } catch (Exception e)
        {
            code = "EXCEPTION";
        } finally
        {
            return CustomResponse.response(code, data);
        }
    }

    @SuppressWarnings("finally")
    @GetMapping("/{orderNumber}")
    public ResponseEntity<Object> showOrderById(@PathVariable Integer orderNumber)
    {
        try
        {
            Order orderFound = iOrderService.findOrderById(orderNumber);

            data = orderFound;
            code = "SUCCESS";
        } catch (OrderNotFoundException orderNotFoundException)
        {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException r)
        {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception e)
        {
            data = null;
            code = "EXCEPTION";
        } finally
        {
            return CustomResponse.response(code, data);
        }
    }

    @SuppressWarnings("finally")
    @DeleteMapping("{orderNumber}")
    public ResponseEntity<Object> deleteOrder(@PathVariable @Valid Integer orderNumber)
    {
        try
        {
            String deletedOrder = iOrderService.deleteOrder(orderNumber);
            data = deletedOrder;
            code = "SUCCESS";
        } catch (OrderNotFoundException e)
        {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException runtimeException)
        {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception e)
        {
            data = null;
            code = "EXCEPTION";
        } finally
        {
            return CustomResponse.response(code, data);
        }
    }

    @SuppressWarnings("finally")
    @PatchMapping("/updateShippingDate/{orderNumber}")
    public ResponseEntity<Object> updateShippingDate(
            @Valid @PathVariable Integer orderNumber,
            @RequestBody @Valid Order order
    )
    {
        try
        {
            Order updatedDate = iOrderService.updateShippingDate(orderNumber, order);
            data = updatedDate;
            code = "SUCCESS";
        } catch (OrderNotFoundException e)
        {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException runtimeException)
        {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception r)
        {
            data = null;
            code = "EXCEPTION";
        } finally
        {
            return CustomResponse.response(code, data);
        }
    }

    @SuppressWarnings("finally")
    @PatchMapping("/updateStatus/{orderNumber}")
    public ResponseEntity<Object> updateStatus(
            @PathVariable @Valid Integer orderNumber,
            @RequestBody @Valid Order order
    )
    {
        try
        {
            Order updatedStatus = iOrderService.updateOrderStatus(orderNumber, order);
            data = updatedStatus;
            code = "SUCCESS";
        } catch (OrderNotFoundException orderNotFoundException)
        {
            data = null;
            code = "ORDER_NOT_FOUND_ERROR";
        } catch (RuntimeException runtimeException)
        {
            data = null;
            code = "RUNTIME_EXCEPTION";
        } catch (Exception exception)
        {
            data = null;
            code = "EXCEPTION";
        } finally
        {
            return CustomResponse.response(code, data);
        }
    }


//	@GetMapping
//	public ResponseEntity<?> getAllOrder() {
//		List<Order> list = iOrderService.getAllOrder();
//
//		return SuccessDetails.success("Order Fetch Successfully",list,HttpStatus.OK);
//	}

}