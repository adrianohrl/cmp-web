/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
public class CasualtyBean implements Serializable {
        
    private final EntityManager em = DataSource.createEntityManager();    
    private final Casualty casualty = new Casualty();
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            CasualtyDAO casualtyDAO = new CasualtyDAO(em);
            casualtyDAO.create(casualty);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", casualty + " foi cadastrado com sucesso!!!");
            next = "/index";
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", casualty + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }

    public Casualty getCasualty() {
        return casualty;
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }
    
}
