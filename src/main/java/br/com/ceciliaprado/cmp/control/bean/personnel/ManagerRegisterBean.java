/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class ManagerRegisterBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final Manager manager = new Manager();
    private final List<Supervisor> supervisors = new ArrayList<>();  
    private Supervisor[] selectedSupervisors;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisors.addAll(supervisorDAO.findAll());
        if (supervisors.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    "Atenção", "Nenhum supervisor foi cadastrado ainda!!!");
            context.addMessage(null, message);
        } else {
            Collections.sort(supervisors);
        }
    }
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            manager.setSupervisor(Arrays.asList(selectedSupervisors));
            ManagerDAO managerDAO = new ManagerDAO(em);
            managerDAO.create(manager);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", manager + " foi cadastrado com sucesso!!!");
            next = "/index";
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", manager + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public Manager getManager() {
        return manager;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public Supervisor[] getSelectedSupervisors() {
        return selectedSupervisors;
    }

    public void setSelectedSupervisors(Supervisor[] selectedSupervisors) {
        this.selectedSupervisors = selectedSupervisors;
    }
    
}
