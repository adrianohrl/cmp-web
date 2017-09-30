/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.PeriodSearch;
import br.com.ceciliaprado.cmp.control.dao.events.AbstractEmployeeRelatedEventDAO;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.exceptions.DAOException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 * @param <T>
 * @param <S>
 */
public abstract class AbstractEventsPeriodSearchBean<T extends Employee, S extends AbstractEmployeeRelatedEvent> extends PeriodSearch {
    
    private final EntityManager em = DataSource.createEntityManager();
    protected EmployeeRelatedEventsList<S> events = new EmployeeRelatedEventsList<>();
    protected T employee;

    @Override
    protected void reset() {
        events.clear();
    }

    @Override
    public void search() {
        reset();
        FacesMessage message;
        try {
            AbstractEmployeeRelatedEventDAO eventDAO = createDAO(em);
            events.addAll(eventDAO.findEmployeeEvents(employee, super.getStart(), super.getEnd()));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado", 
                    events.size() + " registro(s) encontrado(s)!!!");
        } catch (DAOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
                    "A data e o horário iniciais da consulta deve ser anterior à data e horário finais!!!");
        }  
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
    }
    
    protected AbstractEmployeeRelatedEventDAO createDAO(EntityManager em) {
        return new AbstractEmployeeRelatedEventDAO(em);
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public EmployeeRelatedEventsList<S> getEvents() {
        return events;
    }

    public T getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }
    
}
