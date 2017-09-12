/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.services;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ApplicationScoped
public class SectorService extends Service<Sector> {

    @Override
    public String getErrorMessage() {
        return "Nenhum setor foi cadastrado ainda!!!";
    }

    @Override
    protected List<Sector> getElements(EntityManager em) {
        SectorDAO sectorDAO = new SectorDAO(em);
        return sectorDAO.findAll();
    }

    public List<Sector> getSectors() {
        return getElements();
    }
    
}
