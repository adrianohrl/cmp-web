/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class ManagerAlterBean extends AlterBean<Manager> {
    
    
    @ManagedProperty(value = "#{managerService}")
    private ManagerService service;

    @Override
    protected List<Manager> getLoggables() {
        return service.getManagers();
    }

    public void setManagerService(ManagerService managerService) {
        this.service = managerService;
    }
    
}
