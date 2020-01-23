package org.jeesl.controller.handler.invisible;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public class Invisible2Handler<A extends EjbWithVisible, B extends EjbWithVisible> extends Invisible1Handler<A> implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(Invisible2Handler.class);

	protected Class<B> cB;
	
	private final List<B> listB; public List<B> getListB() {return listB;}

	public Invisible2Handler()
	{
		super();
		listB = new ArrayList<B>();
	}
	
	public void updateB(B b)
	{
		listB.clear();
		if(!b.isVisible() && b !=null) {listB.add(b);}
	}
}