package org.jeesl.interfaces.model.io.dms;

import java.io.Serializable;

import org.jeesl.interfaces.model.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.io.fr.JeeslWithFileRepositoryContainer;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslWithAttributeContainer;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisibleParent;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

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