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
import javax.annotation.PreDestroy;
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
    private SubordinateService subordinateService;
    private final EntityManager em = DataSource.createEntityManager();
    private final Subordinate subordinate = new Subordinate();    
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            SubordinateDAO subordinateDAO = new SubordinateDAO(em);
            subordinateDAO.create(subordinate);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", subordinate + " foi cadastrado com sucesso!!!");
            next = "/index";
            subordinateService.update(subordinate);
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", subordinate + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public void setSubordinateService(SubordinateService subordinateService) {
        this.subordinateService = subordinateService;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }
    
}
