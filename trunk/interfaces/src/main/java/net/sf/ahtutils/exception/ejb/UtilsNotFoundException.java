package net.sf.ahtutils.exception.ejb;

import java.io.Serializable;
import java.util.Date;

public class UtilsNotFoundException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;
	
	private boolean withDetails;
	private Date when;

	private String whereKey,whereDetail;
	private String whatKey,whatDetail;

	public UtilsNotFoundException(String s)
	{ 
		super(s);
		withDetails=false;
	}
	
	public UtilsNotFoundException() 
	{
		super("Something is not found, additional infos set in extended attributes of "+UtilsNotFoundException.class.getSimpleName());
		when = new Date();
		withDetails=true;
	}
	
	public String toHash()
	{
		return whereKey+"-"+whatKey;
	}
	
	public boolean isWithDetails() {return withDetails;}
	public Date getWhen() {return when;}
	
	public String getWhereKey() {return whereKey;}
	public void setWhereKey(String whereKey) {this.whereKey = whereKey;}

	public String getWhereDetail() {return whereDetail;}
	public void setWhereDetail(String whereDetail) {this.whereDetail = whereDetail;}

	public String getWhatKey() {return whatKey;}
	public void setWhatKey(String whatKey) {this.whatKey = whatKey;}

	public String getWhatDetail() {return whatDetail;}
	public void setWhatDetail(String whatDetail) {this.whatDetail = whatDetail;}
	
}
