package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.dao.events.TimeClockEventDAO;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class TimeClockEventsSearchBean extends AbstractEventsPeriodSearchBean<Employee, TimeClockEvent> {

    @Override
    protected TimeClockEventDAO createDAO(EntityManager em) {
        return new TimeClockEventDAO(em);
    }
    
}
