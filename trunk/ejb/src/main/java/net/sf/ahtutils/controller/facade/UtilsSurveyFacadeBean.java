package net.sf.ahtutils.controller.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.survey.EjbSurveyAnswerFactory;
import net.sf.ahtutils.factory.ejb.survey.EjbSurveyTemplateFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSurveyFacade;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurvey;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyAnswer;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyCorrelation;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyData;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyOption;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyQuestion;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveySection;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyTemplate;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public class UtilsSurveyFacadeBean <L extends UtilsLang,
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
	extends UtilsFacadeBean implements UtilsSurveyFacade<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>
{	
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efAnswer;
	
	public UtilsSurveyFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override public TEMPLATE load(Class<TEMPLATE> cTemplate, TEMPLATE template)
	{
		template = em.find(cTemplate,template.getId());
		template.getSections().size();
		return template;
	}
	
	@Override public SECTION load(Class<SECTION> cSection, SECTION section)
	{
		section = em.find(cSection,section.getId());
		section.getSections().size();
		section.getQuestions().size();
		return section;
	}
	
	@Override public SURVEY load(Class<SURVEY> cSurvey, SURVEY survey)
	{
		survey = em.find(cSurvey,survey.getId());
		survey.getSurveyData().size();
		return survey;
	}

	@Override public DATA load(Class<DATA> cData, DATA data)
	{
		data = em.find(cData,data.getId());
		data.getAnswers().size();
		return data;
	}
	
	@Override
	public TEMPLATE fcSurveyTemplate(Class<TEMPLATE> cTemplate, Class<TS> cTS, TC category)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();

		CriteriaQuery<TEMPLATE> cQ = cB.createQuery(cTemplate);
		Root<TEMPLATE> root = cQ.from(cTemplate);

		Path<TC> pathCategory = root.get("category");

		cQ.where(pathCategory.in(category));
		cQ.select(root).distinct(true);

		List<TEMPLATE> list = em.createQuery(cQ).getResultList();
		if(list.size()==0)
		{
			TEMPLATE template = null;
			try
			{
				TS status = this.fByCode(cTS,UtilsSurveyOption.Status.open.toString());
				EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fTemplate = EjbSurveyTemplateFactory.factory(cTemplate);
				template = fTemplate.build(category,status,"");
				em.persist(template);
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
			return template;
		}
		else
		{
			return list.get(0);
		}
	}

	@Override
	public List<ANSWER> fcAnswers(Class<DATA> cData, Class<ANSWER> cAnswer, DATA data)
	{
		data = em.find(cData,data.getId());
		List<ANSWER> result = new ArrayList<ANSWER>();
		efAnswer = EjbSurveyAnswerFactory.factory(cAnswer);
		
		Set<Long> existing = new HashSet<Long>();
		for(ANSWER a : data.getAnswers()){existing.add(a.getQuestion().getId());result.add(a);}
		for(SECTION s : data.getSurvey().getTemplate().getSections())
		{
			for(QUESTION q : s.getQuestions())
			{
				if(!existing.contains(q.getId()))
				{
					try
					{
						ANSWER answer = this.persist(efAnswer.build(q, data));
						result.add(answer);
					}
					catch (UtilsContraintViolationException e) {e.printStackTrace();}
				}
			}
		}
		return result;
	}

	@Override
	public DATA saveData(Class<DATA> cData, Class<CORRELATION> cCorrelation, DATA data) throws UtilsContraintViolationException, UtilsLockingException
	{
		if(data.getCorrelation().getId()>0)
		{
			data.setCorrelation(em.find(cCorrelation,data.getCorrelation().getId()));
		}
		return this.saveProtected(data);
	}
}