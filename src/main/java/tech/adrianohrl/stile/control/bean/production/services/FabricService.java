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
import tech.adrianohrl.stile.control.dao.production.FabricDAO;
import tech.adrianohrl.stile.model.production.Fabric;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@SessionScoped
public class FabricService extends Service<Fabric> {

    @Override
    public String getErrorMessage() {
        return "Nenhum tecido foi cadastrado ainda!!!";
    }

    @Override
    protected List<Fabric> getElements(EntityManager em) {
        FabricDAO fabricDAO = new FabricDAO(em);
        return fabricDAO.findAll();
    }
    
    public List<Fabric> getFabrics() {
        return getElements();
    }
    
}
