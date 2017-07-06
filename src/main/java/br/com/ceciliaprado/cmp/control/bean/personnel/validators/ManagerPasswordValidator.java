/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import javax.faces.validator.FacesValidator;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesValidator("managerPasswordValidator")
public class ManagerPasswordValidator extends LoggableEmployeePasswordValidator<Manager> {
    
    @Override
    public void fill(EntityManager em) {
        ManagerDAO managerDAO = new ManagerDAO(em);
        loggableEmployees.addAll(managerDAO.findAll());
    }

    @Override
    protected String getGroupName() {
        return "gerentes";
    }
    
}
