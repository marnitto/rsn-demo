package risk.JfreeChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class GetChartData {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null; 
	private ResultSet rs    = null;
	private StringBuffer sb = null;
	private DateUtil du = null;
	
	/**
	 * 기간/unit에 대하여 x축 데이터를 생성
	 * @param psSdate "yyyyMMdd"
	 * @param psEdate "yyyyMMdd"
	 * @param piUnit 1:day, 2:week, 3:month
	 */
	public ArrayList getXvalDate(String psSdate, String psEdate, String psUnit, String dateFormat)  {
		
		ArrayList result = new ArrayList();
		boolean isDate = true;
		
		du = new DateUtil();
		du.setDate(psSdate);
		
		try{
			while(isDate) {
				
				if (psUnit.equals("d")) {
					result.add(du.getDate("yyyyMMdd").toString());
					du.addDay(1);
				}
				else if (psUnit.equals("w")) {
					result.add(du.getDate("yyyyww").toString());
					du.addWeek(1);
				}
				else if (psUnit.equals("m")) {
					result.add(du.getDate("yyyyMM").toString());
					du.addMonth(1);
				}

				if (du.DateDiff(du.getDate("yyyyMMdd"), psEdate)>0) {
				
					isDate = false;
					
					if (psUnit.equals("w") && du.DateDiff(du.getDate("yyyyMMdd"), psEdate) < 6 ) {
						result.add(du.getDate("yyyyww").toString());
					}
					else if (psUnit.equals("m") && (du.getDate("MM").equals(psEdate.substring(4,6)) )  ) {
						result.add(du.getDate("yyyyMM").toString());
					}
				}
			}				
		} catch(Exception ex) {
			//System.out.println(ex.getMessage());
		}
		return result;
	}

	public ArrayList getSeries( int ic_type )
    {
    	ArrayList result = new ArrayList();
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sb.append("SELECT ic_type, ic_code, ic_name  FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_CODE > 0	\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			
			while( rs.next() )
			{
				result.add( new ChartBean(
						rs.getString("ic_name")
						,rs.getInt("ic_type")
						,rs.getInt("ic_code")
						)
				);
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
	
	public ArrayList getDateStatic( String sDate, String eDate, int ic_type, String chartUnit, ArrayList arrCode , String typecode )
    {
		ArrayList result = new ArrayList();
		ArrayList arrTemp = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
						
			String unit = "DATE_FORMAT(A.MD_DATE,'%Y%m%d')";
			if( chartUnit.equals("w") ) unit = "DATE_FORMAT(A.MD_DATE,'%Y%U') -1";
			else if( chartUnit.equals("m") ) unit = "DATE_FORMAT(A.MD_DATE,'%Y%m')";
			else unit = "DATE_FORMAT(A.MD_DATE,'%Y%m%d')";
			
			if(chartUnit.equals("t")){
				sb.append("select LEFT(A.MD_DATE||ID.ID_TIME,10) xVal, B.IC_CODE, COUNT(0) as cnt	\n");
				}else{
				sb.append(" SELECT "+unit+" xVal, B.IC_CODE, COUNT(0) as cnt                 	\n");
				}
				
				
				sb.append("	FROM ISSUE_DATA A, ISSUE_DATA_CODE B                              \n");
				sb.append("	WHERE A.ID_SEQ = B.ID_SEQ                                         \n");
				//sb.append("	        AND B.ID_SEQ IN (SELECT ID_SEQ                            \n");
				//sb.append("	        FROM ISSUE_DATA_CODE                                      \n");
				//sb.append("	        GROUP BY ID_SEQ )                                         \n");
				sb.append("	        AND DATE_FORMAT(A.MD_DATE,'%Y%m%d') BETWEEN '"+sDate.replaceAll("-","")+"' AND '"+eDate.replaceAll("-","")+"' 		\n");
				if(ic_type>0){
					sb.append("AND B.IC_TYPE = "+ic_type+"								\n");
				}
				if(chartUnit.equals("t")){
				sb.append("GROUP BY LEFT(A.MD_DATE+A.ID_TIME,10), B.IC_CODE						\n");
				}else{
					sb.append("GROUP BY "+unit+", B.IC_CODE						\n");
				}
				sb.append("ORDER BY xVal DESC										\n");
							
				System.out.println("getDateStatic \n "+sb.toString());
				rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				result.add( new ChartBean(
						rs.getString("xVal")
						,rs.getInt("IC_CODE")
						,rs.getInt("cnt")
						)
				);
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
	
	// 수집 날짜 별로 데이터 가져오기
	public ArrayList getDateStatic2( String sDate, String eDate, int ic_type, String chartUnit, ArrayList arrCode )
    {
		ArrayList result = new ArrayList();
		ArrayList arrTemp = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			
			
			
			String unit = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD')";
			if( chartUnit.equals("w") ) unit = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYWW')";
			else if( chartUnit.equals("m") ) unit = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMM')";
			else unit = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD')";
			
			//System.out.println("chartUnit: "+chartUnit);
			
			sb.append("select "+unit+" xVal, IDC.IC_CODE, COUNT(0) as cnt	\n");
			sb.append("FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC					\n");
			sb.append("WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");
			sb.append("		AND IDC.ID_SEQ IN (SELECT ID_SEQ  \n");
			sb.append("		FROM ISSUE_DATA_CODE  \n");
			if( arrCode != null ) {
				if(arrCode.size()>1){
					for( int i=0 ; i<arrCode.size(); i++ )
					{
						arrTemp = (ArrayList)arrCode.get(i);
						
						if( i == 0 )
							sb.append("		WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
						else
							sb.append("		OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					}
				}else{
					if( arrCode != null && arrCode.size() > 0 ) {
						arrTemp = (ArrayList)arrCode.get(0);
						if (!arrTemp.get(0).equals("0")) {
							sb.append("		WHERE (IC_TYPE="+(String)arrTemp.get(0)+" ) \n");
						}
					}
				}
			}
			sb.append("		GROUP BY ID_SEQ )  \n");
			sb.append("AND TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD') BETWEEN '"+sDate+"' AND '"+eDate+"' 		\n");
			if(ic_type>0){
				sb.append("AND IDC.IC_TYPE = "+ic_type+"								\n");
			}
			
			sb.append("GROUP BY "+unit+", IDC.IC_CODE						\n");

			System.out.println("getDateStatic \n "+sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{

				result.add( new ChartBean(
						rs.getString("xVal")
						,rs.getInt("IC_CODE")
						,rs.getInt("cnt")
						)
				);
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

	public ArrayList getDateStaticCnt( String sDate, String eDate, int ic_type, String chartUnit, ArrayList arrCode )
    {
		ArrayList result = new ArrayList();
		ArrayList arrTemp = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();		
			
			
			String unit = "MD_DATE";
			if( chartUnit.equals("w") ) unit = "ID_WEEK";
			else if( chartUnit.equals("m") ) unit = "ID_MONTH";
			else unit = "MD_DATE";
			
			System.out.println("chartUnit: "+chartUnit);
			
			
			sb.append(" SELECT IC_NAME, IC_TYPE, IC_CODE FROM ISSUE_CODE WHERE  IC_TYPE="+ic_type+" AND IC_CODE IN	\n");
			sb.append(" (	\n");
			sb.append(" SELECT CC.IC_CODE	\n");
			sb.append(" FROM(	\n");
			sb.append("SELECT TOP 5 '2008' as xVal, AA.IC_CODE, SUM(CNT) AS CNT FROM(	\n");
			sb.append("select ID."+unit+" xVal, IDC.IC_CODE, COUNT(0) as cnt	\n");
			sb.append("FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC					\n");
			sb.append("WHERE ID.ID_SEQ = IDC.ID_SEQ								\n");
			sb.append("		AND IDC.ID_SEQ IN (SELECT ID_SEQ  \n");
			sb.append("		FROM ISSUE_DATA_CODE  \n");
			
			if(arrCode.size()>1){
				for( int i=0 ; i<arrCode.size(); i++ )
				{
					arrTemp = (ArrayList)arrCode.get(i);
					
					if( i == 0 )
						sb.append("		WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					else
						sb.append("		OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
				}
			}else{
				arrTemp = (ArrayList)arrCode.get(0);
				sb.append("		WHERE (IC_TYPE="+(String)arrTemp.get(0)+" ) \n");
			}
			sb.append("		GROUP BY ID_SEQ )  \n");
			sb.append("AND ID.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' 		\n");
			sb.append("AND IDC.IC_TYPE = "+ic_type+"								\n");
			sb.append("GROUP BY ID."+unit+", IDC.IC_CODE						\n");
			sb.append(") AA						\n");
			sb.append("GROUP BY AA.IC_CODE						\n");
			sb.append("ORDER BY CNT DESC						\n");
			sb.append(")CC						\n");
			sb.append(")						\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{

				result.add( new ChartBean(
						rs.getString("IC_NAME")
						,rs.getInt("IC_TYPE")
						,rs.getInt("IC_CODE")
						)
				);
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
	//통계분석>>이슈분류>> 차트 데이터
	public ArrayList getTimeStatic( String sDate, String eDate, int ic_type, String chartUnit )
    {
		ArrayList result = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();		
						
			if(chartUnit.equals("w")){
				sb.append("	SELECT DATE_FORMAT(ID.MD_DATE,'%Y%U') xVal ,IDC.IC_CODE, COUNT(0) as CNT , IC.IC_NAME	\n");
				sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_CODE IC	\n");
				sb.append("	WHERE ID.ID_SEQ = IDC.ID_SEQ	\n");
				//sb.append("	AND IDC.ID_SEQ IN (SELECT ID_SEQ	\n");
				//sb.append("	FROM ISSUE_DATA_CODE	\n");
				//sb.append("	GROUP BY ID_SEQ )	\n");
				sb.append("	AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
				sb.append("	AND IDC.IC_TYPE = "+ic_type+"	\n");
				sb.append("	AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");
				sb.append("	GROUP BY IC.IC_NAME, xVal, IDC.IC_CODE	\n");
				sb.append("	ORDER BY IDC.IC_CODE, xVal	\n");
				
				System.out.println(sb.toString());
				rs = stmt.executeQuery( sb.toString() );
				
					while( rs.next() )
					{
						result.add( new ChartBean(
								rs.getString("IC_CODE"),
								rs.getString("IC_NAME"),
								rs.getString("xVal"),
								rs.getString("CNT")
								)
						);
					}
			}else if(chartUnit.equals("m")){
				sb.append("	SELECT DATE_FORMAT(ID.MD_DATE,'%Y%m') xVal ,IDC.IC_CODE, COUNT(0) as CNT , IC.IC_NAME	\n");
				sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_CODE IC	\n");
				sb.append("	WHERE ID.ID_SEQ = IDC.ID_SEQ	\n");
				//sb.append("	AND IDC.ID_SEQ IN (SELECT ID_SEQ	\n");
				//sb.append("	FROM ISSUE_DATA_CODE	\n");
				//sb.append("	GROUP BY ID_SEQ )	\n");
				sb.append("	AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
				sb.append("	AND IDC.IC_TYPE = "+ic_type+"	\n");
				sb.append("	AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");
				sb.append("	GROUP BY IC.IC_NAME, substr(ID.MD_DATE,0,6), IDC.IC_CODE	\n");
				sb.append("	ORDER BY IDC.IC_CODE, xVal	\n");
				
				System.out.println(sb.toString());
				rs = stmt.executeQuery( sb.toString() );
				
					while( rs.next() )
					{
						result.add( new ChartBean(
								rs.getString("IC_CODE"),
								rs.getString("IC_NAME"),
								rs.getString("xVal"),
								rs.getString("CNT")
								)
						);
					}
			}else if(chartUnit.equals("d")){
				sb.append("	SELECT DATE_FORMAT(ID.MD_DATE,'%Y%m%d') xVal ,IDC.IC_CODE, COUNT(0) as CNT , IC.IC_NAME	\n");
				sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_CODE IC	\n");
				sb.append("	WHERE ID.ID_SEQ = IDC.ID_SEQ	\n");
				//sb.append("	AND IDC.ID_SEQ IN (SELECT ID_SEQ	\n");
				//sb.append("	FROM ISSUE_DATA_CODE	\n");
				//sb.append("	GROUP BY ID_SEQ )	\n");
				sb.append("	AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
				sb.append("	AND IDC.IC_TYPE = "+ic_type+"	\n");
				sb.append("	AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");
				sb.append("	GROUP BY IC.IC_NAME, ID.MD_DATE, IDC.IC_CODE	\n");
				sb.append("	ORDER BY IDC.IC_CODE, xVal	\n");
				
				System.out.println(sb.toString());
				rs = stmt.executeQuery( sb.toString() );
				
					while( rs.next() )
					{
						result.add( new ChartBean(
								rs.getString("IC_CODE"),
								rs.getString("IC_NAME"),
								rs.getString("xVal"),
								rs.getString("CNT")
								)
						);
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getKeywordStatic( String minNo, String maxNo, String k_xps )
    {
		ArrayList result = new ArrayList();
				
		try {
			
        	dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();							
			
			sb = new StringBuffer();
			sb.append(" SELECT A.K_XP, B.MD_DATE, A.K_VALUE, IFNULL(B.CNT,0) CNT                                                                                                \n");
			sb.append(" FROM                                                                                                                          \n");
			sb.append("   (                                                                                                                           \n");
			sb.append("     SELECT K_XP, K_VALUE FROM KEYWORD WHERE K_YP = 0                                                                          \n");
			sb.append("   ) A LEFT OUTER JOIN                                                                                                         \n");
			sb.append("   (                                                                                                                           \n");
			sb.append("     SELECT K_XP , DATE_FORMAT(MD_DATE,'%Y%m%d') MD_DATE, COUNT(MD_PSEQ) CNT                                                                                          \n");
			sb.append("     FROM                                                                                                                      \n");
			sb.append(" 	META M, (SELECT MD_SEQ, K_XP FROM IDX WHERE MD_SEQ BETWEEN "+minNo+" AND "+maxNo+" GROUP BY MD_SEQ, K_XP) I           \n");
			sb.append("     WHERE                                                                                                                     \n");
			sb.append(" 	M.MD_SEQ = I.MD_SEQ                                                                                                   \n");
			sb.append("     GROUP BY DATE_FORMAT(MD_DATE,'%Y%m%d'), K_XP                                                                                                            \n");
			sb.append("    )B ON A.K_XP = B.K_XP                                                                                                      \n");
			System.out.println(sb.toString());			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				result.add( new ChartBean(
						rs.getString("K_XP"),
						rs.getString("K_VALUE"),
						rs.getString("MD_DATE"),
						rs.getString("CNT")
						)
				);
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getKeywordStaticTwitter( String sDate, String eDate, String k_xps )
	{
		ArrayList result = new ArrayList();
		String sID_SEQ = "";
		String eID_SEQ = "";
		String s_Date = "";
		String e_Date = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("SELECT B2.K_XP, B2.KA_DATE, B2.CT, B1.K_VALUE      	\n");                                                                                                      
			sb.append("FROM (      											\n"); 
			sb.append("        SELECT * FROM KEYWORD WHERE K_YP=0      	\n");
			sb.append("     ) B1,      										\n");                                                                                                                                 
			sb.append("     (      												\n");                                                                                                                                                                        
			sb.append("         SELECT K_XP, SUM(TK_CNT) AS CT, TK_DATE AS KA_DATE      	\n");        
			sb.append("         FROM TWITTER_KEYWORD_CNT      	\n");                                                                                                                                                                                                                                                                       
			sb.append("         WHERE TK_DATE BETWEEN '"+sDate.replaceAll("-","")+"' AND '"+eDate.replaceAll("-","")+"'      	\n");                               
			sb.append("         GROUP BY K_XP,TK_DATE      	\n");                                                                                                                                                
			sb.append("     ) B2      	\n");                                                                                                                                                                     
			sb.append("WHERE B1.K_TYPE=1 AND B1.K_XP = B2.K_XP      	\n");                                                                                                                                       
			sb.append("ORDER BY B2.K_XP,B2.KA_DATE      	\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				result.add( new ChartBean(
						rs.getString("K_XP"),
						rs.getString("K_VALUE"),
						rs.getString("KA_DATE"),
						rs.getString("CT")
				)
				);
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getDateStaticTwitter( String sDate, String eDate )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("         SELECT SUM(TK_CNT) AS CT, TK_DATE      	\n");        
			sb.append("         FROM TWITTER_KEYWORD_CNT      	\n");                                                                                                                                                                                                                                                                       
			sb.append("         WHERE TK_DATE BETWEEN '"+sDate.replaceAll("-","")+"' AND '"+eDate.replaceAll("-","")+"'      	\n");                               
			sb.append("         GROUP BY TK_DATE      	\n");                                                                                                                                                
			sb.append("         ORDER BY TK_DATE      	\n");                                                                                                                                                
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			ChartBean cInfo = null;
			while( rs.next() )
			{
				cInfo = new ChartBean();
				
				cInfo.setData_date(rs.getString("TK_DATE"));
				cInfo.setData_count(rs.getString("CT"));
				cInfo.setData_name("");
				result.add(cInfo);
				
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getDateStaticIssue( String sDate, String eDate,int sTime, int eTime, String issueSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			if(issueSeq.length()>0){
				sb.append("         SELECT SUM(ID_SAME_CT+1) AS CT, TO_CHAR(ID_REGDATETIME,'YYYYMMDD') AS TK_DATE      	\n");        
				sb.append("         FROM ISSUE_DATA ID, ISSUE_TITLE IT      	\n");                                                                                                                                                                                                                                                                       
				sb.append("         WHERE ID_REGDATETIME BETWEEN TO_DATE('"+sDate+" "+sTime+":00:00','YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"+eDate+" "+eTime+":59:59','YYYY-MM-DD HH24:MI:SS')      	\n");                               
				sb.append("         AND ID.IT_SEQ = IT.IT_SEQ AND IT.I_SEQ IN ("+issueSeq+")      	\n");                               
				//트위터 제외 요청 2010.11.05
				sb.append("   	   AND ID.ID_SITECODE <> 10464                                                                                                                          	\n");
				
				sb.append("         GROUP BY TO_CHAR(ID_REGDATETIME,'YYYYMMDD')      	\n");                                                                                                                                                
				sb.append("         ORDER BY TK_DATE      	\n");
			}else{
				sb.append("         SELECT SUM(ID_SAME_CT+1) AS CT, TO_CHAR(ID_REGDATETIME,'YYYYMMDD') AS TK_DATE      	\n");        
				sb.append("         FROM ISSUE_DATA      	\n");                                                                                                                                                                                                                                                                       
				sb.append("         WHERE ID_REGDATETIME BETWEEN TO_DATE('"+sDate+" "+sTime+":00:00','YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"+eDate+" "+eTime+":59:59','YYYY-MM-DD HH24:MI:SS')      	\n");
				//트위터 제외 요청 2010.11.05
				sb.append("   	   AND ID_SITECODE <> 10464                                                                                                                          	\n");
				
				sb.append("         GROUP BY TO_CHAR(ID_REGDATETIME,'YYYYMMDD')      	\n");                                                                                                                                                
				sb.append("         ORDER BY TK_DATE      	\n");
			}
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			ChartBean cInfo = null;
			while( rs.next() )
			{
				cInfo = new ChartBean();
				
				cInfo.setData_date(rs.getString("TK_DATE"));
				cInfo.setData_count(rs.getString("CT"));
				cInfo.setData_name("");
				result.add(cInfo);
				
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getIssueStaticTwitter( String sDate, String eDate, String icType )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			

			// 일별 트위터 긍부정 통계
			
			//긍정 부정중 데이터만 출력
			/*
			sb.append("SELECT DATE_FORMAT(ID.MD_DATE,'%Y%m%d') AS MD_DATE                                \n");
			sb.append("     , IDC.IC_TYPE                                                                \n");
			sb.append(" 	, IDC.IC_CODE                                                                \n");
			sb.append("		, COUNT(0) AS CNT                                                            \n");
			sb.append("		, IC.IC_NAME                                                                 \n");
			sb.append("		, COUNT(0) AS SUMCNT                                                         \n");      	
			sb.append("	 FROM ISSUE_DATA ID                                                              \n");
			sb.append("	    , ISSUE_DATA_CODE IDC                                                        \n");
			sb.append("		, ISSUE_CODE IC                                                              \n");	
			sb.append("	WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:23:59'              \n");      	
			sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ      	                                             \n");
			sb.append("	  AND ID.S_SEQ = 10464     	                                                     \n");
			sb.append("	  AND IDC.IC_TYPE = "+icType+"     	                                             \n");
			sb.append("	  AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE                      \n");     	
			sb.append("	GROUP BY DATE_FORMAT(ID.MD_DATE,'%Y%m%d'), IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME  \n");     	
			sb.append("	ORDER BY IC_CODE, MD_DATE     	                                                 \n");
			*/
			
			sb.append("SELECT E.MD_DATE                                                                         \n");
			sb.append("     , E.IC_CODE_2 AS IC_CODE                                                            \n");
			sb.append("		, E.IC_NAME                                                                         \n");
			sb.append("		, SUM(IF(E.IC_CODE_1 = E.IC_CODE_2, E.CNT, 0)) AS SUMCNT                            \n");
			sb.append("  FROM 	                                                                                \n");
			sb.append("		  (SELECT *                                                                         \n");
			sb.append("			 FROM                                                                           \n");
			sb.append("				  (SELECT DATE_FORMAT(A.MD_DATE, '%Y%m%d') AS MD_DATE                       \n");  
			sb.append("					    , B.IC_CODE AS IC_CODE_1                                            \n");
			sb.append("					    , COUNT(IC_CODE) AS CNT                                             \n");
			sb.append("				     FROM ISSUE_DATA      A                                                 \n");
			sb.append("					    , ISSUE_DATA_CODE B                                                 \n");
			sb.append("			        WHERE A.ID_SEQ = B.ID_SEQ                                               \n");
			sb.append("				      AND A.S_SEQ = '10464'                                                 \n");
			sb.append("				      AND A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:23:59'   \n");      
			sb.append("				      AND B.IC_TYPE = "+icType+"                                            \n");
			sb.append("				      AND B.IC_CODE NOT IN(0)                                               \n");
			sb.append("				    GROUP BY DATE_FORMAT(A.MD_DATE, '%Y%m%d'), B.IC_CODE)C                  \n");
			sb.append("				  ,                                                                         \n");
			sb.append("				  (SELECT IC_NAME                                                           \n"); 
			sb.append("					    , IC_CODE AS IC_CODE_2                                              \n"); 
			sb.append("					 FROM ISSUE_CODE                                                        \n");
			sb.append("					WHERE IC_TYPE = "+icType+"                                              \n");
			sb.append("				      AND IC_CODE NOT IN(0))D)E                                             \n");
			sb.append(" GROUP BY E.MD_DATE, E.IC_CODE_2, E.IC_NAME                                              \n");
			sb.append(" ORDER BY IC_CODE, E.MD_DATE                                                             \n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			while( rs.next() )
			{
					
				result.add( new ChartBean(
							rs.getString("IC_CODE"),
							rs.getString("IC_NAME"),
							rs.getString("MD_DATE"),
							rs.getString("SUMCNT")
					)
					);
				
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getMobileChart( String sDate, String eDate, String icType )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			
			sb.append("    SELECT DATE_FORMAT(ID.MD_DATE, '%Y%m%d') AS ID_DATE, IDC.IC_TYPE, IDC.IC_CODE, COUNT(0) AS CNT, IC.IC_NAME       	\n");
			sb.append("    FROM ISSUE_DATA ID, ISSUE_CODE IC, ISSUE_DATA_CODE IDC                                                                                                	\n");
			sb.append("    WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00' AND '"+eDate+" 23:59'                                                                                     	\n");
			sb.append("    AND ID.ID_USEYN = 'Y'                                                                                                                           	\n");
			sb.append("    AND ID.ID_SEQ = IDC.ID_SEQ      	                                                                                                                                             	\n");
			sb.append("    AND IDC.IC_TYPE = "+icType+"     	                                                                                                                                                     	\n");
			sb.append("    AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE     	                                                                                     	\n");
			sb.append("    GROUP BY DATE_FORMAT(ID.MD_DATE, '%Y%m%d'), IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME     	                                             	\n");
			sb.append("    ORDER BY IC_CODE, ID_DATE                                                                                                                                                  	\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			while( rs.next() )
			{
				
				result.add( new ChartBean(
						rs.getString("IC_CODE"),
						rs.getString("IC_NAME"),
						rs.getString("ID_DATE"),
						rs.getString("CNT")
				)
				);
				
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
	
	//통계분석>>키워드 분류>> 사용자 통계
	public ArrayList getUserStaticTwitter( String sDate, String eDate, String kxp , String kyp , String kzp)
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			
			if(kxp.equals("")){
				sb.append("SELECT SD_ID                                           \n");
				sb.append("	    , TT_CNT                                          \n"); 	
				sb.append("  FROM TWITTER_CNT                                     \n");	
				sb.append(" WHERE TT_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' \n");
				sb.append("                   AND '"+eDate.replaceAll("-", "")+"' \n");
				sb.append(" ORDER BY TT_CNT DESC                                  \n");
				sb.append("	LIMIT 0, 12                                           \n");
			}else{
				
				sb.append("SELECT SUBSTRING(B.MD_TITLE, 1, INSTR(B.MD_TITLE , ':') -1) AS SD_ID                                                                                  \n");
				sb.append("     , COUNT(*) AS TT_CNT                                                                                                                             \n");
				sb.append("  FROM IDX  A                                                                                                                                         \n");                                             
				sb.append("	    , META B                                                                                                                                         \n");                                        
				sb.append(" WHERE A.MD_SEQ = B.MD_SEQ                                                                                                                            \n");                                   
				sb.append("   AND A.MD_SEQ BETWEEN (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1)    \n"); 
				sb.append("	                   AND (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1)   \n");
				sb.append("	  AND A.K_XP = "+kxp+"                                                                                                                               \n");
				if(!kyp.equals("0")){
					sb.append("	  AND A.K_YP = "+kyp+"                                                                                                                           \n");
				}
				if(!kzp.equals("0")){
					sb.append("	  AND A.K_ZP = "+kzp+"                                                                                                                           \n");
				}
				sb.append("	  AND B.S_SEQ  = 10464                                                                                                                               \n");
				sb.append("	  AND A.I_STATUS = 'N'                                                                                                                               \n");
				sb.append("	GROUP BY SD_ID                                                                                                                                       \n");
				sb.append("	ORDER BY TT_CNT DESC                                                                                                                                 \n");
				sb.append("	LIMIT 12                                                                                                                                             \n");
			}
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			ChartInfo cInfo = null;
			
			while( rs.next() )
			{
				cInfo = new ChartInfo();
				cInfo.setName1(rs.getString("SD_ID"));
				//cInfo.setDate1(rs.getString("TT_DATE"));
				cInfo.setCount1(rs.getInt("TT_CNT"));
				
				result.add(cInfo);	
			}
			
			if(result.size() < 12){
				int cnt = result.size();
				for(int i = 0; i < 12 - cnt; i++){
					cInfo = new ChartInfo(); 
					cInfo.setName1("없음");
					cInfo.setCount1(0);
					
					result.add(cInfo);	
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
	
	
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getDateStaticTwitterIssue( String sDate, String eDate, String iSeq, String itSeq )
	{
		
		
		
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			if(itSeq.length()>0){
			
				sb.append("SELECT DATE_FORMAT(ID.MD_DATE,'%Y%m%d') AS MD_DATE                     \n");
				sb.append("     , COUNT(*)                         AS CNT                         \n");
				sb.append("	    , IT.IT_TITLE                      AS TITLE                       \n");             	
				sb.append("  FROM ISSUE_DATA ID                                                   \n");
				sb.append("     , ISSUE_TITLE IT                                                  \n");           	
				sb.append(" WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:23:59'   \n"); 
				sb.append("   AND ID.S_SEQ = 10464            	            	                  \n");
				sb.append("   AND IT.IT_SEQ = "+itSeq+"             	                      \n");
				sb.append("   AND ID.IT_SEQ = IT.IT_SEQ                                           \n");	
				sb.append(" GROUP BY DATE_FORMAT(ID.MD_DATE,'%Y%m%d'), IT.IT_TITLE                \n");             	
				sb.append(" ORDER BY MD_DATE ASC                                                  \n");
				
			}else if(iSeq.length()>0){
					
				if(iSeq.equals("0")){
					sb.append("SELECT DATE_FORMAT(ID.MD_DATE,'%Y%m%d') AS MD_DATE                     \n");
					sb.append("     , COUNT(*)                         AS CNT                         \n");
					sb.append("	    , '전체'                        AS TITLE                          \n");
					sb.append("  FROM ISSUE_DATA ID                                                   \n");
					sb.append("	    , ISSUE_TITLE IT                                                  \n");
					sb.append("	    , ISSUE I                                                         \n");
					sb.append(" WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:23:59'   \n");
					sb.append("   AND ID.S_SEQ = 10464                          	                  \n");
					sb.append("   AND ID.IT_SEQ = IT.IT_SEQ                                           \n");   
					sb.append("   AND IT.I_SEQ = I.I_SEQ                                              \n");
					sb.append(" GROUP BY DATE_FORMAT(ID.MD_DATE,'%Y%m%d')                             \n");
					sb.append(" ORDER BY MD_DATE ASC 												  \n");       
				}else{
					sb.append("SELECT DATE_FORMAT(ID.MD_DATE,'%Y%m%d') AS MD_DATE                     \n");
					sb.append("     , COUNT(*)                         AS CNT                         \n");
					sb.append("	    , I.I_TITLE                        AS TITLE                       \n");      
					sb.append("  FROM ISSUE_DATA ID                                                   \n");
					sb.append("	    , ISSUE_TITLE IT                                                  \n");
					sb.append("	    , ISSUE I                                                         \n");                                                          
					sb.append(" WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:23:59'   \n");                                          
					sb.append("   AND ID.S_SEQ = 10464                          	                  \n");
					sb.append("   AND I.I_SEQ = "+iSeq+"                                              \n");
					sb.append("   AND ID.IT_SEQ = IT.IT_SEQ                                           \n");                               
					sb.append("   AND IT.I_SEQ = I.I_SEQ                                              \n");                          
					sb.append(" GROUP BY DATE_FORMAT(ID.MD_DATE,'%Y%m%d'), I.I_TITLE                  \n");             
					sb.append(" ORDER BY MD_DATE ASC                                                  \n");
				}
				
			}
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			ChartBean cInfo = null;
			while( rs.next() )
			{
				cInfo = new ChartBean();
				
				cInfo.setData_date(rs.getString("MD_DATE"));
				cInfo.setData_count(rs.getString("CNT"));
				cInfo.setData_name(rs.getString("TITLE"));
				result.add(cInfo);
				
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getDateStaticIssue( String sDate, String eDate, String iSeq, String itSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			if(itSeq.length()>0){
				
				sb.append("  SELECT TO_CHAR(ID.ID_REGDATETIME, 'YYYYMMDD') AS ID_REGDATETIME, SUM(ID_SAME_CT) AS CNT, IT.IT_TITLE AS TITLE             	\n");
				sb.append("  FROM ISSUE_DATA ID, ISSUE_TITLE IT             	\n");
				sb.append("  WHERE ID.ID_SITECODE = 10464            	\n");
				sb.append("  AND ID.ID_REGDATETIME >= TO_DATE('"+sDate+" 00:00','YYYY-MM-DD HH24:MI')             	\n");
				sb.append("  AND ID.ID_REGDATETIME <= TO_DATE('"+eDate+" 23:59','YYYY-MM-DD HH24:MI')             	\n");
				sb.append("  AND IT.IT_SEQ = "+itSeq+"             	\n");
				sb.append("  AND ID.IT_SEQ = IT.IT_SEQ             	\n");
				sb.append("  GROUP BY TO_CHAR(ID.ID_REGDATETIME, 'YYYYMMDD'), IT.IT_TITLE             	\n");
				sb.append("  ORDER BY ID_REGDATETIME ASC             	\n");
				
			}else if(iSeq.length()>0){
				
				sb.append("  SELECT TO_CHAR(ID.ID_REGDATETIME, 'YYYYMMDD') AS ID_REGDATETIME, SUM(ID_SAME_CT) AS CNT, I.I_TITLE AS TITLE             	\n");
				sb.append("  FROM ISSUE_DATA ID, ISSUE_TITLE IT, ISSUE I             	\n");
				sb.append("  WHERE ID.ID_SITECODE = 10464            	\n");
				sb.append("  AND ID.ID_REGDATETIME >= TO_DATE('"+sDate+" 00:00','YYYY-MM-DD HH24:MI')             	\n");
				sb.append("  AND ID.ID_REGDATETIME <= TO_DATE('"+eDate+" 23:59','YYYY-MM-DD HH24:MI')             	\n");
				sb.append("  AND I.I_SEQ = "+iSeq+"             	\n");
				sb.append("  AND ID.IT_SEQ = IT.IT_SEQ             	\n");
				sb.append("  AND IT.I_SEQ = I.I_SEQ             	\n");
				sb.append("  GROUP BY TO_CHAR(ID.ID_REGDATETIME, 'YYYYMMDD'), I.I_TITLE             	\n");
				sb.append("  ORDER BY ID_REGDATETIME ASC             	\n");
				
			}
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			ChartBean cInfo = null;
			while( rs.next() )
			{
				cInfo = new ChartBean();
				
				cInfo.setData_date(rs.getString("ID_REGDATETIME"));
				cInfo.setData_count(rs.getString("CNT"));
				cInfo.setData_name(rs.getString("TITLE"));
				result.add(cInfo);
				
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
	
	//통계분석>>이슈 통계
	public ArrayList getDataStaticIssue( String sDate, String eDate,int sTime, int eTime, String iSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
				
			sb.append("   SELECT IT_TITLE, IC_NAME, NVL(SUM(CASE IDC1.IC_CODE WHEN 1 THEN 1+ID.ID_SAME_CT END), 0) AS POS, --긍정            	\n");
			sb.append("                                          NVL(SUM(CASE IDC1.IC_CODE WHEN 2 THEN 1+ID.ID_SAME_CT END), 0) AS NEG, --부정            	\n");
			sb.append("   	                               NVL(SUM(CASE IDC1.IC_CODE WHEN 3 THEN 1+ID.ID_SAME_CT END), 0) AS NEU  --중립            	\n");
			sb.append("   FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC1, ISSUE_CODE IC, ISSUE_TITLE IT                                	\n");
			sb.append("   WHERE ID.IT_SEQ = IT.IT_SEQ                                                                                                                                  	\n");
			//트위터 제외 요청 2010.11.05
			sb.append("   	   AND ID.ID_SITECODE <> 10464                                                                                                                          	\n");
			
			if(iSeq.length()>0){
				sb.append("   AND IT.I_SEQ IN ("+iSeq+")                                                                                                                                               	\n");
			}
			if(sDate.length()>0){
				sb.append("   AND ID_REGDATETIME BETWEEN TO_DATE('"+sDate+" "+sTime+":00:00','YYYY-MM-DD HH24:MI:SS')                                              	\n");
				sb.append("                       AND TO_DATE('"+eDate+" "+eTime+":59:59','YYYY-MM-DD HH24:MI:SS')                                                            	\n");
			}
			sb.append("   AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                   	\n");
			sb.append("   AND IDC1.IC_TYPE = IC.IC_TYPE AND IDC1.IC_CODE = IC.IC_CODE                                                                                  	\n");
			sb.append("   AND IDC1.IC_TYPE = 2                                                                                                                                            	\n");
			sb.append("   GROUP BY IC_NAME, IT_TITLE                                                                                                                                 	\n");
			sb.append("   ORDER BY IT_TITLE, IC_NAME                                                                                                                                  	\n");
				
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				String[] sData = new String[5];
				sData[0] = rs.getString("IT_TITLE");
				sData[1] = rs.getString("IC_NAME");
				sData[2] = rs.getString("POS");
				sData[3] = rs.getString("NEG");
				sData[4] = rs.getString("NEU");

				result.add(sData);
				
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
	
	//통계분석>>이슈 통계
	public ArrayList getDataStaticMttype( String sDate, String eDate,int sTime, int eTime, String iSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("   SELECT  M.MT_TYPE, TO_CHAR(ID_REGDATETIME,'YYYYMMDD') AS ID_REGDATE, IDC1.IC_CODE, IC.IC_NAME,  --ID.ID_SEQ, ID.MT_NO,       	\n");
			sb.append("               NVL(SUM(CASE IDC1.IC_CODE WHEN 1 THEN 1+ID.ID_SAME_CT END), 0) AS POS, --긍정            	          	\n");
			sb.append("               NVL(SUM(CASE IDC1.IC_CODE WHEN 2 THEN 1+ID.ID_SAME_CT END), 0) AS NEG --부정                              	\n");
			sb.append("               --,NVL(SUM(CASE IDC1.IC_CODE WHEN 3 THEN 1+ID.ID_SAME_CT END), 0) AS NEU --중립                              	\n");
			sb.append("   FROM ISSUE_DATA ID, ISSUE_TITLE IT, META M, ISSUE_DATA_CODE IDC1, ISSUE_CODE IC                                	\n");
			sb.append("   WHERE ID_REGDATETIME BETWEEN TO_DATE('"+sDate+" "+sTime+":00:00','YYYY-MM-DD HH24:MI:SS')                                    	\n");
			sb.append("              AND TO_DATE('"+eDate+" "+eTime+":59:59','YYYY-MM-DD HH24:MI:SS')                                                              	\n");
			//트위터 제외 요청 2010.11.05
			sb.append("   	   AND ID.ID_SITECODE <> 10464                                                                                                                          	\n");
			if(iSeq.length()>0){
				sb.append("   	   AND IT.I_SEQ IN ("+iSeq+")                                                                                                                          	\n");
			}
			sb.append("   	   AND IDC1.IC_TYPE = 2                                                                                                                        	\n");
			sb.append("   	   AND ID.IT_SEQ = IT.IT_SEQ                                                                                                                 	\n");
			sb.append("   	   AND ID.MT_NO = M.MT_NO                                                                                                                   	\n");
			sb.append("   	   AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                             	\n");
			sb.append("   	   AND IDC1.IC_TYPE = IC.IC_TYPE AND IDC1.IC_CODE = IC.IC_CODE                                                                                                            	\n");
			sb.append("   GROUP BY TO_CHAR(ID_REGDATETIME,'YYYYMMDD'), M.MT_TYPE, IDC1.IC_CODE,IC.IC_NAME   --ID.ID_SEQ, ID.MT_NO,                      	\n");
			sb.append("   ORDER BY ID_REGDATE ASC, MT_TYPE, IC_CODE                                                                                                                             	\n");
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				String[] sData = new String[5];
				sData[0] = rs.getString("MT_TYPE");
				sData[1] = rs.getString("ID_REGDATE");
				sData[2] = rs.getString("IC_NAME");
				sData[3] = rs.getString("POS");
				sData[4] = rs.getString("NEG");
				
				
				result.add(sData);
				
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
	
	//통계분석>>이슈 통계
	public ArrayList getDataStaticSite( String sDate, String eDate,int sTime, int eTime, String iSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("   SELECT SG.SG_SEQ, SG.SG_NAME, TO_CHAR(ID_REGDATETIME,'YYYYMMDD') AS ID_REGDATE, NVL(SUM(ID.ID_SAME_CT+1),0) AS CNT     	\n");
			sb.append("   FROM META M, ISSUE_DATA ID, ISSUE_TITLE IT, SITE_GROUP SG                                                               	\n");
			sb.append("   WHERE ID_REGDATETIME BETWEEN TO_DATE('"+sDate+" "+sTime+":00:00','YYYY-MM-DD HH24:MI:SS')                                    	            	\n");
			sb.append("         AND TO_DATE('"+eDate+" "+eTime+":59:59','YYYY-MM-DD HH24:MI:SS')                                                              	                    	\n");
			if(iSeq.length()>0){
				sb.append("   	  AND IT.I_SEQ IN ("+iSeq+")                                                                                                                                               	\n");
			}
			sb.append("   	  AND ID.IT_SEQ = IT.IT_SEQ                                                                                                                 	                    	\n");
			sb.append("      	  AND ID.MT_NO = M.MT_NO                                                                                                                                       	\n");
			//트위터 제외 요청 2010.11.05
			sb.append("   	   AND SG.SG_SEQ <> 10                                                                                                                          	\n");
			
			sb.append("      	  AND M.SG_SEQ = SG.SG_SEQ                                                                                                                                 	\n");
			sb.append("   GROUP BY TO_CHAR(ID_REGDATETIME,'YYYYMMDD'), SG.SG_SEQ, SG.SG_NAME                                                                         	\n");
			sb.append("   ORDER BY SG_SEQ, ID_REGDATE                                                                                                                                   	\n");
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() ); 
			
			while( rs.next() )
			{
				result.add( new ChartBean(
						rs.getString("SG_SEQ"),
						rs.getString("SG_NAME"),
						rs.getString("ID_REGDATE"),
						rs.getString("CNT")
						));
				
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
	
	//통계분석>>이슈 통계
	public ArrayList getDataIssueTitle( String sDate, String eDate,int sTime, int eTime, String iSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("  SELECT TO_CHAR(ID_REGDATETIME,'YYYYMMDD') AS T_DATE, I.I_TITLE, IT.IT_TITLE,                             	\n");
			sb.append("  CASE M.MT_TYPE WHEN 1 THEN '기사' WHEN 2 THEN '게시' ELSE '없음' END AS MTYPE,                    	\n");
			sb.append("  COUNT(0) AS CNT, M.MT_TYPE , SUM(NVL(M.MT_SAME_CT+1,1)) AS SCNT, IT.IT_SEQ --,IC.IC_NAME                                                                              	\n");
			sb.append("  FROM ISSUE_DATA ID, ISSUE_TITLE IT, ISSUE I, META M                                   	\n");
			sb.append("  , ISSUE_DATA_CODE IDC, ISSUE_CODE IC                                                                    	\n");
			sb.append("  WHERE ID.ID_REGDATETIME BETWEEN TO_DATE('"+sDate+" "+sTime+":00','YYYY-MM-DD HH24:MI')      	            	\n");
			sb.append("  AND TO_DATE('"+eDate+" "+eTime+":59','YYYY-MM-DD HH24:MI')                                                            	\n");
			sb.append("  AND ID.IT_SEQ = IT.IT_SEQ                                                                                                       	\n");
			if(iSeq.length()>0){
				sb.append("   	  AND IT.I_SEQ IN ("+iSeq+")                                                                                                                                               	\n");
			}
			//트위터 제외 요청 2010.11.05
			sb.append("  AND IT.I_SEQ = I.I_SEQ                                                                                                             	\n");
			sb.append("  AND ID.MT_NO = M.MT_NO                                                                                                        	\n");
			sb.append("   	   AND M.SG_SEQ <> 10   \n");
			sb.append("  AND IDC.IC_TYPE = 2                                                                                                               	\n");
			sb.append("  AND IDC.ID_SEQ = ID.ID_SEQ                                                                                                    	\n");
			sb.append("  AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE                                                     	\n");
			sb.append("  GROUP BY TO_CHAR(ID_REGDATETIME,'YYYYMMDD'), I.I_TITLE, IT.IT_TITLE, M.MT_TYPE, IT.IT_SEQ--, IC.IC_NAME    	\n");
			sb.append("  ORDER BY T_DATE, I.I_TITLE, IT.IT_TITLE, M.MT_TYPE--, IC.IC_NAME                                               	\n");
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() ); 
			
			while( rs.next() )
			{
				String[] tStr = new String[8];
				tStr[0] = rs.getString("IT_SEQ");
				tStr[1] = rs.getString("T_DATE");
				tStr[2] = rs.getString("I_TITLE");
				tStr[3] = rs.getString("IT_TITLE");
				tStr[4] = rs.getString("MT_TYPE");
				tStr[5] = rs.getString("MTYPE");
				tStr[6] = rs.getString("CNT");
				tStr[7] = rs.getString("SCNT");
				
				result.add(tStr);
				
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
	
	public String getHtmlIssueTable(ArrayList arrData, ArrayList arrDate, ArrayList arrIssue, ArrayList arrIssueTitle){
		StringBuffer sb = new StringBuffer();
		StringUtil su = new StringUtil();
		String[] tIssue = null;
		String[] tIssueTitle = null;
		String[] tData = null;
		String cntAr = "";
		String cntPo = "";
		/*
		  sData[0] = rs.getString("MT_TYPE");
				sData[1] = rs.getString("ID_REGDATE");
				sData[2] = rs.getString("IC_NAME");
				sData[3] = rs.getString("POS");
				sData[4] = rs.getString("NEG");
				
				tStr[0] = rs.getString("IT_SEQ");
				tStr[1] = rs.getString("T_DATE");
				tStr[2] = rs.getString("I_TITLE");
				tStr[3] = rs.getString("IT_TITLE");
				tStr[4] = rs.getString("MT_TYPE");
				tStr[5] = rs.getString("MTYPE");
				tStr[6] = rs.getString("CNT");
				tStr[7] = rs.getString("SCNT");
		 */
		try{
			
			for(int i=0; i<arrIssue.size(); i++){
				int[] rowCnt = new int[arrIssueTitle.size()];
				int[] colCnt = new int[arrDate.size()*2];
				tIssue = null;
				tIssue = (String[])arrIssue.get(i);
				
				sb.append("  <table width=\\'730\\' border=\\'0\\' cellpadding=\\'0\\' cellspacing=\\'0\\'> ");
				sb.append("          <tr> ");
				sb.append("  	        <td bgcolor=\\'#FFFFFF\\'>&nbsp;</td> ");
				sb.append("  	    </tr> ");
				sb.append("          <tr> ");
				sb.append("          	<td width=\\'730\\' bgcolor=\\'#CFCFCF\\'> ");
				sb.append("          	<table width=\\'100%\\' border=\\'0\\' cellpadding=\\'1\\' cellspacing=\\'1\\'> ");
				sb.append("             <tr height=\\'30\\'> ");
				sb.append("               <td  width=\\'130\\' rowspan=\\'2\\' height=\\'30\\' align=\\'center\\' background=\\'images/statis_table_bg01.gif\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>"+tIssue[1]+"</b></nobr></td> ");
				for (int j = 0; j < arrDate.size(); j++) {
					sb.append("               <td  align=\\'center\\' colspan=\\'2\\'  background=\\'images/statis_table_bg01.gif\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>&nbsp;"+su.getDateTransFormmat(((String)arrDate.get(j)), "/", "md")+"&nbsp;</b></nobr></td> ");
				}
				sb.append("               <td  width=\\'40\\' rowspan=\\'2\\' height=\\'30\\' align=\\'center\\' bgcolor=\\'#F5F5DC\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>TOTAL</b></nobr></td> ");
				sb.append("             </tr>             ");
				sb.append("             <tr>             ");
				for (int j = 0; j < arrDate.size()*2; j++) {
					if(j%2==0){
						sb.append("               <td  align=\\'center\\' background=\\'images/statis_table_bg01.gif\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>기사</nobr></td> ");
					}else{
						sb.append("               <td  align=\\'center\\' background=\\'images/statis_table_bg01.gif\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>게시</nobr></td> ");
					}
				}
				sb.append("             </tr>             ");
				
				for (int j = 0; j < arrIssueTitle.size(); j++) {
					tIssueTitle = null;
					tIssueTitle = (String[])arrIssueTitle.get(j);
					if(tIssue[0].equals(tIssueTitle[0])){
						sb.append("              <tr height=\\'28\\'>               ");
						sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>"+tIssueTitle[3]+"</b></nobr></td>         ");
						int inc = 0;
						for (int k = 0; k < arrDate.size(); k++) {
							cntAr = "0";
							cntPo = "0";
							
							for(int l = 0; l < arrData.size(); l++){
								tData = null;
								tData = (String[])arrData.get(l);
								if(((String)arrDate.get(k)).equals(tData[1])){
									if(tIssueTitle[2].equals(tData[0])){
										if(tData[4].equals("1")) cntAr = tData[7];
										if(tData[4].equals("2")) cntPo = tData[7];
									}
								}
							}
							sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>&nbsp;"+cntAr+"&nbsp;</b></nobr></td> ");
							sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>&nbsp;"+cntPo+"&nbsp;</b></nobr></td> ");
							
							colCnt[inc]+=(Integer.parseInt(cntAr));
							colCnt[inc+1]+=(Integer.parseInt(cntPo));
							inc+=2;
							
							rowCnt[j]+=(Integer.parseInt(cntAr)+Integer.parseInt(cntPo));
						}
						sb.append("               <td align=\\'center\\' bgcolor=\\'#F5F5DC\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>&nbsp;"+rowCnt[j]+"&nbsp;</b></nobr></td> ");
						sb.append("              </tr>               ");
					}
				}
				sb.append("             <tr height=\\'30\\'> ");
				sb.append("               <td  width=\\'130\\' height=\\'30\\' align=\\'center\\' bgcolor=\\'#F5F5DC\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>TOTAL</b></nobr></td> ");
				for (int j = 0; j < arrDate.size()*2; j++) {
					sb.append("               <td  align=\\'center\\'  bgcolor=\\'#F5F5DC\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>&nbsp;"+colCnt[j]+"&nbsp;</b></nobr></td> ");
				}
				int total = 0;
				for(int j = 0; j < rowCnt.length; j++) {
					total += rowCnt[j];
				}
				sb.append("               <td  align=\\'center\\'  bgcolor=\\'#F5F5DC\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>&nbsp;"+total+"&nbsp;</b></nobr></td> ");
				sb.append("             </tr>             ");
				
				sb.append("  	   ");
				sb.append("              </table>       ");
				sb.append("              </td>           ");
				sb.append("              </tr>            ");
				sb.append("          </table>           ");
			}
			
			
		}catch( Exception ex){
			ex.printStackTrace();
			Log.writeExpt("ERROR_LOG", ex);
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String getHtmlIssueTableExcel(ArrayList arrData, ArrayList arrDate, ArrayList arrIssue, ArrayList arrIssueTitle){
		StringBuffer sb = new StringBuffer();
		StringUtil su = new StringUtil();
		String[] tIssue = null;
		String[] tIssueTitle = null;
		String[] tData = null;
		String cntAr = "";
		String cntPo = "";
		/*
		  sData[0] = rs.getString("MT_TYPE");
				sData[1] = rs.getString("ID_REGDATE");
				sData[2] = rs.getString("IC_NAME");
				sData[3] = rs.getString("POS");
				sData[4] = rs.getString("NEG");
				
				tStr[0] = rs.getString("IT_SEQ");
				tStr[1] = rs.getString("T_DATE");
				tStr[2] = rs.getString("I_TITLE");
				tStr[3] = rs.getString("IT_TITLE");
				tStr[4] = rs.getString("MT_TYPE");
				tStr[5] = rs.getString("MTYPE");
				tStr[6] = rs.getString("CNT");
				tStr[7] = rs.getString("SCNT");
		 */
		try{
			
			for(int i=0; i<arrIssue.size(); i++){
				int[] rowCnt = new int[arrIssueTitle.size()];
				int[] colCnt = new int[arrDate.size()*2];
				tIssue = null;
				tIssue = (String[])arrIssue.get(i);
				
				sb.append("  <table width=\"730\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> ");
				sb.append("          <tr> ");
				sb.append("  	        <td bgcolor=\"#FFFFFF\">&nbsp;</td> ");
				sb.append("  	    </tr> ");
				sb.append("          <tr> ");
				sb.append("          	<td width=\"730\" bgcolor=\"#CFCFCF\"> ");
				sb.append("          	<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\"> ");
				sb.append("             <tr height=\"30\"> ");
				sb.append("               <td  width=\"130\" rowspan=\"2\" height=\"30\" align=\"center\" background=\"images/statis_table_bg01.gif\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>"+tIssue[1]+"</b></nobr></td> ");
				for (int j = 0; j < arrDate.size(); j++) {
					sb.append("               <td  align=\"center\" colspan=\"2\"  background=\"images/statis_table_bg01.gif\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>&nbsp;"+su.getDateTransFormmat(((String)arrDate.get(j)), "/", "md")+"&nbsp;</b></nobr></td> ");
				}
				sb.append("               <td  width=\"40\" rowspan=\"2\" height=\"30\" align=\"center\" bgcolor=\"#F5F5DC\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>TOTAL</b></nobr></td> ");
				sb.append("             </tr>             ");
				sb.append("             <tr>             ");
				for (int j = 0; j < arrDate.size()*2; j++) {
					if(j%2==0){
						sb.append("               <td  align=\"center\" background=\"images/statis_table_bg01.gif\" style=\"padding: 3px 0px 0px 3px;\"><nobr>기사</nobr></td> ");
					}else{
						sb.append("               <td  align=\"center\" background=\"images/statis_table_bg01.gif\" style=\"padding: 3px 0px 0px 3px;\"><nobr>게시</nobr></td> ");
					}
				}
				sb.append("             </tr>             ");
				
				for (int j = 0; j < arrIssueTitle.size(); j++) {
					tIssueTitle = null;
					tIssueTitle = (String[])arrIssueTitle.get(j);
					if(tIssue[0].equals(tIssueTitle[0])){
						sb.append("              <tr height=\"28\">               ");
						sb.append("               <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>"+tIssueTitle[3]+"</b></nobr></td>         ");
						int inc = 0;
						for (int k = 0; k < arrDate.size(); k++) {
							cntAr = "0";
							cntPo = "0";
							
							for(int l = 0; l < arrData.size(); l++){
								tData = null;
								tData = (String[])arrData.get(l);
								if(((String)arrDate.get(k)).equals(tData[1])){
									if(tIssueTitle[2].equals(tData[0])){
										if(tData[4].equals("1")) cntAr = tData[7];
										if(tData[4].equals("2")) cntPo = tData[7];
									}
								}
							}
							sb.append("               <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>&nbsp;"+cntAr+"&nbsp;</b></nobr></td> ");
							sb.append("               <td align=\"center\" bgcolor=\"#FFFFFF\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>&nbsp;"+cntPo+"&nbsp;</b></nobr></td> ");
							
							colCnt[inc]+=(Integer.parseInt(cntAr));
							colCnt[inc+1]+=(Integer.parseInt(cntPo));
							inc+=2;
							
							rowCnt[j]+=(Integer.parseInt(cntAr)+Integer.parseInt(cntPo));
						}
						sb.append("               <td align=\"center\" bgcolor=\"#F5F5DC\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>&nbsp;"+rowCnt[j]+"&nbsp;</b></nobr></td> ");
						sb.append("              </tr>               ");
					}
				}
				sb.append("             <tr height=\"30\"> ");
				sb.append("               <td  width=\"130\" height=\"30\" align=\"center\" bgcolor=\"#F5F5DC\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>TOTAL</b></nobr></td> ");
				for (int j = 0; j < arrDate.size()*2; j++) {
					sb.append("               <td  align=\"center\"  bgcolor=\"#F5F5DC\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>&nbsp;"+colCnt[j]+"&nbsp;</b></nobr></td> ");
				}
				int total = 0;
				for(int j = 0; j < rowCnt.length; j++) {
					total += rowCnt[j];
				}
				sb.append("               <td  align=\"center\"  bgcolor=\"#F5F5DC\" style=\"padding: 3px 0px 0px 3px;\"><nobr><b>&nbsp;"+total+"&nbsp;</b></nobr></td> ");
				sb.append("             </tr>             ");
				
				sb.append("  	   ");
				sb.append("              </table>       ");
				sb.append("              </td>           ");
				sb.append("              </tr>            ");
				sb.append("          </table>           ");
			}
			
			
		}catch( Exception ex){
			ex.printStackTrace();
			Log.writeExpt("ERROR_LOG", ex);
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String getHtmlTable(ArrayList arrData, ArrayList arrDate){
		StringBuffer sb = new StringBuffer();
		StringUtil su = new StringUtil();
		/*
		  sData[0] = rs.getString("MT_TYPE");
				sData[1] = rs.getString("ID_REGDATE");
				sData[2] = rs.getString("IC_NAME");
				sData[3] = rs.getString("POS");
				sData[4] = rs.getString("NEG");
		 */
		try{
			sb.append("  <table width=\\'730\\' border=\\'0\\' cellpadding=\\'0\\' cellspacing=\\'0\\'> ");
			sb.append("          <tr> ");
			sb.append("  	        <td bgcolor=\\'#7CA5DD\\'><img src=\\'images/brank.gif\\' width=\\'1\\' height=\\'2\\'></td> ");
			sb.append("  	    </tr> ");
			sb.append("          <tr> ");
			sb.append("          	<td width=\\'730\\' bgcolor=\\'#CFCFCF\\'> ");
			sb.append("          	<table width=\\'100%\\' border=\\'0\\' cellpadding=\\'1\\' cellspacing=\\'1\\'> ");
			sb.append("             <tr height=\\'30\\'> ");
			sb.append("               <td  width=\\'130\\' height=\\'30\\' align=\\'center\\' background=\\'images/statis_table_bg01.gif\\' style=\\'padding: 3px 0px 0px 3px;\\'>&nbsp;</td> ");
			for (int i = 0; i < arrDate.size(); i++) {
				
				sb.append("               <td  align=\\'center\\' background=\\'images/statis_table_bg01.gif\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr><b>&nbsp;"+su.getDateTransFormmat(((String)arrDate.get(i)), "/", "md")+"&nbsp;</b></nobr></td> ");
			}
			sb.append("             </tr>             ");
			sb.append("  	   ");
			
			String title = "";
			String[] tData = null;
			boolean chkData = false;
			
			for (int i = 1; i < 5; i++) {
				title = "";
	
				if(i==1){
					title = "<font color=\\'#376092\\'><b>■ 긍정기사&nbsp;</b></font>";
					sb.append("              <tr height=\\'28\\'>               ");
					sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+title+"</nobr></td>         ");
					
					for (int j = 0; j < arrDate.size(); j++) {
						chkData = false;
						for (int k = 0; k < arrData.size(); k++) {
							tData = null;
							tData = (String[])arrData.get(k);
							
							if(((String)arrDate.get(j)).equals(tData[1]) && tData[0].equals("1") && !tData[3].equals("0")){
								sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+tData[3]+"</nobr></td>                ");
								chkData = true;
								break;
							}
						}
						if(!chkData){
							sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>0</nobr></td>                ");
						}
					}
					
					sb.append("              </tr>            ");
				}
				if(i==2){
					title = "<font color=\\'#558ED5\\'><b>■ 긍정게시&nbsp;</b></font>";
					sb.append("              <tr height=\\'28\\'>               ");
					sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+title+"</nobr></td>         ");
					
					for (int j = 0; j < arrDate.size(); j++) {
						chkData = false;
						for (int k = 0; k < arrData.size(); k++) {
							tData = null;
							tData = (String[])arrData.get(k);
							
							if(((String)arrDate.get(j)).equals(tData[1]) && tData[0].equals("2") && !tData[3].equals("0")){
								sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+tData[3]+"</nobr></td>                ");
								chkData = true;
								break;
							}
						}
						if(!chkData){
							sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>0</nobr></td>                ");
						}
					}
					
					sb.append("              </tr>            ");
				}
				if(i==3){
					title = "<font color=\\'#CC0000\\'><b>■ 부정기사&nbsp;</b></font>";
					sb.append("              <tr height=\\'28\\'>               ");
					sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+title+"</nobr></td>         ");
					
					for (int j = 0; j < arrDate.size(); j++) {
						chkData = false;
						for (int k = 0; k < arrData.size(); k++) {
							tData = null;
							tData = (String[])arrData.get(k);
							
							if(((String)arrDate.get(j)).equals(tData[1]) && tData[0].equals("1") && !tData[4].equals("0")){
								sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+tData[4]+"</nobr></td>                ");
								chkData = true;
								break;
							}
						}
						if(!chkData){
							sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>0</nobr></td>                ");
						}
					}
					
					sb.append("              </tr>            ");
				}
				if(i==4){
					title = "<font color=\\'#F0847C\\'><b>■ 부정게시&nbsp;</b></font>";
					sb.append("              <tr height=\\'28\\'>               ");
					sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+title+"</nobr></td>         ");
					
					for (int j = 0; j < arrDate.size(); j++) {
						chkData = false;
						for (int k = 0; k < arrData.size(); k++) {
							tData = null;
							tData = (String[])arrData.get(k);
							
							if(((String)arrDate.get(j)).equals(tData[1]) && tData[0].equals("2") && !tData[4].equals("0")){
								sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>"+tData[4]+"</nobr></td>                ");
								chkData = true;
								break;
							}
						}
						if(!chkData){
							sb.append("               <td align=\\'center\\' bgcolor=\\'#FFFFFF\\' style=\\'padding: 3px 0px 0px 3px;\\'><nobr>0</nobr></td>                ");
						}
					}
					
					sb.append("              </tr>            ");
				}
				
			}
			sb.append("              </table>       ");
			sb.append("              </td>           ");
			sb.append("              </tr>            ");
			sb.append("          </table>           ");
			
		}catch( Exception ex){
			ex.printStackTrace();
			Log.writeExpt("ERROR_LOG", ex);
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	//통계분석>>이슈 통계
	public String[] getTwGraphSetting()
	{
		String[] result = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("   SELECT TG_SEQ, TG_ACCRUE, TG_YAXIS_MIN, TG_YAXIS_MAX, TG_XAXIS_MIN, TG_XAXIS_MAX,   	\n");
			sb.append("          TG_TITLE, TG_LEGEND, TG_POINT, TG_POINT_CNT, M_SEQ, TG_ACTIVE     	\n");
			sb.append("   FROM TWITTER_GRAPH_SETTING                                                               	\n");
			sb.append("   WHERE TG_ACTIVE = 1                                                               	\n");
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				result = new String[11];
				result[0] = rs.getString("TG_SEQ");
				result[1] = rs.getString("TG_ACCRUE");
				result[2] = rs.getString("TG_YAXIS_MIN");
				result[3] = rs.getString("TG_YAXIS_MAX");
				result[4] = rs.getString("TG_XAXIS_MIN");
				result[5] = rs.getString("TG_XAXIS_MAX");
				result[6] = rs.getString("TG_TITLE");
				result[7] = rs.getString("TG_LEGEND");
				result[8] = rs.getString("TG_POINT");
				result[9] = rs.getString("TG_POINT_CNT");
				result[10] = rs.getString("M_SEQ");
				
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
	
	//통계분석>>이슈 통계
	public boolean editTwGraphSetting(String tg_title, 
									   String tg_legend, 
									   int tg_accrue, 
									   int tg_yaxis_min, 
									   int tg_yaxis_max, 
									   int tg_point, 
									   int tg_point_cnt, 
									   String m_seq
									   )
	{
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("   UPDATE TWITTER_GRAPH_SETTING   	\n");
			sb.append("   SET TG_TITLE = '"+tg_title+"'  	\n");
			sb.append("   ,TG_LEGEND = '"+tg_legend+"'  	\n");
			sb.append("   ,TG_ACCRUE = "+tg_accrue+"  	\n");
			sb.append("   ,TG_YAXIS_MIN = "+tg_yaxis_min+"   	\n");
			sb.append("   ,TG_YAXIS_MAX = "+tg_yaxis_max+"   	\n");
			sb.append("   ,TG_POINT = "+tg_point+"    	\n");
			sb.append("   ,TG_POINT_CNT = "+tg_point_cnt+"    	\n");
			sb.append("   ,M_SEQ = "+m_seq+"    	\n");
			sb.append("    WHERE TG_ACTIVE = 1    	\n");
			
			System.out.println(sb.toString());
			
			if(stmt.executeUpdate( sb.toString())>-1){
				result = true;
			}
			System.out.println("result:"+result);
			
			
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getDateStaticTwitterStack( String sDate, String eDate, int xp, int yp, int zp, String legend, int baseCnt, String kseq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			String select = "";
			if(xp>0)
				select += ",TKC.K_XP";
			if(yp>0)
				select += ",TKC.K_YP";
			if(zp>0)
				select += ",TKC.K_ZP";
				
			sb = new StringBuffer();
			
				sb.append("  SELECT SUM(TK_CNT) AS CT, TK_DATE "+select+"              	\n");
				sb.append("  FROM TWITTER_KEYWORD_CNT TKC, KEYWORD K             	\n");
				sb.append("  WHERE TK_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"'            	\n");
				sb.append("  AND TKC.K_XP = K.K_XP AND TKC.K_YP = K.K_YP AND TKC.K_ZP = K.K_ZP             	\n");
				sb.append("  AND K.K_TYPE < 11                  	\n");
				if(kseq.length()>0){
					sb.append("  AND K.K_SEQ IN ("+kseq+")             	\n");
				}else{
					if(xp>0)
						sb.append("  AND TKC.K_XP = "+xp+"            	\n");
					if(yp>0)
						sb.append("  AND TKC.K_YP = "+yp+"            	\n");
					if(zp>0)
						sb.append("  AND TKC.K_ZP = "+zp+"            	\n");
				}
				sb.append("  GROUP BY TK_DATE "+select+"             	\n");
				sb.append("  ORDER BY TK_DATE ASC             	\n");
				
				
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			ChartBean cInfo = null;
			while( rs.next() )
			{
				baseCnt += rs.getInt("CT");
					
				cInfo = new ChartBean();
				
				cInfo.setData_date(rs.getString("TK_DATE"));
				cInfo.setData_count(Integer.toString(baseCnt));
				cInfo.setData_name(legend);
				
				result.add(cInfo);
				
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
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getDateStaticTwitterOccur( String sDate, String eDate, String keyWordXyz, String legend, int baseCnt, String kseqs )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			String[] arrKeywords = keyWordXyz.split("@");
			String[] arrKseq = kseqs.split(",");
			
			if(arrKeywords != null && arrKeywords.length > 0 &&
				   arrKseq != null && arrKseq.length     > 0 &&
			   arrKeywords.length == arrKseq.length ){
				
				sb = new StringBuffer();
				
				for(int i = 0; i < arrKeywords.length; i++){
					
					String[] arrKey = arrKeywords[i].split(",");

					if(arrKey != null && arrKey.length == 3){
						
						if(i != 0){
							sb.append("UNION \n");
						}
						
						sb.append("  SELECT SUM(TK_CNT) AS CT, TK_DATE ,TKC.K_XP,TKC.K_YP,TKC.K_ZP        	\n");
						sb.append("  FROM TWITTER_KEYWORD_CNT TKC, KEYWORD K             	\n");
						sb.append("  WHERE TK_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"'            	\n");
						if(!arrKey[0].equals("0")){
							sb.append("  AND TKC.K_XP = K.K_XP                        \n");
						}
						if(!arrKey[1].equals("0")){
							sb.append("  AND TKC.K_YP = K.K_YP             	           \n");
						}
						if(!arrKey[2].equals("0")){
							sb.append("  AND TKC.K_ZP = K.K_ZP                          	\n");
						}
						sb.append("  AND K.K_TYPE < 11                  	          \n");
						sb.append("  AND K.K_SEQ = "+arrKseq[i]+"                  	          \n");
						sb.append("  GROUP BY TK_DATE ,TKC.K_XP,TKC.K_YP,TKC.K_ZP             	\n");	
					}
				}
				sb.append("  ORDER BY TK_DATE ASC, K_XP, K_YP, K_ZP             	 \n");
				
				System.out.println(sb.toString());
				
				rs = stmt.executeQuery( sb.toString() );
				ChartBean cInfo = null;
				while( rs.next() )
				{
					//baseCnt += rs.getInt("CT");
					
					cInfo = new ChartBean();
					
					cInfo.setData_date(rs.getString("TK_DATE"));
					cInfo.setData_count(rs.getString("CT"));
					cInfo.setData_name(legend);
					
					result.add(cInfo);
					
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
	
	//
	public ArrayList getIssueTitle(String iSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("  SELECT I.I_SEQ, I.I_TITLE, IT_SEQ, IT_TITLE                             	\n");
			sb.append("  FROM ISSUE I, ISSUE_TITLE IT                                   	\n");
			sb.append("  WHERE I.I_SEQ = IT.I_SEQ       	            	\n");
			sb.append("  AND I.I_ACTIVE = 1 AND IT.IT_ACTIVE = 1                                                            	\n");
			sb.append("  AND I.I_SEQ IN ("+iSeq+")                                                            	\n");
			sb.append("  ORDER BY I.I_SEQ, IT_SEQ                                                           	\n");
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() ); 
			
			while( rs.next() )
			{
				String[] tStr = new String[4];
				tStr[0] = rs.getString("I_SEQ");
				tStr[1] = rs.getString("I_TITLE");
				tStr[2] = rs.getString("IT_SEQ");
				tStr[3] = rs.getString("IT_TITLE");
				
				result.add(tStr);
				
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
	
	public ArrayList getIssue(String iSeq )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("  SELECT I.I_SEQ, I.I_TITLE                             	\n");
			sb.append("  FROM ISSUE I                                   	\n");
			sb.append("  WHERE I.I_ACTIVE = 1                                                             	\n");
			sb.append("  AND I.I_SEQ IN ("+iSeq+")                                                            	\n");
			sb.append("  ORDER BY I_SEQ                                                           	\n");
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() ); 
			
			while( rs.next() )
			{
				String[] tStr = new String[2];
				tStr[0] = rs.getString("I_SEQ");
				tStr[1] = rs.getString("I_TITLE");
				
				result.add(tStr);
				
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
	
	
	//통계분석>>키워드 분류>> 차트 데이터
	public ArrayList getIssueData( String sDate, String eDate, String icType )
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			sb = new StringBuffer();
			
			sb.append("    SELECT ID.ID_TITLE, ID.MD_DATE, ID.MD_SITE_NAME, ID.ID_URL, IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME       	\n");
			sb.append("    FROM ISSUE_DATA ID, ISSUE_CODE IC, ISSUE_DATA_CODE IDC                                                                                                	\n");
			sb.append("    WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00' AND '"+eDate+" 23:59'                                                                                     	\n");
			sb.append("    AND ID.ID_USEYN = 'Y'      	                                                                                                                                             	\n");
			sb.append("    AND ID.ID_SEQ = IDC.ID_SEQ      	                                                                                                                                             	\n");
			sb.append("    AND IDC.IC_TYPE = "+icType+"     	                                                                                                                                                     	\n");
			sb.append("    AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE     	                                                                                     	\n");
			sb.append("    ORDER BY MD_DATE DESC, IDC.IC_CODE                                                                                                                                                  	\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			Hashtable htData  = null;
			
			while( rs.next() )
			{
				htData = new Hashtable();
				htData.put("ID_TITLE", rs.getString("ID_TITLE"));
				htData.put("MD_DATE", rs.getString("MD_DATE"));
				htData.put("MD_SITE_NAME", rs.getString("MD_SITE_NAME"));
				htData.put("ID_URL", rs.getString("ID_URL"));
				htData.put("IC_NAME", rs.getString("IC_NAME"));
				
				result.add( htData);
				
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
	
	//통계분석>>트위터 키워드>> 키워드 비교 그래프 데이터
	public ArrayList getTwitterKeywordCompare( String sDate, String eDate, String keyWordXyz, String keyWordName, String  baseCnt)
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();					
			
			String[] arrXyz = null;
			String[] arrKname = null;
			String[] arrDivXyz = null;
			String[] arrBase = null;
			String select = "";
			
			sb = new StringBuffer();
			
			if(keyWordXyz.length()>0){
				arrXyz = keyWordXyz.split("@");
				arrKname = keyWordName.split("@");
				arrBase = baseCnt.split("@");
				
				for (int i = 0; i < arrXyz.length; i++) {
					arrDivXyz = null;
					arrDivXyz = arrXyz[i].split(",");
					select = "";
					
					if(arrDivXyz.length>0)
						select += ",TKC.K_XP";
					if(arrDivXyz.length>0 && !arrDivXyz[1].equals("0"))
						select += ",TKC.K_YP";
					if(arrDivXyz.length>1 && !arrDivXyz[2].equals("0"))
						select += ",TKC.K_ZP";
					
					if(i!=0){
						sb.append("  UNION             	\n");
					}
					
					sb.append("  SELECT '"+arrKname[i]+"' AS K_VALUE, SUM(TK_CNT) AS CT, TK_DATE, '"+arrBase[i]+"' AS BASECNT             	\n");
					sb.append("  FROM TWITTER_KEYWORD_CNT TKC, KEYWORD K             	\n");
					sb.append("  WHERE TK_DATE BETWEEN '"+sDate.replaceAll("-", "")+"' AND '"+eDate.replaceAll("-", "")+"'            	\n");
					sb.append("  AND TKC.K_XP = K.K_XP AND TKC.K_YP = K.K_YP AND TKC.K_ZP = K.K_ZP             	\n");
					sb.append("  AND K.K_TYPE < 11                  	\n");
					
					sb.append("  AND TKC.K_XP = "+arrDivXyz[0]+"            	\n");
					if(arrDivXyz.length>0 && !arrDivXyz[1].equals("0"))
						sb.append("  AND TKC.K_YP = "+arrDivXyz[1]+"            	\n");
					if(arrDivXyz.length>1 && !arrDivXyz[2].equals("0"))
						sb.append("  AND TKC.K_ZP = "+arrDivXyz[2]+"            	\n");
					
					sb.append("  GROUP BY TK_DATE "+select+"             	\n");
					if((i+1)==arrXyz.length){
						sb.append("  ORDER BY K_VALUE ASC, TK_DATE ASC             	\n");
					}
					
				}
				
				
			}
			
			System.out.println(sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			ChartBean cInfo = null;
			String preName = "";
			int base = 0; 
			
			while( rs.next() )
			{
				//baseCnt += rs.getInt("CT");
				if(!preName.equals(rs.getString("K_VALUE"))){
					base = rs.getInt("BASECNT");
				}
				
				base+=rs.getInt("CT");
				
				cInfo = new ChartBean();
				
				cInfo.setData_date(rs.getString("TK_DATE"));
				cInfo.setData_count(Integer.toString(base));
				//cInfo.setData_count(rs.getString("CT"));
				cInfo.setData_name(rs.getString("K_VALUE").trim());
				
				result.add(cInfo);
				
				preName = rs.getString("K_VALUE");
				
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
	
	
	
	public int getMaxMinCnt(ArrayList arrData, String mode){
		int result = 0;
		
		if(mode.equals("MAX")){
			for (int i = 0; i < arrData.size(); i++) {
				ChartBean cBean = (ChartBean)arrData.get(i);
				if(result < Integer.parseInt(cBean.getData_count())){
					result = Integer.parseInt(cBean.getData_count());
				}
			}
		}else if(mode.equals("MIN")){
			result = 1000000;
			for (int i = 0; i < arrData.size(); i++) {
				ChartBean cBean = (ChartBean)arrData.get(i);
				if(result > Integer.parseInt(cBean.getData_count())){
					result = Integer.parseInt(cBean.getData_count());
				}
			}
		}
		
		
		return result;
	}
	
	
	
	
	
	
}
