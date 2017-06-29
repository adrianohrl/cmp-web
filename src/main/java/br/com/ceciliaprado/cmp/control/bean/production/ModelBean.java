/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
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
public class ModelBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final ModelDAO modelDAO = new ModelDAO(em);
    private final Model model = new Model();
    private final Phase emptyPhase = new Phase("", null);
    private final List<Phase> phases = new ArrayList<>();
    private ModelPhase modelPhase = new ModelPhase();
    private int minutes = 0;
    private double seconds = 0.0;
    
    @PostConstruct
    public void init() {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        phases.addAll(phaseDAO.findAll());
        Collections.sort(phases);
        if (phases.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no cadastro", "Nenhuma fase foi cadastrada ainda!!!");
            context.addMessage(null, message);
        }
    }
    
    public String insert() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (model.getPhases().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", model + " deve conter fases!!!");
        } else {
            try {
                modelDAO.create(model);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", model + " foi cadastrado com sucesso!!!");
                next = "/index";
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
        modelPhase.setExpectedDuration(minutes + seconds / 60);
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
        requestContext.addCallbackParam("positiveExpectedDuration", positiveExpectedDuration);
    }
    
    public void remove(ModelPhase modelPhase) {    
        model.getPhases().remove(modelPhase);
        phases.add(modelPhase.getPhase());
        Collections.sort(phases);
    }
    
    public void reset() {
        modelPhase = new ModelPhase();
        minutes = 0;
        seconds = 0.0;
    }

    @PreDestroy
    void destroy() {
        em.close();
    }
    
    public Model getModel() {
        return model;
    }

    public Phase getEmptyPhase() {
        return emptyPhase;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public ModelPhase getModelPhase() {
        return modelPhase;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }
    
}
