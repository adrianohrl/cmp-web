/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel.converters;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("sectorConverter")
public class SectorConverter implements Converter {
    
    private final List<Sector> sectors = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        EntityManager em = DataSource.createEntityManager();
        SectorDAO sectorDAO = new SectorDAO(em);
        sectors.addAll(sectorDAO.findAll());
        em.close();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (Sector sector : sectors) {
            if (value.equals(sector.getName())) {
                return sector;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            "Erro na conversão", "Setor inválido!!!");
        throw new ConverterException(message);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        return obj != null ? ((Sector) obj).getName() : null;
    }
    
}
