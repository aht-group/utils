package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslJobTemplate<L extends JeeslLang,D extends JeeslDescription,
									CATEGORY extends JeeslJobCategory<L,D,CATEGORY,?>,
									TYPE extends JeeslJobType<L,D,TYPE,?>,
									PRIORITY extends JeeslJobPriority<L,D,PRIORITY,?>,
									EXPIRE extends JeeslJobExpiration<L,D,EXPIRE,?>
									>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,//EjbWithPosition,
				EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Attributes{category,type,code};
	public static enum Code{surveyAnalysis}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	TYPE getType();
	void setType(TYPE type);
	
	PRIORITY getPriority();
	void setPriority(PRIORITY priority);
	
	int getTimeout();
	void setTimeout(int timeout);
	
	EXPIRE getExpiration();
	void setExpiration(EXPIRE expiration);
}