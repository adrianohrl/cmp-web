/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.model.production.Phase;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("phaseConverter")
public class PhaseConverter extends Converter<Phase> {

    @Override
    public String getErrorMessage() {
        return "Fase inválida!!!";
    }

    @Override
    public String toString(Phase phase) {
        return phase.getName();
    }
    
}
