package org.jeesl.interfaces.model.module.log;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslLogItem <L extends JeeslLang, D extends JeeslDescription,
								M extends JeeslMarkup<MT>, MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
								LOG extends JeeslLogBook<?,?>,
								IMPACT extends JeeslLogImpact<L,D,IMPACT,?>,
								CONF extends JeeslLogConfidentiality<L,D,CONF,?>,
								USER extends EjbWithId
								>
		extends Serializable,EjbWithId,
				EjbSaveable,EjbPersistable,EjbRemoveable,
				EjbWithLang<L>,EjbWithRecord
{
	public enum Attributes{log,record}
	
	LOG getLog();
	void setLog(LOG log);
	
	USER getAuthor();
	void setAuthor(USER author);
	
	IMPACT getImpact();
	void setImpact(IMPACT impact);
	
	Map<String,M> getMarkup();
	public void setMarkup(Map<String,M> markup);
	
	List<CONF> getConfidentialities();
	void setConfidentialities(List<CONF> scopes);
}