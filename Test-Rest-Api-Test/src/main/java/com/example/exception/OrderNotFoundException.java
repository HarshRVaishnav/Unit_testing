package com.example.exception;

public class OrderNotFoundException extends RuntimeException
{

    public OrderNotFoundException(String messege)
    {

        super(messege);
    }

}
