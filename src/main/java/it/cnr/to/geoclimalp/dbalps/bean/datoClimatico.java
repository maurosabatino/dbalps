/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps.bean;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author daler
 */
public class datoClimatico {
	double dato;
	Calendar data;

    public double getDato() {
        return dato;
    }

    public void setDato(double dato) {
        this.dato = dato;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    
     public datoClimatico() {
        this.dato = -9999;
        this.data = new GregorianCalendar();
    }
        
}
