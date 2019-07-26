package risk.demon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import risk.DBconn.DBconn;
import risk.analysis.AutoAnalysis;
import risk.analysis.vo.ResultForm;
import risk.analysis.vo.SuccessForm;
import risk.util.Log;

public class DAEGURelationKeyword{
	
	private static DBconn subdbconn = null;
	private static AutoAnalysis a = new AutoAnalysis();
	
	public static void main(String args[]){
		
		Log.crond( "Start....................." );
		subdbconn = new DBconn();
		
		try {
			
			subdbconn.getSubDirectConnection();
			
			String last_id_seq = getLastUpdateNumber(); 
			
			List<Map> list = getIssueDataList(last_id_seq);
			
			Log.crond( "data size : "+ list.size() );
			Map map = null;
			String pat_seqs = null;
			String last_number =""; 
			for(int i=0; i < list.size(); i++){
				
				Log.crond( "data insert number : "+ (i+1) );
				pat_seqs = "";
				map = new HashMap();
				map = (HashMap)list.get(i);
				
				String seq = map.get("ID_SEQ").toString();
				String title = map.get("ID_TITLE").toString();
				String content = map.get("ID_CONTENT").toString();
				
				pat_seqs = relation_keyword_seq(title, content);
				
				insertIssueRelationKeyword( seq , pat_seqs);
				last_number = seq;
			}
			
			if( !"".equals(last_number) ){
				updateLastSeq(last_number);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}finally{
			if(subdbconn!= null){ try {subdbconn.close();} catch (SQLException e) {}}
			Log.crond( "END....................." );
		}
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
	
	
	public static void updateLastSeq(String id_seq){
		String result ="";
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("UPDATE LAST_ISSUE_SEQ SET ID_SEQ = "+id_seq+" , REG_DATE = NOW() WHERE SEQ = 1 \n");
			//System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			pstmt.executeUpdate();
			
		}catch (Exception ex){	
			Log.writeExpt("updateLastSeq","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
	}
	
	public static String getLastUpdateNumber(){
		String result ="";
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("SELECT ID_SEQ FROM LAST_ISSUE_SEQ LIMIT 1 \n");
			//System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString("ID_SEQ");
			}
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
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
	
	@SuppressWarnings("rawtypes")
	public static List<Map> getIssueDataList(String id_seq){
		List<Map> result = new ArrayList<Map>();
		Map<String, String> map = null;
		StringBuffer sb = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			sb = new StringBuffer();
			sb.append("SELECT ID_SEQ, ID_TITLE, ID_CONTENT										\n");
			sb.append(" FROM ISSUE_DATA                                                         \n");
			sb.append("	WHERE ID_SEQ > "+id_seq+" \n");
			//sb.append(" WHERE MD_DATE BETWEEN '2016-07-05 00:00:00' AND '2016-07-05 23:59:59'   \n");
			sb.append(" ORDER BY ID_SEQ ASC  				                                    \n");
			sb.append(" LIMIT 1000                                       						\n");
			//System.out.println(sb.toString());
			pstmt = subdbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				map = new HashMap<String, String>();
				map.put("ID_SEQ", rs.getString("ID_SEQ"));
				map.put("ID_TITLE", rs.getString("ID_TITLE"));
				map.put("ID_CONTENT", rs.getString("ID_CONTENT"));
				result.add(map);
			}
		}catch (Exception ex){	
			Log.writeExpt("IssueSameUpdate","P:"+ex.getCause()+"  "+ex.getMessage());
		}finally {
			try {if (rs != null) {rs.close();}} catch (SQLException ex) {}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException ex) {}		
		}
		
		return result;
	}
	
	
	public static String relation_keyword_seq(String id_title, String id_content){
		String result ="";
		String pat_seqs ="";
		//자동분석 메서드 (제목 + " " + 본문내용)
		SuccessForm sf = a.AutoAnalysis(id_title+ " "+ id_content);
		//자동분석 성공여부
		if(sf.isSuccess_type()){
			//분석된 이슈 타입 
			//System.out.println("successList : " + sf.getResultIc_type());
			String[] arrIcType = sf.getResultIc_type().split(",");
			if( sf.getResultIc_type().length() > 0 ){
				for(String icType : arrIcType){
					//이슈 타입별 분석된 이슈 코드와 노출횟수
					
					for(ResultForm f : sf.getResultList().get(Integer.parseInt(icType))){
								  //이슈 타입              //이슈 코드              //노출횟수           //노출 패턴
						//System.out.println(f.getIc_type() + " : " + f.getIc_code() + " : " + f.getCount() +" : "+f.getPat_seq());
						//System.out.println(f.getPat_seq());
						pat_seqs = f.getPat_seq();
					}
					System.out.println("#####################################################################");
				} 
			}
		}
		
		if(!"".equals(pat_seqs)){
			String tmp[] = pat_seqs.split(",");
			Set<String> ss = new HashSet();
			for(int i=0; i < tmp.length; i++){
				ss.add(tmp[i]);
			}
			
			for(String aa : ss){
				result += ","+aa;
			}
		}
		
		if(result.length() > 1){
			result = result.substring(1);
		}
		
		String tmp[] = result.split(",");
		
		String word = "";
		
		int keywordlangth = tmp.length;
		
		if(keywordlangth > 10){
			keywordlangth = 10;
		}
		
		for(int i = 0 ; i < keywordlangth ; i++){
			
			 if("".equals(word)){
				 word = tmp[i];
			 }else{
				 word += ","+tmp[i];
			 }
		}
		
		return word;
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
