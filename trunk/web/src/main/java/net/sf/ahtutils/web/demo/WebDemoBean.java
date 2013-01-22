package net.sf.ahtutils.web.demo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import net.sf.ahtutils.jsf.menu.DummyMenuFactory;
import net.sf.ahtutils.xml.navigation.Menu;

@Named
public class WebDemoBean {
	
	private Menu menu;
	private String demoString;
	private List<DemoDataType> menuItems;
	private Date date1;
	private Date date2;
	private Locale locale;
	
	@PostConstruct
	public void init() throws FileNotFoundException
	{
		menu = DummyMenuFactory.create();
		menuItems = new ArrayList<DemoDataType>();
		menuItems.add(new DemoDataType("test"));
		menuItems.add(new DemoDataType("test2"));
		
		date1 = new Date();
		date2 = new Date();
		
		locale = new Locale("de");
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getDemoString() {
		return demoString;
	}

	public void setDemoString(String demoString) {
		this.demoString = demoString;
	}

	public List<DemoDataType> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<DemoDataType> menuItems) {
		this.menuItems = menuItems;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
