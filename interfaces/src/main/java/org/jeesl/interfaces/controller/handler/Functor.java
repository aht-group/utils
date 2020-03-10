package org.jeesl.interfaces.controller.handler;

public interface Functor<T>
{
	public void execute(T element);
}