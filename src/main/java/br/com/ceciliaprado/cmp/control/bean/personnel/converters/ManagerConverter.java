/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.personnel.ManagerService;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("managerConverter")
public class ManagerConverter extends Converter<Manager> {
    
    @ManagedProperty(value = "#{managerService}")
    private ManagerService service;
    
    @Override
    @PostConstruct
    public void init() {
        addAll(service.getManagers());
    }

    @Override
    public String getErrorMessage() {
        return "Supervisor inválido!!!";
    }

    @Override
    public String toString(Manager manager) {
        return manager.getName();
    }

    public void setService(ManagerService service) {
        this.service = service;
    }
    
}
