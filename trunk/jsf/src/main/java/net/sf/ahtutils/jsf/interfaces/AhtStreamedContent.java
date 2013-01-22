package net.sf.ahtutils.jsf.interfaces;

import java.util.Date;

import org.primefaces.model.StreamedContent;

public interface AhtStreamedContent extends StreamedContent
{	
	public Date getRecord();
	public void setRecord(Date record);
}