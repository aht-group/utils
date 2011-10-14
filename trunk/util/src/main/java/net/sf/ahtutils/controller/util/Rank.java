package net.sf.ahtutils.controller.util;

import java.util.Comparator;

public class Rank implements Comparator<Rank>
{
	public int index, points;

	public	Rank ()
	{
		
	}
	public	Rank (int index, int points)
	{
		this.index = index;
		this.points = points;
	}

	public int getIndex() {return index;}
	public void setIndex(int index) {this.index = index;}

	public int getPoints() {return points;}
	public void setPoints(int points) {this.points = points;}

	@Override
	public int compare(Rank a, Rank b)
	{
		// TODO Auto-generated method stub
		
		
		return(new Integer(a.getPoints())).compareTo(b.getPoints())*-1;
		
		/*if (a.getPoints()<b.getPoints())
		{
			return 1;
		}
		else
		if (a.getPoints()>b.getPoints())
		{
			return -1;
		}
		
		return 0;*/
	}	

}


