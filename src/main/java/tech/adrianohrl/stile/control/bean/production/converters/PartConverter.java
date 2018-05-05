/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.converters;


import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Part;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("partConverter")
public class PartConverter extends Converter<Part> {

    @Override
    public String getErrorMessage() {
        return "Parte inválida!!!";
    }

    @Override
    public String toString(Part part) {
        return part.getName();
    }
    
}
