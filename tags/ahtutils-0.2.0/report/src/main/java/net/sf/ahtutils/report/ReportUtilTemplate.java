package net.sf.ahtutils.report;

import java.awt.Color;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

public class ReportUtilTemplate {
	
	public void create() throws JRException
	{
		//JasperDesign represents the in-memory JRXML report design
		JasperDesign design = new JasperDesign();
		
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
	
	public static void main(String[] args) throws JRException
	{
		ReportUtilTemplate templater = new ReportUtilTemplate();
		templater.create();
	}

}
