/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
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

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class PhaseRegisterBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final Phase phase = new Phase();
    private final List<Sector> sectors = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        SectorDAO sectorDAO = new SectorDAO(em);
        sectors.addAll(sectorDAO.findAll());
        Collections.sort(sectors);
        if (sectors.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no cadastro", "Nenhum setor foi cadastrado ainda!!!");
            context.addMessage(null, message);
        }
    }
    
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
            } catch (EntityExistsException e) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no cadastro", phase + " já foi cadastrado!!!");
            }
        }
        context.addMessage(null, message);
        return next;
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public Phase getPhase() {
        return phase;
    }

    public List<Sector> getSectors() {
        return sectors;
    }
    
}
