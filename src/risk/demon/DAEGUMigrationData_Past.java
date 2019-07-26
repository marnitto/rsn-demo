package risk.demon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.PersonalInfoUtil;

public class DAEGUMigrationData_Past{
	
	/**
     *  로컬 DB 컨넥션
     * @return
     */
    static DBconn dbconn1 = null;
    static String name = "MigrationData";
    static String apiUrl = "http://api.realsn.com:2025/json.php";
    static String addTableName = "";
    
    private static int main_list_count = 500;
    private static int twitter_list_count = 500;
    private static int facebook_list_count = 500;
    
    public static void main( String[] args ){
    	
    	Log.crond(name,"Migration_v2 START ...");
    	
    	try{
    		
    		DateUtil du = new DateUtil();
    		String today = du.getCurrentDate("yyyyMMdd");
    		
    		String main_date = "";
    		String twitter_date = "";
    		String facebook_date = "";
    		
    		dbconn1 = new DBconn(); //로컬디비
        	dbconn1.getSubDirectConnection();
        	
        	PersonalInfoUtil  piUtil = new PersonalInfoUtil();  
        	
        	//사이트
        	String[][] siteList = getSite();
        	
        	//티어 사이트
        	String[][] tierSiteList = getTierSite();
        	
        	//키워드
        	String[][] keyword = getKeyword(1);
        	
        	//제외키워드
        	String[][] exKeyword = getKeyword(2); 
        	
        	//전제제외키워드
        	String[][] allExKeyword = getAllExKeyword();
        	
        	//페이스북 페이지
        	String[] facebookList = getFacebook();
        	
        	//제외 사이트
        	//HashMap<String, String[]> exSite_map = getExSite();
        	
        	//현재 로컬 META MAX 시퀀스값
        	int newNum = getNewNum(); 
        	
        	
        	//테이블 묶음
        	ArrayList<String[][]> arDBtable = new ArrayList<String[][]>();
        	//arDBtable.add(0, null); //DATA
        	arDBtable.add(0, null); //TWEET
        	//arDBtable.add(2, null); //FACEBOOK
        	//arDBtable.add(2, null); //SECTION_DATA
        	
        	
        	int[] ar_i = new int[arDBtable.size()];
        	Integer[] ar_stamp = new Integer[arDBtable.size()];
        	boolean[] lack = new boolean[arDBtable.size()];
        	
        	
        	String transferNo = "";
        	String maxValue = "";
        	String[][] DBtable = null;
        	
        	
        	ConfigUtil cu = new ConfigUtil();
        	//String dataKey = cu.getConfig("MAINDATA");
        	String tweetKey = cu.getConfig("TWITTERDATA");
        	//String facebookKey = cu.getConfig("FACEBOOKDATA");
        	//String sectionKey = cu.getConfig("SECTIONDATA");
        	
        	//if(dataKey == null || tweetKey == null || facebookKey == null){
        	//	Log.crond(name," TRANS KEY LOST.... \n PLEASE CHECK SYSTEM_TRANS TABLE.");
        	//	System.exit(0);
        	//}
        	
        	
        	{
        		/****while문 내부 전용변수 선언부****/
    			ArrayList<String[]> idxInfo = new ArrayList<String[]>();
    			
    			
    			String type = "";  
            	String d_seq = "";  
            	String d_stamp = "";  
            	String s_seq = "";  
            	String d_site = "";  
            	String d_board = "";  
            	String d_datetime = "";  
            	String d_title = "";  
            	String d_url = "";  
            	String sb_seq = "";
            	String d_content = "";
            	String if_seq = "";
            	String l_alpha = "";
            	String d_image = "";
            	String sg_seq = "";
            	String ts_type = "";
            	String ts_rank = "";
            	
            	String nick_user = "";
            	String id_user = "";
            	
            	String memberCount = ""; 
            	String visitCount = "";
            	String siteName = "";
            	
            	String d_title_lower = "";
            	String d_content_lower = "";
            	
            	String tweet_id = "";
            	String user_id = "";
            	String is_rt = "";
            	String followers = "";
            	String following = "";
            	String tweet = "";
            	String source = "";
            	String tname = "";
            	String profile_image = "";
            	
            	// facebook 관련
        		//String FD_SEQ = null;
        		String FD_CRAWL_STAMP = "";
        		String FD_ID = "";
        		String FD_TYPE = "";
        		String FD_CREATED_TIME = "";
        		String FD_UPDATED_TIME = "";
        		String FD_FROM_ID = "";
        		String FD_FROM_NAME = "";
        		String FD_LIKES_COUNT = "";
        		String FD_COMMENTS_COUNT = "";
        		String FD_SHARES_COUNT = "";
        		String FD_MESSAGE = "";
        		String FD_STORY = "";
        		String FD_PICTURE = "";
        		String FD_LINK = "";
        		String FD_NAME = "";
        		String FD_CAPTION = "";
        		String FD_URL = "";
        		String FU_SEQ = "";
            	
            	int sum = 0;
            	int idx = 0;
            	String[][] targetData = null;
            	boolean PASSAGE = false;
            	String setHtml = "";
            	//String setHtml1 = "";
            	//String setHtml2 = "";
            	//String setHtml3 = "";
            	boolean keyPass = false;
            	String[] idxBean = null;
            	String tmpSite = "";
            	String [] exUrl = null;
            	String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
            	
            	//제외키워드 관련 변수
            	boolean allExCheck = false; //전체 키워드 체크
            	String exName = ""; //전체 키워드 체크
            	String[] arExTmp = null;
            	String exTable = "";
            	
            	//키워드별 사이트 그룹
            	String[] arKeySgSeq = null;
            	boolean KeySgSeqChk = false;
            	
    			/*********************************/
    			
            	//시간 테스트기~
            	boolean timeChk = false;
            	String strLog = "";
            	long firstTime = 0;
            	long fullTime = 0;
            
            	//main_date = lastUpdateTime(dataKey);
            	twitter_date = lastUpdateTime(tweetKey);
            	//facebook_date = lastUpdateTime(facebookKey);
            	
	        	while(true){
	        		//DB 데이터 가져오기~
	            	for(int i = 0; i< arDBtable.size(); i++){	
	            		if(ar_i[i] == 0){
	            			
	            			if(!type.equals("") && !d_seq.equals("")){
	            				//마지막 시퀀스 저장
	            				if(type.equals("DATA")){
	            					//updateLastTransNum(dataKey,d_seq,d_stamp);
	            				}else if(type.equals("TWEET")){
	            					updateLastTransNum(tweetKey,d_seq,d_stamp);
	            				}else if(type.equals("FACEBOOK")){
	            					//updateLastTransNum(facebookKey,d_seq,d_stamp);
	            				}/*else if(type.equals("SECTION_DATA")){
	            					updateLastTransNum(sectionKey,d_seq);
	            				}*/

	            			}


	            			if(!lack[i]){
	            				if(i == 1){
	            					//DATA 테이블 가져오기~
		            				//transferNo = dataKey;
		            				maxValue = getLastTransNum(transferNo);
		            				Log.crond(name,"type : " + "DATA" + " / d_seq : " + maxValue);
		            				DBtable =  getContents(main_date, maxValue, transferNo);
		            			}else if(i == 0){
		            				//TWEET 테이블 가져오기~
		            				transferNo = tweetKey;
		            				maxValue = getLastTransNum(transferNo);
		            				Log.crond(name,"type : " + "TWEET" + " / d_seq : " + maxValue);
		            				DBtable =  getTweetContents(twitter_date, maxValue, transferNo);	
		            			}else if(i == 2){
		            				//Facebook 테이블 가져오기~
		            				//transferNo = facebookKey;
		            				maxValue = getLastTransNum(transferNo);
		            				Log.crond(name,"type : " + "FACEBOOK" + " / d_seq : " + maxValue);
		            				DBtable =  getFacebookContents(facebook_date, maxValue, transferNo);
		            			}/*else if(i == 3){
		            				//SECTION 테이블 가져오기~
		            				transferNo = sectionKey;
		            				maxValue = getLastTransNum(transferNo);
		            				Log.crond(name,"type : " + "SECTION_DATA" + " / d_seq : " + maxValue);
		            				DBtable =  getSectionContents(maxValue);	
		            			}*/
	            			}
	            			
	            			if(DBtable == null){
	            				Log.crond(name, "처리할 데이터가 없습니다. 마이그레이션을 종료 합니다.");
	            				System.exit(1);
	            			}  
	            			
	            			if(DBtable.length == 0){
            					lack[i] = true;		            					
            				}else{
            					for(int j =0; j< lack.length; j++){
            						lack[j] = false; 
            					}
            				}
	            			
	            			arDBtable.set(i, DBtable);
	            			ar_i[i] = DBtable.length;
	            		}
	            		
	            		if(ar_i[i] == 0){
	            			ar_stamp[i] = 0;
	            		}else{
	            			ar_stamp[i] = Integer.parseInt(arDBtable.get(i)[ar_i[i]-1][2]);
	            		}
	            	}
	            	
	            	
	            	if(timeChk){
	            		strLog += "시간(데이터 확인) : " + Long.toString(System.currentTimeMillis() - firstTime) + "\n";
	            		strLog += "시간(총 시간) : " + Long.toString(System.currentTimeMillis() - fullTime) + "\n";
	            		firstTime = System.currentTimeMillis();
	            	}
	            	
	            	// WHILE문 종료시점
	            	sum = 0;
	            	for(int i =0; i < ar_i.length; i++){
	            		sum += ar_i[i];
	            	}
	            	if(sum == 0){
	            		break;
	            	}
	            	
	            	if(timeChk){
	            		//System.out.println(strLog);
	            		firstTime = System.currentTimeMillis();
	            		fullTime = System.currentTimeMillis();
	            		strLog = "=====================\n";
	            	}
	            	
	            	
	            	//데이터 시간비교하여, 어떤 채널을 사용할 것인지
	            	idx = RankTop(ar_stamp);
	            	targetData = arDBtable.get(idx);
	            	ar_i[idx] = ar_i[idx] - 1;
	            	
	            	
	            	//공통
	            	type = targetData[ar_i[idx]][0];  
	            	d_seq = targetData[ar_i[idx]][1];  
	            	d_stamp = targetData[ar_i[idx]][2];  
	            	s_seq = targetData[ar_i[idx]][3];  
	            	d_site = targetData[ar_i[idx]][4];  
	            	d_board = targetData[ar_i[idx]][5];  
	            	d_datetime = targetData[ar_i[idx]][6];  
	            	d_title = targetData[ar_i[idx]][7];
	            	d_title_lower = targetData[ar_i[idx]][7].toLowerCase().replaceAll(regexpStr, " ").replaceAll("\\s{1,}"," ");
	            	d_url = targetData[ar_i[idx]][8];  
	            	sb_seq = targetData[ar_i[idx]][9];
	            	d_content = targetData[ar_i[idx]][10];
	            	d_content_lower = targetData[ar_i[idx]][10].toLowerCase().replaceAll(regexpStr, " ").replaceAll("\\s{1,}"," ");
	            	if_seq = targetData[ar_i[idx]][11];
	            	l_alpha = targetData[ar_i[idx]][12];
	            	d_image = targetData[ar_i[idx]][13];
	            	nick_user = targetData[ar_i[idx]][14];	//DATA일 경우 NICK NAME, TWEET일 경우 TWITTER ID
	            	id_user = targetData[ar_i[idx]][15];    //DATA일 경우 USER ID, TWEET일 경우 USER ID
	            	memberCount = targetData[ar_i[idx]][16];
	            	visitCount = targetData[ar_i[idx]][17];
	            	siteName = targetData[ar_i[idx]][18];
	            	
	            	sg_seq = "";
	            	ts_type = "0";
	            	ts_rank = "999";
	       
	            	if(timeChk){
	            		strLog += "["+targetData[ar_i[idx]][0]+"]\n";
	            		strLog += "시간(시간 비교 및 변수 할당) : " + Long.toString(System.currentTimeMillis() - firstTime) + "\n";
	            		firstTime = System.currentTimeMillis();
	            	}
	            	
	            
	            	//수집사이트 체크
            		PASSAGE = false;
	            	for(int i = 0; i < siteList.length; i++){
	            		if(siteList[i][1].equals(s_seq)){
	            			sg_seq = siteList[i][0];  
	            			PASSAGE = true;
	            			break;
	            		}
	            	}
	            	if(!PASSAGE){
	            		continue;
	            	}
	            	
	            	if(timeChk){
	            		strLog += "시간(수집사이트 체크) : " + Long.toString(System.currentTimeMillis() - firstTime) + "\n";
	            		firstTime = System.currentTimeMillis();
	            	}
	            	
	            
	            	
	            	//제외사이트
	            	/*
	            	if(type.equals("D")){
	            		PASSAGE = true;
	            		if(exSite_map.size() > 0){
	            			exUrl = exSite_map.get(s_seq);
	            			if (exUrl != null){
	            				for (int i = 0; i<exUrl.length; i++) {
	            					
									// 제외사이트 조건에 걸리면
									if(d_url.indexOf(exUrl[i]) > -1){
										PASSAGE = false;
										break;
									}
								}
	            			}
	            		}
	            		
	            		if(!PASSAGE){
	            			Log.crond(name,"[제외사이트 걸림] d_seq : " + d_seq);
		            		continue;
		            	}
	            		
	            	}
	            	
	            	if(timeChk){
	            		strLog += "시간(제외사이트 체크) : " + Long.toString(System.currentTimeMillis() - firstTime) + "\n";
	            		firstTime = System.currentTimeMillis();
	            	}
	            	*/
	            	
	            	
	            	if("FACEBOOK".equals(type)){
	            		PASSAGE = false;
	            		FU_SEQ = targetData[ar_i[idx]][24];
		            	for(int i = 0; i < facebookList.length; i++){
		            		if(facebookList[i].equals(FU_SEQ)){
		            			PASSAGE = true;
		            			break;
		            		}
		            	}
	            		if(!PASSAGE){
		            		continue;
		            	}
	            	}
	            	
	            	
	            	//트위터인경우 한국어,영어만 수집
	            	//if(type.equals("TWEET")){
	            	//	PASSAGE = false;
	            	//	if(l_alpha.equals("ko")){
	            	//		l_alpha = "KOR";
	            	//		PASSAGE = true;
	            	//	}else if(l_alpha.equals("en")){
	            	//		l_alpha = "ENG";
	            	//		PASSAGE = true;
	            	//	}
	            	//	if(!PASSAGE){
		            //		continue;
		            //	}
	            	//}
	            	
	            	PASSAGE = false;
	            	keyPass = false;
	            	//setHtml = " "+d_title_lower+" "+d_content_lower+" ";
	            	
	            	String origanalContents = "";
	            	//원문 그대로 전체 제외키워드 검사를 한다. 
            		origanalContents = " "+d_title.toLowerCase()+" "+d_content.toLowerCase()+" ";            	
	            	//트위터일 경우 트위터 아이디, 해쉬태그, url을 제거하여 키워드 검사를 한다.
	            	if( "TWEET".equals(type)){
	            		
	            		setHtml = " "+d_title_lower+" ";
	            		
	            		setHtml =  piUtil.removeUrl(piUtil.removeHashTag(piUtil.removeTwitterID(setHtml)) );
	            		setHtml = setHtml.trim();
	            		setHtml = " "+setHtml.toLowerCase()+ " ";
	            		
	            		//Log.crond("twitter - "+setHtml);
	            	}else{
	            		
	            		setHtml = " "+d_title_lower+" "+d_content_lower+" ";
	            		
	            	}
	            	
	            	idxInfo.clear();
	            	idxBean = null;
	            	
	            	//키워드 루프
	            	for(int i = 0; i < keyword.length; i++){
	            		
	            		//키워드별 사이트 체크
	            		if(keyword[i][9].equals("N")){
	            			PASSAGE = false;
	            			arKeySgSeq = keyword[i][5].split(",");
	            			
	            			for(int j = 0; j < arKeySgSeq.length; j++){
	            				if(arKeySgSeq[j].equals(sg_seq)){
	            					PASSAGE = true;
	            					break;
	            				}
	            			}
	            			if(!PASSAGE){
		            			continue;
		            		}
	            		}
	            		
	            		
	            		//키워드 필터
	            		keyPass = false;
						if(searchKeyword(setHtml, keyword[i][4], Integer.parseInt(keyword[i][3]), Integer.parseInt(keyword[i][8]))){
							
							keyPass = true;
							if(!keyword[i][6].equals("0")){
								//제외 키워드 필터
								for(int j =0; j < exKeyword.length; j++){
									
									if(   keyword[i][0].equals(exKeyword[j][0])
									   && keyword[i][1].equals(exKeyword[j][1])
									   && keyword[i][2].equals(exKeyword[j][2])
									  ){
										if(searchKeyword(setHtml, exKeyword[j][4], Integer.parseInt(exKeyword[j][3]), Integer.parseInt(keyword[i][8]))){
											Log.crond(name,"[일반 제외키워드 걸림] d_seq : " + d_seq);
											keyPass = false;
											break;
										}
									}
								}
							}
						}
						
						if(keyPass){
							//System.out.println("등록키워드["+keyword[i][0]+"]["+keyword[i][1]+"]["+keyword[i][2]+"] : " +   keyword[i][4]);
							idxBean = new String[4];
							idxBean[0] = keyword[i][0];
							idxBean[1] = keyword[i][1];
							idxBean[2] = keyword[i][2];
							idxBean[3] = keyword[i][7];
							idxInfo.add(idxBean);
						}
	            	}
	            	
	            	if(timeChk){
	            		strLog += "시간(수집 키워드 체크) : " + Long.toString(System.currentTimeMillis() - firstTime) + "\n";
	            		firstTime = System.currentTimeMillis();
	            	}
	            	
	            	
	            	if(idxInfo.size() > 0){
	            		//시퀀스 증가
	            		newNum++;
	            		
	            		
	            		//전체 제외키워드 맵핑
		            	allExCheck = false;
		            	
		            	for(int i = 0; i < allExKeyword.length; i++){
		            		exName = allExKeyword[i][0];
		            		if(searchKeyword(origanalContents,exName,3,0)){ //일반 인덱싱
		            			allExCheck = true;
		            			//허용매체 검사
		            			if(!allExKeyword[i][2].equals("")){
		            				arExTmp = allExKeyword[i][2].split(",");
		            				for(int j =0; j < arExTmp.length; j++){
		            					if(arExTmp.equals(s_seq)){
		            						allExCheck = false;
		            						break;
		            					}
		            				}
		            			}
		            			
		            			//허용키워드 검사
		            			if(allExCheck){
		            				if(!allExKeyword[i][1].equals("")){
			            				arExTmp = allExKeyword[i][1].split(",");
			            				for(int j =0; j < arExTmp.length; j++){
			            					for(int s = 0; s < idxInfo.size(); s++){
			            						idxBean = (String[])idxInfo.get(s);
			            						if(arExTmp.equals(idxBean[3])){
			            							allExCheck = false;
			            							break;
			            						}
			            					}
			            					if(!allExCheck){
			            						break;
			            					}
			            					
			            				}
			            			}
		            			}

		            		}
		            		
		            	}
		            	
		            	if(allExCheck){
		            		exTable = "EXCEPTION_";
		            	}else{
		            		exTable = "";
		            	}
		            	if(timeChk){
		            		strLog += "시간(전체제외 키워드 체크) : " + Long.toString(System.currentTimeMillis() - firstTime) + "\n";
		            		firstTime = System.currentTimeMillis();
		            	}
	            		
	            		
	            		//Tire 사이트 맵핑
	            		if(tierSiteList.length > 0){
	            			for(int i = 0; i< tierSiteList.length; i++){
	            				if(tierSiteList[i][1].equals(s_seq)){
	            					ts_type = tierSiteList[i][2];
	            					ts_rank = tierSiteList[i][3];
	            					break;
	            				}
	            			}
	            		}
		            	
		            	
	            		
	            		tmpSite = "";
	            		switch(Integer.parseInt(s_seq))
						{
							case 2196 : // 네이버
							case 2199 : // 다음
							case 2201 : // 파란
							case 3883 : // 네이트
							case 2195 : // 야후
								tmpSite = d_site;
								d_site = d_board;
								d_board = tmpSite;
								break;
						}
	            		
	            		//데이터 저장
	            		PASSAGE = insertMapMetaSeq(newNum, d_seq, exTable);
	            		
	            		if(PASSAGE){
	            			insertIdx(newNum, sg_seq, idxInfo, exTable);
	            			insertMeta(newNum, d_seq, d_site, d_board, if_seq, d_datetime, d_title, d_url, "0", newNum, s_seq, d_image, l_alpha, exTable, ts_type, ts_rank, nick_user, id_user, memberCount, visitCount, siteName, exName);
		            		insertData(newNum, d_content, exTable);
		            		
		            		
		            		if(type.equals("TWEET")){
		            			tweet_id = targetData[ar_i[idx]][14];
		                    	user_id = targetData[ar_i[idx]][15];
		                    	is_rt = targetData[ar_i[idx]][16];
		                    	followers = targetData[ar_i[idx]][17];
		                    	following = targetData[ar_i[idx]][18];
		                    	tweet = targetData[ar_i[idx]][19];
		                    	source = targetData[ar_i[idx]][20];
		                    	tname = targetData[ar_i[idx]][21];
		                    	profile_image = targetData[ar_i[idx]][22];
		                    	
		            			insertTweet(newNum, tweet_id, user_id, is_rt, followers, following, tweet, source, l_alpha, d_title, d_datetime, tname, profile_image, exTable);
		            		}else if(type.equals("FACEBOOK")){
		            			
		            			FD_CRAWL_STAMP = d_stamp;
		    					FD_ID = targetData[ar_i[idx]][22];
		    					FD_TYPE = targetData[ar_i[idx]][14];
		    					FD_CREATED_TIME = targetData[ar_i[idx]][15];
		    					FD_UPDATED_TIME = targetData[ar_i[idx]][15];
		    					FD_FROM_ID = targetData[ar_i[idx]][16];
		    					FD_FROM_NAME = targetData[ar_i[idx]][17];
		    					FD_LIKES_COUNT = targetData[ar_i[idx]][18];
		    					FD_COMMENTS_COUNT = targetData[ar_i[idx]][19];
		    					FD_SHARES_COUNT = targetData[ar_i[idx]][20];
		    					FD_MESSAGE = d_content;
		    					FD_STORY = d_content;
		    					FD_PICTURE = targetData[ar_i[idx]][21];
		    					FD_LINK = targetData[ar_i[idx]][22];
		    					FD_NAME = targetData[ar_i[idx]][23];
		    					FD_CAPTION = d_title;
		    					FD_URL = d_url;
		    						          	
		                    	insertFaceBook(newNum, FD_CRAWL_STAMP, FD_ID, FD_TYPE, FD_CREATED_TIME, FD_UPDATED_TIME, FD_FROM_ID, FD_FROM_NAME,
		                    			FD_LIKES_COUNT, FD_COMMENTS_COUNT, FD_SHARES_COUNT, FD_MESSAGE, FD_STORY, FD_PICTURE, FD_LINK, FD_NAME, FD_CAPTION, FD_URL, FU_SEQ, d_datetime, exTable);
		            		}
	            		}
	            	}
	            	if(timeChk){ 
	            		strLog += "시간(데이터 저장) : " + Long.toString(System.currentTimeMillis() - firstTime) + "\n";
	            		firstTime = System.currentTimeMillis();
	            	}
	        	}
        	}
        	
        	
    	}catch(Exception ex) {
    		Log.writeExpt(name,ex.getMessage());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
    	
    	Log.crond(name,"Migration END ...");
    }
    
    
    static String lastUpdateTime(String api_key){
    	String result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" SELECT FROM_UNIXTIME(ST_CRAWL_STAMP,'%Y%m%d') AS UPDATE_DATE FROM SYSTEM_TRANSFER WHERE ST_API_KEY = '"+api_key+"'; 	\n");
            
            System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = rs.getString("UPDATE_DATE");
			}
            
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static int RankTop(Integer[] arRank){
    
    	int reuslt = 0;
    	int cnt = 0;
    	int zeroCnt =0;
    	
    	for(int i = 0; i < arRank.length; i++){
    		if(arRank[i] == 0){
    			zeroCnt++;
    		}
    	}
    	
    	for(int i = 0; i < arRank.length; i++){
    		
    		cnt = 0;
    		
    		if(arRank[i] != 0){
    			for(int j = 0; j < arRank.length; j++){
    				if(arRank[j] != 0){
    					if(arRank[i] <= arRank[j]){
        					cnt++;
        				}
    				}
        		}
    		}
    		
    		if(arRank.length - zeroCnt == cnt){
    			reuslt = i;
    			break;
    		}
    	}
    	
    	return reuslt;
    }
    
    
    
    static String[][] getSite(){
    	
    	String[][] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" SELECT SG_SEQ, S_SEQ FROM SG_S_RELATION ORDER BY S_SEQ 	\n");
            
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			result = new String[rs.getRow()][2];  
			rs.beforeFirst();
			
			int row_idx = 0;
			int column_idx = 0;
			
			while(rs.next()){
				column_idx = 0;				
				result[row_idx][column_idx++] =  rs.getString("SG_SEQ");
				result[row_idx][column_idx++] =  rs.getString("S_SEQ");
				row_idx++;
			}
			
            
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String[][] getTierSite(){
    	
    	String[][] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" SELECT SG_SEQ, S_SEQ, TS_TYPE, TS_RANK FROM TIER_SITE A, SG_S_RELATION B WHERE A.TS_SEQ = B.S_SEQ 	\n");
            
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			result = new String[rs.getRow()][4];  
			rs.beforeFirst();
			
			int row_idx = 0;
			int column_idx = 0;
			
			while(rs.next()){
				column_idx = 0;				
				result[row_idx][column_idx++] =  rs.getString("SG_SEQ");
				result[row_idx][column_idx++] =  rs.getString("S_SEQ");
				result[row_idx][column_idx++] =  rs.getString("TS_TYPE");
				result[row_idx][column_idx++] =  rs.getString("TS_RANK");
				row_idx++;
			}
			
            
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String[][] getKeyword(int type){
    	
    	String[][] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	
        	
        	
        	
        	if(type == 1){
        		
        		String sg_seqs = "";
            	sb = new StringBuffer();
            	sb.append("SELECT GROUP_CONCAT(SG_SEQ) AS SG_SEQS FROM SITE_GROUP	\n");
            	rs = stmt.executeQuery(sb.toString());
            	while(rs.next()){
            		sg_seqs = rs.getString("SG_SEQS");
            	}
        		
        		sb = new StringBuffer();
            	sb.append("SELECT A.*																												\n");
            	sb.append("     , B.SG_SEQS 																										\n");
            	sb.append("     , (SELECT COUNT(*) FROM KEYWORD WHERE K_TYPE >= 11 AND K_XP = A.K_XP AND K_YP = A.K_YP AND K_ZP = A.K_ZP) AS EX_CNT	\n");
            	sb.append("     , IF(B.SG_SEQS = '"+sg_seqs+"','Y','N') AS SG_SEQ_CHK			\n");
            	sb.append("  FROM (SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, K_VALUE, K_NEAR_LEN FROM KEYWORD WHERE K_TYPE = 2 ) A 									\n");
            	sb.append("     , (SELECT K_XP, IFNULL(SG_SEQS,'') AS SG_SEQS FROM KEYWORD WHERE K_YP = 0 AND K_ZP = 0) B							\n");		
            	sb.append(" WHERE A.K_XP = B.K_XP																									\n");
            	
            	
            }else if(type == 2){
            	sb = new StringBuffer();
            	sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_OP, K_VALUE, '' AS SG_SEQS, '' AS EX_CNT, K_NEAR_LEN, '' AS SG_SEQ_CHK FROM KEYWORD \n");
            	sb.append(" WHERE K_TYPE >= 11 \n");	
            }
            
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			result = new String[rs.getRow()][10];  
			rs.beforeFirst();
			
			int row_idx = 0;
			int column_idx = 0;
			
			while(rs.next()){
				column_idx = 0;				
				result[row_idx][column_idx++] =  rs.getString("K_XP");
				result[row_idx][column_idx++] =  rs.getString("K_YP");
				result[row_idx][column_idx++] =  rs.getString("K_ZP");
				result[row_idx][column_idx++] =  rs.getString("K_OP");
				result[row_idx][column_idx++] =  rs.getString("K_VALUE").toLowerCase();
				result[row_idx][column_idx++] =  rs.getString("SG_SEQS");
				result[row_idx][column_idx++] =  rs.getString("EX_CNT");
				result[row_idx][column_idx++] =  rs.getString("K_SEQ");
				result[row_idx][column_idx++] =  rs.getString("K_NEAR_LEN");
				result[row_idx][column_idx++] =  rs.getString("SG_SEQ_CHK");
				
				row_idx++;
			}
			
            
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String[][] getAllExKeyword(){
    	
    	String[][] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
        	//sb.append(" SELECT EK_VALUE, EK_OPTION FROM EXCEPTION_KEYWORD \n");
        	sb.append(" SELECT EK_VALUE, IFNULL(K_SEQS,'') AS K_SEQS, IFNULL(S_SEQS,'') AS S_SEQS FROM EXCEPTION_KEYWORD \n");
        	
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			result = new String[rs.getRow()][3];  
			rs.beforeFirst();
			
			int row_idx = 0;
			int column_idx = 0;
			
			while(rs.next()){
				column_idx = 0;				
				result[row_idx][column_idx++] =  rs.getString("EK_VALUE").toLowerCase();
				result[row_idx][column_idx++] =  rs.getString("K_SEQS");
				result[row_idx][column_idx++] =  rs.getString("S_SEQS");
				row_idx++;
			}
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String[] getFacebook(){
    	
    	String[] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" SELECT FU_SEQ FROM FACEBOOK_USER ORDER BY FU_SEQ 	\n");
            
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			result = new String[rs.getRow()];  
			rs.beforeFirst();
			
			int row_idx = 0;
			
			while(rs.next()){
				result[row_idx] =  rs.getString("FU_SEQ");
				row_idx++;
			}
			
            
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    //최근 INDEXING한 번호 
    static String getLastTransNum( String transferNo ){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
        	sb.append(" SELECT ST_LAST_SEQ AS LASTNO FROM SYSTEM_TRANSFER WHERE ST_API_KEY ='"+transferNo+"' \n");
        	
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){					
				result = rs.getString("LASTNO");
			}
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String[][] getContents(String date, String startNum, String acessToken){
    	
    	JSONObject json = null;
    	String[][] result = null;
    	String returnData = ""; 
    	
        String param = "";
    	param += "api_key=" + acessToken;
    	param += "&max_seq=" + startNum;
    	param += "&search_day=" + date;
    	param += "&list_count="+main_list_count;
        
        try{
        	
        	Log.crond("[SEND]" + apiUrl+ "?" +  param);
        	returnData = GetHtmlPost(apiUrl, param);
        	Log.crond("[RECEIVE]");
        	
        	json = new JSONObject(returnData);
        	JSONArray jAr = new JSONArray();
        	JSONObject row_json = null;
        	      	
        	if(!json.isNull("data")){
        		jAr = json.getJSONArray("data");
        		
        		result = new String[jAr.length()][19];
        		
        		int idx = jAr.length();
        		for(int i=0; i< jAr.length(); i++){
        			row_json = jAr.getJSONObject(i);
        			
        			idx--;
        			
        			for(int j = 0; j < 19; j++){
        				result[idx][j] = "";
        			}
        			
        			result[idx][0] = "DATA";
        			
        			if(!row_json.isNull("MAIN.DATA.Seq")){ //게시글번호
        				result[idx][1] = row_json.getString("MAIN.DATA.Seq");
        			}
        			if(!row_json.isNull("MAIN.DATA.CrawlStamp")){ //수집일시
        				result[idx][2] = row_json.getString("MAIN.DATA.CrawlStamp");
        			}
        			if(!row_json.isNull("COMMON.SITE.Seq")){ //사이트번호
        				result[idx][3] = row_json.getString("COMMON.SITE.Seq");
        			}
        			if(!row_json.isNull("COMMON.SITE.Name")){ //사이트명
        				result[idx][4] = row_json.getString("COMMON.SITE.Name");
        			}
        			if(!row_json.isNull("MAIN.BOARD.Name")){	 //보드명
        				result[idx][5] = row_json.getString("MAIN.BOARD.Name");
        			}
        			if(!row_json.isNull("MAIN.DATA.CrawlStamp")){ //수집일시
        				result[idx][6] = stampToDateTime(row_json.getString("MAIN.DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
        			}
        			if(!row_json.isNull("MAIN.DATA.Title")){	 //게시글제목
        				result[idx][7] = row_json.getString("MAIN.DATA.Title");
        			}
        			if(!row_json.isNull("MAIN.DATA.Link")){	//게시글링크
        				result[idx][8] = row_json.getString("MAIN.DATA.Link");
        			}
        			if(!row_json.isNull("MAIN.BOARD.Seq")){ //보드번호
        				result[idx][9] = row_json.getString("MAIN.BOARD.Seq");
        			}
        			if(!row_json.isNull("MAIN.DATA.Content")){ //게시글본문
        				result[idx][10] = row_json.getString("MAIN.DATA.Content");
        			}
        			if(!row_json.isNull("MAIN.DATA.InfoType")){ //정보유형 (1:기사 2:게시)
        				result[idx][11] = row_json.getString("MAIN.DATA.InfoType");
        			}
        			if(!row_json.isNull("MAIN.DATA.Lang3")){ //언어코드
        				result[idx][12] = row_json.getString("MAIN.DATA.Lang3");
        			}
        			if(!row_json.isNull("MAIN.DATA.ImgIs")){ //이미지포함여부
        				result[idx][13] = row_json.getString("MAIN.DATA.ImgIs");
        			}else{
        				result[idx][13] = "0";
        			}
        			if(!row_json.isNull("MAIN.DATA.WriterName")){ //작성자명
        				result[idx][14] = row_json.getString("MAIN.DATA.WriterName");
        			}
        			if(!row_json.isNull("MAIN.DATA.WriterID")){ //작성자ID
        				result[idx][15] = row_json.getString("MAIN.DATA.WriterID");
        			}
        			if(!row_json.isNull("MAIN.DATA.MemberCount")){ //회원수
        				result[idx][16] = row_json.getString("MAIN.DATA.MemberCount");
        			}else{
        				result[idx][16] = "0";
        			}
        			if(!row_json.isNull("MAIN.DATA.VisitCount")){ //방문자수
        				result[idx][17] = row_json.getString("MAIN.DATA.VisitCount");
        			}else{
        				result[idx][17] = "0";
        			}
        			
        			if(!row_json.isNull("MAIN.DATA.SourceSiteName")){ //블로그명, 카페명
        				result[idx][18] = row_json.getString("MAIN.DATA.SourceSiteName");
        			}else{
        				result[idx][18] = "";
        			}
        			
        		}
        	}else if(!json.isNull("error")){
        		Log.writeExpt(name,json.getJSONObject("error").getString("message"));
        		System.exit(1);
        	}else{
        		result = new String[0][16];
        	}
        	
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
        } 
        
        return result;
    }
    
    static String[][] getTweetContents(String date, String startNum, String acessToken){
    	
    	JSONObject json = null;
    	String[][] result = null;
    	String returnData = ""; 
    	
        String param = "";
    	param += "api_key=" + acessToken;
    	param += "&max_seq=" + startNum;
    	param += "&search_day=" + date;
    	param += "&list_count=" + twitter_list_count;
        
        try{
        	
        	Log.crond("[SEND]" + apiUrl+ "?" +  param);
        	returnData = GetHtmlPost(apiUrl, param);
        	Log.crond("[RECEIVE]");
        	
        	json = new JSONObject(returnData);
        	JSONArray jAr = new JSONArray();
        	JSONObject row_json = null;    	
        	
        	if(!json.isNull("data")){
        		jAr = json.getJSONArray("data");
        		
        		result = new String[jAr.length()][23];
        		
        		int idx = jAr.length();
        		for(int i=0; i< jAr.length(); i++){
        			row_json = jAr.getJSONObject(i);
        			
        			idx--;
        			
        			for(int j = 0; j < 23; j++){
        				result[idx][j] = "";
        			}
        			
        			result[idx][0] = "TWEET";
        			
        			if(!row_json.isNull("TWITTER.DATA.Seq")){	// 게시글번호
        				result[idx][1] = row_json.getString("TWITTER.DATA.Seq");
        			}
        			if(!row_json.isNull("TWITTER.DATA.CrawlStamp")){ //수집일시
        				result[idx][2] = row_json.getString("TWITTER.DATA.CrawlStamp");
        			}
        			
        			result[idx][3] = "10464"; //사이트번호
        			
        			result[idx][4] = "Twitter"; //사이트명
        			
        			result[idx][5] = "Twitter"; //보드명
        			
        			if(!row_json.isNull("TWITTER.DATA.CrawlStamp")){ //수집일시
        				result[idx][6] = stampToDateTime(row_json.getString("TWITTER.DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
        			}
        			
        			int titleLength = row_json.getString("TWITTER.DATA.Content").toString().length();	
    				if (titleLength > 35) {
    					titleLength = 35;
    				}
        			
    				if(!row_json.isNull("TWITTER.DATA.Content")){	//게시글제목
        				result[idx][7] = row_json.getString("TWITTER.DATA.Content").substring(0, titleLength);
        			}

        			if(!row_json.isNull("TWITTER.DATA.Link")){	//게시글링크
        				result[idx][8] = row_json.getString("TWITTER.DATA.Link");
        			}
        			
        			result[idx][9] = "0"; //보드번호
        			
        			if(!row_json.isNull("TWITTER.DATA.Content")){	 //게시글본문
        				result[idx][10] = row_json.getString("TWITTER.DATA.Content");
        			}
        			
        			result[idx][11] = "2"; //정보유형
        			
        			if(!row_json.isNull("TWITTER.DATA.Lang3")){ //언어코드
        				result[idx][12] = row_json.getString("TWITTER.DATA.Lang3");
        			}
        			
        			result[idx][13] = "0"; //이미지포함여부

        			if(!row_json.isNull("TWITTER.DATA.ID")){ //게시글ID
        				result[idx][14] = row_json.getString("TWITTER.DATA.ID");
        			}
        			
        			if(!row_json.isNull("TWITTER.DATA.WriterID")){ //작성자ID
        				result[idx][15] = row_json.getString("TWITTER.DATA.WriterID");
        			}
        			
        			if(!row_json.isNull("TWITTER.DATA.Type")){ //RT여부, 트윗타입
        				result[idx][16] = row_json.getString("TWITTER.DATA.Type");
        			}

        			if(!row_json.isNull("TWITTER.DATA.WriterFollowerCount")){	
        				result[idx][17] = row_json.getString("TWITTER.DATA.WriterFollowerCount");
        			}else{
        				result[idx][17] = "0";
        			}
        			
        			if(!row_json.isNull("TWITTER.DATA.WriterFollowingCount")){	
        				result[idx][18] = row_json.getString("TWITTER.DATA.WriterFollowingCount");
        			}else{
        				result[idx][18] = "0";
        			}
        			
        			if(!row_json.isNull("TWITTER.DATA.WriterDocCount")){ //작성문자수
        				result[idx][19] = row_json.getString("TWITTER.DATA.WriterDocCount");
        			}
        			
        			if(!row_json.isNull("TWITTER.DATA.WriterDevice")){ //디바이스
        				result[idx][20] = row_json.getString("TWITTER.DATA.WriterDevice");
        			}
        			
        			if(!row_json.isNull("TWITTER.DATA.WriterName")){ //작성자명
        				result[idx][21] = row_json.getString("TWITTER.DATA.WriterName");
        			}
        			
        			if(!row_json.isNull("TWITTER.DATA.WriterImgLink")){ //프로필사진링크
        				result[idx][22] = row_json.getString("TWITTER.DATA.WriterImgLink");
        			}
        		}
        	}else if(!json.isNull("error")){
        		Log.writeExpt(name,json.getJSONObject("error").getString("message"));
        		System.exit(1);
        	}else{
        		result = new String[0][23];
        	}
        	
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
        } 
        
        return result;
    }
    
    static String[][] getFacebookContents(String date, String startNum, String acessToken){
    	
    	JSONObject json = null;
    	String[][] result = null;
    	String returnData = ""; 
    	
        String param = "";
    	param += "api_key=" + acessToken;
    	param += "&max_seq=" + startNum;
    	param += "&search_day=" + date;
    	param += "&list_count=" + facebook_list_count;
        
        try{
        	
        	Log.crond("[SEND]" + apiUrl+ "?" +  param);
        	returnData = GetHtmlPost(apiUrl, param);
        	Log.crond("[RECEIVE]");
        	
        	json = new JSONObject(returnData);
        	JSONArray jAr = new JSONArray();
        	JSONObject row_json = null;
        	
        	if(!json.isNull("data")){
        		jAr = json.getJSONArray("data");
        		
        		result = new String[jAr.length()][25];
        		
        		int idx = jAr.length();
        		for(int i=0; i< jAr.length(); i++){
        			
        			row_json = jAr.getJSONObject(i);
        			
        			idx--;
        			
        			for(int j = 0; j < 25; j++){
        				result[idx][j] = "";
        			}
        			
        			result[idx][0] = "FACEBOOK";
        			
        			if(!row_json.isNull("FACEBOOK.DATA.Seq")){ //게시글번호
        				result[idx][1] = row_json.getString("FACEBOOK.DATA.Seq");
        			}
        			if(!row_json.isNull("FACEBOOK.DATA.CrawlStamp")){	//수집일시
        				result[idx][2] = row_json.getString("FACEBOOK.DATA.CrawlStamp");
        			}
        			
        			result[idx][3] = "11377"; // 사이트번호
        			
        			result[idx][4] = "facebook"; // 사이트명
        			
        			if(!row_json.isNull("FACEBOOK.DATA.WriterName")){ //페이지명
        				result[idx][5] = row_json.getString("FACEBOOK.DATA.WriterName");
        			} 	
        			
        			if(!row_json.isNull("FACEBOOK.DATA.CrawlStamp")){	//수집일시
        				result[idx][6] = stampToDateTime(row_json.getString("FACEBOOK.DATA.CrawlStamp"), "yyyy-MM-dd HH:mm:ss");
        			}
        			
        			int titleLength = row_json.getString("FACEBOOK.DATA.Content").toString().length();	
    				if (titleLength > 35) {
    					titleLength = 35;
    				}
        			
    				if(!row_json.isNull("FACEBOOK.DATA.Content")){	//게시글제목
        				result[idx][7] = row_json.getString("FACEBOOK.DATA.Content").substring(0, titleLength);
        			}
    				
    				if(!row_json.isNull("FACEBOOK.DATA.ID")){	//게시글링크
    					result[idx][8] = convertFacebookURL(row_json.getString("FACEBOOK.DATA.ID"));
        			}
    				
    				result[idx][9] = "0"; // 보드번호
    				
    				if(!row_json.isNull("FACEBOOK.DATA.Content")){	//게시글본문
        				result[idx][10] = row_json.getString("FACEBOOK.DATA.Content");
        			}
    				
    				result[idx][11] = "2"; //정보유형
    				
    				result[idx][12] = "KOR"; //언어코드
    				
    				result[idx][13] = "0"; // 이미지포함여부
    				
    				if(!row_json.isNull("FACEBOOK.USER.Category")){	//카테고리
        				result[idx][14] = row_json.getString("FACEBOOK.USER.Category");
        			}
        			
        			if(!row_json.isNull("FACEBOOK.DATA.WriteStamp")){	//작성일시
        				result[idx][15] = row_json.getString("FACEBOOK.DATA.WriteStamp");
        			}
        			
        			if(!row_json.isNull("FACEBOOK.USER.ID")){	//작성자ID
        				result[idx][16] = row_json.getString("FACEBOOK.USER.ID");
        			}
        			
        			if(!row_json.isNull("FACEBOOK.DATA.WriterName")){	//작성자명
        				result[idx][17] = row_json.getString("FACEBOOK.DATA.WriterName");
        			}
        			
        			result[idx][18] = ""; //좋아요 수
        			
        			result[idx][19] = ""; //댓글 수
        			
        			if(!row_json.isNull("FACEBOOK.DATA.ShareCount")){	//공유 수
        				result[idx][20] = row_json.getString("FACEBOOK.DATA.ShareCount");
        			}else{
        				result[idx][20] = "0";
        			}
        			
        			if(!row_json.isNull("FACEBOOK.DATA.ImgLink")){	//이미지링크
        				result[idx][21] = row_json.getString("FACEBOOK.DATA.ImgLink");
        			}
        			
        			if(!row_json.isNull("FACEBOOK.DATA.ID")){ //게시글ID
        				result[idx][22] = row_json.getString("FACEBOOK.DATA.ID");
        			}
        			
        			result[idx][23] = ""; //FD_NAME, 기존소스에서 공백처리된 데이터.
        			
        			if(!row_json.isNull("FACEBOOK.USER.Seq")){ //펜페이지고유번호
        				result[idx][24] = row_json.getString("FACEBOOK.USER.Seq");
        			} 			
        			
        		}
        	}else if(!json.isNull("error")){
        		Log.writeExpt(name,json.getJSONObject("error").getString("message"));
        		System.exit(1);
        	}else{
        		result = new String[0][25];
        	}
        	
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
        } 
        
        return result;
    }
    
    /*
    static String[][] getTopContents(String startNum){
    	
    	String[][] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn2.createStatement();
        	
        	sb = new StringBuffer();
			sb.append("SELECT T_SEQ AS D_SEQ			\n");
			sb.append("     , T_STAMP AS D_STAMP		\n");
			sb.append("     , S_SEQ						\n");
			sb.append("     , T_SITE AS D_SITE			\n");
			sb.append("     , T_BOARD AS D_BOARD		\n");
			sb.append("     , FROM_UNIXTIME(T_STAMP, '%Y-%m-%d %H:%i:%s') AS D_DATETIME\n");
			sb.append("     , T_TITLE AS D_TITLE		\n");
			sb.append("     , T_URL AS D_URL			\n");
			sb.append("     , SB_SEQ					\n");
			sb.append("     , '' AS D_CONTENT			\n");
			sb.append("     , 1 AS IF_SEQ				\n");
			sb.append("     , 'KOR'  AS MD_LANG			\n");
			sb.append("     , 0 AS D_IMAGE				\n");
			sb.append("  FROM TOP						\n");
			sb.append(" WHERE T_SEQ > " + startNum + "	\n");
			sb.append(" ORDER BY T_SEQ ASC				\n");
			sb.append(" LIMIT 100						\n");
			
			
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			result = new String[rs.getRow()][14];
			int row_idx = rs.getRow();
			int column_idx = 0;
			rs.beforeFirst();
			
			while(rs.next()){

				column_idx = 0;
				row_idx--;
				
				result[row_idx][column_idx++] =  "TOP";
				result[row_idx][column_idx++] =  rs.getString("D_SEQ");
				result[row_idx][column_idx++] =  rs.getString("D_STAMP");
				result[row_idx][column_idx++] =  rs.getString("S_SEQ");
				result[row_idx][column_idx++] =  rs.getString("D_SITE");
				result[row_idx][column_idx++] =  rs.getString("D_BOARD");
				result[row_idx][column_idx++] =  rs.getString("D_DATETIME");
				result[row_idx][column_idx++] =  rs.getString("D_TITLE");
				result[row_idx][column_idx++] =  rs.getString("D_URL");
				result[row_idx][column_idx++] =  rs.getString("SB_SEQ");
				result[row_idx][column_idx++] =  rs.getString("D_CONTENT");
				result[row_idx][column_idx++] =  rs.getString("IF_SEQ");
				result[row_idx][column_idx++] =  rs.getString("MD_LANG");
				result[row_idx][column_idx++] =  rs.getString("D_IMAGE");
			}
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    */
    
    static String[][] getSectionContents(String startNum){
    	
    	String[][] result = null;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        JSONObject json = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
        	
			sb.append("SELECT D_SEQ					\n");
			sb.append("     , D_STAMP				\n");
			sb.append("     , S_SEQ					\n");
			sb.append("     , D_SITE				\n");
			sb.append("     , D_BOARD				\n");
			sb.append("     , FROM_UNIXTIME(D_STAMP, '%Y-%m-%d %H:%i:%s') AS D_DATETIME\n");
			sb.append("     , D_TITLE				\n");
			sb.append("     , D_URL					\n");
			sb.append("     , SB_SEQ				\n");
			sb.append("     , IF_SEQ				\n");
			sb.append("     , L_ALPHA AS MD_LANG	\n");
			sb.append("     , 0 AS D_IMAGE			\n");
			sb.append("     , D_JSON_DATA			\n");
			sb.append("FROM SECTION_DATA 			\n");
			sb.append("WHERE D_SEQ > "+startNum+"	\n");
			sb.append("ORDER BY D_SEQ ASC			\n");
			sb.append("LIMIT 100					\n");
        	
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			rs.last();
			result = new String[rs.getRow()][14];
			int row_idx = rs.getRow();
			int column_idx = 0;
			rs.beforeFirst();
			
			String d_content = "";
			
			while(rs.next()){
				
				json = new JSONObject(rs.getString("D_JSON_DATA"));
				if(!json.isNull("CONTENT")){d_content = json.getString("CONTENT");}
				
				
				column_idx = 0;
				row_idx--;
				
				result[row_idx][column_idx++] =  "SECTION_DATA";
				result[row_idx][column_idx++] =  rs.getString("D_SEQ");
				result[row_idx][column_idx++] =  rs.getString("D_STAMP");
				result[row_idx][column_idx++] =  rs.getString("S_SEQ");
				result[row_idx][column_idx++] =  rs.getString("D_SITE");
				result[row_idx][column_idx++] =  rs.getString("D_BOARD");
				result[row_idx][column_idx++] =  rs.getString("D_DATETIME");
				result[row_idx][column_idx++] =  rs.getString("D_TITLE");
				result[row_idx][column_idx++] =  rs.getString("D_URL");
				result[row_idx][column_idx++] =  rs.getString("SB_SEQ");
				result[row_idx][column_idx++] =  d_content;
				result[row_idx][column_idx++] =  rs.getString("IF_SEQ");
				result[row_idx][column_idx++] =  rs.getString("MD_LANG");
				result[row_idx][column_idx++] =  rs.getString("D_IMAGE");
			}
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    /**
     * 옵션별 키워드 처리
     * @param html
     * @param word
     * @param op
     * @return
     */
    static boolean searchKeyword(String html, String word, int op, int near_len){
		boolean result = false;
		
		String[] key = word.split(" ");
		String sKey = "";
		//일반 AND
		if(op==1){
			for(int i=0; i<key.length; i++)
		     {
		     	sKey = ""+key[i];
		     	int check = html.indexOf(sKey);
		     	if ( check < 0 )
		         {
		     		return false;
		         }
		     }
		     return true;
		//인접	150 글자수 OR BYTE
		}else if(op==2){
			return nearSearch(html, key, near_len);
		//구문 A,B가 한구문
		}else if(op==3){
			if(html.indexOf(word)>-1){
				return true;
			}else{
				return false;
			}
		//고유명사 한국: 앞글자 뛰어쓰기, 영어 단어기준:뒤에도 공백	
		}else if(op==4){
			sKey = " "+word;
			
			//if(KorCheck(word)){
			//	sKey = " "+word;
			//} else{
			//	sKey = " "+word+" "; //수정가능
			//}
	    
	     	int check = html.indexOf(sKey);
	     	if ( check < 0 )
	         {
	     		return false;
	         }
	     	
		     return true;
		//고유명사2	 단어,앞뒤 공백
		}else if(op==5){
			return englishPropernoun(html, key);
			
		}
		
		return result;
	}
    
    //psValue 한글이 한글자라도 있으면 TRUE 반환
	static boolean KorCheck(String psValue){ 
		for(int i=0; i<psValue.length(); i++){
		    int c=psValue.charAt(i);		
		    if(c > 0xac00 && c < 0xd7a3){
		    	return true;
		    }
		}
		return false;
	}	
	
	
	/**
     * 키워드의 키워드 앞에 공백,특수문자가 와야함.
     * 키워드의 키워드 뒤에 공백,특수문자, 한글이 와야함.
     * @param html
     * @param key
     * @return
     */
    static boolean englishPropernoun(String html, String[] key ){
    	boolean result = true;
    	
    	boolean s_char = false;
    	boolean e_char = false;
    	
    	int keyIdx  = 0;
    	String alphabet = "";
    	//String alphabet = "[a-zA-Z0-9]"; 숫자포함
    	for(int i = 0; i < key.length; i++){
    		
    		
    		//키워드가 있는지 검색
    		keyIdx  = html.indexOf(key[i]);
    		if(keyIdx<0){
    			return false;
    		}
    		
    		//앞글자 확인
    		s_char = false;
    		if(keyIdx == 0){
    			s_char = true;
    		}else{
    			//공백통과
    			if(html.subSequence(keyIdx-1, keyIdx).equals(" ")){
    				s_char = true;
    			}
    		}
    		
    		//뒷글자 확인
    		e_char = false;
    		if((keyIdx+key[i].length()+1)> html.length()){
    			e_char = true;
    		}else{
    			//공백통과
    			if(html.subSequence(keyIdx+key[i].length(), (keyIdx+key[i].length()+1)).equals(" ")){
    				e_char = true;
    			}else{
    				//한글통과
    				int c = html.subSequence(keyIdx+key[i].length(), (keyIdx+key[i].length()+1)).charAt(0);
    				if(c > 0xac00 && c < 0xd7a3){
    					e_char = true;
    				}
    			}
    		}
    		
    		if(!(s_char && e_char)){
    			return false;
    		}
    		
    	}
    	
    	return result;
    }
    
    /**
	 * <p> key[0] 이 노출된 지점에서 앞뒤로 iNearByte(byte) 이내에 key[n]이 모두 존재 하는지 검사한다.</p>
	 * 
	 * @author Ryu Seung Wan
	 */
    static boolean nearSearch(String strHtml, String[] key, int iNearByte){  	
   	    	    	    	
    	int tempIndex1 = 0;
    	int lastIndex = 0;
    	int nextIndex = 1;
    	boolean bFindKey = false;
    	String[] tempKey = null;
    	String searchHtml = "";
    	String frontHtml = "";
    	String backHtml = "";
    	
    	//key = searchGumunKey(key);
    	
    	//대소문자 처리
    	/*for(int i=0;i<key.length;i++){
			key[i] = key[i].toLowerCase();			
		}*/
    	
    	tempIndex1 = nextIndexOf(strHtml, key[0], nextIndex);
		lastIndex = lastIndexOf(strHtml, key[0]);
		
		//복수 키워드
		if(key.length>1)
		{
			if(tempIndex1 != -1){
				
				tempKey = new String[key.length-1];
				
				for(int j=1;j<key.length;j++){					
					tempKey[j-1] = key[j];
				}
				
				findKeyLoop:while (tempIndex1 <= lastIndex){
					tempIndex1 = nextIndexOf(strHtml, key[0], nextIndex);
					++nextIndex;
					if(tempIndex1 != -1){
						
						// 키의 위치가 첫글짜 이면 (tempIndex1 == 0) 앞쪽은 자르지 않는다.
	
						// 키의 위치로 부터 앞쪽으로 iNearByte 만큼 자른다.
						frontHtml = (tempIndex1 - iNearByte) > 0 ? 
								strHtml.substring(tempIndex1 - iNearByte, tempIndex1) : 
								strHtml.substring(0, tempIndex1); 
	
						
						// 키의 위치로 부터 뒷쪽으로 (키.length + iNearByte) 만큼 자른다.
						backHtml = strHtml.length() - (key[0].length() + iNearByte + 1) > tempIndex1 ? 
								strHtml.substring(tempIndex1, tempIndex1 + key[0].length() + iNearByte) : 
								strHtml.substring(tempIndex1, strHtml.length());
	    				
						
						searchHtml = frontHtml + backHtml;
											
						bFindKey = indexOfAll(searchHtml, tempKey);					
	    				
	    				if(bFindKey) break findKeyLoop;
	    				
					}else{
						break findKeyLoop;
					}				
				}
			}
		//단일 키워드
		}else{	
			//key[0] = " "+key[0];					 
			
			int check = strHtml.indexOf(key[0]);
			if ( check >=0 )
			{
				//System.out.println("key[0]:"+key[0]);
				bFindKey  =  true;
			}		    		
		}
    	return bFindKey;
    }
    
    static int nextIndexOf(String str, String searchStr, int nextIndex) {
        if (str == null || searchStr == null || nextIndex <= 0) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return 0;
        }
        int found = 0;
        int index = -1;
        do {
            index = str.indexOf(searchStr, index + 1);
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < nextIndex);
        return index;
    }
    
    static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr);
    }
    
    static boolean indexOfAll(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return false;
        }
        int sz = searchStrs.length;
        int tmp = 0;
        boolean btmp = true;

        for (int i = 0; i < sz; i++) {
            String search = searchStrs[i];
            if (search == null) {
            	btmp = false;
                continue;
            }
            tmp = str.indexOf(search);
            if (tmp == -1) {
                btmp = false;
                continue;
            }
        }
        return btmp;
    }
    
    /**
     * 최대 번호  가져오기
     * @return
     * @author Lim Seung Chul
     */
    static int getNewNum()
    {
        int result = 0;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;       

		try{
            sb = new StringBuffer();
            
            sb.append("SELECT MAX(A.MD_SEQ) AS MD_SEQ																\n");
            sb.append("FROM (																						\n");
            sb.append("      SELECT IFNULL(MAX(MD_SEQ),1) AS MD_SEQ FROM "+addTableName+"MAP_META_SEQ				\n");
            sb.append("       UNION ALL																				\n");
            sb.append("       SELECT IFNULL(MAX(MD_SEQ),1) AS MD_SEQ FROM "+addTableName+"EXCEPTION_MAP_META_SEQ	\n");
            sb.append("     )A																						\n");

			System.out.println( sb.toString() );
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery( sb.toString() );

			if( rs.next() )
			{
				result = rs.getInt("MD_SEQ");
			}

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static HashMap<String, String[]> getExSite(){
    	
    	HashMap<String, String[]> map = new HashMap<String, String[]>();
    	
        int result = 0;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;       

        String s_seq  = "";
		String ex_keywords[]  = null;
        
		try{
			stmt = dbconn1.createStatement();
			
            sb = new StringBuffer();
            sb.append(" SELECT S_SEQ, EX_KEYWORD FROM EX_SITE \n");

			//System.out.println( sb.toString() );
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				s_seq = rs.getString("S_SEQ");
				ex_keywords = rs.getString("EX_KEYWORD").split("\n");
				
				for(int i=0; i<ex_keywords.length ; i++){
					ex_keywords[i] = ex_keywords[i].trim();
				}
				
				map.put(s_seq, ex_keywords);
			}

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
        
        return map;
    }
    
    
    static boolean insertIdx(int newNum, String sg_seq, ArrayList<String[]> arData, String excepTable ){
    	
    	boolean result = false;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;

		try{
			
            sb = new StringBuffer();
            sb.append(" INSERT INTO "+addTableName+""+excepTable+"IDX (MD_SEQ, K_XP, K_YP, K_ZP, SG_SEQ, I_STATUS, M_SEQ, ISSUE_CHECK, I_DELDATE ) 	\n");
            sb.append(" VALUES (  		\n");
            sb.append(" 		?		\n");
            sb.append("       , ?		\n");
            sb.append("       , ?		\n");
            sb.append("       , ?		\n");
            sb.append("       , ?		\n");
            sb.append("       , 'N'  	\n");
            sb.append("       ,  0   	\n");
            sb.append("       , 'N'   	\n");
            sb.append("       , null   	\n");
            sb.append("        ) 		\n");
                 
			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			String[] arBean = null;
			for(int i =0; i < arData.size(); i++){
				arBean = arData.get(i);
				
				int paNum = 1;
				pstmt.clearParameters();
				pstmt.setInt(paNum++, newNum);
				pstmt.setInt(paNum++, Integer.parseInt(arBean[0]));
				pstmt.setInt(paNum++, Integer.parseInt(arBean[1]));
				pstmt.setInt(paNum++, Integer.parseInt(arBean[2]));
				pstmt.setInt(paNum++, Integer.parseInt(sg_seq));
				pstmt.execute();
				result = true;
			}

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static boolean insertMeta( int newNum
    						 , String d_seq
    		                 , String md_site_name
    		                 , String md_menu_name
    		                 , String md_type
    		                 , String md_date
    		                 , String md_title
    		                 , String md_url
    		                 , String md_name_count
    		                 , int md_pseq
    		                 , String s_seq
    		                 , String md_img
    		                 , String l_alpha
    		                 , String excepTable
    		                 , String ts_type
    		                 , String ts_rank
    		                 , String nick_user
    		                 , String id_user
    		                 , String memberCount
    		                 , String visitCount
    		                 , String siteName
    		                 , String exName
    		                 ){
    	boolean result = false;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;

		try{
			
            sb = new StringBuffer();
            sb.append("INSERT INTO "+addTableName+""+excepTable+"META (      			\n");	      
            sb.append(" MD_SEQ	             			\n");
            sb.append(",D_SEQ	             			\n");
            sb.append(",MD_SITE_NAME	             	\n");
            sb.append(",MD_MENU_NAME	             	\n");
            sb.append(",MD_TYPE	                     	\n");
            sb.append(",MD_DATE	                     	\n");
            sb.append(",MD_TITLE                     	\n");
            sb.append(",MD_URL	                     	\n");
            sb.append(",MD_SAME_COUNT	             	\n");
            sb.append(",MD_PSEQ	                     	\n");
            sb.append(",S_SEQ	                  		\n");
            sb.append(",MD_IMG	               		    \n");
            sb.append(",L_ALPHA	                  		\n");
            sb.append(",TS_TYPE	                  		\n");
            sb.append(",TS_RANK	 	                 	\n");
            sb.append(",USER_NICK	 	                \n");
            sb.append(",USER_ID 	                 	\n");
            sb.append(",CAFE_MEMBER_COUNT               \n");
            sb.append(",BLOG_VISIT_COUNT                \n");
            sb.append(",CAFE_NAME                		\n");
            if(!excepTable.equals("")){
            	sb.append(",EX_NAME	                  \n");
            }
            sb.append(")	                  			\n");
            sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"+ (!excepTable.equals("") ? ",?" : "") +")		\n");

			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			
			int paNum = 1;
			pstmt.clearParameters();
			pstmt.setInt(paNum++, newNum);
			pstmt.setString(paNum++, d_seq);
			pstmt.setString(paNum++, md_site_name);
			if(md_menu_name.length() > 128){
				md_menu_name = md_menu_name.substring(0,128);
			}
			pstmt.setString(paNum++, md_menu_name);
			pstmt.setInt(paNum++, Integer.parseInt(md_type));
			pstmt.setString(paNum++, md_date);
			pstmt.setString(paNum++, md_title);
			pstmt.setString(paNum++, md_url);
			pstmt.setString(paNum++, md_name_count);
			pstmt.setInt(paNum++, md_pseq);
			pstmt.setInt(paNum++, Integer.parseInt(s_seq));
			pstmt.setInt(paNum++, Integer.parseInt(md_img));
			pstmt.setString(paNum++, l_alpha);
			pstmt.setInt(paNum++, Integer.parseInt(ts_type));
			pstmt.setInt(paNum++, Integer.parseInt(ts_rank==null?"999":ts_rank));
			if(nick_user.length() > 64){
				nick_user = nick_user.substring(0, 64);
			}
			pstmt.setString(paNum++, nick_user);
			if(id_user.length() > 64){
				id_user = id_user.substring(0, 64);
			}
			pstmt.setString(paNum++, id_user);
			
			if(memberCount.length() > 64){
				memberCount = memberCount.substring(0, 64);
			}
			pstmt.setString(paNum++, memberCount);
			
			if(visitCount.length() > 64){
				visitCount = visitCount.substring(0, 64);
			}
			pstmt.setString(paNum++, visitCount);
			pstmt.setString(paNum++, siteName);
			
			if(!excepTable.equals("")){
				pstmt.setString(paNum++, exName);
            }
			pstmt.execute();
			result = true;

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static boolean insertData( int newNum, String md_content, String excepTable){
    	boolean result = false;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
        int paNum = 1;
		try{
			
            sb = new StringBuffer();
            sb.append("INSERT INTO "+addTableName+""+excepTable+"DATA (   	\n");	      
            sb.append(" MD_SEQ	            		\n");
            sb.append(",MD_CONTENT)	        		\n");
            sb.append(" VALUES (?, ?)				\n");
                 
			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			
			pstmt.clearParameters();
			pstmt.setInt(paNum++, newNum);
			pstmt.setString(paNum++, md_content);
			pstmt.execute();
			result = true;

		}catch(SQLException ex) { 
			if(ex.getErrorCode() == 1366){
				try{
					paNum = 1;
					pstmt.clearParameters();
		        	
		        	StringBuffer reCon = new StringBuffer();
		        	String content = md_content;
		        	char c = 0;
		        	
		        	for(int i =0; i < content.length(); i++){
		        		c = content.charAt(i);
		        		if (false == (0xD800 <= c && c <= 0xDBFF || 0xDC00 <= c && c <= 0xDFFF) )
		        		{
		        			reCon.append(c);
		        		}
		        	}
		        	
		        	pstmt.setInt(paNum++, newNum);
		        	pstmt.setString(paNum++, reCon.toString());
		        	pstmt.execute();
		        	
				}catch (Exception e) {
					Log.writeExpt(name,ex.toString());
					System.exit(1);
				}
				  
			}else{
				Log.writeExpt(name,ex.toString() + " : errorCode("+ex.getErrorCode()+")" );
				System.exit(1);
			}
		}catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static boolean insertTweet( int newNum
    		                  , String t_tweet_id
    		                  , String t_user_id
    		                  , String t_is_rt
    		                  , String t_followers
    		                  , String t_following
    		                  , String t_tweet
    		                  , String t_source
    		                  , String l_alpha
    		                  , String md_title
    		                  , String md_date
    		                  , String t_name
    		                  , String t_profile_image
    		                  , String excepTable
    		                  ){
    	
    	boolean result = false;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;

		try{
			
            sb = new StringBuffer();
            sb.append("INSERT INTO "+addTableName+""+excepTable+"TWEET (      							\n");	      
            sb.append(" MD_SEQ	             								\n");
            sb.append(",T_TWEET_ID	             							\n");
            sb.append(",T_USER_ID	             							\n");
            sb.append(",T_IS_RT	             								\n");
            sb.append(",T_FOLLOWERS	                     					\n");
            sb.append(",T_FOLLOWING	                     					\n");
            sb.append(",T_TWEET                     						\n");
            sb.append(",T_SOURCE	                     					\n");
            sb.append(",L_ALPHA	      		             					\n");
            sb.append(",MD_TITLE	                     					\n");
            sb.append(",MD_DATE	                  		   					\n");
            sb.append(",T_NAME	                     						\n");
            sb.append(",T_PROFILE_IMAGE)	                				\n");
            sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)		\n");
                 
			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			
			int paNum = 1;
			pstmt.clearParameters();
			pstmt.setInt(paNum++, newNum);
			pstmt.setString(paNum++, t_tweet_id);
			pstmt.setString(paNum++, t_user_id);
			pstmt.setString(paNum++, t_is_rt);
			pstmt.setString(paNum++, t_followers);
			pstmt.setString(paNum++, t_following);
			pstmt.setString(paNum++, t_tweet);
			pstmt.setString(paNum++, t_source);
			pstmt.setString(paNum++, l_alpha);
			pstmt.setString(paNum++, md_title);
			pstmt.setString(paNum++, md_date);
			pstmt.setString(paNum++, t_name);
			pstmt.setString(paNum++, t_profile_image);
			pstmt.execute();
			result = true;

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static boolean insertFaceBook( int newNum
            ,String FD_CRAWL_STAMP,String FD_ID,String FD_TYPE,String FD_CREATED_TIME,String FD_UPDATED_TIME,String FD_FROM_ID,String FD_FROM_NAME,
            String FD_LIKES_COUNT,String FD_COMMENTS_COUNT,String FD_SHARES_COUNT,String FD_MESSAGE,String FD_STORY,String FD_PICTURE,String FD_LINK,
            String FD_NAME,String FD_CAPTION,String FD_URL,String FU_SEQ,String D_DATETIME, String excepTable
            ){

		boolean result = false;
		PreparedStatement pstmt = null;
		StringBuffer sb = null;
	
		try{
	
			sb = new StringBuffer();
			sb.append(" INSERT INTO "+excepTable+"FACEBOOK_DATA ( 						\n");
			sb.append(" 	FD_SEQ, 													\n");
			sb.append(" 	FD_CRAWL_STAMP, 											\n");
			sb.append(" 	FD_ID,							 							\n");
			sb.append(" 	FD_TYPE,									 				\n");
			sb.append(" 	FD_CREATED_TIME,					 						\n");
			sb.append(" 	FD_UPDATED_TIME, 											\n");
			sb.append(" 	FD_FROM_ID,						 							\n");
			sb.append(" 	FD_FROM_NAME, 												\n");
			sb.append(" 	FD_LIKES_COUNT, 											\n");
			sb.append(" 	FD_COMMENTS_COUNT, 											\n");
			sb.append(" 	FD_SHARES_COUNT,				 							\n");
			sb.append(" 	FD_MESSAGE,							 						\n");
			sb.append(" 	FD_STORY,							 						\n");
			sb.append(" 	FD_PICTURE, 												\n");
			sb.append(" 	FD_LINK,							 						\n");
			sb.append(" 	FD_NAME, 													\n");
			sb.append(" 	FD_CAPTION, 												\n");
			sb.append(" 	FD_URL, 													\n");
			sb.append(" 	FU_SEQ 														\n");
			sb.append(" ) VALUES ( 														\n");
			sb.append(" 	?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? 	\n");
			sb.append(" ) 																\n");
			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
		
			int paNum = 1;
			pstmt.clearParameters();
			pstmt.setInt(paNum++, newNum);
			pstmt.setInt(paNum++, Integer.parseInt(FD_CRAWL_STAMP));
			pstmt.setString(paNum++, FD_ID);
			pstmt.setString(paNum++, FD_TYPE);
			pstmt.setInt(paNum++, Integer.parseInt(FD_CREATED_TIME));
			pstmt.setInt(paNum++, Integer.parseInt(FD_UPDATED_TIME));
			pstmt.setString(paNum++, FD_FROM_ID);
			pstmt.setString(paNum++, FD_FROM_NAME);
			pstmt.setString(paNum++, FD_LIKES_COUNT);
			pstmt.setString(paNum++, FD_COMMENTS_COUNT);
			pstmt.setString(paNum++, FD_SHARES_COUNT);
			pstmt.setString(paNum++, FD_MESSAGE);
			pstmt.setString(paNum++, FD_STORY);
			pstmt.setString(paNum++, String.valueOf(FD_PICTURE));
			pstmt.setString(paNum++, FD_LINK);
			pstmt.setString(paNum++, FD_NAME);
			pstmt.setString(paNum++, FD_CAPTION);
			pstmt.setString(paNum++, FD_URL);
			pstmt.setInt(paNum++, Integer.parseInt(FU_SEQ));
			pstmt.execute();
			result = true;
	
	} catch(Exception ex) {
		Log.writeExpt(name,ex.toString());
		ex.printStackTrace();
		System.exit(1);
	} finally {
		sb = null;
		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	}
		return result;
	}
    
    static boolean insertMapMetaSeq( int newNum, String d_seq, String excepTable){
    	boolean result = false;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
        int paNum = 1;
		try{
			
            sb = new StringBuffer();
            sb.append("INSERT INTO "+addTableName+""+excepTable+"MAP_META_SEQ (   \n");	      
            sb.append(" MD_SEQ	            		\n");
            sb.append(",D_SEQ	        			\n");
            sb.append(",SAME_CHK)	        			\n");
            sb.append(" VALUES (?, ?, 0)				\n");
                 
			//System.out.println( sb.toString() );
			pstmt = dbconn1.createPStatement(sb.toString());
			
			pstmt.clearParameters();
			pstmt.setInt(paNum++, newNum);
			pstmt.setLong(paNum++, Long.parseLong(d_seq));
			pstmt.execute();
			result = true;

		}catch(SQLException ex) {
			if(ex.getErrorCode() == 1062){
				
				Log.writeExpt(name,"=====중복오류 처리시작.=====");
				Log.writeExpt(name,ex.toString());
				
				String md_seq = getMdSeq(d_seq, excepTable);
				DeleteData(md_seq, excepTable);
				result = insertMapMetaSeq(newNum, d_seq, excepTable);
				
				Log.writeExpt(name,"=====중복오류 처리 끝.=====");
				
			}else{
				Log.writeExpt(name,ex.toString());
	        	ex.printStackTrace();
	        	System.exit(1);
			}
			
        }catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static boolean updateLastTransNum( String transferNo, String lastTransNum, String lastDate  )
    {
        boolean result = false;

        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            sb.append(" UPDATE SYSTEM_TRANSFER SET ST_LAST_SEQ="+lastTransNum+" , ST_CRAWL_STAMP = "+lastDate+", ST_UPDATE_STAMP = UNIX_TIMESTAMP() WHERE ST_API_KEY='"+transferNo+"' \n");

			//System.out.println( sb.toString() );
			stmt = dbconn1.createStatement();
			if( stmt.executeUpdate( sb.toString() ) > 0 ) result = true;

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    static void DeleteData(String md_seq, String excepTable){
    
    	Statement stmt = null;

		try{
			
			stmt = dbconn1.createStatement();
			StringBuffer sb = null;
			
			
			sb = new StringBuffer();
			sb.append("DELETE FROM "+addTableName+""+excepTable+"MAP_META_SEQ WHERE MD_SEQ = "+md_seq + "\n");
			//System.out.println(sb.toString());
            stmt.execute(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM "+addTableName+""+excepTable+"META WHERE MD_SEQ = "+md_seq + "\n");
			//System.out.println(sb.toString());
            stmt.execute(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM "+addTableName+""+excepTable+"DATA WHERE MD_SEQ = "+md_seq + "\n");
			//System.out.println(sb.toString());
            stmt.execute(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM "+addTableName+""+excepTable+"IDX WHERE MD_SEQ = "+md_seq + "\n");
			//System.out.println(sb.toString());
            stmt.execute(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM "+addTableName+""+excepTable+"TWEET WHERE MD_SEQ = "+md_seq + "\n");
			//System.out.println(sb.toString());
            stmt.execute(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM "+addTableName+""+excepTable+"FACEBOOK_DATA WHERE FD_SEQ = "+md_seq + "\n");
			//System.out.println(sb.toString());
            stmt.execute(sb.toString());
            

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
    }
    
    static String getMdSeq(String d_seq, String excepTable ){
    	String result = "";
    	Statement stmt = null;
    	ResultSet rs = null;
		try{
			
			stmt = dbconn1.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT MD_SEQ FROM "+addTableName+""+excepTable+"MAP_META_SEQ WHERE D_SEQ =" + d_seq + "\n");
			//System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	result = rs.getString("MD_SEQ");
            }
            
		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        }
		return result;
    }
    
    static String convertFacebookURL(String inputStr) {
    	String returnStr = "http://www.facebook.com/";
    	String middleURL = "/posts/";
    	
    	String[] arrStr = inputStr.split("_");
    	
    	for (int i=0; i<arrStr.length; i++) {
    		if (i == 0) {
    			returnStr += arrStr[i].toString();
    		} else if (i == 1) {
    			returnStr += middleURL + arrStr[i].toString();
    		}
    	}
    	
		return returnStr;
    }
    
    public static String GetHtmlPost(String sUrl, String param) {
		StringBuffer html = new StringBuffer();
		try {
			String line = null;
			URL aURL = new URL(sUrl);
			HttpURLConnection urlCon = (HttpURLConnection) aURL.openConnection();
			urlCon.setRequestMethod("POST");

			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			OutputStream out = urlCon.getOutputStream();
			out.write(param.getBytes());
			out.flush();

			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"utf-8"));
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(aURL.openStream()));
			while ((line = br.readLine()) != null) {
				html.append(line);
			}

			aURL = null;
			urlCon = null;
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.writeExpt("CRONLOG", "MalformedURLException : " + e);
			// 프로세스 종료
			// System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			Log.writeExpt("CRONLOG", "IOException :" + e);
			// 프로세스 종료
			// System.exit(1);
		}
		return html.toString();
	}
    
    public static String stampToDateTime(String stamp, String format) {
    	long unixSeconds = Long.parseLong(stamp);
    	Date date = new Date(unixSeconds*1000L); 
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	//sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
    	String formattedDate = sdf.format(date);    	
    	return formattedDate;
    	
    }
    
    /***************************************************************/
    /**테스트기******************************************************/
    
    
    /*
    public static void main_( String[] args ){
    	
    	System.out.println("시작");
    	
    	try{
    		dbconn1 = new DBconn(); //로컬디비
    		dbconn1.getSubDirectConnection();
    		
    		//사이트
        	String[][] siteList = getSite();
        	
        	//키워드
        	String[][] keyword = getKeyword(1);
        	
        	//제외키워드
        	String[][] exKeyword = getKeyword(2);
        	
        	//전제제외키워드
        	String[][] allExKeyword = getAllExKeyword();
        	
        	//제외 사이트
        	HashMap<String, String[]> exSite_map = getExSite();
    		
    		String[] title_seq = getTestTitle("","1497196350");
    		
    		
    		//수집사이트 체크
        	boolean PASSAGE = false;
        	for(int i = 0; i < siteList.length; i++){
        		if(siteList[i][1].equals(title_seq[2])){
        			PASSAGE = true;
        			break;
        		}
        	}
        	if(PASSAGE){
        		System.out.println("사이트 통과");
        	}else{
        		System.out.println("사이트 실패");
        	}
    		
        	//제외사이트
    		PASSAGE = true;
    		String[] exUrl = null;
    		String strExurl = "";
    		if(exSite_map.size() > 0){
    			
    			exUrl = exSite_map.get(title_seq[2]);
    			if (exUrl != null){
    				for (int i = 0; i<exUrl.length; i++) {
    					
						// 제외사이트 조건에 걸리면
    					
						if(title_seq[3].indexOf(exUrl[i]) > -1){
							strExurl = exUrl[i];
							PASSAGE = false;
							break;
						}
					}
    			}
    		}
    		
    		if(!PASSAGE){
    			System.out.println("제외 사이트 걸림 : " + strExurl);
        	}else{
        		System.out.println("제외 사이트 통과");
        	}
        		
        	
    		//전체 제외 키워드 필터
        	PASSAGE = true;
        	String setHtml1 = " "+title_seq[0]+" "+title_seq[1]+" ";
        	String setHtml2 = " "+title_seq[0]+" ";
        	String setHtml3 = " "+title_seq[1]+" ";
        	String setHtml = "";
        	String kk = "";
        	for(int i = 0; i < allExKeyword.length; i++){
        		
        		if(allExKeyword[i][1].equals("1")){
        			setHtml = setHtml1;
        		}else if(allExKeyword[i][1].equals("2")){
        			setHtml = setHtml2;
        		}else if(allExKeyword[i][1].equals("3")){
        			setHtml = setHtml3;
        		}
        		
        		//전체 제외키워드 걸림
        		if(searchKeyword(setHtml,allExKeyword[i][0],3)){ //일반 인덱싱
        			kk = allExKeyword[i][0];
        			PASSAGE = false;
        			break;
        		}
        		
        		
        	}
        	if(!PASSAGE){
        		System.out.println("제외키워드 걸림 : " + kk);
        	}else{
        		System.out.println("제외키워드 통과");
        	}
    		
        	PASSAGE = false;
        	boolean keyPass = false;
        	setHtml = " "+title_seq[0]+" "+title_seq[1]+" ";
        	 
        	for(int i = 0; i < keyword.length; i++){
        		keyPass = false;
				if(searchKeyword(setHtml, keyword[i][4], Integer.parseInt(keyword[i][3]))){
					keyPass = true;
					if(!keyword[i][6].equals("0")){
						//제외 키워드 필터
						for(int j =0; j < exKeyword.length; j++){
							
							if(   keyword[i][0].equals(exKeyword[j][0])
							   && keyword[i][1].equals(exKeyword[j][1])
							   && keyword[i][2].equals(exKeyword[j][2])
							  ){
								if(searchKeyword(setHtml, exKeyword[j][4], Integer.parseInt(exKeyword[j][3]))){
									System.out.println("[일반 제외키워드 걸림]");
									keyPass = false;
									break;
								}
							}
						}
					}
				}
				
				if(keyPass){
					System.out.println("키워드 걸림 : " + keyword[i][4] + "("+keyword[i][0] + " / " + keyword[i][1] + " / " + keyword[i][2] + " / " + keyword[i][3] +")");
				}
        	}
    		
    		
    	}catch(Exception ex) {
			//Log.writeExpt(name,ex.getMessage());
	    	ex.printStackTrace();
	    	System.exit(1);
	    }finally {
	        if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
	    }
	    	
    	System.out.println("끝");
    }
    
    static String[] getTestTitle(String type, String d_seq){
    	String[] result = new String[4];
    	Statement stmt = null;
    	ResultSet rs = null;
    	StringBuffer sb = null;
    	String regexpStr = "[',|@#$%\\^&*\\s\\[\\](){}‘’<>\"+-./:=?_`´~§±·]";
    	
		try{
			
			stmt = dbconn1.createStatement();
            
        
			sb = new StringBuffer();
            sb.append("SELECT MD_TITLE									\n");
            sb.append("     , MD_CONTENT								\n");
            sb.append("     , A.S_SEQ									\n");
            sb.append("     , A.MD_URL									\n");
            sb.append( "FROM "+type+"META A 							\n");
            sb.append("    , "+type+"MAP_META_SEQ B						\n");
            sb.append("    , "+type+"DATA C								\n");
            sb.append("WHERE A.MD_SEQ = B.MD_SEQ						\n");
            sb.append("  AND A.MD_SEQ = C.MD_SEQ						\n");
            sb.append("  AND B.D_SEQ = "+d_seq+"						\n");
            
            rs = stmt.executeQuery(sb.toString());
            
            while(rs.next()){
            	result[0] = rs.getString("MD_TITLE").toLowerCase().replaceAll(regexpStr, " ").replaceAll("\\s{1,}"," ");;
            	result[1] = rs.getString("MD_CONTENT").toLowerCase().replaceAll(regexpStr, " ").replaceAll("\\s{1,}"," ");;
            	result[2] = rs.getString("S_SEQ");
            	result[3] = rs.getString("MD_URL");
            }
            
		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        }
		return result;
    }
    */
    /***************************************************************/
}
