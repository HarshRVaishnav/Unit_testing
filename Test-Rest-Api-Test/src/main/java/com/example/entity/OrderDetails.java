package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderDetails_tbl")

@Data

@AllArgsConstructor

@NoArgsConstructor
@BatchSize(size = 10)
public class OrderDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer orderId;

    @Min(value = 1, message = "Quantity ordered must be at least 1")
    private Integer quantityOrdered;

    //@Min(value = 100, message = "Price each must be at least 100.00")
    private Double priceEach;

    private Integer productCode;


    private Integer orderNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.DETACH})
    //@JoinColumns ({@JoinColumn(name = "orderNumber", referencedColumnName="orderNumber")})
    @JoinColumn(name = "fk_orderNumber")
    @JsonBackReference
    private Order order;

    @JsonIgnore
    @ManyToOne
    private Product product;



    /*
     * @ManyToOne
     *
     * @JoinColumn(name = "orderNumber") private Order order;
     *
     * @ManyToOne
     *
     * @JoinColumn(name="product_id") private Product product;
     */

    /*
     * @ManyToOne(targetEntity = Product.class,cascade = CascadeType.ALL)
     *
     * @JoinColumn(name = "productCode") //default join column
     * product_tbl_product_code private Product product;
     */

}
