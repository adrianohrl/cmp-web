package tech.adrianohrl.stile.control.bean.production.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.production.Phase;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
