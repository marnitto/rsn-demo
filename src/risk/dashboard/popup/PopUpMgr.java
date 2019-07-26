package risk.dashboard.popup;

import risk.DBconn.*;
import risk.util.*;

import java.sql.*;
import java.util.*;

public class PopUpMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private StringBuffer sb = null;
	private ResultSet rs    = null;
	
	private int total_count = 0;
	private String max_id_seq = "1";
	private String min_id_seq = "0";
	
	
	private static final String PRESS = "36";
	private static final String COMMUNITY = "32";
	private static final String BLOG = "31";
	private static final String CAFE = "33";
	private static final String TWIT = "29";
	private static final String ETC = "37";
	
	public int getTotal_count(){
		return this.total_count;
	}
	
	
	public List getPopupData(String sDate, String eDate, String setSTime, String setETime, Map paramMap, String idx_hint){
		
		return getPopupData(0, 0, sDate, eDate, setSTime, setETime, paramMap, idx_hint);
		
	}

	/**
	 * 팝업 로우 데이터 구함
	 * 날짜를 제외한 모든 파라미터는 IN절에 넣습니다.
	 * 
	 * @param nowNum
	 * @param row
	 * @param sDate
	 * @param eDate
	 * @param setSTime
	 * @param setETime
	 * @param paramMap 파라미터 Map
	 * @param idx_hint 힌트 유무 플래그(2016.12.05 기준 사용안함)
	 * @return
	 */
	public List getPopupData(int nowPage, int row, String sDate, String eDate, String setSTime, String setETime, Map paramMap, String idx_hint){
		
		List rList = new ArrayList();
		String is_rt = "";
		try{ 
			
			String setSDate = sDate + " " + setSTime;
			String setEDate = eDate + " " + setETime;

			//getMaxMinIdSeq(setSDate, setEDate);
			if("Y".equals(paramMap.get("RTCHECK").toString())){
				is_rt = "T";	//트위터 RT 필더 ON
			}/* else {
				is_rt = "T";
			}*/
			
			if(nowPage != 0 && row !=0) {
				if(!"".equals(paramMap.get("SEARCHKEY").toString()) || !"".equals(paramMap.get("DIC_SEQ").toString())){
					getCountData_REAL(setSDate, setEDate, paramMap, idx_hint, is_rt);
					//getCountData_REAL(paramMap, searchKey, dic_seq);
				}else {
					//getCountData_STA(sDate, eDate, paramMap);
					getCountData_STA(sDate, eDate, paramMap, is_rt);
				}	
			}
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 데이터 데이터 호출  */							 				\n");
			sb.append("	SELECT ID.ID_SEQ	 					    				\n");
			sb.append("			,ID.MD_SITE_NAME	 			    				\n");
			sb.append("			,ID.SG_SEQ			 			    				\n");
			sb.append("			,ID.S_SEQ			 			    				\n");
			sb.append("			,ID.ID_TITLE		 			    				\n");
			sb.append("			,ID.ID_CONTENT			 			    			\n");
			sb.append("			,ID.ID_URL			 			    				\n");
			sb.append("			,FN_GET_ISSUE_CODE_NAME(ID.ID_SEQ, 9) AS SENTI		\n");
			if( !"".equals(paramMap.get("PRODUCT")) )
				sb.append("			,FN_GET_ISSUE_CODE_NAME2(ID.ID_SEQ, 7, "+paramMap.get("PRODUCT")+") AS PRODUCT	\n");
			else
				sb.append("			,FN_GET_ISSUE_CODE_NAME(ID.ID_SEQ, 7) AS PRODUCT	\n");
			sb.append("			,ID.MD_DATE			 			    				\n");
			/*if("Y".equals(idx_hint)){
				sb.append("	  FROM ISSUE_DATA ID USE INDEX(IDX_MD_DATE)             \n");
			}else{
				sb.append("	  FROM ISSUE_DATA ID 						           \n");	
			}*/
			sb.append("	  FROM ISSUE_DATA ID USE INDEX(IDX_MD_DATE)             \n");
			
			if( !"".equals(paramMap.get("PRODUCT")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_PDT ON ID.ID_SEQ = IDC_PDT.ID_SEQ AND IDC_PDT.IC_TYPE = 7 AND IDC_PDT.IC_CODE IN ("+paramMap.get("PRODUCT")+")	\n");
			
			else if( !"".equals(paramMap.get("CATEGORY")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_CATEGORY ON ID.ID_SEQ = IDC_CATEGORY.ID_SEQ AND IDC_CATEGORY.IC_TYPE = 6 AND IDC_CATEGORY.IC_CODE IN ("+paramMap.get("CATEGORY")+")	\n");
			
			else if( !"".equals(paramMap.get("BRAND")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_BRAND ON ID.ID_SEQ = IDC_BRAND.ID_SEQ AND IDC_BRAND.IC_TYPE = 5 AND IDC_BRAND.IC_CODE IN ("+paramMap.get("BRAND")+")	\n");
			
			else if( !"".equals(paramMap.get("COMPANY")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_COM ON ID.ID_SEQ = IDC_COM.ID_SEQ AND IDC_COM.IC_TYPE = 4 AND IDC_COM.IC_CODE IN ("+paramMap.get("COMPANY")+")	\n");
			
			if( !"".equals(paramMap.get("SENTI")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_SENTI ON ID.ID_SEQ = IDC_SENTI.ID_SEQ AND IDC_SENTI.IC_TYPE = 9 AND IDC_SENTI.IC_CODE IN ("+paramMap.get("SENTI")+")	\n");
			
			if( !"".equals(paramMap.get("COMPLAIN")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_COMPLAIN ON ID.ID_SEQ = IDC_COMPLAIN.ID_SEQ AND IDC_COMPLAIN.IC_TYPE = 10 AND IDC_COMPLAIN.IC_CODE IN ("+paramMap.get("COMPLAIN")+")	\n");
			
			if( !"".equals(paramMap.get("KBF")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_KBF ON ID.ID_SEQ = IDC_KBF.ID_SEQ AND IDC_KBF.IC_TYPE = 11 AND IDC_KBF.IC_CODE IN ("+paramMap.get("KBF")+")	\n");
			
			if( !"".equals(paramMap.get("SENSE")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_SENSE ON ID.ID_SEQ = IDC_SENSE.ID_SEQ AND IDC_SENSE.IC_TYPE = 12 AND IDC_SENSE.IC_CODE IN ("+paramMap.get("SENSE")+")	\n");
			
			if( !"".equals(paramMap.get("MATERIAL")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_MATERIAL ON ID.ID_SEQ = IDC_MATERIAL.ID_SEQ AND IDC_MATERIAL.IC_TYPE = 14 AND IDC_MATERIAL.IC_CODE IN ("+paramMap.get("MATERIAL")+")	\n");
			
			else if( !"".equals(paramMap.get("MATERIAL_GROUP")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_MATERIAL_GROUP ON ID.ID_SEQ = IDC_MATERIAL_GROUP.ID_SEQ AND IDC_MATERIAL_GROUP.IC_TYPE = 13 AND IDC_MATERIAL_GROUP.IC_CODE IN ("+paramMap.get("MATERIAL_GROUP")+")	\n");
			
			if( !"".equals(paramMap.get("ISSUE")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_ISSUE ON ID.ID_SEQ = IDC_ISSUE.ID_SEQ AND IDC_ISSUE.IC_TYPE = 16 AND IDC_ISSUE.IC_CODE IN ("+paramMap.get("ISSUE")+")	\n");
			
			else if( !"".equals(paramMap.get("ISSUE_GROUP")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_ISSUE_GROUP ON ID.ID_SEQ = IDC_ISSUE_GROUP.ID_SEQ AND IDC_ISSUE_GROUP.IC_TYPE = 15 AND IDC_ISSUE_GROUP.IC_CODE IN ("+paramMap.get("ISSUE_GROUP")+")	\n");
			
			if( !"".equals(paramMap.get("TPO")) ) {
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_TPO ON ID.ID_SEQ = IDC_TPO.ID_SEQ AND IDC_TPO.IC_TYPE = 17 AND IDC_TPO.IC_CODE IN ("+paramMap.get("TPO")+")		\n");
			}
			if( !"".equals(paramMap.get("RISK_KEYWORD")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_RISK_KEY ON ID.ID_SEQ = IDC_RISK_KEY.ID_SEQ AND IDC_RISK_KEY.IC_TYPE = 18 AND IDC_RISK_KEY.IC_CODE IN ("+paramMap.get("RISK_KEYWORD")+")	\n");
			
			if( !"".equals(paramMap.get("RELATION_KEYWORD")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_REL_KEY ON ID.ID_SEQ = IDC_REL_KEY.ID_SEQ AND IDC_REL_KEY.IC_TYPE = 19 AND IDC_REL_KEY.IC_CODE IN ("+paramMap.get("RELATION_KEYWORD")+")	\n");
			
			else if( !"".equals(paramMap.get("P_TYPE")) && !"0".equals(paramMap.get("P_TYPE")) ) {
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_P4 ON ID.ID_SEQ = IDC_P4.ID_SEQ 		\n");
				if("22".equals(paramMap.get("P_TYPE"))){
					sb.append("	   AND IDC_P4.IC_TYPE = 11 											\n");
					sb.append("	   AND IDC_P4.IC_CODE = 2 											\n");
				} else if("23".equals(paramMap.get("P_TYPE"))){
					sb.append("	   AND IDC_P4.IC_TYPE = 11 											\n");
					sb.append("	   AND IDC_P4.IC_CODE <> 2 											\n");
				} else {
					sb.append("   AND IDC_P4.IC_TYPE = "+ paramMap.get("P_TYPE") +" 				\n");
					sb.append("	   AND IDC_P4.IC_CODE IN ("+paramMap.get("P_CODE")+")				\n");
				}
				//sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_P4 ON ID.ID_SEQ = IDC_P4.ID_SEQ AND IDC_P4.IC_TYPE = "+ paramMap.get("P_TYPE") +"								\n");
				//if(!"".equals(paramMap.get("P_CODE"))){
				//	sb.append("	   AND IDC_P4.IC_CODE IN ("+paramMap.get("P_CODE")+")							\n");
				//}
			}	
			
			if( !"".equals(paramMap.get("DIC_SEQ")) )
				sb.append("	    INNER JOIN ISSUE_RELATION_KEYWORD IRK ON ID.ID_SEQ = IRK.ID_SEQ AND IRK.PAT_SEQ IN ("+paramMap.get("DIC_SEQ")+")	\n");
			
			if( !"".equals(paramMap.get("TPO_DIC_SEQ")) )
				sb.append("	    INNER JOIN ISSUE_RELATION_KEYWORD IRK2 ON ID.ID_SEQ = IRK2.ID_SEQ AND IRK2.PAT_SEQ IN ("+paramMap.get("TPO_DIC_SEQ")+")	\n");
			
			sb.append("	    WHERE ID.MD_DATE BETWEEN '"+setSDate+"' AND '"+setEDate+"'		\n");
			if( !"".equals(is_rt) )
				sb.append("	    AND ID.ID_IS_RT = '"+is_rt+"'					\n");
			if( !"".equals(paramMap.get("SITE_GROUP")) )
				sb.append("	    AND ID.SG_SEQ IN ( "+paramMap.get("SITE_GROUP")+" )				\n");
			if(!"".equals(paramMap.get("SEARCHKEY")))
				sb.append("	    AND ID_CONTENT LIKE '%"+ paramMap.get("SEARCHKEY") +"%'							\n");
			if(!"".equals(paramMap.get("ISSUE_SEARCHKEY")))
				sb.append("	    AND ID_CONTENT LIKE '%"+ paramMap.get("ISSUE_SEARCHKEY") +"%'					\n");
			//sb.append("		ORDER BY ID.MD_DATE DESC 											\n");
			sb.append("		ORDER BY ID.ID_SCORE DESC, ID.MD_DATE DESC 											\n");
			if(nowPage != 0 && row !=0)
				sb.append("		LIMIT "+((nowPage-1)*row)+", "+row+" 								\n");
			else
				sb.append("		LIMIT 1000														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				
				Map rMap = new HashMap();

				rMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				rMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				rMap.put("SG_SEQ", rs.getString("SG_SEQ"));
				rMap.put("S_SEQ", rs.getString("S_SEQ"));
				rMap.put("MD_DATE", rs.getString("MD_DATE"));
				rMap.put("ID_TITLE", rs.getString("ID_TITLE"));
				rMap.put("ID_CONTENT", rs.getString("ID_CONTENT"));
				rMap.put("ID_URL", rs.getString("ID_URL"));
				rMap.put("SENTI", rs.getString("SENTI"));
				rMap.put("PRODUCT", rs.getString("PRODUCT"));
				
				rList.add( rMap ); 

			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return rList;
        
	}
	
	public List getPopupData_HashTag(String sDate, String eDate, String setSTime, String setETime
			, Map paramMap, String idx_hint){
		return getPopupData_HashTag(0, 0, sDate, eDate, setSTime, setETime, paramMap, idx_hint);
	}
	
	/**
	 * 팝업 로우 데이터 구함 HashTAG 전용
	 * 날짜를 제외한 모든 파라미터는 IN절에 넣습니다.
	 * 
	 * @param nowNum
	 * @param row
	 * @param sDate
	 * @param eDate
	 * @param setSTime
	 * @param setETime
	 * @param paramMap 파라미터 Map
	 * @return
	 */
	public List getPopupData_HashTag(int nowPage, int row, String sDate, String eDate, String setSTime, String setETime
			, Map paramMap, String idx_hint){
		
		List rList = new ArrayList();
		
		try{ 
			
			String setSDate = sDate + " " + setSTime;
			String setEDate = eDate + " " + setETime;

			//getMaxMinIdSeq(setSDate, setEDate);
			
			if(nowPage != 0 && row !=0) {
				getCountData_HashTag(sDate, eDate, paramMap);
			}
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 데이터 데이터 호출 */							 				\n");
			sb.append("	SELECT ID.ID_SEQ	 					    				\n");
			sb.append("			,ID.MD_SITE_NAME	 			    				\n");
			sb.append("			,ID.SG_SEQ			 			    				\n");
			sb.append("			,ID.ID_TITLE		 			    				\n");
			sb.append("			,ID.ID_CONTENT		 			    				\n");
			sb.append("			,ID.ID_URL			 			    				\n");
			sb.append("			,FN_GET_ISSUE_CODE_NAME(ID.ID_SEQ, 9) AS SENTI		\n");
			sb.append("			,FN_GET_ISSUE_CODE_NAME(ID.ID_SEQ, 7) AS PRODUCT	\n");
			sb.append("			,ID.MD_DATE			 			    				\n");
			if("Y".equals(idx_hint)){  
				sb.append("	  FROM ISSUE_INSTA_DATA ID USE INDEX(IDX_MD_DATE)             \n");
			}else{ 
				sb.append("	  FROM ISSUE_INSTA_DATA ID 						           \n");	 
			}
			
			if( !"".equals(paramMap.get("HASH_TAG")) )
				sb.append("	   INNER JOIN ISSUE_INSTA_HASHTAG IH ON ID.ID_SEQ = IH.ID_SEQ AND IH.HC_CODE IN ("+paramMap.get("HASH_TAG")+")	\n");
			
			sb.append("	    WHERE ID.MD_DATE BETWEEN '"+setSDate+"' AND '"+setEDate+"'		\n");
			sb.append("		ORDER BY ID.MD_DATE DESC 											\n");
			
			if(nowPage != 0 && row !=0)
				sb.append("		LIMIT "+((nowPage-1)*row)+", "+row+" 								\n");
			else
				sb.append("		LIMIT 1000														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				
				Map rMap = new HashMap();

				rMap.put("ID_SEQ", rs.getString("ID_SEQ"));
				rMap.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				rMap.put("SG_SEQ", rs.getString("SG_SEQ"));
				rMap.put("MD_DATE", rs.getString("MD_DATE"));
				rMap.put("ID_TITLE", rs.getString("ID_TITLE"));
				rMap.put("ID_CONTENT", rs.getString("ID_CONTENT"));
				rMap.put("ID_URL", rs.getString("ID_URL"));
				rMap.put("SENTI", rs.getString("SENTI"));
				//rMap.put("PRODUCT", rs.getString("PRODUCT"));
				
				rList.add( rMap ); 

			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return rList;
        
	}
	
	private void getCountData_STA(String sDate, String eDate, Map paramMap, String is_rt){
		
		try{ 
			
			String rt_table = "";
			
			if("T".equals(is_rt)){
				rt_table = "_PARENT";
			}
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("/* 팝업 데이터 데이터 카운트 호출  - 통계 ver*/													\n");
			sb.append("	SELECT 						 			    									\n");
			if(!"".equals(paramMap.get("RELATION_KEYWORD"))) {
				
				// 성향
				if( !"".equals(paramMap.get("SENTI")) ) {
					
					StringBuffer param_senti = new StringBuffer();
					if(paramMap.get("SENTI").toString().contains("1"))
						param_senti.append("SAT_POS");
					if(paramMap.get("SENTI").toString().contains("2")){
						if(param_senti.length() > 0){
							param_senti.append("+");	
						}
						param_senti.append("SAT_NEG");
					}
					if(paramMap.get("SENTI").toString().contains("3")){
						if(param_senti.length() > 0){
							param_senti.append("+");	
						}
						param_senti.append("SAT_NEU");
					}
					/*if("1".equals(paramMap.get("SENTI"))) 		// 긍정
						sb.append("			SUM(SAT_POS) CNT	 			    								\n");
					else if("2".equals(paramMap.get("SENTI"))) 	// 부정
						sb.append("			SUM(SAT_NEG) CNT	 			    								\n");
					else if("3".equals(paramMap.get("SENTI"))) 										// 중립
						sb.append("			SUM(SAT_NEU) CNT	 			    								\n");*/
					
					sb.append("			SUM("+param_senti.toString()+") CNT	 			    				\n");
					
				} else {
					sb.append("			SUM(SAT_TOTAL) CNT	 			    								\n");
				}
				
			} else {
				if( !"".equals(paramMap.get("SITE_GROUP")) && !"".equals(paramMap.get("SENTI")) ){
					String chan_senti = "0";
					// 출처 AND 성향
					if( "1".equals(paramMap.get("SENTI")) ){
						if( PRESS.equals(paramMap.get("SITE_GROUP")) ) // 언론+긍정
							chan_senti = "1";
						else if( COMMUNITY.equals(paramMap.get("SITE_GROUP")) ) // 커뮤니티+긍정
							chan_senti = "2";
						else if( BLOG.equals(paramMap.get("SITE_GROUP")) ) // 블로그+긍정
							chan_senti = "3";
						else if( CAFE.equals(paramMap.get("SITE_GROUP")) ) // 카페+긍정
							chan_senti = "4";
						else if( TWIT.equals(paramMap.get("SITE_GROUP")) ) // 트위터+긍정
							chan_senti = "5";
						else if( ETC.equals(paramMap.get("SITE_GROUP")) ) // 기타+긍정
							chan_senti = "6";
						
					} else if( "2".equals(paramMap.get("SENTI")) ){
						if( PRESS.equals(paramMap.get("SITE_GROUP")) ) // 언론+부정
							chan_senti = "7";
						if( COMMUNITY.equals(paramMap.get("SITE_GROUP")) ) // 커뮤니티+부정
							chan_senti = "8";
						if( BLOG.equals(paramMap.get("SITE_GROUP")) ) // 블로그+부정
							chan_senti = "9";
						if( CAFE.equals(paramMap.get("SITE_GROUP")) ) // 카페+부정
							chan_senti = "10";
						if( TWIT.equals(paramMap.get("SITE_GROUP")) ) // 트위터+부정
							chan_senti = "11";
						if( ETC.equals(paramMap.get("SITE_GROUP")) ) // 기타+부정
							chan_senti = "12";
						
					} else if( "3".equals(paramMap.get("SENTI")) ){
						if( PRESS.equals(paramMap.get("SITE_GROUP")) ) // 언론+중립
							chan_senti = "13";
						if( COMMUNITY.equals(paramMap.get("SITE_GROUP")) ) // 커뮤니티+중립
							chan_senti = "14";
						if( BLOG.equals(paramMap.get("SITE_GROUP")) ) // 블로그+중립
							chan_senti = "15";
						if( CAFE.equals(paramMap.get("SITE_GROUP")) ) // 카페+중립
							chan_senti = "16";
						if( TWIT.equals(paramMap.get("SITE_GROUP")) ) // 트위터+중립
							chan_senti = "17";
						if( ETC.equals(paramMap.get("SITE_GROUP")) ) // 기타+중립
							chan_senti = "18";
					}
					
					sb.append("		SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(AI_TEXT,',',"+chan_senti+"),':',-1)) AS CNT	 		\n");
					
				} else if( !"".equals(paramMap.get("SENTI")) ) {
					// 성향
					StringBuffer param_senti = new StringBuffer();
					if(paramMap.get("SENTI").toString().contains("1"))
						param_senti.append("AI_POS");
					if(paramMap.get("SENTI").toString().contains("2")){
						if(param_senti.length() > 0){
							param_senti.append("+");	
						}
						param_senti.append("AI_NEG");
					}
					if(paramMap.get("SENTI").toString().contains("3")){
						if(param_senti.length() > 0){
							param_senti.append("+");	
						}
						param_senti.append("AI_NEU");
					}
					sb.append("			SUM("+param_senti.toString()+") CNT	 			    				\n");
					/*if("1".equals(paramMap.get("SENTI"))) 		// 긍정
						sb.append("			SUM(AI_POS) CNT	 			    								\n");
					else if("2".equals(paramMap.get("SENTI"))) 	// 부정
						sb.append("			SUM(AI_NEG) CNT	 			    								\n");
					else 										// 중립
						sb.append("			SUM(AI_NEU) CNT	 			    								\n");*/
					
				} else if( !"".equals(paramMap.get("SITE_GROUP")) ){
					// 출처
					
					// 사이트 그룹이 여러개 있을 경우
					if(paramMap.get("SITE_GROUP").toString().split(",").length > 1){
						String[] siteGroup = paramMap.get("SITE_GROUP").toString().split(",");
						String subQuery = "";
						for(int i=0; i < siteGroup.length; i++){
								if(!"".equals(subQuery))
									subQuery += "+";
								if(PRESS.equals(siteGroup[i]))
									subQuery += "AI_PRESS";
								else if(COMMUNITY.equals(siteGroup[i]))
									subQuery += "AI_COMMUNITY";
								else if(BLOG.equals(siteGroup[i]))
									subQuery += "AI_BLOG";
								else if(CAFE.equals(siteGroup[i]))
									subQuery += "AI_CAFE";
								else if(TWIT.equals(siteGroup[i]))
									subQuery += "AI_TWITTER";
								else if(ETC.equals(siteGroup[i]))
									subQuery += "AI_ETC";
						}
						sb.append("			SUM("+subQuery+") CNT	 			    						\n");
					}
					
					if(PRESS.equals(paramMap.get("SITE_GROUP"))) 		// 언론
						sb.append("			SUM(AI_PRESS) CNT	 			    							\n");	
					else if(COMMUNITY.equals(paramMap.get("SITE_GROUP"))) 	// 커뮤니티
						sb.append("			SUM(AI_COMMUNITY) CNT	 			    						\n");
					else if(BLOG.equals(paramMap.get("SITE_GROUP"))) 		// 블로그
						sb.append("			SUM(AI_BLOG) CNT	 			    							\n");
					else if(CAFE.equals(paramMap.get("SITE_GROUP"))) 		// 카페
						sb.append("			SUM(AI_CAFE) CNT	 			    							\n");
					else if(TWIT.equals(paramMap.get("SITE_GROUP"))) 		// 트위터
						sb.append("			SUM(AI_TWITTER) CNT	 			    							\n");
					else if(ETC.equals(paramMap.get("SITE_GROUP"))) 		// 기타
						sb.append("			SUM(AI_ETC) CNT	 			    								\n");
					else
						sb.append("			SUM(AI_TOTAL) CNT	 			    							\n");
					
				} else {
					sb.append("			SUM(AI_TOTAL) CNT	 			    								\n");
				}
			}
			
			sb.append("	  FROM						            										\n");
			if(!"".equals(paramMap.get("RELATION_KEYWORD")))
				sb.append("	 		ANA_ISSUE_RELATION"+rt_table+"_STA"+paramMap.get("TABLE_FLAG")+"						        \n");
			else
				sb.append("	 		ANA_ISSUE"+rt_table+"_STA"+paramMap.get("TABLE_FLAG")+"								            \n");
			
			sb.append("	  WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"'						\n");
			
			
			// depth 1
			if( !"".equals(paramMap.get("PRODUCT")) )
				sb.append("	  AND AI_TYPE1 = 7 AND AI_CODE1 IN ("+paramMap.get("PRODUCT")+")			\n");
			
			else if( !"".equals(paramMap.get("COMPANY")) )
				sb.append("	  AND AI_TYPE1 = 4 AND AI_CODE1 IN ("+paramMap.get("COMPANY")+")			\n");
			
			// depth 2
			if( !"".equals(paramMap.get("COMPLAIN")) )
				sb.append("	   AND AI_TYPE2 = 10 AND AI_CODE2 IN ("+paramMap.get("COMPLAIN")+")			\n");
			
			else if( !"".equals(paramMap.get("KBF")) )
				sb.append("	   AND AI_TYPE2 = 11 AND AI_CODE2 IN ("+paramMap.get("KBF")+")				\n");
			
			else if( !"".equals(paramMap.get("SENSE")) )
				sb.append("	   AND AI_TYPE2 = 12 AND AI_CODE2 IN ("+paramMap.get("SENSE")+")			\n");
			
			else if( !"".equals(paramMap.get("MATERIAL_GROUP")) )
				sb.append("	   AND AI_TYPE2 = 13 AND AI_CODE2 IN ("+paramMap.get("MATERIAL_GROUP")+")	\n");
			
			else if( !"".equals(paramMap.get("TPO")) )
				sb.append("	   AND AI_TYPE2 = 17 AND AI_CODE2 IN ("+paramMap.get("TPO")+")				\n");

			else if( !"".equals(paramMap.get("RELATION_KEYWORD")) ) 
				sb.append("	   AND AI_TYPE2 = 19 AND AI_CODE2 IN ("+paramMap.get("RELATION_KEYWORD")+")	\n");
			
			else if( !"".equals(paramMap.get("P_TYPE")) ) {
				sb.append("	   AND AI_TYPE2 = "+ paramMap.get("P_TYPE") +"								\n");
				if(!"".equals(paramMap.get("P_CODE"))){
					sb.append("	   AND AI_CODE2 IN ("+paramMap.get("P_CODE")+")							\n");
				}
			}
			
			else {  
				sb.append("	   AND AI_TYPE2 = 0 AND AI_CODE2 = 0										\n");
				sb.append("	   AND AI_TYPE3 = 0 AND AI_CODE3 = 0										\n");
			}
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				this.total_count = rs.getInt("CNT");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally{
        	sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
 		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
 		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}    
		}

	}
	
	private void getCountData_REAL(String setSDate, String setEDate, Map paramMap, String idx_hint, String is_rt){
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("## 팝업 데이터 데이터 카운트 호출  - REAL ver						\n");
			sb.append("	SELECT COUNT(*) AS CNT	 				    		\n");
			/*if("Y".equals(idx_hint)){
				sb.append("	  FROM ISSUE_DATA ID USE INDEX(IDX_MD_DATE)     \n");
			}else {
				sb.append("	  FROM ISSUE_DATA ID             				\n");	
			}*/
			sb.append("	  FROM ISSUE_DATA ID USE INDEX(IDX_MD_DATE)     	\n");
			
			if( !"".equals(paramMap.get("PRODUCT")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_PDT ON ID.ID_SEQ = IDC_PDT.ID_SEQ AND IDC_PDT.IC_TYPE = 7 AND IDC_PDT.IC_CODE IN ("+paramMap.get("PRODUCT")+")	\n");
			
			else if( !"".equals(paramMap.get("CATEGORY")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_CATEGORY ON ID.ID_SEQ = IDC_CATEGORY.ID_SEQ AND IDC_CATEGORY.IC_TYPE = 6 AND IDC_CATEGORY.IC_CODE IN ("+paramMap.get("CATEGORY")+")	\n");
			
			else if( !"".equals(paramMap.get("BRAND")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_BRAND ON ID.ID_SEQ = IDC_BRAND.ID_SEQ AND IDC_BRAND.IC_TYPE = 5 AND IDC_BRAND.IC_CODE IN ("+paramMap.get("BRAND")+")	\n");
			
			else if( !"".equals(paramMap.get("COMPANY")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_COM ON ID.ID_SEQ = IDC_COM.ID_SEQ AND IDC_COM.IC_TYPE = 4 AND IDC_COM.IC_CODE IN ("+paramMap.get("COMPANY")+")	\n");
			
			if( !"".equals(paramMap.get("SENTI")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_SENTI ON ID.ID_SEQ = IDC_SENTI.ID_SEQ AND IDC_SENTI.IC_TYPE = 9 AND IDC_SENTI.IC_CODE IN ("+paramMap.get("SENTI")+")	\n");
			
			if( !"".equals(paramMap.get("COMPLAIN")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_COMPLAIN ON ID.ID_SEQ = IDC_COMPLAIN.ID_SEQ AND IDC_COMPLAIN.IC_TYPE = 10 AND IDC_COMPLAIN.IC_CODE IN ("+paramMap.get("COMPLAIN")+")	\n");
			
			if( !"".equals(paramMap.get("KBF")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_KBF ON ID.ID_SEQ = IDC_KBF.ID_SEQ AND IDC_KBF.IC_TYPE = 11 AND IDC_KBF.IC_CODE IN ("+paramMap.get("KBF")+")	\n");
			
			if( !"".equals(paramMap.get("SENSE")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_SENSE ON ID.ID_SEQ = IDC_SENSE.ID_SEQ AND IDC_SENSE.IC_TYPE = 12 AND IDC_SENSE.IC_CODE IN ("+paramMap.get("SENSE")+")	\n");
			
			if( !"".equals(paramMap.get("MATERIAL")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_MATERIAL ON ID.ID_SEQ = IDC_MATERIAL.ID_SEQ AND IDC_MATERIAL.IC_TYPE = 14 AND IDC_MATERIAL.IC_CODE IN ("+paramMap.get("MATERIAL")+")	\n");
			
			else if( !"".equals(paramMap.get("MATERIAL_GROUP")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_MATERIAL_GROUP ON ID.ID_SEQ = IDC_MATERIAL_GROUP.ID_SEQ AND IDC_MATERIAL_GROUP.IC_TYPE = 13 AND IDC_MATERIAL_GROUP.IC_CODE IN ("+paramMap.get("MATERIAL_GROUP")+")	\n");
			
			if( !"".equals(paramMap.get("ISSUE")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_ISSUE ON ID.ID_SEQ = IDC_ISSUE.ID_SEQ AND IDC_ISSUE.IC_TYPE = 16 AND IDC_ISSUE.IC_CODE IN ("+paramMap.get("ISSUE")+")	\n");
			else if( !"".equals(paramMap.get("ISSUE_GROUP")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_ISSUE_GROUP ON ID.ID_SEQ = IDC_ISSUE_GROUP.ID_SEQ AND IDC_ISSUE_GROUP.IC_TYPE = 15 AND IDC_ISSUE_GROUP.IC_CODE IN ("+paramMap.get("ISSUE_GROUP")+")	\n");

			if( !"".equals(paramMap.get("TPO")) ){
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_TPO ON ID.ID_SEQ = IDC_TPO.ID_SEQ AND IDC_TPO.IC_TYPE = 17 AND IDC_TPO.IC_CODE IN ("+paramMap.get("TPO")+")		\n");
			}
			if( !"".equals(paramMap.get("RISK_KEYWORD")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_RISK_KEY ON ID.ID_SEQ = IDC_RISK_KEY.ID_SEQ AND IDC_RISK_KEY.IC_TYPE = 18 AND IDC_RISK_KEY.IC_CODE IN ("+paramMap.get("RISK_KEYWORD")+")	\n");
			
			if( !"".equals(paramMap.get("RELATION_KEYWORD")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_REL_KEY ON ID.ID_SEQ = IDC_REL_KEY.ID_SEQ AND IDC_REL_KEY.IC_TYPE = 19 AND IDC_REL_KEY.IC_CODE IN ("+paramMap.get("RELATION_KEYWORD")+")	\n");
	
			if( !"".equals(paramMap.get("DIC_SEQ")) ){
				sb.append("	    INNER JOIN ISSUE_RELATION_KEYWORD IRK USE INDEX (PRIMARY) ON ID.ID_SEQ = IRK.ID_SEQ AND IRK.PAT_SEQ IN ("+paramMap.get("DIC_SEQ")+")	\n");
			}
			if( !"".equals(paramMap.get("P_TYPE")) && !"0".equals(paramMap.get("P_TYPE"))) {
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_P4 ON ID.ID_SEQ = IDC_P4.ID_SEQ 		\n");
				if("22".equals(paramMap.get("P_TYPE"))){
					sb.append("	   AND IDC_P4.IC_TYPE = 11 											\n");
					sb.append("	   AND IDC_P4.IC_CODE = 2 											\n");
				} else if("23".equals(paramMap.get("P_TYPE"))){
					sb.append("	   AND IDC_P4.IC_TYPE = 11 											\n");
					sb.append("	   AND IDC_P4.IC_CODE <> 2 											\n");
				} else {
					sb.append("   AND IDC_P4.IC_TYPE = "+ paramMap.get("P_TYPE") +" 				\n");
					sb.append("	   AND IDC_P4.IC_CODE IN ("+paramMap.get("P_CODE")+")				\n");
				}				
				
				/*sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_P4 ON ID.ID_SEQ = IDC_P4.ID_SEQ AND IDC_P4.IC_TYPE = "+ paramMap.get("P_TYPE") +"								\n");
				if(!"".equals(paramMap.get("P_CODE"))){
					sb.append("	   AND IDC_P4.IC_CODE IN ("+paramMap.get("P_CODE")+")							\n");
				}*/
			}
			if( !"".equals(paramMap.get("TPO_DIC_SEQ")) ){
				sb.append("	    INNER JOIN ISSUE_RELATION_KEYWORD IRK2 USE INDEX (PRIMARY) ON ID.ID_SEQ = IRK2.ID_SEQ AND IRK2.PAT_SEQ IN ("+paramMap.get("TPO_DIC_SEQ")+")	\n");
			}

			sb.append("	    WHERE ID.MD_DATE BETWEEN '"+setSDate+"' AND '"+setEDate+"'		\n");
			if( !"".equals(is_rt) )
				sb.append("	    AND ID.ID_IS_RT = '"+is_rt+"' 								\n");
			if( !"".equals(paramMap.get("SITE_GROUP")) )
				sb.append("	    AND ID.SG_SEQ IN ( "+paramMap.get("SITE_GROUP")+" )			\n");
			if(!"".equals(paramMap.get("ISSUE_SEARCHKEY")))
				sb.append("	    AND ID_CONTENT LIKE '%"+ paramMap.get("ISSUE_SEARCHKEY") +"%'					\n");
			if(!"".equals(paramMap.get("SEARCHKEY")))
				sb.append("	    AND ID_CONTENT LIKE '%"+ paramMap.get("SEARCHKEY") +"%'						\n");
			
		
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				this.total_count = rs.getInt("CNT");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally{
        	sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
 		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
 		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}    
		}
		
	}
	
	/**
	 * HashTag Count
	 * @param setSDate
	 * @param setEDate
	 * @param paramMap
	 * @param idx_hint
	 */
	private void getCountData_HashTag(String sDate, String eDate, Map paramMap){
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("## 팝업 데이터 데이터 카운트 호출  - HashTag ver					\n");
			sb.append("	SELECT IFNULL(SUM(HC_CNT),0) AS CNT	 				\n");
			sb.append("	  FROM ANA_ISSUE_HASHTAG	            			\n");	
			sb.append("	  WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"'		\n");
			
			if( !"".equals(paramMap.get("HASH_TAG")) )
				sb.append("	   AND HC_CODE IN ("+paramMap.get("HASH_TAG")+")	\n");
				
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				this.total_count = rs.getInt("CNT");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally{
        	sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
 		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
 		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}    
		}
		
	}
/*	private void getCountData_REAL(Map paramMap, String searchKey, String dic_seq){
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("## 팝업 데이터 데이터 카운트 호출  - REAL ver							\n");
			sb.append("	SELECT COUNT(*) AS CNT	 				    			\n");
			sb.append("	  FROM ISSUE_DATA ID             \n");
			if(dic_seq.length() > 0){
				sb.append("	  FROM ISSUE_DATA ID             \n");	
			}else{
				sb.append("	  FROM ISSUE_DATA ID USE INDEX(IDX_MD_DATE)             \n");
			}
			
			if( !"".equals(paramMap.get("PRODUCT")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_PDT ON ID.ID_SEQ = IDC_PDT.ID_SEQ AND IDC_PDT.IC_TYPE = 7 AND IDC_PDT.IC_CODE IN ("+paramMap.get("PRODUCT")+")	\n");
			
			else if( !"".equals(paramMap.get("CATEGORY")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_CATEGORY ON ID.ID_SEQ = IDC_CATEGORY.ID_SEQ AND IDC_CATEGORY.IC_TYPE = 6 AND IDC_CATEGORY.IC_CODE IN ("+paramMap.get("CATEGORY")+")	\n");
			
			else if( !"".equals(paramMap.get("BRAND")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_BRAND ON ID.ID_SEQ = IDC_BRAND.ID_SEQ AND IDC_BRAND.IC_TYPE = 5 AND IDC_BRAND.IC_CODE IN ("+paramMap.get("BRAND")+")	\n");
			
			else if( !"".equals(paramMap.get("COMPANY")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_COM ON ID.ID_SEQ = IDC_COM.ID_SEQ AND IDC_COM.IC_TYPE = 4 AND IDC_COM.IC_CODE IN ("+paramMap.get("COMPANY")+")	\n");
			
			if( !"".equals(paramMap.get("SENTI")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_SENTI ON ID.ID_SEQ = IDC_SENTI.ID_SEQ AND IDC_SENTI.IC_TYPE = 9 AND IDC_SENTI.IC_CODE IN ("+paramMap.get("SENTI")+")	\n");
			
			if( !"".equals(paramMap.get("COMPLAIN")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_COMPLAIN ON ID.ID_SEQ = IDC_COMPLAIN.ID_SEQ AND IDC_COMPLAIN.IC_TYPE = 10 AND IDC_COMPLAIN.IC_CODE IN ("+paramMap.get("COMPLAIN")+")	\n");
			
			if( !"".equals(paramMap.get("KBF")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_KBF ON ID.ID_SEQ = IDC_KBF.ID_SEQ AND IDC_KBF.IC_TYPE = 11 AND IDC_KBF.IC_CODE IN ("+paramMap.get("KBF")+")	\n");
			
			if( !"".equals(paramMap.get("SENSE")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_SENSE ON ID.ID_SEQ = IDC_SENSE.ID_SEQ AND IDC_SENSE.IC_TYPE = 12 AND IDC_SENSE.IC_CODE IN ("+paramMap.get("SENSE")+")	\n");
			
			if( !"".equals(paramMap.get("MATERIAL")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_MATERIAL ON ID.ID_SEQ = IDC_MATERIAL.ID_SEQ AND IDC_MATERIAL.IC_TYPE = 14 AND IDC_MATERIAL.IC_CODE IN ("+paramMap.get("MATERIAL")+")	\n");
			
			else if( !"".equals(paramMap.get("MATERIAL_GROUP")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_MATERIAL_GROUP ON ID.ID_SEQ = IDC_MATERIAL_GROUP.ID_SEQ AND IDC_MATERIAL_GROUP.IC_TYPE = 13 AND IDC_MATERIAL_GROUP.IC_CODE IN ("+paramMap.get("MATERIAL_GROUP")+")	\n");
			
			if( !"".equals(paramMap.get("ISSUE")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_ISSUE ON ID.ID_SEQ = IDC_ISSUE.ID_SEQ AND IDC_ISSUE.IC_TYPE = 16 AND IDC_ISSUE.IC_CODE IN ("+paramMap.get("ISSUE")+")	\n");
			else if( !"".equals(paramMap.get("ISSUE_GROUP")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_ISSUE_GROUP ON ID.ID_SEQ = IDC_ISSUE_GROUP.ID_SEQ AND IDC_ISSUE_GROUP.IC_TYPE = 15 AND IDC_ISSUE_GROUP.IC_CODE IN ("+paramMap.get("ISSUE_GROUP")+")	\n");

			if( !"".equals(paramMap.get("TPO")) ){
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_TPO ON ID.ID_SEQ = IDC_TPO.ID_SEQ AND IDC_TPO.IC_TYPE = 17 AND IDC_TPO.IC_CODE IN ("+paramMap.get("TPO")+")		\n");
			}
			if( !"".equals(paramMap.get("RISK_KEYWORD")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_RISK_KEY ON ID.ID_SEQ = IDC_RISK_KEY.ID_SEQ AND IDC_RISK_KEY.IC_TYPE = 18 AND IDC_RISK_KEY.IC_CODE IN ("+paramMap.get("RISK_KEYWORD")+")	\n");
			
			if( !"".equals(paramMap.get("RELATION_KEYWORD")) )
				sb.append("	   INNER JOIN ISSUE_DATA_CODE IDC_REL_KEY ON ID.ID_SEQ = IDC_REL_KEY.ID_SEQ AND IDC_REL_KEY.IC_TYPE = 19 AND IDC_REL_KEY.IC_CODE IN ("+paramMap.get("RELATION_KEYWORD")+")	\n");
			
			if( !"".equals(dic_seq) ){
				
				sb.append("	    INNER JOIN ISSUE_RELATION_KEYWORD IRK USE INDEX (PRIMARY) ON ID.ID_SEQ = IRK.ID_SEQ AND IRK.PAT_SEQ IN ("+dic_seq+")	\n");
			
			}
				
			sb.append("	    WHERE ID.ID_SEQ BETWEEN "+this.min_id_seq+" AND "+this.max_id_seq+"		\n");
			if( !"".equals(paramMap.get("SITE_GROUP")) )
				sb.append("	    AND ID.SG_SEQ IN ( "+paramMap.get("SITE_GROUP")+" )			\n");
			
			if(!"".equals(searchKey))
				sb.append("	    AND ID_TITLE LIKE '%"+ searchKey +"%'						\n");
		
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				this.total_count = rs.getInt("CNT");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally{
        	sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
 		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
 		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}    
		}
		
	}*/
	
	private void getMaxMinIdSeq(String setSDate, String setEDate){
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 MAX, MIN ID_SEQ 구하기 */											\n");
			sb.append("	SELECT MAX(ID_SEQ) AS MAX_SEQ	 				    				\n");
			sb.append("			,MIN(ID_SEQ) AS MIN_SEQ	 				    				\n");
			sb.append("	  FROM ISSUE_DATA ID             									\n");
			sb.append("	  WHERE ID.MD_DATE BETWEEN '"+setSDate+"' AND '"+setEDate+"'		\n");
		
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				this.max_id_seq = rs.getString("MAX_SEQ");
				this.min_id_seq = rs.getString("MIN_SEQ");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally{
        	sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
 		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
 		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}    
		}
		
	}

	/**
	 * TPO 팝업 타이틀
	 * 날짜를 제외한 모든 파라미터는 IN절에 넣습니다.
	 * 
	 * @param dic_seq PAT_SEQ
	 * @return
	 */
	public String getPopupTPO_Title(String dic_seq){
		
		String rStr = "";
		
		try{ 

			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 TPO 타이틀 	*/								\n");
			sb.append("	SELECT FN_RELATION_NAME("+dic_seq+") AS NAME	\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				rStr = rs.getString("NAME");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return rStr;
        
	}
	
	/**
	 * TPO 팝업 카테고리
	 * 날짜를 제외한 모든 파라미터는 IN절에 넣습니다.
	 * 선택한 TPO를 제외한 나머지 TPO를 불러옵니다.
	 * 
	 * @param 선택된 TPO IC_CODE
	 * @return
	  */
	public List getPopupTPO_Header(String tpo){
		
		List rList = new ArrayList();
		
		try{ 

			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 TPO 테이블 헤더	*/								\n");
			sb.append("	SELECT IC_NAME									\n");
			sb.append("		FROM ISSUE_CODE								\n");
			sb.append("		WHERE IC_TYPE = 17							\n");
			sb.append("		AND	 IC_CODE > 0							\n");
			sb.append("		AND	 IC_USEYN = 'Y'							\n");
			if(!"".equals(tpo))
				sb.append("		AND IC_CODE <> "+tpo+"				\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				rList.add(rs.getString("IC_NAME"));
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
             if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return rList;
        
	}
	
	/**
	 * TPO 팝업 AI_CODE
	 * 날짜를 제외한 모든 파라미터는 IN절에 넣습니다.
	 * 선택한 TPO를 제외한 나머지 TPO를 불러옵니다.
	 * 
	 * @param 선택된 TPO IC_CODE
	 * @return 선택된 TPO 제외한 IC_CODE들
	  */
	public List getPopupTPO_AI_CODE_LIST(String tpo){
		
		List rList = new ArrayList();
		
		try{ 

			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 TPO 테이블 헤더	*/								\n");
			sb.append("	SELECT IC_CODE									\n");
			sb.append("		FROM ISSUE_CODE								\n");
			sb.append("		WHERE IC_TYPE = 17							\n");
			sb.append("		AND	 IC_CODE > 0							\n");
			sb.append("		AND	 IC_USEYN = 'Y'							\n");
			if(!"".equals(tpo))
				sb.append("		AND IC_CODE <> "+tpo+"				\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				rList.add(rs.getString("IC_CODE"));
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
             if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return rList;
        
	}
	
	/**
	 * TPO 팝업 카운트
	 * 날짜를 제외한 모든 파라미터는 IN절에 넣습니다.
	 * 
	 * @param sDate
	 * @param eDate
	 * @param setSTime
	 * @param setETime
	 * @param paramMap 파라미터 Map
	 * @return
	 */
	public List getPopupTPO(String sDate, String eDate, String setSTime, String setETime
			, Map paramMap, String dic_seq){
		
		List rList = new ArrayList();
		List tpoList = new ArrayList();
		
		try{ 
			
			// 클릭한 TPO코드를 제외한 TPO코드들을 불러온다.
			tpoList = getPopupTPO_AI_CODE_LIST(paramMap.get("TPO").toString());
			
			// TPO 5*3 표에 대한 초기화 작업 전부 수량을 0으로 맞춰 둔다.
			for(int i=0; i < 5; i++){
				List rowList = new ArrayList();
				for(int j=0; j < tpoList.size(); j++){
					Map rMap = new HashMap();
					rMap.put("AI_CODE", tpoList.get(j).toString());
					rMap.put("RANK", (i+1));
					rMap.put("CNT", "0");
					rowList.add(rMap);
				}
				rList.add(rowList);
			}
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 TPO 데이터 호출  */							 									\n");
			sb.append("	SELECT FN_ISSUE_NAME(A.AI_CODE1, '17') AS IC_NAME						 		\n");
			sb.append("		   , A.AI_CODE1										 						\n");
			sb.append("		   , A.RANK										 							\n");
			sb.append("		   , A.PAT_SEQ															 	\n");
			sb.append("		   , FN_RELATION_NAME(A.PAT_SEQ) AS PAT_NAME							 	\n");
			sb.append("		   , A.CNT					 												\n");
			sb.append("		FROM (												 						\n");
			sb.append("	  		SELECT A.*																\n");
			sb.append("	  			, IF(@CODE = AI_CODE1, @ROW:=@ROW+1,@ROW:=1) AS RANK				\n");
			sb.append("	  			, @CODE:= AI_CODE1													\n");
			sb.append("	  			FROM (																\n");
			sb.append("	  				SELECT AI_CODE1													\n");
			sb.append("	  						, PAT_SEQ AS PAT_SEQ									\n");
			sb.append("	  						, SUM(PAT_TOTAL) AS CNT									\n");
			sb.append("	  					FROM ANA_ISSUE_RELATION_INVOLVE_STA							\n");
			sb.append("	  					WHERE AI_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"'						\n");
			if(!"".equals(paramMap.get("PRODUCT"))){
				sb.append("						AND AI_PTYPE = 7										\n");
				sb.append("						AND AI_PCODE = "+paramMap.get("PRODUCT")+"				\n"); // 제품코드
			}else if(!"".equals(paramMap.get("COMPANY"))){
				sb.append("						AND AI_PTYPE = 4										\n");
				sb.append("						AND AI_PCODE = "+paramMap.get("COMPANY")+"				\n"); // 회사코드
			} 
			sb.append("						AND PAT_PSEQ = "+dic_seq+" 									\n"); // 키워드 코드	
			sb.append("					GROUP BY AI_CODE1, PAT_SEQ					 					\n");
			sb.append("					ORDER BY AI_CODE1, CNT DESC, PAT_SEQ ASC						\n");
			sb.append("					)A, (SELECT @ROW:=1, @CODE:=0)R									\n");
			sb.append("				)A 																	\n");
			sb.append("			WHERE A.RANK <= 5														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				
				for(int i=0; i < rList.size(); i++){
					List rowList = (List) rList.get(i);
					for(int j=0; j < rowList.size(); j++){
						Map rMap = (Map) rowList.get(j);
						
						// 일치하는 AI_CODE와 랭크에 해당하는 항목에 대하여 수량 업데이트
						if(rMap.get("AI_CODE").toString().equals(rs.getString("AI_CODE1"))
								&& rMap.get("RANK").toString().equals(rs.getString("RANK"))
							){
								rMap.put("PAT_SEQ", rs.getString("PAT_SEQ"));	
								rMap.put("PAT_NAME", rs.getString("PAT_NAME"));
								rMap.put("CNT", rs.getString("CNT"));
								rowList.set(j, rMap);
								break;
							}
					}
				}
				
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return rList;
        
	}
	
	public String getSG_SEQ(String s_seq){
		
		String result = "";
		
		try{ 
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* Lucy API 팝업 사이트그룹 리스트 콜  */				\n");
			sb.append("	SELECT SG_SEQ								\n");
			sb.append("		FROM SG_S_RELATION						\n");
			sb.append("		WHERE S_SEQ = "+s_seq+"					\n");
			
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = rs.getString("SG_SEQ");
				
			}
 			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return result;
        
	}
	
	/**
	 * 팝업 관련정보 제목 
	 * 공백이 아닌 파라미터는 IN절에 넣습니다.
	 * 해당 클릭한 항목 카테고리 명을 가져 옵니다.
	 * 
	 * @param 선택된 IC_CODE
	 * @return
	  */
	public String getRelInfo_Header(Map paramMap, String dic_seq){
		
		String pat_seq = dic_seq;
		String sg_seq = "";
		ArrayList<String> typeList = new ArrayList();
		ArrayList<String> codeList = new ArrayList();
		
		if( !"".equals(paramMap.get("COMPANY")) ){
			typeList.add("4");
			codeList.add(paramMap.get("COMPANY").toString());					

		}	
		if( !"".equals(paramMap.get("CATEGORY")) ){
			typeList.add("6");
			codeList.add(paramMap.get("CATEGORY").toString());
				
		}		
		if( !"".equals(paramMap.get("BRAND")) ){
			typeList.add("5");
			codeList.add(paramMap.get("BRAND").toString());
				
		}		
		if( !"".equals(paramMap.get("PRODUCT")) ) {
			typeList.add("7");
			codeList.add(paramMap.get("PRODUCT").toString());
				
		}
		if( !"".equals(paramMap.get("COMPLAIN")) ){
			typeList.add("10");
			codeList.add(paramMap.get("COMPLAIN").toString());			
				
		}	
		if( !"".equals(paramMap.get("P_TYPE")) && !"".equals(paramMap.get("P_CODE")) ){
			typeList.add(paramMap.get("P_TYPE").toString());
			codeList.add(paramMap.get("P_CODE").toString());			
				
		}
		if("".equals(pat_seq)){
			if( !"".equals(paramMap.get("KBF")) ){
				typeList.add("11");
				codeList.add(paramMap.get("KBF").toString());				
					
			}
			if( !"".equals(paramMap.get("SENSE")) ){
				typeList.add("12");
				codeList.add(paramMap.get("SENSE").toString());					
					
			}
			if( !"".equals(paramMap.get("MATERIAL")) ){
				typeList.add("14");
				codeList.add(paramMap.get("MATERIAL").toString());				

			}
			if( !"".equals(paramMap.get("MATERIAL_GROUP"))){
				typeList.add("13");
				codeList.add(paramMap.get("MATERIAL_GROUP").toString());				
					
			}
			if( !"".equals(paramMap.get("ISSUE")) ){
				typeList.add("16");
				codeList.add(paramMap.get("ISSUE").toString());				

			}
			if( !"".equals(paramMap.get("ISSUE_GROUP")) ){
				typeList.add("15");
				codeList.add(paramMap.get("ISSUE_GROUP").toString());					
					
			}
			if( !"".equals(paramMap.get("RISK_KEYWORD")) ){
				typeList.add("18");
				codeList.add(paramMap.get("RISK_KEYWORD").toString());				

			}
			if( !"".equals(paramMap.get("RELATION_KEYWORD")) ){
				typeList.add("19");
				codeList.add(paramMap.get("RELATION_KEYWORD").toString());					

			}
		}
		if( !"".equals(paramMap.get("TPO")) ){
			typeList.add("17");
			codeList.add(paramMap.get("TPO").toString());				
				
		}
		
		if(!"".equals(paramMap.get("SITE_GROUP"))){
			sg_seq = paramMap.get("SITE_GROUP").toString();
		}
		
		if( !"".equals(paramMap.get("SENTI")) ){
			typeList.add("9");
			codeList.add(paramMap.get("SENTI").toString());					

		}				
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < typeList.size(); i++){
			sb.append(getCallPopupTitleQuery(typeList.get(i), codeList.get(i), "", ""));
			sb.append(" ");
		}
		
		if(!"".equals(pat_seq)){
			sb.append(getCallPopupTitleQuery("", "", pat_seq, ""));
			sb.append(" ");
		}
		
		
		if(!"".equals(sg_seq)){
			sb.append(getCallPopupTitleQuery("", "", "", sg_seq));
			sb.append(" ");
		}
		
        return sb.toString();
        
	}
	
	private String getCallPopupTitleQuery(String type, String code, String pat_seq, String sg_seq){

		String rString = "";
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 팝업 헤더  */								 	\n");
			if(!"".equals(pat_seq)) {
				sb.append("	SELECT WORD_NM AS TITLE 				\n");
				sb.append("		FROM ANA_TOPIC_DICTIONARY			\n");
				sb.append("		WHERE PAT_SEQ = "+pat_seq+"			\n");
			} else if(!"".equals(sg_seq)){
				sb.append("	SELECT SG_NAME AS TITLE					\n");
				sb.append("		FROM SITE_GROUP						\n");
				sb.append("		WHERE SG_SEQ = "+sg_seq+" 			\n");
			} else if(!"".equals(code) && !"".equals(type)){
				//sb.append("	SELECT FN_ISSUE_NAME("+code+", "+type+") AS TITLE	\n");
				sb.append("	SELECT GROUP_CONCAT(IC_NAME) AS TITLE	\n");
				sb.append("		FROM ISSUE_CODE						\n");
				sb.append("		WHERE IC_TYPE = "+type+"			\n");
				sb.append("		AND	IC_CODE IN ("+code+")			\n");
			}
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				rString = rs.getString("TITLE");
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        } 
		
		return rString;
	}
	
	/**
	 * 인스타그램 팝업 관련정보 제목 
	 * 해당 클릭한 항목 카테고리 명을 가져 옵니다.
	 * 
	 * @param 선택된 HC_CODE
	 * @return
	  */
	public String getRelInfo_Hashtag_Header(Map paramMap){
		
		String rString = "";
		String code = "0";
		
		try{ 

			if( !"".equals(paramMap.get("HASH_TAG")) ) {
				code = paramMap.get("HASH_TAG").toString();
			} 
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("/* 인스타그램 팝업 헤더  */			 		\n");
			sb.append("	SELECT HC_NAME			 			\n");
			sb.append("		FROM HASHTAG_CODE				\n");
			sb.append("		WHERE HC_CODE = "+code+"		\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				rString = rs.getString("HC_NAME");
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
             if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}            
        }       
        
        return rString;
        
	}
	
}


