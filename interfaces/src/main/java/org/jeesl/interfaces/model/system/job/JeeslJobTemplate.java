package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslJobTemplate<L extends UtilsLang,D extends UtilsDescription,
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