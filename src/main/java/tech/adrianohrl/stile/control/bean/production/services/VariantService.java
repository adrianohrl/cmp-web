/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.services;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.production.VariantDAO;
import tech.adrianohrl.stile.model.production.Variant;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@SessionScoped
public class VariantService extends Service<Variant> {

    @Override
    public String getErrorMessage() {
        return "Nenhuma variante foi cadastrada ainda!!!";
    }

    @Override
    protected List<Variant> getElements(EntityManager em) {
        VariantDAO variantDAO = new VariantDAO(em);
        return variantDAO.findAll();
    }
    
    public List<Variant> getVariants() {
        return getElements();
    }
    
}
