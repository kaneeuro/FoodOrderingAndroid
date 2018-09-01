package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Cuisine {

    @SerializedName("id_cuisine")
    @Expose
    private Long idCuisine;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id_resto")
    @Expose
    private Restaurant restaurant;

    public Long getIdCuisine() {
        return idCuisine;
    }

    public void setIdCuisine(Long idCuisine) {
        this.idCuisine = idCuisine;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Cuisine(String nom, String type, Restaurant restaurant) {

        this.nom = nom;
        this.type = type;
        this.restaurant = restaurant;
    }

    public Cuisine() {

    }
}
