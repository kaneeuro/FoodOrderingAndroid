package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Categorie {

    @SerializedName("idCategorie")
    @Expose
    private Long idCategorie;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("catalogue")
    @Expose
    private Catalogue catalogue;

    public Long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Long idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Categorie(String nom, Catalogue catalogue) {

        this.nom = nom;
        this.catalogue = catalogue;
    }

    public Categorie() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
