package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslWithAttributeContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisibleParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoDmsDocument<L extends JeeslLang, S extends JeeslIoDmsSection<L,?,S>,
								FC extends JeeslFileContainer<?,?>, AC extends JeeslAttributeContainer<?,?>>
					extends Serializable,EjbWithId,
							EjbRemoveable,EjbPersistable,EjbSaveable,
							EjbWithPositionVisibleParent,
							EjbWithLang<L>,
							JeeslWithFileRepositoryContainer<FC>,
							JeeslWithAttributeContainer<AC>
{	
	public enum Attributes{section}
	
	S getSection();
	void setSection(S section);
}