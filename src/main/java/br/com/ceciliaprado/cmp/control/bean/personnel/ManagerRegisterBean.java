/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.personnel.services.ManagerService;
import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
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
public class ManagerRegisterBean implements Serializable {
    
    @ManagedProperty(value = "#{managerService}")
    private ManagerService service;
    private final Manager manager = new Manager(); 
    private Supervisor[] selectedSupervisors;
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        EntityManager em = DataSource.createEntityManager();
        try {
            manager.setSupervisor(Arrays.asList(selectedSupervisors));
            ManagerDAO managerDAO = new ManagerDAO(em);
            managerDAO.create(manager);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", manager + " foi cadastrado com sucesso!!!");
            next = "/index";
            update();
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", manager + " já foi cadastrado!!!");
        }
        em.close();
        context.addMessage(null, message);
        return next;
    }
    
    public void update() {
        service.update();
    }

    public void setService(ManagerService service) {
        this.service = service;
    }

    public Manager getManager() {
        return manager;
    }

    public Supervisor[] getSelectedSupervisors() {
        return selectedSupervisors;
    }

    public void setSelectedSupervisors(Supervisor[] selectedSupervisors) {
        this.selectedSupervisors = selectedSupervisors;
    }
    
}
