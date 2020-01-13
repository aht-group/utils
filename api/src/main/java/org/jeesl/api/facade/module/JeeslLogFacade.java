package org.jeesl.api.facade.module;

import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.module.log.JeeslLogBook;
import org.jeesl.interfaces.model.module.log.JeeslLogConfidentiality;
import org.jeesl.interfaces.model.module.log.JeeslLogImpact;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.jeesl.interfaces.model.module.log.JeeslLogScope;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLogFacade <L extends UtilsLang, D extends UtilsDescription,
									BOOK extends JeeslLogBook<SCOPE,ITEM>,
									SCOPE extends JeeslLogScope<L,D,SCOPE,?>,
									ITEM extends JeeslLogItem<L,D,?,?,BOOK,IMPACT,CONF,USER>,
									IMPACT extends JeeslLogImpact<L,D,IMPACT,?>,
									CONF extends JeeslLogConfidentiality<L,D,CONF,?>,
									USER extends EjbWithId
									>
			extends UtilsFacade
{	
	List<ITEM> fLogItems(List<BOOK> books);
	List<ITEM> fLogItems(List<BOOK> books, List<SCOPE> scopes, List<CONF> confidentialities, Date startDate, Date endDate);
}