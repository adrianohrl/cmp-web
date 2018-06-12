package tech.adrianohrl.stile.control.bean.production.converters;

import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.production.Fabric;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
