package org.jeesl.web.mbean.prototype.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoCmsFactoryBuilder;
import org.jeesl.factory.builder.system.SecurityFactoryBuilder;
import org.jeesl.factory.ejb.system.security.EjbSecurityMenuFactory;
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
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.jsf.helper.TreeHelper;
import org.jeesl.model.xml.system.navigation.Menu;
import org.jeesl.model.xml.system.navigation.MenuItem;
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
											M extends JeeslSecurityMenu<V,M>,
											AR extends JeeslSecurityArea<L,D,V>,
											OT extends JeeslSecurityOnlineTutorial<L,D,V>,
											OH extends JeeslSecurityOnlineHelp<V,DC,DS>,
											DC extends JeeslIoCms<L,D,LOC,?,DS>,
											DS extends JeeslIoCmsSection<L,DS>,
											USER extends JeeslUser<R>>
		extends AbstractAdminSecurityBean<L,D,LOC,C,R,V,U,A,AT,M,AR,OT,OH,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityMenuBean.class);
	
	private final IoCmsFactoryBuilder<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fbCms;
	private final SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,AR,OT,OH,DC,DS,USER> fbSecurity;
	
	protected JeeslIoCmsFacade<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fCms;
	
	private final EjbSecurityMenuFactory<V,M> efMenu;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	
	private final List<OH> helps; public List<OH> getHelps() {return helps;}
	protected final List<DC> documents; public List<DC> getDocuments() {return documents;}
	
	private M menu; public M getMenu() {return menu;}
	private DC document;
	private OH help; public OH getHelp() {return help;} public void setHelp(OH help) {this.help = help;}

	public AbstractAdminSecurityMenuBean(SecurityFactoryBuilder<L,D,C,R,V,U,A,AT,M,AR,OT,OH,DC,DS,USER> fbSecurity, IoCmsFactoryBuilder<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fbCms)
	{
		super(fbSecurity);
		this.fbCms = fbCms;
		this.fbSecurity=fbSecurity;
		efMenu = fbSecurity.ejbMenu();
		helps = new ArrayList<>();
		documents = new ArrayList<>();
	}
	
	public void postConstructMenu(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity,
	                              JeeslTranslationBean<L,D,LOC> bTranslation,
	                              JeeslFacesMessageBean bMessage,
	                              JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity,
	                              JeeslIoCmsFacade<L,D,LOC,?,DC,?,DS,?,?,?,?,?,?,?> fCms)
	{
		super.postConstructSecurity(fSecurity,bTranslation,bMessage,bSecurity);
		opViews = fSecurity.all(fbSecurity.getClassView());
		this.fCms = fCms;
		
		if(fSecurity.all(fbSecurity.getClassMenu(),1).isEmpty()) {firstInit();}
		Map<V,M> map = efMenu.toMapView(fSecurity.all(fbSecurity.getClassMenu()));
		
		for(V v : opViews)
		{
			if(!map.containsKey(v))
			{
				try
				{
					M m = efMenu.create(v);
					fSecurity.save(m);
				}
				catch (JeeslConstraintViolationException e) {e.printStackTrace();}
				catch (JeeslLockingException e) {e.printStackTrace();}
			}
		}
		reload();
		try {reloadDocuments();}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
	}
	
	protected void reloadDocuments()  throws JeeslNotFoundException{};
	
	protected void firstInit() {}
	protected void firstInit(Menu xml)
	{
		firstInit(null,xml.getMenuItem());
	}
	
	private void firstInit(M parent, List<MenuItem> list)
	{
		int i = 1;
		for(MenuItem item : list)
		{
			try
			{
				V v = fSecurity.fByCode(fbSecurity.getClassView(),item.getView().getCode());
				M m = efMenu.create(v);
				m.setPosition(i);i++;
				m.setParent(parent);
				m = fSecurity.save(m);
				if(!item.getMenuItem().isEmpty()) {firstInit(m,item.getMenuItem());}
			}
			catch (JeeslNotFoundException e) {logger.error(e.getMessage());}
			catch (JeeslConstraintViolationException e) {logger.error("Duplicate for "+item.getCode());e.printStackTrace();}
			catch (JeeslLockingException e) {e.printStackTrace();}
		}
	}
	
	public void reload()
    {
		List<M> list = fSecurity.all(fbSecurity.getClassMenu());
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