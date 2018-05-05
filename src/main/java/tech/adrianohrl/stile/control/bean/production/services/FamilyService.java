/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.services;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.FamilyDAO;
import tech.adrianohrl.stile.model.production.Family;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@SessionScoped
public class FamilyService extends Service<Family> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma família foi cadastrada ainda!!!";
    }

    @Override
    protected List<Family> getElements(EntityManager em) {
        FamilyDAO familyDAO = new FamilyDAO(em);
        return familyDAO.findAll();
    }
    
    public List<Family> getFamilies() {
        return getElements();
    }
    
}
