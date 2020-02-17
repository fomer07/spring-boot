package com.payroll.springboot;

/**
 * CREATED BY Omer Faruk AY 2/8/2020
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
     super("Order not found with this  id : "+id);
    }
}
