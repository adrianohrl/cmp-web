package tech.adrianohrl.stile.control.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public abstract class Converter<T> implements javax.faces.convert.Converter, Serializable {

    @Override
    public T getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty())
        {
            return null;
        }
        for (UIComponent component : uic.getChildren()) {
            if (component instanceof UISelectItems) {
                UISelectItems selectItems = (UISelectItems) component;
                return getElement((List<T>) selectItems.getValue(), value);        
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                "Entre em contato com o desenvolvedor da aplicação", 
                "UIComponent does not has a UISelectItems as a child.");
        throw new ConverterException(message);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        return obj != null ? toString((T) obj) : null;
    }
    
    public abstract String getErrorMessage();
    
    public abstract String toString(T element);
    
    private T getElement(List<T> elements, String value) {        
        for (T element : elements) {
            if (toString(element).equals(value)) {
                return element;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Erro na conversão", getErrorMessage());
        throw new ConverterException(message);
    }
    
}
