/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyEntryEventDAO;
import br.com.ceciliaprado.cmp.control.dao.events.EntryEventDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SubordinateDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.model.production.EntryEventsBuilder;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EntryEventsList;
import br.com.ceciliaprado.cmp.exceptions.ProductionException;
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
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
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
    private Calendar maxDate = new GregorianCalendar();
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
    private int producedQuantity;
    private int returnedQuantity;
    private Casualty casualty;
    private final List<Casualty> casualties = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        System.out.println("Initializing...");
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
    
    public void register() {
        System.out.println("Registering...");
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro", 
                "A data e o horário da atividade de produção deve ser antes da data e horário atual!!!");
        boolean succeeded = false;
        try {
            if (date == null || time == null) {
                return;
            }
            Calendar eventDate = Calendars.sum(date, time);
            if (eventDate.before(new GregorianCalendar()) && productionState != null) {
                EntryEvent entryEvent = null;
                EntryEventDAO entryEventDAO = new EntryEventDAO(em);                
                switch (productionState) {
                    case STARTED:
                        entryEvent = builder.buildEntryEvent(phaseProductionOrder, subordinate, eventDate, observation);
                        break;
                    case RESTARTED:
                    case FINISHED:
                        entryEvent = builder.buildEntryEvent(phaseProductionOrder, subordinate, productionState, eventDate, observation);
                        break;
                    case PAUSED:
                    case RETURNED:
                        entryEvent = builder.buildEntryEvent(phaseProductionOrder, subordinate, productionState, producedQuantity, eventDate, observation, casualty);
                        entryEventDAO = new CasualtyEntryEventDAO(em);
                        break;
                    default:
                        throw new ProductionException("Tipo de atividade desconhecido: " + productionState + "!!!");
                }
                entryEventDAO.create(entryEvent);
                entryEvents.add(entryEvent);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", entryEvent + " foi cadastrada com sucesso!!!");
                reset();
                succeeded = true;
            }
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", "Atividade de produção já foi cadastrada!!!");
        } catch (ProductionException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                "Fatalidade no cadastro", e.getMessage());
        }
        context.addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("validationFailed", !succeeded);
    }
    
    public String onFlowProcess(FlowEvent event) {
        System.out.println("Going to " + event.getNewStep() + "...");
        if (event.getNewStep().equals("supervisorTab")) {
            logout();
        } else if (event.getNewStep().equals("entriesTab")) {
            builder = new EntryEventsBuilder(sector, supervisor);
            reset();
        }
        return event.getNewStep();
    }
    
    public void clear() {
        System.out.println("Clearing...");
        supervisor = null;
        sectors.clear();
        sector = null;
        builder = null;
    }
    
    public void selectSupervisor() {
        System.out.println("Selecting supervisor...");
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
                if (sectors.size() == 1) {
                    sector = sectors.get(0);
                }
                Collections.sort(sectors);
                return;
            }
        }
    }
    
    public void reset() {
        System.out.println("Reseting...");
        maxDate = new GregorianCalendar();
        date = new Date();
        time = new Date();
        subordinate = null;
        subordinates.clear();
        if (supervisor != null && sector != null) {
            SubordinateDAO subordinateDAO = new SubordinateDAO(em);
            subordinates.addAll(subordinateDAO.findSupervisorAndSectorSubordinates(supervisor, sector));
            if (subordinates.size() == 1) {
                subordinate = subordinates.get(0);
                selectProductionOrders();
            }
            Collections.sort(subordinates);
        }
        productionOrder = null;
        productionOrders.clear();
        phase = null;
        phases.clear();
        phaseProductionOrder = null;
        productionState = null;
        producedQuantity = 0;
        returnedQuantity = 0;
        observation = null;
        casualty = null;
    }
    
    public void selectProductionOrders() {
        System.out.println("Selecting production order...");
        productionOrders.clear();
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        if (subordinate.isAvailable()) {
            for (PhaseProductionOrder ppo : phaseProductionOrderDAO.findPendents(sector)) {
                ProductionOrder po = ppo.getProductionOrder();
                if (!productionOrders.contains(po)) {
                    productionOrders.add(po);
                }
            }
            if (productionOrders.size() == 1) {
                productionOrder = productionOrders.get(0);
                selectPhases();
            }
        } else {
            phaseProductionOrder = phaseProductionOrderDAO.findCurrent(subordinate);
            System.out.println(subordinate + "'s current ppo: " + phaseProductionOrder);
            productionOrder = phaseProductionOrder.getProductionOrder();
            productionOrders.clear();
            phase = phaseProductionOrder.getPhase().getPhase();
            phases.clear();
        }
        if (productionOrders.isEmpty()) {
            System.out.println("POs list is empty!!!");
        }
        Collections.sort(productionOrders);
    }
    
    public void selectPhases() {
        System.out.println("Selecting phase...");
        phases.clear();
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        for (PhaseProductionOrder ppo : phaseProductionOrderDAO.findPendents(productionOrder)) {
            Phase p = ppo.getPhase().getPhase();
            if (sector.equals(p.getSector()) && !ppo.isBeingProcessed()) {
                phases.add(p);
            }
        }
        if (phases.isEmpty()) {
            productionOrders.remove(productionOrder);
            productionOrder = null;
        } else if (phases.size() == 1) {
            phase = phases.get(0);
            selectPhaseProductionOrder();
        }
    }
    
    public void selectPhaseProductionOrder() {
        System.out.println("Selecting phase production order...");
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        phaseProductionOrder = phaseProductionOrderDAO.find(phase, productionOrder);
        if (phaseProductionOrder.getPossibleNextStates().size() == 1) {
            productionState = phaseProductionOrder.getPossibleNextStates().get(0);
            clearReturnedQuantity();
            updateProducedQuantity();
        }
    }
    
    public void updateProducedQuantity() {
        System.out.println("Updating produced quantity...");
        if (productionState.isStartingState()) {
            producedQuantity = 0;
        } else {
            producedQuantity = phaseProductionOrder.getTotalQuantity() - 
                    phaseProductionOrder.getProducedQuantity() - 
                    (!productionState.isFinished() ? returnedQuantity : 0);
        }
    }
    
    public void clearReturnedQuantity() {
        System.out.println("Clearing returned quantity...");
        returnedQuantity = 0;
    }
    
    public void onIdle() {
        System.out.println("Got idle!!!");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                "Saindo...", "Tempo expirado!!!"));
        logout();
    }
    
    public String logout() {
        System.out.println("Logging out...");
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/index";
    }
    
    @PreDestroy
    public void destroy() {        
        em.close();
    }
    
    public String toString(ProductionStates state) {
        if (state == null) {
            return "";
        }
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

    public int getProducedQuantity() {
        return producedQuantity;
    }

    public void setProducedQuantity(int producedQuantity) {
        this.producedQuantity = producedQuantity;
    }

    public int getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(int returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public Casualty getCasualty() {
        return casualty;
    }

    public void setCasualty(Casualty casualty) {
        this.casualty = casualty;
    }

    public List<Casualty> getCasualties() {
        return casualties;
    }
    
}
