/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.validators;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.LoggableDAO;
import br.com.ceciliaprado.cmp.model.personnel.Loggable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <L>
 */
public abstract class LoggableEmployeeValidator<L extends Loggable> implements Iterable<Loggable>, Validator {
    
    protected final List<Loggable> loggableEmployees = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        EntityManager em = DataSource.createEntityManager();
        fill(em);
        if (loggableEmployees.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade na validação", "Nenhum " + getGroupName() + " foi cadastrado ainda!!!");
            context.addMessage(null, message);
        }
        em.close();
    }
    
    protected void fill(EntityManager em) {
        LoggableDAO loggableDAO = new LoggableDAO(em);
        loggableEmployees.addAll(loggableDAO.findAll());
    }
    
    protected String getGroupName() {
        return "supervisores/gerentes";
    }

    @Override
    public Iterator<Loggable> iterator() {
        return loggableEmployees.iterator();
    }
    
}
