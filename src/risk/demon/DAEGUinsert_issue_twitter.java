package risk.demon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;

public class DAEGUinsert_issue_twitter{
	
	
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException, Exception{
		
		dbconn1 = new DBconn(); //로컬디비
		dbconn1.getSubDirectConnection();
		
		DateUtil du = new DateUtil();
		String currentDay = du.getCurrentDate(); 
		String fromDay = du.addDay(currentDay, -30);
		int[] seqs = new int[2];
		
		ArrayList dataList = new ArrayList();
		
		System.out.println("Issue_Twiiter 테이블 조회==============");
		dataList = getEmptyIssueTwitter(fromDay, currentDay);
		
		if(dataList.size() > 0 ){
			
			for(int i=0; i < dataList.size(); i++){
				seqs = (int [])dataList.get(i);
				
				insertTwitterData(seqs[0], seqs[1]);
				
			}
			
		}else{
			
			System.out.println("dataList.size() - "+dataList.size());
			System.out.println("No empty Data");
		}
		
		
	}
	
	static DBconn dbconn1 = null;
	
	static ArrayList getEmptyIssueTwitter(String fromDay, String currentDay)
    {       
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
    	ArrayList arrContents = new ArrayList();
		int temp[] = new int[2];
		
		try{					
			
			sb = new StringBuffer();
			sb.append(" SELECT                                                                               \n");
			sb.append(" 		AA.*                                                                         \n");
			sb.append(" FROM                                                                                 \n");
			sb.append(" (                                                                                    \n");
			sb.append(" 	SELECT                                                                               \n");
			sb.append("           A.ID_SEQ                                                                             \n");
			sb.append("          ,A.MD_SEQ                                                                            \n");
			sb.append("          ,B.T_USER_ID                                                                         \n");
			sb.append("       FROM                                                                                 \n");
			sb.append("           (SELECT                                                                              \n");
			sb.append("                   ID_SEQ                                                                               \n");
			sb.append("                  ,MD_SEQ                                                                              \n");
			sb.append("              FROM                                                                                 \n");
			sb.append("                   ISSUE_DATA                                                                           \n");
			sb.append("             WHERE S_SEQ = 10464                                                                  \n");
			sb.append("               AND MD_DATE BETWEEN '"+fromDay+" 00:00:00' AND '"+currentDay+" 23:59:59')                 \n");
			sb.append("            A                                                                                    \n");
			sb.append("            LEFT OUTER JOIN                                                                      \n");
			sb.append("            ISSUE_TWITTER B                                                                      \n");
			sb.append("            ON A.ID_SEQ = B.ID_SEQ                                                               \n");
			sb.append("     ORDER BY 3                                                                           \n");
			sb.append(" ) AA                                                                                 \n");
			sb.append(" WHERE AA.T_USER_ID IS NULL					             							 \n");
			System.out.println(sb.toString());
			stmt = dbconn1.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ){
				temp = new int[2];
				temp[0] = rs.getInt("ID_SEQ");
				temp[1] = rs.getInt("MD_SEQ");
				arrContents.add(temp);
			}

		} catch(SQLException ex) {
			Log.crond(ex.toString());			
			System.exit(1);
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
        	sb = null;
            if (rs != null) try { rs.close(); rs = null;} catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); stmt = null;} catch(SQLException ex) {}
        }     
    	
    	return arrContents;
    }
	   
	   
	static void insertTwitterData(int id_seq, int md_seq){
	    
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;       

		try{
            sb = new StringBuffer();
            sb.append("INSERT INTO ISSUE_TWITTER	\n");
            sb.append("(                            \n");
            sb.append("SELECT                       \n");
            sb.append(" "+id_seq+" AS ID_SEQ               \n");
            sb.append(",T_TWEET_ID	,T_USER_ID	,T_IS_RT	,T_FOLLOWERS	,T_FOLLOWING	,T_TWEET	,T_SOURCE	,T_NAME	,T_PROFILE_IMAGE	\n");
            sb.append("FROM TWEET WHERE MD_SEQ = "+md_seq+" )	\n");
			//System.out.println( sb.toString() );
			stmt = dbconn1.createStatement();
			if( stmt.executeUpdate( sb.toString() ) > 0 ) {
				System.out.println("저장 성공 - "+id_seq);
			}else{
				System.out.println("저장 실패 : id_seq - "+id_seq + "    /    md_seq - "+md_seq);
			}

		} catch(SQLException ex) {
			Log.crond(ex.toString());
			System.exit(1);
        } catch(Exception ex) {
        	Log.crond(ex.toString());
        	ex.printStackTrace();
        	System.exit(1);
        } finally {
        	System.out.println("종료=========================================");
            sb = null;
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
        }
	}
	
	
}
