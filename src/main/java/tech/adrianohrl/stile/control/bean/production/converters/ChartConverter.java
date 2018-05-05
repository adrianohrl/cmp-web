/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Chart;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("chartConverter")
public class ChartConverter extends Converter<Chart> {

    @Override
    public String getErrorMessage() {
        return "Grade inválida!!!";
    }

    @Override
    public String toString(Chart chart) {
        return chart.getAbbreviation();
    }
    
}
