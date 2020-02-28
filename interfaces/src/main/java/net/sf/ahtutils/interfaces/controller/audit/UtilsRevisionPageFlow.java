package net.sf.ahtutils.interfaces.controller.audit;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface UtilsRevisionPageFlow<T extends EjbWithId,P extends EjbWithId>
{		
	public void pageFlowPrimarySelect(T revision);
	public void pageFlowParentSelect(P parent);
	public void pageFlowPrimaryCancel();
	public void pageFlowPrimarySave(T revision);
	public void pageFlowPrimaryAdd();
	public void pageFlowActionInOther();
}