package org.jeesl.api.handler;

import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface OpEntitySelection <T extends EjbWithId>
{
	public static final long serialVersionUID=1;

    public T getOp();
    public void setOp(T op);
    
    public T getTb();
    public void setTb(T tb);

    public List<T> getOpList();
    public void setOpList(List<T> opEntites);

    public List<T> getTbList();
    public void setTbList(List<T> tbEntites);

    public void clearTable();
    public void addEntity(T item) throws JeeslLockingException, JeeslConstraintViolationException, JeeslNotFoundException;
    public void addEntity() throws JeeslLockingException, JeeslConstraintViolationException, JeeslNotFoundException;
    public void removeEntity() throws JeeslLockingException, JeeslConstraintViolationException, JeeslNotFoundException;

    public void selectTb();
}