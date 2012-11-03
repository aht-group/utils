package net.sf.ahtutils.jsf.components;

import java.util.ArrayList;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;

@FacesComponent(value="net.sf.ahtutils.jsf.components.MenuListMultiLevel")
public class MenuListMultiLevel extends UINamingContainer
{
	
	private ArrayList<MenuItem> subMenus;
	private Integer noOfSubmenus;

	public void init()
	{
		//Initialize the submenu list
		subMenus=new ArrayList<MenuItem>();
		noOfSubmenus = 0;
		//Get the menu object given from the JSF components attribute "value"
		Menu menu = (Menu) getAttributes().get("value");
		
		//For all root-level menu items, let the menu items be processed by the recursive logic below
        for (MenuItem item : menu.getMenuItem())
        {
        	for (MenuItem submenuItem : item.getMenuItem())
        	{
        		processMenu(submenuItem);
        	}
        }
	}
	
	public void processMenu(MenuItem menu)
	{
		for (int i=0; i < menu.getMenuItem().size(); i++)
        {
			//Because the first element is the one that holds the name of the submenu, add this to the submenu list
			if (i==0)
			{
				System.out.println("Adding menu item " +menu.getName() +" to list of submenus and increment the number of submenus variable.");
				subMenus.add(menu);
				noOfSubmenus++;
			}
			//Recursively continue
			processMenu(menu.getMenuItem().get(i));
        }
	}
	
	public ArrayList<MenuItem> getSubMenus() {
		init();
		return subMenus;
	}
	
	public void setSubMenus(ArrayList<MenuItem> subMenus) {
		this.subMenus = subMenus;
	}

	public Integer getNoOfSubmenus() {
		init();
		return noOfSubmenus;
	}

	public void setNoOfSubmenus(Integer noOfSubmenus) {
		this.noOfSubmenus = noOfSubmenus;
	}
}