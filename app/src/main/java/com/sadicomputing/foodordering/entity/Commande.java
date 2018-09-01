package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by modykane on 09/12/2017.
 */

public class Commande extends ExpandableGroup<CommandeArticle>{

    @SerializedName("id_commande")
    @Expose
    private Long idCommande;
    @SerializedName("numero")
    @Expose
    private Long numero;
    @SerializedName("statut")
    @Expose
    private Integer statut;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id_serveur")
    @Expose
    private Employe serveur;
    @SerializedName("idserveur")
    @Expose
    public Long serveurid;
    @SerializedName("id_cuisinier")
    @Expose
    private Employe cuisinier;
    @SerializedName("id_comptable")
    @Expose
    private Employe comptable;
    @SerializedName("idcomptable")
    @Expose
    private Long comptableid;
    @SerializedName("id_table")
    @Expose
    private Tables table;
    @SerializedName("table")
    @Expose
    public Long tableid;

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Employe getServeur() {
        return serveur;
    }

    public void setServeur(Employe serveur) {
        this.serveur = serveur;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Commande(String title, List<CommandeArticle> commandeArticles, Long numero, Integer statut, String date, Long serveur, Long table) {
        super(title, commandeArticles);
        this.numero = numero;
        this.statut = statut;
        this.date = date;
        this.serveurid = serveur;
        this.tableid = table;
    }

    public Commande(String title, List<CommandeArticle> commandeArticles, Long numero, Integer statut, String date, Employe serveur, Tables table) {
        super(title, commandeArticles);
        this.numero = numero;
        this.statut = statut;
        this.date = date;
        this.serveur = serveur;
        this.table = table;
    }

    public Commande(String title, List<CommandeArticle> commandeArticles){
        super(title, commandeArticles);
    }

    public Employe getCuisinier() {
        return cuisinier;
    }

    public void setCuisinier(Employe cuisinier) {
        this.cuisinier = cuisinier;
    }

    public Employe getComptable() {
        return comptable;
    }

    public void setComptable(Employe comptable) {
        this.comptable = comptable;
    }

    public Long getComptableid() {
        return comptableid;
    }

    public void setComptableid(Long comptableid) {
        this.comptableid = comptableid;
    }
}
