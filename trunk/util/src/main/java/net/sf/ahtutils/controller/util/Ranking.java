package net.sf.ahtutils.controller.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ranking
{
	public Ranking()
	{
		
	}
	
	public List<Integer> rank(List<Integer> listPoints)
	{
		List <Rank> rankList = new ArrayList<Rank>();
		int[] tmpresult = new int[listPoints.size()]; 
		List <Integer> result = new ArrayList<Integer>();
		
		int zaehler = 0, zaehler1 = 0;
		
		for (Integer i: listPoints)
		{
			rankList.add(new Rank(zaehler, listPoints.get(zaehler)));
			zaehler++;
		}
		
		Collections.sort(rankList, new Rank());
		
		for (Rank j: rankList){  //bis hier korrekt
			
			tmpresult[zaehler1] = rankList.get(zaehler1).getPoints();
			
			result.add(tmpresult[zaehler1]);
			zaehler1++;
		}
		
		
		
		return result;
	}
}
