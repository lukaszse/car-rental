package org.lukaszse.contractorsapp.model.dto;

import org.lukaszse.contractorsapp.model.Car;
import org.lukaszse.contractorsapp.model.Reservation;

import java.time.LocalDate;

public class OrderViewDto {


    private Integer id;
    private LocalDate orderDate;
    private Integer contractorId;
    private Car car;
    private String price;
    private String orderName;
    private String orderDescription;
    public OrderViewDto() {
    }

    public OrderViewDto(Reservation reservation) {
        if(reservation != null) {
            this.id = reservation.getId();
            this.orderDate = reservation.getReservationDate();
            this.contractorId = reservation.getCar().getId();
            this.car = reservation.getCar();
            this.price = String.format("%.2f", reservation.getPrice());
            this.orderName = reservation.getReservationName();
            this.orderDescription = reservation.getReservationDescription();
        } else {
            throw new IllegalArgumentException("Order object must not be null");
        }

    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Car getContractor() {
        return car;
    }
    public void setContractor(Car car) {
        this.car = car;
    }
    public Integer getContractorId() {
        return contractorId;
    }
    public String getOrderName() {
        return orderName;
    }
    public String getOrderDescription() {
        return orderDescription;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public String getPrice() {
        return price;
    }
    void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    void setContractorId(Integer contractorId) {
        this.contractorId = contractorId;
    }
    void setPrice(String price) {
        this.price = price;
    }
    void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }
}
