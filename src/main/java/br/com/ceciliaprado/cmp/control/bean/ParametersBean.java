/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean;

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

    public int getGrowlLife() {
        return growlLife;
    }

    public void setGrowlLife(int growlLife) {
        this.growlLife = growlLife;
    }
    
}
