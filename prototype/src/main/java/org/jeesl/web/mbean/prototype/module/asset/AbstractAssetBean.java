package org.jeesl.web.mbean.prototype.module.asset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.asset.JeeslAssetType;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractAssetBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										REALM extends JeeslAssetRealm<L,D,REALM,?>, RREF extends EjbWithId,
										ASSET extends JeeslAsset<REALM,ASSET,STATUS,TYPE>,
										MANU extends JeeslAssetManufacturer<REALM>,
										STATUS extends JeeslAssetStatus<L,D,STATUS,?>,
										TYPE extends JeeslAssetType<L,D,REALM,TYPE,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,ASSET,MANU,STATUS,TYPE> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,ASSET,MANU,STATUS,TYPE> fbAsset;
	
	private final EjbAssetFactory<REALM,ASSET,STATUS,TYPE> efAsset;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

    private final List<STATUS> status; public List<STATUS> getStatus() {return status;}
	
	private REALM realm;
    private RREF realmReference;
    private ASSET root;
    private TYPE type;
    private ASSET asset; public ASSET getAsset() {return asset;} public void setAsset(ASSET asset) {this.asset = asset;}

	public AbstractAssetBean(AssetFactoryBuilder<L,D,REALM,ASSET,MANU,STATUS,TYPE> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
		
		efAsset = fbAsset.ejbAsset();
		
		status = new ArrayList<>();
	}
	
	protected <E extends Enum<E>> void postConstructAsset(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslAssetFacade<L,D,REALM,ASSET,MANU,STATUS,TYPE> fAsset,
									E eRealm, RREF realmReference
									)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		
		realm = fAsset.fByEnum(fbAsset.getClassRealm(),eRealm);
		this.realmReference=realmReference;
		
		status.addAll(fAsset.allOrderedPositionVisible(fbAsset.getClassStatus()));
		type = fAsset.fcAssetRootType(realm, realmReference);
		
		reloadTree();
	}
	
	private void reloadTree()
	{
		root = fAsset.fcAssetRoot(realm, realmReference);
		
		tree = new DefaultTreeNode(root, null);
		buildTree(tree,fAsset.allForParent(fbAsset.getClassAsset(), root));
	}
	
	private void buildTree(TreeNode parent, List<ASSET> assets)
	{
		for(ASSET a : assets)
		{
			TreeNode n = new DefaultTreeNode(a,parent);
			List<ASSET> childs = fAsset.allForParent(fbAsset.getClassAsset(),a);
			if(!childs.isEmpty()){buildTree(n,childs);}
		}
	}
	
	public void addAsset()
	{
		ASSET parent = null; if(asset!=null) {parent = asset;} else {parent = root;}
		STATUS status = fAsset.fByEnum(fbAsset.getClassStatus(),JeeslAssetStatus.Code.na);
		TYPE type = fAsset.fcAssetRootType(realm,realmReference);
		asset = efAsset.build(realm,realmReference, parent, status,type);
	}
	
	public void saveAsset() throws JeeslConstraintViolationException, JeeslLockingException
	{
		efAsset.converter(asset);
		asset = fAsset.save(asset);
		reloadTree();
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