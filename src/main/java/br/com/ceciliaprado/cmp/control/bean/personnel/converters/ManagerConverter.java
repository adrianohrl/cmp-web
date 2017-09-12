/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.ManagerDAO;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.List;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("managerConverter")
public class ManagerConverter extends Converter<Manager> {

    @Override
    protected List<Manager> getElements() {
        EntityManager em = DataSource.createEntityManager();
        ManagerDAO managerDAO = new ManagerDAO(em);
        List<Manager> managers = managerDAO.findAll();
        em.close();
        return managers;
    }

    @Override
    public String getErrorMessage() {
        return "Gerente inválido!!!";
    }

    @Override
    public String toString(Manager manager) {
        return manager.getName();
    }
    
}
