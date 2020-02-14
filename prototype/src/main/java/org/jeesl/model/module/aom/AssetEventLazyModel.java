package org.jeesl.model.module.aom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.controller.handler.th.ThMultiFilterHandler;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.jsf.util.JeeslLazyListHandler;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetEventLazyModel <ASSET extends JeeslAomAsset<?,ASSET,?,?,?>,
										
									EVENT extends JeeslAomEvent<?,ASSET,ETYPE,ESTATUS>,
									ETYPE extends JeeslAomEventType<?,?,ETYPE,?>,
									ESTATUS extends JeeslAomEventStatus<?,?,ESTATUS,?>>
					extends LazyDataModel<EVENT>
{
	final static Logger logger = LoggerFactory.getLogger(AssetEventLazyModel.class);

	private static final long serialVersionUID = 1L;

	private final List<EVENT> list;
	private final JeeslLazyListHandler<EVENT> llh;
//	private final JeeslEjbFilter<COMPANY> filter;
	private final Comparator<EVENT> cpEvent;
	private final ThMultiFilterHandler<ETYPE> thfEventType; 
	
	public AssetEventLazyModel(Comparator<EVENT> cpEvent, ThMultiFilterHandler<ETYPE> thfEventType)
	{
		this.cpEvent=cpEvent;
		this.thfEventType=thfEventType;
        llh = new JeeslLazyListHandler<>();
        list = new ArrayList<>();
	}
	
	@Override public EVENT getRowData(String rowKey){return llh.getRowData(list,rowKey);}
    @Override public Object getRowKey(EVENT account) {return llh.getRowKey(account);}
    public void clear() {list.clear();}
	
    public void setScope(JeeslAssetFacade<?,?,?,?,?,ASSET,?,?,EVENT,ETYPE,ESTATUS> fAsset, ASSET asset)
    {
		this.clear();
		list.addAll(fAsset.fAssetEvents(asset));
		Collections.sort(list,cpEvent);
		logger.info("Reloaded "+list.size());
    }

	@Override public List<EVENT> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)
	{
		llh.clear();
		for(EVENT event : list)
		{
			boolean thfTypeMatches = true;
			if(thfEventType!=null) {thfTypeMatches = thfEventType.isSelected(event.getType());} 
//			if(filter.matches(filters,item))
			if(thfTypeMatches){llh.add(event);}
		}
		
		if(sortField != null)
		{
          
		}
		else
		{
			logger.info("Default Sorting");
//			Collections.sort(llh.getTmp(),cpBeneficiary);
		}

		this.setRowCount(llh.size());
		
		List<EVENT> x = llh.paginator(first,pageSize);
		logger.info("Rows "+this.getRowCount()+" x:"+x.size());
		return x;
	}
}