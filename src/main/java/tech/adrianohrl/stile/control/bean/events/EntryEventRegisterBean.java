package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.SessionUtils;
import tech.adrianohrl.stile.control.bean.personnel.services.SupervisorService;
import tech.adrianohrl.stile.control.dao.events.CasualtyEntryEventDAO;
import tech.adrianohrl.stile.control.dao.events.EntryEventDAO;
import tech.adrianohrl.stile.control.dao.personnel.SubordinateDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.control.dao.order.PhaseProductionOrderDAO;
import tech.adrianohrl.stile.control.model.production.EntryEventsBuilder;
import tech.adrianohrl.stile.control.model.production.reports.filters.EntryEventsList;
import tech.adrianohrl.stile.exceptions.ProductionException;
import tech.adrianohrl.stile.model.events.Casualty;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import tech.adrianohrl.stile.model.order.ProductionStates;
import tech.adrianohrl.util.Calendars;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import tech.adrianohrl.util.CalendarUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class EntryEventRegisterBean implements Serializable {
    
    @ManagedProperty(value = "#{supervisorService}")
    private SupervisorService supervisorService;
    private final EntityManager em = DataSource.createEntityManager();
    private final EntryEventsList entryEvents = new EntryEventsList();
    private EntryEventsBuilder builder;
    private String supervisorLogin;
    private Supervisor supervisor;
    private Sector sector;
    private final List<Sector> sectors = new ArrayList<>();
    private Calendar maxDate = CalendarUtil.now();
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
    
    public void register() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro", 
                "A data e o horário da atividade de produção deve ser antes da data e horário atual!!!");
        boolean succeeded = false;
        try {
            if (date == null || time == null) {
                return;
            }
            Calendar eventDate = Calendars.combine(date, time);
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
        if (event.getOldStep().equals("supervisorTab") && sector == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
                    "Selecione o sector desejado!!!");
            context.addMessage(null, message);
            return event.getOldStep();
        }
        if (event.getNewStep().equals("supervisorTab")) {
            logout();
        } else if (event.getNewStep().equals("entriesTab")) {
            builder = new EntryEventsBuilder(sector, supervisor);
            reset();
        }
        return event.getNewStep();
    }
    
    public void clear() {
        supervisor = null;
        sectors.clear();
        sector = null;
        builder = null;
    }
    
    public void selectSupervisor() {
        clear();
        if (supervisorLogin == null || supervisorLogin.isEmpty()) {
            return;
        }
        for (Supervisor s : supervisorService) {
            if (supervisorLogin.equals(s.getLogin())) {
                supervisor = s;
                SessionUtils.setUser(supervisor);
                SupervisorDAO supervisorDAO = new SupervisorDAO(em);
                sectors.addAll(supervisorDAO.findSupervisorSectors(supervisor));
                sector = sectors.size() == 1 ? sectors.get(0) : null;
                Collections.sort(sectors);
                return;
            }
        }
    }
    
    public void reset() {
        maxDate = CalendarUtil.now();
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
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        phaseProductionOrder = phaseProductionOrderDAO.find(phase, productionOrder);
        if (phaseProductionOrder.getPossibleNextStates().size() == 1) {
            productionState = phaseProductionOrder.getPossibleNextStates().get(0);
            clearReturnedQuantity();
            updateProducedQuantity();
        }
    }
    
    public void updateProducedQuantity() {
        if (productionState.isStartingState()) {
            producedQuantity = 0;
        } else {
            producedQuantity = phaseProductionOrder.getTotalQuantity() - 
                    phaseProductionOrder.getProducedQuantity() - 
                    (!productionState.isFinished() ? returnedQuantity : 0);
        }
    }
    
    public void clearReturnedQuantity() {
        returnedQuantity = 0;
    }
    
    public void onIdle() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                "Saindo...", "Tempo expirado!!!"));
        logout();
    }
    
    public String logout() {
        SessionUtils.invalidate();
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

    public void setSupervisorService(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
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
    
}
