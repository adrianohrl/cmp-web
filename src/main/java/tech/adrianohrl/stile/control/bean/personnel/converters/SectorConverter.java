/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.personnel.Sector;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("sectorConverter")
public class SectorConverter extends Converter<Sector> {
    
    @Override
    public String getErrorMessage() {
        return "Setor inválido!!!";
    }

    @Override
    public String toString(Sector sector) {
        return sector.getName();
    }
    
}
