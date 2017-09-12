/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.services;

import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ManagerService extends PersonnelService<Manager> {

    @Override
    public String getErrorMessage() {
        return "Nenhum gerente foi cadastrado ainda!!!";
    }

    @Override
    protected List<Manager> getElements(EntityManager em) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        return managerDAO.findAll();
    }
    
    public List<Manager> getManagers() {
        return getElements();
    }
    
}
