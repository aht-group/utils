package net.sf.ahtutils.model.primefaces;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.model.ListDataModel;

import net.sf.ahtutils.model.interfaces.EjbWithId;

import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class  PrimefacesEjbIdDataModel <T extends EjbWithId> extends ListDataModel<T> implements SelectableDataModel<T>
{    
	final static Logger logger = LoggerFactory.getLogger(PrimefacesEjbIdDataModel.class);
	
	private Map<Long,Boolean> mapUnlock;
	private Map<Long,Boolean> mapSelect;
   
	public PrimefacesEjbIdDataModel(List<T> data)
    {  
        super(data);
        mapUnlock = new Hashtable<Long,Boolean>();
        mapSelect = new Hashtable<Long,Boolean>();
    }  

    @SuppressWarnings("unchecked")
    @Override  
    public T getRowData(String rowKey)
    {  
    	List<T> list = (List<T>) getWrappedData();  
         
        for(T ejb : list)
        {  
            if(ejb.getId()==(new Integer(rowKey)))
            {
            	return ejb;
            }  
        }
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(T item) {return item.getId();}
    
    public void unlock(T item) {mapUnlock.put(item.getId(), true);}
    public void select(T item, boolean value) {mapSelect.put(item.getId(), value);}
    
    @SuppressWarnings("unchecked")
    public void update(T item)
    {
		List<T> list = (List<T>) getWrappedData();
		int index = list.indexOf(item);
		list.set(index, item);
		this.setWrappedData(list);
		this.setRowIndex(index);
    }
    
    public Map<Long, Boolean> getMapUnlock(){return mapUnlock;}
    public Map<Long, Boolean> getMapSelect() {return mapSelect;}
}
