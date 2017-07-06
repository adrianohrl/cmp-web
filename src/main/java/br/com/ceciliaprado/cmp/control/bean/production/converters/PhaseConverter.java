/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.model.production.Phase;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("phaseConverter")
public class PhaseConverter implements Converter {
    
    private final List<Phase> phases = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        EntityManager em = DataSource.createEntityManager();
        PhaseDAO phaseDAO = new PhaseDAO(em);
        phases.addAll(phaseDAO.findAll());
        em.close();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (Phase phase : phases) {        
            if (value.equals(phase.getName())) {
                return phase;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Erro na conversão", "Fase inválida!!!");
        throw new ConverterException(message);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        return obj != null ? ((Phase) obj).getName() : null;
    }
    
}
