/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.model.production.Phase;
import java.util.List;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("phaseConverter")
public class PhaseConverter extends Converter<Phase> {

    @Override
    protected List<Phase> getElements() {
        EntityManager em = DataSource.createEntityManager();
        PhaseDAO phaseDAO = new PhaseDAO(em);
        List<Phase> phases = phaseDAO.findAll();
        em.close();
        return phases;
    }

    @Override
    public String getErrorMessage() {
        return "Fase inválida!!!";
    }

    @Override
    public String toString(Phase phase) {
        return phase.getName();
    }
    
}
