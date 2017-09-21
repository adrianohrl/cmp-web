/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.production.services.ProductionOrderService;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
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
 * @author adrianohrl
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
