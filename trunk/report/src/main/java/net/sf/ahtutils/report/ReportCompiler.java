package net.sf.ahtutils.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

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
    static ArrayList<String> log;
    static String currentHash;
    
    public static Boolean compileReport(String jrxml, String jasper)
    {
    	Boolean compilation = true;
    	
    	ReportUtilHash hashUtil = new ReportUtilHash(jrxml);
    	String oldHash = hashUtil.readAndRemoveHash();
    	String newHash = hashUtil.calculateHash();
    	log.add("Hash was:");
    	log.add(oldHash);
    	log.add(newHash);
    	log.add("is new Hash");
    	
    	if (!(newHash.equals(oldHash)))
    	{
    		compilation=true;
    		log.add("Re-Compilation needed.");
    	}
		else
		{
			compilation = false;
		    log.add("Re-Compilation not needed.");
		}
    	
    	
	    if (compilation || !(new File(jasper).exists()))
	    {
	    	try {
				JasperCompileManager.compileReportToFile(jrxml, jasper);
				hashUtil.saveNewHash();
			} catch (JRException e) {
				logger.error(e.getMessage());
				log.add(e.getMessage());
			}
			logger.info("Compiling " +jrxml +" to " +jasper);
			log.add("Compiled  " +jrxml +" to " +jasper);
	    }
    	return compilation;
    }
    
    public static Boolean compileReport(InputStream report ,String jrxml, String jasper)
    {
    	Boolean compilation = true;
    	
    	ReportUtilHash hashUtil = new ReportUtilHash(report, jrxml);
    	String oldHash = hashUtil.readAndRemoveHash();
    	String newHash = hashUtil.calculateHash();
    	log.add("Hash was:");
    	log.add(oldHash);
    	log.add(newHash);
    	log.add("is new Hash");
    	
    	if (!(newHash.equals(oldHash)))
    	{
    		compilation=true;
    		log.add("Re-Compilation needed.");
    	}
		else
		{
			compilation = false;
		    log.add("Re-Compilation not needed.");
		}
    	
    	
	    if (compilation || !(new File(jasper).exists()))
	    {
	    	try {
				JasperCompileManager.compileReportToFile(jrxml, jasper);
				hashUtil.saveNewHash();
			} catch (JRException e) {
				logger.error(e.getMessage());
				log.add(e.getMessage());
			}
			logger.info("Compiling " +jrxml +" to " +jasper);
			log.add("Compiled  " +jrxml +" to " +jasper);
	    }
    	return compilation;
    }
    
    public static ArrayList<String> execute(String configFile, String reportRoot) 
    {
    	log = new ArrayList<String>();
    	
    	logger.info("Using " +configFile +" for report configuration.");
 		log.add("Using " +configFile +" for report configuration.");
 		
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
 					String jrxml  = reportRoot +"/" +"jrxml"  +"/" +report.getDir() +"/" + media.getType() + "/" + jr.getType() + jr.getName() +".jrxml";
 					if (report.isSetLtr())
 					{
 					//Compiling ltr version
	 					if (report.isLtr())
	 					{
		 					String jasperLtr = reportRoot +"/" +"jasper" +"/" +report.getDir() +"/" + media.getType() + "/ltr/" + jr.getType() + jr.getName() +".jasper";
		 					new File(reportRoot +"/" +"jasper"  +"/" +report.getDir() +"/" + media.getType() + "/ltr/").mkdirs();
		 					compileReport(jrxml, jasperLtr);
	 					}
 					}
 					
 					//Compiling rtl version
 					if (report.isSetRtl())
 					{
	 					if (report.isRtl())
	 					{
	 						String jasperRtl = reportRoot +"/" +"jasper" +"/" +report.getDir() +"/" + media.getType() + "/rtl/" + jr.getType() + jr.getName() +".jasper";
	 	 					logger.info("Compiling " +jrxml +" to " +jasperRtl);
	 	 					log.add("Compiled  " +jrxml +" to " +jasperRtl);
	 	 					
	 	 					new File(reportRoot +"/" +"jasper"  +"/" +report.getDir() +"/" + media.getType() + "/rtl/").mkdirs();
	 	 					InputStream in = null;
	 	 					try {
	 	 						in = ReportUtilRtl.LeftToRightConversion(jrxml);
	 	 					} catch (JDOMException e) {
	 	 						logger.error("Problem converting to right-to-left language.");
	 	 					}
	 	 					compileReport(in, jrxml, jasperRtl);
	 					}
 					}
 				}
 			}
 		}
 		return log;
 	}
}
