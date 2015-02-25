package net.sf.ahtutils.util.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionsUtil {
	
	final static Logger logger = LoggerFactory.getLogger(ReflectionsUtil.class);

	/**
	 * Inspect if an Object has some given method
	 * @param object     The parent Object (e.g. Person)
	 * @param methodName The name of the method (e.g. setName)
	 * @return Is method there? (e.g. true)
	 */
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
	
	/**
	 * Intended to get the class of the first argument to be passed to a method
	 * @param object     The parent Object (e.g. Person)
	 * @param methodName The name of the method (e.g. setName)
	 * @return Class of the first parameter (e.g. java.lang.String)
	 */
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
	 
 	/**
	 * First skeleton for traversing through .-separated methods (get or create/set)
	 * NOT READY YET!
	 * @param parent      The parent Object
	 * @param expression  .-separated fields (eg. person.name.lastName)
	 * @return The parent Object now including the value to be set
	 */
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
	 
	 /**
		 * Invoke a method on a given object
		 * @param methodName   Method to be invoked (e.g. "setId")
		 * @param parameters   Array of parameters to be used as they appear in the method (e.g. [Long id])
		 * @param targetClass  Targeted class
		 * @param target       The actual object that holds the method to be invoked
		 * @return The returned object (e.g. the long value of the ID)
		 */
	 public static Object simpleInvokeMethod(String   methodName, 
				Object[] parameters,
				Class<?> targetClass,
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
