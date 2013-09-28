package net.sf.ahtutils.db.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlConnectionFactory
{
	final static Logger logger = LoggerFactory.getLogger(SqlConnectionFactory.class);
	
	private static enum DbType{mysql,postgresql};
	
	private Connection c;
	private String type,host,db,username,password;
	private int port;
	
	private Configuration config;
	
	public SqlConnectionFactory(Configuration config)
	{
		this.config=config;
	}
	
	public Connection getConnection(String code)
	{
		logger.debug("Using connection code: "+code);
		
		type = config.getString("net.db."+code+".type");
		host = config.getString("net.db."+code+".host");
		port = config.getInt("net.db."+code+".port");
		db = config.getString("net.db."+code+".database");
		
		username = config.getString("net.db."+code+".username");
		password = config.getString("net.db."+code+".password");
	
//		logger.info(getConnInfo());
		
		DbType dbType = DbType.valueOf(type);
		switch(dbType)
		{
			case mysql: connectMySQL(); break;
			case postgresql: connectPostgreSQL(); break;
		}
		
		return c;
	}

	private void connectMySQL()
	{
	    try
	    {
	    	logger.debug("Connecting ... "+getConnInfo());
	    	Class.forName("org.postgresql.Driver");
			c=DriverManager.getConnection(getConnInfo(),username, password);
		}
	    catch (ClassNotFoundException e) {logger.error(e.getMessage());}
	    catch (SQLException e) {logger.error(e.getMessage());}
	}
	
	private void connectPostgreSQL()
	{
	    try
	    {
	    	logger.debug("Connecting ... "+getConnInfo());
	    	Class.forName("org.postgresql.Driver");
			c=DriverManager.getConnection(getConnInfo(),username, password);
		}
	    catch (ClassNotFoundException e) {e.printStackTrace();logger.error(e.getMessage());}
	    catch (SQLException e) {logger.error(e.getMessage());}
	}
	
	public void disconnect()
	{
		logger.debug("Disconnecting ...");
		try {c.close();}
		catch (SQLException e) {logger.error(e.getMessage());}
	}
	
	private String getConnInfo()
	{
		StringBuffer sb = new StringBuffer();
			sb.append("jdbc:");
			sb.append(type+"://");
			sb.append(host+":");
			sb.append(port+"/");
			sb.append(db);
		return sb.toString();
	}
}