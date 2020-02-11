package org.jeesl.interfaces.model.system.io.mail.template;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.status.JeeslWithType;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoTemplateToken<L extends JeeslLang,D extends JeeslDescription,
								TEMPLATE extends JeeslIoTemplate<L,D,?,?,?,?>,
								TOKENTYPE extends JeeslStatus<TOKENTYPE,?,D>>
		extends Serializable,EjbPersistable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>,JeeslWithType<TOKENTYPE>
{	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	String getExample();
	void setExample(String example);
	
	TOKENTYPE getType();
	void setType(TOKENTYPE type);
}