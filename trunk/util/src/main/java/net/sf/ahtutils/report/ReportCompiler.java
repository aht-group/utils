package net.sf.ahtutils.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;

public class ReportCompiler {

    static Log logger = LogFactory.getLog(ReportCompiler.class);
	
    public static void execute(String configFile, String reportRoot)
    {
    	 logger.info("Using " +configFile +" for report configuration.");
 		
 		Reports reports = null;
 		try {reports = (Reports)JaxbUtil.loadJAXB(reportRoot +"/" +configFile, Reports.class);}
 		catch (FileNotFoundException e) {logger.error(e.getMessage());}
 		
 		//Compiling reports
 		logger.info("Pre-Compiling "+reports.getReport().size()+" Report(s)");
 		for(Report report : reports.getReport())
 		{
 			logger.info("Report: "+report.getId() +" (" +report.getMedia().size() + " media outputs)");
 			for (Media media : report.getMedia())
 			{
 				logger.info("Compiling " +media.getJr().size() +" reports for output -" +media.getType() +"-");
 				for (Jr jr : media.getJr())
 				{
 					logger.info(jr.getName());
 					//Compiling for left to right and right to left languages
 					
 					//Compiling ltr version
 					String jrxml  = reportRoot +"/" +"jrxml"  +"/" +report.getDir() +"/" + media.getType() + "/" + jr.getType() + jr.getName() +".jrxml";
 					String jasperLtr = reportRoot +"/" +"jasper" +"/" +report.getDir() +"/" + media.getType() + "/ltr/" + jr.getType() + jr.getName() +".jasper";
 					logger.info("Compiling " +jrxml +" to " +jasperLtr);
 					try {
 						new File(reportRoot +"/" +"jasper"  +"/" +report.getDir() +"/" + media.getType() + "/ltr/").mkdirs();
 						JasperCompileManager.compileReportToFile(jrxml, jasperLtr);
 					} catch (JRException e) {
 						logger.error(e.getMessage());
 					}
 					
 					//Compiling rtl version
 					String jasperRtl = reportRoot +"/" +"jasper" +"/" +report.getDir() +"/" + media.getType() + "/rtl/" + jr.getType() + jr.getName() +".jasper";
 					logger.info("Compiling " +jrxml +" to " +jasperRtl);
 					new File(reportRoot +"/" +"jasper"  +"/" +report.getDir() +"/" + media.getType() + "/rtl/").mkdirs();
 					InputStream in = null;
 					try {
 						in = ReportUtilRtl.LeftToRightConversion(jrxml);
 					} catch (JDOMException e) {
 						logger.error("Problem converting to right-to-left language.");
 					}
 					try {
 						JasperCompileManager.compileReportToStream(in, new FileOutputStream(jasperRtl));
 					} catch (FileNotFoundException e) {
 						logger.error(e.getMessage());
 					} catch (JRException e) {
 						logger.error(e.getMessage());
 					}
 				}
 			}
 		}
 	}
}
