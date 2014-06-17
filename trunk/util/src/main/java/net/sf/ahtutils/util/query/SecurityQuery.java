package net.sf.ahtutils.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.security.Role;
import net.sf.ahtutils.xml.security.User;

public class SecurityQuery
{
	public static enum Key {role}
	
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
}
