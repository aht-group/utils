package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoCmsFactoryBuilder;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.ejb.system.security.EjbSecurityMenuFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.security.doc.JeeslSecurityOnlineHelp;
import org.jeesl.interfaces.model.system.security.doc.JeeslSecurityOnlineTutorial;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityArea;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityContext;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.jsf.handler.PositionListReorderer;
import org.jeesl.jsf.helper.TreeHelper;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAdminSecurityMenuBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											CTX extends JeeslSecurityContext<L,D>,
											M extends JeeslSecurityMenu<V,CTX,M>,
											AR extends JeeslSecurityArea<L,D,V>,
											OT extends JeeslSecurityOnlineTutorial<L,D,V>,
											OH extends JeeslSecurityOnlineHelp<V,DC,DS>,
											DC extends JeeslIoCms<L,D,LOC,?,DS>,
											DS extends JeeslIoCmsSection<L,DS>,
											USER extends JeeslUser<R>>
		extends AbstractAdminSecurityBean<L,D,LOC,C,R,V,U,A,AT,CTX,M,AR,OT,OH,USER>
		implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityMenuBean.class);

	private final IoCmsFactoryBuilder<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fbCms;
	private final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,CTX,M,AR,OT,OH,DC,DS,USER> fbSecurity;

	protected JeeslIoCmsFacade<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fCms;

	private final EjbSecurityMenuFactory<V,CTX,M> efMenu;

	protected final SbSingleHandler<CTX> sbhContext; public SbSingleHandler<CTX> getSbhContext() {return sbhContext;}

	private TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

	private final List<OH> helps; public List<OH> getHelps() {return helps;}
	protected final List<DC> documents; public List<DC> getDocuments() {return documents;}

	private M menu; public M getMenu() {return menu;}
	private DC document;
	private OH help; public OH getHelp() {return help;} public void setHelp(OH help) {this.help = help;}

	public AbstractAdminSecurityMenuBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,CTX,M,AR,OT,OH,DC,DS,USER> fbSecurity, IoCmsFactoryBuilder<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fbCms)
	{
		super(fbSecurity);
		this.fbCms = fbCms;
		this.fbSecurity=fbSecurity;

		sbhContext = new SbSingleHandler<CTX>(fbSecurity.getClassContext(),this);

		efMenu = fbSecurity.ejbMenu();
		helps = new ArrayList<>();
		documents = new ArrayList<>();
	}

	public void postConstructMenu(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,CTX,M,USER> fSecurity,
	                              JeeslTranslationBean<L,D,LOC> bTranslation,
	                              JeeslFacesMessageBean bMessage,
	                              JeeslSecurityBean<L,D,C,R,V,U,A,AT,AR,CTX,M,USER> bSecurity,
	                              JeeslIoCmsFacade<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fCms)
	{
		super.postConstructSecurity(fSecurity,bTranslation,bMessage,bSecurity);
		opViews = fSecurity.all(fbSecurity.getClassView());
		this.fCms = fCms;

		sbhContext.setList(fSecurity.allOrderedPosition(fbSecurity.getClassContext()));
		sbhContext.setDefault();

		createMissingItems();
		reloadMenu();

		try {reloadDocuments();}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
	}

	@Override public void selectSbSingle(EjbWithId ejb)
	{
		createMissingItems();
		reloadMenu();
	}

	private void createMissingItems()
	{
		List<M> list = new ArrayList<>();
		if(sbhContext.isSelected())
		{
			list.addAll(fSecurity.allForParent(fbSecurity.getClassMenu(), JeeslSecurityMenu.Attributes.context,sbhContext.getSelection()));
			if(debugOnInfo) {logger.info(fbSecurity.getClassMenu().getSimpleName()+": "+list.size()+" in context "+sbhContext.getSelection().getCode());}
		}
		else
		{
			list.addAll(fSecurity.all(fbSecurity.getClassMenu()));
			if(debugOnInfo) {logger.info(fbSecurity.getClassMenu().getSimpleName()+": "+list.size());}
		}

		if(!list.isEmpty() || fSecurity.all(fbSecurity.getClassMenu(),1).isEmpty())
		{
			// We check if we have to create missing items if
			// - we have already items for this context or
			// - there are not items at all (initial start)
			Map<V,M> map = efMenu.toMapView(list);
			for(V v : opViews)
			{
//				if(debugOnInfo) {logger.info(v.toString()+" already available: "+map.containsKey(v));}
				if(!map.containsKey(v))
				{
					try
					{
						M m = efMenu.build(v);
						if(sbhContext.isSelected()) {m.setContext(sbhContext.getSelection());}
						fSecurity.save(m);
					}
					catch (JeeslConstraintViolationException e) {e.printStackTrace();}
					catch (JeeslLockingException e) {e.printStackTrace();}
				}
			}
		}
		else
		{
			// This happens if a new and empty context is selected, then we provide the "Import from other context" feature
		}
	}

	public void importFromDefaultContext() {
		try {
		if(sbhContext.isSelected()) {
			CTX defaultCtx = fSecurity.fByCode(fbSecurity.getClassContext(), "core");
			CTX currentCtx =sbhContext.getSelection();
			List<M> list = new ArrayList<>();
			list.addAll(fSecurity.allForParent(fbSecurity.getClassMenu(), JeeslSecurityMenu.Attributes.context,defaultCtx));
			Map<M,List<M>> map = efMenu.toMapChild(list);

			Map<M,M> defaultVsCurrentMap = new HashedMap();
			logger.info("copying menu items....");
			for (M m :  list) {
				M newMenu = efMenu.build();
				newMenu.setPosition(m.getPosition());
				newMenu.setView(m.getView());
				newMenu.setContext(currentCtx);
				newMenu = fSecurity.save(newMenu);
				//logger.info("copying ids from = " + m.getId() +" to " + newMenu.getId());
				defaultVsCurrentMap.put(m, newMenu);
			}
			logger.info("copying menu items....done");
			logger.info("Updating menu parents....");
			for (Map.Entry<M,List<M>> defautlMenuEntry : map.entrySet()) {
				M currentParentMenu= fSecurity.find(fbSecurity.getClassMenu(),defaultVsCurrentMap.get(defautlMenuEntry.getKey()));
				for (M m :  defautlMenuEntry.getValue()) {
					M currentMenu = fSecurity.find(fbSecurity.getClassMenu(), defaultVsCurrentMap.get(m));
					//logger.info("new MenuId:" + currentMenu.getId() + "parent :" + currentParentMenu.getId());
					currentMenu.setParent(currentParentMenu);
					fSecurity.update(currentMenu);
				}
			}
			logger.info("Updating menu parents....done");
			createMissingItems();
			reloadMenu();
		}
		} catch (JeeslNotFoundException | JeeslConstraintViolationException | JeeslLockingException e) {
			logger.info("import of menu item failed : " + e.getMessage());
		}
	}

	public boolean isMenuReloaded() {
		if(sbhContext.isSelected()) {
			if(fSecurity.allForParent(fbSecurity.getClassMenu(), JeeslSecurityMenu.Attributes.context,sbhContext.getSelection()).size() > 0) {
			return true;
			}
		}
		return false;
	}

	protected void reloadDocuments()  throws JeeslNotFoundException{};

	public void reloadMenu()
    {
		List<M> list = new ArrayList<>();
		if(sbhContext.isSelected()) {list.addAll(fSecurity.allForParent(fbSecurity.getClassMenu(), JeeslSecurityMenu.Attributes.context,sbhContext.getSelection()));}
		else {list.addAll(fSecurity.all(fbSecurity.getClassMenu()));}

		Map<M,List<M>> map = efMenu.toMapChild(list);
	    tree = new DefaultTreeNode(null, null);

	    buildTree(tree, efMenu.toListRoot(list),map);
    }

	private void buildTree(TreeNode parent, List<M> items, Map<M,List<M>> map)
	{
		for(M menu : items)
		{
			TreeNode n = new DefaultTreeNode(menu, parent);
			if(map.containsKey(menu))
			{
				buildTree(n, map.get(menu),map);
			}
		}
	}

	public void expandTree() {TreeHelper.setExpansion(this.node!=null ? this.node : this.tree, true);}
	public void collapseTree() {TreeHelper.setExpansion(this.tree,  false);}
	public boolean isExpanded() {return this.tree != null && this.tree.getChildren().stream().filter(node -> node.isExpanded()).count() > 1;}
	public void onNodeExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onNodeCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}

	@SuppressWarnings("unchecked")
	public void onDragDrop(TreeDragDropEvent event) throws JeeslConstraintViolationException, JeeslLockingException
	{
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
        if(debugOnInfo) {logger.info("Dragged " + dragNode.getData() + "Dropped on " + dropNode.getData() + " at " + dropIndex);}

        M parent = (M)dropNode.getData();
        int index=1;
        for(TreeNode n : dropNode.getChildren())
        {
        		M child =(M)n.getData();
        		child = fSecurity.find(fbSecurity.getClassMenu(),child);
        		child.setParent(parent);
        		child.setPosition(index);
        		fSecurity.save(child);
        		index++;
        }
        propagateChanges();
	}
	protected abstract void propagateChanges();

    @SuppressWarnings("unchecked")
	public void onNodeSelect(NodeSelectEvent event)
    {
		logger.info("Selected "+event.getTreeNode().toString());
		menu = (M)event.getTreeNode().getData();
		menu = fSecurity.find(fbSecurity.getClassMenu(),menu);

		reloadHelps();
    }

    private void reloadHelps()
    {
    	helps.clear();
		helps.addAll(fSecurity.allForParent(fbSecurity.getClassOnlineHelp(),menu.getView()));
    }

    // Handler Tree-Select

	private TreeNode helpTree; public TreeNode getHelpTree() {return helpTree;}
	private TreeNode helpNode; public TreeNode getHelpNode() {return helpNode;} public void setHelpNode(TreeNode helpNode) {this.helpNode = helpNode;}

    public void addHelp(DC doc)
    {
    	this.document=doc;
    	logger.info(document.toString());

    	DS root = this.fCms.load(document.getRoot(), true);

		this.helpTree = new DefaultTreeNode(root, null);
		buildTree(this.helpTree, root.getSections());
    }

    private void buildTree(TreeNode parent, List<DS> sections)
	{
		for(DS s : sections)
		{
			TreeNode n = new DefaultTreeNode(s, parent);
			if(!s.getSections().isEmpty()) {buildTree(n,s.getSections());}
		}
	}

	public void expandHelp(){TreeHelper.setExpansion(this.helpNode!=null ? this.helpNode : this.helpTree, true);}
	public void collapseHelp() {TreeHelper.setExpansion(this.helpTree,  false);}
	public boolean isHelpExpanded() {return this.helpTree != null && this.helpTree.getChildren().stream().filter(node -> node.isExpanded()).count() > 1;}

	public void onHelpNodeSelect(NodeSelectEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
	public void onHelpExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onHelpCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}

    public void selectHelp(){}
    
    public void reorderHelp() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fSecurity, helps);}
    
    public void removeHelp() throws JeeslConstraintViolationException
    {
    	fSecurity.rm(help);
    	reloadHelps();
    }
    
    // Handler Tree-Select

    @SuppressWarnings("unchecked")
	public void onHelpDrop(DragDropEvent ddEvent) throws JeeslConstraintViolationException, JeeslLockingException
    {
    	if(debugOnInfo) {logger.info("DRAG "+ddEvent.getDragId());}
    	if(debugOnInfo) {logger.info("DROP "+ddEvent.getDropId());}
		Object data = ddEvent.getData();
		if(debugOnInfo) {if(data==null) {logger.info("data = null");} else{logger.info("Data "+data.getClass().getSimpleName());}}

		TreeNode n = TreeHelper.getNode(helpTree,ddEvent.getDragId(),3);
		DS section = (DS)n.getData();
		logger.info(section.toString());

		help = fbSecurity.ejbHelp().build(menu.getView(),document,section,helps);
		help = fSecurity.save(help);
		reloadHelps();
    }
    
    
}