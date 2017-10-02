/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.services;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.model.production.Phase;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@SessionScoped
public class PhaseService extends Service<Phase> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma fase foi cadastrada ainda!!!";
    }

    @Override
    protected List<Phase> getElements(EntityManager em) {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        return phaseDAO.findAll();
    }
    
    public List<Phase> getPhases() {
        return getElements();
    }
    
}
