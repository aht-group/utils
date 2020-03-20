package org.jeesl.factory.ejb.module.hd;

import org.jeesl.interfaces.model.module.hd.ticket.JeeslHdTicket;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbHdTicketFactory<R extends JeeslMcsRealm<?,?,R,?>,
								TICKET extends JeeslHdTicket<R,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbHdTicketFactory.class);
	
	private final Class<TICKET> cTicket;
	
    public EjbHdTicketFactory(final Class<TICKET> cTicket)
    {
        this.cTicket = cTicket;
    }
	
	public <RREF extends EjbWithId> TICKET build(R realm, RREF rref)
	{
		try
		{
			TICKET ejb = cTicket.newInstance();
			ejb.setRealm(realm);
			ejb.setRref(rref.getId());
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}