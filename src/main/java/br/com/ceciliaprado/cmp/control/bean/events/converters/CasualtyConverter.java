/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.events.CasualtyService;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("casualtyConverter")
public class CasualtyConverter extends Converter<Casualty> {
   
    @ManagedProperty(value = "#{casualtyService}")
    private CasualtyService service;
    
    @Override
    @PostConstruct
    public void init() {
        addAll(service.getCasualties());
    }

    @Override
    public String getErrorMessage() {
        return "Sinistro inválido!!!";
    }

    @Override
    public String toString(Casualty casualty) {
        return casualty.getName();
    }

    public void setService(CasualtyService service) {
        this.service = service;
    }
    
}
