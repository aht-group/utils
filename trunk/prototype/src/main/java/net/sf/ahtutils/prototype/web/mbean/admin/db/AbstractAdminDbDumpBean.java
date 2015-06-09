package net.sf.ahtutils.prototype.web.mbean.admin.db;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.facade.UtilsDbFacade;
import net.sf.ahtutils.interfaces.model.db.UtilsDbDumpFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminDbDumpBean <D extends UtilsDbDumpFile> implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbDumpBean.class);
	
	protected UtilsDbFacade fDb;
	
	protected List<D> dumps;
	public List<D> getDumps(){return dumps;}

	private Class<D> cDump;
	
	public void initSuper(final Class<D> cDump)
	{
		this.cDump=cDump;
		refreshList();
	}
	
	protected void refreshList()
	{
		dumps = fDb.allOrdered(cDump,"startDate",false);
	}
}