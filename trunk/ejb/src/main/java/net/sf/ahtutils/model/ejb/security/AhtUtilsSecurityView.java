package net.sf.ahtutils.model.ejb.security;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.ejb.user.AhtUtilsUser;
import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@EjbErNode(name="SecurityView")

public class AhtUtilsSecurityView implements EjbWithCode,Serializable,EjbRemoveable,EjbPersistable,
		UtilsSecurityView<AhtUtilsLang,AhtUtilsDescription,AhtUtilsSecurityCategory,AhtUtilsSecurityRole,AhtUtilsSecurityView,AhtUtilsSecurityUsecase,AhtUtilsSecurityAction,AhtUtilsUser>
{
	public static enum Code {welcome}
	public static enum CodeSeries {seriesAll,series,season,episode}
	public static enum CodeTag {client,info}
	
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull @ManyToOne
	private AhtUtilsSecurityCategory category;
	
	@NotNull
	private String code;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsLang> name;
	public Map<String, AhtUtilsLang> getName() {return name;}
	public void setName(Map<String, AhtUtilsLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsDescription> description;
	public Map<String, AhtUtilsDescription> getDescription() {return description;}
	public void setDescription(Map<String, AhtUtilsDescription> description) {this.description = description;}
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="view")
	private List<AhtUtilsSecurityAction> actions;
	
	//******************************************************************************
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public AhtUtilsSecurityCategory getCategory() {return category;}
	public void setCategory(AhtUtilsSecurityCategory category) {this.category = category;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public List<AhtUtilsSecurityAction> getActions() {if(actions==null){actions=new ArrayList<AhtUtilsSecurityAction>();}return actions;}
	public void setActions(List<AhtUtilsSecurityAction> actions) {this.actions = actions;}
	
	//******************************************************************************
	
	public boolean equals(Object object)
	{
        return (object instanceof AhtUtilsSecurityView)
             ? id == ((AhtUtilsSecurityView) object).getId()
             : (object == this);
    }
}