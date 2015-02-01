package net.sf.ahtutils.interfaces.model.sync;

import net.sf.ahtutils.interfaces.model.with.utils.UtilsWithCategory;
import net.sf.ahtutils.interfaces.model.with.utils.UtilsWithStatus;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsSync<L extends UtilsLang,
							D extends UtilsDescription,
							STATUS extends UtilsStatus<STATUS,L,D>,
							CATEGORY extends UtilsStatus<CATEGORY,L,D>>
			extends EjbWithId,EjbWithCode,EjbWithRecord,
						UtilsWithStatus<L,D,STATUS>,
						UtilsWithCategory<L,D,CATEGORY>
{
	public static enum Code{never,pending,success,fail};
}