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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class ModelBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final ModelDAO modelDAO = new ModelDAO(em);
    private final PhaseDAO phaseDAO = new PhaseDAO(em);
    private Model model = new Model();
    private final List<Phase> phases = phaseDAO.findAll();
    
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
    
    @PostConstruct
    public void add() {
        model.getPhases().add(new ModelPhase());
    }
    
    public void remove(ModelPhase phase) {    
        model.getPhases().remove(phase);
    }
    
    public void onCellEdit(CellEditEvent event) {
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public List<Phase> getPhases() {
        return phases;
    }
    
}
