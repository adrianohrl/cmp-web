package tech.adrianohrl.stile.control.bean.personnel.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.personnel.Manager;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@FacesConverter("managerConverter")
public class ManagerConverter extends Converter<Manager> {

    @Override
    public String getErrorMessage() {
        return "Gerente inválido!!!";
    }

    @Override
    public String toString(Manager manager) {
        return manager.getName();
    }
    
}
