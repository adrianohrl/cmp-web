/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.model.production.EntryEventsBuilder;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EntryEventsList;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import br.com.ceciliaprado.cmp.model.production.Phase;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
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
import javax.faces.event.AjaxBehaviorEvent;
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
    private Sector sector;
    private final Sector emptySector = new Sector("", null);
    private final List<Sector> sectors = new ArrayList<>();
    private final Subordinate emptySubordinate = new Subordinate("", "");
    private final List<Subordinate> subordinates = new ArrayList<>();
    private final ProductionOrder emptyProductionOrder = new ProductionOrder("", null);
    private final List<ProductionOrder> productionOrders = new ArrayList<>();
    private final Phase emptyPhase = new Phase("", null);
    private PhaseProductionOrder phaseProductionOrder;
    private final List<PhaseProductionOrder> phaseProductionOrders = new ArrayList<>();
    private final Casualty emptyCasualty = new Casualty("");
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
                SupervisorDAO supervisorDAO = new SupervisorDAO(em);
                sectors.addAll(supervisorDAO.findSupervisorSectors(supervisor));
                Collections.sort(sectors);
                subordinates.clear();
                for (Subordinate subordinate : supervisor.getSubordinates()) {
                    if (subordinate.isAvailable()) {
                        subordinates.add(subordinate);
                    }
                }
                Collections.sort(subordinates);
                return;
            }
        }
    }
    
    public void selectProductionOrders(AjaxBehaviorEvent event) {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        phaseProductionOrders.clear();
        phaseProductionOrders.addAll(phaseProductionOrderDAO.findPendents(sector));
        productionOrders.clear();
        for (PhaseProductionOrder ppo : phaseProductionOrders) {
            ProductionOrder po = ppo.getProductionOrder();
            if (!productionOrders.contains(po)) {
                productionOrders.add(po);
            }
        }
        Collections.sort(productionOrders);
    }
    
    public void selectPhaseProductionOrder(AjaxBehaviorEvent event) {
        
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

    public Subordinate getEmptySubordinate() {
        return emptySubordinate;
    }

    public List<Subordinate> getSubordinates() {
        return subordinates;
    }

    public ProductionOrder getEmptyProductionOrder() {
        return emptyProductionOrder;
    }

    public List<ProductionOrder> getProductionOrders() {
        return productionOrders;
    }

    public Phase getEmptyPhase() {
        return emptyPhase;
    }

    public PhaseProductionOrder getPhaseProductionOrder() {
        return phaseProductionOrder;
    }

    public void setPhaseProductionOrder(PhaseProductionOrder phaseProductionOrder) {
        this.phaseProductionOrder = phaseProductionOrder;
    }

    public List<PhaseProductionOrder> getPhaseProductionOrders() {
        return phaseProductionOrders;
    }

    public Casualty getEmptyCasualty() {
        return emptyCasualty;
    }

    public List<Casualty> getCasualties() {
        return casualties;
    }
    
}
