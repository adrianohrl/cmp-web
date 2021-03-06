package tech.adrianohrl.stile.control.bean.production;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.production.services.ProductionOrderService;
import tech.adrianohrl.stile.control.dao.order.ProductionOrderDAO;
import tech.adrianohrl.stile.model.order.ProductionOrder;
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
public class ProductionOrderRegisterBean implements Serializable {
    
    @ManagedProperty(value = "#{productionOrderService}")
    private ProductionOrderService service;
    private final EntityManager em = DataSource.createEntityManager();
    private final ProductionOrder productionOrder = new ProductionOrder();
    
    public String register() {
        String next = "";
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        try {
            ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
            productionOrderDAO.create(productionOrder);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso no cadastro", productionOrder + " foi cadastrado com sucesso!!!");
            next = "/index";
            update();
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", productionOrder + " já foi cadastrado!!!");
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

    public void setService(ProductionOrderService service) {
        this.service = service;
    }

    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }
    
}
