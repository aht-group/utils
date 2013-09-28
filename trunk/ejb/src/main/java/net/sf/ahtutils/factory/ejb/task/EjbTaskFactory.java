package net.sf.ahtutils.factory.ejb.task;

import net.sf.ahtutils.interfaces.model.task.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTaskFactory<T extends Task>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTaskFactory.class);
	
	final Class<T> clTask;
	
    public EjbTaskFactory(final Class<T> clTask)
    {
        this.clTask = clTask;
    } 
    
    public static <T extends Task> EjbTaskFactory<T> factory(final Class<T> clTask)
    {
        return new EjbTaskFactory<T>(clTask);
    }
    	
	public T build(String code)
	{
		try
		{
			T t = clTask.newInstance();
			t.setCode(code);
		    return t;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		throw new RuntimeException("x");
    }
}