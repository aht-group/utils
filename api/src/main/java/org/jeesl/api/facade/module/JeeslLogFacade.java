package org.jeesl.api.facade.module;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.log.JeeslLogBook;
import org.jeesl.interfaces.model.module.log.JeeslLogConfidentiality;
import org.jeesl.interfaces.model.module.log.JeeslLogImpact;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.jeesl.interfaces.model.module.log.JeeslLogScope;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLogFacade <L extends JeeslLang, D extends JeeslDescription,
									BOOK extends JeeslLogBook<SCOPE,ITEM>,
									SCOPE extends JeeslLogScope<L,D,SCOPE,?>,
									ITEM extends JeeslLogItem<L,D,?,?,BOOK,IMPACT,CONF,USER>,
									IMPACT extends JeeslLogImpact<L,D,IMPACT,?>,
									CONF extends JeeslLogConfidentiality<L,D,CONF,?>,
									USER extends EjbWithId
									>
			extends JeeslFacade
{	
	List<ITEM> fLogItems(List<BOOK> books);
	List<ITEM> fLogItems(List<BOOK> books, List<SCOPE> scopes, List<CONF> confidentialities, Date startDate, Date endDate);
}