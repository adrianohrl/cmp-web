package tech.adrianohrl.stile.control.bean;

import tech.adrianohrl.util.Calendars;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
        return Calendars.combine(startDate, startTime);
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
        return Calendars.combine(endDate, endTime);
    }
    
}
