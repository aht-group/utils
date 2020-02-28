package org.jeesl.interfaces.model.module.training;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPositionVisible;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslTrainingModule<L extends JeeslLang,D extends JeeslDescription,
										MODULE extends JeeslTrainingModule<L,D,MODULE,SESSION,PAGE,TRAINING,DAY,SLOT,TYPE>,
										SESSION extends JeeslTrainingSession<L,D,MODULE,SESSION,PAGE,TRAINING,DAY,SLOT,TYPE>,
										PAGE extends JeeslTrainingPage<L,D,MODULE,SESSION,PAGE,TRAINING,DAY,SLOT,TYPE>,
										TRAINING extends JeeslTraining<L,D,MODULE,SESSION,PAGE,TRAINING,DAY,SLOT,TYPE>,
										DAY extends JeeslTrainingDay<L,D,MODULE,SESSION,PAGE,TRAINING,DAY,SLOT,TYPE>,
										SLOT extends JeeslTrainingSlot<L,D,MODULE,SESSION,PAGE,TRAINING,DAY,SLOT,TYPE>,
										TYPE extends JeeslStatus<TYPE,L,D>
>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{	

}