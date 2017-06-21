/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.control.dao.personnel.SupervisorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.util.List;
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
public class SectorBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final SectorDAO sectorDAO = new SectorDAO(em);
    private final SupervisorDAO supervisorDAO = new SupervisorDAO(em);
    private final Sector sector = new Sector();
    private final List<Supervisor> supervisors = supervisorDAO.findAll();
    
    public String insert() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (sector.getSupervisor() == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", sector + " deve estar associado a um supervisor!!!");
        } else {
            try {
                sectorDAO.create(sector);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Sucesso no cadastro", sector + " foi cadastrado com sucesso!!!");
                next = "/index";
            } catch (EntityExistsException e) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro no cadastro", sector + " já foi cadastrado!!!");
            }
        }
        context.addMessage(null, message);
        return next;
    }

    public Sector getSector() {
        return sector;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }
    
}
