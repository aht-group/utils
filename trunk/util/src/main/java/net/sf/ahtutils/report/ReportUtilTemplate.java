package net.sf.ahtutils.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Field;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Template;
import net.sf.ahtutils.xml.report.TestTemplate;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign; 
import net.sf.jasperreports.engine.xml.JRXmlWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportUtilTemplate
{
	final static Logger logger = LoggerFactory.getLogger(ReportUtilXls.class);
	
	public void applyTemplates(Report report)
	{
		for (Media media : report.getMedia())
		{
			
		}
	}
	
	public void create(Template template) throws JRException
	{
		//JasperDesign  the in-memory JRXML report design
		JasperDesign design = new JasperDesign();
		
		//Now, the standard fields need to be declared (holding title, subtitle, logo, footer and record date)
		for (Field field : template.getField())
		{
			JRDesignField reportField = new JRDesignField();
			reportField.setName(field.getName());
			reportField.setDescription(field.getExpression());
			reportField.setValueClass(java.lang.String.class);
			design.addField(reportField);
		}
		
		//A JRDesignBand can represent different report parts like the DetailBand, the PageHeader/Footer, ...
		JRBand header = new JRDesignBand();
		
		//Example of a dynamic text field
		JRDesignTextField textField=new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setBlankWhenNull(true);
		textField.setX(0);
		textField.setY(4);
		textField.setWidth(100);
		textField.setHeight(13);
		textField.setKey("header");
	
		//TextField expression
		JRDesignExpression designExpression=new JRDesignExpression();
		designExpression.setValueClass(String.class);
		designExpression.setText("\"Welcome to the report.\"");

		//Setting the expression
		textField.setExpression(designExpression);
		
		//Add element to header band
		header.getChildren().add(textField);
		
		//A band is then set as a part of the report design
		design.setPageHeader(header);
		
		//JRXmlWriter cares about writing the in-memory design to an OutputStream
		JRXmlWriter.writeReport(design, System.out, "UTF-8");
	}
	
	public static void main(String[] args) throws JRException, FileNotFoundException
	{
		ReportUtilTemplate templater = new ReportUtilTemplate();
		String rootDir = "../xml/src/test/resources/data/xml/report";
		
		File fXml = new File(rootDir,"template.xml");
		Template template = (Template)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Template.class);
		
		File fXml2 = new File(rootDir,"field.xml");
		Field field = (Field)JaxbUtil.loadJAXB(fXml2.getAbsolutePath(), Field.class);
		
		template.getField().add(field);
		templater.create(template);
	}

}
