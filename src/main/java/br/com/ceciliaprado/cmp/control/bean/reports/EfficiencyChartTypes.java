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
public enum EfficiencyChartTypes implements ChartType<EfficiencyChartTypes> {

    DURATION("Horas na produção diária de ", "Duração", "[h]"), 
    QUANTITY("Quantidade de peças diária trabalhadas por ", "Quantidade", "[un]"), 
    EFFICIENCY("Eficiência diária de ", "Eficiência", "[%]"); 
    
    private final String title;
    private final String label;
    private final String unit;

    private EfficiencyChartTypes(String title, String label, String unit) {
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
