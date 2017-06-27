/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.model.production.Model;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("modelConverter")
public class ModelConverter implements Converter {
    
    private final List<Model> models = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        EntityManager em = DataSource.createEntityManager();
        ModelDAO modelDAO = new ModelDAO(em);
        models.addAll(modelDAO.findAll());
        em.close();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (Model model : models) {
            if (value.equals(model.getName())) {
                return model;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Erro na conversão", "Modelo inválido!!!");
        throw new ConverterException(message);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        return obj != null ? ((Model) obj).getName() : null;
    }
    
}
