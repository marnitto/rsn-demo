package risk.demon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.Log;

public class DAEGUIssueSameUpdate {
	
	private static long runTime = 0;
	
	private static DBconn subdbconn = null;
	
	
	public static void main(String[] args) {
				
		Log.crond("FSSIssueSameUpdate START ...");
		
		subdbconn = new DBconn();

		int result = 0;
		
		try{
			subdbconn.getSubDirectConnection();
			
			//유사 수량 업데이트
			CountUpdate();
			
			//유사 데이터 이슈 저장
			SameIssueDataInsert();
			
			Log.crond("FSSIssueSameUpdate END ...");
			
		}catch (Exception ex){	
			result = 0;
			Log.crond("cause:"+ex.getCause()+"  "+ex.getMessage());
			System.exit(1);
			
		}finally {
			try {if (subdbconn != null) {subdbconn.close();}} catch (SQLException ex) {}			
		}
	}
	
	
	
	// 유사 카운드 업데이트
	public static void CountUpdate()
	{
		StringBuffer sb = new StringBuffer();
		Statement stmt = null;
		try {    
			
			sb.append("UPDATE META A, ISSUE_DATA B                           \n");
			sb.append("   SET B.MD_SAME_CT = A.MD_SAME_COUNT                 \n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ                            \n");
			//sb.append("   AND B.SG_SEQ <> 30 \n");
			sb.append("   AND B.MD_DATE >= DATE_ADD( NOW(), INTERVAL -2 DAY) \n");
			
			stmt = subdbconn.createStatement();			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (stmt != null) {stmt.close();}} catch (SQLException ex) {}			
		}		
	}
	

	public static void SameIssueDataInsert()
	{
		StringBuffer sb = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {    
			stmt = subdbconn.createStatement();	
			
			
			// 모기사 가져오기
			sb = new StringBuffer();
			sb.append("SELECT DISTINCT MD_PSEQ \n");
			sb.append("  FROM ISSUE_DATA \n");
			sb.append(" WHERE MD_DATE >= DATE_ADD( NOW(), INTERVAL -2 DAY)\n");
			sb.append("   AND MD_SEQ = MD_PSEQ								\n");
			//sb.append("   AND SG_SEQ <> 30								\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			String md_pseqs = "";
			while(rs.next()){
				if(md_pseqs.equals("")){
					md_pseqs = rs.getString("MD_PSEQ");
				}else{
					md_pseqs += "," + rs.getString("MD_PSEQ");
				}
			}
			
			// 인서트 할 데이터 가져오기
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ,MD_PSEQ 									\n");
			sb.append("  FROM META 												\n");
			sb.append(" WHERE MD_PSEQ IN ("+md_pseqs+")							\n");
			sb.append("   AND MD_SEQ NOT IN (SELECT MD_SEQ 						\n");
			sb.append("                        FROM ISSUE_DATA		 			\n");
			sb.append("                       WHERE MD_PSEQ IN ("+md_pseqs+")	\n");
			sb.append("                      )									\n");
			sb.append("   AND MD_SEQ <> MD_PSEQ									\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			
			ArrayList<String[]> arrdata = new ArrayList<String[]>();
			
			String[] arr_seqs = null;
			
			while(rs.next()){
				
				arr_seqs = new String[2];
				arr_seqs[0] = rs.getString("MD_SEQ");
				arr_seqs[1] = rs.getString("MD_PSEQ");
				
				arrdata.add(arr_seqs);		
			}
			
			
			String md_seq = "";
			String md_pseq = "";
			String id_seq = "";
			String d_seq = "";
			String sg_seq = "";
			int insertCnt = 0;
			
			for(int i =0; i < arrdata.size(); i++){
				md_seq = arrdata.get(i)[0];
				md_pseq = arrdata.get(i)[1];
				
				
				//추가분
				
				// 키워드별 안쓰기로함. (김하나)
				
				/*
				sb = new StringBuffer();
				sb.append("SELECT K_XP, K_YP, MEDIA_INFO FROM ISSUE_DATA WHERE MD_SEQ = "+md_pseq+"	\n");
				System.out.println(sb.toString());
				rs = stmt.executeQuery(sb.toString());
				
				
				issueBean ibean = new issueBean();
				
				if(rs.next()){
					ibean.setK_xp(rs.getString("K_XP"));
					ibean.setK_yp(rs.getString("K_YP"));
					ibean.setMedia_info(rs.getString("MEDIA_INFO"));
				}
				*/
				issueBean ibean = new issueBean();
				
				//ISSUE_DATA 저장
				insertCnt = IssueDataInsert(md_seq, md_pseq, ibean);
				
				if(insertCnt > 0){
					
					sb = new StringBuffer();
					sb.append("SELECT ID_SEQ,D_SEQ, (SELECT B.IC_CODE																			  \n"); 
					sb.append("                   FROM IC_S_RELATION A																			  \n");
					sb.append("                      , ISSUE_CODE B 																			  \n");
					sb.append("                  WHERE A.IC_SEQ = B.IC_SEQ																		  \n"); 
					sb.append("                    AND A.S_SEQ = SG_SEQ) AS SG_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "+md_seq+" ORDER BY ID_SEQ DESC LIMIT 1 	\n");
					
					System.out.println(sb.toString());
					rs = stmt.executeQuery(sb.toString());
					
					id_seq ="";
					d_seq  = "";
					if(rs.next()){
						id_seq = rs.getString("ID_SEQ");
						d_seq  = rs.getString("D_SEQ");
						sg_seq = rs.getString("SG_SEQ");
					}
					
					if(id_seq.equals("")){
						Log.writeExpt("IssueSameUpdate","[이슈 저장안됨] : " + md_seq);
						continue;
					}
					
					//ISSUE_DATA_CODE 저장
					IssueDataCodeInsert(md_pseq, id_seq,d_seq, sg_seq);
					
					//PRESS_ISSUE_DATA 저장
					//PressIssueDataInsert(md_pseq, id_seq);
					
					//String itc_seq = "";
					//sb = new StringBuffer();
					//sb.append("SELECT ITD.ITC_SEQ\n");
					//sb.append("FROM ISSUE_DATA ID\n");
					//sb.append("INNER JOIN ISSUE_TAG_DATA ITD\n");
					//sb.append("ON ID.MD_PSEQ = "+md_pseq+"\n");
					//sb.append("AND ID.ID_SEQ = ITD.ID_SEQ\n");
					//sb.append("INNER JOIN ISSUE_TAG_CODE ITC\n");
					//sb.append("ON ITD.ITC_SEQ = ITC.ITC_SEQ\n");
					//sb.append("LIMIT 1\n");
					//rs = stmt.executeQuery(sb.toString());
					//if(rs.next()){
					//	itc_seq = rs.getString("ITC_SEQ");
					//}
					//
					//if(!itc_seq.equals("")){
					//	sb = new StringBuffer();
					//	sb.append("INSERT INTO ISSUE_TAG_DATA(ID_SEQ, ITC_SEQ) VALUES("+id_seq+", "+itc_seq+")\n");
					//	stmt.executeUpdate(sb.toString());
					//}
				}
				
			}
			
			
			
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage()+ " " +sb.toString());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (stmt != null) {stmt.close();}} catch (SQLException ex) {}			
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}		
	}
	
	
	public static void PressIssueDataInsert(String mdPseq, String idSeq){
		
		Statement stmt = null;
		StringBuffer sb = null;
		ResultSet rs  = null;
		
		String p_Seq = "";
		try {
			
			stmt = subdbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT P_SEQ FROM PRESS_ISSUE_DATA WHERE ID_SEQ = (SELECT ID_SEQ FROM ISSUE_DATA WHERE MD_SEQ = "+mdPseq+") \n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				p_Seq = rs.getString("P_SEQ");
			}
			
			if(!"".equals(p_Seq)){
			sb = new StringBuffer();
			sb.append(" INSERT INTO PRESS_ISSUE_DATA (ID_SEQ, P_SEQ)	\n");
			sb.append(" VALUES ("+idSeq+", "+p_Seq+")                                    	\n");
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			}
			
		}catch (Exception ex){	
			Log.writeExpt("PressIssueDataInsert","P:"+ex.getCause()+"  "+ex.getMessage()+" "+idSeq+" "+p_Seq);
		}finally {
			try {if (stmt != null) {stmt.close();}} catch (SQLException ex) {}			
		}
		
	}
	
	  public static int IssueRegistCheck(String md_seq)    
	    {    	
	    	int result = 0;
	    	Statement stmt = null;
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			try {
				stmt = subdbconn.createStatement();
				sb = new StringBuffer();				
				sb.append(" SELECT COUNT(0) AS CNT FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seq+")	\n");
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
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			}
		    return result;
		 }
	
	public static int IssueDataInsert(String mdSeq, String mdPseq, issueBean ibean)
	{
		
		Statement stmt = null;
		int result = 0;
		ResultSet rs = null;
		String xp = "";
		String yp = "";
		String zp = "";
		try {
			
			stmt = subdbconn.createStatement();
			StringBuffer sb = new StringBuffer();
			//sb.append(" SELECT K_XP, K_YP, K_ZP FROM ISSUE_DATA WHERE MD_SEQ = "+mdPseq+" LIMIT 1            	\n");
			//rs = stmt.executeQuery(sb.toString());
			//if(rs.next()){
			//	xp = rs.getString("K_XP");
			//	yp = rs.getString("K_YP");
			//	zp = rs.getString("K_ZP");
			//}
			//if( IssueRegistCheck(mdSeq) > 0){
			
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA      ( I_SEQ			\n");
				sb.append("                            , IT_SEQ			\n");
				sb.append("                            , MD_SEQ			\n");
				sb.append("                            , ID_TITLE		\n");
				sb.append("                            , ID_URL			\n");
				sb.append("                            , SG_SEQ			\n");
				sb.append("                            , S_SEQ			\n");
				sb.append("                            , MD_SITE_NAME	\n");
				sb.append("                            , MD_SITE_MENU	\n");
				sb.append("                            , MD_DATE		\n");
				sb.append("                            , ID_WRITTER		\n");
				sb.append("                            , ID_CONTENT		\n");
				sb.append("                            , ID_REGDATE		\n");
				sb.append("                            , ID_MAILYN		\n");
				sb.append("                            , ID_USEYN		\n");
				sb.append("                            , M_SEQ			\n");
				sb.append("                            , MD_SAME_CT		\n");
				sb.append("                            , MD_TYPE		\n");
	//			sb.append("                            , L_ALPHA		\n");
	//			sb.append("                            , ID_REPORTYN	\n");
				if(xp != null && !xp.equals("")){
				sb.append("                            , K_XP			\n");
				sb.append("                            , K_YP			\n");
				sb.append("                            , K_ZP			\n");
				}
				sb.append("                   ,USER_ID                  \n");
				sb.append("                   ,USER_NICK                \n");
				sb.append("                   ,BLOG_VISIT_COUNT         \n");
				sb.append("                   ,CAFE_NAME                \n");
				sb.append("                   ,CAFE_MEMBER_COUNT        \n");
	//			sb.append("                            , F_NEWS			\n");
				sb.append("                            , MD_PSEQ 		\n");
				sb.append("                            , D_SEQ) 		\n");
				sb.append(" (											\n");
				sb.append("   SELECT 0 AS I_SEQ							\n"); 
				sb.append("        , 0 AS IT_SEQ						\n");
				sb.append("        , A.MD_SEQ 							\n");
				sb.append("        , A.MD_TITLE							\n");
				sb.append("        , A.MD_URL							\n");
				sb.append("        , B.SG_SEQ							\n");
				sb.append("        , A.S_SEQ							\n");
				sb.append("        , A.MD_SITE_NAME						\n");
				sb.append("        , A.MD_MENU_NAME						\n");
				sb.append("        , A.MD_DATE							\n");
				sb.append("        , ''									\n");
				sb.append("        , C.MD_CONTENT						\n");
				sb.append("        , NOW()								\n");
				sb.append("        , 'N'								\n");
				sb.append("        , 'Y'								\n");
				sb.append("        , 0									\n");
				sb.append("        , A.MD_SAME_COUNT					\n");
				sb.append("        , A.MD_TYPE							\n");
	//			sb.append("        , A.L_ALPHA							\n");
	//			sb.append("        , 'N'								\n");
				if(xp != null && !xp.equals("")){
				sb.append("        , "+xp+"				\n");
				sb.append("        , "+yp+"				\n");
				sb.append("        , "+zp+"				\n");
				}
	//			sb.append("        , ''									\n");
				sb.append("        , USER_ID							\n");
				sb.append("        , USER_NICK							\n");
				sb.append("        , BLOG_VISIT_COUNT					\n");
				sb.append("        , CAFE_NAME							\n");
				sb.append("        , CAFE_MEMBER_COUNT					\n");
				
				sb.append("        , A.MD_PSEQ 							\n");
				sb.append("        ,(SELECT IFNULL(D_SEQ, '') FROM META WHERE MD_SEQ = "+mdSeq+ ")	 \n");
				sb.append("     FROM META A, SG_S_RELATION B, DATA C	\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ					\n");
				sb.append("      AND A.MD_SEQ = C.MD_SEQ				\n");
				sb.append("      AND A.MD_SEQ = "+mdSeq+"				\n");
				sb.append(" )											\n");
				System.out.println(sb.toString());
				result = stmt.executeUpdate(sb.toString());
				
				String s_seq = "";
				
				sb = new StringBuffer();
				sb.append("   SELECT A.S_SEQ							\n"); 
				sb.append("     FROM META A, SG_S_RELATION B, DATA C	\n");
				sb.append("    WHERE A.S_SEQ = B.S_SEQ					\n");
				sb.append("      AND A.MD_SEQ = C.MD_SEQ				\n");
				sb.append("      AND A.MD_SEQ = "+mdSeq+"				\n");
				System.out.println(sb.toString());				
				rs = stmt.executeQuery(sb.toString());
				if(rs.next()){
					s_seq = rs.getString("S_SEQ");
				}	
				
				if("2196".equals(s_seq) || "2199".equals(s_seq) ){
					sb = new StringBuffer();
	        		sb.append("INSERT INTO REPLY_CNT  			\n");
	        		sb.append("VALUES ( 						\n");
	        		sb.append("        (SELECT IFNULL(D_SEQ, '') FROM MAP_META_SEQ WHERE MD_SEQ = "+mdSeq+ ")	\n");
	        		sb.append("        ,0						\n");
	        		sb.append("        ) 						\n");
	        		System.out.println(sb.toString());
	        		stmt.executeUpdate(sb.toString());
				}				
				
				sb = new StringBuffer();
				sb.append(" UPDATE IDX SET ISSUE_CHECK ='Y'            	\n");
				sb.append(" WHERE MD_SEQ = "+mdSeq+"           		 	\n");
				stmt.executeUpdate(sb.toString());
			//}
			
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (stmt != null) {stmt.close();}} catch (SQLException ex) {}			
		}	
		
		return result;
	}
	
	
	public static void IssueDataCodeInsert(String mdPseq, String idSeq,String d_seq, String sgSeq)
	{
		
		Statement stmt = null;
		StringBuffer sb = null;
		ResultSet rs  = null;
		
		try {
			
			stmt = subdbconn.createStatement();
			
			
			sb = new StringBuffer();
			sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE) 	\n");
			sb.append(" VALUES ("+idSeq+", 6, "+sgSeq+"		)					\n");
			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE)	\n");
			sb.append(" (														\n");
			sb.append("  SELECT "+idSeq+"										\n");
			sb.append("       , B.IC_TYPE										\n");
			sb.append("       , B.IC_CODE										\n");
			sb.append("    FROM ISSUE_DATA A									\n");
			sb.append("       , ISSUE_DATA_CODE B 								\n");
			sb.append("   WHERE A.ID_SEQ = B.ID_SEQ								\n");
			sb.append("     AND A.MD_SEQ = "+mdPseq+"							\n");
			sb.append("     AND A.MD_SEQ = A.MD_PSEQ							\n");
			sb.append("     AND B.IC_TYPE <> 6									\n");
			sb.append(" )														\n");
			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			sb.append("SELECT COUNT(*) AS CNT FROM ISSUE_COMMENT WHERE ID_SEQ = "+idSeq+"	\n");
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			int cnt = 0;
			if(rs.next()){
				cnt = rs.getInt("CNT");
			}
			
			if(cnt > 0){
			
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_COMMENT (ID_SEQ, IM_DATE, IM_COMMENT)	\n");   
				sb.append(" (														\n");
				sb.append("   SELECT "+idSeq+"										\n");
				sb.append("        , NOW()											\n");
				sb.append("        , B.IM_COMMENT									\n");
				sb.append("     FROM ISSUE_DATA A									\n");
				sb.append("        , ISSUE_COMMENT B								\n");
				sb.append("    WHERE A.ID_SEQ = B.ID_SEQ							\n");
				sb.append("      AND A.MD_SEQ = "+mdPseq+"							\n");
				sb.append("      AND A.MD_SEQ = A.MD_PSEQ							\n");
				sb.append(" )														\n");
				
				System.out.println(sb.toString());
				stmt.executeUpdate(sb.toString());
			
			}
			
			
			sb = new StringBuffer();
			sb.append("INSERT INTO RELATION_KEYWORD_MAP (ID_SEQ, RK_SEQ)    	\n");
			sb.append(" (														\n");
			sb.append("  SELECT "+idSeq+"										\n");
			sb.append("       , B.RK_SEQ										\n");
			sb.append("    FROM ISSUE_DATA A									\n");
			sb.append("       , RELATION_KEYWORD_MAP B 							\n");
			sb.append("   WHERE A.ID_SEQ = B.ID_SEQ								\n");			
			sb.append("     AND A.MD_SEQ = A.MD_PSEQ							\n");
			sb.append("     AND A.MD_SEQ = "+mdPseq+"							\n");
			sb.append(" )														\n");
			
			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (stmt != null) {stmt.close();}} catch (SQLException ex) {}			
		}	
		
	}
	
	public static class issueBean {
			
		String k_xp;
		String k_yp;
		String k_zp;
		String media_info;
		
		
		public String getK_zp() {
			return k_zp;
		}
		public void setK_zp(String kZp) {
			k_zp = kZp;
		}
		public String getK_xp() {
			return k_xp;
		}
		public void setK_xp(String k_xp) {
			this.k_xp = k_xp;
		}
		public String getK_yp() {
			return k_yp;
		}
		public void setK_yp(String k_yp) {
			this.k_yp = k_yp;
		}
		public String getMedia_info() {
			return media_info;
		}
		public void setMedia_info(String media_info) {
			this.media_info = media_info;
		}

		
		
	}
	
}
