package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslAttributeSet <L extends JeeslLang, D extends JeeslDescription,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>,
									ITEM extends JeeslAttributeItem<?,?>>
		extends Serializable,EjbSaveable,EjbRemoveable,
				EjbWithPosition,EjbWithPositionParent,EjbWithCode,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{category,refId,position}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	Long getRefId();
	void setRefId(Long refId);
	
	List<ITEM> getItems();
	void setItems(List<ITEM> items);
}