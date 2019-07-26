package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.Log;

public class IssueCodeMgr {	
	
	public ArrayList arrAllType = new ArrayList();
	private static IssueCodeMgr instance = new IssueCodeMgr();
	
	public static IssueCodeMgr getInstance() {
		
		return instance;
	}
	
	
	public ArrayList getArrayListForTypeCode2(String kind){
		ArrayList list = new ArrayList(); 
		DBconn  conn  = null;
	    Statement stmt  = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    IssueCodeBean icBean= new IssueCodeBean();
	    
	    try {
	    	conn  = new DBconn();
	   	 	conn.getDBCPConnection();
	   	 	sb = new StringBuffer();
	   	 	stmt = conn.createStatement();
	
	   	    sb.append("SELECT IC_NAME, IC_TYPE, IC_CODE FROM ISSUE_CODE WHERE IC_TYPE = 2 AND IC_USEYN = 'Y' AND IC_CODE > 0	\n");
	   	    if(kind.equals("S")){
	   	    	sb.append("AND (IC_DESCRIPTION = 'C' OR IC_DESCRIPTION = 'S') ORDER BY IC_ORDER ASC \n");	
	   	    }else{
	   	    	sb.append("AND (IC_DESCRIPTION = 'C' OR IC_DESCRIPTION = 'H') ORDER BY IC_ORDER ASC \n");
	   	    }
	   	    
	   	    System.out.println(sb.toString());
	        rs = stmt.executeQuery(sb.toString());      
	        
	        while(rs.next()) {	       
	        	icBean= new IssueCodeBean();
	        	icBean.setIc_name(rs.getString("IC_NAME"));
	        	icBean.setIc_type(rs.getInt("IC_TYPE"));
	        	icBean.setIc_code(rs.getInt("IC_CODE"));
	        	list.add(icBean);
	    	}
	       
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	        if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	    }   
		
		return list;
	}
	
	public ArrayList getArrayListForOptionCode(String kind){
		ArrayList list = new ArrayList(); 
		DBconn  conn  = null;
	    Statement stmt  = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    IssueCodeBean icBean= new IssueCodeBean();
	    
	    try {
	    	conn  = new DBconn();
	   	 	conn.getDBCPConnection();
	   	 	sb = new StringBuffer();
	   	 	stmt = conn.createStatement();
	   	 	//sb.append("## 여기 입니다. \n");
	   	    sb.append("SELECT IC_NAME, IC_TYPE, IC_CODE FROM ISSUE_CODE WHERE IC_PTYPE = 1 AND IC_USEYN = 'Y' AND IC_CODE > 0 \n");
	   	    if(kind.equals("S")){
	   	    	sb.append("AND (IC_DESCRIPTION = 'C' OR IC_DESCRIPTION = 'S') ORDER BY IC_ORDER ASC \n");	
	   	    }else{
	   	    	sb.append("AND (IC_DESCRIPTION = 'C' OR IC_DESCRIPTION = 'H') ORDER BY IC_ORDER ASC \n");
	   	    }
	   	    
	        //Log.debug(sb.toString() );
	   	    System.out.println(sb.toString());
	        rs = stmt.executeQuery(sb.toString());      
	        
	        while(rs.next()) {	       
	        	icBean= new IssueCodeBean();
	        	icBean.setIc_name(rs.getString("IC_NAME"));
	        	icBean.setIc_type(rs.getInt("IC_TYPE"));
	        	icBean.setIc_code(rs.getInt("IC_CODE"));
	        	list.add(icBean);
	    	}
	       
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	        if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	    }   
		
		return list;
	}

	public ArrayList getArrayListForIssueCode(String ptype , String pcode){
		ArrayList list = new ArrayList(); 
		DBconn  conn  = null;
	    Statement stmt  = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    IssueCodeBean icBean= new IssueCodeBean();
	    
	    try {
	    	conn  = new DBconn();
	   	 	conn.getDBCPConnection();
	   	 	sb = new StringBuffer();
	   	 	stmt = conn.createStatement();
	   	 	sb.append("## 데이터 가져오기 2death \n");
	   	    sb.append("SELECT IC_NAME, IC_TYPE, IC_CODE FROM ISSUE_CODE WHERE IC_PTYPE = "+ptype+" AND IC_PCODE = "+pcode+" AND IC_USEYN = 'Y' AND IC_CODE > 0  ORDER BY IC_ORDER ASC \n");
	        //Log.debug(sb.toString() );
	   	    System.out.println(sb.toString());
	        rs = stmt.executeQuery(sb.toString());      
	        
	        while(rs.next()) {	       
	        	icBean= new IssueCodeBean();
	        	icBean.setIc_name(rs.getString("IC_NAME"));
	        	icBean.setIc_type(rs.getInt("IC_TYPE"));
	        	icBean.setIc_code(rs.getInt("IC_CODE"));
	        	list.add(icBean);
	    	}
	       
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	        if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	    }   
		
		return list;
	}
	
	public JSONObject getArrayListForIssueCode_Manual(String type5 , String type51){
		DBconn  conn  = null;
	    Statement stmt  = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
	    
	    JSONObject result = null;
	    
	    try {
	    	conn  = new DBconn();
	   	 	conn.getDBCPConnection();
	   	 	sb = new StringBuffer();
	   	 	stmt = conn.createStatement();
	   	 	sb.append("## 데이터 가져오기 수동맵핑 \n");
	   	    sb.append("SELECT TYPE3, TYPE31 FROM KEYWORD_MAP_MANUAL WHERE TYPE5 = "+type5+" AND TYPE51 = "+type51+" \n");
	        //Log.debug(sb.toString() );
	   	    System.out.println(sb.toString());
	        rs = stmt.executeQuery(sb.toString());      
	        
	        if(rs.next()) {
	        	result = new JSONObject();
	        	result.put("type3",rs.getString("TYPE3"));
	        	result.put("type31",rs.getString("TYPE31"));
	    	}
	       
	    } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	        if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	    }   
		
		return result;
	}
	
	/**
	 * mode : 0: 분류항목까지 포함, 1: 분류항목은 제외
	 * 
	 */	
	 public void init(int mode) {
		
		IssueCodeBean icb; 
		ArrayList arrEachType = null;
		arrEachType = new ArrayList();
		DBconn  conn  = null;
        PreparedStatement pstmt = null;
        Statement stmt  = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        this.arrAllType = new ArrayList();
        
        int prevType = 1;
        int nowType = 1;
        
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	sb = new StringBuffer();
       	 	stmt = conn.createStatement();

	        if(mode != 1){
	        	sb.append(" SELECT * FROM ISSUE_CODE WHERE IC_USEYN='Y' ORDER BY IC_TYPE, IC_ORDER ASC \n");
	        }else{
	        	sb.append(" SELECT * FROM ISSUE_CODE WHERE IC_CODE > 0 AND IC_USEYN='Y' ORDER BY IC_TYPE, IC_CODE, IC_ORDER ASC  \n");
	        }
        
	        Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());
	        /*pstmt = conn.createPStatement( sb.toString() );
	        rs = pstmt.executeQuery();*/
		
        
	        
	        while(rs.next()) {
	        	
	        	nowType = rs.getInt("ic_type");
	    		if ( prevType != nowType ) { 
	        		arrAllType.add(arrEachType);
	    			prevType = nowType;
	    			arrEachType = new ArrayList();
	    		}
    			
				icb = new IssueCodeBean();
				icb.setIc_seq(rs.getInt(		"IC_SEQ"	));
				icb.setIc_name(rs.getString(		"IC_NAME"	));
				icb.setIc_type( rs.getInt(		"IC_TYPE"	));
				icb.setIc_code(rs.getInt(		"IC_CODE"	));
				icb.setIc_ptype(rs.getInt(		"IC_PTYPE"	));
				icb.setIc_pcode(rs.getInt(		"IC_PCODE"	));
				icb.setM_seq(rs.getString(		"M_SEQ"		));
				icb.setIc_regdate(rs.getString(		"IC_REGDATE"	));
				icb.setIc_description(rs.getString(		"IC_DESCRIPTION"	));
			
				arrEachType.add(icb);			    		
	    	} // End while
	        arrAllType.add(arrEachType);
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
       
    }
	 public void initOld(int mode) {
		 
		 IssueCodeBean icb; 
		 ArrayList arrEachType = null;
		 arrEachType = new ArrayList();
		 DBconn  conn  = null;
		 PreparedStatement pstmt = null;
		 Statement stmt  = null;
		 ResultSet rs = null;
		 StringBuffer sb = null;
		 
		 this.arrAllType = new ArrayList();
		 
		 int prevType = 1;
		 int nowType = 1;
		 
		 
		 try {
			 conn  = new DBconn();
			 conn.getDBCPConnection();
			 sb = new StringBuffer();
			 stmt = conn.createStatement();
			 
			 if(mode != 1){
				 sb.append(" SELECT * FROM ISSUE_CODE_OLD WHERE IC_USEYN='Y' ORDER BY IC_TYPE, IC_CODE ASC \n");
			 }else{
				 sb.append(" SELECT * FROM ISSUE_CODE_OLD WHERE IC_CODE > 0 AND IC_USEYN='Y' ORDER BY IC_TYPE, IC_CODE ASC  \n");
			 }
			 
			 Log.debug(sb.toString() );
			 rs = stmt.executeQuery(sb.toString());
			 /*pstmt = conn.createPStatement( sb.toString() );
	        rs = pstmt.executeQuery();*/
			 
			 
			 
			 while(rs.next()) {
				 
				 nowType = rs.getInt("ic_type");
				 if ( prevType != nowType ) { 
					 arrAllType.add(arrEachType);
					 prevType = nowType;
					 arrEachType = new ArrayList();
				 }
				 
				 icb = new IssueCodeBean();
				 icb.setIc_seq(rs.getInt(		"IC_SEQ"	));
				 icb.setIc_name(rs.getString(		"IC_NAME"	));
				 icb.setIc_type( rs.getInt(		"IC_TYPE"	));
				 icb.setIc_code(rs.getInt(		"IC_CODE"	));
				 icb.setIc_ptype(rs.getInt(		"IC_PTYPE"	));
				 icb.setIc_pcode(rs.getInt(		"IC_PCODE"	));
				 icb.setM_seq(rs.getString(		"M_SEQ"		));
				 icb.setIc_regdate(rs.getString(		"IC_REGDATE"	));
				 icb.setIc_description(rs.getString(		"IC_DESCRIPTION"	));
				 
				 arrEachType.add(icb);			    		
			 } // End while
			 arrAllType.add(arrEachType);
		 } catch(SQLException ex) {
			 Log.writeExpt(ex, sb.toString() );
		 } catch(Exception ex) {
			 Log.writeExpt(ex);
		 } finally {
			 if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			 if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			 if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		 }
		 
	 }

	 
	 public ArrayList getSouceCodeOrder() {
			
		 
		 	ArrayList result = new ArrayList();
			IssueCodeBean icb; 
		
		
			DBconn  conn  = null;
	        PreparedStatement pstmt = null;
	        Statement stmt  = null;
	        ResultSet rs = null;
	        StringBuffer sb = null;
	        
	        
	        
	        try {
	        	conn  = new DBconn();
	       	 	conn.getDBCPConnection();
	       	 	sb = new StringBuffer();
	       	 	stmt = conn.createStatement();

	       	 	/*
	       	 	sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE = 0 AND IC_USEYN = 'Y')UNION\n");
	        	sb.append("(SELECT A.*					\n");
	        	sb.append("  FROM ISSUE_CODE A 			\n");
	        	sb.append("     , IC_S_RELATION B		\n");
	        	sb.append(" WHERE A.IC_SEQ = B.IC_SEQ	\n");
	        	sb.append("   AND A.IC_TYPE = 6 		\n");
	        	sb.append("   AND A.IC_CODE > 0 		\n");
	        	sb.append("   AND A.IC_USEYN = 'Y'		\n");
	        	sb.append(" ORDER BY  B.IC_ORDER)		\n");
	        	*/
	        	
	        	sb.append("SELECT A.* FROM\n");
	        	sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE <> 8  AND IC_USEYN = 'Y')A\n");
	        	sb.append("LEFT OUTER JOIN IC_S_RELATION B ON A.IC_SEQ = B.IC_SEQ ORDER BY B.IC_ORDER\n");
	        	
	        
		        Log.debug(sb.toString() );
		        rs = stmt.executeQuery(sb.toString());
		    
			
		        while(rs.next()) {
		
					icb = new IssueCodeBean();
					icb.setIc_seq(rs.getInt(		"IC_SEQ"	));
					icb.setIc_name(rs.getString(		"IC_NAME"	));
					icb.setIc_type( rs.getInt(		"IC_TYPE"	));
					icb.setIc_code(rs.getInt(		"IC_CODE"	));
					icb.setIc_ptype(rs.getInt(		"IC_PTYPE"	));
					icb.setIc_pcode(rs.getInt(		"IC_PCODE"	));
					icb.setM_seq(rs.getString(		"M_SEQ"		));
					icb.setIc_regdate(rs.getString(		"IC_REGDATE"	));
					icb.setIc_description(rs.getString(		"IC_DESCRIPTION"	));
					
					result.add(icb);
						    		
		    	} // End while
		        
	        } catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	        }
	        
	        return result;
	       
	    }
	 
	 /**
	 *	코드 와 사이트 매핑
	 * 
	 */	
	 public int getSiteMapCode(String s_seq) {		
		
		DBconn  conn  = null;
        Statement stmt  = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        int ic_seq = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	sb = new StringBuffer();
       	 	stmt = conn.createStatement();

       	    sb.append(" SELECT IC_SEQ FROM IC_S_RELATION WHERE S_SEQ = "+s_seq+" \n");
        
	        //Log.debug(sb.toString() );
	        rs = stmt.executeQuery(sb.toString());      
	        
	        if(rs.next()) {	        	
	        	ic_seq = rs.getInt("IC_SEQ");
	    	} // End while
	       
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }        
        return ic_seq;       
    }
	 
	
	
	 /**
	  * 해당타입의 코드 어레이 반환
	  * @return
	  */
	public ArrayList GetType(int type) {
		ArrayList arrEachType = null;
		IssueCodeBean isb = null;
		int returnType = 0;
		
		for (int i=0; i < arrAllType.size(); i++) {
			arrEachType = (ArrayList) arrAllType.get(i);
			isb = (IssueCodeBean) arrEachType.get(0);
			if (isb.getIc_type() == type) {
				returnType = i;
				break;
			}
		}
		if (returnType == 0) {
			return arrEachType;	
		}
		arrEachType = (ArrayList) arrAllType.get(returnType);		
		return arrEachType;
	}
	
	
	 /**
	  * 해당타입의 코드 어레이 반환
	  * @return
	  */
	public ArrayList GetType_mobile() {
		
		ArrayList arrEachType = null;
		IssueCodeBean isb = null;
		
		ArrayList result = new ArrayList();
		
		IssueCodeBean[] ar_type = new IssueCodeBean[8];
		
		for(int i =0; i < arrAllType.size(); i++){
			arrEachType = (ArrayList) arrAllType.get(i);
			isb = (IssueCodeBean) arrEachType.get(0);
			
			if (isb.getIc_type() == 6) {
			
				for(int j =0; j < arrEachType.size(); j++){
					isb = (IssueCodeBean) arrEachType.get(j);
					
					/*
					1	언론사
					6	포탈기사
					2	트위터
					3	블로그
					7	카페
					4	커뮤니티
					*/
					
					
					if(isb.getIc_code() == 0){
						ar_type[0] = isb;  
					}
					if(isb.getIc_code() == 1){
						isb.setIc_name("언론");
						ar_type[1] = isb;
					}
					if(isb.getIc_code() == 6){
						isb.setIc_name("포탈");
						ar_type[2] = isb;
					}
					if(isb.getIc_code() == 2){
						ar_type[3] = isb;
					}
					if(isb.getIc_code() == 3){
						ar_type[4] = isb;
					}
					if(isb.getIc_code() == 7){
						ar_type[5] = isb;
					}
					if(isb.getIc_code() == 4){
						ar_type[6] = isb;
					}
				}
				
			}
			
			if (isb.getIc_type() == 8) {
				
				for(int j =0; j < arrEachType.size(); j++){
					isb = (IssueCodeBean) arrEachType.get(j);
					
					if(isb.getIc_code() == 2){
						
						isb.setIc_code(isb.getIc_code()+ 8000);
						isb.setIc_name("긴급");
						ar_type[7] = isb;
						
					}
				}
			}
		}
		
		for(int i =0; i < ar_type.length; i++){
			if(ar_type[i] != null){
				result.add(ar_type[i]);
			}
		}

		return result;
	}
	
		
	/**
	 * @와,로 조합된 스트링으로 부터 해당 타입의 코드가 있는지 판단한다.
	 * @param typeCode : @와,로 구성된 코드 리스트 스트링 ("1,2@2,3@3,4")
	 * @param oneTypeCode : type, code 로 구성된 스트링 ("1,2")
	 * @return boolean
	 */
    public boolean isTypeCode(String typeCode, String oneTypeCode) {
    	boolean isFind = false;
    	if (!typeCode.equals("")) {
    		String[] code = typeCode.split("@");
    		for (int i=0; i<code.length; i++) {
    			if (code[i].trim().equals(oneTypeCode.trim())) {
    				isFind=true;
    				break;
    			}
    		}
    	}
    	return isFind;
    }
    
    /**
	 * 해당 타입으로  존재하는 코드  값반환
	 * @return
	 */
	public String getCodeVal(ArrayList arrType, int piType) {
		IssueCodeBean isb = null;
		String typeCode="";
		
		for (int i=0; i < arrType.size(); i++) {
			isb = (IssueCodeBean) arrType.get(i);
			if (isb.getIc_type() == piType) {
				typeCode = String.valueOf(isb.getIc_code());
				break;
			}
		}
		return typeCode;
	}
	
	/**
	 * 해당 타입으로  존재하는 타입,코드  값반환
	 * @return
	 */
	public String getTypeCodeVal(ArrayList arrType, int piType) {
		IssueCodeBean isb = null;
		String typeCode="";
		if(arrType.size() > 0){
			for (int i=0; i < arrType.size(); i++) {
				isb = (IssueCodeBean) arrType.get(i);
				if (isb.getIc_type() == piType) {
					typeCode = isb.getIc_type()+","+isb.getIc_code();
					break;
				}
			}
		}
		return typeCode;
	}
	
	/**
	 * 해당 타입으로  존재하는 타입,코드  값반환
	 * @return
	 */
	public String getTypeCodeValByKey(ArrayList arrType, int key) {
		IssueCodeBean isb = null;
		String typeCode="";
		
		for (int i=0; i < arrType.size(); i++) {
			isb = (IssueCodeBean) arrType.get(i);
			if (isb.getIc_seq() == key) {
				typeCode = isb.getIc_type()+","+isb.getIc_code();
				break;
			}
		}
		return typeCode;
	}
	
	/**
	 * 코드로 코드명을 반환
	 * @return
	 */
	public String GetCodeName(ArrayList arrType, int piCode) {
		IssueCodeBean isb = null;
		String codeName="";
		
		for (int i=0; i < arrType.size(); i++) {
			isb = (IssueCodeBean) arrType.get(i);
			if (isb.getIc_code() == piCode) {
				codeName = isb.getIc_name();
				break;
			}
		}
		return codeName;
	}
	
	/**
	 * 타입으로 코드명을 반환
	 * @return
	 */ 
	public String GetCodeNameType(ArrayList arrType, int piType) {
		IssueCodeBean isb = null;
		String codeName="";
		if(arrType != null){ 
			for (int i=0; i < arrType.size(); i++) {
				isb = (IssueCodeBean) arrType.get(i);
				if (isb.getIc_type() == piType) {
					codeName = isb.getIc_name();				
				}
			}
		}
		return codeName;
	}
	
	/**
	 * 이슈 분류의 타이틀을 반환
	 * */
	public String getCodeTitle() {
		IssueCodeBean isb = null;
		String codeName="";
		ArrayList arrlist = new ArrayList();
		
		for (int i=0; i < arrAllType.size(); i++) {
			arrlist = (ArrayList) arrAllType.get(i);
			
				if(codeName.equals("")){
					codeName = GetCodeName(arrlist,0);
				}else{
					codeName += ","+GetCodeName(arrlist,0);
				}
				
			}
		
		return codeName;
	}

	/**
	 * 이슈 코드의 전체 카운트를 반환한다.
	 * @return
	 */
	public int getTypeCount() {	return arrAllType.size();	}
	
	
	public int getLastCodePosition(ArrayList arData, int type){
		IssueCodeBean isb = null;
		int result = 0;
		for(int i = 0; i < arData.size(); i++){
			isb = (IssueCodeBean)arData.get(i);
			if(isb.getIc_type() == type ){
				result = isb.getIc_code();
				break;
			}
		}
		return result;
	}
	
	 public String getCodeName2(int icType, int icCode){
			DBconn  conn  = null;
		    Statement stmt  = null;
		    ResultSet rs = null;
		    StringBuffer sb = null;
		    
		    String codeName = "";
		    
		    try {
		    	conn  = new DBconn();
		   	 	conn.getDBCPConnection();
		   	 	sb = new StringBuffer();
		   	 	stmt = conn.createStatement();
		
		   	    sb.append("SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE= " + icType + " AND IC_CODE=" + icCode + "				\n");
		   	    rs = stmt.executeQuery(sb.toString());
		   	  
				while(rs.next()){
					codeName = rs.getString("IC_NAME");
				}
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	        }
	   	 	return codeName;
	    }
	 
	 public List getCodeList(){
			DBconn  conn  = null;
		    Statement stmt  = null;
		    ResultSet rs = null;
		    StringBuffer sb = null;
		    
		    List result = null;
		    result = new ArrayList();
		    Map map = new HashMap();
		    try {
		    	conn  = new DBconn();
		   	 	conn.getDBCPConnection();
		   	 	sb = new StringBuffer();
		   	 	stmt = conn.createStatement();
		
		   	    sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_CODE > 0 ORDER BY 1 ASC, 2 ASC, 3 ASC, 4 ASC	\n");
		   	    rs = stmt.executeQuery(sb.toString());
		   	  
				while(rs.next()){
					map = new HashMap();
					map.put("IC_TYPE", rs.getInt("IC_TYPE"));
					map.put("IC_CODE", rs.getInt("IC_CODE"));
					map.put("IC_NAME", rs.getString("IC_NAME"));
					map.put("IC_PTYPE", rs.getInt("IC_PTYPE"));
					map.put("IC_PCODE", rs.getInt("IC_PCODE"));
					result.add(map);
				}
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }
	 
	 public ArrayList getCodeList(String type, String code){
			DBconn  conn  = null;
		    Statement stmt  = null;
		    ResultSet rs = null;
		    StringBuffer sb = null;
		    
		    ArrayList result = null;
		    result = new ArrayList();
		    Map map = new HashMap();
		    try {
		    	conn  = new DBconn();
		   	 	conn.getDBCPConnection();
		   	 	sb = new StringBuffer();
		   	 	stmt = conn.createStatement();
		   	 	if(!"".equals(type) && !"".equals(code)){
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_TYPE IN ("+type+") AND IC_CODE IN ("+code+") ORDER BY 1 ASC, 2 ASC, 3 ASC, 4 ASC	\n");
		   	 		
		   	 	}else if("".equals(type) && !"".equals(code)){
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_CODE IN ("+code+") ORDER BY 1 ASC, 2 ASC, 3 ASC, 4 ASC	\n");
		   	 	}else if(!"".equals(type) && "".equals(code)){
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_TYPE IN ("+type+") ORDER BY 1 ASC, 2 ASC, 3 ASC, 4 ASC	\n");
		   	 	}else{
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_CODE > 0 ORDER BY 1 ASC, 2 ASC, 3 ASC, 4 ASC	\n");
		   	 	}
		   	 	System.out.println(sb.toString());
		   	    rs = stmt.executeQuery(sb.toString());
		   	  
				while(rs.next()){
					map = new HashMap();
					map.put("IC_TYPE", rs.getInt("IC_TYPE"));
					map.put("IC_CODE", rs.getInt("IC_CODE"));
					map.put("IC_NAME", rs.getString("IC_NAME"));
					map.put("IC_PTYPE", rs.getInt("IC_PTYPE"));
					map.put("IC_PCODE", rs.getInt("IC_PCODE"));
					result.add(map);
				}
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }
	 
	 
	 public ArrayList getCodeList(String type, String code, String order){
			DBconn  conn  = null;
		    Statement stmt  = null;
		    ResultSet rs = null;
		    StringBuffer sb = null;
		    
		    ArrayList result = null;
		    result = new ArrayList();
		    Map map = new HashMap();
		    try {
		    	conn  = new DBconn();
		   	 	conn.getDBCPConnection();
		   	 	sb = new StringBuffer();
		   	 	stmt = conn.createStatement();
		   	 	if(!"".equals(type) && !"".equals(code)){
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE,DATE_FORMAT(IC_REGDATE, '%Y-%m-%d') AS IC_REGDATE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_TYPE IN ("+type+") AND IC_CODE IN ("+code+") ORDER BY 1 "+order+", 2 "+order+", 3 "+order+", 4 "+order+"	\n");
		   	 		
		   	 	}else if("".equals(type) && !"".equals(code)){
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE,DATE_FORMAT(IC_REGDATE, '%Y-%m-%d') AS IC_REGDATE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_CODE IN ("+code+") ORDER BY 1 "+order+", 2 "+order+", 3 "+order+", 4 "+order+"	\n");
		   	 	}else if(!"".equals(type) && "".equals(code)){
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE,DATE_FORMAT(IC_REGDATE, '%Y-%m-%d') AS IC_REGDATE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_TYPE IN ("+type+") ORDER BY 1 "+order+", 2 "+order+", 3 "+order+", 4 "+order+"	\n");		   	 	
		   	 	}else{
		   	 		sb.append("SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE,DATE_FORMAT(IC_REGDATE, '%Y-%m-%d') AS IC_REGDATE FROM ISSUE_CODE WHERE IC_USEYN='Y' AND IC_CODE > 0 ORDER BY 1 "+order+", 2 "+order+", 3 "+order+", 4 "+order+"	\n");
		   	 	}
		   	 	System.out.println(sb.toString());
		   	    rs = stmt.executeQuery(sb.toString());
		   	  
				while(rs.next()){
					map = new HashMap();
					map.put("IC_TYPE", rs.getInt("IC_TYPE"));
					map.put("IC_CODE", rs.getInt("IC_CODE"));
					map.put("IC_NAME", rs.getString("IC_NAME"));
					map.put("IC_PTYPE", rs.getInt("IC_PTYPE"));
					map.put("IC_PCODE", rs.getInt("IC_PCODE"));
					map.put("IC_REGDATE", rs.getString("IC_REGDATE"));
					
					result.add(map);
				}
				
	   	 	}catch(SQLException ex) {
				Log.writeExpt(ex, sb.toString() );
	        } catch(Exception ex) {
				Log.writeExpt(ex);
	        } finally {
	            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	        }
	   	 	return result;
	    }
	 
}

