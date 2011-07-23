package net.sf.ahtutils.report;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class AntJasperCreatorTask extends Task{
	
	private Boolean pdf, xls, rtl;
	

	private String srcDir, dstDir;
	
	private String configFile;

    static Log logger = LogFactory.getLog(AntJasperCreatorTask.class);
	protected Configuration config;
	
    public void execute() throws BuildException
    {
    	//Create Configuration object from configuration file
        ConfigurationFactory factory = new ConfigurationFactory(configFile);
        Configuration config = null;
        try {
			config = factory.getConfiguration();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		
		//Get the ReportController from ahtutils
		ReportController reportController = new ReportController(config);

		//Get a list of all reports in configuration file
		String xPathPrefix = "//report";
		int reportCount = (config.getStringArray(xPathPrefix)).length;
		logger.debug("Pre-Compiling "+reportCount+" Reports");
		for(int i=1;i<=reportCount;i++)
		{
			//Compile reports and save to file
			String rId = config.getString(xPathPrefix+"/["+i+"]/@id");
			reportController.setParameter("en", rId);
		}
    }

	public static void main(String[] args) {
		AntJasperCreatorTask task = new AntJasperCreatorTask();
		task.setConfigFile("../aht-java/resources/config/erp/erp.xml");
		task.execute();

	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	public Boolean getPdf() {
		return pdf;
	}

	public void setPdf(Boolean pdf) {
		this.pdf = pdf;
	}

	public Boolean getXls() {
		return xls;
	}

	public void setXls(Boolean xls) {
		this.xls = xls;
	}

	public Boolean getRtl() {
		return rtl;
	}

	public void setRtl(Boolean rtl) {
		this.rtl = rtl;
	}

	public String getSrcDir() {
		return srcDir;
	}

	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}

	public String getDstDir() {
		return dstDir;
	}

	public void setDstDir(String dstDir) {
		this.dstDir = dstDir;
	}

}
