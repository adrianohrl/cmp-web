/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.services;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@SessionScoped
public class ProductionOrderService extends Service<ProductionOrder> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma ordem de produção foi cadastrada ainda!!!";
    }

    @Override
    protected List<ProductionOrder> getElements(EntityManager em) {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        return productionOrderDAO.findAll();
    }
    
    public List<ProductionOrder> getProductionOrders() {
        return getElements();
    }
    
}
