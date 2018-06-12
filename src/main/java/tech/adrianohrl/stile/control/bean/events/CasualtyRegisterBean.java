package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.events.services.CasualtyService;
import tech.adrianohrl.stile.control.dao.events.CasualtyDAO;
import tech.adrianohrl.stile.model.events.Casualty;
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
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class CasualtyRegisterBean implements Serializable {
        
    @ManagedProperty(value = "#{casualtyService}")
    private CasualtyService service;
    private final Casualty casualty = new Casualty();
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        EntityManager em = DataSource.createEntityManager();
        try {
            CasualtyDAO casualtyDAO = new CasualtyDAO(em);
            casualtyDAO.create(casualty);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", casualty + " foi cadastrado com sucesso!!!");
            next = "/index";
            update();
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", casualty + " já foi cadastrado!!!");
        }
        em.close();
        context.addMessage(null, message);
        return next;
    }
    
    public void update() {
        service.update();
    }

    public void setService(CasualtyService service) {
        this.service = service;
    }

    public Casualty getCasualty() {
        return casualty;
    }
    
}
