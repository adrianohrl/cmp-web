/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.personnel.services.PersonnelService;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ApplicationScoped
public class SupervisorService extends PersonnelService<Supervisor> {

    @Override
    public String getErrorMessage() {
        return "Nenhum supervisor foi cadastrado ainda!!!";
    }

    @Override
    protected List<Supervisor> getElements(EntityManager em) {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        return supervisorDAO.findAll();
    }
    
    public List<Supervisor> getSupervisors() {
        return getElements();
    }
    
}
