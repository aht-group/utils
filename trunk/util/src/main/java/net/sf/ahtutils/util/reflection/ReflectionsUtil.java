package net.sf.ahtutils.util.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionsUtil {
	
	final static Logger logger = LoggerFactory.getLogger(ReflectionsUtil.class);
	
	public static Boolean hasMethod(Object object, String methodName)
	{
		Method[] methods  = object.getClass().getMethods();
		Boolean hasMethod = false;
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				hasMethod = true;
			}
		}
		return hasMethod;
	}
	
	 public static Class<?> getParameterClass(Object object, String methodName)
	 {
		 
		logger.trace("Getting parameter class for " +methodName +" in class " +object.getClass().getSimpleName());
		
		// Now find the correct method
		Method[] methods = object.getClass().getMethods();
		Class<?> parameter  = null;
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				parameter = method.getParameterTypes()[0];
			}
		}
		return parameter;
	 }
	 
	 public static Object resolveExpression(Object parent, String expression) throws Exception
	 {
		 String[] pathToProperty = expression.split(".");
			for (String node : pathToProperty)
			{
				Object nodeObject = simpleInvokeMethod("get" +node,
					      new Object[] { },
					      parent.getClass(),
					      parent); 
				if (nodeObject == null)
				{
					Object empty = getParameterClass(parent, "get" +node).newInstance();
					simpleInvokeMethod("set" +node,
						      new Object[] { },
						      parent.getClass(),
						      parent);
					nodeObject = empty;
				}
				parent   = nodeObject;
			//	property = node;
			}
			return parent;
	 }
	 
	 private static Object simpleInvokeMethod(String   methodName, 
				Object[] parameters,
				Class    targetClass,
				Object   target)        throws Exception
	{
		logger.trace("Invoking " +methodName);
		
		// Now find the correct method
		Method[] methods = targetClass.getMethods();
		Method m         = null;
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				m = method;
			}
		}
		
		if (Modifier.isPrivate(m.getModifiers()))
		{
			m.setAccessible(true);
		}
			return m.invoke(target, parameters);
	}

}
