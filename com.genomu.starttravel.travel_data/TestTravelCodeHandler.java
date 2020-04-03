package com.genomu.starttravel.travel_data;

import java.util.ArrayList;
import java.util.List;

public class TestTravelCodeHandler {
	
	public static void main(String[] args) {
		setRawList();
		TravelCodeFilter tcf = new TravelCodeFilter(
				new MedianStrat(),	//replace with new Strategy
				rawList);
		setUpFilteredList(tcf);
	}
	private final static String[] names = 
		{"Taiwan","Tailand","US","UK","Japan","Korean","Sweden","Finland","Iraq","Aust"};
	private final static int[] codes = 
		{238548,385786,622899,1238068,1083065,734802,926710,1092272,1143927,967126};
	
	private static List<TravelCode> rawList;
	private static List<TravelCode> flist;
	
	private static void setUpFilteredList(TravelCodeFilter tcf) {
		flist = tcf.getFilteredList();
		System.out.println("-------filtered list show test-------");
		for(int i = 0;i<flist.size();i++) {
			TravelCode tc = flist.get(i);
			System.out.println(i+":"+tc.getTravelCodeName()+","+tc.getTravelCode());
		}
	}
	private static void setRawList() {
		rawList = new ArrayList<TravelCode>();
		System.out.println("-------raw list generating-------");
		for(int i = 0;i<10;i++) {
			rawList.add(new TravelCode(names[i],codes[i]));
			TravelCode raw = rawList.get(i);
			System.out.println(".");
		}
		System.out.println("-------raw list show-------");
		for(int i = 0;i<rawList.size();i++) {
			TravelCode tc = rawList.get(i);
			System.out.println(i+":"+tc.getTravelCodeName()+","+tc.getTravelCode());
		}
	}
}
