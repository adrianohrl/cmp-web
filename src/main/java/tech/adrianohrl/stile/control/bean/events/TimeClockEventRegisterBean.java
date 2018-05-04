/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.dao.events.TimeClockEventDAO;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.util.Calendars;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class TimeClockEventRegisterBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final TimeClockEvent timeClockEvent = new TimeClockEvent();
    private final Calendar maxDate = new GregorianCalendar();
    private Date date;
    private Date time;
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro", 
                "A data e o horário de entrada/saída deve ser antes da data e horário atual!!!");
        try {
            timeClockEvent.setEventDate(Calendars.sum(date, time));
            if (maxDate.after(timeClockEvent.getEventDate())) {
                TimeClockEventDAO timeClockEventDAO = new TimeClockEventDAO(em);
                timeClockEventDAO.create(timeClockEvent);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", timeClockEvent + " foi cadastrado com sucesso!!!");
                next = "/index";
            }
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", timeClockEvent + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public TimeClockEvent getTimeClockEvent() {
        return timeClockEvent;
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
