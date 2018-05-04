/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.order.ProductionOrderDAO;
import tech.adrianohrl.stile.model.order.ProductionOrder;
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
