/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.personnel.services.SubordinateService;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SubordinateDAO;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class SubordinateRegisterBean implements Serializable {
       
    @ManagedProperty(value = "#{subordinateService}")
    private SubordinateService service;
    private final Subordinate subordinate = new Subordinate();    
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        EntityManager em = DataSource.createEntityManager();
        try {
            SubordinateDAO subordinateDAO = new SubordinateDAO(em);
            subordinateDAO.create(subordinate);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", subordinate + " foi cadastrado com sucesso!!!");
            next = "/index";
            update();
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", subordinate + " já foi cadastrado!!!");
        }
        em.close();
        context.addMessage(null, message);
        return next;
    }
    
    public void update() {
        service.update();
    }

    public void setService(SubordinateService service) {
        this.service = service;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }
    
}
