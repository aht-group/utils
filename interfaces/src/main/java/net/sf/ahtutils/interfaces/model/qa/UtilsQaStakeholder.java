package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;

import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsQaStakeholder<QA extends UtilsQualityAssurarance<?,?,?>>
			extends Serializable,EjbWithId,EjbWithName,EjbWithCode
{
	QA getQa();
	void setQa(QA qa);
}