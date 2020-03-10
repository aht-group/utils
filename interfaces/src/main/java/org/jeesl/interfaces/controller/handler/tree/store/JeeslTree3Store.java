package org.jeesl.interfaces.controller.handler.tree.store;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslTree3Store<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId> extends JeeslTree2Store<L1,L2>
{
	void storeTreeLevel3(L3 l3);
}