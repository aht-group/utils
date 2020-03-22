package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslItsFacade <L extends JeeslLang, D extends JeeslDescription,
									R extends JeeslMcsRealm<L,D,R,?>,
									I extends JeeslItsIssue<R,I>>
			extends JeeslFacade
{	
	<RREF extends EjbWithId> I fcAItsRoot(R realm, RREF rref);
}