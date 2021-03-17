package org.jeesl.interfaces.model.module.lf;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;
import org.jeesl.interfaces.model.with.system.status.JeeslWithLevel;
import org.jeesl.interfaces.model.with.system.status.JeeslWithType;

public interface JeeslLfLogframe<L extends JeeslLang, D extends JeeslDescription,
								LFL extends JeeslLfLevel<L,D,LFL,?>,
								LFT extends JeeslLfType<L,D,LFT,?>>
						extends Serializable,EjbSaveable,EjbRemoveable,
								EjbWithId,EjbWithName,
								JeeslWithType<LFT>,JeeslWithLevel<LFL>
//,								JeeslWithFileRepositoryContainer<FRC>
{
	//public enum Attributes{}

}