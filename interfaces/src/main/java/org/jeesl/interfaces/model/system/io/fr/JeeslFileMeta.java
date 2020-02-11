package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.number.EjbWithSize;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;
import org.jeesl.interfaces.model.with.status.JeeslWithType;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslFileMeta<D extends JeeslDescription,
								CONTAINER extends JeeslFileContainer<?,?>,
								TYPE extends JeeslFileType<?,D,TYPE,?>,
								STATUS extends JeeslFileStatus<?,D,STATUS,?>
>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithParentAttributeResolver,
					EjbWithPosition,
					EjbWithCode,
//					EjbWithName,
					JeeslWithType<TYPE>,JeeslWithStatus<STATUS>,
					EjbWithSize,EjbWithRecord,
					EjbWithDescription<D>
{
	public enum Attributes{container,type}
	
	CONTAINER getContainer();
	void setContainer(CONTAINER container);
	
	String getMd5Hash();
	void setMd5Hash(String md5Hash);
	
	String getFileName();
	void setFileName(String fileName);
	
	Date getStatusCheck();
	void setStatusCheck(Date statusCheck);
	
	String getCategory();
	void setCategory(String category);
}