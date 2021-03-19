package org.jeesl.interfaces.model.module.lf;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorLevel;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;
import org.jeesl.interfaces.model.with.system.status.JeeslWithLevel;
import org.jeesl.interfaces.model.with.system.status.JeeslWithType;

public interface JeeslLfLogframe<L extends JeeslLang, D extends JeeslDescription,R extends JeeslTenantRealm<L,D,R,?>,
								LFL extends JeeslLfIndicatorLevel<L,D,R,LFL,?>,
								LFT extends JeeslLfIndicatorType<L,D,R,LFT,?>>
						extends Serializable,EjbSaveable,EjbRemoveable,
								EjbWithId,EjbWithName,
								JeeslWithType<LFT>,JeeslWithLevel<LFL>
//,								JeeslWithFileRepositoryContainer<FRC>
{
	//public enum Attributes{}

}