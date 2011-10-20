package net.sf.ahtutils.controller.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.primitives.Ints;

public class Ranking
{
	public final Integer[] iA = {};
	
	public Ranking()
	{
		
	}
	
	public int[] rank(int[] points)
	{
		List<Integer> tmp = rank(Ints.asList(points));
		int[] result = new int[tmp.size()];
		for(int i=0;i<tmp.size();i++){result[i]=tmp.get(i);}
		return result;
	}
	public List<Integer> rank(List<Integer> listPoints)
	{
		List <Rank> rankList = new ArrayList<Rank>();
		int[] tmpresult = new int[listPoints.size()]; 
		List <Integer> result = new ArrayList<Integer>();
		
		int zaehler = 0, zaehler1 = 0;
		
		for (Integer i: listPoints)
		{
			rankList.add(new Rank(zaehler, i));
			zaehler++;
		}
		
		Collections.sort(rankList, new Rank());
		
		for (Rank j: rankList)
		{  //bis hier korrekt
			
			tmpresult[zaehler1] = rankList.get(zaehler1).getIndex()+1;
			
			result.add(tmpresult[zaehler1]);
			zaehler1++;
		}	
		
		return result;
	}
	
	public class Rank implements Comparator<Rank>
	{
		public int index, points;

		public	Rank () {}
		public	Rank (int index, int points)
		{
			this.index = index;
			this.points = points;
		}

		private int getIndex() {return index;}
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
}
