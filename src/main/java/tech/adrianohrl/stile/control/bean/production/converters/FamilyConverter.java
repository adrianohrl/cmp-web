package tech.adrianohrl.stile.control.bean.production.converters;


import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Family;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
