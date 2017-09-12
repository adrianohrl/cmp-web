/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.io.EntryEventsReaderDAO;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.production.ProductionStates;
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
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class EntryImportBean implements Serializable {
    
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
        } catch (java.io.IOException | br.com.ceciliaprado.cmp.exceptions.IOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no upload", 
                    "Não foi possível fazer o upload do arquivo " + event.getFile().getFileName() + "!!!");
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
