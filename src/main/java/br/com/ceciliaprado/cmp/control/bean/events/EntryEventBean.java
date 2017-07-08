/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.EntryEventDAO;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EntryEventsList;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.production.Phase;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import br.com.ceciliaprado.cmp.util.Calendars;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@RequestScoped
public class EntryEventBean implements Serializable {
        
    @ManagedProperty("#{entryEventsBean.entryEvents}")
    private EntryEventsList entryEvents;
    private final EntityManager em = DataSource.createEntityManager();
    private EntryEvent entryEvent;
    private ProductionOrder productionOrder;
    private Phase phase;
    private Casualty casualty;
    private final Calendar maxDate = new GregorianCalendar();
    private Date date;
    private Date time;
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro", 
                "A data e o horário da atividade de produção deve ser antes da data e horário atual!!!");
        try {
            entryEvent.setEventDate(Calendars.sum(date, time));
            if (maxDate.after(entryEvent.getEventDate())) {
                EntryEventDAO entryEventDAO = new EntryEventDAO(em);
                entryEventDAO.create(entryEvent);
                entryEvents.add(entryEvent);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", entryEvent + " foi cadastrado com sucesso!!!");
                next = "/index";
            }
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", entryEvent + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }
    
    @PostConstruct
    public void reset() {
        entryEvent = new EntryEvent();
        date = new Date();
        time = new Date();
    }

    public EntryEventsList getEntryEvents() {
        return entryEvents;
    }

    public void setEntryEvents(EntryEventsList entryEvents) {
        this.entryEvents = entryEvents;
    }

    public EntryEvent getEntryEvent() {
        return entryEvent;
    }

    public void setEntryEvent(EntryEvent entryEvent) {
        this.entryEvent = entryEvent;
    }

    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }

    public void setProductionOrder(ProductionOrder productionOrder) {
        this.productionOrder = productionOrder;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Casualty getCasualty() {
        return casualty;
    }

    public void setCasualty(Casualty casualty) {
        this.casualty = casualty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    public Date getMaxDate() {
        return maxDate.getTime();
    }
    
}
