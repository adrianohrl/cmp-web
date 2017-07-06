/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
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
public class ProductionOrderBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final ProductionOrder productionOrder = new ProductionOrder();
    private final Model emptyModel = new Model("", "");
    private final List<Model> models = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        ModelDAO modelDAO = new ModelDAO(em);
        models.addAll(modelDAO.findAll());
        Collections.sort(models);
        if (models.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade no cadastro", "Nenhum modelo foi cadastrado ainda!!!");
            context.addMessage(null, message);
        }
    }
    
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
        } catch (EntityExistsException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro no cadastro", productionOrder + " já foi cadastrado!!!");
        }
        context.addMessage(null, message);
        return next;
    }

    @PreDestroy
    void destroy() {
        em.close();
    }

    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }

    public Model getEmptyModel() {
        return emptyModel;
    }

    public List<Model> getModels() {
        return models;
    }
    
}
