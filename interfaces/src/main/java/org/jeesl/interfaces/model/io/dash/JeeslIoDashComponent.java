package org.jeesl.interfaces.model.io.dash;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

public interface JeeslIoDashComponent <L extends JeeslLang, D extends JeeslDescription,
DBC extends JeeslIoDashComponent<L,D,DBC>>
extends Serializable,EjbSaveable,EjbRemoveable,
EjbWithParentAttributeResolver,
EjbWithLang<L>,EjbWithDescription<D>,
EjbWithCode,EjbWithPosition
{
public enum Attributes{dashboard}

}