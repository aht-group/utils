package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.bb.EjbBbBoardFactory;
import org.jeesl.factory.ejb.module.bb.EjbBbPostFactory;
import org.jeesl.factory.ejb.module.bb.EjbBbThreadFactory;
import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbPost;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbThread;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BbFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								SCOPE extends JeeslStatus<SCOPE,L,D>,
								BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,USER>,
								PUB extends JeeslStatus<PUB,L,D>,
								THREAD extends JeeslBbThread<BB>,
								POST extends JeeslBbPost<THREAD,M,MT,USER>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
								USER extends EjbWithEmail>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(BbFactoryBuilder.class);
	
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<BB> cBb; public Class<BB> getClassBoard() {return cBb;}
	private final Class<PUB> cPublishing; public Class<PUB> getClassPublishing() {return cPublishing;}
	private final Class<THREAD> cThread; public Class<THREAD> getClassThread() {return cThread;}
	private final Class<POST> cPost; public Class<POST> getClassPost() {return cPost;}
	private final Class<M> cMarkup; public Class<M> getClassMarkup() {return cMarkup;}
	private final Class<MT> cMarkupType; public Class<MT> getClassMarkupType() {return cMarkupType;}

	public BbFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<SCOPE> cScope,
								final Class<BB> cBb,
								final Class<PUB> cPublishing,
								final Class<THREAD> cThread,
								final Class<POST> cPost,
								final Class<M> cMarkup,
								final Class<MT> cMarkupType)
	{       
		super(cL,cD);
		this.cScope=cScope;
		this.cBb=cBb;
		this.cPublishing=cPublishing;
		this.cThread=cThread;
		this.cPost=cPost;
		this.cMarkup=cMarkup;
		this.cMarkupType=cMarkupType;
	}

	public EjbBbBoardFactory<L,D,SCOPE,BB,PUB> bb(){return new EjbBbBoardFactory<>(cBb);}
	public EjbBbThreadFactory<BB,THREAD> ejbThread(){return new EjbBbThreadFactory<>(cThread);}
	public EjbBbPostFactory<THREAD,POST,M,MT,USER> ejbPost(){return new EjbBbPostFactory<>(cPost,cMarkup);}
}