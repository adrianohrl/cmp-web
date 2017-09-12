/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.personnel.SupervisorService;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("supervisorConverter")
public class SupervisorConverter extends Converter<Supervisor> {
    
    @ManagedProperty(value = "#{supervisorService}")
    private SupervisorService service;
    
    @Override
    @PostConstruct
    public void init() {
        addAll(service.getSupervisors());
    }

    @Override
    public String getErrorMessage() {
        return "Supervisor inválido!!!";
    }

    @Override
    public String toString(Supervisor supervisor) {
        return supervisor.getName();
    }

    public void setService(SupervisorService service) {
        this.service = service;
    }
    
}
