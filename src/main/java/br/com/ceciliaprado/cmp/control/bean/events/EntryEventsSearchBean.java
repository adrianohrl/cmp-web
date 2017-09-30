/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.dao.events.EntryEventDAO;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class EntryEventsSearchBean extends AbstractEventsPeriodSearchBean<Subordinate, EntryEvent> {

    public Subordinate getSubordinate() {
        return employee;
    }

    public void setSubordinate(Subordinate subordinate) {
       employee = subordinate;
    }

    @Override
    protected EntryEventDAO createDAO(EntityManager em) {
        return new EntryEventDAO(em);
    }
    
}
