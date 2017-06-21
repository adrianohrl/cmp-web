/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.Phase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class ModelPhaseView implements Serializable {
    
    private final ModelPhase phase = new ModelPhase();
    @ManagedProperty("#{modelBean}")
    private ModelBean modelBean;
    private final List<Phase> phases = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        phases.addAll(modelBean.getPhases());
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> params = context.getExternalContext().getRequestMap();
        if (!params.containsKey("phases")) {
            RequestContext.getCurrentInstance().closeDialog(null);
            return;
        }
        phases.addAll((List<Phase>) params.get("phases"));
        if (phases.isEmpty()) {
            phases.addAll(modelBean.getPhases());
            RequestContext.getCurrentInstance().closeDialog(null);
        }
    }
    
    public void closeDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.closeDialog(phase);
    }

    public ModelPhase getPhase() {
        return phase;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public ModelBean getModelBean() {
        return modelBean;
    }

    public void setModelBean(ModelBean modelBean) {
        this.modelBean = modelBean;
    }
    
}
