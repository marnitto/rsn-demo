package risk.admin.sns_manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import risk.DBconn.DBconn;

public class snsManagerMgr {
	
	
	public int getSnsManagerInfoCount(String sDate, String s_type){
		int result = 0;
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		Map map = null;
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT 													\n");
			sb.append("     COUNT(S_SEQ) AS TOTAL                               \n");
			sb.append("FROM SNS_MANAGER                                         \n");
			sb.append("WHERE DATE_FORMAT(S_DATE, '%Y-%m-%d') = '"+sDate+"'      \n");
			sb.append("AND S_TYPE = '"+s_type+"'                                \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				result = rs.getInt("TOTAL");
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
	
	public ArrayList getSnsManagerInfo(int nowpage, int rowCnt, String sDate, String s_type){
		
		int startNum =0;
		int endNum =0;
		if(rowCnt>0)  startNum = (nowpage-1)*rowCnt;
		endNum = rowCnt;
		
		ArrayList result = new  ArrayList();
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		Map map = null;
		
		try{
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT 													\n");
			sb.append("      S_SEQ                                              \n");
			sb.append("      ,DATE_FORMAT(S_DATE, '%Y-%m-%d') AS S_DATE         \n");
			sb.append("      ,S_CONTENT                                         \n");
			sb.append("      ,S_CNT1                                            \n");
			sb.append("      ,S_CNT2                                            \n");
			sb.append("      ,S_TYPE                                            \n");
			sb.append("FROM SNS_MANAGER                                         \n");
			sb.append("WHERE DATE_FORMAT(S_DATE, '%Y-%m-%d') = '"+sDate+"'      \n");
			sb.append("AND S_TYPE = '"+s_type+"'                                \n");
			sb.append("LIMIT "+startNum+", "+endNum+"                           \n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				map = new HashMap();
				map.put("S_SEQ", rs.getString("S_SEQ"));
				map.put("S_DATE", rs.getString("S_DATE"));
				map.put("S_CONTENT", rs.getString("S_CONTENT"));
				map.put("S_CNT1", rs.getString("S_CNT1"));
				map.put("S_CNT2", rs.getString("S_CNT2"));
				map.put("S_TYPE", rs.getString("S_TYPE"));
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
	
	public void deleteSnsInfo(String seqs){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		 try{
			 dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();
				sb.append("DELETE FROM SNS_MANAGER WHERE S_SEQ IN ("+seqs+")  \n");
				//System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
			 
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
	}
	
	public void insertSnsInfo(String s_type,String s_date,String s_content,String s_cnt1,String s_cnt2){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		 try{
			 dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();
				sb.append("INSERT INTO SNS_MANAGER  \n");
				sb.append("	(s_date, s_content, s_cnt1, s_cnt2, s_type)  \n");
				sb.append("VALUES 	\n");
				sb.append("('"+s_date+"', '"+s_content+"', "+s_cnt1+", "+s_cnt2+", '"+s_type+"');	\n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
			 
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
	}
 
 
	public void updateSnsInfo(String s_seq, String s_type,String s_date,String s_content,String s_cnt1,String s_cnt2){
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		 try{
			 dbconn = new DBconn();
				dbconn.getDBCPConnection();
				sb = new StringBuffer();
				sb.append("UPDATE SNS_MANAGER   \n");
				sb.append(" SET	s_date='"+s_date+"', s_content='"+s_content+"', s_cnt1='"+s_cnt1+"', s_cnt2='"+s_cnt2+"', s_type='"+s_type+"'  \n");
				sb.append("WHERE s_seq="+s_seq+"	\n");
				System.out.println(sb.toString());
				pstmt = dbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
			 
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
 }

}
