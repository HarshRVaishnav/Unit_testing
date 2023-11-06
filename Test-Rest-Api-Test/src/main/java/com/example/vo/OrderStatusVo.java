package com.example.vo;

import com.example.entity.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Schema(implementation = OrderStatusEnum.class, description = "Order Status")

public class OrderStatusVo
{

    OrderStatusEnum value;


    public OrderStatusVo(String dbData)
    {

        value = OrderStatusEnum.valueOf(dbData);

    }

    @Override
    public String toString()
    {
        return value.toString();
    }
}