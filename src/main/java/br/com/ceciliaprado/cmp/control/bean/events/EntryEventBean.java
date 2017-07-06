/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@RequestScoped
public class EntryEventBean implements Serializable {
        
    @ManagedProperty("#{entryEventsBean}")
    private EntryEventsBean entryEventsBean;
    private EntryEvent entryEvent = new EntryEvent();
    private final List<Casualty> casualties = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(entryEventsBean.getEntityManager());
        casualties.addAll(casualtyDAO.findAll());
    }
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no cadastro", "A data e o horário de entrada/saída deve ser antes da data e horário atual!!!");
        /*try {
            timeClockEvent.setEventDate(Calendars.sum(date, time));
            if (maxDate.after(timeClockEvent.getEventDate())) {
                timeClockEventDAO.create(timeClockEvent);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", timeClockEvent + " foi cadastrado com sucesso!!!");
                next = "/index";
            }
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", timeClockEvent + " já foi cadastrado!!!");
        }*/
        context.addMessage(null, message);
        return next;
    }
    
    public void add() {
        
    }
    
    public void reset() {
        entryEvent = new EntryEvent();
    }

    public EntryEvent getEntryEvent() {
        return entryEvent;
    }

    public void setEntryEvent(EntryEvent entryEvent) {
        this.entryEvent = entryEvent;
    }
    
}
