/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.dao.events.TimeClockEventDAO;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
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
