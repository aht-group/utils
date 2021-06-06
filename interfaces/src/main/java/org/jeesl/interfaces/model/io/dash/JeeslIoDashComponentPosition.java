package org.jeesl.interfaces.model.io.dash;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslIoDashComponentPosition<L extends JeeslLang, D extends JeeslDescription,
											DBR extends JeeslStatus<L,D,DBR>,
											DBC extends JeeslIoDashComponent<?,?,DBC>,
											DB extends JeeslIoDashboard<L,D,DBR,DBCP,DB>,
											DBCP extends JeeslIoDashComponentPosition<L,D,DBR,DBC,DB,DBCP>>
	extends Serializable,EjbSaveable,EjbRemoveable, EjbWithId
 {
	@Override
	long getId();
	@Override
	void setId(long id);

	DBC getComponent();
	void setComponent(DBC component);

	int getRow();
	void setRow(int row);

	int getWidth();
	void setWidth(int width);
	DB getDashboard();
	void setDashboard(DB dashboard);

}
