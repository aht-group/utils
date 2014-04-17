package net.sf.ahtutils.model.primefaces.exception;

import java.io.Serializable;
import java.util.Date;

public class DatatableException implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Date record;
	private String type;
	private String detail;


	public DatatableException()
	{
		
	}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
