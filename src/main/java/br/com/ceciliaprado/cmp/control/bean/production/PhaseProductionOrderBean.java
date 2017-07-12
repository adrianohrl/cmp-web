/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.exceptions.ProductionException;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
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
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.primefaces.context.RequestContext;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class PhaseProductionOrderBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private PhaseProductionOrder phaseProductionOrder;
    private final List<PhaseProductionOrder> phaseProductionOrders = new ArrayList<>();
    private ProductionOrder productionOrder;
    private final List<ProductionOrder> productionOrders = new ArrayList<>();
    private Phase phase;
    private final List<Phase> phases = new ArrayList<>();
    private boolean allPhases = false;
    private int totalQuantity;
    
    @PostConstruct
    public void init() {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        productionOrders.addAll(productionOrderDAO.findAll());
        Collections.sort(productionOrders);
        if (productionOrders.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no cadastro", "Nenhuma ordem de produção foi cadastrada ainda!!!");
            context.addMessage(null, message);
        }
        reset();
    }
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (allPhases) {
            phaseProductionOrders.clear();
            for (Phase p : phases) {
                try {
                    ModelPhase modelPhase = productionOrder.getModel().getPhase(p);
                    phaseProductionOrders.add(new PhaseProductionOrder(modelPhase, productionOrder, totalQuantity));
                } catch (ProductionException ex) {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Erro no cadastro", ex.getMessage());
                    context.addMessage(null, message);
                }
            }
        }
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        for (PhaseProductionOrder ppe : phaseProductionOrders) {
            try {
                phaseProductionOrderDAO.create(ppe);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", ppe + " foi cadastrado com sucesso!!!");
                next = "/index";
            } catch (EntityExistsException e) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no cadastro", ppe + " já foi cadastrado!!!");
            }
            context.addMessage(null, message);
        }
        return next;
    }
    
    public void add() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Dado inconsistente", "A quantidade total a ser produzida dessa fase deve ser positiva!!!");
        boolean positiveTotalQuantity = phaseProductionOrder.getTotalQuantity() > 0;
        if (positiveTotalQuantity) {
            for (ModelPhase modelPhase : productionOrder.getModel().getPhases()) {
                if (modelPhase.equals(phase)) {
                    phaseProductionOrder.setPhase(modelPhase);
                }
            }
            phaseProductionOrders.add(phaseProductionOrder);
            phases.remove(phase);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Nova ordem de produção de fase adicionada ao modelo", 
                    phaseProductionOrder.getTotalQuantity() + " [un] no fase " + 
                            phaseProductionOrder.getPhase() + " da ordem de produção " + 
                            phaseProductionOrder.getProductionOrder());
            reset();    
        }
        context.addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("otherValidationsFailed", !positiveTotalQuantity);
    }
    
    public void remove(PhaseProductionOrder phaseProductionOrder) {
        phaseProductionOrders.remove(phaseProductionOrder);
        phases.add(phaseProductionOrder.getPhase().getPhase());
        Collections.sort(phases);
    }
    
    private void reset() {
        totalQuantity = 0;
        phase = new Phase("", null);
        phaseProductionOrder = new PhaseProductionOrder();    
        phaseProductionOrder.setProductionOrder(productionOrder);
    }
    
    public void clear() {
        phaseProductionOrders.clear();
        phases.clear();
        Model model = productionOrder.getModel();
        if (model != null) {
            phaseProductionOrder.setProductionOrder(productionOrder);
            for (ModelPhase modelPhase : model.getPhases()) {
                phases.add(modelPhase.getPhase());
            }
            Collections.sort(phases);
        }
    }

    @PreDestroy
    void destroy() {
        em.close();
    }

    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }

    public void setProductionOrder(ProductionOrder productionOrder) {
        this.productionOrder = productionOrder;
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

    public boolean isAllPhases() {
        return allPhases;
    }

    public void setAllPhases(boolean allPhases) {
        this.allPhases = allPhases;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
}
