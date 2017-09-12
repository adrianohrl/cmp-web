/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.Converter;
import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import java.util.List;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("sectorConverter")
public class SectorConverter extends Converter<Sector> {

    @Override
    protected List<Sector> getElements() {
        EntityManager em = DataSource.createEntityManager();
        SectorDAO sectorDAO = new SectorDAO(em);
        List<Sector> sectors = sectorDAO.findAll();
        em.close();
        return sectors;
    }
    
    @Override
    public String getErrorMessage() {
        return "Setor inválido!!!";
    }

    @Override
    public String toString(Sector sector) {
        return sector.getName();
    }
    
}
