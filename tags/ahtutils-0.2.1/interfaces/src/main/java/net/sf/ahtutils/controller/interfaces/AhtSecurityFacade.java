package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.model.interfaces.EjbWithType;

public interface AhtSecurityFacade extends UtilsFacade
{	
	<T extends EjbWithType> List<T> allUtilsForType(Class<T> claszz, String type);
}
