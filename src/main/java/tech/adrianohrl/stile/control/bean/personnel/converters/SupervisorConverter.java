/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.personnel.Supervisor;
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
