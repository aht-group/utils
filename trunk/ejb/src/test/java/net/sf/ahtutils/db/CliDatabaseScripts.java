package net.sf.ahtutils.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.test.AhtUtilsEjbTestBootstrap;
import net.sf.exlp.interfaces.util.TextWriter;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliDatabaseScripts
{
	final static Logger logger = LoggerFactory.getLogger(CliDatabaseScripts.class);
	
	private static final String prefix = "db.init.script.src";
	private Configuration config;
	
	private File fWorkspace;
	
	public CliDatabaseScripts(Configuration config)
	{
		this.config=config;
		fWorkspace = new File(config.getString(prefix+".workspace"));
		
		TextWriter tw;
	}
	
	
	
	public void build(List<String> listOs, List<String> listDb)
	{
		for(String os : listOs)
		{
			for(String db : listDb)
			{
				String[] files = config.getStringArray(prefix+"."+os+"."+db);
		 		for(String src : files)
		 		{
		 			File f = new File(fWorkspace,src);
		 			logger.info(f.toString());	
		 		}
			}
		}
	}
	
	public static void main(String[] args)
    {
		Configuration config = AhtUtilsEjbTestBootstrap.init();

		CliDatabaseScripts test = new CliDatabaseScripts(config);
		
		List<String> os = new ArrayList<String>();
		os.add("yosemite");
		
		List<String> db = new ArrayList<String>();
		db.add("mysql");
		
		test.build(os,db);
    }
}