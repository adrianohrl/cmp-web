/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.control.dao.events.EntryEventDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SubordinateDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.model.production.EntryEventsBuilder;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EntryEventsList;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import br.com.ceciliaprado.cmp.model.production.Phase;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionStates;
import br.com.ceciliaprado.cmp.util.Calendars;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.servlet.http.HttpSession;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class EntryEventBean implements Serializable {
        
    private final EntityManager em = DataSource.createEntityManager();
    private final EntryEventsList entryEvents = new EntryEventsList();
    private EntryEventsBuilder builder;
    private String supervisorLogin;
    private Supervisor supervisor;
    private final List<Supervisor> supervisors = new ArrayList<>();
    private Sector sector;
    private final List<Sector> sectors = new ArrayList<>();
    private final Calendar maxDate = new GregorianCalendar();
    private Date date;
    private Date time;
    private Subordinate subordinate;
    private final List<Subordinate> subordinates = new ArrayList<>();
    private ProductionOrder productionOrder;
    private final List<ProductionOrder> productionOrders = new ArrayList<>();
    private Phase phase;
    private final List<Phase> phases = new ArrayList<>();
    private PhaseProductionOrder phaseProductionOrder;
    private ProductionStates productionState;
    private String observation;
    private Casualty casualty;
    private final List<Casualty> casualties = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        supervisors.addAll(supervisorDAO.findAll());
        Collections.sort(supervisors);
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        casualties.addAll(casualtyDAO.findAll());
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (supervisors.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no login", "Nenhum supervisor foi cadastrado ainda!!!");
            context.addMessage(null, message);
        }
        reset();
    }
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro", 
                "A data e o horário da atividade de produção deve ser antes da data e horário atual!!!");
        EntryEvent entryEvent = new EntryEvent();
        try {
            entryEvent.setEventDate(Calendars.sum(date, time));
            if (maxDate.after(entryEvent.getEventDate())) {
                EntryEventDAO entryEventDAO = new EntryEventDAO(em);
                entryEventDAO.create(entryEvent);
                entryEvents.add(entryEvent);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", entryEvent + " foi cadastrado com sucesso!!!");
                next = "/index";
            }
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", entryEvent + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }
    
    public String onFlowProcess(FlowEvent event) {
        if (event.getNewStep().equals("supervisorTab")) {
            logout();
        } else if (event.getNewStep().equals("entriesTab")) {
            builder = new EntryEventsBuilder(sector, supervisor);
        }
        return event.getNewStep();
    }
    
    public void clear() {
        supervisor = null;
        sectors.clear();
        sector = null;
        builder = null;
    }
    
    public void reset() {
        date = new Date();
        time = new Date();
        subordinate = null;
        productionOrder = null;
        productionOrders.clear();
        phase = null;
        phases.clear();
        phaseProductionOrder = null;
        productionState = null;
    }
    
    public void onIdle() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                "Saindo...", "Tempo expirado!!!"));
        logout();
    }
    
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/index";
    }
    
    public void selectSupervisor() {
        clear();
        if (supervisorLogin == null || supervisorLogin.isEmpty()) {
            return;
        }
        for (Supervisor s : supervisors) {
            if (supervisorLogin.equals(s.getLogin())) {
                supervisor = s;
                HttpSession session = SessionUtils.getSession();
			session.setAttribute("loggedEmployee", supervisor);
                SupervisorDAO supervisorDAO = new SupervisorDAO(em);
                sectors.addAll(supervisorDAO.findSupervisorSectors(supervisor));
                Collections.sort(sectors);
                return;
            }
        }
    }
    
    public void selectSubordinates() {
        subordinates.clear();
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        subordinates.addAll(subordinateDAO.findSupervisorAndSectorSubordinates(supervisor, sector));
        Collections.sort(subordinates);
    }
    
    public void selectProductionOrders() {
        productionOrders.clear();
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        if (subordinate.isAvailable()) {
            for (PhaseProductionOrder ppo : phaseProductionOrderDAO.findPendents(sector)) {
                ProductionOrder po = ppo.getProductionOrder();
                if (!productionOrders.contains(po)) {
                    productionOrders.add(po);
                }
            }
        } else {
            phaseProductionOrder = phaseProductionOrderDAO.findCurrent(subordinate);
            productionOrder = phaseProductionOrder.getProductionOrder();
            productionOrders.clear();
            phase = phaseProductionOrder.getPhase().getPhase();
            phases.clear();
        }
        Collections.sort(productionOrders);
    }
    
    public void selectPhases() {
        phases.clear();
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        for (PhaseProductionOrder ppo : phaseProductionOrderDAO.findPendents(productionOrder)) {
            phases.add(ppo.getPhase().getPhase());
        }
    }
    
    public void selectPhaseProductionOrder() {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        phaseProductionOrder = phaseProductionOrderDAO.find(phase, productionOrder);
    }
    
    public String toString(ProductionStates state) {
        String str = "";
        switch (state) {
            case STARTED:
                str = "Início";
                break;
            case RESTARTED:
                str = "Reinício";
                break;
            case PAUSED:
                str = "Pausa";
                break;
            case FINISHED:
                str = "Término";
                break;
            case RETURNED:
                str = "Devolução";
                break;
            default:
                throw new AssertionError(state.name());
        }
        return str;
    }

    public EntryEventsList getEntryEvents() {
        return entryEvents;
    }
    
    public String getSupervisorLogin() {
        return supervisorLogin;
    }

    public void setSupervisorLogin(String supervisorLogin) {
        this.supervisorLogin = supervisorLogin;
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

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public List<Sector> getSectors() {
        return sectors;
    }
    
    public Date getMaxDate() {
        return maxDate.getTime();
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

    public Subordinate getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Subordinate subordinate) {
        this.subordinate = subordinate;
    }

    public List<Subordinate> getSubordinates() {
        return subordinates;
    }

    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }

    public void setProductionOrder(ProductionOrder productionOrder) {
        this.productionOrder = productionOrder;
    }

    public List<ProductionOrder> getProductionOrders() {
        return productionOrders;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public PhaseProductionOrder getPhaseProductionOrder() {
        return phaseProductionOrder;
    }

    public void setPhaseProductionOrder(PhaseProductionOrder phaseProductionOrder) {
        this.phaseProductionOrder = phaseProductionOrder;
    }

    public ProductionStates getProductionState() {
        return productionState;
    }

    public void setProductionState(ProductionStates productionState) {
        this.productionState = productionState;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Casualty getCasualty() {
        return casualty;
    }

    public List<Casualty> getCasualties() {
        return casualties;
    }

    public void setCasualty(Casualty casualty) {
        this.casualty = casualty;
    }
    
}
