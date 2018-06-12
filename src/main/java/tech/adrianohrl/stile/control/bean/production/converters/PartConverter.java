package tech.adrianohrl.stile.control.bean.production.converters;


import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Part;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
