package net.sf.ahtutils.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;

public abstract class AbstractReportControl
{
	static Log logger = LogFactory.getLog(AbstractReportControl.class);

	public static enum Output{pdf,xls}
	public static enum Source{database,example,report}
	public static enum Direction{ltr,rtl}	
	
	protected Reports config;
	protected MultiResourceLoader mrl;
	protected Map<String,Object> mapReportParameter;
	
	protected String rId,lang;
	protected JasperPrint jPrint;
	
	protected String reportRoot = "";
	
	public AbstractReportControl(Reports config)
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
	
	public void setReportRoot(String reportRoot)
	{
		this.reportRoot = reportRoot;
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
		Report  report  = (Report)JXPathContext.newContext(config).getValue("report[id='" +rId +"']");
		for (Media media : report.getMedia())
		{
			String rDir = reportRoot +"jrxml/"+report.getDir() +"/" +media.getType() +"/";
			for (Jr jr : media.getJr())
			{
				if (jr.getType().equals("sr"))
				{
					String rName    = "";
					String fileName = jr.getType() +jr.getName();
					logger.debug("Compiling " +fileName);
					JasperReport jreport = getReport(rDir, fileName,dir);
					mapReportParameter.put(rName, jreport);
				}
			}
		}
	}
	
	protected JasperReport getMasterReport(Output output, Direction dir)
	{
		
		Jr jr  = (Jr)JXPathContext.newContext(config).getValue("report[id='"+ rId +"']/media[type='" +output.name() +"']/jr[type='mr']");
		String rName = jr.getName();
		String rDir  = reportRoot +"jrxml/"+(String)JXPathContext.newContext(config).getValue("report[id='" +rId +"']/@dir") +"/" +output.name();
		return getReport(rDir, "mr"+rName,dir);
	}
	
	private JasperReport getReport(String rDir, String fileName, Direction dir)
	{
		JasperReport report = null;
		fileName = rDir+"/"+fileName;
		fileName = FilenameUtils.normalize(fileName);
		fileName = fileName.replace("\\", "/");
		File compiledReport = new File(fileName+".jasper");
		if (compiledReport.exists())
		{
			logger.debug("Loading pre-compiled Report: "+fileName);
			FileInputStream in;
			try {
				in = new FileInputStream(compiledReport);
				report = (JasperReport)JRLoader.loadObject(in);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (JRException e) {
				logger.error(e.getMessage());
			} 
		}
		else
		{
			fileName = fileName +".jrxml";
			logger.debug("Compiling Report: "+fileName);
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
		}
		return report;
	}
}