package org.jeesl.factory.ejb.module.its;

import java.util.UUID;

import org.jeesl.factory.builder.module.ItsFactoryBuilder;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbItsFactory<R extends JeeslTenantRealm<?,?,R,?>,
							I extends JeeslItsIssue<R,I>,
							IS extends JeeslItsIssueStatus<?,?,R,IS,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbItsFactory.class);
	
	private final ItsFactoryBuilder<?,?,?,?,?,I,IS,?,?> fbIssue;
	
    public EjbItsFactory(final ItsFactoryBuilder<?,?,?,?,?,I,IS,?,?> fbIssue)
    {
        this.fbIssue = fbIssue;
    }
	
	public <RREF extends EjbWithId> I build(R realm, RREF ref, I parent/*, STATUS status*/)
	{
		try
		{
			I ejb = fbIssue.getClassIssue().newInstance();
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