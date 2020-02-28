package org.jeesl.interfaces.controller.handler.tree;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslTree4Store<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId> extends JeeslTree3Store<L1,L2,L3>
{
	void storeTreeLevel4(L4 l4);
}