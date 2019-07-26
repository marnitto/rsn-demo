package risk.statistics;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;

import risk.DBconn.DBconn;
import risk.issue.IssueDataBean;
import risk.json.JSONArray;
import risk.json.JSONException;
import risk.json.JSONObject;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class StatisticsMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private ResultSet rs2    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
    
    private StatisticsBean statisticsBean = null;
 	
    
    /**
     * 키워드 그룹별 차트 데이터
     */
	public HashMap getKeywordChartData(String sdate,String stime, String edate,String etime)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		int HashKey = 0;
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
				
		String first = "";
		String last = "";
		
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
			
			first = du.getDate(sdate,"yyyyMMdd");
			last = du.getDate(edate,"yyyyMMdd");
			
			diffDay = -du.DateDiff("yyyyMMdd",first,last);
			intDiffDay = Integer.parseInt(String.valueOf(diffDay));													
	    	
	    	sb = new StringBuffer();
	    	sb.append(" SELECT DATE_FORMAT(KA_REGDATE,'%Y%m%d') KA_REGDATE                                                              \n");
	    	sb.append("        ,K_VALUE                                                                                                                                        \n");
	    	sb.append("        ,K_CNT                                                                                                                                           \n");
	    	sb.append(" FROM KEYWORD_ANALYSIS WHERE KA_REGDATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'  \n");
	    	sb.append("      AND K_YP = 0                                                                                                                                     \n");
	    	sb.append(" ORDER BY KA_REGDATE,K_XP, K_YP, K_ZP                                                                                                                   \n");
																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
			if(intDiffDay>0)
			{
				date  = new String[intDiffDay+1];
				for(int i = 0; i<intDiffDay+1; i++)
				{
					date[i] = du.addDay(first, i , "yyyyMMdd");
					tempHash = new HashMap();
					tempHash.put("1", date[i]);
					
									
					HashKey = 1;
					rs.beforeFirst();
					while(rs.next())
					{						
						HashKey++;
						tempHash.put(String.valueOf(HashKey), "0");
						HashKey++;
						tempHash.put(String.valueOf(HashKey),  rs.getString("K_VALUE"));
					}
					typeAChart.add(tempHash);
				}
			}		
				    	 			    			    		
    		for(int i =0;i<typeAChart.size();i++)
    		{
    			
    			dataA = new HashMap();
    			dataA =(HashMap)typeAChart.get(i);
    			  			
    			HashKey = 1;
				rs.beforeFirst();
    			while(rs.next()){    				
	    			if((rs.getString("KA_REGDATE")).equals((String)dataA.get("1")))
	    			{	    				
	    				HashKey++;
	    				dataA.put(String.valueOf(HashKey), rs.getString("K_CNT"));
	    				HashKey++;
	    				dataA.put(String.valueOf(HashKey), rs.getString("K_VALUE"));	    			   					    				   				
	    			}
    			}
    			
    			typeAChart.set(i, dataA);	    			
    		}	    		
	    		    	
	    	ChartDataHm.put("A",typeAChart);			
		    	    		    	
	    	
	    } catch(SQLException ex) {
	    	Log.writeExpt(ex,ex.getMessage());
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	    	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	    }   	
		return ChartDataHm;
	}
	
	/**
     * 키워드별 정보 건수 TOP 10
     */
	public ArrayList getKeywordData(String sDate, String eDate)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" SELECT                                                                                                                                                 \n");
			sb.append("        K_XP                                                                                                                                        \n");
			sb.append("        ,K_YP                                                                                                                                        \n");
			sb.append("        ,K_ZP                                                                                                                                        \n");
			sb.append("        ,K_VALUE                                                                                                                                           \n");
			sb.append("        ,SUM(K_CNT) K_CNT                                                                                                                                      \n");
			sb.append(" FROM KEYWORD_ANALYSIS  \n");			
			sb.append(" WHERE KA_REGDATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'  \n");			
			sb.append(" 	  AND K_YP > 0 AND K_ZP >0  \n");			
			sb.append(" GROUP BY K_XP, K_YP, K_ZP                                                                                                                              \n");
			sb.append(" ORDER BY K_CNT DESC                                                                                                                   \n");
			sb.append(" LIMIT 10                                                                                                                   \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
        		statisticsBean.setK_xp(rs.getString("K_XP"));
        		statisticsBean.setK_yp(rs.getString("K_YP"));
        		statisticsBean.setK_zp(rs.getString("K_ZP"));
        		statisticsBean.setK_value(rs.getString("K_VALUE"));
        		statisticsBean.setK_cnt(rs.getString("K_CNT")); 
        		result.add(statisticsBean);	
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
	
	
	public ArrayList getKeywordData(String sDate, String eDate, String k_xp, String k_yp, String k_zp)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+sDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO 	\n");
			sb.append("     , (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO	\n"); 
			
			String min = "";
			String max = "";
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			if(rs.next()){
				min = rs.getString("MIN_NO");
				max = rs.getString("MAX_NO");
			}
			
			if(min.equals("") || min == null){
				min ="0";
			}
			if(max.equals("") || max == null){
				max ="999999999";
			}
			
			sb = new StringBuffer();
			sb.append("SELECT DATE_FORMAT(A.MD_DATE,'%m.%d') AS DATE		\n");
			sb.append("     , B.SG_SEQ										\n");
			sb.append("     , C.SG_NAME										\n");
			sb.append("     , COUNT(*) AS S_CNT								\n");
			sb.append("  FROM META A										\n");
			sb.append("     , IDX B											\n");
			sb.append("     , SITE_GROUP C									\n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ							\n"); 
			sb.append("   AND B.SG_SEQ = C.SG_SEQ							\n");
			sb.append("   AND A.MD_SEQ BETWEEN "+min+" AND "+max+"			\n");
			sb.append("   AND (B.I_STATUS = 'N' AND B.M_SEQ <> 2)			\n");
			sb.append("   AND B.K_XP = "+k_xp+"								\n");
			sb.append("   AND B.K_YP = "+k_yp+"								\n");
			sb.append("   AND B.K_ZP = "+k_zp+"								\n");
			sb.append(" GROUP BY DATE_FORMAT(A.MD_DATE,'%Y%m%d'), B.SG_SEQ	\n");
			sb.append(" ORDER BY C.SG_SEQ									\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
				statisticsBean.setDate(rs.getString("DATE"));
				statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
        		statisticsBean.setSg_name(rs.getString("SG_NAME"));
        		statisticsBean.setS_cnt(rs.getString("S_CNT")); 
        		result.add(statisticsBean);	
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
	

	public ArrayList getSiteGroup()
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" SELECT SG_SEQ, SG_NAME FROM SITE_GROUP \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			while(rs.next()){
				statisticsBean = new StatisticsBean();
				statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
        		statisticsBean.setSg_name(rs.getString("SG_NAME"));
        		result.add(statisticsBean);
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
	
	public ArrayList getIssueCode(String type)
    {
		ArrayList result = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.CodeNameBean childBean = null; 
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_CODE > 0 AND IC_TYPE = "+type+" AND IC_USEYN = 'Y' \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			while(rs.next()){
				
				childBean = superBean.new CodeNameBean();
				childBean.setCode(rs.getString("IC_CODE"));
				childBean.setName(rs.getString("IC_NAME"));
				
        		result.add(childBean);
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
	
	public ArrayList getStatisticsIssue(String sDate, String eDate)
    {
		ArrayList result = new ArrayList();
		
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.CodeNameBean childBean = null;
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			/*
			sb.append("SELECT DATE_FORMAT(A.MD_DATE,'%m.%d') AS DATE		\n");
			sb.append("     , B.SG_SEQ										\n");
			sb.append("     , C.SG_NAME										\n");
			sb.append("     , COUNT(*) AS S_CNT								\n");
			sb.append("  FROM META A										\n");
			sb.append("     , IDX B											\n");
			sb.append("     , SITE_GROUP C									\n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ							\n"); 
			sb.append("   AND B.SG_SEQ = C.SG_SEQ							\n");
			sb.append("   AND A.MD_SEQ BETWEEN "+min+" AND "+max+"			\n");
			sb.append("   AND (B.I_STATUS = 'N' AND B.M_SEQ <> 2)			\n");
			sb.append("   AND B.K_XP = "+k_xp+"								\n");
			sb.append("   AND B.K_YP = "+k_yp+"								\n");
			sb.append("   AND B.K_ZP = "+k_zp+"								\n");
			sb.append(" GROUP BY DATE_FORMAT(A.MD_DATE,'%Y%m%d'), B.SG_SEQ	\n");
			sb.append(" ORDER BY C.SG_SEQ									\n");
			*/
			
			sb.append("SELECT DATE_FORMAT(A.MD_DATE,'%m.%d') AS MD_DATE							\n");
			sb.append("     , B.IC_CODE 														\n");
			sb.append("     , C.IC_NAME 														\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("  FROM ISSUE_DATA A														\n");
			sb.append("     , ISSUE_DATA_CODE B													\n");
			sb.append("     , ISSUE_CODE C														\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
			sb.append("   AND B.IC_TYPE = 11													\n");
			sb.append("   AND B.IC_TYPE = C.IC_TYPE												\n");
			sb.append("   AND B.IC_CODE = C.IC_CODE												\n");
			sb.append(" GROUP BY DATE_FORMAT(A.MD_DATE,'%Y%m%d'), B.IC_CODE						\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				childBean = superBean.new CodeNameBean();
				childBean.setDate(rs.getString("MD_DATE"));
				childBean.setCode(rs.getString("IC_CODE"));
				childBean.setName(rs.getString("IC_NAME"));
				childBean.setCnt(rs.getString("CNT")); 
        		result.add(childBean);	
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
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public ArrayList get_Keyword_name()
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("		SELECT K_XP, K_VALUE FROM KEYWORD WHERE K_USEYN='Y' AND K_YP = 0	\n");
			rs = stmt.executeQuery( sb.toString() );
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
				statisticsBean.setK_xp(rs.getString("K_XP"));
				statisticsBean.setK_value(rs.getString("K_VALUE"));
        		result.add(statisticsBean);	
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
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public ArrayList get_siteG_name()
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("		SELECT SG_SEQ, SG_NAME FROM SITE_GROUP	\n");
			rs = stmt.executeQuery( sb.toString() );
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
				statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
				statisticsBean.setSg_name(rs.getString("SG_NAME"));
        		result.add(statisticsBean);	
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
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public ArrayList get_site_group_data( String minNo, String maxNo, String xp )
    {
		ArrayList result = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append(" SELECT "+xp+" AS K_XP, A.SG_SEQ, A.SG_NAME, IFNULL(B.CNT,0) CNT                                                                                        \n");
			sb.append(" FROM                                                                                                                     \n");
			sb.append("   (                                                                                                                      \n");
			sb.append("     SELECT SG_SEQ, SG_NAME FROM SITE_GROUP                                                                               \n");
			sb.append("   ) A LEFT OUTER JOIN                                                                                                    \n");
			sb.append("   (                                                                                                                      \n");
			sb.append("     SELECT SG_SEQ , COUNT(MD_PSEQ) CNT                                                                                   \n");
			sb.append("     FROM                                                                                                                 \n");
			sb.append(" 	META M, (SELECT MD_SEQ, SG_SEQ FROM IDX WHERE MD_SEQ BETWEEN "+minNo+" AND "+maxNo+" AND K_XP = "+xp+" GROUP BY MD_SEQ, SG_SEQ) I  \n");
			sb.append("     WHERE                                                                                                                \n");
			sb.append(" 	M.MD_SEQ = I.MD_SEQ                                                                                              \n");
			sb.append("     GROUP BY SG_SEQ                                                                                                      \n");
			sb.append("    )B ON A.SG_SEQ = B.SG_SEQ                                                                                             \n");
			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
        		statisticsBean.setK_xp(rs.getString("K_XP"));
        		statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
        		statisticsBean.setSg_name(rs.getString("A.SG_NAME"));
        		statisticsBean.setK_cnt(rs.getString("CNT"));
        		result.add(statisticsBean);	
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
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */		
	public String get_site_group_data_sum( String minNo, String maxNo, String xp )
    {
		String result = "";
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append(" SELECT K_XP , COUNT(MD_PSEQ) CNT                                                                                                                                                               \n");
			sb.append(" FROM                                                                                                                                                                                                                 \n");
			sb.append("       META M, (SELECT MD_SEQ, K_XP FROM IDX WHERE MD_SEQ BETWEEN "+minNo+" AND "+maxNo+" AND K_XP = "+xp+" GROUP BY MD_SEQ, SG_SEQ) I \n");
			sb.append(" WHERE                                                                                                                                                                                                               \n");
			sb.append("       M.MD_SEQ = I.MD_SEQ                                                                                                                                                                                  \n");
			sb.append(" GROUP BY K_XP                                                                                                                                                                                                  \n");
			
			rs = stmt.executeQuery( sb.toString() );
			System.out.println(sb.toString());
			
			if( rs.next() )
			{
				result = rs.getString("CNT");
			}else{
				result = "0";
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
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public String get_chart_legend_name( String xp )
	{
		String result = "";
		try {
			if(xp.length()>0)
			{
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				stmt = dbconn.createStatement();
				
				sb = new StringBuffer();
				
				sb.append("	SELECT K_VALUE FROM KEYWORD WHERE K_USEYN='Y' AND K_YP=0 AND K_XP IN("+xp+") ORDER BY K_XP	\n");
				//System.out.println(sb.toString());
				rs = stmt.executeQuery( sb.toString() );
				
				while( rs.next() )
				{
					if(result.equals("")){
						result = rs.getString("K_VALUE");
					}else{
						result += ","+rs.getString("K_VALUE");
					}
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
    
	/**
     * 사이트 그룹별 수집현황
     */
	public ArrayList getSiteGroupAnalysis(String fromDate, String toDate) {
		ArrayList result = new ArrayList();

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            
        	sb = new StringBuffer();
        	sb.append("SELECT A.SG_SEQ, A.SG_NAME, IFNULL(A.S_CNT, 0) AS S_CNT, IFNULL(B.ID_CNT, 0) AS ID_CNT\n");
        	sb.append(", IFNULL(C.P_CNT, 0) AS P_CNT, IFNULL(D.N_CNT, 0) AS N_CNT, IFNULL(E.E_CNT, 0) AS E_CNT\n");
        	sb.append(", IFNULL(F.CNT1, 0) AS CNT1, IFNULL(G.CNT2, 0) AS CNT2, IFNULL(H.CNT3, 0) AS CNT3\n");
        	sb.append("FROM\n");
        	sb.append("(\n");
        	sb.append("		SELECT SG.SG_SEQ, SG.SG_NAME, COUNT(S_SEQ) AS S_CNT\n");
        	sb.append("		FROM SITE_GROUP SG\n");
        	sb.append("		INNER JOIN SG_S_RELATION SSR\n");
        	sb.append("		ON SG.SG_SEQ = SSR.SG_SEQ\n");
        	sb.append("		GROUP BY SG.SG_SEQ, SG.SG_NAME\n");
        	sb.append(") A LEFT OUTER JOIN\n");
        	sb.append("(\n");
        	sb.append("		SELECT SG_SEQ, COUNT(ID_SEQ) AS ID_CNT\n");
        	sb.append("		FROM ISSUE_DATA\n");
        	sb.append("		WHERE MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
        	sb.append("		GROUP BY SG_SEQ\n");
        	sb.append(") B\n");
        	sb.append("ON A.SG_SEQ = B.SG_SEQ\n");
        	sb.append("LEFT OUTER JOIN\n");
        	sb.append("(\n");
        	sb.append("		SELECT ID.SG_SEQ, COUNT(ID.ID_SEQ) AS P_CNT\n");
        	sb.append("		FROM ISSUE_DATA ID\n");
        	sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
        	sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
        	sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
        	sb.append("		AND IDC.IC_TYPE = 9 AND IDC.IC_CODE = 1\n");
        	sb.append("		GROUP BY ID.SG_SEQ   \n");
        	sb.append(") C\n");
        	sb.append("ON A.SG_SEQ = C.SG_SEQ\n");
        	sb.append("LEFT OUTER JOIN\n");
        	sb.append("(\n");
        	sb.append("		SELECT ID.SG_SEQ, COUNT(ID.ID_SEQ) AS N_CNT\n");
        	sb.append("		FROM ISSUE_DATA ID\n");
        	sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
        	sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
        	sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
        	sb.append("		AND IDC.IC_TYPE = 9 AND IDC.IC_CODE = 2\n");
        	sb.append("		GROUP BY ID.SG_SEQ\n");
        	sb.append(") D\n");
        	sb.append("ON A.SG_SEQ = D.SG_SEQ\n");
        	sb.append("LEFT OUTER JOIN\n");
        	sb.append("(\n");
        	sb.append("		SELECT ID.SG_SEQ, COUNT(ID.ID_SEQ) AS E_CNT\n");
        	sb.append("		FROM ISSUE_DATA ID\n");
        	sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
        	sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
        	sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
        	sb.append("		AND IDC.IC_TYPE = 9 AND IDC.IC_CODE = 3\n");
        	sb.append("		GROUP BY ID.SG_SEQ\n");   
        	sb.append(") E\n");
        	sb.append("ON A.SG_SEQ = E.SG_SEQ\n");
        	sb.append("LEFT OUTER JOIN\n");
        	sb.append("(\n");
        	sb.append("		SELECT ID.SG_SEQ, COUNT(ID.ID_SEQ) AS CNT1\n");
        	sb.append("		FROM ISSUE_DATA ID\n");
        	sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
        	sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
        	sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
        	sb.append("		AND IDC.IC_TYPE = 10 AND IDC.IC_CODE = 1\n");
        	sb.append("		GROUP BY ID.SG_SEQ\n");
        	sb.append(") F\n");
        	sb.append("ON A.SG_SEQ = F.SG_SEQ\n");
        	sb.append("LEFT OUTER JOIN\n");
        	sb.append("(\n");
        	sb.append("		SELECT ID.SG_SEQ, COUNT(ID.ID_SEQ) AS CNT2\n");
        	sb.append("		FROM ISSUE_DATA ID\n");
        	sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
        	sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
        	sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
        	sb.append("		AND IDC.IC_TYPE = 10 AND IDC.IC_CODE = 2\n");
        	sb.append("		GROUP BY ID.SG_SEQ\n");
        	sb.append(") G\n");
        	sb.append("ON A.SG_SEQ = G.SG_SEQ\n");
        	sb.append("LEFT OUTER JOIN\n");
        	sb.append("(\n");
        	sb.append("		SELECT ID.SG_SEQ, COUNT(ID.ID_SEQ) AS CNT3\n");
        	sb.append("		FROM ISSUE_DATA ID\n");
        	sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
        	sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
        	sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
        	sb.append("		AND IDC.IC_TYPE = 10 AND IDC.IC_CODE = 3\n");
        	sb.append("		GROUP BY ID.SG_SEQ\n");
        	sb.append(") H\n");
        	sb.append("ON A.SG_SEQ = H.SG_SEQ\n");
        	sb.append("GROUP BY A.SG_SEQ\n");

			System.out.println(sb.toString());
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
			
            while(rs.next()){
            		statisticsBean = new StatisticsBean();
            		statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
            		statisticsBean.setSg_name(rs.getString("SG_NAME"));
            		statisticsBean.setScnt(rs.getInt("S_CNT"));
            		statisticsBean.setIdcnt(rs.getInt("ID_CNT"));
            		statisticsBean.setP_cnt(rs.getInt("P_CNT"));
            		statisticsBean.setN_cnt(rs.getInt("N_CNT"));
            		statisticsBean.setE_cnt(rs.getInt("E_CNT"));
            		statisticsBean.setCnt1(rs.getInt("CNT1"));
            		statisticsBean.setCnt2(rs.getInt("CNT2"));
            		statisticsBean.setCnt3(rs.getInt("CNT3"));
					result.add(statisticsBean);
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
     * 사이트별 수집현황
     */
	public ArrayList getSiteAnalysis(String fromDate, String toDate, String Sgroup) {
		ArrayList result = new ArrayList();	//최종리스트

        try {        	
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            
			sb = new StringBuffer();
			sb.append("SELECT A.S_SEQ, A.S_NAME, IFNULL(B.ID_CNT, 0) AS ID_CNT\n");
			sb.append(", IFNULL(C.P_CNT, 0) AS P_CNT, IFNULL(D.N_CNT, 0) AS N_CNT, IFNULL(E.E_CNT, 0) AS E_CNT\n");
			sb.append(", IFNULL(F.CNT1, 0) AS CNT1, IFNULL(G.CNT2, 0) AS CNT2, IFNULL(H.CNT3, 0) AS CNT3\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT SSR.S_SEQ, SSR.S_NAME\n");
			sb.append("		FROM SG_S_RELATION SSR\n");
			sb.append("		WHERE SG_SEQ = "+Sgroup+"\n");
			sb.append(") A LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT S_SEQ, COUNT(ID_SEQ) AS ID_CNT\n");
			sb.append("		FROM ISSUE_DATA\n");
			sb.append("		WHERE MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
			sb.append("		GROUP BY S_SEQ\n");
			sb.append(") B\n");
			sb.append("ON A.S_SEQ = B.S_SEQ\n");
			sb.append("LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT ID.S_SEQ, COUNT(ID.ID_SEQ) AS P_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9 AND IDC.IC_CODE = 1\n");
			sb.append("		GROUP BY ID.S_SEQ  \n");
			sb.append(") C\n");
			sb.append("ON A.S_SEQ = C.S_SEQ\n");
			sb.append("LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT ID.S_SEQ, COUNT(ID.ID_SEQ) AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9 AND IDC.IC_CODE = 2\n");
			sb.append("		GROUP BY ID.S_SEQ  \n");
			sb.append(") D\n");
			sb.append("ON A.S_SEQ = D.S_SEQ\n");
			sb.append("LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT ID.S_SEQ, COUNT(ID.ID_SEQ) AS E_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9 AND IDC.IC_CODE = 3\n");
			sb.append("		GROUP BY ID.S_SEQ  \n");
			sb.append(") E\n");
			sb.append("ON A.S_SEQ = E.S_SEQ\n");
			sb.append("LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT ID.S_SEQ, COUNT(ID.ID_SEQ) AS CNT1\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 10 AND IDC.IC_CODE = 1\n");
			sb.append("		GROUP BY ID.S_SEQ  \n");
			sb.append(") F\n");
			sb.append("ON A.S_SEQ = F.S_SEQ\n");
			sb.append("LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT ID.S_SEQ, COUNT(ID.ID_SEQ) AS CNT2\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 10 AND IDC.IC_CODE = 2\n");
			sb.append("		GROUP BY ID.S_SEQ  \n");
			sb.append(") G\n");
			sb.append("ON A.S_SEQ = G.S_SEQ\n");
			sb.append("LEFT OUTER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT ID.S_SEQ, COUNT(ID.ID_SEQ) AS CNT3\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') AND DATE_FORMAT(NOW(), '%Y-%m-%d 23:59:59')\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 10 AND IDC.IC_CODE = 3\n");
			sb.append("		GROUP BY ID.S_SEQ  \n");
			sb.append(") H\n");
			sb.append("ON A.S_SEQ = H.S_SEQ\n");
			sb.append("GROUP BY A.S_SEQ\n");
			sb.append("ORDER BY id_cnt desc \n");
								
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
			
            while(rs.next()){
            		
        		statisticsBean = new StatisticsBean();
        		statisticsBean.setS_seq(rs.getString("S_SEQ"));
        		statisticsBean.setS_name(rs.getString("S_NAME"));
        		statisticsBean.setIdcnt(rs.getInt("ID_CNT"));
        		statisticsBean.setP_cnt(rs.getInt("P_CNT"));
        		statisticsBean.setN_cnt(rs.getInt("N_CNT"));
        		statisticsBean.setE_cnt(rs.getInt("E_CNT"));
        		statisticsBean.setCnt1(rs.getInt("CNT1"));
        		statisticsBean.setCnt2(rs.getInt("CNT2"));
        		statisticsBean.setCnt3(rs.getInt("CNT3"));
				result.add(statisticsBean);
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
	
	public ArrayList getDailyIssue(String sDate, String eDate, String siteGroup, String code3) {
		ArrayList arrIssueData = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT ID.IT_SEQ, ID.ID_SEQ, ID.ID_TYPE, ID.ID_DATETIME, ID.ID_SITENAME, ID.ID_TITLE, ID.ID_URL, ID.ID_SAME_CT, IC.IC_NAME, IDC3.IC_CODE                                                                                                 		\n");
			sb.append("FROM IMS_ISSUE_DATA ID, IMS_SITE_DATA SD, IMS_ISSUE_DATA_CODE IDC1, IMS_ISSUE_DATA_CODE IDC2, IMS_ISSUE_DATA_CODE IDC3, IMS_ISSUE_CODE IC                                   		\n");
			sb.append("WHERE ID_DATETIME BETWEEN  TO_DATE('"+sDate+"','YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"+eDate+"','YYYY-MM-DD HH24:MI:SS')		\n");
			sb.append("	AND ID.ID_SITECODE = SD.SD_GSN                                                                                                                                                                        		\n");
			sb.append("	AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                               		\n");
			sb.append("	AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                               		\n");
			sb.append("	AND ID.ID_SEQ = IDC3.ID_SEQ                                                                                                                                                                               		\n");
			sb.append("	AND IDC2.IC_TYPE = IC.IC_TYPE AND IDC2.IC_CODE = IC.IC_CODE                                                                                                                               		\n");
			sb.append("	AND IDC1.IC_TYPE = 3 AND IDC1.IC_CODE IN ("+code3+")                                                                                                                                                          		\n");
			sb.append("	AND IDC2.IC_TYPE = 2                                                                                                                                                                                          		\n");
			sb.append("	AND IDC3.IC_TYPE = 1                                                                                                                                                                                          		\n");
			sb.append("	AND SD.SG_SEQ IN ("+siteGroup+")                                                                                                                                                                            		\n");
			sb.append("ORDER BY IDC2.IC_CODE, ID.ID_SAME_CT DESC                                                                                                                                                                            		\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				IssueDataBean idBean = new IssueDataBean();
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));						
        		arrIssueData.add(idBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrIssueData;
	}
	
	public ArrayList getDailyIssueWeather(String sDate,String sTime, String eDate,String eTime, String siteGroup, String type1) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT A1.IC_CODE, A1.IC_NAME, IFNULL(A2.CT1,0) AS CT1, IFNULL(A2.CT2,0) AS CT2, IFNULL(A2.CT3,0) AS CT3, IFNULL(CT1+CT2,0) AS TOTAL, IFNULL(CASE CT1 WHEN 0 THEN '0.00' ELSE FORMAT(CT1/(CT1+CT2)*100,'####.##') END,0) AS CT1_PER	\n");
			sb.append("FROM (	                                                                                                                                                                                                                                                                               	\n");
			sb.append("		SELECT *                                                                                                                                                                                                                                                                  	\n");
			sb.append("		FROM ISSUE_CODE IC3                                                                                                                                                                                                                                        	\n");
			sb.append("		WHERE IC3.IC_TYPE = 4 AND IC3.IC_CODE != 0                                                                                                                                                                                                               	\n");
			sb.append("	 )A1 LEFT OUTER JOIN                                                                                                                                                                                                                                                        	\n");
			sb.append("	 (                                                                                                                                                                                                                                                                                     	\n");
			sb.append("	  SELECT IFNULL(SUM(CASE IC.IC_CODE WHEN 1 THEN 1 END), 0) AS CT1, IFNULL(SUM(CASE IC.IC_CODE WHEN 2 THEN 1 END), 0) AS CT2,                        		       	\n");
			sb.append("		    IFNULL(SUM(CASE IC.IC_CODE WHEN 3 THEN 1 END), 0) AS CT3, IDC2.IC_CODE                                                                                                                  		       	\n");
			sb.append("			FROM ISSUE_DATA ID, SG_S_RELATION SD, ISSUE_DATA_CODE IDC1, ISSUE_DATA_CODE IDC2, ISSUE_CODE IC 	                               	\n");
			sb.append("			WHERE ID_REGDATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'  \n");
			sb.append("			AND ID.S_SEQ = SD.S_SEQ                                                                                                                                                                        		                        		\n");
			sb.append("			AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                               		                                		\n");
			sb.append("			AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                                                                       	       	\n");
			sb.append("			AND IDC1.IC_TYPE = IC.IC_TYPE AND IDC1.IC_CODE = IC.IC_CODE                                                                                                                                                                      	       	\n");
			sb.append("			AND IDC1.IC_TYPE = 1 AND IDC1.IC_CODE IN ("+type1+")                                                                                                                                                                                      	       	\n");
			sb.append("			AND IDC2.IC_TYPE = 4                                                                                                                                                                                                                                 	       	\n");
			//sb.append("			AND IDC3.IC_TYPE = 5                                                                                                                          		                                                                                        		\n");
			sb.append("			AND SD.SG_SEQ IN ("+siteGroup+")                                                                                                                                                                                                                   	       	\n");
			sb.append("			GROUP BY IDC2.IC_CODE                                                                                                                                                                                                                                   	\n");
			sb.append("	 )A2 ON (A1.IC_CODE = A2.IC_CODE)                                                                                                                                                                                                                                     	\n");
			sb.append("ORDER BY A1.IC_CODE	                                                                                                                                                                                                                                                       	\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
					String[] result = new String[5];
					result[0] = rs.getString("CT1");
					result[1] = rs.getString("CT2");
					result[2] = rs.getString("IC_NAME");
					result[3] = rs.getString("CT1_PER");
					result[4] = rs.getString("TOTAL");
					arrResult.add(result);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getDailyIssueWeatherAll(String sDate, String siteGroup) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(" SELECT A1.IC_CODE                                                                                                                                                                                                       \n");
			sb.append(" 	    , A1.IC_NAME                                                                                                                                                                                                     \n");
			sb.append(" 	    , IFNULL(A2.CT1,0) AS CT1                                                                                                                                                                                  \n");
			sb.append(" 	    , IFNULL(A2.CT2,0) AS CT2                                                                                                                                                                                  \n");
			sb.append(" 	    , IFNULL(A2.CT3,0) AS CT3                                                                                                                                                                                  \n");
			sb.append(" 	    , IFNULL(CT1+CT2,0) AS TOTAL                                                                                                                                                                           \n");
			sb.append(" 	    , IFNULL(CASE CT1 WHEN 0 THEN '0.00' ELSE (CT1/(CT1+CT2)*100) END,0) AS CT1_PER	                                                                                     \n");
			sb.append(" FROM (	                                                                                                                                                                                                                      \n");
			sb.append(" 		SELECT *                                                                                                                                                                                                        \n");
			sb.append(" 		FROM ISSUE_CODE IC3                                                                                                                                                                                    \n");
			sb.append(" 		WHERE IC3.IC_TYPE = 6 AND IC3.IC_CODE != 0  AND IC_USEYN = 'Y'                                                                                                                   \n");
			sb.append(" 	 )A1 LEFT OUTER JOIN                                                                                                                                                                                             \n");
			sb.append(" 	 (                                                                                                                                                                                                                            \n");
			sb.append(" 	  SELECT IFNULL(SUM(CASE IC.IC_NAME WHEN '긍정' THEN 1 END), 0) AS CT1, IFNULL(SUM(CASE IC.IC_NAME WHEN '부정' THEN 1 END), 0) AS CT2, \n");
			sb.append(" 		    IFNULL(SUM(CASE IC.IC_NAME WHEN '중립' THEN 1 END), 0) AS CT3, IDC3.IC_CODE                                                                                      \n");
			sb.append(" 			FROM ISSUE_DATA ID, SG_S_RELATION SD, ISSUE_DATA_CODE IDC1, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3, ISSUE_CODE IC 	\n");
			sb.append(" 			WHERE ID.MD_DATE BETWEEN  '"+sDate+" 00:00:00' AND '"+sDate+" 23:59:59'		                        	                                             \n");
			sb.append(" 			AND ID.ID_USEYN = 'Y'                                                                                                                                                                            \n");
			sb.append(" 			AND ID.S_SEQ = SD.S_SEQ                                                                                                                                                                       \n");
			sb.append(" 			AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                   \n");
			sb.append(" 			AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                   \n");
			sb.append(" 			AND ID.ID_SEQ = IDC3.ID_SEQ                                                                                                                                                                   \n");
			sb.append(" 			AND IDC2.IC_TYPE = IC.IC_TYPE AND IDC2.IC_CODE = IC.IC_CODE                                                                                                              \n");
			sb.append(" 			AND IDC1.IC_TYPE = 5                                                                                                                                                                              \n");
			sb.append(" 			AND IDC2.IC_TYPE = 1                                                                                                                                                                              \n");
			sb.append(" 			AND IDC3.IC_TYPE = 6                                                                                                                          		                                      \n");
			sb.append(" 			AND SD.SG_SEQ IN ("+siteGroup+")                                                                                                                         \n");
			sb.append(" 			GROUP BY IDC3.IC_CODE                                                                                                                                                                         \n");
			sb.append(" 	 )A2 ON (A1.IC_CODE = A2.IC_CODE)                                                                                                                                                                         \n");
			sb.append(" ORDER BY A1.IC_CODE                                                                                                                                                                                                    \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			int toTalCnt1 = 0;
			int toTalCnt2 = 0;
			int toTalCnt3 = 0;
			int toTalCnt = 0;
			double toTalPer = 0;
			
			while( rs.next() ) {
				toTalCnt1 += rs.getInt("CT1");
				toTalCnt2 += rs.getInt("CT2");
				toTalCnt3 += rs.getInt("CT3");
				toTalCnt += rs.getInt("TOTAL");
				toTalPer += rs.getDouble("CT1_PER");
				
				String[] result = new String[6];
				result[0] = rs.getString("CT1");
				result[1] = rs.getString("CT2");
				result[2] = rs.getString("IC_NAME");
				result[3] = rs.getString("CT1_PER");
				result[4] = rs.getString("TOTAL");
				result[5] = rs.getString("IC_CODE");
				arrResult.add(result);
			}
			
			if(toTalCnt>0){
				//toTalPer = toTalPer/arrResult.size();
				toTalPer = ((double)toTalCnt1/(toTalCnt1+toTalCnt2)*100);
				String[] total = new String[6];
				total[0] = Integer.toString(toTalCnt1);
				total[1] = Integer.toString(toTalCnt2);
				total[2] = Integer.toString(toTalCnt3);
				total[3] = Integer.toString(toTalCnt);
				String tPercent ="";
				
				if(toTalPer>0){
					
					tPercent = su.digitFormatDouble(Double.toString(toTalPer),"###.##");
				}
				total[4] = tPercent;
				arrResult.add(total);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
  
	public String[] getTwitterMinMax(String sdate, String edate) {
			
		String[] result = new String[2];
		result[0] = "";
		result[1] = "";
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+sdate+" 00:00:00' AND '"+sdate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO	\n"); 
			sb.append("      ,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+edate+" 00:00:00' AND '"+edate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO 	\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ) {
				
				result[0] = rs.getString("MIN_NO");
				result[1] = rs.getString("MAX_NO");
				
				
				if(result[0] == null || result[0].equals("")){
					result[0] = "0";
				}
				if(result[1] == null || result[1].equals("")){
					result[1] = "9999999999";
				}
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList getTwitter_follower(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT T_USER_ID									\n");
			sb.append("     , MAX(T_FOLLOWERS) AS T_FOLLOWERS			\n");
			sb.append("  FROM TWEET 									\n");
			sb.append(" WHERE MD_SEQ BETWEEN "+min+" AND "+max+"		\n");
			sb.append(" GROUP BY T_USER_ID 								\n");
			sb.append(" ORDER BY T_FOLLOWERS DESC						\n");
			sb.append(" LIMIT 10										\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("T_USER_ID"));
				childBean.setCnt(rs.getString("T_FOLLOWERS"));
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getTwitter_twit(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT T_USER_ID								\n");
			sb.append("     , MAX(T_TWEET) AS T_TWEET				\n");
			sb.append("  FROM TWEET 								\n");
			sb.append(" WHERE MD_SEQ BETWEEN "+min+" AND "+max+"	\n");
			sb.append(" GROUP BY T_USER_ID 							\n");
			sb.append(" ORDER BY T_TWEET DESC						\n");
			sb.append(" LIMIT 10									\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("T_USER_ID"));
				childBean.setCnt(rs.getString("T_TWEET"));
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getTwitter_negative(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			
			sb.append("SELECT A.T_USER_ID								\n");
			sb.append("     , COUNT(0) AS CNT							\n");
			sb.append("  FROM TWEET A									\n");
			sb.append("     , ISSUE_DATA B								\n");
			sb.append("     , ISSUE_DATA_CODE C							\n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ						\n");
			sb.append("   AND B.ID_SEQ = C.ID_SEQ						\n");
			sb.append("   AND A.MD_SEQ BETWEEN "+min+" AND "+max+"		\n");	
			sb.append("   AND C.IC_TYPE = 9								\n");
			sb.append("   AND C.IC_CODE = 2								\n");
			sb.append(" GROUP BY A.T_USER_ID   							\n");							
			sb.append(" ORDER BY COUNT(0) DESC							\n");					
			sb.append(" LIMIT 10										\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("T_USER_ID"));
				childBean.setCnt(rs.getString("CNT"));
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getTwitter_bestRr(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("SELECT A.P_USER																																								\n");
			sb.append("     , A.CNT																																									\n");
			sb.append("     , A.T_FOLLOWERS AS FULL_FOLLOWER																																		\n");
			sb.append("     , IFNULL((SELECT CONCAT(T_FOLLOWERS,'@',T_FOLLOWING,'@',T_TWEET) FROM TWEET WHERE T_USER_ID = A.P_USER AND MD_TITLE LIKE CONCAT('%',A.DATA,'%') LIMIT 1 ),'') AS P_INFO	\n");
			sb.append("     , A.MD_TITLE																																							\n");
			sb.append("  FROM (																																										\n");
			sb.append("        SELECT MID(A.MD_TITLE, INSTR(A.MD_TITLE, 'RT @')+4, (INSTR(A.MD_TITLE, ':') - (INSTR(A.MD_TITLE, 'RT @')+4))) AS P_USER												\n");
			sb.append("             , MID(A.MD_TITLE, INSTR(A.MD_TITLE, ': ')+2, 40) AS DATA																										\n");
			sb.append("             , COUNT(0) AS CNT																																				\n");
			sb.append("             , MIN(A.MD_TITLE) AS MD_TITLE																																	\n"); 
			sb.append("             , SUM(T_FOLLOWERS) AS T_FOLLOWERS																																\n");
			sb.append("          FROM TWEET A																																						\n");
			sb.append("         WHERE A.MD_SEQ BETWEEN "+min+" AND "+max+"																															\n");
			sb.append("           AND MID(A.MD_TITLE, INSTR(A.MD_TITLE, 'RT @'), 40) <> ''																											\n");
			sb.append("           AND A.T_IS_RT = 'R'																																				\n");
			sb.append("         GROUP BY MID(A.MD_TITLE, INSTR(A.MD_TITLE, 'RT @'), 40)																												\n");
			sb.append("         ORDER BY  COUNT(0) DESC																																				\n");
			sb.append("         LIMIT 10)A																																							\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("P_USER"));
				childBean.setCnt(rs.getString("CNT"));
				
				childBean.setFull_follower(rs.getString("FULL_FOLLOWER"));
				childBean.setInfo(rs.getString("P_INFO"));
				childBean.setMd_title(rs.getString("MD_TITLE"));
				
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public int[] getFacebookStatistics(String currentDate, String oldDate) {
		
		int[] result = new int[4];
		
		int currentFan = 0, currentFriend = 0;
		int oldFan = 0, oldFriend = 0;

		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("SELECT SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT FROM STATIC_SOCIAL_FB WHERE SSF_DATE = '"+oldDate+"'		\n");
			sb.append("UNION																								\n");
			sb.append("SELECT SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT FROM STATIC_SOCIAL_FB WHERE SSF_DATE = '"+currentDate+"'	\n");
			sb.append("ORDER BY SSF_DATE ASC																				\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				if(rs.getString("SSF_DATE").equals(oldDate)){
					oldFan = rs.getInt("SSF_FAN_CNT");
					oldFriend = rs.getInt("SSF_FRD_CNT");
				}else if(rs.getString("SSF_DATE").equals(currentDate)){
					currentFan = rs.getInt("SSF_FAN_CNT");
					currentFriend = rs.getInt("SSF_FRD_CNT");
				}
			}
			
			result[0] = currentFan;
			result[1] = currentFan - oldFan;
			result[2] = currentFriend;
			result[3] = currentFriend - oldFriend;
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public String[] getBlogStatistics(String currentDate, String oldDate) {
		
		String[] result = new String[4];
		
		int currentHit = 0;
		int oldHit = 0;
		String currentSearchKey = "";
		int accrueHit = 0;
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("SELECT SSB_DATE, SSB_DAILY_HIT, SSB_ALL_HIT, SSB_SEARCHKEY FROM STATIC_SOCIAL_BLOG WHERE SSB_DATE = '"+oldDate+"'		\n");
			sb.append("UNION																													\n");
			sb.append("SELECT SSB_DATE, SSB_DAILY_HIT, SSB_ALL_HIT, SSB_SEARCHKEY FROM STATIC_SOCIAL_BLOG WHERE SSB_DATE = '"+currentDate+"'	\n");
			sb.append("ORDER BY SSB_DATE ASC																									\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				if(rs.getString("SSB_DATE").equals(oldDate)){
					oldHit = rs.getInt("SSB_DAILY_HIT");
				}else if(rs.getString("SSB_DATE").equals(currentDate)){
					currentHit = rs.getInt("SSB_DAILY_HIT");
					accrueHit = rs.getInt("SSB_ALL_HIT");
					currentSearchKey = rs.getString("SSB_SEARCHKEY");
				}
			}
			
			result[0] = Integer.toString(currentHit);
			result[1] = Integer.toString(currentHit-oldHit);
			result[2] = Integer.toString(accrueHit);
			result[3] = currentSearchKey;
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public int[] getTweetStatistics(String currentDate, String oldDate) {
		
		int[] result = new int[4];
		
		int currentFollower = 0, currentFollowing = 0;
		int oldFollower = 0, oldFollowing = 0;

		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("SELECT SST_DATE, SST_FOLLOWER, SST_FOLLOWING FROM STATIC_SOCIAL_TWEET WHERE SST_DATE = '"+oldDate+"'		\n");
			sb.append("UNION																									\n");
			sb.append("SELECT SST_DATE, SST_FOLLOWER, SST_FOLLOWING FROM STATIC_SOCIAL_TWEET WHERE SST_DATE = '"+currentDate+"'	\n");
			sb.append("ORDER BY SST_DATE ASC																					\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				if(rs.getString("SST_DATE").equals(oldDate)){
					oldFollower = rs.getInt("SST_FOLLOWER");
					oldFollowing = rs.getInt("SST_FOLLOWING");
				}else if(rs.getString("SST_DATE").equals(currentDate)){
					currentFollower = rs.getInt("SST_FOLLOWER");
					currentFollowing = rs.getInt("SST_FOLLOWING");
				}
			}
			
			result[0] = currentFollower;
			result[1] = currentFollower - oldFollower;
			result[2] = currentFollowing;
			result[3] = currentFollowing - oldFollowing;
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	
	public JSONArray getWeeklyChannelVolumn(String sDate, String eDate){
		JSONArray result = new JSONArray();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append(" SELECT                                                                                        \n");
			sb.append("        DATE_FORMAT(A.MD_DATE,'%Y-%m-%d') AS MD_DATE                                                \n");
			//sb.append("        ,SUM(IF(B.IC_CODE=1, 1,0)) AS 'CODE1'                                                       \n");
			sb.append("        ,SUM(IF(B.IC_CODE IN (1,7),1,0)) AS 'CODE1'     				                               \n");
			sb.append("        ,SUM(IF(B.IC_CODE=2, 1,0)) AS 'CODE2'                                                       \n");
			sb.append("        ,SUM(IF(B.IC_CODE=3, 1,0)) AS 'CODE3'                                                       \n");
			sb.append("        ,SUM(IF(B.IC_CODE=4, 1,0)) AS 'CODE4'                                                       \n");
			sb.append("        ,SUM(IF(B.IC_CODE=5, 1,0)) AS 'CODE5'                                                       \n");
			//sb.append("        ,SUM(IF(B.IC_CODE=7, 1,0)) AS 'CODE7'                                                       \n");
			sb.append("        ,SUM(IF(B.IC_CODE=9, 1,0)) AS 'CODE9'                                                       \n");
			sb.append("        ,SUM(IF(B.IC_CODE=10, 1,0)) AS 'CODE10'                                                     \n");
			sb.append("        ,COUNT(0) AS TOTAL                                                                          \n");
			//sb.append("        ,ROUND((SUM(IF(C.IC_CODE = 2, 1,0))/count(0))*100) AS NEG                                   \n");
			sb.append("   FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C                                          \n");
			sb.append("  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                             \n");
			sb.append("    AND A.ID_SEQ = B.ID_SEQ                                                                         \n");
			sb.append("    AND B.IC_TYPE = 6                                                                               \n");
			sb.append("    AND A.ID_SEQ = C.ID_SEQ                                                                         \n");
			sb.append("    AND C.IC_TYPE = 9                                                                               \n");
			sb.append("  GROUP BY DATE_FORMAT(A.MD_DATE,'%Y-%m-%d')                                                        \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while( rs.next() ) {
				JSONObject obj = new JSONObject();
				obj.put("md_date", rs.getString("MD_DATE"));
				obj.put("news", rs.getString("CODE1"));
				obj.put("twitter", rs.getString("CODE2"));
				obj.put("blog", rs.getString("CODE3"));
				obj.put("caffe", rs.getString("CODE4"));
				obj.put("community", rs.getString("CODE5"));
				//obj.put("portal", rs.getString("CODE7"));
				obj.put("facebook", rs.getString("CODE9"));
				obj.put("daegu", rs.getString("CODE10"));
				obj.put("total", rs.getString("TOTAL"));
				//obj.put("neg", rs.getString("NEG"));
				result.put(obj);
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	
	public JSONArray getWeeklyInfoVolumn(String sDate, String eDate){
		JSONArray result = new JSONArray();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append(" SELECT                                                                                           \n");
			sb.append("   AA.IC_CODE                                                                                       \n");
		    sb.append("   ,AA.TYPE2                                                                                       \n");
		    sb.append("   ,BB.NEG AS NEG1                                                                                \n");
		    sb.append("   ,BB.CNT AS CNT2                                                                                \n");
		    sb.append("   ,AA.TYPE21                                                                                     \n");
		    sb.append("   ,AA.NEG AS NEG2                                                                                \n");
		    sb.append("   ,AA.CNT AS CNT21                                                                               \n");
			sb.append(" FROM                                                                                             \n");
			sb.append(" (                                                                                                \n");
			sb.append("   SELECT                                                                                         \n");
			sb.append("         B.IC_CODE                                                                                \n");
			sb.append("         ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 2 AND IC_CODE = B.IC_CODE) AS TYPE2     \n");
			sb.append("         ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 21 AND IC_CODE = C.IC_CODE) AS TYPE21   \n");
			sb.append("         ,COUNT(C.IC_CODE) AS CNT                                                                 \n");
			sb.append("         ,SUM(IF(D.IC_CODE = 2 ,1,0)) AS NEG                                                      \n");			
			sb.append("   FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C , ISSUE_DATA_CODE D                    \n");
			sb.append("   WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                          \n");
			sb.append("     AND A.ID_SEQ = B.ID_SEQ                                                                      \n");
			sb.append("     AND B.IC_TYPE = 2                                                                            \n");
			sb.append("     AND A.ID_SEQ = C.ID_SEQ                                                                      \n");
			sb.append("     AND C.IC_TYPE = 21                                                                           \n");
			sb.append("     AND A.ID_SEQ = D.ID_SEQ                                                                      \n");
			sb.append("     AND D.IC_TYPE = 9                                                                            \n");
			sb.append("     GROUP BY C.IC_CODE                                                                           \n");
			sb.append("     ) AA ,                                                                                       \n");
			sb.append("     (                                                                                            \n");
			sb.append("         SELECT                                                                                   \n");
			sb.append("         B.IC_CODE                                                                                \n");
			sb.append("         ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 2 AND IC_CODE = B.IC_CODE) AS TYPE2     \n");  
			sb.append("         ,COUNT(B.IC_CODE) AS CNT                                                                 \n");
			sb.append("         ,SUM(IF(C.IC_CODE = 2 ,1,0)) AS NEG                                                      \n");
			sb.append("   FROM ISSUE_DATA A, ISSUE_DATA_CODE B , ISSUE_DATA_CODE C                                       \n");
			sb.append("   WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                        \n");
			sb.append("     AND A.ID_SEQ = B.ID_SEQ                                                                      \n");
			sb.append("     AND B.IC_TYPE = 2                                                                            \n");
			sb.append("     AND A.ID_SEQ = C.ID_SEQ                                                                      \n");
			sb.append("     AND C.IC_TYPE = 9                                                                            \n");
			sb.append("     GROUP BY B.IC_CODE                                                                           \n");
			sb.append("     ) BB                                                                                         \n");
			sb.append("     WHERE AA.IC_CODE = BB.IC_CODE                                                                \n");
			sb.append("     ORDER BY IC_CODE ASC ,CNT21 DESC                                                             \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while( rs.next() ) {
				JSONObject obj = new JSONObject();
				obj.put("type2", rs.getString("TYPE2"));
				obj.put("neg1", rs.getString("NEG1"));
				obj.put("cnt2", rs.getString("CNT2"));
				obj.put("type21", rs.getString("TYPE21"));
				obj.put("neg2", rs.getString("NEG2"));
				obj.put("cnt21", rs.getString("CNT21"));
				result.put(obj);
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	
	public JSONArray getWeeklyInfoVolumn3(String sDate, String eDate){
		JSONArray result = new JSONArray();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("	SELECT                                                                                                                     \n");
			sb.append("	  AA.IC_CODE                                                                                                               \n");
			sb.append("	  ,AA.TYPE5                                                                                                                \n");
			sb.append("	  ,BB.NEG AS NEG1                                                                                                                  \n");
			sb.append("	  ,BB.CNT AS CNT5                                                                                                          \n");
			sb.append("	  ,AA.TYPE51                                                                                                               \n");
			sb.append("	  ,AA.NEG AS NEG2                                                                                                                  \n");
			sb.append("	  ,AA.CNT AS CNT51                                                                                                         \n");
			sb.append("	FROM                                                                                                                       \n");
			sb.append("	(                                                                                                                          \n");
			sb.append("	  SELECT                                                                                                                   \n");
			sb.append("	        B.IC_CODE                                                                                                          \n");
			sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 5 AND IC_CODE = B.IC_CODE) AS TYPE5                               \n");
			sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 51 AND IC_CODE = C.IC_CODE) AS TYPE51                             \n");
			sb.append("	        ,COUNT(C.IC_CODE) AS CNT                                                                                           \n");
			sb.append("	        ,IFNULL(COUNT(D.IC_CODE),0) AS NEG                                                                                 \n");
			sb.append("	  FROM ISSUE_DATA A LEFT OUTER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ = D.ID_SEQ AND D.IC_TYPE = 9 AND D.IC_CODE = 2           \n");
			sb.append("	  , ISSUE_DATA_CODE B, ISSUE_DATA_CODE C                                                                                   \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                  \n");
			sb.append("	    AND A.ID_SEQ = B.ID_SEQ                                                                                                \n");
			sb.append("	    AND B.IC_TYPE = 5                                                                                                      \n");
			sb.append("	    AND A.ID_SEQ = C.ID_SEQ                                                                                                \n");
			sb.append("	    AND C.IC_TYPE = 51                                                                                                     \n");
			sb.append("	    GROUP BY C.IC_CODE                                                                                                     \n");
			sb.append("	    ) AA ,                                                                                                                 \n");
			sb.append("	    (                                                                                                                      \n");
			sb.append("	        SELECT                                                                                                             \n");
			sb.append("	        B.IC_CODE                                                                                                          \n");
			sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 5 AND IC_CODE = B.IC_CODE) AS TYPE5                               \n");
			sb.append("	        ,COUNT(B.IC_CODE) AS CNT                                                                                           \n");
			sb.append("         ,SUM(IF(C.IC_CODE = 2 ,1,0)) AS NEG                                                      \n");
			sb.append("	  FROM ISSUE_DATA A, ISSUE_DATA_CODE B ,ISSUE_DATA_CODE C                                                                                    \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                  \n");
			sb.append("	    AND A.ID_SEQ = B.ID_SEQ                                                                                                \n");
			sb.append("	    AND B.IC_TYPE = 5                                                                                                      \n");
			sb.append("     AND A.ID_SEQ = C.ID_SEQ                                                                      \n");
			sb.append("     AND C.IC_TYPE = 9                                                                            \n");
			sb.append("	    GROUP BY B.IC_CODE                                                                                                     \n");
			sb.append("	    ) BB                                                                                                                   \n");
			sb.append("	    WHERE AA.IC_CODE = BB.IC_CODE                                                                                          \n");
			sb.append("	    ORDER BY IC_CODE ASC ,CNT51 DESC                                                                                       \n");
			System.out.println(sb.toString());                                                                                                     
			rs = stmt.executeQuery(sb.toString());
			while( rs.next() ) {
				JSONObject obj = new JSONObject();
				obj.put("type5", rs.getString("TYPE5"));
				obj.put("neg1", rs.getString("NEG1"));
				obj.put("cnt5", rs.getString("CNT5"));
				obj.put("type51", rs.getString("TYPE51"));
				obj.put("neg2", rs.getString("NEG2"));
				obj.put("cnt51", rs.getString("CNT51"));
				result.put(obj);
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	
	public JSONArray getWeeklyInfoVolumn2(String sDate, String eDate){
		JSONArray result = new JSONArray();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("	SELECT                                                                                                                     \n");
			sb.append("	  AA.IC_CODE                                                                                                               \n");
			sb.append("	  ,AA.TYPE3                                                                                                                \n");
			sb.append("	  ,BB.NEG AS NEG1                                                                                                          \n");
			sb.append("	  ,BB.CNT AS CNT3                                                                                                          \n");
			sb.append("	  ,AA.TYPE31                                                                                                               \n");
			sb.append("	  ,AA.NEG AS NEG2                                                                                                          \n");
			sb.append("	  ,AA.CNT AS CNT31                                                                                                         \n");
			sb.append("	FROM                                                                                                                       \n");
			sb.append("	(                                                                                                                          \n");
			sb.append("	  SELECT                                                                                                                   \n");
			sb.append("	        B.IC_CODE                                                                                                          \n");
			sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 3 AND IC_CODE = B.IC_CODE) AS TYPE3                               \n");
			sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 31 AND IC_CODE = C.IC_CODE) AS TYPE31                             \n");
			sb.append("	        ,COUNT(C.IC_CODE) AS CNT                                                                                           \n");
			sb.append("	        ,IFNULL(COUNT(D.IC_CODE),0) AS NEG                                                                                 \n");
			sb.append("	  FROM ISSUE_DATA A LEFT OUTER JOIN ISSUE_DATA_CODE D ON A.ID_SEQ = D.ID_SEQ AND D.IC_TYPE = 9 AND D.IC_CODE = 2           \n");
			sb.append("	  , ISSUE_DATA_CODE B, ISSUE_DATA_CODE C                                                                                   \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                  \n");
			sb.append("	    AND A.ID_SEQ = B.ID_SEQ                                                                                                \n");
			sb.append("	    AND B.IC_TYPE = 3                                                                                                      \n");
			sb.append("	    AND A.ID_SEQ = C.ID_SEQ                                                                                                \n");
			sb.append("	    AND C.IC_TYPE = 31                                                                                                     \n");
			sb.append("	    GROUP BY C.IC_CODE                                                                                                     \n");
			sb.append("	    ) AA ,                                                                                                                 \n");
			sb.append("	    (                                                                                                                      \n");
			sb.append("	        SELECT                                                                                                             \n");
			sb.append("	        B.IC_CODE                                                                                                          \n");
			sb.append("	        ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 3 AND IC_CODE = B.IC_CODE) AS TYPE3                               \n");
			sb.append("	        ,SUM(IF(C.IC_CODE = 2 ,1,0)) AS NEG                                                                                 \n");
			sb.append("	        ,COUNT(B.IC_CODE) AS CNT                                                                                           \n");
			sb.append("	  FROM ISSUE_DATA A, ISSUE_DATA_CODE B,ISSUE_DATA_CODE C                                                                   \n");
			sb.append("	  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                                                  \n");
			sb.append("	    AND A.ID_SEQ = B.ID_SEQ                                                                                                \n");
			sb.append("	    AND B.IC_TYPE = 3                                                                                                      \n");
			sb.append("	    AND A.ID_SEQ = C.ID_SEQ                                                                                                \n");
			sb.append("	    AND C.IC_TYPE = 9                                                                                                      \n");
			sb.append("	    GROUP BY B.IC_CODE                                                                                                     \n");
			sb.append("	    ) BB                                                                                                                   \n");
			sb.append("	    WHERE AA.IC_CODE = BB.IC_CODE                                                                                          \n");
			sb.append("	    ORDER BY IC_CODE ASC ,CNT31 DESC                                                                                       \n");
			System.out.println(sb.toString());                                                                                                     
			rs = stmt.executeQuery(sb.toString());
			while( rs.next() ) {
				JSONObject obj = new JSONObject();
				obj.put("type3", rs.getString("TYPE3"));
				obj.put("neg1", rs.getString("NEG1"));
				obj.put("cnt3", rs.getString("CNT3"));
				obj.put("type31", rs.getString("TYPE31"));
				obj.put("neg2", rs.getString("NEG2"));
				obj.put("cnt31", rs.getString("CNT31"));
				result.put(obj);
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	
	public JSONArray getWeeklyInfluencerTop10(String sDate, String eDate){
		JSONArray result = new JSONArray();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("	SELECT 	                                                                                         \n");
			sb.append("          (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE =6 AND IC_CODE = B.IC_CODE) AS SOURCE     \n");
			sb.append("          ,A.ID_TITLE                                                                             \n");
			sb.append("          ,A.ID_URL                                                                               \n");
			sb.append("          ,COUNT(0) AS CNT                                                                        \n");
			sb.append("   FROM ISSUE_DATA A, ISSUE_DATA_CODE B                                                           \n");
			sb.append("  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'                           \n");
			sb.append("    AND A.ID_SEQ = B.ID_SEQ                                                                       \n");
			sb.append("    AND B.IC_TYPE = 6                                                                             \n");
			sb.append("    AND B.IC_CODE IN (2,3,4,5,9,10)                                                               \n");
			sb.append("    GROUP BY A.MD_PSEQ                                                                            \n");
			sb.append("    ORDER BY CNT DESC LIMIT 10                                                                    \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while( rs.next() ) {
				JSONObject obj = new JSONObject();
				obj.put("source", rs.getString("SOURCE"));
				obj.put("id_title", rs.getString("ID_TITLE"));
				obj.put("id_url", rs.getString("ID_URL"));
				obj.put("cnt", rs.getString("CNT"));
				result.put(obj);
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	public JSONObject getSentiVolume(String sDate , String eDate){
		JSONObject result = new JSONObject();
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("	SELECT 	                                                                    \n");
			sb.append(" 	   SUM(IF(B.IC_CODE=1,1,0)) AS POS 										\n");
			sb.append("       ,SUM(IF(B.IC_CODE=2,1,0)) AS NEG 										\n");
			sb.append("       ,SUM(IF(B.IC_CODE=3,1,0)) AS NEU 										\n");
			sb.append("       ,COUNT(0) AS TOTAL_CNT 												\n");
			sb.append("  FROM ISSUE_DATA A, ISSUE_DATA_CODE B                                       \n");
			sb.append("  WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'      \n");
			sb.append("  	AND A.ID_SEQ = B.ID_SEQ													\n");
			sb.append("  	AND B.IC_TYPE = 9														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if( rs.next() ) {
				result.put("POS", rs.getString("POS"));
				result.put("NEG", rs.getString("NEG"));
				result.put("NEU", rs.getString("NEU"));
				result.put("TOTAL_CNT", rs.getString("TOTAL_CNT"));
			}

			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	
	public JSONObject getOniineVolume(String sDate , String eDate){
		JSONObject result = new JSONObject();		
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sb.append(" ##실국별 온라인 																	\n");
			sb.append(" SELECT  																	\n");
			sb.append("       AA.IC_NAME      														\n");
			sb.append("      ,IFNULL(BB.NEWS,0) AS NEWS 											\n");
			sb.append("      ,IFNULL(BB.INDIVIDUAL,0) AS INDIVIDUAL 								\n");
			sb.append("      ,IFNULL(BB.REPLY_CNT,0) AS REPLY_CNT 									\n");
			sb.append("      ,IFNULL(BB.SNS,0) AS SNS 									\n");
			sb.append(" FROM  																		\n");
			sb.append(" ( 																			\n");
			sb.append(" SELECT 																		\n");
			sb.append("        IC_CODE 																\n");
			sb.append("       ,IC_NAME 																\n");
			sb.append(" FROM ISSUE_CODE 															\n");
			sb.append(" WHERE IC_TYPE = 5 AND IC_CODE > 0 AND IC_USEYN = 'Y' 						\n");
			sb.append(" ) AA LEFT OUTER JOIN 														\n");
			sb.append(" (SELECT  																	\n");
			sb.append("       B.IC_CODE 															\n");
			sb.append("      ,SUM(IF(C.IC_CODE IN (1,7),1,0)) AS NEWS 								\n");
			sb.append("      ,SUM(IF(C.IC_CODE IN (2,3,4,5,9),1,0)) AS INDIVIDUAL 				\n");
			sb.append("      ,SUM(IF(C.IC_CODE IN (10),1,0)) AS SNS 				\n");
			sb.append(" 	 ,IFNULL( 																\n");
			sb.append("        (SELECT 																\n");
			sb.append("               SUM(C.REPLY_CNT) AS REPLY_CNT 								\n");
			sb.append("         FROM ISSUE_DATA A , ISSUE_DATA_CODE B1 , REPLY_CNT C 				\n");
			sb.append("         WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
			sb.append("           AND A.ID_SEQ = B1.ID_SEQ 											\n");
			sb.append("           AND B1.IC_TYPE = 5 												\n");
			sb.append("           AND B1.IC_CODE = B.IC_CODE 										\n");
			sb.append("           AND A.D_SEQ = C.D_SEQ 											\n");
			sb.append("         ),0) AS  REPLY_CNT 													\n");
			sb.append(" FROM ISSUE_DATA A , ISSUE_DATA_CODE B , ISSUE_DATA_CODE C 					\n");
			sb.append(" WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'      	\n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ 													\n");
			sb.append("   AND B.IC_TYPE = 5 														\n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ 													\n");
			sb.append("   AND C.IC_TYPE = 6  														\n");
			sb.append(" GROUP BY B.IC_CODE 															\n");
			sb.append(" ) BB ON AA.IC_CODE = BB.IC_CODE 											\n");
			sb.append(" ORDER BY AA.IC_CODE  														\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			JSONObject obj = null;
			JSONArray arr = new JSONArray();
			
			int news = 0;
			int indi = 0;
			int sns = 0;
			int reply = 0;
		
			while( rs.next() ) {
				obj = new JSONObject();
				obj.put("name", rs.getString("IC_NAME"));
				obj.put("news", rs.getString("NEWS"));
				obj.put("indi", rs.getString("INDIVIDUAL"));
				obj.put("sns", rs.getString("SNS"));
				obj.put("reply", rs.getString("REPLY_CNT"));
				
				news += rs.getInt("NEWS");
				indi += rs.getInt("INDIVIDUAL");
				sns += rs.getInt("SNS");
				reply += rs.getInt("REPLY_CNT");
				//reply += rs.getInt("REPLY");				
				arr.put(obj);
			}
			
			result.put("news",news);
			result.put("indi",indi);
			result.put("sns",sns);
			result.put("reply",reply);
			result.put("list",arr);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}

		return result;
	}
	
	public JSONArray ReplyDataExcel(String sDate , String eDate){
		
		JSONArray result = new JSONArray();
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" ##댓글 상세 엑셀  \n");
			sb.append(" SELECT  																\n");
			sb.append("       B.IC_CODE 														\n");
			sb.append("      ,FN_GET_ISSUE_CODE_NAME(A.ID_SEQ,5) AS IC_NAME						\n");
			sb.append("      ,A.ID_TITLE 														\n");
			sb.append("      ,A.ID_URL 															\n");
			sb.append("      ,C.REPLY_CNT 														\n");
			sb.append(" FROM ISSUE_DATA A, ISSUE_DATA_CODE B , REPLY_CNT C 						\n");
			sb.append(" WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
			sb.append("   AND A.ID_SEQ = B.ID_SEQ 												\n");
			sb.append("   AND B.IC_TYPE = 5 													\n");
			sb.append("   AND A.D_SEQ = C.D_SEQ 												\n");
			sb.append(" ORDER BY B.IC_CODE ASC 													\n");
						
		    System.out.println(sb.toString());		                                                        
		    rs = stmt.executeQuery(sb.toString());
		    
		    JSONObject obj = null;
		    while(rs.next()){
		    	obj = new JSONObject();		    	
		    	obj.put("name",rs.getString("IC_NAME"));
		    	obj.put("title",rs.getString("ID_TITLE"));
		    	obj.put("url",rs.getString("ID_URL"));
		    	obj.put("cnt",rs.getString("REPLY_CNT"));
		    	result.put(obj);
		    }			
			
		}catch(SQLException ex) {
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
	
	public String ReplyDataStart(String sDate , String eDate){
		
		String result = "";
		
		DateUtil du = new DateUtil();
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			boolean date_chk = true;
			
			while(date_chk){
				
				boolean chk = true;
				int startNumber = 0;
				String doc_id = "";
				
				while(chk){
					
					doc_id = ReplyDataCollect(startNumber,sDate,sDate);
					
					if(!"".equals(doc_id)){
						ArrayList arr = null;
						arr = getPortalReplyList(doc_id,sDate);    			
						
						if(arr.size() > 0){
							updateReplyCnt(arr);
						}
						
					}else{
						chk = false;
						result = "success";
					}
					startNumber = startNumber + 20;
				}				
				
				if(sDate.equals(eDate)){
					date_chk = false;
				}else{
					sDate = du.addDay_v2(sDate, 1);
				}
				
			}
			
			
		}catch(SQLException ex) {
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
	
	public String ReplyDataCollect(int num,String sDate , String eDate){
		
		String result = "";
		
		try{
			
			sb = new StringBuffer();
			sb.append(" SELECT  																	\n");
			sb.append("	      B.D_SEQ       		                             					\n");
			sb.append("	     ,DATE_FORMAT(A.MD_DATE,'%Y-%m-%d') AS MD_DATE                			\n");
			sb.append("	FROM ISSUE_DATA A , REPLY_CNT B 											\n");
			sb.append(" WHERE A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'       \n");
			sb.append("	  AND A.D_SEQ = B.D_SEQ   													\n");
			sb.append("	  AND A.S_SEQ IN (2196,2199)												\n");
			sb.append("	ORDER BY MD_DATE ASC														\n");
			sb.append(" LIMIT "+num+", 20                                                           \n");
						
		    System.out.println(sb.toString());		                                                        
		    rs = stmt.executeQuery(sb.toString());
		    while(rs.next()){
		    	if("".equals(result)){
		    		result = rs.getString("D_SEQ");
		    	}else{
		    		result += ","+rs.getString("D_SEQ");	
		    	}
		    }
    		
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			/*if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}*/
		}
		return result;
	}
	
	public void updateReplyCnt(ArrayList list){
		
		
		try {
			
			
			Map map = null;
			for(int i=0; i<list.size(); i++){
				map = new HashMap();
				map = (HashMap)list.get(i);
				
				sb = new StringBuffer();
				sb.append(" UPDATE REPLY_CNT							\n"); 
				sb.append(" SET REPLY_CNT = "+map.get("docCnt")+"		\n");
				sb.append(" WHERE D_SEQ = "+map.get("p_docid")+"        \n");
			    System.out.println(sb.toString());		
			    stmt.executeUpdate(sb.toString());
			    
			    Thread.sleep(500);
			}
			                                                                                    
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			/*if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}*/
		    //if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		//return result;
	}
	
	public ArrayList getPortalReplyList(String p_docid , String p_date){
		ArrayList result = new ArrayList();
		
		String url = "http://lucyapi1.realsn.com/API?cmd=replySortCnt&systemkey=54&userid=system4"; 
		String params = "";
		
		if(!"".equals(p_docid)){
			params += "&p_docid="+p_docid;
		}
		if(!"".equals(p_date)){
			params += "&p_date="+p_date.replaceAll("-", "");
		}
		
		System.out.println(url+params);
		
		try{
			JSONObject root = rootReturn(url, params);
			
			if( !root.isNull("docs")){
				JSONArray jsonArray = root.getJSONArray("docs");
				if(jsonArray.length() > 0){
					JSONObject jsonObject = null;
					LinkedHashMap map = null;
					for(int i=0; i < jsonArray.length(); i++){
						map = new LinkedHashMap();
						jsonObject = (JSONObject)jsonArray.get(i);
						map.put("docCnt", jsonObject.get("docCnt"));
						map.put("p_docid", jsonObject.get("p_docid"));
						
						result.add(map);
					}
				}
			}
			
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			//try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		
		return result;
	}
	
	//Element root를 리턴해준다
		public JSONObject rootReturn(String queryUrl, String q)throws IOException ,JDOMException, JSONException{
				
					Element root=null;
					URL url = new URL(queryUrl);
					URLConnection conn;
					conn = url.openConnection();
					((HttpURLConnection)conn).setRequestMethod("POST");
					conn.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				    wr.writeBytes(q);
				    //wr.write(q);
				    wr.flush();
					
				    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				    StringBuffer response = new StringBuffer();
				    
				    String lines = "";
				    while( (lines=rd.readLine()) != null){
				    	response.append(lines);
				    }
				    JSONObject jsonObj = new JSONObject(response.toString());
				    System.out.println("===========================   JSON convert =======================");
				    System.out.println(jsonObj.toString());
				    System.out.println("===========================   JSON convert ========================");
					return jsonObj;
			}
	
}
