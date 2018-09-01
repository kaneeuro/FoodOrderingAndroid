package com.sadicomputing.foodordering.entityspring;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Restaurant {

    @SerializedName("idResto")
    @Expose
    private Long idResto;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("adresse")
    @Expose
    private String adresse;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("email")
    @Expose
    private String email;

    public Long getIdResto() {
        return idResto;
    }

    public void setIdResto(Long idResto) {
        this.idResto = idResto;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Restaurant(String code, String designation, String adresse, String telephone, String email) {

        this.code = code;
        this.designation = designation;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    public Restaurant() {

    }
}
