package org.jeesl.controller.handler.module.mdc;

import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.factory.builder.module.MdcFactoryBuilder;
import org.jeesl.factory.ejb.io.attribute.EjbAttributeContainerFactory;
import org.jeesl.factory.ejb.module.mdc.EjbMdcDataFactory;
import org.jeesl.factory.json.module.attribute.JsonAttributeContainerFactory;
import org.jeesl.factory.json.module.attribute.JsonAttributeSetFactory;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.module.mdc.JeeslMdcActivity;
import org.jeesl.interfaces.model.module.mdc.JeeslMdcData;
import org.jeesl.interfaces.model.module.mdc.JeeslMdcScope;
import org.jeesl.interfaces.model.module.mdc.JeeslMdcStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.module.attribute.JsonAttributeSet;
import org.jeesl.model.json.module.mdc.JsonMdcCollection;
import org.jeesl.model.json.module.mdc.JsonMdcData;
import org.jeesl.util.query.json.JsonAttributeQueryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdcJsonRestHandler<L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								COLLECTION extends JeeslMdcActivity<?,SCOPE,STATUS,ASET>,

								SCOPE extends JeeslMdcScope<L,D,?,SCOPE,?>,
								STATUS extends JeeslMdcStatus<L,D,STATUS,?>,
								
								CDATA extends JeeslMdcData<COLLECTION,CONTAINER>,
								
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE,OPTION>,
								TYPE extends JeeslStatus<TYPE,L,D>,
								OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
								ASET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
								ITEM extends JeeslAttributeItem<CRITERIA,ASET>,
								CONTAINER extends JeeslAttributeContainer<ASET,ADATA>,
								ADATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
{
	final static Logger logger = LoggerFactory.getLogger(MdcJsonRestHandler.class);
	
	private final EjbMdcDataFactory<COLLECTION,CDATA> efCollectionData;
	private final EjbAttributeContainerFactory<ASET,CONTAINER> efAttributeContainer;
	
	private final JsonAttributeSetFactory<L,D,LOC,CATEGORY,CRITERIA,TYPE,OPTION,ASET,ITEM> jfAttributeSet;
	private final JsonAttributeContainerFactory<L,D,LOC,CATEGORY,CRITERIA,TYPE,OPTION,ASET,ITEM,CONTAINER,ADATA> jfAttributeContainer;
	
	
	public MdcJsonRestHandler(MdcFactoryBuilder<L,D,LOC,?,COLLECTION,SCOPE,STATUS,CDATA,ASET> fbMdc,
								IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,ASET,ITEM,CONTAINER,ADATA> fbAttribute,
								JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,ASET,ITEM,CONTAINER,ADATA> fAttribute,
								String localeCode)
	{

		jfAttributeSet = new JsonAttributeSetFactory<>(localeCode,JsonAttributeQueryProvider.set());					jfAttributeSet.lazy(fAttribute,fbAttribute);
		jfAttributeContainer = new JsonAttributeContainerFactory<>(localeCode,JsonAttributeQueryProvider.container());	jfAttributeContainer.lazy(fAttribute,fbAttribute);
		
		efCollectionData = fbMdc.ejbData();
		efAttributeContainer = fbAttribute.ejbContainer();
	}
	
	public JsonAttributeSet attriubteSetHousehold(ASET set)
	{
		return jfAttributeSet.build(set);
	}

	public JsonMdcCollection collection(COLLECTION collection)
	{
		JsonMdcCollection json = new JsonMdcCollection();
		json.setName(collection.getName());
		json.setCollectionSet(jfAttributeSet.build(collection.getCollectionSet()));
		
		return json;
	}
	
	public JsonMdcData upload(COLLECTION collection, JsonMdcData data)
	{
		
		CONTAINER aContainer = efAttributeContainer.build(collection.getCollectionSet());
		
		
		
		CDATA cData = efCollectionData.build(collection);
		
		
		
		JsonMdcData result = new JsonMdcData();
		return result;
	}
}