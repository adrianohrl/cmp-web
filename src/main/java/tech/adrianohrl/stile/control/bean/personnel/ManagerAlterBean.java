/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.personnel.services.ManagerService;
import tech.adrianohrl.stile.model.personnel.Manager;
import java.util.Iterator;
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
    protected void update() {
        service.update();
    }

    @Override
    protected List<Manager> getLoggables() {
        return service.getManagers();
    }

    public void setService(ManagerService service) {
        this.service = service;
    }

    @Override
    public Iterator<Manager> iterator() {
        return service.iterator();
    }
    
}
