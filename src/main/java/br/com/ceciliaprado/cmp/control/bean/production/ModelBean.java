/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.Phase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@SessionScoped
public class ModelBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final ModelDAO modelDAO = new ModelDAO(em);
    private final Model model = new Model();
    private final List<Phase> phases = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        phases.addAll(phaseDAO.findAll());
        if (phases.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatalidade no cadastro", "Nenhuma fase foi cadastrada ainda!!!");
            context.addMessage(null, message);
        }
    }
    
    public String insert() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        for (int i = 0; i < model.getPhases().size(); i++) {
            if (model.getPhases().get(i).getPhase() == null) {
                model.getPhases().remove(i);
            }
        }
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
    
    public void remove(ModelPhase phase) {    
        model.getPhases().remove(phase);
        phases.add(phase.getPhase());
    }
    
    public void edit(ModelPhase phase) {    
        //model.getPhases().remove(phase);
    }
    
    public void openModelPhaseView() {
        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        ExternalContext externalContent = FacesContext.getCurrentInstance().getExternalContext();
        externalContent.getRequestMap().put("phases", phases);
        RequestContext context = RequestContext.getCurrentInstance();
        context.openDialog("addPhase", options, null);        
    }
     
    public void onReturnFromModelPhaseView(SelectEvent event) {
        ModelPhase phase = (ModelPhase) event.getObject();
        model.getPhases().add(phase);
        phases.remove(phase.getPhase());
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nova fase adicionada", phase.toString());
        context.addMessage(null, message);
    }

    public Model getModel() {
        return model;
    }

    public List<Phase> getPhases() {
        return phases;
    }
    
}
