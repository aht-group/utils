package org.jeesl.interfaces.model.module.hd.ticket;

import java.io.Serializable;

import org.jeesl.interfaces.model.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.mcs.JeeslWithMultiClientSupport;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;
import org.jeesl.interfaces.model.with.system.status.JeeslWithCategory;
import org.jeesl.interfaces.model.with.system.status.JeeslWithStatus;

public interface JeeslHdTicket<R extends JeeslMcsRealm<?,?,R,?>,
								TICKET extends JeeslHdTicket<?,TICKET,CAT,STATUS,?,?,USER>,
								CAT extends JeeslHdTicketCategory<?,?,R,CAT,?>,
								STATUS extends JeeslHdTicketStatus<?,?,R,STATUS,?>,
								
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<?,?,MT,?>,

								USER extends JeeslSimpleUser>
						extends Serializable,EjbSaveable,
								EjbWithId,EjbWithName,EjbWithNonUniqueCode,
								JeeslWithCategory<CAT>,JeeslWithStatus<STATUS>,
								JeeslWithMultiClientSupport<R>
{	
	public enum Attributes{xx}
	
	M getMarkup();
	void setMarkup(M markup);
	
	USER getReporter();
	void setReporter(USER reporter);
	
	USER getSupporter();
	void setSupporter(USER user);
}