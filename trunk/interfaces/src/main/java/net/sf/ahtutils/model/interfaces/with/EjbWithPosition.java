package net.sf.ahtutils.model.interfaces.with;

import net.sf.ahtutils.model.interfaces.EjbWithId;

public interface EjbWithPosition extends EjbWithId
{
	public int getPosition();
	public void setPosition(int position);
}
