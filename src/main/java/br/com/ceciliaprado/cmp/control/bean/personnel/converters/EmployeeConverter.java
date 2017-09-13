/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("employeeConverter")
public class EmployeeConverter extends Converter<Employee> {

    @Override
    public String getErrorMessage() {
        return "Funcionário inválido!!!";
    }

    @Override
    public String toString(Employee employee) {
        return employee.getName();
    }
    
}
