/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.events;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.dao.events.TimeClockEventDAO;
import br.com.ceciliaprado.cmp.control.model.production.reports.EmployeeAttendanceReport;
import br.com.ceciliaprado.cmp.exceptions.DAOException;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.util.Calendars;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class TimeClockEventsSearchBean implements Serializable {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final List<TimeClockEvent> events = new ArrayList<>();
    private Employee employee;
    private final Calendar maxDate = new GregorianCalendar();
    private Date startDate;
    private Date startTime = new GregorianCalendar(0, 0, 0).getTime();
    private Date endDate = maxDate.getTime();
    private Date endTime = new GregorianCalendar(0, 0, 0, 23, 59, 59).getTime();
    private LineChartModel attendance;
    private double totalDuration = 0.0;
    
    public void search() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        TimeClockEventDAO eventDAO = new TimeClockEventDAO(em);
        events.clear();
        try {
            events.addAll(eventDAO.findEmployeeEvents(employee, getStart(), getEnd()));
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resultado", 
                    events.size() + " registro(s) encontrado(s)!!!");
        } catch (DAOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", 
                    "A data e o horário iniciais da consulta deve ser antes da data e horário finais!!!");
        }  
        context.addMessage(null, message);
    }
    
    public void plot() {
        totalDuration = 0.0;
        search();
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        if (events.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sem atividades", 
                    "Não foi encontrada nenhuma atividade deste funcionário dentro do período dado!!!");
            context.addMessage(null, message);
            return;
        }
        attendance = new LineChartModel();
        LineChartSeries series = new LineChartSeries();
        series.setLabel("Horas trabalhadas");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            EmployeeAttendanceReport report = new EmployeeAttendanceReport(employee, events, null, getStart(), getEnd());
            totalDuration = report.getTotalDuration() / 60.0;
            Map<Calendar, Double> data = report.getDailyTotalDuration();
            for (Map.Entry<Calendar, Double> d : data.entrySet()) {
                series.set(dateFormatter.format(d.getKey().getTime()), d.getValue() / 60.0);
            }
            attendance.addSeries(series);
            attendance.setTitle("Horas trabalhadas diárias");
            attendance.setAnimate(true);
            attendance.setZoom(true);
            DateAxis axis = new DateAxis("Tempo [dd/mm/aaaa]");
            axis.setTickAngle(-50);
            axis.setMax(dateFormatter.format(endDate));
            axis.setTickFormat("%d/%m/%Y");
            attendance.getAxes().put(AxisType.X, axis);
            attendance.getAxis(AxisType.Y).setLabel("Hora [h]");
        } catch (ReportException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro na geração dos dados", e.getMessage());
            context.addMessage(null, message);
        }
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    public List<TimeClockEvent> getEvents() {
        return events;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public Date getMaxDate() {
        return maxDate.getTime();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Calendar getStart() {
        return Calendars.sum(startDate, startTime);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Calendar getEnd() {
        return Calendars.sum(endDate, endTime);
    }

    public LineChartModel getAttendance() {
        return attendance;
    }

    public double getTotalDuration() {
        return totalDuration;
    }
    
}
