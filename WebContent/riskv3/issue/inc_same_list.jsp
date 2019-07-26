<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.issue.IssueDataBean
                 ,risk.issue.IssueMgr 
                 "
%>
<%
	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.

    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    pr.printParams();

    IssueDataBean idBean = new IssueDataBean();
    
    String md_pseq    = pr.getString("md_pseq");
    String ic_seq    = pr.getString("ic_seq");
    String ic_name    = pr.getString("ic_name");
    String sDateFrom   = pr.getString("sDateFrom");
    String sDateTo     = pr.getString("sDateTo");
    String ir_stime    = pr.getString("ir_stime","16");
    String ir_etime    = pr.getString("ir_etime","16");
    
   	IssueMgr imgr = new IssueMgr();
   	ArrayList alData = imgr.getSourceData( md_pseq , ic_seq, sDateFrom, ir_stime+":00:00", sDateTo, ir_etime+":00:00") ;
%>
<div id="zzFilter">
<table width="820px" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
<col width="5%"><col width="13%"><col width="*"><col width="5%"><col width="5%"><col width="10%"><col width="11%"><col width="5%">
<%
String sunghyang ="";
if(alData.size() > 0){
	for(int i = 0; i < alData.size(); i++){
		idBean = (IssueDataBean)alData.get(i);
		sunghyang = idBean.getTemp1(); 
		//제목 색갈변경하기
		String titleColor = "";
    	if(idBean.getMd_seq().equals(idBean.getMd_pseq())){
    		titleColor = "<font color='0000CC'/>";
    	}else{
    		titleColor = "";
    	}
		
    	//출처 색갈변경하기
		String sourchColor = "";
    	System.out.println(idBean.getF_news());
    	if(idBean.getF_news() != null && idBean.getF_news().equals("Y")){
    		sourchColor = "<font color='F41B2F'/>";
    	}else{
    		sourchColor = "";
    	}
		
%>
			<tr bgcolor="F2F7FB">
				<td><img src="../../images/issue/icon.gif" /></td>
				<td><span style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></span></td>
				<td style="text-align: left;">
					<div style="width:350px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01" title="<%=idBean.getId_title()%>"><a href="javascript:PopIssueDataForm('<%=idBean.getMd_seq()%>','N');"><%=titleColor%><%=idBean.getId_title()%></a></div></td>
				<td><a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);" ><img src="../../images/issue/ico_original.gif" align="absmiddle" /></a></td>
				<td><a href="javascript:goSmsTo('<%=idBean.getId_seq()%>');" ><img src="../../images/issue/ico_sms.gif" align="absmiddle" /></a></td>
				<td><%=sourchColor%><%=ic_name%></td>
				<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
				<td><span class="tendency_0<%if(sunghyang.equals("긍정")){out.print("1");}else if(sunghyang.equals("중립")){out.print("2");}else{out.print("3");}%>"><%=sunghyang%></span></td>
			</tr>
<%
	}
}
%>
</table>
</div>
<script language="javascript">
<!--	
	parent.fillSameList('<%=md_pseq%>');
//-->
</script>