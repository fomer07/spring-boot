package com.payroll.springboot;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CREATED BY Omer Faruk AY 2/6/2020
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
