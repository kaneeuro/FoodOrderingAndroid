package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by modykane on 04/12/2017.
 */

public class Compte implements Serializable {

    @SerializedName("id_compte")
    @Expose
    private Long idCompte;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("id_employe")
    @Expose
    private Employe employe;

    public Compte() {
    }

    public Compte(String login, String password, String role, Employe employe) {

        this.login = login;
        this.password = password;
        this.role = role;
        this.employe = employe;
    }

    public Long getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Long idCompte) {
        this.idCompte = idCompte;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

}
