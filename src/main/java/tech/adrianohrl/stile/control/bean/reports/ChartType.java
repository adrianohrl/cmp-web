package tech.adrianohrl.stile.control.bean.reports;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public interface ChartType<T extends Enum<T>> extends Comparable<T> {
    
    public abstract String getTitle();
    
    public abstract String getLabel();
    
    public abstract String getUnit();
    
    @Override
    public abstract String toString();
    
    public abstract boolean equals(String unit);
    
}
