package org.jeesl.interfaces.controller.handler.tree.cache;

import java.util.List;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslTree5Cache<L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId, L5 extends EjbWithId> extends JeeslTree4Cache<L1,L2,L3,L4>
{
	List<L5> getCachedChildsForL4(L4 ejb);
}