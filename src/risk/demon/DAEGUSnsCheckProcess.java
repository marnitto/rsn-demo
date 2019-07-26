package risk.demon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.PersonalInfoUtil;

public class DAEGUSnsCheckProcess {
	
		/**
	     *  로컬 DB 컨넥션
	     * @return
	     */
	    static DBconn dbconn1 = null;
	    static String name = "DAEGUSnsCheckProcess";
	    static String addName = "";
	    static String s_seq = "2300475,2300476,2300477,2300478,2300479";
	    
	    public static void main( String[] args ){
	    	
	    	Log.crond(name, name + " START ...");
	    	
	    	try{
	    		dbconn1 = new DBconn(); //로컬디비
	        	dbconn1.getSubDirectConnection();
	        	
	        	
	        	DateUtil du = new DateUtil();
	        	String targetDay =  du.addDay_v2(du.getCurrentDate(), -1); 
	        	
	        	System.out.println(targetDay);
	        	
	        	Statement stmt = null;
	            ResultSet rs = null;
	            StringBuffer sb = null;
	            
	            try{
	            	stmt = dbconn1.createStatement();
	            	
	            	sb = new StringBuffer();
	                sb.append(" SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+targetDay+" 00:00:00' AND '"+targetDay+" 23:59:59' AND S_SEQ  IN ("+s_seq+"); 	\n");
	                System.out.println(sb.toString());
	    			rs = stmt.executeQuery(sb.toString());
	    			String md_seq = "";
	    			while(rs.next()){
	    				if("".equals(md_seq)){
	    					md_seq = rs.getString("MD_SEQ"); 	
	    				}else{
	    					md_seq += ","+rs.getString("MD_SEQ");
	    				}
	    			}
	    			
	    			rs = null;
	    			List list = new ArrayList();
	    			sb = new StringBuffer();
	    			sb.append(" SELECT MD_SEQ, K_XP FROM IDX WHERE MD_SEQ IN ("+md_seq+") AND K_XP NOT IN (14) GROUP BY MD_SEQ, K_XP 	\n");
	    			System.out.println(sb.toString());
	    			rs = stmt.executeQuery(sb.toString());
	    			String tmp[] = new String[2];
	    			while(rs.next()){
	    				tmp = new String[2];
	    				tmp[0] = rs.getString("MD_SEQ");
	    				tmp[1] = rs.getString("K_XP");
	    				list.add(tmp);
	    			}
	    			
	    			if(list.size() > 0){
	    				for(int i=0; i < list.size(); i++){
	    					String temp[] = (String[])list.get(i);
	    					sb = new StringBuffer();
	    					sb.append(" DELETE FROM IDX WHERE MD_SEQ= "+temp[0]+" AND K_XP = "+temp[1]+" 	\n");
	    					System.out.println(sb.toString());
	    					stmt.executeUpdate(sb.toString());
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
	        	
	        	
	        	

	    	}catch(Exception ex) {
	    		Log.writeExpt(name,ex.getMessage());
	        	ex.printStackTrace();
	        	System.exit(1);
	        }finally {
	            if (dbconn1 != null) try { dbconn1.close(); } catch(SQLException ex) {}
	        }
	    	
	    	Log.crond(name, name + " END ...");
	}

}
