package net.sf.ahtutils.db.xml;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.dbseed.Db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAhtDbXmlInit <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
	extends AbstractDbRestInit
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtDbXmlInit.class);

	protected UtilsIdMapper idMapper;
	protected AhtStatusDbInit<S,L,D> asdi;
	
	public AbstractAhtDbXmlInit(Db dbSeed, DataSource datasource, UtilsIdMapper idMapper, AhtStatusDbInit<S,L,D> asdi)
	{
		super(dbSeed, datasource,null,idMapper);
		this.idMapper=idMapper;
		this.asdi=asdi;
	}
}