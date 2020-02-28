package net.sf.ahtutils.jsf.util;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

import net.sf.ahtutils.model.event.GlobalCdiEvent;

public class GlobalCdiEventTypeFactory
{
	public static GlobalCdiEvent.Type addOrMod(EjbWithId ejb)
	{
		if(ejb.getId()==0){return GlobalCdiEvent.Type.ADD;}
		else{return GlobalCdiEvent.Type.MOD;}
	}
}
