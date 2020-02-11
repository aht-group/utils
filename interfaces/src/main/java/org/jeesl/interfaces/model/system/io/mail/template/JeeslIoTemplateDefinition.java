package org.jeesl.interfaces.model.system.io.mail.template;

import java.io.Serializable;
import java.util.Map;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoTemplateDefinition<D extends JeeslDescription,
								CHANNEL extends JeeslTemplateChannel<?,D,CHANNEL,?>,
								TEMPLATE extends JeeslIoTemplate<?,D,?,?,?,?>
								>
		extends Serializable,EjbPersistable,
				EjbWithId,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver,
				EjbWithDescription<D>
{	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	CHANNEL getType();
	void setType(CHANNEL type);
	
	public Map<String,D> getHeader();
	public void setHeader(Map<String,D> header);
}