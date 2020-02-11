package org.jeesl.jsf.util;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslSelectManyHandler <T extends EjbWithCode>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslSelectManyHandler.class);
	
	private List<String> codes; public List<String> getCodes() {return codes;} public void setCodes(List<String> codes) {this.codes = codes;}
	private final List<T> list; public List<T> getList() {return list;}
	
	public JeeslSelectManyHandler()
	{
		list = new ArrayList<>();
	}
	
	public void updateList(List<T> list)
	{
		this.list.clear();
		this.list.addAll(list);
	}
	
	public void clear()
	{
		if(codes!=null) {codes.clear();}
	}
	
	public void init(List<T> ejbs)
	{
		clear();
		if(codes==null) {codes = new ArrayList<>();}
		for(T t : ejbs) {codes.add(t.getCode());}
	}
	
	public List<T> toEjb()
	{
		List<T> result = new ArrayList<T>();
		for(String code : codes)
		{
			for(T t : list) {if(t.getCode().equals(code)) {result.add(t);}}
		}
		return result;
	}
}