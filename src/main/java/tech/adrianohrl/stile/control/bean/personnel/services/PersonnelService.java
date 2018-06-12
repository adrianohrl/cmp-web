package tech.adrianohrl.stile.control.bean.personnel.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.model.personnel.Employee;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <E>
 */
public abstract class PersonnelService<E extends Employee> extends Service<E> {
    
    @ManagedProperty(value = "#{employeeService}")
    private EmployeeService service;
    
    @Override
    public void update() {
        super.update();
        service.update();
    }
    
    @Override
    public void update(E employee) {
        super.update(employee);
        service.update(employee);
    }

    public void setService(EmployeeService service) {
        this.service = service;
    }
    
}
