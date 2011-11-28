package net.sf.ahtutils.report;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntJasperCreatorTask extends Task
{
	final static Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	private String configFile, reportRoot, targetDir;
	
    public void execute() throws BuildException
    {
    	ReportCompiler.execute(configFile, reportRoot, targetDir);
  	}

	public static void main(String[] args) {
		AntJasperCreatorTask task = new AntJasperCreatorTask();
		task.execute();

	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}


	public String getReportRoot() {
		return reportRoot;
	}

	public void setReportRoot(String reportRoot) {
		this.reportRoot = reportRoot;
	}
}
