package com.payroll.springboot;

/**
 * CREATED BY Omer Faruk AY 2/6/2020
 */
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Could not find employee with this id : " + id);
    }
}
