/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import java.util.List;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("productionOrderConverter")
public class ProductionOrderConverter extends Converter<ProductionOrder> {

    @Override
    protected List<ProductionOrder> getElements() {
        EntityManager em = DataSource.createEntityManager();
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        List<ProductionOrder> productionOrders = productionOrderDAO.findAll();
        em.close();
        return productionOrders;
    }

    @Override
    public String getErrorMessage() {
        return "Order de produção inválida!!!";
    }

    @Override
    public String toString(ProductionOrder productionOrder) {
        return productionOrder.getReference();
    }
    
}
