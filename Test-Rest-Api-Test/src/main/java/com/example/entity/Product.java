package com.example.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "product_tbl")
@Data
public class Product
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productCode;

    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    private String productDescription;

    @Min(value = 0, message = "Quantity in stock must be at least 0")
    private Integer quantityInStock;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "100.00", message = "Price must be at least 100.00")
    private Double price;

    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;



    /*
     * @OneToOne(fetch = FetchType.EAGER)
     *
     * private OrderDetails details;
     */


}
