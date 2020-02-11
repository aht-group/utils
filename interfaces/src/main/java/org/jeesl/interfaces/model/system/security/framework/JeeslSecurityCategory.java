package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.code.EjbWithTypeCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionType;
import org.jeesl.interfaces.model.with.position.EjbWithPositionTypeVisible;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityCategory<L extends JeeslLang, D extends JeeslDescription>
			extends Serializable,EjbWithCode,EjbRemoveable,EjbPersistable,
				EjbWithId,EjbWithTypeCode,
				EjbWithPositionTypeVisible,EjbWithPositionVisible,EjbWithPositionType,
				EjbWithLang<L>,EjbWithDescription<D>,
				EjbSaveable
{
	public enum Attributes{code,type}
	public static enum Type {role,view,usecase,action}
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}