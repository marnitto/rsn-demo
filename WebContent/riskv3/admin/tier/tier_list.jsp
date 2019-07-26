<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="
                java.util.ArrayList
                ,risk.util.*                
                ,risk.admin.tier.*"
%>  
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	ArrayList arr = new ArrayList();
	TierSiteMgr msMgr = new TierSiteMgr();
	TierSiteBean tsBean = new TierSiteBean();
	
	String ts_types = pr.getString("ts_type","");	
	arr = msMgr.getTierSiteList(0,0,"",ts_types);
	
	if(arr.size()>0)
	{

%>
		<div style="height:290px;overflow: auto;">
		<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="cdd4d6">
			<col width="32"><col width="57"><col width="*"><!-- <col width="236"> --><col width="170">
<%
			for(int i =0; i<arr.size(); i++)
			{
				tsBean = new TierSiteBean();
				tsBean = (TierSiteBean)arr.get(i);
%> 		
			<tr>		
			  <td align="center"bgcolor="#FFFFFF"><%=i+1%></td>	  
              <td align="center"bgcolor="#FFFFFF" class="table_top"><select id="ts_type<%=i%>" name="ts_type<%=i%>"><option value="1" <%if(tsBean.getTs_type().equals("1"))out.print("selected");%>>1</option><option value="2" <%if(tsBean.getTs_type().equals("2"))out.print("selected");%>>2</option><option value="3" <%if(tsBean.getTs_type().equals("3"))out.print("selected");%>>3</option><option value="4" <%if(tsBean.getTs_type().equals("4"))out.print("selected");%>>4</option><option value="4" <%if(tsBean.getTs_type().equals("5"))out.print("selected");%>>5</option></select></td>
              <td align="center" bgcolor="#FFFFFF" class="table_top"><%=tsBean.getTs_name()%></td>              
              <%-- <td align="center" bgcolor="#FFFFFF">
                <input id="ts_rank<%=i%>" name="ts_rank<%=i%>" type="text" class="input_text" style="width:105px" value="<%=tsBean.getTs_rank()%>" onkeyup="fnTextCheck(this);"/> 
              </td> --%>             
              <td align="center" bgcolor="#FFFFFF"><img src="../../../images/admin/tier/modify_btn.gif" width="39" height="23" align="absmiddle" style="cursor:pointer;" onclick="updateTierSite('<%=tsBean.getTs_seq()%>','ts_type<%=i%>','ts_rank<%=i%>');"><img src="../../../images/admin/tier/delete_btn.gif" width="39" height="23" align="absmiddle" style="cursor:pointer;" onclick="deleteTierSite('<%=tsBean.getTs_seq()%>');"></td>
            </tr>                    
<%
			}
%>	
		</table>
		</div>
<%
	}
%>
