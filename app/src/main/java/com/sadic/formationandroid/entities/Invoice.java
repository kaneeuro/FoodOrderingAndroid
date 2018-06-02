package com.sadic.formationandroid.entities;

/**
 * Created by modykane on 29/10/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable{

    @SerializedName("invoiceId")
    @Expose
    private Long invoiceId;
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("dateUpdated")
    @Expose
    private String dateUpdated;
    @SerializedName("dateTill")
    @Expose
    private String dateTill;
    @SerializedName("datePayment")
    @Expose
    private String datePayment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("paymentId")
    @Expose
    private Integer paymentId;
    @SerializedName("paydFromDeposit")
    @Expose
    private Integer paydFromDeposit;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("unit")
    @Expose
    private Integer unit;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("periodFrom")
    @Expose
    private String periodFrom;
    @SerializedName("periodTo")
    @Expose
    private String periodTo;
    @SerializedName("customer")
    @Expose
    private Customer customer;

    public Invoice() {
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDateTill() {
        return dateTill;
    }

    public void setDateTill(String dateTill) {
        this.dateTill = dateTill;
    }

    public String getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(String datePayment) {
        this.datePayment = datePayment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaydFromDeposit() {
        return paydFromDeposit;
    }

    public void setPaydFromDeposit(Integer paydFromDeposit) {
        this.paydFromDeposit = paydFromDeposit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public String getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(String periodFrom) {
        this.periodFrom = periodFrom;
    }

    public String getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(String periodTo) {
        this.periodTo = periodTo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Invoice(String dateCreated, String dateUpdated, String dateTill, String datePayment, String status, String number, Integer paymentId, Integer paydFromDeposit, String description, Integer quantity, Integer unit, Double price, Integer tax, String periodFrom, String periodTo, Customer customer) {

        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateTill = dateTill;
        this.datePayment = datePayment;
        this.status = status;
        this.number = number;
        this.paymentId = paymentId;
        this.paydFromDeposit = paydFromDeposit;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.tax = tax;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        this.customer = customer;
    }
}
