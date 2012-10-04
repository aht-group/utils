package net.sf.ahtutils.model.interfaces.with;

import net.sf.ahtutils.model.interfaces.EjbWithId;

public interface EjbWithVisible extends EjbWithId
{
	public boolean isVisible();
	public void setVisible(boolean visible);
}
