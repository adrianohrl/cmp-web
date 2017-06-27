/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.exceptions.ProductionException;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class PhaseProductionOrderBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
    private ProductionOrder productionOrder = new ProductionOrder();
    private PhaseProductionOrder phaseProductionOrder = new PhaseProductionOrder();
    private final List<PhaseProductionOrder> phaseProductionOrders = new ArrayList<>();
    private final List<ProductionOrder> productionOrders = new ArrayList<>();
    private final List<ModelPhase> modelPhases = new ArrayList<>();
    private boolean allPhases = true;
    private int totalQuantity;
    
    @PostConstruct
    public void init() {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        productionOrders.addAll(productionOrderDAO.findAll());
        if (productionOrders.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no cadastro", "Nenhuma ordem de produção foi cadastrada ainda!!!");
            context.addMessage(null, message);
        } else {
            productionOrders.add(new ProductionOrder("", null));
            Collections.sort(productionOrders);
        }
    }
    
    public String insert() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (isAllPhases()) {
            phaseProductionOrders.clear();
            for (ModelPhase modelPhase : modelPhases) {
                try {
                    phaseProductionOrders.add(new PhaseProductionOrder(modelPhase, productionOrder, totalQuantity));
                } catch (ProductionException ex) {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Erro no cadastro", ex.getMessage());
                    context.addMessage(null, message);
                }
            }
        }
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
    
    public void add(){
        phaseProductionOrders.add(phaseProductionOrder);
        modelPhases.remove(phaseProductionOrder.getPhase());
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Nova ordem de produção de fase adicionada ao modelo", phaseProductionOrder.toString());
        reset();    
        context.addMessage(null, message);
    }
    
    public void remove(PhaseProductionOrder phaseProductionOrder) {
        phaseProductionOrders.remove(phaseProductionOrder);
        modelPhases.add(phaseProductionOrder.getPhase());
        Collections.sort(modelPhases);
    }
    
    private void reset() {
        phaseProductionOrder = new PhaseProductionOrder();
        phaseProductionOrder.setProductionOrder(productionOrder);
    }
    
    public void clear() {
        phaseProductionOrders.clear();
        modelPhases.clear();
        Model model = productionOrder.getModel();
        if (model != null) {
            phaseProductionOrder.setProductionOrder(productionOrder);
            modelPhases.addAll(model.getPhases());
            Collections.sort(modelPhases);
        }
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

    public List<ModelPhase> getModelPhases() {
        return modelPhases;
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
