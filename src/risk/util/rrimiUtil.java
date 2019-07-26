package risk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;

/*
 * TODO 알리미 고객사 이름 교체 risk.properties 고객사 이름을 유니코드로 저장 
 * transMember.put("organizationName", URLEncoder.encode(cu.getConfig("COMPANY"), "UTF-8"));
 */
public class rrimiUtil {
	
	/**
	 * sendRRIMI 푸쉬알리미(R-rimi)전송
	 * 
	 * @param md_seq		문서번호
	 * @param phoneNums		콤마로 구분된 수신자전화번호
	 * @param dateTime		수집시간
	 * @param title			제목
	 * @param url			url
	 * @param content		본문
	 * @param source		수집사이트
	 * @param k_value		키워드
	 * @param as_seq		알리미 셋트 번호
	 * @param type			기사 1, 개인미디어 2
	 * @param data_type		알리미 데이터 종류에 따라 구분 ex) 시스템 알리미 1, 포털알리미 2 
	 * @param dbconn		DBconn object
	 * @return	전송 성공여부 true or false
	 */
	public boolean sendRRIMI(String md_seq, String phoneNums, String dateTime, String title, String url, String content, String source, String k_value, String as_seq, String type, String data_type, DBconn dbconn) {
		boolean result = false;
		String msg = "";
		
		ConfigUtil cu = new ConfigUtil();
		
		try{
			
			// change -> "double quotation(")" TO "single quotation(')"
			title = title.replaceAll("\"", "'");
			content = content.replaceAll("\"", "'");
			
			// delete -> "\"
			title = title.replaceAll("\\\\", "");
			content = content.replaceAll("\\\\", "");
			
			// change ->  "\n         \n \n\n\n" TO "\n"
			title = title.replaceAll(" {1,}", " ").replaceAll("(\n ){1,}", "\n");
			content = content.replaceAll(" {1,}", " ").replaceAll("(\n ){1,}", "\n");
			
			// change -> "control character" TO "\n"
			title = title.replaceAll("\\p{Cntrl}", "");
			content = content.replaceAll("\\p{Cntrl}", "");
			
			// 언론기사일 경우 키워드 강조 없이 본문 샘플을 짤래냄.
			if("1".equals(type)){
				content = cutKeyNonHighlight(content, k_value.replaceAll(",", "").trim(), 200);
			}
						
			// api에 정의된 byte수 대로 글자를 자름
			title = cutStrInByte(title, 220);
			msg = cutStrInByte(content, 20000);
			source = cutStrInByte(source, 45);
			
			if(url.length() > 500){
				if(url.length() > 20000-msg.length()-50){
					msg = cutStrInByte(msg, (20000 - url.length() - 50));
				}
				
				msg = msg+"\n\n"+url;
				url = "";
			}
			
			String smsMsg = "["+cutStrInByte(source.replaceAll(" ", ""), 16)+" "+dateTime.substring(11, 16)+"]"+title;
			String shortUrl = makeShortUrl(url);
			smsMsg = cutStrInByte(smsMsg, 80-shortUrl.length()-2);
			smsMsg = smsMsg+" "+shortUrl;
				
			// json만들기
			JSONObject rrimiObj = new JSONObject();
			JSONArray rrimiPhoneNumArray = new JSONArray();
			
			String[] num = phoneNums.split(",");
			for (int i=0; i<num.length; i++) {
				JSONObject phoneNum = new JSONObject();
				phoneNum.put("mobileNo", URLEncoder.encode(num[i], "UTF-8") );	
				rrimiPhoneNumArray.put(phoneNum);
			}
			
			rrimiObj.put("sendObjectList", rrimiPhoneNumArray);
			rrimiObj.put("linkUrl", URLEncoder.encode(url, "UTF-8"));
			rrimiObj.put("collectDtime", URLEncoder.encode(dateTime, "UTF-8"));
			rrimiObj.put("title", URLEncoder.encode(title, "UTF-8"));
			rrimiObj.put("msg", URLEncoder.encode(msg, "UTF-8"));
			rrimiObj.put("source", URLEncoder.encode(source, "UTF-8"));
			rrimiObj.put("smsMsg", URLEncoder.encode(smsMsg, "UTF-8"));
			rrimiObj.put("matchKeyword", URLEncoder.encode(k_value, "UTF-8"));
			rrimiObj.put("organizationName", URLEncoder.encode(cu.getConfig("COMPANY"), "UTF-8"));
			
			//Log.crond(rrimiObj.toString());
			//System.out.println(rrimiObj.toString());
			
			// 전송
			result = SendPost("http://rrimi.realsn.com/web/ajax/sendMessages.do", rrimiObj.toString(), phoneNums, md_seq, as_seq, dbconn, data_type, smsMsg, true);
		}catch(Exception e){
			e.printStackTrace();
			Log.writeExpt("RRIMILOG", e.toString());
		}
		
		return result;
	}
	
	/**
	 * 
	 * resendRRIMI 실패데이터 재전송
	 * 
	 * @param json_data		알리미 json데이터 전문
	 * @param sms_data		알리미 sms데이터 전문
	 * @param phoneNums		콤마로 구분된 수신자전화번호
	 * @param md_seq		문서번호
	 * @param as_seq		알리미 셋팅번호
	 * @param data_type		알리미 데이터 종류에 따라 구분 ex) 시스템 알리미 1, 포털알리미 2 
	 * @param dbconn		DBconn object
	 * @return 전송 성공여부 true or false
	 */
	public boolean resendRRIMI(String json_data, String sms_data, String phoneNums, String md_seq, String as_seq, String data_type, DBconn dbconn) {
		boolean result = false;
		
		try{
			// 전송
			result = SendPost("http://rrimi.realsn.com/web/ajax/sendMessages.do", json_data, phoneNums, md_seq, as_seq, dbconn, data_type, sms_data, false);
		}catch(Exception e){
			e.printStackTrace();
			Log.writeExpt("RRIMILOG", e.toString());
		}
		
		return result;
	}
	
	public boolean SendPost(String sUrl, String param, String numbers, String md_seq, String as_seq, DBconn dbconn, String data_type, String sms_msg, boolean bFirst){
    	StringBuffer html = new StringBuffer();
    	try{
    		String line = null;
    		URL aURL = new URL(sUrl);
    		URLConnection urlCon = (URLConnection)aURL.openConnection();
    		String sParam = "";
    		//urlCon.setRequestMethod("POST");
    		
    		//urlCon.setDoInput(true);
    		urlCon.setDoOutput(true);
    		OutputStreamWriter out = new OutputStreamWriter(urlCon.getOutputStream());
    		sParam = "data="+param;
    		
    		out.write(sParam);
    		out.flush();
    		
    		out.close();
    		BufferedReader br  = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
    		while((line = br.readLine()) != null){
    			html.append(line);
    		}
    		
    		Log.writeExpt("RRIMILOG", "["+html.toString()+"]");
    		
    		// 실패시 별도의 table에 저장하여 재시도한다. - 재시도는 별도 데몬에서 실행 ( RrimiResender )
    		if(html.toString().toLowerCase().indexOf("fail") > -1){
    			if(!md_seq.equals("") && bFirst){
    				saveSendFailData(param, sms_msg, numbers, data_type, md_seq, as_seq, dbconn);	
    			}
    		}
    		
    		aURL = null;
    		urlCon = null;
    		br.close();
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    		Log.writeExpt("RRIMILOG", "MalformedURLException : "+e.toString() );
    		// 프로세스 종료
    		//System.exit(1);
    	}catch(IOException e) {
    		e.printStackTrace();
    		Log.writeExpt("RRIMILOG", "IOException :"+ e.toString() );
    		// 프로세스 종료
    		//System.exit(1);
    	}
    	
    	boolean result = false;
    	if(html.toString().indexOf("SUCCESS") > -1){
    		result = true;
    	}else{
    		result = false;
    	}
    	
    	return result;
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
    		Log.writeExpt("RRIMILOG", "MalformedURLException : "+e.toString() );
    		// 프로세스 종료
    		//System.exit(1);
    	}catch(IOException e) {
    		e.printStackTrace();
    		Log.writeExpt("RRIMILOG", "IOException :"+ e.toString() );
    		// 프로세스 종료
    		//System.exit(1);
    	}
    	return html.toString();
    }
	
	/**
     * 발송에 실패한 데이터를 저장한다
     */
	public void saveSendFailData(String json_data, String sms_data, String numbers, String data_type, String md_seq, String as_seq, DBconn dbconn) {

	    Statement stmt  = null;
	    StringBuffer sb	= null;

	    /*
	      
		    CREATE TABLE `RRIMI_FAIL_DATA` (
			  `RFD_SEQ` int(13) unsigned NOT NULL AUTO_INCREMENT COMMENT '관리번호',
			  `RFD_MSG_JSON_DATA` text CHARACTER SET latin1 COMMENT 'R-rimi 메세지 json전문',
			  `RFD_MSG_SMS_DATA` varchar(256) DEFAULT NULL COMMENT 'sms 메세지 전문',
			  `RFD_NUMBERS` mediumtext CHARACTER SET latin1 COMMENT '수신자 전화번호 ',
			  `RFD_DATA_TYPE` int(1) DEFAULT NULL COMMENT '데이터종류 (시스템 알리미, 포탈 알리미 등)',
			  `RFD_RETRY_CNT` int(1) DEFAULT NULL COMMENT '재시도 Count',
			  `MD_SEQ` int(13) unsigned DEFAULT NULL COMMENT '문서번호',
			  `AS_SEQ` int(13) unsigned DEFAULT NULL COMMENT '알리미셋팅번호',
			  PRIMARY KEY (`RFD_SEQ`)
			) ENGINE=MyISAM DEFAULT CHARSET=utf8;
	     
	     */

        try {
        	sb = new StringBuffer();
  
            sb.append("   INSERT INTO RRIMI_FAIL_DATA (RFD_MSG_JSON_DATA, RFD_MSG_SMS_DATA, RFD_NUMBERS, RFD_DATA_TYPE, RFD_RETRY_CNT, MD_SEQ, AS_SEQ) 	\n");
            sb.append("   VALUES ('"+json_data+"', '"+sms_data+"', '"+numbers+"', "+data_type+", 1, "+md_seq+", "+as_seq+") 								\n");
            
            System.out.println(sb.toString());
            
            stmt = dbconn.createStatement();
            stmt.executeUpdate(sb.toString());

            sb = null;

        } catch (SQLException ex ) {
            Log.writeExpt("RRIMILOG", ex.toString() );
        } catch (Exception ex ) {
            Log.writeExpt("RRIMILOG", ex.toString() );

        } finally {
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) {   Log.writeExpt("RRIMILOG", ex.toString() ); }
        }
	}
	
	/**
     * byte단위로 문자열을 자른다
     * @param endIndex
     * @return 잘려진문자열
     */
	public String cutStrInByte(String str, int endIndex) {
		StringBuffer sbStr = new StringBuffer(endIndex);
		int iTotal = 0;
		char[] chars = str.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			iTotal += String.valueOf(chars[i]).getBytes().length;	
			if (chars.length != endIndex && iTotal > endIndex-3) {
				sbStr.append("...");
				break;
			}
			sbStr.append(chars[i]);
		}
		return sbStr.toString();
	}
	
	public String makeShortUrl(String longUrl) throws Exception {
		String shortUrl = "";
		
		// 1. NAVER 시도
		shortUrl = GetHtmlPost("http://n.realsn.com","url="+java.net.URLEncoder.encode(longUrl,"utf-8")+"&site=POSCO");
		
		// 2. twr.kr 시도
		if(shortUrl.trim().equals("") || shortUrl.toLowerCase().indexOf("http") < 0){
			shortUrl = GetHtmlPost("http://twr.kr","api=twrkr&u="+longUrl);	
		}
		
		// 3. ver.kr 시도
		if(shortUrl.trim().equals("") || shortUrl.toLowerCase().indexOf("http") < 0){
			shortUrl = GetHtmlPost("http://ver.kr?isAPI=F&url="+longUrl,"");
		}
		
    	return shortUrl;
	}
    
	/**
     * 본문을 piLen*2 길이로 자르기하고 키워드 강조
     * @param psBody : 본문
     * @param psKeyword : 키워드 ( 키워드1 키워드2 ... )
     * @param piLen : 자르는 문자열 길이의 절반
     * @return : 키워드가 강조된 주요내용
     * 2011.02.11 limSeungChul 소문자 강조 처리 및 구문 키워드("keyword") 처리 수정
     */
    public String cutKeyNonHighlight( String psBody, String psKeyword, int piLen)
    {
    	String result = null;
    	
		StringUtil su = new StringUtil();
		
		int idxPoint = -1; 

		try{
			if(psBody==null || psBody.length()==0){
				return "";
			}
				
			psBody = su.ChangeString( psBody.toLowerCase() );
			if (!su.nvl(psKeyword,"").equals("")) {
				
				String[] arrBFKey = su.getUniqeArray( psKeyword.split(" ") );			// 강조키워드 배열
				arrBFKey = searchGumunKey( arrBFKey );
//				String[] arrAFKey = new String[arrBFKey.length];	// 강조키워드 html 배열
			
				for( int i=0 ; i<arrBFKey.length ; i++ )
				{
			    	//System.out.println("Keyword : "+arrBFKey[i]);
//					arrBFKey[i] = arrBFKey[i].toLowerCase();
//					arrAFKey[i] = "<font color='"+tColor+"'><b>"+arrBFKey[i]+"</b></font>";
					
					if( idxPoint == -1 ) {
						idxPoint = psBody.indexOf(arrBFKey[i]);
					}
				}
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
		
				if( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if( currPoint >= piLen ) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if( psBody.length() >= (startCut + piLen*2) ) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length();
				}
				if(startCut<0){
					startCut = 0;
				}
				if(endCut>psBody.length()-1){
					endCut = psBody.length();
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
//				if( idxPoint >= 0 ) {
//					if (arrBFKey.length>0) {
//						for( int i=0 ; i<arrBFKey.length ; i++  )
//						{
//							result = result.replaceAll( "(?is)"+arrBFKey[i], arrAFKey[i]);
//						}
//					}
//				}
			} else {
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
		
				if( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if( currPoint >= piLen ) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if( psBody.length() >= (startCut + piLen*2) ) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length()-1;
					
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
			}
    	} catch( Exception ex ) {
    		Log.writeExpt(ex);
    		System.out.println(psBody+"/psKeyword:"+psKeyword+"/piLen:"+piLen);
		}
		
		return result;
    }
    
    /**
     * 구문 키워드를 찾아 키워드를 재배열한다.
     * @param key
     * @return
     */
    public String[] searchGumunKey(String[] key)
    {
    	int indexCnt = 0;
		int firstWordIndex = 0;
		int endWordIndex = 0;
		int gumunCount = 0;
		
		String notGumnunKey = "";
		String gumnunKeyword = "";
		String[] tempKey = null;
		String[] lastKey = null;
	
		
		//구문 인덱스 범위를 찾는다.
		for(int i =0 ; i<key.length ; i++){
			notGumnunKey += notGumnunKey.equals("") ? key[i] : " "+ key[i];
			
			if(key[i].indexOf("\"")>-1)
			{
					if(indexCnt==0) firstWordIndex = i;					
					if(indexCnt>0) endWordIndex = i;	
					indexCnt++;
			}			
		}
		
		if(endWordIndex>0)
		{
			gumunCount = endWordIndex -	firstWordIndex;	
			
			for(int i = firstWordIndex ; i<gumunCount+1 ; i++){
				
				gumnunKeyword += gumnunKeyword.equals("") ? key[i] : " "+ key[i];	
			}
			
			//구문 제외한 키워드
			notGumnunKey = notGumnunKey.replaceAll(gumnunKeyword,"");
			notGumnunKey = notGumnunKey.replaceAll("  ", " ");
			
						
			//구문 제외한 키워드 배열
			if(notGumnunKey.equals(" "))tempKey = notGumnunKey.split(" ");			
			if(tempKey!=null)
			{
				lastKey = new String[tempKey.length+1];
				for(int i =0 ; i<tempKey.length ; i++)
				{					
					lastKey[i] = tempKey[i];
				}
				lastKey[tempKey.length] = gumnunKeyword;
		
			}else{
				lastKey = new String[1];
				lastKey[0] = gumnunKeyword;
			}
		}else{
			lastKey = key;
		}
		for(int i =0 ; i<lastKey.length ; i++){
			lastKey[i] = lastKey[i].replaceAll("\"","");
		}
    	return lastKey;
    }
}
