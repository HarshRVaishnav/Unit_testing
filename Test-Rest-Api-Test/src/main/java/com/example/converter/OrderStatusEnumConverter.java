package com.example.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.context.annotation.ComponentScan;

import com.example.entity.OrderStatusEnum;
import com.example.vo.OrderStatusVo;


@Converter(autoApply = true)
public class OrderStatusEnumConverter implements AttributeConverter<OrderStatusVo, String>
{


    @Override
    public OrderStatusVo convertToEntityAttribute(String dbData)
    {

        return new OrderStatusVo(dbData);
    }

    @Override
    public String convertToDatabaseColumn(OrderStatusVo attribute)
    {

        return attribute.getValue().toString();
    }


}
