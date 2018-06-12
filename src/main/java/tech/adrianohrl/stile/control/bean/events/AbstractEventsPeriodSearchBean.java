package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.PeriodSearch;
import tech.adrianohrl.stile.control.dao.events.AbstractEmployeeRelatedEventDAO;
import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.exceptions.DAOException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 * @param <S>
 */
public abstract class AbstractEventsPeriodSearchBean<T extends Employee, S extends AbstractEmployeeRelatedEvent> extends PeriodSearch {
    
    public final EntityManager em = DataSource.createEntityManager();
    protected EmployeeRelatedEventsList<S> events = new EmployeeRelatedEventsList<>();
    protected T employee;

    @Override
    protected void reset() {
        events.clear();
    }

    @Override
    public void search() {
        reset();
        FacesMessage message;
        try {
            AbstractEmployeeRelatedEventDAO eventDAO = createDAO(em);
            events.addAll(eventDAO.findEmployeeEvents(employee, super.getStart(), super.getEnd()));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado", 
                    events.size() + " registro(s) encontrado(s)!!!");
        } catch (DAOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
                    "A data e o horário iniciais da consulta deve ser anterior à data e horário finais!!!");
        }  
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
    }
    
    protected AbstractEmployeeRelatedEventDAO createDAO(EntityManager em) {
        return new AbstractEmployeeRelatedEventDAO(em);
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public EmployeeRelatedEventsList<S> getEvents() {
        return events;
    }

    public T getEmployee() {
        return employee;
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }
    
}
