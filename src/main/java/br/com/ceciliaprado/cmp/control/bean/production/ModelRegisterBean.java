/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.production.services.ModelService;
import br.com.ceciliaprado.cmp.control.bean.production.services.PhaseService;
import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.Phase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class ModelRegisterBean implements Serializable {
    
    @ManagedProperty(value = "#{modelService}")
    private ModelService service;
    @ManagedProperty(value = "#{phaseService}")
    private PhaseService phaseService;
    private final EntityManager em = DataSource.createEntityManager();
    private final Model model = new Model();
    private final List<Phase> phases = new ArrayList<>();
    private Phase phase;
    private double expectedDuration = 0.0;
    
    @PostConstruct
    public void init() {
        phases.addAll(phaseService.getPhases());
    }
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (model.getPhases().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", model + " deve conter fases!!!");
        } else {
            try {
                ModelDAO modelDAO = new ModelDAO(em);
                modelDAO.create(model);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", model + " foi cadastrado com sucesso!!!");
                next = "/index";
                update();
            } catch (EntityExistsException e) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no cadastro", model + " já foi cadastrado!!!");
            }
        }
        context.addMessage(null, message);
        return next;
    }
    
    public void add(){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Dado inconsistente", "A duração da fase não pode ser nula!!!");
        ModelPhase modelPhase = new ModelPhase(phase, expectedDuration);
        boolean positiveExpectedDuration = modelPhase.getExpectedDuration() > 0.0;
        if (positiveExpectedDuration) {
            model.getPhases().add(modelPhase);
            phases.remove(modelPhase.getPhase());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Nova fase adicionada ao modelo", modelPhase.toString());
            reset();    
        }
        context.addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("validationFailed", !positiveExpectedDuration);
    }
    
    public void remove(ModelPhase modelPhase) {    
        model.getPhases().remove(modelPhase);
        phases.add(modelPhase.getPhase());
        Collections.sort(phases);
    }
    
    private void reset() {
        expectedDuration = 0.0;
    }
    
    public void update() {
        service.update();
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public void setService(ModelService service) {
        this.service = service;
    }

    public void setPhaseService(PhaseService phaseService) {
        this.phaseService = phaseService;
    }
    
    public Model getModel() {
        return model;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public double getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(double expectedDuration) {
        this.expectedDuration = expectedDuration;
    }
    
}
