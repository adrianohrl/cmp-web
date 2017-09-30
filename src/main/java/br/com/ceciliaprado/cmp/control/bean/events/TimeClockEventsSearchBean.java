/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.dao.events.TimeClockEventDAO;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class TimeClockEventsSearchBean extends AbstractEventsPeriodSearchBean<Employee, TimeClockEvent> {

    @Override
    protected TimeClockEventDAO createDAO(EntityManager em) {
        return new TimeClockEventDAO(em);
    }
    
}
