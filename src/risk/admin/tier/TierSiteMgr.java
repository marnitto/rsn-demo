package risk.admin.tier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.admin.site.SiteBean;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class TierSiteMgr {
	private TierSiteBean tsBean = null;
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs    = null;	
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private int startNum;
	private int endNum;
	private int totalCnt;
	
	public int totalCnt()
	{
		return totalCnt;
	}
	
	
    /**
     * 원본사이트 테이블을 조회하여 해당정보를 어레이로 반환.    
     * @param 
     * @return : 기자정보리스트
     */
    public ArrayList getSiteList(int pageNum, int rowCnt, String name)    
    {  	    	
    
    	ArrayList resultList = new ArrayList();
    	
    	if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    	    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();    		
    		
    		sb = new StringBuffer();    
    		sb.append(" SELECT COUNT(*) CNT                     \n");    		
    		sb.append(" FROM SITE A , SG_S_RELATION B                      \n");
    		sb.append(" WHERE A.S_SEQ = B.S_SEQ AND A.S_SEQ NOT IN (SELECT B.TS_SEQ FROM TIER_SITE B) \n");       			
    		if(!name.equals(""))
        	sb.append("       AND A.S_NAME LIKE '%"+name+"%'             \n");    		
			rs = stmt.executeQuery(sb.toString());					
			System.out.println(sb.toString());
			if(rs.next()){
				totalCnt = rs.getInt("CNT");
			}		
						    		
			rs = null;			
    		sb = new StringBuffer();
    		sb.append(" SELECT A.S_SEQ                           \n");
    		sb.append("        ,A.S_NAME                           \n");
    		sb.append("        ,A.S_URL                           \n");    	
    		sb.append(" FROM SITE A , SG_S_RELATION B                           \n");
    		sb.append(" WHERE A.S_SEQ = B.S_SEQ AND A.S_SEQ NOT IN (SELECT B.TS_SEQ FROM TIER_SITE B)                                 \n");    			
    		if(!name.equals(""))
        	sb.append("       AND A.S_NAME LIKE '%"+name+"%'             \n");    		
    		sb.append(" ORDER BY A.S_NAME \n");
    		if(pageNum>0)
    		sb.append(" LIMIT "+startNum+", "+endNum+"                           \n");    		
			rs = stmt.executeQuery(sb.toString());			
			System.out.println(sb.toString());
			while(rs.next()){
				tsBean = new TierSiteBean();
				tsBean.setTs_seq(rs.getString("S_SEQ"));
				tsBean.setTs_name(rs.getString("S_NAME"));
							
				resultList.add(tsBean);
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
		
	    return resultList;
	 }
	
	
	public TierSiteBean getTierSiteBean(String ts_seq)
	{
		return (TierSiteBean)getTierSiteList(0,0,ts_seq,"").get(0);
	}
	
	/**
     * 매체 정보 리스트를 가져온다.
     * @param 
     * @return :  매체 정보 리스트
     */
    public ArrayList getTierSiteList(int pageNum, int rowCnt, String ts_seq, String types)    
    { 	    	    	
    	ArrayList resultList = new ArrayList();
    	
    	if(rowCnt>0) this.startNum = (pageNum-1)*rowCnt;
		this.endNum = rowCnt;
    	    	
    	try{ 
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();    		
    		
    		sb = new StringBuffer();
    		sb.append(" SELECT COUNT(*) CNT  \n");    		
    		sb.append(" FROM TIER_SITE \n");   
    		sb.append(" WHERE 1=1   	\n");   		
    		if(!ts_seq.equals(""))
        	sb.append(" AND TS_SEQ = '"+ts_seq+"' \n");  	
    		if(!types.equals(""))
        	sb.append(" AND TS_TYPE IN ("+types+") \n");	
			rs = stmt.executeQuery(sb.toString());					
			System.out.println(sb.toString());
			if(rs.next()){
				totalCnt = rs.getInt("CNT");
			}		
						    		
			rs = null;			
    		sb = new StringBuffer();
    		sb.append(" SELECT TS_SEQ  \n");
    		sb.append("        ,TS_NAME  \n");
    		sb.append("        ,TS_TYPE  \n");
    		sb.append("        ,TS_RANK  \n");    	
    		sb.append(" FROM TIER_SITE \n");
    		sb.append(" WHERE 1=1                                  \n");
    		if(!ts_seq.equals(""))
            sb.append(" AND TS_SEQ = '"+ts_seq+"' \n");     	
    		if(!types.equals(""))
        	sb.append(" AND TS_TYPE IN ("+types+") \n");
    		sb.append(" ORDER BY TS_RANK ASC \n");	
    		if(pageNum>0)
    		sb.append(" LIMIT "+startNum+", "+endNum+"                           \n");    		
			rs = stmt.executeQuery(sb.toString());			
			System.out.println(sb.toString());
			while(rs.next()){
				tsBean = new TierSiteBean();
				tsBean.setTs_seq(rs.getString("TS_SEQ"));
				tsBean.setTs_name(rs.getString("TS_NAME"));
				tsBean.setTs_type(rs.getString("TS_TYPE"));
				tsBean.setTs_rank(rs.getString("TS_RANK"));
			
				resultList.add(tsBean);
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
		
	    return resultList;
	 }    
    
    /**
     * 매체사이트를 등록
     * @param 
     * @return
     */
	public boolean insertTierSite(TierSiteBean tsBean)
	{		
		boolean result = false;
	
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO TIER_SITE(			    		   \n");
			sb.append("        					TS_SEQ  \n");
			sb.append("        					,TS_NAME  \n");
    		sb.append("        					,TS_TYPE  \n");
    		/*sb.append("        					,TS_RANK  \n");*/
    		sb.append("                                 )			                        \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("			"+tsBean.getTs_seq()+"");	
			sb.append("			,'"+tsBean.getTs_name()+"' \n");		
			sb.append("			,"+tsBean.getTs_type()+" \n");
			/*sb.append("			,"+tsBean.getTs_rank()+" \n");*/				
			sb.append("        ) \n");
			
			
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0){	
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
     * 매체사이트를 수정
     * @param 
     * @return
     */
	public boolean updateTierSite(TierSiteBean tsBean)
	{		
		boolean result = false;
	
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" UPDATE TIER_SITE			    		   \n");			
    		sb.append(" SET 									   \n"); 
    		sb.append("    TS_TYPE = "+tsBean.getTs_type()+"  \n");
    		sb.append("    ,TS_RANK = "+tsBean.getTs_rank()+"  \n");    		
			sb.append(" WHERE TS_SEQ = "+tsBean.getTs_seq()+"               \n");
			
			
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0){	
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
     * 매체사이트를 수정
     * @param 
     * @return
     */
	public boolean deleteTierSite(TierSiteBean tsBean)
	{		
		boolean result = false;
	
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" DELETE FROM TIER_SITE			    		   \n");	    		
			sb.append(" WHERE TS_SEQ = "+tsBean.getTs_seq()+"               \n");
			
			
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0){	
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
}
