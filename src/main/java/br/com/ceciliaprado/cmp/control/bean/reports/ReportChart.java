/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.ReportNumericSeries;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author adrianohrl
 */
public class ReportChart {
    
    private final LineChartModel chart = new LineChartModel();
    private final ChartType info;
    
    public ReportChart(ChartType info, String titleEnd, Date maxDate) {
        this.info = info;
        chart.setTitle(info.getTitle() + titleEnd);
        chart.setAnimate(true);
        chart.setZoom(true);
        DateAxis axis = new DateAxis("Tempo [dd/mm/aaaa]");
        axis.setTickAngle(-50);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        axis.setMax(formatter.format(maxDate));
        axis.setTickFormat("%d/%m/%Y");
        chart.getAxes().put(AxisType.X, axis);
        chart.getAxis(AxisType.Y).setLabel(info.getLabel() + " " + info.getUnit());        
    }

    private ReportChart(ChartType info, Date maxDate) {
        this(info, "", maxDate);
    }
    
    public void addSeries(ReportNumericSeries series, String name) {
        if (!info.equals(series.getUnit())) {
            return;
        }
        LineChartSeries chartSeries = new LineChartSeries();
        chartSeries.setLabel(name);
        chartSeries.setData(series.getChartSeries());
        chart.addSeries(chartSeries);
    }

    public LineChartModel getChart() {
        return chart;
    }
    
}
