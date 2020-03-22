package org.jeesl.interfaces.model.module.hd.ticket;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.hd.event.JeeslHdEvent;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.mcs.JeeslWithMultiClientSupport;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslHdTicket<R extends JeeslMcsRealm<?,?,R,?>,
								EVENT extends JeeslHdEvent<?,?,?,?,?,?>,
								M extends JeeslMarkup<?>>
						extends Serializable,EjbSaveable,
								EjbWithId,EjbWithName,EjbWithNonUniqueCode,
//								JeeslWithCategory<CAT>,JeeslWithStatus<STATUS>,
								JeeslWithMultiClientSupport<R>
{	
	public enum Attributes{lastEvent}
	
	EVENT getFirstEvent();
	void setFirstEvent(EVENT firstEvent);
	
	EVENT getLastEvent();
	void setLastEvent(EVENT lastEvent);
	
	M getMarkupUser();
	void setMarkupUser(M markupUser);
	
	M getMarkupSupport();
	void setMarkupSupport(M markupSupport);
	
	M getMarkupSolution();
	void setMarkupSolution(M markupSolution);
}