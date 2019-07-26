package risk.demon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.mail.GoogleSmtp;
import risk.mobile.AlimiReceiverBean;
import risk.mobile.AlimiSettingBean;
import risk.search.MetaBean;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;
import risk.util.rrimiUtil;

public class AlimiTestJava{
  static GoogleSmtp gMail = new GoogleSmtp(); 
  static boolean chkWork = false;		// 근무시간 여부
  static int chkTimeMode = 1;			// 시간구분 ( 1: 새벽, 2: 근무시간, 3: 근무외시간)

  static String maxSeq = null;

  static DBconn dbconn = null;

  
  public static void main(String[] args){
    Log.crond("MOELSendAlimi Start..");
	try{
	  dbconn = new DBconn();
	  dbconn.getSubDirectConnection();
	
	  // 근무시간, 시간구분 설정
	  java.util.Date currDate = null;
	  currDate = new java.util.Date();
	  int time = currDate.getHours();
	  int min = currDate.getMinutes();
	  System.out.println("현재 : "+currDate);
	  SimpleDateFormat sdf01 = new SimpleDateFormat("HH");
	  SimpleDateFormat sdf02 = new SimpleDateFormat("E");
	  SimpleDateFormat sdf03 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	  int currHour = Integer.parseInt( sdf01.format(currDate) );
	  String currWeek = sdf02.format(currDate);
	
	  ArrayList arr_chkTimeMode = new ArrayList();
	
	  if(!currWeek.equals("토") && !currWeek.equals("일") && currHour >= 6 && currHour < 22){ //주말 제외 근무시간 
	    arr_chkTimeMode.add("1");
	  }
	  if(!currWeek.equals("토") && !currWeek.equals("일") && (currHour >= 22 || currHour < 6)){ //주말 제외 근무시간
	    arr_chkTimeMode.add("2");
	  }
	  if((currWeek.equals("토") || currWeek.equals("일")) && currHour >= 6 && currHour < 22){ //주말 포함 근무시간
	    arr_chkTimeMode.add("3");
	  }
	  if((currWeek.equals("토") || currWeek.equals("일")) && currHour >= 9 && currHour < 21){ // 주말 포함 근무 시간2
	    arr_chkTimeMode.add("4");
	  }
	  if((currWeek.equals("토") || currWeek.equals("일")) && (currHour >= 22 || currHour < 6)){ //주말 포함 근무 외시간
	    arr_chkTimeMode.add("5");
	  }
	
	
	
	  /*
			//주말을 제외한 오전 7시 부터 오후 8시까지
			if( (6<currHour) && (currHour<20) && !currWeek.equals("토") && !currWeek.equals("일") ){
				chkWork = true;			//근무시간
			} else {
				chkWork = false;		//근무시간 외 
			}
	
			if((0 <= currHour) && (currHour < 6)){			//0시부터 07시까지
				chkTimeMode = 3;
			}else if((6 <= currHour) && (currHour < 20)){	//7시부터 20시까지
				chkTimeMode = 1;
			}else{											//나머지
				chkTimeMode = 2;
			}
	   */
	
	  //최근 수집된 정보
	  maxSeq = getMaxMtno();
	
	  //알리미 설정
	  ArrayList arrAlimiSet = null;
	  ArrayList arrReceiverList = new ArrayList();
	  ArrayList arrAlimiData = new ArrayList();
	
	  arrAlimiSet = getAlimiSetList(1, 0, "", "Y");
	
	  if(arrAlimiSet != null){
	    for(int i = 0; i < arrAlimiSet.size(); i++){
	      AlimiSettingBean asBean = new AlimiSettingBean();
	      asBean = (AlimiSettingBean)arrAlimiSet.get(i);
	
	      //알리미 세팅에 해당하는 수신자
	      if(asBean.getArrReceiver()!=null){
	        arrReceiverList = asBean.getArrReceiver();
	      }
	
	      //수신자가 없으면 패스
	      if(arrReceiverList == null){continue;}
	      boolean successSend = false;
	      String mailAddr = "";
	      if(asBean.getAs_type().equals("10")){				//메일발송
	    	
	      }else if(asBean.getAs_type().equals("20")){			//SMS발송
	       
	      }else if(asBean.getAs_type().equals("3")){      //R-rimi 푸시 발송
	    	  //알리미 발송
		      Log.crond("발송여부 체크 "+asBean.getAs_seq());
	    	  if(asBean.getAs_chk().equals("1")){				//정보발송여부 발송일때만
	    		Log.crond("발송방법 : R-rimi확인");
			    Log.writeExpt("RRIMILOG", "발송방법 : R-rimi확인");  
		        //알리미 세팅조건에 해당하는 데이터
		        arrAlimiData = getAlimiDataForRrimi("291560843", "291567473", asBean.getKg_xps(), asBean.getKg_yps(), asBean.getSg_seqs(), asBean.getSd_gsns(), asBean.getMt_types());
		        Log.crond("발송여부확인~");
		        Log.writeExpt("RRIMILOG", "발송여부확인~");
		        //String[] sms_time = asBean.getAs_sms_time().split(",");
		        
		    	//boolean send = false;
		        //for(int j = 0; j < sms_time.length; j++){
		          //Log.crond("   sms_time[j]비교chkTimeMode  ====>>   "+sms_time[j]+" :: "+chkTimeMode);
		          //Log.writeExpt("RRIMILOG", "   sms_time[j]비교chkTimeMode  ====>>   "+sms_time[j]+" :: "+chkTimeMode);
		          
		          /*for(int s = 0 ; s < arr_chkTimeMode.size(); s++){
		            if(!send && sms_time[j].equals((String)arr_chkTimeMode.get(s))){
		              Log.crond("chkTimeMode : "+chkTimeMode);
		              send = true;
		              //sendRrimi(arrAlimiData, arrReceiverList, asBean);
		              successSend = true;
		            }
		          }*/
		        //}
	    	 } // 발송여부 체크
	      }
	      //발송이 됐을 경우에만 라스트 MT_NO를 업데이트 한다.
	      /*if(successSend){
	    	  updateAlimiSet( asBean.getAs_seq(), maxSeq );
	      }*/
	    }
	  }
	}catch(SQLException ex) {
	  ex.printStackTrace();
	  Log.writeExpt(ex);
	}catch(Exception ex) {
	  ex.printStackTrace();
	  Log.writeExpt(ex);
	}finally{
	  if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	}
	Log.crond("MOELSendAlimi Terminate");
	  }
	
	  static ArrayList getAlimiDataForRrimi(String sno, String eno, String xp, String yp, String sg_seq, String sd_gsn, String mt_type){
	    ArrayList result = new ArrayList();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	
	    try{
	      sb = new StringBuffer();
	      sb.append("## 알리미 정보 (R-rimi)	\n");
	      sb.append("SELECT	\n");
		  sb.append("MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE ,B.MD_DATE	\n");
		  sb.append(",B.MD_SAME_COUNT ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, ISSUE_CHECK, I_DELDATE, M_SEQ, D.MD_CONTENT	\n");
		  sb.append(",FN_GET_KEYWORD5(A.MD_SEQ) AS K_VALUE	\n");
		  sb.append("FROM IDX A, META B, KEYWORD C, DATA D	\n");
		  sb.append("WHERE A.MD_SEQ > "+sno+" AND A.MD_SEQ <= "+eno+"	\n");
		  sb.append("AND A.MD_SEQ = B.MD_SEQ	\n");
		  if(!xp.equals(""))sb.append("AND A.K_XP   IN ( "+xp+" )	\n");
		  if(!yp.equals(""))sb.append("AND A.K_YP   IN ( "+yp+" )	\n");
		  if(!sg_seq.equals("") && !sd_gsn.equals("")){
		    sb.append("AND (A.SG_SEQ IN ("+sg_seq+") OR B.S_SEQ IN ("+sd_gsn+"))	\n");
		  }else if(!sg_seq.equals("") && sd_gsn.equals("")){
		    sb.append("AND A.SG_SEQ IN ("+sg_seq+")	\n");
		  }else if(sg_seq.equals("") && !sd_gsn.equals("")){
		    sb.append("AND B.S_SEQ IN ("+sd_gsn+")	\n");
		  }
		  if(!mt_type.equals(""))sb.append("AND B.MD_TYPE IN ("+mt_type+")	\n");
		  //sb.append("AND B.MD_SEQ = B.MD_PSEQ\n");
		  sb.append("AND B.MD_SEQ = D.MD_SEQ	\n");
		  sb.append("AND (A.I_STATUS = 'N' AND A.M_SEQ <> 2)	\n");
		  sb.append("AND A.K_XP = C.K_XP AND A.K_YP = C.K_YP AND A.K_ZP = C.K_ZP	\n");
		  sb.append("GROUP BY B.MD_PSEQ	\n");
		  sb.append("ORDER BY B.MD_SEQ DESC	\n");
		  System.out.println(sb.toString());
		  Log.crond(sb.toString());
		  Log.writeExpt("RRIMILOG", sb.toString());
		  pstmt = dbconn.createPStatement(sb.toString());
	
		  rs = pstmt.executeQuery();
		  while(rs.next()){
		    MetaBean mBean  = new MetaBean();
		    mBean.setMd_seq(rs.getString("MD_SEQ"));
		    mBean.setS_seq(rs.getString("S_SEQ"));
		    mBean.setSg_seq(rs.getString("SG_SEQ"));
		    mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
		    mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
		    mBean.setMd_type(rs.getString("MD_TYPE"));
		    mBean.setMd_date(rs.getString("MD_DATE"));
		    mBean.setMd_pseq(rs.getString("MD_PSEQ"));
		    mBean.setMd_title(rs.getString("MD_TITLE"));
		    mBean.setMd_url(rs.getString("MD_URL"));
		    mBean.setMd_content(rs.getString("MD_CONTENT"));
		    mBean.setK_value(rs.getString("K_VALUE"));
		    mBean.setMd_same_count(rs.getString("MD_SAME_COUNT")); 
		    mBean.setM_seq(rs.getString("M_SEQ"));
		    mBean.setI_deldate(rs.getString("I_DELDATE"));
		    result.add(mBean);  
		  }
	
	}catch(SQLException ex){
	  ex.printStackTrace();
	  Log.writeExpt(ex, sb.toString());
	  Log.writeExpt("RRIMILOG", ex.toString());
	}catch(Exception ex){
	  ex.printStackTrace();
	  Log.writeExpt(ex);
	  Log.writeExpt("RRIMILOG", ex.toString());
	    }finally{
	      sb = null;
	      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	
	    return result;
	  }
	  
	  public static void sendRrimi(ArrayList arrAlimiData, ArrayList arrReceiverList, AlimiSettingBean asBean){
	    try{
	      Log.crond(arrAlimiData.size()+"건발송");
	
	  String dateTime = "";
	  String title = "";
	  String url = "";
	  String content = "";
	  String recievers = "";
	  String source = "";
	  String md_seq = "";
	  String k_value = "";
	  
	  for( int i=0 ; i<arrAlimiData.size() ; i++ ){
	    MetaBean metaBean = (MetaBean)arrAlimiData.get(i);
	
	    Log.crond("보내는 데이터md_seq : "+metaBean.getMd_seq());
	
	    md_seq = metaBean.getMd_seq();
	    dateTime = metaBean.getMd_date();
	    title = metaBean.getMd_title();
	    url = metaBean.getMd_url();
	    content = metaBean.getMd_content();
	    source = metaBean.getMd_site_name();
	    k_value = metaBean.getK_value();
	    
	    
	    
	    String sameSeq = getLogSameList(metaBean.getS_seq(), metaBean.getMd_title(), asBean.getAs_same_percent(), asBean.getAs_seq());
	
	    if(sameSeq.equals("")){
	      
	      for( int k = 0 ; k < arrReceiverList.size() ; k++ ){
	        AlimiReceiverBean arBean = new AlimiReceiverBean();
	        arBean = (AlimiReceiverBean)arrReceiverList.get(k);
	        
	        if(recievers.equals("")){
	          recievers = arBean.getAb_mobile().trim().replaceAll("-", ""); 
	        }else{
	          recievers += ","+arBean.getAb_mobile().trim().replaceAll("-", "");
	        }
	      }
	      
	      boolean rslt = false;
	      rrimiUtil rUtil = new rrimiUtil();
	      rslt = rUtil.sendRRIMI(md_seq, recievers, dateTime, title, url, content, source, k_value, asBean.getAs_seq(), metaBean.getMd_type(), "1", dbconn);
	      
	      if(rslt){
	        Log.crond("R-rimi 발송 성공");
	        Log.writeExpt("RRIMILOG", "R-rimi 발송 성공");
	        
	        RegAlimiLog(metaBean, arrReceiverList, "1", metaBean.getMd_seq(), asBean, "[R-rimi]" + title);
	      } else {
	        Log.crond("R-rimi 발송 실패");
	        Log.writeExpt("RRIMILOG", "R-rimi 발송 실패");
	        RegAlimiLog(metaBean, arrReceiverList, "0", metaBean.getMd_seq(), asBean, "[R-rimi]" + title);
	      }
	
	    }else{
	      for( int k = 0 ; k < arrReceiverList.size() ; k++ ){
	        AlimiReceiverBean arBean = new AlimiReceiverBean();
	        arBean = (AlimiReceiverBean)arrReceiverList.get(k);
	        RegAlimiLog(metaBean, arBean, "2", sameSeq, asBean, "[R-rimi]" + title);
	          }
	        }
	
	
	
	      }
	    }catch(Exception e){
	      e.printStackTrace();
	      Log.writeExpt(e);
	    }
	  }
	
	  public static void sendSms(ArrayList arrAlimiData, ArrayList arrReceiverList, AlimiSettingBean asBean){
	    ConfigUtil cu = new ConfigUtil();
	    StringUtil su = new StringUtil();
	    DateUtil du = new DateUtil();
	    try{
	      String msg = "";
	  Log.crond(arrAlimiData.size()+"건발송");
	
	  String shotUrl = "";
	
	  for( int i=0 ; i<arrAlimiData.size() ; i++ ){
	    MetaBean metaBean = (MetaBean)arrAlimiData.get(i);
	
	
	    Log.crond("보내는 데이터md_seq : "+metaBean.getMd_seq());
	
	    shotUrl = GetHtmlPost("http://n.realsn.com","url="+java.net.URLEncoder.encode(metaBean.getMd_url(),"utf-8")+"&site=POSCO");
	
	    if(shotUrl.trim().equals("") || shotUrl.toLowerCase().indexOf("http") < 0){
	      shotUrl = "http://n.realsn.com/05/?n=" + metaBean.getMd_seq(); 
	    }
	
	    shotUrl = " " + shotUrl; 
	
	    msg = "["+su.cutString( metaBean.getMd_site_name().replaceAll(" ", ""), 4, "" )+ " "+metaBean.getMd_date().substring(11, 16)+"]"+ metaBean.getMd_title();
	    //msg += URL;
	    int titleLeng = msg.getBytes().length;
	
	    int maxLeng = 80 - (shotUrl.getBytes().length);
	
	    while(titleLeng>maxLeng){
	      msg = msg.substring(0, msg.length()-1);
	
	      titleLeng = msg.getBytes().length;
	    }
	
	    msg += shotUrl;
	
	    System.out.println("발송메세지 : "+msg);
	    System.out.println("발송메세지 크기 : "+msg.getBytes().length);
	
	    String sameSeq = getLogSameList(metaBean.getS_seq(), metaBean.getMd_title(), asBean.getAs_same_percent(), asBean.getAs_seq());
	
	    if(sameSeq.equals("")){
	
	      for( int k = 0 ; k < arrReceiverList.size() ; k++ ){
	        AlimiReceiverBean arBean = new AlimiReceiverBean();
	        arBean = (AlimiReceiverBean)arrReceiverList.get(k);
	
	        String url = "http://www.munja114.co.kr/Remote/RemoteSms.html";
	        StringBuffer param = new StringBuffer();
	        param.append("remote_id=rsnrsn");  
	        param.append("&remote_pass=9219"); 
	        param.append("&remote_num=%s");    //문자메시지 전송할 개수
	        param.append("&remote_reserve=%d");//문자메시지 예약전송 체크 (0 : 즉시전송, 1: 예약전송)
	        param.append("&remote_phone=%s");  //문자메시지 수신번호 
	        param.append("&remote_callback=07076000339");
	        param.append("&remote_msg=%s");   //내용
	
	        String data = String.format(param.toString(), "1", 0, arBean.getAb_mobile().trim().replaceAll("-", ""), java.net.URLEncoder.encode(msg,"euc-kr"));
	        String rslt = GetHtmlPost(url, data);
	
	        //String rslt = insertSmsMessage(arBean.getAb_mobile().trim().replaceAll("-", ""),msg);
	
	        if(rslt.equals("0000")){
	          Log.crond(arBean.getAb_mobile()+"번호로 전송되었습니다.");
	          RegAlimiLog(metaBean, arBean, "1", metaBean.getMd_seq(), asBean, msg);
	        }else{
	          Log.crond("전송실패");
	          RegAlimiLog(metaBean, arBean, "0", metaBean.getMd_seq(), asBean, msg);
	        }
	      }
	
	
	    }else{
	      for( int k = 0 ; k < arrReceiverList.size() ; k++ ){
	        AlimiReceiverBean arBean = new AlimiReceiverBean();
	        arBean = (AlimiReceiverBean)arrReceiverList.get(k);
	        RegAlimiLog(metaBean, arBean, "2", sameSeq, asBean, msg);
	          }
	        }
	
	
	
	      }
	    }catch(Exception e){
	      e.printStackTrace();
	      Log.writeExpt(e);
	    }
	  }
	
	  public static String insertSmsMessage(String phone, String msg){
	
	    String result = null;
	    PreparedStatement pstmt = null;
	    int cnt = 0;
	
	    StringBuffer sb = null;
	    try{
	      sb = new StringBuffer();
	      sb.append("Insert into em_tran																\n");
		  sb.append("(tran_phone, tran_callback, tran_status, tran_date, tran_msg , tran_type) values	\n");
		  sb.append("(?, '07076000339', '1', sysdate(), ? ,4);											\n");
		  System.out.print(sb.toString());
		  pstmt = dbconn.createPStatement(sb.toString());
		  pstmt.setString(1, phone);
		  pstmt.setString(2, msg);
		  cnt = pstmt.executeUpdate();
	
		  if(cnt > 0){
		    result = "0000";
		  }else{
		    result = "";
	      }
	
	
	    }catch(SQLException ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex, sb.toString());
	    }catch(Exception ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    }finally{
	      sb = null;
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	    return result;
	
	  }
	
	
	  public static String getLogSameList(String s_seq, String title, int samePer, String as_seq){
	
	    Statement stmt  = null;
	    ResultSet rs    = null;
	    StringBuffer sb = null;
	
	    java.util.Date uDate = new java.util.Date();
	    SimpleDateFormat simDate = new SimpleDateFormat("yyyy-MM-dd");
		String curdate = simDate.format(uDate);
		
		String result = "";
	
		try
		{
		  stmt = dbconn.createStatement();			
		  sb = new StringBuffer();
		  sb.append("SELECT DISTINCT MT_NO, S_SEQ, MT_TITLE 									\n");
		  sb.append("  FROM MALIMI_LOG 														\n");
		  sb.append(" WHERE MT_DATE BETWEEN '"+curdate+" 00:00:00' AND '"+curdate+" 23:59:59'	\n"); 
		  sb.append("   AND S_SEQ = "+s_seq+"		     										\n");
		  sb.append("   AND AS_SEQ = "+as_seq+"		     									\n");
		
		  Log.debug(sb.toString() );			
		  rs = stmt.executeQuery(sb.toString());
		
		  String mt_title = "";
		
		  int percent = 0; 
		
		  while(rs.next())
		  {
		    mt_title = rs.getString("MT_TITLE");
		
		    percent = StrCompare(title, mt_title);
		
		    if( percent > samePer){
		      result = rs.getString("MT_NO");
		          break;
		        }
		      }
		
		    } catch (SQLException ex ) {
		      ex.printStackTrace();
		      Log.writeExpt(ex, ex.getMessage() );
		
		    } catch (Exception ex ) {
		      ex.printStackTrace();
		      Log.writeExpt(ex.getMessage());
		
		    } finally {
		      sb = null;
		      try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		      try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
		    }   
		
	    return result;
	  }
	
	  //	문자열의 유사도 비교
	  public static int StrCompare(String s1, String s2)
	  {
	    int repercent = 0;
	    try{
	
	      //공백 제거
	  s1 = s1.replaceAll(" ","");
	  s2 = s2.replaceAll(" ","");
	
	  //소문자로 변환
	  s1 = s1.toLowerCase();
	  s2 = s2.toLowerCase();
	
	  int lcs = 0;
	  int ms = 0;
	  int i = 0;
	  int j = 0;
	
	  //바이트로 읽어들임
	  byte[] b1 = s1.getBytes();
	  byte[] b2 = s2.getBytes();
	
	  //바이트의 크기가져오기
	  int m = b1.length;
	  int n = b2.length;
	
	  //[128][128]의 배열 생성
	  int [][] LCS_Length_Table = new int[m+1][n+1];
	
	  //배열 초기화
	  for(i=1; i <  m; i++) LCS_Length_Table[i][0]=0;
	  for(j=0; j < n; j++) LCS_Length_Table[0][j]=0;
	
	  //루프를 돌며 바이트를 비교
	  for (i=1; i <= m; i++) {
	    for (j=1; j <= n; j++) {
	      if ((b1[i-1]) == (b2[j-1])) {
	        LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j-1] + 1;
	      } else if (LCS_Length_Table[i-1][j] >= LCS_Length_Table[i][j-1]) {
	        LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j];
	      } else {
	        LCS_Length_Table[i][j] = LCS_Length_Table[i][j-1];
	      }
	    }
	  }
	
	  //percent를 리턴
	      ms = (m + n) / 2;
	      lcs = LCS_Length_Table[m][n];
	      repercent = ((lcs*100)/ms);
	
	    }catch(ArithmeticException ex){
	
	    }
	    return repercent;
	
	  }
	
	
	  public static String GetHtmlPost(String sUrl, String param){
	    StringBuffer html = new StringBuffer();
	    try{
	      String line = null;
	      URL aURL = new URL(sUrl);
	      HttpURLConnection urlCon = (HttpURLConnection)aURL.openConnection();
	      urlCon.setRequestMethod("POST");
	
		  urlCon.setDoInput(true);
		  urlCon.setDoOutput(true);
		  OutputStream out = urlCon.getOutputStream();
		  out.write(param.getBytes());
		  out.flush();
		
		  out.close();
		  BufferedReader br  = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
		  //BufferedReader br  = new BufferedReader(new InputStreamReader(aURL.openStream()));
		  while((line = br.readLine()) != null){
		    html.append(line);
		  }
	
	  aURL = null;
	  urlCon = null;
	  br.close();
	}catch(MalformedURLException e){
	  e.printStackTrace();
	  Log.writeExpt("CRONLOG", "MalformedURLException : "+e );
	  // 프로세스 종료
	  //System.exit(1);
	}catch(IOException e) {
	  e.printStackTrace();
	  Log.writeExpt("CRONLOG", "IOException :"+ e );
	  // 프로세스 종료
	  //System.exit(1);
	    }
	    return html.toString();
	  }
	  
	  
	  /**
	   * sms발송시 저장 MAL_TYPE 성공여부저장 어떤 용도의 컬럼인지 몰라 성공여부로 사용 하겠음 : 0:실패 1:성공
	   * @param metaBean
	   * @param arBean
	   * @param mal_type
	   */
	  public static void EmailAlimiLog(MetaBean metaBean, AlimiReceiverBean arBean, String mal_type, String mtPno , AlimiSettingBean asBean , String msg){
		  PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    StringBuffer sb = null;
		    try{
		      sb = new StringBuffer();
		
		      sb.append("INSERT INTO MALIMI_LOG (\n");
			  sb.append("       SG_SEQ\n");
			  sb.append("     , SD_NAME\n");
			  sb.append("     , SD_MENU\n");
			  sb.append("     , MT_TITLE\n");
			  sb.append("     , MT_DATE\n");
			  sb.append("     , MT_URL\n");
			  sb.append("     , MT_NO\n");
			  sb.append("     , MAL_SEND_DATE\n");
			  sb.append("     , MT_CONTENT\n");
			  sb.append("     , AB_SEQ\n");
			  sb.append("     , AB_MOBILE\n");
			  sb.append("     , AB_EMAIL\n");
			  sb.append("     , AB_NAME\n");
			  sb.append("     , MAL_TYPE\n");
			  sb.append("     , S_SEQ\n");
			  sb.append("     , MT_PNO\n");
			  sb.append("     , AS_SEQ\n");
			  sb.append("     , AS_TITLE\n");
			  sb.append("     , SEND_MESSAGE\n");
			  sb.append("     , AS_TYPE )\n");
			  sb.append(" VALUES ( \n");
			  sb.append("  ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , NOW()	\n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? \n");
			  sb.append(" , ? )\n");
		      pstmt = dbconn.createPStatement(sb.toString());
		      pstmt.setString(1, metaBean.getSg_seq());
		      pstmt.setString(2, metaBean.getMd_site_name());
		      pstmt.setString(3, metaBean.getMd_site_menu());
		      pstmt.setString(4, metaBean.getMd_title());
		      pstmt.setString(5, metaBean.getMd_date());
		      pstmt.setString(6, metaBean.getMd_url());
		      pstmt.setString(7, metaBean.getMd_seq());
		      if(metaBean.getMd_content().length() > 500){
		        metaBean.setMd_content(metaBean.getMd_content().substring(0, 500));
		      }else{
		        metaBean.setMd_content(metaBean.getMd_content().substring(0, metaBean.getMd_content().length()));
		      }
		      pstmt.setString(8, metaBean.getMd_content());
		      pstmt.setString(9, arBean.getAb_seq());
		      pstmt.setString(10, arBean.getAb_mobile());
		      pstmt.setString(11, arBean.getAb_mail());
		      pstmt.setString(12, arBean.getAb_name());
		      pstmt.setString(13, mal_type);
		      pstmt.setString(14, metaBean.getS_seq());
		      pstmt.setString(15, mtPno);
		      pstmt.setString(16, asBean.getAs_seq());
		      pstmt.setString(17, asBean.getAs_title());
		      if(msg.length() > 60){
		        	msg = msg.substring(0, 60);
		      }
		      pstmt.setString(18, msg);
		      pstmt.setString(19, asBean.getAs_type());
		      pstmt.executeUpdate();
		
		    }catch(SQLException ex){
		      ex.printStackTrace();
		      Log.writeExpt(ex, sb.toString());
		    }catch(Exception ex){
		      ex.printStackTrace();
		      Log.writeExpt(ex);
		    }finally{
		      sb = null;
		      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		    }
	  }
	
	  /**
	   * sms발송시 저장 MAL_TYPE 성공여부저장 어떤 용도의 컬럼인지 몰라 성공여부로 사용 하겠음 : 0:실패 1:성공
	   * @param metaBean
	   * @param arBean
	   * @param mal_type
	   */
	  public static void RegAlimiLog(MetaBean metaBean, ArrayList receiverList, String mal_type, String mtPno, AlimiSettingBean asBean, String msg){
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    try{
	      sb = new StringBuffer();
	
	      AlimiReceiverBean arBean = null;
	      
	      for(int i = 0; i < receiverList.size(); i++) {
	        arBean = (AlimiReceiverBean)receiverList.get(i);
	        sb.setLength(0);
		    sb.append("INSERT INTO MALIMI_LOG (\n");
		    sb.append("       SG_SEQ\n");
			sb.append("     , SD_NAME\n");
			sb.append("     , SD_MENU\n");
			sb.append("     , MT_TITLE\n");
			sb.append("     , MT_DATE\n");
			sb.append("     , MT_URL\n");
			sb.append("     , MT_NO\n");
			sb.append("     , MAL_SEND_DATE\n");
			sb.append("     , MT_CONTENT\n");
			sb.append("     , AB_SEQ\n");
			sb.append("     , AB_MOBILE\n");
			sb.append("     , AB_NAME\n");
			sb.append("     , MAL_TYPE\n");
			sb.append("     , S_SEQ\n");
			sb.append("     , MT_PNO\n");
			sb.append("     , AS_SEQ\n");
			sb.append("     , AS_TITLE\n");
			sb.append("     , SEND_MESSAGE \n");
			sb.append("     , AS_TYPE )\n");
			sb.append("VALUES ( \n");
			sb.append("  ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , NOW()	\n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? \n");
			sb.append(" , ? )\n");
			System.out.println("query : " + sb.toString());
	        pstmt = dbconn.createPStatement(sb.toString());
	        pstmt.setString(1, metaBean.getSg_seq());
	        pstmt.setString(2, metaBean.getMd_site_name());
	        pstmt.setString(3, metaBean.getMd_site_menu());
	        pstmt.setString(4, metaBean.getMd_title());
	        pstmt.setString(5, metaBean.getMd_date());
	        pstmt.setString(6, metaBean.getMd_url());
	        pstmt.setString(7, metaBean.getMd_seq());
	        if(metaBean.getMd_content().length() > 500){
	          metaBean.setMd_content(metaBean.getMd_content().substring(0, 500));
	        }else{
	          metaBean.setMd_content(metaBean.getMd_content().substring(0, metaBean.getMd_content().length()));
	        }
	        pstmt.setString(8, metaBean.getMd_content());
	        pstmt.setString(9, arBean.getAb_seq());
	        pstmt.setString(10, arBean.getAb_mobile());
	        pstmt.setString(11, arBean.getAb_name());
	        pstmt.setString(12, mal_type);
	        pstmt.setString(13, metaBean.getS_seq());
	        pstmt.setString(14, mtPno);
	        pstmt.setString(15, asBean.getAs_seq());
	        pstmt.setString(16, asBean.getAs_title());
	        if(msg.length() > 60){
	        	msg = msg.substring(0, 60);
	        }
	        pstmt.setString(17, msg);
	        pstmt.setString(18, asBean.getAs_type());
	        pstmt.executeUpdate();
	      }
	
	    }catch(SQLException ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex, sb.toString());
	    }catch(Exception ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    }finally{
	      sb = null;
	      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	  }
	  
	  /**
	   * sms발송시 저장 MAL_TYPE 성공여부저장 어떤 용도의 컬럼인지 몰라 성공여부로 사용 하겠음 : 0:실패 1:성공
	   * @param metaBean
	   * @param arBean
	   * @param mal_type
	   */
	  public static void RegAlimiLog(MetaBean metaBean, AlimiReceiverBean arBean, String mal_type, String mtPno, AlimiSettingBean asBean, String msg){
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    try{
	      sb = new StringBuffer();
	
	      sb.append("INSERT INTO MALIMI_LOG (\n");
		  sb.append("       SG_SEQ\n");
		  sb.append("     , SD_NAME\n");
		  sb.append("     , SD_MENU\n");
		  sb.append("     , MT_TITLE\n");
		  sb.append("     , MT_DATE\n");
		  sb.append("     , MT_URL\n");
		  sb.append("     , MT_NO\n");
		  sb.append("     , MAL_SEND_DATE\n");
		  sb.append("     , MT_CONTENT\n");
		  sb.append("     , AB_SEQ\n");
		  sb.append("     , AB_MOBILE\n");
		  sb.append("     , AB_NAME\n");
		  sb.append("     , MAL_TYPE\n");
		  sb.append("     , S_SEQ\n");
		  sb.append("     , MT_PNO\n");
		  sb.append("     , AS_SEQ\n");
		  sb.append("     , AS_TITLE\n");
		  sb.append("     , SEND_MESSAGE\n");
		  sb.append("     , AS_TYPE )\n");
		  sb.append(" VALUES ( \n");
		  sb.append("  ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , NOW()	\n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? \n");
		  sb.append(" , ? )\n");
	      pstmt = dbconn.createPStatement(sb.toString());
	      pstmt.setString(1, metaBean.getSg_seq());
	      pstmt.setString(2, metaBean.getMd_site_name());
	      pstmt.setString(3, metaBean.getMd_site_menu());
	      pstmt.setString(4, metaBean.getMd_title());
	      pstmt.setString(5, metaBean.getMd_date());
	      pstmt.setString(6, metaBean.getMd_url());
	      pstmt.setString(7, metaBean.getMd_seq());
	      if(metaBean.getMd_content().length() > 500){
	        metaBean.setMd_content(metaBean.getMd_content().substring(0, 500));
	      }else{
	        metaBean.setMd_content(metaBean.getMd_content().substring(0, metaBean.getMd_content().length()));
	      }
	      pstmt.setString(8, metaBean.getMd_content());
	      pstmt.setString(9, arBean.getAb_seq());
	      pstmt.setString(10, arBean.getAb_mobile());
	      pstmt.setString(11, arBean.getAb_name());
	      pstmt.setString(12, mal_type);
	      pstmt.setString(13, metaBean.getS_seq());
	      pstmt.setString(14, mtPno);
	      pstmt.setString(15, asBean.getAs_seq());
	      pstmt.setString(16, asBean.getAs_title());
	      if(msg.length() > 60){
	        	msg = msg.substring(0, 60);
	      }
	      pstmt.setString(17, msg);
	      pstmt.setString(18, asBean.getAs_type());
	      pstmt.executeUpdate();
	
	    }catch(SQLException ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex, sb.toString());
	    }catch(Exception ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    }finally{
	      sb = null;
	      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	  }
	
	  public static String en(String ko){
	    String new_str = null;
	    try{        
	      new_str  = new String(ko.getBytes("KSC5601"), "8859_1");
	    } catch(UnsupportedEncodingException ex) {ex.printStackTrace(); }
	    return new_str;
	  }
	
	  public static String ko(String en){
	    String new_str = null;
	    try{              
	      try{
	        new_str  = new String(en.getBytes("8859_1"), "KSC5601");                
	  } catch(UnsupportedEncodingException ex) {ex.printStackTrace(); }
	}catch(NullPointerException e)
	{	System.out.println("Null  " + en);
	    new_str = en;
	    }
	    return new_str;
	  }
	
	  static String getMaxMtno(){
	    String result = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    try{
	      sb = new StringBuffer();
	      //sb.append(" SELECT MD_SEQ FROM META ORDER BY MD_SEQ DESC LIMIT 1 \n");
		  sb.append(" SELECT I.MD_SEQ FROM META M INNER JOIN IDX I ON M.MD_SEQ = I.MD_SEQ ORDER BY I.MD_SEQ DESC LIMIT 1 \n");
		  System.out.print(sb.toString());
		  pstmt = dbconn.createPStatement(sb.toString());
		  rs = pstmt.executeQuery();
		  if(rs.next()){
		    result = rs.getString("MD_SEQ");
	      }
	
	    }catch(SQLException ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex, sb.toString());
	    }catch(Exception ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    }finally{
	      sb = null;
	      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	    return result;
	  }
	
	  static boolean updateAlimiSet(String asSeq, String lastNum){
	    boolean result = false;
	
	    PreparedStatement 	pstmt = null;
	    StringBuffer sb = null;
	
	    try{
	      sb = new StringBuffer();
	      sb.append(" UPDATE ALIMI_SETTING SET AS_LAST_SENDTIME= now(), AS_LAST_NUM='"+lastNum+"' WHERE AS_SEQ="+asSeq+" \n");
	
	      pstmt = dbconn.createPStatement(sb.toString());
	      if(pstmt.executeUpdate() > 0){
	        result = true;
	      }
	    } catch(SQLException ex) {
	      ex.printStackTrace();
	      Log.writeExpt(ex, sb.toString() );
	    } catch(Exception ex) {
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    } finally {
	      sb = null;
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	    return result;
	  }
	
	  static ArrayList getAlimiData(String sno, String eno, String xp, String yp, String sg_seq, String sd_gsn, String mt_type){
	    ArrayList result = new ArrayList();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	
	    try{
	      sb = new StringBuffer();
	      sb.append("## 알리미 정보 (email, sms) \n");
	      sb.append("SELECT	\n");	                               
		  sb.append("MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE ,B.MD_DATE\n");
		  sb.append(",B.MD_SAME_COUNT ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, ISSUE_CHECK, I_DELDATE, M_SEQ, C.K_VALUE, D.MD_CONTENT\n");
		  sb.append("FROM IDX A, META B, KEYWORD C, DATA D\n");
		  sb.append("WHERE A.MD_SEQ > "+sno+" AND A.MD_SEQ <= "+eno+"\n");
		  sb.append("AND A.MD_SEQ = B.MD_SEQ\n");
		  if(!xp.equals(""))sb.append("AND A.K_XP   IN ( "+xp+" )\n");
		  if(!yp.equals(""))sb.append("AND A.K_YP   IN ( "+yp+" )\n");
		  if(!sg_seq.equals("") && !sd_gsn.equals("")){
		    sb.append("AND (A.SG_SEQ IN ("+sg_seq+") OR B.S_SEQ IN ("+sd_gsn+"))\n");
		  }else if(!sg_seq.equals("") && sd_gsn.equals("")){
		    sb.append("AND A.SG_SEQ IN ("+sg_seq+")\n");
		  }else if(sg_seq.equals("") && !sd_gsn.equals("")){
		    sb.append("AND B.S_SEQ IN ("+sd_gsn+")\n");
		  }
		  if(!mt_type.equals(""))sb.append("AND B.MD_TYPE IN ("+mt_type+")\n");
		  //sb.append("AND B.MD_SEQ = B.MD_PSEQ\n");
		  sb.append("AND B.MD_SEQ = D.MD_SEQ\n");
		  sb.append("AND (A.I_STATUS = 'N' AND A.M_SEQ <> 2)\n");
		  sb.append("AND A.K_XP = C.K_XP AND A.K_YP = C.K_YP AND A.K_ZP = C.K_ZP\n");
		  sb.append("GROUP BY B.MD_PSEQ\n");
		  sb.append("ORDER BY B.MD_SEQ DESC\n");
		  Log.crond(sb.toString());
		  pstmt = dbconn.createPStatement(sb.toString());
		  rs = pstmt.executeQuery();
		  while(rs.next()){
		    //Log.crond("MD_SEQ : "+rs.getString("MD_SEQ"));
		
		    MetaBean mBean  = new MetaBean();
		    mBean.setMd_seq(rs.getString("MD_SEQ"));
		    mBean.setS_seq(rs.getString("S_SEQ"));
		    mBean.setSg_seq(rs.getString("SG_SEQ"));
		    mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
		    mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
		    mBean.setMd_type(rs.getString("MD_TYPE"));
		    mBean.setMd_date(rs.getString("MD_DATE"));
		    mBean.setMd_pseq(rs.getString("MD_PSEQ"));
		    mBean.setMd_title(rs.getString("MD_TITLE"));
		    mBean.setMd_url(rs.getString("MD_URL"));
		    mBean.setMd_content(rs.getString("MD_CONTENT"));
		    mBean.setK_value(rs.getString("K_VALUE"));
		    mBean.setMd_same_count(rs.getString("MD_SAME_COUNT")); 
		    mBean.setM_seq(rs.getString("M_SEQ"));
		    mBean.setI_deldate(rs.getString("I_DELDATE"));
	        result.add(mBean);	
	      }
	
	    }catch(SQLException ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex, sb.toString());
	    }catch(Exception ex){
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    }finally{
	      sb = null;
	      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	    }
	
	    return result;
	  }
	
	
	
	  public static boolean sendUnitAlimi( ArrayList arrAlimiInfo, String forMail,  ArrayList arrAlimiReceiver , AlimiSettingBean asBean){
	    boolean result = false;
	    ConfigUtil cu = new ConfigUtil();
	    StringUtil su = new StringUtil();
	    System.out.println("sender mail : "+forMail);
	    try{
	      String siteUrl = cu.getConfig("URL");
		  String siteTitle = new String( cu.getConfig("TITLE").getBytes("8859_1"), "KSC5601" );
		  String senderMail = cu.getConfig("SENDER");
		
		  String receiverEmail = null;
		  String receiverName = null;
		  String mailTitle = null;
		  String baseUrl = siteUrl+"alimi_form/";
		  StringBuffer sb = null;
		  MetaBean metaBean = null;
		  
		  //삼성화재 이메일 준법감시 = 본문내용 삭제해달라는 요청 - 2015-02-16
		  //삼성화재 이메일 준법감시 설정관리 AS_SEQ번호는 31
		  String as_seq = "31";
	
		  for( int i=0 ; i<arrAlimiInfo.size(); i++ ){
		
		    metaBean = (MetaBean)arrAlimiInfo.get(i);
		    sb = new StringBuffer();
		
		    sb.append(" <html> \n");
		    sb.append(" <head> \n");
		    sb.append(" <title>"+siteTitle+"</title> \n");
		    sb.append(" <meta http-equiv='Content-Type' content='text/html; charset=euc-kr'> \n");
		    sb.append(" <style type='text/css'> \n");
		    sb.append(" <!-- \n");
		    sb.append(" body { \n");
		    sb.append(" 	margin-left: 10px; \n");
		    sb.append(" 	margin-top: 10px; \n");
		    sb.append(" 	margin-right: 0px; \n");
		    sb.append(" 	margin-bottom: 10px; \n");
		    sb.append(" } \n");
		    sb.append(" A:link { \n");
		    sb.append(" 	COLOR: #333333; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A:visited { \n");
		    sb.append(" 	COLOR: #000000; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A:hover { \n");
		    sb.append(" 	COLOR: #ff9900; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A:active { \n");
		    sb.append(" 	TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:link { \n");
		    sb.append(" 	COLOR: #333333; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:visited { \n");
		    sb.append(" 	COLOR: #aaaaaa; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:hover { \n");
		    sb.append(" 	COLOR: #ff9900; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:active { \n");
		    sb.append(" 	TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" .menu_black { \n");
		    sb.append(" 	FONT-SIZE: 12px; COLOR: #000000; LINE-HEIGHT: 16px; FONT-FAMILY: 'gulim' 'Arial' \n");
		    sb.append(" } \n");
		    sb.append(" .menu_gray1 { \n");
		    sb.append(" 	FONT-SIZE: 12px; COLOR: #a0a0a0; LINE-HEIGHT: 16px; FONT-FAMILY: 'gulim' 'Arial' \n");
		    sb.append(" } \n");
		    sb.append(" --> \n");
		    sb.append(" </style></head> \n");
		    sb.append("  \n");
		    sb.append(" <body> \n");
		    sb.append(" <table width='690' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("   <tr> \n");
		    sb.append("     <td><img src='"+baseUrl+"images/mail_top.gif' width='690' height='91'></td> \n");
		    sb.append("   </tr> \n");
		    sb.append(" </table> \n");
		    sb.append(" <table width='690' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("   <tr> \n");
		    sb.append("     <td background='"+baseUrl+"images/bg_line.gif' style='padding:20px 0px 30px 0px'><table width='660' border='0' align='center' cellpadding='0' cellspacing='0'> \n");
		    sb.append("       <tr> \n");
		    sb.append("         <td><table width='660' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("             <tr> \n");
		    sb.append("               <td height='2' bgcolor='94CDCD'></td> \n");
		    sb.append("             </tr> \n");
		    sb.append("             <tr> \n");
		    sb.append("               <td><table width='660' border='0' cellpadding='0' cellspacing='1' bgcolor='D9D9D9'> \n");
		    sb.append("                 <tr> \n");
		    sb.append("                   <td width='128' height='28' align='center' background='"+baseUrl+"images/bbg_02.gif'class='menu_black'><strong>제목</strong></td> \n");
		    sb.append("                   <td colspan='3' bgcolor='#FFFFFF' class='menu_gray1' style='padding:0px 0px 0px 10px'><a href='"+metaBean.getMd_url()+"' target='_blank'>"+su.cutString( metaBean.getMd_title(), 27, "...").replaceAll("\\\\n","")+"</td> \n");
		    sb.append("                   </tr> \n");
		    sb.append("                 <tr> \n");
		    sb.append("                   <td width='128' height='28' align='center' background='"+baseUrl+"images/bbg_02.gif' class='menu_black'><strong>출처</strong></td> \n");
		    sb.append("                   <td width='242' bgcolor='#FFFFFF' class='menu_gray1' style='padding:0px 0px 0px 10px'>"+su.cutString( metaBean.getMd_site_name(), 28, "" )+"</td> \n");
		    sb.append("                   <td width='128' height='28' align='center' background='"+baseUrl+"images/bbg_02.gif' class='menu_black'><strong>수집일시</strong></td> \n");
		    sb.append("                   <td width='157' bgcolor='#FFFFFF' class='menu_gray1' style='padding:0px 0px 0px 10px'>"+metaBean.getMd_date()+"</td> \n");
		    sb.append("                 </tr> \n");
		    if(metaBean.getMd_content().length()>0)
		    {
		    	//as_seq = 31 일 경우 내용을 포함시키지 않음.
		    	if(!as_seq.equals(asBean.getAs_seq())){
			      sb.append("                 <tr> \n");
			      sb.append("                   <td colspan='4' bgcolor='#FFFFFF' class='menu_black' style='padding:15px 15px 15px 15px'>"+su.cutKey(metaBean.getMd_content(), metaBean.getK_value(), 200, "blue")+"</td> \n");
			      sb.append("                 </tr> \n");
		    	}
		    }
		
		    sb.append("               </table></td> \n");
		    sb.append("             </tr> \n");
		    sb.append("             <tr> \n");
		    sb.append("               <td height='1' bgcolor='CCCCCC'></td> \n");
		    sb.append("             </tr> \n");
		    sb.append("           </table></td> \n");
		    sb.append("       </tr> \n");
		    sb.append("     </table></td> \n");
		    sb.append("   </tr> \n");
		    sb.append(" </table> \n");
		    sb.append(" <table width='690' border='0' cellspacing='0' cellpadding='0'>                                      \n");
		    sb.append("   <tr>                                                                                              \n");
		    sb.append("     <td><img src='"+baseUrl+"images/mail_btm.gif' width='690' height='50' border='0' usemap='#Map'></td> \n");
		    sb.append("   </tr>                                                                                             \n");
		    sb.append(" </table>                                                                                            \n");
		    sb.append(" <map name='Map'>                                                                                    \n");
		    sb.append(" <area shape='rect' coords='13,3,193,37' href='"+siteUrl+"' target='_blank'>                            \n");
		    sb.append(" </map>  \n");
		    sb.append(" </body> \n");
		    sb.append(" </html> \n");
	
			    for( int j=0 ; j<arrAlimiReceiver.size() ; j++ )
			    {
			      AlimiReceiverBean arBean = new AlimiReceiverBean();
			      arBean = (AlimiReceiverBean)arrAlimiReceiver.get(j);
			
			      receiverEmail = arBean.getAb_mail();
			      mailTitle = "["+su.cutString( metaBean.getMd_site_name(), 8, "" )+"] "+su.cutString( metaBean.getMd_title(), 27, "...").replaceAll("\\\\n","");
		
		          if(gMail.sendmessage(receiverEmail, forMail, mailTitle, sb.toString(), true)){
		        	  EmailAlimiLog(metaBean, arBean, "1", metaBean.getMd_seq(), asBean, mailTitle);
		          }else{
		        	  EmailAlimiLog(metaBean, arBean, "0", metaBean.getMd_seq(), asBean, mailTitle);
		          }
		        }
		
			    result = true;
		   }
	    } catch(Exception ex) {
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    }
	
	    return result;
	  }
	
	
	  public static boolean sendListAlimi( ArrayList arrAlimiInfo, String forMail,  ArrayList arrAlimiReceiver, String mailTitle ){
	    boolean result = false;
	    ConfigUtil cu = new ConfigUtil();
	    StringUtil su = new StringUtil();
	
	    try{
	      String siteUrl = cu.getConfig("URL");
		  String siteTitle = new String( cu.getConfig("TITLE").getBytes("8859_1"), "KSC5601" );
		  String senderMail = cu.getConfig("SENDER");
		
		  String receiverEmail = null;
		  String receiverName = null;
		  String baseUrl = siteUrl+"alimi_form/";
		  StringBuffer sb = null;
		  MetaBean metaBean = null;
		
		  if( arrAlimiInfo.size()>0 ){
		    sb = new StringBuffer();
		
		    sb.append(" <html> \n");
		    sb.append(" <head> \n");
		    sb.append(" <title>"+siteTitle+"</title> \n");
		    sb.append(" <meta http-equiv='Content-Type' content='text/html; charset=euc-kr'> \n");
		    sb.append(" <style type='text/css'> \n");
		    sb.append(" <!-- \n");
		    sb.append(" body { \n");
		    sb.append(" 	margin-left: 10px; \n");
		    sb.append(" 	margin-top: 10px; \n");
		    sb.append(" 	margin-right: 0px; \n");
		    sb.append(" 	margin-bottom: 10px; \n");
		    sb.append(" } \n");
		    sb.append(" A:link { \n");
		    sb.append(" 	COLOR: #333333; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A:visited { \n");
		    sb.append(" 	COLOR: #000000; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A:hover { \n");
		    sb.append(" 	COLOR: #ff9900; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A:active { \n");
		    sb.append(" 	TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:link { \n");
		    sb.append(" 	COLOR: #333333; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:visited { \n");
		    sb.append(" 	COLOR: #aaaaaa; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:hover { \n");
		    sb.append(" 	COLOR: #ff9900; TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" A.sch_title:active { \n");
		    sb.append(" 	TEXT-DECORATION: none \n");
		    sb.append(" } \n");
		    sb.append(" .menu_black { \n");
		    sb.append(" 	FONT-SIZE: 12px; COLOR: #000000; LINE-HEIGHT: 16px; FONT-FAMILY: 'gulim' 'Arial' \n");
		    sb.append(" } \n");
		    sb.append(" .menu_gray1 { \n");
		    sb.append(" 	FONT-SIZE: 12px; COLOR: #a0a0a0; LINE-HEIGHT: 16px; FONT-FAMILY: 'gulim' 'Arial' \n");
		    sb.append(" } \n");
		    sb.append(" --> \n");
		    sb.append(" </style></head> \n");
		    sb.append("  \n");
		    sb.append(" <body> \n");
		    sb.append(" <table width='690' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("   <tr> \n");
		    sb.append("     <td><img src='"+baseUrl+"images/mail_top.gif' width='690' height='91'></td> \n");
		    sb.append("   </tr> \n");
		    sb.append(" </table> \n");
		    sb.append(" <table width='690' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("   <tr> \n");
		    sb.append("     <td background='"+baseUrl+"images/bg_line.gif' style='padding:20px 0px 30px 0px'><table width='660' border='0' align='center' cellpadding='0' cellspacing='0'> \n");
		    sb.append("       <tr> \n");
		    sb.append("         <td><table width='660' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("           <tr> \n");
		    sb.append("             <td width='590' class='menu_black' style='padding:0px 0px 0px 5px'></td> \n");
		    sb.append("             <td width='70' align='right' class='menu_black' style='padding:0px 5px 0px 0px'>총 <strong>"+arrAlimiInfo.size()+"</strong> 건</td> \n");
		    sb.append("             </tr> \n");
		    sb.append("         </table></td> \n");
		    sb.append("       </tr> \n");
		    sb.append("       <tr> \n");
		    sb.append("         <td height='5'></td> \n");
		    sb.append("       </tr> \n");
		    sb.append("       <tr> \n");
		    sb.append("         <td><table width='660' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("           <tr> \n");
		    sb.append("             <td height='2' bgcolor='94CDCD'></td> \n");
		    sb.append("           </tr> \n");
		    sb.append("           <tr> \n");
		    sb.append("             <td><table width='660' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("               <tr> \n");
		    sb.append("                 <td><table width='660' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("                   <tr> \n");
		    sb.append("                     <td height='1' bgcolor='CCCCCC'></td> \n");
		    sb.append("                   </tr> \n");
		    sb.append("                   <tr> \n");
		    sb.append("                     <td height='28' background='"+baseUrl+"images/bbg_02.gif'><table width='660' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("                       <tr> \n");
		    sb.append("                         <td width='438' align='center' class='menu_black'><strong>제목</strong></td> \n");
		    sb.append("                         <td width='1'><img src='"+baseUrl+"images/line_02.gif' width='1' height='12'></td> \n");
		    sb.append("                         <td width='104' align='center' class='menu_black'><strong>출처</strong></td> \n");
		    sb.append("                         <td width='1'><img src='"+baseUrl+"images/line_02.gif' width='1' height='12'></td> \n");
		    sb.append("                         <td width='116' align='center' class='menu_black'><strong>수집일시</strong></td> \n");
		    sb.append("                       </tr> \n");
		    sb.append("                     </table></td> \n");
		    sb.append("                   </tr> \n");
		    sb.append("                   <tr> \n");
		    sb.append("                     <td height='1' bgcolor='CCCCCC'></td> \n");
		    sb.append("                   </tr> \n");
		    sb.append("                 </table></td> \n");
		    sb.append("               </tr> \n");
		    sb.append("               <tr> \n");
		    sb.append("                 <td height='5'></td> \n");
		    sb.append("               </tr> \n");
		    sb.append("               <tr> \n");
		    sb.append("                 <td><table width='660' border='0' cellspacing='0' cellpadding='0'> \n");
		    sb.append("                 <col width='438'><col width='105'><col width='117'> \n");
	
		    for( int i=0 ; i<arrAlimiInfo.size() ; i++ )
		    {
		      metaBean = (MetaBean)arrAlimiInfo.get(i);
		
		      if( i != 0 )
		      {
		        sb.append(" 				  <tr> \n");
		        sb.append("                     <td colspan='3'><img src='"+baseUrl+"images/dotline.gif' width='660' height='1'></td> \n");
		        sb.append("                   </tr> \n");
		
		      }
		
		      sb.append("                   <tr height='28' align='center'> \n");
		      sb.append("                     <td align='left' class='menu_gray1' style='padding:0px 0px 0px 0px'><a href='"+metaBean.getMd_url()+"' target='_blank'>"+su.cutString( metaBean.getMd_title(), 27, "...")+"</a></td> \n");
		      sb.append("                     <td class='menu_gray1'>"+su.cutString( metaBean.getMd_site_name(), 8, "" )+"</td> \n");
		      sb.append("                     <td class='menu_gray1'>"+metaBean.getMd_date().substring(5, 16)+"</td> \n");
		      sb.append("                   </tr> \n");
		    }
		    sb.append("                 </table></td> \n");
		    sb.append("               </tr> \n");
		    sb.append("             </table></td> \n");
		    sb.append("           </tr> \n");
		    sb.append("           <tr> \n");
		    sb.append("             <td height='2' bgcolor='CCCCCC'></td> \n");
		    sb.append("           </tr> \n");
		    sb.append("         </table></td> \n");
		    sb.append("       </tr> \n");
		    sb.append("     </table></td> \n");
		    sb.append("   </tr> \n");
		    sb.append(" </table> \n");
		    sb.append(" <table width='690' border='0' cellspacing='0' cellpadding='0'>                                      \n");
		    sb.append("   <tr>                                                                                              \n");
		    sb.append("     <td><img src='"+baseUrl+"images/mail_btm.gif' width='690' height='50' border='0' usemap='#Map'></td> \n");
		    sb.append("   </tr>                                                                                             \n");
		    sb.append(" </table>                                                                                            \n");
		    sb.append(" <map name='Map'>                                                                                    \n");
		    sb.append(" <area shape='rect' coords='13,3,193,37' href='"+siteUrl+"' target='_blank'>                            \n");
		    sb.append(" </map>  \n");
		    sb.append(" </body> \n");
		    sb.append(" </html> \n");
		
		        for( int j=0 ; j<arrAlimiReceiver.size() ; j++ )
		        {
		          AlimiReceiverBean arBean = new AlimiReceiverBean();
		          arBean = (AlimiReceiverBean)arrAlimiReceiver.get(j);
		
		          receiverEmail = arBean.getAb_mail();
		          gMail.sendmessage(receiverEmail, forMail, mailTitle, sb.toString(), true);
		        }
		
		        result = true;
		      }
	    } catch(Exception ex) {
	      ex.printStackTrace();
	      Log.writeExpt(ex);
	    }
	
	    return result;
	  }
	
	  public static ArrayList getAlimiSetList(int nowPage, int rowCnt, String Pas_seq, String subInfoIn)
	  {
	    Statement stmt  = null;
	    ResultSet rs    = null;
	    StringBuffer sb = null;
	
	    ArrayList arrAlimiSetList = new ArrayList();
	    ArrayList arrReceiverList = new ArrayList();
	    AlimiSettingBean asBean = new AlimiSettingBean();
	    AlimiReceiverBean arBean = new AlimiReceiverBean();
	    String as_seq = null;
	
	    int liststart = 0;
	    int listend = 0;
	    int whereCnt = 0;   	
	
	    try
	    {
	      stmt = dbconn.createStatement();
	
	
	      sb = new StringBuffer();
	      whereCnt =0;
	
	      sb.append("SELECT AS_SEQ                                                      \n");                 
	  sb.append("     , AS_TITLE                                                    \n");            
	  sb.append("     , AS_CHK                                                      \n");       
	  sb.append("     , AS_TYPE                                                     \n");  
	  sb.append("     , AS_INFOTYPE                                                 \n"); 
	  sb.append("     , K_XPS                                                       \n");
	  sb.append("     , SG_SEQS                                                     \n");      
	  sb.append("     , SD_GSNS                                                     \n");
	  sb.append("     , MT_TYPES                                                    \n");
	  sb.append("     , AS_SMS_KEY                                                  \n");
	  sb.append("     , AS_SMS_EXKEY                                                \n");
	  sb.append("     , AS_SMS_TIME                                                 \n");
	  sb.append("     , AS_INTERVAL                                                 \n");
	  sb.append("     , AS_LAST_SENDTIME                                            \n");
	  sb.append("     , AS_SMS_DAY                                                  \n");
	  sb.append("     , AS_SMS_STIME                                                \n");
	  sb.append("     , AS_SMS_ETIME                                                \n");
	  sb.append("     , AS_SAME_CNT                                                 \n");
	  sb.append("     , K_YPS                                                 \n");
	  sb.append("     , AS_LAST_NUM                                            \n");
	  sb.append("     , CASE WHEN (NOW() >= DATE_ADD(AS_LAST_SENDTIME, INTERVAL+AS_INTERVAL HOUR_MINUTE)) THEN 'Y' ELSE 'N' END AS SENDCHK                                            \n");
	  sb.append("     , AS_SAME_PERCENT                                             \n");
	  sb.append("  FROM ALIMI_SETTING                                               \n");
	
	
	  //System.out.println(sb.toString());
	  Log.debug(sb.toString() );			
	  rs = stmt.executeQuery(sb.toString());
	
	  while(rs.next())
	  {
	    asBean = new AlimiSettingBean();
	    asBean.setAs_seq(rs.getString(1));
	    asBean.setAs_title(rs.getString(2));
	    asBean.setAs_chk(rs.getString(3));
	    asBean.setAs_type(rs.getString(4));
	    asBean.setAs_infotype(rs.getString(5));
	    asBean.setKg_xps(rs.getString(6));
	    asBean.setSg_seqs(rs.getString(7));
	    asBean.setSd_gsns(rs.getString(8));
	    asBean.setMt_types(rs.getString(9));
	    asBean.setAs_sms_key(rs.getString(10));
	    asBean.setAs_sms_exkey(rs.getString(11));
	    asBean.setAs_sms_time(rs.getString(12));
	    asBean.setAs_interval(rs.getString(13));
	    asBean.setAs_last_sendtime(rs.getString(14));
	    asBean.setAs_sms_day(rs.getString(15));
	    asBean.setAs_sms_stime(rs.getString(16));
	    asBean.setAs_sms_etime(rs.getString(17));
	    asBean.setAs_same_cnt(rs.getInt(18));
	    asBean.setKg_yps(rs.getString(19));
	    asBean.setAs_last_num(rs.getString(20));
	    asBean.setSendchk(rs.getString(21));
	    asBean.setAs_same_percent(rs.getInt(22));
	    arrAlimiSetList.add(asBean);
	  }
	
	  if(subInfoIn.equals("Y"))
	  {
	    rs = null;           
	    sb = new StringBuffer();
	    whereCnt =1;
	    as_seq ="";
	
	    sb.append("SELECT A.AS_SEQ	                    \n");                
	    sb.append("     , C.AB_SEQ                      \n");                         
	    sb.append("     , C.AB_NAME                     \n");                         
	    sb.append("     , C.AB_DEPT                     \n");                         
	    sb.append("     , C.AB_POSITION                 \n");                              
	    sb.append("     , C.AB_MOBILE                   \n");                         
	    sb.append("     , C.AB_MAIL                     \n");                     
	    sb.append("     , NULL AS K_XP                  \n");                           
	    sb.append("     , NULL AS SG_SEQ                \n");                           
	    sb.append("     , NULL AS MT_TYPE               \n");                           
	    sb.append("     , NULL AS AB_ISSUE_DEPT         \n");                           
	    sb.append("     , 0 AS AB_ISSUE_RECEIVECHK      \n");                        
	    sb.append("     , C.AB_REPORT_DAY_CHK           \n");                     
	    sb.append("     , C.AB_REPORT_WEEK_CHK          \n");                     
	    sb.append("     , NULL AS AB_SMS_LIMIT          \n");                            
	    sb.append("  FROM ALIMI_SETTING  A              \n");
	    sb.append("     , ALIMI_RECEIVER B              \n");
	    sb.append("     , ADDRESS_BOOK   C              \n");
	    sb.append(" WHERE A.AS_SEQ = B.AS_SEQ	        \n");                       
	    sb.append("   AND B.AB_SEQ = C.AB_SEQ           \n");
	
	    if(!Pas_seq.equals(""))
	    {
	      if(whereCnt==0)
	      {
	        sb.append("WHERE ");
	      }else{
	        sb.append("AND ");
	      }
	      sb.append("A.AS_SEQ = "+Pas_seq+" \n");
	      whereCnt++;
	    }
	    sb.append("ORDER BY A.AS_SEQ ASC	                          \n");
	
	    Log.debug(sb.toString() );				
	    rs = stmt.executeQuery(sb.toString());
	
	
	    while(rs.next())
	    {
	      if (!as_seq.equals(rs.getString(1))) {
	        //System.out.println("arrReceiverList:"+arrReceiverList.size());
	            arrAlimiSetList = addArrReceiber(arrAlimiSetList, as_seq, arrReceiverList);
	            arrReceiverList = new ArrayList();
	          }
	          as_seq = rs.getString(1);
	          arBean = new AlimiReceiverBean();
	          arBean.setAb_name( rs.getString(3));
	          arBean.setAb_dept( rs.getString(4));
	          arBean.setAb_pos( rs.getString(5));
	          arBean.setAb_mobile( rs.getString(6));
	          arBean.setAb_mail( rs.getString(7));
	          arBean.setK_xp( rs.getString(8));
	          arBean.setSg_seq( rs.getString(9));
	          arBean.setMt_type( rs.getString(10));
	          arBean.setAb_issue_dept( rs.getString(11));
	          arBean.setAb_issue_receivechk( rs.getString(12));
	          arBean.setAb_report_day_chk( rs.getString(13));
	          arBean.setAb_report_week_chk( rs.getString(14));
	          arBean.setAb_sms_limit( rs.getString(15));           	
	          arrReceiverList.add(arBean);
	        }
	        arrAlimiSetList = addArrReceiber(arrAlimiSetList, as_seq, arrReceiverList);
	      }
	
	    } catch (SQLException ex ) {
	      ex.printStackTrace();
	      Log.writeExpt(ex, ex.getMessage() );
	
	    } catch (Exception ex ) {
	      ex.printStackTrace();
	      Log.writeExpt(ex.getMessage());
	
	    } finally {
	      sb = null;
	      try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	      try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	    }   
	
	    return arrAlimiSetList;
	  }
	
	  public static ArrayList addArrReceiber(ArrayList paArrInsert, String as_seq, ArrayList paArrData){
	    int i=0;
	    AlimiSettingBean asBean = new AlimiSettingBean();
	    for(i=0; i<paArrInsert.size(); i++) {
	      asBean = new AlimiSettingBean();
	      asBean = (AlimiSettingBean) paArrInsert.get(i);
	      if (asBean.getAs_seq().equals(as_seq)) {
	        asBean.setArrReceiver(paArrData);
	        paArrInsert.set(i, asBean);
	      }
	    }
	    return paArrInsert;
	  }
	  
	  public static String getSenderMail(String as_seq){
		  	String str =  "";
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    StringBuffer sb = null;
		
		    try{
		      sb = new StringBuffer();
		      sb.append("## 알리미 설정 - 발송 메일 가져오기 \n");
		      sb.append("SELECT	GM_MAIL \n");	                               
			  sb.append("FROM GMAIL_GROUP \n");
			  sb.append("WHERE AS_SEQ = "+as_seq+" \n");
			  sb.append("LIMIT 1 \n");
			  Log.crond(sb.toString());
			  pstmt = dbconn.createPStatement(sb.toString());
			  rs = pstmt.executeQuery();
			  if(rs.next()){
				  str = rs.getString("GM_MAIL");
		      }
		
		    }catch(SQLException ex){
		      ex.printStackTrace();
		      Log.writeExpt(ex, sb.toString());
		    }catch(Exception ex){
		      ex.printStackTrace();
		      Log.writeExpt(ex);
		    }finally{
		      sb = null;
		      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		      if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		    }
		    
		  return str;
	  }

}