package org.primefaces.showcase.view.data;
 
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
 
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
 
@ManagedBean
public class PickListView {
     
     
    private List<String> source = new  ArrayList<>();
    private List<String> target = new  ArrayList<>();
    private DualListModel<String> cities;

    
    @PostConstruct
    public void init() {
        source.add("San Francisco");
        source.add("London");
        source.add("Paris");
        source.add("Istanbul");
        source.add("Berlin");
        source.add("Barcelona");
        source.add("Rome");
         
        cities = new DualListModel<String>(source, target);
         
        //Themes
       
         
    }
    
 
    public DualListModel<String> getCities() {
        return cities;
    }
 
    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
    }
  
    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }
     
    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }
     
    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }


	public List<String> getSource() {
		return source;
	}


	public void setSource(List<String> source) {
		this.source = source;
	}


	public List<String> getTarget() {
		return target;
	}


	public void setTarget(List<String> terget) {
		this.target = terget;
	} 
}