package net.sf.ahtutils.jsf.exception;

import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.FacesContext;

import org.jeesl.exception.jsf.JeeslMenuException;
import org.omnifaces.exceptionhandler.FullAjaxExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class UtilsAjaxExceptionHandler extends FullAjaxExceptionHandler
{
	final static Logger logger = LoggerFactory.getLogger(UtilsAjaxExceptionHandler.class);
	
    public UtilsAjaxExceptionHandler(ExceptionHandler wrapped)
    {
        super(wrapped);
    }

    @Override
    protected void logException(FacesContext context, Throwable exception, String location, String message, Object... parameters)
    {
    	logger.trace(this.getClass().getSimpleName()+" catched an exception: "+exception.getClass().getSimpleName());
    	 // With exception==null, no trace will be logged.
    	
        if(exception.getClass().getName().equals(ViewExpiredException.class.getName()))
        {
        	logger.warn(ViewExpiredException.class.getSimpleName()+": "+String.format(message, location)+" "+exception.getMessage());
//            super.logException(context, null, location, message, parameters);
        }
        else if(exception.getClass().getName().equals(ExlpXpathNotFoundException.class.getName()))
        {
        	logger.warn(ViewExpiredException.class.getSimpleName()+": "+String.format(message, location)+" "+exception.getMessage());
        }
        else if(exception.getClass().getName().equals(JeeslMenuException.class.getName()))
        {
        	logger.warn(JeeslMenuException.class.getSimpleName()+": "+String.format(message, location)+" "+exception.getMessage());
 //       	super.logException(context, null, location, message, parameters);
        }
        else
        {
        	logger.trace("Other");
            super.logException(context, exception, location, message, parameters);
        }
    }
}