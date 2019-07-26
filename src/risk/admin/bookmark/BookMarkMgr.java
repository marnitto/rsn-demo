package risk.admin.bookmark;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class BookMarkMgr {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	

	
	/**
     * 해당 컨텐츠의 북마크 번호 리턴
     * @param 
     * @return
     */
	public String getBookMarkNum(String contents)
	{
		String bookMarkNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			sb.append("SELECT B_MARK_SEQ FROM BOOKMARK WHERE B_CONTENTS = '"+contents+"'");
			
			rs = stmt.executeQuery(sb.toString());
			System.out.println(sb.toString());
			if(rs.next())
			{			
				bookMarkNum = rs.getString("B_MARK_SEQ");
			}else{
				bookMarkNum = "";
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		   
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return bookMarkNum;
	}
	
	/**
     * 해당 컨텐츠에서 북마크 저장번호의 해당 페이지번호를 추출
     * @param 
     * @return
     */
	public int getPageNum(String contents, int rowcnt, String query)
	{
		int pageNum = 0;
		int rownum = 0;
		
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			if(contents.equals("search")){
				query = query.replaceAll(":BOOKMARK", "(SELECT B_MARK_SEQ FROM BOOKMARK WHERE B_CONTENTS = '"+contents+"')");				
				System.out.println(query);
				rs = stmt.executeQuery(query);				
				if(rs.next())
				{			
					rownum = rs.getInt(1);				
				}				
				
				pageNum = rownum/rowcnt;
				if(rownum%rowcnt!=0) pageNum++;				
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
		return pageNum;
	}
	
	/**
     * BookMark 저장  (해당 컨텐츠의 북마크번호가 존재하면 수정 아니하면 등록)
     * @param 
     * @return
     */
	public boolean saveBookMark(BookMarkBean bmBean)
	{
		boolean result = false;
		String insertNum = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();		
			
			sb = new StringBuffer();   
			sb.append(" SELECT B_MARK_SEQ FROM BOOKMARK WHERE B_CONTENTS = '"+bmBean.getB_contents()+"' \n");				
			rs = stmt.executeQuery(sb.toString());
			if(rs.next())
			{
				updateBookMark(bmBean);
			}else{
				insertBookMark(bmBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}        
		return result;
	}
	
	/**
     * BookMark 등록
     * @param 
     * @return
     */
	public boolean insertBookMark(BookMarkBean bmBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{				
			
			sb = new StringBuffer();   
			sb.append(" INSERT INTO BOOKMARK(	                            	   \n");				
			sb.append("                   B_MARK_SEEQ                              \n");
			sb.append("                   ,B_CONTENTS)                             \n");
			sb.append(" VALUES(                                                    \n");
			sb.append("        "+bmBean.getB_mark_seq()+"                          \n");			
			sb.append("        ,'"+bmBean.getB_contents()+"'				       \n");			
			sb.append(" )                                                          \n");
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		  
		}        
		return result;
	}
	
	/**
     * BookMark 수정
     * @param i_seq : ISSUE.I_SEQ
     * @return
     */
	public boolean updateBookMark(BookMarkBean bmBean)
	{		
		boolean result = false;
		String insertNum = null;
		try{				
			
			sb = new StringBuffer();   
			sb.append(" UPDATE BOOKMARK SET B_MARK_SEQ = "+bmBean.getB_mark_seq()+"    \n");				
			sb.append(" WHERE B_CONTENTS = '"+bmBean.getB_contents()+"'                \n");
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString())>0) result = true;																
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		   
		}        
		return result;
	}
}
