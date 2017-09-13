/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("productionOrderConverter")
public class ProductionOrderConverter extends Converter<ProductionOrder> {

    @Override
    public String getErrorMessage() {
        return "Order de produção inválida!!!";
    }

    @Override
    public String toString(ProductionOrder productionOrder) {
        return productionOrder.getReference();
    }
    
}
