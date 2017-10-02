/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.ReportNumericSeries;
import br.com.ceciliaprado.cmp.control.model.production.reports.SeriesType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author adrianohrl
 * @param <S>
 */
public class ReportChart<S extends SeriesType> {
    
    private final LineChartModel chart = new LineChartModel();
    private final ChartType chartType;
    private final List<S> seriesTypes = new ArrayList<>();
    
    public ReportChart(ChartType chartType, String titleEnd, Date maxDate) {
        this.chartType = chartType;
        chart.setTitle(chartType.getTitle() + titleEnd);
        chart.setAnimate(true);
        chart.setZoom(true);
        chart.setLegendPosition("ne");
        DateAxis axis = new DateAxis("Tempo [dd/mm/aaaa]");
        axis.setTickAngle(-50);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        axis.setMax(formatter.format(maxDate));
        axis.setTickFormat("%d/%m/%Y");
        chart.getAxes().put(AxisType.X, axis);
        chart.getAxis(AxisType.Y).setLabel(chartType.getLabel() + " " + chartType.getUnit());        
    }

    private ReportChart(ChartType chartType, Date maxDate) {
        this(chartType, "", maxDate);
    }
    
    public void addSeries(ReportNumericSeries<S> series, String name) {
        if (!chartType.equals(series.getUnit())) {
            return;
        }
        LineChartSeries chartSeries = new LineChartSeries();
        chartSeries.setLabel(name);
        chartSeries.setData(series.getChartSeries());
        chart.addSeries(chartSeries);
        seriesTypes.add(series.getType());
    }

    public LineChartModel getChart() {
        return chart;
    }

    public List<S> getSeriesTypes() {
        return seriesTypes;
    }
    
}
