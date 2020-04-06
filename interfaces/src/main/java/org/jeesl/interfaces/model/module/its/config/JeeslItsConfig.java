package org.jeesl.interfaces.model.module.its.config;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.mcs.JeeslWithMultiClientSupport;
import org.jeesl.interfaces.model.with.primitive.bool.EjbWithVisible;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLangDescription;

public interface JeeslItsConfig <L extends JeeslLang, D extends JeeslDescription,
								R extends JeeslMcsRealm<L,D,R,?>,
								O extends JeeslItsConfigOption<L,D,O,?>>
			extends Serializable,EjbSaveable,EjbRemoveable,
					JeeslWithMultiClientSupport<R>,
					EjbWithVisible,EjbWithLangDescription<L,D>
					
					
{
	public enum Attributes{realm,rref,option}
	
	O getOption();
	void setOption(O option);
	
	boolean isOverrideLabel();
	void setOverrideLabel(boolean overrideLabel);
}