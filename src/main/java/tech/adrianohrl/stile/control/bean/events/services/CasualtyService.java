/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.events.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.events.CasualtyDAO;
import tech.adrianohrl.stile.model.events.Casualty;
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
