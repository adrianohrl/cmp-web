/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import javax.faces.validator.FacesValidator;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesValidator("supervisorLoginValidator")
public class SupervisorLoginValidator extends LoggableEmployeeLoginValidator<Supervisor> {
    
    @Override
    public void fill(EntityManager em) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        loggableEmployees.addAll(supervisorDAO.findAll());
    }

    @Override
    protected String getGroupName() {
        return "supervisores";
    }
    
}
