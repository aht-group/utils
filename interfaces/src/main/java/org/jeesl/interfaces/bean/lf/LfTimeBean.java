package org.jeesl.interfaces.bean.lf;

import java.util.Date;

import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeInterval;

public interface LfTimeBean<TI extends JeeslLfTimeInterval<?,?,TI,?>,
								TG extends JeeslLfTimeGroup<?,TI>,
								TE extends JeeslLfTimeElement<?,TG>>
{
	void updateTimeGroupStartAndEndDate(Date date);
	Date getTimeGroupStartDate();
	void updateTimeGroupEndDate(Date date);
	TE createNewTimeElement();
}
