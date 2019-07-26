package risk.demon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.PersonalInfoUtil;

public class DAEGUSameProcess{
	
	/**
     *  로컬 DB 컨넥션
     * @return
     */
    static DBconn dbconn1 = null;
    static String name = "SameProcess";
    static String addName = "";
    
    public static void main( String[] args ){
    	
    	Log.crond(name, name + " START ...");
    	
    	try{
    		dbconn1 = new DBconn(); //로컬디비
        	dbconn1.getSubDirectConnection();
        	
        	ArrayList<String[]> arSameTarget = new ArrayList<String[]>();
        	ArrayList<String[]> arSameList = new ArrayList<String[]>();
        	
        	String[] targetBean = null;
        	String[] listBean = null;
        	
        	String md_seqs = "";
        	boolean sameChk = false;
        	String md_pseq = "";
    		String targetTitle = "";
    		String sameListTitle = "";
    		String s_seq = "";
    		PersonalInfoUtil piUtil = new PersonalInfoUtil();
    		
    		String sb_seqs = getSb_Seq();
        	
        	while(!(md_seqs = getMdSeqs()).equals("")){
        		
        		arSameTarget.clear();
        		arSameList.clear();
        		
        		arSameTarget = getSameTarget(md_seqs,sb_seqs);
        		
        		if(arSameTarget.size() > 0){
        			arSameList = getSameList(1, ((String[])arSameTarget.get(0))[2]);
        		}
        		
        		for(int i = 0; i < arSameTarget.size(); i++){
        			sameChk = false;
        			targetBean = arSameTarget.get(i); //[0]:MD_SEQ , [1]:MD_TITLE , [2]:MD_DATE , [3]:S_SEQ

        			targetTitle = targetBean[1];
        			
        			for(int j = 0; j < arSameList.size(); j++){
        				listBean = arSameList.get(j);	//[0]:MD_SEQ , [1]:MD_TITLE, [2]:S_SEQ
        				sameListTitle = listBean[1]; 
        				s_seq = listBean[2];
        				
        				/**
        				 * 트위터일 경우 url, twitter id 제거
        				 */
        				if("10464".equals(targetBean[3])){
        					targetTitle = piUtil.removeUrl(piUtil.removeHashTag(piUtil.removeTwitterID(targetTitle)) );
        					sameListTitle = piUtil.removeUrl(piUtil.removeHashTag(piUtil.removeTwitterID(sameListTitle)));
        					targetBean[1] = targetTitle; 
        					if( "10464".equals(s_seq) ){
        						if(StrCompare(sameListTitle, targetTitle) >= 90){	//20190219. 이윤정 대리 요청
                					sameChk = true;
                					md_pseq = listBean[0];
                					break;
                				}
        					}
        					
        				}else{
        					if( !"10464".equals(s_seq) ){
        						if(StrCompare(sameListTitle, targetTitle) >= 90){	//20190219. 이윤정 대리 요청
                					sameChk = true;
                					md_pseq = listBean[0];
                					break;
                				}
        					}
        				}
        			}
        			
        			if(sameChk){
        				sameUpdate(md_pseq, targetBean[0]);
        			}else{
        				sameRegist(targetBean);
        				
        				String[] tmpBean = new String[3];
        				tmpBean[0] = targetBean[0];
        				tmpBean[1] = targetBean[1];
        				tmpBean[2] = targetBean[3];
        				
        				arSameList.add(tmpBean);
        			}
        			
        			
        		}
        		
        		if(insertSameCheck(md_seqs) == 0){
        			Log.writeExpt(name,"저장실패");
        			break;
        		}
        	}
        	
    	}catch(Exception ex) {
    		Log.writeExpt(name,ex.getMessage());
        	ex.printStackTrace();
        	System.exit(1);
        }finally {
            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
        }
    	
    	Log.crond(name, name + " END ...");
    }
    
    
    static ArrayList<String[]> getSameList(int DateCnt, String stamp){
    	
    	ArrayList<String[]> result = new ArrayList<String[]>();
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
        	sb.append(" DELETE FROM "+addName+"SAME_FILTER WHERE MD_DATE <=  DATE_ADD(FROM_UNIXTIME("+stamp+"), interval -"+DateCnt+" day)  \n");
        	System.out.println(sb.toString());
        	stmt.executeUpdate( sb.toString() );
        	
        	sb = new StringBuffer();
            sb.append(" SELECT MD_SEQ, MD_TITLE , S_SEQ FROM "+addName+"SAME_FILTER ORDER BY MD_DATE ASC \n");
            
            System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			String[] bean = null;
			int idx = 0;
			while(rs.next()){
				idx = 0;
				bean = new String[3];
				bean[idx++] = rs.getString("MD_SEQ");
				bean[idx++] = rs.getString("MD_TITLE");
				bean[idx++] = rs.getString("S_SEQ");
				result.add(bean);
			}
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static String getMdSeqs(){
    	
    	String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" SELECT MD_SEQ FROM "+addName+"MAP_META_SEQ WHERE SAME_CHK = 0  ORDER BY MD_SEQ ASC LIMIT 100 \n");
            
            //System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			String md_seqs = "";
			while(rs.next()){
				if(md_seqs.equals("")){
					md_seqs = rs.getString("MD_SEQ");
				}else{
					md_seqs += "," + rs.getString("MD_SEQ");
				}	
			}
			
			result = md_seqs;
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static int insertSameCheck(String md_seq){
    	
    	int result = 0;
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
        	sb = new StringBuffer();
            sb.append(" UPDATE "+addName+"MAP_META_SEQ SET SAME_CHK = 1 WHERE MD_SEQ IN ("+md_seq+") \n");
            
            //System.out.println(sb.toString());
            result = stmt.executeUpdate(sb.toString());
		
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static ArrayList<String[]> getSameTarget(String md_seqs,String sb_seqs){
    	
    	ArrayList<String[]> result = new ArrayList<String[]>();
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
			sb = new StringBuffer();
            sb.append("SELECT DISTINCT(A.MD_SEQ)		\n");
            sb.append("     , A.MD_TITLE				\n");
            sb.append("     , A.S_SEQ					\n");
            sb.append("     , UNIX_TIMESTAMP(A.MD_DATE) AS	MD_DATE \n");
            sb.append("  FROM "+addName+"META A, IDX B				\n");
            sb.append(" WHERE A.MD_SEQ = B.MD_SEQ		\n");
            sb.append("   AND A.MD_SEQ IN ("+md_seqs+")	\n");
            sb.append("   AND A.SB_SEQ NOT IN ("+sb_seqs+")	\n");            
            sb.append("   AND B.ISSUE_CHECK = 'N'		\n");
            //트위터만 유사처리
            //sb.append("   AND B.SG_SEQ = 17		\n");
            
            //System.out.println(sb.toString());
            Log.crond(name, sb.toString());
			rs = stmt.executeQuery(sb.toString());
            
            String[] bean = null;
			int idx = 0;
			while(rs.next()){
				idx = 0;
				bean = new String[4];
				bean[idx++] = rs.getString("MD_SEQ");
				bean[idx++] = rs.getString("MD_TITLE");
				bean[idx++] = rs.getString("MD_DATE");
				bean[idx++] = rs.getString("S_SEQ");
				result.add(bean);
			}
            
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    static int sameUpdate( String md_pseq, String md_seq )
    {
    	int result = 0;
    	
    	Statement stmt = null;
    	ResultSet rs = null;
    	StringBuffer sb = null;
    	int sameCt = 0;
    	try{
    		stmt = dbconn1.createStatement();
    		
    		
    		sb = new StringBuffer();
    		sb.append(" SELECT COUNT(*) AS CNT FROM "+addName+"META WHERE MD_SEQ = "+md_pseq+" \n");
    		rs = stmt.executeQuery( sb.toString() );
    		int cnt = 0;
    		if( rs.next() )
    		{
    			cnt = rs.getInt("CNT");
    		}
    		
    		if(cnt > 0){
    			
    			
    			sb = new StringBuffer();
        		sb.append(" UPDATE "+addName+"META SET MD_PSEQ = "+md_pseq+" WHERE MD_SEQ = "+md_seq+" \n");
        		stmt.executeUpdate(sb.toString());
    			
    			
    			sb = new StringBuffer();
        		sb.append(" SELECT COUNT(*) - 1 AS MD_SAME_COUNT FROM "+addName+"META WHERE MD_PSEQ = "+md_pseq+" \n");
        		
        		rs = stmt.executeQuery( sb.toString() );
        		
        		if( rs.next() )
        		{
        			sameCt = rs.getInt("MD_SAME_COUNT");
        		}
        		
        		sb = new StringBuffer();
        		sb.append(" UPDATE "+addName+"META SET MD_SAME_COUNT = "+sameCt+" WHERE MD_PSEQ = "+md_pseq+" \n");
        		stmt.executeUpdate(sb.toString());
        		
        		/*
        		sb = new StringBuffer();
        		sb.append(" UPDATE ISSUE_DATA SET MD_SAME_CT = "+sameCt+" WHERE MD_SEQ = "+md_pseq+" \n");
        		stmt.addBatch(sb.toString());
        		*/
        	
    		}
    		
    		
    	}catch(Exception ex) {
    		Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
    	} finally {
    		sb = null;
    		if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
    		if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
    	}
    	
    	return result;
    }
    
    static int sameRegist( String[] same)
    {
    	int result = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;

		try{
            sb = new StringBuffer();
            
            sb.append(" INSERT INTO "+addName+"SAME_FILTER (MD_SEQ, MD_TITLE, MD_DATE, S_SEQ) \n");
            sb.append(" VALUES ("+same[0]+", ?, FROM_UNIXTIME("+same[2]+"),? ) \n");

			pstmt = dbconn1.createPStatement(sb.toString());
			pstmt.setString(1, same[1]);
			pstmt.setString(2, same[3]);
			result = pstmt.executeUpdate();

		} catch(Exception ex) {
			Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); pstmt = null;} catch(SQLException ex) {}
        }
        
        return result;
    }
    
    //	문자열의 유사도 비교
	public static int StrCompare(String s1, String s2)
	{
		int repercent = 0;
		try{

			//공백 제거
			s1 = s1.replaceAll(" ","");
			s2 = s2.replaceAll(" ","");

			//소문자로 변환
			s1 = s1.toLowerCase();
			s2 = s2.toLowerCase();

			int lcs = 0;
			int ms = 0;
			int i = 0;
			int j = 0;

			//바이트로 읽어들임
			byte[] b1 = s1.getBytes();
			byte[] b2 = s2.getBytes();

			//바이트의 크기가져오기
			int m = b1.length;
			int n = b2.length;

			//[128][128]의 배열 생성
			int [][] LCS_Length_Table = new int[m+1][n+1];

			//배열 초기화
			for(i=1; i <  m; i++) LCS_Length_Table[i][0]=0;
			for(j=0; j < n; j++) LCS_Length_Table[0][j]=0;

			//루프를 돌며 바이트를 비교
			for (i=1; i <= m; i++) {
				for (j=1; j <= n; j++) {
					if ((b1[i-1]) == (b2[j-1])) {
						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j-1] + 1;
					} else if (LCS_Length_Table[i-1][j] >= LCS_Length_Table[i][j-1]) {
						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j];
					} else {
						LCS_Length_Table[i][j] = LCS_Length_Table[i][j-1];
					}
				}
			}

			//percent를 리턴
			ms = (m + n) / 2;
			lcs = LCS_Length_Table[m][n];
			repercent = ((lcs*100)/ms);

		}catch(ArithmeticException ex){
			
		}
		return repercent;

	}
	
	static String getSb_Seq(){
		String result = "";
    	
    	Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        try{
        	stmt = dbconn1.createStatement();
        	
			sb = new StringBuffer();
            sb.append("SELECT SB_SEQ FROM SB_SEQ_MAP		\n");
            
            Log.crond(name, sb.toString());
			rs = stmt.executeQuery(sb.toString());
            
			while(rs.next()){
				if("".equals(result)){
					result = rs.getString("SB_SEQ");
				}else{
					result += ","+rs.getString("SB_SEQ");
				}
			}
            
			
        } catch(Exception ex) {
        	Log.writeExpt(name,ex.toString());			
        	ex.printStackTrace();
			System.exit(1);
			
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }
        
        return result;
	}
}
