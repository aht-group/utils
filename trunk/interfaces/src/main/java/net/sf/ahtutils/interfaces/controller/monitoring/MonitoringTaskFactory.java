package net.sf.ahtutils.interfaces.controller.monitoring;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;

public interface MonitoringTaskFactory<R extends MonitoringResult>
{		
	public CompletionService<R> getCompletionService();
	public void setCompletionService(CompletionService<R> completionService);
	
	public MonitoringResultProcessor<R> getResultProcessor();
	public void setResultProcessor(MonitoringResultProcessor<R> resultProcessor);
	
	public Callable<R> buildTask();
}