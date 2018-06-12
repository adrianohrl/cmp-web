package tech.adrianohrl.stile.control.bean.reports;

import tech.adrianohrl.stile.control.model.production.reports.AbstractProductionReport;
import tech.adrianohrl.stile.control.model.production.reports.EfficiencySeriesTypes;
import tech.adrianohrl.stile.control.model.production.reports.SubordinateEfficiencyReport;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class EfficiencyReportBean extends AbstractReportBean<Subordinate, EfficiencyChartTypes, EfficiencySeriesTypes> {

    @Override
    protected EfficiencyChartTypes[] getChartTypes() {
        return EfficiencyChartTypes.values();
    }

    @Override
    protected EfficiencySeriesTypes[] getSeriesTypes() {
        return EfficiencySeriesTypes.values();
    }

    @Override
    protected EfficiencyChartTypes getChartType(EfficiencySeriesTypes seriesType) {
        switch (seriesType) {
            case EFFECTIVE_DURATION:
                return EfficiencyChartTypes.DURATION;
            case EXPECTED_DURATION:
                return EfficiencyChartTypes.DURATION;
            case FREE_DURATION:
                return EfficiencyChartTypes.DURATION;
            case TOTAL_DURATION:
                return EfficiencyChartTypes.DURATION;
            case PRODUCED_QUANTITY:
                return EfficiencyChartTypes.QUANTITY;
            case RETURNED_QUANTITY:
                return EfficiencyChartTypes.DURATION;
            case EFFECTIVE_EFFICIENCY:
                return EfficiencyChartTypes.EFFICIENCY;
            case TOTAL_EFFICIENCY:
                return EfficiencyChartTypes.EFFICIENCY;
        }
        return null;
    }

    @Override
    protected AbstractProductionReport<EfficiencySeriesTypes> getReport() throws ReportException {
        return new SubordinateEfficiencyReport(employee, events, manager, super.getStart(), super.getEnd());
    }

    @Override
    public String getLegend(EfficiencySeriesTypes seriesType) {
        switch (seriesType) {
            case EFFECTIVE_DURATION:
                return "Carga Horária Efetiva";
            case EXPECTED_DURATION:
                return "Carga Horária Esperada";
            case FREE_DURATION:
                return "Carga Horária Livre";
            case TOTAL_DURATION:
                return "Carga Horária Total";
            case PRODUCED_QUANTITY:
                return "Quantidade Produzida";
            case RETURNED_QUANTITY:
                return "Quantidade Retornada";
            case EFFECTIVE_EFFICIENCY:
                return "Eficiência Efetiva";
            case TOTAL_EFFICIENCY:
                return "Eficiência Total";
        }
        return null;
    }
    
}
