package net.sf.ahtutils.controller.util;

import java.util.ArrayList;
import java.util.List;

public class Ranking
{
	public Ranking()
	{
		
	}
	
	public List<Integer> rank(List<Integer> listPoints)
	{
		List <Integer> tempList;
		List <Rank> rankList= null;
		int zaehler = 0, zaehler1 = 0;
		
		tempList=listPoints;
		
		while (listPoints.get(zaehler)!=null)
		{
			
			rankList.set(zaehler, new Rank(zaehler, listPoints.get(zaehler)));
			zaehler++;
		}
		
		while (listPoints.get(zaehler1)!=null){
			if (rankList.get(zaehler1).compare(rankList.get(zaehler1), rankList.get(zaehler1+1))>0)
			{
				rankList.set(zaehler1, new Rank(zaehler1, listPoints.get(zaehler1)));
				listPoints.set(zaehler1, rankList.get(zaehler1).getPoints());
				zaehler1++;
				
			}
			else 
			{	
				rankList.set(zaehler1+1, new Rank(zaehler1+1, listPoints.get(zaehler1+1)));
				listPoints.set(zaehler1+1, rankList.get(zaehler1+1).getPoints());
				zaehler1++;
			}
			
		}
		
		
		
		return listPoints;
	}
}
