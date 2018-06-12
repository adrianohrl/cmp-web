package tech.adrianohrl.stile.control.bean.production;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.production.services.PhaseService;
import tech.adrianohrl.stile.control.dao.production.PhaseDAO;
import tech.adrianohrl.stile.model.production.Phase;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class PhaseRegisterBean implements Serializable {
    
    @ManagedProperty(value = "#{phaseService}")
    private PhaseService service;
    private final EntityManager em = DataSource.createEntityManager();
    private final Phase phase = new Phase();
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (phase.getSector() == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", phase + " deve estar associado a um setor!!!");
        } else {
            try {
                PhaseDAO phaseDAO = new PhaseDAO(em);
                phaseDAO.create(phase);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", phase + " foZi cadastrado com sucesso!!!");
                next = "/index";
                update();
            } catch (EntityExistsException e) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no cadastro", phase + " já foi cadastrado!!!");
            }
        }
        context.addMessage(null, message);
        return next;
    }
    
    public void update() {
        service.update();
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public void setService(PhaseService service) {
        this.service = service;
    }

    public Phase getPhase() {
        return phase;
    }
    
}
