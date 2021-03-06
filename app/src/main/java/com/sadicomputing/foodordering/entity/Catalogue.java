package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Catalogue {

    @SerializedName("id_catalogue")
    @Expose
    private Long idCatalogue;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("id_resto")
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
