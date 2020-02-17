package com.payroll.springboot;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CREATED BY Omer Faruk AY 2/7/2020
 */
@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
public class Order {
    private @Id
    @GeneratedValue
    Long id;
    private String description;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order() {
    }
    public Order(String description, Status status) {

        this.description = description;
        this.status = status;
    }

}