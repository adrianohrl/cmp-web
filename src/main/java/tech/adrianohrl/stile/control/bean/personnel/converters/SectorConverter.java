package tech.adrianohrl.stile.control.bean.personnel.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.personnel.Sector;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
