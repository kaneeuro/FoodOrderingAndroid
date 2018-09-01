package com.sadicomputing.foodordering.entityspring;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Catalogue {

    @SerializedName("idCatalogue")
    @Expose
    private Long idCatalogue;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("restaurant")
    @Expose
    private Restaurant restaurant;

    public Long getIdCatalogue() {
        return idCatalogue;
    }

    public void setIdCatalogue(Long idCatalogue) {
        this.idCatalogue = idCatalogue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Catalogue(String nom, Restaurant restaurant) {

        this.nom = nom;
        this.restaurant = restaurant;
    }

    public Catalogue() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
