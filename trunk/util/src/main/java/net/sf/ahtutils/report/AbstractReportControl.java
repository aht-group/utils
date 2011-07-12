package net.sf.ahtutils.report;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;

public abstract class AbstractReportControl
{
	static Log logger = LogFactory.getLog(AbstractReportControl.class);

	public static enum Output{pdf,xls}
	public static enum Source{database,example,report}
	public static enum Direction{ltr,rtl}	
	
	protected Configuration config;
	protected MultiResourceLoader mrl;
	protected Map<String,Object> mapReportParameter;
	
	protected String rId,lang;
	protected JasperPrint jPrint;
	
	public AbstractReportControl(Configuration config)
	{
		this.config=config;
		mrl = new MultiResourceLoader();
		mapReportParameter = new Hashtable<String,Object>();
		jPrint = new JasperPrint();
		lang = "en";
	}
	
	public void setParameter(String lang, String rId)
	{
		this.lang=lang;
		this.rId=rId;
	}
	
	public JasperPrint getJasperPrint(Output output, Direction dir)
	{
		JasperPrint  print = new JasperPrint();
		JasperReport report = getMasterReport(output,dir);
		try {print  = JasperFillManager.fillReport(report, mapReportParameter);}
		catch (JRException e) {e.printStackTrace();}
		return print;
	}
	
	public void compileSubReports(Output output, Direction dir)
	{	
		String xPathPrefix = "reports/report[@id='"+rId+"']/"+output.toString();
		String rDir = config.getString(xPathPrefix+"/@dir");
		int anz = (config.getStringArray(xPathPrefix+"/jr[@type='sr']/@name")).length;
		logger.debug("Compiling "+anz+" Sub Reports");
		for(int i=1;i<=anz;i++)
		{
			String rName = "sr"+config.getString(xPathPrefix+"/jr[@type='sr']["+i+"]/@name");
			String fileName = rName;
			try
			{
				fileName = config.getString(xPathPrefix+"/jr[@type='sr']["+i+"]/@file");
			}
			catch (NoSuchElementException e){}
			JasperReport jr = getReport(rDir, fileName,dir);
			mapReportParameter.put(rName, jr);
		}
	}
	
	protected JasperReport getMasterReport(Output output, Direction dir)
	{
		String xPathPrefix = "reports/report[@id='"+rId+"']/"+output.toString();
		String rName = config.getString(xPathPrefix+"/jr[@type='mr']/@name");
		String rDir = config.getString(xPathPrefix+"/@dir");
		return getReport(rDir, "mr"+rName,dir);
	}
	
	private JasperReport getReport(String rDir, String fileName, Direction dir)
	{
		JasperReport report = null;
		fileName = rDir+"/"+fileName;
		fileName = FilenameUtils.normalize(fileName);
		fileName = fileName.replace("\\", "/")+".jrxml";
		logger.debug("Getting Report: "+fileName);
		try
		{
			InputStream is=null;
			switch(dir)
			{
				case ltr: is = mrl.searchIs(fileName);break;
				case rtl: is = ReportUtil.LeftToRightConversion(fileName);break;
			}
			report = JasperCompileManager.compileReport(is);
		}
		catch (JRException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
		catch (JDOMException e) {logger.error(e);}
		return report;
	}
}