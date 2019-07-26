/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 날짜 관련 함수 모음
프로그램 ID : DateUtil.class
프로그램 명 : DateUtil
프로그램개요 : 문자열 관련 유틸 메서드
작 성 자 : 윤석준
작 성 일 : 2006. 4. 12
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sun.util.logging.resources.logging;

/**
 * @author asmin
 *  
 */
public class DateUtil {
    
    static Calendar cal    = null;
    static Calendar newCal = null;
    
	public static final SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat userFormat = new SimpleDateFormat();
	public static final SimpleDateFormat userFormat2 = new SimpleDateFormat();
	public static final SimpleDateFormat detailFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	/**
	 * 생성자
	 */
	public DateUtil() {
        cal    = Calendar.getInstance();
        newCal = Calendar.getInstance();
	}
	
	/**
	 * 날자 세팅
	 * @param piYear
	 * @param piMonth
	 * @param piDay
	 */
	public void setDate(int  piYear, int piMonth , int piDay ) {
		cal.setTime( new Date( piYear - 1900 , piMonth -1 , piDay ) );
	}
	
	/**
	 * 날자 세팅
	 * @param piDate "yyyyMMdd"
	 */
	public void setDate(String piDate ) {
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").replaceAll("\\.", "");
		
		if(piDate.length()==8)
		{
			cal.setTime( new Date( 
	        		Integer.parseInt(piDate.substring(0, 4))-1900 ,
	        		Integer.parseInt(piDate.substring(4, 6))-1 ,
	        		Integer.parseInt(piDate.substring(6, 8)) 
	        		)
	        );
		}
		else
		{
			cal.setTime( new Date( 
	        		Integer.parseInt(piDate.substring(0, 4))-1900 ,
	        		Integer.parseInt(piDate.substring(4, 6))-1 ,
	        		Integer.parseInt(piDate.substring(6, 8)),
	        		Integer.parseInt(piDate.substring(8, 10)),
	        		Integer.parseInt(piDate.substring(10, 12)),
	        		Integer.parseInt(piDate.substring(12, 14))	        		
	        		)
	        );
		}
	}

	/**
	 * 현재 날짜 리턴
	 * @return
	 */
	public String getDate() {
		
        return defaultFormat.format(cal.getTime());
	}
	/**
	 * 특정 포맷에 현재날짜  리턴
	 * @param format
	 * @return
	 */
	public String getDate( String piFormat ) {
		userFormat.applyPattern(piFormat);
        return userFormat.format(cal.getTime());
       
	}	
	
	
	/**
	 * 설정 날짜 포맷으로 리턴
	 * @return
	 */
	public String getDate(String piDate,String piFormat) {		
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");		
		setDate(piDate);		
		userFormat.applyPattern(piFormat);
		return userFormat.format(cal.getTime());
	}
	
	/**
	 * 설정 날짜 포맷으로 리턴
	 * @return
	 */
	public String getEnglishDate(String piDate, String piFormat) {		
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");		
		setDate(piDate);		
		SimpleDateFormat sdf = new SimpleDateFormat(piFormat, Locale.ENGLISH); 
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 설정 날짜 포맷으로 리턴
	 * @return
	 */
	public String getCountyFormatDate(String piDate, Locale lc) {		
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");		
		setDate(piDate);		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d yy", lc); 
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 현재 요일 리턴
	 * @return
	 */
	public int getWeekNum(){
		
		int weekNumber =0;		
			
		weekNumber = cal.get(Calendar.DAY_OF_WEEK);
		
		return weekNumber ;
	}
	
	/**
	 * 현재 요일 리턴
	 * @return
	 */
	public String getWeek(){
		String week = "";
		int weekNumber =0;		
			
		weekNumber = cal.get(Calendar.DAY_OF_WEEK);
		switch(weekNumber)
		{
			case 1:
				week ="(일)";
				break;
			case 2:
				week ="(월)";
				break;
			case 3:
				week ="(화)";
				break;
			case 4:
				week ="(수)";
				break;
			case 5:
				week ="(목)";
				break;
			case 6:
				week ="(금)";
				break;
			case 7:
				week ="(토)";
				break;
			
		}
		return week ;
	}
	
	/**
	 * 현재 요일 리턴
	 * @return
	 */
	public int getWeekNum(String piDate){
		
		int weekNumber =0;
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");
		setDate(piDate);		
		weekNumber = cal.get(Calendar.DAY_OF_WEEK);		
		return weekNumber ;
	}
	public String getWeek(String piDate){
		String week = "";
		int weekNumber =0;
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");
		setDate(piDate);		
		weekNumber = cal.get(Calendar.DAY_OF_WEEK);
		switch(weekNumber)
		{
			case 1:
				week ="(일)";
				break;
			case 2:
				week ="(월)";
				break;
			case 3:
				week ="(화)";
				break;
			case 4:
				week ="(수)";
				break;
			case 5:
				week ="(목)";
				break;
			case 6:
				week ="(금)";
				break;
			case 7:
				week ="(토)";
				break;
			
		}
		return week ;
	}
	
	/**
	 * 현재 요일 리턴
	 * @return
	 */
	public int getDayofWeek(String piDate){
		int weekNumber =0;
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");
		setDate(piDate);		
		weekNumber = cal.get(Calendar.DAY_OF_WEEK);
		
		return weekNumber ;
	}
	
	public int getWeekDay() {
	    return cal.get(Calendar.WEEK_OF_MONTH);
	}
	
	public int getWeekDay(String piDate) {
		setDate(piDate);
	    return cal.get(Calendar.WEEK_OF_MONTH);
	}
	
	public int getLastDay(){		
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public Date getTime(String piDate) {		
		piDate=piDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");		
		setDate(piDate);		
		return cal.getTime();
	}
	
	/**
	 * 일자 증가감 버전2 ( 캘린더 충돌날때 이거쓰세요.)
	 * 
	 * 
	 * @param  curDate "현재 일자"
	 * @param  days    "증감 일수"
	 *             
	 */
	public String addDay_v2(String curDate, int days ){
		
		curDate=curDate.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll("\\.", "");		
		
		StringBuffer sb = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		
		cal.set( Integer.parseInt(curDate.substring(0, 4))
			   , Integer.parseInt(curDate.substring(4, 6))-1
			   , Integer.parseInt(curDate.substring(6, 8)));
		cal.add( Calendar.DAY_OF_MONTH, days);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		sb.append ( year );
		sb.append ( "-" );
		if ( month < 10 ){
			sb.append ( "0" );
		}
		sb.append ( month );
		sb.append ( "-" );
		if ( day < 10 ){
			sb.append ( "0" );
		}
		sb.append ( day );
		
		return sb.toString();
	}
	
	/**
	 *일자 증가감
	 * @param piDay
	 */
	public void addDay( int piDay ) {
	    cal.add(Calendar.DATE, piDay);
	}
	
	/**
	 *시간 증가감
	 * @param piDay
	 */
	public void addHour( int piHour ) {
	    cal.add(Calendar.HOUR_OF_DAY, piHour);
	}
	
	public String addDay(String curDate, int day) throws ParseException{
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");	  
	  cal.setTime(sdf.parse(curDate));
	  cal.add(cal.DATE, day);
	  String rtnVal=sdf.format(cal.getTime());
	  return rtnVal;
	 }
		 
	 public static String addMonth(String curDate, int month) throws ParseException{
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	  Calendar newCal=Calendar.getInstance();
	  newCal.setTime(sdf.parse(curDate));
	  newCal.add(newCal.MONTH,month);
	  String rtnVal=sdf.format(newCal.getTime());
	  return rtnVal;
	 }
	 
	 public static String addYear(String curDate, int year) throws ParseException{
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	  Calendar newCal=Calendar.getInstance();
	  newCal.setTime(sdf.parse(curDate));
	  newCal.add(newCal.YEAR,year);
	  String rtnVal=sdf.format(newCal.getTime());
	  return rtnVal;
	 }
	 
	 /**
	 * 설정날짜에서 시간을 더한다.
	 * @return
	 */
	public static String addHour(String curDate, int piHour, String insertFormat, String Pformat){
		  
		  userFormat.applyPattern(insertFormat);
		  try{
			  cal.setTime(userFormat.parse(curDate));
		  }catch(Exception e){
			  System.out.println(e);
		  }
		  cal.add(cal.HOUR_OF_DAY, piHour);	
		  userFormat.applyPattern(Pformat);
		  String rtnVal=userFormat.format(cal.getTime());
		  return rtnVal;
	}	
	
	/**
	 * 설정날짜에서 설정일을 더한다.
	 * @return
	 */
	public static String addDay(String curDate, int day, String Pformat){
		  
		 return addDay(curDate, day, Pformat, Pformat);
	}
	
	/**
	 * 설정날짜에서 설정일을 더한다.
	 * @return
	 */
	public static String addDay(String curDate, int day, String insertFormat, String Pformat){
		  
		  userFormat.applyPattern(insertFormat);
		  try{
			  cal.setTime(userFormat.parse(curDate));
		  }catch(Exception e){
			  System.out.println(e);
		  }
		  cal.add(cal.DATE, day);	
		  userFormat.applyPattern(Pformat);
		  String rtnVal=userFormat.format(cal.getTime());
		  return rtnVal;
	}	
		
	
	/**
	 *주 증가감
	 * @param piDay
	 */
	public void addWeek( int piWeek ) {
	    cal.add(Calendar.WEEK_OF_YEAR, piWeek);
	}
    
	/**
	 * 월 증가감 함수
	 * @param piMonth
	 */
	public void addMonth( int piMonth ) {
	    cal.add(Calendar.MONTH , piMonth);
	}
	

	/**
	 * 년도 증가감 함수
	 * @param piYear
	 */
	public void addYear( int piYear ) {
        cal.add(Calendar.YEAR, piYear); 
	}
	

	/**
	 * 서버 기준 현재 일자 리턴
	 * @return
	 */
	public String getCurrentDate() {
		return defaultFormat.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 서버 기준 현재 일자, 시간, 분, 초 까지 리턴
	 * @return
	 */
	public String getCurrentDateDetail() {
		return detailFormat.format(Calendar.getInstance().getTime());
	}
	/**
	 * 서버 기준 현재 시간, 분 리턴
	 * @return
	 */
	
	public String getCurrentTime() {
		return timeFormat.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 특정 포맷에 현재 서버 일자 리턴
	 * @param format
	 * @return
	 */
	public String getCurrentDate(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
	}
	
	
	/**
	 * 날짜포맷을 영문으로 바꾸어 준다.
	 * @param format
	 * @return String SimpleDateFormat
	 */
	public String getCurrentDate(String format, String plocal) {
		if(plocal.equals("eng") ){
			return new SimpleDateFormat(format, Locale.ENGLISH).format(Calendar.getInstance().getTime());
		}else{
			return new SimpleDateFormat(format, Locale.KOREA).format(Calendar.getInstance().getTime());
		}
		
	}

	/**
	 * 현제 년도 리턴
	 * @return
	 */
	public String getCurrentYear() {
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	/**
	 * 현재 날자 월 리턴 
	 * @return yyyymm
	 */
	public String getCurrentYearMonth(int pday) {
		String strYear = "";
		String strMonth = "";
		strYear = String.valueOf(cal.get(Calendar.YEAR));		
		strMonth = String.valueOf(cal.get(Calendar.MONTH) + (1 + pday)  );
		if(strMonth.length() < 2)
			strMonth = "0"+ strMonth;
		
		return strYear + strMonth;
	}
	
	/**
	 * 날짜 차이 계산
	 * @return yyyymm
	 */
	public long DateDiff(String sDate, String eDate) throws Exception {
		long result ;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		
		Date mSdate = fmt.parse(sDate);
		Date mEdate = fmt.parse(eDate);
		result = (mSdate.getTime() - mEdate.getTime()) / (1000 * 60 * 60 * 24);

		return result ;
		
	}
	
	
	
	public long DateDiff(String format,String sDate, String eDate) throws Exception {
		long result ;
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		
		Date mSdate = fmt.parse(sDate);
		Date mEdate = fmt.parse(eDate);
		result = (mSdate.getTime() - mEdate.getTime()) / (1000 * 60 * 60 * 24);

		return result ;
		
	}
	
	public long DateDiffSecond(String format,String sDate, String eDate) throws Exception {
		long result ;
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		
		Date mSdate = fmt.parse(sDate);
		Date mEdate = fmt.parse(eDate);
		result = (mSdate.getTime() - mEdate.getTime()) / 1000;

		return result ;
		
	}
	
	/**
	 * 날짜 초단위로 리턴
	 * @return yyyymm
	 */
	public long getSecond(String format, String date){
		long result = 0 ;
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		
		try{
			Date Ddate = fmt.parse(date);		
			result = Ddate.getTime()*1000;
		}catch(Exception e){
			Log.debug(e.getMessage());
		}

		return result ;
		
	}
	
	
	 public static String getLastDate() {
		  //Calendar tmp_cal = cal;
		  setDay(cal.getActualMaximum(Calendar.DAY_OF_MONTH) );
		        return defaultFormat.format(cal.getTime());
		 }
	 
	 public static int getDayCount() {
		  //Calendar tmp_cal = cal;
		  int reCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		  return reCount;
	}

	 /**
	  * 날짜 지정
	  * @param piDay
	  */
	 public static void setDay(int piDay ) {
	  cal.set(Calendar.DATE , piDay);
	        //cal.setTime( new Date( piYear - 1900 , piMonth -1 , piDay ) );
	 }
	
	public Calendar getCal() {
		return this.cal;
	}
	
	/**
	 * 날짜 형식 포멧
	 * date : 날짜 또는 날짜와 시간
	 * datefm : 원하는 날짜포멧 형식 ex) - / . (2008-08-05)
	 * timefm : 원하는 시간포멧 형식 ex) : (15:21:30)
	 * @param DateFormat
	 * 
	 */
	public String getFormatDate(String date,String datefm,String timefm)
	{
		if (date.length()>14) {
			date = date.substring(0,4)+datefm+date.substring(4,6)+datefm+date.substring(6,8)+" "+date.substring(8,10)+timefm+date.substring(10,12)+timefm+date.substring(12,date.length());
		} else if (date.length()==14) {
			date = date.substring(0,4)+datefm+date.substring(4,6)+datefm+date.substring(6,8)+" "+date.substring(8,10)+timefm+date.substring(10,12)+timefm+date.substring(12,14);
		} else if (date.length()==12) {
			date = date.substring(0,4)+datefm+date.substring(4,6)+datefm+date.substring(6,8)+" "+date.substring(8,10)+timefm+date.substring(10,12);
		} else if (date.length()==8) {
			date = date.substring(0,4)+datefm+date.substring(4,6)+datefm+date.substring(6,8);
		}
		
		return date;
	}
	
	/**
	   * 년월을 입력하면 그 달의 마지막 날짜를 리턴하는 함수 
	   * @param yyyymm
	   * @return 마지막 날짜 
	   */
	  public String LastDay(String yyyymm)
	  {
	     String lastmonthDay = "";
	           String mm = yyyymm.substring(4,6);
	           int year = Integer.parseInt(yyyymm.substring(0,4));
	           
	     SimpleDateFormat formatter = new SimpleDateFormat(yyyymm);
	       java.util.Date d;
	       try {
	        
	        if(mm.equals("01") || mm.equals("03")|| mm.equals("05")|| mm.equals("07")|| mm.equals("08")|| mm.equals("10")|| mm.equals("12") )
	        {         
	         lastmonthDay = "31";
	        }else if(mm.equals("04") || mm.equals("06")||mm.equals("09") || mm.equals("11")){
	         lastmonthDay = "30";
	        }else if(mm.equals("02")){
	         if ((year%4==0) && ((year%100!=0) || (year%400==0))){
	         lastmonthDay = "29";
	         }else{
	         lastmonthDay = "28";
	         }
	        }
	       } catch (Exception e) {
	        e.printStackTrace();
	       }
	      return lastmonthDay;
	     }
	  
	  /**
	   * yyyy-MM-dd 로 입력하면 그주의 시작날짜 리턴
	   * @param date
	   * @return 그 주의 시작날짜
	   */
	  public String sDayofWeek(String date){
		String sWeekday = "";
		
		int now = getDayofWeek(date);
		int turm = -now+2;
		System.out.println("turm : "+turm);
		try{	
			sWeekday = addDay(date,turm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sWeekday;
	  }
	  
	  /**
	   * yyyy-MM-dd 로 입력하면 그주의 마지막날짜 리턴
	   * @param date
	   * @return 그 주의 마지막날짜
	   */
	  public String eDayofWeek(String date){
		  String eWeekday = "";
		  
		  int now = getDayofWeek(date);
		  int turm = -now+2;
		  try{	
			 String sWeekday = addDay(date,turm);
			 eWeekday = addDay(sWeekday,6);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return eWeekday;
	  }
	  
	  /**
	   * yyyy-MM-dd 로 입력하면 그달의 시작날짜 리턴
	   * @param date
	   * @return 그 달의 시작날짜
	   */
	  public String sDayofMonth(String date){
		  String sMonthday = date.substring(0,8)+"01";
		  return sMonthday;
	  }
	  
	  public static int getWeekOfMonth() {
		  Locale LOCALE_COUNTRY = Locale.KOREA;
		  Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		  int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
		  return week_of_month;
	  }
	  
	  public static String getWeekToDay(String yyyymm , int week, String pattern) {
		  Calendar cal = Calendar.getInstance(Locale.FRANCE);
		  int new_yy = Integer.parseInt(yyyymm.substring(0,4));
		  int new_mm = Integer.parseInt(yyyymm.substring(4,6));
		  int new_dd = 1;
		  cal.set(new_yy,new_mm-1,new_dd);
		  if (cal.get(cal.DAY_OF_WEEK)==cal.SUNDAY) {
			  week = week-1;
		  }
		  cal.add(Calendar.DATE,(week-1)*7+(cal.getFirstDayOfWeek()-cal.get(Calendar.DAY_OF_WEEK)));
		  SimpleDateFormat formatter = new SimpleDateFormat(pattern,Locale.FRANCE);
		  return formatter.format(cal.getTime());
	  }
	  
	  /**
	   * yyyy-MM-dd 로 입력하면 그달의 마지막날짜 리턴
	   * @param date
	   * @return 그 달의 마지막날짜
	   */
	  public String eDayofMonth(String date){
		  	
		  	String lastDay = LastDay((date.replaceAll("-","")).substring(0,6));
			
			String yearMonth = date.substring(0,8);
			
			return yearMonth+lastDay;
	  }
	
	  public ArrayList getarrDate(String sDate, String eDate){
		  ArrayList arrDate = new ArrayList();
		  String tmpDate = sDate;
		  try{
			  while(!tmpDate.equals(eDate)){
				  arrDate.add(tmpDate);
				  tmpDate = addDay(tmpDate, 1);
			  }
			  arrDate.add(tmpDate);
		  }catch(Exception e){
			  System.out.println(e);
		  }
		  
		  return arrDate;
	  }
	  
	    public String formatDate(String yyyyMMdd, String pattern1, String pattern2) throws ParseException{
	    	if(yyyyMMdd == null || yyyyMMdd.equals("")){
	    		return "";
	    	}
	    	SimpleDateFormat fommatter = new SimpleDateFormat(pattern2);

	    	return fommatter.format(new SimpleDateFormat(pattern1).parse(yyyyMMdd));
	    }
	  
	  
	  
		public static String[] arrHour(String sdate, String edate) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long diff;
			String[] result = null;
			
			Date mSdate = sdf.parse(sdate);
			Date mEdate = sdf.parse(edate);
			diff = (mSdate.getTime() - mEdate.getTime()) / (1000 * 60 * 60);
			if(diff < 0){
				diff = diff * -1;
			}
			result = new String[(int)diff+1];
			Calendar newCal=Calendar.getInstance();
			newCal.setTime(sdf.parse(sdate));
			for(int i = 0; i < result.length; i++){
				result[i] = sdf.format(newCal.getTime());
				newCal.add(newCal.HOUR, 1);
			}
			return result;
		}
		
		public static String[] arrDay(String sdate, String edate) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long diff;
			String[] result = null;
			
			Date mSdate = sdf.parse(sdate);
			Date mEdate = sdf.parse(edate);
			diff = (mSdate.getTime() - mEdate.getTime()) / (1000 * 60 * 60 * 24);
			if(diff < 0){
				diff = diff * -1;
			}
			result = new String[(int)diff+1];
			Calendar newCal=Calendar.getInstance();
			newCal.setTime(sdf.parse(sdate));
			for(int i = 0; i < result.length; i++){
				result[i] = sdf.format(newCal.getTime());
				newCal.add(newCal.DATE, 1);
			}
			return result;
		}
		
		 /**
		   * 설명: 입력 받은 기간대비 전 기간을 리턴한다. 
		   * 로직:
		   * 1. 
		   * @param format
		   * @param sDate
		   * @param eDate
		   * @return
		   * @throws Exception
		   */
		  public String[] getDayBefore(String format, String sDate, String eDate) throws Exception{
			  
			  long dateDiff = this.DateDiff(format,sDate, eDate);
			  
			  dateDiff = dateDiff - 1;
			  
			  String sDateDayBefore = this.addDay(sDate, Integer.parseInt(String.valueOf(dateDiff)),format);
			  String eDateDayBefore = this.addDay(eDate, Integer.parseInt(String.valueOf(dateDiff)),format);
			  
			  String[] s = new String[2];
			  s[0] = sDateDayBefore;
			  s[1] = eDateDayBefore;
			  
			  return s;
			  
		  } 
		  
		  public static String timestamp2Date(String seconds, String format) {
			  
				if(seconds == null || seconds.equals("null")){
					return "";
				}
				
				if(format == null) format = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				return sdf.format(new Date(Long.valueOf(seconds+"000")));
		  }
}