package com.example.serde;


import com.example.vo.OrderStatusVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class OrderStatusSerdeProvider extends SimpleModule
{

    private static final long serialVersionUID = 1L;

    public OrderStatusSerdeProvider()
    {
        addSerializer(OrderStatusVo.class, new OrderStatusVoSerializer());
        addDeserializer(OrderStatusVo.class, new OrderStatusVoDeserializer());
    }

    private static class OrderStatusVoSerializer extends JsonSerializer<OrderStatusVo>
    {
        @Override
        public void serialize(
                OrderStatusVo orderStatusVo, JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider
        ) throws IOException
        {
            jsonGenerator.writeString(orderStatusVo.getValue().toString());
        }
    }

    private static class OrderStatusVoDeserializer extends JsonDeserializer<OrderStatusVo>
    {
        @Override
        public OrderStatusVo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException
        {
            String value = jsonParser.getValueAsString();
            return new OrderStatusVo(value);
        }
    }
}
