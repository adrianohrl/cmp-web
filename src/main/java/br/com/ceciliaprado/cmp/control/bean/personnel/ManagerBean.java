/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
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
public class ManagerBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final ManagerDAO managerDAO = new ManagerDAO(em);
    private Manager manager = new Manager();
    private String message = null;
    private String password;
    
    public String insert() {
        if (!password.equals(manager.getPassword()))
        {
            message = "As senhas devem ser iguais!!!";
            return "";
        }
        try {
            managerDAO.create(manager);
            message = null;
        } catch (EntityExistsException e) {
            message = manager + " já foi cadastrado!!!";
            return "";
        }
        return "/index";
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
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
