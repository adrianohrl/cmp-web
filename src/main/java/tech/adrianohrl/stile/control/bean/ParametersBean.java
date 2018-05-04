/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@SessionScoped
public class ParametersBean implements Serializable {
    
    private int growlLife = 5000;
    private String theme = "blitzer";
    private long timeout = 30 * 60 * 1000;

    public int getGrowlLife() {
        return growlLife;
    }

    public void setGrowlLife(int growlLife) {
        this.growlLife = growlLife;
    } 

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    
}
