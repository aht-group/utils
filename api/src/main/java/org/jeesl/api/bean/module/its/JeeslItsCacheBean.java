package org.jeesl.api.bean.module.its;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.its.issue.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslItsCacheBean <L extends JeeslLang, D extends JeeslDescription,
										R extends JeeslTenantRealm<L,D,R,?>, RREF extends EjbWithId,
										STATUS extends JeeslItsIssueStatus<L,D,R,STATUS,?>
										>
								extends Serializable
{
	void reloadRealm(R realm, RREF rref);
	
	
	Map<RREF,List<STATUS>> getMapStatus();
	
}