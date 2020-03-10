package org.jeesl.interfaces.controller.handler.tree.store;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslTree1Store<L1 extends EjbWithId>
{
	void storeTreeLevel1(L1 l1);
}