/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.io.Serializable;
import java.util.Arrays;
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
public class SupervisorRegisterBean implements Serializable {
       
    @ManagedProperty(value = "#{supervisorService}")
    private SupervisorService supervisorService;
    private final EntityManager em = DataSource.createEntityManager();
    private final Supervisor supervisor = new Supervisor();
    private Subordinate[] selectedSubordinates;
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            supervisor.setSubordinates(Arrays.asList(selectedSubordinates));
            SupervisorDAO supervisorDAO = new SupervisorDAO(em);
            supervisorDAO.create(supervisor);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", supervisor + " foi cadastrado com sucesso!!!");
            next = "/index";
            supervisorService.update(supervisor);
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", supervisor + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public void setSupervisorService(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    public Supervisor getSupervisor() {
        return supervisor;  
    }

    public Subordinate[] getSelectedSubordinates() {
        return selectedSubordinates;
    }

    public void setSelectedSubordinates(Subordinate[] selectedSubordinates) {
        this.selectedSubordinates = selectedSubordinates;
    }
    
}
