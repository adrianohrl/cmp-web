/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.events.services.CasualtyService;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.model.events.Casualty;
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
public class CasualtyRegisterBean implements Serializable {
        
    @ManagedProperty(value = "#{casualtyService}")
    private CasualtyService service;
    private final Casualty casualty = new Casualty();
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        EntityManager em = DataSource.createEntityManager();
        try {
            CasualtyDAO casualtyDAO = new CasualtyDAO(em);
            casualtyDAO.create(casualty);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", casualty + " foi cadastrado com sucesso!!!");
            next = "/index";
            update();
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", casualty + " já foi cadastrado!!!");
        }
        em.close();
        context.addMessage(null, message);
        return next;
    }
    
    public void update() {
        service.update();
    }

    public void setService(CasualtyService service) {
        this.service = service;
    }

    public Casualty getCasualty() {
        return casualty;
    }
    
}
