package org.jeesl.interfaces.model.system.constraint;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.status.JeeslWithLevel;
import org.jeesl.interfaces.model.with.status.JeeslWithType;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslConstraint<L extends JeeslLang, D extends JeeslDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									LEVEL extends JeeslStatus<LEVEL,L,D>,
									TYPE extends JeeslStatus<TYPE,L,D>,
									RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
			extends Serializable,EjbWithId,
					EjbRemoveable,EjbPersistable,EjbSaveable,
					EjbWithNonUniqueCode,
					
					EjbWithPosition,
					EjbWithParentAttributeResolver,
					EjbWithLangDescription<L,D>,
					JeeslWithType<TYPE>,
					JeeslWithLevel<LEVEL>
{
	public static enum Attributes{scope,code};
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	String getContextMessage();
	void setContextMessage(String contextMessage);
}