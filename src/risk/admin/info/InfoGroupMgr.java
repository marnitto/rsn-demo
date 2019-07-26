package risk.admin.info;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class InfoGroupMgr {
	
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private StringBuffer sb = null;	
	private InfoGroupBean igBean = null;
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	
	
	/**
     * InfoGroup 등록
     * @param I_SEQ
     * @return
     */
	public boolean insertInfoGroup(InfoGroupBean igBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO INFO_GROUP(	                            		   \n");				
			sb.append("                   I_NM		                               \n");			
			sb.append("                   ,I_REGDATE                                 \n");
			sb.append("                   ,M_SEQ                                  \n");
			sb.append("                   ,I_USEYN                                  \n");
			sb.append(" 				  )                                        \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("                   '"+igBean.getI_nm()+"'                               \n");
			sb.append("                   ,'"+igBean.getI_regdate()+"'                               \n");
			sb.append("                   ,"+igBean.getM_seq()+"                               \n");
			sb.append("                   ,'"+igBean.getI_useyn()+"'                               \n");
			sb.append(" )                                                    \n");
					
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
     * InfoGroup 등록
     * @param I_SEQ
     * @return
     */
	public boolean updateInfoGroup(InfoGroupBean igBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" UPDATE INFO_GROUP	                            		   \n");				
			sb.append(" SET  I_NM = '"+igBean.getI_nm()+"'		                   \n");				
			sb.append("     ,M_SEQ = "+igBean.getM_seq()+"                         \n");		
			sb.append("     ,I_USEYN = '"+igBean.getI_useyn()+"'                         \n");		
			sb.append(" WHERE I_SEQ = "+igBean.getI_seq()+"                        \n");
					
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
     * InfoGroup 등록
     * @param I_SEQ
     * @return
     */
	public boolean DeleteInfoGroup(String i_seq)
	{		
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" DELETE FROM INFO_GROUP WHERE I_SEQ IN ("+i_seq+")                		   \n");				
			
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
	
	public InfoGroupBean getInfoGroupBean(String i_seq)
	{
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT I_SEQ, I_NM,  DATE_FORMAT(I_REGDATE,'%Y.%m.%d') AS I_REGDATE, M_SEQ, I.I_USEYN  \n");	
			sb.append("        , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ )M_NAME \n");	
			sb.append(" FROM INFO_GROUP I                            		   \n");
			sb.append(" WHERE I_SEQ = "+i_seq+"                           		   \n");	
			sb.append(" ORDER BY I_SEQ DESC                            		   \n");				
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{				
				igBean = new InfoGroupBean();
				igBean.setI_seq(rs.getString("I_SEQ"));
				igBean.setI_nm(rs.getString("I_NM"));
				igBean.setI_regdate(rs.getString("I_REGDATE"));
				igBean.setM_name(rs.getString("M_NAME"));
				igBean.setM_seq(rs.getString("M_SEQ"));	
				igBean.setI_useyn(rs.getString("I_USEYN"));
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
		return igBean;
	}
	
	/**
     * InfoGroup 리스트
     * @param 
     * @return
     */
	public ArrayList getInfoGroup(String useyn)
	{		
		ArrayList arr = new ArrayList();
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT I_SEQ, I_NM,  DATE_FORMAT(I_REGDATE,'%Y.%m.%d') AS I_REGDATE, M_SEQ, I.I_USEYN  \n");	
			sb.append("        , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ )M_NAME \n");	
			sb.append(" FROM INFO_GROUP I                            		   \n");
			if(!useyn.equals("")){
			sb.append(" WHERE I.I_USEYN = '"+useyn+"'                            		   \n");
			}
			sb.append(" ORDER BY I_SEQ DESC                            		   \n");				
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next())
			{				
				igBean = new InfoGroupBean();
				igBean.setI_seq(rs.getString("I_SEQ"));
				igBean.setI_nm(rs.getString("I_NM"));
				igBean.setI_regdate(rs.getString("I_REGDATE"));
				igBean.setM_name(rs.getString("M_NAME"));
				igBean.setM_seq(rs.getString("M_SEQ"));
				igBean.setI_useyn(rs.getString("I_USEYN"));
				arr.add(igBean);
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
		return arr;
	}
	
	public ArrayList getInfoGroup(String useyn, int limit)
	{		
		ArrayList arr = new ArrayList();
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT I_SEQ, I_NM,  DATE_FORMAT(I_REGDATE,'%Y.%m.%d') AS I_REGDATE, M_SEQ, I.I_USEYN  \n");	
			sb.append("        , (SELECT M_NAME FROM MEMBER WHERE M_SEQ = I.M_SEQ )M_NAME \n");	
			sb.append(" FROM INFO_GROUP I                            		   \n");
			if(!useyn.equals("")){
			sb.append(" WHERE I.I_USEYN = '"+useyn+"'                            		   \n");
			}
			sb.append(" ORDER BY I_REGDATE DESC                            		   \n");
			sb.append(" LIMIT "+limit+" 		                           		   \n");
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next())
			{				
				igBean = new InfoGroupBean();
				igBean.setI_seq(rs.getString("I_SEQ"));
				igBean.setI_nm(rs.getString("I_NM"));
				igBean.setI_regdate(rs.getString("I_REGDATE"));
				igBean.setM_name(rs.getString("M_NAME"));
				igBean.setM_seq(rs.getString("M_SEQ"));
				igBean.setI_useyn(rs.getString("I_USEYN"));
				arr.add(igBean);
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
		return arr;
	}
	
	/*정보 그룹 추가
	 * 2014.01.14.화요일
	 * Mini*/
	public void infoinsert(String nm, String yn){		
		PreparedStatement pstmt = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();   
			sb.append("INSERT INTO INFO_GROUP\n");	
			sb.append("(I_NM ,I_REGDATE ,I_USEYN) \n");
			sb.append(" VALUES(?, NOW(), ?) \n");

			pstmt = dbconn.createPStatement(sb.toString());
			
			pstmt.setString(1, nm);
			pstmt.setString(2, yn);
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		    sb = null;
		    try{if( dbconn != null)dbconn.close(); } catch(Exception e) {e.printStackTrace();};
		    try{if( pstmt != null)pstmt.close(); } catch(Exception e) {e.printStackTrace();};
		}        
	}
	/* INFO 가져오기
	 * 2014.01.15.수요일
	 * Mini */
	public ArrayList infoinfo(String mg_seq){
		ArrayList array = new ArrayList();
		
		StringBuffer sb = null;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT M.M_SEQ, M_NAME, I.M_SEQ, I_SEQ, I_NM, I_REGDATE, I_USEYN \n");
			sb.append("FROM MEMBER M \n");
			sb.append("JOIN INFO_GROUP I \n");
			sb.append("ON M.M_SEQ = I.M_SEQ \n");
			sb.append("AND MG_SEQ = "+mg_seq+" \n");

			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				InfoGroupBean igb = new InfoGroupBean();
				igb.setM_seq(rs.getString("M_SEQ"));
				igb.setM_name(rs.getString("M_NAME"));
				igb.setI_seq(rs.getString("I_SEQ"));
				igb.setI_nm(rs.getString("I_NM"));
				igb.setI_regdate(rs.getString("I_REGDATE"));
				igb.setI_useyn(rs.getString("I_USEYN"));
				array.add(igb);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if( dbconn != null) dbconn.close();}catch(Exception e){e.printStackTrace();};
			try{if( pstmt != null) pstmt.close();}catch(Exception e){e.printStackTrace();};
			try{if( rs!= null) rs.close();}catch(Exception e){e.printStackTrace();};
		}
		
		return array;
	}
	
	public ArrayList getInfoGroupByMg_seq(String mg_seq, String YN){
		System.out.println("InfoGroupMgr.  getInfoGroupByMg_seq()");
		
		ArrayList array = new ArrayList();
		
		StringBuffer sb = null;
		DBconn dbconn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(" SELECT M.M_SEQ, M_NAME, I.M_SEQ, I_SEQ, I_NM, I_REGDATE, I_USEYN 	\n");
			sb.append(" FROM MEMBER M 														\n");
			sb.append(" JOIN INFO_GROUP I 													\n");
			sb.append(" ON M.M_SEQ = I.M_SEQ 												\n");
			sb.append(" AND M.MG_SEQ = "+mg_seq+" 											\n");
			sb.append("	AND I.I_USEYN = '"+YN+"'												\n");
			
			stmt = dbconn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			System.out.println(sb.toString());
			
			while(rs.next()){
				InfoGroupBean igb = new InfoGroupBean();
				igb.setM_seq(rs.getString("M_SEQ"));
				igb.setM_name(rs.getString("M_NAME"));
				igb.setI_seq(rs.getString("I_SEQ"));
				igb.setI_nm(rs.getString("I_NM"));
				igb.setI_regdate(rs.getString("I_REGDATE"));
				array.add(igb);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			try{if( dbconn != null) dbconn.close();}catch(Exception e){e.printStackTrace();};
			try{if( stmt != null) stmt.close();}catch(Exception e){e.printStackTrace();};
			try{if( rs!= null) rs.close();}catch(Exception e){e.printStackTrace();};
		}
		
		return array;
	}
	
}
