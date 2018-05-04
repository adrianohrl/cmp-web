/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.model.production.Model;
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
public class ModelService extends Service<Model> {

    @Override
    public String getErrorMessage() {
        return "Nenhum modelo foi cadastrado ainda!!!";
    }

    @Override
    protected List<Model> getElements(EntityManager em) {
        ModelDAO modelDAO = new ModelDAO(em);
        return modelDAO.findAll();
    }
    
    public List<Model> getModels() {
        return getElements();
    }
    
}
