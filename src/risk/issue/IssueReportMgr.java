package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


import risk.DBconn.DBconn;
import risk.sms.AddressBookBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;
import risk.admin.log.LogBean;
import risk.admin.log.LogConstants;
import risk.issue.IssueReportBean;

public class IssueReportMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private ResultSet rs2    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private IssueReportBean irBean = new IssueReportBean();
	private LogBean lBean = new LogBean();
	private int startNum = 0;
    private int endNum = 0;
	private int totalCnt = 0;
	
	
	public String msMinNo   = "";   //검색 시작 MD_SEQ
    public String msMaxNo   = "";   //검색 마지막 MD_SEQ
	
	public int getTotalCnt() {
		return totalCnt;
	}

	/**
     * ISSUE_REPORT를 등록하고 이슈번호 리턴
     * @param IssueBean
     * @return
     */
    public String insertReport(IssueReportBean irBean)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
        String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO ISSUE_REPORT(                                  \n");
			sb.append("                    IR_TYPE                                 \n");
			sb.append("                   ,IR_TITLE                                \n");
			sb.append("                   ,IR_MEMO                                 \n");
			sb.append("                   ,IR_HTML                                 \n");
			sb.append("                   ,IR_REGDATE                              \n");
			sb.append("                   ,IR_MAILYN                               \n");					
			sb.append("                   )                               		   \n");
			sb.append(" VALUES(                                                    \n");			
			sb.append("        '"+irBean.getIr_type()+"'                          \n");
			sb.append("        ,'"+irBean.getIr_title()+"'                         \n");
			sb.append("        ,'"+irBean.getIr_memo()+"'                          \n");
			sb.append("        ,'"+irBean.getIr_html()+"'                          \n");
			sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'     \n");
			sb.append("        ,'"+irBean.getIr_mailyn()+"'                        \n");			
			sb.append(" 	  )                                                    \n");
			if(stmt.executeUpdate(sb.toString())>0) {
				sb = new StringBuffer();
				sb.append(" SELECT MAX(IR_SEQ)AS IR_SEQ FROM ISSUE_REPORT                              \n");			
				rs = stmt.executeQuery(sb.toString());
				if(rs.next()){
					insertNum = rs.getString("IR_SEQ");
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
        return insertNum;
    }
    
    /**
     * ISSUE_REPORT를 등록하고 이슈번호 리턴
     * @param IssueBean
     * @return
     */
    public boolean UpdateReport(String irSeq, String irHtml)
    {
        boolean result = false;
        
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			
			sb.append("UPDATE ISSUE_REPORT           \n");
			sb.append("   SET IR_HTML = '"+irHtml+"' \n");
			sb.append(" WHERE IR_SEQ = "+irSeq+"     \n");
			
			if(stmt.executeUpdate(sb.toString())>0) {
				result = true;								
			}											
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            sb = null;
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }        
        return result;
    }
    
    /**
     * ISSUE_REPORT 메일 성공 여부
     * @param IssueBean
     * @return
     */
    public boolean updateMailYN(IssueReportBean irBean)
    {    	
        boolean result = false;
        String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" UPDATE ISSUE_REPORT SET IR_MAILYN = '"+irBean.getIr_mailyn()+"'       \n");			
			sb.append(" WHERE                                                    \n");			
			sb.append("       IR_SEQ = "+irBean.getIr_seq()+"                    \n");
			
			if(stmt.executeUpdate(sb.toString())>0) {
				result = true;		
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
     * ISSUE_REPORT를 삭제한다.
     * @param IssueBean
     * @return
     */
    public String deleteReport(String ir_seq)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
        String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" DELETE FROM ISSUE_REPORT                              	   \n");
			sb.append(" WHERE IR_SEQ IN ("+ir_seq+")                               \n");
		
			if(stmt.executeUpdate(sb.toString())>0) {
										
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
        return insertNum;
    }
    
    public IssueReportBean getReportBean(String ir_seq)
    {
    	return (IssueReportBean)getIssueReportList(0,0,ir_seq,"","","","").get(0);	
    }
    public IssueReportBean getReportBean_old(String ir_seq)
    {
    	return (IssueReportBean)getIssueReportList_old(0,0,ir_seq,"","","","").get(0);	
    }
    
    /**
     * ISSUE_REPORT 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param ir_seq : ISSUE_REPORT.IR_SEQ
     * @param schKey : ISSUE_REPORT.IR_TITLE
     * @param schSdate : ISSUE_REPORT.IR_REGDATE
     * @param schEdate : ISSUE_REPORT.IR_REGDATE
     * @param useYn : ISSUE_REPORT.IR_USEYN
     * @return : 이슈 리포트 검색리스트
     */
    public ArrayList getIssueReportList( 
    		int pageNum, 
    		int rowCnt,
    		String ir_seq,
    		String ir_type, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate
    )    
    {    	
    	ArrayList issueReportList = new ArrayList();    	    	
    	String tmpI_seq = null;
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
		
		if(ir_type.equals("D1")){
			ir_type = "D1','I";
		}
		
		if(ir_type.equals("W")){
			ir_type = "W','W2";
		}
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(IR_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE_REPORT                                                      \n");
			sb.append("		WHERE 1=1														\n");
			if( !ir_seq.equals("") ) {
				sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
			}
			if( !ir_type.equals("") ) {
				sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}					
						
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();				
			sb.append(" SELECT IR_SEQ                                                         \n");	
			sb.append("        ,IR_TYPE                                                      \n");
			sb.append("        ,IR_TITLE                                                      \n");
			sb.append("        ,IR_HTML                                                      \n");
			sb.append("        ,IR_MEMO                                                      \n");
			sb.append("        ,IR_MAILYN                                                     \n");
			sb.append("        ,(SELECT L_REGDATE FROM LOG L WHERE L.L_KEY = IR.IR_SEQ AND L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+" ORDER BY L_REGDATE DESC LIMIT 1) AS IR_MAILDATE     \n");
			sb.append("        ,DATE_FORMAT(IR_REGDATE,'%Y-%m-%d %H:%i:%s') AS IR_REGDATE     \n");			
			sb.append("        ,(SELECT COUNT(*) FROM LOG L , LOG_RECEIVER LR WHERE L.L_SEQ=LR.L_SEQ AND L.L_KEY = IR.IR_SEQ AND L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+") AS MAIL_CNT     \n");
			sb.append(" FROM ISSUE_REPORT IR                                                   \n");
			sb.append(" WHERE 1=1														      \n");
			if( !ir_seq.equals("") ) {
				sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
			}
			if( !ir_type.equals("") ) {
				sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}	
			
			sb.append(" ORDER BY IR_REGDATE DESC 		                                     \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                      \n");
			}			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				irBean = new IssueReportBean();
				irBean.setIr_seq(rs.getString("IR_SEQ"));
				irBean.setIr_title(rs.getString("IR_TITLE"));
				irBean.setIr_html(rs.getString("IR_HTML"));
				irBean.setIr_type(rs.getString("IR_TYPE"));
				irBean.setIr_memo(rs.getString("IR_MEMO"));
				irBean.setIr_regdate(rs.getString("IR_REGDATE"));
				irBean.setIr_maildate(rs.getString("IR_MAILDATE"));
				irBean.setIr_mailyn(rs.getString("IR_MAILYN"));
				irBean.setIr_mailcnt(rs.getString("MAIL_CNT"));
				issueReportList.add(irBean);
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
		
	    return issueReportList;
	}
    public ArrayList getIssueReportList_old( 
    		int pageNum, 
    		int rowCnt,
    		String ir_seq,
    		String ir_type, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate
    )    
    {    	
    	ArrayList issueReportList = new ArrayList();    	    	
    	String tmpI_seq = null;
    	
    	//리스트 시작, 끝번호 
    	if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
    	this.endNum = rowCnt;
    	
    	if(ir_type.equals("D")){
    		ir_type = "D','D2";
    	}
    	
    	if(ir_type.equals("W")){
    		ir_type = "W','W2";
    	}
    	
    	try {
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();			
    		sb.append("		SELECT COUNT(IR_SEQ) AS CNT 								\n");
    		sb.append("		FROM ISSUE_REPORT_OLD                                                      \n");
    		sb.append("		WHERE 1=1														\n");
    		if( !ir_seq.equals("") ) {
    			sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
    		}
    		if( !ir_type.equals("") ) {
    			sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
    		}	
    		if( !schKey.equals("") ) {
    			sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
    		}				
    		if( !schSdate.equals("") && !schEdate.equals("")) {					
    			sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
    		}					
    		
    		rs = stmt.executeQuery(sb.toString());
    		if ( rs.next() ) {
    			totalCnt  = rs.getInt("CNT");            	
    		}
    		
    		rs.close();                     			
    		sb = new StringBuffer();				
    		sb.append(" SELECT IR_SEQ                                                         \n");	
    		sb.append("        ,IR_TYPE                                                      \n");
    		sb.append("        ,IR_TITLE                                                      \n");
    		sb.append("        ,IR_HTML                                                      \n");
    		sb.append("        ,IR_MEMO                                                      \n");
    		sb.append("        ,IR_MAILYN                                                     \n");
    		sb.append("        ,DATE_FORMAT(IR_REGDATE,'%Y-%m-%d %H:%i:%s') AS IR_REGDATE     \n");			
    		sb.append(" FROM ISSUE_REPORT_OLD IR                                                   \n");
    		sb.append(" WHERE 1=1														      \n");
    		if( !ir_seq.equals("") ) {
    			sb.append("		AND	IR_SEQ IN ("+ir_seq+")  									\n");
    		}
    		if( !ir_type.equals("") ) {
    			sb.append("		AND	IR_TYPE IN ( '"+ir_type+"')  									\n");
    		}	
    		if( !schKey.equals("") ) {
    			sb.append("		AND	IR_TITLE LIKE '%"+schKey+"%'  							\n");
    		}				
    		if( !schSdate.equals("") && !schEdate.equals("")) {					
    			sb.append("		AND	IR_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
    		}	
    		
    		sb.append(" ORDER BY IR_REGDATE DESC 		                                     \n");
    		if(pageNum>0){
    			sb.append(" LIMIT   "+startNum+","+endNum+"                                      \n");
    		}			
    		System.out.println(sb.toString());				
    		rs = stmt.executeQuery(sb.toString());
    		
    		while(rs.next()){
    			irBean = new IssueReportBean();
    			irBean.setIr_seq(rs.getString("IR_SEQ"));
    			irBean.setIr_title(rs.getString("IR_TITLE"));
    			irBean.setIr_html(rs.getString("IR_HTML"));
    			irBean.setIr_type(rs.getString("IR_TYPE"));
    			irBean.setIr_memo(rs.getString("IR_MEMO"));
    			irBean.setIr_regdate(rs.getString("IR_REGDATE"));
    			irBean.setIr_mailyn(rs.getString("IR_MAILYN"));
    			issueReportList.add(irBean);
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
    	
    	return issueReportList;
    }
    
    /**
     * LOG 테이블을 조회하여 리포트 로그를 어레이로 반환.   
     * @param ir_seq : LOG.L_KEY     
     * @return : 이슈 리포트 검색리스트
     */
    public LogBean getIssueReportLogBean(String ir_seq)    
    {
    	ArrayList receiverList = new ArrayList();
    	AddressBookBean abBean = new AddressBookBean();
    	String l_seq = "";
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		    			
			sb = new StringBuffer();				
			sb.append(" SELECT L.L_SEQ                         \n");
    		sb.append("       ,L.L_KINDS                       \n");
    		sb.append("       ,(SELECT LK_NAME FROM LOG_KINDS  WHERE LK_SEQ = L.L_KINDS) AS KINDSNAME  \n");
    		sb.append("       ,L.L_TYPE                        \n");
    		sb.append("       ,(SELECT LT_NAME FROM LOG_TYPE  WHERE LT_SEQ = L.L_TYPE) AS TYPENAME  \n");
    		sb.append("       ,IFNULL(L.L_IP,'') AS L_IP         \n");
    		sb.append("       ,L.L_KEY                           \n");
    		sb.append("       ,L.L_REGDATE                        \n");
    		sb.append("       ,L.M_SEQ                         \n");
    		sb.append("       ,M.M_ID                         \n");
    		sb.append("       ,M.M_NAME                         \n");
    		sb.append(" FROM LOG L INNER JOIN MEMBER M ON L.M_SEQ = M.M_SEQ \n");
    		sb.append(" WHERE                                               \n");
    		sb.append(" L.L_KINDS ="+LogConstants.getReportKindsVal()+" AND L.L_TYPE="+LogConstants.getMailTypeVal()+"  \n");
    		
    		if(ir_seq.length()>0)
    		{
    			sb.append(" AND L_KEY = "+ir_seq+" \n");
    		}
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				l_seq += l_seq.equals("") ?  rs.getString("L_SEQ") : ","+ rs.getString("L_SEQ");    	
			}
			
			rs.close();
			
			if(l_seq.length()>0){
				sb = new StringBuffer();				
				sb.append(" SELECT AB.*	,DATE_FORMAT(LR.LR_REGDATE,'%Y-%m-%d %H:%i:%s')LR_REGDATE, LR.L_COUNT	 			\n");
	    		sb.append(" FROM LOG_RECEIVER LR,ADDRESS_BOOK AB WHERE LR.AB_SEQ=AB.AB_SEQ AND L_SEQ IN ("+l_seq+") \n");
	    		sb.append(" ORDER BY LR_REGDATE DESC \n");
	    		System.out.println(sb.toString());
	    		rs = stmt.executeQuery(sb.toString());
	    		while(rs.next()){
	    			
	    			abBean = new AddressBookBean();
		        	abBean.setMab_seq(rs.getInt("AB_SEQ"));
		        	abBean.setMab_name(rs.getString("AB_NAME"));
		        	abBean.setMab_dept(rs.getString("AB_DEPT"));
		        	abBean.setMab_pos(rs.getString("AB_POSITION"));
		        	abBean.setMab_mobile(rs.getString("AB_MOBILE"));
		        	abBean.setMab_mail(rs.getString("AB_MAIL"));
		        	abBean.setMab_issue_receivechk(rs.getString("AB_ISSUE_CHK"));
		        	abBean.setMab_report_day_chk(rs.getString("AB_REPORT_DAY_CHK"));
		        	abBean.setMab_report_week_chk(rs.getString("AB_REPORT_WEEK_CHK"));
		        	abBean.setMab_send_date(rs.getString("LR_REGDATE"));
		        	abBean.setL_count(rs.getString("L_COUNT"));
	    			receiverList.add(abBean);
	    		}	    		
	    		lBean.setArrReceiver(receiverList);
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
		
	    return lBean;
	}
    
    /**
     * 일일 보고서 차트  데이터 
     * A타입:일일 여론변화
     * B타입:출처별 관심도
     * C타입:업무구분 주요정보
     */
	public HashMap getDailyChartData(String ir_sdate,String ir_stime, String ir_edate,String ir_etime,String typeCode)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
		ArrayList typeBChart = new ArrayList();
		HashMap dataB = new HashMap();	
		ArrayList typeCChart = new ArrayList();
		HashMap dataC = new HashMap();
		ArrayList typeDChart = new ArrayList();
		HashMap dataD = new HashMap();
		ArrayList typeEChart = new ArrayList();
		HashMap dataE = new HashMap();
		
		String first = "";
		String last = "";
		String[] arrTypeCode = typeCode.split(",");
		
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
				
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();
			
	    	if(!typeCode.equals("")){
		    	sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 9 AND IC_CODE<>0) A2                                           \n");
		    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B 														   \n");
		    	sb.append("       INNER JOIN  (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE ="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") C \n");
		    	sb.append("       INNER JOIN  (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE =4 AND IC_CODE=1) D ON (A.ID_SEQ = B.ID_SEQ AND B.ID_SEQ =C.ID_SEQ AND C.ID_SEQ =D.ID_SEQ)  \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                              \n");
		    	sb.append("      AND B.IC_TYPE =9                                                                                                  \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");															
	    	}else{
	    		sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 9 AND IC_CODE<>0) A2                                           \n");
		    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B 												    	\n");
		    	sb.append("       INNER JOIN  (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE =4 AND IC_CODE=1) C ON (A.ID_SEQ = B.ID_SEQ AND B.ID_SEQ =C.ID_SEQ)  \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                     \n");
		    	sb.append("      AND B.IC_TYPE =9                                                                                                  \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");	
	    	}
		    System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataA = new HashMap();
	    		dataA.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataA.put("CNT", rs.getString("CNT"));
	    		typeAChart.add(dataA);
	    	}
	    	ChartDataHm.put("A",typeAChart);
	    	
	    	
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();
	    	if(!typeCode.equals("")){
		    	sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE<>0) A2                                           \n");
		    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B 														   \n");
		    	sb.append("       INNER JOIN  (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE ="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") C \n");
		    	sb.append("       INNER JOIN  (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE =4 AND IC_CODE=1) D ON (A.ID_SEQ = B.ID_SEQ AND B.ID_SEQ =C.ID_SEQ AND C.ID_SEQ =D.ID_SEQ)  \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                              \n");
		    	sb.append("      AND B.IC_TYPE =6                                                                                                  \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");																			
	    	}else{
	    		sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE<>0) A2                                           \n");
		    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B 												    	\n");
		    	sb.append("       INNER JOIN  (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE =4 AND IC_CODE=1) C ON (A.ID_SEQ = B.ID_SEQ AND B.ID_SEQ =C.ID_SEQ)  \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                     \n");
		    	sb.append("      AND B.IC_TYPE =6                                                                                                  \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");	
	    	}
	    	System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataB = new HashMap();
	    		dataB.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataB.put("CNT", rs.getString("CNT"));
	    		typeBChart.add(dataB);
	    	}
	    	ChartDataHm.put("B",typeBChart);
   	  	   		    	
	    	
	    	   	
	    	
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
	
	
	public HashMap getDailyChartData_TMP(String ir_sdate,String ir_stime, String ir_edate,String ir_etime,String typeCode)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
		ArrayList typeBChart = new ArrayList();
		HashMap dataB = new HashMap();	
		ArrayList typeCChart = new ArrayList();
		HashMap dataC = new HashMap();
		ArrayList typeDChart = new ArrayList();
		HashMap dataD = new HashMap();
		ArrayList typeEChart = new ArrayList();
		HashMap dataE = new HashMap();
		
		String first = "";
		String last = "";
		String[] arrTypeCode = typeCode.split(",");
		
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
				
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();
			
	    	sb.append("SELECT '긍정' AS CATEGORY, 204 AS CNT  UNION\n");
	    	sb.append("SELECT '중립' AS CATEGORY, 4 AS CNT  UNION\n");
	    	sb.append("SELECT '부정' AS CATEGORY, 3 AS CNT\n");
	    	 
	    	 
		    System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataA = new HashMap();
	    		dataA.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataA.put("CNT", rs.getString("CNT"));
	    		typeAChart.add(dataA);
	    	}
	    	ChartDataHm.put("A",typeAChart);
	    	
	    	
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();
	    	
	    	sb.append("SELECT '켜뮤니티' AS CATEGORY, 1 AS CNT  UNION\n");
	    	sb.append("SELECT '블로그' AS CATEGORY, 46 AS CNT  UNION\n");
	    	sb.append("SELECT '언론/방송' AS CATEGORY, 160 AS CNT UNION\n");
	    	sb.append("SELECT '카페' AS CATEGORY, 8 AS CNT\n");
	    	
	    	System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataB = new HashMap();
	    		dataB.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataB.put("CNT", rs.getString("CNT"));
	    		typeBChart.add(dataB);
	    	}
	    	ChartDataHm.put("B",typeBChart);
   	  	   		    	
	    	
	    	   	
	    	
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
     * 주간 보고서 차트  데이터 
     * A타입:우호도
     * B타입:출처별 관심도
     * C타입:사업 부분별 관심도
     * D타입:정보 유형별 관심도
     */
	public HashMap getWeeklyChartData(String ir_sdate,String ir_stime, String ir_edate,String ir_etime,String typeCode)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
		ArrayList typeBChart = new ArrayList();
		HashMap dataB = new HashMap();	
		ArrayList typeCChart = new ArrayList();
		HashMap dataC = new HashMap();
		ArrayList typeDChart = new ArrayList();
		HashMap dataD = new HashMap();
		ArrayList typeEChart = new ArrayList();
		HashMap dataE = new HashMap();
		
		String first = "";
		String last = "";
		
		String[] arrTypeCode = typeCode.split(",");
		
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();	    					    

			first = du.getDate(ir_sdate,"yyyyMMdd");
			last = du.getDate(ir_edate,"yyyyMMdd");
			
			diffDay = -du.DateDiff("yyyyMMdd",first,last);
			intDiffDay = Integer.parseInt(String.valueOf(diffDay));
			
			if(intDiffDay>0)
			{
				date  = new String[intDiffDay+1];
				for(int i = 0; i<intDiffDay+1; i++)
				{
					date[i] = du.addDay(first, i , "yyyyMMdd");
					tempHash = new HashMap();
					tempHash.put("CATEGORY", date[i]);
					tempHash.put("PCNT","0");
					tempHash.put("NCNT","0");
					tempHash.put("TCNT","0");							
					typeAChart.add(tempHash);
				}
			}									
	    	
	    	sb = new StringBuffer();
	    	/**
	    	 * 우호도동향 ISSUE_CODE : IC_CODE = 9 : 우호도
	    	 */
	    	/*
	    	if(!typeCode.equals("")){
		    	sb.append(" SELECT  A1.MD_DATE AS CATEGORY, COUNT(ID_SEQ) TCNT , '계' NAME1                       \n");
		    	sb.append(" 		, SUM(PCNT) PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) NAME2     \n");
		    	sb.append(" 		, SUM(NCNT) NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) NAME3                       \n");
		    	sb.append(" 		, SUM(ECNT) ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) NAME4                      \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("     SELECT  A2.ID_SEQ, A2.MD_DATE, IF(B2.IC_CODE=1,1,0) PCNT, IF(B2.IC_CODE=2,1,0) NCNT, IF(B2.IC_CODE=3,1,0) ECNT 	\n");
		    	sb.append("     FROM                                                                                                               \n");
		    	sb.append("     ( SELECT A.ID_SEQ,DATE_FORMAT(MD_DATE,'%Y%m%d')AS MD_DATE FROM ISSUE_DATA A INNER JOIN ISSUE_DATA_CODE B ON(A.ID_SEQ=B.ID_SEQ)            \n");
		    	sb.append("       WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"' AND B.IC_TYPE = "+arrTypeCode[0]+" AND B.IC_CODE = "+arrTypeCode[1]+"     \n");
		    	sb.append("      )A2                                                            	                                                                    \n");
		    	sb.append("     ,(SELECT ID_SEQ,IC_CODE,IC_TYPE FROM ISSUE_DATA_CODE WHERE IC_TYPE=9)B2                                            \n");
		    	sb.append("     WHERE                                                                                                              \n");
		    	sb.append("         A2.ID_SEQ = B2.ID_SEQ                                                                                      \n");
		    	sb.append(" ) A1                                                                                                                   \n");
		    	sb.append(" GROUP BY MD_DATE                                                                                                    \n");
		    	sb.append(" ORDER BY MD_DATE                                                                                                    \n");
	    	}else{
	    		sb.append(" SELECT  A1.MD_DATE AS CATEGORY, COUNT(ID_SEQ) TCNT, '계' NAME1                        \n");
		    	sb.append(" 		, SUM(PCNT) PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) NAME2     				\n");
		    	sb.append(" 		, SUM(NCNT) NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) NAME3                       \n");
		    	sb.append(" 		, SUM(ECNT) ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) NAME4                      \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("     SELECT  A2.ID_SEQ, A2.MD_DATE, IF(B2.IC_CODE=1,1,0) PCNT, IF(B2.IC_CODE=2,1,0) NCNT, IF(B2.IC_CODE=3,1,0) ECNT 	\n");
		    	sb.append("     FROM                                                                                                               \n");
		    	sb.append("     ( SELECT ID_SEQ,DATE_FORMAT(MD_DATE,'%Y%m%d')AS MD_DATE FROM ISSUE_DATA                                      \n");
		    	sb.append("       WHERE MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                         \n");
		    	sb.append("      )A2                                                            	                                               \n");
		    	sb.append("     ,(SELECT ID_SEQ,IC_CODE,IC_TYPE FROM ISSUE_DATA_CODE WHERE IC_TYPE=9)B2                                            \n");
		    	sb.append("     WHERE                                                                                                              \n");
		    	sb.append("         A2.ID_SEQ = B2.ID_SEQ                                                                                      \n");
		    	sb.append(" ) A1                                                                                                                   \n");
		    	sb.append(" GROUP BY MD_DATE                                                                                                    \n");
		    	sb.append(" ORDER BY MD_DATE                                                                                                    \n");
	    	}
	    	*/
	    	
	    	sb.append("SELECT DATE_FORMAT(MD_DATE, '%Y%m%d') AS CATEGORY											\n");
	    	sb.append("     , COUNT(*) AS TCNT																		\n");
	    	sb.append("     , '계' AS NAME1																			\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=1, 1,0)) AS PCNT														\n");
	    	sb.append("     , '긍정' AS NAME2																		\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=2, 1,0)) AS NCNT														\n");
	    	sb.append("     , '부정' AS NAME3																		\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=3, 1,0)) AS ECNT														\n");
	    	sb.append("     , '중립' AS NAME4																		\n");
	    	sb.append("  FROM ISSUE_DATA A																			\n");
	    	if(!typeCode.equals("")){
	    		sb.append("       INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ AND IC_TYPE = "+arrTypeCode[0]+" AND IC_CODE = "+arrTypeCode[1]+"	\n");
	    	}
	    	sb.append("     , ISSUE_DATA_CODE B																		\n");
	    	sb.append(" WHERE A.ID_SEQ = B.ID_SEQ																	\n");
	    	sb.append("   AND MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'			\n");  
	    	sb.append("   AND B.IC_TYPE = 9																			\n");
	    	sb.append(" GROUP BY DATE_FORMAT(MD_DATE, '%Y%m%d')														\n");
	    	sb.append(" ORDER BY MD_DATE																			\n");
	    	
	    	
	    	
	    	
	    	
	    	
																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
	    	while(rs.next()){
	    		
	    		for(int i =0;i<typeAChart.size();i++)
	    		{
	    			
	    			dataA = new HashMap();
	    			dataA =(HashMap)typeAChart.get(i);
	    			//System.out.println("date : "+(String)dataA.get("1"));
	    			if((rs.getString("CATEGORY")).equals((String)dataA.get("CATEGORY")))
	    			{
	    				dataA.put("PCNT",rs.getString("PCNT"));
	    				dataA.put("NAME1",rs.getString("NAME1"));
	    				dataA.put("NCNT",rs.getString("NCNT"));
	    				dataA.put("NAME2",rs.getString("NAME2"));
	    				dataA.put("TCNT",rs.getString("TCNT"));
	    				dataA.put("NAME3",rs.getString("NAME3"));
	    			}else{
	    				dataA.put("NAME1",rs.getString("NAME1"));
	    				dataA.put("NAME2",rs.getString("NAME2"));
	    				dataA.put("NAME3",rs.getString("NAME3"));
	    				
	    			}
	    			typeAChart.set(i, dataA);	    			
	    		}	    		
	    	}    		    	
	    	ChartDataHm.put("A",typeAChart);
	    	
	    	first = du.getDate(ir_sdate,"yyyyMMdd");
			last = du.getDate(ir_edate,"yyyyMMdd");
			
			diffDay = -du.DateDiff("yyyyMMdd",first,last);
			intDiffDay = Integer.parseInt(String.valueOf(diffDay));
			
			if(intDiffDay>0)
			{
				date  = new String[intDiffDay+1];
				for(int i = 0; i<intDiffDay+1; i++)
				{
					date[i] = du.addDay(first, i , "yyyyMMdd");
					tempHash = new HashMap();
					tempHash.put("CATEGORY", date[i]);
					tempHash.put("PCNT","0");
					tempHash.put("NCNT","0");
					tempHash.put("TCNT","0");							
					typeBChart.add(tempHash);
				}
			}									
	    	
			rs = null;
			pstmt = null;
	    	sb = new StringBuffer();
	    	
	    	/**
	    	 * 출처별 관심도 동향 ISSUE_CODE : IC_CODE = 6 : 출처별
	    	 */
	    	/*
	    	if(!typeCode.equals("")){
		    	sb.append(" SELECT  A1.MD_DATE AS CATEGORY, SUM(CNT1) CNT1, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =1) AS NAME1 \n");
		    	sb.append("         , SUM(CNT2) CNT2, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =2) AS NAME2                  \n");
		    	sb.append("         , SUM(CNT3) CNT3, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =3) AS NAME3                  \n");
		    	sb.append("         , SUM(CNT4) CNT4, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =4) AS NAME4                  \n");
		    	sb.append("         , SUM(CNT5) CNT5, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =5) AS NAME5                  \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("     SELECT  A2.ID_SEQ, A2.MD_DATE, IF(B2.IC_CODE=1,1,0) CNT1, IF(B2.IC_CODE=2,1,0) CNT2, IF(B2.IC_CODE=3,1,0) CNT3 \n");
		    	sb.append("             ,IF(B2.IC_CODE=4,1,0) CNT4, IF(B2.IC_CODE=5,1,0) CNT5, IF(B2.IC_CODE=6,1,0) CNT6 			\n");
		    	sb.append("     FROM                                                                                                               \n");
		    	sb.append("     ( SELECT A.ID_SEQ,DATE_FORMAT(MD_DATE,'%Y%m%d')AS MD_DATE FROM ISSUE_DATA A INNER JOIN ISSUE_DATA_CODE B ON(A.ID_SEQ=B.ID_SEQ)            \n");
		    	sb.append("       WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"' AND B.IC_TYPE = "+arrTypeCode[0]+" AND B.IC_CODE = "+arrTypeCode[1]+" \n");
		    	sb.append("      )A2                                                            	                                               \n");
		    	sb.append("     ,(SELECT ID_SEQ,IC_CODE,IC_TYPE FROM ISSUE_DATA_CODE WHERE IC_TYPE=6)B2                                            \n");
		    	sb.append("     WHERE                                                                                                              \n");
		    	sb.append("         A2.ID_SEQ = B2.ID_SEQ                                                                                       \n");
		       	sb.append(" ) A1                                                                                                                   \n");
		    	sb.append(" GROUP BY MD_DATE                                                                                                    \n");
		    	sb.append(" ORDER BY MD_DATE                                                                                                    \n");
	    	}else{
	    		sb.append(" SELECT  A1.MD_DATE AS CATEGORY, SUM(CNT1) CNT1, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =1) AS NAME1 \n");
		    	sb.append("         , SUM(CNT2) CNT2, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =2) AS NAME2                  \n");
		    	sb.append("         , SUM(CNT3) CNT3, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =3) AS NAME3                  \n");
		    	sb.append("         , SUM(CNT4) CNT4, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =4) AS NAME4                  \n");
		    	sb.append("         , SUM(CNT5) CNT5, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=6 AND IC_CODE =5) AS NAME5                  \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("     SELECT  A2.ID_SEQ, A2.MD_DATE, IF(B2.IC_CODE=1,1,0) CNT1, IF(B2.IC_CODE=2,1,0) CNT2, IF(B2.IC_CODE=3,1,0) CNT3 \n");
		    	sb.append("             ,IF(B2.IC_CODE=4,1,0) CNT4, IF(B2.IC_CODE=5,1,0) CNT5, IF(B2.IC_CODE=6,1,0) CNT6 			\n");
		    	sb.append("     FROM                                                                                                               \n");
		    	sb.append("     ( SELECT ID_SEQ,DATE_FORMAT(MD_DATE,'%Y%m%d')AS MD_DATE FROM ISSUE_DATA                                      \n");
		    	sb.append("       WHERE MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                         \n");
		    	sb.append("      )A2                                                            	                                               \n");
		    	sb.append("     ,(SELECT ID_SEQ,IC_CODE,IC_TYPE FROM ISSUE_DATA_CODE WHERE IC_TYPE=6)B2                                            \n");
		    	sb.append("     WHERE                                                                                                              \n");
		    	sb.append("         A2.ID_SEQ = B2.ID_SEQ                                                                                      \n");
		    	sb.append(" ) A1                                                                                                                   \n");
		    	sb.append(" GROUP BY MD_DATE                                                                                                    \n");
		    	sb.append(" ORDER BY MD_DATE                                                                                                    \n");
		    	
	    	}
	    	*/
	    	
	    	sb.append("SELECT DATE_FORMAT(MD_DATE, '%Y%m%d') AS CATEGORY						\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=1, 1,0)) AS CNT1									\n");
	    	sb.append("     , '언론&포털' AS NAME1												\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=2, 1,0)) AS CNT2									\n");
	    	sb.append("     , '트위터' AS NAME2													\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=3, 1,0)) AS CNT3									\n");
	    	sb.append("     , '블로그' AS NAME3													\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=4, 1,0)) AS CNT4									\n");
	    	sb.append("     , '카페&커뮤니티' AS NAME4											\n");
	    	sb.append("     , SUM(IF(B.IC_CODE=5, 1,0)) AS CNT5									\n");
	    	sb.append("     , '공공기관' AS NAME5												\n");
	    	sb.append("  FROM ISSUE_DATA A														\n");
	    	if(!typeCode.equals("")){
	    		sb.append("       INNER JOIN ISSUE_DATA_CODE C ON A.ID_SEQ = C.ID_SEQ AND IC_TYPE = "+arrTypeCode[0]+" AND IC_CODE = "+arrTypeCode[1]+"	\n");
	    	}
	    	sb.append("     , ISSUE_DATA_CODE B													\n");
	    	sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
	    	sb.append("   AND MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n");  
	    	sb.append("   AND B.IC_TYPE = 6														\n");
	    	sb.append(" GROUP BY DATE_FORMAT(MD_DATE, '%Y%m%d')									\n");
	    	sb.append(" ORDER BY MD_DATE														\n");
	   
	    	
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
	    	while(rs.next()){
	    		
	    		for(int i =0;i<typeBChart.size();i++)
	    		{
	    			
	    			dataB = new HashMap();
	    			dataB =(HashMap)typeBChart.get(i);
	    			//System.out.println("date : "+(String)dataA.get("1"));
	    			if((rs.getString("CATEGORY")).equals((String)dataB.get("CATEGORY")))
	    			{
	    				dataB.put("CNT1",rs.getString("CNT1"));
	    				dataB.put("NAME1",rs.getString("NAME1"));
	    				dataB.put("CNT2",rs.getString("CNT2"));
	    				dataB.put("NAME2",rs.getString("NAME2"));
	    				dataB.put("CNT3",rs.getString("CNT3"));
	    				dataB.put("NAME3",rs.getString("NAME3"));
	    				dataB.put("CNT4",rs.getString("CNT4"));
	    				dataB.put("NAME4",rs.getString("NAME4"));
	    				dataB.put("CNT5",rs.getString("CNT5"));
	    				dataB.put("NAME5",rs.getString("NAME5"));
	    				
	    			}else{
	    				dataB.put("NAME1",rs.getString("NAME1"));
	    				dataB.put("NAME2",rs.getString("NAME2"));
	    				dataB.put("NAME3",rs.getString("NAME3"));
	    				dataB.put("NAME4",rs.getString("NAME4"));
	    				dataB.put("NAME5",rs.getString("NAME5"));
	    				
	    			}
	    			typeBChart.set(i, dataB);	    			
	    		}	    		
	    	}    		    	
	    	ChartDataHm.put("B",typeBChart);	
	    	
	    	/*
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();
	    	
	    	if(!typeCode.equals("")){
		    	sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 3 AND IC_CODE<>0) A2                                           \n");
		    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT B.IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B 														   \n");
		    	sb.append("       INNER JOIN  (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE ="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") C  \n");
		    	sb.append("       ON (A.ID_SEQ = B.ID_SEQ AND B.ID_SEQ =C.ID_SEQ)  \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                              \n");
		    	sb.append("      AND B.IC_TYPE =3                                                                                                  \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");																			
	    	}else{
	    		sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 3 AND IC_CODE<>0) A2                                           \n");
		    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT B.IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B 												    	\n");
		    	sb.append("       ON (A.ID_SEQ = B.ID_SEQ)  \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                     \n");
		    	sb.append("      AND B.IC_TYPE =3                                                                                                  \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");	
	    	}															
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataC = new HashMap();
	    		dataC.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataC.put("CNT", rs.getString("CNT"));
	    		typeCChart.add(dataC);
	    	}
	    	ChartDataHm.put("C",typeCChart);
	    	*/
	    	/*
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();
	    	
	    	 //유형별 점유율 ISSUE_CODE : IC_CODE = 7 : 유형별
	    	 
	    	if(!typeCode.equals("")){
		    	sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 7 AND IC_CODE<>0 AND IC_USEYN = 'Y') A2                                           \n");
		    	sb.append(" INNER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT B.IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B INNER JOIN														   \n");
		    	sb.append("       (SELECT ID_SEQ FROM ISSUE_DATA_CODE WHERE IC_TYPE ="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") C ON (A.ID_SEQ = B.ID_SEQ AND A.ID_SEQ =C.ID_SEQ)                                                \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                              \n");
		    	sb.append("      AND B.IC_TYPE =7                                                                                                  \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");																			
	    	}else{
	    		sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
		    	sb.append(" FROM                                                                                                                   \n");
		    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 7 AND IC_CODE<>0 AND IC_USEYN = 'Y') A2                                           \n");
		    	sb.append(" INNER JOIN                                                                                                        \n");
		    	sb.append(" (                                                                                                                      \n");
		    	sb.append("  SELECT B.IC_CODE, COUNT(*) CNT                                                                                          \n");
		    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B 												    	\n");
		    	sb.append("       ON (A.ID_SEQ = B.ID_SEQ)  \n");
		    	sb.append("  WHERE                                                                                                                 \n");
		    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'                                     \n");
		    	sb.append("      AND B.IC_TYPE =7                                                                                                 \n");
		    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
		    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");	
	    	}																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataD = new HashMap();
	    		dataD.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataD.put("CNT", rs.getString("CNT"));
	    		typeDChart.add(dataD);
	    	}
	    	ChartDataHm.put("D",typeDChart);
	    	*/
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
     * 이슈 보고서 차트  데이터 
     * A타입:일일 여론변화
     * B타입:출처별 관심도
     * C타입:업무구분 주요정보
     */
	public HashMap getIssueChartData(String ir_sdate,String ir_stime, String ir_edate,String ir_etime,String i_seq)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
		ArrayList typeBChart = new ArrayList();
		HashMap dataB = new HashMap();	
		ArrayList typeCChart = new ArrayList();
		HashMap dataC = new HashMap();
		ArrayList typeDChart = new ArrayList();
		HashMap dataD = new HashMap();
		ArrayList typeEChart = new ArrayList();
		HashMap dataE = new HashMap();
		
		String first = "";
		String last = "";
		
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
			
			first = du.getDate(ir_sdate,"yyyyMMdd");
			last = du.getDate(ir_edate,"yyyyMMdd");
			
			diffDay = -du.DateDiff("yyyyMMdd",first,last);
			intDiffDay = Integer.parseInt(String.valueOf(diffDay));
			
			if(intDiffDay>0)
			{
				date  = new String[intDiffDay+1];
				for(int i = 0; i<intDiffDay+1; i++)
				{
					date[i] = du.addDay(first, i , "yyyyMMdd");
					tempHash = new HashMap();
					tempHash.put("CATEGORY", date[i]);
					tempHash.put("PCNT","0");
					tempHash.put("NCNT","0");
					tempHash.put("TCNT","0");							
					typeAChart.add(tempHash);
				}
			}									
	    	
	    	sb = new StringBuffer();
	    	sb.append(" SELECT  A1.MD_DATE AS CATEGORY, COUNT(ID_SEQ) TCNT , '계' NAME1                       \n");
	    	sb.append(" 		, SUM(PCNT) PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) NAME2     \n");
	    	sb.append(" 		, SUM(NCNT) NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) NAME3                       \n");
	    	sb.append(" 		, SUM(ECNT) ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) NAME4                      \n");
	    	sb.append(" FROM                                                                                                                   \n");
	    	sb.append(" (                                                                                                                      \n");
	    	sb.append("     SELECT  A2.ID_SEQ, A2.MD_DATE, IF(B2.IC_CODE=1,1,0) PCNT, IF(B2.IC_CODE=2,1,0) NCNT, IF(B2.IC_CODE=3,1,0) ECNT 	\n");
	    	sb.append("     FROM                                                                                                               \n");
	    	sb.append("     ( SELECT ID_SEQ,DATE_FORMAT(MD_DATE,'%Y%m%d')AS MD_DATE FROM ISSUE_DATA                                      \n");
	    	sb.append("       WHERE MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'  AND I_SEQ ="+i_seq+"                                       \n");
	    	sb.append("      )A2                                                            	                                               \n");
	    	sb.append("     ,(SELECT ID_SEQ,IC_CODE,IC_TYPE FROM ISSUE_DATA_CODE WHERE IC_TYPE=9)B2                                            \n");
	    	sb.append("     WHERE                                                                                                              \n");
	    	sb.append("         A2.ID_SEQ = B2.ID_SEQ                                                                                          \n");
	    	sb.append(" ) A1                                                                                                                   \n");
	    	sb.append(" GROUP BY MD_DATE                                                                                                    \n");
	    	sb.append(" ORDER BY MD_DATE                                                                                                    \n");
																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
	    	while(rs.next()){
	    		
	    		for(int i =0;i<typeAChart.size();i++)
	    		{
	    			
	    			dataA = new HashMap();
	    			dataA =(HashMap)typeAChart.get(i);
	    			//System.out.println("date : "+(String)dataA.get("1"));
	    			if((rs.getString("CATEGORY")).equals((String)dataA.get("CATEGORY")))
	    			{
	    				dataA.put("TCNT",rs.getString("TCNT"));
	    				dataA.put("NAME1",rs.getString("NAME1"));
	    				dataA.put("PCNT",rs.getString("PCNT"));
	    				dataA.put("NAME2",rs.getString("NAME2"));
	    				dataA.put("NCNT",rs.getString("NCNT"));
	    				dataA.put("NAME3",rs.getString("NAME3"));
	    				dataA.put("ECNT",rs.getString("ECNT"));
	    				dataA.put("NAME4",rs.getString("NAME4"));
	    			}else{
	    				dataA.put("NAME1",rs.getString("NAME1"));
	    				dataA.put("NAME2",rs.getString("NAME2"));
	    				dataA.put("NAME3",rs.getString("NAME3"));
	    				dataA.put("NAME4",rs.getString("NAME4"));	    				
	    			}
	    			typeAChart.set(i, dataA);	    			
	    		}	    		
	    	}    		    	
	    	ChartDataHm.put("A",typeAChart);			
		    

	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();	    
    		sb.append(" SELECT A2.IC_NAME AS CATEGORY, IFNULL(B2.CNT,0) AS CNT                                                                                \n");
	    	sb.append(" FROM                                                                                                                   \n");
	    	sb.append(" (SELECT IC_NAME,IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 9 AND IC_CODE<>0) A2                                           \n");
	    	sb.append(" LEFT OUTER JOIN                                                                                                        \n");
	    	sb.append(" (                                                                                                                      \n");
	    	sb.append("  SELECT IC_CODE, COUNT(*) CNT                                                                                          \n");
	    	sb.append("  FROM ISSUE_DATA A INNER JOIN  ISSUE_DATA_CODE B ON (A.ID_SEQ = B.ID_SEQ )										    	\n");
	    	sb.append("  WHERE                                                                                                                 \n");
	    	sb.append("      A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"' AND A.I_SEQ ="+i_seq+"                                     \n");
	    	sb.append("      AND B.IC_TYPE =9                                                                                                  \n");
	    	sb.append("  GROUP BY B.IC_CODE                                                                                                    \n");
	    	sb.append("  )B2 ON (A2.IC_CODE =B2.IC_CODE)                                                                                       \n");	
	    	
	    	System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataB = new HashMap();
	    		dataB.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataB.put("CNT", rs.getString("CNT"));
	    		typeBChart.add(dataB);
	    	}
	    	ChartDataHm.put("B",typeBChart);
	    	
	    	rs = null;
			pstmt = null;
	    	sb = new StringBuffer();	    
	    	sb.append(" SELECT A1.IC_NAME AS CATEGORY                                                                                   \n");
	    	sb.append("        ,IFNULL(A2.PCNT,0) PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1                                                                 \n");
	    	sb.append("        ,IFNULL(A2.NCNT,0) NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2                                                                 \n");
	    	sb.append("        ,IFNULL(A2.ECNT,0) ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3                                                                 \n");
	    	sb.append(" FROM                                                                                                            \n");
	    	sb.append("     (SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE<>0) A1 LEFT OUTER JOIN               \n");
	    	sb.append("     (                                                                                                           \n");
	    	sb.append("     SELECT C.IC_CODE , SUM(IF(B.IC_CODE=1,1,0))PCNT, SUM(IF(B.IC_CODE=2,1,0))NCNT, SUM(IF(B.IC_CODE=3,1,0))ECNT \n");
	    	sb.append("     FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C                                                     \n");
	    	sb.append("     WHERE                                                                                                       \n");
	    	sb.append("          A.ID_SEQ = B.ID_SEQ                                                                                    \n");
	    	sb.append("          AND B.ID_SEQ = C.ID_SEQ                                                                                \n");
	    	sb.append("          AND B.IC_TYPE = 9                                                                                      \n");
	    	sb.append("          AND C.IC_TYPE = 6                                                                                      \n");
	    	sb.append("          AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"' AND A.I_SEQ ="+i_seq+"                               \n");
	    	sb.append("     GROUP BY C.IC_CODE                                                                                          \n");
	    	sb.append("     )A2 ON(A1.IC_CODE=A2.IC_CODE)                                                                               \n");
	    	sb.append(" ORDER BY A1.IC_CODE ASC                                                                                         \n");
	    	
	    	System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
	    	while(rs.next()){
	    		dataC = new HashMap();
	    		dataC.put("CATEGORY", rs.getString("CATEGORY"));
	    		dataC.put("PCNT", rs.getString("PCNT"));
	    		dataC.put("NAME1", rs.getString("NAME1"));
	    		dataC.put("NCNT", rs.getString("NCNT"));
	    		dataC.put("NAME2", rs.getString("NAME2"));
	    		dataC.put("ECNT", rs.getString("ECNT"));
	    		dataC.put("NAME3", rs.getString("NAME3"));
	    		typeCChart.add(dataC);
	    	}
	    	ChartDataHm.put("C",typeCChart);
	    	   	
	    	
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
     * 기간 보고서 차트  데이터 
     * A타입:일일 여론변화
     * B타입:출처별 관심도
     * C타입:업무구분 주요정보
     */
	public HashMap getPeoridChartData(String ir_sdate,String ir_stime, String ir_edate,String ir_etime,String k_xp,String k_yp,String k_zp,String sg_seq)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
		ArrayList typeBChart = new ArrayList();
		HashMap dataB = new HashMap();	
		ArrayList typeCChart = new ArrayList();
		HashMap dataC = new HashMap();
		ArrayList typeDChart = new ArrayList();
		HashMap dataD = new HashMap();
		ArrayList typeEChart = new ArrayList();
		HashMap dataE = new HashMap();
		
		String tmpTime = "";
		String first = "";
		String last = "";
		getMaxMinNo( ir_sdate, ir_edate );
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
				
			
			for(int i = 0; i<23; i++)
			{			
				tempHash = new HashMap();
				
				if(i+1>=10){tmpTime =String.valueOf(i+1);}
				else{tmpTime ="0"+(i+1);}
				
				System.out.println("tmpTime:"+tmpTime);
				
				tempHash.put("CATEGORY", tmpTime);
				tempHash.put("CNT","0");
										
				typeAChart.add(tempHash);
			}					
	    	
	    	sb = new StringBuffer();
	    	sb.append("SELECT MD_DATE CATEGORY, COUNT(*) AS CNT, (SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ =RESULT.SG_SEQ) NAME\n");
	    	sb.append("FROM                                                                                        \n");
	    	sb.append("(                                                                                           \n");
	    	sb.append("  SELECT				                                                    \n");
	    	sb.append("      MIN(DISTINCT A.MD_SEQ) AS MD_SEQ ,B.S_SEQ ,B.MD_SITE_NAME ,B.MD_MENU_NAME ,B.MD_TYPE  \n");
	    	sb.append("      ,DATE_FORMAT(B.MD_DATE,'%H') AS MD_DATE ,B.MD_IMG ,B.MD_SAME_COUNT              \n");
	    	sb.append("      ,B.MD_PSEQ ,B.MD_TITLE ,B.MD_URL , SG_SEQ, ISSUE_CHECK, I_DELDATE, M_SEQ              \n");
	    	sb.append("  FROM IDX A, META B		                                                            \n");
	    	sb.append("  WHERE A.MD_SEQ BETWEEN "+msMinNo+" AND "+msMaxNo+"                                            \n");
	    	sb.append("       AND A.MD_SEQ = B.MD_SEQ                                                              \n");
	    	if(k_xp.length()>0)
	    	sb.append("       AND A.K_XP   IN ("+k_xp+")                                           \n");
	    	if(k_yp.length()>0)
	    	sb.append("       AND A.K_YP   IN ("+k_yp+")                                           \n");
	    	if(k_zp.length()>0)
	    	sb.append("       AND A.K_ZP   IN ("+k_zp+")                                           \n");
	    	if(sg_seq.length()>0)
	    	sb.append("       AND A.SG_SEQ IN ("+sg_seq+")                                                            \n");
	    	sb.append("       AND B.MD_SEQ = B.MD_SEQ                                                              \n");
	    	sb.append("       AND (A.I_STATUS = 'N')                                             \n");
	    	sb.append("  GROUP BY B.MD_PSEQ                                                                        \n");
	    	sb.append(") RESULT                                                                                    \n");
	    	sb.append("GROUP BY MD_DATE                                                                            \n");
																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
	    	while(rs.next()){
	    		
	    		for(int i =0;i<typeAChart.size();i++)
	    		{
	    			
	    			dataA = new HashMap();
	    			dataA =(HashMap)typeAChart.get(i);
	    			//System.out.println("date : "+(String)dataA.get("1"));
	    			if((rs.getString("CATEGORY")).equals((String)dataA.get("CATEGORY")))
	    			{
	    				dataA.put("NAME",rs.getString("NAME"));	
	    				dataA.put("CNT",rs.getString("CNT"));	    				
	    			}else{
	    				dataA.put("NAME",rs.getString("NAME"));	
	    			}
	    			typeAChart.set(i, dataA);	    			
	    		}	    		
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
	
	public void getMaxMinNo( String psSDate, String psEdate ) {

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
             
            sb = new StringBuffer();                    
            sb.append(" SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+psSDate+" 00:00:00' AND '"+psSDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
                       

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {
                msMinNo = rs.getString("MIN_NO");
                msMaxNo = rs.getString("MAX_NO");
                
                if(msMinNo==null || msMinNo.equals("null")){
                	msMinNo = "0";
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
	
	public void AddCnt( String l_seq, String ab_seq ) {

        try {
        	dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
            sb = new StringBuffer();                    
            sb.append(" UPDATE LOG_RECEIVER SET L_COUNT = L_COUNT+1 WHERE L_SEQ = "+l_seq+" AND AB_SEQ = "+ab_seq+" \n");
            
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
    }
	
}//IssueReportMgr End.
