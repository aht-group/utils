package org.jeesl.interfaces.model.module.workflow.instance;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWithWorkflow<AW extends JeeslWorkflow<?,?,?,?>> extends EjbWithId
{
	public static enum Attributes{workflow}
	
	//This is a marker interface for all domain classes which have a workflow correlation
}