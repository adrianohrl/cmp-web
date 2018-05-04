/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.personnel.services.SectorService;
import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.model.personnel.Sector;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class SectorRegisterBean implements Serializable {
    
    @ManagedProperty(value = "#{sectorService}")
    private SectorService service;
    private final Sector sector = new Sector();
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (sector.getSupervisor() == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", sector + " deve estar associado a um supervisor!!!");
        } else {
            EntityManager em = DataSource.createEntityManager();
            try {
                SectorDAO sectorDAO = new SectorDAO(em);
                sectorDAO.create(sector);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", sector + " foi cadastrado com sucesso!!!");
                next = "/index";
                update();
            } catch (EntityExistsException e) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no cadastro", sector + " já foi cadastrado!!!");
            }
            em.close();
        }
        context.addMessage(null, message);
        return next;
    }

    protected void update() {
        service.update();
    }

    public void setService(SectorService service) {
        this.service = service;
    }

    public Sector getSector() {
        return sector;
    }
    
}
