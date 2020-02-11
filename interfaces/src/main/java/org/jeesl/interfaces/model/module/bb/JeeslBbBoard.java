package org.jeesl.interfaces.model.module.bb;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.number.EjbWithRefId;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslBbBoard<L extends JeeslLang, D extends JeeslDescription,
								SCOPE extends JeeslStatus<SCOPE,L,D>,
								BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,USER>,
								PUB extends JeeslStatus<PUB,L,D>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,EjbWithRefId,EjbWithPosition,EjbWithName,
								EjbSaveable
{	
	public enum Attributes{scope,refId}
	
	BB getParent();
	void setParent(BB bb);
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	String getDescription();
	void setDescription(String description);
	
	PUB getPublishing();
	void setPublishing(PUB publishing);
}