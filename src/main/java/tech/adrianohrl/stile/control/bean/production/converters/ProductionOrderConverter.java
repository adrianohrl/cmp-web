package tech.adrianohrl.stile.control.bean.production.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
