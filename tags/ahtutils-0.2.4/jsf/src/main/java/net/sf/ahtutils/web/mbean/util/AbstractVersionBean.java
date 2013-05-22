package net.sf.ahtutils.web.mbean.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.jar.Manifest;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractVersionBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractVersionBean.class);
	private static final long serialVersionUID = 1L;

	protected String version;
	protected String snapshot;
	
	public void initVersion()
	{
		try
		{
			Manifest manifest = new Manifest();
			manifest.read(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
			java.util.jar.Attributes attributes = manifest.getMainAttributes();
			version = attributes.getValue("Implementation-Version");
			if(version.endsWith("SNAPSHOT")){snapshot="("+version+")";}
			else{snapshot="";}
		}
		catch (IOException e)
		{
			version = "UNKNOWN VERSION";
			snapshot = version;
		}
	}
	
	public String getSnapshotVersion() {return snapshot;}
	public String getVersion() {return version;}
}