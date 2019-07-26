<%
/*******************************************************
*  1. 분    류    명 : 관리자메뉴 키워드그룹설정
*  2. 업무 시스템 명 : 정보검색검색
*  3. 프로그램  개요 : 키워드설정 사용자화면
*  4. 관 련  Table명 : KEYWORD
*  5. 작    성    자 : 박도형
*  6. 작    성    일 : 2006.4.21
*  7. 주  의  사  항 :
*  8. 변   경   자   :
*  9. 변 경  일 자   :
* 10. 변 경  사 유   :
********************************************************/
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="risk.util.*,
				 java.lang.*,
				 risk.admin.keyword.KeywordMng,
				 risk.admin.keyword.KeywordBean
                 "
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%

/*
    ParseRequest    pr = new ParseRequest(request);

	String tg			= pr.getString("tg");
	String mode			= pr.getString("mode");
    String xp			= pr.getString("xp");
    String yp			= pr.getString("yp");
    String zp			= pr.getString("zp");
    String val			= pr.getString("val");

	String[] type = null;
	String Script = "";
	int i;

	
	
		KeywordMng keymng = KeywordMng.getInstance();

	if( tg.equals("total") ) {
		if( mode.equals("ins") ) {
			keymng.insert(val, SS_M_NAME);
		}
	} else if( tg.equals("upkg") ) {
		if( mode.equals("ins") ) {
			keymng.insert( Integer.parseInt(xp), val, SS_M_NAME );
			Script = "?xp="+xp+"";
		} else if( mode.equals("edit") ) {
			keymng.update( Integer.parseInt(xp), val, SS_M_NAME );
			Script = "?xp="+xp+"";
		} else if( mode.equals("del") ) {
			keymng.delete( Integer.parseInt(xp), SS_M_NAME );
			Script = "";
		} else if( mode.equals("up") ) {
			keymng.moveUP( Integer.parseInt(xp), SS_M_NAME );
			Script = "?xp="+xp+"";
		} else if( mode.equals("down") ) {
			keymng.moveDOWN( Integer.parseInt(xp), SS_M_NAME );
			Script = "?xp="+xp+"";
		} else {
			Script = "?xp="+xp+"";
		} 
		
		
		
		
		
	} else if( tg.equals("downkg") ) {
		if( mode.equals("ins") ) {
			keymng.insert( Integer.parseInt(xp), Integer.parseInt(yp), val, 1, SS_M_NAME);
			Script = "?xp="+xp+"&yp="+yp+"";
		} else if( mode.equals("edit") ) {
			keymng.update( Integer.parseInt(xp), Integer.parseInt(yp), val, SS_M_NAME );
			Script = "?xp="+xp+"&yp="+yp+"";
		} else if( mode.equals("del") ) {
			keymng.delete( Integer.parseInt(xp), Integer.parseInt(yp), SS_M_NAME );
			Script = "?xp="+xp+"";
		} else {
			Script = "?xp="+xp+"";
		}
		
		
		
	} 
	
		else if( tg.equals("kg") ) {
		if( mode.equals("ins") ) {
			keymng.insert( Integer.parseInt(xp), Integer.parseInt(yp), Integer.parseInt(zp), 0, val, SS_M_NAME  );
			Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
		} else if( mode.equals("edit") ) {
			Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
		} else if( mode.equals("del") ) {
			keymng.delete( Integer.parseInt(xp), Integer.parseInt(yp), Integer.parseInt(zp), SS_M_NAME );
			Script = "?xp="+xp+"&yp="+yp+"";
		} else {
			Script = "?xp="+xp+"&yp="+yp+"";
		} 
		
		
		 
	} else if( tg.equals("exkey") ) { 
		   StringBuffer sb = new StringBuffer();

			if( request.getParameter("type") != null ) type = request.getParameterValues("type");

			for(i=0; i< type.length;i++)
			{
				if( i != 0) sb.append(",");
				sb.append(type[i]);
			}
			keymng.delete(Integer.parseInt(xp), Integer.parseInt(yp), Integer.parseInt(zp), sb.toString(), SS_M_NAME);
			Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
	} else {
		Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
	}
	*/
	
	ParseRequest    pr = new ParseRequest(request);

	String tg			= pr.getString("tg");
	String mode			= pr.getString("mode");
    String xp			= pr.getString("xp");
    String yp			= pr.getString("yp");
    String zp			= pr.getString("zp");
    String val			= pr.getString("val").replaceAll("'","''");
    String typeCode 	= pr.getString("typeCode","");
    
	int op			= pr.getInt("k_op",1);
	String[] type = null;
	String Script = "";
	int i;
	
	String sg_seqs = pr.getString("sg_seqs");
	
	System.out.println("tg = "+tg+", mode = "+mode+" ");
	System.out.println("xp = "+xp+", yp = "+yp+", zp = "+zp+", val = "+val);

	
	
	KeywordMng keymng = KeywordMng.getInstance();

	if( tg.equals("total") ) {
		if( mode.equals("ins") ) {
			String[] tmpVal = val.split("\n");
			for(int j=0; j<tmpVal.length; j++){
				keymng.insert(tmpVal[j].trim(), SS_M_NO);
			}
		}
	} else if( tg.equals("upkg") ) {
		if( mode.equals("ins") ) {
			String[] tmpVal = val.split("\n");
			for(int j=0; j<tmpVal.length; j++){
				keymng.insert( Integer.parseInt(xp), tmpVal[j].trim(), SS_M_NO );
			}
			Script = "?xp="+xp+"";
		} else if( mode.equals("edit") ) {
			keymng.update( Integer.parseInt(xp), val, SS_M_NO );
			Script = "?xp="+xp+"";
		} else if( mode.equals("del") ) {
			keymng.delete( Integer.parseInt(xp), SS_M_NO );
			Script = "";
		} else if( mode.equals("up") ) {
			keymng.moveUP( Integer.parseInt(xp), SS_M_NO );
			Script = "?xp="+xp+"";
		} else if( mode.equals("down") ) {
			keymng.moveDOWN( Integer.parseInt(xp), SS_M_NO );
			Script = "?xp="+xp+"";
		} else if( mode.equals("sitegroup") ) {
			keymng.setSiteGroup(xp, sg_seqs);
			Script = "?xp="+xp+"";
		} else {
			Script = "?xp="+xp+"";
		}
	} else if( tg.equals("downkg") ) {
		if( mode.equals("ins") ) {
			String[] tmpVal = val.split("\n");
			for(int j=0; j<tmpVal.length; j++){
				
				//keymng.insert( Integer.parseInt(xp), Integer.parseInt(yp), tmpVal[j].trim(), op, SS_M_NO);
				
				//키워드 자동 분류 저장
				keymng.insertAnaTypeCode( Integer.parseInt(xp), Integer.parseInt(yp), tmpVal[j].trim(), op, SS_M_NO, typeCode);
			}
			Script = "?xp="+xp+"&yp="+yp+"";
		} else if( mode.equals("edit") ) {
			keymng.update( Integer.parseInt(xp), Integer.parseInt(yp), val, SS_M_NO );
			Script = "?xp="+xp+"&yp="+yp+"";
		} else if( mode.equals("del") ) {
			keymng.delete( Integer.parseInt(xp), Integer.parseInt(yp), SS_M_NO );
			Script = "?xp="+xp+"";
		} else {
			Script = "?xp="+xp+"";
		}
	}else if( tg.equals("kg") ) {
		if( mode.equals("ins") ) {
			String[] tmpVal = val.split("\n");
			for(int j=0; j<tmpVal.length; j++){
				keymng.insert( Integer.parseInt(xp), Integer.parseInt(yp), Integer.parseInt(zp), 1, tmpVal[j].trim(), SS_M_NO  );
			}
			Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
		} else if( mode.equals("edit") ) {
			Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
		} else if( mode.equals("del") ) {
			keymng.delete( Integer.parseInt(xp), Integer.parseInt(yp), Integer.parseInt(zp), SS_M_NO );
			Script = "?xp="+xp+"&yp="+yp+"";
		} else {
			Script = "?xp="+xp+"&yp="+yp+"";
		}
	} else if( tg.equals("exkey") ) {
		   StringBuffer sb = new StringBuffer();

			if( request.getParameter("type") != null ) type = request.getParameterValues("type");

			for(i=0; i< type.length;i++)
			{
				if( i != 0) sb.append(",");
				sb.append(type[i]);
			}
			keymng.delete(Integer.parseInt(xp), Integer.parseInt(yp), Integer.parseInt(zp), sb.toString(), SS_M_NO);
			Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
	} else {
		Script = "?xp="+xp+"&yp="+yp+"&zp="+zp+"";
	}
	
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	parent.keyword_left.location.href = 'admin_keyword_left.jsp<%=Script%>';
	document.location.href = "admin_keyword_right.jsp<%=Script%>";
//-->
</SCRIPT>