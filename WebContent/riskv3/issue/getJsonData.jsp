<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>

<%-- <%@ page import="net.sf.json.JSONArray"%>
<%@ page import="net.sf.json.JSONObject"%> --%>
<%@ page import="risk.json.JSONObject"%>
<%@ page import="risk.json.JSONArray"%>

<%@ page import="risk.util.ParseRequest"%>
<%@ page import="risk.issue.IssueCodeMgr"%>
<%@ page import="risk.issue.IssueCodeBean"%>
<%

ParseRequest    pr = new ParseRequest(request);
pr.printParams();
IssueCodeMgr icMgr = new IssueCodeMgr();
IssueCodeBean icBean= new IssueCodeBean();
String icPtype = pr.getString("ic_Ptype","");
String icPcode = pr.getString("ic_Pcode","");
String flag = pr.getString("flag","");
String kind = pr.getString("icType1","");
String type5 = pr.getString("type5");
String type51 = pr.getString("type51");


if(flag.equals("2death")){
	
	JSONObject jObect = new JSONObject(); 
	JSONArray jArray = new JSONArray();
	ArrayList alDate  = new ArrayList();
	
	alDate =  icMgr.getArrayListForIssueCode(icPtype, icPcode);
	
	if(alDate.size() > 0){
		for(int i =0; i < alDate.size(); i++){
			icBean = (IssueCodeBean)alDate.get(i);
			
			JSONObject object = new JSONObject();
			object.put("icName",icBean.getIc_name());
			object.put("icType",icBean.getIc_type());
			object.put("icCode",icBean.getIc_code());
			jArray.put(object);
		}
	}
	jObect.put("list", jArray);
	out.println(jObect);
}else if(flag.equals("1death")){
		
	JSONObject jObect = new JSONObject(); 
	JSONArray jArray = new JSONArray();
	ArrayList alDate  = new ArrayList();
	
	alDate =  icMgr.getArrayListForOptionCode(kind);
	
	if(alDate.size() > 0){
		for(int i =0; i < alDate.size(); i++){
			icBean = (IssueCodeBean)alDate.get(i);
			
			JSONObject object = new JSONObject();
			object.put("icName",icBean.getIc_name());
			object.put("icType",icBean.getIc_type());
			object.put("icCode",icBean.getIc_code());
			jArray.put(object);
		}
	}
	jObect.put("list", jArray);
	out.println(jObect);
}else if(flag.equals("0death")){
	JSONObject jObect = new JSONObject(); 
	JSONArray jArray = new JSONArray();
	ArrayList alDate  = new ArrayList();
	
	alDate =  icMgr.getArrayListForTypeCode2(kind);
	
	if(alDate.size() > 0){
		for(int i =0; i < alDate.size(); i++){
			icBean = (IssueCodeBean)alDate.get(i);
			
			JSONObject object = new JSONObject();
			object.put("icName",icBean.getIc_name());
			object.put("icType",icBean.getIc_type());
			object.put("icCode",icBean.getIc_code());
			jArray.put(object);
		}
	}
	jObect.put("list", jArray);
	out.println(jObect);	
}else if("manual".equals(flag)){
	JSONObject obj = new JSONObject();	
	
	obj = icMgr.getArrayListForIssueCode_Manual(type5,type51);
	
	out.println(obj);
}

%>