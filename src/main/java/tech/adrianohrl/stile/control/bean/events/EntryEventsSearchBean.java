package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.dao.events.EntryEventDAO;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
