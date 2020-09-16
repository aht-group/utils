package org.jeesl.factory.json.module.attribute;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.module.attribute.JsonAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonAttributeSetFactory<L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>,
									CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE,OPTION>,
									TYPE extends JeeslStatus<TYPE,L,D>,
									OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
									SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
									ITEM extends JeeslAttributeItem<CRITERIA,SET>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonAttributeSetFactory.class);
	
	private final JsonAttributeSet q;
	
	private JeeslFacade facade;
	private IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,?,?> fbAttribute;
	
	private JsonAttributeItemFactory<L,D,LOC,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM> jfItem;
	
	public JsonAttributeSetFactory(String localeCode, JsonAttributeSet q)
	{
		this.q=q;
		if(q.getItems()!=null && !q.getItems().isEmpty()) {jfItem = new JsonAttributeItemFactory<>(localeCode,q.getItems().get(0));}
	}
	
	public void lazy(JeeslFacade facade, IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,?,?> fbAttribute)
	{
		this.facade=facade;
		this.fbAttribute=fbAttribute;
		if(jfItem!=null) {jfItem.lazy(facade,fbAttribute);}
	}
	
	public static JsonAttributeSet build(){return new JsonAttributeSet();}
	
	public JsonAttributeSet build(SET set)
	{
		JsonAttributeSet json = build();
		if(q.getId()!=null) {json.setId(set.getId());}
		
		if(q.getItems()!=null && !q.getItems().isEmpty())
		{
			json.setItems(new ArrayList<>());
			
			List<ITEM> items = new ArrayList<>();
			if(facade==null) {items.addAll(set.getItems());}
			else {items.addAll(facade.allForParent(fbAttribute.getClassItem(),set));}
			for(ITEM item : items)
			{
				json.getItems().add(jfItem.build(item));
			}
		}
		
		
		return json;
	}
}