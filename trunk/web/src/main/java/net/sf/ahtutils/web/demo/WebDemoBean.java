package net.sf.ahtutils.web.demo;

import java.io.FileNotFoundException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import net.sf.ahtutils.jsf.menu.DummyMenuFactory;
import net.sf.ahtutils.xml.navigation.Menu;

@ManagedBean
public class WebDemoBean {
	
	private Menu menu;
	
	@PostConstruct
	public void init() throws FileNotFoundException
	{
		menu = DummyMenuFactory.create();
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
