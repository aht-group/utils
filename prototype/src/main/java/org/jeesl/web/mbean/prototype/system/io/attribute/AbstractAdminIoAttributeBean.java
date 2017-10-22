package org.jeesl.web.mbean.prototype.system.io.attribute;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslAttributeBean;
import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.AttributeFactoryBuilder;
import org.jeesl.factory.ejb.module.attribute.EjbAttributeCriteriaFactory;
import org.jeesl.factory.ejb.system.io.attribute.EjbAttributeItemFactory;
import org.jeesl.factory.ejb.system.io.attribute.EjbAttributeSetFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractAdminIoAttributeBean <L extends UtilsLang, D extends UtilsDescription,
													CATEGORY extends UtilsStatus<CATEGORY,L,D>,
													CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
													TYPE extends UtilsStatus<TYPE,L,D>,
													SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
													ITEM extends JeeslAttributeItem<CRITERIA,SET>,
													CONTAINER extends JeeslAttributeContainer<SET,DATA>,
													DATA extends JeeslAttributeData<CRITERIA,CONTAINER>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoAttributeBean.class);
	
	protected JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fAttribute;
	protected JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> bAttribute;
	protected final AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fbAttribute;
	
	protected final SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	
	protected final EjbAttributeCriteriaFactory<L,D,CATEGORY,CRITERIA,TYPE> efCriteria;
	protected final EjbAttributeSetFactory<L,D,CATEGORY,SET,ITEM> efSet;
	protected final EjbAttributeItemFactory<CRITERIA,SET,ITEM> efItem;

	public AbstractAdminIoAttributeBean(AttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fbAttribute)
	{
		super(fbAttribute.getClassL(),fbAttribute.getClassD());
		this.fbAttribute=fbAttribute;
		
		efCriteria = fbAttribute.ejbCriteria();
		efSet = fbAttribute.ejbSet();
		efItem = fbAttribute.ejbItem();
		
		sbhCategory = new SbMultiHandler<CATEGORY>(fbAttribute.getClassCategory(),this);
	}
	
	protected void initAttribute(String[] localeCodes, FacesMessageBean bMessage, JeeslAttributeBean<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> bAttribute, JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,SET,ITEM,CONTAINER,DATA> fAttribute)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);
		this.fAttribute=fAttribute;
		this.bAttribute=bAttribute;
		sbhCategory.setList(fAttribute.allOrderedPositionVisible(fbAttribute.getClassCategory()));
		sbhCategory.toggleNone();
	}
}