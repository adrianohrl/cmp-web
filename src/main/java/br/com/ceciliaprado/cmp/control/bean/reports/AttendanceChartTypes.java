/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.reports;

/**
 *
 * @author adrianohrl
 */
public enum AttendanceChartTypes implements ChartType<AttendanceChartTypes> {
    
    ATTENDANCE("Horas de trabalho diárias de ", "Horas de Trabalho", "[h]");
    
    private final String title;
    private final String label;
    private final String unit;    

    private AttendanceChartTypes(String title, String label, String unit) {
        this.title = title;
        this.label = label;
        this.unit = unit;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(String unit) {
        return this.unit.equalsIgnoreCase(unit);
    }
    
}
