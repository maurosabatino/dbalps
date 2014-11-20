/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Mauro
 */
public class MyHttpSessionListener implements HttpSessionListener{
  public void sessionCreated(HttpSessionEvent event){
    event.getSession().setMaxInactiveInterval(120*60); //in seconds
  }
  public void sessionDestroyed(HttpSessionEvent event){}
}
