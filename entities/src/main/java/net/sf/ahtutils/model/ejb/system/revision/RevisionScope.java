package net.sf.ahtutils.model.ejb.system.revision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.HashCodeBuilder;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Table(name="RevisionScope", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
@EjbErNode(name="Scope",category="revision",subset="revision")
public class RevisionScope implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsRevisionScope<Lang,Description,RevisionCategory,RevisionView,RevisionViewMapping,RevisionScope,RevisionEntity,RevisionEntityMapping,RevisionAttribute,RevisionAttributeType>
{
	public static final long serialVersionUID=1;

	public static enum Code {login}
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String,Lang> name;
	@Override public Map<String,Lang> getName() {return name;}
	@Override public void setName(Map<String,Lang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String,Description> description;
	@Override public Map<String,Description> getDescription() {return description;}
	@Override public void setDescription(Map<String,Description> description) {this.description = description;}
	
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}

	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
	
	private String label;
	@Override public String getLabel() {return label;}
	@Override public void setLabel(String label) {this.label = label;}

	private String fqcn;
	@Override public String getFqcn() {return fqcn;}
	@Override public void setFqcn(String fqcn) {this.fqcn = fqcn;}
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="RevisionScope_Attribute",joinColumns={@JoinColumn(name="scope")},inverseJoinColumns={@JoinColumn(name="attribute")})
	private List<RevisionAttribute> attributes;
	@Override public List<RevisionAttribute> getAttributes() {if(attributes==null){attributes=new ArrayList<RevisionAttribute>();}return attributes;}
	@Override public void setAttributes(List<RevisionAttribute> attributes) {this.attributes = attributes;}
	
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof RevisionScope) ? id == ((RevisionScope) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,53).append(id).toHashCode();}
}