package org.jeesl.interfaces.controller.handler;

public interface Expression<T>
{
	public boolean condition(T element);
}