package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.io.report.JeeslIoReport;

public interface JeeslReport <REPORT extends JeeslIoReport<?,?,?,?>>
{		
	REPORT getIoReport();
}