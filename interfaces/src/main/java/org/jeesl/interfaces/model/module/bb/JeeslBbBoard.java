package org.jeesl.interfaces.model.module.bb;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithRefId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithEmail;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslBbBoard<L extends JeeslLang, D extends JeeslDescription,
								SCOPE extends JeeslStatus<L,D,SCOPE>,
								BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,USER>,
								PUB extends JeeslStatus<L,D,PUB>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,EjbWithRefId,EjbWithPosition,EjbWithName,
								EjbSaveable
{	
	public enum Attributes{refId,parent,scope,publishing,name,description}
	
	BB getParent();
	void setParent(BB parent);
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	String getDescription();
	void setDescription(String description);
	
	PUB getPublishing();
	void setPublishing(PUB publishing);
}