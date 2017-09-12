/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.model.production.Model;
import java.util.List;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("modelConverter")
public class ModelConverter extends Converter<Model> {

    @Override
    protected List<Model> getElements() {
        EntityManager em = DataSource.createEntityManager();
        ModelDAO modelDAO = new ModelDAO(em);
        List<Model> models = modelDAO.findAll();
        em.close();
        return models;
    }

    @Override
    public String getErrorMessage() {
        return "Modelo inválido!!!";
    }

    @Override
    public String toString(Model model) {
        return model.getReference();
    }
    
}
