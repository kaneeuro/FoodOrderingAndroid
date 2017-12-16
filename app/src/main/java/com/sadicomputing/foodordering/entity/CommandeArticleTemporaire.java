package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 14/12/2017.
 */

public class CommandeArticleTemporaire {

    @SerializedName("idCommandeArticleTemporaire")
    @Expose
    private Long idCommandeArticleTemporaire;
    @SerializedName("compte")
    @Expose
    private Compte compte;
    @SerializedName("employe")
    @Expose
    private Employe employe;
    @SerializedName("article")
    @Expose
    private Article article;
    @SerializedName("table")
    @Expose
    private Tables table;
    @SerializedName("date")
    @Expose
    private String date;

    public CommandeArticleTemporaire(Employe employe, Article article) {
        this.employe = employe;
        this.article = article;
    }

    public Long getIdCommandeArticleTemporaire() {
        return idCommandeArticleTemporaire;
    }

    public void setIdCommandeArticleTemporaire(Long idCommandeArticleTemporaire) {
        this.idCommandeArticleTemporaire = idCommandeArticleTemporaire;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CommandeArticleTemporaire(Employe employe, Article article, Tables table) {
        this.employe = employe;
        this.article = article;
        this.table = table;
    }

    public CommandeArticleTemporaire(Compte compte, Employe employe, Article article, Tables table, String date) {

        this.compte = compte;
        this.employe = employe;
        this.article = article;
        this.table = table;
        this.date = date;
    }

    public CommandeArticleTemporaire() {

    }
}
