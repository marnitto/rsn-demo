package risk.admin.relation_manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;

import risk.DBconn.DBconn;
import risk.admin.relation_manager.RelationManagerBean;
import risk.admin.relation_manager.RelationManagerMgr;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class RelationManagerMgr {
	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
	private DBconn dbconn;
	private StringBuffer sb;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Statement stmt;
		
	public ArrayList getRelationKeywordList(String searchKey, String kor_val, String eng_val, String num_val){
		ArrayList result = new ArrayList();
		HashMap map = new HashMap();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append(" SELECT	RK_SEQ, RK_NAME			\n");
			sb.append(" FROM RELATION_KEYWORD_R			\n");
			sb.append(" WHERE 1=1						\n");
			sb.append(" AND RK_USE = 'Y'				\n");
			if(!kor_val.equals(""))
			{
				if(kor_val.equals("ㄱ")){
					sb.append("AND RK_NAME >= '가' AND RK_NAME < '나'		\n");
				}
				else if(kor_val.equals("ㄴ")){
					sb.append("AND RK_NAME >= '나' AND RK_NAME < '다'		\n");
				}
				else if(kor_val.equals("ㄴ")){
					sb.append("AND RK_NAME >= '나' AND RK_NAME < '다'		\n");
				}
				else if(kor_val.equals("ㄷ")){
					sb.append("AND RK_NAME >= '다' AND RK_NAME < '라'		\n");
				}
				else if(kor_val.equals("ㄹ")){
					sb.append("AND RK_NAME >= '라' AND RK_NAME < '마'		\n");
				}
				else if(kor_val.equals("ㅁ")){
					sb.append("AND RK_NAME >= '마' AND RK_NAME < '바'		\n");
				}
				else if(kor_val.equals("ㅂ")){
					sb.append("AND RK_NAME >= '바' AND RK_NAME < '사'		\n");
				}
				else if(kor_val.equals("ㅅ")){
					sb.append("AND RK_NAME >= '사' AND RK_NAME < '아'		\n");
				}
				else if(kor_val.equals("ㅇ")){
					sb.append("AND RK_NAME >= '아' AND RK_NAME < '자'		\n");
				}
				else if(kor_val.equals("ㅈ")){
					sb.append("AND RK_NAME >= '자' AND RK_NAME < '차'		\n");
				}
				else if(kor_val.equals("ㅊ")){
					sb.append("AND RK_NAME >= '차' AND RK_NAME < '카'		\n");
				}
				else if(kor_val.equals("ㅋ")){
					sb.append("AND RK_NAME >= '카' AND RK_NAME < '타'		\n");
				}
				else if(kor_val.equals("ㅌ")){
					sb.append("AND RK_NAME >= '타' AND RK_NAME < '파'		\n");
				}
				else if(kor_val.equals("ㅍ")){
					sb.append("AND RK_NAME >= '파' AND RK_NAME < '하'		\n");
				}
				else if(kor_val.equals("ㅎ")){
					sb.append("AND RK_NAME >= '하' AND RK_NAME < '힣'		\n");
				}
				sb.append("ORDER BY RK_NAME ASC							\n");
			}
			if(!eng_val.equals("")){
				sb.append("AND substring(RK_NAME, 1, 1) = '"+eng_val+"'	\n");
				sb.append("ORDER BY RK_NAME ASC							\n");
			}
			if(!num_val.equals("")){
				sb.append("AND substring(RK_NAME, 1, 1) = '"+num_val+"'	\n");
				sb.append("ORDER BY RK_NAME ASC							\n");
			}
			if(!searchKey.equals("")){
				sb.append("AND RK_NAME LIKE '%"+searchKey+"%'			\n");
				sb.append("ORDER BY RK_NAME ASC							\n");
			}
			if(searchKey.equals("") && kor_val.equals("") && eng_val.equals("") && num_val.equals("")){
				sb.append("ORDER BY RK_SEQ DESC							\n");
			}
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				map = new HashMap();
				map.put("rk_seq", rs.getString("RK_SEQ"));
				map.put("rk_name", rs.getString("RK_NAME"));
				result.add(map);
			}
		} catch (SQLException e){
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally{
			sb = null;
			try { if(dbconn != null) dbconn.close();} catch (Exception e) {}
			try { if(rs != null) rs.close();} catch (Exception e) {}
			try { if(pstmt != null) pstmt.close();} catch (Exception e) {}
		}
		
		return result;
	}
	
	public RelationManagerBean getKeyword(String rkSeq)
	{
		dbconn = null;
		stmt = null;
		pstmt = null;
		rs = null;
		sb = null;
		ArrayList arrList = new ArrayList();
		
		RelationManagerBean RKeyword = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT RK_SEQ, RK_NAME, RK_USE		\n");
			sb.append("FROM RELATION_KEYWORD_R				\n");
			sb.append("WHERE RK_SEQ = ('"+rkSeq+"')				\n");
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				RKeyword = new RelationManagerBean();
				RKeyword.setRk_seq(rs.getString("RK_SEQ"));
				RKeyword.setRk_name(rs.getString("RK_NAME"));
				RKeyword.setRk_use(rs.getString("RK_USE"));
			}
			
		} catch (SQLException	ex) {
			Log.writeExpt(ex, sb.toString());
		} catch (Exception ex) {
			Log.writeExpt(ex);
		} finally{
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}		
		return RKeyword;				
	}
	
	public boolean insertKeyword(String rk_name){
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" INSERT INTO RELATION_KEYWORD_R	 (RK_NAME)		\n");
			sb.append(" VALUES ('"+rk_name+"')							\n");
			
			stmt.executeUpdate(sb.toString());
			result = true;
			
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null) try{ rs.close(); } catch(SQLException e) {}
			if (stmt != null) try{ stmt.close(); } catch(SQLException e) {}
			if (dbconn != null) try{ dbconn.close(); } catch(SQLException e) {}
		}
		
		return result;
	}
	
	public boolean updateKeyword(String rk_seq,String rk_name){
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" UPDATE RELATION_KEYWORD_R				 		\n");
			sb.append(" SET RK_NAME = '"+rk_name+"'						\n");
			sb.append(" WHERE RK_SEQ = "+rk_seq+"						\n");
			
			stmt.executeUpdate(sb.toString());
			result = true;
			
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if (rs != null) try{ rs.close(); } catch(SQLException e) {}
			if (stmt != null) try{ stmt.close(); } catch(SQLException e) {}
			if (dbconn != null) try{ dbconn.close(); } catch(SQLException e) {}
		}
		
		return result;
	}
	
	public boolean deleteKeyword(String rk_seq){
		boolean result = false;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" UPDATE RELATION_KEYWORD_R					\n");
			sb.append(" SET RK_USE = 'N'							\n");
			sb.append(" WHERE RK_SEQ IN ("+rk_seq+")				\n");
			
			stmt.executeUpdate(sb.toString());
			result = true;
			
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());			
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally {
			if(rs != null) try{ rs.close(); } catch(SQLException e) {}
			if(stmt != null) try{ stmt.close(); } catch(SQLException e) {}
			if(dbconn != null) try{ dbconn.close(); } catch(SQLException e) {}
		}
		return result;
	}
	
	public String relationkeywordMerge(String keyword, String rkSeqs) throws SQLException, NamingException{
		String result = "";
		String insertRkSeq = "";
		ArrayList idSeqs = null;
		boolean chk = false;
		boolean delChk1 = false;
		boolean delChk2 = false;
		dbconn = new DBconn();
		dbconn.getDBCPConnection();
		stmt = dbconn.createStatement();
		
		try {
			dbconn.TransactionStart();
			sb = new StringBuffer();
			sb.append(" INSERT INTO RELATION_KEYWORD_R	 (RK_NAME, RK_USE)		\n");
			sb.append(" VALUES ('"+keyword+"', 'Y');							\n");
			System.out.println(sb.toString());
			if(stmt.executeUpdate(sb.toString()) > 0){
				sb = new StringBuffer();
				sb.append(" SELECT LAST_INSERT_ID() AS last_rkseq				\n");
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				if(rs.next()){
					insertRkSeq = rs.getString("last_rkseq");
					System.out.println("insertRkSeq : "+insertRkSeq);
				}
			}else{
				chk = false;
			}
			if( !insertRkSeq.equals("")){
				sb = new StringBuffer();
				sb.append("	SELECT							   		\n");
			    sb.append("        DISTINCT(A.ID_SEQ) AS ID_SEQ  	\n");
			    sb.append("   FROM ISSUE_DATA A                		\n");
			    sb.append("       ,RELATION_KEYWORD_MAP B	 	    \n");
			    sb.append("  WHERE A.ID_SEQ = B.ID_SEQ         		\n");
			    sb.append("    AND B.RK_SEQ IN ("+rkSeqs+")         \n");
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				idSeqs = new ArrayList();
				System.out.print("id_seqs : ");
				while(rs.next()){
					System.out.print(rs.getString("ID_SEQ")+" ");
					idSeqs.add((String)rs.getString("ID_SEQ"));
				}
				System.out.println("");
			}
			int cnt = 0;
			if(idSeqs.size() > 0){
				String idSeq = "";
				for(int i=0; i<idSeqs.size(); i++){
					idSeq = (String)idSeqs.get(i);
					sb = new StringBuffer();
					sb.append("	INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ, PRE_RK_SEQ)			\n");
					sb.append("	VALUES ("+idSeq+", "+insertRkSeq+", 0);									\n");
					if(stmt.executeUpdate(sb.toString()) > 0) {
						cnt++;
					}
				}
			}
			
			chk = false;
			int cntMap = 0;
			sb = new StringBuffer();
			sb.append("	SELECT COUNT(0) AS CNT FROM RELATION_KEYWORD_MAP WHERE RK_SEQ IN ("+rkSeqs+") 	\n");
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				cntMap = rs.getInt("CNT");
			}
			
			if(cntMap > 0 ){
				sb = new StringBuffer();
				sb.append("	DELETE FROM RELATION_KEYWORD_MAP WHERE RK_SEQ IN ("+rkSeqs+") 				\n");
				stmt.executeUpdate(sb.toString());
			}
			
			sb = new StringBuffer();
			sb.append("	DELETE FROM RELATION_KEYWORD_R WHERE RK_SEQ IN ("+rkSeqs+")						\n");
			if(stmt.executeUpdate(sb.toString()) > 0){
				delChk2 = true;
			}
			
			//RK_USE 사용여부
			/*sb = new StringBuffer();
			sb.append("	UPDATE RELATION_KEYWORD_TEST SET RK_USE='N'	WHERE RK_SEQ IN ("+rkSeqs+")		\n");
			if(stmt.executeUpdate(sb.toString()) > 0){
				chk = true;
			}*/
			
			if(delChk2){
				result = "success";
			}else{
				result = "fail";
				sb = new StringBuffer();
				sb.append("	DELETE FROM RELATION_KEYWORD_R WHERE RK_SEQ IN ("+insertRkSeq+")					\n");
				stmt.executeUpdate(sb.toString());
				
				sb = new StringBuffer();
				sb.append("	DELETE FROM RELATION_KEYWORD_MAP WHERE RK_SEQ IN ("+insertRkSeq+") 				\n");
				stmt.executeUpdate(sb.toString());
			}
			
		} catch (SQLException e) {
			Log.writeExpt(e, sb.toString());
		} catch (Exception e) {
			Log.writeExpt(e);
		} finally{
			if(rs != null) try { rs.close(); } catch(SQLException e) {}
			if(stmt != null) try { stmt.close(); } catch(SQLException e) {}
			if(dbconn != null) try { dbconn.close(); } catch(SQLException e) {}
		}
		return result;
	}	

}
