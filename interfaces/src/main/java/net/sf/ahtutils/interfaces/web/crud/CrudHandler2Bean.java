package net.sf.ahtutils.interfaces.web.crud;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface CrudHandler2Bean <T extends EjbWithId>
{
	T crud2Build(Class<T> cT);
	T crud2Update(T t);
	void crud2Select();
}