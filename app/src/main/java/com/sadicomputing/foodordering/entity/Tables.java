package com.sadicomputing.foodordering.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by modykane on 09/12/2017.
 */

public class Tables {

    @SerializedName("id_table")
    @Expose
    private Long idTable;
    @SerializedName("numero")
    @Expose
    private Integer numero;
    @SerializedName("nombre_place")
    @Expose
    private Integer nombrePlace;

    public Long getIdTable() {
        return idTable;
    }

    public void setIdTable(Long idTable) {
        this.idTable = idTable;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNombrePlace() {
        return nombrePlace;
    }

    public void setNombrePlace(Integer nombrePlace) {
        this.nombrePlace = nombrePlace;
    }

    public Tables() {

    }

    public Tables(Integer numero) {

        this.numero = numero;
    }

    public Tables(Integer numero, Integer nombrePlace) {

        this.numero = numero;
        this.nombrePlace = nombrePlace;
    }
}
