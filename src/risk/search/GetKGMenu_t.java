/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 검색메뉴
프로그램 ID : GetKGMenu.class
프로그램 명 : GetKGMenu
프로그램개요 : keyword 테이블에서 키워드 그룹과 키워드를 가져와
			tree형태의 html메뉴를 생성하여 반환한다.
작 성 자 : 박정호
작 성 일 : 2006.04.12
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.search;

import java.sql.*;

import risk.DBconn.DBconn;

import risk.util.Log;
import risk.util.StringUtil;
import java.util.Vector;

public class GetKGMenu_t {

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
	public void getMaxMinNo( String psSDate, String psEdate ) {

        try {
            sb = new StringBuffer();
            /*sb.append(" SELECT CASE DATE_FORMAT(NOW(),'%Y-%m-%d')                                                                    \n");
            sb.append("         WHEN DATE_FORMAT('"+psSDate+"','%Y-%m-%d')                                                            \n");
            sb.append("           THEN IFNULL((SELECT MIN(MD_SEQ) FROM META WHERE MD_DATE BETWEEN '"+psSDate+" 00:00:00' AND '"+psSDate+" 23:59:59'),1)   \n");
            sb.append("         ELSE                                                                                                 \n");
            sb.append("           IFNULL((SELECT MINNO FROM DAILY_MAXNINNO WHERE DM_DATE = '"+psSDate+" 23:59:59'),1)   \n");
            sb.append("        END  AS MIN_NO,                                                                                       \n");
            sb.append("                                                                                                              \n");
            sb.append("        CASE DATE_FORMAT(NOW(),'%Y-%m-%d')                                                                    \n");
            sb.append("         WHEN DATE_FORMAT('"+psEdate+"','%Y-%m-%d')                                                            \n");
            sb.append("           THEN IFNULL((SELECT MAX(MD_SEQ) FROM META WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59'),1)   \n");
            sb.append("         ELSE                                                                                                 \n");
            sb.append("           IFNULL((SELECT MAXNO FROM DAILY_MAXNINNO WHERE DM_DATE = '"+psEdate+" 23:59:59'),1)    \n");
            sb.append("        END  AS MAX_NO                                                                                        \n");*/
            sb.append(" SELECT (SELECT MD_SEQ FROM META_TMP WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
            sb.append("        ,(SELECT MD_SEQ FROM META_TMP WHERE MD_DATE BETWEEN '"+psEdate+" 00:00:00' AND '"+psEdate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
                                  
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
    
    
//  tree형태의 html을 반환한다.
	public String GetNormalHtml( String psDateFrom,String psDateTo,String MGxp, String showCount ) {
		// Oracle  연결관련 Object 선언
		
		
		//String html         = "";
		StringBuffer sbhtml = null;
		String frontIcon    = "";
		String trID         = "";
		String spanID       = "";
		String imgID		= "";
		String trStyle      = "";
		String kgName       = "";
		String linkURL      = "";
		String imgAction    = "";
		String frontSpace   = "";
		String spanClass    = "";
		String sCount       = "";
		
		StringBuffer sb = null;
		
		String MinMtno      = "";
		String MaxMtno      = "";
		
		int xp=0;
		int yp=0;
		int zp=0;
		int prevxp = 0;
		int prevyp = 0;
		int prevzp = 0;
		int k_seq = 0;
		
		
		dbconn = null;
		
		try {
			dbconn  = new DBconn();
			dbconn.getDBCPConnection();
			stmt    = dbconn.createStatement();
			
			sb = new StringBuffer();
				
				sb.append(" SELECT K_SEQ, K_XP, K_YP, K_ZP, K_VALUE  	\n");
				sb.append(" FROM KEYWORD_TMP 						\n");
				sb.append(" WHERE K_TYPE < 3 					\n");
				sb.append("   AND K_XP > 0 			\n");
				if( MGxp.length() > 0 )
					sb.append("   AND K_XP IN ("+MGxp+") 			\n");
				sb.append(" ORDER BY K_POS, K_XP, K_YP, K_ZP 	\n");
			
			
			Log.debug(sb.toString());
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			// 마우스 오버시 처리를 위해 this.className을 임시로 저장한다.
			// 아웃될때 선택된 넘과 아닌놈이 다르게 적용되어야 하므로..
			spanID = "spanID_"+selectedXP+"_"+selectedYP+"_"+selectedZP;
			
			sbhtml = new StringBuffer();
			sbhtml.append("<span id='tmpspan' style='display:none' class=''></span>");
			sbhtml.append("<input type=\"hidden\" name=\"kgmenu_id\" id=\"kgmenu_id\" value=\""+spanID+"\">");
			
			while(rs.next()){
				
				// 선처리 공통
				kgName = rs.getString("k_value").trim();            	
				System.out.println();
				xp = rs.getInt("k_xp");
				yp = rs.getInt("k_yp");
				zp = rs.getInt("k_zp");
				k_seq = rs.getInt("K_SEQ");
				
				
				linkURL = "setKey('"+xp+"','"+yp+"','"+zp+"','"+kgName+"', '"+k_seq+"');"; //baseTarget+".location.href='"+baseURL+"&xp="+xp+"&yp="+yp+"&zp="+zp+"'";
				spanID = "spanID_"+xp+"_"+yp+"_"+zp;
				imgID = "imgID_"+xp+"_"+yp+"_"+zp;
				
				// 대분류일때 선처리
				if (yp==0) {
					frontSpace="";
					frontIcon = "ico03";
					if (selectedXP==xp) { trStyle = "display:"; frontIcon = "ico04";}
					else  { trStyle = "display:none"; }
					trID = "trID_"+xp;
					imgAction = " toggleme('"+imgID+"','trID_"+xp+"'); ";
					kgName = "<b>" + kgName + "</b>";
				}
				// 중분류의 선처리
				else if (zp==0) {
					frontSpace="&nbsp;&nbsp;";
					frontIcon = "ico03";
					if (selectedXP==xp && selectedYP==yp) { trStyle = "display:"; frontIcon = "ico04";}
					else  { trStyle = "display:none"; }
					trID = "trID_"+xp+"_"+yp;
					imgAction = " toggleme('"+imgID+"', 'trID_"+xp+"_"+yp+"'); ";
					kgName = "<b>" + kgName + "</b>";
				}
				// 키워드의 선처리
				else {
					frontSpace="&nbsp;&nbsp;&nbsp;&nbsp;";
					frontIcon = "ico05";
					trStyle = "";
					trID = "";
					imgAction = " ";
					
					
					
				}
				// 선택된 넘은 스타일을 바꺼준다.
				if (selectedXP==xp && selectedYP==yp && selectedZP==zp) {
					spanClass=" class=\"kgmenu_selected\" ";
				}
				else {
					spanClass=" class=\"kgmenu\" ";
				}
				
				// block end 처리
				if (prevyp!=0 && yp==0)
				{
					sbhtml.append("</div>\n");
				} 
				else if ( prevxp != 0 && prevxp != xp && prevyp==0 && yp==0)
				{
					sbhtml.append("</div>\n");
				}
				if (prevzp!=0 && zp==0)
				{
					sbhtml.append("  </div>\n");
				} 
				else if ( prevyp !=0 && prevyp != yp && prevzp==0 && zp==0)
				{
					sbhtml.append("  </div>\n");
				}
				
				if (showCount.equals("true")){
					
					sCount = "<span class=\"kgmenu_cnt\">[" + rs.getString("cnt") + "]</span>";
				}	
				// 메뉴의 각 항목을 처리한다.
				if (zp==0) { // 키워드 그룹일경우
					sbhtml.append("    "+frontSpace+"\n"+
							"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\"images/left_"+frontIcon+".gif\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>"+
							" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
							"onmouseout=\"kg_out(this);\">" 
							+ kgName + " " + sCount +
					"</span></br>\n");
				}
				else { // 키워드일 경우
					sbhtml.append("    "+frontSpace+"\n"+
						//"      <input type='checkbox' name='k_seq' value='"+k_seq+"'id='"+kgName+"' >"+
							"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\"images/left_"+frontIcon+".gif\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>"+
							" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
					"onmouseout=\"kg_out(this);\">");
					
						sbhtml.append("<nobr style =\"text-overflow:ellipsis; overflow:hidden; width:110px; \" title=\" " +rs.getString("k_value").replaceAll("\"","")+ " \">");
					
					sbhtml.append(kgName + "</nobr> " + sCount + "</span></br>\n");					
				}
				
				// block start 처리
				if (yp==0)
				{
					sbhtml.append("<div id=\""+trID+"\" style=\""+trStyle+"\">\n");
				}
				else if (zp==0)
				{
					sbhtml.append("  <div id=\""+trID+"\" style=\""+trStyle+"\">\n");
				}
				
				// block처리를 위해 이전 xp,yp,zp를 저장한다.
				prevxp=xp;
				prevyp=yp;
				prevzp=zp;
			}
			// html의 마지막을 장식한다.
			sbhtml.append("  </div>		\n");
			sbhtml.append("</div>		\n");
			
			//html += "  </div>\n";
			//html += "</div>\n";
			
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
		return sbhtml.toString();
	}
    
  

    //정보검색 키워드 트리를 만든다.
	public String GetHtml( String psDateFrom,String psDateTo,String MGxp, String showCount ) {

		StringBuffer sbhtml = null;
		String frontIcon    = "";
		String trID         = "";
		String spanID       = "";
		String imgID		= "";
		String trStyle      = "";
		String kgName       = "";
		String linkURL      = "";
		String imgAction    = "";
		String frontSpace   = "";
		String spanClass    = "";
		String sCount       = "";
        StringBuffer sb = null;
        
        String option = "";

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
				getMaxMinNo( psDateTo, psDateTo );
			}

            //MinMtno= msMinNo;
            //MaxMtno= msMaxNo;
			
			MinMtno= "0";
            MaxMtno= "9999999999";
      

            /**************************************
             	오라클 직접연결
            **************************************/
            dbconn  = new DBconn();
            dbconn.getDBCPConnection();
            stmt    = dbconn.createStatement();

            sb = new StringBuffer();
            sbhtml = new StringBuffer();
            
	            if( showCount.equals("false") ){
	                
	                sb.append(" SELECT K_XP, K_YP, K_ZP,K_OP, K_VALUE  	\n");
	                sb.append(" FROM KEYWORD_TMP 						\n");
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
	                sb.append("   , ( SELECT COUNT(*) FROM KEYWORD_TMP WHERE K_XP = B1.K_XP AND K_YP=B1.K_YP AND K_ZP > 0 AND  K_USEYN='Y') AS K_CNT \n");
	                sb.append(" FROM                                                                                                 \n");
	                sb.append(" KEYWORD_TMP B1 LEFT OUTER JOIN                                                                           \n");
	                sb.append(" (                                                                                                    \n");
	                sb.append(" 	SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
	                sb.append("     FROM META_TMP A1,                                                                                    \n");
	                sb.append("       (                                                                                              \n");
	                sb.append("         SELECT DISTINCT K_XP, 0 AS K_YP, 0 AS K_ZP, MD_SEQ                                           \n");
	                sb.append("         FROM IDX_TMP                                                                                     \n");
	                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
	                sb.append("         	  AND I_STATUS = 'N'                                               							 \n");
	                sb.append("       ) A2                                                                                           \n");
	                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
	                //sb.append("     AND A1.MD_SEQ = A1.MD_PSEQ                                                                        \n");
	                sb.append("     GROUP BY K_XP                                                                                    \n");
	                sb.append(" 		UNION                                                                                \n");
	                sb.append(" 		SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
	                sb.append("     FROM META_TMP A1,                                                                                    \n");
	                sb.append("       (                                                                                              \n");
	                sb.append("         SELECT DISTINCT K_XP, K_YP, 0 AS K_ZP, MD_SEQ                                                \n");
	                sb.append("         FROM IDX_TMP                                                                                     \n");
	                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
	                sb.append("         	  AND I_STATUS = 'N'                                               							 \n");
	                sb.append("       ) A2                                                                                           \n");
	                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
	                //sb.append("     AND A1.MD_SEQ = A1.MD_PSEQ                                                                        \n");
	                sb.append("     GROUP BY K_XP, K_YP                                                                              \n");
	                sb.append(" 		UNION                                                                                \n");
	                sb.append(" 		SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
	                sb.append("     FROM META_TMP A1,                                                                                    \n");
	                sb.append("       (                                                                                              \n");
	                sb.append("         SELECT DISTINCT K_XP, K_YP, K_ZP, MD_SEQ                                                     \n");
	                sb.append("         FROM IDX_TMP                                                                                     \n");
	                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
	                sb.append("         	  AND I_STATUS = 'N'                                               							 \n");
	                sb.append("       ) A2                                                                                           \n");
	                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
	                //sb.append("     AND A1.MD_SEQ = A1.MD_PSEQ                                                                        \n");
	                sb.append("     GROUP BY K_XP, K_YP, K_ZP                                                                        \n");
	                sb.append(" 	) B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP                              \n");
	                sb.append(" 	WHERE B1.K_TYPE<3  "+tempStr+"  AND B1.K_USEYN = 'Y'                                \n");
	                sb.append(" ORDER BY B1.K_POS, B1.K_XP, B1.K_YP, B1.K_ZP ASC                                                     \n");
	            }           	
	            System.out.println(sb.toString());
	            //Log.debug(sb.toString());
	            //System.out.println(sb.toString());
	            rs = stmt.executeQuery( sb.toString() );
	
				// 마우스 오버시 처리를 위해 this.className을 임시로 저장한다.
				// 아웃될때 선택된 넘과 아닌놈이 다르게 적용되어야 하므로..
				spanID = "spanID_"+selectedXP+"_"+selectedYP+"_"+selectedZP;
								
				sbhtml.append("<span id='tmpspan' style='display:none' class=''></span>");
				sbhtml.append("<input type=\"hidden\" name=\"kgmenu_id\" id=\"kgmenu_id\" value=\""+spanID+"\">");
			
				while(rs.next()){
					// 선처리 공통
	            	kgName = rs.getString("K_VALUE");            	
	            	
	            	xp = rs.getInt("K_XP");
	            	yp = rs.getInt("K_YP");
	            	zp = rs.getInt("K_ZP");
	            	keyword_cnt = rs.getInt("K_CNT");
	
	            	linkURL = baseTarget+".location.href='"+baseURL+"&xp="+xp+"&yp="+yp+"&zp="+zp+"'";
					spanID = "spanID_"+xp+"_"+yp+"_"+zp;
					imgID = "imgID_"+xp+"_"+yp+"_"+zp;
	
					// 대분류일때 선처리
	            	if (yp==0) {
						frontSpace="";
						frontIcon = "left_ico03.gif";
						if (selectedXP==xp) { trStyle = "display:"; frontIcon = "left_ico04.gif";}
						else  { trStyle = "display:none"; }
						trID = "trID_"+xp;
						imgAction = " toggleme('"+imgID+"','trID_"+xp+"'); ";
						kgName = "" + kgName + "";
					}
					// 중분류의 선처리
	            	else if (zp==0) {
						frontSpace="&nbsp;&nbsp;";
						frontIcon = "left_ico03.gif";
						if (selectedXP==xp && selectedYP==yp) { trStyle = "display:"; frontIcon = "left_ico04.gif";}
						else  { trStyle = "display:none"; }
						trID = "trID_"+xp+"_"+yp;
						imgAction = " toggleme('"+imgID+"', 'trID_"+xp+"_"+yp+"'); ";
						kgName = "" + kgName + "";
					}
					// 키워드의 선처리
	            	else {
	            		
	            		if(rs.getString("K_OP").equals("1"))
	            		{
	            			frontIcon = "icon_a.jpg";
	            		}else if(rs.getString("K_OP").equals("2")){
	            			frontIcon = "icon_n.jpg";
	            		}else if(rs.getString("K_OP").equals("3")){
	            			frontIcon = "icon_p.jpg";
	            		}else if(rs.getString("K_OP").equals("4")){
	            			frontIcon = "icon_o.jpg";
	            		}else if(rs.getString("K_OP").equals("5")){
	            			frontIcon = "icon_k.gif";
	            		}
	            		
						frontSpace="&nbsp;&nbsp;&nbsp;&nbsp;";						
						trStyle = "";
						trID = "";
						imgAction = " ";
					}
					// 선택된 넘은 스타일을 바꺼준다.
					if (selectedXP==xp && selectedYP==yp && selectedZP==zp) {
						spanClass=" class=\"kgmenu_selected\" ";
					}
					else {
						spanClass=" class=\"kgmenu\" ";
					}
	
					// block end 처리
					if (prevyp!=0 && yp==0)
					{
						sbhtml.append("</div>	\n");
						//html+="</div>\n";
					} else if ( prevxp != 0 && prevxp != xp && prevyp==0 && yp==0)
					{
						sbhtml.append("</div>	\n");
					}
					if (prevzp!=0 && zp==0)
					{
						sbhtml.append("</div>	\n");
					} else if ( prevyp !=0 && prevyp != yp && prevzp==0 && zp==0)
					{
						sbhtml.append("</div>	\n");
					}
	
					if (showCount.equals("true")){
						
						sCount = "<span class=\"kgmenu_cnt\">[" + rs.getString("cnt") + "]</span>";
					}else{
						op = rs.getInt("K_OP");
						option = "";
						if(op==1) option = "[일반]";
						else if(op==2) option = "[인접]";
						else if(op==3) option = "[정확]";
						else if(op==4) option = "[고유]";
						else if(op==5) option = "[고유2]";
					}	
					// 메뉴의 각 항목을 처리한다.
					if (zp==0) { // 키워드 그룹일경우
						
						
						if(yp == 0 || (yp > 0 && keyword_cnt > 0)){
							sbhtml.append("    "+frontSpace+"	\n");
							sbhtml.append("      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\"../../images/search/"+frontIcon+"\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>");
							sbhtml.append(" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\"");
							sbhtml.append("onmouseout=\"kg_out(this);\">");
							sbhtml.append("<span style =\"width:190px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" title=\" " +kgName+ " \">");
							sbhtml.append("<b>"+ kgName + "</b></span> " + sCount +"");
							sbhtml.append("</span></br>			\n");
						}
					
					}else { // 키워드일 경우
						sbhtml.append("    "+frontSpace+"	\n");
						sbhtml.append("      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\"../../images/search/"+frontIcon+"\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>");
						sbhtml.append(" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\"");
						sbhtml.append("onmouseout=\"kg_out(this);\">");
						if (showCount.equals("false")){	// admin 키워드 에서 보여줄때
							sbhtml.append("<span style =\"width:120px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" title=\" " +kgName+ " \">");						
						}else{  // 정보검색에서 보여줄때 
							sbhtml.append("<span style =\"width:170px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;\" title=\" " +kgName+ " \">");	
						}					
						sbhtml.append(option+""+kgName + "</span> " + sCount);
						sbhtml.append("</span></br>		\n");					
					}
					
					// block start 처리
					if (yp==0)
					{
						sbhtml.append("<div id=\""+trID+"\" style=\""+trStyle+"\">		\n");				
					}
					else if (zp==0)
					{
						sbhtml.append("  <div id=\""+trID+"\" style=\""+trStyle+"\">	\n");					
					}
	
					// block처리를 위해 이전 xp,yp,zp를 저장한다.
					prevxp=xp;
					prevyp=yp;
					prevzp=zp;
	            }
				// html의 마지막을 장식한다.
				sbhtml.append("  </div>		\n");
				sbhtml.append("</div>		\n");
           
		
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
		return sbhtml.toString();
	}
	
	// 토글을 위한 자바스크립트를 반환한다.
	public String GetScript() {
		String kgScript = ""+
			"<SCRIPT LANGUAGE=\"JavaScript\">					\n"+
			"<!--												\n"+
			"function toggleme(object, subo) {					\n"+
			"   var obj = document.getElementById(object);		\n"+
			"	var subObj = document.getElementById(subo);		\n"+
			"	var i = 0;										\n"+
			"	var subcnt = subObj.length;						\n"+
			"	if (obj.src.indexOf('ico03')>0) {				\n"+
			"		if (subObj)	{								\n"+
			"			obj.src = '../../images/search/left_ico04.gif';		\n"+
			"			subObj.style.display='';				\n"+
			"		}											\n"+
			"	}												\n"+
			"	else {											\n"+
			"		if (subObj)	{								\n"+
			"			obj.src = '../../images/search/left_ico03.gif';		\n"+
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
			"	var prvObj = document.getElementById(document.getElementById('kgmenu_id').value);	\n"+
			"	if (prvObj)										\n"+
			"	{												\n"+
			"		prvObj.className = 'kgmenu';				\n"+
			"	}												\n"+
			"	document.getElementById('kgmenu_id').value = obj.id;			\n"+
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
}