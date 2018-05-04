/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean;

import tech.adrianohrl.stile.util.Calendars;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author adrianohrl
 */
public abstract class PeriodSearch implements Serializable {
    
    private final Date maxDate = new Date();
    protected Date startDate;
    private Date startTime = new GregorianCalendar(0, 0, 0).getTime();
    protected Date endDate;
    private Date endTime = new GregorianCalendar(0, 0, 0, 23, 59, 59).getTime();
    
    protected abstract void reset();
    
    public abstract void search();
    
    public Date getMaxDate() {
        return maxDate;
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
    
}
