/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.services;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author adrianohrl
 * @param <E>
 */
public abstract class PersonnelService<E extends Employee> extends Service<E> {
    
    @ManagedProperty(value = "#{employeeService}")
    private EmployeeService service;
    
    @Override
    public void update() {
        super.update();
        service.update();
    }
    
    @Override
    public void update(E employee) {
        super.update(employee);
        service.update(employee);
    }

    public void setService(EmployeeService service) {
        this.service = service;
    }
    
}
