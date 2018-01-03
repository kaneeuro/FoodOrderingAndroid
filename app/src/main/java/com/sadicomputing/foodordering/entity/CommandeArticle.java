package com.sadicomputing.foodordering.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class CommandeArticle implements Parcelable{
    private String name;

    @SerializedName("idCommandeArticle")
    @Expose
    private Long idCommandeArticle;
    @SerializedName("prixUnitaire")
    @Expose
    private Double prixUnitaire;
    @SerializedName("prixTotal")
    @Expose
    private Double prixTotal;
    @SerializedName("quantite")
    @Expose
    private Long quantite;
    @SerializedName("article")
    @Expose
    private Article article;
    @SerializedName("commande")
    @Expose
    private Commande commande;

    public Long getIdCommandeArticle() {
        return idCommandeArticle;
    }

    public void setIdCommandeArticle(Long idCommandeArticle) {
        this.idCommandeArticle = idCommandeArticle;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public CommandeArticle(Double prixUnitaire, Double prixTotal, Long quantite, Article article, Commande commande) {

        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
        this.quantite = quantite;
        this.article = article;
        this.commande = commande;
    }

    public CommandeArticle(String name, Double prixUnitaire, Double prixTotal, Long quantite, Article article, Commande commande) {
        this.name = name;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
        this.quantite = quantite;
        this.article = article;
        this.commande = commande;
    }

    public CommandeArticle() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }

    protected CommandeArticle(Parcel in) {
        name = in.readString();
    }

    public static final Creator<CommandeArticle> CREATOR = new Creator<CommandeArticle>() {
        @Override
        public CommandeArticle createFromParcel(Parcel in) {
            return new CommandeArticle(in);
        }

        @Override
        public CommandeArticle[] newArray(int size) {
            return new CommandeArticle[size];
        }
    };

    public String getName() {
        return name;
    }
}
