package tech.adrianohrl.stile.control.bean.reports;

import tech.adrianohrl.stile.control.model.production.reports.AbstractProductionReport;
import tech.adrianohrl.stile.control.model.production.reports.AttendanceSeriesTypes;
import tech.adrianohrl.stile.control.model.production.reports.EmployeeAttendanceReport;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.personnel.Employee;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class AttendanceReportBean extends AbstractReportBean<Employee, AttendanceChartTypes, AttendanceSeriesTypes> {   

    @Override
    protected AttendanceChartTypes[] getChartTypes() {
        return AttendanceChartTypes.values();
    }

    @Override
    protected AttendanceSeriesTypes[] getSeriesTypes() {
        return AttendanceSeriesTypes.values();
    }

    @Override
    protected AttendanceChartTypes getChartType(AttendanceSeriesTypes seriesType) {
        return AttendanceChartTypes.ATTENDANCE;
    }

    @Override
    protected AbstractProductionReport<AttendanceSeriesTypes> getReport() throws ReportException {
        return new EmployeeAttendanceReport(employee, events, manager, super.getStart(), super.getEnd());
    }

    @Override
    public String getLegend(AttendanceSeriesTypes seriesType) {
        return "Carga Horária";
    }
    
}
