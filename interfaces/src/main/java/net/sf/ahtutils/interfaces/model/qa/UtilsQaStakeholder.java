package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;

import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface UtilsQaStakeholder<QA extends UtilsQualityAssurarance<?,?,?>>
			extends Serializable,EjbWithId,EjbWithName,EjbWithCode
{
	QA getQa();
	void setQa(QA qa);
}