/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("subordinateConverter")
public class SubordinateConverter extends Converter<Subordinate> {

    @Override
    public String getErrorMessage() {
        return "Subordinado inválido!!!";
    }

    @Override
    public String toString(Subordinate subordinate) {
        return subordinate.getName();
    }
    
}
