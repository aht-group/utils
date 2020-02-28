package org.jeesl.interfaces.controller.handler.tree;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslTree2Store<L1 extends EjbWithId, L2 extends EjbWithId> extends JeeslTree1Store<L1>
{
	void storeTreeLevel2(L2 l2);
}