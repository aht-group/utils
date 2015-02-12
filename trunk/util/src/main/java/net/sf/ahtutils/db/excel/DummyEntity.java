package net.sf.ahtutils.db.excel;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class DummyEntity implements EjbWithId {
		
		private String valueString;
		private Double valueDouble;
		private Date   valueDate;
		private String code;
		private long   id;

		public String getValueString() {return valueString;}
		public void setValueString(String value) {this.valueString = value;}
		
		public Double getValueDouble() {return valueDouble;}
		public void setValueDouble(Double valueDouble) {this.valueDouble = valueDouble;}
		
		public Date getValueDate() {return valueDate;}
		public void setValueDate(Date valueDate) {this.valueDate = valueDate;}
		
		public long getId() {return id;}
		public void setId(long id) {this.id = id;}
	
	}