package org.jeesl.controller.handler.module.survey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.factory.ejb.util.EjbCodeFactory;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class helps to find s1o for Survey imports
public class SurveyOptionHandler<QUESTION extends JeeslSurveyQuestion<?,?,?,?,?,?,?,?,?,OPTION,?>,
								OPTION extends JeeslSurveyOption<?,?>>
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SurveyOptionHandler.class);
	private static final long serialVersionUID = 1L;
	
	private final Map<QUESTION,Map<String,OPTION>> mapOption;
	
	public SurveyOptionHandler()
	{
		mapOption = new HashMap<>();
	}
	
	public void clear() {mapOption.clear();}
	
	public void add(QUESTION question, List<OPTION> options)
	{
		mapOption.put(question,EjbCodeFactory.toMapNonUniqueCode(options));
	}
	
	public OPTION toOption(QUESTION question, String code) throws JeeslConstraintViolationException
	{
		
		if(code==null)
		{
			if(BooleanComparator.active(question.getMandatory())){throw new JeeslConstraintViolationException("Answer is mandatory");}
			else {return null;}
			
		}
		
		if(mapOption.containsKey(question))
		{
			if(mapOption.get(question).containsKey(code)) {return mapOption.get(question).get(code);}
			else {throw new JeeslConstraintViolationException("No Option for question:{"+question.toString()+"} with code:"+code);}
		}
		else {throw new JeeslConstraintViolationException("Nothing defined for "+question.toString());}
	}
}