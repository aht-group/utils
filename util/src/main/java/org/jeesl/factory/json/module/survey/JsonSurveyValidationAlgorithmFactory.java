package org.jeesl.factory.json.module.survey;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidationAlgorithm;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.model.json.survey.validation.ValidationAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSurveyValidationAlgorithmFactory<L extends JeeslLang,D extends JeeslDescription,
													VALGORITHM extends JeeslSurveyValidationAlgorithm<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyValidationAlgorithmFactory.class);
	
	@SuppressWarnings("unused")
	private final String localeCode;
	private final ValidationAlgorithm q;
	
	public JsonSurveyValidationAlgorithmFactory(String localeCode, ValidationAlgorithm q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public static ValidationAlgorithm build() {return new ValidationAlgorithm();}
	
	public ValidationAlgorithm build(VALGORITHM ejb)
	{
		ValidationAlgorithm json = build();
		
		if(q.getId()!=null) {json.setId(ejb.getId());}
		if(q.getCode()!=null) {json.setCode(ejb.getCode());}
		if(q.getConfig()!=null) {json.setConfig(ejb.getConfig());}
		
		return json;
	}
	
}