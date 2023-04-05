package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class OrderDto extends RepresentationModel<OrderDto> {
    private long id;
    private Double cost;
    private LocalDateTime purchaseTime;
    private GiftCertificateDto certificate;
    private UserDto user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public GiftCertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificateDto certificate) {
        this.certificate = certificate;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
