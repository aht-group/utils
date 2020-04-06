package org.jeesl.web.mbean.prototype.module.its;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslItsFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.ItsFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.jsf.helper.TreeHelper;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractItsIssueBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
    										REALM extends JeeslMcsRealm<L,D,REALM,?>, RREF extends EjbWithId,
    										ISSUE extends JeeslItsIssue<REALM,ISSUE>,
    										STATUS extends JeeslItsIssueStatus<L,D,REALM,STATUS,?>>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable, SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractItsIssueBean.class);
	
	protected JeeslItsFacade<L,D,REALM,ISSUE> fIssue;

	protected final ItsFactoryBuilder<L,D,REALM,ISSUE,STATUS> fbIssue;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

    protected REALM realm;
    protected RREF rref;
    protected ISSUE root;
    protected ISSUE issue;  public ISSUE getIssue() {return issue;} public void setType(ISSUE issue) {this.issue = issue;}

	public AbstractItsIssueBean(ItsFactoryBuilder<L,D,REALM,ISSUE,STATUS> fbIssue/*, SvgFactoryBuilder<L,D,G,GT,F,FS> fbSvg*/)
	{
		super(fbIssue.getClassL(),fbIssue.getClassD());	
		this.fbIssue=fbIssue;
	}

	protected void postConstructIssue(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslItsFacade<L,D,REALM,ISSUE> fIssue,
									REALM realm)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fIssue=fIssue;
		this.realm=realm;
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
		reloadTree();
	}
	
	@Override public void selectSbSingle(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException
	{
		reloadTree();
	}
	
	@SuppressWarnings("unchecked")
	protected void reloadTree()
	{
		List<Long> expandedNodes = TreeHelper.findNodes(this.tree, node -> node.isExpanded()).stream().map(node -> (ISSUE)node.getData()).filter(data -> data != null).map(data -> data.getId()).collect(Collectors.toList());
		
		root = fIssue.fcAItsRoot(realm,rref);
		
		tree = new DefaultTreeNode();
		this.issue = null;
		TreeHelper.buildTree(this.fIssue, this.tree, this.fIssue.allForParent(this.fbIssue.getClassIssue(), this.root), this.fbIssue.getClassIssue());
		
		TreeHelper.findNodes(this.tree, node -> node.getData() != null && expandedNodes.contains(((ISSUE)node.getData()).getId())).forEach(node -> node.setExpanded(true));
	}
	
	private void reset(boolean rIssue)
	{
		if(rIssue) {issue=null;}
	}
	
	public void addIssue()
	{
		ISSUE parent=null; if(issue!=null) {parent = issue;} else {parent = root;}
		issue = fbIssue.ejbIssue().build(realm,rref,parent);
	}
	
	public void cancelIssue()
	{
		if (this.node != null)
		{
			this.node.setSelected(false);
		}
		this.issue = null;
	}
	
	public void saveIssue() throws JeeslConstraintViolationException, JeeslLockingException
	{
		issue = fIssue.save(issue);
		reloadTree();
	}
	
	public void deleteIssue() throws JeeslLockingException
	{
		try
		{
			fIssue.rm(issue);
			reloadTree();
			reset(true);
		}
		catch (JeeslConstraintViolationException e) {bMessage.errorConstraintViolationInUse();}
	}
	
	public void onNodeExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onNodeCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}
	
	@SuppressWarnings("unchecked")
	public void onDragDrop(TreeDragDropEvent event) throws JeeslConstraintViolationException, JeeslLockingException
	{
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
        logger.info("Dragged " + dragNode.getData() + "Dropped on " + dropNode.getData() + " at " + dropIndex);

        ISSUE parent = (ISSUE)dropNode.getData();
        int index=1;
        for(TreeNode n : dropNode.getChildren())
        {
        	ISSUE child =(ISSUE)n.getData();
    		child.setParent(parent);
    		child.setPosition(index);
    		fIssue.save(child);
    		index++;
        }
    }

    @SuppressWarnings("unchecked")
	public void onNodeSelect(NodeSelectEvent event)
    {
		logger.info("Selected "+event.getTreeNode().toString());
		issue = (ISSUE)event.getTreeNode().getData();
    }
}