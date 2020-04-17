package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.module.its.config.JeeslItsConfig;
import org.jeesl.interfaces.model.module.its.config.JeeslItsConfigOption;
import org.jeesl.interfaces.model.module.its.task.JeeslItsTask;
import org.jeesl.interfaces.model.module.its.task.JeeslItsTaskType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslItsFacade <L extends JeeslLang, D extends JeeslDescription,
									R extends JeeslMcsRealm<L,D,R,?>,
									C extends JeeslItsConfig<L,D,R,O>,
									O extends JeeslItsConfigOption<L,D,O,?>,
									I extends JeeslItsIssue<R,I>,
									STATUS extends JeeslItsIssueStatus<L,D,R,STATUS,?>,
									T extends JeeslItsTask<I,TT,?>,
									TT extends JeeslItsTaskType<L,D,TT,?>>
			extends JeeslFacade
{	
	<RREF extends EjbWithId> I fcAItsRoot(R realm, RREF rref);
}