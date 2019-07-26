<%@ page contentType = "text/html; charset=euc-kr"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%@ page import="	java.util.ArrayList
                 	,risk.mobile.AlimiReceiverBean
					,risk.mobile.AlimiSettingDao
					,risk.util.ParseRequest					
				 	,java.util.List
					" 
%>
<%
	ParseRequest pr = new ParseRequest(request);
	AlimiSettingDao asDao = new AlimiSettingDao();
	ArrayList arrReceiverList =  new ArrayList();
	AlimiReceiverBean arBean = new AlimiReceiverBean();	
	
	String mode = pr.getString("mode","");
	String ab_seq = pr.getString("ab_seq","");
	String[] arrAb_seq = null;
	String checked = "";
	int abSeqCount = 0;
		
	if(ab_seq!=null && !ab_seq.equals(""))
	{
		arrAb_seq = ab_seq.split(",");
		abSeqCount = arrAb_seq.length;
	}
	
	
	String existsAb_seq = "";
	arrReceiverList = asDao.getAddressList("","","");	
	pr.printParams();
	
	
%>
<html>
<head>
<title><%=SS_TITLE %></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../css/basic.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
var listCheckCnt = 0;
function checkAll(check)
{
	listCheckCnt = 0;  
	var checked = check;
	var f = document.ifr_receiver;		
    for (var i = 0; i < f.elements.length; i++) {        	 
        var e = f.elements[i];
        if ( e.type == "checkbox" ) {
            if(check)
            {
        		listCheckCnt++;
            }else{
            	listCheckCnt--;
            }
            e.checked = checked;
        }       
    }
    listCnt();    
}

function checkList()
{
	var f = document.ifr_receiver;
	var checked = false;
	var values = new Array();
	

	if(f.chkNum)
	{
		if(f.chkNum.length)
		{
			for(var i =0;i<f.chkNum.length;i++)
			{				
				if(f.chkNum[i].checked)
				{
					checked = true;
					values = f.chkNum[i].value.split(',');					
					if(f.delSeq.value=='')
					{
						f.delSeq.value = values[0];
					}
					else
					{
						f.delSeq.value +=','+ values[0];
					}
				}
			}
			
		}else{			
			if(f.chkNum.checked)
			{
				checked = true;
				values = f.chkNum.value.split(',');
				f.delSeq.value = values[0];				
			}
		}
	}	
	return checked;	
}

function listCnt()
{
	var f = document.ifr_receiver;
	var chkCount = 0;

	if(f.chkNum)
	{
		if(f.chkNum.length)
		{
			for(var i =0;i<f.chkNum.length;i++)
			{				
				if(f.chkNum[i].checked)
				{
					chkCount ++;
				}
			}			
		}else{			
			if(f.chkNum.checked)
			{
				chkCount ++;			
			}
		}
	}
	
	document.getElementById('cnt').innerHTML = chkCount;     
}


function receiverDel(){
	
	var f = document.ifr_receiver ;	
	if(f.existAb_seq.value.length>0)
	{
		if(checkList())
		{
			f.target = '';
			f.action = 'pop_receiver_del_prc.jsp';
			f.submit();
		}else{
			alert("삭제할 수신자를 체크하여 주십시오.");
		}
	}else{
		alert("설정된 수신자가 없습니다.");
	}
}

function receiverAdd(){ 		
	window.open("pop_receiver_detail.jsp", "receiverAdd", "width=650,height=220,scrollbars=yes");
}

function receiverCall(){
	var f = document.ifr_receiver ;
	f.target = '';
	f.action = 'pop_receiver_call_prc.jsp';
	f.submit();	
}
//-->
</SCRIPT>
</head>
<body leftmargin="10" topmargin="0" marginwidth="0" marginheight="0" onload="" >
<form name="ifr_receiver" method="post">
<input type="hidden" name="delSeq">
<table width="780" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding: 0px 0px 0px 10px;">
		<table width="750" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td height="25" colspan="2"><table width="600" border="0" cellspacing="0" cellpadding="0">
		         <tr>
		           <td width="15"><img src="../images2/ico_won_blue.gif" width="11" height="11"></td>
		           <td class="BIG_title02" style="padding: 2px 0px 0px 0px;"><strong>수신자 설정</strong></td>
		         </tr>
		     </table></td>
		   </tr>		   
		</table>
		<table width="750" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<!--
			<td width="55"><img src="images/btn_del.gif" width="55" height="24"  style="cursor:hand;" onclick="receiverDel();">&nbsp;</td>
		   	<td width="97"><img src="images/btn_receive_add.gif" width="97" height="24" hspace="5" onclick="receiverAdd();" style="cursor:hand;">&nbsp;</td>
		   	<td width="457" align="right">수신자 <b><span id="cnt">0</span></b>/<span id="tCnt"><%=arrReceiverList.size()%> 명</span></td>   	
		   	<td width="156" align="right"><img src="images/btn_mailopen.gif" width="141" height="24" hspace="5" style="cursor:hand;" onclick="receiverCall();" title="메일수신자 불러오기 : 부서별 메일수신자를 불러옵니다."></td> 
		   	-->
		   	<td width="55"></td>
		   	<td width="97"></td>
		   	<td width="457" align="right">수신자 <b><span id="cnt"><%=abSeqCount%></span></b>/<span id="tCnt"><%=arrReceiverList.size()%> 명</span></td>   	
<!--		   	<td width="156" align="right"><img src="../images/btn_receive_add.gif" width="97" height="24" hspace="5" onclick="receiverAdd();" style="cursor:hand;">&nbsp;</td> -->
			</tr>
		</table>
		
		<table width="750" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td width="5" align="center" bgcolor="#F0F0F0" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><input type="checkbox" name="checkall" id="ab_seqALL" onclick="checkAll(this.checked);"></td>
			  <td width="3" background="images/issue_table_bg02.gif"><img src="../images2/admin_table_bar.gif" width="3" height="32"></td>
			  <td width="150" align="center" bgcolor="#F0F0F0" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>성명</strong></td>
			  <td width="3" background="images/issue_table_bg02.gif"><img src="../images2/admin_table_bar.gif" width="3" height="32"></td>
			  <td width="268" align="center" bgcolor="#F0F0F0" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>부서</strong></td>
			  <td width="3" background="images/issue_table_bg02.gif"><img src="../images2/admin_table_bar.gif" width="3" height="32"></td>
			  <td width="318" align="center" bgcolor="#F0F0F0" background="../images2/admin_table_bg01.gif" class="table_top" style="padding: 5px 0px 0px 0px;"><strong>메일주소</strong></td>			  
			</tr>
			<tr>
	          <td colspan="7" bgcolor="#D3D3D3"><img src="../images2/brank.gif" width="1" height="1"></td>
	        </tr>
		</table>
		<div style="overflow-y:auto; width:750; height:129px;">		
		<table width="730" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
	
	//existsAb_seq="";
	if(arrReceiverList.size()>0)
	{
		for(int i=0; i<arrReceiverList.size(); i++)
		{
			arBean = new AlimiReceiverBean();
			arBean = (AlimiReceiverBean)arrReceiverList.get(i);
			//existsAb_seq += existsAb_seq.equals("") ? arBean.getAb_seq() : ","+arBean.getAb_seq();
			
			if(arrAb_seq!=null)
			{				
				checked ="";
				for(int j=0 ; j<arrAb_seq.length; j++)
				{
					if(arrAb_seq[j].equals(arBean.getAb_seq()))
					{
						System.out.println(arrAb_seq[j]);
						checked ="checked";	
					}
				}
			}
			
			if( i>0 )
			{
%>
			<tr>
		    	<td width="100%" colspan="7"  background="../images2/admin_table_dot.gif" ><img src="../images2/brank.gif" width="1" height="1"></td>
		    </tr>
<%
			}
%>
			
			<tr height="25" align="center">
			  <td width="5" style="padding: 3px 0px 0px 0px;"><input type="checkbox" name="chkNum" value="<%=arBean.getAb_seq()%>" onclick="listCnt();" <%=checked%> ></td>
			  <td width="3">&nbsp;</td>
			  <td width="150" style="padding: 3px 0px 0px 0px;"><%=arBean.getAb_name() %></td>
			  <td width="3">&nbsp;</td>
			  <td width="268" style="padding: 3px 0px 0px 0px;"><%=arBean.getAb_dept()%></td>
			  <td width="3">&nbsp;</td>
			  <td width="318" style="padding: 3px 0px 0px 0px;"><%=arBean.getAb_mail()%></td>			 
			</tr>
<%
			

			

		}
%>
		<tr>
	    	<td width="100%" height="100%" colspan="7"></td>
	    </tr>
<%
	}else{
%>
			<tr>
		    	<td width="100%" height="100%" align="center">수신자가 존재하지 않습니다.</td>
		    </tr>
<%		
	}
%>
		
		</table>		
		</div>	
	</td>
</tr>
</table>

<table width="750" border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td style="padding: 0px 0px 0px 10px;"><table table width="750" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	    <td  width="3" background="../images2/admin_table_bg01.gif" height="2"></td>
	   </tr>
   </table></td>
   </tr>		   
</table>


<input type="hidden" name="existAb_seq" value="<%=ab_seq%>">
</form>
</body>
</html>
