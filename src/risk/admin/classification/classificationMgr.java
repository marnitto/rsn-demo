/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 분류체계관리
프로그램 ID : classificationMgr.class
프로그램 명 : classificationMgr
프로그램개요 : ISSUE_CODE 테이블에서 분류체계를 가져와 반환한다.
작 성 자 : 이재희
작 성 일 : 2007.11.30
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.admin.classification;

import java.sql.*;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import risk.admin.classification.clfBean;
import risk.issue.IssueBean;
import risk.util.DateUtil;

public class classificationMgr {
	
	DBconn  dbconn  = null; // db connection
    Statement stmt  = null; //oracle
    ResultSet rs    = null; //oracle
    
	String baseURL = "";
	String baseTarget = "";
	
	public String msMinNo   = "";   //검색 시작 MT_NO
    public String msMaxNo   = "";   //검색 마지막 MT_NO


	


    int selectedType = 0;
	int selectedCode = 0;
	

	// 메뉴 클릭시 이동할 페이지를 지정한다.
	public void setBaseURL(String url) {
		baseURL = url;
	}
	// 메뉴 클릭시 이동할 target을 정한다.
	public void setBaseTarget(String target) {
		baseTarget = target;
	}

	// 최초 선택된 xp, yp, zp를 지정하고, 지정된 항목을 강조한다.
	public void setSelected(int piType, int piCode){
		selectedType = piType;
		selectedCode = piCode;
		
	}
	
	// tree형태의 html을 반환한다.
	public String GetHtml( int piType,
						   int piCode) {

		String html         = "";
		String frontIcon    = "";
		String trID         = "";
		String spanID       = "";
		String imgID		= "";
		String trStyle      = "";
		String icName       = "";
		String linkURL      = "";
		String imgAction    = "";
		String frontSpace   = "";
		String spanClass    = "";

        StringBuffer sb = null;
        String sql = ""; // sql statement

        String MinMtno      = "";
        String MaxMtno      = "";

		int itype=0;
		int icode=0;
		int prevType = 0;
		int prevCode = 0;
		
		spanClass = "class=\"kgmenu\"";

		try {

            dbconn  = new DBconn();
            dbconn.getDBCPConnection();
            stmt    = dbconn.createStatement();

            sb = new StringBuffer();
            StringUtil su = new StringUtil();
            
            sb.append("SELECT IC_SEQ,	          			\n"); 
            sb.append("		  IC_NAME,           			\n");
            sb.append("		  IC_TYPE,           			\n");
            sb.append("		  IC_CODE           			\n");
            sb.append("FROM ISSUE_CODE           			\n");
            sb.append("WHERE IC_PTYPE=0           			\n");
            sb.append("		 AND IC_PCODE=0	           		\n");
            sb.append("		 AND IC_USEYN='Y'          		\n");
            sb.append("ORDER BY IC_TYPE ASC, IC_CODE ASC    \n");

            Log.debug(sb.toString());
            rs = stmt.executeQuery( sb.toString() );

			// 마우스 오버시 처리를 위해 this.className을 임시로 저장한다.
			// 아웃될때 선택된 넘과 아닌놈이 다르게 적용되어야 하므로..
			spanID = "spanID_"+selectedType+"_"+selectedCode;
			html += "<span id='tmpspan' style='display:none' class=''></span>";
			html += "<input type=\"hidden\" name=\"kgmenu_id\" value=\""+spanID+"\">";

			while(rs.next()){

				// 선처리 공통
				icName = rs.getString("IC_NAME");
            	itype = rs.getInt("IC_TYPE");
            	icode = rs.getInt("IC_CODE");

            	linkURL = baseTarget+".location.href='"+baseURL+"&itype="+itype+"&icode="+icode+"'";
				spanID = "spanID_"+itype+"_"+icode;
				imgID = "imgID_"+itype+"_"+icode;

				// 대분류일때 선처리
            	if (icode==0) {
					frontSpace="";
					frontIcon = "ico03";
					if (selectedType == itype) { trStyle = "display:"; frontIcon = "ico04";}
					else  { trStyle = "display:none"; }
					trID = "trID_"+itype;
					//imgAction = " onclick=\"toggleme(this,'trID_"+itype+"');\" ";
					imgAction = " toggleme('"+imgID+"','trID_"+itype+"');";
					//icName = "<b>" + icName + "</b>";
				}
				// 하위 분류 일때 선처리
            	else {
					frontSpace="&nbsp;&nbsp;";
					frontIcon = "ico05";
					trStyle = "";
					trID = "";
					imgAction = " ";
				}

				// block end 처리
				if (prevCode!=0 && icode==0)
				{
					html+="</div>\n";
				}			

				// 메뉴의 각 항목을 처리한다.
				
				if (icode == 0) { // 분류 그룹일경우
					html += "    "+frontSpace+"\n"+
						"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\"../../../images/search/left_"+frontIcon+".gif\" onClick=\""+imgAction+" \" border=\"0\" align=absmiddle>"+
						" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
						"onmouseout=\"kg_out(this);\">" 
						+"<b>"+ icName + "</b> "+
						"</span></br>\n";
				} else { // 하위 분류일  경우
					html += "    "+frontSpace+"\n"+
						"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\"../../../images/search/left_"+frontIcon+".gif\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>"+
						" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
						"onmouseout=\"kg_out(this);\"><nobr style =\"text-overflow:ellipsis; overflow:hidden; width:90px; \" title=\" " +icName+ " \">" 
						+"<b>"+ icName + "</b></nobr> "+
						"</span></br>\n";					
				}
				
				// block start 처리
				if (icode==0)
				{
					html+="<div id=\""+trID+"\" style=\""+trStyle+"\">\n";
				}

				// block처리를 위해 이전 xp,yp,zp를 저장한다.
				prevType = itype;
				prevCode = icode;
				
            }
		// html의 마지막을 장식한다.
		html += "</div>\n";

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
		
		return html;
	}
	
	// 해당 분류체계 리스트를 반환한다.
	public ArrayList GetDetailList( int piType,
						   	   int piCode) 
	{
		
		ArrayList clfList = null;
        StringBuffer sb = null;

		try {

            dbconn  = new DBconn();
            dbconn.getDBCPConnection();
            stmt    = dbconn.createStatement();

            sb = new StringBuffer();
            StringUtil su = new StringUtil();
            if( piType > 0 ) {
            	if( piCode > 0 ) {
            		sb.append("SELECT IC_SEQ,	          			\n"); 
                    sb.append("		  IC_NAME,           			\n");
                    sb.append("		  IC_TYPE,           			\n");
                    sb.append("		  IC_CODE,           			\n");
                    sb.append("		  IC_PTYPE,           			\n");
                    sb.append("		  IC_PCODE,           			\n");
                    sb.append("		  IC_REGDATE,           		\n");
                    sb.append("		  M_SEQ,          				\n");
                    sb.append("		  IC_DESCRIPTION       			\n");
                    sb.append("FROM ISSUE_CODE           			\n");
                    sb.append("WHERE IC_PTYPE="+piType+"     		\n");
                    sb.append("		 AND IC_PCODE="+piCode+"   		\n");
                    sb.append("		 AND IC_USEYN='Y'        		\n");
                    sb.append("ORDER BY IC_TYPE ASC, IC_CODE ASC    \n");
            	}else {
            		sb.append("SELECT IC_SEQ,	          			\n"); 
                    sb.append("		  IC_NAME,           			\n");
                    sb.append("		  IC_TYPE,           			\n");
                    sb.append("		  IC_CODE,           			\n");
                    sb.append("		  IC_PTYPE,           			\n");
                    sb.append("		  IC_PCODE,           			\n");
                    sb.append("		  IC_REGDATE,           		\n");
                    sb.append("		  M_SEQ,           				\n");
                    sb.append("		  IC_DESCRIPTION       			\n");
                    sb.append("FROM ISSUE_CODE           			\n");
                    sb.append("WHERE IC_TYPE="+piType+"    			\n");
                    sb.append("		 AND IC_CODE>0        			\n");
                    sb.append("		 AND IC_PTYPE=0        			\n");
                    sb.append("		 AND IC_PCODE=0	           		\n");
                    sb.append("		 AND IC_USEYN='Y'        		\n");
                    sb.append("ORDER BY IC_TYPE ASC, IC_CODE ASC    \n");
            	}

                Log.debug(sb.toString());
                rs = stmt.executeQuery( sb.toString() );
                clfBean clb = null;
                
                clfList = new ArrayList();

    			while(rs.next()){
    				clfList.add( clb = new clfBean( rs.getInt("IC_SEQ"),
    										   		rs.getString("IC_NAME"),
    										   		rs.getInt("IC_TYPE"),
    										   		rs.getInt("IC_CODE"),
    										   		rs.getInt("IC_PTYPE"),
    										   		rs.getInt("IC_PCODE"),
    										   		0,
    										   		rs.getString("IC_REGDATE").substring(0,10),
    										   		rs.getString("IC_REGDATE").substring(10,19),
    										   		rs.getInt("M_SEQ"),
    										   		rs.getString("IC_DESCRIPTION")
    						)
    				);
    				
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
		return clfList;
	}
	
	// 중간 분류의 해당 분류체계 의 하위 분류의 갯수를 반환한다.
	public int GetSubClf( int piType,
						  int piCode)
	{
		int reCount = 0;
        StringBuffer sb = null;

		try {

            dbconn  = new DBconn();
            dbconn.getDBCPConnection();
            stmt    = dbconn.createStatement();

            sb = new StringBuffer();
            StringUtil su = new StringUtil();
            if( piType > 0 ) {
            	if( piCode > 0 ) {
            		sb.append("SELECT COUNT(IC_SEQ)	AS IC_COUNT		\n");
                    sb.append("FROM ISSUE_CODE           			\n");
                    sb.append("WHERE IC_PTYPE="+piType+"     		\n");
                    sb.append("		 AND IC_USEYN='Y'        		\n");

	                Log.debug(sb.toString());
	                rs = stmt.executeQuery( sb.toString() );
	
	    			if(rs.next()){
	    				reCount = rs.getInt("IC_COUNT");
	                }
            	}else {
            		// Code 가 0 인경우 무조건 0보다 큰수를 넘겨준다.
            		reCount = 1;
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
		return reCount;
	}
	
	// 해당 분류체계의 하위 분류를 추가 한다.
	public boolean InsertClf( int piType,
						   	  int piCode,
						   	  String psName,
						   	  String psM_seq) 
	{
		DateUtil du = new DateUtil();
		DBconn dbconn = null;
        StringBuffer sb = null;
        StringBuffer sb2 = null;
        System.out.println("psName::"+psName);
        
        String sCurrentDate = du.getCurrentDate("yyyy-MM-dd HH:mm:ss");
                
        int nextOrder = 0;
        int nextCode = 0;
        int getType = 0;
        int subCount = 0;
        
		try {

            dbconn  = new DBconn();
            dbconn.getDBCPConnection();

            sb = new StringBuffer();
            sb2 = new StringBuffer();
            StringUtil su = new StringUtil();
            
            // 하위 분류체계가 있는지 체크 한다.
            if( GetSubClf( piType, piCode) > 0 ) {
	            if( piType > 0 ) {
	            	if( piCode > 0 ) {
	            		try {
		    			
		            		sb.append("SELECT IC_TYPE, IFNULL(MAX(IC_CODE), 0)+1 AS CODE, IFNULL(MAX(IC_ORDER), 0)+1 AS IC_ORDER   \n");
		                    sb.append("FROM ISSUE_CODE           				  \n");
		                    sb.append("WHERE IC_PTYPE="+piType+"     			  \n");
		                    sb.append("		 AND IC_USEYN='Y'        		\n");
		                    //sb.append("		 AND IC_PCODE="+piCode+"   			  \n");
		                    sb.append("GROUP BY IC_TYPE    	  \n");
		                    
		                   System.out.println(sb.toString());
		                    Log.debug(sb.toString());
	            			stmt = dbconn.createStatement();
		                    rs = stmt.executeQuery( sb.toString() );
		                    
		
		        			if(rs.next()){
		        				getType = rs.getInt("IC_TYPE");
		        				nextCode = rs.getInt("CODE");
		        				nextOrder = rs.getInt("IC_ORDER");
		                    }
	            		} catch (SQLException ex) {
	                        Log.writeExpt(ex);
	                        return false;
	                    } catch (Exception ex) {
	                        Log.writeExpt(ex);
	                        return false;
	                    } finally {
	                        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	                        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	                    }
		        			
	        			sb = new StringBuffer();
	                    
	        			sb.append("INSERT INTO ISSUE_CODE (           	\n");
	            		sb.append("		  			      IC_NAME           	\n");
	                    sb.append("		  				  ,IC_TYPE           	\n");
	                    sb.append("		  				  ,IC_CODE           	\n");
	                    sb.append("		  				  ,IC_PTYPE           \n");
	                    sb.append("		  				  ,IC_PCODE           \n");
	                    sb.append("		  				  ,IC_REGDATE          \n");
                        sb.append("		  				  ,M_SEQ           	\n");
	                    sb.append("		  				  ,IC_DESCRIPTION     \n");
	                    sb.append("		  				  )     \n");
	                    sb.append("VALUES (	\n");
	                    sb.append("	            '"+psName+"'      			\n");
                        sb.append("				,"+piType+"    		\n");
	                    sb.append("				,"+nextCode+" 		\n");
	                    sb.append("		 		,0        			\n");
	                    sb.append("		 		,0        			\n");	                    
	                    sb.append("		 		,'"+sCurrentDate+"' \n");	                   
	                    sb.append("		 		,"+psM_seq+"     	\n");
	                    sb.append("		 		NULL        		\n");
	                    sb.append("		)        		\n");
	            	}else {
	            		try {
	            			stmt = dbconn.createStatement();
	            		
		            		sb.append("	SELECT IFNULL(MAX(IC_CODE), 0)+1 AS CODE , IFNULL(MAX(IC_ORDER), 0)+1 AS IC_ORDER	\n");
		                    sb.append("	FROM ISSUE_CODE           			\n");
		                    sb.append("	WHERE IC_TYPE="+piType+"    		\n");
		                    sb.append("		 AND IC_PTYPE=0        		\n");	
		                    sb.append("		 AND IC_PCODE=0	  			\n");
		                    sb.append("		 AND IC_USEYN='Y'        		\n");
		                    
		                    System.out.println(sb.toString());
		                    Log.debug(sb.toString());
		                    rs = stmt.executeQuery( sb.toString() );
		                    
		
		        			if(rs.next()){
		        				nextCode = rs.getInt("CODE");
		        				nextOrder = rs.getInt("IC_ORDER");
		                    }
	            		} catch (SQLException ex) {
	                        Log.writeExpt(ex);
	                        return false;
	                    } catch (Exception ex) {
	                        Log.writeExpt(ex);
	                        return false;
	                    } finally {
	                        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	                        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
	                    }
	        			
	        			sb = new StringBuffer();
	        			
	            		sb.append("INSERT INTO ISSUE_CODE (           	\n");
	            		sb.append("		  			      IC_NAME           	\n");
	                    sb.append("		  				  ,IC_TYPE           	\n");
	                    sb.append("		  				  ,IC_CODE           	\n");
	                    sb.append("		  				  ,IC_PTYPE           \n");
	                    sb.append("		  				  ,IC_PCODE           \n");
	                    sb.append("		  				  ,IC_REGDATE          \n");
                        sb.append("		  				  ,M_SEQ           	\n");
	                    sb.append("		  				  ,IC_DESCRIPTION     \n");
	                    sb.append("		  				  ,IC_ORDER     \n");
	                    sb.append("		  				  )     \n");
	                    sb.append("		VALUES (	\n");
	                    sb.append("	            '"+psName+"'      			\n");
                        sb.append("				,"+piType+"    		\n");
	                    sb.append("				,"+nextCode+" 		\n");
	                    sb.append("		 		,0        			\n");
	                    sb.append("		 		,0        			\n");	                    
	                    sb.append("		 		,'"+sCurrentDate+"' \n");	                   
	                    sb.append("		 		,"+psM_seq+"     	\n");
	                    sb.append("		 		,NULL        			\n");
	                    sb.append("				,"+nextOrder+" 		\n");
	                    sb.append("		 		)        		\n");
	                    
	            	}
	            	
	            	try {
	            		System.out.println(sb.toString());
		            	stmt = dbconn.createStatement();
		
		                Log.debug(sb.toString());
		                stmt.executeUpdate( sb.toString() );
	            	} catch (SQLException ex) {
                        Log.writeExpt(ex);
                        return false;
                    } catch (Exception ex) {
                        Log.writeExpt(ex);
                        return false;
                    } finally {
                        try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
                        try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
                    }
	            }
            }else {
            	return false;
            }
            
            
        } catch (Exception ex) {
            Log.writeExpt(ex);
            //강제 종료
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return true;
	}
	
	//등록된 타입코드를 가져온다.
    public String getInsertTypeCode(int piType)    		   
    {    	
    	String typeCode = null;
    	StringBuffer sb = new StringBuffer();	
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			sb.append("SELECT IC_TYPE, IC_CODE  \n");
            sb.append("FROM ISSUE_CODE           				  \n");
            sb.append("WHERE IC_TYPE = "+piType+"     			  \n");
            sb.append("		 AND IC_USEYN='Y'        		\n");
            //sb.append("		 AND IC_PCODE="+piCode+"   			  \n");
            sb.append("ORDER BY IC_CODE DESC LIMIT 1  \n");
			
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
							
			if(rs.next()){
				typeCode = rs.getString("IC_TYPE")+","+rs.getString("IC_CODE");
			}												
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    	
	    return typeCode;
	 }
	
	// 해당 분류체계의 하위 분류를 삭제 한다.
	public boolean DelClf( String psIc_seq ) 
	{
		DateUtil du = new DateUtil();
        StringBuffer sb = null;
        
		try {

            dbconn  = new DBconn();
            sb = new StringBuffer();
            dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
            
            sb.append("UPDATE ISSUE_CODE           			  \n");
            sb.append("SET IC_USEYN='N' WHERE IC_SEQ IN ("+psIc_seq+")     			  \n");
            
            System.out.println(sb.toString());
            Log.debug(sb.toString());
            stmt.executeUpdate( sb.toString() );  
            
        } catch (SQLException ex) {
            Log.writeExpt(ex);
            return false;
        } catch (Exception ex) {
            Log.writeExpt(ex);
            return false;
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return true;
	}
	

	// 토글을 위한 자바스크립트를 반환한다.
	public String GetScript() {
		String kgScript = ""+
			"<SCRIPT LANGUAGE=\"JavaScript\">					\n"+
			"<!--												\n"+
			"function toggleme(obj, subo) {						\n"+
			"	var subObj = eval(\"document.all.\"+subo);		\n"+
			"	var i = 0;										\n"+
			"	var subcnt = subObj.length;						\n"+
			"	if (obj.src.indexOf('ico03')>0) {				\n"+
			"		if (subObj)	{								\n"+
			"			obj.src = '../images2/left_ico04.gif';		\n"+
			"			subObj.style.display='';				\n"+
			"		}											\n"+
			"	}												\n"+
			"	else {											\n"+
			"		if (subObj)	{								\n"+
			"			obj.src = '../images2/left_ico03.gif';		\n"+
			"			subObj.style.display='none';			\n"+
			"		}											\n"+
			"	}												\n"+
			"}													\n"+
			"function kg_over(obj) {							\n"+
			"	tmpspan.className=obj.className;				\n"+
			"	obj.className='kgmenu_over';					\n"+
			"}													\n"+
			"function kg_out(obj){								\n"+
			"	obj.className=tmpspan.className;				\n"+
			"}													\n"+
			"function kg_click(obj){							\n"+
			"	var prvObj = eval('document.all.'+document.all.kgmenu_id.value);	\n"+
			"	if (prvObj)										\n"+
			"	{												\n"+
			"		prvObj.className = 'kgmenu';				\n"+
			"	}												\n"+
			"	document.all.kgmenu_id.value = obj.id;			\n"+
			"	obj.className='kgmenu_selected';				\n"+
			"	tmpspan.className=obj.className;				\n"+
			"}													\n"+
			"//-->												\n"+
			"</SCRIPT>											\n";
		return kgScript;
	}

	// 스타일 시트를 반환한다. 쓰기시름 만들어서 사용한다.
	public String GetStyle() {
		String kgStyle=""+
			"<style>\n"+
			"<!--\n"+
			".kgmenu { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #FFFFFF; }\n"+
			".kgmenu_cnt { FONT-SIZE: 10px; COLOR: navy; FONT-FAMILY: \"tahoma\"; padding:0 0 0 3 }\n"+
			".kgmenu_selected { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; border:1px solid #999999; \n"+
			"background-color:#F3F3F3; cursor:hand;}\n"+
			".kgmenu_over { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #999999; }\n"+
			".kgmenu_img { cursor:hand; }\n"+
			"-->\n"+
			"</style>";
		return kgStyle;
	}
	
	
	//등록된 타입코드를 가져온다.
    public List getTypeCodeFlag(int itype, int icode)    		   
    {    	
    	List result = new ArrayList();
    	StringBuffer sb = new StringBuffer();	
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();		
			sb.append("SELECT IC_FLAG  \n");
            sb.append("FROM ISSUE_CODE_FLAG           			\n");
            sb.append("WHERE IC_TYPE = "+itype+"     			\n");
            sb.append("  AND IC_CODE = "+icode+"        		\n");
            sb.append("ORDER BY IC_FLAG ASC  \n");
			System.out.println(sb.toString());				
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				result.add( rs.getString("IC_FLAG").toString() );
			}												
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
	    	
	    return result;
	 }
    
    public boolean insertFlag(int itype, int icode, String iflag){
    	boolean result = false;
    	StringBuffer sb = new StringBuffer();
    	 
    	
    	try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			String tmp[] = iflag.split(",");
			
			if(tmp.length > 0){
				sb = new StringBuffer();	
				sb.append("DELETE FROM ISSUE_CODE_FLAG WHERE IC_TYPE="+itype+" AND IC_CODE="+icode+" \n");
				for(int i=0;i<tmp.length;i++){
					System.out.println(sb.toString());				
					if( stmt.executeUpdate(sb.toString()) >= 0 ){
						sb = new StringBuffer();
						sb.append("INSERT INTO ISSUE_CODE_FLAG (IC_TYPE, IC_CODE, IC_FLAG)   \n");
			            sb.append("VALUES ("+itype+","+icode+","+tmp[i]+")     			\n");
						System.out.println(sb.toString());				
						if( stmt.executeUpdate(sb.toString()) > 0 ){
							result = true;
						}
					}
				}
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		    if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
    	
    	return result;
    }
    
    public boolean ModifyName(String ic_seq, String ic_name){
		return ModifyName(ic_seq, ic_name, "", "");
	}
	
	public boolean ModifyName( String ic_seq, String ic_name, String ic_regdate, String ic_regtime  ) 
	{
        StringBuffer sb = null;
        boolean reslut = false; 
        
		try {

            dbconn  = new DBconn();
            sb = new StringBuffer();
            dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
           
            sb.append("UPDATE ISSUE_CODE SET IC_NAME = '"+ic_name+"'							\n");
            if(!"".equals(ic_regdate) && !"".equals(ic_regtime)){
            	sb.append(" , IC_REGDATE = '"+ic_regdate+" "+ic_regtime+"'						\n");	
            }
            sb.append("	WHERE IC_SEQ = "+ic_seq+"	\n");
            
            System.out.println(sb.toString());
            Log.debug(sb.toString());
            int count = stmt.executeUpdate( sb.toString() );
            
            if(count > 0){
            	reslut = true;
            }
            
        } catch (SQLException ex) {
            Log.writeExpt(ex);
            return false;
        } catch (Exception ex) {
            Log.writeExpt(ex);
            return false;
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return reslut;
	}
    
}