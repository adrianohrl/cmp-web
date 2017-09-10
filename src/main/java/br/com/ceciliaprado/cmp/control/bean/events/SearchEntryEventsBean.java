/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.EntryEventDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SubordinateDAO;
import br.com.ceciliaprado.cmp.exceptions.DAOException;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.util.Calendars;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class SearchEntryEventsBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final List<EntryEvent> events = new ArrayList<>();
    private Subordinate subordinate;
    private final List<Subordinate> subordinates = new ArrayList<>();
    private final Calendar maxDate = new GregorianCalendar();
    private Date startDate;
    private Date startTime;
    private Date endDate;
    private Date endTime;
    
    @PostConstruct
    public void init() {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        subordinates.addAll(subordinateDAO.findAll());
        Collections.sort(subordinates);
        if (subordinates.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no cadastro", "Nenhum funcionário foi cadastrado ainda!!!");
            context.addMessage(null, message);
        }
    }
    
    public void search() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        EntryEventDAO eventDAO = new EntryEventDAO(em);
        events.clear();
        try {
            events.addAll(eventDAO.findEmployeeEvents(subordinate, getStart(), getEnd()));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado", 
                    events.size() + " registro(s) encontrado(s)!!!");
        } catch (DAOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
                    "A data e o horário iniciais da consulta deve ser antes da data e horário finais!!!");
        }  
        context.addMessage(null, message);
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public List<EntryEvent> getEvents() {
        return events;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Subordinate subordinate) {
        this.subordinate = subordinate;
    }

    public List<Subordinate> getSubordinates() {
        return subordinates;
    }
    
    public Date getMaxDate() {
        return maxDate.getTime();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Calendar getStart() {
        return Calendars.sum(startDate, startTime);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Calendar getEnd() {
        return Calendars.sum(endDate, endTime);
    }
    
}
