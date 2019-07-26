package risk.sms;

//기본 참조
import risk.util.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.lang.String;

//사용 참조
import risk.DBconn.DBconn;

public class AddressBookDao {
	private	DBconn  dbconn  = null;
	private	Statement stmt = null;
	private	ResultSet rs   = null;
	private StringBuffer sb = null;
	private AddressBookBean abBean = new AddressBookBean();

	//SMS 수신할 사용자 정보(ArrayList)를 반환하는 함수
	//사용자일련번호,이름,핸드폰번호,수신할 키워드그룹,수신할 사이트그룹,수신할 접수매체 유형
	public AddressBookDao(){
		 dbconn  = new DBconn();        
	     sb = new StringBuffer();   
	}
	
	public List getAdressBookGroup()
    {
    	AddressBookGroupBean abgBean = new AddressBookGroupBean();
    	List arrBean = new ArrayList();
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;		
		
		try{
			conn  = new DBconn();
	    	conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			
			sb = new StringBuffer();
			sb.append("SELECT AG_SEQ, AG_NAME FROM ADDRESS_BOOK_GROUP ORDER BY AG_SEQ ASC \n");			
			
			pstmt = conn.createPStatement( sb.toString() );
	        rs = pstmt.executeQuery();
	        
	        while(rs.next())
	        {
	        	abgBean = new AddressBookGroupBean();
	        	abgBean.setAg_seq(rs.getString("AG_SEQ"));
	        	abgBean.setAg_name(rs.getString("AG_NAME"));
	        	
	        	arrBean.add(abgBean);
	        }	        
	        
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}    	
    	return arrBean;
    }
	
	public String smsurlsearch(String id ) {
		String result = "";

		try {

            sb = new StringBuffer();
            
            sb.append(" SELECT MD_URL FROM META WHERE MD_SEQ = "+id+" \n");
            
            System.out.println(sb.toString());
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());

            if( rs.next() ) {
            	result = rs.getString("MD_URL");
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
	
    public boolean insertAdressBookGroup(AddressBookGroupBean abgBean)
    {
    	boolean result = false;
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
    	try{
			conn  = new DBconn();
	    	conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			
			sb = new StringBuffer();
			sb.append("INSERT INTO ADDRESS_BOOK_GROUP(AG_NAME) VALUES('"+abgBean.getAg_name()+"') \n");			

			pstmt = conn.createPStatement( sb.toString() );
			if(pstmt.executeUpdate()>0) result = true;  	       	        
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
    	return result;
    }
    
    public boolean deleteAdressBookGroup(AddressBookGroupBean abgBean)
    {
    	boolean result = false;
    	DBconn  conn  = null;
        PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
    	try{
			conn  = new DBconn();
	    	conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			
			sb = new StringBuffer();
			sb.append("DELETE FROM ADDRESS_BOOK_GROUP WHERE AG_SEQ IN ("+abgBean.getAg_seq()+")\n");			
			
			pstmt = conn.createPStatement( sb.toString() );
			if(pstmt.executeUpdate()>0) result = true;       	       	        
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
    	return result;
    }


    /**
     * 주소록을 검색하여 배열로 결과값을 리턴
     * @param ab_seq
     * @param ab_name
     * @param ab_dept
     * @param ab_issue_dept
     * @param ab_sms_receivechk
     * @param ab_report_day_chk
     * @param ab_report_week_chk
     * @return
     */
	 public ArrayList getAddressList( String ab_seq,
									  String ab_name,
									  String ab_dept,									 
									  String ab_issue_chk,
									  String ab_report_day_chk,
									  String ab_report_week_chk 			 
									)
	{
		 return getAddressList(ab_seq,ab_name,ab_dept,ab_issue_chk,ab_report_day_chk,ab_report_week_chk,"","");
	}
	
	public ArrayList getAddressList( String ab_seq,
									 String ab_name,
									 String ab_dept,									
									 String ab_issue_chk,
									 String ab_report_day_chk,
									 String ab_report_week_chk,
									 String ag_seq
									)
	{
		return getAddressList(ab_seq,ab_name,ab_dept,ab_issue_chk,ab_report_day_chk,ab_report_week_chk,ag_seq,"");
	}    
    
    public ArrayList getAddressList( String ab_seq,
    								 String ab_name,
    								 String ab_dept,    								 
    								 String ab_issue_chk,
    								 String ab_report_day_chk,
    								 String ab_report_week_chk,
    								 String ag_seq,
    								 String selectedAbSeq
    								)
    {
    	ArrayList result = null;    	
		try {			
			String check = ab_seq+ab_name+ab_dept+ab_issue_chk+ab_report_day_chk+ab_report_week_chk;
			String whereQuery = "";
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			sb.append("SELECT AB_SEQ\n");
			sb.append("       ,AB_NAME \n");
			sb.append("       ,AG_SEQ \n");
			sb.append("       ,AB_DEPT \n");
			sb.append("       ,AB_POSITION \n");
			sb.append("		  ,AB_MOBILE \n");
			sb.append(" 	  ,AB_ISSUE_CHK \n");
			sb.append("		  ,AB_REPORT_DAY_CHK  \n");
			sb.append("		  ,AB_REPORT_WEEK_CHK \n");
			sb.append("		  ,AB_SMS_CHK \n");
			sb.append("FROM ADDRESS_BOOK \n");
			sb.append("WHERE 1=1 \n");
			
			if( ab_seq.length() > 0 )
			whereQuery += " AND AB_SEQ IN ("+ab_seq+") ";
			
			if( ab_name.length() > 0 ) {
				whereQuery += "AND  (AB_NAME LIKE '%"+ab_name+"%' OR AB_DEPT LIKE '%"+ab_name+"%')  ";
			}			
			if( ab_dept.length() > 0 ) {
				whereQuery += "AND AB_DEPT LIKE '%"+ab_dept+"%' ";
			}
			if( ab_issue_chk.length() > 0 ) {			
				whereQuery += "AND AB_ISSUE_CHK = "+ab_issue_chk+" ";
			}			
			if( ab_report_week_chk.length() > 0 ) {				
				whereQuery += "AND AB_REPORT_WEEK_CHK = '"+ab_report_week_chk+"' ";
			}
			if( ab_report_day_chk.length() > 0 ) {				
				whereQuery += "AND AB_REPORT_DAY_CHK = '"+ab_report_day_chk+"' ";
			}			
			if( ab_report_week_chk.length() > 0 ) {				
				whereQuery += "AND AB_REPORT_WEEK_CHK = '"+ab_report_week_chk+"' ";
			}
			if( ag_seq.length() > 0 ) {
				whereQuery += " AND AG_SEQ = "+ag_seq+" ";
			}	
			
			if( selectedAbSeq.length() > 0 ) {
				whereQuery += " AND AB_SEQ NOT IN ("+selectedAbSeq+") ";
			}
			sb.append(whereQuery);
		
			System.out.println((sb.toString()));
			rs = stmt.executeQuery(sb.toString());
			
			result = new ArrayList();
	        while(rs.next())
	        {	        	
	        	//핸드폰번호 특수문자 제거
	        	String replaceString =  rs.getString("AB_MOBILE");
	        	replaceString = replaceString.replaceAll("-", "");
	        	replaceString = replaceString.replaceAll("\\.", "");
	        	replaceString = replaceString.replaceAll(" ", "");
	        	replaceString = replaceString.replaceAll("_", "");
	        	replaceString = replaceString.replaceAll("\'", "");
	        	replaceString = replaceString.replaceAll("\"", "");
	        	
	        	abBean = new AddressBookBean();
	        	abBean.setMab_seq(rs.getInt("AB_SEQ"));
	        	abBean.setMag_seq(rs.getString("AG_SEQ"));
	        	abBean.setMab_name(rs.getString("AB_NAME"));
	        	abBean.setMab_dept(rs.getString("AB_DEPT"));
	        	abBean.setMab_pos(rs.getString("AB_POSITION"));
	        	abBean.setMab_mobile(rs.getString("AB_MOBILE"));
	        	abBean.setMab_issue_receivechk(rs.getString("AB_ISSUE_CHK"));
	        	abBean.setMab_report_day_chk(rs.getString("AB_REPORT_DAY_CHK"));
	        	abBean.setMab_report_week_chk(rs.getString("AB_REPORT_WEEK_CHK"));
	        	abBean.setMab_sms_chk(rs.getString("AB_SMS_CHK"));
	        	result.add(abBean);  				      						        	
	        }
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
    	return result;
    }
    
    public ArrayList getInfoReceiverList(String ag_seq){
    	ArrayList result = null;    	
		try {			
			
			String whereQuery = "";
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
		
			sb = new StringBuffer();
			sb.append("SELECT  A.AB_SEQ\n");
			sb.append("       ,A.AB_NAME \n");
			sb.append("       ,A.AG_SEQ \n");
			sb.append("       ,A.AB_DEPT \n");
			sb.append("       ,A.AB_POSITION \n");
			sb.append("		  ,A.AB_MOBILE \n");
			sb.append(" 	  ,A.AB_ISSUE_CHK \n");
			sb.append("		  ,A.AB_REPORT_DAY_CHK  \n");
			sb.append("		  ,A.AB_REPORT_WEEK_CHK \n");
			sb.append("		  ,A.AB_SMS_CHK \n");
			sb.append("		  ,B.AG_NAME \n");
			sb.append("FROM ADDRESS_BOOK A, ADDRESS_BOOK_GROUP B \n");
			sb.append("WHERE A.AG_SEQ = B.AG_SEQ \n");
			if( ag_seq.length() > 0 ) {
				whereQuery += " AND A.AG_SEQ = "+ag_seq+" \n";
			}	
			sb.append(whereQuery);
			sb.append("ORDER BY AB_NAME ASC \n");
		
			System.out.println((sb.toString()));
			rs = stmt.executeQuery(sb.toString());
			
			result = new ArrayList();
	        while(rs.next())
	        {	        	
	        	//핸드폰번호 특수문자 제거
	        	String replaceString =  rs.getString("AB_MOBILE");
	        	replaceString = replaceString.replaceAll("-", "");
	        	replaceString = replaceString.replaceAll("\\.", "");
	        	replaceString = replaceString.replaceAll(" ", "");
	        	replaceString = replaceString.replaceAll("_", "");
	        	replaceString = replaceString.replaceAll("\'", "");
	        	replaceString = replaceString.replaceAll("\"", "");
	        	
	        	abBean = new AddressBookBean();
	        	abBean.setMab_seq(rs.getInt("AB_SEQ"));
	        	abBean.setMag_seq(rs.getString("AG_SEQ"));
	        	abBean.setMab_name(rs.getString("AB_NAME"));
	        	abBean.setMab_dept(rs.getString("AG_NAME"));
	        	abBean.setMab_pos(rs.getString("AB_POSITION"));
	        	abBean.setMab_mobile(rs.getString("AB_MOBILE"));
	        	abBean.setMab_issue_receivechk(rs.getString("AB_ISSUE_CHK"));
	        	abBean.setMab_report_day_chk(rs.getString("AB_REPORT_DAY_CHK"));
	        	abBean.setMab_report_week_chk(rs.getString("AB_REPORT_WEEK_CHK"));
	        	abBean.setMab_sms_chk(rs.getString("AB_SMS_CHK"));
	        	result.add(abBean);  				      						        	
	        }
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
    	return result;
    }
	
	
	public int addressCount( String psSearchWord )
	{
		int reCount = 0;
		
		try {
			sb = new StringBuffer();
			dbconn.getDBCPConnection();
			sb.append(" 	 SELECT COUNT(1) AS ACOUNT     \n");
			sb.append(" 	 FROM ADDRESS_BOOK     \n");			
			if( !psSearchWord.equals("")) {
				sb.append("  WHERE AB_NAME LIKE '%"+psSearchWord+"%'     \n");
			}
	        
	        stmt = dbconn.createStatement();       
	        //System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
	        		        
	        if(rs.next())
	        {
	        	reCount = rs.getInt("ACOUNT");
	        }
			
		}catch (SQLException ex) {
		  Log.writeExpt(ex);
		}catch (Exception ex) {
          Log.writeExpt(ex);
		} finally {
          try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
          try { if( rs  != null) rs.close();      } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
          try { if( stmt  != null) stmt.close();  } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
		}
      return reCount;
	}	
	
	
	public ArrayList addressList( int piNowpage, int piRowCnt, String psSearchWord )
	{		
		ArrayList arr = new ArrayList();
		int liststart;
     	int listend;
     	
     	if (piNowpage == 0) {
     		piNowpage = 1;
     	}
     	
     	liststart = (piNowpage-1) * piRowCnt;
     	listend = piNowpage * piRowCnt;
     	listend = 10;	 
		try {
			sb = new StringBuffer();
			dbconn.getDBCPConnection();
			
			sb.append(" SELECT AB_SEQ     \n");
			sb.append(" 	   ,AB_NAME     \n"); 
			sb.append(" 	   ,AB_DEPT     \n");
			sb.append(" 	   ,AB_POSITION     \n");
			sb.append(" 	   ,AB_MOBILE     \n");
			sb.append(" 	   ,AB_MAIL     \n");	
			sb.append(" 	   ,AB_ISSUE_CHK     \n");
			sb.append(" 	   ,AB_REPORT_DAY_CHK     \n");
			sb.append(" 	   ,AB_REPORT_WEEK_CHK     \n");			
			sb.append(" FROM  ADDRESS_BOOK  \n");
			if( !psSearchWord.equals("")) {
				sb.append("WHERE  LOWER(AB_NAME) LIKE '%"+psSearchWord.toLowerCase()+"%'     \n");
			}
			sb.append(" LIMIT   "+liststart+","+listend+"    \n");
			System.out.println(sb.toString());
	        stmt = dbconn.createStatement();  
		    rs = stmt.executeQuery(sb.toString());
	        		        
	        while(rs.next())
	        {
	        	//핸드폰번호 특수문자 제거
	        	String replaceString =  rs.getString("AB_MOBILE");
	        	replaceString = replaceString.replaceAll("-", "");
	        	replaceString = replaceString.replaceAll("\\.", "");
	        	replaceString = replaceString.replaceAll(" ", "");
	        	replaceString = replaceString.replaceAll("_", "");
	        	replaceString = replaceString.replaceAll("\'", "");
	        	replaceString = replaceString.replaceAll("\"", "");
	        	
	        	abBean = new AddressBookBean();
	        	abBean.setMab_seq(rs.getInt("AB_SEQ"));
	        	abBean.setMab_name(rs.getString("AB_NAME"));
	        	abBean.setMab_dept(rs.getString("AB_DEPT"));
	        	abBean.setMab_pos(rs.getString("AB_POSITION"));
	        	abBean.setMab_mobile(rs.getString("AB_MOBILE"));
	        	abBean.setMab_mail(rs.getString("AB_MAIL"));
	        	abBean.setMab_issue_receivechk(rs.getString("AB_ISSUE_CHK"));
	        	abBean.setMab_report_day_chk(rs.getString("AB_REPORT_DAY_CHK"));
	        	abBean.setMab_report_week_chk(rs.getString("AB_REPORT_WEEK_CHK"));
	        	arr.add(abBean);
	        }//WHILE 루프
			
		}catch (SQLException ex) {
		  Log.writeExpt(ex.getMessage());
		}catch (Exception ex) {
          Log.writeExpt(ex.getMessage());
		} finally {
          try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
          try { if( rs  != null) rs.close();      } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
          try { if( stmt  != null) stmt.close();  } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
		}
      return arr;
	}
	
	public AddressBookBean Getaddress( int piAbSeq )
	{		
		ArrayList arr = new ArrayList();
		AddressBookBean reAddBean = null;
		int liststart;
     	int listend;
     		 
		try {
			sb = new StringBuffer();
			dbconn.getDBCPConnection();
			
			sb.append(" SELECT AB_SEQ                      \n");
			sb.append(" 	   ,AG_SEQ                    \n"); 
			sb.append(" 	   ,AB_NAME                    \n"); 
			sb.append(" 	   ,AB_DEPT                    \n");
			sb.append(" 	   ,AB_POSITION                \n");
			sb.append(" 	   ,AB_MOBILE                  \n");
			sb.append(" 	   ,AB_MAIL                    \n");	
			sb.append(" 	   ,AB_ISSUE_CHK               \n");
			sb.append(" 	   ,AB_REPORT_DAY_CHK          \n");
			sb.append(" 	   ,AB_REPORT_WEEK_CHK         \n");
			sb.append(" 	   ,AB_APP_CHK                 \n");   
			sb.append(" 	   ,AB_SMS_CHK                 \n");   
			sb.append(" FROM ADDRESS_BOOK	               \n");			
			sb.append(" WHERE AB_SEQ IN ( "+piAbSeq+" )    \n");
	        
	        stmt = dbconn.createStatement();       
	        System.out.println(sb.toString());
		    rs = stmt.executeQuery(sb.toString());
	        		        
	        while(rs.next())
	        {
	        	//핸드폰번호 특수문자 제거
	        	String replaceString =  rs.getString("AB_MOBILE");
	        	replaceString = replaceString.replaceAll("-", "");
	        	replaceString = replaceString.replaceAll("\\.", "");
	        	replaceString = replaceString.replaceAll(" ", "");
	        	replaceString = replaceString.replaceAll("_", "");
	        	replaceString = replaceString.replaceAll("\'", "");
	        	replaceString = replaceString.replaceAll("\"", "");
	        	
	        	abBean = new AddressBookBean();
	        	abBean.setMab_seq(rs.getInt("AB_SEQ"));
	        	abBean.setMag_seq(rs.getString("AG_SEQ"));
	        	abBean.setMab_name(rs.getString("AB_NAME"));
	        	abBean.setMab_dept(rs.getString("AB_DEPT"));
	        	abBean.setMab_pos(rs.getString("AB_POSITION"));
	        	abBean.setMab_mobile(rs.getString("AB_MOBILE"));
	        	abBean.setMab_mail(rs.getString("AB_MAIL"));
	        	abBean.setMab_issue_receivechk(rs.getString("AB_ISSUE_CHK"));
	        	abBean.setMab_report_day_chk(rs.getString("AB_REPORT_DAY_CHK"));
	        	abBean.setMab_report_week_chk(rs.getString("AB_REPORT_WEEK_CHK"));
	        	abBean.setMab_app_chk(rs.getString("AB_APP_CHK"));
	        	abBean.setMab_sms_chk(rs.getString("AB_SMS_CHK"));
	        }//WHILE 루프
			
		}catch (SQLException ex) {
		  Log.writeExpt(ex);
		}catch (Exception ex) {
          Log.writeExpt(ex);
		} finally {
          try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
          try { if( rs  != null) rs.close();      } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
          try { if( stmt  != null) stmt.close();  } catch(SQLException ex) { Log.writeExpt("CRONLOG",ex); }
		}
      return abBean;
	}
	
	
	// 사용자 정보 추가 --현재 메일 수신정보만 받아 추가
	public int insertAddressBook(AddressBookBean AdrBean){
			int abSeq = 0;
		try {
			
			dbconn = new DBconn();
			Statement stmt = null;
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sb.append(" INSERT INTO ADDRESS_BOOK (AB_NAME                                       \n");
			sb.append(" 	   					  ,AG_SEQ                                       \n");
			sb.append(" 	   					  ,AB_DEPT                                      \n");
			sb.append(" 	   					  ,AB_POSITION                                  \n");
			sb.append(" 	   					  ,AB_MOBILE                                    \n");
			sb.append(" 	   					  ,AB_MAIL                                      \n");			
			sb.append(" 	   					  ,AB_ISSUE_CHK                                 \n");
			sb.append(" 	   					  ,AB_REPORT_DAY_CHK                            \n");
			sb.append(" 	  	 				  ,AB_REPORT_WEEK_CHK                           \n");
			sb.append(" 	  	 				  ,AB_APP_CHK                                   \n");
			sb.append(" 	  	 				  ,AB_SMS_CHK                                   \n");
			sb.append(" 	  	 				  )                                             \n");
			sb.append("				VALUES ('" + AdrBean.getMab_name() + "'			 	        \n");
			sb.append("						,"+AdrBean.getMag_seq()+"		 	                \n");
			sb.append("						,'" + AdrBean.getMab_dept() + "'		 	        \n");
			sb.append("						,'" + AdrBean.getMab_pos() + "'		 	            \n");
			sb.append("						,'" + AdrBean.getMab_mobile() + "'		 	        \n");
			sb.append("						,'" + AdrBean.getMab_mail() + "'		 	        \n");
			sb.append("						,'" + AdrBean.getMab_issue_receivechk() + "'		\n");
			sb.append("						,'" + AdrBean.getMab_report_day_chk() + "'		 	\n");
			sb.append("						,'" + AdrBean.getMab_report_week_chk() + "'		 	\n");
			sb.append("						," + AdrBean.getMab_app_chk() + "		         	\n");
			sb.append("						," + AdrBean.getMab_sms_chk() + "		         	\n");
			sb.append("        				) 								                    \n");
		
			//System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());
			
			sb = new StringBuffer();
			
			sb.append(" select top (1) ab_seq from address_book order by ab_seq desc ");
			rs = stmt.executeQuery(sb.toString());
			if (rs.next() ) {
				abSeq = rs.getInt("ab_seq");				
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
        	System.out.println(sb);
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return abSeq;
    }

	// 사용자 정보 추가 --현재 메일 수신정보만 받아 추가
	public void updateAddressBook(AddressBookBean AdrBean){
		
		try {
			dbconn = new DBconn();
			Statement stmt = null;
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sb.append(" UPDATE ADDRESS_BOOK SET AB_NAME='" + AdrBean.getMab_name() + "'	                         \n"); 
			sb.append(" 	   					,AB_DEPT='" + AdrBean.getMab_dept() + "'                         \n");
			sb.append(" 	   					,AG_SEQ= " + AdrBean.getMag_seq()+ "                             \n");
			sb.append(" 	   					,AB_POSITION='" + AdrBean.getMab_pos() + "'                      \n");
			sb.append(" 	   					,AB_MOBILE='" + AdrBean.getMab_mobile() + "'                     \n");
			sb.append(" 	   					,AB_MAIL='" + AdrBean.getMab_mail() + "'                         \n");			
			sb.append(" 	   					,AB_ISSUE_CHK=" + AdrBean.getMab_issue_receivechk() + "          \n");
			sb.append(" 	   					,AB_REPORT_DAY_CHK=" + AdrBean.getMab_report_day_chk() + "       \n");
			sb.append(" 	  	 				,AB_REPORT_WEEK_CHK=" + AdrBean.getMab_report_week_chk() + "     \n");
			sb.append(" 	  	 				,AB_APP_CHK=" + AdrBean.getMab_app_chk() + "                     \n");
			sb.append(" 	  	 				,AB_SMS_CHK=" + AdrBean.getMab_sms_chk() + "                     \n");
			sb.append(" WHERE AB_SEQ = " + AdrBean.getMab_seq() + "								                 \n");

			System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
        	System.out.println(sb);
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }
	
	// 사용자 정보 추가 --현재 메일 수신정보만 받아 추가
	public void deleteAddressBook(String seqList){
		
		try {
			dbconn = new DBconn();
			Statement stmt = null;
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sb.append(" DELETE FROM ADDRESS_BOOK WHERE AB_SEQ IN ("+seqList+")\n"); 			

			//System.out.println(sb.toString());
			stmt.executeUpdate(sb.toString());

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
        	System.out.println(sb);
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
    }
	
    public String getPageIndex(int nowpage, int totpage) {
        String rtnStr="";
        int idx = 0;
        int i = 0;
        int idxpage = 0;
        int tmp = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int nUpdown = 0;

        idx = ( nowpage - 1) / 10 + 1;
        for( i = 1 ; i <= 10 ; i++ )
        {
            idxpage = (idx - 1) * 10 + i;

            if( idxpage != 0 && idxpage <= totpage ) {
                if( idxpage == nowpage ) {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr = ""; //"<FONT size=2>";

                        rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                        rtnStr += "><img src=\"../images2/cen02_pre_btn01.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = idxpage - 1;
                        tmp3 = totpage;
                        rtnStr += "";
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10 ){
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\" ";
                            }
                           rtnStr += "><img src=\"../images2/cen02_next_btn02.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        }
                    } else {
                        rtnStr += "<B><font color=\"C6120E\">" +  idxpage + "</font></B> &nbsp; ";
                    }

                } else {
                    if ( i == 1 && idx != 1 ) {
                        tmp = idxpage - 1;
                        rtnStr += "";
                        //rtnStr += "<a HREF='" + gourl + "?nowpage=1" + add_tag + "'> ";
                        //rtnStr += "<img src=\"./images/next2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('"+ tmp +"');\" ";
                        rtnStr += "><img src=\"../images2/cen02_pre_btn01.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;&nbsp;";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\"";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    } else if ( i == 10 ) {
                        tmp = idxpage + 1;
                        tmp2 = totpage;
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" +  idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";

                        if ( totpage >= tmp ) {
                            if ( idx != totpage / 10) {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            } else {
                                rtnStr += "<a HREF=\"javascript:pageClick('" + tmp + "');\"";
                            }
                            rtnStr += "><img src=\"../images2/cen02_next_btn02.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";
                            //rtnStr += "<a HREF='" + gourl + "?nowpage=" + tmp2 + add_tag + "&nUpdown=-1'> ";
                            //rtnStr += "<img src=\"./images/prev2.gif\" border=\"0\" align=\"absmiddle\"></A>&nbsp;";

                        }
                    } else {
                        rtnStr += "";
                        rtnStr += "<a HREF=\"javascript:pageClick('" + idxpage + "');\" ";
                        rtnStr += ">" +  idxpage + "</A> &nbsp; ";
                    }
                }
            }
        }

        return rtnStr;
    }
    
	public ArrayList getAddressBook(String seqList){
			
		ArrayList arrABBean = new ArrayList();
		//AddressBookBean  ABBean;
		try {
			dbconn = new DBconn();
			Statement stmt = null;
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			rs = null;
			
			sb.append("SELECT *									\n");
			sb.append("FROM ADDRESS_BOOK						 	\n");
			if(seqList != "" || seqList != null){
			sb.append("WHERE       								\n");
			sb.append("AB_SEQ IN("+seqList+")					\n");
			}
			sb.append("         								\n");

			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
					
			while(rs.next()){
				
				abBean = new AddressBookBean();
	        	abBean.setMab_seq(rs.getInt("AB_SEQ"));
	        	abBean.setMab_name(rs.getString("AB_NAME"));
	        	abBean.setMab_dept(rs.getString("AB_DEPT"));
	        	abBean.setMab_pos(rs.getString("AB_POSITION"));
	        	abBean.setMab_mobile(rs.getString("AB_MOBILE"));
	        	abBean.setMab_mail(rs.getString("AB_MAIL"));
	        	abBean.setMab_issue_receivechk(rs.getString("AB_ISSUE_CHK"));
	        	abBean.setMab_report_day_chk(rs.getString("AB_REPORT_DAY_CHK"));
	        	abBean.setMab_report_week_chk(rs.getString("AB_REPORT_WEEK_CHK"));
	        	arrABBean.add(abBean);
				
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
        	System.out.println(sb);
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return arrABBean;
    }
}


