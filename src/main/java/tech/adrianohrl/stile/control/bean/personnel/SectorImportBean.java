package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.personnel.services.SectorService;
import tech.adrianohrl.stile.control.dao.personnel.io.SectorsReaderDAO;
import tech.adrianohrl.stile.model.personnel.Sector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class SectorImportBean implements Serializable {
    
    @ManagedProperty(value = "#{sectorService}")
    private SectorService service;
    private final List<Sector> sectors = new ArrayList<>();
    
    public void upload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        UploadedFile file = event.getFile();
        EntityManager em = DataSource.createEntityManager();
        try {
            SectorsReaderDAO readerDAO = new SectorsReaderDAO(em);
            readerDAO.readFile(file.getInputstream());
            sectors.addAll(readerDAO.getRegisteredSectors());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso no upload", 
                    "O arquivo " + event.getFile().getFileName() + " foi importado para a aplicação!!!");
            update();
        } catch (java.io.IOException | tech.adrianohrl.stile.exceptions.IOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no upload", 
                    "Não foi possível fazer o upload do arquivo " + event.getFile().getFileName() + ": " + e.getMessage());
            System.out.println("IOException catched during importation: " + e.getMessage());
        }
        em.close();
        context.addMessage(null, message);
    }

    public void update() {
        service.update();
    }

    public void setService(SectorService service) {
        this.service = service;
    }

    public List<Sector> getSectors() {
        return sectors;
    }
    
}
