package org.jeesl.web.mbean.prototype.module.aom;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.module.JeeslAssetCacheBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventStatus;
import org.jeesl.interfaces.model.module.aom.event.JeeslAomEventType;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractAssetOpBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,ATYPE>,
										STATUS extends JeeslAomStatus<L,D,STATUS,?>,
										ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
										EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE,ESTATUS>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>,
										ESTATUS extends JeeslAomEventStatus<L,D,ESTATUS,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetOpBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset;
	
	private final EjbAssetFactory<REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE> efAsset;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

    private final Set<ASSET> path;
	
	private REALM realm; public REALM getRealm() {return realm;}
	private RREF rref; public RREF getRref() {return rref;}

	private ASSET root;
    private ASSET asset; public ASSET getAsset() {return asset;} public void setAsset(ASSET asset) {this.asset = asset;}

	public AbstractAssetOpBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		
		efAsset = fbAsset.ejbAsset();
		
		path = new HashSet<>();
	}
	
	protected <E extends Enum<E>> void postConstructAsset(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,ATYPE,EVENT,ETYPE,ESTATUS> fAsset,
									JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,STATUS,ATYPE> bCache,
									E eRealm, RREF rref
									)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		
		realm = fAsset.fByEnum(fbAsset.getClassRealm(),eRealm);
		this.rref=rref;
		
		reloadTree();
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
		logger.info("Selected "+event.getTreeNode().toString());
		asset = (ASSET)event.getTreeNode().getData();
		
    }
}