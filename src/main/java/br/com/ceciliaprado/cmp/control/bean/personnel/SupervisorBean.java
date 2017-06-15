/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class SupervisorBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final SupervisorDAO supervisorDAO = new SupervisorDAO(em);
    private Supervisor supervisor = new Supervisor();
    private String message = null;
    private String password;
    
    public String insert() {
        if (!password.equals(supervisor.getPassword()))
        {
            message = "As senhas devem ser iguais!!!";
            return "";
        }
        try {
            supervisorDAO.create(supervisor);
            message = null;
        } catch (EntityExistsException e) {
            message = supervisor + " já foi cadastrado!!!";
            return "";
        }
        return "/index";
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
