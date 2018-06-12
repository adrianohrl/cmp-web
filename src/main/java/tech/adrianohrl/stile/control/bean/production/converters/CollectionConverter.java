package tech.adrianohrl.stile.control.bean.production.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import javax.faces.convert.FacesConverter;
import tech.adrianohrl.stile.model.production.Collection;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@FacesConverter("collectionConverter")
public class CollectionConverter extends Converter<Collection> {

    @Override
    public String getErrorMessage() {
        return "Coleção inválida!!!";
    }

    @Override
    public String toString(Collection collection) {
        return collection.getName();
    }
    
}
