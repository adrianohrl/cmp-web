/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.personnel.services.ManagerService;
import tech.adrianohrl.stile.control.dao.personnel.io.SupervisorsReaderDAO;
import tech.adrianohrl.stile.model.personnel.Manager;
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
public class ManagerImportBean implements Serializable {
    
    @ManagedProperty(value = "#{managerService}")
    private ManagerService service;
    private final List<Manager> managers = new ArrayList<>();
    
    public void upload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        UploadedFile file = event.getFile();
        EntityManager em = DataSource.createEntityManager();
        try {
            SupervisorsReaderDAO readerDAO = new SupervisorsReaderDAO(em);
            readerDAO.readFile(file.getInputstream());
            managers.addAll(readerDAO.getRegisteredManagers());
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

    public void setService(ManagerService service) {
        this.service = service;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    
    
}
