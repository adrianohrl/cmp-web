package tech.adrianohrl.stile.control.bean.production.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Chart;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
