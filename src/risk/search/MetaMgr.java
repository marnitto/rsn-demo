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
package risk.search;
/// SSL
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.admin.bookmark.BookMarkMgr;
import risk.admin.keyword.KeywordBean;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;

@SuppressWarnings("rawtypes")
public class MetaMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;

    StringBuffer sb = null;    
    String sQuery   = "";
    String BookMarkQuery = null;
    
    private BookMarkMgr bmMgr = null;
    
	private int bookMarkPage = 0;
    private String bookMarkNum = null;
    
    public  MetaBean mBean  = null;
    public int mBeanTotalCnt   = 0;
    public int mBeanPageCnt    = 0;
    public int mBeanDataCnt    = 0;
    
    public int portalCnt       = 0;
    public int miTotalCnt      = 0;
    
	ArrayList arrSourceCnt = new ArrayList();

    public ArrayList getArrSourceCnt() {
		return arrSourceCnt;
	}


    public String msMinNo   = "";   //검색 시작 MD_SEQ
    public String msMaxNo   = "";   //검색 마지막 MD_SEQ
    public String msKeyTitle= "";   //검색 그룹 한글명


    public int mBeanTotalPageCount = 0;

    public MetaMgr() {

    }
    
    public int getBookMarkPage() {
		return bookMarkPage;
	}


	public String getBookMarkNum() {
		return bookMarkNum;
	}


    public ArrayList getSiteGroup( ) {
        return getSiteGroup( "" );
    }


    
	@SuppressWarnings("unchecked")
	public ArrayList getSiteGroup(String psMgSite ) {

        ArrayList arrlist = new ArrayList();

        try {

            sb = new StringBuffer();
  
            sb.append("SELECT A.SG_SEQ                       			\n");
            sb.append("     , A.SG_NAME                       			\n");
            sb.append("  FROM SITE_GROUP A 								\n");
            sb.append("     , IC_S_RELATION B							\n");
            if ( !psMgSite.equals("0") )
            sb.append(" WHERE A.SG_SEQ IN ("+psMgSite+")				\n");
            sb.append("   AND A.SG_SEQ = B.S_SEQ						\n");
            sb.append(" ORDER BY B.IC_ORDER ASC  						\n");
            
            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            
            System.out.println(sb.toString());
            
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {
                arrlist.add( new siteGroupInfo( rs.getInt("SG_SEQ"), rs.getString("SG_NAME") ) );
            }

            sb = null;

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

    /**
    *   사이트 데이터 조회
    */

    public ArrayList getSiteData(String psSiteGroup ) {

        ArrayList arrlist = new ArrayList();

        try {
            if ( !psSiteGroup.equals("") ) {
                sb = new StringBuffer();

                sb.append("SELECT S_SEQ,                   \n");
                sb.append("       SG_SEQ,                   \n");
                sb.append("       S_NAME                   \n");
                sb.append("  FROM SG_S_RELATION                 \n");
                sb.append(" WHERE SG_SEQ IN (" + psSiteGroup +") \n");
                sb.append(" ORDER BY S_NAME                \n");

                dbconn = new DBconn();
                dbconn.getDBCPConnection();

                stmt = dbconn.createStatement();
                rs = stmt.executeQuery(sb.toString());

                while( rs.next() ) {
                    arrlist.add( new siteDataInfo(  rs.getInt("S_SEQ")     ,
                                                    rs.getInt("SG_SEQ")     ,
                                                    rs.getString("S_NAME") )
                               );
                }

                sb = null;
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
        return arrlist;
    }


    public void getMaxMinNo( String psSDate, String psEdate) {
    	getMaxMinNo(psSDate, psEdate, "00", "23");
    }
    
    public void getMaxMinNo( String psSDate, String psEdate, String psSTime, String psETime) {
    	getMaxMinNo(psSDate, psEdate, psSTime, psETime, "");
    }

    public void getMaxMinNo( String psSDate, String psEdate, String psSTime, String psETime, String stname ) {
    	
    	
    	
    	DateUtil du = new DateUtil();
    	
    	
    	if(psSDate == null){
    		psSDate = du.getCurrentDate(); 
    	}
    	if(psEdate == null){
    		psEdate = du.getCurrentDate();
    	}
    	if(psSTime.length() == 1){
    		psSTime = "0" + psSTime;  
    	}
    	if(psETime.length() == 1){
    		psETime = "0" + psETime;  
    	}

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
             
            sb = new StringBuffer();                    
            sb.append(" SELECT (SELECT MD_SEQ FROM "+stname+"META WHERE MD_DATE BETWEEN '"+psSDate+" "+psSTime+":00:00' AND '"+psSDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM "+stname+"META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" "+psETime+":59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
                       

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {
                msMinNo = rs.getString("MIN_NO");
                msMaxNo = rs.getString("MAX_NO");
                
                if(msMinNo==null || msMinNo.equals("null")){
                	
                	if(Integer.parseInt(psSDate.replaceAll("-", "") + psSTime) >= Integer.parseInt(du.getDate("yyyyMMddHH"))){
                		msMinNo = msMaxNo+1;
                	}else{
                		msMinNo = "0";
                	}
                	
                }
                if(msMaxNo==null || msMaxNo.equals("null")){
                	msMaxNo = "999999999";
                }
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
            sTypeCondition = "  AND (B.MD_TYPE = 1 OR B.MD_TYPE = 3) ";
        } else if ( psTypeCode.equals("6") ) {
            sTypeCondition = "  AND B.MD_TYPE >= 2 ";
        } else if ( psTypeCode.equals("7") ) {
            sTypeCondition = "";
        }

        return sTypeCondition;
	}
    
    public MetaBean getMetaData(String md_seq){
    	return getMetaData(md_seq, ""); 
    }
    
    public MetaBean getMetaData(String md_seq, String stName)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            sb = new StringBuffer();

            sb.append("SELECT M.MD_SEQ         ,                                   \n");
            sb.append("       M.S_SEQ        ,                                   \n");
            sb.append("       M.SB_SEQ        ,                                   \n");
            sb.append("       I.SG_SEQ        ,                                   \n");
            sb.append("       M.MD_SITE_NAME       ,                                   \n");
            sb.append("       M.MD_MENU_NAME       ,                                   \n");
            sb.append("       M.MD_TYPE       ,                                   \n");
            sb.append("       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,          \n");
            sb.append("       M.MD_SAME_COUNT    ,                                   \n");
            sb.append("       M.MD_PSEQ        ,                                   \n");
            sb.append("       M.MD_TITLE      ,                                   \n");
            sb.append("       M.MD_URL        ,                                   \n");
            if(stName.equals("")){
            	sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                  \n");
            }else{
            	sb.append("       M.EX_NAME AS K_VALUE,                  \n");
            }
            sb.append("       M.MD_IMG        ,                                   \n");
            sb.append("       D.MD_CONTENT    ,                                     \n");
            sb.append("       M.L_ALPHA       ,                                    \n");
            sb.append("       M.USER_ID       ,                                    \n");
            sb.append("       M.USER_NICK     ,                                    \n");
            sb.append("       M.BLOG_VISIT_COUNT     ,                                    \n");
            sb.append("       M.CAFE_NAME     ,                                    \n");
            sb.append("       (SELECT IFNULL(D_SEQ, '') FROM META WHERE MD_SEQ = "+md_seq+ ") AS D_SEQ	, \n");
            sb.append("       M.CAFE_MEMBER_COUNT                                         \n");
            sb.append("  FROM "+stName+"META M, "+stName+"DATA D,(SELECT  MD_SEQ, SG_SEQ FROM "+stName+"IDX WHERE MD_SEQ = "+md_seq+"  GROUP BY MD_SEQ) I \n");
            sb.append(" WHERE                           \n");                      				  
            sb.append("                             \n");
            sb.append("   	 M.MD_SEQ = D.MD_SEQ                         \n");
            sb.append("   	 AND M.MD_SEQ = I.MD_SEQ                          \n");
            sb.append(" ORDER BY M.MD_DATE ASC                                    \n");

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSb_seq(rs.getString("SB_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        	 mBean.setUser_id(rs.getString("USER_ID"));
	        	 mBean.setUser_nick(rs.getString("USER_NICK"));
	        	 mBean.setBlog_visit_count(rs.getString("BLOG_VISIT_COUNT"));
	        	 mBean.setCafe_name(rs.getString("CAFE_NAME"));
	        	 mBean.setCafe_member_count(rs.getString("CAFE_MEMBER_COUNT"));
	        	 mBean.setD_seq(rs.getString("D_SEQ"));
	        
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return mBean;
    }
    
    public ArrayList getKeywordInfo(String md_seq){
    	ArrayList result = new ArrayList();
    	
    	StringBuffer sb = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	DBconn dbconn = null;
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append("SELECT I.MD_SEQ, I.K_XP, I.K_YP, I.K_ZP, K.K_VALUE FROM IDX I\n");
    		sb.append("INNER JOIN KEYWORD K\n");
    		sb.append("ON I.MD_SEQ = "+md_seq+"\n");
    		sb.append("AND I.K_XP = K.K_XP\n");
    		sb.append("AND I.K_YP = K.K_YP\n");
    		sb.append("AND I.K_ZP = K.K_ZP\n");
    		sb.append("AND I.K_XP = 9 AND I.K_YP = 5\n");	//SMS대그룹  HOT ISSUE중그룹에 속하는 키워드
    		sb.append("ORDER BY K_XP, K_YP, K_ZP\n");
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		if(rs.next()){
    			KeywordBean kb = new KeywordBean();
    			kb.setMd_seq(rs.getString("MD_SEQ"));
    			kb.setK_xp(rs.getString("K_XP"));
    			kb.setK_yp(rs.getString("K_YP"));
    			kb.setK_zp(rs.getString("K_ZP"));
    			kb.setK_value(rs.getString("K_VALUE"));
    			result.add(kb);
    		}
    	}catch(SQLException e){
    		Log.writeExpt(e, e.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);    		
    	}finally{
    		sb = null;
    		try{if(dbconn != null)dbconn.close();}catch(Exception e){}
    		try{if(rs != null)rs.close();}catch(Exception e){}
    		try{if(pstmt != null)pstmt.close();}catch(Exception e){}
    	}
    	return result;
    }
    
    public JSONArray getSb_seq_Map(){
    	JSONArray result = new JSONArray();
    	
    	StringBuffer sb = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	DBconn dbconn = null;
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append(" SELECT  														\n");
    		sb.append(" 	  SB_SEQ													\n");
    		sb.append(" 	 ,TYPE5	 													\n");
    		sb.append(" 	 ,TYPE51	 												\n");
    		sb.append(" 	 ,TYPE3	 													\n");
    		sb.append(" 	 ,TYPE31	 												\n");
    		sb.append(" FROM SB_SEQ_MAP			 										\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		
    		JSONObject obj = null;
    		
    		while(rs.next()){
    			obj = new JSONObject();
    			obj.put("SB_SEQ",rs.getString("SB_SEQ"));
    			obj.put("TYPE5",rs.getString("TYPE5"));
    			obj.put("TYPE51",rs.getString("TYPE51"));
    			obj.put("TYPE3",rs.getString("TYPE3"));
    			obj.put("TYPE31",rs.getString("TYPE31"));
    			result.put(obj);
    		}
    		
    	}catch(SQLException e){
    		Log.writeExpt(e, e.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);    		
    	}finally{
    		sb = null;
    		try{if(dbconn != null)dbconn.close();}catch(Exception e){}
    		try{if(rs != null)rs.close();}catch(Exception e){}
    		try{if(pstmt != null)pstmt.close();}catch(Exception e){}
    	}
    	return result;
    }
    
    public String getKeyword_Map(String md_seq){
    	String result = "";
    	
    	StringBuffer sb = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	DBconn dbconn = null;
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append(" SELECT  														\n");
    		sb.append(" 	   B.IC_TYPE												\n");
    		sb.append(" 	  ,B.IC_CODE												\n");
    		sb.append(" FROM IDX A , KEYWORD_MAP B 										\n");
    		sb.append(" WHERE A.K_XP = B.K_XP AND A.K_YP = B.K_YP AND A.K_ZP = B.K_ZP 	\n");
    		sb.append(" 	AND A.MD_SEQ = "+md_seq+" 									\n");
    		sb.append(" GROUP BY B.IC_TYPE 												\n");
    		sb.append(" ORDER BY A.K_XP , A.K_YP , A.K_ZP 								\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()){
    			if("".equals(result)){
    				result = rs.getString("IC_TYPE")+","+rs.getString("IC_CODE");
    			}else{
    				result += "@"+rs.getString("IC_TYPE")+","+rs.getString("IC_CODE");
    			}
    		}
    		
    	}catch(SQLException e){
    		Log.writeExpt(e, e.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);    		
    	}finally{
    		sb = null;
    		try{if(dbconn != null)dbconn.close();}catch(Exception e){}
    		try{if(rs != null)rs.close();}catch(Exception e){}
    		try{if(pstmt != null)pstmt.close();}catch(Exception e){}
    	}
    	return result;
    }
    
    public String getKeywordXP(String md_seq){
    	String result = null;
    	int [] xp = {34,35,36,37,38,40,41,42,43,44,46,47,48,49,50};
    	
    	StringBuffer sb = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	DBconn dbconn = null;
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append("SELECT K_XP FROM IDX WHERE MD_SEQ = "+md_seq+" GROUP BY K_XP \n");
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		boolean bStop = false;
    		while(rs.next()){
    			result = rs.getString("K_XP");
    			for(int i=0 ; i<xp.length ; i++){
    				if(xp[i] == rs.getInt("K_XP")){
    					bStop = true;
    					break;
    				}
    			}
    			
    			if(bStop){
    				break;
    			}
    		}
    	}catch(SQLException e){
    		Log.writeExpt(e, e.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);    		
    	}finally{
    		sb = null;
    		try{if(dbconn != null)dbconn.close();}catch(Exception e){}
    		try{if(rs != null)rs.close();}catch(Exception e){}
    		try{if(pstmt != null)pstmt.close();}catch(Exception e){}
    	}
    	return result;
    }
    
    public MetaBean getTopData(String t_seq)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            sb = new StringBuffer();

            
            sb.append("SELECT T_SEQ      AS MD_SEQ			\n");
            sb.append("     , S_SEQ      AS S_SEQ			\n");
            sb.append("     , 30         AS SG_SEQ			\n");
            sb.append("     , T_SITE     AS MD_SITE_NAME	\n");
            sb.append("     , T_BOARD    AS MD_MENU_NAME	\n");
            sb.append("     , 1          AS MD_TYPE			\n");
            sb.append("     , T_DATETIME AS MD_DATE			\n");
            sb.append("     , 0          AS MD_SAME_COUNT 	\n");
            sb.append("     , T_SEQ      AS MD_PSEQ			\n");
            sb.append("     , T_TITLE    AS MD_TITLE		\n");
            sb.append("     , T_URL      AS MD_URL			\n");
            sb.append("     , ''         AS K_VALUE			\n");
            sb.append("     , ''         AS MD_IMG			\n");
            sb.append("     , ''         AS MD_CONTENT		\n");
            sb.append("     , 'KOR'      AS L_ALPHA			\n");
            sb.append("  FROM TOP 							\n");
            sb.append(" WHERE T_SEQ = "+t_seq+"				\n");
            
            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return mBean;
    }
    
    public ArrayList getK_XPData(String md_seq){
    	return getK_XPData(md_seq, "");
    }
    
    public ArrayList getK_XPData(String md_seq, String subMode)
    {
    	ArrayList arrlist = new ArrayList();
    	risk.admin.keyword.KeywordBean keyBean = null;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            
            if(subMode.equals("TOP")){
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT T_XP AS K_XP, T_VALUE AS K_VALUE FROM TOP_KEYWORD WHERE T_YP =0 AND T_ZP =0 	\n");
                }else{
                	sb = new StringBuffer();
                    sb.append("SELECT DISTINCT A.K_XP																							\n");
                    sb.append("     , (SELECT T_VALUE FROM TOP_KEYWORD WHERE T_XP = A.K_XP AND T_YP =0 AND T_ZP =0) AS K_VALUE			\n");
                    sb.append("  FROM TOP_IDX A 																						\n");
                    sb.append(" WHERE A.T_SEQ = "+md_seq+"																				\n");	
                }
            	
            }else{
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT K_XP, K_VALUE FROM KEYWORD WHERE K_YP =0 AND K_ZP =0 AND K_USEYN = 'Y' \n");
                }else{
                	sb = new StringBuffer();
                    sb.append("SELECT DISTINCT A.K_XP																									\n");
                    sb.append("     , (SELECT K_VALUE FROM KEYWORD WHERE K_XP = A.K_XP AND K_YP =0 AND K_ZP =0 AND K_USEYN = 'Y') AS K_VALUE	\n");
                    sb.append("  FROM IDX A 																									\n");
                    sb.append(" WHERE A.MD_SEQ = "+md_seq+"																						\n");	
                }
            }

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
            	keyBean  = new risk.admin.keyword.KeywordBean();
            	keyBean.setKGxp(rs.getString("K_XP"));
            	keyBean.setKGvalue(rs.getString("K_VALUE"));
            	arrlist.add(keyBean);
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
        return arrlist;
    }
    
    public ArrayList getK_YPData(String md_seq, String k_xp){
    	return getK_YPData(md_seq, k_xp, "");
    }
    
    public ArrayList getK_YPData(String md_seq, String k_xp, String subMode)
    {
    	ArrayList arrlist = new ArrayList();
    	risk.admin.keyword.KeywordBean keyBean = null;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            
            if(subMode.equals("TOP")){
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT T_YP AS K_YP, T_VALUE AS K_VALUE FROM TOP_KEYWORD WHERE T_XP = "+k_xp+" AND T_ZP = 0\n");
                }else{
    	            sb = new StringBuffer();
    	            sb.append("SELECT DISTINCT A.K_YP																					\n");
    	            sb.append("     , (SELECT T_VALUE FROM TOP_KEYWORD WHERE T_XP = "+k_xp+" AND T_YP =A.K_YP AND T_ZP =0) AS K_VALUE	\n");
    	            sb.append("  FROM TOP_IDX A 																						\n");
    	            sb.append(" WHERE A.T_SEQ = "+md_seq+"																				\n");
    	            sb.append("   AND A.K_XP = "+k_xp+"																					\n");
                }
            }else{
            	if(md_seq.equals("All")){
                	sb = new StringBuffer();
                    sb.append("SELECT K_YP, K_VALUE FROM KEYWORD WHERE K_XP = "+k_xp+" AND K_ZP = 0 AND K_USEYN = 'Y' \n");
                }else{
    	            sb = new StringBuffer();
    	            sb.append("SELECT DISTINCT A.K_YP																								\n");
    	            sb.append("     , (SELECT K_VALUE FROM KEYWORD WHERE K_XP = "+k_xp+" AND K_YP =A.K_YP AND K_ZP =0 AND K_USEYN = 'Y') AS K_VALUE	\n");
    	            sb.append("  FROM IDX A 																										\n");
    	            sb.append(" WHERE A.MD_SEQ = "+md_seq+"																							\n");
    	            sb.append("   AND K_XP = "+k_xp+"																								\n");
                }
            }
            
            

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
            	keyBean  = new risk.admin.keyword.KeywordBean();
            	keyBean.setKGyp(rs.getString("K_YP"));
            	keyBean.setKGvalue(rs.getString("K_VALUE"));
            	arrlist.add(keyBean);
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
        return arrlist;
    }
    
    
    public ArrayList getMetaDataList(String md_seq)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("SELECT M.MD_SEQ         ,                                   \n");
            sb.append("       M.S_SEQ        ,                                   \n");
            sb.append("       I.SG_SEQ        ,                                   \n");
            sb.append("       M.MD_SITE_NAME       ,                                   \n");
            sb.append("       M.MD_MENU_NAME       ,                                   \n");
            sb.append("       M.MD_TYPE       ,                                   \n");
            sb.append("       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,          \n");
            sb.append("       M.MD_SAME_COUNT    ,                                   \n");
            sb.append("       M.MD_PSEQ        ,                                   \n");
            sb.append("       M.MD_TITLE      ,                                   \n");
            sb.append("       M.MD_URL        ,                                   \n");
            sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                  \n");
            sb.append("       M.MD_IMG        ,                                   \n");
            sb.append("       D.MD_CONTENT    ,                                       \n");
            sb.append("       M.L_ALPHA       ,                                   \n");
            sb.append("       M.USER_ID       ,                                   \n");
            sb.append("       M.D_SEQ       ,                                   \n");
            sb.append("       M.USER_NICK                                        \n");            
            sb.append("  FROM META M, DATA D,(SELECT  MD_SEQ, SG_SEQ FROM IDX WHERE MD_SEQ IN ("+md_seq+")  GROUP BY MD_SEQ) I \n");
            sb.append(" WHERE                           \n");                      				  
            sb.append("                             \n");
            sb.append("   	 M.MD_SEQ = D.MD_SEQ                         \n");
            sb.append("   	 AND M.MD_SEQ = I.MD_SEQ                          \n");
            sb.append(" ORDER BY M.MD_DATE ASC                                    \n");

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setL_alpha(rs.getString("L_ALPHA"));
	        	 mBean.setUser_id(rs.getString("USER_ID"));
	        	 mBean.setUser_nick(rs.getString("USER_NICK"));
	        	 mBean.setD_seq(rs.getString("D_SEQ"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    public ArrayList getMetaDataList(String md_date, int k_xp, int k_yp, int nowPage, int PageCount)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.*, I.K_XP, I.K_YP													\n");
            sb.append("	FROM META M, IDX I															\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ													\n");
            sb.append("	  AND M.MD_DATE BETWEEN '"+md_date+" 00:00:00' AND '"+md_date+" 23:59:59'	\n");
            sb.append("	  AND I.K_XP = "+k_xp+"														\n");
            sb.append("	  AND I.K_YP = "+k_yp+"  													\n");
            sb.append("	  AND I.K_ZP <> 0															\n");
            sb.append("	GROUP BY M.MD_SEQ															\n");
            sb.append("	ORDER BY M.MD_DATE DESC														\n");
            sb.append("	LIMIT "+((nowPage-1)*PageCount)+", "+PageCount+"							\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    public ArrayList getMetaDataList(String sdate, String edate, String time, int k_xp, int k_yp, int nowPage, int PageCount)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.*, I.K_XP, I.K_YP													\n");
            sb.append("	FROM META M, IDX I															\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ													\n");
            sb.append("	  AND M.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'	\n");
            sb.append("	  AND I.K_XP = "+k_xp+"														\n");
            sb.append("	  AND I.K_YP = "+k_yp+"  													\n");
            sb.append("	  AND I.K_ZP <> 0															\n");
            sb.append("	GROUP BY M.MD_SEQ															\n");
            sb.append("	ORDER BY M.MD_DATE DESC														\n");
            sb.append("	LIMIT "+((nowPage-1)*PageCount)+", "+PageCount+"							\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    public int getMetaDataCount(String md_date, int k_xp, int k_yp)
    {
    	int result = 0;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT COUNT(*) AS CNT FROM (													\n");
            sb.append("		SELECT M.MD_SEQ															\n");
            sb.append("		FROM META M, IDX I														\n");
            sb.append("		WHERE M.MD_SEQ = I.MD_SEQ												\n");
            sb.append("	  	AND M.MD_DATE BETWEEN '"+md_date+" 00:00:00' AND '"+md_date+" 23:59:59'	\n");
            sb.append("	  	AND I.K_XP = "+k_xp+"													\n");
            sb.append("	  	AND I.K_YP = "+k_yp+"  													\n");
            sb.append("	  	AND I.K_ZP <> 0															\n");
            sb.append("	  	GROUP BY M.MD_SEQ														\n");
            sb.append("	  	)A																		\n");
            
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	result = rs.getInt("CNT");
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    }   
    
    public int getMetaDataCount(String sdate, String edate, String time, int k_xp, int k_yp)
    {
    	int result = 0;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT COUNT(*) AS CNT FROM (													\n");
            sb.append("		SELECT M.MD_SEQ															\n");
            sb.append("		FROM META M, IDX I														\n");
            sb.append("		WHERE M.MD_SEQ = I.MD_SEQ												\n");
            sb.append("	  	AND M.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'	\n");
            sb.append("	  	AND I.K_XP = "+k_xp+"													\n");
            sb.append("	  	AND I.K_YP = "+k_yp+"  													\n");
            sb.append("	  	AND I.K_ZP <> 0															\n");
            sb.append("	  	GROUP BY M.MD_SEQ														\n");
            sb.append("	  	)A																		\n");
            
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	result = rs.getInt("CNT");
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    } 
    
    public ArrayList getAttendIssue(String date, int k_xp, int count)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL 	\n");
            sb.append("	FROM META M, IDX I, SG_S_RELATION S					\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ							\n");
            sb.append("	  AND I.K_XP = "+k_xp+"								\n");
            sb.append("	  AND M.S_SEQ = S.S_SEQ								\n");
            sb.append("	  AND S.SG_SEQ IN (3,24,27,28,29,30,33)			\n");
            sb.append("	  AND M.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'				\n");
            sb.append("	GROUP BY M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL 	\n");
            sb.append("	ORDER BY MD_DATE DESC								\n");
            sb.append("	LIMIT "+count+"										\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    public ArrayList getAttendIssue(String sdate, String edate, String time, int k_xp, int count)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL 	\n");
            sb.append("	FROM META M, IDX I, SG_S_RELATION S					\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ							\n");
            sb.append("	  AND I.K_XP = "+k_xp+"								\n");
            sb.append("	  AND M.S_SEQ = S.S_SEQ								\n");
            sb.append("	  AND S.SG_SEQ IN (3,24,27,28,29,30,33)				\n");
            sb.append("	  AND M.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'				\n");
            sb.append("	GROUP BY M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL 	\n");
            sb.append("	ORDER BY MD_DATE DESC								\n");
            sb.append("	LIMIT "+count+"										\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 arrlist.add(mBean);
            } // end while                        

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

    public ArrayList getKeywordCollect()
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL, M.MD_SITE_NAME, I.ISSUE_CHECK 	\n");
            sb.append("	FROM META M, IDX I									\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ							\n");
            sb.append("	AND I.K_XP BETWEEN 34 AND 50						\n");
            sb.append("	GROUP BY M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL, M.MD_SITE_NAME 	\n");
            sb.append("	ORDER BY M.MD_SEQ DESC								\n");
            sb.append("	LIMIT 100												\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setIssue_check(rs.getString("ISSUE_CHECK"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    
 
    /**
    * 유사 기사 리스트 검색
    */
    public ArrayList getSameList( String md_pseq, String md_seq  )
    {
        ArrayList arrlist = new ArrayList();

        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            /*
            sb.append("SELECT M.MD_SEQ         ,                                   \n");
            sb.append("       M.S_SEQ        ,                                   \n");            
            sb.append("       M.MD_SITE_NAME       ,                                   \n");
            sb.append("       M.MD_MENU_NAME       ,                                   \n");
            sb.append("       M.MD_TYPE       ,                                   \n");
            sb.append("       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,          \n");
            sb.append("       M.MD_SAME_COUNT    ,                                   \n");
            sb.append("       M.MD_PSEQ        ,                                   \n");
            sb.append("       M.MD_TITLE      ,                                   \n");
            sb.append("       M.MD_URL        ,                                   \n");
            sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                \n");
            sb.append("       (SELECT DISTINCT ISSUE_CHECK FROM IDX WHERE MD_SEQ =M.MD_SEQ) AS ISSUE_CHECK,                \n");
            sb.append("       D.MD_CONTENT                                        \n");
            sb.append("FROM META M, DATA D	                             \n");
            sb.append("WHERE M.MD_SEQ = D.MD_SEQ          \n");           
            sb.append("   AND M.MD_PSEQ = " + md_pseq + "                          \n");
            sb.append(" ORDER BY M.MD_DATE ASC                                    										\n");
            */
            
            sb.append("SELECT A.* 																						\n");
            sb.append("     , (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = A.SG_SEQ) AS SG_NAME						\n");
            sb.append("  FROM (																							\n");
            sb.append("        SELECT A.* 																				\n");
            sb.append("             , FN_GET_KEYWORD(A.MD_SEQ) AS K_VALUE												\n");
            sb.append("             , (SELECT DISTINCT ISSUE_CHECK FROM IDX WHERE MD_SEQ =A.MD_SEQ) AS ISSUE_CHECK		\n");
            sb.append("             , (SELECT SG_SEQ FROM SG_S_RELATION WHERE S_SEQ = A.S_SEQ) AS SG_SEQ				\n");
            sb.append("          FROM (																					\n");
            sb.append("                SELECT M.MD_SEQ         ,														\n");                                   
            sb.append("                       M.S_SEQ        ,        													\n");                           
            sb.append("                       M.MD_SITE_NAME       ,       												\n");                            
            sb.append("                       M.MD_MENU_NAME       ,            										\n");                       
            sb.append("                       M.MD_TYPE       ,                      									\n");             
            sb.append("                       DATE_FORMAT(M.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,					\n");          
            sb.append("                       M.MD_SAME_COUNT    ,                                   					\n");
            sb.append("                       M.MD_PSEQ        ,                                   						\n");
            sb.append("                       M.MD_TITLE      ,                                   						\n");
            sb.append("                       M.MD_URL        ,                                   						\n");
            sb.append("                       D.MD_CONTENT                                        						\n");
            sb.append("                  FROM META M, DATA D	                             							\n");
            sb.append("                 WHERE M.MD_SEQ = D.MD_SEQ														\n");
            sb.append("                   AND M.MD_PSEQ = "+md_pseq+"  													\n");                      
            sb.append("               )A )A , IC_S_RELATION B	 														\n");
            sb.append(" WHERE A.SG_SEQ = B.S_SEQ 																		\n");
            sb.append(" ORDER BY A.SG_SEQ, A.MD_DATE ASC																\n");
            
            

            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {
            	
            	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setIssue_check(rs.getString("ISSUE_CHECK"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setSg_seq(rs.getString("SG_SEQ"));
	        	 mBean.setSg_name(rs.getString("SG_NAME"));
	        	
                arrlist.add(mBean);

            } // end while

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
    
    /**
     * 파일저장을 위한 조회
     */
     public ArrayList getSearchSaveList(    String psOrder   ,
                                            String md_seqs )
     {
         ArrayList arrlist = new ArrayList();

         try {

             dbconn = new DBconn();
             dbconn.getDBCPConnection();

             //stmt = dbn.createScrollStatement();
             stmt = dbconn.createStatement();

             sb = new StringBuffer();

             sb.append("SELECT M.MD_SEQ         ,                                   \n");
             sb.append("       M.S_SEQ        ,                                   \n");            
             sb.append("       M.MD_SITE_NAME       ,                                   \n");
             sb.append("       M.MD_MENU_NAME       ,                                   \n");
             sb.append("       M.MD_TYPE       ,                                   \n");
             sb.append("       M.MD_DATE  AS MD_DATE,          \n");
             sb.append("       M.MD_SAME_COUNT    ,                                   \n");
             sb.append("       M.MD_PSEQ        ,                                   \n");
             sb.append("       M.MD_TITLE      ,                                   \n");
             sb.append("       M.MD_URL        ,                                   \n");
             sb.append("       FN_GET_KEYWORD(M.MD_SEQ) AS K_VALUE,                  \n");
             sb.append("       D.MD_CONTENT                                           \n");
             sb.append("  FROM META M, DATA D                                        \n");          
             sb.append(" WHERE M.MD_SEQ IN (" + md_seqs + ")                           \n");
             sb.append("   AND M.MD_SEQ = D.MD_SEQ                                         \n");
             sb.append(" ORDER BY M." + psOrder + "                                      \n");

             //Log.debug(sb.toString() );
             rs = stmt.executeQuery(sb.toString());

             while( rs.next() ) {
                 mBeanDataCnt++;               
                 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_content(rs.getString("MD_CONTENT"));
	        	 mBean.setK_value(rs.getString("K_VALUE"));
            	
                arrlist.add(mBean);             
             } // end while

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
    
    

    /**
    * 전체 DB 그룹별 검색
    */
    public ArrayList getAllSearchList(     int    piNowpage ,
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
                                           String psMode    )
    {
        ArrayList arrlist = new ArrayList();

        try {

            //StringUtil  su = new StringUtil();

            getMaxMinNo( psDateFrom, psDateTo, "00", "23" );

            String MinMtNo= msMinNo;
            String MaxMtNo= msMaxNo;
            
            //psKeyWord = psKeyWord.replaceAll("[ ]{1,}|[ ]{1,}&\\?[ ]{1,}", "&");
            
            

            String sTypeCondition = convTypeCodeToSQL(psType);

            if ( psOrder == null || psOrder.equals("" ) ) {
                psOrder  = "MD_DATE";
            }


            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            //stmt = dbn.createScrollStatement();
            stmt = dbconn.createStatement();

            //총개시물 건수를 구한다.
            sb = new StringBuffer();

            //검색조건이 하나도 없을경우
            if( (psXp + psYp + psZp + psSGseq + psSDgsn + psKeyWord + sTypeCondition).equals("") ) {

                sb.append("SELECT COUNT(1) AS TOTAL_CNT                 \n");
                sb.append("  FROM META                                  \n");
                sb.append(" WHERE MD_SEQ = MD_PSEQ                        \n");
                sb.append("   AND MD_SEQ BETWEEN " + MinMtNo     +   "   \n");
                sb.append("                 AND " + MaxMtNo     +   "   \n");



            } else {
                sb.append("SELECT /*+ORDERED*/                              \n");
                sb.append("       COUNT(1) AS TOTAL_CNT                     \n");
                sb.append("  FROM META B, DATA A                                      \n");
                sb.append(" WHERE B.MD_SEQ = A.MD_SEQ                            \n");
                sb.append(" AND B.MD_SEQ = B.MD_PSEQ                            \n");
                sb.append(" AND B.MD_SEQ BETWEEN " + MinMtNo     +   "       \n");
                sb.append("                 AND " + MaxMtNo     +   "       \n");
               
                if ( !psSDgsn.equals("") )
                sb.append("   AND S_SEQ = " + psSDgsn + "                  \n");
                if ( !psKeyWord.equals("") )
                sb.append("   AND MD_TITLE LIKE '%" + psKeyWord + "%'       \n");
             
                if ( !sTypeCondition.equals("") )
                sb.append("   " + sTypeCondition   +                     "  \n");
            }

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
                sb.append("         WHERE K_XP = " + psXp + "                               \n");
                sb.append("           AND K_YP = 0                                          \n");
                sb.append("           AND K_ZP = 0                                          \n");
                sb.append("           AND K_TYPE < 3                                        \n");


                if ( !psYp.equals("") ) {
                    sb.append("        UNION                                                    \n");
                    sb.append("        SELECT '' , K_VALUE MAIN , ''                            \n");
                    sb.append("          FROM KEYWORD                                           \n");
                    sb.append("         WHERE K_XP = " + psXp + "                               \n");
                    sb.append("           AND K_YP = " + psYp + "                               \n");
                    sb.append("           AND K_ZP = 0                                          \n");
                    sb.append("           AND K_TYPE < 3                                        \n");
                }

                if ( !psZp.equals("") ) {
                    sb.append("        UNION                                                    \n");
                    sb.append("        SELECT '' , '' , K_VALUE MAIN                            \n");
                    sb.append("          FROM KEYWORD                                           \n");
                    sb.append("         WHERE K_XP = " + psXp + "                               \n");
                    sb.append("           AND K_YP = " + psYp + "                               \n");
                    sb.append("           AND K_ZP = " + psZp + "                               \n");
                    sb.append("           AND K_TYPE < 3                                        \n");
                }

                sb.append("        ) ");



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
            } else {
                if(psMode.equals("ALLDB")){
                	msKeyTitle = "전체DB검색";
                	if(psKeyWord.length()>0){
                		msKeyTitle += "-"+psKeyWord;
                	}
                }else {
                	msKeyTitle = "전체키워드";
                }
            }

            //게시물 리스트를 구한다.
            if ( mBeanTotalCnt > 0 ) {

                sb = new StringBuffer();                
                
                
                int liststart;
             	int listend;             	

        		liststart = (piNowpage-1) * piRowCnt;
        		listend = piRowCnt;                     
	         
	           sb.append("                 SELECT B.MD_SEQ         ,                                    \n");
	           sb.append("                        B.S_SEQ        ,                                    \n");                
	           sb.append("                        B.MD_SITE_NAME       ,                                    \n");
	           sb.append("                        B.MD_MENU_NAME       ,                                    \n");
	           sb.append("                        B.MD_TYPE       ,                                    \n");
	           sb.append("                        DATE_FORMAT(B.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE,                                \n");	           
	           sb.append("                        B.MD_SAME_COUNT    ,                                    \n");
	           sb.append("                        B.MD_PSEQ        ,                                    \n");
	           sb.append("                        B.MD_TITLE      ,                                    \n");
	           sb.append("                        B.MD_URL  ,                                             \n");  
	           sb.append("                        D.MD_CONTENT                                             \n");
	           sb.append("                  FROM META B, DATA D                                              \n");
	           sb.append("                  WHERE                                   \n");
	           sb.append("                       B.MD_SEQ BETWEEN " + MinMtNo + "                      \n");	           
	           sb.append("                                    AND " + MaxMtNo + "                      \n");
	           sb.append("                       AND B.MD_SEQ = D.MD_SEQ                    \n");
	           sb.append("                       AND B.MD_SEQ = B.MD_PSEQ                    \n");
	           if ( !psSDgsn.equals("") )
	           sb.append("                       AND B.S_SEQ = " + psSDgsn + "                           \n");
	           if ( !psKeyWord.equals("") )
	           sb.append("                       AND B.MD_TITLE LIKE '%" + psKeyWord + "%'              \n");
	           if ( !sTypeCondition.equals("") )
	           sb.append("                   " + sTypeCondition   +                      "             \n");
	           sb.append("                   ORDER BY B."+psOrder+"             \n");         	          
	           sb.append("LIMIT "+liststart+","+listend+"                                                  \n");
              	           	
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
                    
                    mBean  = new MetaBean();
					mBean.setMd_seq(rs.getString("MD_SEQ"));
					mBean.setS_seq(rs.getString("S_SEQ"));
					mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
					mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
					mBean.setMd_type(rs.getString("MD_TYPE"));
					mBean.setMd_date(rs.getString("MD_DATE"));
					mBean.setMd_pseq(rs.getString("MD_PSEQ"));
					mBean.setMd_title(rs.getString("MD_TITLE"));
					mBean.setMd_url(rs.getString("MD_URL"));
					mBean.setMd_content(rs.getString("MD_CONTENT"));
					mBean.setK_value("");
					mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));                    
	   	        	arrlist.add(mBean);

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
    
    
    
    /**
     * 키워드 그룹별 검색 오버로딩 (모바일 웹용)
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
								            String psMname   
								            
										   )
    {
    	return getKeySearchList(         piNowpage ,
					                     piRowCnt  ,
						                 psXp      ,
						                 psYp      ,
						                 psZp      ,
						                 psSGseq   ,
						                 psSDgsn   ,
						                 psDateFrom,
						                 psDateTo  ,
						                 psKeyWord ,
						                 psType    ,
						                 psOrder   ,
						                 psMode	,
						                 psMname   ,
						                 "N",
						                 "",
						                 "",
						                 "00",
						                 "23",
						                 ""
						                 
								   );
    }	
    

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
            String psMname   ,
            String bookMarkYn,
            String language,
            String psMseq,
            String stime,
            String etime,
            String stName
		   ){
    	return getKeySearchList(piNowpage, piRowCnt, psXp, psYp, psZp, psSGseq, psSDgsn, psDateFrom, psDateTo, psKeyWord, psType, psOrder, psMode, psMname, bookMarkYn, language, psMseq, stime, etime, stName, "", "", "");
    }
	/**
	* 키워드 그룹별 검색(정보 검색용 :북마크 추가)
	*/
	@SuppressWarnings("unchecked")
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
	                                       String psMname   ,
	                                       String bookMarkYn,
	                                       String language,
	                                       String psMseq,
	                                       String stime,
	                                       String etime,
	                                       String stName,
	                                       String issue_check,
	                                       String tiers,
	                                       String searchType
									   )
	
	
	{	
		ArrayList arrlist = new ArrayList();
	    try {
	    	getMaxMinNo( psDateFrom, psDateTo, stime, etime, stName );
	    	String keywordFunction = "";
	        String MinMtNo= msMinNo;
	        String MaxMtNo= msMaxNo;
	        String addQuery = "";      
	        String sTypeCondition = convTypeCodeToSQL(psType);
	        
	        
	        String[] arrKeyWord = null;
			arrKeyWord = psKeyWord.split("&");
		
	        if(psMode.equals("DELIDX")){
	        	addQuery = " AND A.I_STATUS = 'T'";
	        	if(psMname.length()>0){
	        //		addQuery += " AND A.M_SEQ = "+psMname;
		        }  		        	
	        }else{
	        	addQuery = " AND (A.I_STATUS = 'N' AND A.M_SEQ <> "+psMname+")";
	        }
	      
	        dbconn = new DBconn();
	        dbconn.getDBCPConnection();
	
	        //stmt = dbn.createScrollStatement();
	        stmt = dbconn.createStatement();
	           
            sb = new StringBuffer();
            sb.append("  SELECT ROWNUM                                     \n");
	        sb.append("  FROM                                     \n");
            sb.append("  (                                     \n");
	        sb.append("  SELECT                                     \n");
	        sb.append("         md_seq, @RNUM:=@RNUM+1 AS ROWNUM                               \n");
	        sb.append("  FROM   (                              \n");	       
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ, (SELECT @RNUM:=0) ROW                     \n");		   
            sb.append("                           FROM "+stName+"IDX A, "+stName+"META B		                   \n");
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                             \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
            if ( !psKeyWord.equals("") ){
            	for(int i = 0; i < arrKeyWord.length; i++){
					sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
				}
            }		            
            if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
            if ( !psXp.equals("") )
            sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
            if ( !psSGseq.equals("") )
	        sb.append("                                 AND A.SG_SEQ  IN (" + psSGseq + ")                   \n");
            if(!issue_check.equals("") )
            sb.append("                                 AND A.ISSUE_CHECK IN ( '"+issue_check+"' )                   \n");
            if(!tiers.equals("")){
            	sb.append("                                 AND B.TS_TYPE IN ("+tiers+")                    \n");
            }
            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
           /* if(psMode.equals("DELIDX")){
            	sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
            }else{
            	sb.append("                                 AND B.MD_SEQ = B.MD_PSEQ                   \n");
            }*/
            
            if(!language.equals("")){
            	sb.append("                                 AND B.L_ALPHA = '"+language+"'                   \n");
            }
            
            sb.append("                                 "+addQuery+"                   \n");
            
            if ( !sTypeCondition.equals("") )
	        sb.append("                                 " + sTypeCondition   +                      "      \n");
            if(psMode.equals("DELIDX")){
	        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
	        }else{
	        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
	        }
            sb.append("  						   ORDER BY MD_SEQ DESC\n");
            sb.append("  			)RESULT                              \n");
            sb.append("    )RESULT                              \n");
            sb.append("    WHERE  MD_SEQ IN :BOOKMARK                             \n");
	        
            BookMarkQuery = sb.toString();
            
            //북마크찾기 일시 페이지 값
            bmMgr = new BookMarkMgr();
            if(bookMarkYn.equals("Y")){            	
            	bookMarkNum = bmMgr.getBookMarkNum("search");
            	
            	//현재 리스트 카운트 기준이 MD_SEQ가 아닌 MD_PSEQ이기 때문에 번호대역대별로 차이 수가 존재하므로  차이를 계산해줘야함           	
            	bookMarkPage = bmMgr.getPageNum("search", piRowCnt, BookMarkQuery);
            	 
            	//북마크 번호가 시작 날짜의 MD_SEQ 최소값 보다 적으면 페이지 설정 초기화
            	if(Integer.parseInt(bookMarkNum)<Integer.parseInt(MinMtNo)) bookMarkPage = 1;
            	piNowpage = bookMarkPage;
            	
            	System.out.println("bookMarkPage:"+bookMarkPage);
            }                              
	        
	        //총게시물 건수를 구한다.
	        sb = new StringBuffer();
	        sb.append("  SELECT                                     \n");
	        sb.append("         COUNT(1) AS TOTAL_CNT                              \n");
	        sb.append("  FROM   (                              \n");	       
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ                     \n");		   
            sb.append("                           FROM "+stName+"IDX A, "+stName+"META B		                   \n");
            if(!psKeyWord.equals("") && searchType.equals("2") ){
            sb.append("                                , "+stName+"DATA C				                               \n");
            }
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                             \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
            if ( !psKeyWord.equals("")){
            	if(searchType.equals("1")){
            		for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}else if(searchType.equals("2")){
            		sb.append("                             AND B.MD_SEQ = C.MD_SEQ				                               \n");
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND CONCAT(B.MD_TITLE, C.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}else if(searchType.equals("3")){
            		for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND B.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}else{
            		//모바일 제목 검색
            		for(int i = 0; i < arrKeyWord.length; i++){
    					sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
    				}
            	}
            	
            }		
            if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
            if ( !psXp.equals("") )
            sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
            if ( !psSGseq.equals("") )
	        sb.append("                                 AND A.SG_SEQ  IN (" + psSGseq + ")                   \n");
            if(!issue_check.equals(""))
            sb.append("                                 AND A.ISSUE_CHECK IN ( '"+issue_check+"' )                   \n");
           /* if(psMode.equals("DELIDX")){
            	sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
            }else{
            	sb.append("                                 AND B.MD_SEQ = B.MD_PSEQ                   \n");
            }*/
            if(!language.equals("")){
            	sb.append("                                 AND B.L_ALPHA = '"+language+"'                   \n");
            }
            if(!tiers.equals("")){
            	sb.append("                                 AND B.TS_TYPE IN ("+tiers+")                    \n");
            }
            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
            sb.append("                                 "+addQuery+"                   \n");
            if ( !sTypeCondition.equals("") )
	        sb.append("                                 " + sTypeCondition   +                      "      \n");
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
	        
	        //출처별 전체 건수 구하기~(유사포함.)
	        sb = new StringBuffer();
	        
	        sb.append("SELECT A.SG_SEQ 																\n");
	        sb.append("     , (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = A.SG_SEQ) AS SG_NAME	\n");
	        sb.append("     , A.CNT																	\n");
	        sb.append("FROM (																		\n");
	        sb.append("  SELECT A.SG_SEQ                                    \n");
	        sb.append("       , COUNT(1) AS CNT                              \n");
	        sb.append("  FROM   (                              \n");	       
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ                     \n");
            sb.append("                                , MIN(A.SG_SEQ) AS SG_SEQ                     		\n");
            sb.append("                           FROM "+stName+"IDX A, "+stName+"META B		                   \n");
            if(!psKeyWord.equals("") && searchType.equals("2") ){
            sb.append("                                , "+stName+"DATA C				                               \n");
            }
            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                             \n");
            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
            if ( !psKeyWord.equals("")){
            	if(searchType.equals("1")){
            		for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}else if(searchType.equals("2")){
            		sb.append("                             AND B.MD_SEQ = C.MD_SEQ				                               \n");
	            	for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND CONCAT(B.MD_TITLE, C.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}else if(searchType.equals("3")){
            		for(int i = 0; i < arrKeyWord.length; i++){
						sb.append("            AND B.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");
					}
            	}else{
            		//모바일 제목 검색
            		for(int i = 0; i < arrKeyWord.length; i++){
    					sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
    				}
            	}
            	
            }		            
            if ( !psSDgsn.equals("") )
            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
            if ( !psXp.equals("") )
            sb.append("                                 AND A.K_XP    IN (" + psXp + ")               \n");
            if ( !psYp.equals("") )
            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
            if ( !psZp.equals("") )
            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
            if ( !psSGseq.equals("") )
	        sb.append("                                 AND A.SG_SEQ  IN (" + psSGseq + ")                   \n");
            if(!issue_check.equals(""))
            sb.append("                                 AND A.ISSUE_CHECK IN ( '"+issue_check+"' )                   \n");
           /* if(psMode.equals("DELIDX")){
            	sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
            }else{
            	sb.append("                                 AND B.MD_SEQ = B.MD_PSEQ                   \n");
            }*/
            /*if(!language.equals("")){
            	sb.append("                                 AND B.L_ALPHA = '"+language+"'                   \n");
            }*/
            if(!tiers.equals("")){
            	sb.append("                                 AND B.TS_TYPE IN ("+tiers+")                    \n");
            }
            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
            sb.append("                                 "+addQuery+"                   \n");
            if ( !sTypeCondition.equals("") )
	        sb.append("                                 " + sTypeCondition   +                      "      \n");
            if(psMode.equals("DELIDX")){
	        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
	        }else{
	        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
	        }
            sb.append("  			)A GROUP BY A.SG_SEQ    )A                         \n");
            sb.append("             , IC_S_RELATION B WHERE A.SG_SEQ = B.S_SEQ ORDER BY B.IC_ORDER		\n");
	        Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        
	        int totCnt = 0;
	        while ( rs.next() ) {
	        	totCnt += rs.getInt("CNT");
	        	
	            arrSourceCnt.add(rs.getString("SG_NAME") + "," + rs.getString("CNT")); 
	        }
	        arrSourceCnt.add("전체" + "," + String.valueOf(totCnt));
	        
	
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
	            
	            //키워드 펑션 생성 
	            if(stName.equals("")){
	            	if(psXp.length()>0){
		            	if(psXp.split(",").length>1)
		            	{
		            		keywordFunction = "FN_GET_KEYWORD(A.MD_SEQ)";
		            	}else{
		            		keywordFunction = "FN_GET_KEYWORD2(A.MD_SEQ,"+psXp+")";
		            	}
		            }
		            if(psYp.length()>0) keywordFunction = "FN_GET_KEYWORD3(A.MD_SEQ,"+psXp+","+psYp+")";
		            if(psZp.length()>0) keywordFunction = "FN_GET_KEYWORD4(A.MD_SEQ,"+psXp+","+psYp+","+psZp+")";
	            }else{
	            	keywordFunction = "A.EX_NAME";
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
	         		
	         		sb.append("SELECT A.*    											\n");
	         		sb.append("     , "+keywordFunction+" AS K_VALUE  					\n");
	         		sb.append("     , FN_GET_COMFIRM("+(psMseq.equals("") ? "null" : psMseq) +",A.MD_SEQ) AS COMFIRM  	\n");
	         		//sb.append("     , IF(A.S_SEQ = 10464, (SELECT T_FOLLOWERS FROM TWEET WHERE MD_SEQ = A.MD_SEQ LIMIT 1) ,'') AS T_FOLLOWERS\n");
	         		//sb.append("     , IF(A.S_SEQ = 10464, IF(INSTR(MD_TITLE,'@') > 0, IFNULL((SELECT T_FOLLOWERS FROM TWEET WHERE T_USER_ID = (REPLACE((LEFT(RIGHT(A.MD_TITLE, INSTR(REVERSE(A.MD_TITLE),'@')-1), INSTR(RIGHT(A.MD_TITLE, INSTR(REVERSE(A.MD_TITLE),'@')-1),' ')-1)),':','')) LIMIT 1 ),'')   ,(SELECT T_FOLLOWERS FROM TWEET WHERE MD_SEQ = A.MD_SEQ LIMIT 1)    ) ,'') AS T_FOLLOWERS  \n");
	         		sb.append(", '' AS T_FOLLOWERS\n");
	         		
	         		sb.append("  FROM (  												\n");  
		            sb.append("                 SELECT 					                                     \n");
		            //sb.append("                        "+keywordFunction+" AS K_VALUE         ,        \n");
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
		            sb.append("                        A.MD_IMG  ,                                           \n");
		            sb.append("                        B.MD_CONTENT                                             \n");
		            if(!stName.equals("")){
		            	sb.append("                                ,A.EX_NAME   \n");
		            	//sb.append("                                ,A.EX_KEYWORD   \n");
		            }
		            sb.append("                   FROM (                                                    \n");
		            sb.append("                           SELECT				                               \n");
		            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE ,B.MD_DATE ,B.MD_IMG  \n");
		            sb.append("                                ,B.MD_SAME_COUNT ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, ISSUE_CHECK, I_DELDATE, M_SEQ   \n");
		            if(!stName.equals("")){
		            	sb.append("                                ,B.EX_NAME   \n");
		            	//sb.append("                                ,D.EX_KEYWORD   \n");
		            }
		            sb.append("                           FROM "+stName+"IDX A, "+stName+"META B		                   \n");
		            if(!psKeyWord.equals("") && searchType.equals("2")){
		                sb.append("                                , "+stName+"DATA C				                               \n");
		            }
		            //if(!stName.equals("")){
		            	//sb.append("                                , EX_KEYWORD_INFO D				                               \n");
		            //}
		            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                            \n");
		            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
		            //if(!stName.equals("")){
		            //	sb.append("                                 AND B.D_SEQ = D.D_SEQ                            \n");
		            //}
		            if ( !psKeyWord.equals("")){
		            	if(searchType.equals("1")){
		            		for(int i = 0; i < arrKeyWord.length; i++){
								sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
							}
		            	}else if(searchType.equals("2")){
		            		sb.append("                             AND B.MD_SEQ = C.MD_SEQ				                               \n");
			            	for(int i = 0; i < arrKeyWord.length; i++){
								sb.append("            AND CONCAT(B.MD_TITLE, C.MD_CONTENT) LIKE '%" + arrKeyWord[i] + "%'        \n");
							}
		            	}else if(searchType.equals("3")){
		            		for(int i = 0; i < arrKeyWord.length; i++){
								sb.append("            AND B.MD_SITE_NAME LIKE '%" + arrKeyWord[i] + "%'        \n");
							}
		            	}else{
		            		//모바일 제목 검색
		            		for(int i = 0; i < arrKeyWord.length; i++){
		    					sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
		    				}
		            	}
		            	
		            }		            
		            if ( !psSDgsn.equals("") )
		            sb.append("                                 AND B.S_SEQ = '" + psSDgsn + "'                   \n");
		            if ( !psXp.equals("") )
		            sb.append("                                 AND A.K_XP   IN ( " + psXp + " )               \n");
		            if ( !psYp.equals("") )
		            sb.append("                                 AND A.K_YP    = " + psYp + "               \n");
		            if ( !psZp.equals("") )
		            sb.append("                                 AND A.K_ZP    = " + psZp + "               \n");
		            if ( !psSGseq.equals("") )
			        sb.append("                                 AND A.SG_SEQ IN (" + psSGseq + ")                   \n");
		            if(!issue_check.equals(""))
		            sb.append("                                 AND A.ISSUE_CHECK IN ('"+issue_check+"')            \n");
		            if(!tiers.equals("")){
		            	sb.append("                                 AND B.TS_TYPE IN ("+tiers+")                    \n");
		            }
		            /*if(psMode.equals("DELIDX")){
		            	sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
		            }else{
		            	sb.append("                                 AND B.MD_SEQ = B.MD_PSEQ                   \n");
		            }*/
		            if(!language.equals("")){
		            	sb.append("                                 AND B.L_ALPHA = '"+language+"'                   \n");
		            }
		            sb.append("                                 AND B.MD_SEQ = B.MD_SEQ                   \n");
		            sb.append("                                 "+addQuery+"                   \n");
		            		            
		            if ( !sTypeCondition.equals("") )
		            sb.append("                            " + sTypeCondition   +                      "      \n");
		            
		            if(psMode.equals("DELIDX")){
			        	sb.append("                        GROUP BY B.MD_SEQ                                  \n");
			        }else{
			        	sb.append("                        GROUP BY B.MD_PSEQ                                  \n");
			        }
		            
		            sb.append("                        ) A ,                                                \n");
		            sb.append("                        "+stName+"DATA B                                       \n");		           		    	       
		            sb.append("                  WHERE                                   \n");
		            sb.append("                        A.MD_SEQ = B.MD_SEQ                                    \n");
		            sb.append("         	     ORDER BY " + psOrder + "                                                 \n");
		            sb.append("         		 LIMIT "+liststart+","+listend+"                                 \n");
		            sb.append(" )A      \n");
		            
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
						mBean.setMd_comfirm(rs.getString("COMFIRM"));
						mBean.setT_followers(rs.getString("T_FOLLOWERS"));
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
						mBean.setMd_comfirm(rs.getString("COMFIRM"));
						mBean.setT_followers(rs.getString("T_FOLLOWERS"));
						
						if(!stName.equals("")){
							mBean.setEx_keyword_name(rs.getString("EX_NAME"));	
						}
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
	
	 /**
     * 포털별 검색 리스트
     */
    public ArrayList getPortalList(int    piNowpage ,
    		                       int    piRowCnt  ,
    		                       String psDateFrom,
    		                       String psDateTo  ,
    		                       String psKeyWord ){
    	
    	ArrayList resultArr = new ArrayList();
    	PortalBean bean = null;
    	
    	 
      	
    	try{
    		
    		int liststart = 0;
       	 	int listend = 0 ;
         	
         	if (piNowpage == 0) 
         		piNowpage = 1;
         	else {
         		liststart = (piNowpage-1) * piRowCnt;
         		listend =    piRowCnt;
         	}
    		
    		
    		dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            
            
            sb = new StringBuffer();
            
            sb.append(" SELECT COUNT(DISTINCT A.T_SEQ) AS CNT        \n");
            sb.append(" FROM TOP A, TOP_IDX B  \n");
            sb.append(" WHERE A.T_DATETIME BETWEEN '"+psDateFrom+" 00:00:00' AND '"+psDateTo+" 23:59:59'  \n");
            if(!psKeyWord.equals("")){
            	sb.append("   AND T_TITLE LIKE '%"+psKeyWord+"%'                 \n");
            }
            sb.append("  AND A.T_SEQ = B.T_SEQ		\n");
            
                        
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            if(rs.next()){
            	portalCnt = rs.getInt("CNT");
            }
           
            sb = null; 
            sb = new StringBuffer();
            
            sb.append("SELECT A.T_SEQ	       \n");
            sb.append("     , A.T_SITE	       \n");
            sb.append("     , DATE_FORMAT(A.T_DATETIME,'%Y-%m-%d %H:%i:%s') AS T_DATETIME	       \n");
            sb.append("     , A.T_URL	       \n");
            sb.append("     , A.T_TITLE	       \n");
            sb.append("     , '' AS T_HTML	       \n");
            sb.append("     , TIMEDIFF(A.T_PRESENTTIME,A.T_DATETIME) AS EXPOSETIME	       \n");
            sb.append("     , B.ISSUE_CHECK\n");
            sb.append("     , A.S_SEQ \n");
            sb.append("     , A.T_BOARD \n");
            sb.append("  FROM TOP A, TOP_IDX B  \n");
            sb.append(" WHERE A.T_DATETIME BETWEEN '"+psDateFrom+" 00:00:00' AND '"+psDateTo+" 23:59:59'  \n");
            if(!psKeyWord.equals("")){
            	sb.append("   AND A.T_TITLE LIKE '%"+psKeyWord+"%'                                                                     \n");
            }
            sb.append("   AND A.T_SEQ = B.T_SEQ						\n");
            sb.append(" ORDER BY A.T_SEQ DESC                                                                                           \n");
            sb.append(" LIMIT "+liststart+","+listend+"                                     \n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while(rs.next()){
            	bean = new PortalBean();
            	bean.setNo(rs.getInt("T_SEQ"));
            	bean.setName(rs.getString("T_SITE"));
            	bean.setStime(rs.getString("T_DATETIME"));
            	bean.setUrl(rs.getString("T_URL"));
            	bean.setTitle(rs.getString("T_TITLE"));
            	bean.setHtml(rs.getString("T_HTML"));
            	bean.setExposeTime(rs.getString("EXPOSETIME"));
            	bean.setIssue_check(rs.getString("ISSUE_CHECK"));
            	bean.setS_seq(rs.getString("S_SEQ"));
            	bean.setT_board(rs.getString("T_BOARD"));
            	
            	resultArr.add(bean);
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
	    return  resultArr;
    }

	/**
	* 휴지통에 들어있는 기사 카운트
	*/
    public String getIdxDelCNT(String m_seq){
    	return getIdxDelCNT(m_seq,"");
    }
	public String getIdxDelCNT(String m_seq, String stName){
		String IdxDelCNT ="0";
		
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            //게시물 리스트를 구한다.

            sb = new StringBuffer();

            sb.append("SELECT COUNT(DISTINCT(MD_SEQ)) AS TOTAL_CNT FROM "+stName+"IDX WHERE I_STATUS = 'T' "); // AND M_SEQ="+m_seq+" \n");
                   
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {

            	IdxDelCNT = rs.getString("TOTAL_CNT");

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
		
		return IdxDelCNT;
	}
	
	/**
	* IDX  truncate:휴지통 , revert:복원, del:영구삭제, delAll:휴지통비우기
	*/
	
	public boolean idxProcess(String mode, String key, String m_seq){
		return idxProcess(mode, key, m_seq, "");
	}
	public boolean idxProcess(String mode, String key, String m_seq, String stName)
	{
		DateUtil du = new DateUtil();
		boolean result = false;
		 try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            //md_pseq를 구한다
            sb = new StringBuffer();
            String Tempno = "";
            //sb.append("SELECT MD_SEQ FROM META WHERE MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = "+key+")");
            
            
            if(key.length()>0){
	            sb.append(" SELECT M1.MD_SEQ               \n");
	            sb.append("   FROM "+stName+"META M1, "+stName+"META M2        \n"); 
	            sb.append("  WHERE M1.MD_PSEQ = M2.MD_PSEQ \n");
	            sb.append("    AND M2.MD_SEQ IN ("+ key +")\n");
	            
	            rs = stmt.executeQuery(sb.toString());
	            while(rs.next()){
	            	if(Tempno.equals("")){
	            		Tempno = rs.getString("MD_SEQ");
	            	}else{
	            		Tempno += ","+rs.getString("MD_SEQ");
	            	}
	            }
            }
            //게시물 리스트를 구한다.
            

            sb = new StringBuffer();
            if(mode.equals("truncate")){
            	sb.append("UPDATE "+stName+"IDX SET I_STATUS='T' , M_SEQ="+m_seq+", I_DELDATE='"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' WHERE MD_SEQ IN ("+Tempno+")");
            }else if(mode.equals("revert")){
            	sb.append("UPDATE "+stName+"IDX SET I_STATUS='N' , M_SEQ = 0 WHERE MD_SEQ IN ("+Tempno+")");
            }else if(mode.equals("del")){
            	sb.append("DELETE FROM "+stName+"IDX WHERE I_STATUS='T' AND M_SEQ = "+m_seq);
            }else if(mode.equals("delAll")){
            	sb.append("DELETE FROM "+stName+"IDX WHERE I_STATUS='T' ");
            }          
                   
            if(stmt.executeUpdate(sb.toString())>0) result = true;

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {            
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return result;
	}
	
	public boolean restoreProcess(String m_seq)
	{
		DateUtil du = new DateUtil();
		boolean result = false;
		 try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            //md_pseq를 구한다
            sb = new StringBuffer();
            sb.append("INSERT INTO META (MD_SEQ,MD_SITE_NAME,MD_MENU_NAME,MD_TYPE,MD_DATE,MD_TITLE,MD_URL,MD_SAME_COUNT,MD_PSEQ,S_SEQ,MD_IMG,L_ALPHA,TS_TYPE,TS_RANK)\n");
            sb.append("          (SELECT MD_SEQ,MD_SITE_NAME,MD_MENU_NAME,MD_TYPE,MD_DATE,MD_TITLE,MD_URL,MD_SAME_COUNT,MD_PSEQ,S_SEQ,MD_IMG,L_ALPHA,TS_TYPE,TS_RANK FROM EXCEPTION_META WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("INSERT INTO DATA (MD_SEQ,MD_CONTENT)\n");
            sb.append("          (SELECT MD_SEQ,MD_CONTENT FROM EXCEPTION_DATA WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("INSERT INTO IDX (MD_SEQ,K_XP,K_YP,K_ZP,SG_SEQ,I_STATUS,M_SEQ,ISSUE_CHECK,I_DELDATE)\n");
            sb.append("         (SELECT MD_SEQ,K_XP,K_YP,K_ZP,SG_SEQ,I_STATUS,M_SEQ,ISSUE_CHECK,I_DELDATE FROM EXCEPTION_IDX WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("INSERT INTO TWEET (MD_SEQ,T_TWEET_ID,T_USER_ID,T_IS_RT,T_FOLLOWERS,T_FOLLOWING,T_TWEET,T_SOURCE,L_ALPHA,MD_TITLE,MD_DATE)\n");
            sb.append("           (SELECT MD_SEQ,T_TWEET_ID,T_USER_ID,T_IS_RT,T_FOLLOWERS,T_FOLLOWING,T_TWEET,T_SOURCE,L_ALPHA,MD_TITLE,MD_DATE FROM EXCEPTION_TWEET WHERE MD_SEQ = "+m_seq+")\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_META WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_DATA WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_IDX WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("DELETE FROM EXCEPTION_TWEET WHERE MD_SEQ = "+m_seq+"\n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            		
            

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {            
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return result;
	}
	
	/**
	 * 모바일 웹
	 * 키워드 정보를 가져온다
	 */
	public ArrayList getMobileKeyword(String psXp, String psYp, String psZp ) {

        ArrayList arrlist = new ArrayList();

        try {

            sb = new StringBuffer();
            sb.append("SELECT K_SEQ                       \n");
            sb.append("       ,K_XP                       \n");
            sb.append("       ,K_YP                       \n");
            sb.append("       ,K_ZP                       \n");
            sb.append("       ,K_VALUE                       \n");
            sb.append("  FROM KEYWORD                    \n");
            //sb.append(" WHERE K_TYPE < 11  \n");
            sb.append(" WHERE 1 = 1  \n");
            if ( psXp.length()>0 )
            	sb.append(" AND K_XP IN ("+psXp+")                   \n");
            if ( psYp.length()>0 )
            	sb.append(" AND K_YP IN ("+psYp+")                   \n");
            
            if ( psZp.length()>0 )
            	sb.append(" AND K_ZP IN ("+psZp+")                   \n");
            else
            	sb.append(" AND K_ZP = 0                   \n");
            
            sb.append(" ORDER BY K_POS, K_XP, K_YP, K_ZP, K_VALUE                   \n");
            System.out.println(sb.toString());
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            while( rs.next() ) {
            	keywordInfo kBean = new keywordInfo();
            	kBean.setK_Seq(rs.getString("K_SEQ"));
            	kBean.setK_Xp(rs.getString("K_XP"));
            	kBean.setK_Yp(rs.getString("K_YP"));
            	kBean.setK_Zp(rs.getString("K_ZP"));
            	kBean.setK_Value(rs.getString("K_VALUE"));
                arrlist.add( kBean );
            }

            sb = null;

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
	
	public ArrayList Get_portal_total_list(String sd_gsn, int nowpage, int piRowCnt, String keyword, String sDateFrom, String sDateTo) {
  	  ArrayList list = new ArrayList();
  	  //sDateFrom = sDateFrom.replaceAll("-","");
  	  //sDateTo = sDateTo.replaceAll("-","");
  	  //System.out.println("sd_gsn : "+sd_gsn);
  	  try {
  		  
  		  
  		  
  		  
  		int liststart = 0;
   	 	int listend = 0 ;
     	
     	if (nowpage == 0) 
     		nowpage = 1;
     	else {
     		liststart = (nowpage-1) * piRowCnt;
     		listend =    piRowCnt;
     	}
  		  
  		  
  		  dbconn = new DBconn();
  		  dbconn.getDBCPConnection();
  		  stmt = dbconn.createStatement();
  		  String sXpValue = "";
	        String sYpValue = "";
	        String sZpValue = "";
	
	        
	        msKeyTitle = "초기면 전체";
			if(keyword.length()>0){
				msKeyTitle += "-"+keyword;
			}
	        
  		  sb = new StringBuffer();
  
  		  sb.append("SELECT COUNT(*) AS CNT\n");
  		  sb.append("  FROM TOP	A			\n");
  		  sb.append("     , PORTAL_IDX B				\n");
  		  sb.append(" WHERE A.T_STAMP BETWEEN UNIX_TIMESTAMP('"+sDateFrom+" 00:00:00') AND UNIX_TIMESTAMP('"+sDateTo+" 23:59:59')\n");  
  		  sb.append("	AND A.T_SEQ = B.P_SEQ  \n");
  		  if(!sd_gsn.equals("0")){
			  sb.append("	    AND A.S_SEQ IN ("+sd_gsn+")   																								\n");
		  }
		  if(!keyword.equals("")){
			  sb.append("	    AND A.T_TITLE LIKE '%"+keyword+"%'   																							\n");
		  }
  		  sb.append(" ORDER BY A.T_STAMP DESC\n");
  		  
  		  
  		  
  		  //System.out.println(sb.toString());
  		  Log.writeExpt(sb.toString());
  		  rs = stmt.executeQuery(sb.toString());
  		  if(rs.next()){
  			  miTotalCnt = rs.getInt("CNT");
  		  }
  		  
  		  sb = new StringBuffer();

  		  sb.append("SELECT A.T_SEQ ,A.S_SEQ, A.T_SITE, FROM_UNIXTIME(A.T_STAMP) AS STIME , A.T_URL, A.T_TITLE, A.T_STAMP, '' AS ISSUE_CHECK \n");
  		  sb.append("	  FROM TOP        A\n");
  		  sb.append("		   , PORTAL_IDX B\n");
  		  sb.append("WHERE A.T_STAMP BETWEEN UNIX_TIMESTAMP('"+sDateFrom+" 00:00:00') AND UNIX_TIMESTAMP('"+sDateTo+" 23:59:59')\n");  
  		  sb.append("AND A.T_SEQ = B.P_SEQ \n");
  		  if(!sd_gsn.equals("0")){
			  sb.append("	    AND A.S_SEQ IN ("+sd_gsn+")   																								\n");
		  }
		  if(!keyword.equals("")){
			  sb.append("	    AND A.T_TITLE LIKE '%"+keyword+"%'   																							\n");
		  }
  		  sb.append("ORDER BY A.T_STAMP DESC\n");
  		  sb.append("LIMIT "+liststart+", "+listend+"\n");
  		  
  		  Log.writeExpt(sb.toString());
  		  rs = stmt.executeQuery(sb.toString());
  		  while(rs.next()) {
				    			  list.add(new SearchPortalBean(
					  						rs.getString("T_SEQ"),
					  						rs.getString("S_SEQ"),
					  						rs.getString("T_SITE"),
					  						rs.getString("STIME"),
					  						rs.getString("T_URL"),
					  						rs.getString("T_TITLE"),
					  						rs.getString("T_STAMP"),
					  						rs.getString("T_STAMP"),
					  						rs.getString("ISSUE_CHECK"),
					  						""
					  						)
				    			  );
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
  	  return list;
    }
	
	/**
	 * 열어본 페이지 저장 
	 */
	public ArrayList insertComfirm(String m_seq, String md_seq) {

        ArrayList arrlist = new ArrayList();

        try {

            sb = new StringBuffer();
            
            sb.append("INSERT INTO MEMBER_COMFIRM ( M_SEQ, MD_SEQ, M_REGDATE)		\n");
            sb.append("                    VALUES ( "+m_seq+", "+md_seq+", NOW())	\n");
            sb.append("ON DUPLICATE KEY												\n");
            sb.append("UPDATE M_REGDATE = NOW()										\n");
            
            System.out.println(sb.toString());
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            stmt.executeUpdate(sb.toString());

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
	
	/**
	 * 열어본 페이지 저장 
	 */
	public String deleteComfirm(String m_seq, String md_seqs) {

        String result = "";

        try {

        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
        	
            sb = new StringBuffer();
            sb.append("DELETE FROM MEMBER_COMFIRM WHERE M_SEQ = "+m_seq+" AND MD_SEQ IN( "+md_seqs+")\n");
            
            System.out.println(sb.toString());            
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("SELECT MD_SEQ FROM MEMBER_COMFIRM WHERE MD_SEQ IN ("+md_seqs+")\n");
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	if(result.equals("")){
            		result = rs.getString("MD_SEQ");
            	}else{
            		result += "," + rs.getString("MD_SEQ");
            	}
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
        return result;
    }
	
	
	/**
	* 대표 유사를 변경한다.
	*/
	public boolean alterList(String md_seq, String md_pseq)
	{
		
		boolean result = false;
		 try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
          
            sb = new StringBuffer();
            sb.append("UPDATE META SET MD_PSEQ = "+md_seq+", MD_SAME_COUNT = 0 WHERE MD_SEQ = "+md_seq+" \n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("UPDATE META SET MD_SAME_COUNT = MD_SAME_COUNT - 1 WHERE MD_PSEQ = "+md_pseq+" \n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            
            sb = new StringBuffer();
            sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_SEQ = "+md_seq+" \n");
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            int cnt = 0;
            if(rs.next()){
            	cnt = rs.getInt("CNT");
            }
            
            if(cnt > 0){
            	sb = new StringBuffer();
                sb.append("UPDATE ISSUE_DATA SET MD_PSEQ = "+md_seq+", MD_SAME_CT = 0 WHERE MD_SEQ = "+md_seq+" \n");
                System.out.println(sb.toString());
                stmt.executeUpdate(sb.toString());
                
                sb = new StringBuffer();
                sb.append("UPDATE ISSUE_DATA SET MD_SAME_CT = MD_SAME_CT - 1 WHERE MD_PSEQ = "+md_pseq+" \n");
                System.out.println(sb.toString());
                stmt.executeUpdate(sb.toString());
            }
            
            

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {            
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return result;
	}
	
	public String Alter_mdSeq_mdPseq(String md_seq)    
    {    	
    	String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append(" SELECT DISTINCT MD_PSEQ FROM META WHERE MD_SEQ IN ("+md_seq+") 	\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_PSEQ");	
				}else{
					result += "," +  rs.getString("MD_PSEQ");
				}
			}												
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return result;
	 }
	
	public ArrayList getMetaDataBySameData(String keyword, String today, String md_seq){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT M.MD_SEQ, M.MD_PSEQ, M.MD_SITE_NAME, M.MD_TITLE, M.MD_DATE\n");
			sb.append("FROM META M\n");
			sb.append("WHERE M.MD_DATE BETWEEN '"+today+" 00:00:00' AND '"+today+" 23:59:59'\n");
			if(!keyword.equals("")){
			sb.append("AND M.MD_TITLE LIKE '%"+keyword+"%'\n");
			}
			sb.append("AND M.MD_SEQ = M.MD_PSEQ\n");
			sb.append("AND M.MD_PSEQ <> (SELECT MD_PSEQ FROM META WHERE MD_SEQ = "+md_seq+")\n");
			sb.append("ORDER BY M.MD_DATE DESC\n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				MetaBean mb = new MetaBean();
				mb.setMd_seq(rs.getString("MD_SEQ"));
				mb.setMd_pseq(rs.getString("MD_PSEQ"));
				mb.setMd_site_name(rs.getString("MD_SITE_NAME"));
				mb.setMd_title(rs.getString("MD_TITLE"));
				mb.setMd_date(rs.getString("MD_DATE"));
				result.add(mb);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public String getSiteCode(String sg_seq){
		String result = "";
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			
			sb.append("SELECT CONCAT(IC_TYPE,',',IC_CODE) AS IC_CODE FROM ISSUE_CODE WHERE IC_SEQ =\n"); 
			sb.append("(SELECT IC_SEQ FROM IC_S_RELATION WHERE S_SEQ = "+sg_seq+")\n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString("IC_CODE");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public int[] getKeyConditionDayChartData(String date){
		int [] result = new int[16];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, K_YP, SUM(CNT)	AS CNT											\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("	  AND K_XP IN (23,24,25,26)													\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP, K_YP															\n");
			sb.append("	ORDER BY K_YP, K_XP															\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<16 ; i++){
				result[i] = 0;
			}
			
			while(rs.next()){
				if(rs.getInt("K_XP") == 23){
					if(rs.getInt("K_YP") == 1){
						result[0] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[1] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[2] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[3] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 24){
					if(rs.getInt("K_YP") == 1){
						result[4] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[5] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[6] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[7] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 25){
					if(rs.getInt("K_YP") == 1){
						result[8] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[9] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[10] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[11] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 26){
					if(rs.getInt("K_YP") == 1){
						result[12] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[13] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[14] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[15] = rs.getInt("CNT");
					}
				}
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}
	
	public int[] getKeyConditionDayChartData(String sdate, String edate, String time){
		int [] result = new int[16];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, K_YP, SUM(CNT)	AS CNT											\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'			\n");
			sb.append("	  AND K_XP IN (23,24,25,26)													\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP, K_YP															\n");
			sb.append("	ORDER BY K_YP, K_XP															\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<16 ; i++){
				result[i] = 0;
			}
			
			while(rs.next()){
				if(rs.getInt("K_XP") == 23){
					if(rs.getInt("K_YP") == 1){
						result[0] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[1] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[2] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[3] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 24){
					if(rs.getInt("K_YP") == 1){
						result[4] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[5] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[6] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[7] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 25){
					if(rs.getInt("K_YP") == 1){
						result[8] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[9] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[10] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[11] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 26){
					if(rs.getInt("K_YP") == 1){
						result[12] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[13] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[14] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[15] = rs.getInt("CNT");
					}
				}
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}
	
	public int[] getKeyConditionChartData(String sDate, String eDate){
		int [] result = new int[16];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, K_YP, SUM(CNT)	AS CNT											\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'			\n");
			sb.append("	  AND K_XP IN (23,24,25,26)													\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP, K_YP															\n");
			sb.append("	ORDER BY K_XP, K_YP															\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<16 ; i++){
				result[i] = 0;
			}
			
			while(rs.next()){
				if(rs.getInt("K_XP") == 23){
					if(rs.getInt("K_YP") == 1){
						result[0] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[1] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[2] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[3] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 24){
					if(rs.getInt("K_YP") == 1){
						result[4] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[5] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[6] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[7] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 25){
					if(rs.getInt("K_YP") == 1){
						result[8] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[9] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[10] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[11] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_XP") == 26){
					if(rs.getInt("K_YP") == 1){
						result[12] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 2){
						result[13] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 3){
						result[14] = rs.getInt("CNT");
					}else if(rs.getInt("K_YP") == 4){
						result[15] = rs.getInt("CNT");
					}
				}
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}
	
	public int[] getTodayConditionChartData_IT(String date){
		int [] result = new int[20];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, K_YP, SUM(CNT)	AS CNT											\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("	  AND K_XP IN (34,35,36,37,38)												\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP, K_YP															\n");
			sb.append("	ORDER BY K_XP, K_YP															\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<result.length ; i++){
				result[i] = 0;
			}
			
			while(rs.next()){
				if(rs.getInt("K_YP") == 1){
					if(rs.getInt("K_XP") == 34){
						result[0] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[1] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[2] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[3] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[4] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 2){
					if(rs.getInt("K_XP") == 34){
						result[5] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[6] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[7] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[8] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[9] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 3){
					if(rs.getInt("K_XP") == 34){
						result[10] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[11] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[12] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[13] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[14] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 4){
					if(rs.getInt("K_XP") == 34){
						result[15] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[16] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[17] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[18] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[19] = rs.getInt("CNT");
					}
				}		
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}
	
	public int[] getTodayConditionChartData_IT(String sdate, String edate, String time){
		int [] result = new int[20];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, K_YP, SUM(CNT)	AS CNT											\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'			\n");
			sb.append("	  AND K_XP IN (34,35,36,37,38)												\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP, K_YP															\n");
			sb.append("	ORDER BY K_XP, K_YP															\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<result.length ; i++){
				result[i] = 0;
			}
			
			while(rs.next()){
				if(rs.getInt("K_YP") == 1){
					if(rs.getInt("K_XP") == 34){
						result[0] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[1] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[2] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[3] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[4] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 2){
					if(rs.getInt("K_XP") == 34){
						result[5] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[6] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[7] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[8] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[9] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 3){
					if(rs.getInt("K_XP") == 34){
						result[10] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[11] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[12] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[13] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[14] = rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 4){
					if(rs.getInt("K_XP") == 34){
						result[15] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 35){
						result[16] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 36){
						result[17] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 37){
						result[18] = rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 38){
						result[19] = rs.getInt("CNT");
					}
				}		
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}
	
	public int[] getTodayConditionChartData_xEV_ESS(String date){
		int [] result = new int[20];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, K_YP, SUM(CNT)	AS CNT											\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("	  AND K_XP IN (40,41,42,43,44,46,47,48,49,50)												\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP, K_YP															\n");
			sb.append("	ORDER BY K_XP, K_YP															\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<result.length ; i++){
				result[i] = 0;
			}
			
			while(rs.next()){
				if(rs.getInt("K_YP") == 1){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[0] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[1] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[2] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[3] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[4] += rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 2){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[5] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[6] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[7] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[8] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[9] += rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 3){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[10] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[11] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[12] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[13] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[14] += rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 4){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[15] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[16] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[17] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[18] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[19] += rs.getInt("CNT");
					}
				}
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}
	
	public int[] getTodayConditionChartData_xEV_ESS(String sdate, String edate, String time){
		int [] result = new int[20];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, K_YP, SUM(CNT)	AS CNT											\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'			\n");
			sb.append("	  AND K_XP IN (40,41,42,43,44,46,47,48,49,50)												\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP, K_YP															\n");
			sb.append("	ORDER BY K_XP, K_YP															\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<result.length ; i++){
				result[i] = 0;
			}
			
			while(rs.next()){
				if(rs.getInt("K_YP") == 1){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[0] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[1] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[2] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[3] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[4] += rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 2){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[5] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[6] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[7] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[8] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[9] += rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 3){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[10] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[11] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[12] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[13] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[14] += rs.getInt("CNT");
					}
				}else if(rs.getInt("K_YP") == 4){
					if(rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
						result[15] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
						result[16] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
						result[17] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
						result[18] += rs.getInt("CNT");
					}else if(rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
						result[19] += rs.getInt("CNT");
					}
				}
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}
	
	public int[] getIssueConditionChartData(String sdate, String edate, String mode){
		int [] result = new int[5];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		// #(IT) 34,35,36,37,38   #(xEV) 40,41,42,43,44    #(ESS) 46,47,48,49,50
		String xps = "";
		if(mode.equals("IT")){
			xps = "34,35,36,37,38";
		}else if(mode.equals("xEV")){
			xps = "40,41,42,43,44";
		}else if(mode.equals("ESS")){
			xps = "46,47,48,49,50";
		}
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("	SELECT K_XP, SUM(CNT) AS CNT												\n");
			sb.append("	FROM STATIC_ANALYSIS														\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+sdate+" 00:00:00' AND '"+edate+" 23:59:59'			\n");
			sb.append("	  AND K_XP IN ("+xps+")														\n");
			sb.append("	  AND K_YP <> 0																\n");
			sb.append("	  AND K_ZP <> 0																\n");
			sb.append("	GROUP BY K_XP																\n");
			sb.append("	ORDER BY K_XP																\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			for(int i=0 ; i<result.length ; i++){
				result[i] = 0;
			}
			
			int i=0;
			while(rs.next()){
				result[i] = rs.getInt("CNT");
				i++;
			}

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result; 
	}	

    public int getTodayConditionDataCount(String md_date, String k_xp, String k_yp)
    {
    	int result = 0;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();  

            sb = new StringBuffer();
            sb.append("	SELECT COUNT(*) AS CNT FROM (												\n");
            sb.append("		SELECT M.MD_SEQ																\n");
            sb.append("		FROM META M, IDX I															\n");
            sb.append("		WHERE M.MD_SEQ = I.MD_SEQ													\n");
            sb.append("		  AND M.MD_DATE BETWEEN '"+md_date+" 00:00:00' AND '"+md_date+" 23:59:59'	\n");
            sb.append("		  AND I.K_XP IN ("+k_xp+")													\n");
            sb.append("		  AND I.K_YP = "+k_yp+"  													\n");
            sb.append("		  AND I.K_ZP <> 0															\n");
            sb.append("	  	GROUP BY M.MD_SEQ														\n");
            sb.append("	  	)A																		\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	result = rs.getInt("CNT");
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    } 
    
    public int getTodayConditionDataCount(String sdate, String edate, String time, String k_xp, String k_yp)
    {
    	int result = 0;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();  

            sb = new StringBuffer();
            sb.append("	SELECT COUNT(*) AS CNT FROM (												\n");
            sb.append("		SELECT M.MD_SEQ																\n");
            sb.append("		FROM META M, IDX I															\n");
            sb.append("		WHERE M.MD_SEQ = I.MD_SEQ													\n");
            sb.append("		  AND M.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'	\n");
            sb.append("		  AND I.K_XP IN ("+k_xp+")													\n");
            sb.append("		  AND I.K_YP = "+k_yp+"  													\n");
            sb.append("		  AND I.K_ZP <> 0															\n");
            sb.append("	  	GROUP BY M.MD_SEQ														\n");
            sb.append("	  	)A																		\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            if( rs.next() ) {
            	result = rs.getInt("CNT");
            } // end while                        

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return result;
    } 
    
	
    public ArrayList getTodayConditionDataList(String md_date, String k_xp, String k_yp, int nowPage, int PageCount)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.*, I.K_XP, I.K_YP													\n");
            sb.append("	FROM META M, IDX I															\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ													\n");
            sb.append("	  AND M.MD_DATE BETWEEN '"+md_date+" 00:00:00' AND '"+md_date+" 23:59:59'	\n");
            sb.append("	  AND I.K_XP IN ("+k_xp+")													\n");
            sb.append("	  AND I.K_YP = "+k_yp+"  													\n");
            sb.append("	  AND I.K_ZP <> 0															\n");
            sb.append("	GROUP BY M.MD_SEQ															\n");
            sb.append("	ORDER BY M.MD_DATE DESC														\n");
            sb.append("	LIMIT "+((nowPage-1)*PageCount)+", "+PageCount+"							\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    public ArrayList getTodayConditionDataList(String sdate, String edate, String time, String k_xp, String k_yp, int nowPage, int PageCount)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.*, I.K_XP, I.K_YP													\n");
            sb.append("	FROM META M, IDX I															\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ													\n");
            sb.append("	  AND M.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'	\n");
            sb.append("	  AND I.K_XP IN ("+k_xp+")													\n");
            sb.append("	  AND I.K_YP = "+k_yp+"  													\n");
            sb.append("	  AND I.K_ZP <> 0															\n");
            sb.append("	GROUP BY M.MD_SEQ															\n");
            sb.append("	ORDER BY M.MD_DATE DESC														\n");
            sb.append("	LIMIT "+((nowPage-1)*PageCount)+", "+PageCount+"							\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setS_seq(rs.getString("S_SEQ"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
	        	 mBean.setMd_type(rs.getString("MD_TYPE"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_pseq(rs.getString("MD_PSEQ"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_img(rs.getString("MD_IMG"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_same_count(rs.getString("MD_SAME_COUNT"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    public String[][] getKeyConditionChartDataCompliance(String sDateTime, String eDateTime, int k_xp) {
    	String [][] result = null;
    	
    	try {dbconn = new DBconn();
		dbconn.getDBCPConnection();
		stmt = dbconn.createStatement();
		
		sb = new StringBuffer();
		sb.append("SELECT T.K_XP, T.K_YP, T.K_ZP, K.K_VALUE, T.CNT								\n");                 
		sb.append("FROM																			\n");                 
		sb.append("(SELECT * FROM KEYWORD WHERE K_YP != 0 AND K_ZP = 0) K						\n");                                 
		sb.append("		INNER JOIN(																\n");         
		sb.append("  		SELECT K_XP, K_YP, K_ZP, SUM(CNT) AS CNT 							\n");         
		sb.append("  		FROM STATIC_ANALYSIS 												\n");         
		sb.append("  		WHERE MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'	\n");         
		sb.append("  		AND K_XP = "+k_xp+"														\n"); 
		sb.append("			AND K_YP <> 0														\n");                 
		sb.append("			AND K_ZP <> 0														\n"); 
		sb.append("			AND SG_SEQ IN (3,24,17,18,19,25,20)								\n");
		sb.append("			GROUP BY K_YP														\n");                                 
		sb.append("			ORDER BY CNT DESC													\n");                         
		sb.append("			LIMIT 5																\n");         
		sb.append("			) T																	\n");         
		sb.append("		ON K.K_XP = T.K_XP AND K.K_YP = T.K_YP									\n");         
		sb.append("		ORDER BY CNT DESC														\n");
		        
        System.out.println(sb.toString());				
		rs = stmt.executeQuery(sb.toString());
		
		int i = 0;
		while(rs.next()){
			i++;
		}
		
		result = new String[i][4];
		i=0;
		rs.beforeFirst();
		
		while(rs.next()){
			result[i][0] = rs.getString("k_yp");
			result[i][1] = rs.getString("k_value");
			result[i][2] = rs.getString("CNT");
			result[i][3] = rs.getString("k_xp");
			i++;
		}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
    	
		return result;
	}
    
    public ArrayList getKeywordDataListCompliance(String sDateTime, String eDateTime, int k_xp, int k_yp, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	MetaBean mBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append(" SELECT A.MD_SEQ, A.MD_DATE, A.MD_TITLE, A.MD_URL, A.MD_SITE_NAME			\n");
			sb.append(" FROM META A, IDX B															\n");
			sb.append(" WHERE MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'		\n");
			sb.append(" AND B.K_XP="+k_xp+"																\n");
			sb.append(" AND B.K_YP="+k_yp+"																\n");
			sb.append(" AND B.K_ZP<>0																\n");
			sb.append("	AND A.MD_SEQ = B.MD_SEQ														\n");
			sb.append("	ORDER BY A.MD_SEQ DESC														\n");
	        sb.append(" LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"								\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				mBean = new MetaBean();
				mBean.setMd_seq(rs.getString("MD_SEQ"));
				mBean.setMd_date(rs.getString("MD_DATE"));
				mBean.setMd_title(rs.getString("MD_TITLE"));
				mBean.setMd_url(rs.getString("MD_URL"));
				mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(mBean);
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    	
	    return result;
	 }
    
    public int getKeywordDataCount(String sDateTime, String eDateTime, int k_xp, int k_yp)    
    {    	
    	int result = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append(" SELECT COUNT(*)	AS CNT		\n");
			sb.append(" FROM META A, IDX B															\n");
			sb.append(" WHERE MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'		\n");
			sb.append(" AND B.K_XP="+k_xp+"																\n");
			sb.append(" AND B.K_YP="+k_yp+"																\n");
			sb.append(" AND B.K_ZP<>0																\n");
			sb.append("	AND A.MD_SEQ = B.MD_SEQ														\n");	

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				result = rs.getInt("cnt");
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    	
	    return result;
	 }
    
    public String getYPName(int k_xp, int k_yp){
    	String result = null;
    	
    	try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append(" SELECT K_VALUE 				\n"); 
			sb.append("	FROM KEYWORD				\n");
			sb.append(" WHERE K_XP = "+k_xp+"		\n");				
			sb.append(" AND K_YP = "+k_yp+"			\n");
			sb.append(" AND K_ZP = 0				\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				result = rs.getString("K_VALUE");
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    	
	    return result;
    	
    }
    
    /**
     * @param sDate
     * @param eDate
     * @return ArrayList<MemberBean>
     * @comment : 컴플라이언스 키워드 수집 리스트, k_xp 세팅
     */
    public ArrayList getKeywordCollect_compliance(String sDateTime, String eDateTime){
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("	SELECT M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL, M.MD_SITE_NAME, I.ISSUE_CHECK 	\n");
            sb.append("	FROM META M, IDX I																	\n");
            sb.append("	WHERE M.MD_SEQ = I.MD_SEQ															\n");
            sb.append("	AND I.K_XP BETWEEN 58 AND 62														\n");//58~62
            sb.append(" AND M.MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'							\n");//
            sb.append("	GROUP BY M.MD_SEQ, M.MD_DATE, M.MD_TITLE, M.MD_URL, M.MD_SITE_NAME 					\n");
            sb.append("	ORDER BY M.MD_SEQ DESC																\n");
            sb.append("	LIMIT 100												\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
	        	 mBean  = new MetaBean();
	        	 mBean.setMd_seq(rs.getString("MD_SEQ"));
	        	 mBean.setMd_date(rs.getString("MD_DATE"));
	        	 mBean.setMd_title(rs.getString("MD_TITLE"));
	        	 mBean.setMd_url(rs.getString("MD_URL"));
	        	 mBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
	        	 mBean.setIssue_check(rs.getString("ISSUE_CHECK"));
	        	 arrlist.add(mBean);
            } // end while                        

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
    
    public int getPackGubun(String md_seqs)    
    {    	
    	int result = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append(" SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seqs+") 	\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = rs.getInt("CNT");
			}												
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return result;
	 }
	
    
    public String getSamePseq(String md_seqs)    
    {    	
    	String result = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			
			sb.append("SELECT MD_PSEQ FROM META WHERE MD_SEQ IN ("+md_seqs+") ORDER BY MD_PSEQ ASC\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
	
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_PSEQ");
				}else{
					result += "," + rs.getString("MD_PSEQ");
				}
			}												
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    return result;
	 }
	
	public void UpdateSamePseq(String md_pseqs)    
    {    	
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append("UPDATE META SET MD_PSEQ = "+md_pseqs.split(",")[0]+" WHERE MD_PSEQ IN ("+md_pseqs+")\n");
			
			System.out.println(sb.toString());				
			int cnt = stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			sb.append("UPDATE META SET MD_SAME_COUNT = "+String.valueOf(cnt-1)+" WHERE MD_PSEQ = "+md_pseqs.split(",")[0]+"\n");
			System.out.println(sb.toString());				
			stmt.executeUpdate(sb.toString());
	
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	 }
	
	public void UpdateSameIssuePseq(String md_pseqs)    
    {    	
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append("UPDATE ISSUE_DATA SET MD_PSEQ = "+md_pseqs.split(",")[0]+" WHERE MD_PSEQ IN ("+md_pseqs+")\n");
			
			System.out.println(sb.toString());				
			stmt.executeUpdate(sb.toString());
	
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	 }
	
	public void DeleteSamePseq(String md_pseqs)    
    {    	
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			String[] arTemp = md_pseqs.split(",");
			String new_md_pseqs = "";
			for(int i = 1; i < arTemp.length; i++){
				if(new_md_pseqs.equals("")){
					new_md_pseqs = arTemp[i];
				}else{
					new_md_pseqs += "," + arTemp[i];
				}
			}
			
			sb = new StringBuffer();
			sb.append("DELETE FROM SAME_FILTER WHERE MD_SEQ IN ("+new_md_pseqs+")\n");
			
			System.out.println(sb.toString());				
			stmt.executeUpdate(sb.toString());
	
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	 }
	
	
	public String getIssueSeq(String md_seqs, String type)    
    {    	
		String result = "";
		try {
			
			String not = "";
			if(type.equals("YES")){
				not = "";
			}else if(type.equals("NO")){
				not = "NOT";
			}
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			
			sb.append("SELECT MD_SEQ 																		\n");
			sb.append("  FROM META 																			\n");
			sb.append(" WHERE MD_SEQ IN ("+md_seqs+")														\n");
			sb.append("   AND MD_SEQ "+not+" IN (SELECT MD_SEQ FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seqs+"))	\n");
			
		
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
	
			while(rs.next()){
				if(result.equals("")){
					result = rs.getString("MD_SEQ");
				}else{
					result += "," + rs.getString("MD_SEQ");
				}
			}	
	
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
		return result;
	 }
	
	public String setUpdateIssueCheck(String t_seq, String yn){
		String  result = "";
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("UPDATE TOP_IDX SET ISSUE_CHECK = '"+yn+"' WHERE T_SEQ ="+t_seq+" \n");
			System.out.println(sb.toString());				
			if(stmt.executeUpdate(sb.toString())>0){
				result = t_seq;
			} 	
	
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
		return result;
	}
	public JSONArray getSb_seq_MapList(){
		JSONArray result = new JSONArray();
		try {
						
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			
			sb.append(" SELECT SB_SEQ, TPYE5, TYPE51, TYPE3,TYPE31 FROM SB_SEQ_MAP \n");
		
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
	
			JSONObject obj = null;
			while(rs.next()){
				obj = new JSONObject();
				obj.put("sb_seq",rs.getString("SB_SEQ"));
				obj.put("tpye5",rs.getString("TPYE5"));
				obj.put("tpye5",rs.getString("TPYE51"));
				obj.put("type3",rs.getString("TPYE3"));
				obj.put("type31",rs.getString("TPYE31"));
				result.put(obj);
			}	
	
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		
		return result;
	}
	
}
