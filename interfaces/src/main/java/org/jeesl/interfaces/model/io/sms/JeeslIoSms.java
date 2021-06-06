package org.jeesl.interfaces.model.io.sms;

import java.util.Date;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithRefId;

public interface JeeslIoSms<L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends JeeslStatus<L,D,CATEGORY>,
								SMS extends JeeslIoSms<L,D,CATEGORY,SMS,STATUS>,
								STATUS extends JeeslStatus<L,D,STATUS>
								>
		extends EjbWithId,EjbWithRefId,EjbSaveable,EjbRemoveable
{	
	
	public static enum Status{queue,spooling,sent,failed};
	public static enum Attributes{category,status,recordCreation,recordSpool};
	
	Long getVersionLock();
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	STATUS getStatus();
	void setStatus(STATUS status);
	
	Date getRecordCreation();
	void setRecordCreation(Date recordCreation);
	
	Date getRecordSpool();
	void setRecordSpool(Date recordSpool);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
	
	int getCounter();
	void setCounter(int counter);
	
	String getRecipient();
	void setRecipient(String recipient);
		
	String getTxt();
	void setTxt(String txt);
}