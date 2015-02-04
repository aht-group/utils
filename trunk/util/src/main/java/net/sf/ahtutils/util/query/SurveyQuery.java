package net.sf.ahtutils.util.query;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.factory.xml.status.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.status.XmlDescriptionFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlUnitFactory;
import net.sf.ahtutils.factory.xml.survey.XmlTemplateFactory;
import net.sf.ahtutils.factory.xml.text.XmlQuestionFactory;
import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.survey.Question;
import net.sf.ahtutils.xml.survey.Section;
import net.sf.ahtutils.xml.survey.Survey;
import net.sf.ahtutils.xml.survey.Template;
import net.sf.exlp.util.DateUtil;

public class SurveyQuery
{
	public static enum Key {exTeplate,exSurvey}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key){return get(key,null);}
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case exTeplate: q.setTemplate(exTeplate());break;
				case exSurvey: q.setSurvey(exSurvey());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Template exTeplate()
	{		
		Template xml = new Template();
		xml.setId(0);
		xml.setCode("");
		xml.setDescription(XmlDescriptionFactory.build(""));
		xml.setCategory(XmlCategoryFactory.create(""));
		xml.setStatus(XmlStatusFactory.create(""));
		xml.getSection().add(exSection());
		return xml;
	}
	
	public static Section exSection()
	{		
		Section xml = new Section();
		xml.setId(0);
		xml.setCode("");
		xml.setPosition(0);
		xml.setDescription(XmlDescriptionFactory.build(""));
		xml.getQuestion().add(exQuestion());
		return xml;
	}
	
	public static Question exQuestion()
	{		
		Question xml = new Question();
		xml.setId(0);
		xml.setPosition(0);
		xml.setCode("");
		xml.setTopic("");
		xml.setUnit(XmlUnitFactory.build(""));
		xml.setQuestion(XmlQuestionFactory.build(""));
		xml.setRemark(XmlRemarkFactory.build(""));
		
		return xml;
	}
	
	public static Survey exSurvey()
	{				
		Survey xml = new Survey();
		xml.setId(0);
		xml.setName("");
		xml.setValidFrom(DateUtil.toXmlGc(new Date()));
		xml.setValidTo(DateUtil.toXmlGc(new Date()));
		xml.setStatus(XmlStatusFactory.create(""));
		xml.setTemplate(XmlTemplateFactory.id());
		
		return xml;
	}
}
