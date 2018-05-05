/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.converters;

import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.production.Fabric;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("fabricConverter")
public class FabricConverter extends Converter<Fabric> {

    @Override
    public String getErrorMessage() {
        return "Tecido inválido!!!";
    }

    @Override
    public String toString(Fabric fabric) {
        return fabric.getName();
    }
    
}
