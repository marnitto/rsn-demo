/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 검색메뉴
프로그램 ID : GetTreeKeyword.class
프로그램 명 : GetTreeKeyword
프로그램개요 : keyword 테이블에서 키워드 그룹과 키워드를 가져와
			   json타입의 데이터로 세팅한다. 
작 성 자 : 성민석
작 성 일 : 2015.02.25
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import risk.DBconn.DBconn;
import risk.json.JSONObject;
import risk.util.Log;

public class GetTreeKeyword {

	String baseURL = "";
	String baseTarget = "";
	StringBuffer sb = null;
	
	public String msMinNo   = "";   //검색 시작 MD_SEQ
    public String msMaxNo   = "";   //검색 마지막 MD_SEQ
    
	DBconn  dbconn  = null; // db connection
    Statement stmt  = null; //oracle
    ResultSet rs    = null; //oracle
    String sql = ""; // sql statement

    int selectedXP = 0;
	int selectedYP = 0;
	int selectedZP = 0;

	// 메뉴 클릭시 이동할 페이지를 지정한다.
	public void setBaseURL(String url) {
		baseURL = url;
	}
	// 메뉴 클릭시 이동할 target을 정한다.
	public void setBaseTarget(String target) {
		baseTarget = target;
	}

	// 최초 선택된 xp, yp, zp를 지정하고, 지정된 항목을 강조한다.
	public void setSelected(int xp, int yp, int zp){
		selectedXP = xp;
		selectedYP = yp;
		selectedZP = zp;
	}

	//기간동안의 META 테이블의 MIN,MAX 값
	
	public void getMaxMinNo( String psSDate, String psEdate) {
		getMaxMinNo(psSDate, psEdate, "" );
	}
	public void getMaxMinNo( String psSDate, String psEdate, String stName ) {

        try {
            sb = new StringBuffer();
            
            sb.append(" SELECT (SELECT MD_SEQ FROM "+stName+"META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM "+stName+"META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
                                  
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            System.out.println(sb.toString());

            if ( rs.next() ) {
                msMinNo = rs.getString("MIN_NO");
                msMaxNo = rs.getString("MAX_NO");
            }
            //Log.debug("msMinNo :" + msMinNo );
            //Log.debug("msMaxNo :" + msMaxNo );

            sb = null;
        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    }

    public String convTypeCodeToSQL( String psTypeCode ) {

        String sTypeCondition = "";

        if ( psTypeCode.equals("1") ) {
            sTypeCondition = "  AND MT_TYPE = 1 ";
        } else if ( psTypeCode.equals("2") ) {
            sTypeCondition = "  AND MT_TYPE = 2 ";
        } else if ( psTypeCode.equals("4") ) {
            sTypeCondition = "  AND MT_TYPE = 3 ";
        } else if ( psTypeCode.equals("3") ) {
            sTypeCondition = "  AND MT_TYPE <= 2 ";
        } else if ( psTypeCode.equals("5") ) {
            sTypeCondition = "  AND (MT_TYPE = 1 OR MT_TYPE = 3) ";
        } else if ( psTypeCode.equals("6") ) {
            sTypeCondition = "  AND MT_TYPE >= 2 ";
        } else if ( psTypeCode.equals("7") ) {
            sTypeCondition = "";
        }

        return sTypeCondition;
    }
    

    //정보검색 키워드 트리를 만든다.
	
	public JSONObject GetHtml( String psDateFrom,String psDateTo,String MGxp, String showCount, String stName ) {
		
		JSONObject obj = new JSONObject();

		StringBuffer sbhtml = null;
        StringBuffer sb = null;
        
        String MinMtno      = "";
        String MaxMtno      = "";

		int xp=0;
		int yp=0;
		int zp=0;
		int op=0;
		int prevxp = 0;
		int prevyp = 0;
		int prevzp = 0;
		int keyword_cnt = 0;
		try {
			
			if(! showCount.equals("false") ){
				getMaxMinNo( psDateTo, psDateTo, stName );
			}

            MinMtno= msMinNo;
            MaxMtno= msMaxNo;
      

            /**************************************
             	 DB 직접연결
            **************************************/
            dbconn  = new DBconn();
            dbconn.getDBCPConnection();
            stmt    = dbconn.createStatement();

            sb = new StringBuffer();
            sbhtml = new StringBuffer();
            
	            if( showCount.equals("false") ){
	                
	                sb.append(" SELECT K_XP, K_YP, K_ZP,K_OP, K_VALUE  	\n");
	                sb.append(" FROM KEYWORD 						\n");
	                sb.append(" WHERE K_TYPE < 3 					\n");
	                if( MGxp.length() > 0 )
	                sb.append("   AND K_XP IN ("+MGxp+") 			\n");
	                sb.append("   AND K_USEYN = 'Y'       			\n");
	                sb.append(" ORDER BY K_POS, K_XP, K_YP, K_ZP 	\n");
	            }else {   
	            	String tempStr = null;
	            	
	                if( MGxp.length() > 0 ) tempStr = " AND B1.K_XP IN ("+MGxp+") ";
	                else tempStr = "";
	                sb.append(" SELECT B1.K_VALUE, B1.K_XP, B1.K_YP, B1.K_ZP, B1.K_OP, IFNULL(B2.CNT,0) AS CNT                                \n");
	                sb.append("   , ( SELECT COUNT(*) FROM KEYWORD WHERE K_XP = B1.K_XP AND K_YP=B1.K_YP AND K_ZP > 0 AND  K_USEYN='Y') AS K_CNT \n");
	                sb.append(" FROM                                                                                                 \n");
	                sb.append(" KEYWORD B1 LEFT OUTER JOIN                                                                           \n");
	                sb.append(" (                                                                                                    \n");
	                sb.append(" 	SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
	                sb.append("     FROM "+stName+"META A1,                                                                                    \n");
	                sb.append("       (                                                                                              \n");
	                sb.append("         SELECT DISTINCT K_XP, 0 AS K_YP, 0 AS K_ZP, MD_SEQ                                           \n");
	                sb.append("         FROM "+stName+"IDX                                                                                     \n");
	                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
	                sb.append("         	  AND I_STATUS = 'N'                                               							 \n");
	                sb.append("       ) A2                                                                                           \n");
	                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
	                sb.append("     GROUP BY K_XP                                                                                    \n");
	                sb.append(" 		UNION                                                                                \n");
	                sb.append(" 		SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
	                sb.append("     FROM "+stName+"META A1,                                                                                    \n");
	                sb.append("       (                                                                                              \n");
	                sb.append("         SELECT DISTINCT K_XP, K_YP, 0 AS K_ZP, MD_SEQ                                                \n");
	                sb.append("         FROM "+stName+"IDX                                                                                     \n");
	                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
	                sb.append("         	  AND I_STATUS = 'N'                                               							 \n");
	                sb.append("       ) A2                                                                                           \n");
	                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
	                sb.append("     GROUP BY K_XP, K_YP                                                                              \n");
	                sb.append(" 		UNION                                                                                \n");
	                sb.append(" 		SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
	                sb.append("     FROM "+stName+"META A1,                                                                                    \n");
	                sb.append("       (                                                                                              \n");
	                sb.append("         SELECT DISTINCT K_XP, K_YP, K_ZP, MD_SEQ                                                     \n");
	                sb.append("         FROM "+stName+"IDX                                                                                     \n");
	                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
	                sb.append("         	  AND I_STATUS = 'N'                                               							 \n");
	                sb.append("       ) A2                                                                                           \n");
	                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
	                sb.append("     GROUP BY K_XP, K_YP, K_ZP                                                                        \n");
	                sb.append(" 	) B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP                              \n");
	                sb.append(" 	WHERE B1.K_TYPE<3  "+tempStr+"  AND B1.K_USEYN = 'Y'                                \n");
	                sb.append(" ORDER BY B1.K_POS, B1.K_XP, B1.K_YP, B1.K_ZP ASC                                                     \n");
	            }           	
	            System.out.println(sb.toString());
	            rs = stmt.executeQuery( sb.toString() );
	            obj.put("baseURL", baseURL);
				while(rs.next()){
					if(!rs.getString("K_XP").equals("") && rs.getString("K_YP").equals("0") && rs.getString("K_ZP").equals("0")){
						obj.put("name", rs.getString("K_VALUE"));
						obj.put("params", "&xp="+rs.getString("K_XP")+"&yp="+rs.getString("K_YP")+"&zp="+rs.getString("K_ZP"));
						obj.put("option", rs.getString("K_OP"));
						obj.put("cnt", rs.getString("CNT"));
					}

				}
		
        } catch (SQLException ex) {
            Log.writeExpt(ex);
        } catch (Exception ex) {
            Log.writeExpt(ex);
            //강제 종료
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }       
		return obj;
	}
	
	
}