package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.db.EjbDbDumpFileFactory;
import org.jeesl.factory.ejb.system.io.db.EjbIoDumpFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbConnectionColumn;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpHost;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbReplicationColumn;
import org.jeesl.interfaces.model.system.io.db.JeeslDbReplicationState;
import org.jeesl.interfaces.model.system.io.db.JeeslDbReplicationSync;
import org.jeesl.interfaces.model.system.io.db.JeeslDbStatementColumn;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoDbFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								SYSTEM extends JeeslIoSsiSystem,
								DUMP extends JeeslDbDump<SYSTEM,DF>,
								DF extends JeeslDbDumpFile<DUMP,DH,DS>,
								DH extends JeeslDbDumpHost<L,D,DH,?>,
								DS extends JeeslDbDumpStatus<L,D,DS,?>,
								
								CC extends JeeslDbConnectionColumn<L,D,CC,?>,
								
								SC extends JeeslDbStatementColumn<L,D,SC,?>,

								RC extends JeeslDbReplicationColumn<L,D,RC,?>,
								RS extends JeeslDbReplicationState<L,D,RS,?>,
								RY extends JeeslDbReplicationSync<L,D,RY,?>
>
			extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoDbFactoryBuilder.class);
	
	private final Class<DUMP> cDump; public Class<DUMP> getClassDump(){return cDump;}
	private final Class<DF> cFile; public Class<DF> getClassDumpFile(){return cFile;}
	private final Class<DH> cHost; public Class<DH> getClassDumpHost(){return cHost;}
	private final Class<DS> cStatus; public Class<DS> getClassDumpStatus(){return cStatus;}
	private final Class<CC> cConnectionColumn; public Class<CC> getClassConnectionColumn() {return cConnectionColumn;}
	private final Class<SC> cStatementColumn; public Class<SC> getClassStatementColumn() {return cStatementColumn;}
	private final Class<RC> cReplicationColumn; public Class<RC> getClassReplicationColumn() {return cReplicationColumn;}
	private final Class<RS> cReplicationState; public Class<RS> getClassReplicationState() {return cReplicationState;}
	private final Class<RY> cReplicationSync; public Class<RY> getClassReplicationSync() {return cReplicationSync;}
	
	public IoDbFactoryBuilder(final Class<L> cL, final Class<D> cD,
							final Class<DUMP> cDump, final Class<DF> cFile, final Class<DH> cHost, final Class<DS> cStatus,
							final Class<CC> cConnectionColumn,
							final Class<SC> cStatementColumn,
							final Class<RC> cReplicationColumn, final Class<RS> cReplicationState, final Class<RY> cReplicationSync)
	{
		super(cL,cD);
		
		this.cDump = cDump;
		this.cFile=cFile;
		this.cHost=cHost;
		this.cStatus=cStatus;
		this.cConnectionColumn=cConnectionColumn;
		this.cStatementColumn=cStatementColumn;
		this.cReplicationColumn=cReplicationColumn;
		this.cReplicationState=cReplicationState;
		this.cReplicationSync=cReplicationSync;
		
	}
	
	public EjbIoDumpFactory<SYSTEM,DUMP> dump(){return new EjbIoDumpFactory<>(cDump);}
	public EjbDbDumpFileFactory<DUMP,DF,DH,DS> dumpFile(){return new EjbDbDumpFileFactory<>(cFile);}
}