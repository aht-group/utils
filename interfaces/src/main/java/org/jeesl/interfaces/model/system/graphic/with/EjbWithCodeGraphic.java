package org.jeesl.interfaces.model.system.graphic.with;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.graphic.EjbWithGraphic;

public interface EjbWithCodeGraphic<G extends JeeslGraphic<?,?,?,?,?>> extends EjbWithGraphic<G>,EjbWithCode
{
}