package org.jeesl.interfaces.model.module.lf.target.time;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;
import org.jeesl.interfaces.model.with.system.status.JeeslWithInterval;

public interface JeeslLfTargetTimeGroup<L extends JeeslLang,
//										R extends JeeslTenantRealm<?,?,R,?>,		/tk: will be activated soon
										TTI extends JeeslLfTargetTimeInterval<?,?,TTI,?>>
						extends Serializable,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver,EjbWithPosition,
								EjbWithId,EjbWithName,
								JeeslWithInterval<TTI>
//								,JeeslWithTenantSupport<R>
{
	public enum Attributes{interval}
	
	public int getValues();
	public void setValues(int value);
	
	public Date getStartDate();
	public void setStartDate(Date startDate);
	
	public Date getEndDate();
	public void setEndDate(Date endDate);
}