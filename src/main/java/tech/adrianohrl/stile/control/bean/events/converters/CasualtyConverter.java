/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.events.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.events.Casualty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("casualtyConverter")
public class CasualtyConverter extends Converter<Casualty> {

    @Override
    public String getErrorMessage() {
        return "Sinistro inválido!!!";
    }

    @Override
    public String toString(Casualty casualty) {
        return casualty.getName();
    }
    
}
