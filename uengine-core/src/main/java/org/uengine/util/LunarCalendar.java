package org.uengine.util;

import java.util.Calendar;

public class LunarCalendar {
	public final int SOLAR_YEAR = 1 ;
	public final int SOLAR_MONTH = 2 ;
	public final int SOLAR_DATE = 3 ;
	public final int LUNAR_YEAR = 4 ;
	public final int LUNAR_MONTH = 5 ;
	public final int LUNAR_DATE = 6 ;
	public final int IS_YUNDAL = 7;

	private final int[][] matchTable = { 
			// 1881
			{ 1, 2, 1, 2, 1, 2, 2, 3, 2, 2, 1, 2, 1 }, 
			{ 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0 }, 
			{ 2, 1, 1, 2, 1, 3, 2, 1, 2, 2, 1, 2, 2 }, 
			{ 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2 }, 
			{ 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2 }, 
			// 1891
			{ 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 1, 1, 2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 2 }, 
			{ 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 2, 1, 2, 3, 1, 2, 1, 2, 1, 2, 1 }, 
			{ 2, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 2, 3, 2, 2, 1, 2, 1, 2, 1, 2, 1 }, 
			{ 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 2, 3, 2, 2, 1, 2 }, 
			// 1901
			{ 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0 }, 
			{ 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 3, 2, 1, 1, 2, 2, 1, 2 }, 
			{ 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 0 }, 
			{ 2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 2, 1, 4, 1, 2, 1, 2, 1, 2, 1, 2 }, 
			{ 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 1, 2, 3, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0 }, 
			// 1911
			{ 2, 1, 2, 1, 1, 2, 3, 1, 2, 2, 1, 2, 2 }, 
			{ 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 2, 3, 1, 2, 1, 2, 1, 1, 2 }, 
			{ 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1 }, 
			{ 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 0 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 3, 2, 2, 1, 2, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0 }, 
			// 1921
			{ 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 2, 2, 1, 3, 2, 1, 1, 2, 1, 2, 2 }, 
			{ 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 0 }, 
			{ 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 0 }, 
			{ 2, 1, 2, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2 }, 
			{ 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0 }, 
			{ 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 1, 2, 3, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 0 }, 
			{ 1, 2, 2, 1, 1, 2, 3, 1, 2, 1, 2, 2, 1 }, 
			// 1931
			{ 2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 2, 1, 2, 4, 1, 2, 1, 2, 1, 1, 2 }, 
			{ 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0 }, 
			{ 2, 1, 1, 4, 1, 2, 1, 2, 1, 2, 2, 2, 1 }, 
			{ 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 0 }, 
			{ 2, 2, 1, 1, 2, 1, 1, 4, 1, 2, 2, 1, 2 }, 
			{ 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0 }, 
			// 1941
			{ 2, 2, 1, 2, 2, 1, 4, 1, 1, 2, 1, 2, 1 }, 
			{ 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 1, 1, 2, 1, 4, 1, 2, 1, 2, 2, 1, 2, 2 }, 
			{ 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0 }, 
			{ 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 2, 2, 3, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2 }, 
			{ 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 1, 2, 1, 3, 2, 1, 2, 1, 2 }, 
			{ 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0 }, 
			// 1951
			{ 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 4, 2, 1, 2, 1, 2, 1, 2 }, 
			{ 1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 1, 4, 1, 1, 2, 1, 2, 1, 2, 2, 2 }, 
			{ 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 2, 1, 2, 1, 1, 2, 3, 2, 1, 2, 2 }, 
			{ 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 2, 1, 2, 2, 3, 2, 1, 2, 1, 2, 1 }, 
			// 1961
			{ 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0 }, 
			{ 2, 1, 2, 1, 3, 2, 1, 2, 1, 2, 2, 2, 1 }, 
			{ 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 0 }, 
			{ 2, 2, 2, 3, 2, 1, 1, 2, 1, 1, 2, 2, 1 }, 
			{ 2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 2, 1, 2, 1, 2, 3, 2, 1, 2, 1, 2 }, 
			{ 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0 }, 
			// 1971
			{ 1, 2, 1, 1, 2, 3, 2, 1, 2, 2, 2, 1, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0 }, 
			{ 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 1, 0 }, 
			{ 2, 2, 1, 2, 3, 1, 2, 1, 1, 2, 2, 1, 2 }, 
			{ 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 1, 2, 1, 2, 3, 2, 1, 1, 2 }, 
			{ 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 0 }, 
			{ 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 1, 2, 1, 2, 4, 1, 2, 2, 1, 2, 1 }, 
			{ 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			// 1981
			{ 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 2, 1, 3, 2, 1, 1, 2, 2, 1, 2, 2 }, 
			{ 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 3, 2, 2 }, 
			{ 1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 0 }, 
			{ 2, 1, 2, 2, 1, 2, 3, 2, 2, 1, 2, 1, 2 }, 
			{ 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0 }, 
			{ 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 1, 2, 1, 1, 2, 3, 1, 2, 1, 2, 2, 2, 2 }, 
			// 1991
			{ 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 0 }, 
			{ 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0 }, 
			{ 1, 2, 2, 3, 2, 1, 2, 1, 1, 2, 1, 2, 1 }, 
			{ 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 2, 1, 2, 2, 1, 2, 3, 2, 1, 1, 2 }, 
			{ 1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0 }, 
			{ 2, 1, 1, 2, 1, 3, 2, 2, 1, 2, 2, 2, 1 }, 
			{ 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 0 }, 
			{ 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 0 }, 
			// 2001
			{ 2, 2, 2, 1, 3, 2, 1, 1, 2, 1, 2, 1, 2 }, 
			{ 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 3, 2, 2, 1, 2, 1, 2, 2, 1, 1, 2 }, 
			{ 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 1, 1, 2, 1, 2, 1, 2, 3, 2, 2, 1, 2, 2 }, 
			{ 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0 }, 
			{ 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 1, 2, 3, 1, 2, 1, 2, 1, 2, 2 }, 
			{ 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			// 2011
			{ 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 2, 4, 2, 1, 2, 1, 1, 2, 1, 2, 1 }, 
			{ 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 2, 1, 2, 2, 3, 2, 1, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 2, 0 }, 
			{ 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 1, 2, 1, 3, 2, 1, 2, 1, 2, 2, 2 }, 
			{ 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0 }, 
			{ 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 2, 1, 2, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2 }, 
			// 2021
			{ 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0 }, 
			{ 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 0 }, 
			{ 1, 2, 3, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0 }, 
			{ 2, 1, 2, 1, 1, 2, 3, 2, 1, 2, 2, 2, 1 }, 
			{ 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0 }, 
			{ 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 0 }, 
			{ 1, 2, 2, 1, 2, 3, 1, 2, 1, 1, 2, 2, 1 }, 
			{ 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 0 }, 
			{ 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 0 }, 
			// 2031
			{ 2, 1, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2, 1 }, 
			{ 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 3, 2, 2, 2, 1, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0 }, 
			{ 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 1, 1, 4, 1, 1, 2, 1, 2, 2 }, 
			{ 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 0 }, 
			{ 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 0 }, 
			{ 2, 2, 1, 2, 2, 3, 2, 1, 2, 1, 2, 1, 1 }, 
			{ 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0 }, 
			// 2041
			{ 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0 }, 
			{ 1, 2, 3, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2 }, 
			{ 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0 }};
	private int year, month, date;
	private int lunarYear, lunarMonth, lunarDate;
	private boolean leap;
	private final String lunarYuk[] = {};
	private final String lunarGap[] ={};
	private final String lunarDdi[] = {};
	private int lunarMonthDay[] = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private final String lunarWeekDay[] = {};

	public LunarCalendar(String year, String month, String date){
		this.year = Integer.parseInt(year);
		this.month = Integer.parseInt(month);
		this.date = Integer.parseInt(date);
		convert();
	}
	public LunarCalendar(int year, int month, int date){
		this.year = year;
		this.month = month;
		this.date = date;
		convert();
	}
	public LunarCalendar(){
		Calendar cal = Calendar.getInstance();
		this.year = cal.get(Calendar.YEAR);
		this.month = cal.get(Calendar.MONTH)-1;
		this.date = cal.get(Calendar.DAY_OF_MONTH);	
		convert();
	}

	private void convert(){
		int[] dt = new int[163];
		int td1, td2,  k11, td, td0, t1, t2, jcount, m2, m1, m0, w, ti1, tj1;
		for(int i=0; i<matchTable.length; i++){
			dt[i] = 0;
			for(int j=0; j<12; j++){
				if( matchTable[i][j]%2 == 1 ){
					dt[i] +=29;
				}else{
					dt[i] +=30;
				}
			}
			if( matchTable[i][12] == 0 ){
				dt[i] += 0;
			}else if( (matchTable[i][12] % 2) == 1 ){
				dt[i] += 29;
			}else{
				dt[i] += 30;
			}
		}

		td1 = 1880*365 + (int)((double)1880/(double)4) - (int)((double)1880/(double)100) + (int)((double)1880/(double)400) + 30;
		k11 = year - 1;
		td2 = k11*365 + (int)((double)k11/(double)4) - (int)((double)k11/(double)100) + (int)((double)k11/(double)400);

		if( (year%400 == 0) || (year%100 != 0) & (year%4 == 0) ){
			lunarMonthDay[1] = 29;	
		}else{
			lunarMonthDay[1] = 28;	
		}

		for(int i=0; i<month-1 ; i++){
			td2 += lunarMonthDay[i];
		}
		td2 += date;
		td = td2 - td1 + 1;
		td0 = dt[0];
		t1 = 0;
		for( t1=0 ; t1<163 ; t1++){
			if( td <= td0 ){
				break;	
			}
			td0 += dt[t1+1];
		}
	
		lunarYear = t1 + 1881;
		td0 -= dt[t1];
		td -= td0;
		
		jcount = 12;
		if( matchTable[t1][12] !=0 ){
			jcount = 13;
		}
		
		m2 = 0;
		t2 = 0;
		for( t2 =0; t2 < jcount ; t2++) {
			if( matchTable[t1][t2] <=2 ){
				m2++;
				m1 = matchTable[t1][t2] + 28;
			}else{
				m1 = matchTable[t1][t2] + 26;
			}
			if( td<=m1 ){
				break;
			}	
			td -= m1;
		}
		m0 = t2;
		lunarMonth = m2;
		lunarDate = td;
		w = td2%7;

		t1 = (int)( (td2 +4) % 10 );
		t2 = (int)( (td2 +2) % 12 );
		ti1 = (lunarYear + 6) % 10;
		tj1 = (lunarYear + 8) % 12;
		if( matchTable[lunarYear-1881][12] >2  & matchTable[lunarYear-1881][m0] >2 ){
			leap = true;
		}
		
	}
	
	public void set(int field, int value){
		switch(field){
			case SOLAR_YEAR: 
				this.year = value;
				convert();
				break;
			case SOLAR_MONTH:
				this.month = value;
				convert();
				break;
			case SOLAR_DATE:
				this.date = value;
				convert();
				break;
			case LUNAR_YEAR:
				this.lunarYear = value;
				break;
			case LUNAR_MONTH:
				this.lunarMonth = value;
				break;
			case LUNAR_DATE:
				this.lunarDate = value;
				break;
		}
	}
	public int get(int field){
		switch(field){
			case SOLAR_YEAR: 
				return this.year;
			case SOLAR_MONTH:
				return this.month;
			case SOLAR_DATE:
				return this.date;
			case LUNAR_YEAR:
				return this.lunarYear;
			case LUNAR_MONTH:
				return this.lunarMonth;
			case LUNAR_DATE:
				return this.lunarDate;
			case IS_YUNDAL:
				return leap?1:0;
			default :
				return -1;
		}
	}
	public String getYukGap(){
		String gap = lunarYuk[(lunarYear-1881+7)%10];
		gap += lunarGap[(lunarYear-1881+5)%12];
		return gap;
	}
	public String getDdi(){
		return lunarDdi[(lunarYear-1881+5)%12];
	}
}
