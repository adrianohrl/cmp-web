package tech.adrianohrl.stile.control.bean.personnel.services;

import tech.adrianohrl.stile.control.bean.Service;
import tech.adrianohrl.stile.control.dao.personnel.EmployeeDAO;
import tech.adrianohrl.stile.model.personnel.Employee;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@SessionScoped
public class EmployeeService extends Service<Employee> {

    @Override
    public String getErrorMessage() {
        return "Nenhum funcionário foi cadastrado ainda!!!";
    }

    public List<Employee> getEmployees() {
        return getElements();
    }

    @Override
    protected List<Employee> getElements(EntityManager em) {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        return employeeDAO.findAll();
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
    
}
