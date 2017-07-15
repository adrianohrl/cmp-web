/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SubordinateDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
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
public class SupervisorBean implements Serializable {
       
    private final EntityManager em = DataSource.createEntityManager();
    private final Supervisor supervisor = new Supervisor();
    private final List<Subordinate> subordinates = new ArrayList<>();
    private Subordinate[] selectedSubordinates;
    
    @PostConstruct
    public void init() {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        subordinates.addAll(subordinateDAO.findAll());
        if (subordinates.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Nenhum subordinado foi cadastrado ainda!!!");
            context.addMessage(null, message);
        } else {
            Collections.sort(subordinates);
        }
    }
    
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

    public Supervisor getSupervisor() {
        return supervisor;  
    }

    public List<Subordinate> getSubordinates() {
        return subordinates;
    }

    public Subordinate[] getSelectedSubordinates() {
        return selectedSubordinates;
    }

    public void setSelectedSubordinates(Subordinate[] selectedSubordinates) {
        this.selectedSubordinates = selectedSubordinates;
    }
    
}
