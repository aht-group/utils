package org.jeesl.interfaces.controller.handler.tree.cache;

import java.util.List;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslTree2Cache<L1 extends EjbWithId, L2 extends EjbWithId> extends JeeslTree1Cache<L1>
{
	List<L2> getCachedChildsForL1(L1 ejb);
}