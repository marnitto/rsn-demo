/**
========================================================
주 시 스 템 : RSN
서브 시스템 :
프로그램 ID : siteDataInfo
프로그램 명 : 사이트그룹 데이터 class
프로그램개요 : 사이트그룹 Data Beans
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.mobile;
/// SSL
import java.io.Reader;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;



//import risk.util.StringUtil;
import risk.DBconn.DBconn;
import risk.search.MetaBean;
import risk.search.replyInfo;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class PcAlimiMgr {

    DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;

    StringBuffer sb = null;

    public boolean checkIP( String psIP ) {
    	boolean result = false;
        try {
            sb = new StringBuffer();
           
            
            sb.append(" SELECT PA_SEQ \n");
            sb.append(" FROM PC_ALIMI_LOGIN_IP \n");
            sb.append(" WHERE PA_ACTIVE = 1 \n");
            sb.append(" AND PA_IP = '"+psIP+"' \n");
            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            Log.debug(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if ( rs.next() ) {
            	if(rs.getInt("PA_SEQ")>0){
            		result = true;
            	}
            }

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
        return result;
    }
    
	
}