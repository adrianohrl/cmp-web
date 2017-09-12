/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events.services;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.model.events.Casualty;
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
public class CasualtyService extends Service<Casualty> {

    @Override
    public String getErrorMessage() {
        return "Nenhum sinistro foi cadastrado ainda!!!";
    }

    @Override
    protected List<Casualty> getElements(EntityManager em) {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        return casualtyDAO.findAll();
    }
    
    public List<Casualty> getCasualties() {
        return getElements();
    }
    
}
