package net.sf.ahtutils.doc.er;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.shell.spawn.Spawn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErImageWriter
{
	final static Logger logger = LoggerFactory.getLogger(ErImageWriter.class);
	
	public ErImageWriter()
	{
		
	}
	
	public void svg(File src, File dst) throws IOException, ClassNotFoundException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("/usr/local/bin/neato");
		sb.append(" -Tsvg");
		sb.append(" ").append(src.getAbsolutePath());
		sb.append(" -o ");
		sb.append(dst.getAbsolutePath());
		
		Spawn spawn = new Spawn(sb.toString());
		spawn.debug();
		spawn.run();
	}
}