package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.dao.events.io.EntryEventsReaderDAO;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.order.ProductionStates;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
public class EntryEventsImportBean implements Serializable {
    
    private final List<EntryEvent> events = new ArrayList<>();
    
    public void upload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        UploadedFile file = event.getFile();
        EntityManager em = DataSource.createEntityManager();
        try {
            EntryEventsReaderDAO readerDAO = new EntryEventsReaderDAO(em);
            readerDAO.readFile(file.getInputstream());
            events.addAll(readerDAO.getRegisteredEvents());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso no upload", 
                    "O arquivo " + event.getFile().getFileName() + " foi importado para a aplicação!!!");
        } catch (java.io.IOException | tech.adrianohrl.stile.exceptions.IOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no upload", 
                    "Não foi possível fazer o upload do arquivo " + event.getFile().getFileName() + ": " + e.getMessage());
            System.out.println("IOException catched during importation: " + e.getMessage());
        }
        em.close();
        context.addMessage(null, message);
    }
    
    public String toString(ProductionStates state) {
        if (state == null) {
            return "";
        }
        String str = "";
        switch (state) {
            case STARTED:
                str = "Início";
                break;
            case RESTARTED:
                str = "Reinício";
                break;
            case PAUSED:
                str = "Pausa";
                break;
            case FINISHED:
                str = "Término";
                break;
            case RETURNED:
                str = "Devolução";
                break;
        }
        return str;
    }

    public List<EntryEvent> getEvents() {
        return events;
    }
    
}
