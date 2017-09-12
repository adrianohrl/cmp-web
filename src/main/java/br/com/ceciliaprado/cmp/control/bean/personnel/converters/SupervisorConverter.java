/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.util.List;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("supervisorConverter")
public class SupervisorConverter extends Converter<Supervisor> {

    @Override
    protected List<Supervisor> getElements() {
        EntityManager em = DataSource.createEntityManager();
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        List<Supervisor> supervisors = supervisorDAO.findAll();
        em.close();
        return supervisors;
    }

    @Override
    public String getErrorMessage() {
        return "Supervisor inválido!!!";
    }

    @Override
    public String toString(Supervisor supervisor) {
        return supervisor.getName();
    }
    
}
