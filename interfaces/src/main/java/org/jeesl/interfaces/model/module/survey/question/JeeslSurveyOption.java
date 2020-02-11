package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithNonUniqueCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyOption<L extends JeeslLang, D extends JeeslDescription>
			extends Serializable,EjbWithId,EjbWithNonUniqueCode,EjbWithPosition,//EjbSaveable,
					EjbWithLang<L>,EjbWithDescription<D>
{
	enum Units{yn,number,txt}
	enum Status{open}
	
	double getScore();
	void setScore(double score);
	
	boolean getRow();
	void setRow(boolean row);
	
	boolean getCol();
	void setCol(boolean col);
	
	boolean getCell();
	void setCell(boolean cell);
}