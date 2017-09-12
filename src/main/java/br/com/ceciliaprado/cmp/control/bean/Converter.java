/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public abstract class Converter<T> implements Serializable, javax.faces.convert.Converter {
    
    private final List<T> elements = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        elements.addAll(getElements());
    }

    @Override
    public T getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (T element : elements) {
            if (element.equals(value)) {
                return element;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Erro na conversão", getErrorMessage());
        throw new ConverterException(message);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        return obj != null ? toString((T) obj) : null;
    }
    
    protected abstract List<T> getElements();
    
    public abstract String getErrorMessage();
    
    public abstract String toString(T element);
    
}
