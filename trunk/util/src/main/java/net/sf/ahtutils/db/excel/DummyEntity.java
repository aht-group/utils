package net.sf.ahtutils.db.excel;

import java.util.Date;

public class DummyEntity {
		
		private String valueString;
		private Double valueDouble;
		private Date   valueDate;

		public String getValueString() {return valueString;}
		public void setValueString(String value) {this.valueString = value;}
		
		public Double getValueDouble() {return valueDouble;}
		public void setValueDouble(Double valueDouble) {this.valueDouble = valueDouble;}
		
		public Date getValueDate() {return valueDate;}
		public void setValueDate(Date valueDate) {this.valueDate = valueDate;}
	}