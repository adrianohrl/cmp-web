/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.production.ModelService;
import br.com.ceciliaprado.cmp.model.production.Model;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("modelConverter")
public class ModelConverter extends Converter<Model> {
    
    @ManagedProperty(value = "#{modelService}")
    private ModelService service;
    
    @Override
    @PostConstruct
    public void init() {
        addAll(service.getModels());
    }

    @Override
    public String getErrorMessage() {
        return "Modelo inválido!!!";
    }

    @Override
    public String toString(Model model) {
        return model.getReference();
    }

    public void setService(ModelService service) {
        this.service = service;
    }
    
}
