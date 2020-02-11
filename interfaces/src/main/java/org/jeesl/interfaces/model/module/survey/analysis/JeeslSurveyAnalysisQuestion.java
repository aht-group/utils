package org.jeesl.interfaces.model.module.survey.analysis;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyAnalysisQuestion<L extends JeeslLang, D extends JeeslDescription,
												QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,?,?,?,?>,
												ANALYSIS extends JeeslSurveyAnalysis<L,D,?,?,?,?>>
			extends EjbWithId,EjbWithParentAttributeResolver,EjbSaveable,
					EjbWithLang<L>
{
	public enum Attributes{analysis,question}
	
	ANALYSIS getAnalysis();
	void setAnalysis(ANALYSIS analysis);
	
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
}