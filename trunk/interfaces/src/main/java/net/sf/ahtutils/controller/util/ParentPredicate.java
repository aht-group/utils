package net.sf.ahtutils.controller.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class ParentPredicate <P extends EjbWithId> implements Serializable
{
	private static final long serialVersionUID = 1L;

	private P parent;

	private String name;
	
	public ParentPredicate()
	{
		
	}
	
	public P getParent() {return parent;}
	public void setParent(P parent) {this.parent = parent;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public static <P extends EjbWithId> ParentPredicate<P> create(P parent, String name)
	{
		ParentPredicate<P> pp = new ParentPredicate<P>();
		pp.setParent(parent);
		pp.setName(name);
		return pp;
	}
	
	public <T extends EjbWithId> Predicate to(CriteriaBuilder cB, Root<T> from)
	{
		Path<Object> pPath = from.get(name);
		return cB.equal(pPath, parent.getId());
	}
	
	public static <T extends EjbWithId> List<ParentPredicate<T>> list(ParentPredicate<T> pp)
	{
		List<ParentPredicate<T>> result = new ArrayList<ParentPredicate<T>>();
		
/*		for(int i=0;i<aP.length;i++)
		{
			result.add(aP[i]);
		}
*/		result.add(pp);
		return result;
	}
	
	public static <T extends EjbWithId> List<ParentPredicate<T>> empty()
	{
		return new ArrayList<ParentPredicate<T>>();
	}
	
	public static <T extends EjbWithId, P extends EjbWithId> Predicate[] array(CriteriaBuilder cB, Root<T> from, List<ParentPredicate<P>> list)
	{
		int size=0;
		if(list!=null){size = list.size();}
		Predicate[] result = new Predicate[size];
		for(int i=0;i<size;i++)
		{
			result[i] = list.get(i).to(cB, from);
		}
		return result;
	}
	
	public static <T extends EjbWithId> List<ParentPredicate<T>> createFromList(Class<T> clT, String name, List<T> list)
	{
		List<Long> lLong = new ArrayList<Long>();
		for(T t : list){lLong.add(t.getId());}
		return create(clT, name, lLong);
	}
	public static <T extends EjbWithId> List<ParentPredicate<T>> create(Class<T> clT, String name, List<Long> lLong)
	{
		List<ParentPredicate<T>> result = new ArrayList<ParentPredicate<T>>();
		
		for(Long l : lLong)
		{
			try
			{
				T t = clT.newInstance();
				t.setId(l);
				
				ParentPredicate<T> pp = new ParentPredicate<T>();
				pp.setParent(t);
				pp.setName(name);
				result.add(pp);
			}
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
		}
		
		return result;
	}
}
