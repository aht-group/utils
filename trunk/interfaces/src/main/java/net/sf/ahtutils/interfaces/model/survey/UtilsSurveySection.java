package net.sf.ahtutils.interfaces.model.survey;

import java.util.List;

import net.sf.ahtutils.interfaces.model.with.EjbWithLevel;
import net.sf.ahtutils.interfaces.model.with.EjbWithRemark;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface UtilsSurveySection<L extends UtilsLang,
							D extends UtilsDescription,
							SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							SS extends UtilsStatus<SS,L,D>,
							TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							OT extends UtilsStatus<OT,L,D>,
							CORRELATION extends EjbWithId>
			extends EjbWithId,EjbWithName,EjbWithRemark,EjbWithPosition,EjbWithLevel,EjbWithVisible
{
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	SECTION getParentSection();
	void setParentSection(SECTION section);
	
	List<SECTION> getSections();
	void setSections(List<SECTION> sections);
}