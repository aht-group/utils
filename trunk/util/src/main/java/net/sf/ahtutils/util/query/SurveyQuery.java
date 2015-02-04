package net.sf.ahtutils.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.factory.xml.qa.XmlResultFactory;
import net.sf.ahtutils.factory.xml.status.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatementFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Description;
import net.sf.ahtutils.xml.qa.Expected;
import net.sf.ahtutils.xml.qa.PreCondition;
import net.sf.ahtutils.xml.qa.Reference;
import net.sf.ahtutils.xml.qa.Result;
import net.sf.ahtutils.xml.qa.Steps;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.survey.Template;

public class SurveyQuery
{
	public static enum Key {exTeplate}
	
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
		xml.setCategory(XmlCategoryFactory.create(""));
		xml.setStatus(XmlStatusFactory.create(""));
				
		return xml;
	}
	
	public static Staff staff()
	{
		Staff xml = new Staff();
		xml.setId(0);
		xml.setRole(SecurityQuery.role());
		xml.setUser(SecurityQuery.user());
		return xml;
	}
	
	public static Result result()
	{
		Role role = new Role();
		role.setCode("");
		
		Staff staff = new Staff();
		staff.setId(0);
		staff.setRole(role);
		staff.setUser(SecurityQuery.user());
		
		Status status = XmlStatusFactory.create("");
		status.setImage("");
		
		Result xml = new Result();
		xml.setStaff(staff);
		xml.setId(0);
		xml.setStatus(status);
		xml.setActual(XmlResultFactory.buildActual(""));
		xml.setComment(XmlResultFactory.buildComment(""));
		
		return xml;
	}
	
	public static Category category()
	{
		Category xml = new Category();
		xml.setId(0);
		xml.setCode("");
		xml.setName("");
		return xml;
	}
	
	public static Test test()
	{
		Test xml = new Test();
		xml.setId(0);
		xml.setCode("");
		xml.setName("");
		
		xml.setReference(new Reference());
		xml.setPreCondition(new PreCondition());
		xml.setDescription(new Description());
		xml.setSteps(new Steps());
		xml.setExpected(new Expected());
		
		xml.setStatus(XmlStatusFactory.create(""));
		xml.setStatement(XmlStatementFactory.build(""));
		
		return xml;
	}
}
