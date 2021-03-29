package org.jeesl.controller.handler.module.mdc;

import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.factory.json.module.attribute.JsonAttributeContainerFactory;
import org.jeesl.factory.json.module.attribute.JsonAttributeSetFactory;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.json.module.attribute.JsonAttributeSet;
import org.jeesl.util.query.json.JsonAttributeQueryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdcJsonRestHandler<L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE,OPTION>,
								TYPE extends JeeslStatus<TYPE,L,D>,
								OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
								SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
								ITEM extends JeeslAttributeItem<CRITERIA,SET>,
								CONTAINER extends JeeslAttributeContainer<SET,DATA>,
								DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
{
	final static Logger logger = LoggerFactory.getLogger(MdcJsonRestHandler.class);
	
	private final JsonAttributeSetFactory<L,D,LOC,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM> jfAttributeSet;
	private final JsonAttributeContainerFactory<L,D,LOC,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> jfAttributeContainer;
	
	
	public MdcJsonRestHandler(IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute,
								JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fAttribute,
								String localeCode)
	{

		jfAttributeSet = new JsonAttributeSetFactory<>(localeCode,JsonAttributeQueryProvider.set());					jfAttributeSet.lazy(fAttribute,fbAttribute);
		jfAttributeContainer = new JsonAttributeContainerFactory<>(localeCode,JsonAttributeQueryProvider.container());	jfAttributeContainer.lazy(fAttribute,fbAttribute);
	}
	
	public JsonAttributeSet attriubteSetHousehold(SET set)
	{
		return jfAttributeSet.build(set);
	}

}