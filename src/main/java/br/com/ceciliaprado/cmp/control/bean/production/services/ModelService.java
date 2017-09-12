/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.services;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.model.production.Model;
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
