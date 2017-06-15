/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SubordinateDAO;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class SubordinateBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final SubordinateDAO subordinateDAO = new SubordinateDAO(em);
    private Subordinate subordinate = new Subordinate();
    private String message = null;
    
    public String insert() {
        try {
            subordinateDAO.create(subordinate);
            message = null;
        } catch (EntityExistsException e) {
            message = subordinate + " já foi cadastrado!!!";
            return "";
        }
        return "/index";
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Subordinate subordinate) {
        this.subordinate = subordinate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
