/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.production.services.ModelService;
import tech.adrianohrl.stile.control.dao.production.io.ModelsReaderDAO;
import tech.adrianohrl.stile.model.production.Model;
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
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class ModelImportBean implements Serializable {
    
    @ManagedProperty(value = "#{modelService}")
    private ModelService service;
    private final List<Model> models = new ArrayList<>();
    
    public void upload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        UploadedFile file = event.getFile();
        EntityManager em = DataSource.createEntityManager();
        try {
            ModelsReaderDAO readerDAO = new ModelsReaderDAO(em);
            readerDAO.readFile(file.getInputstream());
            models.addAll(readerDAO.getRegisteredModels());
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

    public ModelService getService() {
        return service;
    }

    public void setService(ModelService service) {
        this.service = service;
    }

    public List<Model> getModels() {
        return models;
    }
    
}
