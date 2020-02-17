package org.jeesl.web.mbean.prototype.module.aom;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.module.JeeslAssetCacheBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.controller.handler.NullNumberBinder;
import org.jeesl.controller.handler.th.ThMultiFilterHandler;
import org.jeesl.controller.handler.ui.helper.UiHelperAsset;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetEventFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.interfaces.bean.th.ThMultiFilter;
import org.jeesl.interfaces.bean.th.ThMultiFilterBean;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.model.module.aom.AssetEventLazyModel;
import org.jeesl.util.comparator.ejb.module.asset.EjbAssetComparator;
import org.jeesl.util.comparator.ejb.module.asset.EjbEventComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAssetBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
										ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
										ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
										EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS,USER>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
										ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>,
										USER extends JeeslSimpleUser>
					extends AbstractAdminBean<L,D>
					implements Serializable,ThMultiFilterBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fAsset;
	private JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ETYPE> bCache;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fbAsset;
	
	private final EjbAssetFactory<REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE> efAsset;
	private final EjbAssetEventFactory<COMPANY,ASSET,EVENT,ETYPE,ESTATUS,USER> efEvent;
	
	private final Comparator<ASSET> cpAsset;
	
	private final UiHelperAsset<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> uiHelper; public UiHelperAsset<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> getUiHelper() {return uiHelper;}
	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
    private final NullNumberBinder nnb; public NullNumberBinder getNnb() {return nnb;}
    private final ThMultiFilterHandler<ETYPE> thfEventType; public ThMultiFilterHandler<ETYPE> getThfEventType() {return thfEventType;}
    private final AssetEventLazyModel<ASSET,EVENT,ETYPE,ESTATUS,USER> lazyEvents; public AssetEventLazyModel<ASSET,EVENT,ETYPE,ESTATUS,USER> getLazyEvents() {return lazyEvents;}
    
	private final Set<ASSET> path;
    
	protected REALM realm; public REALM getRealm() {return realm;}
	protected RREF rref; public RREF getRref() {return rref;}

	private ASSET root;
    private ASSET asset; public ASSET getAsset() {return asset;} public void setAsset(ASSET asset) {this.asset = asset;}
    private EVENT event; public EVENT getEvent() {return event;} public void setEvent(EVENT event) {this.event = event;}

	public AbstractAssetBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		
		uiHelper = new UiHelperAsset<>();
		nnb = new NullNumberBinder();
		
		thfEventType = new ThMultiFilterHandler<>(this);
		lazyEvents = new AssetEventLazyModel<>(fbAsset.cpEvent(EjbEventComparator.Type.recordDesc),thfEventType);
		
		efAsset = fbAsset.ejbAsset();
		efEvent = fbAsset.ejbEvent();
		
		cpAsset = fbAsset.cpAsset(EjbAssetComparator.Type.position);
		
		path = new HashSet<>();
	}
	
	protected void postConstructAsset(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
						JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,EVENT,ETYPE,ESTATUS,USER> fAsset,
						JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE,ETYPE> bCache,
						REALM realm)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		this.bCache=bCache;
		uiHelper.setCacheBean(bCache);
		
		this.realm=realm;
		
		thfEventType.getList().addAll(bCache.getEventType());
		thfEventType.selectAll();
//		thFilter.preSelect(MeisNsdsCandidateStatus.class,MeisNsdsCandidateStatus.Code.pending);
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
		reloadTree();
	}
	
	@Override public void filtered(ThMultiFilter filter) throws JeeslLockingException, JeeslConstraintViolationException
	{
		logger.info("TH Filter");
	}
	
	private void reset(boolean rAsset, boolean rEvents, boolean rEvent)
	{
		if(rAsset) {asset=null;}
		if(rEvents) {lazyEvents.clear();}
		if(rEvent) {event=null;}
	}
	
	private void reloadTree()
	{
		root = fAsset.fcAssetRoot(realm,rref);
		
		tree = new DefaultTreeNode(root, null);
		buildTree(tree,fAsset.allForParent(fbAsset.getClassAsset(), root));
	}
	
	private void buildTree(TreeNode parent, List<ASSET> assets)
	{
		for(ASSET a : assets)
		{
			TreeNode n = new DefaultTreeNode(a,parent);
			n.setExpanded(path.contains(a));
			List<ASSET> childs = fAsset.allForParent(fbAsset.getClassAsset(),a);
			if(!childs.isEmpty()){buildTree(n,childs);}
		}
	}
	
	public void addAsset()
	{
		ASSET parent = null; if(asset!=null) {parent = asset;} else {parent = root;}
		ASTATUS status = fAsset.fByEnum(fbAsset.getClassStatus(),JeeslAomStatus.Code.na);
		ATYPE type = fAsset.fcAssetRootType(realm,rref);
		reset(true,true,true);
		asset = efAsset.build(realm,rref,parent,status,type);
	}
	
	public void saveAsset() throws JeeslConstraintViolationException, JeeslLockingException
	{
		efAsset.converter(fAsset,asset);
		asset = fAsset.save(asset);
		path.clear();addPath(asset);
		
		reloadTree();
	}
	
	private void addPath(ASSET asset)
	{
		path.add(asset);
		if(asset.getParent()!=null) {addPath(asset.getParent());}
	}
	
	public void onNodeExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onNodeCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}
	
	@SuppressWarnings("unchecked")
	public void onDragDrop(TreeDragDropEvent event) throws JeeslConstraintViolationException, JeeslLockingException
	{
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
        logger.info("Dragged " + dragNode.getData() + " Dropped on " + dropNode.getData() + " at " + dropIndex);
        
        ASSET parent = (ASSET)dropNode.getData();
        int index=1;
        for(TreeNode n : dropNode.getChildren())
        {
    		ASSET child =(ASSET)n.getData();
    		child.setParent(parent);
    		child.setPosition(index);
    		fAsset.save(child);
    		index++;
        }  
    }

    @SuppressWarnings("unchecked")
	public void onNodeSelect(NodeSelectEvent event)
    {
    	reset(true,true,true);
		logger.info("Selected "+event.getTreeNode().toString());
		asset = (ASSET)event.getTreeNode().getData();
		reloadEvents();
    }
    
	private void reloadEvents()
	{
		lazyEvents.setScope(fAsset,asset);
	}
    
    public void addEvent()
    {
    	logger.info(AbstractLogMessage.addEntity(fbAsset.getClassEvent()));
    	event = efEvent.build(asset,bCache.getEventType().get(0));
    	efEvent.ejb2nnb(event,nnb);
    	uiHelper.update(realm,rref,event);
    }
    
    public void selectEvent()
    {
    	logger.info(AbstractLogMessage.selectEntity(event));
    	event = fAsset.find(fbAsset.getClassEvent(),event);
    	efEvent.ejb2nnb(event,nnb);
    	Collections.sort(event.getAssets(),cpAsset);
    	uiHelper.update(realm,rref,event);
    }
    
    public void saveEvent() throws JeeslConstraintViolationException, JeeslLockingException
    {
    	logger.info(AbstractLogMessage.saveEntity(event));
    	efEvent.converter(fAsset,event);
    	efEvent.nnb2ejb(event,nnb);
    	event = fAsset.save(event);
    	reloadEvents();
    	uiHelper.update(realm,rref,event);
    }
    
    public void removeEvent() throws JeeslConstraintViolationException, JeeslLockingException
    {
    	event.getAssets().remove(asset);
    	fAsset.save(event);
    	reset(false,true,true);
    	reloadEvents();
    }
    
    public void onDrop(DragDropEvent ddEvent) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
    	logger.info("DRAG "+ddEvent.getDragId());
		logger.info("DROP "+ddEvent.getDropId());
		Object o = ddEvent.getData();
		if(o==null)
		{
			logger.info("data = null");
		}
		else
		{
			logger.info("Data "+o.getClass().getSimpleName());
		}
		TreeNode n = getNode(ddEvent.getDragId(),3);
		ASSET a = (ASSET)n.getData();
		logger.info(a.toString());
		
		if(!event.getAssets().contains(a))
		{
			event.getAssets().add(a);
			event = fAsset.save(event);
		}
	}
    
    private TreeNode getNode(String dragId, int position)
    {
    	String[] elements = dragId.split(":");
    	String[] index = elements[position].split("_");
    	return getNode(tree.getChildren(),index,0);
    }
    
    private TreeNode getNode(List<TreeNode> nodes, String[] index, int level)
    {
    	Integer position = Integer.valueOf(index[level]);
    	TreeNode n = nodes.get(position);
    	if(index.length==(level+1)){return n;}
    	else {return getNode(n.getChildren(),index,level+1);}
    }
}