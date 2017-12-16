package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class CommandeArticle {

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

    public CommandeArticle() {

    }
}
