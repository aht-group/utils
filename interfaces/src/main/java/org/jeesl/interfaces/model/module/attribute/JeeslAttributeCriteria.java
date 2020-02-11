package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslAttributeCriteria<L extends JeeslLang, D extends JeeslDescription,
										CATEGORY extends JeeslStatus<CATEGORY,L,D>,
										TYPE extends JeeslStatus<TYPE,L,D>>
			extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithNonUniqueCode,EjbWithPositionVisible,EjbWithPositionParent,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{category,refId,position,type}
	public enum Types{text,bool,intNumber,doubleNumber,date,selectOne,selectMany,remark}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	Long getRefId();
	void setRefId(Long refId);
	
	TYPE getType();
	void setType(TYPE type);
	
	Boolean getAllowEmpty();
	void setAllowEmpty(Boolean allowEmpty);
}