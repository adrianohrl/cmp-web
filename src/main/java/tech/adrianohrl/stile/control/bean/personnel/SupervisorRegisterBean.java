/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.personnel.services.SupervisorService;
import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import java.io.Serializable;
import java.util.Arrays;
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
    private SupervisorService service;
    private final Supervisor supervisor = new Supervisor();
    private Subordinate[] selectedSubordinates;
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        EntityManager em = DataSource.createEntityManager();
        try {
            supervisor.setSubordinates(Arrays.asList(selectedSubordinates));
            SupervisorDAO supervisorDAO = new SupervisorDAO(em);
            supervisorDAO.create(supervisor);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", supervisor + " foi cadastrado com sucesso!!!");
            next = "/index";
            update();
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", supervisor + " já foi cadastrado!!!");
        }
        em.close();
        context.addMessage(null, message);
        return next;
    }
    
    public void update() {
        service.update();
    }

    public void setService(SupervisorService service) {
        this.service = service;
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
