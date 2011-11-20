package net.sf.ahtutils.report;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class AntJasperCreatorTask extends Task{
	
	private String configFile, reportRoot, targetDir;

    static Log logger = LogFactory.getLog(AntJasperCreatorTask.class);
	
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
