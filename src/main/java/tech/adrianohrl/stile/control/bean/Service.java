package tech.adrianohrl.stile.control.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <E>
 */
public abstract class Service<E extends Comparable> implements Serializable, Iterable<E> {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final List<E> elements = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        elements.addAll(getElements(em));
        if (elements.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Fatalidade", getErrorMessage());
            context.addMessage(null, message);
            System.out.println("Fatalidade: " + getErrorMessage());
            return;
        }
        Collections.sort(elements);
    }
    
    public void update() {
        elements.clear();
        elements.addAll(getElements(em));
        Collections.sort(elements);
    }
    
    public void update(E element) {
        elements.add(element);
        Collections.sort(elements);
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    protected List<E> getElements() {
        return elements;
    }
    
    public abstract String getErrorMessage();
    
    protected abstract List<E> getElements(EntityManager em);

    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }
    
}
