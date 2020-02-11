package org.jeesl.api.bean.msg;

import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslConstraintMessageBean<L extends JeeslLang, D extends JeeslDescription,
											ALGCAT extends JeeslStatus<ALGCAT,L,D>,
											ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
											SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
											CONCAT extends JeeslStatus<CONCAT,L,D>,
											CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
											LEVEL extends JeeslStatus<LEVEL,L,D>,
											TYPE extends JeeslStatus<TYPE,L,D>,
											RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
				extends JeeslFacesMessageBean
{
	<FID extends Enum<FID>> void show(FID fId, CONSTRAINT constraint);
	<FID extends Enum<FID>, SID extends Enum<SID>, CID extends Enum<CID>> void show(FID fId, SID sId, CID cId);
}