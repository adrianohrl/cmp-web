package tech.adrianohrl.stile.control.bean.events.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.events.Casualty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
