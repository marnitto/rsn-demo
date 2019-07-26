/**
========================================================
주 시 스 템 : RSN
서브 시스템 :
프로그램 ID : siteDataInfo
프로그램 명 : 사이트그룹 데이터 class
프로그램개요 : 사이트그룹 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.mobile;
/// SSL
import java.io.Reader;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;



//import risk.util.StringUtil;
import risk.DBconn.DBconn;
import risk.search.MetaBean;
import risk.search.replyInfo;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class MobileMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;

    StringBuffer sb = null;
    String sQuery   = "";

    public  MetaBean mBean  = null;
    public int mBeanTotalCnt   = 0;
    public int mBeanPageCnt    = 0;
    public int mBeanDataCnt    = 0;


    public String msMinNo   = "";   //검색 시작 MD_SEQ
    public String msMaxNo   = "";   //검색 마지막 MD_SEQ
    public String msKeyTitle= "";   //검색 그룹 한글명


    public int mBeanTotalPageCount = 0;

    public MobileMgr() {

    }
    
    public void getMaxMinNo( String psSDate, String psEdate, DBconn dbconn ) {

        try {
            sb = new StringBuffer();
           
          /*sb.append(" SELECT CASE DATE_FORMAT(NOW(),'%Y-%m-%d')                                                                    \n");
            sb.append("         WHEN DATE_FORMAT('"+psSDate+"','%Y-%m-%d')                                                            \n");
            sb.append("           THEN IFNULL((SELECT MIN(MD_SEQ) FROM META WHERE MD_DATE BETWEEN '"+psSDate+" 00:00:00' AND '"+psSDate+" 23:59:59'),1)   \n");
            sb.append("         ELSE                                                                                                 \n");
            sb.append("           IFNULL((SELECT MINNO FROM DAILY_MAXNINNO WHERE DM_DATE = '"+psSDate+" 23:59:59'),1)   \n");
            sb.append("        END  AS MIN_NO,                                                                                       \n");
            sb.append("                                                                                                              \n");
            sb.append("        CASE DATE_FORMAT(NOW(),'%Y-%m-%d')                                                                    \n");
            sb.append("         WHEN DATE_FORMAT('"+psEdate+"','%Y-%m-%d')                                                            \n");
            sb.append("           THEN IFNULL((SELECT MAX(MD_SEQ) FROM META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59'),1)   \n");
            sb.append("         ELSE                                                                                                 \n");
            sb.append("           IFNULL((SELECT MAXNO FROM DAILY_MAXNINNO WHERE DM_DATE = '"+psEdate+" 23:59:59'),1)    \n");
            sb.append("        END  AS MAX_NO                                                                                        \n");*/
            
            sb.append(" SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+psSDate+" 00:00:00' AND '"+psSDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
            
            //dbconn = new DBconn();
            //dbconn.getDBCPConnection();

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {
                msMinNo = rs.getString("MIN_NO");
                msMaxNo = rs.getString("MAX_NO");
            }

            //Log.debug("msMinNo :" + msMinNo );
            //Log.debug("msMaxNo :" + msMaxNo );

            sb = null;
        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            //try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    }
    
    public int TotalCount(String pK_XP, String pSG_SEQ, String pMT_TYPE, String KeyWord, String dataFrom, String DataTo)
    {
    	
    	String minDate = msMinNo;
    	String maxDate = msMaxNo;
    	
    	try
    	{
	    	dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        
	        getMaxMinNo(dataFrom, DataTo, dbconn);
	        
	        stmt = dbconn.createStatement();
	        
	      //총개시물 건수를 구한다.
	        sb = new StringBuffer();
	        sb.append("  SELECT                                     \n");
	        sb.append("         COUNT(1) AS TOTAL_CNT                              \n");
	        sb.append("  FROM   (                              \n");
	       
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ                     \n");		   
            sb.append("                           FROM IDX A, META B		                   \n");
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+minDate +" AND "+ maxDate +"                             \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
            if ( !KeyWord.equals("") )		           
            sb.append("                                 AND B.MD_TITLE LIKE '%" + KeyWord + "%'                               \n");		            
            if ( !pK_XP.equals("") )
            sb.append("                                 AND A.K_XP    in ( " + pK_XP + " )               \n");
            if ( !pSG_SEQ.equals("") )
	        sb.append("                                 AND A.SG_SEQ IN ( " + pSG_SEQ + " )                   \n");
            if ( !pMT_TYPE.equals("") )
	        sb.append("                                 AND B.MD_TYPE IN (" + pMT_TYPE   + ")                      \n");
            
            sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
            sb.append("  			)RESULT                              \n");
            
           
            Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	            mBeanTotalCnt  = rs.getInt("TOTAL_CNT");
	        }
            
	    } catch (SQLException ex ) {
	        Log.writeExpt(ex, sb.toString() );
	
	    } catch (Exception ex ) {
	        Log.writeExpt(ex);
	
	    } finally {
	        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	    }
	    	return mBeanTotalCnt;
	    }
    
    // MOBILE 접속 인증 (true:인증성공, false:인증실패 ) 
    public boolean UserAccessCheck(String tel)
    {
    	int checked = 0;
    	try
    	{
	    	dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        stmt = dbconn.createStatement();
	       
	        sb = new StringBuffer();
	        sb.append("SELECT COUNT(*) AS CHECKED                         \n");
	        sb.append("  FROM ADDRESS_BOOK                                \n");
	        sb.append(" WHERE AB_APP_CHK = 1					          \n");
	        sb.append("   AND REPLACE(AB_MOBILE, '-','') = '"+ tel +"'    \n");
	          
            Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	        	checked  = rs.getInt("CHECKED");
	        }
            
	    } catch (SQLException ex ) {
	        Log.writeExpt(ex, sb.toString() );
	
	    } catch (Exception ex ) {
	        Log.writeExpt(ex);
	
	    } finally {
	        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	    }
	    
	    if(checked > 0){
        	return true;
        } else{
        	return false;
        }
	}
    
 // MOBILE 알리미의 마지막 값과 등록수를  가져온다.(안드로이드 타이머 호출)
    public long[] DataMaxNumCheck(String tel, long pOldMaxCount)
    {
    	long[] arrResult = new long[2]; 
    	
    	try
    	{
	    	dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        stmt = dbconn.createStatement();
	        
	        /*
	        sb = new StringBuffer();
	        sb.append("SELECT MAL_SEQ                                     \n");
	        sb.append("  FROM MALIMI_LOG                                  \n");
	        sb.append(" WHERE REPLACE(AB_MOBILE, '-','') = '"+tel+"'      \n"); 
	        sb.append(" ORDER BY MAL_SEQ DESC                             \n");
	        sb.append(" LIMIT 0, 1                                        \n");
	        */
	        
	        sb = new StringBuffer();
	        sb.append("SELECT B.MAX_SEQ AS MAX_SEQ                                   \n");
	        sb.append("     , COUNT(*) - 1 AS COUNT                                  \n");
	        sb.append("  FROM MALIMI_LOG A                                           \n");
	        sb.append("     , (SELECT MAL_SEQ AS MAX_SEQ                             \n");                                     
	        sb.append("          FROM MALIMI_LOG                                     \n");                    
	        sb.append("         WHERE REPLACE(AB_MOBILE, '-','') = '"+ tel +"'       \n");
	        sb.append("         ORDER BY MAL_SEQ DESC                                \n");          
	        sb.append("         LIMIT 0, 1) B                                        \n");              
	        sb.append(" WHERE REPLACE(A.AB_MOBILE, '-','') = '"+ tel +"'             \n");
	        sb.append("   AND A.MAL_SEQ BETWEEN "+ pOldMaxCount +" AND B.MAX_SEQ     \n");
	        
            Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	        	arrResult[0] = rs.getLong("MAX_SEQ");
	        	arrResult[1] = rs.getLong("COUNT");
	        }
            
	    } catch (SQLException ex ) {
	        Log.writeExpt(ex, sb.toString() );
	
	    } catch (Exception ex ) {
	        Log.writeExpt(ex);
	
	    } finally {
	        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	    }
	    
	    return arrResult;
	}
    
    public String IntconvTypeCodeToSQL( int psTypeCode ) {

        String sTypeCondition = "";
        
        if ( psTypeCode==1 ) {
            sTypeCondition = "  AND MD_TYPE = 1 ";
        } else if ( psTypeCode==2 ) {
            sTypeCondition = "  AND MD_TYPE = 2 ";
        } else if ( psTypeCode==3 ) {
            sTypeCondition = "  AND MD_TYPE = 3 ";
        } else if ( psTypeCode==4 ) {
            sTypeCondition = "  AND MD_TYPE <= 2 ";
        } else if ( psTypeCode==5 ) {
            sTypeCondition = "  AND (MD_TYPE = 1 OR MD_TYPE = 3) ";
        } else if ( psTypeCode==6 ) {
            sTypeCondition = "  AND MD_TYPE >= 2 ";
        } else if ( psTypeCode==7 ) {
            sTypeCondition = "";
        }
        return sTypeCondition;
    }
    
    public String convTypeCodeToSQL( String psTypeCode ) {

        String sTypeCondition = "";

        if ( psTypeCode.equals("1") ) {
            sTypeCondition = "  AND B.MD_TYPE = 1 ";
        } else if ( psTypeCode.equals("2") ) {
            sTypeCondition = "  AND B.MD_TYPE = 2 ";
        } else if ( psTypeCode.equals("3") ) {
            sTypeCondition = "  AND B.MD_TYPE <= 2 ";
        } else if ( psTypeCode.equals("4") ) {
            sTypeCondition = "  AND B.MD_TYPE = 3 ";
        } else if ( psTypeCode.equals("5") ) {
            sTypeCondition = "  AND B.MD_TYPE = 1 OR B.MD_TYPE = 3 ";
        } else if ( psTypeCode.equals("6") ) {
            sTypeCondition = "  AND B.MD_TYPE >= 2 ";
        } else if ( psTypeCode.equals("7") ) {
            sTypeCondition = "";
        }

        return sTypeCondition;
	}
    


	/**
	* 키워드 그룹별 검색
	*/
	public ArrayList getKeySearchList(     int    piNowpage ,
	                                       int    piRowCnt  ,
	                                       String psXp      ,
	                                       String psYp      ,
	                                       String psZp      ,
	                                       String psSGseq   ,
	                                       String psSDgsn   ,
	                                       String psDateFrom,
	                                       String psDateTo  ,
	                                       String psKeyWord ,
	                                       String psType    ,
	                                       String psOrder   ,
	                                       String psMode	,
	                                       String psMname)
	
	
	{	
		ArrayList arrlist = new ArrayList();
	
	    try {
	
	      
	        dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	        
	        getMaxMinNo( psDateFrom, psDateTo, dbconn );	
	        String MinMtNo= msMinNo;
	        String MaxMtNo= msMaxNo;
	        String addQuery = "";      
	        //String sTypeCondition = convTypeCodeToSQL(psType);
	        
	        if(psMode.equals("DELIDX")){
	        	addQuery = " AND A.I_STATUS = 'T'";
	        	if(psMname.length()>0){
	        		addQuery += " AND A.M_SEQ = "+psMname;
	        	}  		        	
	        }else{
	        	//addQuery = " AND (A.I_STATUS = 'N' AND A.M_SEQ <> "+psMname+")";
	        	addQuery = " AND (A.I_STATUS = 'N' )";
	        }
	        
	        
	
	        //stmt = dbn.createScrollStatement();
	        stmt = dbconn.createStatement();
	
	        //총개시물 건수를 구한다.
	        sb = new StringBuffer();
	        sb.append("  SELECT                                     \n");
	        sb.append("         COUNT(1) AS TOTAL_CNT                              \n");
	        sb.append("  FROM   (                              \n");
	       
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ                     \n");		   
            sb.append("                           FROM IDX A, META B		                   \n");
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                             \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
            if ( !psKeyWord.equals("") )		           
            sb.append("                                 AND B.MD_TITLE LIKE '%" + psKeyWord + "%'                               \n");		            
            if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
            if ( !psXp.equals("") )
            sb.append("                                 AND A.K_XP    in ( " + psXp + " )               \n");
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
            if ( !psSGseq.equals("") )
	        sb.append("                                 AND A.SG_SEQ IN ( " + psSGseq + " )                   \n");
            sb.append("                                 "+addQuery+"                   \n");
            if ( !psType.equals("") )
	        sb.append("                                 AND B.MD_TYPE IN (" + psType   + ")                      \n");
            if(psMode.equals("DELIDX")){
	        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
	        }else{
	        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
	        }
            sb.append("  			)RESULT                              \n");
	
	
	
	        Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        if ( rs.next() ) {
	            mBeanTotalCnt  = rs.getInt("TOTAL_CNT");
	            mBeanPageCnt   = mBeanTotalCnt / piRowCnt ;
	        }
	
	        rs.close();
	        rs = null;
	        sb = null;
	
	        String sXpValue = "";
	        String sYpValue = "";
	        String sZpValue = "";
	
	        if ( !(psXp + psYp + psZp).equals("") ) {
	
	            sb = new StringBuffer();	
	            sb.append("SELECT MAX(MAIN) MAIN , MAX(MIDLE) MIDLE, MAX(BOTTOM) BOTTOM     \n");
	            sb.append("  FROM (                                                         \n");
	            sb.append("        SELECT K_VALUE AS  MAIN , '' AS MIDLE , '' AS BOTTOM     \n");
	            sb.append("          FROM KEYWORD                                           \n");
	            sb.append("         WHERE K_XP in ( " + psXp + " )                               \n");
	            sb.append("           AND K_YP = 0                                          \n");
	            sb.append("           AND K_ZP = 0                                          \n");
	            sb.append("           AND K_TYPE < 3                                        \n");
	            sb.append("  		  AND K_USEYN='Y'                                              \n");
	            if ( !psYp.equals("") ) {
	                sb.append("        UNION                                                    \n");
	                sb.append("        SELECT '' , K_VALUE MAIN , ''                            \n");
	                sb.append("          FROM KEYWORD                                           \n");
	                sb.append("         WHERE K_XP = " + psXp + "                               \n");
	                sb.append("           AND K_YP = " + psYp + "                               \n");
	                sb.append("           AND K_ZP = 0                                          \n");
	                sb.append("           AND K_TYPE < 3                                        \n");
	                sb.append("  		  AND K_USEYN='Y'                                              \n");
	            }
	
	            if ( !psZp.equals("") ) {
	                sb.append("        UNION                                                    \n");
	                sb.append("        SELECT '' , '' , K_VALUE MAIN                            \n");
	                sb.append("          FROM KEYWORD                                           \n");
	                sb.append("         WHERE K_XP = " + psXp + "                               \n");
	                sb.append("           AND K_YP = " + psYp + "                               \n");
	                sb.append("           AND K_ZP = " + psZp + "                               \n");
	                sb.append("           AND K_TYPE < 3                                        \n");
	                sb.append("  		  AND K_USEYN='Y'                                              \n");
	            }
	            sb.append("       )TEMP                                                     \n");
	
	            Log.debug(sb.toString() );
	            rs = stmt.executeQuery(sb.toString());
	
	            if ( rs.next() ) {
	                sXpValue = rs.getString("MAIN")   == null ? "" : rs.getString("MAIN")   ;
	                sYpValue = rs.getString("MIDLE")  == null ? "" : rs.getString("MIDLE")  ;
	                sZpValue = rs.getString("BOTTOM") == null ? "" : rs.getString("BOTTOM") ;
	            }
	
	            msKeyTitle = sXpValue;
	            if ( !sYpValue.equals("") ) {
	                msKeyTitle += "-" + sYpValue;
	            }
	
	            if ( !sZpValue.equals("") ) {
	                msKeyTitle += "-" + sZpValue;
	            }
	            
	            if(psXp.length()>1){
	            	msKeyTitle = "전체키워드";
	            }
	            
	        } else {
	            msKeyTitle = "전체키워드";
	        }
	        if(psMode.equals("DELIDX")){
	        	msKeyTitle = "휴지통";
	        }
	        // 좌측 메뉴 키워드 그룹별 키워드 강조
	        String KeywordGroup="";
	        if(!psXp.equals("")){
	      	 	sb = null;
	 	        sb = new StringBuffer();
	            
		        if(psYp.equals("") && psZp.equals("") ){
		        	sb.append("  SELECT *                                                \n");
		        	sb.append("  FROM KEYWORD                                                 \n");
		        	sb.append("  WHERE K_XP in ( " + psXp + " )                                                 \n");
		        	sb.append("  AND K_YP > 0                                                 \n");
		        	sb.append("  AND K_ZP > 0                                                \n");
		        	sb.append("  AND K_TYPE < 3                                                \n");
		        	sb.append("  AND K_USEYN='Y'                                              \n");		        	
			        rs = stmt.executeQuery(sb.toString());
			        while(rs.next()){
			        	if(KeywordGroup.equals("")){
			        		KeywordGroup = rs.getString("K_VALUE");
			        	}else{
			        		KeywordGroup += " "+rs.getString("K_VALUE");
			        	}		        	
			        }            
		        }else if(!psYp.equals("") && psZp.equals("") ){
		        	sb.append("  SELECT *                                                \n");
		        	sb.append("  FROM KEYWORD                                                 \n");
		        	sb.append("  WHERE K_XP = " + psXp + "                                                 \n");
		        	sb.append("  AND K_YP   = " + psYp + "                                                \n");
		        	sb.append("  AND K_ZP > 0                                                \n");
		        	sb.append("  AND K_TYPE < 3                                                \n");
		        	sb.append("  AND K_USEYN='Y'                                              \n");       	
			        rs = stmt.executeQuery(sb.toString());
			        while(rs.next()){
			        	if(KeywordGroup.equals("")){
			        		KeywordGroup = rs.getString("K_VALUE");
			        	}else{
			        		KeywordGroup += " "+rs.getString("K_VALUE");
			        	}		        	
			        }
	            }	   
		    }

            sb = new StringBuffer();
	        String Keyword = "";
	        //mBeanTotalCnt = 10;
	        //게시물 리스트를 구한다.
	        if ( mBeanTotalCnt > 0 ) {
	            sb = new StringBuffer();
	            
	            int liststart;
	         	int listend;
	         	
	         	if (piNowpage == 0) 
	         		piNowpage = 1;
	         	else {
	         		liststart = (piNowpage-1) * piRowCnt;
	         		listend =    piRowCnt;
	         		                    
		            sb.append("                 SELECT 					                                     \n");
		            sb.append("                        FN_GET_KEYWORD(A.MD_PSEQ) AS K_VALUE         ,        \n");
		            sb.append("                        A.MD_SEQ         ,                                    \n");
		            sb.append("                        A.SG_SEQ,                                           \n");
		            sb.append("                        A.S_SEQ        ,                                    \n");
		            sb.append("                        A.MD_SITE_NAME       ,                                    \n");	
		            sb.append("                        A.MD_MENU_NAME       ,                                    \n");		            
		            sb.append("                        A.MD_TYPE       ,                                    \n");
		            sb.append("                        DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE, 						        \n");
		            sb.append("                        A.MD_SAME_COUNT  ,                                    \n");
		            sb.append("                        A.MD_PSEQ        ,                                    \n");
		            sb.append("                        A.MD_TITLE       ,                                    \n");
		            sb.append("                        A.MD_URL         ,                                    \n");		            
		            sb.append("                        A.ISSUE_CHECK,                                 \n");							            
		            if(addQuery.length()>0){
		            sb.append("                        DATE_FORMAT(A.I_DELDATE,'%Y-%m-%d %H:%i:%s') AS I_DELDATE,  \n");
					sb.append("                        A.M_SEQ,   \n");
					sb.append("                        (SELECT M_NAME FROM MEMBER WHERE M_SEQ =A.M_SEQ)AS M_NAME, \n");            
		            }
		            sb.append("                        B.MD_CONTENT                                             \n");
		            sb.append("                   FROM (                                                    \n");
		            sb.append("                           SELECT				                               \n");
		            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE ,B.MD_DATE  \n");
		            sb.append("                                ,B.MD_SAME_COUNT ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, ISSUE_CHECK, I_DELDATE, M_SEQ    \n");		            
		            sb.append("                           FROM IDX A, META B		                   \n");
		            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                            \n");
		            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
		            if ( !psKeyWord.equals("") )		           
		            sb.append("                                 AND B.MD_TITLE LIKE '%" + psKeyWord + "%'                               \n");		            
		            if ( !psSDgsn.equals("") )
		            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
		            if ( !psXp.equals("") )
		            sb.append("                                 AND A.K_XP    in ( " + psXp + " )               \n");
		            if ( !psYp.equals("") )
		            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
		            if ( !psZp.equals("") )
		            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
		            if ( !psSGseq.equals("") )
			        sb.append("                                 AND A.SG_SEQ IN( " + psSGseq + " )                   \n");
		            sb.append("                                 "+addQuery+"                   \n");
		            		            
		            if ( !psType.equals("") )
		    	        sb.append("                                 AND B.MD_TYPE IN (" + psType   + ")                      \n");
		            
		            if(psMode.equals("DELIDX")){
			        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
			        }else{
			        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
			        }
		            
		            sb.append("                        ) A ,                                                \n");
		            sb.append("                        DATA B                                       \n");		           		    	       
		            sb.append("                  WHERE                                   \n");
		            sb.append("                        A.MD_SEQ = B.MD_SEQ                                    \n");
		            sb.append("         	     ORDER BY " + psOrder + "                                                 \n");
		            sb.append("         		 LIMIT "+liststart+","+listend+"                                 \n");
	         	}
	         	Log.debug(sb.toString() );
	         	rs = stmt.executeQuery(sb.toString());
          	          
	            while( rs.next() ) {
	                mBeanDataCnt++;
	
	                String sD_html  = "";
	
	                if ( rs.getCharacterStream("MD_CONTENT") != null ) {
	
	                    StringBuffer output = new StringBuffer();
	                    Reader       input  = rs.getCharacterStream("MD_CONTENT");
	                    char[]       buffer = new char[2048];
	
	                    int byteRead;
	                    while( (byteRead=input.read(buffer,0,1024)) != -1  ){
	                       output.append(buffer,0,byteRead);
	                    }
	                    input.close();
	
	                    sD_html = output.toString();
	
	                }
	              
	                if(psMode.equals("DELIDX")){
	                	
						mBean  = new MetaBean();
						mBean.setMd_seq(rs.getString("MD_SEQ"));
						mBean.setS_seq(rs.getString("S_SEQ"));
						mBean.setSg_seq("SG_SEQ");
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
						mBean.setM_name(rs.getString("M_NAME"));
						arrlist.add(mBean);					
	                	
      		        }else{
      		        	mBean  = new MetaBean();
						mBean.setMd_seq(rs.getString("MD_SEQ"));
						mBean.setS_seq(rs.getString("S_SEQ"));
						mBean.setSg_seq("SG_SEQ");
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
						mBean.setIssue_check(rs.getString("ISSUE_CHECK"));					
						arrlist.add(mBean);			
      		        }
	
	            } // end while
	        } // end if
	
	    } catch (SQLException ex ) {
	        Log.writeExpt(ex, sb.toString() );
	
	    } catch (Exception ex ) {
	        Log.writeExpt(ex);
	
	    } finally {
	        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	        try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	    }
	
	    return arrlist;
	}
	
}