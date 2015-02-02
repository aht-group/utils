package net.sf.ahtutils.web.rest;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurvey;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyAnswer;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyCorrelation;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyData;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyOption;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyQuestion;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveySection;
import net.sf.ahtutils.interfaces.model.survey.UtilsSurveyTemplate;
import net.sf.ahtutils.interfaces.rest.survey.UtilsSurveyRestExport;
import net.sf.ahtutils.interfaces.rest.survey.UtilsSurveyRestImport;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyRestService <L extends UtilsLang,
							D extends UtilsDescription,
							SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							SS extends UtilsStatus<SS,L,D>,
							TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
							OT extends UtilsStatus<OT,L,D>,
							CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>>
					implements UtilsSurveyRestExport,UtilsSurveyRestImport
{
	final static Logger logger = LoggerFactory.getLogger(SurveyRestService.class);
	
	private UtilsSecurityFacade fSurvey;
	
	private final Class<L> cL;
	private final Class<D> cD;
	
	private final Class<SS> cSS;
	private final Class<TS> cTS;
	private final Class<TC> cTC;
	private final Class<OT> cOT;
	
	private XmlStatusFactory fStatus;
	
	private SurveyRestService(UtilsSecurityFacade fSurvey,final Class<L> cL,final Class<D> cD,final Class<SS> cSS,final Class<TS> cTS,final Class<TC> cTC,final Class<OT> cOT)
	{
		this.fSurvey=fSurvey;
		this.cL=cL;
		this.cD=cD;
		this.cSS=cSS;
		this.cTS=cTS;
		this.cTC=cTC;
		this.cOT=cOT;
	
		fStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SURVEY extends UtilsSurvey<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
					SS extends UtilsStatus<SS,L,D>,
					TEMPLATE extends UtilsSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
					TS extends UtilsStatus<TS,L,D>,
					TC extends UtilsStatus<TC,L,D>,
					SECTION extends UtilsSurveySection<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
					QUESTION extends UtilsSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends UtilsSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
					DATA extends UtilsSurveyData<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
					OPTION extends UtilsSurveyOption<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>,
					OT extends UtilsStatus<OT,L,D>,
					CORRELATION extends UtilsSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>>
		SurveyRestService<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>
			factory(UtilsSecurityFacade fSurvey,final Class<L> cL,final Class<D> cD,final Class<SS> cSS,final Class<TS> cTS,final Class<TC> cTC,final Class<OT> cOT)
	{
		return new SurveyRestService<L,D,SURVEY,SS,TEMPLATE,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,OT,CORRELATION>(fSurvey,cL,cD,cSS,cTS,cTC,cOT);
	}

	@Override public Aht exportSurveyTemplateCategory()
	{
		Aht aht = new Aht();
		for(TC ejb : fSurvey.allOrderedPosition(cTC)){aht.getStatus().add(fStatus.build(ejb));}
		return aht;
	}

	@Override public Aht exportSurveyTemplateStatus()
	{
		Aht aht = new Aht();
		for(TS ejb : fSurvey.allOrderedPosition(cTS)){aht.getStatus().add(fStatus.build(ejb));}
		return aht;
	}

	@Override public Aht exportSurveyOptionTypes()
	{
		Aht aht = new Aht();
		for(OT ejb : fSurvey.allOrderedPosition(cOT)){aht.getStatus().add(fStatus.build(ejb));}
		return aht;
	}

	@Override public Aht exportSurveyStatus()
	{
		Aht aht = new Aht();
		for(SS ejb : fSurvey.allOrderedPosition(cSS)){aht.getStatus().add(fStatus.build(ejb));}
		return aht;
	}

	@Override public DataUpdate importSurveyTemplateCategory(Aht categories){return importStatus(cTC,cL,cD,categories,null);}
	@Override public DataUpdate importSurveyTemplateStatus(Aht categories){return importStatus(cTS,cL,cD,categories,null);}
	@Override public DataUpdate importSurveyOptionType(Aht categories){return importStatus(cOT,cL,cD,categories,null);}
	@Override public DataUpdate importSurveyStatus(Aht categories){return importStatus(cSS,cL,cD,categories,null);}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Aht container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fSurvey);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }

	
}