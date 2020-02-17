package com.payroll.springboot;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CREATED BY Omer Faruk AY 2/7/2020
 */
public interface OrderRepository extends JpaRepository<Order,Long> {

}
