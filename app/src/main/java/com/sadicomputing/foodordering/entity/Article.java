package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Article {
    @SerializedName("idArticle")
    @Expose
    private Long idArticle;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("prix")
    @Expose
    private Double prix;
    @SerializedName("categorie")
    @Expose
    private Categorie categorie;

    public Long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Article(String designation, String imageUrl, Double prix, Categorie categorie) {
        this.designation = designation;
        this.imageUrl = imageUrl;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Article(String designation, Double prix, Categorie categorie) {

        this.designation = designation;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Article() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
