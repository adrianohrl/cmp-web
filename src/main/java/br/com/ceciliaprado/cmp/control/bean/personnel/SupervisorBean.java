/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class SupervisorBean implements Serializable {
       
    private final EntityManager em = DataSource.createEntityManager();
    private Supervisor supervisor = new Supervisor();
    private final List<Subordinate> subordinates = new ArrayList<>();
    private Subordinate[] selectedSubordinates;
    private final List<Supervisor> supervisors = new ArrayList<>();
    private String supervisorLogin;
    private String supervisorPassword;
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        subordinates.addAll(subordinateDAO.findAll());
        if (subordinates.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Nenhum subordinado foi cadastrado ainda!!!");
            context.addMessage(null, message);
        } else {
            Collections.sort(subordinates);
        }
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisors.addAll(supervisorDAO.findAll());
        if (supervisors.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no login", "Nenhum supervisor foi cadastrado ainda!!!");
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
    
    public String alter() {
        if (supervisorPassword.isEmpty())
        {
            return "";
        }
        supervisor.setPassword(supervisorPassword);
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisorDAO.update(supervisor);
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/index";
    }
    
    public void selectSupervisor() {
        supervisor = null;
        if (supervisorLogin == null || supervisorLogin.isEmpty()) {
            return;
        }
        for (Supervisor s : supervisors) {
            if (supervisorLogin.equals(s.getLogin())) {
                supervisor = s;
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("loggedEmployee", supervisor);
            }
        }
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

    public String getSupervisorLogin() {
        return supervisorLogin;
    }

    public void setSupervisorLogin(String supervisorLogin) {
        this.supervisorLogin = supervisorLogin;
    }

    public String getSupervisorPassword() {
        return supervisorPassword;
    }

    public void setSupervisorPassword(String supervisorPassword) {
        this.supervisorPassword = supervisorPassword;
    }
    
}
