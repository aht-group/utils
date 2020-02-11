package org.jeesl.interfaces.model.module.ts.core;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisibleParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslTsScope <L extends JeeslLang, D extends JeeslDescription,
									CAT extends JeeslStatus<CAT,L,D>,
									ST extends JeeslStatus<ST,L,D>,
									UNIT extends JeeslStatus<UNIT,L,D>,
									EC extends JeeslTsEntityClass<L,D,CAT>,
									INT extends JeeslStatus<INT,L,D>
//									,AS extends JeeslAttributeSet<L,D,?,?>
									>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithCode,
				EjbWithPositionVisibleParent,EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Statistic{none}
	
	CAT getCategory();
	void setCategory(CAT category);
	
	UNIT getUnit();
	void setUnit(UNIT unit);
	
	ST getType();
	void setType(ST type);
	
	String getCode();
	void setCode(String code);
	
	List<INT> getIntervals();
	void setIntervals(List<INT> intervals);
	
	List<EC> getClasses();
	void setClasses(List<EC> classes);
}