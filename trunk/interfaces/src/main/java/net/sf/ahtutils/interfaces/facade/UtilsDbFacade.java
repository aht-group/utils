package net.sf.ahtutils.interfaces.facade;

import java.util.List;
import java.util.Map;

public interface UtilsDbFacade extends UtilsFacade
{
	//<T extends Object>
	long count(Class<?> cl);
	//<T extends Object>
	Map<Class<?>, Long> count(List<Class<?>> list);
}