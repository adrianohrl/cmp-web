/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import java.util.List;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("casualtyConverter")
public class CasualtyConverter extends Converter<Casualty> {

    @Override
    protected List<Casualty> getElements() {
        EntityManager em = DataSource.createEntityManager();
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findAll();
        em.close();
        return casualties;
    }

    @Override
    public String getErrorMessage() {
        return "Sinistro inválido!!!";
    }

    @Override
    public String toString(Casualty casualty) {
        return casualty.getName();
    }
    
}
