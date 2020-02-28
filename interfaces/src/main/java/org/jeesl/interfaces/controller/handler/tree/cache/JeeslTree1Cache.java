package org.jeesl.interfaces.controller.handler.tree.cache;

import java.util.List;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslTree1Cache<L1 extends EjbWithId>
{
	List<L1> getCachedL1();
}