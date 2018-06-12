package tech.adrianohrl.stile.control.bean.reports;

import tech.adrianohrl.stile.control.bean.events.AbstractEventsPeriodSearchBean;
import tech.adrianohrl.stile.control.model.production.reports.AbstractProductionReport;
import tech.adrianohrl.stile.control.model.production.reports.ReportNumericSeries;
import tech.adrianohrl.stile.control.model.production.reports.SeriesType;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 * @param <C>
 * @param <S>
 */
public abstract class AbstractReportBean<T extends Employee, C extends ChartType, S extends SeriesType> extends AbstractEventsPeriodSearchBean<T, AbstractEmployeeRelatedEvent> {
    
    protected Manager manager = new Manager();
    private final Map<C, ReportChart<S>> dailySeriesCharts = new TreeMap<>();
    private final Map<S, Number> totals = new TreeMap<>();
    
    @Override
    protected void reset() {
        super.reset();
        dailySeriesCharts.clear();
        totals.clear();
        for (C chartType : getChartTypes()) {
            dailySeriesCharts.put(chartType, new ReportChart(chartType, employee.getName(), endDate));
        }
        for (S seriesType : getSeriesTypes()) {
            totals.put(seriesType, 0.0);
        }
    }
    
    protected abstract C[] getChartTypes();
    
    protected abstract S[] getSeriesTypes();
    
    protected abstract C getChartType(S seriesType);
    
    protected abstract AbstractProductionReport<S> getReport() throws ReportException;
    
    public void plot() {
        search();
        FacesMessage message;
        if (!events.isEmpty()) {
            try {
                AbstractProductionReport<S> report = getReport();
                for (ReportNumericSeries<S> series : report) {
                    C chartType = getChartType((S) series.getType());
                    addDailySeries(chartType, series);
                }            
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Relatório gerado com sucesso!!!");
            } catch (ReportException e) {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro na geração dos dados", e.getMessage());
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sem atividades", 
                    "Não foi encontrada nenhuma atividade deste funcionário dentro do período dado!!!");
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
    }    

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public LineChartModel getDailySeriesChart(C chartType) {
        return dailySeriesCharts.get(chartType).getChart();
    }
    
    public Number getTotal(S seriesType) {
        return totals.get(seriesType);
    }

    private void addDailySeries(C chartType, ReportNumericSeries<S> dailySeries) {
        ReportChart<S> chart = dailySeriesCharts.get(chartType);
        chart.addSeries(dailySeries, getLegend(dailySeries.getType()));
        totals.put(dailySeries.getType(), dailySeries.getTotal());
    }
    
    public List<S> getSeriesTypes(C chartType) {
        return dailySeriesCharts.get(chartType).getSeriesTypes();
    }
        
    public abstract String getLegend(S seriesType);
    
}
