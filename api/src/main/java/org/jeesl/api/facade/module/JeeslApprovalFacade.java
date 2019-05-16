package org.jeesl.api.facade.module;

import org.jeesl.interfaces.model.module.approval.JeeslApprovalCommunication;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalContext;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalStage;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalTransition;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslApprovalFacade <L extends UtilsLang, D extends UtilsDescription,
									CTX extends JeeslApprovalContext<CTX,L,D,?>,
									P extends JeeslApprovalProcess<L,D,CTX>,
									S extends JeeslApprovalStage<L,D,P>,
									T extends JeeslApprovalTransition<L,D,S>,
									C extends JeeslApprovalCommunication<T>>
			extends UtilsFacade
{	
	
}