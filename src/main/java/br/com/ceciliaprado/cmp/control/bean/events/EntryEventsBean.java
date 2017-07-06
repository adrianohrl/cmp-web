/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.model.production.EntryEventsBuilder;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EntryEventsList;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class EntryEventsBean implements Serializable {
        
    private final EntityManager em = DataSource.createEntityManager();
    private final EntryEventsList entryEvents = new EntryEventsList();
    private EntryEventsBuilder builder;
    private Supervisor supervisor = new Supervisor();
    private final List<Supervisor> supervisors = new ArrayList<>();
    private Sector sector = new Sector();
    private final Sector emptySector = new Sector("", null);
    private final List<Sector> sectors = new ArrayList<>();
    private final List<Subordinate> subordinates = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisors.addAll(supervisorDAO.findAll());
        Collections.sort(supervisors);
        SectorDAO sectorDAO = new SectorDAO(em);
        sectors.addAll(sectorDAO.findAll());
        Collections.sort(sectors);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (supervisors.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no login", "Nenhum supervisor foi cadastrado ainda!!!");
            context.addMessage(null, message);
        }
        if (sectors.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no login", "Nenhum setor foi cadastrado ainda!!!");
            context.addMessage(null, message);
        }
    }
    
    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }
    
    public void selectSupervisor() {
        String login = supervisor.getLogin();
        if (login == null) {
            return;
        }
        for (Supervisor s : supervisors) {
            if (login.equals(s.getLogin())) {
                HttpSession session = SessionUtils.getSession();
			session.setAttribute("loggedEmployee", s);
                supervisor = s;
                return;
            }
        }
    }
    
    public void onIdle() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                "No activity.", "What are you doing over there?"));
        logout();
    }
 
    public void onActive() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Welcome Back", "Well, that's a long coffee break!"));
    }
    
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/index";
    }

    @PreDestroy
    void destroy() {
        em.close();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public EntryEventsList getEntryEvents() {
        return entryEvents;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Sector getEmptySector() {
        return emptySector;
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public List<Subordinate> getSubordinates() {
        return subordinates;
    }
    
}
