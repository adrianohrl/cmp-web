/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
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
@FacesConverter("supervisorConverter")
public class SupervisorConverter implements Converter {
    
    private final List<Supervisor> supervisors = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        EntityManager em = DataSource.createEntityManager();
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisors.addAll(supervisorDAO.findAll());
        em.close();
    }
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (Supervisor supervisor : supervisors) {
            if (value.equals(supervisor.getName())) {
                return supervisor;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Erro na conversão", "Supervisor inválido!!!");
        throw new ConverterException(message);
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        return obj != null ? ((Supervisor) obj).getName() : null;
    }   
    
}
