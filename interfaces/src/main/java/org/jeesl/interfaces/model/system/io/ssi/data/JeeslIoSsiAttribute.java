package org.jeesl.interfaces.model.system.io.ssi.data;

import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiAttribute <MAPPING extends JeeslIoSsiMapping<?,ENTITY>,
									 ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithParentAttributeResolver
{	
	public enum Attributes{mapping}
	
	public MAPPING getMapping();
	public void setMapping(MAPPING mapping);
	
	public ENTITY getEntity();
	public void setEntity(ENTITY entity);
	
	public String getLocalCode();
	public void setLocalCode(String code);
	
	public String getRemoteCode();
	public void setRemoteCode(String code);
}