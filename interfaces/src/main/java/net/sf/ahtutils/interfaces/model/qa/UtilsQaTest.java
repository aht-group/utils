package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithNr;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface UtilsQaTest<
							GROUP extends UtilsQaGroup<?,?,?>,
							QAC extends UtilsQaCategory<?,?>,
							QAR extends UtilsQaResult<?,?,?>,
							QATD extends UtilsQaTestDiscussion<?,?>,
							QATI extends UtilsQaTestInfo<?>,
							QATS extends JeeslStatus<?,?,QATS>>
			extends Serializable,EjbSaveable,EjbWithNr,EjbWithId,EjbWithName,EjbWithCode
{
	QAC getCategory();
	void setCategory(QAC category);
	
	String getReference();
	void setReference(String reference);

	String getUrl();
	void setUrl(String url);
	
	String getDescription();
	void setDescription(String description);
	
	String getPreCondition();
    void setPreCondition(String preCondition);
    
    String getSteps();
	void setSteps(String steps);
	
	String getExpectedResult();
	void setExpectedResult(String expectedResult);
	
	List<QATD> getDiscussions();
	void setDiscussions(List<QATD> discussions);
	
	List<QAR> getResults();
	void setResults(List<QAR> results);
	
	QATS getClientStatus();
	void setClientStatus(QATS clientStatus);
	
	QATS getDeveloperStatus();
	void setDeveloperStatus(QATS developerStatus);
	
	QATI getInfo();
	void setInfo(QATI info);
	
	Double getWeight();
	void setWeight(Double weight);
	
	Date getRecord();
	void setRecord(Date record);
	
	Integer getDuration();
	void setDuration(Integer duration);
	
	Boolean getVisible();
	void setVisible(Boolean visible);
	
	List<GROUP> getGroups();
	void setGroups(List<GROUP> groups);
}