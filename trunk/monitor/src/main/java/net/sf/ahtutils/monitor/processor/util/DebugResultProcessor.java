package net.sf.ahtutils.monitor.processor.util;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.sf.ahtutils.interfaces.controller.MonitoringResultProcessor;
import net.sf.ahtutils.monitor.result.util.DebugResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugResultProcessor implements MonitoringResultProcessor
{
	final static Logger logger = LoggerFactory.getLogger(DebugResultProcessor.class);

	private CompletionService<DebugResult> csDebug;
	
    public DebugResultProcessor(CompletionService<DebugResult> csDebug)
    {
  	    this.csDebug=csDebug;
    }

	@Override
	public void run()
	{
		while(true)
        {
            try
            {
                Future<DebugResult> future = csDebug.take();
                DebugResult result = future.get();
                
               logger.info(result.toString());
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
        }
	}
}