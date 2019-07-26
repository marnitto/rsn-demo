<%@ page contentType = "text/html; charset=utf-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script language="javascript">
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
	
	//document.getElementById('cnt').innerHTML = chkCount;     
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
</script>
</head>
<body>
<form name="ifr_receiver" method="post">
<input type="hidden" name="delSeq">
<input type="hidden" name="existAb_seq" value="<%=ab_seq%>">
<table width="820" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td style="padding-top:15px;height:45px;"><span class="sub_tit">수신자 설정</span></td>
</tr>
<tr>
	<td>
	<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="height:30px;table-layout:fixed;">
	<col width="5%"><col width="15%"><col width="15%"><col width="65%">
	<tr>           
			<th><input type="checkbox" name="checkall" id="ab_seqALL" onclick="checkAll(this.checked);"></th>
			<th>성명</th>
			<th>부서</th>
			<th>메일주소</th>
		</tr>
	</table>
	<div style="height:125px;overflow:auto">
	<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<col width="5%"><col width="15%"><col width="15%"><col width="65%">
<%
	if(arrReceiverList.size()>0){
		for(int i=0; i<arrReceiverList.size(); i++){
			arBean = new AlimiReceiverBean();
			arBean = (AlimiReceiverBean)arrReceiverList.get(i);
			
			if(arrAb_seq!=null){				
				checked ="";
				for(int j=0 ; j<arrAb_seq.length; j++){
					if(arrAb_seq[j].equals(arBean.getAb_seq())){
						System.out.println(arrAb_seq[j]);
						checked ="checked";	
					}
				}
			}
%>
		<tr>
			<td><input type="checkbox" name="chkNum" value="<%=arBean.getAb_seq()%>" onclick="listCnt();" <%=checked%>></td>
			<td><%=arBean.getAb_name() %></td>
			<td><%=arBean.getAb_dept()%></td>
			<td><span class="mail"><%=arBean.getAb_mail()%></span></td>
		</tr>
<%
		}
	}else{
%>
		<tr>
			<td colspan="4" style="font-weight:bold;height:40px" align="center">조건에 맞는 데이터가 없습니다.</td>
		</tr>
<%
	}
%>
	</table>
	</div>
	</td>
</tr>
</table>
</form>
</body>
</html>
