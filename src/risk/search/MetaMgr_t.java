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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.xalan.templates.WhiteSpaceInfo;

import risk.DBconn.DBconn;
import risk.admin.bookmark.BookMarkMgr;
import risk.admin.keyword.KeywordBean;
import risk.util.DateUtil;
import risk.util.Log;

public class MetaMgr_t {

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


    public String msMinNo   = "";   //검색 시작 MD_SEQ
    public String msMaxNo   = "";   //검색 마지막 MD_SEQ
    public String msKeyTitle= "";   //검색 그룹 한글명


    public int mBeanTotalPageCount = 0;

    public MetaMgr_t() {

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

    public void getMaxMinNo( String psSDate, String psEdate, String psSTime, String psETime ) {
    	
    	DateUtil du = new DateUtil();
    	
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
            sb.append(" SELECT (SELECT MD_SEQ FROM META_TMP WHERE MD_DATE BETWEEN '"+psSDate+" "+psSTime+":00:00' AND '"+psSDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM META_TMP WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" "+psETime+":59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
                       

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
    
    public MetaBean getMetaData(String md_seq)
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
            sb.append("       D.MD_CONTENT    ,                                     \n");
            sb.append("       M.L_ALPHA                                           \n");
            sb.append("  FROM META_TMP M, DATA_TMP D,(SELECT  MD_SEQ, SG_SEQ FROM IDX_TMP WHERE MD_SEQ = "+md_seq+"  GROUP BY MD_SEQ) I \n");
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
                    sb.append("SELECT K_XP, K_VALUE FROM KEYWORD_TMP WHERE K_YP =0 AND K_ZP =0 AND K_USEYN = 'Y' \n");
                }else{
                	sb = new StringBuffer();
                    sb.append("SELECT DISTINCT A.K_XP																									\n");
                    sb.append("     , (SELECT K_VALUE FROM KEYWORD_TMP WHERE K_XP = A.K_XP AND K_YP =0 AND K_ZP =0 AND K_USEYN = 'Y') AS K_VALUE	\n");
                    sb.append("  FROM IDX_TMP A 																									\n");
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
                    sb.append("SELECT K_YP, K_VALUE FROM KEYWORD_TMP WHERE K_XP = "+k_xp+" AND K_ZP = 0 AND K_USEYN = 'Y' \n");
                }else{
    	            sb = new StringBuffer();
    	            sb.append("SELECT DISTINCT A.K_YP																								\n");
    	            sb.append("     , (SELECT K_VALUE FROM KEYWORD_TMP WHERE K_XP = "+k_xp+" AND K_YP =A.K_YP AND K_ZP =0 AND K_USEYN = 'Y') AS K_VALUE	\n");
    	            sb.append("  FROM IDX_TMP A 																										\n");
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
            sb.append("       M.L_ALPHA                                           \n");
            sb.append("  FROM META_TMP M, DATA_TMP D,(SELECT  MD_SEQ, SG_SEQ FROM IDX_TMP WHERE MD_SEQ IN ("+md_seq+")  GROUP BY MD_SEQ) I \n");
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
            sb.append("       (SELECT DISTINCT ISSUE_CHECK FROM IDX_TMP WHERE MD_SEQ =M.MD_SEQ) AS ISSUE_CHECK,                \n");
            sb.append("       D.MD_CONTENT                                        \n");
            sb.append("FROM META_TMP M, DATA_TMP D	                             \n");
            sb.append("WHERE M.MD_SEQ = D.MD_SEQ          \n");           
            sb.append("   AND M.MD_PSEQ = " + md_pseq + "                          \n");
            sb.append(" ORDER BY M.MD_DATE ASC                                    										\n");
            */
            
            sb.append("SELECT A.* 																						\n");
            sb.append("     , (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = A.SG_SEQ) AS SG_NAME						\n");
            sb.append("  FROM (																							\n");
            sb.append("        SELECT A.* 																				\n");
            sb.append("             , FN_GET_KEYWORD(A.MD_SEQ) AS K_VALUE												\n");
            sb.append("             , (SELECT DISTINCT ISSUE_CHECK FROM IDX_TMP WHERE MD_SEQ =A.MD_SEQ) AS ISSUE_CHECK		\n");
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
            sb.append("                  FROM META_TMP M, DATA_TMP D	                             							\n");
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
             sb.append("  FROM META_TMP M, DATA_TMP D                                        \n");          
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
                sb.append("  FROM META_TMP                                  \n");
                sb.append(" WHERE MD_SEQ = MD_PSEQ                        \n");
                sb.append("   AND MD_SEQ BETWEEN " + MinMtNo     +   "   \n");
                sb.append("                 AND " + MaxMtNo     +   "   \n");



            } else {
                sb.append("SELECT /*+ORDERED*/                              \n");
                sb.append("       COUNT(1) AS TOTAL_CNT                     \n");
                sb.append("  FROM META_TMP B, DATA_TMP A                                      \n");
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
                sb.append("          FROM KEYWORD_TMP                                           \n");
                sb.append("         WHERE K_XP = " + psXp + "                               \n");
                sb.append("           AND K_YP = 0                                          \n");
                sb.append("           AND K_ZP = 0                                          \n");
                sb.append("           AND K_TYPE < 3                                        \n");


                if ( !psYp.equals("") ) {
                    sb.append("        UNION                                                    \n");
                    sb.append("        SELECT '' , K_VALUE MAIN , ''                            \n");
                    sb.append("          FROM KEYWORD_TMP                                           \n");
                    sb.append("         WHERE K_XP = " + psXp + "                               \n");
                    sb.append("           AND K_YP = " + psYp + "                               \n");
                    sb.append("           AND K_ZP = 0                                          \n");
                    sb.append("           AND K_TYPE < 3                                        \n");
                }

                if ( !psZp.equals("") ) {
                    sb.append("        UNION                                                    \n");
                    sb.append("        SELECT '' , '' , K_VALUE MAIN                            \n");
                    sb.append("          FROM KEYWORD_TMP                                           \n");
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
	           sb.append("                  FROM META_TMP B, DATA_TMP D                                              \n");
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
						                 "23"
								   );
    }	
    

	/**
	* 키워드 그룹별 검색(정보 검색용 :북마크 추가)
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
	                                       String psMname   ,
	                                       String bookMarkYn,
	                                       String language,
	                                       String psMseq,
	                                       String stime,
	                                       String etime
									   )
	
	
	{	
		ArrayList arrlist = new ArrayList();
	
	    try {
	
	    	getMaxMinNo( psDateFrom, psDateTo, stime, etime );
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
            sb.append("                           FROM IDX_TMP A, META_TMP B		                   \n");
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
	        
	        //총개시물 건수를 구한다.
	        sb = new StringBuffer();
	        sb.append("  SELECT                                     \n");
	        sb.append("         COUNT(1) AS TOTAL_CNT                              \n");
	        sb.append("  FROM   (                              \n");	       
	        sb.append("                           SELECT				                               \n");
            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ                     \n");		   
            sb.append("                           FROM IDX_TMP A, META_TMP B		                   \n");
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
           /* if(psMode.equals("DELIDX")){
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
	            sb.append("          FROM KEYWORD_TMP                                           \n");
	            sb.append("         WHERE K_XP in ( " + psXp + " )                               \n");
	            sb.append("           AND K_YP = 0                                          \n");
	            sb.append("           AND K_ZP = 0                                          \n");
	            sb.append("           AND K_TYPE < 3                                        \n");
	            sb.append("  		  AND K_USEYN='Y'                                              \n");
	            if ( !psYp.equals("") ) {
	                sb.append("        UNION                                                    \n");
	                sb.append("        SELECT '' , K_VALUE MAIN , ''                            \n");
	                sb.append("          FROM KEYWORD_TMP                                           \n");
	                sb.append("         WHERE K_XP = " + psXp + "                               \n");
	                sb.append("           AND K_YP = " + psYp + "                               \n");
	                sb.append("           AND K_ZP = 0                                          \n");
	                sb.append("           AND K_TYPE < 3                                        \n");
	                sb.append("  		  AND K_USEYN='Y'                                              \n");
	            }
	
	            if ( !psZp.equals("") ) {
	                sb.append("        UNION                                                    \n");
	                sb.append("        SELECT '' , '' , K_VALUE MAIN                            \n");
	                sb.append("          FROM KEYWORD_TMP                                           \n");
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
	            if(psXp.length()>0){
	            	if(psXp.split(",").length>1)
	            	{
	            		keywordFunction = "FN_GET_KEYWORD_TMP(A.MD_SEQ)";
	            	}else{
	            		keywordFunction = "FN_GET_KEYWORD2_TMP(A.MD_SEQ,"+psXp+")";
	            	}
	            }
	            if(psYp.length()>0) keywordFunction = "FN_GET_KEYWORD3_TMP(A.MD_SEQ,"+psXp+","+psYp+")";
	            if(psZp.length()>0) keywordFunction = "FN_GET_KEYWORD4_TMP(A.MD_SEQ,"+psXp+","+psYp+","+psZp+")";
	           
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
		        	sb.append("  FROM KEYWORD_TMP                                                 \n");
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
		        	sb.append("  FROM KEYWORD_TMP                                                 \n");
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
	         		
	         		sb.append("  , '' AS T_FOLLOWERS  												\n");
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
		            sb.append("                   FROM (                                                    \n");
		            sb.append("                           SELECT				                               \n");
		            sb.append("                                MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE ,B.MD_DATE ,B.MD_IMG  \n");
		            sb.append("                                ,B.MD_SAME_COUNT ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, ISSUE_CHECK, I_DELDATE, M_SEQ    \n");		            
		            sb.append("                           FROM IDX_TMP A, META_TMP B		                   \n");
		            sb.append("                           WHERE A.MD_SEQ BETWEEN "+MinMtNo+" AND "+MaxMtNo+"                            \n");
		            sb.append("                                 AND A.MD_SEQ = B.MD_SEQ                            \n");
		            if ( !psKeyWord.equals("") ){
		            	for(int i = 0; i < arrKeyWord.length; i++){
							sb.append("            AND B.MD_TITLE LIKE '%" + arrKeyWord[i] + "%'        \n");
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
		            sb.append("                        DATA_TMP B                                       \n");		           		    	       
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
		String IdxDelCNT ="0";
		
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();

            //게시물 리스트를 구한다.

            sb = new StringBuffer();

            sb.append("SELECT COUNT(DISTINCT(MD_SEQ)) AS TOTAL_CNT FROM IDX_TMP WHERE I_STATUS = 'T' "); // AND M_SEQ="+m_seq+" \n");
                   
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
	public boolean idxProcess(String mode, String key, String m_seq)
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
            //sb.append("SELECT MD_SEQ FROM META_TMP WHERE MD_PSEQ = (SELECT MD_PSEQ FROM META_TMP WHERE MD_SEQ = "+key+")");
            
            
            if(key.length()>0){
	            sb.append(" SELECT M1.MD_SEQ               \n");
	            sb.append("   FROM META_TMP M1, META_TMP M2        \n"); 
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
            	sb.append("UPDATE IDX_TMP SET I_STATUS='T' , M_SEQ="+m_seq+", I_DELDATE='"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' WHERE MD_SEQ IN ("+Tempno+")");
            }else if(mode.equals("revert")){
            	sb.append("UPDATE IDX_TMP SET I_STATUS='N' , M_SEQ = 0 WHERE MD_SEQ IN ("+Tempno+")");
            }else if(mode.equals("del")){
            	sb.append("DELETE FROM IDX_TMP WHERE I_STATUS='T' AND M_SEQ = "+m_seq);
            }else if(mode.equals("delAll")){
            	sb.append("DELETE FROM IDX_TMP WHERE I_STATUS='T' ");
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
            sb.append("  FROM KEYWORD_TMP                    \n");
            sb.append(" WHERE K_TYPE < 11  \n");
            if ( psXp.length()>0 )
            	sb.append(" AND K_XP IN ("+psXp+")                   \n");
            if ( psYp.length()>0 )
            	sb.append(" AND K_YP IN ("+psYp+")                   \n");
            
            if ( psZp.length()>0 )
            	sb.append(" AND K_ZP IN ("+psZp+")                   \n");
            else
            	sb.append(" AND K_ZP = 0                   \n");
            
            sb.append(" ORDER BY K_OP, K_XP, K_YP, K_ZP, K_VALUE                   \n");
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
            sb.append("UPDATE META_TMP SET MD_PSEQ = "+md_seq+", MD_SAME_COUNT = 0 WHERE MD_SEQ = "+md_seq+" \n");
            System.out.println(sb.toString());
            stmt.executeUpdate(sb.toString());
            
            sb = new StringBuffer();
            sb.append("UPDATE META_TMP SET MD_SAME_COUNT = MD_SAME_COUNT - 1 WHERE MD_PSEQ = "+md_pseq+" \n");
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
                sb.append("UPDATE ISSUE_DATA SET MD_PSEQ = "+md_seq+", MD_SAME_COUNT = 0 WHERE MD_SEQ = "+md_seq+" \n");
                System.out.println(sb.toString());
                stmt.executeUpdate(sb.toString());
                
                sb = new StringBuffer();
                sb.append("UPDATE ISSUE_DATA SET MD_SAME_COUNT = MD_SAME_COUNT - 1 WHERE MD_PSEQ = "+md_pseq+" \n");
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
			sb.append(" SELECT DISTINCT MD_PSEQ FROM META_TMP WHERE MD_SEQ IN ("+md_seq+") 	\n");
			
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
	
}
