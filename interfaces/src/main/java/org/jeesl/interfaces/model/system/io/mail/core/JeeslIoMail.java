package org.jeesl.interfaces.model.system.io.mail.core;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoMail<L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								STATUS extends JeeslMailStatus<L,D,STATUS,?>,
								RETENTION extends JeeslStatus<RETENTION,L,D>,
								FRC extends JeeslFileContainer<?,?>
								>
		extends Serializable,EjbWithId,
					EjbRemoveable,EjbPersistable,EjbSaveable
{	
	

	public static enum Retention{fully,partially,toDelete};
	public static enum Attributes{category,status,retention,recordCreation,recordSpool};
	
	Long getVersionLock();
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	STATUS getStatus();
	void setStatus(STATUS status);
	
	RETENTION getRetention();
	void setRetention(RETENTION retention);
	
	Date getRecordCreation();
	void setRecordCreation(Date recordCreation);
	
	Date getRecordSpool();
	void setRecordSpool(Date recordSpool);
	
	Date getRecordSent();
	void setRecordSent(Date recordSent);
	
	String getRecipient();
	void setRecipient(String recipient);
	
	int getCounter();
	void setCounter(int counter);
	
	Integer getRetentionDays();
	void setRetentionDays(Integer retentionDays);
	
	String getXml();
	void setXml(String xml);
}