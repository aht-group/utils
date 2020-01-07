package org.jeesl.web.mbean.prototype.system.io.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.factory.builder.io.IoDbFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbCodeFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbConnectionColumn;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.model.json.system.io.db.JsonPostgresConnection;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractDbConnectionBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									SYSTEM extends JeeslIoSsiSystem,
									CC extends JeeslDbConnectionColumn<L,D,CC,?>>
						extends AbstractAdminBean<L,D>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDbConnectionBean.class);
	
	
	private JeeslIoDbFacade<L,D,SYSTEM,?,?,?,?> fDb;
	private final IoDbFactoryBuilder<L,D,SYSTEM,?,?,?,?,CC,?,?,?,?> fbDb;
	
	private final Map<String,CC> mapColumn; public Map<String,CC> getMapColumn() {return mapColumn;}
	
	private List<JsonPostgresConnection> connections; public List<JsonPostgresConnection> getConnections() {return connections;}
	
	private final String dbName;
	
	public AbstractDbConnectionBean(String dbName, final IoDbFactoryBuilder<L,D,SYSTEM,?,?,?,?,CC,?,?,?,?> fbDb)
	{
		super(fbDb.getClassL(),fbDb.getClassD());
		this.dbName=dbName;
		this.fbDb=fbDb;
		
		mapColumn = new HashMap<>();
	}
	
	public void postConstructDbReplication(JeeslIoDbFacade<L,D,SYSTEM,?,?,?,?> fDb)
	{
		this.fDb=fDb;
		mapColumn.putAll(EjbCodeFactory.toMapCode(fDb.allOrderedPositionVisible(fbDb.getClassConnectionColumn())));
		refreshList(); 
	}
	
	protected void refreshList()
	{		
		connections = fDb.postgresConnections(dbName).getConnections();
	}
}