package risk.demon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import risk.DBconn.DBconn;
import risk.analysis.AutoAnalysis;
import risk.analysis.vo.ResultForm;
import risk.analysis.vo.SuccessForm;
import risk.util.Log;

public class DAEGUAutoAnalysis{
	
	private static DBconn subdbconn = null;
	private static AutoAnalysis a = new AutoAnalysis();
	//private static AutoAnalysis a = null;
	private static int limitcnt = 100;	//조회 건수
	
	public static void main(String args[]){
		
		Log.crond( "DAEGUAutoanAlysis Start....................." );
		
		String md_seq = "";
		String md_seqs= "";
		
		try {
			
			subdbconn = new DBconn();
			subdbconn.getSubDirectConnection();
			System.out.println( subdbconn );
			
			md_seq = selectLastMetaDataNumber();
			
			boolean chk = true;
			while(chk){
				md_seqs = "";
				md_seqs = selectMetaData(md_seq);
				if("".equals(md_seqs)){
					chk = false;
					Log.crond( "no search data." );
				}else{
					List list = insertIssueData(md_seqs);
					
					if(list.size() > 0){
						for(int i=0; i < list.size(); i++){
							String seq[] = (String[])list.get(i);
							
							insertIssueDataCode(seq);
							
							md_seq = seq[1];
							
						}
					}
					
					//마지막 md_seq 값 저장
					updateLastMetaDataNumber(md_seq);
					chk = true;
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(subdbconn!= null){ try {subdbconn.close();} catch (SQLException e) {}}
			Log.crond( "END....................." );
		}
	}
	
	static void insertIssueDataCode(String seq[]){	//seq[0]:ID_SEQ , seq[1]:MD_SEQ, seq[2]:ID_TITLE, seq[3]:ID_CONTENT
		
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sgSeq = "";
		boolean chk_type1 = false;
		boolean chk_type2 = false;
		
		List codeList = new ArrayList();
		try{
			
			sb = new StringBuffer();
			sb.append("SELECT  A.SG_SEQ, B.IC_TYPE, B.IC_CODE       \n");
			sb.append("  FROM IDX A, KEYWORD_MAP B                  \n");
			sb.append(" WHERE A.MD_SEQ IN ("+seq[1]+")                  \n");
			sb.append("   AND A.K_XP = B.K_XP                       \n");
			sb.append("   AND A.K_YP = B.K_YP                       \n");
			sb.append("   AND A.K_ZP = B.K_ZP                       \n");
			sb.append("GROUP BY A.SG_SEQ, B.IC_TYPE, B.IC_CODE		\n");
			System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				sgSeq = rs.getString("SG_SEQ");
				
				String arr_typeCode [] = new String[2];
				
				arr_typeCode[0] = rs.getString("IC_TYPE");
				arr_typeCode[1] = rs.getString("IC_CODE");
				
				if(arr_typeCode[0].equals("1")){
					chk_type1 = true;	
				}
				if(arr_typeCode[0].equals("2")){
					chk_type2 = true;	
				}	
				
				codeList.add(arr_typeCode);
			}
			
			if(codeList.size() > 0){
				for(int i=0; i < codeList.size(); i++){
					String temp[] = (String[])codeList.get(i);
					
					try{
					
						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE) VALUES ("+seq[0]+", "+temp[0]+", "+temp[1]+")  \n");
						System.out.println(sb.toString());
						pstmt = subdbconn.createPStatement(sb.toString());
						pstmt.executeUpdate();
						
					}catch(Exception e){
						Log.writeExpt("INSERT TABLE ISSUE_DATA_CODE ", e.getMessage() + " ID_SEQ="+seq[0] +" ,MD_SEQ="+seq[1]);
					}
					
				}
				
				/**
				 * 분류체계의 정보구분 코드값은 있으나 구분 코드값이 없을때 디폴트로 구분 코드는 서울특별시청 코드를 등록한다.
				 */
				//정보구분 유무 체크
				if(chk_type2){
					if(!chk_type1){ //구분 유무 체크
						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE) VALUES ("+seq[0]+", 1, 1)  \n");
						System.out.println(sb.toString());
						pstmt = subdbconn.createPStatement(sb.toString());
						pstmt.executeUpdate();
					}
				}
				
				//민원유형 저장 (디폴트 기타로 저장)
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE) VALUES ("+seq[0]+", 3, 4)  \n");
				System.out.println(sb.toString());
				pstmt = subdbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
				
				//출처 저장
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE)  \n");
				sb.append("SELECT "+seq[0]+" AS ID_SEQ, 6 AS IC_TYPE, B.IC_CODE 		\n");
				sb.append(" FROM IC_S_RELATION A, ISSUE_CODE B  \n");
				sb.append(" WHERE S_SEQ = "+sgSeq+"                    \n");
				sb.append(" AND A.IC_SEQ = B.IC_SEQ             \n");
				System.out.println(sb.toString());
				pstmt = subdbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
				
				//자동 분석
				String result[] = autoAnalysisProc(seq[2], seq[3]);
				
				String sentiCode = result[0];
				
				if("".equals(sentiCode)){
					sentiCode = "3";
				}
				
				//감성(성향) 저장
				//수동 감성  - 추후 수정 가능한 감성 코드 
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE) VALUES ("+seq[0]+", 9, "+sentiCode+")  \n");
				System.out.println(sb.toString());
				pstmt = subdbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
				//자동 감성  - 수동감성과 비교하여, 자동 감성율 정확도 확인을 위해 필요
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_DATA_CODE (ID_SEQ, IC_TYPE, IC_CODE) VALUES ("+seq[0]+", 99, "+sentiCode+")  \n");
				System.out.println(sb.toString());
				pstmt = subdbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
				
				
				//연관키워드 저장
				if(!"".equals(result[1]) ){
					insertIssueRelationKeyword(seq[0] , result[1]);	
				}
				
			}else{
				
				sb = new StringBuffer();
				sb.append("DELETE FROM ISSUE_DATA WHERE ID_SEQ = "+seq[0]+"  \n");
				System.out.println(sb.toString());
				pstmt = subdbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
			}
			
			
		}catch (Exception ex){	
			Log.writeExpt("insertIssueDataCode", ex.getMessage() + " ID_SEQ="+seq[0] +" ,MD_SEQ="+seq[1]);
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		
	}
	
	
	/**
	 * 이슈 데이터 저장
	 * @param md_seqs
	 * @return
	 */
	static List insertIssueData(String md_seqs){
		List result = new ArrayList();
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			sb = new StringBuffer();
			sb.append("INSERT INTO ISSUE_DATA      ( I_SEQ			            \n");
			sb.append("			                    , IT_SEQ			        \n");
			sb.append("			                    , MD_SEQ			        \n");
			sb.append("			                    , ID_TITLE		            \n");
			sb.append("			                    , ID_URL			        \n");
			sb.append("			                    , SG_SEQ			        \n");
			sb.append("			                    , S_SEQ			            \n");
			sb.append("			                    , MD_SITE_NAME	            \n");
			sb.append("			                    , MD_SITE_MENU	            \n");
			sb.append("			                    , MD_DATE		            \n");
			sb.append("			                    , ID_WRITTER		        \n");
			sb.append("			                    , ID_CONTENT		        \n");
			sb.append("			                    , ID_REGDATE		        \n");
			sb.append("			                    , ID_MAILYN		            \n");
			sb.append("			                    , ID_USEYN		            \n");
			sb.append("			                    , M_SEQ			            \n");
			sb.append("			                    , MD_SAME_CT		        \n");
			sb.append("			                    , MD_TYPE		            \n");
			sb.append("					            , USER_ID                   \n");
			sb.append("					            , USER_NICK                 \n");
			sb.append("					            , BLOG_VISIT_COUNT          \n");
			sb.append("					            , CAFE_NAME                 \n");
			sb.append("					            , CAFE_MEMBER_COUNT         \n");
			sb.append("					            , MD_PSEQ) 		            \n");
			sb.append("					(										\n");	
			sb.append("					SELECT 0 AS I_SEQ						\n");	 
			sb.append("					, 0 AS IT_SEQ						    \n");
			sb.append("					, A.MD_SEQ 							    \n");
			sb.append("					, A.MD_TITLE							\n");
			sb.append("					, A.MD_URL							    \n");
			sb.append("					, B.SG_SEQ							    \n");
			sb.append("					, A.S_SEQ							    \n");
			sb.append("					, A.MD_SITE_NAME						\n");
			sb.append("					, A.MD_MENU_NAME						\n");
			sb.append("					, A.MD_DATE							    \n");
			sb.append("					, ''									\n");
			sb.append("					, C.MD_CONTENT						    \n");
			sb.append("					, NOW()								    \n");
			sb.append("					, 'N'								    \n");
			sb.append("					, 'Y'								    \n");
			sb.append("					, 0									    \n");
			sb.append("					, A.MD_SAME_COUNT					    \n");
			sb.append("					, A.MD_TYPE							    \n");
			sb.append("					, USER_ID							    \n");
			sb.append("					, USER_NICK							    \n");
			sb.append("					, BLOG_VISIT_COUNT					    \n");
			sb.append("					, CAFE_NAME							    \n");
			sb.append("					, CAFE_MEMBER_COUNT			            \n");
			sb.append("					, A.MD_PSEQ 							\n");
			sb.append("					FROM META A, SG_S_RELATION B, DATA C	\n");
			sb.append("					WHERE A.S_SEQ = B.S_SEQ					\n");
			sb.append("					AND A.MD_SEQ = C.MD_SEQ				    \n");
			sb.append("					AND A.MD_SEQ IN ("+md_seqs+")           \n");
			sb.append("					ORDER BY MD_SEQ ASC                     \n");
			sb.append("					)										\n");
			System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			if(pstmt.executeUpdate() > 0){
				
				sb = new StringBuffer();
				sb.append("UPDATE IDX SET ISSUE_CHECK='Y' WHERE MD_SEQ IN ("+md_seqs+") \n");
				System.out.println(sb.toString());
				pstmt = subdbconn.createPStatement(sb.toString());
				pstmt.executeUpdate();
				
				
				sb = new StringBuffer();
				sb.append("SELECT ID_SEQ , MD_SEQ , ID_TITLE, ID_CONTENT FROM ISSUE_DATA WHERE MD_SEQ IN ("+md_seqs+") ORDER BY ID_SEQ ASC \n");
				System.out.println(sb.toString());
				pstmt = subdbconn.createPStatement(sb.toString());
				rs = pstmt.executeQuery();
				String tmp[] = new String[4]; 
				while(rs.next()){
					tmp = new String[4];
					tmp [0] = rs.getString("ID_SEQ");
					tmp [1] = rs.getString("MD_SEQ");
					tmp [2] = rs.getString("ID_TITLE");
					tmp [3] = rs.getString("ID_CONTENT");
					result.add(tmp);
				}
			}
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		return result;
		
	}
	
	static String selectMetaData(String md_seq){
		String result ="";
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("SELECT A.MD_SEQ						\n");
			sb.append("  FROM META A, IDX B, MAP_META_SEQ C \n");
			sb.append(" WHERE A.MD_SEQ > "+md_seq+"         \n");
			sb.append("   AND A.MD_SEQ = B.MD_SEQ           \n");
			sb.append("   AND B.ISSUE_CHECK = 'N'           \n");
			sb.append("   AND B.I_STATUS = 'N'              \n");
			sb.append("   AND B.K_XP != 14              	\n");
			sb.append("   AND A.MD_SEQ = C.MD_SEQ           \n");
			sb.append("   AND C.SAME_CHK = 1         		\n");
			sb.append("ORDER BY A.MD_SEQ ASC LIMIT "+limitcnt+";     \n");
			System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				if("".equals(result)){
					result = rs.getString("MD_SEQ");	
				}else{
					result += ","+rs.getString("MD_SEQ");
				}
			}
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		return result;
	}
	
	static void updateLastMetaDataNumber(String md_seq){
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("UPDATE ANALYSIS_MAP_SEQ SET MD_SEQ = "+md_seq+" , REG_DATE = NOW() WHERE SEQ = 1 \n");
			System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
	}
	
	static String selectLastMetaDataNumber(){
		String result ="";
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("SELECT MD_SEQ FROM ANALYSIS_MAP_SEQ WHERE SEQ = 1 ORDER BY 1 DESC LIMIT 1 \n");
			System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString("MD_SEQ");
			}
		}catch (Exception ex){	
			Log.writeExpt("selectLastMetaDataNumber","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		return result;
	}
	
	
	static HashMap<String, Integer> getGroupBy(String keyCodes){
    	HashMap<String, Integer> result= new HashMap<String, Integer>();
    	String[] arKeyCode = keyCodes.split(",");
    	
    	for(int i = 0; i < arKeyCode.length; i++){
    		if(result.containsKey(arKeyCode[i])){
    			result.put(arKeyCode[i], result.get(arKeyCode[i]) + 1);
    		}else{
    			result.put(arKeyCode[i], 1);
    		}
    	}
    	
    	return result;
    }
	
	static boolean insertRelationKeyword(int idSeq, int icType, int icCode, HashMap<String, Integer> hKey){
    	boolean result = false;
    	PreparedStatement pstmt = null;
        StringBuffer sb = null;
        int paNum = 1;
		try{
			
            sb = new StringBuffer();
            sb.append("INSERT INTO ISSUE_RELATION_KEYWORD (ID_SEQ, IC_TYPE, IC_CODE, PAT_SEQ, PAT_CNT)\n"); 
            sb.append("                            VALUES (?, ?, ?, ?, ?)\n");
                 
			//System.out.println( sb.toString() );
			pstmt = subdbconn.createPStatement(sb.toString());
			
			Set<String> keys = hKey.keySet();
			Iterator<String> it = keys.iterator();
			
			
			String name = "";
			while(it.hasNext()){
				paNum = 1;
				
				name = it.next();
				pstmt.clearParameters();
				pstmt.setInt(paNum++, idSeq);
				pstmt.setInt(paNum++, icType);
				pstmt.setInt(paNum++, icCode);
				pstmt.setInt(paNum++, Integer.parseInt(name));
				pstmt.setInt(paNum++, hKey.get(name));
				pstmt.execute();
			}
			
			result = true;

		}catch(SQLException ex) {
			//Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
			
        }catch(Exception ex) {
			//Log.writeExpt(name,ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
            sb = null;
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
	
	
	public static void insertIssueRelationKeyword(String id_seq, String pat_seqs){
		
		StringBuffer sb = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String tmp[] = pat_seqs.split(",");
		//System.out.println("추출 연관키워드 건수 - "+tmp.length);
		
		try{
			stmt = subdbconn.createStatement();	
			if(tmp.length > 1){
				for(int i=0; i < tmp.length; i++){
				sb = new StringBuffer();
				sb.append("INSERT INTO ISSUE_RELATION_KEYWORD	\n");
				sb.append("(ID_SEQ, PAT_SEQ)                      \n");
				sb.append("VALUES ("+id_seq+", "+tmp[i]+")     \n");
				stmt.addBatch(sb.toString());
				}
				stmt.executeBatch();
			}
			
			
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (stmt != null) {stmt.close();}} catch (SQLException ex) {}		
		}
		
		
	}
	
	
	public static String[] autoAnalysisProc(String id_title, String id_content){
		String result[] = new String[]{ "", "" };	//[0] : 감성코드 , [1] : 연관키워드 seq
		//자동분석 메서드 (제목 + " " + 본문내용)
		
		//System.out.println(id_title+ " "+ id_content);
		
		String sentiCode = "";
		boolean sentiChk = false;
		ArrayList<ResultForm> arResultForm = null;
		int option = 0;
		ResultForm rf = null;
		HashMap<String, Integer> arRelationKey = null;
		
		SuccessForm sf = a.AutoAnalysis(id_title+ " "+ id_content);
		//자동분석 성공여부
		if(sf.isSuccess_type()){
			//분석된 이슈 타입 
			System.out.println("successList : " + sf.getResultIc_type());
			String[] arrIcType = sf.getResultIc_type().split(",");
			if( sf.getResultIc_type().length() > 0 ){
				for(String icType : arrIcType){
					//이슈 타입별 분석된 이슈 코드와 노출횟수
					arResultForm = sf.getResultList().get(Integer.parseInt(icType));
					
					if(icType.equals("9")){
						sentiChk = true;
    					int positive = 0;
    					int negative = 0;
    					double trd_score = 0;
    					//성향분석
    					for(int i = 0; i < arResultForm.size(); i++){
    						rf = arResultForm.get(i);
    						if(rf.getIc_code() == 1){
    							positive = rf.getCount();
    						}else if(rf.getIc_code() == 2){
    							negative = rf.getCount();
    						}	
    					}
    						
    					if(positive > 0 || negative > 0){
    						trd_score = (double)positive / ((double)positive + negative) * 100;
    						
    						if((int)Math.round(trd_score) > 60  ){
    							sentiCode = "1";	//긍정
        					}else if((int)Math.round(trd_score) <= 40  ){
        						sentiCode = "2";	//부정
        					}else{
        						sentiCode = "3";	//중립
        					}
    					}else{
    						sentiCode = "3";	//중립
    					}
					}
					
					result[0] = sentiCode;
					
					for(ResultForm f : sf.getResultList().get(Integer.parseInt(icType))){
								  //이슈 타입              //이슈 코드              //노출횟수           //노출 패턴
						//System.out.println(f.getIc_type() + " : " + f.getIc_code() + " : " + f.getCount() +" : "+f.getPat_seq());
						//System.out.println(f.getPat_seq());
						result[1] = f.getPat_seq();
					}
					//System.out.println("#####################################################################");
				} 
			}
		}
		
		return result;
	}
	
	/**
	 * 배열에서 중복제거
	 * @param pat_seqs
	 * @return
	 */
	public static String duplication_check(String pat_seqs){
		
		String tmps = "";
		
		TreeSet tSet = new TreeSet();
		String result[] = null;
		if(!"".equals(pat_seqs)){
			result = pat_seqs.split(",");
			for(int i=0; i < result.length; i++){
				tSet.add(result[i]);
			}
		}
		
		Iterator it = tSet.iterator();
		while(it.hasNext()){
			
			if(!"".equals( tmps )){
				tmps += ","+it.next();
			}else{
				tmps = it.next().toString();
			}
		}
		return tmps;
	}

}
