package net.sf.ahtutils.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.xml.access.Action;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.security.Category;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.security.User;
import net.sf.ahtutils.xml.status.Domain;

public class SecurityQuery
{
	public static enum Key {role,exStaff}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				default: break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Role role()
	{
		Role xml = new Role();
		xml.setId(0);
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
	
	public static User user()
	{
		User xml = new User();
		xml.setId(0);
		xml.setFirstName("");
		xml.setLastName("");
		return xml;
	}
	
	public static Staff exStaff()
	{
		Role role = new Role();
		role.setCode("");
		
		Domain domain = new Domain();
		domain.setId(0);
		
		User user = new User();
		user.setId(0);
		
		Staff xml = new Staff();
		xml.setRole(role);
		xml.setUser(user);
		xml.setDomain(domain);
		
		return xml;
	}
	
	public static Category exCategory()
	{
		Category xml = new Category();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
	
	public static View exView()
	{
		View xml = new View();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
	
	public static Role exRole()
	{
		Role xml = new Role();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
	
	public static Action exAction()
	{
		Action xml = new Action();
		xml.setCode("");
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
}
