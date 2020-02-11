package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.EjbWithRendered;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.text.EjbWithRemark;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveyQuestion<L extends JeeslLang, D extends JeeslDescription,
										SECTION extends JeeslSurveySection<L,D,?,SECTION,?>,
										CONDITION extends JeeslSurveyCondition<?,QE,OPTION>,
										VALIDATION extends JeeslSurveyValidation<L,D,?,?>,
										QE extends JeeslSurveyQuestionElement<L,D,QE,?>,
										SCORE extends JeeslSurveyScore<L,D,?,?>,
										UNIT extends JeeslSurveyQuestionUnit<L,D,UNIT,?>,
										OPTIONS extends JeeslSurveyOptionSet<L,D,?,OPTION>,
										OPTION extends JeeslSurveyOption<L,D>,
										AQ extends JeeslSurveyAnalysisQuestion<L,D,?,?>
										>
			extends Serializable,EjbWithCode,EjbWithRemark,EjbWithPosition,EjbWithVisible,EjbSaveable,EjbRemoveable,
					EjbWithRendered,EjbWithParentAttributeResolver,
					EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{section,visible,position,optionSet}
	
	public enum Elements{selectOne};
	public enum AttributeTypeCode{text,number,bool};
	
	SECTION getSection();
	void setSection(SECTION section);
	
	String getTopic();
	void setTopic(String topic);
	
	public Map<String,D> getText();
	public void setText(Map<String,D> name);
	
	String getQuestion();
	void setQuestion(String question);
	
	UNIT getUnit();
	void setUnit(UNIT unit); 
	
	OPTIONS getOptionSet();
	void setOptionSet(OPTIONS optionSet);
	
	List<OPTION> getOptions();
	void setOptions(List<OPTION> options);
	
	Boolean getCalculateScore();
	void setCalculateScore(Boolean calculateScore);
	
	Double getMinScore();
	void setMinScore(Double maxScore);
	
	Double getMaxScore();
	void setMaxScore(Double maxScore);
	
	Boolean getBonusScore();
	void setBonusScore(Boolean bonusScore);
	
	Boolean getShowBoolean();
	void setShowBoolean(Boolean showBoolean);
	
	Boolean getShowDate();
	void setShowDate(Boolean showDate);
	
	Boolean getShowInteger();
	void setShowInteger(Boolean showInteger);
	
	Boolean getShowDouble();
	void setShowDouble(Boolean showDouble);
	
	Boolean getShowText();
	void setShowText(Boolean showText);
	
	Boolean getShowScore();
	void setShowScore(Boolean showScore);
	
	Boolean getShowRemark();
	void setShowRemark(Boolean showRemark);
	
	Boolean getShowSelectOne();
	void setShowSelectOne(Boolean showSelectOne);
	
	Boolean getShowSelectMulti();
	void setShowSelectMulti(Boolean showSelectMulti);
	
	Boolean getShowMatrix();
	void setShowMatrix(Boolean showMatrix);
	
	List<SCORE> getScores();
	void setScores(List<SCORE> scores);
	
	String getRenderCondition();
	void setRenderCondition(String renderCondition);
	
	List<CONDITION> getConditions();
	void setConditions(List<CONDITION> conditions);
	
	Boolean getMandatory();
	void setMandatory(Boolean mandatory);
	
	Boolean getShowEmptyOption();
	void setShowEmptyOption(Boolean showEmptyOption);
	
	List<VALIDATION> getValidations();
	void setValidations(List<VALIDATION> validations);
}