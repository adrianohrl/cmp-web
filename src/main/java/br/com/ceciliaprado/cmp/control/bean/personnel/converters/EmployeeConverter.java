/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.personnel.EmployeeService;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("employeeConverter")
public class EmployeeConverter extends Converter<Employee> {
    
    @ManagedProperty(value = "#{employeeService}")
    private EmployeeService service;
    
    @Override
    @PostConstruct
    public void init() {
        addAll(service.getEmployees());
    }

    @Override
    public String getErrorMessage() {
        return "Funcionário inválido!!!";
    }

    @Override
    public String toString(Employee employee) {
        return employee.getName();
    }

    public void setService(EmployeeService service) {
        this.service = service;
    }
    
}
