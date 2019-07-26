package risk.admin.alimi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.util.Log;

public class AlimiSettingDao {
	
	DBconn dbconn   = null;
    Statement stmt  = null;
    ResultSet rs    = null;
    
    public Hashtable getMobileAlimiSetting(String maType ) {
    	Hashtable htResult = new Hashtable();
    	
        StringBuffer sb = null;
        try {

            sb = new StringBuffer();

            sb.append("SELECT MA_SEQ, MA_ACTIVE, K_XP, SG_SEQ, MT_TYPE, MA_COUNT, MA_TYPE                                    \n");
            sb.append("FROM MOBILE_ALIMI_SETTING                                    \n");
            sb.append("WHERE MA_TYPE = "+maType+"                                    \n");


            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            
            rs = stmt.executeQuery(sb.toString());
            
            Log.debug(sb.toString() );
            
            if ( rs.next() ) {
            	htResult.put("MA_SEQ", rs.getString("MA_SEQ"));
            	htResult.put("MA_ACTIVE", rs.getString("MA_ACTIVE"));
            	htResult.put("K_XP", rs.getString("K_XP"));
            	htResult.put("SG_SEQ", rs.getString("SG_SEQ"));
            	htResult.put("MT_TYPE", rs.getString("MT_TYPE"));
            	htResult.put("MA_COUNT", rs.getString("MA_COUNT"));
            	htResult.put("MA_TYPE", rs.getString("MA_TYPE"));

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
        return htResult;
    }
    
    public int setMobileAlimiSetting(String maType, Hashtable pInput) {
    	
        StringBuffer sb = null;
        int result = 0;
        
        try {

            sb = new StringBuffer();

            sb.append("UPDATE MOBILE_ALIMI_SETTING                                  \n");
            sb.append("   SET K_XP     = '"+(String)pInput.get("K_XP")+"'           \n"); 
            sb.append("     , SG_SEQ   = '"+(String)pInput.get("SG_SEQ")+"'         \n");
            sb.append("     , MT_TYPE  = '"+(String)pInput.get("MT_TYPE")+"'        \n");
            sb.append("	    , MA_COUNT = '"+(String)pInput.get("MA_COUNT")+"'       \n");
            sb.append(" WHERE MA_TYPE = "+maType+"                                  \n");
            
            
            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
            Log.debug(sb.toString() );
            result = stmt.executeUpdate(sb.toString());
           
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
    
    public ArrayList getAlimiSttingList(){
    	ArrayList list = new ArrayList();
    	StringBuffer sb = null;
    	String tmp[] = null;
    	try {
    		dbconn = new DBconn();
            dbconn.getDBCPConnection();
            stmt = dbconn.createStatement();
            
            sb = new StringBuffer();
            sb.append("  SELECT AS_SEQ, AS_TITLE FROM ALIMI_SETTING ORDER BY 2 ASC      \n");
            Log.debug(sb.toString() );
            rs = stmt.executeQuery(sb.toString());
            
            while(rs.next()){
            	tmp = new String [2];
            	tmp[0] = rs.getString("AS_SEQ");
            	tmp[1] = rs.getString("AS_TITLE");
            	list.add(tmp);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex ) {
            Log.writeExpt(ex);
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return list;
    }
    
    
}
