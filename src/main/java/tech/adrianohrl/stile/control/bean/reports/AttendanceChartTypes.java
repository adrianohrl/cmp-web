package tech.adrianohrl.stile.control.bean.reports;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
