package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslReportSelectorTransformation <L extends JeeslLang, D extends JeeslDescription,
														REPORT extends JeeslIoReport<L,D,?,?>,
														TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>
														>
				extends JeeslReport<REPORT>
{	
	TRANSFORMATION getReportSettingTransformation();
	void setReportSettingTransformation(TRANSFORMATION transformation);
}