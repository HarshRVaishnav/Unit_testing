package com.example.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.ComponentScan;

import com.example.converter.OrderStatusEnumConverter;
import com.example.vo.OrderStatusVo;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;

    //@NotNull(message = "Order date cannot be null")
    private LocalDate orderDate = LocalDate.now();

    private LocalDate shippedDate = LocalDate.now();

    @Column(name = "status", columnDefinition = "ENUM('PENDING', 'CONFIRMED', 'IN_PROGRESS', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'ACTIVATED', 'DEACTIVATED')")
    @Convert(converter = OrderStatusEnumConverter.class)
    private OrderStatusVo status;

    @Size(max = 500, message = "Comments cannot be more than 500 characters")
    private String comments;

    private Integer customerNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.DETACH})
    @JoinColumn(name = "fk_customerNumber")
    @JsonBackReference
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderNumber", referencedColumnName = "orderNumber")
    private List<OrderDetails> orderDetails;

}
