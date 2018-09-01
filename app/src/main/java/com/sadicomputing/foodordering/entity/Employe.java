package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by modykane on 04/12/2017.
 */

public class Employe implements Serializable {

    @SerializedName("id_employe")
    @Expose
    private Long idEmploye;
    @SerializedName("matricule")
    @Expose
    private String matricule;
    @SerializedName("prenom")
    @Expose
    private String prenom;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fonction")
    @Expose
    private String fonction;
    @SerializedName("id_resto")
    @Expose
    private Restaurant restaurant;
    //private Cuisine cuisine;

    public Employe() {
    }

    public Employe(String matricule, String prenom, String nom, String telephone, String email, String fonction) {
        this.matricule = matricule;
        this.prenom = prenom;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
        this.fonction = fonction;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
