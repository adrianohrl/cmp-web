/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("supervisorConverter")
public class SupervisorConverter extends Converter<Supervisor> {

    @Override
    public String getErrorMessage() {
        return "Supervisor inválido!!!";
    }

    @Override
    public String toString(Supervisor supervisor) {
        return supervisor.getName();
    }
    
}
