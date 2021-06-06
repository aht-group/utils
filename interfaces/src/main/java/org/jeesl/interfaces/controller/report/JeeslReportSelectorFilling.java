package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

public interface JeeslReportSelectorFilling <L extends JeeslLang,D extends JeeslDescription,
												REPORT extends JeeslIoReport<L,D,?,?>,
												FILLING extends JeeslStatus<L,D,FILLING>
												>
			extends JeeslReport<REPORT>
{		
	void setReportSettingFilling(FILLING filling);
}