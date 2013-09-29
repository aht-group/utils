package net.sf.ahtutils.factory.ejb.issue;

import net.sf.ahtutils.interfaces.model.issue.UtilsTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTaskFactory<T extends UtilsTask<T>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTaskFactory.class);
	
	final Class<T> clTask;
	
    public EjbTaskFactory(final Class<T> clTask)
    {
        this.clTask = clTask;
    } 
    
    public static <T extends UtilsTask<T>> EjbTaskFactory<T> factory(final Class<T> clTask)
    {
        return new EjbTaskFactory<T>(clTask);
    }
    	
    public T build() {return build(null,null);}
    public T buildCode(String code) {return build(null,code);}
    public T build(T parent) {return build(parent,null);}
    
	public T build(T parent, String code)
	{
		try
		{
			T t = clTask.newInstance();
			t.setParent(parent);
			t.setCode(code);
		    return t;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		throw new RuntimeException("x");
    }
}