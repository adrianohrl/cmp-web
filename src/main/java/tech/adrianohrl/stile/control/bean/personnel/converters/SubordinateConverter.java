package tech.adrianohrl.stile.control.bean.personnel.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
