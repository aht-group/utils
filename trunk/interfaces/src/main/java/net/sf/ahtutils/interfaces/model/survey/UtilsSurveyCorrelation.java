package net.sf.ahtutils.interfaces.model.survey;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsSurveyCorrelation<L extends UtilsLang,
										D extends UtilsDescription,
										SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										SS extends UtilsStatus<SS,L,D>,
										TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
			extends EjbWithId
{
//	DATA getData();
//	void setData(DATA data);
}