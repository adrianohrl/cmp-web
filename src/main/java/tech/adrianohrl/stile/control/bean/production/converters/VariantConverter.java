package tech.adrianohrl.stile.control.bean.production.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Variant;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
