package com.genomu.starttravel.travel_data;

import java.util.ArrayList;
import java.util.List;

public class TestTravelHandler {
	
	public static void main(String[] args) {
		setUpRawList();
		TravelFilter tf = new TravelFilter(
				new MedianStrat(),	//replace with new Strategy
				rawList);
		setUpFilteredList(tf);
	}

	private static void setUpFilteredList(TravelFilter tf) {
		flist = tf.getFilteredList();
		System.out.println("-------filtered list show test-------");
		for(int i = 0;i<flist.size();i++) {
			Travel tc = flist.get(i);
			System.out.println(i+":"+tc.getTitle()+"\n價格:"+tc.getPrice());
		}
	}
	
	private static void setUpRawList() {
		rawList = new ArrayList<Travel>();
		System.out.println("-------raw list generating-------");
		for(int i = 0;i<7;i++) {
			rawList.add(new Travel(titles[i],codes[i],keys[i],prices[i],starts[i],ends[i],lowwers[i],uppers[i]));
			Travel raw = rawList.get(i);
			System.out.println(".");
		}
		System.out.println("-------raw list show-------");
		for(int i = 0;i<rawList.size();i++) {
			Travel tc = rawList.get(i);
			System.out.println(i+":"+tc.getTitle()+"\n價格:"+tc.getPrice());
		}
	}
	
	
	
	private final static String[] titles = 
		{"【主題旅遊】阿爾卑斯Ｘ波希米亞｜山湖美景｜金色大廳音樂會｜米其林餐｜蒸汽火車｜不凡體驗｜深度奧捷13日"
			,"魅力濟州－購物兩站、兩晚五花、牛島海洋公園、卡丁車+騎馬體驗、山茶花園、樂高樂園、塗鴉秀５日"
			,"【土耳其單飛10天】最近.最快.最好土耳其"
			,"你出國•我贊助♯梅瀾的微笑 吳哥五日"
			,"波蘭+波羅的海三小國+北歐6國全覽12日 即日起報名2020年5月～2020年10月行程，享早鳥送小費"
			,"波蘭+波羅的海三小國+北歐6國全覽12日 即日起報名2020年5月～2020年10月行程，享早鳥送小費"
			,"【岡山四國.金刀比羅宮.倉敷美觀5日】~千光寺.道後松山城.大步危遊船.栗林公園.手作烏龍麵DIY"
		};
	private final static int[] codes = 
		{101,343,368,391,395,395,401};
	private final static String[] keys = 
		{"VDR0000001846","VDR0000001888","VDR0000010449","VDR0000001955","VDR0000001255","VDR0000001255","VDR0000001843"};
	private final static int[] prices = 
		{159900,17900,73100,22900,59900,69900,26500};
	private final static String[] starts = 
		{"2021-06-27","2020-10-21","2020-06-30","2020-03-11","2020-06-07","2020-07-21","2020-04-19"};
	private final static String[] ends = 
		{"2021-07-09","2020-10-25","2020-07-09","2020-03-15","2020-06-18","2020-08-01","2020-04-23"};
	private final static int[] lowwers = 
		{15,16,26,10,32,32,16};
	private final static int[] uppers = 
		{21,16,26,2,45,45,36};
	private static List<Travel> rawList;
	private static List<Travel> flist;
	
}
