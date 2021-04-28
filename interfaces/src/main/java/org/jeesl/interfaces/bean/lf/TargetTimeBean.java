package org.jeesl.interfaces.bean.lf;

import java.util.Date;

public interface TargetTimeBean {

	void updateTimeGroupStartAndEndDate(Date date);
	Date getTimeGroupStartDate();
	void updateTimeGroupEndDate(Date date);
}
