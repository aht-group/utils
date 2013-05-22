package net.sf.ahtutils.model.primefaces.stream;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import net.sf.ahtutils.jsf.interfaces.AhtStreamedContent;

public class PdfStreamedContent implements AhtStreamedContent, Serializable
{	
	private static final long serialVersionUID = 1L;

	private InputStream stream;
	private String contentType;
	private String name;
	private Date record;
		
	public PdfStreamedContent(InputStream stream, String name)
	{
		this.contentType = "application/pdf";
		record = new Date();
		this.stream = stream;
		this.name = name;
	}

	public InputStream getStream() {return stream;}
	public void setStream(InputStream stream) {this.stream = stream;}
	
	public String getContentType() {return contentType;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
}