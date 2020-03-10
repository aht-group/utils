package org.jeesl.util;

public interface Expression<T>
{
	public boolean condition(T element);
}