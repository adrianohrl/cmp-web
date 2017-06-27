/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.TimeClockEventDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.EmployeeDAO;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.util.Calendars;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class TimeClockEventBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final TimeClockEventDAO timeClockEventDAO = new TimeClockEventDAO(em);
    private final TimeClockEvent timeClockEvent = new TimeClockEvent();
    private final List<Employee> employees = new ArrayList<>();
    private Date date;
    private Date time;
    
    
    @PostConstruct
    public void init() {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        employees.addAll(employeeDAO.findAll());
        if (employees.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no cadastro", "Nenhum funcionário foi cadastrado ainda!!!");
            context.addMessage(null, message);
        } else {
            employees.add(new Subordinate("", ""));
            Collections.sort(employees);
        }
    }
    
    public String insert() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            timeClockEventDAO.create(timeClockEvent);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", timeClockEvent + " foi cadastrado com sucesso!!!");
            next = "/index";
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", timeClockEvent + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }

    public TimeClockEvent getTimeClockEvent() {
        return timeClockEvent;
    }

    public List<Employee> getEmployees() {
        return employees;
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
    
}
