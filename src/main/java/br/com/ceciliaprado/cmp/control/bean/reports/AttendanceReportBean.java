/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.AbstractProductionReport;
import br.com.ceciliaprado.cmp.control.model.production.reports.AttendanceSeriesTypes;
import br.com.ceciliaprado.cmp.control.model.production.reports.EmployeeAttendanceReport;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author adrianohrl
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
