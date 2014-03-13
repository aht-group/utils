package net.sf.ahtutils.interfaces.model.date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithYear extends EjbWithId
{
    public int getYear();
    public void setYear(int year);
}