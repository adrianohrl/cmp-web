/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.production.ProductionOrderService;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("productionOrderConverter")
public class ProductionOrderConverter extends Converter<ProductionOrder> {
 
    @ManagedProperty(value = "#{productionOrderService}")
    private ProductionOrderService service;
    
    @Override
    @PostConstruct
    public void init() {
        addAll(service.getProductionOrders());
    }

    @Override
    public String getErrorMessage() {
        return "Order de produção inválida!!!";
    }

    @Override
    public String toString(ProductionOrder productionOrder) {
        return productionOrder.getReference();
    }

    public void setService(ProductionOrderService service) {
        this.service = service;
    }
    
}
