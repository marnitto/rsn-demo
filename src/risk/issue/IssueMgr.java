package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import risk.DBconn.DBconn;
import risk.statistics.StatisticsSuperBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class IssueMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private IssueBean isBean = null;
	private IssueTitleBean itBean = null;
	private IssueDataBean idBean = null;
	private IssueCodeBean icBean = null;
	private IssueCommentBean icmBean = null;
	private int startNum = 0;
    private int endNum = 0;
	private int totalIssueCnt = 0;
	private int totalIssueTitleCnt = 0;
	private int totalIssueDataCnt = 0;
	private int totalSameDataCnt = 0;
	
	ArrayList arraySourceList = new ArrayList();
	
	/**
     * 어레이 호출 함수의 정보 카운트
     * @return
     */
	public int getTotalIssueCnt() {
		return totalIssueCnt;
	}

	public int getTotalIssueTitleCnt() {
		return totalIssueTitleCnt;
	}

	public int getTotalIssueDataCnt() {
		return totalIssueDataCnt;
	}
	
	public int getTotalSameDataCnt() {
		return totalSameDataCnt;
	}
	
	public ArrayList getSourceCnt(){
		return arraySourceList;
	}

	/**
     * ISSUE 등록하고 해당번호를 반환
     * @param i_seq : ISSUE.I_SEQ
     * @return
     */
	public boolean insertIssue(IssueBean isBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO ISSUE(	                            		   \n");				
			sb.append("                   I_TITLE                                  \n");
			sb.append("                   ,I_REGDATE                               \n");			
			sb.append("                   ,I_USEYN                                 \n");
			sb.append("                   ,M_SEQ)                                  \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("        '"+isBean.getI_title()+"'                           \n");			
			sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'     \n");
			sb.append("        ,'"+isBean.getI_useyn()+"'                          \n");				
			sb.append("        ,"+isBean.getM_seq()+"                              \n");
			sb.append(" )                                                          \n");
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																
			
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
     * ISSUE 등록하고 해당번호를 반환
     * @param IssueDataBean
     * @return
     */
	public boolean updateIssue(IssueBean isBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append("UPDATE ISSUE SET I_TITLE = '"+isBean.getI_title()+"' WHERE I_SEQ IN ("+isBean.getI_seq()+")  \n");			
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;													
			
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
     * ISSUE 의 사용 상태를 변경한다. 
     * @param IssueDataBean
     * @return
     */
	public boolean updateIssueFlag(IssueBean isBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			String nextFalg = "Y";
			String str = "SELECT I_USEYN FROM ISSUE WHERE I_SEQ IN ("+isBean.getI_seq()+")";
			rs = stmt.executeQuery(str);			
			if(rs.next()){
				if(rs.getString("I_USEYN").equals("Y"))
					nextFalg = "N";
				else
					nextFalg = "Y";
			}
			
			sb = new StringBuffer();   
			sb.append("UPDATE ISSUE SET I_USEYN = '"+nextFalg+"' WHERE I_SEQ IN ("+isBean.getI_seq()+")  \n");			
			if(stmt.executeUpdate(sb.toString())>0) result = true;													
			
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
     * ISSUE 등록하고 해당번호를 반환
     * @param IssueDataBean
     * @return
     */
	public boolean deleteIssue(IssueBean isBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_TITLE WHERE I_SEQ IN ("+isBean.getI_seq()+")");
			rs = stmt.executeQuery(sb.toString());			
			if(rs.next()){
				if(rs.getInt("CNT") == 0){
					sb = new StringBuffer(); 
					sb.append(" DELETE FROM ISSUE WHERE I_SEQ IN ("+isBean.getI_seq()+")               \n");					
					if(stmt.executeUpdate(sb.toString())>0) {
						result = true;						
					}
				}else{
					result = false;
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
		return result;
	}
	 
    /**
     * ISSUE 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param i_seq : ISSUE.I_SEQ
     * @param schKey : ISSUE.I_TITLE
     * @param schSdate : ISSUE.I_REGDATE
     * @param schEdate : ISSUE.I_REGDATE
     * @param useYn : ISSUE.I_USEYN
     * @return : 이슈검색리스트
     */
    public ArrayList getIssueList( 
    		int pageNum, 
    		int rowCnt, 
    		String i_seq, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate, 	    	
    		String useYN
    )    
    {    	
    	ArrayList issueList = new ArrayList();    	    	
    	String tmpI_seq = null;
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(I_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE                                                      \n");
			sb.append("		WHERE 1=1														\n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									\n");
			}				
			//if( !schKey.equals("") ) {
			if(  !"".equals(schKey) ) {
				sb.append("		AND	I_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	I_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND I_USEYN = '"+useYN+"'									    \n");
			}
						
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();				
			sb.append(" SELECT I_SEQ                                                         \n");				
			sb.append("        ,I_TITLE                                                      \n");						
			sb.append("        ,DATE_FORMAT(I_REGDATE,'%Y-%m-%d %H:%i:%s') AS I_REGDATE     \n");
			sb.append("        ,M_SEQ , I_USEYN                                                       \n");
			sb.append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ) AS M_NAME  \n");
			sb.append("        ,(SELECT COUNT(1) FROM ISSUE_TITLE WHERE I_SEQ = I.I_SEQ) AS I_COUNT  \n");
			sb.append(" FROM ISSUE I                                                         \n");
			sb.append(" WHERE 1=1														     \n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									 \n");
			}				
			if( !schKey.equals("") ) {
				sb.append("		AND	I_TITLE LIKE '%"+schKey+"%'  							 \n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				sb.append("		AND	I_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	 \n");
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND I_USEYN = '"+useYN+"'									     \n");
			}
			sb.append(" ORDER BY I_REGDATE DESC 		                                     \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                      \n");
			}			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				isBean = new IssueBean();
				isBean.setI_seq(rs.getString("I_SEQ"));
				isBean.setI_title(rs.getString("I_TITLE"));
				isBean.setI_regdate(rs.getString("I_REGDATE"));
				isBean.setM_seq(rs.getString("M_SEQ"));
				isBean.setI_useyn(rs.getString("I_USEYN"));
				isBean.setM_name(rs.getString("M_NAME"));
				isBean.setI_count(rs.getString("I_COUNT"));
				issueList.add(isBean);
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
	    	
	    return issueList;
	 }
	
	/**
     * ISSUE_TITLE 등록하고 해당번호를 반환
     * @param IssueTitleBean
     * @return
     */
	 public boolean insertIssueTitle(IssueTitleBean itBean)
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
			sb.append(" INSERT INTO ISSUE_TITLE(	                      		   \n");				
			sb.append("                         IT_TITLE                           \n");
			sb.append("                         ,I_SEQ                             \n");
			sb.append("                         ,IT_REGDATE                        \n");				
			sb.append("                         ,IT_USEYN                          \n");
			sb.append("                         ,M_SEQ)                            \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("        '"+itBean.getIt_title()+"'                          \n");
			sb.append("        ,"+itBean.getI_seq()+"                              \n");				
			sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'     \n");
			sb.append("        ,'"+itBean.getIt_useyn()+"'                         \n");				
			sb.append("        ,"+itBean.getM_seq()+"                              \n");
			sb.append(" )                                                          \n");
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;													
			
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
     * ISSUE_TITLE 수정
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @return
     */
	public boolean updateIssueTitle(IssueTitleBean itBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb = new StringBuffer();
			sb.append("UPDATE ISSUE_TITLE SET IT_TITLE = '"+itBean.getIt_title()+"'   WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");			
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																				
			
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
     * ISSUE_TITLE의 useyn 수정
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @return
     */
	public boolean updateIssueTitleFlag(IssueTitleBean itBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			String nextFalg = "Y";
			String str = "SELECT IT_USEYN FROM ISSUE_TITLE WHERE IT_SEQ IN ("+itBean.getIt_seq()+")";
			rs = stmt.executeQuery(str);			
			if(rs.next()){
				if(rs.getString("IT_USEYN").equals("Y"))
					nextFalg = "N";
				else
					nextFalg = "Y";
			}
			
			
			sb = new StringBuffer();   
			sb = new StringBuffer();
			sb.append("UPDATE ISSUE_TITLE SET IT_USEYN = '"+nextFalg+"'   WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");			
			if(stmt.executeUpdate(sb.toString())>0) result = true;																				
			
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
     * ISSUE_TITLE 삭제
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @return
     */
	public boolean deleteIssueTitle(IssueTitleBean itBean)
	{	
		boolean result = false;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb = new StringBuffer();
			sb.append(" DELETE FROM ISSUE_TITLE WHERE IT_SEQ IN ("+itBean.getIt_seq()+")     \n");			
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																				
			
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
     * ISSUE_TITLE 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param it_seq : ISSUE_TITLE.IT_SEQ
     * @param schKey : ISSUE_TITLE.IT_TITLE
     * @param schSdate : ISSUE_TITLE.IT_REGDATE
     * @param schEdate : ISSUE_TITLE.IT_REGDATE
     * @param useYn : ISSUE.IT_USEYN
     * @return : 이슈타이틀검색리스트
     */
    public ArrayList getIssueTitleList( 
    		int pageNum, 
    		int rowCnt,
    		String i_seq,
    		String it_seq, 
    		String schKey,	    	
    		String schSdate, 
    		String schEdate, 	    	
    		String useYN
    )    
    {    	
    	ArrayList issueTitleList = new ArrayList();    	    	
    	String tmpI_seq = null;
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(IT_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE_TITLE                                                      \n");
			sb.append("		WHERE 1=1														\n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	IT_SEQ IN ("+it_seq+")  									\n");
			}	
			if( !schKey.equals("") ) {
				sb.append("		AND	IT_TITLE LIKE '%"+schKey+"%'  							\n");
			}				
			if( !schSdate.equals("") && !schEdate.equals("")) {					
				sb.append("		AND	IT_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");				
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND IT_USEYN = '"+useYN+"'									    \n");
			}
						
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueTitleCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();				
			sb.append(" SELECT IT_SEQ                                                         \n");	
			sb.append("        ,I_SEQ                                                      \n");
			sb.append("        ,IT_TITLE , IT_USEYN                                                     \n");						
			sb.append("        ,DATE_FORMAT(IT_REGDATE,'%Y-%m-%d %H:%i:%s') AS IT_REGDATE     \n");
			sb.append("        ,M_SEQ                                                         \n");
			sb.append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = IT.M_SEQ) AS M_NAME  \n");
			sb.append(" FROM ISSUE_TITLE IT                                                   \n");
			sb.append(" WHERE 1=1														      \n");
			if( !i_seq.equals("") ) {
				sb.append("		AND	I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	IT_SEQ IN ("+it_seq+")  									\n");
			}				
			if( !schKey.equals("") ) {
				sb.append("		AND	IT_TITLE LIKE '%"+schKey+"%'  							 \n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				sb.append("		AND	IT_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	 \n");
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND IT_USEYN = '"+useYN+"'									     \n");
			}
			sb.append(" ORDER BY IT_REGDATE DESC 		                                     \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                      \n");
			}			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				itBean = new IssueTitleBean();
				itBean.setIt_seq(rs.getString("IT_SEQ"));
				itBean.setI_seq(rs.getString("I_SEQ"));
				itBean.setIt_title(rs.getString("IT_TITLE"));
				itBean.setIt_useyn(rs.getString("IT_USEYN"));
				itBean.setIt_regdate(rs.getString("IT_REGDATE"));
				itBean.setM_seq(rs.getString("M_SEQ"));
				itBean.setM_name(rs.getString("M_NAME"));	
				issueTitleList.add(itBean);
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
		
	    return issueTitleList;
	 }	 
    
    
    
    
    public int IssueRegistCheck(String md_pseqs)    
    {    	
    	int result = 0;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();				
			sb.append(" SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_PSEQ IN ("+md_pseqs+")	\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
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
    
    
    
	
	/**
     * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 등록하고 이슈번호 리턴
     * @param IssueDataBean :이슈데이터빈
     * @param IssueCommentBean :이슈코멘트빈
     * @param typeCode :분류체계
     * @return
     */
    public String insertIssueData(IssueDataBean idBean, IssueCommentBean icBean, String typeCode, String p_seq)
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
			sb.append(" INSERT INTO ISSUE_DATA(MD_SEQ                             	\n");
			sb.append("                   ,I_SEQ                                  	\n");
			sb.append("                   ,IT_SEQ                                 	\n");
			sb.append("                   ,S_SEQ                                  	\n");
			sb.append("                   ,SG_SEQ                                 	\n");
			sb.append("                   ,MD_SITE_NAME                           	\n");
			sb.append("                   ,MD_SITE_MENU                           	\n");
			sb.append("                   ,MD_DATE                                	\n");
			sb.append("                   ,ID_TITLE                               	\n");
			sb.append("                   ,ID_URL                                 	\n");
			sb.append("                   ,ID_WRITTER                             	\n");
			sb.append("                   ,ID_CONTENT                             	\n");
			sb.append("                   ,ID_REGDATE                             	\n");
			sb.append("                   ,ID_MAILYN                              	\n");
			sb.append("                   ,ID_USEYN                               	\n");
			sb.append("                   ,MD_SAME_CT                            	\n");
			sb.append("                   ,M_SEQ  	                               	\n");
			sb.append("                   ,MD_TYPE                                 	\n");
			sb.append("                   ,K_XP                                 	\n");
			if(idBean.getKeywordInfo()!= null && !idBean.getKeywordInfo().equals("")){
			//sb.append("                   ,K_XP                                 	\n");
			sb.append("                   ,K_YP                                 	\n");
			sb.append("                   ,K_ZP                                 	\n");
			}
			sb.append("                   ,MD_PSEQ 								\n");
			if(idBean.getH_seq() != null && !idBean.getH_seq().equals("")){
			sb.append("                   ,H_SEQ 								\n");
			}
			if(idBean.getId_mobile() != null && !idBean.getId_mobile().equals("")){
			sb.append("                   ,ID_MOBILE 								\n");
			}
			sb.append("                   ,USER_ID                                 	\n");
			sb.append("                   ,USER_NICK                                 	\n");
			
			sb.append("                   ,BLOG_VISIT_COUNT                                 	\n");
			sb.append("                   ,CAFE_NAME                                 	\n");
			sb.append("                   ,CAFE_MEMBER_COUNT                                 	\n");
			sb.append("                   ,D_SEQ                                 	\n");
			
			sb.append("                   ) 								\n");
			sb.append(" VALUES(                                                   	\n");
			sb.append("        "+idBean.getMd_seq()+"                             	\n");
			sb.append("        ,"+idBean.getI_seq()+"                             	\n");
			sb.append("        ,"+idBean.getIt_seq()+"                            	\n");
			sb.append("        ,"+idBean.getS_seq()+"                             	\n");
			sb.append("        ,"+idBean.getSg_seq()+"                            	\n");
			sb.append("        ,'"+idBean.getMd_site_name()+"'                    	\n");
			sb.append("        ,'"+idBean.getMd_site_menu()+"'                    	\n");
			sb.append("        ,'"+idBean.getMd_date()+"'                         	\n");
			sb.append("        ,'"+idBean.getId_title()+"'                        	\n");
			sb.append("        ,'"+idBean.getId_url()+"'                          	\n");
			sb.append("        ,'"+idBean.getId_writter()+"'                      	\n");
			sb.append("        ,'"+idBean.getId_content()+"'                      	\n");
			sb.append("        ,'"+idBean.getId_regdate()+"'                      	\n");
			sb.append("        ,'"+idBean.getId_mailyn()+"'                       	\n");
			sb.append("        ,'"+idBean.getId_useyn()+"'                        	\n");
			sb.append("        ,'"+idBean.getMd_same_ct()+"'                        \n");
			sb.append("        ,"+idBean.getM_seq()+"                             	\n");
			sb.append("        ,"+idBean.getMd_type()+"                             \n");
			sb.append("        ,"+idBean.getK_xp()+"           \n");
			if(idBean.getKeywordInfo()!= null && !idBean.getKeywordInfo().equals("")){
			//sb.append("        ,"+idBean.getKeywordInfo().split(",")[0]+"           \n");
			sb.append("        ,"+idBean.getKeywordInfo().split(",")[1]+"           \n");
			sb.append("        ,"+idBean.getKeywordInfo().split(",")[2]+"           \n");
			}
			sb.append("        ,'"+idBean.getMd_pseq()+"'                           \n");
			if(idBean.getH_seq() != null && !idBean.getH_seq().equals("")){
			sb.append("        ,"+idBean.getH_seq()+"                           \n");
			}
			if(idBean.getId_mobile() != null && !idBean.getId_mobile().equals("")){
			sb.append("        ,'"+idBean.getId_mobile()+"'                           \n");
			}
			
			sb.append("        ,'"+idBean.getUser_id()+"'                             	\n");
			sb.append("        ,'"+idBean.getUser_nick()+"'                             	\n");
			
			sb.append("        ,'"+idBean.getBlog_visit_count()+"'                             	\n");
			sb.append("        ,'"+idBean.getCafe_name()+"'                             	\n");
			sb.append("        ,'"+idBean.getCafe_member_count()+"'                             	\n");
			sb.append("        ,"+idBean.getD_seq()+ "					 \n");
			
			sb.append(" )                                                         	\n");
			
			System.out.println(sb.toString());
			
			String sg_seq ="";
			
			if(stmt.executeUpdate(sb.toString())>0) {
				sb = new StringBuffer();
				//sb.append(" SELECT MAX(ID_SEQ) ID_SEQ FROM ISSUE_DATA                  \n");
				sb.append(" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n"); 
				sb.append("                   FROM IC_S_RELATION A																			  \n");
				sb.append("                      , ISSUE_CODE B 																			  \n");
				sb.append("                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n"); 
				sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "+idBean.getMd_seq()+"   		  \n");
				
				rs = stmt.executeQuery(sb.toString());
				if(rs.next()){
					insertNum = rs.getString("ID_SEQ");
					sg_seq = rs.getString("SG_SEQ");
				}
				
				if(!idBean.getMd_site_menu().equals("SOLR") && !idBean.getSg_seq().equals("26")){
					sb = new StringBuffer();
					sb.append(" UPDATE IDX SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = "+idBean.getMd_seq()+"                  \n");
					stmt.executeUpdate(sb.toString());	
				}
				
				if("2196".equals(idBean.getS_seq()) || "2199".equals(idBean.getS_seq()) ){
					sb = new StringBuffer();
	        		sb.append("INSERT INTO REPLY_CNT  			\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        "+idBean.getD_seq()+ "	\n");
	        		sb.append("        ,0						\n");
	        		sb.append("        ) 						\n");
	        		stmt.executeUpdate(sb.toString());
				}
				
				//if(idBean.getSg_seq().equals("26")){
				//	sb = new StringBuffer();
				//	sb.append(" UPDATE TOP_IDX SET ISSUE_CHECK ='Y'                        \n");
				//	sb.append(" WHERE T_SEQ = "+idBean.getMd_seq()+"                  \n");
				//	stmt.executeUpdate(sb.toString());
				//	
				//}
			}
			
			//if(!"".equals(p_seq) && !"".equals(insertNum)){			
			//	sb = new StringBuffer();   
			//	sb.append(" INSERT INTO PRESS_ISSUE_DATA(ID_SEQ                        \n");							
			//	sb.append("                           ,P_SEQ                     \n");
			//	sb.append("                           )                             \n");
			//	sb.append(" VALUES(                                                 \n");
			//	sb.append("        "+insertNum+"                                    \n");
			//	sb.append("        ,"+p_seq+"                      \n");		
			//	sb.append(" )                                                       \n");
			//	System.out.println(sb.toString());
			//	stmt.executeUpdate(sb.toString());
			//}
			
			if(icBean!=null){			
				sb = new StringBuffer();   
				sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
				sb.append("                           ,IM_DATE)                     \n");
				sb.append("                           ,IM_COMMENT                   \n");
				sb.append("                           )                             \n");
				sb.append(" VALUES(                                                 \n");
				sb.append("        "+insertNum+"                                    \n");
				sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
				sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
				sb.append(" )                                                       \n");			
				if(stmt.executeUpdate(sb.toString())>0) result = true;
			}		
			
			
			Boolean check = false;
			System.out.println("typeCode - "+typeCode);
			if(typeCode!=null && !typeCode.equals("")){
				arrTemp = typeCode.split("@");
				for (int i = 0; i < arrTemp.length; i++) {	        		
					arrTypeCode = arrTemp[i].split(",");
					
					if(arrTypeCode[0].equals("6")){
						check = true;
					}
					 
					sb = new StringBuffer();
	        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + insertNum + " 		\n");
	        		sb.append("        ," + arrTypeCode[0] + "	\n");
	        		sb.append("        ," + arrTypeCode[1] + "  \n");
	        		sb.append("        ) 						\n");
	        		if(stmt.executeUpdate(sb.toString())>0) result = true;        		
	        	}
				
				if(!check){
					
					sb = new StringBuffer();
					
					sb = new StringBuffer();
	        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + insertNum + " 		\n");
	        		sb.append("        ,6						\n");
	        		sb.append("        ,"+sg_seq+"  			\n");
	        		sb.append("        ) 						\n");
	        		if(stmt.executeUpdate(sb.toString())>0) result = true; 
				}
				
		       if( !"".equals(idBean.getRelationkeys())) {
		          arrTemp = idBean.getRelationkeys().split(",");
		          for(int i  =0; i <arrTemp.length; i++){          
		            sb = new StringBuffer();
		            StringBuffer sb_insertMap = new StringBuffer();
		            StringBuffer sb_insertKey = new StringBuffer();

		            sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"+arrTemp[i]+"'  \n");      
		            rs = stmt.executeQuery(sb.toString());
		            if(rs.next()){
		              sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("+insertNum+","+rs.getInt("RK_SEQ")+")  \n");     
		              if(stmt.executeUpdate(sb_insertMap.toString())>0) result = true;
		            } else {            
		              sb_insertKey.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('"+arrTemp[i]+"')  \n");     
		              if(stmt.executeUpdate(sb_insertKey.toString())>0) result = true;
		              sb = new StringBuffer();
		              rs = null;
		              sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"+arrTemp[i]+"'  \n");
		              rs = stmt.executeQuery(sb.toString());
		              if(rs.next()){            
		                sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("+insertNum+","+rs.getInt("RK_SEQ")+")  \n");
		                if(stmt.executeUpdate(sb_insertMap.toString())>0) result = true;
		              }
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
        return insertNum;
    }
    
    
    /*
     * 자기사 이슈 저장
     */
    public String insertIssueData_sub(String md_seq, IssueCommentBean icBean, String typeCode, IssueDataBean idBean, String p_seq)
    {
    
        String insertNum = "";
        String sg_seq = "";
        String[] arrTemp = null; 
        String[] arrTypeCode = null;
        String getCode = "";
        boolean result = false;
        
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			//자기사 가져오기~
			sb = new StringBuffer();
			sb.append("SELECT C.MD_SEQ 																				\n");
			sb.append("  FROM META C																				\n");
			sb.append(" WHERE C.MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = "+md_seq+")						\n");
			sb.append("   AND C.MD_SEQ <> "+md_seq+"																\n");
			sb.append("   AND NOT EXISTS (																			\n");
			sb.append("                   SELECT 1																	\n"); 
			sb.append("                     FROM META A, ISSUE_DATA B												\n"); 
			sb.append("                    WHERE A.MD_PSEQ = (SELECT MD_PSEQ FROM META WHERE MD_SEQ = "+md_seq+")	\n");  
			sb.append("                      AND A.MD_SEQ <> "+md_seq+"												\n");
			sb.append("                      AND A.MD_SEQ = B.MD_SEQ												\n");
			sb.append("                      AND A.MD_SEQ = C.MD_PSEQ												\n");
			sb.append("                   )																			\n");
			
			rs = stmt.executeQuery(sb.toString());
			
			ArrayList arr_mdSeq = new ArrayList();
			while(rs.next()){
				arr_mdSeq.add(rs.getString("MD_SEQ"));
			}
			
			System.out.println("유사 : " + arr_mdSeq.size() + "건");
			
			for(int i = 0; i < arr_mdSeq.size(); i++){			
				
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA      ( I_SEQ					\n");
				sb.append("                            , IT_SEQ					\n");
				sb.append("                            , MD_SEQ					\n");
				sb.append("                            , ID_TITLE				\n");
				sb.append("                            , ID_URL					\n");
				sb.append("                            , SG_SEQ					\n");
				sb.append("                            , S_SEQ					\n");
				sb.append("                            , MD_SITE_NAME			\n");
				sb.append("                            , MD_SITE_MENU			\n");
				sb.append("                            , MD_DATE				\n");
				sb.append("                            , ID_WRITTER				\n");
				sb.append("                            , ID_CONTENT				\n");
				sb.append("                            , ID_REGDATE				\n");
				sb.append("                            , ID_MAILYN				\n");
				sb.append("                            , ID_USEYN				\n");
				sb.append("                            , M_SEQ					\n");
				sb.append("                            , MD_SAME_CT				\n");
				sb.append("                            , MD_TYPE				\n");
				if(idBean.getKeywordInfo()!= null && !idBean.getKeywordInfo().equals("")){
				sb.append("                            , K_XP					\n");
				sb.append("                            , K_YP					\n");
				sb.append("                            , K_ZP					\n");
				}
				sb.append("                            , MD_PSEQ 				\n");
				if(idBean.getH_seq() != null && !idBean.getH_seq().equals("")){
				sb.append("                            , H_SEQ 				\n");
				}
				if(idBean.getId_mobile() != null && !idBean.getId_mobile().equals("")){
				sb.append("                            , ID_MOBILE 				\n");
				}
				sb.append("                            , D_SEQ 				\n");
				sb.append("                            ) 				\n");
				sb.append(" (													\n");
				sb.append("   SELECT 0 AS I_SEQ									\n"); 
				sb.append("        , 0 AS IT_SEQ								\n");
				sb.append("        , A.MD_SEQ 									\n");
				sb.append("        , A.MD_TITLE									\n");
				sb.append("        , A.MD_URL									\n");
				sb.append("        , B.SG_SEQ									\n");
				sb.append("        , A.S_SEQ									\n");
				sb.append("        , A.MD_SITE_NAME								\n");
				sb.append("        , A.MD_MENU_NAME								\n");
				sb.append("        , A.MD_DATE									\n");
				sb.append("        , ''											\n");
				sb.append("        , C.MD_CONTENT								\n");
				sb.append("        , NOW()										\n");
				sb.append("        , 'N'										\n");
				sb.append("        , 'Y'										\n");
				sb.append("        , 1											\n");
				sb.append("        , A.MD_SAME_COUNT							\n");
				sb.append("        , A.MD_TYPE									\n");
				if(idBean.getKeywordInfo()!= null && !idBean.getKeywordInfo().equals("")){
				sb.append("        , "+idBean.getKeywordInfo().split(",")[0]+" 	\n");
				sb.append("        , "+idBean.getKeywordInfo().split(",")[1]+" 	\n");
				sb.append("        , "+idBean.getKeywordInfo().split(",")[2]+" 	\n");
				}
				sb.append("        , A.MD_PSEQ 									\n");
				if(idBean.getH_seq() != null && !idBean.getH_seq().equals("")){
				sb.append("        , "+idBean.getH_seq()+" 									\n");
				}
				if(idBean.getId_mobile() != null && !idBean.getId_mobile().equals("")){
				sb.append("        , '"+idBean.getId_mobile()+"' 									\n");
				}
				sb.append("       ,(SELECT IFNULL(D_SEQ, '') FROM META WHERE MD_SEQ = "+(String)arr_mdSeq.get(i)+ ")	 \n");
				sb.append("     FROM META A, SG_S_RELATION B, DATA C			\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
				sb.append("      AND A.MD_SEQ = C.MD_SEQ						\n");
				sb.append("      AND A.MD_SEQ = "+(String)arr_mdSeq.get(i)+"	\n");
				sb.append(" )													\n");
				
				
				insertNum = "";
				sg_seq = "";
				
				
				if(stmt.executeUpdate(sb.toString()) > 0){
					sb = new StringBuffer();
					sb.append(" SELECT ID_SEQ, (SELECT B.IC_CODE																				  \n"); 
					sb.append("                   FROM IC_S_RELATION A																			  \n");
					sb.append("                      , ISSUE_CODE B 																			  \n");
					sb.append("                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n"); 
					sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "+(String)arr_mdSeq.get(i)+"    \n");			
					rs = stmt.executeQuery(sb.toString());
					if(rs.next()){
						insertNum = rs.getString("ID_SEQ");
						sg_seq = rs.getString("SG_SEQ");
					}
					
					
					String s_seq = "";
					
					sb = new StringBuffer();
					sb.append("   SELECT A.S_SEQ							\n"); 
					sb.append("     FROM META A, SG_S_RELATION B, DATA C	\n");
					sb.append("    WHERE A.S_SEQ = B.S_SEQ					\n");
					sb.append("      AND A.MD_SEQ = C.MD_SEQ				\n");
					sb.append("      AND A.MD_SEQ = "+(String)arr_mdSeq.get(i)+"				\n");
					System.out.println(sb.toString());				
					rs = stmt.executeQuery(sb.toString());
					if(rs.next()){
						s_seq = rs.getString("S_SEQ");
					}
					
					if("2196".equals(s_seq) || "2199".equals(s_seq) ){
						sb = new StringBuffer();	        			        		
		        		sb.append(" INSERT INTO REPLY_CNT 			\n");
		        		sb.append(" (D_SEQ, REPLY_CNT)  			\n");
		        		sb.append(" VALUES (       					\n");
		        		sb.append("       (SELECT IFNULL(D_SEQ, '') FROM META WHERE MD_SEQ = "+(String)arr_mdSeq.get(i)+ ") \n");
		        		sb.append("       , 0); 					\n");	        		
		        		stmt.executeUpdate(sb.toString());
					}
					
					
					sb = new StringBuffer();
					sb.append(" UPDATE IDX SET ISSUE_CHECK ='Y'                        \n");
					sb.append(" WHERE MD_SEQ = "+(String)arr_mdSeq.get(i)+"            \n");
					stmt.executeUpdate(sb.toString());
					
				}
				
				//보도자료 저장
				/*if(!"".equals(p_seq) && !"".equals(insertNum)){			
					sb = new StringBuffer();   
					sb.append(" INSERT INTO PRESS_ISSUE_DATA(ID_SEQ                        \n");							
					sb.append("                           ,P_SEQ                     \n");
					sb.append("                           )                             \n");
					sb.append(" VALUES(                                                 \n");
					sb.append("        "+insertNum+"                                    \n");
					sb.append("        ,"+p_seq+"                      \n");		
					sb.append(" )                                                       \n");
					stmt.executeUpdate(sb.toString());
				}	*/
				

				if(icBean!=null && !insertNum.equals("")){			
					sb = new StringBuffer();   
					sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
					sb.append("                           ,IM_DATE)                     \n");
					sb.append("                           ,IM_COMMENT                   \n");
					sb.append("                           )                             \n");
					sb.append(" VALUES(                                                 \n");
					sb.append("        "+insertNum+"                                    \n");
					sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
					sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
					sb.append(" )                                                       \n");
					
					stmt.executeUpdate(sb.toString());
				}	
				
				if(typeCode!=null && !typeCode.equals("") && !insertNum.equals("")){
					
					sb = new StringBuffer();   
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + insertNum + " 		\n");
	        		sb.append("        ,6						\n");
	        		sb.append("        ," + sg_seq + " 			\n");
	        		sb.append("        ) 						\n");		        		
	        		stmt.executeUpdate(sb.toString());
					
					arrTemp = typeCode.split("@");
					
					for (int j = 0; j < arrTemp.length; j++) {		        		
						arrTypeCode = arrTemp[j].split(",");
						if(!arrTypeCode[0].equals("6")){
							sb = new StringBuffer();
			        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
			        		sb.append("VALUES ( 						\n");
			        		sb.append("        " + insertNum + " 		\n");
			        		sb.append("        ," + arrTypeCode[0] + "	\n");
			        		sb.append("        ," + arrTypeCode[1] + "  \n");
			        		sb.append("        ) 						\n");		        		
			        		stmt.executeUpdate(sb.toString());
			        		
						}
						
		        	}
				}
				
				
				
				
				//연관키워드 저장
				if(!idBean.getRelationkeys().equals("")){
					arrTemp = idBean.getRelationkeys().split(",");
					for(int h =0; h <arrTemp.length; h++){					
						sb = new StringBuffer();
						StringBuffer sb_insertMap = new StringBuffer();
						StringBuffer sb_insertKey = new StringBuffer();
						StringBuffer sb_insertIssue = new StringBuffer();
						
						String flag = ""; 
						if(!"".equals(getCode)){
							sb.append("SELECT A.RK_SEQ \n");
							sb.append(", (SELECT COUNT(*) FROM RELATION_KEYWORD_R_ISSUE WHERE RK_SEQ = A.RK_SEQ AND IC_CODE = "+getCode+") AS ISSUE_RK_SEQ \n");
							sb.append("FROM RELATION_KEYWORD_R A WHERE A.RK_NAME  ='"+arrTemp[h]+"' \n");
							flag = "one";
						}else{
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"+arrTemp[h]+"'  \n");
							flag = "two";
						}
												
						rs = stmt.executeQuery(sb.toString());
						if(rs.next()){
							if("one".equals(flag)){
								if(rs.getInt("ISSUE_RK_SEQ") > 0){
									System.out.println("유사 기사 연관키워드 저장");
									sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("+insertNum+","+rs.getInt("RK_SEQ")+")  \n");			
									System.out.println(sb_insertMap.toString());
									stmt.executeUpdate(sb_insertMap.toString());
								}else{
									sb_insertIssue.append(" INSERT INTO RELATION_KEYWORD_R_ISSUE (RK_SEQ, IC_CODE) VALUES("+rs.getInt("RK_SEQ")+","+getCode+") \n");
									System.out.println(sb_insertIssue.toString());
									if(stmt.executeUpdate(sb_insertIssue.toString())>0) result = true;
									
									sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("+insertNum+","+rs.getInt("RK_SEQ")+")  \n");			
									System.out.println(sb_insertMap.toString());
									stmt.executeUpdate(sb_insertMap.toString());
								}
							}else{
								sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("+insertNum+","+rs.getInt("RK_SEQ")+")  \n");			
								System.out.println(sb_insertMap.toString());
								stmt.executeUpdate(sb_insertMap.toString());
							}
							
						}else{						
							sb_insertKey.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('"+arrTemp[h]+"')  \n");
							System.out.println(sb_insertKey.toString());
							if(stmt.executeUpdate(sb_insertKey.toString())>0) result = true;
							sb = new StringBuffer();
							rs = null;
							sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"+arrTemp[i]+"'  \n");
							rs = stmt.executeQuery(sb.toString());
						
							if(rs.next()){
								
								sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("+insertNum+","+rs.getInt("RK_SEQ")+")  \n");
								System.out.println(sb_insertMap.toString());								
								if(stmt.executeUpdate(sb_insertMap.toString())>0){
									sb = new StringBuffer();
									rs = null;
									sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"+arrTemp[i]+"'  \n");
									System.out.println(sb.toString());
									rs = stmt.executeQuery(sb.toString());
									if(!getCode.equals("")){
										if(rs.next()){
											sb_insertIssue.append(" INSERT INTO RELATION_KEYWORD_R_ISSUE (RK_SEQ, IC_CODE) VALUES("+rs.getInt("RK_SEQ")+","+getCode+") \n");
											System.out.println(sb_insertIssue.toString());
											if(stmt.executeUpdate(sb_insertIssue.toString())>0) result = true;
										}
									}
								}
							}
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
        return insertNum;
    }
    
    /**
     * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 수정
     * @param IssueDataBean :이슈데이터빈
     * @param IssueCommentBean :이슈코멘트빈
     * @param typeCode :분류체계
     * @return
     */
    public boolean updateIssueData(IssueDataBean idBean,  IssueCommentBean icBean, String typeCode, String p_seq)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
       
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" UPDATE ISSUE_DATA SET                                     \n");
			sb.append("                 I_SEQ ="+idBean.getI_seq()+"              \n");
			sb.append("                 ,IT_SEQ ="+idBean.getIt_seq()+"           \n");				
			sb.append("                 ,ID_TITLE ='"+idBean.getId_title()+"'     \n");
			sb.append("                 ,ID_URL ='"+idBean.getId_url()+"'         \n");
			sb.append("                 ,MD_SITE_NAME ='"+idBean.getMd_site_name()+"' \n");
			sb.append("                 ,ID_WRITTER ='"+idBean.getId_writter()+"' \n");		
			sb.append("                 ,ID_CONTENT ='"+idBean.getId_content()+"' \n");						
			sb.append("                 ,M_SEQ ="+idBean.getM_seq()+"             \n");
			sb.append("                 ,MD_TYPE ="+idBean.getMd_type()+"             \n");
			if(idBean.getH_seq() != null && !idBean.getH_seq().equals("")){
			sb.append("                 ,H_SEQ ="+idBean.getH_seq()+"             \n");
			}
			if(idBean.getKeywordInfo()!= null && !idBean.getKeywordInfo().equals("")){
			sb.append("                 ,K_XP ="+idBean.getKeywordInfo().split(",")[0]+"             \n");
			sb.append("                 ,K_YP ="+idBean.getKeywordInfo().split(",")[1]+"             \n");
			sb.append("                 ,K_ZP ="+idBean.getKeywordInfo().split(",")[2]+"             \n");
			}
			
			sb.append("                 ,USER_ID ='"+idBean.getUser_id()+"'             \n");
			sb.append("                 ,USER_NICK ='"+idBean.getUser_nick()+"'             \n");
			sb.append("                 ,BLOG_VISIT_COUNT ='"+idBean.getBlog_visit_count()+"'             \n");
			sb.append("                 ,CAFE_NAME ='"+idBean.getCafe_name()+"'             \n");
			sb.append("                 ,CAFE_MEMBER_COUNT ='"+idBean.getCafe_member_count()+"'             \n");
			
			sb.append(" WHERE                                                     \n");
			sb.append("       ID_SEQ = "+idBean.getId_seq()+"                     \n");
			//System.out.println( sb.toString() );
			if(stmt.executeUpdate(sb.toString())>0) {
				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ ="+idBean.getId_seq()+"   \n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());
				
				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_COMMENT WHERE ID_SEQ ="+idBean.getId_seq()+"   \n");			
				stmt.executeUpdate(sb.toString());
			}
			
			/*if(!"".equals(p_seq) && !"".equals(idBean.getId_seq())){
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM PRESS_ISSUE_DATA WHERE ID_SEQ = "+idBean.getId_seq()+" \n");		
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());
				
				sb = new StringBuffer();   
				sb.append(" INSERT INTO PRESS_ISSUE_DATA(ID_SEQ                        \n");							
				sb.append("                           ,P_SEQ                     \n");
				sb.append("                           )                             \n");
				sb.append(" VALUES(                                                 \n");
				sb.append("        "+idBean.getId_seq()+"                                    \n");
				sb.append("        ,"+p_seq+"                      \n");		
				sb.append(" )                                                       \n");
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());
			}*/
			
			if(icBean!=null){			
				sb = new StringBuffer();   
				sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
				sb.append("                           ,IM_DATE)                     \n");
				sb.append("                           ,IM_COMMENT                   \n");
				sb.append("                           )                             \n");
				sb.append(" VALUES(                                                 \n");
				sb.append("        "+idBean.getId_seq()+"                           \n");
				sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
				sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
				sb.append(" )                                                       \n");
				//System.out.println(sb.toString());
				if(stmt.executeUpdate(sb.toString())>0) result = true;
			}		
			
			if(typeCode!=null && !typeCode.equals("")){
				arrTemp = typeCode.split("@");
				for (int i = 0; i < arrTemp.length; i++) {		        		
					arrTypeCode = arrTemp[i].split(",");  
					
					sb = new StringBuffer();
	        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + idBean.getId_seq() +"\n");
	        		sb.append("        ," + arrTypeCode[0] + "	\n");
	        		sb.append("        ," + arrTypeCode[1] + "  \n");
	        		sb.append("        ) 						\n");
	        		//System.out.println(sb.toString());
	        		if(stmt.executeUpdate(sb.toString())>0) result = true;
	        		       		
	        		        		
	        	}
			}
		
		//연관키워드 수동 저장 기능 	
		   // 현재 매핑된 연관 키워드 정보를 모두 삭제
		    sb = new StringBuffer();
		    sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ = " + idBean.getId_seq());
		    //System.out.println("연관 키워드 삭제 쿼리 :\n" + sb.toString());
		    stmt.executeUpdate(sb.toString());
         
		    System.out.println(idBean.getRelationkeys());
		    // 설정된 연관 키워드 정보가 있으면 저장
		    if( !"".equals(idBean.getRelationkeys()) ){
		     arrTemp = idBean.getRelationkeys().split(",");
           
		     for(int i = 0; i < arrTemp.length; i++) {
		     
		       sb = new StringBuffer();
		       StringBuffer sb_insertMap = new StringBuffer();
		       StringBuffer sb_insertKey = new StringBuffer();
           
		       // 등록된 연관 키워드 인지 조회
		       sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"+arrTemp[i]+"'  \n");
		       //System.out.println(sb.toString());
		       rs = stmt.executeQuery(sb.toString());
		       if(rs.next()){
		     	  //System.out.println(rs.getString("RK_SEQ"));
		     	  //System.out.println(idBean.getId_seq());
		         // 등록된 연관 키워드 이면 매핑 정보만 인서트
		         sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES (" + idBean.getId_seq() + ", " + rs.getInt("RK_SEQ") + ")\n");
		         //System.out.println(sb.toString());
		         if(stmt.executeUpdate(sb_insertMap.toString()) > 0) {
		           result = true;
		         }
		       } else {
		         // 등록되지 않은 연관 키워드 이면 키워드 정보 인서트 후 매핑 정보 인서트
		         sb_insertKey.append(" INSERT INTO RELATION_KEYWORD_R (RK_NAME) VALUES ('"+arrTemp[i]+"')  \n");
		         //System.out.println(sb.toString());
		         if(stmt.executeUpdate(sb_insertKey.toString()) > 0) {
		           result = true;
		         }
           
		         sb = new StringBuffer();
		         rs = null;
           
		         // 연관 키워드 등록
		         sb.append(" SELECT RK_SEQ FROM RELATION_KEYWORD_R WHERE RK_NAME ='"+arrTemp[i]+"'  \n");
		         //System.out.println(sb.toString());
		         rs = stmt.executeQuery(sb.toString());
           
		         if(rs.next()) {
		           // 연관 키워드 매핑정보 등록
		           sb_insertMap.append(" INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ) VALUES ("+idBean.getId_seq()+","+rs.getInt("RK_SEQ")+")  \n");
		           //System.out.println(sb.toString());
		           if(stmt.executeUpdate(sb_insertMap.toString())>0) result = true;
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
        return result;
    }
    
    
    
    public boolean updateChildIssueData(IssueDataBean idBean,  IssueCommentBean icBean, String typeCode, String p_seq)
    {
    	String[] arrTemp;
    	String[] arrTypeCode ;
        boolean result = false;
       
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_PSEQ = (SELECT distinct MD_PSEQ FROM ISSUE_DATA WHERE MD_SEQ = "+idBean.getMd_seq()+" LIMIT 1 )  \n");
			
			rs =  stmt.executeQuery(sb.toString());
			String idseqs = "";
			ArrayList arrIdSeq = new ArrayList();
			
			
			while(rs.next()){
				if(idseqs.equals("")){
					idseqs = rs.getString("ID_SEQ");
				}else{
					idseqs += "," + rs.getString("ID_SEQ");
				}	
				
				arrIdSeq.add(rs.getString("ID_SEQ"));
			}
			
			//먼저 삭제후 인서트
			sb = new StringBuffer();   
			sb.append(" DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN ("+idseqs+")  \n");
			stmt.executeUpdate(sb.toString());
			
			String insertNum = "";
			String sg_seq = "";
			String d_seq = "";
			for(int i=0; i < arrIdSeq.size(); i++){
				
				sb = new StringBuffer();
				sb.append(" SELECT ID_SEQ,IFNULL(D_SEQ,'') AS D_SEQ, (SELECT B.IC_CODE														  \n"); 
				sb.append("                   FROM IC_S_RELATION A																			  \n");
				sb.append("                      , ISSUE_CODE B 																			  \n");
				sb.append("                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n"); 
				sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE ID_SEQ = "+arrIdSeq.get(i)+"    \n");			
				rs = stmt.executeQuery(sb.toString());
				if(rs.next()){
					insertNum = rs.getString("ID_SEQ");
					sg_seq = rs.getString("SG_SEQ");
					d_seq  = rs.getString("D_SEQ");
				}
				if( !"".equals(insertNum) && !"".equals(sg_seq)){
					sb = new StringBuffer();   
					sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        " + insertNum + " 		\n");
	        		sb.append("        ,6						\n");
	        		sb.append("        ," + sg_seq + " 			\n");
	        		sb.append("        ) 						\n");
	        		System.out.println(sb.toString());
	        		stmt.executeUpdate(sb.toString());
				}
				
				/*if(!"".equals(p_seq) && !"".equals(insertNum)){
					
					sb = new StringBuffer();
					sb.append(" DELETE FROM PRESS_ISSUE_DATA WHERE ID_SEQ = "+insertNum+" \n");		
					System.out.println(sb.toString());
					stmt.executeUpdate(sb.toString());
					
					sb = new StringBuffer();   
					sb.append(" INSERT INTO PRESS_ISSUE_DATA(ID_SEQ                        \n");							
					sb.append("                           ,P_SEQ                     \n");
					sb.append("                           )                             \n");
					sb.append(" VALUES(                                                 \n");
					sb.append("        "+insertNum+"                                    \n");
					sb.append("        ,"+p_seq+"                      \n");		
					sb.append(" )                                                       \n");
					System.out.println(sb.toString());
					stmt.executeUpdate(sb.toString());
				}*/
				
				if(typeCode!=null && !typeCode.equals("")){
					arrTemp = typeCode.split("@");
					for (int j = 0; j < arrTemp.length; j++) {		        		
						arrTypeCode = arrTemp[j].split(",");
							sb = new StringBuffer();
							if(!arrTypeCode[0].equals("6")){
								sb = new StringBuffer();
				        		sb.append("INSERT INTO ISSUE_DATA_CODE  	\n");
				        		sb.append("VALUES ( 						\n");
				        		sb.append("        " + arrIdSeq.get(i) +"\n");
				        		sb.append("        ," + arrTypeCode[0] + "	\n");
				        		sb.append("        ," + arrTypeCode[1] + "  \n");
				        		sb.append("        ) 						\n");		        		
				        		if(stmt.executeUpdate(sb.toString())>0) result = true;
				        					        						        		
							}
		        	}
					if(icBean!=null){			
						sb = new StringBuffer();   
						sb.append(" INSERT INTO ISSUE_COMMENT(ID_SEQ                        \n");							
						sb.append("                           ,IM_DATE)                     \n");
						sb.append("                           ,IM_COMMENT                   \n");
						sb.append("                           )                             \n");
						sb.append(" VALUES(                                                 \n");
						sb.append("        "+arrIdSeq.get(i)+"                           	\n");
						sb.append("        ,'"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"'  \n");
						sb.append("        ,"+icBean.getIm_comment()+"                      \n");		
						sb.append(" )                                                       \n");			
						if(stmt.executeUpdate(sb.toString())>0) result = true;
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
        return result;
    }
    
    
    /**
     * ISSUE_DATA, ISSUE_CODE, ISSUE_COMMENT 삭제
     * @param id_seq : ISSUE_DATA.ID_SEQ  
     * @return
     */
    public boolean deleteIssueData(String id_seq)
    {    	
        boolean result = false;
        String tempNo = "";
        String tempPNo = "";
        String tempIseqNo = "";
        String tempDseqNo = "";
        
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT MD_PSEQ FROM ISSUE_DATA WHERE ID_SEQ IN ("+id_seq+") \n");		
			rs = stmt.executeQuery(sb.toString());

			while(rs.next()){
				tempPNo += tempPNo.equals("")? rs.getString("MD_PSEQ") : ","+rs.getString("MD_PSEQ");				
			}

			sb = new StringBuffer();
			sb.append("SELECT ID_SEQ,D_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN ("+tempPNo+")                      \n");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				tempIseqNo += tempIseqNo.equals("")? rs.getString("ID_SEQ") : ","+rs.getString("ID_SEQ");
				tempDseqNo += tempDseqNo.equals("")? rs.getString("D_SEQ") : ","+rs.getString("D_SEQ");
			}
			
			//일반 IDX 처리
			tempNo = "";
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN ("+tempPNo+") AND SG_SEQ <> 30           \n");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				tempNo += tempNo.equals("")? rs.getString("MD_SEQ") : ","+rs.getString("MD_SEQ");				
			}
			if(!tempNo.equals("")){
				sb = new StringBuffer();   
				sb.append("UPDATE IDX SET ISSUE_CHECK='N' WHERE MD_SEQ IN ("+tempNo+")   \n");
				System.out.println(sb.toString());			
				stmt.executeUpdate(sb.toString());	
			}
			
			//TOP IDX 처리
			tempNo = "";
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ FROM ISSUE_DATA WHERE MD_PSEQ IN ("+tempPNo+") AND SG_SEQ = 30           \n");
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				tempNo += tempNo.equals("")? rs.getString("MD_SEQ") : ","+rs.getString("MD_SEQ");				
			}
				if(!tempNo.equals("")){
				sb = new StringBuffer();   
				sb.append("UPDATE TOP_IDX SET ISSUE_CHECK='N' WHERE T_SEQ IN ("+tempNo+")   \n");
				System.out.println(sb.toString());			
				stmt.executeUpdate(sb.toString());		
			}
			
			
		
			sb = new StringBuffer();
			sb.append(" DELETE FROM ISSUE_DATA                                      \n");		
			sb.append(" WHERE                                                       \n");
			sb.append("       ID_SEQ IN ("+tempIseqNo+")                                \n");						
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ IN ("+tempIseqNo+") \n");			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_COMMENT WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_TAG_DATA WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			// 자동연관키워드 삭제
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_RELATION_KEYWORD WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			//댓글 카운팅 삭젝
			sb = new StringBuffer();
			sb.append("DELETE FROM REPLY_CNT WHERE D_SEQ IN ("+tempDseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			/*sb = new StringBuffer();
			sb.append("DELETE FROM PRESS_ISSUE_DATA WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());*/
			
			// 연관 키워드 MAP 삭제
			/*sb = new StringBuffer();
			sb.append("DELETE FROM RELATION_KEYWORD_MAP WHERE ID_SEQ IN ("+tempIseqNo+")   \n");						
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());*/
				
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
     * ISSUE_DATA.MD_SEQ 로 ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여 해당정보를 빈으로 반환.
     * @param md_seq : ISSUE_DATA.MD_SEQ
     * @return
     */
    public IssueDataBean getIssueDataBean(String md_seq)
    {
    	IssueDataBean idBean = new IssueDataBean();
    	ArrayList arrIdBean = new ArrayList();
    	arrIdBean = getIssueDataList(0,0,"","","",md_seq,"","1","","","","","","","Y","","","N");
    	if(arrIdBean.size()>0)
    	{
    		idBean = (IssueDataBean)arrIdBean.get(0);
    	}
    	return idBean;
    }
    
    
    /**
     * ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param id_seq : ISSUE_DATA.ID_SEQ
     * @param i_seq : ISSUE_DATA.I_SEQ
     * @param it_seq : ISSUE_DATA.IT_SEQ 
     * @param md_seq : ISSUE_DATA.MD_SEQ
     * @param schKey : ISSUE_DATA.ID_TITLE
     * @param DateType : 1:ISSUE_DATA.ID_REGDATE 2:ISSUE_DATA.MD_DATE
     * @param schSdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param schEdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param typeCode : ISSUE_DATA_CODE.*
     * @param Order : 정렬하고픈 code type
     * @param useYN : 보고서 사용여부
     * @return : 이슈데이터 검색리스트
     */
    /*
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,    		
    		String useYN,
    		String language
    )    
    {
    	return  getIssueDataList(pageNum,rowCnt,id_seq,i_seq,it_seq,md_seq,schKey,DateType,schSdate,schStime,schEdate,schEtime,typeCode,"","Y",language,"");
    }
    
    public ArrayList getIssueDataList2( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,    		
    		String useYN,
    		String language,
    		String reportYn
    )    
    {
    	return  getIssueDataList(pageNum,rowCnt,id_seq,i_seq,it_seq,md_seq,schKey,DateType,schSdate,schStime,schEdate,schEtime,typeCode,"","Y",language,reportYn);
    }
    */
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents
    ){
    	return  getIssueDataList(pageNum, rowCnt, id_seq,i_seq, it_seq, md_seq, schKey, DateType, schSdate, schStime, schEdate, schEtime, typeCode, typeCodeOrder, useYN, language, reportyn, parents, "");
    }
    
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news
    ){
    	return  getIssueDataList(pageNum, rowCnt, id_seq,i_seq, it_seq, md_seq, schKey, DateType, schSdate, schStime, schEdate, schEtime, typeCode, typeCodeOrder, useYN, language, reportyn, parents, f_news,"");
    }
    
    
    public ArrayList getIssueDataList(
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news,
    		String s_seq
    ){
    	return  getIssueDataList(pageNum, rowCnt, id_seq,i_seq, it_seq, md_seq, schKey, DateType, schSdate, schStime, schEdate, schEtime, typeCode, typeCodeOrder, useYN, language, reportyn, parents, f_news, s_seq, 0);
    }
    

    /**
     * ISSUE_DATA ,ISSUE_DATA_CODE ,ISSUE_COMMENT 테이블을 조회하여 해당정보를 어레이로 반환.
     * @param pageNum : 페이징번호 0일시 페이징처리 안함
     * @param id_seq : ISSUE_DATA.ID_SEQ
     * @param i_seq : ISSUE_DATA.I_SEQ
     * @param it_seq : ISSUE_DATA.IT_SEQ 
     * @param md_seq : ISSUE_DATA.MD_SEQ
     * @param schKey : ISSUE_DATA.ID_TITLE
     * @param DateType : 1:ISSUE_DATA.ID_REGDATE 2:ISSUE_DATA.MD_DATE
     * @param schSdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param schEdate : ISSUE_DATA.ID_REGDATE , ISSUE_DATA.MD_DATE
     * @param typeCode : ISSUE_DATA_CODE.*
     * @param typeCodeOrder : ISSUE_DATA_CODE.*
     * @param Order : 정렬하고픈 code type
     * @param useYN : 보고서 사용여부
     * @return : 이슈데이터 검색리스트
     */
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news,
    		String s_seq,
    		int tier
    )    
    {    	
    	ArrayList issueDataList = new ArrayList();    	    	
    	String[] tmpArr = typeCode.split("@");
    	String[] arrTypeCode = null;
    	String tmpId_seq = null;
    	
    	
    	//code에 대한 쿼리문을 생성한다.
    	String tmpSameCodeYn = "";
    	int sameCodeCount = 0;
    	String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
    	codeQuery +=       "FROM ISSUE_DATA_CODE   \n";
    	codeQuery +=       "WHERE                  \n";
    	codeQuery +=       "	 1=1               \n";
    	for( int i=0 ; i<tmpArr.length; i++ )
		{
    		
			if (!tmpArr[i].equals("")) {
				arrTypeCode = tmpArr[i].split(",");		
					if (arrTypeCode.length==2) {
					if(tmpSameCodeYn.equals(arrTypeCode[0])) sameCodeCount++;
					codeQuery = i == 0 ?	
					codeQuery+"AND (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n" : codeQuery+" OR (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n";				
					tmpSameCodeYn = arrTypeCode[0];
				}
			}
		}
    	System.out.println("tmpArr.length:"+tmpArr.length);
    	System.out.println("sameCodeCount:"+sameCodeCount);
    	codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)="+(tmpArr.length-sameCodeCount);
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(ID.ID_SEQ) AS CNT 								\n");
			sb.append("		     , SUM(ID.MD_SAME_CT + 1) AS SAME_CNT 					\n");
			sb.append("		FROM ISSUE_DATA ID                                          \n");
			if(!typeCode.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			
			if(tier == 1){
				sb.append("         INNER JOIN TIER_SITE E ON E.TS_SEQ = ID.S_SEQ									\n");
			}
			
			
			sb.append("		WHERE 1=1													\n");
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  							\n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
			}
			if( !schKey.equals("") ) {
				sb.append("		AND	ID.ID_TITLE LIKE '%"+schKey+"%'  							\n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
			}
			
			//모기사일경우
			if(parents.equals("Y")){
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			
			if(!s_seq.equals("")){
				sb.append(" AND ID.S_SEQ = "+s_seq+"\n");
			}
			
			
			/*
			// 기사 유형~~
			if(parents.equals("super")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}else if(parents.equals("child")){
				//자기사일경우
				sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n");
			}else if(parents.equals("self")){
				//수동등록
				sb.append(" AND M_SEQ NOT IN (1,3)		\n");
			}else if(parents.equals("childSelf")){
				//모기사 + 수동등록
				sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n");
			}
			*/
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueDataCnt  = rs.getInt("CNT");
            	totalSameDataCnt = rs.getInt("SAME_CNT");
            }
            
            rs.close();                     			
			sb = new StringBuffer();
			
			sb.append("SELECT A.*													\n");
			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT 			\n");
			sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = A.M_SEQ) AS M_NAME		 \n");
			sb.append("  FROM(																	 \n");
			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	 \n");
			sb.append("        ,ID.IT_SEQ                                                     	 \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			sb.append("        ,ID.SG_SEQ                                                        \n");
			sb.append("        ,ID.S_SEQ                                                         \n");
			sb.append("        ,ID.MD_SITE_NAME                                                  \n");
			sb.append("        ,ID.MD_SITE_MENU                                                  \n");
			sb.append("        ,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE           \n");
			sb.append("        ,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE     \n");
			sb.append("        ,ID.ID_WRITTER                                                    \n");
			sb.append("        ,ID.ID_CONTENT                                                    \n");
			sb.append("        ,ID.ID_MAILYN                                                     \n");
			sb.append("        ,ID.ID_USEYN                                                      \n");
			sb.append("        ,ID.M_SEQ                                                         \n");
			sb.append("        ,ID.MD_TYPE                                                    	 \n");
			sb.append("        ,ID.MD_PSEQ 													 	 \n");
			sb.append("        ,ID.K_XP, ID.K_YP, ID.K_ZP, ID.H_SEQ								 \n");
			sb.append("        ,ID.USER_ID                                                       \n");
			sb.append("        ,ID.USER_NICK                                                     \n");
			sb.append("        ,ID.BLOG_VISIT_COUNT                                              \n");
			sb.append("        ,ID.CAFE_NAME                                                     \n");
			sb.append("        ,ID.CAFE_MEMBER_COUNT                                             \n");
			sb.append("        ,ID.D_SEQ			                                             \n");
			if(!typeCodeOrder.equals("")){
				sb.append("    ,IDC.IC_CODE      \n");
			}			
			sb.append(" FROM ISSUE_DATA ID                                                    \n");
			if(!typeCodeOrder.equals("")&&typeCode.equals("")){
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = "+typeCodeOrder+") IDC  ON (ID.ID_SEQ = IDC.ID_SEQ)    \n");
			}			
			if(!typeCode.equals("")&&typeCodeOrder.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if(!typeCodeOrder.equals("")&&!typeCode.equals("")){
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = "+typeCodeOrder+") IDC    \n");
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IDC.ID_SEQ AND ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			
			if(tier == 1){
				sb.append("         INNER JOIN TIER_SITE E ON E.TS_SEQ = ID.S_SEQ									\n");
			}
			
			sb.append(" WHERE 1=1										      \n");
			
			
			/*
			 *  ISSUE_DATA 테이블에 ID_REPORTYN 컬럼이 없음 2015.01.02
			 * 
			 */
			//if(!reportyn.equals("")){
			//	sb.append("   AND ID.ID_REPORTYN = '"+reportyn+"' 						      \n");
			//}
			
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									  \n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ in ("+md_seq+") 			                          \n");
			}
			if( !schKey.equals("") ) {
				sb.append("		AND	ID.ID_TITLE LIKE '%"+schKey+"%'  							  \n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}			
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									      \n");
			}
			
			if(parents.equals("Y")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			
			if(!s_seq.equals("")){
				sb.append(" AND ID.S_SEQ = "+s_seq+"\n");
			}
			
			/*
			// 기사 유형~~
			if(parents.equals("super")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}else if(parents.equals("child")){
				//자기사일경우
				sb.append(" AND ID.MD_SEQ <> ID.MD_PSEQ		\n");
			}else if(parents.equals("self")){
				//수동등록
				sb.append(" AND M_SEQ NOT IN (1,3)		\n");
			}else if(parents.equals("childSelf")){
				//모기사 + 수동등록
				sb.append(" AND (ID.MD_SEQ = ID.MD_PSEQ OR M_SEQ NOT IN (1,3))		\n");
			}
			*/
			
			if(DateType.equals("1")){
				if(!typeCodeOrder.equals("")){
					sb.append(" ORDER BY IDC.IC_CODE, ID_REGDATE DESC 		                                      \n");
				}else{
					sb.append(" ORDER BY ID_REGDATE DESC 		                                      \n");
				}
			
			}else{
				if(!typeCodeOrder.equals("")){
					sb.append(" ORDER BY IDC.IC_CODE, MD_DATE DESC 		                                      \n");
				}else{
					sb.append("	ORDER BY MD_DATE DESC 		                                          \n");	
				}			
			}
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                       \n");
			}			
			
			sb.append("  )A                                                                    \n");
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			tmpId_seq ="";
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setUser_id(rs.getString("USER_ID"));
				idBean.setUser_nick(rs.getString("USER_NICK"));
				if(rs.getString("K_XP") != null && !rs.getString("K_XP").equals("")){
					idBean.setKeywordInfo(rs.getString("K_XP")+","+rs.getString("K_YP")+","+rs.getString("K_ZP"));
				}
				idBean.setH_seq(rs.getString("H_SEQ"));
				
				idBean.setBlog_visit_count(rs.getString("BLOG_VISIT_COUNT"));
				idBean.setCafe_name(rs.getString("CAFE_NAME"));
				idBean.setCafe_member_count(rs.getString("CAFE_MEMBER_COUNT"));
				idBean.setD_seq(rs.getString("D_SEQ"));
				
				issueDataList.add(idBean);	
				if(tmpId_seq.equals("")) tmpId_seq += rs.getString("ID_SEQ");
				else tmpId_seq += "," + rs.getString("ID_SEQ");
			}
			
			
			String prevI_seq = null;			
			if( tmpId_seq.length() > 0 ) 
			{
				//이슈에 이슈코드어레이 추가
				ArrayList ArrIcList = null;
				ArrIcList = new ArrayList();	    			    	
		    	
				rs.close();
				sb = new StringBuffer();				
				sb.append("SELECT IDC.ID_SEQ 								        	\n");
				sb.append("		  ,IC.IC_SEQ							                \n");
				sb.append("		  ,IDC.IC_TYPE							                \n");
				sb.append(" 	  ,IDC.IC_CODE 								            \n");
				sb.append(" 	  ,IC.IC_PTYPE							                \n");
				sb.append(" 	  ,IC.IC_PCODE   						                \n");
				sb.append(" 	  ,DATE_FORMAT(IC.IC_REGDATE,'%Y-%m-%d %H:%i:%s') IC_REGDATE		\n");				
				sb.append(" 	  ,IC_DESCRIPTION								        \n");				
				sb.append(" 	  ,IC.M_SEQ								                \n");
				sb.append("       ,IC.IC_NAME 									        \n");
				sb.append("FROM ISSUE_CODE IC, ISSUE_DATA_CODE IDC	 					\n");
				sb.append("WHERE	IDC.ID_SEQ IN ("+tmpId_seq+")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,ArrIcList);        			
	        			ArrIcList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		icBean = new IssueCodeBean();
	        		icBean.setIc_seq(rs.getInt("IC_SEQ"));
	        		icBean.setIc_type(rs.getInt("IC_TYPE"));
	        		icBean.setIc_code(rs.getInt("IC_CODE"));
	        		icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
	        		icBean.setIc_pcode(rs.getInt("IC_PCODE"));
	        		icBean.setIc_regdate(rs.getString("IC_REGDATE"));
	        		icBean.setIc_description(rs.getString("IC_DESCRIPTION"));
	        		icBean.setM_seq(rs.getString("M_SEQ"));
	        		icBean.setIc_name(rs.getString("IC_NAME"));
	        		
	        		ArrIcList.add(icBean);
	        	}
				
				System.out.println(issueDataList.size()+"  issueDataList.size() ");
				System.out.println(ArrIcList.size()+"  ArrIcList.size() ");
				addIssueCode(issueDataList,prevI_seq,ArrIcList);
				
				
				//이슈에 이슈코멘트어레이 추가					    	
				rs = null;
				prevI_seq = null;	
				sb = new StringBuffer();
				ArrayList arrIcmList = new ArrayList();
				
				sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
				sb.append("FROM ISSUE_COMMENT	 						                           \n");
				sb.append("WHERE ID_SEQ IN ("+tmpId_seq+")  					                       \n");
				sb.append("ORDER BY IM_SEQ DESC                                 				   \n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,arrIcmList);        			
	        			arrIcmList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icmBean = new IssueCommentBean();
	        		icmBean.setIm_seq(rs.getString("IM_SEQ"));
	        		icmBean.setIm_comment(rs.getString("IM_COMMENT"));
	        		icmBean.setIm_date(rs.getString("IM_DATE"));       			        		
	        		
	        		arrIcmList.add(icmBean);
	        	}
				addIssueComment(issueDataList,prevI_seq,arrIcmList);
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
    	
    	return issueDataList;
    }
    
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,    		
    		String useYN,
    		String orderby
    )    
    {
    	return  getIssueDataList(pageNum,rowCnt,id_seq,i_seq,it_seq,md_seq,schKey,DateType,schSdate,schStime,schEdate,schEtime,typeCode,"","Y", orderby, "");
    }
    
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,    		
    		String useYN
    )    
    {
    	return  getIssueDataList(pageNum,rowCnt,id_seq,i_seq,it_seq,md_seq,schKey,DateType,schSdate,schStime,schEdate,schEtime,typeCode,"","Y", "", "");
    }
    
    public ArrayList getIssueDataList( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String orderby,
    		String md_type
    )    
    {    	
    	ArrayList issueDataList = new ArrayList();    	    	
    	String[] tmpArr = typeCode.split("@");
    	String[] arrTypeCode = null;
    	String tmpId_seq = null;
    	
    	
    	//code에 대한 쿼리문을 생성한다.
    	String tmpSameCodeYn = "";
    	int sameCodeCount = 0;
    	String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
    	codeQuery +=       "FROM ISSUE_DATA_CODE   \n";
    	codeQuery +=       "WHERE                  \n";
    	codeQuery +=       "	 1=1               \n";
    	for( int i=0 ; i<tmpArr.length; i++ )
		{
    		
			if (!tmpArr[i].equals("")) {
				arrTypeCode = tmpArr[i].split(",");		
					if (arrTypeCode.length==2) {
					if(tmpSameCodeYn.equals(arrTypeCode[0])) sameCodeCount++;
					codeQuery = i == 0 ?	
					codeQuery+"AND (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n" : codeQuery+" OR (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n";				
					tmpSameCodeYn = arrTypeCode[0];
				}
			}
		}
    	System.out.println("tmpArr.length:"+tmpArr.length);
    	System.out.println("sameCodeCount:"+sameCodeCount);
    	codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)="+(tmpArr.length-sameCodeCount);
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("		SELECT COUNT(ID.ID_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE_DATA ID                                                     \n");
			if(!typeCode.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			sb.append("		WHERE 1=1														\n");
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									\n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
			}
			if( !schKey.equals("") ) {
				sb.append("		AND	ID.ID_TITLE LIKE '%"+schKey+"%'  							\n");
			}
			if( !md_type.equals("") ) {
				sb.append("		AND	ID.MD_TYPE = "+md_type+"  							\n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
			}
			//긴급메일 발송 시 문제
			//sb.append(" 	AND ID.MD_SEQ = ID.MD_PSEQ								      \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueDataCnt  = rs.getInt("CNT");            	
            }
            
            rs.close();                     			
			sb = new StringBuffer();
			
			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	  \n");
			sb.append("        ,ID.IT_SEQ                                                     	  \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.SG_SEQ                                                        \n");
			sb.append("        ,ID.S_SEQ                                                         \n");
			sb.append("        ,ID.MD_SITE_NAME                                                  \n");
			sb.append("        ,ID.MD_SITE_MENU                                                  \n");
			sb.append("        ,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE           \n");
			sb.append("        ,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE     \n");
			sb.append("        ,ID.ID_WRITTER                                                    \n");
			sb.append("        ,ID.ID_CONTENT                                                    \n");
			sb.append("        ,ID.ID_MAILYN                                                     \n");
			sb.append("        ,ID.ID_USEYN                                                      \n");
			sb.append("        ,ID.M_SEQ                                                         \n");
			sb.append("        ,ID.MD_SAME_CT                                                    \n");
			sb.append("        ,ID.MD_TYPE                                                    \n");
			sb.append("        ,(SELECT M_NAME FROM MEMBER WHERE M_SEQ = ID.M_SEQ) AS M_NAME  \n");
			if(!typeCodeOrder.equals("")){
				sb.append("    ,IDC.IC_CODE      \n");
			}
			sb.append(" FROM ISSUE_DATA ID                                                    \n");
			if(!typeCodeOrder.equals("")&&typeCode.equals("")){
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = "+typeCodeOrder+") IDC  ON (ID.ID_SEQ = IDC.ID_SEQ)    \n");
			}			
			if(!typeCode.equals("")&&typeCodeOrder.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if(!typeCodeOrder.equals("")&&!typeCode.equals("")){
				sb.append("  INNER JOIN (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE  WHERE  IC_TYPE = "+typeCodeOrder+") IDC    \n");
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IDC.ID_SEQ AND ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			
			sb.append(" WHERE 1=1										      \n");
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									  \n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ in ("+md_seq+") 			                          \n");
			}
			if( !schKey.equals("") ) {
				sb.append("		AND	ID.ID_TITLE LIKE '%"+schKey+"%'  							  \n");
			}
			if( !md_type.equals("") ) {
				sb.append("		AND	ID.MD_TYPE = "+md_type+"  							\n");
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}			
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									      \n");
			}
			//긴급메일 발송 시 문제
			//sb.append(" 	AND ID.MD_SEQ = ID.MD_PSEQ								      \n");
			if(DateType.equals("1")){
				if(!typeCodeOrder.equals("")){
					sb.append(" ORDER BY IDC.IC_CODE, ID_REGDATE DESC 		                                      \n");
				}else{
					sb.append(" ORDER BY ID_REGDATE DESC 		                                      \n");
				}
			
			}else{
				if(!typeCodeOrder.equals("")){
					sb.append(" ORDER BY IDC.IC_CODE, MD_DATE DESC 		                                      \n");
				}else{
					if(orderby.equals("")){
						sb.append("	ORDER BY MD_DATE DESC 		                                          \n");
					}else{
						sb.append("	ORDER BY "+orderby+" DESC 		                                          \n");
					}
				}			
			}
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                       \n");
			}			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			tmpId_seq ="";
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				issueDataList.add(idBean);	
				if(tmpId_seq.equals("")) tmpId_seq += rs.getString("ID_SEQ");
				else tmpId_seq += "," + rs.getString("ID_SEQ");
			}
			
			
			
			String prevI_seq = null;			
			if( tmpId_seq.length() > 0 ) 
			{
				//이슈에 이슈코드어레이 추가
				ArrayList ArrIcList = null;
				ArrIcList = new ArrayList();	    			    	
		    	
				rs.close();
				sb = new StringBuffer();				
				sb.append("SELECT IDC.ID_SEQ 								        	\n");
				sb.append("		  ,IC.IC_SEQ							                \n");
				sb.append("		  ,IDC.IC_TYPE							                \n");
				sb.append(" 	  ,IDC.IC_CODE 								            \n");
				sb.append(" 	  ,IC.IC_PTYPE							                \n");
				sb.append(" 	  ,IC.IC_PCODE   						                \n");
				sb.append(" 	  ,DATE_FORMAT(IC.IC_REGDATE,'%Y-%m-%d %H:%i:%s') IC_REGDATE		\n");				
				sb.append(" 	  ,IC_DESCRIPTION								        \n");				
				sb.append(" 	  ,IC.M_SEQ								                \n");
				sb.append("       ,IC.IC_NAME 									        \n");
				sb.append("FROM ISSUE_CODE IC, ISSUE_DATA_CODE IDC	 					\n");
				sb.append("WHERE	IDC.ID_SEQ IN ("+tmpId_seq+")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,ArrIcList);        			
	        			ArrIcList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icBean = new IssueCodeBean();
	        		icBean.setIc_seq(rs.getInt("IC_SEQ"));
	        		icBean.setIc_type(rs.getInt("IC_TYPE"));
	        		icBean.setIc_code(rs.getInt("IC_CODE"));
	        		icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
	        		icBean.setIc_pcode(rs.getInt("IC_PCODE"));
	        		icBean.setIc_regdate(rs.getString("IC_REGDATE"));
	        		icBean.setIc_description(rs.getString("IC_DESCRIPTION"));
	        		icBean.setM_seq(rs.getString("M_SEQ"));
	        		icBean.setIc_name(rs.getString("IC_NAME"));
	        		
	        		ArrIcList.add(icBean);
	        	}
				addIssueCode(issueDataList,prevI_seq,ArrIcList);
				
				
				//이슈에 이슈코멘트어레이 추가					    	
				rs = null;
				prevI_seq = null;	
				sb = new StringBuffer();
				ArrayList arrIcmList = new ArrayList();
				
				sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
				sb.append("FROM ISSUE_COMMENT	 						                           \n");
				sb.append("WHERE ID_SEQ IN ("+tmpId_seq+")  					                       \n");
				sb.append("ORDER BY IM_SEQ DESC                                 				   \n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,arrIcmList);        			
	        			arrIcmList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icmBean = new IssueCommentBean();
	        		icmBean.setIm_seq(rs.getString("IM_SEQ"));
	        		icmBean.setIm_comment(rs.getString("IM_COMMENT"));
	        		icmBean.setIm_date(rs.getString("IM_DATE"));       			        		
	        		
	        		arrIcmList.add(icmBean);
	        	}
				addIssueComment(issueDataList,prevI_seq,arrIcmList);
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
    	
    	return issueDataList;
    }
    
    public ArrayList getPrivateMediaCnt(String id_seq){
    	ArrayList result = new ArrayList();
    	HashMap bean = null;
    	DBconn dbconn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	StringBuffer sb = null;
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append("SELECT SG.SG_NAME, COUNT(*) AS CNT\n");
    		sb.append("FROM ISSUE_DATA ID\n");
    		sb.append("INNER JOIN SITE_GROUP SG\n");
    		sb.append("ON ID.MD_PSEQ = (SELECT MD_PSEQ FROM ISSUE_DATA WHERE ID_SEQ = "+id_seq+")\n");
    		sb.append("AND ID.MD_SEQ <> (SELECT MD_SEQ FROM ISSUE_DATA WHERE ID_SEQ = "+id_seq+")\n");
    		sb.append("AND ID.SG_SEQ = SG.SG_SEQ\n");
    		sb.append("AND ID.SG_SEQ <> 3\n");
    		sb.append("GROUP BY SG.SG_NAME\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			bean = new HashMap();
    			bean.put("SG_NAME", rs.getString("SG_NAME"));
    			bean.put("CNT", rs.getString("CNT"));
    			result.add(bean);
    		}
    	}catch(SQLException e){
    		Log.writeExpt(e);
    	}catch(Exception e){
    		Log.writeExpt(e);
    	}finally{
    		sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
    	return result;
    }
    
    public String getTwitterWriter(String md_seq){
    	String result = "";
    	
    	DBconn dbconn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	StringBuffer sb = null;
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append("SELECT T_USER_ID FROM TWEET WHERE MD_SEQ = "+md_seq+"\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		if(rs.next()){
    			result = rs.getString("T_USER_ID");
    		}
    	}catch(SQLException e){
    		Log.writeExpt(e);
    	}catch(Exception e){
    		Log.writeExpt(e);
    	}finally{
    		sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
    	return result;
    }
    
    
    public ArrayList getIssueDataList_excel( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news,
    		String id_mobile,
    		String kind,
    		String searchType
    )    
    {
    	ArrayList issueDataList = new ArrayList();
    	try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			String[] tmpArr = typeCode.split("@");
	    	String[] arrTypeCode = null;
	    	String tmpId_seq = "";
	    	
	    	
	    	//code에 대한 쿼리문을 생성한다.
	    	String tmpSameCodeYn = "";
	    	int sameCodeCount = 0;
	    	String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
	    	codeQuery +=       "FROM ISSUE_DATA_CODE   \n";
	    	codeQuery +=       "WHERE                  \n";
	    	codeQuery +=       "	 1=1               \n";
	    	for( int i=0 ; i<tmpArr.length; i++ )
			{
	    		
				if (!tmpArr[i].equals("")) {
					arrTypeCode = tmpArr[i].split(",");		
						if (arrTypeCode.length==2) {
						if(tmpSameCodeYn.equals(arrTypeCode[0])) sameCodeCount++;
						codeQuery = i == 0 ?	
						codeQuery+"AND (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n" : codeQuery+" OR (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n";				
						tmpSameCodeYn = arrTypeCode[0];
					}
				}
			}
	    	//System.out.println("tmpArr.length:"+tmpArr.length);
	    	//System.out.println("sameCodeCount:"+sameCodeCount);
	    	codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)="+(tmpArr.length-sameCodeCount);
			
			sb = new StringBuffer();	
			sb.append("SELECT A.*													\n");
//			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT 			\n");
			sb.append("		, FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS SITE_GROUP_SAME_CT 				\n");
			sb.append("		, FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS SITE_GROUP_SAME_CT_CHECK 	\n");
			//sb.append("		, FN_PRESS_TITLE(A.ID_SEQ) AS PRESS_TITLE				\n");
			//sb.append("		, FN_ANA_RELATION_KEYWORD(A.ID_SEQ) AS RELATION_KEYWORD				\n");
			//sb.append("		, FN_GET_MEDIA(A.ID_SEQ) AS MEDIA									\n");
			sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME	\n");
			sb.append("  FROM(														\n");
			
			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	  \n");
			sb.append("        ,ID.IT_SEQ                                                     	  \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			sb.append("        ,ID.SG_SEQ                                                        \n");
			sb.append("        ,ID.S_SEQ                                                         \n");
			sb.append("        ,ID.MD_SITE_NAME                                                  \n");
			sb.append("        ,ID.MD_SITE_MENU                                                  \n");
			sb.append("        ,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE           \n");
			sb.append("        ,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE     \n");
			sb.append("        ,ID.ID_WRITTER                                                    \n");
			sb.append("        ,ID.ID_CONTENT                                                    \n");
			sb.append("        ,ID.ID_MAILYN                                                     \n");
			sb.append("        ,ID.ID_USEYN                                                      \n");
			sb.append("        ,ID.M_SEQ                                                         \n");
			sb.append("        ,ID.MD_SAME_CT                                                    	 \n");
			sb.append("        ,ID.MD_TYPE                                                    	 \n");
			sb.append("        , ID.MD_PSEQ 													 \n");
			if("group".equals(kind)){
			sb.append("        , count(ID.MD_PSEQ) AS SAME_CT									 \n");
			}
			sb.append("   FROM ( 													 \n");
			
			
			sb.append("SELECT ID.* FROM ISSUE_DATA ID 													 \n");
			
			
			if(!"".equals(typeCode)&&"".equals(typeCodeOrder)){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			
			sb.append(" WHERE 1=1										      \n");
			if(!"".equals(reportyn)){
				sb.append("   AND ID.ID_REPORTYN = '"+reportyn+"' 						      \n");
			}
			
			if( !"".equals(id_seq) ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									  \n");
			}
			if( !"".equals(i_seq) ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !"".equals(it_seq) ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !"".equals(md_seq) ) {
				sb.append("		AND	ID.MD_SEQ in ("+md_seq+") 			                          \n");
			}
			if( !"".equals(id_mobile) ) {
				sb.append("		AND	ID.ID_MOBILE = '"+id_mobile+"' 			                        \n");
			}
			if( !"".equals(schKey) ) {
				
				String[] arrSchKey = schKey.split(" ");
				
				for(int i =0; i < arrSchKey.length; i++){
					if(searchType.equals("1")){
						sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
					}else{
						sb.append("		AND	CONCAT(ID.ID_TITLE , ID.ID_CONTENT) LIKE '%"+arrSchKey[i]+"%'  							\n");
					}
					
				}
				
			}
			if( !"".equals(schSdate) && !"".equals(schEdate)) {
				if(!"".equals(schStime) && !"".equals(schEtime)){
					if("1".equals(schEtime)){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if("1".equals(DateType)){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}			
			if (!"".equals(useYN)) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									      \n");
			}
			
			if("Y".equals(parents)){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			sb.append(" ORDER BY MD_SEQ ASC		\n");
			sb.append("   )ID 																 \n");
			if("group".equals(kind)){
				sb.append("   GROUP BY ID.MD_PSEQ 												 \n");
			}
			sb.append("   ORDER BY ID.ID_SEQ DESC 											 \n");
			sb.append("  )A                                                                    \n");
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				if("group".equals(kind)){
					idBean.setMd_same_ct(rs.getString("SAME_CT"));	
				}else{
					idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				}
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setSite_group_same_ct(rs.getString("SITE_GROUP_SAME_CT"));
				idBean.setSite_group_same_ct_check(rs.getString("SITE_GROUP_SAME_CT_CHECK"));
				//idBean.setRelationkeys(rs.getString("RELATION_KEYWORD"));
				//idBean.setPressTitle(rs.getString("PRESS_TITLE"));
				//idBean.setMedia_name(rs.getString("MEDIA"));
				issueDataList.add(idBean);
				
				if("".equals(tmpId_seq)) {
					tmpId_seq += rs.getString("ID_SEQ");
				}else{
					tmpId_seq += "," + rs.getString("ID_SEQ");
				}
			}

			String prevI_seq = null;			
			if( tmpId_seq.length() > 0 ) 
			{
				//이슈에 이슈코드어레이 추가
				ArrayList ArrIcList = null;
				ArrIcList = new ArrayList();	    			    	
		    	
				rs.close();
				sb = new StringBuffer();				
				sb.append("SELECT IDC.ID_SEQ 								        	\n");
				sb.append("		  ,IC.IC_SEQ							                \n");
				sb.append("		  ,IDC.IC_TYPE							                \n");
				sb.append(" 	  ,IDC.IC_CODE 								            \n");
				sb.append(" 	  ,IC.IC_PTYPE							                \n");
				sb.append(" 	  ,IC.IC_PCODE   						                \n");
				sb.append(" 	  ,DATE_FORMAT(IC.IC_REGDATE,'%Y-%m-%d %H:%i:%s') IC_REGDATE		\n");				
				sb.append(" 	  ,IC_DESCRIPTION								        \n");				
				sb.append(" 	  ,IC.M_SEQ								                \n");
				sb.append("       ,IC.IC_NAME 									        \n");
				sb.append("FROM ISSUE_CODE IC, ISSUE_DATA_CODE IDC	 					\n");
				sb.append("WHERE	IDC.ID_SEQ IN ("+tmpId_seq+")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,ArrIcList);        			
	        			ArrIcList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icBean = new IssueCodeBean();
	        		icBean.setIc_seq(rs.getInt("IC_SEQ"));
	        		icBean.setIc_type(rs.getInt("IC_TYPE"));
	        		icBean.setIc_code(rs.getInt("IC_CODE"));
	        		icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
	        		icBean.setIc_pcode(rs.getInt("IC_PCODE"));
	        		icBean.setIc_regdate(rs.getString("IC_REGDATE"));
	        		icBean.setIc_description(rs.getString("IC_DESCRIPTION"));
	        		icBean.setM_seq(rs.getString("M_SEQ"));
	        		icBean.setIc_name(rs.getString("IC_NAME"));
	        		
	        		ArrIcList.add(icBean);
	        	}
				addIssueCode(issueDataList,prevI_seq,ArrIcList);
				
				
				//이슈에 이슈코멘트어레이 추가					    	
				rs = null;
				prevI_seq = null;	
				sb = new StringBuffer();
				ArrayList arrIcmList = new ArrayList();
				
				sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
				sb.append("FROM ISSUE_COMMENT	 						                           \n");
				sb.append("WHERE ID_SEQ IN ("+tmpId_seq+")  					                       \n");
				sb.append("ORDER BY IM_SEQ DESC                                 				   \n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,arrIcmList);        			
	        			arrIcmList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icmBean = new IssueCommentBean();
	        		icmBean.setIm_seq(rs.getString("IM_SEQ"));
	        		icmBean.setIm_comment(rs.getString("IM_COMMENT"));
	        		icmBean.setIm_date(rs.getString("IM_DATE"));       			        		
	        		
	        		arrIcmList.add(icmBean);
	        	}
				addIssueComment(issueDataList,prevI_seq,arrIcmList);
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
    	
    	return issueDataList;
    }
    
    
    public ArrayList getIssueDataList_groupBy( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news,
    		String id_mobile,
    		String searchType,
    		String pseqVal,
    		String tiers,
    		String m_seq
    )    
    {    	
    	ArrayList issueDataList = new ArrayList();    	    	
    	String[] tmpArr = typeCode.split("@");
    	String[] arrTypeCode = null;
    	String tmpId_seq = null;
    	
    	
    	//code에 대한 쿼리문을 생성한다.
    	String tmpSameCodeYn = "";
    	int sameCodeCount = 0;
    	String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
    	codeQuery +=       "FROM ISSUE_DATA_CODE   \n";
    	codeQuery +=       "WHERE                  \n";
    	codeQuery +=       "	 1=1               \n";
    	for( int i=0 ; i<tmpArr.length; i++ )
		{
    		
			if (!tmpArr[i].equals("")) {
				arrTypeCode = tmpArr[i].split(",");		
					if (arrTypeCode.length==2) {
					if(tmpSameCodeYn.equals(arrTypeCode[0])) sameCodeCount++;
					codeQuery = i == 0 ?	
					codeQuery+"AND (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n" : codeQuery+" OR (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n";				
					tmpSameCodeYn = arrTypeCode[0];
				}
			}
		}
    	System.out.println("tmpArr.length:"+tmpArr.length);
    	System.out.println("sameCodeCount:"+sameCodeCount);
    	codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)="+(tmpArr.length-sameCodeCount);
    	
    	//리스트 시작, 끝번호 
		if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			
			sb.append("SELECT COUNT(*) AS CNT FROM (\n");
			sb.append("		SELECT MD_PSEQ AS CNT 								\n");
			//sb.append("		SELECT COUNT(ID.ID_SEQ) AS CNT 								\n");
			sb.append("		     , SUM(ID.MD_SAME_CT + 1) AS SAME_CNT 					\n");
			sb.append("		FROM ISSUE_DATA ID                                          \n");
			if(!typeCode.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if(!"".equals(pseqVal)){
				sb.append(" , PRESS_ISSUE_DATA PD	\n");
			}
			if(!"".equals(tiers)){
				sb.append(" , TIER_SITE TS	\n");
			}
			sb.append("		WHERE 1=1													\n");

			if(!"".equals(tiers)){
				sb.append("		  AND ID.S_SEQ = TS.TS_SEQ																								\n");
				sb.append("		  AND TS.TS_TYPE IN ("+tiers+")																							\n");
			}
			if(!"".equals(pseqVal)){
				sb.append(" AND ID.ID_SEQ = PD.ID_SEQ AND PD.P_SEQ IN ("+pseqVal+")	\n");
			}
			if(!m_seq.equals("")){
				sb.append("		AND	ID.M_SEQ IN ("+m_seq+")  							\n");
			}
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  							\n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
			}
			if( !id_mobile.equals("") ) {
				sb.append("		AND	ID.ID_MOBILE = '"+id_mobile+"' 			                        \n");
			}
			if( !schKey.equals("") ) {
				String[] arrSchKey = schKey.split(" ");
				
				for(int i =0; i < arrSchKey.length; i++){
					if(searchType.equals("1")){
						sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
					}else{
						sb.append("		AND	CONCAT(ID.ID_TITLE , ID.ID_CONTENT) LIKE '%"+arrSchKey[i]+"%'  							\n");
					}
					
				}
				
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
			}
			
			//모기사일경우
			if(parents.equals("Y")){
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			
			sb.append(" GROUP BY ID.MD_PSEQ)A		\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueDataCnt  = rs.getInt("CNT");
            	//totalSameDataCnt = rs.getInt("SAME_CNT");
            }
            rs.close();                     			
			sb = new StringBuffer();
			sb.append(" ## 출처별 카운터 수		\n");
			sb.append(" SELECT 																											\n");
			sb.append("   (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE = AA.IC_CODE) AS NAME	                        \n");
			sb.append("   ,IFNULL(BB.CNT,0) AS CNT                                                                                      \n");
			sb.append(" FROM                                                                                                            \n");
			sb.append(" (SELECT IC_NAME, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE > 0 ORDER BY 2 ASC) AA LEFT OUTER JOIN   \n");
			sb.append(" (SELECT		\n");
			sb.append(" 		 ISC.IC_CODE	\n");
			sb.append(" 		,COUNT(ISC.IC_CODE) AS CNT		\n");
            sb.append("		FROM ISSUE_DATA ID                                          \n");
			if(!typeCode.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			sb.append("		, ISSUE_DATA_CODE ISC										\n");
			if(!"".equals(pseqVal)){
				sb.append(" , PRESS_ISSUE_DATA PD	\n");
			}
			if(!"".equals(tiers)){
				sb.append(" , TIER_SITE TS	\n");
			}
			sb.append("		WHERE 1=1													\n");
			if(!"".equals(tiers)){
				sb.append("		  AND ID.S_SEQ = TS.TS_SEQ																								\n");
				sb.append("		  AND TS.TS_TYPE IN ("+tiers+")																							\n");
			}
			sb.append("		  AND ID.ID_SEQ = ISC.ID_SEQ								\n");
			sb.append("		  AND ISC.IC_TYPE = 6										\n");
			if(!"".equals(pseqVal)){
				sb.append(" AND ID.ID_SEQ = PD.ID_SEQ AND PD.P_SEQ IN ("+pseqVal+")	\n");
			}
			if(!m_seq.equals("")){
				sb.append("		AND	ID.M_SEQ IN ("+m_seq+")  							\n");
			}
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  							\n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
			}
			if( !id_mobile.equals("") ) {
				sb.append("		AND	ID.ID_MOBILE = '"+id_mobile+"' 			                        \n");
			}
			if( !schKey.equals("") ) {
				String[] arrSchKey = schKey.split(" ");
				
				for(int i =0; i < arrSchKey.length; i++){
					if(searchType.equals("1")){
						sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
					}else{
						sb.append("		AND	CONCAT(ID.ID_TITLE , ID.ID_CONTENT) LIKE '%"+arrSchKey[i]+"%'  							\n");
					}
				}
				
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}				
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
			}
			
			//모기사일경우
			if(parents.equals("Y")){
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			sb.append(" GROUP BY ISC.IC_CODE) BB ON AA.IC_CODE = BB.IC_CODE		\n");
			//sb.append(" UNION ALL		\n");
			//sb.append(" SELECT		\n");
			//sb.append(" 		 '언론사(포탈제외)' AS NAME	\n");
			//sb.append(" 		,COUNT(ISC.IC_CODE) AS CNT		\n");
            //sb.append("		FROM ISSUE_DATA ID                                          \n");
			//if(!typeCode.equals("")){
			//	sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			//}
			//sb.append("		, ISSUE_DATA_CODE ISC										\n");
			//if(!"".equals(pseqVal)){
			//	sb.append(" , PRESS_ISSUE_DATA PD	\n");
			//}
			//sb.append("		WHERE 1=1													\n");
			//sb.append("		  AND ID.ID_SEQ = ISC.ID_SEQ								\n");
			//sb.append("		  AND ISC.IC_TYPE = 6 AND ISC.IC_CODE = 1					\n");
			//sb.append("		  AND ID.S_SEQ NOT IN (2196,2199,3883,5016219)				\n");
			//if(!"".equals(pseqVal)){
			//	sb.append(" AND ID.ID_SEQ = PD.ID_SEQ AND PD.P_SEQ IN ("+pseqVal+")	\n");
			//}
			//if(!m_seq.equals("")){
			//	sb.append("		AND	ID.M_SEQ IN ("+m_seq+")  							\n");
			//}
			//if( !id_seq.equals("") ) {
			//	sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  							\n");
			//}
			//if( !i_seq.equals("") ) {
			//	sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			//}
			//if( !it_seq.equals("") ) {
			//	sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			//}
			//if( !md_seq.equals("") ) {
			//	sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
			//}
			//if( !id_mobile.equals("") ) {
			//	sb.append("		AND	ID.ID_MOBILE = '"+id_mobile+"' 			                        \n");
			//}
			//if( !schKey.equals("") ) {
			//	String[] arrSchKey = schKey.split(" ");
			//	
			//	for(int i =0; i < arrSchKey.length; i++){
			//		if(searchType.equals("1")){
			//			sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
			//		}else{
			//			sb.append("		AND	CONCAT(ID.ID_TITLE , ID.ID_CONTENT) LIKE '%"+arrSchKey[i]+"%'  							\n");
			//		}
			//	}
			//	
			//}
			//if( !schSdate.equals("") && !schEdate.equals("")) {
			//	if(!schStime.equals("") && !schEtime.equals("")){
			//		if(DateType.equals("1")){				
			//		sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
			//		}else{
			//		sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
			//		}
			//	}else{
			//		if(DateType.equals("1")){				
			//		sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
			//		}else{
			//		sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
			//		}
			//	}
			//}				
			//if (!useYN.equals("")) {
			//	sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
			//}
			//
			////모기사일경우
			//if(parents.equals("Y")){
			//	sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			//}
			//sb.append(" GROUP BY ISC.IC_CODE	\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			String tmp[] = new String[2];
			while(rs.next()){
				tmp = new String[2];
				tmp[0] = rs.getString("NAME"); 
				tmp[1] = rs.getString("CNT");
				arraySourceList.add(tmp);
			}
            
            rs.close();                     			
			sb = new StringBuffer();
			
			sb.append("SELECT A.*													\n");
//			sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT 			\n");
			sb.append("		, FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS SITE_GROUP_SAME_CT 				\n");
			sb.append("		, FN_GET_SITE_SAME_CHECK(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS SITE_GROUP_SAME_CT_CHECK 	\n");
			sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME	\n");
			sb.append("  FROM(														\n");
			
			sb.append(" SELECT ID.ID_SEQ                                                         \n");
			sb.append("        ,ID.MD_SEQ                                                        \n");
			sb.append("        ,ID.I_SEQ                                                     	  \n");
			sb.append("        ,ID.IT_SEQ                                                     	  \n");
			sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.ID_TITLE                                                      \n");
			//sb.append("        ,ID.ID_URL                                                        \n");
			sb.append("        ,ID.SG_SEQ                                                        \n");
			sb.append("        ,ID.S_SEQ                                                         \n");
			sb.append("        ,ID.MD_SITE_NAME                                                  \n");
			sb.append("        ,ID.MD_SITE_MENU                                                  \n");
			sb.append("        ,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE           \n");
			sb.append("        ,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE     \n");
			sb.append("        ,ID.ID_WRITTER                                                    \n");
			sb.append("        ,ID.ID_CONTENT                                                    \n");
			sb.append("        ,ID.ID_MAILYN                                                     \n");
			sb.append("        ,ID.ID_USEYN                                                      \n");
			sb.append("        ,ID.M_SEQ                                                         \n");
			sb.append("        ,ID.MD_SAME_CT                                                    	 \n");
			sb.append("        ,ID.MD_TYPE                                                    	 \n");
			sb.append("        , ID.MD_PSEQ 													 \n");
			sb.append("   FROM ( 													 \n");
			sb.append("SELECT ID.* FROM ISSUE_DATA ID 													 \n");
			if(!typeCode.equals("")&&typeCodeOrder.equals("")){
				sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
			}
			if(!"".equals(pseqVal)){
				sb.append(" ,PRESS_ISSUE_DATA PD	\n");
			}
			if(!"".equals(tiers)){
				sb.append(" , TIER_SITE TS	\n");
			}
			sb.append(" WHERE 1=1										      \n");
			if(!"".equals(tiers)){
				sb.append("		  AND ID.S_SEQ = TS.TS_SEQ																								\n");
				sb.append("		  AND TS.TS_TYPE IN ("+tiers+")																							\n");
			}
			if(!"".equals(pseqVal)){
				sb.append(" AND ID.ID_SEQ = PD.ID_SEQ AND PD.P_SEQ IN ("+pseqVal+")	\n");
			}
			if(!m_seq.equals("")){
				sb.append("		AND	ID.M_SEQ IN ("+m_seq+")  							\n");
			}
			if(!reportyn.equals("")){
				sb.append("   AND ID.ID_REPORTYN = '"+reportyn+"' 						      \n");
			}
			
			if( !id_seq.equals("") ) {
				sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									  \n");
			}
			if( !i_seq.equals("") ) {
				sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
			}
			if( !it_seq.equals("") ) {
				sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
			}
			if( !md_seq.equals("") ) {
				sb.append("		AND	ID.MD_SEQ in ("+md_seq+") 			                          \n");
			}
			if( !id_mobile.equals("") ) {
				sb.append("		AND	ID.ID_MOBILE = '"+id_mobile+"' 			                        \n");
			}
			if( !schKey.equals("") ) {
				
				String[] arrSchKey = schKey.split(" ");
				
				for(int i =0; i < arrSchKey.length; i++){
					if(searchType.equals("1")){
						sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
					}else{
						sb.append("		AND	CONCAT(ID.ID_TITLE , ID.ID_CONTENT) LIKE '%"+arrSchKey[i]+"%'  							\n");
					}
				}
				
			}
			if( !schSdate.equals("") && !schEdate.equals("")) {
				if(!schStime.equals("") && !schEtime.equals("")){
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
					}
				}else{
					if(DateType.equals("1")){				
					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
					}else{
					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
					}
				}
			}			
			if (!useYN.equals("")) {
				sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									      \n");
			}
			
			if(parents.equals("Y")){
				//모기사일경우
				sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
			}
			sb.append(" ORDER BY MD_SEQ ASC		\n");
			sb.append("   )ID 																 \n");
			sb.append("   GROUP BY ID.MD_PSEQ 												 \n");
			sb.append("   ORDER BY ID.ID_SEQ DESC 											 \n");
			if(pageNum>0){
			sb.append(" LIMIT   "+startNum+","+endNum+"                                       \n");
			}			
			
			sb.append("  )A                                                                    \n");
			
			System.out.println(sb.toString());		
			rs = stmt.executeQuery(sb.toString());
			
			tmpId_seq ="";
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setM_name(rs.getString("M_NAME"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setSite_group_same_ct(rs.getString("SITE_GROUP_SAME_CT"));
				idBean.setSite_group_same_ct_check(rs.getString("SITE_GROUP_SAME_CT_CHECK"));
				
				issueDataList.add(idBean);	
				if(tmpId_seq.equals("")) tmpId_seq += rs.getString("ID_SEQ");
				else tmpId_seq += "," + rs.getString("ID_SEQ");
			}
			
			
			
			String prevI_seq = null;			
			if( tmpId_seq.length() > 0 ) 
			{
				//이슈에 이슈코드어레이 추가
				ArrayList ArrIcList = null;
				ArrIcList = new ArrayList();	    			    	
		    	
				rs.close();
				sb = new StringBuffer();				
				sb.append("SELECT IDC.ID_SEQ 								        	\n");
				sb.append("		  ,IC.IC_SEQ							                \n");
				sb.append("		  ,IDC.IC_TYPE							                \n");
				sb.append(" 	  ,IDC.IC_CODE 								            \n");
				sb.append(" 	  ,IC.IC_PTYPE							                \n");
				sb.append(" 	  ,IC.IC_PCODE   						                \n");
				sb.append(" 	  ,DATE_FORMAT(IC.IC_REGDATE,'%Y-%m-%d %H:%i:%s') IC_REGDATE		\n");				
				sb.append(" 	  ,IC_DESCRIPTION								        \n");				
				sb.append(" 	  ,IC.M_SEQ								                \n");
				sb.append("       ,IC.IC_NAME 									        \n");
				sb.append("FROM ISSUE_CODE IC, ISSUE_DATA_CODE IDC	 					\n");
				sb.append("WHERE	IDC.ID_SEQ IN ("+tmpId_seq+")  						\n");
				sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
				sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
				sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,ArrIcList);        			
	        			ArrIcList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icBean = new IssueCodeBean();
	        		icBean.setIc_seq(rs.getInt("IC_SEQ"));
	        		icBean.setIc_type(rs.getInt("IC_TYPE"));
	        		icBean.setIc_code(rs.getInt("IC_CODE"));
	        		icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
	        		icBean.setIc_pcode(rs.getInt("IC_PCODE"));
	        		icBean.setIc_regdate(rs.getString("IC_REGDATE"));
	        		icBean.setIc_description(rs.getString("IC_DESCRIPTION"));
	        		icBean.setM_seq(rs.getString("M_SEQ"));
	        		icBean.setIc_name(rs.getString("IC_NAME"));
	        		
	        		ArrIcList.add(icBean);
	        	}
				addIssueCode(issueDataList,prevI_seq,ArrIcList);
				
				
				//이슈에 이슈코멘트어레이 추가					    	
				rs = null;
				prevI_seq = null;	
				sb = new StringBuffer();
				ArrayList arrIcmList = new ArrayList();
				
				sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
				sb.append("FROM ISSUE_COMMENT	 						                           \n");
				sb.append("WHERE ID_SEQ IN ("+tmpId_seq+")  					                       \n");
				sb.append("ORDER BY IM_SEQ DESC                                 				   \n");				
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
	        		
	        		if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
	        		{
	        			addIssueCode(issueDataList,prevI_seq,arrIcmList);        			
	        			arrIcmList = new ArrayList();
	        		}        		
	        		
	        		prevI_seq = rs.getString("ID_SEQ");
	        		
	        		icmBean = new IssueCommentBean();
	        		icmBean.setIm_seq(rs.getString("IM_SEQ"));
	        		icmBean.setIm_comment(rs.getString("IM_COMMENT"));
	        		icmBean.setIm_date(rs.getString("IM_DATE"));       			        		
	        		
	        		arrIcmList.add(icmBean);
	        	}
				addIssueComment(issueDataList,prevI_seq,arrIcmList);
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
    	
    	return issueDataList;
    }
    
    public ArrayList getIssueDataList_groupBy_OLD( 
    		int pageNum, 
    		int rowCnt, 
    		String id_seq,
    		String i_seq,
    		String it_seq,
    		String md_seq, 
    		String schKey,
    		String DateType,
    		String schSdate,
    		String schStime,
    		String schEdate,
    		String schEtime,
    		String typeCode,
    		String typeCodeOrder,
    		String useYN,
    		String language,
    		String reportyn,
    		String parents,
    		String f_news
    )    
    {    	
    	ArrayList issueDataList = new ArrayList();    	    	
    	String[] tmpArr = typeCode.split("@");
    	String[] arrTypeCode = null;
    	String tmpId_seq = null;
    	
    	
    	//code에 대한 쿼리문을 생성한다.
    	String tmpSameCodeYn = "";
    	int sameCodeCount = 0;
    	String codeQuery = "SELECT DISTINCT(ID_SEQ) \n";
    	codeQuery +=       "FROM ISSUE_DATA_CODE_OLD   \n";
    	codeQuery +=       "WHERE                  \n";
    	codeQuery +=       "	 1=1               \n";
    	for( int i=0 ; i<tmpArr.length; i++ )
    	{
    		
    		if (!tmpArr[i].equals("")) {
    			arrTypeCode = tmpArr[i].split(",");		
    			if (arrTypeCode.length==2) {
    				if(tmpSameCodeYn.equals(arrTypeCode[0])) sameCodeCount++;
    				codeQuery = i == 0 ?	
    						codeQuery+"AND (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n" : codeQuery+" OR (IC_TYPE="+arrTypeCode[0]+" AND IC_CODE="+arrTypeCode[1]+") \n";				
    						tmpSameCodeYn = arrTypeCode[0];
    			}
    		}
    	}
    	System.out.println("tmpArr.length:"+tmpArr.length);
    	System.out.println("sameCodeCount:"+sameCodeCount);
    	codeQuery += "GROUP BY ID_SEQ HAVING COUNT(0)="+(tmpArr.length-sameCodeCount);
    	
    	//리스트 시작, 끝번호 
    	if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
    	this.endNum = rowCnt;
    	
    	
    	try {
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		
    		sb = new StringBuffer();		
    		
    		sb.append("SELECT COUNT(*) AS CNT FROM (\n");
    		sb.append("		SELECT MD_SEQ AS CNT 								\n");
    		sb.append("		FROM ISSUE_DATA_OLD ID                                          \n");
    		if(!typeCode.equals("")){
    			sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
    		}
    		sb.append("		WHERE 1=1													\n");
    		if( !id_seq.equals("") ) {
    			sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  							\n");
    		}
    		if( !i_seq.equals("") ) {
    			sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
    		}
    		if( !it_seq.equals("") ) {
    			sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
    		}
    		if( !md_seq.equals("") ) {
    			sb.append("		AND	ID.MD_SEQ IN ("+md_seq+") 			                        \n");
    		}
    		if( !schKey.equals("") ) {
    			String[] arrSchKey = schKey.split(" ");
    			
    			for(int i =0; i < arrSchKey.length; i++){
    				sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
    			}
    			
    		}
    		if( !schSdate.equals("") && !schEdate.equals("")) {
    			if(!schStime.equals("") && !schEtime.equals("")){
    				if(DateType.equals("1")){				
    					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
    				}else{
    					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
    				}
    			}else{
    				if(DateType.equals("1")){				
    					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
    				}else{
    					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
    				}
    			}
    		}				
    		if (!useYN.equals("")) {
    			sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									    \n");
    		}
    		
    		//모기사일경우
    		if(parents.equals("Y")){
    			sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
    		}
    		
    		sb.append(" )A		\n");
    		
    		
    		System.out.println(sb.toString());
    		rs = stmt.executeQuery(sb.toString());
    		if ( rs.next() ) {
    			totalIssueDataCnt  = rs.getInt("CNT");
    			//totalSameDataCnt = rs.getInt("SAME_CNT");
    		}
    		
    		rs.close();                     			
    		sb = new StringBuffer();
    		
    		sb.append("SELECT A.*													\n");
    		//sb.append("     , FN_GET_SITE_SAME(A.MD_PSEQ,'"+schSdate+" "+schStime+"','"+schEdate+" "+schEtime+"') AS MD_SAME_CT 			\n");
    		sb.append("     , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = 3) AS M_NAME	\n");
    		sb.append("  FROM(														\n");
    		
    		sb.append(" SELECT ID.ID_SEQ                                                         \n");
    		sb.append("        ,ID.MD_SEQ                                                        \n");
    		sb.append("        ,ID.I_SEQ                                                     	  \n");
    		sb.append("        ,ID.IT_SEQ                                                     	  \n");
    		sb.append("        ,ID.ID_URL                                                        \n");
    		sb.append("        ,ID.ID_TITLE                                                      \n");
    		//sb.append("        ,ID.ID_URL                                                        \n");
    		sb.append("        ,ID.SG_SEQ                                                        \n");
    		sb.append("        ,ID.S_SEQ                                                         \n");
    		sb.append("        ,ID.MD_SITE_NAME                                                  \n");
    		sb.append("        ,ID.MD_SITE_MENU                                                  \n");
    		sb.append("        ,DATE_FORMAT(ID.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE           \n");
    		sb.append("        ,DATE_FORMAT(ID.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE     \n");
    		sb.append("        ,ID.ID_WRITTER                                                    \n");
    		sb.append("        ,ID.ID_CONTENT                                                    \n");
    		sb.append("        ,ID.ID_MAILYN                                                     \n");
    		sb.append("        ,ID.ID_USEYN                                                      \n");
    		sb.append("        ,ID.M_SEQ                                                         \n");
    		sb.append("        ,ID.MD_TYPE                                                    	 \n");
    		sb.append("        , ID.MD_PSEQ 													 \n");
    		
    		
    		sb.append("   FROM ( 													 \n");
    		
    		
    		sb.append("SELECT ID.* FROM ISSUE_DATA_OLD ID 													 \n");
    		
    		
    		if(!typeCode.equals("")&&typeCodeOrder.equals("")){
    			sb.append("  INNER JOIN ("+codeQuery+")IC ON (ID.ID_SEQ = IC.ID_SEQ)    \n");
    		}
    		
    		sb.append(" WHERE 1=1										      \n");
    		if(!reportyn.equals("")){
    			sb.append("   AND ID.ID_REPORTYN = '"+reportyn+"' 						      \n");
    		}
    		
    		if( !id_seq.equals("") ) {
    			sb.append("		AND	ID.ID_SEQ IN ("+id_seq+")  									  \n");
    		}
    		if( !i_seq.equals("") ) {
    			sb.append("		AND	ID.I_SEQ IN ("+i_seq+")  									\n");
    		}
    		if( !it_seq.equals("") ) {
    			sb.append("		AND	ID.IT_SEQ IN ("+it_seq+")  									\n");
    		}
    		if( !md_seq.equals("") ) {
    			sb.append("		AND	ID.MD_SEQ in ("+md_seq+") 			                          \n");
    		}
    		if( !schKey.equals("") ) {
    			
    			String[] arrSchKey = schKey.split(" ");
    			
    			for(int i =0; i < arrSchKey.length; i++){
    				sb.append("		AND	ID.ID_TITLE LIKE '%"+arrSchKey[i]+"%'  							\n");
    			}
    			
    		}
    		if( !schSdate.equals("") && !schEdate.equals("")) {
    			if(!schStime.equals("") && !schEtime.equals("")){
    				if(DateType.equals("1")){				
    					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            	\n");
    				}else{
    					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" "+schStime+"' AND '"+schEdate+" "+schEtime+"'            		\n");	
    				}
    			}else{
    				if(DateType.equals("1")){				
    					sb.append("		AND	ID.ID_REGDATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            	\n");
    				}else{
    					sb.append("		AND	ID.MD_DATE BETWEEN '"+schSdate+" 00:00:00' AND '"+schEdate+" 23:59:59'            		\n");	
    				}
    			}
    		}			
    		if (!useYN.equals("")) {
    			sb.append(" 	AND ID.ID_USEYN = '"+useYN+"'									      \n");
    		}
    		
    		if(parents.equals("Y")){
    			//모기사일경우
    			sb.append(" AND ID.MD_SEQ = ID.MD_PSEQ		\n");
    		}
    		sb.append(" ORDER BY MD_SEQ ASC		\n");
    		
    		sb.append("   )ID 																 \n");
    		//sb.append("   GROUP BY ID.MD_PSEQ 												 \n");
    		sb.append("   ORDER BY ID.ID_SEQ DESC 											 \n");
    		if(pageNum>0){
    			sb.append(" LIMIT   "+startNum+","+endNum+"                                       \n");
    		}			
    		
    		sb.append("  )A                                                                    \n");
    		
    		
    		
    		
    		
    		
    		
    		System.out.println(sb.toString());		
    		rs = stmt.executeQuery(sb.toString());
    		
    		tmpId_seq ="";
    		while(rs.next()){
    			idBean = new IssueDataBean();
    			idBean.setId_seq(rs.getString("ID_SEQ"));
    			idBean.setId_title(rs.getString("ID_TITLE"));
    			idBean.setId_url(rs.getString("ID_URL"));
    			idBean.setId_writter(rs.getString("ID_WRITTER"));
    			idBean.setId_content(rs.getString("ID_CONTENT"));
    			idBean.setId_regdate(rs.getString("ID_REGDATE"));
    			idBean.setMd_seq(rs.getString("MD_SEQ"));
    			idBean.setI_seq(rs.getString("I_SEQ"));
    			idBean.setIt_seq(rs.getString("IT_SEQ"));
    			idBean.setMd_date(rs.getString("MD_DATE"));
    			idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
    			idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
    			idBean.setS_seq(rs.getString("S_SEQ"));
    			idBean.setSg_seq(rs.getString("SG_SEQ"));
    			idBean.setId_mailyn(rs.getString("ID_MAILYN"));
    			idBean.setId_useyn(rs.getString("ID_USEYN"));
    			idBean.setM_seq(rs.getString("M_SEQ"));
    			idBean.setM_name(rs.getString("M_NAME"));
//    			idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
    			idBean.setMd_type(rs.getString("MD_TYPE"));
    			idBean.setMd_pseq(rs.getString("MD_PSEQ"));
    			
    			issueDataList.add(idBean);	
    			if(tmpId_seq.equals("")) tmpId_seq += rs.getString("ID_SEQ");
    			else tmpId_seq += "," + rs.getString("ID_SEQ");
    		}
    		
    		
    		
    		String prevI_seq = null;			
    		if( tmpId_seq.length() > 0 ) 
    		{
    			//이슈에 이슈코드어레이 추가
    			ArrayList ArrIcList = null;
    			ArrIcList = new ArrayList();	    			    	
    			
    			rs.close();
    			sb = new StringBuffer();				
    			sb.append("SELECT IDC.ID_SEQ 								        	\n");
    			sb.append("		  ,IC.IC_SEQ							                \n");
    			sb.append("		  ,IDC.IC_TYPE							                \n");
    			sb.append(" 	  ,IDC.IC_CODE 								            \n");
    			sb.append(" 	  ,IC.IC_PTYPE							                \n");
    			sb.append(" 	  ,IC.IC_PCODE   						                \n");
    			sb.append(" 	  ,DATE_FORMAT(IC.IC_REGDATE,'%Y-%m-%d %H:%i:%s') IC_REGDATE		\n");				
    			sb.append(" 	  ,IC_DESCRIPTION								        \n");				
    			sb.append(" 	  ,IC.M_SEQ								                \n");
    			sb.append("       ,IC.IC_NAME 									        \n");
    			sb.append("FROM ISSUE_CODE_OLD IC, ISSUE_DATA_CODE_OLD IDC	 					\n");
    			sb.append("WHERE	IDC.ID_SEQ IN ("+tmpId_seq+")  						\n");
    			sb.append("         AND IC.IC_CODE = IDC.IC_CODE   						\n");
    			sb.append("         AND IC.IC_TYPE = IDC.IC_TYPE	 					\n");
    			sb.append("ORDER BY IDC.ID_SEQ DESC					 					\n");				
    			System.out.println(sb.toString());
    			rs = stmt.executeQuery(sb.toString());
    			
    			while(rs.next()){
    				
    				if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
    				{
    					addIssueCode(issueDataList,prevI_seq,ArrIcList);        			
    					ArrIcList = new ArrayList();
    				}        		
    				
    				prevI_seq = rs.getString("ID_SEQ");
    				
    				icBean = new IssueCodeBean();
    				icBean.setIc_seq(rs.getInt("IC_SEQ"));
    				icBean.setIc_type(rs.getInt("IC_TYPE"));
    				icBean.setIc_code(rs.getInt("IC_CODE"));
    				icBean.setIc_ptype(rs.getInt("IC_PTYPE"));
    				icBean.setIc_pcode(rs.getInt("IC_PCODE"));
    				icBean.setIc_regdate(rs.getString("IC_REGDATE"));
    				icBean.setIc_description(rs.getString("IC_DESCRIPTION"));
    				icBean.setM_seq(rs.getString("M_SEQ"));
    				icBean.setIc_name(rs.getString("IC_NAME"));
    				
    				ArrIcList.add(icBean);
    			}
    			addIssueCode(issueDataList,prevI_seq,ArrIcList);
    			
    			
    			//이슈에 이슈코멘트어레이 추가					    	
    			rs = null;
    			prevI_seq = null;	
    			sb = new StringBuffer();
    			ArrayList arrIcmList = new ArrayList();
    			
    			sb.append("SELECT IM_SEQ, IM_COMMENT, DATE_FORMAT(IM_DATE,'%Y-%m-%d %H:%i:%s') IM_DATE, ID_SEQ \n");
    			sb.append("FROM ISSUE_COMMENT	 						                           \n");
    			sb.append("WHERE ID_SEQ IN ("+tmpId_seq+")  					                       \n");
    			sb.append("ORDER BY IM_SEQ DESC                                 				   \n");				
    			System.out.println(sb.toString());
    			rs = stmt.executeQuery(sb.toString());
    			
    			while(rs.next()){
    				
    				if(prevI_seq!=null && !prevI_seq.equals(rs.getString("ID_SEQ")) )
    				{
    					addIssueCode(issueDataList,prevI_seq,arrIcmList);        			
    					arrIcmList = new ArrayList();
    				}        		
    				
    				prevI_seq = rs.getString("ID_SEQ");
    				
    				icmBean = new IssueCommentBean();
    				icmBean.setIm_seq(rs.getString("IM_SEQ"));
    				icmBean.setIm_comment(rs.getString("IM_COMMENT"));
    				icmBean.setIm_date(rs.getString("IM_DATE"));       			        		
    				
    				arrIcmList.add(icmBean);
    			}
    			addIssueComment(issueDataList,prevI_seq,arrIcmList);
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
    	
    	return issueDataList;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public ArrayList getSourceData(String md_pseq, String ic_seq, String sdate, String stime, String edate, String etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT A.ID_SEQ														\n");
			sb.append("     , A.MD_SEQ														\n");
			sb.append("     , A.I_SEQ														\n");
			sb.append("     , A.IT_SEQ														\n");
			sb.append("     , A.ID_URL														\n");
			sb.append("     , A.ID_TITLE													\n");
			sb.append("     , A.SG_SEQ														\n");
			sb.append("     , A.S_SEQ														\n");
			sb.append("     , A.MD_SITE_NAME												\n");
			sb.append("     , A.MD_SITE_MENU												\n");
			sb.append("     , DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE			\n");
			sb.append("     , DATE_FORMAT(A.ID_REGDATE,'%Y-%m-%d %H:%i:%s') AS ID_REGDATE	\n");
			sb.append("     , A.ID_WRITTER													\n");
			sb.append("     , A.ID_CONTENT													\n");
			sb.append("     , A.ID_MAILYN													\n");
			sb.append("     , A.ID_USEYN													\n");
			sb.append("     , A.M_SEQ														\n");
			sb.append("     , A.MD_TYPE														\n");
			sb.append("     , A.MD_PSEQ														\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 9) AS SENTI					\n");
			sb.append("  FROM ISSUE_DATA A													\n");
			sb.append("     , IC_S_RELATION B												\n");
			sb.append("     , ISSUE_CODE C													\n");
			sb.append(" WHERE A.MD_PSEQ = "+md_pseq+"										\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'		\n");
			sb.append("   AND A.SG_SEQ = B.S_SEQ											\n");
			sb.append("   AND B.IC_SEQ = C.IC_SEQ											\n");
			sb.append("   AND C.IC_CODE > 0													\n");
			sb.append("   AND C.IC_USEYN = 'Y' 												\n");
			sb.append("   AND C.IC_CODE = "+ic_seq+"										\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setTemp1(rs.getString("SENTI"));

				arrResult.add(idBean);
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
    
    
	/**
     * 해당이슈데이터의  코드 추가
     * @param IssueDataBean
     * @return
     */   
    private ArrayList addIssueCode(ArrayList IssueList, String id_seq, ArrayList issueCodeList) {
    	int i=0;
    	for(i=0; i<IssueList.size(); i++) {
    		IssueDataBean idBean = (IssueDataBean) IssueList.get(i);
    		if (idBean.getId_seq().equals(id_seq)) {
    			idBean.setArrCodeList(issueCodeList);
    			IssueList.set(i, idBean);
    		}
    	}
    	return IssueList;
    }
    
	/**
     * 해당이슈데이터의  Comment 추가
     * @param IssueDataBean
     * @return
     */
    private ArrayList addIssueComment(ArrayList IssueList, String id_seq, ArrayList issueCommentList) {
    	int i=0;
    	for(i=0; i<IssueList.size(); i++) {
    		IssueDataBean idBean = (IssueDataBean) IssueList.get(i);
    		if (idBean.getId_seq().equals(id_seq)) {
    			idBean.setArrCommentList(issueCommentList);
    			IssueList.set(i, idBean);
    		}
    	}
    	return IssueList;
    }
    
    //이슈 불러오기
    public ArrayList getActiveIssueTw(String sDate, String eDate, String gSn){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			DateUtil du = new DateUtil();
			
			sb = new StringBuffer();
			
			sb.append("SELECT I.I_SEQ                                                           \n");
			sb.append("     , I.I_TITLE	                                                        \n");			                          
			sb.append("  FROM ISSUE I                                                           \n");
			sb.append("     , ISSUE_DATA ID                                                     \n");
			sb.append("	    , ISSUE_TITLE IT	                                                \n");	
			sb.append(" WHERE I.I_USEYN = 'Y'		                                            \n");								                  
			sb.append("   AND IT.IT_USEYN = 'Y'			                                        \n");							                  
			sb.append("   AND ID.S_SEQ = '"+gSn+"'                                              \n");					                  
			sb.append("   AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'  \n");						
			sb.append("   AND ID.IT_SEQ = IT.IT_SEQ								                \n");
			sb.append("   AND IT.I_SEQ = I.I_SEQ									            \n");
			sb.append(" GROUP BY I.I_SEQ, I.I_TITLE												\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			issueList = new ArrayList();
			
			while(rs.next()){
				
				isBean =  new IssueBean();
				isBean.setI_seq(rs.getString("I_SEQ"));
				isBean.setI_title(rs.getString("I_TITLE"));
				isBean.setI_regdate("");
				isBean.setM_seq("");
				isBean.setI_count("");
	        	
	        	issueList.add(isBean);
			}

			if(issueList==null || issueList.size()==0){
				sb = new StringBuffer();
				
				sb.append("SELECT I.I_SEQ               \n");
				sb.append("     , I.I_TITLE             \n");
				sb.append("		, I.I_REGDATE           \n");
				sb.append("		, I.M_SEQ               \n");
				sb.append("		, IT.IT_SEQ             \n");
				sb.append("		, IT.IT_TITLE	        \n");	
				sb.append("  FROM ISSUE       I         \n");
				sb.append("	    , ISSUE_TITLE IT	    \n");                                  
				sb.append("	WHERE I.I_SEQ = IT.I_SEQ	\n");						                                  												                                  
				sb.append("	ORDER BY IT.IT_SEQ DESC     \n");
				sb.append("	LIMIT 0,1                   \n");
				
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				issueList = new ArrayList();
				while(rs.next()){
					
					isBean =  new IssueBean();
					isBean.setI_seq(rs.getString("I_SEQ"));
					isBean.setI_title(rs.getString("I_TITLE"));
					isBean.setI_regdate(rs.getString("I_REGDATE"));
					isBean.setM_seq(rs.getString("M_SEQ"));
					isBean.setI_count("");
		        	
		        	issueList.add(isBean);
				}
				
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
    
    //주제 불러오기
	public ArrayList getActiveTitleTw(String sDate, String eDate, String gSn){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			DateUtil du = new DateUtil();
			
			sb = new StringBuffer();

			sb.append("SELECT IT.I_SEQ                                                          \n");
			sb.append("	    , IT.IT_TITLE                                                       \n");
			sb.append("		, IT.IT_SEQ	                                                        \n");				         
			sb.append("  FROM ISSUE       I                                                     \n");
			sb.append("	    , ISSUE_DATA  ID                                                    \n");
			sb.append("	    , ISSUE_TITLE IT	                                                \n");	
			sb.append(" WHERE I.I_USEYN = 'Y'			                                        \n");					                   		
			sb.append("	  AND IT.IT_USEYN = 'Y'			                                        \n");					                  		
			sb.append("   AND ID.S_SEQ = '"+gSn+"'	                                    	    \n");			                  		
			sb.append("   AND ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'  \n");							
			sb.append("   AND ID.IT_SEQ = IT.IT_SEQ							                    \n");
			sb.append("   AND IT.I_SEQ = I.I_SEQ								                \n");
			sb.append(" GROUP BY IT.I_SEQ, IT.IT_TITLE, IT.IT_SEQ                               \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			issueList = new ArrayList();
			IssueTitleBean itBean = null;
			while(rs.next()){
				itBean =  new IssueTitleBean();
				itBean.setIt_seq(rs.getString("IT_SEQ"));
	        	itBean.setIt_title(rs.getString("IT_TITLE"));
	        	itBean.setI_seq(rs.getString("I_SEQ"));
	        	
	        	issueList.add(itBean);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
	
	public void reportNameUpdate(String ir_seq, String ir_title){
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		sb = new StringBuffer();
    		
    		sb.append("UPDATE ISSUE_REPORT SET IR_TITLE = '"+ir_title+"' WHERE IR_SEQ = "+ir_seq+"\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		pstmt.executeUpdate();
    	}catch(SQLException e){
    		Log.writeExpt(e, sb.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);
    	}finally{
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    	}
    }
	
	public ArrayList getIssueTitle(ArrayList pArray, String iSeq){
		ArrayList result = new ArrayList();
		IssueTitleBean itb = null;
		for (int i = 0; i < pArray.size(); i++) {
			itb = (IssueTitleBean)pArray.get(i);
			if(itb.getI_seq().equals(iSeq)){
				result.add(itb);
			}
			
		}
		return result;
	}
	
	//사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order(){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT distinct(A.IC_CODE)	\n");
			sb.append("     , A.IC_NAME				\n");
			sb.append("  FROM ISSUE_CODE A 			\n");
			sb.append("     , IC_S_RELATION B		\n");
			sb.append(" WHERE A.IC_SEQ = B.IC_SEQ	\n");
			sb.append("   AND A.IC_TYPE = 6 		\n");
			sb.append("   AND A.IC_CODE > 0 		\n");
			sb.append("   AND A.IC_USEYN = 'Y'		\n");
			sb.append(" ORDER BY 1 ASC				\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			issueList = new ArrayList();
			String[] siteGroup = null;
			while(rs.next()){
				
				siteGroup = new String[2];
				
				siteGroup[0] = rs.getString("IC_CODE");
				siteGroup[1] = rs.getString("IC_NAME");
	        	
	        	issueList.add(siteGroup);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
	
	//사이트 그룹 정렬해서 가져오기
	public ArrayList getSiteGroup_order2(){
		ArrayList issueList = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT A.IC_CODE				\n");
			sb.append("     , A.IC_NAME				\n");
			sb.append("     , B.IC_ORDER			\n");
			sb.append("  FROM ISSUE_CODE A 			\n");
			sb.append("     , IC_S_RELATION B		\n");
			sb.append(" WHERE A.IC_SEQ = B.IC_SEQ	\n");
			sb.append("   AND A.IC_TYPE = 6 		\n");
			sb.append("   AND A.IC_CODE > 0 		\n");
			sb.append("   AND A.IC_USEYN = 'Y'		\n");
			//sb.append("   AND IC_CODE <> 8			\n");
			sb.append(" ORDER BY B.IC_ORDER			\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			issueList = new ArrayList();
			String[] siteGroup = null;
			while(rs.next()){
				
				siteGroup = new String[3];
				
				siteGroup[0] = rs.getString("IC_CODE");
				siteGroup[1] = rs.getString("IC_NAME");
				siteGroup[2] = rs.getString("IC_ORDER");
	        	
	        	issueList.add(siteGroup);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return issueList;
	}
	
	public int GetSearchSourceOrder(ArrayList data, String icCode ){
		int result = 0;
		String[] row_data = null;
		for(int i = 0; i < data.size(); i++){
			row_data = (String[])data.get(i);
			if(row_data[0].equals(icCode)){
				result = Integer.parseInt(row_data[2]);
				break;
			}
		}
		return result;
	}
    
	
	public ArrayList TrendPieChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
    	try{
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		sb = new StringBuffer();
    		
    		
    		sb.append("SELECT IC.IC_TYPE, IC.IC_CODE, IC.IC_NAME AS CATEGORY, IFNULL(T.CNT, 0) AS CNT\n");
    		sb.append("FROM\n");
    		sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
    		//sb.append("LEFT OUTER JOIN\n");
    		sb.append("INNER JOIN\n");
    		sb.append("(\n");
    		sb.append("		SELECT IDC.IC_CODE, COUNT(*) AS CNT\n");
    		sb.append("		FROM ISSUE_DATA ID\n");
    		sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
    		sb.append("		ON IDC.IC_TYPE = "+ic_type+"\n");
    		sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
    		sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
    		sb.append("		ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
    		sb.append("		AND ID.ID_SEQ = IDC2.ID_SEQ\n");
    		sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'\n");
    		sb.append("		GROUP BY IDC.IC_CODE\n");
    		sb.append(") T\n");
    		sb.append("ON IC.IC_CODE = T.IC_CODE\n");
    		sb.append("ORDER BY IC.IC_TYPE, IC.IC_CODE\n");
    		
    		System.out.println(sb.toString());
    		pstmt = dbconn.createPStatement(sb.toString());
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			dataMap = new HashMap();
    			dataMap.put("CATEGORY", rs.getString("CATEGORY"));
    			dataMap.put("CNT", rs.getString("CNT"));
    			result.add(dataMap);
    		}
    	}catch(SQLException e){
    		Log.writeExpt(e, sb.toString());
    	}catch(Exception e){
    		Log.writeExpt(e);
    	}finally{
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    	}
    	return result;
    }
	
	public ArrayList BarChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT B.IC_TYPE, B.IC_CODE, B.IC_NAME AS CATEGORY, IFNULL(A.CNT,0) AS CNT  FROM (\n");
			
			
			sb.append("SELECT IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME AS CATEGORY, COUNT(*) AS CNT\n");
			sb.append("FROM ISSUE_DATA ID\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("ON IDC.IC_TYPE = "+ic_type+"\n");
			sb.append("AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_CODE IC\n");
			sb.append("ON IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'\n");
			sb.append("GROUP BY IDC.IC_TYPE, IDC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IDC.IC_TYPE, IDC.IC_CODE\n");
			
			
			sb.append(")A RIGHT OUTER JOIN (SELECT A.IC_TYPE, A.IC_CODE\n");
			sb.append(", A.IC_NAME\n");
			sb.append(", B.IC_ORDER\n");
			sb.append("FROM ISSUE_CODE A \n");
			sb.append(", IC_S_RELATION B\n");
			sb.append("WHERE A.IC_SEQ = B.IC_SEQ\n");
			sb.append("AND A.IC_TYPE = 6 \n");
			sb.append("AND A.IC_CODE > 0 \n");
			sb.append("AND A.IC_USEYN = 'Y'\n");
			sb.append(") B ON A.IC_CODE = B.IC_CODE ORDER BY B.IC_ORDER ASC\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			int sumPotal = 0;
			
			while(rs.next()){
				
				if(rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("8")){
					sumPotal += rs.getInt("CNT"); 
				}
			}
			
			rs.beforeFirst();
			
			while(rs.next()){
				
				dataMap = new HashMap();
				
				
				if(!rs.getString("IC_CODE").equals("8")){
					dataMap.put("CATEGORY", rs.getString("CATEGORY"));
					if(rs.getString("IC_CODE").equals("6")){
				
						dataMap.put("CNT", Integer.toString(sumPotal));
					}else{
						dataMap.put("CNT", rs.getString("CNT"));
					}
					result.add(dataMap);
				}
				
				
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList channelTrendChartData(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			//sb.append("LEFT OUTER JOIN\n");
			sb.append("INNER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC1.IC_CODE\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON IDC.IC_TYPE = 9	\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON IDC1.IC_TYPE = "+ic_type+"\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			//sb.append("		WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				
					dataMap = new HashMap();
					dataMap.put("CATEGORY", rs.getString("CATEGORY"));
					dataMap.put("PCNT", rs.getString("PCNT"));
					dataMap.put("NAME1", rs.getString("NAME1"));
					dataMap.put("NCNT", rs.getString("NCNT"));
					dataMap.put("NAME2", rs.getString("NAME2"));
					dataMap.put("ECNT", rs.getString("ECNT"));
					dataMap.put("NAME3", rs.getString("NAME3"));
					result.add(dataMap);
				
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList channelTrendChartData_TYPE15(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			//sb.append("LEFT OUTER JOIN\n");
			sb.append("INNER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC1.IC_CODE\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON IDC.IC_TYPE = 9	\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON IDC1.IC_TYPE = "+ic_type+"\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			//sb.append("		WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			StatisticsSuperBean superBean = new StatisticsSuperBean();
			StatisticsSuperBean.dailyChart[] childBean = new StatisticsSuperBean.dailyChart[9]; 
			
				childBean[0] = superBean.new dailyChart(); 
				childBean[0].setCategory("경영진");
				childBean[1] = superBean.new dailyChart(); 
				childBean[1].setCategory("광고/홍보");
				childBean[2] = superBean.new dailyChart(); 
				childBean[2].setCategory("인사/노무");
				childBean[3] = superBean.new dailyChart(); 
				childBean[3].setCategory("경제/일반");
				childBean[4] = superBean.new dailyChart(); 
				childBean[4].setCategory("해외/투자");
				childBean[5] = superBean.new dailyChart(); 
				childBean[5].setCategory("사회공헌");
				childBean[6] = superBean.new dailyChart(); 
				childBean[6].setCategory("제품/기술");
				childBean[7] = superBean.new dailyChart(); 
				childBean[7].setCategory("환경/재해");
				childBean[8] = superBean.new dailyChart(); 
				childBean[8].setCategory("행사/문화");
			
			while(rs.next()){
				if(rs.getString("IC_CODE").equals("2") || rs.getString("IC_CODE").equals("3")){
					childBean[0].setAddPcnt(rs.getInt("PCNT"));
					childBean[0].setAddNcnt(rs.getInt("NCNT"));
					childBean[0].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("10")){
					childBean[1].setAddPcnt(rs.getInt("PCNT"));
					childBean[1].setAddNcnt(rs.getInt("NCNT"));
					childBean[1].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("7")){
					childBean[2].setAddPcnt(rs.getInt("PCNT"));
					childBean[2].setAddNcnt(rs.getInt("NCNT"));
					childBean[2].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("1")){
					childBean[3].setAddPcnt(rs.getInt("PCNT"));
					childBean[3].setAddNcnt(rs.getInt("NCNT"));
					childBean[3].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("4")){
					childBean[4].setAddPcnt(rs.getInt("PCNT"));
					childBean[4].setAddNcnt(rs.getInt("NCNT"));
					childBean[4].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("11")){
					childBean[5].setAddPcnt(rs.getInt("PCNT"));
					childBean[5].setAddNcnt(rs.getInt("NCNT"));
					childBean[5].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("5")){
					childBean[6].setAddPcnt(rs.getInt("PCNT"));
					childBean[6].setAddNcnt(rs.getInt("NCNT"));
					childBean[6].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("8") || rs.getString("IC_CODE").equals("9")){
					childBean[7].setAddPcnt(rs.getInt("PCNT"));
					childBean[7].setAddNcnt(rs.getInt("NCNT"));
					childBean[7].setAddEcnt(rs.getInt("ECNT"));
				}
				
				if(rs.getString("IC_CODE").equals("12")){
					childBean[8].setAddPcnt(rs.getInt("PCNT"));
					childBean[8].setAddNcnt(rs.getInt("NCNT"));
					childBean[8].setAddEcnt(rs.getInt("ECNT"));
				}
			
			}
			
			
			for(int i =0; i < childBean.length; i++){
				if(childBean[i].getPcnt() > 0 || childBean[i].getNcnt() > 0 || childBean[i].getEcnt() > 0){
					dataMap = new HashMap();
					dataMap.put("CATEGORY", childBean[i].getCategory());
					dataMap.put("PCNT", Integer.toString(childBean[i].getPcnt()));
					dataMap.put("NAME1", childBean[i].getName1());
					dataMap.put("NCNT", Integer.toString(childBean[i].getNcnt()));
					dataMap.put("NAME2", childBean[i].getName2());
					dataMap.put("ECNT", Integer.toString(childBean[i].getEcnt()));
					dataMap.put("NAME3", childBean[i].getName3());
					result.add(dataMap);
					
				}
			}
			
			/*
			while(rs.next()){
				
					dataMap = new HashMap();
					dataMap.put("CATEGORY", rs.getString("CATEGORY"));
					dataMap.put("PCNT", rs.getString("PCNT"));
					dataMap.put("NAME1", rs.getString("NAME1"));
					dataMap.put("NCNT", rs.getString("NCNT"));
					dataMap.put("NAME2", rs.getString("NAME2"));
					dataMap.put("ECNT", rs.getString("ECNT"));
					dataMap.put("NAME3", rs.getString("NAME3"));
					result.add(dataMap);
				
			}
			*/
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	
	public ArrayList channelTrendChartData_TEMP(String ic_type, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			System.out.println("메롱메롱메롱");
			
			sb.append("SELECT A.* FROM (\n");
			
			
			
			sb.append("SELECT IC.IC_CODE, IC.IC_NAME AS CATEGORY\n");
			sb.append(", IFNULL(SUM(T.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2\n");
			sb.append(", IFNULL(SUM(T.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3\n");
			sb.append("FROM\n");
			sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = "+ic_type+" AND IC_USEYN = 'Y' AND IC_CODE != 0) IC\n");
			sb.append("LEFT OUTER JOIN\n");
			//sb.append("INNER JOIN\n");
			sb.append("(\n");
			sb.append("		SELECT IDC1.IC_CODE\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON IDC.IC_TYPE = 9\n");
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON IDC1.IC_TYPE = "+ic_type+"\n");
			sb.append("		AND IDC1.ID_SEQ = ID.ID_SEQ\n");
			
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("ON IDC2.IC_TYPE = 4 AND IDC2.IC_CODE IN (1,2)\n");
			sb.append("AND IDC2.ID_SEQ = ID.ID_SEQ\n");
			
			//sb.append("		WHERE DATE_FORMAT(ID.MD_DATE, '%Y%m%d') = '"+date+"'\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n");
			sb.append("		GROUP BY IDC1.IC_CODE, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("ON IC.IC_CODE = T.IC_CODE\n");
			sb.append("GROUP BY IC.IC_CODE, IC.IC_NAME\n");
			sb.append("ORDER BY IC.IC_CODE\n");
			
			
			sb.append(")A,(SELECT A.IC_CODE\n");
			sb.append(", A.IC_NAME\n");
			sb.append(", B.IC_ORDER\n");
			sb.append("FROM ISSUE_CODE A \n");
			sb.append(", IC_S_RELATION B\n");
			sb.append("WHERE A.IC_SEQ = B.IC_SEQ\n");
			sb.append("AND A.IC_TYPE = 6 \n");
			sb.append("AND A.IC_CODE > 0 \n");
			sb.append("AND A.IC_USEYN = 'Y'\n");
			sb.append(") B WHERE A.IC_CODE = B.IC_CODE ORDER BY B.IC_ORDER ASC\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			int sumPotal_pcnt = 0;
			int sumPotal_ncnt = 0;
			int sumPotal_ecnt = 0;
			
			while(rs.next()){
				if(rs.getString("IC_CODE").equals("6") || rs.getString("IC_CODE").equals("8")){
					sumPotal_pcnt += rs.getInt("PCNT"); 
					sumPotal_ncnt += rs.getInt("NCNT"); 
					sumPotal_ecnt += rs.getInt("ECNT"); 
				}
			}
			
			rs.beforeFirst();
			
			while(rs.next()){
				
				if(!rs.getString("IC_CODE").equals("8")){
					
					if(rs.getString("IC_CODE").equals("6")){
						dataMap = new HashMap();
						dataMap.put("CATEGORY", rs.getString("CATEGORY"));
						dataMap.put("PCNT", Integer.toString(sumPotal_pcnt));
						dataMap.put("NAME1", rs.getString("NAME1"));
						dataMap.put("NCNT", Integer.toString(sumPotal_ncnt));
						dataMap.put("NAME2", rs.getString("NAME2"));
						dataMap.put("ECNT", Integer.toString(sumPotal_ecnt));
						dataMap.put("NAME3", rs.getString("NAME3"));
						result.add(dataMap);
					}else{
						dataMap = new HashMap();
						dataMap.put("CATEGORY", rs.getString("CATEGORY"));
						dataMap.put("PCNT", rs.getString("PCNT"));
						dataMap.put("NAME1", rs.getString("NAME1"));
						dataMap.put("NCNT", rs.getString("NCNT"));
						dataMap.put("NAME2", rs.getString("NAME2"));
						dataMap.put("ECNT", rs.getString("ECNT"));
						dataMap.put("NAME3", rs.getString("NAME3"));
						result.add(dataMap);
					}
					
					
				}
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList channelTrendChartData_Keyword(String k_xp, String ir_sdate, String ir_stime, String ir_edate, String ir_etime){
		ArrayList result = new ArrayList();
		HashMap dataMap = new HashMap();
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			
			
			sb.append("SELECT A.K_YP, A.K_VALUE AS CATEGORY																				\n");
			sb.append("     , IFNULL(SUM(B.P_CNT), 0) AS PCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =1) AS NAME1	\n");
			sb.append("     , IFNULL(SUM(B.N_CNT), 0) AS NCNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =2) AS NAME2	\n");
			sb.append("     , IFNULL(SUM(B.E_CNT), 0) AS ECNT, (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=9 AND IC_CODE =3) AS NAME3	\n");
			sb.append("  FROM 																											\n");
			sb.append("       (SELECT K_YP, K_VALUE FROM KEYWORD WHERE K_XP = "+k_xp+" AND K_YP > 0 AND K_ZP = 0)A						\n");
			sb.append("        LEFT OUTER JOIN																							\n");
			sb.append("       (SELECT A.K_YP																							\n");
			sb.append("             , IFNULL(CASE B.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT											\n");
			sb.append("             , IFNULL(CASE B.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT											\n");
			sb.append("             , IFNULL(CASE B.IC_CODE WHEN 3 THEN SUM(1) END, 0) AS E_CNT											\n");
			sb.append("          FROM ISSUE_DATA_TEMP A																					\n");
			sb.append("             , ISSUE_DATA_CODE B																					\n");
			sb.append("         WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append("           AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'						\n");
			sb.append("           AND B.IC_TYPE = 9																						\n");
			sb.append("           AND A.K_XP = "+k_xp+"																					\n");
			sb.append("         GROUP BY A.K_YP, B.IC_CODE)B ON A.K_YP = B.K_YP															\n");
			sb.append("         GROUP BY A.K_YP, A.K_VALUE  																			\n");
			sb.append("         ORDER BY A.K_YP																							\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				dataMap = new HashMap();
				dataMap.put("CATEGORY", rs.getString("CATEGORY"));
				dataMap.put("PCNT", rs.getString("PCNT"));
				dataMap.put("NAME1", rs.getString("NAME1"));
				dataMap.put("NCNT", rs.getString("NCNT"));
				dataMap.put("NAME2", rs.getString("NAME2"));
				dataMap.put("ECNT", rs.getString("ECNT"));
				dataMap.put("NAME3", rs.getString("NAME3"));
				result.add(dataMap);
			}
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList DailyReport1 (String id_seqs) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT MD_PSEQ FROM ISSUE_DATA WHERE ID_SEQ IN ("+id_seqs+")\n");
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			String md_pseq = "";
			if(rs.next()){
				
				if(md_pseq.equals("")){
					md_pseq = rs.getString("MD_PSEQ");
				}else{
					md_pseq += "," + rs.getString("MD_PSEQ");
				}
				
			}
			
			
			sb = new StringBuffer();			
			sb.append("SELECT A.SG_SEQ																								\n");
			sb.append("     , A.MD_SITE_NAME																						\n");
			sb.append("     , A.ID_TITLE																							\n");
			sb.append("     , A.MEDIA_INFO																							\n");
			sb.append("     , A.IC_NAME																								\n");
			sb.append("     , A.MD_PSEQ																								\n");
			sb.append("     , A.ID_URL																								\n");
			sb.append("     , A.MD_DATE																								\n");
			sb.append("     , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ)  AS SAME_CT		\n");
			sb.append("  FROM ( 																									\n");
			sb.append("        SELECT A.*																							\n");
			sb.append("             , IF(A.SG_SEQ = @SG_SEQ, @ROW:=@ROW+1, @ROW:=1) AS RANK											\n");
			sb.append("             , @SG_SEQ := A.SG_SEQ																			\n");
			sb.append("          FROM (   																							\n");
			sb.append("                SELECT A.SG_SEQ																				\n");
			sb.append("                     , A.MD_SITE_NAME																		\n");
			sb.append("                     , A.ID_TITLE																			\n");
			sb.append("                     , A.MEDIA_INFO																			\n");
			sb.append("                     , C.IC_NAME																				\n");
			sb.append("                     , A.MD_PSEQ																				\n");
			sb.append("                     , A.ID_URL																				\n");
			sb.append("                     , A.MD_DATE																				\n");
			sb.append("                  FROM ISSUE_DATA A																			\n");
			sb.append("                     , ISSUE_DATA_CODE B																		\n");
			sb.append("                     , ISSUE_CODE C																			\n");
			sb.append("                 WHERE A.MD_PSEQ IN ("+md_pseq+")															\n");  
			sb.append("                   AND A.SG_SEQ IN (17,18,29)																\n");
			sb.append("                   AND A.ID_SEQ = B.ID_SEQ																	\n");
			sb.append("                   AND B.IC_TYPE = 12																		\n");
			sb.append("                   AND B.IC_TYPE = C.IC_TYPE																	\n");
			sb.append("                   AND B.IC_CODE = C.IC_CODE																	\n");
			sb.append("                   AND C.IC_USEYN = 'Y'																		\n");
			sb.append("              GROUP BY A.MD_PSEQ, A.SG_SEQ																	\n");
			sb.append("              ORDER BY SG_SEQ, MD_PSEQ																		\n");
			sb.append("             )A																								\n");
			sb.append("           , (SELECT @ROW:=0, @SG_SEQ:='') R																	\n");  
			sb.append("       )A																									\n");
			sb.append(" WHERE RANK <= 3																								\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setTemp1(rs.getString("IC_NAME"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				idBean.setMd_same_ct(rs.getString("SAME_CT"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				
				arrResult.add(idBean);
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
	
	public ArrayList DailyReport2 (String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();			
			 
			sb.append("SELECT A.*																										\n");
			sb.append("     , B.SG_NAME																									\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 12) AS TYPE12															\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 13) AS TYPE13															\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 9) AS TYPE9																\n");
			sb.append("  FROM (																											\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE	,A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 17 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ										\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("        UNION 																									\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE, A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C 												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 18 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ									\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("        UNION 																									\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE, A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C 												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 29 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ										\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("        UNION 																									\n");
			sb.append("        (SELECT SG_SEQ, A.ID_SEQ, A.MD_SITE_NAME, A.ID_TITLE, A.ID_URL, MEDIA_INFO, A.MD_DATE					\n");  
			sb.append("              , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ) AS SAME_CT	\n");
			sb.append("           FROM ISSUE_DATA A, ISSUE_DATA_CODE B, ISSUE_DATA_CODE C 												\n");
			sb.append("          WHERE A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'					\n"); 
			sb.append("            AND A.SG_SEQ = 19 AND A.ID_SEQ = B.ID_SEQ AND B.IC_TYPE = 12 AND A.ID_SEQ = C.ID_SEQ AND C.IC_TYPE = 13 AND C.IC_CODE <> 1 AND A.MD_SEQ = A.MD_PSEQ										\n");
			sb.append("          ORDER BY B.IC_CODE ASC, SAME_CT DESC LIMIT 1)															\n");
			sb.append("       )A																										\n");
			sb.append("     , SITE_GROUP B																								\n");
			sb.append(" WHERE A.SG_SEQ = B.SG_SEQ																						\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setMd_same_ct(rs.getString("SAME_CT"));
				idBean.setTemp1(rs.getString("SG_NAME"));
				idBean.setTemp2(rs.getString("TYPE12"));
				idBean.setTemp3(rs.getString("TYPE13"));
				idBean.setTemp4(rs.getString("TYPE9"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				
				arrResult.add(idBean);
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
    
public ArrayList DailyReport3 (String id_seq, String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();		
			
			sb.append("SELECT A.T_SEQ          AS MD_SEQ													\n");
			sb.append("     , A.T_SITE         AS MD_SITE_NAME	 											\n");
			sb.append("     , A.T_TITLE        AS ID_TITLE 													\n");
			sb.append("     , A.T_BOARD        AS MD_SITE_MENU						 						\n");
			sb.append("     , A.T_DATETIME     AS MD_DATE													\n");
			sb.append("     , A.T_PRESENTTIME  AS TEMP1														\n");
			sb.append("     , A.T_URL          AS ID_URL													\n");
			sb.append("  FROM TOP A																			\n");
			sb.append("     , ISSUE_DATA B																	\n");
			sb.append(" WHERE A.T_SEQ = B.MD_SEQ															\n");
			sb.append("   AND B.SG_SEQ = 30																	\n");
			sb.append("   AND B.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'	\n"); 
			if(!id_seq.equals("")){
			sb.append("   AND ID_SEQ IN ("+id_seq+")															\n");
			}
			
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();

				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setTemp1(rs.getString("TEMP1"));
				idBean.setId_url(rs.getString("ID_URL"));
				arrResult.add(idBean);
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

	public ArrayList DailyReport4 (String id_seqs , String ir_sdate, String ir_stime, String ir_edate, String ir_etime) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();			
			
			sb.append("SELECT A.ID_SEQ 																							\n");
			sb.append("     , A.ID_TITLE																						\n");
			sb.append("     , A.ID_URL																							\n");
			sb.append("     , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, 14) AS TYPE14													\n");
			sb.append("     , A.MD_SITE_NAME																					\n");
			sb.append("     , A.MD_DATE																							\n");
			sb.append("     , (SELECT COUNT(*)-1 FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND SG_SEQ = A.SG_SEQ)  AS SAME_CT	\n");
			sb.append("  FROM ISSUE_DATA A																						\n");
			sb.append("     , ISSUE_DATA_CODE B																					\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ																				\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+ir_sdate+" "+ir_stime+"' AND '"+ir_edate+" "+ir_etime+"'						\n");
			sb.append("   AND B.IC_TYPE = 6																						\n");
			sb.append("   AND B.IC_CODE IN (1,6)																				\n");
			if(!id_seqs.equals("")){
				sb.append("   AND A.ID_SEQ IN ("+id_seqs+") 																	\n");
			}
			sb.append("  ORDER BY SAME_CT DESC																					\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setTemp1(rs.getString("TYPE14"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_same_ct(rs.getString("SAME_CT"));
				arrResult.add(idBean);
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

	public ArrayList getChartData(String sdate, String stime, String edate, String etime, ArrayList codeList, boolean addType){
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		
		try{
			
			long diff = du.DateDiff(sdate.replaceAll("-", ""), edate.replaceAll("-", ""));
			
			
			// 차이값구할때 첫날은  빼버림 (사유 : 데이터 안나와서.SOL)
			String tempDate = du.addDay(edate.replaceAll("-", ""), (int)diff+1, "yyyyMMdd");
			
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			
			sb.append("SELECT MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				sb.append(", SUM(T.CNT"+i+") AS CNT"+i+"\n");
				
				
			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				if(icb.getIc_code() == 6){
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) + IFNULL(CASE IDC1.IC_CODE WHEN 8 THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}else{
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}
				
			
			}
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 6\n");
			
			
			if(addType){
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("		ON ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("		AND IDC2.IC_TYPE = 9 AND IDC2.IC_CODE = 1\n");
			}
			
			sb.append("INNER JOIN ISSUE_DATA_CODE IDC3					\n");
			sb.append("   ON ID.ID_SEQ = IDC3.ID_SEQ					\n");
			sb.append("  AND IDC3.IC_TYPE = 4 AND IDC3.IC_CODE IN (1,2)	\n"); 
			
			sb.append("		WHERE DATE_FORMAT(MD_DATE, '%Y%m%d%H') BETWEEN '"+sdate.replaceAll("-", "")+stime.substring(0,2)+"' AND '"+edate.replaceAll("-", "")+etime.substring(0,2)+"'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size()-1];
				if(codeList.size() > 0){
					for(int i = 1; i < codeList.size(); i++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
						cnt[i-1] = rs.getInt("CNT"+i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}
			
			boolean chk = false;
			int chkCnt = 0;
			while(true){
				if(chkCnt != 0){
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for(int i = 0; i < TempResult.size(); i++){
					IssueDataBean idb = (IssueDataBean)TempResult.get(i);
					if(idb.getMd_date().equals(tempDate)){
						chk = true;
						result.add(idb);
					}
				}
				if(!chk){
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size()-1];
	    			if(codeList.size() > 0){
	    				for(int i = 1; i < codeList.size(); i++){
	    					IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
	    					cnt[i-1] = 0;
	    				}
	    			}
	    			idb.setCnt(cnt);
					result.add(idb);
				}else{
					chk = false;
				}
				chkCnt++;
				if(tempDate.equals(edate.replaceAll("-", ""))){
					break;
				}
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	
	public ArrayList getChartData_type9(String sdate, String stime, String edate, String etime, ArrayList codeList, boolean addType){
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		
		try{
			
			long diff = du.DateDiff(sdate.replaceAll("-", ""), edate.replaceAll("-", ""));
			
			
			// 차이값구할때 첫날은  빼버림 (사유 : 데이터 안나와서.SOL)
			String tempDate = du.addDay(edate.replaceAll("-", ""), (int)diff+1, "yyyyMMdd");
			
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			
			sb.append("SELECT MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				sb.append(", SUM(T.CNT"+i+") AS CNT"+i+"\n");
				
				
			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				
				if(icb.getIc_code() == 6){
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) + IFNULL(CASE IDC1.IC_CODE WHEN 8 THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}else{
					sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) AS CNT"+i+"\n");
				}
				
			
			}
			sb.append("		FROM ISSUE_DATA ID\n");
			
			
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 9\n");
			
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC3\n");
			sb.append("		ON ID.ID_SEQ = IDC3.ID_SEQ\n");
			sb.append("		AND IDC3.IC_TYPE = 4 AND IDC3.IC_CODE IN (1,2)\n");
			
			
			sb.append("		WHERE DATE_FORMAT(MD_DATE, '%Y%m%d%H') BETWEEN '"+sdate.replaceAll("-", "")+stime.substring(0,2)+"' AND '"+edate.replaceAll("-", "")+etime.substring(0,2)+"'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size()-1];
				if(codeList.size() > 0){
					for(int i = 1; i < codeList.size(); i++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
						cnt[i-1] = rs.getInt("CNT"+i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}
			
			boolean chk = false;
			int chkCnt = 0;
			while(true){
				if(chkCnt != 0){
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for(int i = 0; i < TempResult.size(); i++){
					IssueDataBean idb = (IssueDataBean)TempResult.get(i);
					if(idb.getMd_date().equals(tempDate)){
						chk = true;
						result.add(idb);
					}
				}
				if(!chk){
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size()-1];
	    			if(codeList.size() > 0){
	    				for(int i = 1; i < codeList.size(); i++){
	    					IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
	    					cnt[i-1] = 0;
	    				}
	    			}
	    			idb.setCnt(cnt);
					result.add(idb);
				}else{
					chk = false;
				}
				chkCnt++;
				if(tempDate.equals(edate.replaceAll("-", ""))){
					break;
				}
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getChartData_type(String sdate, String stime, String edate, String etime, ArrayList codeList, boolean addType){
		ArrayList result = new ArrayList();
		ArrayList TempResult = new ArrayList();
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String tempDate = du.addDay(edate.replaceAll("-", ""), -6, "yyyyMMdd");
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			
			sb.append("SELECT MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
				sb.append(", SUM(T.CNT"+i+") AS CNT"+i+"\n");
			}
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d') AS MD_DATE\n");
			for(int i = 1; i < codeList.size(); i++){
				IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
			sb.append("		, IFNULL(CASE IDC1.IC_CODE WHEN "+icb.getIc_code()+" THEN SUM(1) END, 0) AS CNT"+i+"\n");
			}
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC1\n");
			sb.append("		ON ID.ID_SEQ = IDC1.ID_SEQ\n");
			sb.append("		AND IDC1.IC_TYPE = 6\n");
			
			if(addType){
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC2\n");
			sb.append("		ON ID.ID_SEQ = IDC2.ID_SEQ\n");
			sb.append("		AND IDC2.IC_TYPE = 9 AND IDC2.IC_CODE = 1\n");
			}
			
			sb.append("		WHERE DATE_FORMAT(MD_DATE, '%Y%m%d%H') BETWEEN '"+tempDate.replaceAll("-", "")+stime.substring(0,2)+"' AND '"+edate.replaceAll("-", "")+etime.substring(0,2)+"'\n");
			sb.append("		GROUP BY DATE_FORMAT(DATE_ADD(MD_DATE, INTERVAL+8 HOUR), '%Y%m%d'), IDC1.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				int[] cnt = new int[codeList.size()-1];
				if(codeList.size() > 0){
					for(int i = 1; i < codeList.size(); i++){
						IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
						cnt[i-1] = rs.getInt("CNT"+i);
					}
				}
				idb.setCnt(cnt);
				TempResult.add(idb);
			}
			
			boolean chk = false;
			int chkCnt = 0;
			while(true){
				if(chkCnt != 0){
					tempDate = du.addDay(tempDate, 1, "yyyyMMdd");
				}
				for(int i = 0; i < TempResult.size(); i++){
					IssueDataBean idb = (IssueDataBean)TempResult.get(i);
					if(idb.getMd_date().equals(tempDate)){
						chk = true;
						result.add(idb);
					}
				}
				if(!chk){
					IssueDataBean idb = new IssueDataBean();
					idb.setMd_date(tempDate);
					int[] cnt = new int[codeList.size()-1];
	    			if(codeList.size() > 0){
	    				for(int i = 1; i < codeList.size(); i++){
	    					IssueCodeBean icb = (IssueCodeBean)codeList.get(i);
	    					cnt[i-1] = 0;
	    				}
	    			}
	    			idb.setCnt(cnt);
					result.add(idb);
				}else{
					chk = false;
				}
				chkCnt++;
				if(tempDate.equals(edate.replaceAll("-", ""))){
					break;
				}
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getLastDataCode() {
		
		ArrayList arrResult = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();			
			
			sb.append("SELECT IC_TYPE, IC_CODE FROM ISSUE_DATA_CODE WHERE ID_SEQ = (SELECT ID_SEQ FROM ISSUE_DATA_CODE ORDER BY 1 DESC LIMIT 1)\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				icBean = new IssueCodeBean();
				
				icBean.setIc_type(rs.getInt("IC_TYPE"));
				icBean.setIc_code(rs.getInt("IC_CODE"));

				arrResult.add(icBean);
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
	
	public ArrayList getTagGroup(){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT ITG_SEQ, ITG_NAME FROM ISSUE_TAG_GROUP ORDER BY ITG_SEQ\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setItg_seq(rs.getString("ITG_SEQ"));
				idb.setItg_name(rs.getString("ITG_NAME"));
				result.add(idb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);			
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getTagCode(String itg_seq){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT ITC_SEQ, ITC_NAME FROM ISSUE_TAG_CODE ORDER BY ITC_SEQ\n");
//			sb.append("SELECT ITC_SEQ, ITC_NAME FROM ISSUE_TAG_CODE WHERE ITG_SEQ = "+itg_seq+" ORDER BY ITC_SEQ\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setItc_seq(rs.getString("ITC_SEQ"));
				idb.setItc_name(rs.getString("ITC_NAME"));
				result.add(idb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);			
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public int regTagCode(String regTagCode, String id_seq){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		String[] regData = regTagCode.split("@");
		int chk = 0;
		String maxItcSeq = "";
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ISSUE_TAG_DATA WHERE ID_SEQ = "+id_seq+"\n");
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			pstmt.close();
			
			for(int i = 0; i < regData.length; i++){
				chk = 0;
				sb = new StringBuffer();
//				sb.append("SELECT ITC_SEQ FROM ISSUE_TAG_CODE WHERE ITG_SEQ = "+regData[i].split(",")[1]+" AND ITC_NAME = '"+regData[i].split(",")[0]+"'\n");
				sb.append("SELECT ITC_SEQ FROM ISSUE_TAG_CODE WHERE ITC_NAME = '"+regData[i].split(",")[0]+"'\n");
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if(rs.next()){
					chk = rs.getInt("ITC_SEQ");
				}
				if(chk > 0){
					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_TAG_DATA VALUES("+id_seq+", "+chk+")\n");
					pstmt = dbconn.createPStatement(sb.toString());
					pstmt.executeUpdate();
				}else{
					sb = new StringBuffer();
					sb.append("SELECT IFNULL(MAX(ITC_SEQ)+1, 1) AS ITC_SEQ FROM ISSUE_TAG_CODE\n");
					pstmt = dbconn.createPStatement(sb.toString());
					rs = pstmt.executeQuery();
					if(rs.next()){
						maxItcSeq = rs.getString("ITC_SEQ");
					}
					
					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_TAG_CODE\n");
					sb.append("SELECT IFNULL(MAX(ITC_SEQ)+1, 1) AS ITC_SEQ\n");
					sb.append(", '"+regData[i].split(",")[0]+"' AS ITC_NAME\n");
//					sb.append(", "+regData[i].split(",")[1]+" AS ITG_SEQ\n");
					sb.append(", 0 AS ITG_SEQ\n");
					sb.append("FROM ISSUE_TAG_CODE\n");
					pstmt = dbconn.createPStatement(sb.toString());
					pstmt.executeUpdate();
					pstmt.close();
					
					sb = new StringBuffer();
					sb.append("INSERT INTO ISSUE_TAG_DATA VALUES("+id_seq+", "+maxItcSeq+")\n");
					pstmt = dbconn.createPStatement(sb.toString());
					pstmt.executeUpdate();
				}
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);			
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public static ArrayList getTagList(String sdate, String edate){
		ArrayList result = new ArrayList();
		HashMap bean = new HashMap();
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			SimpleDateFormat fommatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdate = fommatter.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(sdate));
			edate = fommatter.format(new SimpleDateFormat("yyyyMMddHHmmss").parse(edate));
			
			dbconn = new DBconn();
			dbconn.getSubDirectConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT ITC.ITG_SEQ, ITG.ITG_NAME, ITD.ITC_SEQ, ITC.ITC_NAME, COUNT(*) AS CNT\n");
			sb.append("FROM ISSUE_DATA ID\n");
			sb.append("INNER JOIN ISSUE_TAG_DATA ITD\n");
			sb.append("ON ID.MD_DATE BETWEEN '"+sdate+"' AND '"+edate+"'\n");
			sb.append("AND ID.ID_SEQ = ITD.ID_SEQ\n");
			sb.append("INNER JOIN ISSUE_TAG_CODE ITC\n");
			sb.append("ON ITD.ITC_SEQ = ITC.ITC_SEQ\n");
			sb.append("INNER JOIN ISSUE_TAG_GROUP ITG\n");
			sb.append("ON ITC.ITG_SEQ = ITG.ITG_SEQ\n");
			sb.append("GROUP BY ITC.ITG_SEQ, ITG.ITG_NAME, ITC_SEQ, ITC.ITC_NAME\n");
			sb.append("ORDER BY ITC.ITG_SEQ, ITC_SEQ\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new HashMap();
				bean.put("ITG_SEQ", rs.getString("ITG_SEQ"));
				bean.put("ITG_NAME", rs.getString("ITG_NAME"));
				bean.put("ITC_SEQ", rs.getString("ITC_SEQ"));
				bean.put("ITC_NAME", rs.getString("ITC_NAME"));
				bean.put("CNT", rs.getString("CNT"));
				result.add(bean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public static ArrayList getRegTagList(String id_seq){
		ArrayList result = new ArrayList();
		HashMap bean = new HashMap();
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			dbconn = new DBconn();
			dbconn.getSubDirectConnection();
			
			sb = new StringBuffer();
//			sb.append("SELECT ITC.ITC_SEQ, ITC.ITC_NAME, ITG.ITG_SEQ\n");
			sb.append("SELECT ITC.ITC_SEQ, ITC.ITC_NAME, 0 AS ITG_SEQ\n");
			sb.append("FROM ISSUE_TAG_DATA ITD\n");
			sb.append("INNER JOIN ISSUE_TAG_CODE ITC\n");
			sb.append("ON ITD.ID_SEQ = "+id_seq+"\n");
			sb.append("AND ITD.ITC_SEQ = ITC.ITC_SEQ\n");
//			sb.append("INNER JOIN ISSUE_TAG_GROUP ITG\n");
//			sb.append("ON ITC.ITG_SEQ = ITG.ITG_SEQ\n");
			sb.append("ORDER BY ITC.ITC_SEQ\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new HashMap();
				bean.put("ITC_SEQ", rs.getString("ITC_SEQ"));
				bean.put("ITC_NAME", rs.getString("ITC_NAME"));
				bean.put("ITG_SEQ", rs.getString("ITG_SEQ"));
				result.add(bean);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public int issueChk(String md_seq){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_DATA WHERE MD_SEQ = "+md_seq+"\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt("CNT");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getRelationKeyword(String h_seq){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT T.ID_SEQ, T.ITC_SEQ, T.ITC_NAME, T.CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT MAX(ID.ID_SEQ) AS ID_SEQ, ITD.ITC_SEQ, ITC.ITC_NAME,COUNT(*) AS CNT\n");
			sb.append("		FROM\n");
			sb.append("		(SELECT ID_SEQ FROM ISSUE_DATA WHERE H_SEQ = "+h_seq+") ID\n");
			sb.append("		INNER JOIN ISSUE_TAG_DATA ITD\n");
			sb.append("		ON ID.ID_SEQ = ITD.ID_SEQ\n");
			sb.append("		INNER JOIN ISSUE_TAG_CODE ITC\n");
			sb.append("		ON ITD.ITC_SEQ = ITC.ITC_SEQ\n");
			sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME\n");
			sb.append("		ORDER BY ID_SEQ desc \n");
			sb.append(") T\n");			
			sb.append("LIMIT 10\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setItc_seq(rs.getString("ITC_SEQ"));
				idb.setItc_name(rs.getString("ITC_NAME"));
				idb.setItc_cnt(rs.getInt("CNT"));
				result.add(idb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getTrendCntData(String sdate, String edate){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT T.MD_DATE, T.SG_SEQ, T.SG_NAME, SUM(T.P_CNT) AS P_CNT, SUM(T.N_CNT) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d') AS MD_DATE, ID.SG_SEQ, SG.SG_NAME\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 1 THEN SUM(1) END, 0) AS P_CNT\n");
			sb.append("		, IFNULL(CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END, 0) AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN SITE_GROUP SG\n");
			sb.append("		ON ID.SG_SEQ = SG.SG_SEQ\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9\n");
			sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" 00:00:00' AND '"+edate+" 23:59:59'\n");
			sb.append("		GROUP BY DATE_FORMAT(ID.MD_DATE, '%Y-%m-%d'), ID.SG_SEQ, SG.SG_NAME, IDC.IC_CODE\n");
			sb.append(") T\n");
			sb.append("GROUP BY T.MD_DATE, T.SG_SEQ, T.SG_NAME\n");
			sb.append("ORDER BY T.SG_SEQ\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				IssueDataBean idb = new IssueDataBean();
				idb.setMd_date(rs.getString("MD_DATE"));
				idb.setSg_seq(rs.getString("SG_SEQ"));
				idb.setSg_name(rs.getString("SG_NAME"));
				idb.setP_cnt(rs.getInt("P_CNT"));
				idb.setN_cnt(rs.getInt("N_CNT"));
				result.add(idb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public String[] getBuzzCnt(String sdate, String stime, String edate, String etime){
		String[] result = new String[12];
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT IFNULL(SUM(T.P_CNT), 0) AS P_CNT\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT CASE WHEN (IDC.IC_CODE = 1 AND IDC0.IC_CODE = 1) OR (IDC.IC_CODE = 2 AND IDC0.IC_CODE = 3) THEN SUM(1) END AS P_CNT\n");
			sb.append("		, CASE WHEN (IDC.IC_CODE = 2 AND IDC0.IC_CODE = 1) OR (IDC.IC_CODE = 1 AND IDC0.IC_CODE = 3) THEN SUM(1) END AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN '"+sdate+" "+stime+":00:00'\n");
			if(etime.equals("23")){
				sb.append("		AND '"+edate+" "+etime+":59:59'\n");
			}else{
				sb.append("		AND '"+edate+" "+etime+":00:00'\n");
			}
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC0\n");
			sb.append("		ON ID.ID_SEQ = IDC0.ID_SEQ\n");
			sb.append("		AND IDC0.IC_TYPE = 4 AND (IDC0.IC_CODE = 1 OR IDC0.IC_CODE = 3)\n");
			sb.append("		WHERE ID.MD_TYPE = 1\n");
			sb.append("		GROUP BY IDC.IC_CODE, IDC0.IC_CODE\n");
			sb.append(") T\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result[0] = rs.getString("P_CNT");
				result[1] = rs.getString("N_CNT");
			}
			pstmt.close();
			
			sb = new StringBuffer();
			sb.append("SELECT IFNULL(SUM(T.P_CNT), 0) AS P_CNT\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT CASE WHEN (IDC.IC_CODE = 1 AND IDC0.IC_CODE = 1) OR (IDC.IC_CODE = 2 AND IDC0.IC_CODE = 3) THEN SUM(1) END AS P_CNT\n");
			sb.append("		, CASE WHEN (IDC.IC_CODE = 2 AND IDC0.IC_CODE = 1) OR (IDC.IC_CODE = 1 AND IDC0.IC_CODE = 3) THEN SUM(1) END AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN '"+sdate+" "+stime+":00:00'\n");
			if(etime.equals("23")){
				sb.append("		AND '"+edate+" "+etime+":59:59'\n");
			}else{
				sb.append("		AND '"+edate+" "+etime+":00:00'\n");
			}
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC0\n");
			sb.append("		ON ID.ID_SEQ = IDC0.ID_SEQ\n");
			sb.append("		AND IDC0.IC_TYPE = 4 AND (IDC0.IC_CODE = 1 OR IDC0.IC_CODE = 3)\n");
			sb.append("		WHERE ID.MD_TYPE = 2\n");
			sb.append("		GROUP BY IDC.IC_CODE, IDC0.IC_CODE\n");
			sb.append(") T\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result[2] = rs.getString("P_CNT");
				result[3] = rs.getString("N_CNT");
			}
			pstmt.close();
			
			sb = new StringBuffer();
			sb.append("SELECT IFNULL(SUM(T.P_CNT), 0) AS P_CNT\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT CASE IDC.IC_CODE WHEN  1 THEN SUM(1) END AS P_CNT\n");
			sb.append("		, CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN '"+sdate+" "+stime+":00:00'\n");
			if(etime.equals("23")){
				sb.append("		AND '"+edate+" "+etime+":59:59'\n");
			}else{
				sb.append("		AND '"+edate+" "+etime+":00:00'\n");
			}
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC0\n");
			sb.append("		ON ID.ID_SEQ = IDC0.ID_SEQ\n");
			sb.append("		AND IDC0.IC_TYPE = 4 AND IDC0.IC_CODE = 1\n");
			sb.append("		WHERE ID.MD_TYPE = 1\n");
			sb.append("		GROUP BY IDC.IC_CODE, IDC0.IC_CODE\n");
			sb.append(") T\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result[4] = rs.getString("P_CNT");
				result[5] = rs.getString("N_CNT");
			}
			pstmt.close();
			
			sb = new StringBuffer();
			sb.append("SELECT IFNULL(SUM(T.P_CNT), 0) AS P_CNT\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT CASE IDC.IC_CODE WHEN  1 THEN SUM(1) END AS P_CNT\n");
			sb.append("		, CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN '"+sdate+" "+stime+":00:00'\n");
			if(etime.equals("23")){
				sb.append("		AND '"+edate+" "+etime+":59:59'\n");
			}else{
				sb.append("		AND '"+edate+" "+etime+":00:00'\n");
			}
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC0\n");
			sb.append("		ON ID.ID_SEQ = IDC0.ID_SEQ\n");
			sb.append("		AND IDC0.IC_TYPE = 4 AND IDC0.IC_CODE = 1\n");
			sb.append("		WHERE ID.MD_TYPE = 2\n");
			sb.append("		GROUP BY IDC.IC_CODE, IDC0.IC_CODE\n");
			sb.append(") T\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result[6] = rs.getString("P_CNT");
				result[7] = rs.getString("N_CNT");
			}
			pstmt.close();
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			sb = new StringBuffer();
			sb.append("SELECT IFNULL(SUM(T.P_CNT), 0) AS P_CNT\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT CASE IDC.IC_CODE WHEN  1 THEN SUM(1) END AS P_CNT\n");
			sb.append("		, CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN '"+sdate+" "+stime+":00:00'\n");
			if(etime.equals("23")){
				sb.append("		AND '"+edate+" "+etime+":59:59'\n");
			}else{
				sb.append("		AND '"+edate+" "+etime+":00:00'\n");
			}
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9\n");
			sb.append("		WHERE ID.MD_TYPE = 1\n");
			sb.append("		GROUP BY IDC.IC_CODE\n");
			sb.append(") T\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result[8] = rs.getString("P_CNT");
				result[9] = rs.getString("N_CNT");
			}
			pstmt.close();
			
			sb = new StringBuffer();
			sb.append("SELECT IFNULL(SUM(T.P_CNT), 0) AS P_CNT\n");
			sb.append(", IFNULL(SUM(T.N_CNT), 0) AS N_CNT\n");
			sb.append("FROM\n");
			sb.append("(\n");
			sb.append("		SELECT CASE IDC.IC_CODE WHEN  1 THEN SUM(1) END AS P_CNT\n");
			sb.append("		, CASE IDC.IC_CODE WHEN 2 THEN SUM(1) END AS N_CNT\n");
			sb.append("		FROM ISSUE_DATA ID\n");
			sb.append("		INNER JOIN ISSUE_DATA_CODE IDC\n");
			sb.append("		ON ID.MD_DATE BETWEEN '"+sdate+" "+stime+":00:00'\n");
			if(etime.equals("23")){
				sb.append("		AND '"+edate+" "+etime+":59:59'\n");
			}else{
				sb.append("		AND '"+edate+" "+etime+":00:00'\n");
			}
			sb.append("		AND ID.ID_SEQ = IDC.ID_SEQ\n");
			sb.append("		AND IDC.IC_TYPE = 9\n");
			sb.append("		WHERE ID.MD_TYPE = 2\n");
			sb.append("		GROUP BY IDC.IC_CODE\n");
			sb.append(") T\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result[10] = rs.getString("P_CNT");
				result[11] = rs.getString("N_CNT");
			}
			pstmt.close();

		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	
	public ArrayList getStaticSocialFacebook(String sdate, String curDate, String tableType){
		ArrayList list = null;
		String[]result = null;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		DateUtil du = new DateUtil();
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("	SELECT SSF_TIME FROM "+tableType+" WHERE SSF_DATE = '"+curDate+"' \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			int num = 0;
			String timeType[] = new String[3];
			while(rs.next()){
				timeType[num] = rs.getString("SSF_TIME");
				num++;
			}	
			
			list = new ArrayList();
			for(int i=0; i < timeType.length; i++){
				if(!timeType[i].equals("") || timeType[i] != null ){
					sb = new StringBuffer();
					sb.append("		SELECT														\n");
					sb.append("	      FORMAT(AA.SSF_FAN_CNT, '###,###,###') AS SSF_FAN_CNT      \n");
					sb.append("	     ,FORMAT(AA.SSF_FRD_CNT, '###,###,###') AS SSF_FRD_CNT      \n");
					sb.append("	     ,FORMAT(AA.FAN_DIFF, '###,###,###') AS FAN_DIFF            \n");
					sb.append("	     ,FORMAT(AA.FRD_DIFF, '###,###,###') AS FRD_DIFF            \n");
					sb.append("	 		, AA.SSF_TIME                                        \n");
					sb.append("	 		, AA.SSF_DATE                                        \n");
					sb.append("	FROM                                                            \n");
					sb.append("	(SELECT															\n");									
					sb.append("	 		 FB.SSF_FAN_CNT                                         \n");                                 
					sb.append("	 		, FB.SSF_FRD_CNT                                        \n");                                   
					sb.append("	 		, FB.SSF_TIME                                        \n");
					sb.append("	 		, FB.SSF_DATE                                        \n");
					sb.append("	 		,(SELECT SSF_FAN_CNT FROM "+tableType+" WHERE SSF_DATE = '"+curDate+"' AND SSF_TIME = "+timeType[i]+")-			\n");                  
					sb.append("	 	   (SELECT SSF_FAN_CNT FROM "+tableType+" WHERE SSF_DATE = '"+sdate+"' AND SSF_TIME = "+timeType[i]+") AS FAN_DIFF   \n");    
					sb.append("	 	  ,(SELECT SSF_FRD_CNT FROM "+tableType+" WHERE SSF_DATE = '"+curDate+"' AND SSF_TIME = "+timeType[i]+")-              \n");    
					sb.append("	 	   (SELECT SSF_FRD_CNT FROM "+tableType+" WHERE SSF_DATE = '"+sdate+"' AND SSF_TIME = "+timeType[i]+") AS FRD_DIFF   \n");    
					sb.append("	  FROM "+tableType+" FB WHERE SSF_DATE = '"+curDate+"' AND SSF_TIME = "+timeType[i]+" ) AA                                            \n");
					
					System.out.println(sb.toString());
					pstmt = dbconn.createPStatement(sb.toString());
					rs = pstmt.executeQuery();
					while(rs.next()){
						result = new String[6];
						result[0] = rs.getString("SSF_DATE");
						result[1] = rs.getString("SSF_TIME");
						result[2] = rs.getString("SSF_FAN_CNT");
						result[3] = rs.getString("FAN_DIFF");
						result[4] = rs.getString("SSF_FRD_CNT");
						result[5] = rs.getString("FRD_DIFF");
						list.add(result);
					}
				}
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return list;
	}	
	
	public String[][] getStaticSocialBlog(String sdate, String curDate){
		String[][] result = null;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		DateUtil du = new DateUtil();
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
//			String lastDate = "";
//			int daily_hit = 0;
//			int all_hit = 0;
//			String searchKey = "";
//			
//			sb = new StringBuffer();
//			sb.append("SELECT * FROM STATIC_SOCIAL_BLOG ORDER BY SSB_DATE DESC LIMIT 1	\n");
//			System.out.println(sb.toString());
//			pstmt = dbconn.createPStatement(sb.toString());
//			rs = pstmt.executeQuery();
//			if(rs.next()){
//				lastDate = rs.getString("SSB_DATE");
//				daily_hit = rs.getInt("SSB_DAILY_HIT");
//				all_hit = rs.getInt("SSB_ALL_HIT");
//				searchKey = rs.getString("SSB_SEARCHKEY");
//			}
//			
//			rs = null;
//			pstmt = null;
//			
//			du.setDate(lastDate.replaceAll("-", ""));
//			
//			while(!du.getDate("yyyy-MM-dd").equals(curDate)){
//				du.addDay(1);
//				if(du.getDate("yyyy-MM-dd").equals(curDate)){
//					break;
//				}
//				
//				sb = new StringBuffer();
//				sb.append("INSERT INTO STATIC_SOCIAL_BLOG (SSB_DATE, SSB_DAILY_HIT, SSB_ALL_HIT, SSB_SEARCHKEY) VALUES ('"+du.getDate("yyyy-MM-dd")+"', "+daily_hit+", "+all_hit+", '') \n");
//				System.out.println(sb.toString());
//				pstmt = dbconn.createPStatement(sb.toString());
//				pstmt.executeUpdate();
//			}
//			
//			rs = null;
//			pstmt = null;
			
			sb = new StringBuffer();
			sb.append("SELECT * FROM STATIC_SOCIAL_BLOG WHERE SSB_DATE > '"+sdate+"' ORDER BY SSB_DATE ASC LIMIT 3  \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()){
				count++;
			}
			
			result = new String[count][4];
			count = 0;
			
			rs.beforeFirst();
			while(rs.next()){
				result[count][0] = rs.getString("SSB_DATE");
				result[count][1] = rs.getString("SSB_DAILY_HIT");
				result[count][2] = rs.getString("SSB_ALL_HIT");
				result[count][3] = rs.getString("SSb_SEARCHKEY");
				count++;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return result;
	}
	
	public ArrayList getStaticSocialTwitter(String sdate, String curDate, String tableType){
		ArrayList list = new ArrayList();
		
		String[]result = null;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		DateUtil du = new DateUtil();
		int num = 0;
		String timeType[] = new String[3];
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("	SELECT SST_TIME FROM "+tableType+" WHERE SST_DATE = '"+curDate+"' \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();			
			while(rs.next()){
				timeType[num] = rs.getString("SST_TIME");
				num++;
			}
			
			list = new ArrayList();
			for(int i =0; i <timeType.length; i++ ){
				if(!timeType[i].equals("") || timeType[i] != null ){
					sb = new StringBuffer();
				    sb.append(" 	SELECT														\n");															
				    sb.append("       FORMAT(AA.SST_FOLLOWER, '###,###,###') AS SST_FOLLOWER      \n"); 														
				    sb.append("      ,FORMAT(AA.SST_FOLLOWING, '###,###,###') AS SST_FOLLOWING    \n"); 														
				    sb.append("      ,FORMAT(AA.FAN_DIFF, '###,###,###') AS FAN_DIFF              \n"); 														
				    sb.append("      ,FORMAT(AA.FRD_DIFF, '###,###,###') AS FRD_DIFF              \n"); 														
				    sb.append("  	 , AA.SST_TIME                                             \n"); 														
				    sb.append("  	 , AA.SST_DATE                                             \n"); 														
				    sb.append(" FROM                                                              \n"); 														
				    sb.append(" (SELECT															  \n"); 														
				    sb.append("  		 TW.SST_FOLLOWER                                          \n"); 														
				    sb.append("  		, TW.SST_FOLLOWING                                        \n"); 														
				    sb.append("  		, TW.SST_TIME                                             \n"); 														
				    sb.append("  		, TW.SST_DATE                                             \n"); 														
				    sb.append("  		,(SELECT SST_FOLLOWER FROM "+tableType+" WHERE SST_DATE = '"+curDate+"' AND SST_TIME = "+timeType[i]+")-			\n");			
				    sb.append("  	   (SELECT SST_FOLLOWER FROM "+tableType+" WHERE SST_DATE = '"+sdate+"' AND SST_TIME = "+timeType[i]+") AS FAN_DIFF  \n");
				    sb.append("  	  ,(SELECT SST_FOLLOWING FROM "+tableType+" WHERE SST_DATE = '"+curDate+"' AND SST_TIME = "+timeType[i]+")-            \n");
				    sb.append("  	   (SELECT SST_FOLLOWING FROM "+tableType+" WHERE SST_DATE = '"+sdate+"' AND SST_TIME = "+timeType[i]+") AS FRD_DIFF \n");
				    sb.append("   FROM "+tableType+" TW WHERE SST_DATE = '"+curDate+"' AND SST_TIME = "+timeType[i]+") AA                                  \n");
					
					System.out.println(sb.toString());
					pstmt = dbconn.createPStatement(sb.toString());
					rs = pstmt.executeQuery();
					while(rs.next()){
						result = new String[6];
						result[0] = rs.getString("SST_DATE");
						result[1] = rs.getString("SST_TIME");
						result[2] = rs.getString("SST_FOLLOWER");
						result[3] = rs.getString("FAN_DIFF");
						result[4] = rs.getString("SST_FOLLOWING");
						result[5] = rs.getString("FRD_DIFF");
						list.add(result);
					}
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		return list;
	}
	
	public void registerSocialData(String type, String date, String data1, String data2, String keyword){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			if(type.equals("fb")){
				sb.append("	INSERT INTO STATIC_SOCIAL_FB (SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT)	\n");
				sb.append("	VALUES ('"+date+"',"+data1+","+data2+")								\n");
			}else if(type.equals("fb2")){
				sb.append("	INSERT INTO STATIC_SOCIAL_FB2 (SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT)	\n");
				sb.append("	VALUES ('"+date+"',"+data1+","+data2+")								\n");	
			}else if(type.equals("tweet")){
				sb.append("	INSERT INTO STATIC_SOCIAL_TWEET (SST_DATE, SST_FOLLOWER, SST_FOLLOWING)	\n");
				sb.append("	VALUES ('"+date+"',"+data1+","+data2+")								\n");
			}else if(type.equals("tweet2")){
				sb.append("	INSERT INTO STATIC_SOCIAL_TWEET2 (SST_DATE, SST_FOLLOWER, SST_FOLLOWING)	\n");
				sb.append("	VALUES ('"+date+"',"+data1+","+data2+")								\n");	
			}
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
	}
	
	public void updateSocialData(String type, String date, String data1, String data2, String keyword){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		if(data1.length() > 3){
			data1 = data1.replaceAll(",", "");
		}
		if(data2.length() > 3){
			data2 = data2.replaceAll(",", "");
		}
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			System.out.println("date:"+date);
			
			sb = new StringBuffer();
			if(type.equals("fb")){
				sb.append("	UPDATE STATIC_SOCIAL_FB SET SSF_FAN_CNT = "+ data1 +" , SSF_FRD_CNT = "+ data2 +" WHERE SSF_DATE = '"+ date +"'	\n");
			}else if(type.equals("fb2")){
				sb.append("	UPDATE STATIC_SOCIAL_FB2 SET SSF_FAN_CNT = "+ data1 +" , SSF_FRD_CNT = "+ data2 +" WHERE SSF_DATE = '"+ date +"'	\n");
			}else if(type.equals("tweet")){
				sb.append("	UPDATE STATIC_SOCIAL_TWEET SET SST_FOLLOWER = "+ data1 +" , SST_FOLLOWING = "+ data2 +" WHERE SST_DATE = '"+ date +"'	\n");
			}else if(type.equals("tweet2")){
				sb.append("	UPDATE STATIC_SOCIAL_TWEET2 SET SST_FOLLOWER = "+ data1 +" , SST_FOLLOWING = "+ data2 +" WHERE SST_DATE = '"+ date +"'	\n");
			}
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
	}	
	
	/*
	 * 소셜 통계 관리 수정
	 */
	public void updateSocial(String type, String date, String time, String data1, String data2, String data3){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		int cnt = 0;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("	SELECT COUNT(0) AS CNT FROM SOCIAL_MANAGER WHERE SM_DATE = '"+ date +"' AND  SM_TYPE = '"+type+"' \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				cnt = rs.getInt("CNT");
			}
			sb = new StringBuffer();
			if( cnt > 0 ){
				sb.append("	UPDATE SOCIAL_MANAGER SET SM_CNT1 = "+ data1 +" , SM_CNT2 = "+ data2 +" , SM_CNT3 = "+ data3 +" \n");
				sb.append(" , SM_TIME = "+time+"	\n");
				sb.append(" WHERE SM_DATE = '"+ date +"' AND  SM_TYPE = '"+type+"'	\n");
			}else{
				sb.append("	 \n");
				sb.append("  INSERT INTO SOCIAL_MANAGER	\n");
				sb.append("  (SM_DATE               \n");
				sb.append(" , SM_TIME              \n");
				sb.append(" , SM_CNT1           \n");
				sb.append(" , SM_CNT2           \n");
				sb.append(" , SM_CNT3           \n");
				sb.append(" , SM_TYPE)    \n");
				sb.append(" VALUES ('"+date+"'      \n");
				sb.append(" , "+ time +"           \n");
				sb.append(" , "+ data1 +"           \n");
				sb.append(" , "+ data2 +"           \n");
				sb.append(" , "+ data3 +"           \n");
				sb.append(" , '"+type+"')   \n");
			}
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
	}
	
	/*
	 * 소셜 통계 관리 삭제
	 */
	public boolean deleteSocial(String type, String date){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = null;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("DELETE FROM SOCIAL_MANAGER WHERE SM_TYPE='" + type + "' AND SM_DATE='" + date + "'		\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
			System.out.println(sb.toString());
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
		}
		return true;
	}
	
	
	public ArrayList getRegisteredIssue()    
    {    	
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC2.IC_CODE AS IC1, IDC3.IC_CODE AS IC2, ID.K_XP	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3											\n");
            sb.append("	WHERE ID.K_XP BETWEEN 34 AND 50																\n");
            sb.append("	  AND ID.MD_DATE BETWEEN '2013-10-28 00:00:00' AND '2013-10-28 23:59:59'			\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ																\n");
            sb.append("	  AND IDC.IC_TYPE = 17																		\n");
            sb.append("	  AND IDC.IC_CODE = 1																		\n");
            sb.append("	  AND IDC2.IC_TYPE = 9																		\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																		\n");
            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE		\n");
            sb.append("	ORDER BY ID.MD_DATE DESC																	\n");
//            /sb.append("	LIMIT 6            																			\n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				
				if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 1) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 3)){
					idBean.setTemp1("긍정");
				}else if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 3) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 1)){
					idBean.setTemp1("부정");
				}
				
				if(rs.getInt("K_XP") == 34 || rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){
					idBean.setTemp2("발화");
				}else if(rs.getInt("K_XP") == 35 || rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
					idBean.setTemp2("발열");
				}else if(rs.getInt("K_XP") == 36 || rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
					idBean.setTemp2("폭발");
				}else if(rs.getInt("K_XP") == 37 || rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
					idBean.setTemp2("스웰링");
				}else if(rs.getInt("K_XP") == 38 || rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
					idBean.setTemp2("기타");
				}
				
				idBean.setK_xp(rs.getString("K_XP"));
				issueList.add(idBean);
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
	    	
	    return issueList;
	 }
	
	public String[][] getIssueDataCheck(String md_seqs)    
    {    	
		String[][] issueList = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
            sb.append("	SELECT ID_SEQ, MD_SEQ FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seqs+")	\n");

			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
					
			int i = 0 ;
			while(rs.next()){
				i++;
			}		
			
			issueList = new String[i][2];
			rs.beforeFirst();
			
			i = 0;
			while(rs.next()){
				issueList[i][0] = rs.getString("ID_SEQ");
				issueList[i][1] = rs.getString("MD_SEQ");
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
	    	
	    return issueList;
	 }
	
    public ArrayList getRegisteredIssueList(String date)    
    {    	
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE AS IC1, IDC2.IC_CODE AS IC2	\n");
            sb.append("	FROM ISSUE_DATA ID, IDX I, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2											\n");
            sb.append("	WHERE ID.MD_SEQ = I.MD_SEQ																\n");
            sb.append("	  AND ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND I.K_XP IN (23,24,25,26,27,28,29,30,31,32)											\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND ID.MD_SEQ = ID.MD_PSEQ 															\n");
            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE	\n");
            sb.append("	ORDER BY ID.MD_DATE DESC																\n");
            //sb.append("	LIMIT 5            																		\n");

			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				
				if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 1) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 3)){
					idBean.setTemp1("긍정");
				}else if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 3) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 1)){
					idBean.setTemp1("부정");
				}
				
				issueList.add(idBean);
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
	    	
	    return issueList;
	 }
    
    public ArrayList getRegisteredIssueList(String sdate, String edate, String time)    
    {    	
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE AS IC1, IDC2.IC_CODE AS IC2		\n");
            sb.append("	FROM ISSUE_DATA ID, IDX I, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2									\n");
            sb.append("	WHERE ID.MD_SEQ = I.MD_SEQ																\n");
            sb.append("	  AND ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND I.K_XP IN (23,24,25,26,27,28,29,30,31,32)											\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND ID.MD_SEQ = ID.MD_PSEQ 															\n");
            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE	\n");
            sb.append("	ORDER BY ID.MD_DATE DESC																\n");
            //sb.append("	LIMIT 5            																		\n");

			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				
				if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 1) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 3)){
					idBean.setTemp1("긍정");
				}else if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 3) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 1)){
					idBean.setTemp1("부정");
				}
				
				issueList.add(idBean);
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
	    	
	    return issueList;
	 }
    
    public ArrayList getSpecialIssueList(String date)    
    {    	
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE		\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND IDC2.IC_TYPE = 19																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND ID.MD_SEQ = ID.MD_PSEQ 															\n");
            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE	\n");
            sb.append("	ORDER BY ID.MD_DATE DESC																\n");
            //sb.append("	LIMIT 5            																		\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				if(rs.getInt("IC_CODE") == 1){
					idBean.setTemp1("긍정");
				}else if(rs.getInt("IC_CODE") == 2){
					idBean.setTemp1("부정");
				}
				
				issueList.add(idBean);
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
	    	
	    return issueList;
	 }  
    
    public ArrayList getSpecialIssueList(String sdate, String edate, String time)    
    {    	
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE		\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND IDC2.IC_TYPE = 19																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND ID.MD_SEQ = ID.MD_PSEQ 															\n");
            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE	\n");
            sb.append("	ORDER BY ID.MD_DATE DESC																\n");
            //sb.append("	LIMIT 5            																		\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				if(rs.getInt("IC_CODE") == 1){
					idBean.setTemp1("긍정");
				}else if(rs.getInt("IC_CODE") == 2){
					idBean.setTemp1("부정");
				}
				
				issueList.add(idBean);
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
	    	
	    return issueList;
	 } 
    
    public String[] getRelationCount(String mode, String date)    
    {    	
    	String[] result = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("	)A																												\n");
			} else {
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ				 																			\n");
	            sb.append("	)A																												\n");
			}

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				if(rs.getString("CNT") != null){
					result = rs.getString("CNT").split("");
				}else{
					result = new String("0").split("");
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

    public String[] getRelationCount(String mode, String sdate, String edate, String time)    
    {    	
    	String[] result = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("	)A																												\n");
			} else {
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2												\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		GROUP BY ID.ID_SEQ				 																			\n");
	            sb.append("	)A																												\n");
			}

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				if(rs.getString("CNT") != null){
					result = rs.getString("CNT").split("");
				}else{
					result = new String("0").split("");
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
    
    public int getRelationCountForInt(String mode, String date)    
    {    	
    	int result = 0;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("	)A																												\n");
			} else {
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ				 																			\n");
	            sb.append("	)A																												\n");
			}

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
    
    public int getRelationCountForInt(String mode, String sdate, String edate, String time)    
    {    	
    	int result = 0;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("	)A																												\n");
			} else {
	            sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ 																							\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ																							\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ				 																			\n");
	            sb.append("	)A																												\n");
			}

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
    
    public String[][] getRelationKeyCount(String mode, String date)    
    {    	
    	String[][] result = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("	SELECT * FROM (																									\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT													\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE																		\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT													\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE 																			\n");
	            sb.append("	)A																												\n");
	            sb.append("	ORDER BY A.CNT DESC																								\n");
	            sb.append("	LIMIT 7																											\n");
			} else {
	            sb.append("	SELECT * FROM (																									\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT													\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE 																			\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT													\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE 																			\n");
	            sb.append("	)A																												\n");
	            sb.append("	ORDER BY A.CNT DESC																								\n");
	            sb.append("	LIMIT 7																											\n");
			}

            System.out.println("check11 : "+sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			int i = 0;
			while(rs.next()){
				i++;
			}
			
			result = new String[i][4];
			
			rs.beforeFirst();
			i = 0;
			while(rs.next()){
				result[i][0] = rs.getString("ITC_NAME");
				result[i][1] = rs.getString("CNT");
				result[i][2] = rs.getString("ITC_SEQ");
				result[i][3] = rs.getString("IC_CODE");
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
    
    public String[][] getRelationKeyCount(String mode, String sdate, String edate, String time)    
    {    	
    	String[][] result = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("	SELECT * FROM (																									\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT									\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE															\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT									\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE															\n");
	            sb.append("	)A																												\n");
	            sb.append("	ORDER BY A.CNT DESC																								\n");
	            sb.append("	LIMIT 7																											\n");
			} else {
	            sb.append("	SELECT * FROM (																									\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT									\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE															\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE, COUNT(ITD.ITC_SEQ) AS CNT									\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ITD.ITC_SEQ, ITC.ITC_NAME, IDC2.IC_CODE															\n");
	            sb.append("	)A																												\n");
	            sb.append("	ORDER BY A.CNT DESC																								\n");
	            sb.append("	LIMIT 7																											\n");
			}

            System.out.println("CHECK2 : "+sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			int i = 0;
			while(rs.next()){
				i++;
			}
			
			result = new String[i][4];
			
			rs.beforeFirst();
			i = 0;
			while(rs.next()){
				result[i][0] = rs.getString("ITC_NAME");
				result[i][1] = rs.getString("CNT");
				result[i][2] = rs.getString("ITC_SEQ");
				result[i][3] = rs.getString("IC_CODE");
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
    
    public ArrayList getMajorIssue(String mode, String date)    
    {    	
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			if(mode.equals("posit")){
	            sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
	            sb.append("	ORDER BY MD_DATE DESC																	\n");
	            sb.append("	LIMIT 4																					\n");
			} else {
				sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
	            sb.append("	ORDER BY MD_DATE DESC																	\n");
	            sb.append("	LIMIT 4																					\n");
			}

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setTemp1(rs.getString("IC_CODE"));
				issueList.add(idBean);
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
	    	
	    return issueList;
	 } 
    
    public ArrayList getMajorIssue(String mode, String sdate, String edate, String time)    
    {    	
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			if(mode.equals("posit")){
	            sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
	            sb.append("	ORDER BY MD_DATE DESC																	\n");
	            sb.append("	LIMIT 4																					\n");
			} else {
				sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, ID.MD_DATE, IDC.IC_CODE 		\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
	            sb.append("	ORDER BY MD_DATE DESC																	\n");
	            sb.append("	LIMIT 4																					\n");
			}

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setTemp1(rs.getString("IC_CODE"));
				issueList.add(idBean);
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
	    	
	    return issueList;
	 } 
    
    public int[] getMajorIssueCount(String mode, String date)    
    {    	
    	int [] result = new int[2];    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(ID_SEQ) AS CNT FROM (													 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[0] = rs.getInt("CNT");
			}
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(ID_SEQ) AS CNT FROM (													 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[1] = rs.getInt("CNT");
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
    
    public int[] getMajorIssueCount(String mode, String sdate, String edate, String time)    
    {    	
    	int [] result = new int[2];    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(ID_SEQ) AS CNT FROM (													 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[0] = rs.getInt("CNT");
			}
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(ID_SEQ) AS CNT FROM (													 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC3.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[1] = rs.getInt("CNT");
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
    
    public int getMajorIssueCount(String date, String mode, String mode1)    
    {    	
    	int result = 0;    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(ID_SEQ) AS CNT FROM (													 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");            	
            }
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");            	
            }
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

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
    
    public int getMajorIssueCount(String sdate, String edate, String time, String mode, String mode1)    
    {    	
    	int result = 0;    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(ID_SEQ) AS CNT FROM (													 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");            	
            }
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");            	
            }
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

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
    
    public ArrayList getMajorIssueList(String date, String mode, String mode1, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");            	
            }
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");            	
            }
            sb.append("	  GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				\n"); 
            sb.append("	  ORDER BY MD_DATE DESC 																							\n");
	        sb.append("	  LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																	\n");
            
            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public ArrayList getMajorIssueList(String sdate, String edate, String time, String mode, String mode1, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");            	
            }
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3		\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC3.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 17																	\n");
			if(mode.equals("product")){
				 sb.append("	  AND IDC.IC_CODE = 1																	\n");
			} else if(mode.equals("management")){
				 sb.append("	  AND IDC.IC_CODE = 2																	\n");
			} else if(mode.equals("personal_manage")){
				 sb.append("	  AND IDC.IC_CODE = 3																	\n");
			} else if(mode.equals("social")){
				 sb.append("	  AND IDC.IC_CODE = 4																	\n");
			}
            sb.append("	  AND IDC2.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC2.IC_CODE = 2																	\n");
            sb.append("	  AND IDC3.IC_TYPE = 4																	\n");
            if(mode1.equals("posit")){
            	sb.append("	  AND IDC3.IC_CODE = 3  																\n");	
            }else{
            	sb.append("	  AND IDC3.IC_CODE = 1  																\n");            	
            }
            sb.append("	  GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				\n"); 
            sb.append("	  ORDER BY MD_DATE DESC 																							\n");
	        sb.append("	  LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																	\n");
            
            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public int[] getWeeklyIssueCount(String date)    
    {    	
    	int [] result = new int[2];
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(*) AS CNT FROM (														 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 1																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 2																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[0] = rs.getInt("CNT");
			}
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(*) AS CNT FROM (														 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 2																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 1																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[1] = rs.getInt("CNT");
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
    
    public int getRelationKeywordDataCount(String itc_seq, String date, String mode) {
		
    	int result = 0;    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append(" )A  																											\n");
	        		
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
    
public int getRelationKeywordDataCount(String itc_seq, String sdate, String edate, String time, String mode) {
		
    	int result = 0;    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append(" )A  																											\n");
	        		
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
    
    public ArrayList getRelationKeywordData(String itc_seq, String date, String mode, int nowPage, int PageCount) {
		
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			

			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append("	ORDER BY MD_DATE DESC 																							\n");
	        sb.append("	LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																	\n");
	        		
	        		System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				issueList.add(idBean);
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
	    	
	    return issueList; 
	}
    
public ArrayList getRelationKeywordData(String itc_seq, String sdate, String edate, String time, String mode, int nowPage, int PageCount) {
		
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			

			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC			\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = ITD.ID_SEQ																					\n");
	        sb.append("	  AND ITD.ITC_SEQ = ITC.ITC_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append("	  AND ITD.ITC_SEQ = "+itc_seq+"																					\n");
	        sb.append("	ORDER BY MD_DATE DESC 																							\n");
	        sb.append("	LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																	\n");
	        		
	        		System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				issueList.add(idBean);
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
	    	
	    return issueList; 
	}
    
    
    public int getTodayIssueDataCount(String date, String mode) {
		
    	int result = 0;    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append(" )A  																											\n");
	        		
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
    
public int getTodayIssueDataCount(String sdate, String edate, String time, String mode) {
		
    	int result = 0;    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("	SELECT COUNT(*) AS CNT FROM (																					\n");
			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append(" )A  																											\n");
	        		
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
    
    public ArrayList getTodayIssueData(String date, String mode, int nowPage, int PageCount) {
		
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			

			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append("	ORDER BY MD_DATE DESC 																							\n");
	        sb.append("	LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																	\n");
	        		
	        		System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				issueList.add(idBean);
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
	    	
	    return issueList; 
	}
    
public ArrayList getTodayIssueData(String sdate, String edate, String time, String mode, int nowPage, int PageCount) {
		
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			

			sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 1																							\n");
	        sb.append("	UNION 																											\n");
	        sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME											\n");
	        sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2													\n");
	        sb.append("	WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'											\n");
	        sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ																					\n");
	        sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ																					\n");
	        sb.append("	  AND IDC.IC_TYPE = 4																							\n");
	        if(mode.equals("posit")){
	        	sb.append("	  AND IDC.IC_CODE = 3																							\n");
	        }else{
	        	sb.append("	  AND IDC.IC_CODE = 1																							\n");
	        }
	        sb.append("	  AND IDC2.IC_TYPE = 9																							\n");
	        sb.append("	  AND IDC2.IC_CODE = 2																							\n");
	        sb.append("	ORDER BY MD_DATE DESC 																							\n");
	        sb.append("	LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																	\n");
	        		
	        		System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				issueList.add(idBean);
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
	    	
	    return issueList; 
	}
    
    
    public String getRelationKeywordName(String itc_seq) {
		
    	String result = "";    
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("	SELECT ITC_NAME FROM ISSUE_TAG_CODE WHERE ITC_SEQ = "+itc_seq+"		\n");
			
	        		
	        System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result = rs.getString("ITC_NAME");
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
    
    public ArrayList getRelationKeywordAllData(String mode, String date, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		ORDER BY MD_DATE DESC 																						\n");
		        sb.append("		LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																\n");
			} else {
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		ORDER BY MD_DATE DESC 																						\n");
		        sb.append("		LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																\n");
			}

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public ArrayList getRelationKeywordAllData(String mode, String sdate, String edate, String time, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			if(mode.equals("posit")){
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		ORDER BY MD_DATE DESC 																						\n");
		        sb.append("		LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																\n");
			} else {
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 2																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 1																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		UNION																										\n");
	            sb.append("		SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_TAG_DATA ITD, ISSUE_TAG_CODE ITC		\n");
	            sb.append("		WHERE ID.MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'										\n");
	            sb.append("		  AND ID.ID_SEQ = ITD.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC.ID_SEQ																				\n");
	            sb.append("		  AND ID.ID_SEQ = IDC2.ID_SEQ																				\n");
	            sb.append("		  AND IDC.IC_TYPE = 9																						\n");
	            sb.append("		  AND IDC.IC_CODE = 1																						\n");
	            sb.append("		  AND IDC2.IC_TYPE = 4																						\n");
	            sb.append("		  AND IDC2.IC_CODE = 3																						\n");
	            sb.append("		  AND ITD.ITC_SEQ = ITC.ITC_SEQ																				\n");
	            sb.append("		GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME										\n");
	            sb.append("		ORDER BY MD_DATE DESC 																						\n");
		        sb.append("		LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																\n");
			}

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public int getWeeklyIssueListCount(String date, String mode)    
    {    	
    	int result = 0;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			if(mode.equals("posit")){
				sb.append("SELECT COUNT(*) AS CNT FROM (														 	\n");
				sb.append("	SELECT ID.ID_SEQ																	 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ																		\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ																	 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ																		\n");
	            sb.append(") A																						\n");
			}else{
				sb.append("SELECT COUNT(*) AS CNT FROM (														 	\n");
				sb.append("	SELECT ID.ID_SEQ																	 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ																		\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ																	 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ																		\n");
	            sb.append(") A																						\n");
			}

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
    
    public ArrayList getWeeklyIssueList(String date, String mode, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			if(mode.equals("posit")){
				sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME					\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME					\n");
	            sb.append("	ORDER BY MD_DATE DESC 																						\n");
		        sb.append("	LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																\n");
			}else{
				sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 2																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME					\n");
	            sb.append("	UNION  																					\n");
	            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME				 	\n");
	            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
	            sb.append("	WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
	            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
	            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
	            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
	            sb.append("	  AND IDC.IC_CODE = 1																	\n");
	            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
	            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
	            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME					\n");
	            sb.append("	ORDER BY MD_DATE DESC 																						\n");
		        sb.append("	LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"																\n");
			}

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public String[][] getChanelIssueData(String date, String mode) {
    	String [][] result = null;
    	
    	try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("	SELECT ID.SG_SEQ, SG.SG_NAME, COUNT(*) AS CNT								\n");
			sb.append("	FROM ISSUE_DATA ID, SITE_GROUP SG											\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("	  AND ID.SG_SEQ = SG.SG_SEQ													\n");
			if(mode.equals("social")){
				sb.append("	  AND ID.SG_SEQ IN (17,18,19,25)											\n");	
			}else{
				sb.append("	  AND ID.SG_SEQ IN (3,24,27,28,29,30)										\n");	
			}
			sb.append("	GROUP BY ID.SG_SEQ, SG.SG_NAME  											\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			int i = 0;
			while(rs.next()){
				i++;
			}
			
			result = new String[i][3];
			i=0;
			rs.beforeFirst();
			
			while(rs.next()){
				result[i][0] = rs.getString("SG_SEQ");
				result[i][1] = rs.getString("SG_NAME");
				result[i][2] = rs.getString("CNT");
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
    
    public String[][] getChanelIssueData(String mode, String sdate, String edate, String time) {
    	String [][] result = null;
    	
    	try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("	SELECT ID.SG_SEQ, SG.SG_NAME, COUNT(*) AS CNT								\n");
			sb.append("	FROM ISSUE_DATA ID, SITE_GROUP SG											\n");
			sb.append("	WHERE MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'			\n");
			sb.append("	  AND ID.SG_SEQ = SG.SG_SEQ													\n");
			if(mode.equals("social")){
				sb.append("	  AND ID.SG_SEQ IN (17,18,19,25)											\n");	
			}else{
				sb.append("	  AND ID.SG_SEQ IN (3,24,27,28,29,30)										\n");	
			}
			sb.append("	GROUP BY ID.SG_SEQ, SG.SG_NAME  											\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			int i = 0;
			while(rs.next()){
				i++;
			}
			
			result = new String[i][3];
			i=0;
			rs.beforeFirst();
			
			while(rs.next()){
				result[i][0] = rs.getString("SG_SEQ");
				result[i][1] = rs.getString("SG_NAME");
				result[i][2] = rs.getString("CNT");
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
    
    public String getSg_name(String sg_seq) {
    	String  result = null;
    	
    	try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("	SELECT SG_NAME FROM SITE_GROUP WHERE SG_SEQ = "+sg_seq+"				\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next()){
				result = rs.getString("SG_NAME");
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
    
    public int getChanelIssueDataCount(String date, String sg_seq)    
    {    	
    	int result = 0;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT COUNT(*) AS CNT														\n");
			sb.append("FROM ISSUE_DATA 																\n");
			sb.append("WHERE MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("  AND SG_SEQ = "+sg_seq+"													\n");
			

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
    
    public int getChanelIssueDataCount(String sdate, String edate, String time, String sg_seq)    
    {    	
    	int result = 0;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append("SELECT COUNT(*) AS CNT														\n");
			sb.append("FROM ISSUE_DATA 																\n");
			sb.append("WHERE MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'			\n");
			sb.append("  AND SG_SEQ = "+sg_seq+"													\n");
			

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
    
    public ArrayList getChanelIssueDataList(String date, String sg_seq, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			
			sb.append("SELECT ID_SEQ, MD_DATE, ID_TITLE, ID_URL, MD_SITE_NAME						\n");
			sb.append("FROM ISSUE_DATA 																\n");
			sb.append("WHERE MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("  AND SG_SEQ = "+sg_seq+"													\n");
			sb.append("ORDER BY MD_DATE DESC 														\n");
	        sb.append("LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"								\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public ArrayList getChanelIssueDataList(String sdate, String edate, String time, String sg_seq, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			
			sb.append("SELECT ID_SEQ, MD_DATE, ID_TITLE, ID_URL, MD_SITE_NAME						\n");
			sb.append("FROM ISSUE_DATA 																\n");
			sb.append("WHERE MD_DATE BETWEEN '"+sdate+" "+time+"' AND '"+edate+" "+time+"'			\n");
			sb.append("  AND SG_SEQ = "+sg_seq+"													\n");
			sb.append("ORDER BY MD_DATE DESC 														\n");
	        sb.append("LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"								\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public ArrayList getAllRegisteredIssueList(String date, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			
            sb.append("	SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE		\n");
            sb.append("	FROM ISSUE_DATA ID, IDX I, ISSUE_DATA_CODE IDC											\n");
            sb.append("	WHERE ID.MD_SEQ = I.MD_SEQ																\n");
            sb.append("	  AND ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND I.K_XP IN (23,24,25,26,27,28,29,30,31,32)											\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND ID.MD_SEQ = ID.MD_PSEQ 															\n");
            sb.append("	GROUP BY ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE	\n");
            sb.append("	ORDER BY ID.MD_DATE DESC																\n");
	        sb.append(" LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"											\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public int getAllRegisteredIssueCount(String date)    
    {    	
    	int result = 0;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			
			sb.append("	SELECT COUNT(*) AS CNT FROM(															\n");
            sb.append("	SELECT ID.ID_SEQ																		\n");
            sb.append("	FROM ISSUE_DATA ID, IDX I, ISSUE_DATA_CODE IDC											\n");
            sb.append("	WHERE ID.MD_SEQ = I.MD_SEQ																\n");
            sb.append("	  AND ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND I.K_XP IN (23,24,25,26,27,28,29,30,31,32)											\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND ID.MD_SEQ = ID.MD_PSEQ 															\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	) A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
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
    
    public ArrayList getAllSpecialIssueList(String date, int nowPage, int PageCount)    
    {    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			
			sb.append("SELECT ID.ID_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME						\n");
			sb.append("FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC 										\n");
			sb.append("WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("  AND ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 19 AND IDC.IC_CODE = 1		\n");
			sb.append("ORDER BY ID.MD_DATE DESC 														\n");
	        sb.append("LIMIT "+(PageCount*(nowPage-1))+","+PageCount+"								\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				result.add(idBean);
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
    
    public int getAllSpecialIssueCount(String date)    
    {    	
    	int result = 0;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			
			sb.append("SELECT COUNT(*) AS CNT														\n");
			sb.append("FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC 										\n");
			sb.append("WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'			\n");
			sb.append("  AND ID.ID_SEQ = IDC.ID_SEQ AND IDC.IC_TYPE = 19 AND IDC.IC_CODE = 1		\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
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

    public IssueDataBean dashcnt(String date, String why, String fill){
    	
    	IssueDataBean idb = new IssueDataBean();
    	StringBuffer sb = null;
    	DBconn dbconn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try{
    	
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append("SELECT ITD.ITC_SEQ, IDC1.IC_CODE, COUNT(*) AS CNT \n");
    		sb.append("FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC1, ISSUE_DATA_CODE IDC, ISSUE_TAG_DATA ITD \n");
    		sb.append("WHERE ID.MD_DATE BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59' \n");
    		sb.append("AND IDC.IC_TYPE = 9 \n");
    		sb.append("AND IDC.IC_CODE = "+fill+" \n");
    		sb.append("AND IDC1.IC_TYPE = 4 \n");
    		sb.append("AND IDC1.IC_CODE = "+why+" \n");
    		sb.append("AND ID.ID_SEQ = IDC.ID_SEQ \n");
    		sb.append("AND ID.ID_SEQ = IDC1.ID_SEQ \n");
    		sb.append("AND ID.ID_SEQ = ITD.ID_SEQ \n");
    		sb.append("GROUP BY ITD.ITC_SEQ, IDC1.IC_CODE \n");
    		
    		pstmt = dbconn.createPStatement(rs.toString());
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()){
    			IssueDataBean idBean = new IssueDataBean();
    			idBean.setItc_seq(rs.getString("ITC_SEQ"));
    			idBean.setIc_code(rs.getString("IC_CODE"));
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		sb = null;
    		try{if( dbconn!=null ) dbconn.close();}catch(Exception e){e.printStackTrace();};
    		try{if( pstmt!=null ) pstmt.close();}catch(Exception e){e.printStackTrace();};
    		try{if( rs!=null ) rs.close();}catch(Exception e){e.printStackTrace();};
    	}
    	
    	return idb;
    }
    /*긍정 자사*/

public ArrayList getImportantIssueCompliance(String sDateTime, String eDateTime, int ic_type)
    {
    	ArrayList arrlist = new ArrayList();
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            sb.append("SELECT I.ID_SEQ, I.MD_DATE, I.ID_TITLE, I.ID_URL								\n");
            sb.append("FROM ISSUE_DATA I, ISSUE_DATA_CODE IDC										\n");
            sb.append("WHERE I.MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'		\n");//"+sDate+"
            sb.append("AND I.ID_SEQ = IDC.ID_SEQ													\n");
            sb.append("AND IDC.IC_TYPE = "+ic_type+"												\n");
            sb.append("GROUP BY I.ID_SEQ, I.MD_DATE, I.ID_TITLE, I.ID_URL							\n");
            sb.append("ORDER BY MD_DATE DESC														\n");
            sb.append("LIMIT 100																	\n");
            
            System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
            while( rs.next() ) {
            	 
	        	 isBean  = new IssueBean();
	        	 isBean.setI_seq(rs.getString("ID_SEQ"));
	        	 isBean.setMd_date(rs.getString("MD_DATE"));
	        	 isBean.setI_title(rs.getString("ID_TITLE"));//issuedatabean에 수정했음
	        	 isBean.setId_url(rs.getString("ID_URL"));
	        	 arrlist.add(isBean);
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
    
    public ArrayList getRegisteredIssueCompliance(String sDateTime, String eDateTime)    
    {    	
    	System.out.println("IssueMgr getRegisteredIssueCompliance() \n");
    	ArrayList issueList = new ArrayList();    
    	IssueDataBean idBean = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			/*
			sb.append("	SELECT ID.MD_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC2.IC_CODE AS IC1, IDC3.IC_CODE AS IC2, ID.K_XP	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3, MEMBER M											\n");
            sb.append("	WHERE ID.K_XP BETWEEN 34 AND 50																\n");//52~56
            sb.append("	AND ID.MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'						\n");
            sb.append("	AND ID.ID_SEQ = IDC.ID_SEQ																\n");
            sb.append("	AND ID.ID_SEQ = IDC2.ID_SEQ																\n");
            sb.append("	AND ID.ID_SEQ = IDC3.ID_SEQ																\n");
            sb.append(" AND ID.M_SEQ = M.M_SEQ																	\n");			
            sb.append(" AND M.MG_SEQ = 20																						\n");
            //sb.append("	AND IDC.IC_TYPE = 17																		\n");//21~24
            //sb.append("	AND IDC.IC_CODE = 1																		\n");
            sb.append("	AND IDC2.IC_TYPE = 9																		\n");//성향
            sb.append("	AND IDC3.IC_TYPE = 4																		\n");//구분
            sb.append("	GROUP BY ID.MD_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC.IC_CODE		\n");
            sb.append("	ORDER BY ID.MD_DATE DESC																	\n");
			*/
			sb.append("	SELECT ID.MD_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC2.IC_CODE AS IC1, IDC3.IC_CODE AS IC2, ID.K_XP 	\n");
			sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3, MEMBER M								\n");
			sb.append("	WHERE ID.MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'																\n");
			//sb.append("	AND ID.K_XP BETWEEN 34 AND 50																								\n");//추가되는 xp에 맞출 것
			sb.append("	AND ID.ID_SEQ = IDC.ID_SEQ																									\n");
			sb.append("	AND ID.ID_SEQ = IDC2.ID_SEQ																									\n");
			sb.append("	AND ID.ID_SEQ = IDC3.ID_SEQ																									\n");
			sb.append("	AND IDC.IC_TYPE = 19 AND IDC.IC_CODE = 1																					\n");
			sb.append("	AND IDC2.IC_TYPE = 9																										\n");
			sb.append("	AND IDC3.IC_TYPE = 4																										\n");
			sb.append("	AND ID.M_SEQ = M.M_SEQ																										\n");
			sb.append("	AND M.MG_SEQ IN (19, 20)																											\n");
			sb.append("	ORDER BY ID.MD_DATE DESC																									\n");
			/*
			sb.append("SELECT ID.MD_SEQ, ID.MD_DATE, ID.ID_TITLE, ID.ID_URL, ID.MD_SITE_NAME, IDC2.IC_CODE AS IC1, IDC3.IC_CODE AS IC2 	\n");
			sb.append("FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3													\n");
			sb.append("WHERE ID.MD_DATE BETWEEN '"+sDateTime+"' AND '"+eDateTime+"'														\n");
			sb.append("AND ID.ID_SEQ = IDC2.ID_SEQ																						\n");
			sb.append("AND ID.ID_SEQ = IDC3.ID_SEQ																						\n");
			sb.append("AND IDC2.IC_TYPE = 9																								\n");
			sb.append("AND IDC3.IC_TYPE = 4																								\n");
			sb.append("AND ID.M_SEQ IN (SELECT M_SEQ FROM MEMBER WHERE MG_SEQ = 20)														\n");//20
			sb.append("ORDER BY ID.MD_DATE DESC																							\n");
			*/
			
			
			System.out.println("check : "+sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				
				//긍부정 셋팅
				//IC1 ; 1. 긍정 2.부정  IC2 ; 1.자사 2.관계사 3.경쟁사 4.산업전반 
				if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 1) 
						||	(rs.getInt("IC1") == 1 && rs.getInt("IC2") == 2) 
						|| 	(rs.getInt("IC1") == 1 && rs.getInt("IC2") == 3)
						|| 	(rs.getInt("IC1") == 1 && rs.getInt("IC2") == 4) ){
					idBean.setTemp1("긍정");
					
				}else if((rs.getInt("IC1") == 2 && rs.getInt("IC2") == 1) 
						|| (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 2)
						|| (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 3)
						|| (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 4)	){
					idBean.setTemp1("부정");
				}
					
				
				/*if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 1) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 3)){
					idBean.setTemp1("긍정");
				}else if((rs.getInt("IC1") == 1 && rs.getInt("IC2") == 3) || (rs.getInt("IC1") == 2 && rs.getInt("IC2") == 1)){
					idBean.setTemp1("부정");
				}*/
				
				if(rs.getInt("K_XP") == 58 || rs.getInt("K_XP") == 40 || rs.getInt("K_XP") == 46){//엑스피 변경에 맞출 것
					idBean.setTemp2("A risk");
				}else if(rs.getInt("K_XP") == 59 || rs.getInt("K_XP") == 41 || rs.getInt("K_XP") == 47){
					idBean.setTemp2("B risk");
				}else if(rs.getInt("K_XP") == 60 || rs.getInt("K_XP") == 42 || rs.getInt("K_XP") == 48){
					idBean.setTemp2("C risk");
				}else if(rs.getInt("K_XP") == 61 || rs.getInt("K_XP") == 43 || rs.getInt("K_XP") == 49){
					idBean.setTemp2("법률/정부");
				}else if(rs.getInt("K_XP") == 62 || rs.getInt("K_XP") == 44 || rs.getInt("K_XP") == 50){
					idBean.setTemp2("지적재산");
				}
				
				idBean.setK_xp(rs.getString("K_XP"));
				
				issueList.add(idBean);
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
	    	
	    return issueList;
	 }
    
    
    public int[] getstackChartCount(String sDate, String eDate)    
    {    	
    	int [] result = new int[2];
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(*) AS CNT FROM (														 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 1																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 2																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[0] = rs.getInt("CNT");
			}
			
			sb = new StringBuffer();	
			sb.append("SELECT COUNT(*) AS CNT FROM (														 	\n");
			sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 2																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 1  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append("	UNION  																					\n");
            sb.append("	SELECT ID.ID_SEQ																	 	\n");
            sb.append("	FROM ISSUE_DATA ID, ISSUE_DATA_CODE IDC, ISSUE_DATA_CODE IDC2							\n");
            sb.append("	WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'					\n");
            sb.append("	  AND ID.ID_SEQ = IDC.ID_SEQ															\n");
            sb.append("	  AND ID.ID_SEQ = IDC2.ID_SEQ															\n");
            sb.append("	  AND IDC.IC_TYPE = 9																	\n");
            sb.append("	  AND IDC.IC_CODE = 1																	\n");
            sb.append("	  AND IDC2.IC_TYPE = 4																	\n");
            sb.append("	  AND IDC2.IC_CODE = 3  																\n");
            sb.append("	GROUP BY ID.ID_SEQ																		\n");
            sb.append(") A																						\n");

            System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				result[1] = rs.getInt("CNT");
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
    
    public String getIssueStackCountCompliance(String sDate, String eDate)
    {
    	String resultData = null;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            
            //등록된 이슈의 카운트
            sb.append("	SELECT SUM(CNT1) AS CNT1, SUM(CNT2) AS CNT2, SUM(CNT3) AS CNT3      	\n");
    		sb.append("	, SUM(CNT4) AS CNT4, SUM(CNT5) AS CNT5									\n");
    		sb.append("	FROM																	\n");
    		sb.append("	(																		\n");
    		sb.append("	SELECT IFNULL(CASE IDC.IC_TYPE WHEN 21 THEN SUM(1) END, 0) AS CNT1		\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 22 THEN SUM(1) END, 0) AS CNT2			\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 23 THEN SUM(1) END, 0) AS CNT3			\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 24 THEN SUM(1) END, 0) AS CNT4			\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 25 THEN SUM(1) END, 0) AS CNT5			\n");
    		sb.append("	FROM ISSUE_DATA ID															\n");
    		sb.append("		INNER JOIN																	\n");
    		sb.append("		ISSUE_DATA_CODE IDC															\n");
    		sb.append("		ON ID.ID_SEQ = IDC.ID_SEQ													\n");
    		sb.append("		WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'		\n");
    		sb.append("		AND IDC.IC_TYPE IN (21,22,23,24,25)										\n"); //(21, 22, 23, 24, 25)
    		sb.append("	GROUP BY IDC.IC_TYPE) T															\n");

    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            System.out.println(sb.toString());
            
            while( rs.next() ) {
            	resultData = rs.getString("CNT1")+","+rs.getString("CNT2")+","+rs.getString("CNT3")+","+rs.getString("CNT4")+","+rs.getString("CNT5");
            	System.out.println("resultData : "+resultData);
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
        return resultData;
    }
    
    public String getIssueColumnCountCompliance(String sDate, String eDate)
    {
    	String resultData = null;
    	
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();

            sb = new StringBuffer();
            
            //등록된 이슈의 카운트
            sb.append("	SELECT SUM(CNT1) AS CNT1, SUM(CNT2) AS CNT2, SUM(CNT3) AS CNT3      	\n");
    		sb.append("	, SUM(CNT4) AS CNT4, SUM(CNT5) AS CNT5									\n");
    		sb.append("	FROM																	\n");
    		sb.append("	(																		\n");
    		sb.append("	SELECT IFNULL(CASE IDC.IC_TYPE WHEN 21 THEN SUM(1) END, 0) AS CNT1		\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 22 THEN SUM(1) END, 0) AS CNT2			\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 23 THEN SUM(1) END, 0) AS CNT3			\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 24 THEN SUM(1) END, 0) AS CNT4			\n");
    		sb.append("	, IFNULL(CASE IDC.IC_TYPE WHEN 25 THEN SUM(1) END, 0) AS CNT5			\n");
    		sb.append("	FROM ISSUE_DATA ID															\n");
    		sb.append("		INNER JOIN																	\n");
    		sb.append("		ISSUE_DATA_CODE IDC															\n");
    		sb.append("		ON ID.ID_SEQ = IDC.ID_SEQ													\n");
    		sb.append("		WHERE ID.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'		\n");
    		sb.append("		AND IDC.IC_TYPE IN (21,22,23,24,25)										\n"); //(21, 22, 23, 24, 25)
    		sb.append("	GROUP BY IDC.IC_TYPE) T															\n");

    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            System.out.println(sb.toString());
            
            while( rs.next() ) {
            	resultData = rs.getString("CNT1")+","+rs.getString("CNT2")+","+rs.getString("CNT3")+","+rs.getString("CNT4")+","+rs.getString("CNT5");
            	System.out.println("resultData : "+resultData);
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
        return resultData;
    }
    
    
    public ArrayList getIssueDataforTypeCode2(String sDate, String sTime, String eDate, String eTime, String typCode1, String typeCode2)
    {
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            sb = new StringBuffer();
    		sb.append("	##자사 관련 콘텐츠 현황 \n");
			sb.append("	SELECT A.ID_SEQ				\n");
			sb.append("      ,A.MD_SITE_NAME        \n");
			sb.append("      ,A.ID_TITLE            \n");
			sb.append("      ,A.ID_URL              \n");
			sb.append("      ,A.MD_SEQ              \n");
			sb.append("      ,A.MD_PSEQ             \n");
			sb.append("      ,A.MD_DATE             \n");
			sb.append("      ,A.MD_SAME_CT          \n");
			sb.append("      ,A.MD_TYPE             \n");
			sb.append("      ,D.IC_CODE             \n");
			sb.append("  FROM ISSUE_DATA A          \n");
			sb.append("     , ISSUE_DATA_CODE B     \n");
			sb.append("     , ISSUE_DATA_CODE C     \n");
			sb.append("     , ISSUE_DATA_CODE D     \n");
			sb.append("     , ISSUE_DATA_CODE E     \n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ   \n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ   \n");
			sb.append("   AND A.ID_SEQ = D.ID_SEQ   \n");
			sb.append("   AND A.ID_SEQ = E.ID_SEQ   \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"' \n");
			sb.append("   AND A.S_SEQ NOT IN (2196,2199,3883,5016219) \n");	//포탈 사이트 제외
			sb.append("   AND B.IC_TYPE = 1 AND B.IC_CODE IN ("+typCode1+")			\n");
			sb.append("   AND C.IC_TYPE = 2 AND C.IC_CODE IN ( "+typeCode2+")      \n");
			sb.append("   AND D.IC_TYPE = 9 			\n");
			sb.append("   AND E.IC_TYPE = 10 AND E.IC_CODE = 1			\n");
			sb.append("ORDER BY MD_DATE DESC                            \n");
    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while( rs.next() ) {
            	idBean = new IssueDataBean();
            	idBean.setId_seq(rs.getString("ID_SEQ"));
            	idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
            	idBean.setId_title(rs.getString("ID_TITLE"));
            	idBean.setId_url(rs.getString("ID_URL"));
            	idBean.setMd_seq(rs.getString("MD_SEQ"));
            	idBean.setMd_pseq(rs.getString("MD_PSEQ"));
            	idBean.setMd_date(rs.getString("MD_DATE"));
            	idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
            	idBean.setMd_type(rs.getString("MD_TYPE"));
            	idBean.setIc_code(rs.getString("IC_CODE"));
            	result.add(idBean);
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
    
    
    public ArrayList getIssueDataforTypeCode31(String sDate, String sTime, String eDate, String eTime, String typCode1, String typCode31)
    {
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            sb = new StringBuffer();
    		sb.append("	##상품 관련 콘텐츠 현황 	\n");
			sb.append("	SELECT A.ID_SEQ				\n");
			sb.append("      ,A.MD_SITE_NAME        \n");
			sb.append("      ,A.ID_TITLE            \n");
			sb.append("      ,A.ID_URL              \n");
			sb.append("      ,A.MD_SEQ              \n");
			sb.append("      ,A.MD_PSEQ             \n");
			sb.append("      ,A.MD_DATE             \n");
			sb.append("      ,A.MD_SAME_CT          \n");
			sb.append("      ,A.MD_TYPE             \n");
			sb.append("      ,C.IC_CODE             \n");
			sb.append("  FROM ISSUE_DATA A          \n");
			sb.append("     , ISSUE_DATA_CODE B     \n");
			sb.append("     , ISSUE_DATA_CODE C     \n");
			sb.append("     , ISSUE_DATA_CODE D     \n");
			sb.append("     , ISSUE_DATA_CODE E     \n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ   \n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ   \n");
			sb.append("   AND A.ID_SEQ = D.ID_SEQ   \n");
			sb.append("   AND A.ID_SEQ = E.ID_SEQ   \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"' \n");
			sb.append("   AND A.S_SEQ NOT IN (2196,2199,3883,5016219) 			\n");
			sb.append("   AND B.IC_TYPE = 31 AND B.IC_CODE IN ("+typCode31+")					\n");
			sb.append("   AND C.IC_TYPE = 9 							\n");
			sb.append("   AND D.IC_TYPE = 1 AND D.IC_CODE IN ("+typCode1+") 		\n");
			sb.append("   AND E.IC_TYPE = 10 AND E.IC_CODE = 1 			\n");
			sb.append("ORDER BY MD_DATE DESC                            \n");
    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while( rs.next() ) {
            	idBean = new IssueDataBean();
            	idBean.setId_seq(rs.getString("ID_SEQ"));
            	idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
            	idBean.setId_title(rs.getString("ID_TITLE"));
            	idBean.setId_url(rs.getString("ID_URL"));
            	idBean.setMd_seq(rs.getString("MD_SEQ"));
            	idBean.setMd_pseq(rs.getString("MD_PSEQ"));
            	idBean.setMd_date(rs.getString("MD_DATE"));
            	idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
            	idBean.setMd_type(rs.getString("MD_TYPE"));
            	idBean.setIc_code(rs.getString("IC_CODE"));
            	result.add(idBean);
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
    
    public ArrayList getIssueDataforTypeCode11(String sDate, String sTime, String eDate, String eTime, String typCode1, String typeCode2, String typeCode31)
    {
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
        try {

            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            sb = new StringBuffer();
            sb.append("	## 온라인 관련 정보 \n");
			sb.append("SELECT A.ID_SEQ				\n");
			sb.append("      ,A.MD_SITE_NAME        \n");
			sb.append("      ,A.ID_TITLE            \n");
			sb.append("      ,A.ID_URL              \n");
			sb.append("      ,A.MD_SEQ              \n");
			sb.append("      ,A.MD_PSEQ             \n");
			sb.append("      ,MD_DATE             \n");
			sb.append("      ,A.MD_SAME_CT          \n");
			sb.append("      ,A.MD_TYPE             \n");
			sb.append("      ,C.IC_CODE             \n");
			sb.append("  FROM ISSUE_DATA A          \n");
			sb.append("     , ISSUE_DATA_CODE B     \n");
			sb.append("     , ISSUE_DATA_CODE C    \n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ   \n");
			sb.append("   AND A.ID_SEQ = C.ID_SEQ   \n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"' \n");
			sb.append("   AND A.S_SEQ NOT IN (2196,2199,3883,5016219) \n");
			sb.append("   AND B.IC_TYPE = 1 AND B.IC_CODE IN ("+typCode1+")			\n");
			sb.append("   AND C.IC_TYPE = 9 			\n");
			sb.append("   GROUP BY A.MD_PSEQ 			\n");
			sb.append("ORDER BY MD_DATE DESC                            \n");
    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while( rs.next() ) {
            	idBean = new IssueDataBean();
            	idBean.setId_seq(rs.getString("ID_SEQ"));
            	idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
            	idBean.setId_title(rs.getString("ID_TITLE"));
            	idBean.setId_url(rs.getString("ID_URL"));
            	idBean.setMd_seq(rs.getString("MD_SEQ"));
            	idBean.setMd_pseq(rs.getString("MD_PSEQ"));
            	idBean.setMd_date(rs.getString("MD_DATE"));
            	idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
            	idBean.setMd_type(rs.getString("MD_TYPE"));
            	idBean.setIc_code(rs.getString("IC_CODE"));
            	result.add(idBean);
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
    
    public ArrayList getReportDataList(String id_seq, String md_type, String sDate, String eDate, String sTime, String eTime, String ordFlag){
    	
    	ArrayList result = new ArrayList();
    	IssueDataBean idBean = null;
        try {
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            sb = new StringBuffer();
            sb.append("	## 보고서 리스트 			\n");
			sb.append("	SELECT A.ID_SEQ				\n");	
			sb.append("	      ,A.MD_SITE_NAME       \n");
			sb.append("	      ,A.ID_TITLE           \n");
			sb.append("	      ,A.ID_URL             \n");
			sb.append("	      ,A.S_SEQ             \n");
			sb.append("	      ,A.MD_SEQ             \n");
			sb.append("	      ,A.MD_PSEQ            \n");
			//sb.append("	      ,A.MD_DATE            \n");
			sb.append("	      ,DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i')   AS  MD_DATE       \n");
			sb.append("	      ,A.MD_SAME_CT         \n");
			//sb.append("	      ,(SELECT COUNT(0) FROM ISSUE_DATA WHERE MD_PSEQ = A.MD_PSEQ AND MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"') AS SAMECNT   \n");
			sb.append("	      ,A.MD_TYPE            \n");
			sb.append("	      ,B.IC_CODE  AS TYPE9          \n");
			sb.append("	      ,(SELECT IC_ORDER FROM ISSUE_CODE WHERE IC_TYPE = 9 AND IC_CODE = B.IC_CODE) AS IC_ORDER          \n");
			sb.append("	      ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE=7 AND IC_CODE = C.IC_CODE)  AS TYPE8          \n");
			sb.append("	  FROM ISSUE_DATA A         \n");
			sb.append("	     , ISSUE_DATA_CODE B    \n");
			sb.append("	     , ISSUE_DATA_CODE C   \n");
			sb.append("	     , ISSUE_DATA_CODE D   \n");
			sb.append("	 WHERE A.ID_SEQ = B.ID_SEQ  \n");
			sb.append("	   AND A.ID_SEQ = C.ID_SEQ   \n");
			sb.append("	   AND A.ID_SEQ = D.ID_SEQ   \n");
			if(!id_seq.equals("")){
				sb.append("	   AND A.ID_SEQ IN ("+id_seq+")	\n");
			}
			if(!md_type.equals("") && md_type.equals("1")){
				sb.append("	   AND D.IC_TYPE = 6 AND D.IC_CODE IN ("+md_type+")  \n");
			}else{
				sb.append("	   AND D.IC_TYPE = 6 AND D.IC_CODE NOT IN (1)  \n");
			}
			sb.append("	   AND B.IC_TYPE = 9 		\n");
			sb.append("	   AND C.IC_TYPE = 7 		\n");
			if(ordFlag.equals("C")){ //온라인
				sb.append("	ORDER BY   IC_ORDER ASC, MD_DATE DESC      \n");
			}else{//자사, 상품
				if(md_type.equals("2")){//개인
					sb.append("	ORDER BY   MD_DATE DESC      \n");
				}else{
					sb.append("	ORDER BY   IC_ORDER ASC, MD_DATE DESC      \n");
				}
			}
    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while( rs.next() ) {
            	idBean = new IssueDataBean();
            	idBean.setId_seq(rs.getString("ID_SEQ"));
            	idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
            	idBean.setId_title(rs.getString("ID_TITLE"));
            	idBean.setId_url(rs.getString("ID_URL"));
            	idBean.setMd_seq(rs.getString("MD_SEQ"));
            	idBean.setMd_pseq(rs.getString("MD_PSEQ"));
            	idBean.setMd_date(rs.getString("MD_DATE"));
            	idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
            	idBean.setMd_type(rs.getString("MD_TYPE"));
            	idBean.setIc_code(rs.getString("TYPE9"));
            	idBean.setIc_influence(rs.getString("TYPE8"));
            	idBean.setS_seq(rs.getString("S_SEQ"));
            	
            	result.add(idBean);
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
    
    
    public ArrayList getChartDataListSet(String type3, String sDate, String eDate){
    	ArrayList arList = new ArrayList();
    	IssueDataBean idBean = null;
    	HashMap dataMap = new HashMap();
        try {
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            sb = new StringBuffer();
			sb.append(" ## 정보 그룹별 성향					\n");
			sb.append(" SELECT                              \n");
			sb.append("     (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 3 AND IC_CODE = AA.IC_CODE) AS CATEGORY	\n");
			sb.append("     ,IFNULL(BB.POS, 0) AS POS				\n");
			sb.append("     ,IFNULL(BB.NEG, 0) AS NEG               \n");
			sb.append("     ,IFNULL(BB.NEU, 0) AS NEU               \n");
			sb.append("     ,IFNULL(BB.TOTAL, 0) AS TOTAL           \n");
			sb.append(" FROM                                        \n");
			sb.append(" (SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 3 AND IC_CODE > 0) AA LEFT OUTER JOIN		\n");
			sb.append(" (SELECT  										\n");
			sb.append("          B.IC_CODE AS TYPE3                     \n");
			sb.append("         ,SUM(IF(C.IC_CODE=1 , 1, 0)) AS POS     \n");
			sb.append("         ,SUM(IF(C.IC_CODE=2 , 1, 0)) AS NEU     \n");
			sb.append("         ,SUM(IF(C.IC_CODE=3 , 1, 0)) AS NEG     \n");
			sb.append("         ,COUNT(C.IC_CODE) AS TOTAL              \n");
			sb.append(" FROM ISSUE_DATA A                               \n");
			sb.append(" , ISSUE_DATA_CODE B                             \n");
			sb.append(" , ISSUE_DATA_CODE C                             \n");
			sb.append(" , ISSUE_DATA_CODE D                             \n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ                       \n");
			sb.append(" AND A.ID_SEQ = C.ID_SEQ                         \n");
			sb.append(" AND A.ID_SEQ = D.ID_SEQ                         \n");
			sb.append(" AND A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"'	\n");
			sb.append(" AND A.S_SEQ NOT IN (2196,2199,3883,5016219) \n");			//포탈 사이트 제외
			sb.append(" AND B.IC_TYPE = "+type3+"							\n");
			sb.append(" AND C.IC_TYPE = 9                               \n");
			sb.append(" AND D.IC_TYPE = 1 AND D.IC_CODE IN (1)             \n");
			sb.append(" GROUP BY TYPE3) BB ON AA.IC_CODE = BB.TYPE3     \n");
    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while( rs.next() ) {
            	dataMap = new HashMap();
            	dataMap.put("CATEGORY", rs.getString("CATEGORY"));
            	
            	dataMap.put("PCNT", rs.getString("POS"));
            	dataMap.put("NAME1", "긍정");
            	
            	dataMap.put("NCNT", rs.getString("NEG"));
            	dataMap.put("NAME2", "부정");
            	
            	dataMap.put("ECNT", rs.getString("NEU"));
            	dataMap.put("NAME3", "중립");
            	arList.add(dataMap);
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
    	return arList;
    }
    
    
    public ArrayList getChartDataListSet2(String type3, String sDate, String eDate, String mode){
    	ArrayList arList = new ArrayList();
    	IssueDataBean idBean = null;
    	HashMap dataMap = new HashMap();
        try {
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            sb = new StringBuffer();
			sb.append(" ##이슈 출처별 정보량 점유율 및 부정율					\n");
			sb.append(" SELECT AA.IC_NAME AS SITE, IFNULL(BB.POS, 0) AS POS , IFNULL(BB.NEU, 0) AS NEU, IFNULL(BB.NEG, 0) AS NEG, IFNULL(BB.TOTAL,0) AS TOTAL \n");
			sb.append(" , IFNULL(ROUND((((BB.POS+BB.NEU+BB.NEG)/BB.TOTAL)*100)),0) AS RATE \n");
			sb.append(" FROM \n");
			sb.append(" (SELECT IC_CODE ,IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE > 0 ORDER BY 1) AA LEFT OUTER JOIN \n"); 
			sb.append(" (SELECT 								\n");
			sb.append("          D.IC_CODE									\n");
			sb.append("         ,SUM(IF(B.IC_CODE=1, 1, 0)) AS POS          \n");
			sb.append("         ,SUM(IF(B.IC_CODE=2, 1, 0)) AS NEU          \n");
			sb.append("         ,SUM(IF(B.IC_CODE=3, 1, 0)) AS NEG          \n");
			sb.append("         ,COUNT(C.IC_CODE) AS TOTAL                  \n");
			sb.append("    FROM ISSUE_DATA A                                \n");
			sb.append("       , ISSUE_DATA_CODE B                           \n");
			sb.append("       , ISSUE_DATA_CODE C                           \n");
			sb.append("       , ISSUE_DATA_CODE D                           \n");
			sb.append("       , ISSUE_DATA_CODE E                           \n");
			sb.append("   WHERE A.ID_SEQ = B.ID_SEQ                         \n");
			sb.append("     AND A.ID_SEQ = C.ID_SEQ                         \n");
			sb.append("     AND A.ID_SEQ = D.ID_SEQ                         \n");
			sb.append("     AND A.ID_SEQ = E.ID_SEQ                         \n");
			sb.append("     AND A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"'	\n");
			sb.append("   	AND A.S_SEQ NOT IN (2196,2199,3883)     			\n");	//포탈 사이트 제외
			sb.append("     AND B.IC_TYPE = 9			\n");
			sb.append("     AND C.IC_TYPE = 11          \n");
			sb.append("     AND D.IC_TYPE = 6           \n");
			sb.append("     AND E.IC_TYPE = 1 AND E.IC_CODE IN (1)           \n");
			sb.append(" GROUP BY D.IC_CODE ) BB                  \n");
			sb.append(" ON AA.IC_CODE = BB. IC_CODE         	 \n");
			//sb.append(" ORDER BY 6 DESC                 \n");
    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while( rs.next() ) {
            	
            	if(mode.equals("chart")){
            		dataMap = new HashMap();
                	dataMap.put("CATEGORY", rs.getString("SITE"));
                	dataMap.put("CNT", rs.getString("TOTAL"));
                	arList.add(dataMap);
            	}else{
            		dataMap = new HashMap();
                	dataMap.put("SITE", rs.getString("SITE"));
                	dataMap.put("POS", rs.getString("POS"));
                	dataMap.put("NEU", rs.getString("NEU"));
                	dataMap.put("NEG", rs.getString("NEG"));
                	dataMap.put("TOTAL", rs.getString("TOTAL"));
                	dataMap.put("RATE", rs.getString("RATE"));
                	arList.add(dataMap);
            	}
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
    	return arList;
    }
    
    
    public ArrayList getChartDataListSet3(String type3, String sDate, String eDate , String mode){
    	ArrayList arList = new ArrayList();
    	IssueDataBean idBean = null;
    	HashMap dataMap = new HashMap();
    	long diff = 0;
        try {
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            sb = new StringBuffer();
            
          //두 날짜 차이 계산
			sb = new StringBuffer();
			sb.append(" SELECT DATEDIFF('"+eDate+" 00:00:00' ,'"+sDate+" 00:00:00' )+1 AS NUM \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				diff = rs.getLong("NUM");
			}
			sb = new StringBuffer();
			sb.append(" ## 주요이슈분석																\n");
			sb.append(" SELECT AA.MD_DATE                                          				\n");
			sb.append(" 	  ,date_format(AA.MD_DATE, '%m-%d') AS mdDate                                          				\n");
			sb.append("       ,IFNULL(BB.POS, 0) AS POS		  \n");
			sb.append("       ,IFNULL(BB.NEU, 0) AS NEU       \n");
			sb.append("       ,IFNULL(BB.NEG, 0) AS NEG       \n");
			sb.append("       ,IFNULL(BB.TOTAL, 0) AS TOTAL   \n");
			sb.append("   FROM                                                           					\n");
			sb.append("         ( SELECT date_format(date_add('"+eDate+"', interval @ROW:=@ROW-1 DAY),'%Y-%m-%d') AS MD_DATE \n");
			sb.append("                  FROM KEYWORD A, (SELECT @ROW:= 1)R 						\n");
			sb.append("           ORDER BY MD_DATE DESC LIMIT "+diff+" ) AA 						\n");
		    sb.append("	LEFT OUTER JOIN                                               				\n");
			sb.append(" 																			\n");
			sb.append(" 	    ( SELECT																   \n"); 
			sb.append("         		DATE_FORMAT(A.MD_DATE, '%Y-%m-%d') AS MD_DATE                      \n");
			sb.append("        			,Round((SUM(IF(B.IC_CODE=1, 1, 0))/COUNT(C.IC_CODE))*100) AS POS   \n");
			sb.append("        			,Round((SUM(IF(B.IC_CODE=2, 1, 0))/COUNT(C.IC_CODE))*100) AS NEU   \n");
			sb.append("        			,Round((SUM(IF(B.IC_CODE=3, 1, 0))/COUNT(C.IC_CODE))*100) AS NEG   \n");
			sb.append("        			,COUNT(C.IC_CODE) AS TOTAL                                  \n");
			sb.append("   		FROM ISSUE_DATA A                                                   \n");
			sb.append("      		, ISSUE_DATA_CODE B                                             \n");
			sb.append("      		, ISSUE_DATA_CODE C                                             \n");
			sb.append("      		, ISSUE_DATA_CODE D                                             \n");
			sb.append("  		WHERE A.ID_SEQ = B.ID_SEQ                                           \n");
			sb.append("   		 AND A.ID_SEQ = C.ID_SEQ                                            \n");
			sb.append("   		 AND A.ID_SEQ = D.ID_SEQ                                            \n");
			sb.append("   		 AND A.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"'     				\n");
			sb.append("   		 AND A.S_SEQ NOT IN (2196,2199,3883)     							\n");	//포탈 사이트 제외
			sb.append("    		 AND B.IC_TYPE = 9                                                  \n");
			sb.append("   		 AND C.IC_TYPE = "+type3+"                                          \n");
			sb.append("   		 AND D.IC_TYPE = 1 AND D.IC_CODE IN (1)                                \n");
			sb.append("		GROUP BY DATE_FORMAT(A.MD_DATE, '%Y-%m-%d')                             \n");
			sb.append("		ORDER BY 1 ASC                                                       	\n");
			sb.append("		) BB                                                      				\n");
			sb.append("  ON  AA.MD_DATE = BB.MD_DATE                               					\n");
			sb.append("  ORDER BY AA.MD_DATE ASC                              						\n");
    		System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            while( rs.next() ) {
            	if(mode.equals("chart")){
            		dataMap = new HashMap();
                	dataMap.put("CATEGORY", rs.getString("MD_DATE"));
                	
                	dataMap.put("CNT", rs.getString("TOTAL"));
                	dataMap.put("NAME", "전체");
                	
                	dataMap.put("NCNT", rs.getString("NEU"));
                	dataMap.put("NAME3", "중립");
                	
                	dataMap.put("ECNT", rs.getString("NEG"));
                	dataMap.put("NAME2", "부정");
                	
                	dataMap.put("PCNT", rs.getString("POS"));
                	dataMap.put("NAME1", "긍정");
                	
                	arList.add(dataMap);
            	}else{
            		dataMap = new HashMap();
                	dataMap.put("MD_DATE", rs.getString("mdDate"));
                	dataMap.put("NEG", rs.getString("NEG"));
                	dataMap.put("TOTAL", rs.getString("TOTAL"));
                	arList.add(dataMap);
            	}
            	
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
    	return arList;
    }
    
    public String[] getFaceBookData(String preDay, String toDay, String tableType){
		String[]  result = new String[4];
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DBconn dbconn = null;
		String table = "";
		if(tableType.equals("fb")){
			table = "STATIC_SOCIAL_FB";
		}else{
			table = "STATIC_SOCIAL_FB2";
		}
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("  ## 페이스북 데이터 조회  \n");
			sb.append(" SELECT																								\n");	 
			sb.append(" 		  FB.SSF_FAN_CNT                                                                              \n");
			sb.append(" 		, FB.SSF_FRD_CNT                                                                            \n");
			sb.append(" 		,(SELECT SSF_FAN_CNT FROM "+table+" WHERE SSF_DATE = '"+toDay+"')-                  \n");
			sb.append(" 	     (SELECT SSF_FAN_CNT FROM "+table+" WHERE SSF_DATE = '"+preDay+"') AS FAN_DIFF       \n");
			sb.append(" 	    ,(SELECT SSF_FRD_CNT FROM "+table+" WHERE SSF_DATE = '"+toDay+"')-                  \n");
			sb.append(" 	     (SELECT SSF_FRD_CNT FROM "+table+" WHERE SSF_DATE = '"+preDay+"') AS FRD_DIFF       \n");
		    sb.append("  FROM "+table+" FB WHERE SSF_DATE = '"+toDay+"'                                             \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				result[0] = rs.getString("SSF_FAN_CNT"); 
				result[1] = rs.getString("SSF_FRD_CNT");
				result[2] = rs.getString("FAN_DIFF");
				result[3] = rs.getString("FRD_DIFF");
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
	
	public String[] getTwitterData(String preDay, String toDay, String tableType, String timeType){
		String[]  result = new String[4];
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DBconn dbconn = null;
		String table = "";
		if(tableType.equals("tw")){
			table = "STATIC_SOCIAL_TWEET";
		}else{
			table = "STATIC_SOCIAL_TWEET2";
		}
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("  ## 트위터 데이터 조회  \n");
			sb.append(" SELECT																								\n");	 
			sb.append(" 		  TW.SST_FOLLOWER                                                                              \n");
			sb.append(" 		, TW.SST_FOLLOWING                                                                            \n");
			sb.append(" 		,(SELECT SST_FOLLOWER FROM "+table+" WHERE SST_DATE = '"+toDay+"' AND SST_TIME = "+timeType+")-                  \n");
			sb.append(" 	     (SELECT SST_FOLLOWER FROM "+table+" WHERE SST_DATE = '"+preDay+"' AND SST_TIME = "+timeType+") AS WER_DIFF       \n");
			sb.append(" 	    ,(SELECT SST_FOLLOWING FROM "+table+" WHERE SST_DATE = '"+toDay+"' AND SST_TIME = "+timeType+")-                  \n");
			sb.append(" 	     (SELECT SST_FOLLOWING FROM "+table+" WHERE SST_DATE = '"+preDay+"' AND SST_TIME = "+timeType+") AS WIN_DIFF       \n");
		    sb.append("  FROM "+table+" TW WHERE SST_DATE = '"+toDay+"'                                             \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				result[0] = rs.getString("SST_FOLLOWER"); 
				result[1] = rs.getString("SST_FOLLOWING");
				result[2] = rs.getString("WER_DIFF");
				result[3] = rs.getString("WIN_DIFF");
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
	
	public IssueDataBean getIssueDataInfo (String md_seq, String baseSeq) {
		
		idBean = new IssueDataBean();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
		
			sb.append("   SELECT 0 AS I_SEQ									\n"); 
			sb.append("        , 0 AS IT_SEQ								\n");
			sb.append("        , A.MD_SEQ 									\n");
			sb.append("        , A.MD_TITLE									\n");
			sb.append("        , A.MD_URL									\n");
			sb.append("        , B.SG_SEQ									\n");
			sb.append("        , A.S_SEQ									\n");
			sb.append("        , A.MD_SITE_NAME								\n");
			sb.append("        , A.MD_MENU_NAME								\n");
			sb.append("        , A.MD_DATE									\n");
			sb.append("        , ''	AS ID_WRITTER							\n");
			sb.append("        , C.MD_CONTENT								\n");
			sb.append("        , NOW() AS ID_REGDATE						\n");
			sb.append("        , 'N' AS ID_MAILYN							\n");
			sb.append("        , 'Y' AS	ID_USEYN							\n");
			sb.append("        , A.MD_SAME_COUNT							\n");
			sb.append("        , A.MD_TYPE									\n");
			sb.append("        , A.L_ALPHA									\n");
			sb.append("        , 'Y' AS ID_REPORTYN							\n");
			sb.append("        , ''	AS MEDIA_INFO							\n");
			sb.append("        , ''	AS F_NEWS								\n");
			sb.append("        , A.MD_PSEQ 									\n");
			sb.append("     FROM META A, SG_S_RELATION B, DATA C			\n");
			sb.append("    WHERE A.S_SEQ = B.S_SEQ							\n");
			sb.append("      AND A.MD_SEQ = C.MD_SEQ						\n");
			sb.append("      AND A.MD_SEQ = "+md_seq+"						\n");
			
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));		
				idBean.setId_title(su.dbString(rs.getString("MD_TITLE")));
				idBean.setId_url(su.dbString(rs.getString("MD_URL")));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setMd_site_menu(rs.getString("MD_MENU_NAME"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(su.dbString(rs.getString("MD_CONTENT")));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_COUNT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setL_alpha(rs.getString("L_ALPHA"));
				idBean.setId_reportyn(rs.getString("ID_REPORTYN"));
				idBean.setMedia_info(rs.getString("MEDIA_INFO"));
				idBean.setF_news(rs.getString("F_NEWS"));
				idBean.setMd_pseq(rs.getString("MD_PSEQ"));
				
				
			}
			
//			
//			sb = new StringBuffer();
//			sb.append("SELECT A.RK_NAME 				\n");
//			sb.append("  FROM RELATION_KEYWORD A		\n");
//			sb.append("     , RELATION_KEYWORD_MAP B	\n");
//			sb.append("     , ISSUE_DATA C				\n");
//			sb.append(" WHERE A.RK_SEQ = B.RK_SEQ		\n");
//			sb.append("   AND B.ID_SEQ = C.ID_SEQ		\n");
//			sb.append("   AND C.MD_SEQ = "+baseSeq+"	\n");
//			
//			System.out.println(sb.toString());
//			pstmt = dbconn.createPStatement(sb.toString());
//			rs = pstmt.executeQuery();
//			String stream = "";
//			while( rs.next() ) {
//				
//				if(stream.equals("")){
//					stream = rs.getString("RK_NAME");
//				}else{
//					stream += "," + rs.getString("RK_NAME");
//				}
//			}
//			idBean.setRelationkeys(stream);
//			
			
//			
//			sb = new StringBuffer();
//			sb.append("SELECT RD_SEQ				\n");
//			sb.append("     , M_SEQ					\n");
//			sb.append("  FROM ISSUE_DATA			\n"); 
//			sb.append(" WHERE MD_SEQ = "+baseSeq+"	\n");
//			
//			System.out.println(sb.toString());
//			pstmt = dbconn.createPStatement(sb.toString());
//			rs = pstmt.executeQuery();
//			
//			while( rs.next() ) {
//				idBean.setRd_seq(rs.getString("RD_SEQ"));
//				idBean.setRd_seq_useYn("Y");
//				idBean.setM_seq(rs.getString("M_SEQ"));
//			}
//			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return idBean;
	}
  
  public String getTypeCode (String baseSeq) {
		
		String result = "";
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
		
			sb.append("SELECT B.IC_TYPE					\n");
			sb.append("     , B.IC_CODE					\n");
			sb.append("  FROM ISSUE_DATA A				\n");
			sb.append("     , ISSUE_DATA_CODE B			\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ		\n");
			sb.append("   AND A.MD_SEQ = "+baseSeq+"	\n");
			
			
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();

			while( rs.next() ) {
				if(result.equals("")){
					result = rs.getString("IC_TYPE") + "," + rs.getString("IC_CODE");
				}else{
					result += "@" + rs.getString("IC_TYPE") + "," + rs.getString("IC_CODE");
				}
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
		return result;
	}
  
  public HashMap<String, String> getSocialFacebook(String curDate, String tableType){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		HashMap result = null;
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT AA.SSF_DATE   	\n");
			sb.append(", AA.SSF_FAN_CNT 	    \n");
			sb.append(", AA.SSF_FRD_CNT			\n");
			sb.append(", AA.SSF_TIME			\n");
			sb.append(", (AA.SSF_FAN_CNT-BB.SSF_FAN_CNT) AS DIF_SSF_FAN_CNT	\n");
			sb.append(", (AA.SSF_FRD_CNT-BB.SSF_FRD_CNT) AS DIF_SSF_FRD_CNT	\n");
			sb.append("FROM	\n");
			sb.append("(	\n");
			sb.append("SELECT SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT, SSF_TIME	\n");
			sb.append("FROM "+tableType+"                                   \n");
			sb.append("WHERE SSF_DATE = '"+curDate+"'                       \n");
			sb.append("LIMIT 1 \n");
			sb.append(") AA ,	\n");
			sb.append("(	\n");
			sb.append("SELECT SSF_DATE, SSF_FAN_CNT, SSF_FRD_CNT, SSF_TIME	\n");
			sb.append("FROM "+tableType+"                                   \n");
			sb.append("WHERE SSF_DATE != '"+curDate+"'                       \n");
			sb.append("ORDER BY SSF_DATE DESC \n");
			sb.append("LIMIT 1 \n");
			sb.append(") BB	\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = new HashMap();
				result.put("DATE", rs.getString("SSF_DATE"));
				result.put("FAN_CNT", rs.getString("SSF_FAN_CNT"));
				result.put("FRD_CNT", rs.getString("SSF_FRD_CNT"));
				result.put("DIF_FAN_CNT", rs.getString("DIF_SSF_FAN_CNT"));
				result.put("DIF_FRD_CNT", rs.getString("DIF_SSF_FRD_CNT"));
				result.put("TIME", rs.getString("SSF_TIME"));
			}
			/*else{
				result.put("DATE", "-");
				result.put("FAN_CNT", "0");
				result.put("FRD_CNT", "0");
				result.put("DIF_FAN_CNT", "0");
				result.put("DIF_FRD_CNT", "0");
				result.put("TIME", "9");
			}*/
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		
		return result;
	
	}
	
	public HashMap<String, String> getSocialTw(String curDate, String tableType){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		HashMap result = null;
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT AA.SST_DATE   	\n");
			sb.append(", AA.SST_FOLLOWER 	    \n");
			sb.append(", AA.SST_FOLLOWING			\n");
			sb.append(", AA.SST_TIME			\n");
			sb.append(", (AA.SST_FOLLOWER-BB.SST_FOLLOWER) AS DIF_SST_FOLLOWER	\n");
			sb.append(", (AA.SST_FOLLOWING-BB.SST_FOLLOWING) AS DIF_SST_FOLLOWING	\n");
			sb.append("FROM	\n");
			sb.append("(	\n");
			sb.append("SELECT SST_DATE, SST_FOLLOWER, SST_FOLLOWING, SST_TIME	\n");
			sb.append("FROM "+tableType+"                                   \n");
			sb.append("WHERE SST_DATE = '"+curDate+"'                       \n");
			sb.append("LIMIT 1 \n");
			sb.append(") AA ,	\n");
			sb.append("(	\n");
			sb.append("SELECT SST_DATE, SST_FOLLOWER, SST_FOLLOWING, SST_TIME	\n");
			sb.append("FROM "+tableType+"                                   \n");
			sb.append("WHERE SST_DATE != '"+curDate+"'                       \n");
			sb.append("ORDER BY SST_DATE DESC \n");
			sb.append("LIMIT 1 \n");
			sb.append(") BB	\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = new HashMap();
				result.put("DATE", rs.getString("SST_DATE"));
				result.put("FAN_CNT", rs.getString("SST_FOLLOWER"));
				result.put("FRD_CNT", rs.getString("SST_FOLLOWING"));
				result.put("DIF_FAN_CNT", rs.getString("DIF_SST_FOLLOWER"));
				result.put("DIF_FRD_CNT", rs.getString("DIF_SST_FOLLOWING"));
				result.put("TIME", rs.getString("SST_TIME"));
			}
			/*else{
				result.put("DATE", "-");
				result.put("FAN_CNT", "0");
				result.put("FRD_CNT", "0");
				result.put("DIF_FAN_CNT", "0");
				result.put("DIF_FRD_CNT", "0");
				result.put("TIME", "9");
			}*/
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		
		return result;
	}
	
	
	public HashMap<String, String> getSocialBlog(String curDate, String tableType){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		HashMap result = null;
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			
			sb.append("SELECT AA.SSB_DATE   	\n");
			sb.append(", AA.SSB_POST_CNT 	    \n");
			sb.append(", AA.SSB_VISITOR_CNT			\n");
			sb.append(", AA.SSB_TIME			\n");
			sb.append(", (AA.SSB_POST_CNT-BB.SSB_POST_CNT) AS DIF_SSB_POST_CNT	\n");
			sb.append(", (AA.SSB_VISITOR_CNT-BB.SSB_VISITOR_CNT) AS DIF_SSB_VISITOR_CNT	\n");
			sb.append("FROM	\n");
			sb.append("(	\n");
			sb.append("SELECT SSB_DATE, SSB_POST_CNT, SSB_VISITOR_CNT, SSB_TIME	\n");
			sb.append("FROM "+tableType+"                                   \n");
			sb.append("WHERE SSB_DATE = '"+curDate+"'                       \n");
			sb.append("LIMIT 1 \n");
			sb.append(") AA ,	\n");
			sb.append("(	\n");
			sb.append("SELECT SSB_DATE, SSB_POST_CNT, SSB_VISITOR_CNT, SSB_TIME	\n");
			sb.append("FROM "+tableType+"                                   \n");
			sb.append("WHERE SSB_DATE != '"+curDate+"'                       \n");
			sb.append("ORDER BY SSB_DATE DESC \n");
			sb.append("LIMIT 1 \n");
			sb.append(") BB	\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = new HashMap();
				result.put("DATE", rs.getString("SSB_DATE"));
				result.put("FAN_CNT", rs.getString("SSB_POST_CNT"));
				result.put("FRD_CNT", rs.getString("SSB_VISITOR_CNT"));
				result.put("DIF_FAN_CNT", rs.getString("DIF_SSB_POST_CNT"));
				result.put("DIF_FRD_CNT", rs.getString("DIF_SSB_VISITOR_CNT"));
				result.put("TIME", rs.getString("SSB_TIME"));
			}
			/*else{
				result.put("DATE", "-");
				result.put("FAN_CNT", "0");
				result.put("FRD_CNT", "0");
				result.put("DIF_FAN_CNT", "0");
				result.put("DIF_FRD_CNT", "0");
				result.put("TIME", "9");
			}*/
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		}
		
		return result;
	}
	
	public ArrayList getRelationKey() {
	    return getRelationKey("");
	  }
	
	
	public ArrayList getRelationKey(String id_seq) {

	    ArrayList arrResult = new ArrayList();

	    try {
	      dbconn = new DBconn();
	      dbconn.getDBCPConnection();

	      sb = new StringBuffer();
	      if(!id_seq.equals("")){
	        sb.append(" SELECT B.RK_NAME \n");
	        sb.append(" FROM RELATION_KEYWORD_MAP A, RELATION_KEYWORD_R B \n");
	        sb.append(" WHERE A.RK_SEQ = B.RK_SEQ \n");     
	        sb.append(" AND A.ID_SEQ = "+id_seq+"   \n");
	        sb.append(" GROUP BY B.RK_NAME    \n");
	      }else{
	        sb.append(" SELECT B.RK_NAME \n");
	        sb.append(" FROM RELATION_KEYWORD_R B \n");               
	      }

	      System.out.println("getRelationKey :\n" + sb.toString());
	      pstmt = dbconn.createPStatement(sb.toString());
	      rs = pstmt.executeQuery();

	      while( rs.next() ) {
	        arrResult.add(rs.getString("RK_NAME"));
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
	
	
	 public HashMap<String, String> getSocialData(String curDate, String type){
			DBconn dbconn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuffer sb = null;
			
			HashMap result = null;
			
			try{
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				
				sb = new StringBuffer();
				sb.append("SELECT AA.SM_DATE   	\n");
				sb.append(", AA.SM_TIME			\n");
				sb.append(", AA.SM_CNT1 	    \n");
				sb.append(", AA.SM_CNT2			\n");
				sb.append(", AA.SM_CNT3			\n");
				sb.append(", (AA.SM_CNT1-BB.SM_CNT1) AS DIF_SM_CNT1	\n");
				sb.append(", (AA.SM_CNT2-BB.SM_CNT2) AS DIF_SM_CNT2	\n");
				sb.append(", (AA.SM_CNT3-BB.SM_CNT3) AS DIF_SM_CNT3	\n");
				sb.append("FROM	\n");
				sb.append("(	\n");
				sb.append("SELECT SM_DATE, SM_TIME, SM_CNT1, SM_CNT2, SM_CNT3	\n");
				sb.append("FROM SOCIAL_MANAGER                                   \n");
				sb.append("WHERE SM_DATE = '"+curDate+"' AND SM_TYPE = '"+type+"'       \n");
				sb.append("LIMIT 1 \n");
				sb.append(") AA ,	\n");
				sb.append("(	\n");
				sb.append("SELECT SM_DATE, SM_TIME, SM_CNT1, SM_CNT2, SM_CNT3	\n");
				sb.append("FROM SOCIAL_MANAGER                                   \n");
				sb.append("WHERE SM_DATE != '"+curDate+"' AND SM_TYPE = '"+type+"'    \n");
				sb.append("ORDER BY SM_DATE DESC \n");
				sb.append("LIMIT 1 \n");
				sb.append(") BB	\n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if(rs.next()){
					result = new HashMap();
					result.put("DATE", rs.getString("SM_DATE"));
					result.put("TIME", rs.getString("SM_TIME"));
					result.put("SM_CNT1", rs.getString("SM_CNT1"));
					result.put("SM_CNT2", rs.getString("SM_CNT2"));
					result.put("SM_CNT3", rs.getString("SM_CNT3"));
					result.put("DIF_SM_CNT1", rs.getString("DIF_SM_CNT1"));
					result.put("DIF_SM_CNT2", rs.getString("DIF_SM_CNT2"));
					result.put("DIF_SM_CNT3", rs.getString("DIF_SM_CNT3"));
				}
				else{
					result = new HashMap();
					result.put("DATE", "-");
					result.put("SM_CNT1", "0");
					result.put("SM_CNT2", "0");
					result.put("SM_CNT3", "0");
					result.put("DIF_SM_CNT1", "0");
					result.put("DIF_SM_CNT2", "0");
					result.put("DIF_SM_CNT3", "0");
					result.put("TIME", "-");
				}
				
			}catch(SQLException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sb = null;
				try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
				try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
				try{if(rs != null){rs.close();}}catch(Exception e){}
			}
			
			return result;
		
		}
	 
	 public ArrayList getPressList(){
		ArrayList result = new ArrayList();
		Map map = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		 
		 try{
				
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();
				sb.append("SELECT P_SEQ, P_TITLE FROM PRESS_RELEASE  \n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				while(rs.next()){
					map = new HashMap();
					map.put("P_SEQ", rs.getString("P_SEQ"));
					map.put("P_TITLE", rs.getString("P_TITLE"));
					result.add(map);
				}
		 
		 }catch(SQLException e){
			e.printStackTrace();
		 }catch(Exception e){
			e.printStackTrace();
		 }finally{
			sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
		 }	
		 return result;
	 }
	 
	 public String getPressIssueData(String id_Seq){
		String result = "";
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		 try{
			 dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();
				sb.append("SELECT P_SEQ  FROM PRESS_ISSUE_DATA WHERE ID_SEQ = "+id_Seq+"  \n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				if(rs.next()){
					 result = rs.getString("P_SEQ");
				}
			 
		 }catch(SQLException e1){
			 e1.printStackTrace();
	 	 }catch(Exception e2){
	 		e2.printStackTrace();
	 	 }finally{
	 		sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
	 	 }
		 
		 return result;
	 }
	 
	 
	 public List getMember(String mg_seq){
		 List result = new ArrayList();
		 String [] tmp = null;
			DBconn dbconn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuffer sb = null;
			
			 try{
				 dbconn = new DBconn();
					dbconn.getDBCPConnection();
					sb = new StringBuffer();
					sb.append("SELECT M_SEQ , MG_SEQ, M_ID, M_NAME FROM MEMBER WHERE MG_SEQ IN ("+mg_seq+") ORDER BY M_NAME ASC  \n");
					System.out.println(sb.toString());
					pstmt = dbconn.createPStatement(sb.toString());
					rs = pstmt.executeQuery();
					while(rs.next()){
						tmp = new String[2];
						tmp[0] = rs.getString("M_SEQ");
						tmp[1] = rs.getString("M_NAME");
						result.add(tmp);
					}
				 
			 }catch(SQLException e1){
				 e1.printStackTrace();
		 	 }catch(Exception e2){
		 		e2.printStackTrace();
		 	 }finally{
		 		sb = null;
				try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
				try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
				try{if(rs != null){rs.close();}}catch(Exception e){}
		 	 }
			 
			 return result;
		 
	 }
	 
	 
	 public String getIssueData_MdSeqs(String id_seqs){
		 	String result ="";
		 	DBconn dbconn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuffer sb = null;
			
			 try{
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				String mdSeqs = "";
				sb = new StringBuffer();
				sb.append("SELECT a.MD_PSEQ                         \n");
				sb.append("FROM ISSUE_DATA a                        \n");
				sb.append("WHERE a.ID_SEQ IN ("+id_seqs+")         \n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				while(rs.next()){
					if(!"".equals(mdSeqs)){
						mdSeqs += ","+rs.getString("MD_PSEQ");
					}else{
						mdSeqs = rs.getString("MD_PSEQ");
					}
				}
				
				sb = new StringBuffer();
				sb.append("SELECT DISTINCT(AA.ID_SEQ) AS ID_SEQ		\n");
				sb.append("FROM ISSUE_DATA AA                       \n");
				sb.append("WHERE AA.MD_PSEQ IN ( "+mdSeqs+" )       \n");
				sb.append("ORDER BY 1 DESC               	        \n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				while(rs.next()){
					if(!"".equals(result)){
						result += ","+rs.getString("ID_SEQ");
					}else{
						result = rs.getString("ID_SEQ");
					}
				}
				 
			 }catch(SQLException e1){
				 e1.printStackTrace();
		 	 }catch(Exception e2){
		 		e2.printStackTrace();
		 	 }finally{
		 		sb = null;
				try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
				try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
				try{if(rs != null){rs.close();}}catch(Exception e){}
		 	 }
		 
		 return result;
	 }
	 
	 
	 public boolean issueDataMod(String id_seqs , String chk_typeCode, String SS_M_NO){
		 
		 boolean result = false;
		 DBconn dbconn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 StringBuffer sb = null;
		 
		 String idSeqs[] = id_seqs.split(",");
		 String chkTypecodes[] = chk_typeCode.split("@");
			
		 try{
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				if(idSeqs.length > 0){
					///////////////////////////////////////수정할 타입 삭제
					for(int i=0; i < idSeqs.length; i++){
						
						sb = new StringBuffer();
						sb.append("UPDATE ISSUE_DATA SET ID_WRITTER = '"+SS_M_NO+"'  WHERE ID_SEQ = "+idSeqs[i]+"  \n");
						System.out.println(sb.toString());
						pstmt = dbconn.createPStatement(sb.toString());
						if(pstmt.executeUpdate() > 0 ){result = true;}
					
						/*sb = new StringBuffer();
						sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ = "+idSeqs[i]+" AND IC_TYPE NOT IN (6)  \n");
						System.out.println(sb.toString());
						pstmt = dbconn.createPStatement(sb.toString());*/
						
						///////////////////////////////////////수정할 타입 입력
						if(chkTypecodes.length > 0){
							for(int k=0; k < chkTypecodes.length; k++){
								String type = chkTypecodes[k].split(",")[0];
								String code = chkTypecodes[k].split(",")[1];
								
								sb = new StringBuffer();
								sb.append("DELETE FROM ISSUE_DATA_CODE WHERE ID_SEQ = "+idSeqs[i]+" AND IC_TYPE = "+type+"  \n");
								System.out.println(sb.toString());
								pstmt = dbconn.createPStatement(sb.toString());
								pstmt.executeUpdate();
								
								sb = new StringBuffer();
								sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE)	\n");
								sb.append("VALUES ("+idSeqs[i]+", "+type+", "+code+");               \n");
								System.out.println(sb.toString());
								pstmt = dbconn.createPStatement(sb.toString());
								if(pstmt.executeUpdate() > 0 ){result = true;}
																
								
							}
						}
						////////////////////////////////////////수정할 타입 입력
					}
				}
				 
			 }catch(SQLException e1){
				 e1.printStackTrace();
		 	 }catch(Exception e2){
		 		e2.printStackTrace();
		 	 }finally{
		 		sb = null;
				try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
				try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
				try{if(rs != null){rs.close();}}catch(Exception e){}
		 	 }
		 
		 return result;
	 }
	 
	 
	 
	 public boolean MultiUpdate(String id_seqs , String chk_type, String chk_typeCode, String p_seq){
		 
		 boolean result = false;
		 DBconn dbconn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 StringBuffer sb = null;
		 
		 String idSeqs[] = id_seqs.split(",");
		 String chkTypes[] = chk_type.split(",");
		 String chkTypecodes[] = chk_typeCode.split("@");
			
		 try{
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				if(idSeqs.length > 0){
					for(int i=0; i < idSeqs.length; i++){
						
						///////////////////////////////////////수정할 타입 삭제
						if(chkTypes.length > 0){
							for(int j=0; j < chkTypes.length; j++){
								sb = new StringBuffer();
								sb.append("DELETE FROM ISSUE_DATA_CODE WHERE IC_TYPE = "+chkTypes[j]+" AND ID_SEQ = "+idSeqs[i]+"  \n");
								System.out.println(sb.toString());
								pstmt = dbconn.createPStatement(sb.toString());
								if(pstmt.executeUpdate() > 0 ){result = true;}
							}
						}
						///////////////////////////////////////수정할 타입 삭제
						
						
						///////////////////////////////////////수정할 타입 입력
						if(chkTypecodes.length > 0){
							for(int k=0; k < chkTypecodes.length; k++){
								String type = chkTypecodes[k].split(",")[0];
								String code = chkTypecodes[k].split(",")[1];
								sb = new StringBuffer();
								sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE)	\n");
								sb.append("VALUES ("+idSeqs[i]+", "+type+", "+code+");               \n");
								System.out.println(sb.toString());
								pstmt = dbconn.createPStatement(sb.toString());
								if(pstmt.executeUpdate() > 0 ){result = true;}
							}
						}
						////////////////////////////////////////수정할 타입 입력
						
						
						if(!"".equals(p_seq)){
							//////////////////////////////////////보도자료 삭제
							sb = new StringBuffer();
							sb.append("DELETE FROM PRESS_ISSUE_DATA WHERE ID_SEQ = "+idSeqs[i]+" AND ID_SEQ = "+idSeqs[i]+" \n");
							System.out.println(sb.toString());
							pstmt = dbconn.createPStatement(sb.toString());
							pstmt.executeUpdate();
							
							//////////////////////////////////////보도자료 입력
							sb = new StringBuffer();
							sb.append("INSERT INTO PRESS_ISSUE_DATA (ID_SEQ, P_SEQ)       \n");
							sb.append("VALUES ("+idSeqs[i]+", "+p_seq+");                 \n");
							System.out.println(sb.toString());
							pstmt = dbconn.createPStatement(sb.toString());
							if(pstmt.executeUpdate() > 0 ){result = true;}
						}
					}
				}
				 
			 }catch(SQLException e1){
				 e1.printStackTrace();
		 	 }catch(Exception e2){
		 		e2.printStackTrace();
		 	 }finally{
		 		sb = null;
				try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
				try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
				try{if(rs != null){rs.close();}}catch(Exception e){}
		 	 }
		 
		 return result;
	 }
    
	 
	 public List issueExcelDownLoad(String sDate,String  eDate,String  ir_stime,String  ir_etime,String  typeCode,String  searchType,String  searchKey,String  m_seq){
		 List result = new ArrayList();
		 
		 DBconn dbconn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 StringBuffer sb = null;
		 
		 String idSeqs ="";
		 String arryTypeCode[] = typeCode.split("@");
		 
		 
		 try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			//sb = new StringBuffer();
			//sb.append("	SELECT						\n");
			//sb.append("	DISTINCT(ID.ID_SEQ) AS ID_SEQ 					\n");
			//sb.append("	FROM ISSUE_DATA ID 			\n");
			//if(arryTypeCode.length > 0 && !"".equals(arryTypeCode[0]) ){
			//	for(int i=0;i<arryTypeCode.length;i++){
			//		sb.append("	 ,ISSUE_DATA_CODE IC"+i+"		\n");
			//	}
			//}
			//sb.append("	WHERE ID.MD_DATE BETWEEN '"+sDate+" "+ir_stime+":00:00' AND '"+eDate+" "+ir_etime+":59:59'       \n");
			//if(arryTypeCode.length > 0){
			//	for(int i=0;i<arryTypeCode.length;i++){
			//		String typeAndCode[] = arryTypeCode[i].split(",");
			//		if( typeAndCode.length == 1 && !"".equals(typeAndCode[0]) ){
			//			sb.append(" AND IC"+i+".IC_TYPE = "+typeAndCode[0]+"		\n");
			//		}else if(typeAndCode.length == 2){
			//			sb.append(" AND IC"+i+".IC_TYPE = "+typeAndCode[0]+"		\n");
			//			sb.append(" AND IC"+i+".IC_CODE = "+typeAndCode[1]+"		\n");
			//		}
			//	}
			//}
			//if(!"".equals(searchKey)){
			//	if("1".equals(searchType)){
			//		sb.append(" AND ID.ID_TITLE LIKE '%"+searchKey+"%' \n");
			//	}else if("2".equals(searchType)){
			//		sb.append(" AND CONCAT(ID.ID_TITLE, ID.ID_CONTENT) LIKE '%"+searchKey+"%' \n");
			//	}
			//}
			//System.out.println(sb.toString());
			//pstmt = dbconn.createPStatement(sb.toString());
			//rs = pstmt.executeQuery();
			//while(rs.next()){
			//	if("".equals(idSeqs)){
			//		idSeqs  = rs.getString("ID_SEQ");
			//	}else{
			//		idSeqs  += ","+rs.getString("ID_SEQ");	
			//	}
			//}
			
			sb = new StringBuffer();
			sb.append(" ##엑셀 다운로드 \n");
			sb.append("	SELECT																			\n");
			sb.append("	         ID.ID_SEQ                                                              \n");
			sb.append("	        ,ID.MD_DATE                                                             \n");
			sb.append("	        ,ID.ID_TITLE                                                            \n");
			sb.append("	        ,ID.MD_SITE_MENU                                                        \n");
			sb.append("	        ,ID.ID_URL                                                              \n");
			sb.append("	        ,ID.MD_SITE_NAME                                                        \n");
			sb.append("	        ,FN_GET_ISSUE_CODE_NAME(ID.ID_SEQ, 9) AS CODE9                          \n");
			sb.append("	        ,FN_GET_ISSUE_CODE_NAME(ID.ID_SEQ, 6) AS CODE6                          \n");
			sb.append("	        ,IFNULL(ID.USER_ID, '') AS USER_ID                                      \n");
			sb.append("	        ,IFNULL(ID.USER_NICK, '') AS USER_NICK                                  \n");
			sb.append("	        ,IFNULL(ID.BLOG_VISIT_COUNT, '') AS BLOG_VISIT_COUNT                    \n");
			sb.append("	        ,IFNULL(ID.CAFE_NAME, '') AS CAFE_NAME                                  \n");
			sb.append("	        ,IFNULL(ID.CAFE_MEMBER_COUNT, '') AS CAFE_MEMBER_COUNT                  \n");
			sb.append("	        ,FN_GET_ISSUE_CODE_NAME(ID.ID_SEQ, 10) AS CODE10                        \n");
			sb.append("	        ,FN_PRESS_TITLE(ID.ID_SEQ) AS P_NAME                                    \n");
			sb.append("	 FROM ISSUE_DATA ID                                                             \n");
			if(arryTypeCode.length > 0 && !"".equals(arryTypeCode[0]) ){
				for(int i=0;i<arryTypeCode.length;i++){
					sb.append("	 ,ISSUE_DATA_CODE IC"+i+"		\n");
				}
			}
			sb.append("	WHERE ID.MD_DATE BETWEEN '"+sDate+" "+ir_stime+":00:00' AND '"+eDate+" "+ir_etime+":59:59'       \n");
			if(arryTypeCode.length > 0){
				for(int i=0;i<arryTypeCode.length;i++){
					String typeAndCode[] = arryTypeCode[i].split(",");
					if( typeAndCode.length == 1 && !"".equals(typeAndCode[0]) ){
						sb.append(" AND IC"+i+".IC_TYPE = "+typeAndCode[0]+"		\n");
					}else if(typeAndCode.length == 2){
						sb.append(" AND IC"+i+".IC_TYPE = "+typeAndCode[0]+"		\n");
						sb.append(" AND IC"+i+".IC_CODE = "+typeAndCode[1]+"		\n");
					}
				}
			}
			if(!"".equals(searchKey)){
				if("1".equals(searchType)){
					sb.append(" AND ID.ID_TITLE LIKE '%"+searchKey+"%' \n");
				}else if("2".equals(searchType)){
					sb.append(" AND CONCAT(ID.ID_TITLE, ID.ID_CONTENT) LIKE '%"+searchKey+"%' \n");
				}
			}
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			Map map = null;
			while(rs.next()){
				map = new HashMap();
				map.put("ID_SEQ"			, rs.getString("ID_SEQ"));
				map.put("MD_DATE"			, rs.getString("MD_DATE"));
				map.put("ID_TITLE"			, rs.getString("ID_TITLE"));
				map.put("MD_SITE_MENU"		, rs.getString("MD_SITE_MENU"));
				map.put("ID_URL"			, rs.getString("ID_URL"));
				map.put("MD_SITE_NAME"		, rs.getString("MD_SITE_NAME"));
				map.put("CODE9"				, rs.getString("CODE9"));
				map.put("CODE6"				, rs.getString("CODE6"));
				map.put("USER_ID"			, rs.getString("USER_ID"));
				map.put("USER_NICK"			, rs.getString("USER_NICK"));
				map.put("BLOG_VISIT_COUNT"	, rs.getString("BLOG_VISIT_COUNT"));
				map.put("CAFE_NAME"			, rs.getString("CAFE_NAME"));
				map.put("CAFE_MEMBER_COUNT"	, rs.getString("CAFE_MEMBER_COUNT"));
				map.put("CODE10"			, rs.getString("CODE10"));
				map.put("P_NAME"			, rs.getString("P_NAME"));
				result.add(map);
			}
			
		 }catch(SQLException e1){
			 e1.printStackTrace();
	 	 }catch(Exception e2){
	 		e2.printStackTrace();
	 	 }finally{
	 		sb = null;
			try{if(dbconn != null){dbconn.close();}}catch(Exception e){}
			try{if(pstmt != null){pstmt.close();}}catch(Exception e){}
			try{if(rs != null){rs.close();}}catch(Exception e){}
	 	 }
		 
		 return result;
	 }
	 
	 public ArrayList replyAnalysisList(String sDate, String eDate, int nowPage, String searchKey, String pressYn, String pseqVal, String r_trend, String mode){
		 ArrayList result = new ArrayList();
		 DBconn dbconn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 Map map = null;
		 String r_docid = "";
		 int startNum = (nowPage-1)*100;
		 try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			/*sb = new StringBuffer();			
			sb.append("		SELECT COUNT(I_SEQ) AS CNT 								\n");
			sb.append("		FROM ISSUE                                                      \n");
			sb.append("		WHERE 1=1														\n");
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueCnt  = rs.getInt("CNT");            	
            }*/
			
			sb =  new StringBuffer();
			sb.append(" SELECT DISTINCT(R_DOCID) FROM REPLY_ANALYSIS WHERE R_SDATE BETWEEN '"+sDate+"' AND '"+eDate+"' \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				r_docid += rs.getString("R_DOCID") + ",";
			}
			
			r_docid = r_docid.substring(0, r_docid.length()-1);
			
			sb =  new StringBuffer();
			sb.append("SELECT COUNT(0) AS CNT	\n");
			sb.append("FROM	\n");
			sb.append("(	\n");
			sb.append("SELECT DISTINCT(AA.D_SEQ), B.ID_SEQ	\n");
			sb.append("FROM	\n");
			sb.append("(	\n");
			sb.append("SELECT DISTINCT(M.MD_SEQ) AS MD_SEQ, M.D_SEQ	\n");
			sb.append("FROM META M	\n");
			sb.append("WHERE M.D_SEQ IN ("+r_docid+")	\n");
			sb.append(") AA, ISSUE_DATA B	\n");
			sb.append("WHERE AA.MD_SEQ = B.MD_SEQ	\n");
			sb.append(")AAA LEFT OUTER JOIN PRESS_ISSUE_DATA BBB ON AAA.ID_SEQ = BBB.ID_SEQ	\n");
			sb.append("LEFT OUTER JOIN REPLY_ANALYSIS RAT ON AAA.D_SEQ = RAT.r_docid	\n");
			sb.append("WHERE 1=1	\n");
			if(!searchKey.equals("")){
				sb.append("AND RAT.P_TITLE LIKE '%"+searchKey+"%'	\n");
			}
			if(pressYn.equals("Y")){
				sb.append("AND BBB.P_SEQ IS NOT NULL	\n");
			}else if(pressYn.equals("N")){
				sb.append("AND BBB.P_SEQ IS NULL	\n");
			}
			if(!pseqVal.equals("")){
				sb.append("AND BBB.P_SEQ = "+pseqVal+"	\n");
			}
			if(!r_trend.equals("")){
				sb.append("AND RAT.R_TREND = "+r_trend+"	\n");
			}
			rs = stmt.executeQuery(sb.toString());
            if ( rs.next() ) {
            	totalIssueCnt  = rs.getInt("CNT");            	
            }
			
			sb =  new StringBuffer();
			sb.append("SELECT RAT.R_SEQ	\n");
			sb.append("        , RAT.R_DOCID	\n");
			sb.append("        , RAT.R_SDATE	\n");
			sb.append("        , RAT.P_TITLE AS R_TITLE	\n");
			sb.append("        , RAT.P_SITENAME	\n");
			sb.append("        , RAT.P_URL	\n");
			sb.append("        , RAT.R_CONTENT	\n");
			sb.append("        , RAT.R_TREND	\n");
			sb.append("        , RAT.M_TREND	\n");
			sb.append("        , RAT.R_TRENDWORD_POS	\n");
			sb.append("        , RAT.R_TRENDWORD_NEG	\n");
			sb.append("        , RAT.R_RELATION_WORD	\n");
			sb.append("        , IFNULL((SELECT P_TITLE FROM PRESS_RELEASE WHERE P_SEQ = BBB.P_SEQ) , '') AS P_TITLE	\n");
			sb.append("FROM	\n");
			sb.append("(	\n");
			sb.append("SELECT DISTINCT(AA.D_SEQ), B.ID_SEQ	\n");
			sb.append("FROM	\n");
			sb.append("(	\n");
			sb.append("SELECT DISTINCT(M.MD_SEQ) AS MD_SEQ, M.D_SEQ	\n");
			sb.append("FROM META M	\n");
			sb.append("WHERE M.D_SEQ IN ("+r_docid+")	\n");
			sb.append(") AA, ISSUE_DATA B	\n");
			sb.append("WHERE AA.MD_SEQ = B.MD_SEQ	\n");
			sb.append(")AAA LEFT OUTER JOIN PRESS_ISSUE_DATA BBB ON AAA.ID_SEQ = BBB.ID_SEQ	\n");
			sb.append("LEFT OUTER JOIN REPLY_ANALYSIS RAT ON AAA.D_SEQ = RAT.r_docid	\n");
			sb.append("WHERE 1=1	\n");
			if(!searchKey.equals("")){
				sb.append("AND RAT.P_TITLE LIKE '%"+searchKey+"%'	\n");
			}
			if(pressYn.equals("Y")){
				sb.append("AND BBB.P_SEQ IS NOT NULL	\n");
			}else if(pressYn.equals("N")){
				sb.append("AND BBB.P_SEQ IS NULL	\n");
			}
			if(!pseqVal.equals("")){
				sb.append("AND BBB.P_SEQ = "+pseqVal+"	\n");
			}
			if(!r_trend.equals("")){
				sb.append("AND RAT.R_TREND = "+r_trend+"	\n");
			}
			sb.append("ORDER BY RAT.R_DOCID DESC, RAT.R_SDATE DESC	\n");
			if(!mode.equals("excel")){
				sb.append("LIMIT "+startNum+",100	\n");
			}
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				map = new HashMap();
				map.put("R_SEQ", rs.getString("R_SEQ"));
				map.put("R_DOCID", rs.getString("R_DOCID"));
				map.put("R_SDATE", rs.getString("R_SDATE").substring(0, 10));
				map.put("R_TITLE", rs.getString("R_TITLE"));
				map.put("P_SITENAME", rs.getString("P_SITENAME"));
				map.put("P_URL", rs.getString("P_URL"));
				map.put("R_CONTENT", rs.getString("R_CONTENT"));
				map.put("R_TREND", rs.getString("R_TREND"));
				map.put("M_TREND", rs.getString("M_TREND"));
				map.put("P_TITLE", rs.getString("P_TITLE"));
				map.put("R_TRENDWORD_POS", rs.getString("R_TRENDWORD_POS"));
				map.put("R_TRENDWORD_NEG", rs.getString("R_TRENDWORD_NEG"));
				map.put("R_RELATION_WORD", rs.getString("R_RELATION_WORD"));
				result.add(map);
			}
			
			
		 } catch (SQLException ex ) {
			 ex.printStackTrace();
         } catch (Exception ex ) {
        	 ex.printStackTrace();
         } finally {
        	 sb = null;
        	 try { if( dbconn  != null) dbconn.close(); } catch(SQLException ex) {}
        	 try { if( pstmt  != null) pstmt.close(); } catch(SQLException ex) {}
        	 try { if( rs != null) rs.close(); } catch(SQLException ex) {}
         }
		 return result;
	 }
	 
	 public boolean updateTrend(String r_seq, String r_trend){
		 boolean result = false;
		 try{
			 dbconn = new DBconn();
			 dbconn.getDBCPConnection();
			 stmt = dbconn.createStatement();
			 
			 sb = new StringBuffer();
			 sb.append("UPDATE REPLY_ANALYSIS		\n");
			 sb.append("SET M_TREND = "+r_trend+"		\n");
			 sb.append("WHERE R_SEQ IN ( "+r_seq+" )	\n");
			 
			 System.out.println(sb.toString());
			 if(stmt.executeUpdate(sb.toString()) > 0) result = true;
			 
		 }catch(SQLException ex){
			 Log.writeExpt(ex, sb.toString());
		 }catch(Exception ex){
			 Log.writeExpt(ex);
		 }finally{
			 sb = null;
			 if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			 if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			 if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		 }
		 return result;
	 }
	 
	 
	 public IssueDataBean getIssueData(String md_seq){
		 IssueDataBean idBean = new IssueDataBean();
		 IssueDataCodeBean idcBean = new IssueDataCodeBean();
		 try{
			 dbconn = new DBconn();
			 dbconn.getDBCPConnection();
			 stmt = dbconn.createStatement();
			 ResultSet rs = null;
			 
			 sb = new StringBuffer();
			 sb.append("SELECT ID_SEQ       \n");
			 sb.append("	 , I_SEQ             \n");
			 sb.append("	 , IT_SEQ            \n");
			 sb.append("	 , MD_SEQ            \n");
			 sb.append("	 , ID_TITLE          \n");
			 sb.append("	 , ID_URL            \n");
			 sb.append("	 , SG_SEQ            \n");
			 sb.append("	 , S_SEQ             \n");
			 sb.append("	 , MD_SITE_NAME      \n");
			 sb.append("	 , MD_SITE_MENU      \n");
			 sb.append("	 , MD_DATE           \n");
			 sb.append("	 , ID_WRITTER        \n");
			 sb.append("	 , ID_CONTENT        \n");
			 sb.append("	 , ID_REGDATE        \n");
			 sb.append("	 , ID_MAILYN         \n");
			 sb.append("	 , ID_USEYN          \n");
			 sb.append("	 , M_SEQ             \n");
			 sb.append("	 , MD_SAME_CT        \n");
			 sb.append("	 , MD_TYPE           \n");
			 sb.append("	 , MD_PSEQ           \n");
			 sb.append("	 , K_XP              \n");
			 sb.append("	 , K_YP              \n");
			 sb.append("	 , K_ZP              \n");
			 sb.append("	 , H_SEQ             \n");
			 sb.append("	 , ID_MOBILE         \n");
			 sb.append("	 , USER_ID           \n");
			 sb.append("	 , USER_NICK         \n");
			 sb.append("	 , BLOG_VISIT_COUNT  \n");
			 sb.append("	 , CAFE_NAME         \n");
			 sb.append("	 , CAFE_MEMBER_COUNT \n");
			 sb.append("	 , GARBAGE           \n");
			 sb.append("FROM ISSUE_DATA    \n");
			 sb.append("WHERE MD_SEQ IN ( "+md_seq+" )	\n");
			 System.out.println(sb.toString());
			 rs = stmt.executeQuery(sb.toString());
			 while(rs.next()){
				idBean = new IssueDataBean();
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));
				idBean.setId_writter(rs.getString("ID_WRITTER"));
				idBean.setId_content(rs.getString("ID_CONTENT"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setMd_seq(rs.getString("MD_SEQ"));
				idBean.setI_seq(rs.getString("I_SEQ"));
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setMd_date(rs.getString("MD_DATE"));
				idBean.setMd_site_menu(rs.getString("MD_SITE_MENU"));
				idBean.setMd_site_name(rs.getString("MD_SITE_NAME"));
				idBean.setS_seq(rs.getString("S_SEQ"));
				idBean.setSg_seq(rs.getString("SG_SEQ"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
				idBean.setId_useyn(rs.getString("ID_USEYN"));
				idBean.setM_seq(rs.getString("M_SEQ"));
				idBean.setMd_same_ct(rs.getString("MD_SAME_CT"));
				idBean.setMd_type(rs.getString("MD_TYPE"));
				idBean.setUser_id(rs.getString("USER_ID"));
				idBean.setUser_nick(rs.getString("USER_NICK"));
				idBean.setCafe_name(rs.getString("CAFE_NAME"));
				idBean.setCafe_member_count(rs.getString("CAFE_MEMBER_COUNT"));
				idBean.setBlog_visit_count(rs.getString("BLOG_VISIT_COUNT"));
				idBean.setId_mailyn(rs.getString("ID_MAILYN"));
			 }
			 
		 }catch(SQLException ex){
			 Log.writeExpt(ex, sb.toString());
		 }catch(Exception ex){
			 Log.writeExpt(ex);
		 }finally{
			 sb = null;
			 if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			 if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			 if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		 }
		 
		 return idBean;
	 }
	 
	 public List getIssueDataCode(String id_seq){
		 List result = new ArrayList();
		 try{
			 dbconn = new DBconn();
			 dbconn.getDBCPConnection();
			 stmt = dbconn.createStatement();
			 ResultSet rs = null;
			 Map map = null; 
			 sb = new StringBuffer();
			 sb.append("SELECT		\n");
			 sb.append("   A.ID_SEQ			\n");
			 sb.append("   ,A.IC_TYPE      \n");
			 sb.append("   ,A.IC_CODE         \n");
			 sb.append("   ,(SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = A.IC_TYPE AND IC_CODE = A.IC_CODE) AS IC_NAME         \n");
			 sb.append("FROM ISSUE_DATA_CODE A		\n");
			 sb.append("WHERE A.ID_SEQ IN ( "+id_seq+" )	\n");
			 System.out.println(sb.toString());
			 rs = stmt.executeQuery(sb.toString());
			 while(rs.next()){
				 map = new HashMap();
				 map.put("ID_SEQ", rs.getInt("ID_SEQ"));
				 map.put("IC_TYPE", rs.getInt("IC_TYPE"));
				 map.put("IC_CODE", rs.getInt("IC_CODE"));
				 map.put("IC_NAME", rs.getString("IC_NAME"));
				 result.add(map);
			 }
			 
		 }catch(SQLException ex){
			 Log.writeExpt(ex, sb.toString());
		 }catch(Exception ex){
			 Log.writeExpt(ex);
		 }finally{
			 sb = null;
			 if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			 if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			 if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		 }
		 
		 return result;
	 }
	 
}//IssueMgr End.
