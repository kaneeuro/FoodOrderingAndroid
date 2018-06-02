package com.sadic.formationandroid.entities;

/**
 * Created by modykane on 29/10/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Customer {

    @SerializedName("customerId")
    @Expose
    private Long customerId;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("solde")
    @Expose
    private Double solde;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("partnerId")
    @Expose
    private Integer partnerId;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("street1")
    @Expose
    private String street1;
    @SerializedName("street2")
    @Expose
    private String street2;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("dateAdd")
    @Expose
    private String dateAdd;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Customer() {
    }

    public Customer(String login, Double solde, String status, Integer partnerId, Integer locationId, String name, String email, String phone, String category, String street1, String street2, String zipCode, String city, String dateAdd) {
        this.login = login;
        this.solde = solde;
        this.status = status;
        this.partnerId = partnerId;
        this.locationId = locationId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.category = category;
        this.street1 = street1;
        this.street2 = street2;
        this.zipCode = zipCode;
        this.city = city;
        this.dateAdd = dateAdd;
    }
}