package org.jeesl.factory.ejb.module.its;

import java.util.UUID;

import org.jeesl.factory.builder.module.ItsFactoryBuilder;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbItsFactory<REALM extends JeeslMcsRealm<?,?,REALM,?>,
							ISSUE extends JeeslItsIssue<REALM,ISSUE>,
							STATUS extends JeeslItsIssueStatus<?,?,REALM,STATUS,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbItsFactory.class);
	
	private final ItsFactoryBuilder<?,?,?,ISSUE,STATUS> fbIssue;
	
    public EjbItsFactory(final ItsFactoryBuilder<?,?,?,ISSUE,STATUS> fbIssue)
    {
        this.fbIssue = fbIssue;
    }
	
	public <RREF extends EjbWithId> ISSUE build(REALM realm, RREF ref, ISSUE parent/*, STATUS status*/)
	{
		try
		{
			ISSUE ejb = fbIssue.getClassIssue().newInstance();
			ejb.setRealm(realm);
			ejb.setRref(ref.getId());
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setParent(parent);
			ejb.setName("");
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}