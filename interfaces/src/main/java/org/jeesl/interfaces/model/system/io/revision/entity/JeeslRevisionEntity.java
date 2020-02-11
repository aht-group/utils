package org.jeesl.interfaces.model.system.io.revision.entity;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.revision.EjbWithRevisionAttributes;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslRevisionEntity<L extends JeeslLang, D extends JeeslDescription,
									RC extends JeeslRevisionCategory<L,D,RC,?>,
									REM extends JeeslRevisionEntityMapping<?,?,?>,
									RA extends JeeslRevisionAttribute<L,D,?,?,?>,
									ERD extends JeeslRevisionDiagram<L,D,RC>>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>,EjbWithRevisionAttributes<RA>
{	
	public enum Attributes {category}
	
	RC getCategory();
	void setCategory(RC category);
	
	Boolean getTimeseries();
	void setTimeseries(Boolean timeseries);
	
	Boolean getDocumentation();
	void setDocumentation(Boolean documentation);
	
	List<REM> getMaps();
	void setMaps(List<REM> maps);
	
	String getDeveloperInfo();
	void setDeveloperInfo(String developerInfo);
	
	ERD getDiagram();
	void setDiagram(ERD diagram);
}