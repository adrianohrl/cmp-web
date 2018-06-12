package tech.adrianohrl.stile.control.bean.personnel.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.personnel.Employee;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@FacesConverter("employeeConverter")
public class EmployeeConverter extends Converter<Employee> {

    @Override
    public String getErrorMessage() {
        return "Funcionário inválido!!!";
    }

    @Override
    public String toString(Employee employee) {
        return employee.getName();
    }
    
}
