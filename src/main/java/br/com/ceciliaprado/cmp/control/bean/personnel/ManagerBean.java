/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class ManagerBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private Manager manager = new Manager();
    private final List<Supervisor> supervisors = new ArrayList<>();  
    private Supervisor[] selectedSupervisors;
    private final List<Manager> managers = new ArrayList<>();
    private String managerLogin;
    private String managerPassword;

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
        ManagerDAO managerDAO = new ManagerDAO(em);
        managers.addAll(managerDAO.findAll());
        if (managers.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no login", "Nenhum gerente foi cadastrado ainda!!!");
            context.addMessage(null, message);
        } else {
            Collections.sort(managers);
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
    
    public String alter() {
        if (managerPassword.isEmpty())
        {
            return "";
        }
        manager.setPassword(managerPassword);
        ManagerDAO managerDAO = new ManagerDAO(em);
        managerDAO.update(manager);
        return "/index";
    }
    
    public void selectSupervisor() {
        manager = null;
        if (managerLogin == null || managerLogin.isEmpty()) {
            return;
        }
        for (Manager m : managers) {
            if (managerLogin.equals(m.getLogin())) {
                manager = m;
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("loggedEmployee", manager);
            }
        }
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

    public String getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(String managerLogin) {
        this.managerLogin = managerLogin;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }
    
}
