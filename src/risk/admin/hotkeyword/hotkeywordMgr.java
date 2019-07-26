package risk.admin.hotkeyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.Log;

public class hotkeywordMgr{
	public ArrayList getHotkeyword(String h_seqs, String useyn){		
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();   
			
			sb.append("SELECT H.H_SEQ, H.H_NAME, H.H_REGDATE, H.H_USEYN, H.M_SEQ, M.M_NAME\n");
			sb.append("FROM HOT_KEYWORD H\n");
			sb.append("INNER JOIN MEMBER M\n");
			sb.append("ON H.M_SEQ = M.M_SEQ\n");
			sb.append("WHERE 1 = 1\n");
			if(!h_seqs.equals("")){
			sb.append("AND H.H_SEQ IN ("+h_seqs+")\n");
			}
			if(!useyn.equals("")){
			sb.append("AND H.H_USEYN = '"+useyn+"'\n");
			}
			sb.append("ORDER BY H.H_SEQ DESC\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				hotkeywordBean hb = new hotkeywordBean();
				hb.setH_seq(rs.getString("H_SEQ"));
				hb.setH_name(rs.getString("H_NAME"));
				hb.setH_regdate(rs.getString("H_REGDATE"));
				hb.setH_useyn(rs.getString("H_USEYN"));
				hb.setM_seq(rs.getString("M_SEQ"));
				hb.setM_name(rs.getString("M_NAME"));
				result.add(hb);
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}        
		return result;
	}
	
	public int regkeyword(hotkeywordBean hb){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();   
			sb.append("INSERT INTO HOT_KEYWORD\n");
			sb.append("SELECT IFNULL(MAX(H_SEQ)+1, 1) AS H_SEQ\n");
			sb.append(", '"+hb.getH_name()+"' AS H_NAME, NOW(), '"+hb.getH_useyn()+"' AS H_USEYN, "+hb.getM_seq()+" AS M_SEQ\n");
			sb.append("FROM HOT_KEYWORD\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			result = pstmt.executeUpdate();
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		    if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		    if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}    
		return result;
	}
	
	public int updatekeyword(hotkeywordBean hb){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();   
			sb.append("UPDATE HOT_KEYWORD\n");
			sb.append("SET H_NAME = '"+hb.getH_name()+"'\n");
			sb.append(", H_USEYN = '"+hb.getH_useyn()+"'\n");
			sb.append("WHERE H_SEQ = "+hb.getH_seq()+"\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			result = pstmt.executeUpdate();
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}    
		return result;
	}
	
	public int delkeyword(String h_seqs){
		int result = 0;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();   
			sb.append("DELETE FROM HOT_KEYWORD WHERE H_SEQ IN ("+h_seqs+")\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			result = pstmt.executeUpdate();
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if( dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}    
		return result;
	}
}