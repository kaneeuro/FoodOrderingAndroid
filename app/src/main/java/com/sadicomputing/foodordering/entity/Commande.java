package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Commande {

    @SerializedName("idCommande")
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
    @SerializedName("employe")
    @Expose
    private Employe employe;
    @SerializedName("table")
    @Expose
    private Tables table;

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

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Commande(Long numero, Integer statut, String date, Employe employe, Tables table) {

        this.numero = numero;
        this.statut = statut;
        this.date = date;
        this.employe = employe;
        this.table = table;
    }

    public Commande() {

    }
}
