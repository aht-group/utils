package net.sf.ahtutils.interfaces.controller.monitoring;

import java.util.concurrent.CompletionService;

public interface MonitoringResultProcessor <R extends MonitoringResult> extends Runnable
{		
	void setCompletionService(CompletionService<R> completionService);
}