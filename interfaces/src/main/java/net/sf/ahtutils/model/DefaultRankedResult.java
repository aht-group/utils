package net.sf.ahtutils.model;

import java.io.Serializable;

import org.jeesl.interfaces.model.util.UtilsRankedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class DefaultRankedResult <T extends EjbWithId> implements Serializable,UtilsRankedResult<T>
{
	final static Logger logger = LoggerFactory.getLogger(DefaultRankedResult.class);
	public static final long serialVersionUID=1;

	double ranking;
	@Override public double getRanking() {return ranking;}
	@Override public void setRanking(double ranking) {this.ranking = ranking;}

	T entity;
	@Override public T getEntity() {return entity;}
	@Override public void setEntity(T entity) {this.entity = entity;}
}
