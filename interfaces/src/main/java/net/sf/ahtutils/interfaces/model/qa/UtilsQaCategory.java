package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.number.EjbWithNr;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsQaCategory<QA extends UtilsQualityAssurarance<?,?,?>,
								QAT extends UtilsQaTest<?,?,?,?,?,?>>
			extends Serializable,EjbSaveable,EjbWithCode,EjbWithNr,EjbWithId,EjbWithName
{
	QA getQa();
	void setQa(QA qa);
	
	List<QAT> getTests();
	void setTests(List<QAT> tests);
}