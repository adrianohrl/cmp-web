/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.converters;


import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Family;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("familyConverter")
public class FamilyConverter extends Converter<Family> {

    @Override
    public String getErrorMessage() {
        return "Família inválida!!!";
    }

    @Override
    public String toString(Family family) {
        return family.getName();
    }
    
}
