package org.jeesl.controller.handler.invisible;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public class Invisible1Handler<A extends EjbWithVisible> implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(Invisible1Handler.class);

	protected Class<A> cA;
	
	private final List<A> listA; public List<A> getListA() {return listA;}

	public Invisible1Handler()
	{
		listA = new ArrayList<A>();
	}
	
	public void updateA(A a)
	{
		listA.clear();
		if(!a.isVisible() && a!=null) {listA.add(a);}
	}
}