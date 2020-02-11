package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslIoAttributeFacade <L extends JeeslLang, D extends JeeslDescription,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
										TYPE extends JeeslStatus<TYPE,L,D>,
										OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
										SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
										ITEM extends JeeslAttributeItem<CRITERIA,SET>,
										CONTAINER extends JeeslAttributeContainer<SET,DATA>,
										DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
			extends JeeslFacade
{	
	SET load(SET set);
	List<CRITERIA> fAttributeCriteria(List<CATEGORY> categories, long refId);
	List<SET> fAttributeSets(List<CATEGORY> categories, long refId);
	List<DATA> fAttributeData(CONTAINER container);
	DATA fAttributeData(CRITERIA criteria, CONTAINER container) throws JeeslNotFoundException;
	List<DATA> fAttributeData(CRITERIA criteria, List<CONTAINER> containers);
	CONTAINER copy(CONTAINER container) throws JeeslConstraintViolationException, JeeslLockingException;
}