/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.personnel.services.SupervisorService;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
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
public class SupervisorAlterBean extends AlterBean<Supervisor> {
    
    @ManagedProperty(value = "#{supervisorService}")
    private SupervisorService service;

    @Override
    protected void update() {
        service.update();
    }

    @Override
    protected List<Supervisor> getLoggables() {
        return service.getSupervisors();
    }

    public void setService(SupervisorService service) {
        this.service = service;
    }

    @Override
    public Iterator<Supervisor> iterator() {
        return service.iterator();
    }
    
}
