package org.jeesl.interfaces.model.system.job;

public interface EjbWithMigrationJob1 <STATUS extends JeeslJobStatus<?,?,?,?>>
{
	public enum Attributes{job1}
	
	STATUS getJob1();
	void setJob1(STATUS job1);
}
