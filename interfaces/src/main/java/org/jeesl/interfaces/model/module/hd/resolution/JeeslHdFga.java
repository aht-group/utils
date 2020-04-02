package org.jeesl.interfaces.model.module.hd.resolution;

import java.io.Serializable;

import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisibleParent;

public interface JeeslHdFga<FAQ extends JeeslHdFaq<?,?,?,?,?>,
							DOC extends JeeslIoCms<?,?,?,SEC,?>,
							SEC extends JeeslIoCmsSection<?,SEC>>
						extends Serializable,EjbSaveable,EjbRemoveable,
								EjbWithId,EjbWithPositionVisibleParent
{
	public enum Attributes{faq}
	
	FAQ getFaq();
	void setFaq(FAQ faq);
	
	SEC getSection();
	void setSection(SEC section);
}