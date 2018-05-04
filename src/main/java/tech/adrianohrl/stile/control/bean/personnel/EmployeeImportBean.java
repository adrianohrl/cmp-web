/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.personnel.services.ManagerService;
import tech.adrianohrl.stile.control.bean.personnel.services.SubordinateService;
import tech.adrianohrl.stile.control.bean.personnel.services.SupervisorService;
import tech.adrianohrl.stile.control.dao.personnel.io.PersonnelReaderDAO;
import tech.adrianohrl.stile.model.personnel.Employee;
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
public class EmployeeImportBean implements Serializable {
    
    @ManagedProperty(value = "#{subordinateService}")
    private SubordinateService subordinateService;
    @ManagedProperty(value = "#{supervisorService}")
    private SupervisorService supervisorService;
    @ManagedProperty(value = "#{managerService}")
    private ManagerService managerService;
    private final List<Employee> employees = new ArrayList<>();
    
    public void upload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        UploadedFile file = event.getFile();
        EntityManager em = DataSource.createEntityManager();
        try {
            PersonnelReaderDAO readerDAO = new PersonnelReaderDAO(em);
            readerDAO.readFile(file.getInputstream());
            employees.addAll(readerDAO.getRegisteredEmployees());
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
        subordinateService.update();
        supervisorService.update();
        managerService.update();
    }

    public List<Employee> getEmployees() {
        return employees;
    }
    
    public String toString(String employeeType) {
        String str = "";
        switch (employeeType) {
            case "Subordinate":
                str = "Subordinado";
                break;
            case "Supervisor":
                str = "Supervisor";
                break;
            case "Manager":
                str = "Gerente";
                break;
        }
        return str;
    }

    public void setSubordinateService(SubordinateService subordinateService) {
        this.subordinateService = subordinateService;
    }

    public void setSupervisorService(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }
    
}
