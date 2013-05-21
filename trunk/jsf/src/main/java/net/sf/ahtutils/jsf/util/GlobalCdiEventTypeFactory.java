package net.sf.ahtutils.jsf.util;

import net.sf.ahtutils.model.event.GlobalCdiEvent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class GlobalCdiEventTypeFactory
{
	public static GlobalCdiEvent.Type addOrMod(EjbWithId ejb)
	{
		if(ejb.getId()==0){return GlobalCdiEvent.Type.ADD;}
		else{return GlobalCdiEvent.Type.MOD;}
	}
}
