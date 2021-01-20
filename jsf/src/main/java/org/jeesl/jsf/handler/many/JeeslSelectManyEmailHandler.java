package org.jeesl.jsf.handler.many;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.with.primitive.text.EjbWithEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslSelectManyEmailHandler <T extends EjbWithEmail>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslSelectManyEmailHandler.class);
	
	private List<String> emails; public List<String> getEmails() {return emails;} public void setEmails(List<String> emails) {this.emails = emails;}
	private final List<T> list; public List<T> getList() {return list;}
	
	public JeeslSelectManyEmailHandler()
	{
		list = new ArrayList<>();
	}
	
	public void updateList(List<T> list)
	{
		this.list.clear();
		this.list.addAll(list);
	}
	
	public void addToList(T ejb)
	{
		list.add(ejb);
	}
	
	public void addToList(List<T> list)
	{
		this.list.addAll(list);
	}
	
	public void clear()
	{
		if(emails!=null) {emails.clear();}
	}
	
	public void init(List<T> ejbs)
	{
		clear();
		if(emails==null) {emails = new ArrayList<>();}
		for(T t : ejbs) {emails.add(t.getEmail());}
	}
	
	public List<T> toEjb()
	{
		List<T> result = new ArrayList<T>();
		for(String email : emails)
		{
			for(T t : list) {if(t.getEmail().equals(email)) {result.add(t);}}
		}
		return result;
	}
}