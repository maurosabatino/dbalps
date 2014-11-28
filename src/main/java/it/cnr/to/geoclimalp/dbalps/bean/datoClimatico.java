/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps.bean;

import java.sql.Timestamp;

/**
 *
 * @author daler
 */
public class datoClimatico {
	double dato;
	Timestamp data;

    public double getDato() {
        return dato;
    }

    public void setDato(double dato) {
        this.dato = dato;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public datoClimatico(double dato, Timestamp data) {
        this.dato = dato;
        this.data = data;
    }
     public datoClimatico() {
        this.dato = -9999;
        this.data = new Timestamp(0);
    }
        
}
