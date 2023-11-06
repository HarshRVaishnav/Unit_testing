package com.example.runner;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.entity.Order;
import com.example.entity.OrderStatusEnum;
import com.example.repository.IOrderRepo;
import com.github.javafaker.Faker;

@org.springframework.core.annotation.Order(10)

@Component
public class OrderRunner implements CommandLineRunner
{

    @Autowired
    private IOrderRepo orderRepo;

    @Override
    public void run(String... args) throws Exception
    {

        if (orderRepo.count() == 100)
        {

            for (int i = 1; i < 100; i++)
            {
                Faker faker = new Faker(new Locale("en-IND"));
                Order order = new Order();
                // order.setOrderDate(faker.date().future(1, TimeUnit.DAYS));
                order.setComments(faker.lorem().sentence());
                OrderStatusEnum curentstatus = OrderStatusEnum.PENDING; //order.setStatus(null); //
                // order.setShippedDate(faker.date().future(2, TimeUnit.DAYS));
                //order.setOrderStatusVo(order.getOrderStatusVo().);
                orderRepo.save(order);
            }
        }

    }
}
