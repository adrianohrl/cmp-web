/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.converters;


import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Variant;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("variantConverter")
public class VariantConverter extends Converter<Variant> {

    @Override
    public String getErrorMessage() {
        return "Variante inválida!!!";
    }

    @Override
    public String toString(Variant variant) {
        return variant.getName();
    }
    
}
