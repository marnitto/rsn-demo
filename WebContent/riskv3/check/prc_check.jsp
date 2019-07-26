<%@page import="risk.util.DateUtil"%>
<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page import="java.io.BufferedReader
			     ,java.io.InputStream
			     ,java.io.InputStreamReader
			     ,risk.util.ParseRequest
			     ,java.util.Calendar
			     "
%>   
<%
	ParseRequest pr = new ParseRequest(request);
	String prc = pr.getString("prc", "");
%>
<%!

	String getTimeStamp(String processName) {
	
		DateUtil du = new DateUtil();
	
		if(processName.length() > 9){
			processName = processName.substring(0, 9);
		}
	
		//                  pid,ppid,etime,cmd를 시작 시간 순서로 정렬하여 가져온다. (오래된 프로세스순서대로 나온다)
		//String ps = "ps -eo pid,ppid,etime,cmd --sort=start_time | grep demon."+processName;
		String ps = "ps -eo etime,cmd --sort=start_time | grep demon."+processName;
		String[] cmdGetProcessList = {"/bin/sh", "-c", ps};
		
		String result = "";
		
		// 현재 실행중인 프로세스 리스트를 가져온다.
		try{
			String processList = "";
			String elapsedTime = "";
			
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(cmdGetProcessList);
			p.waitFor();
			
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			for(String str; (str = br.readLine()) != null;){
				processList += str+"<br>";
			}
			
			// 프로세스 리스트를 시작 순서대로 정렬해서 가져오기 때문에 제일 첫번째 프로세스 실행시간(소요시간)을 가져온다.
			processList = processList.split("<br>")[0];
			processList = processList.trim();
			elapsedTime = processList.replaceAll("\\s+", " ");
			elapsedTime = elapsedTime.split(" ")[0].trim();
			
			System.out.println(processList);
			System.out.println(elapsedTime);
			
			// 계산해볼까??? 
			if(!elapsedTime.equals("00:00")){
				// 소요시간이 있으면... 시작시간을 리턴한다.
				result = minusTimeToTimeStamp(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"), elapsedTime);	
			}else{
				// 별거 없을 때... 현재시간을 리턴한다.
				result = changeTimeStamp(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return result;
	}

	String minusTimeToTimeStamp(String time, String eTime){
		
		int year = Integer.parseInt(time.split(" ")[0].split("-")[0]);
		int month = Integer.parseInt(time.split(" ")[0].split("-")[1])-1;
		int day = Integer.parseInt(time.split(" ")[0].split("-")[2]);
		
		int hour = Integer.parseInt(time.split(" ")[1].split(":")[0]);
		int minute = Integer.parseInt(time.split(" ")[1].split(":")[1]);
		int second = Integer.parseInt(time.split(" ")[1].split(":")[2]);
		
		int minusDay = 0;
		int minusHour = 0;
		int minusMinute = 0;
		int minusSecond = 0;
		
		if(eTime.length()>8){
			minusDay 	= (-1)*Integer.parseInt(eTime.split("-")[0].trim());
			minusHour 	= (-1)*Integer.parseInt(eTime.split("-")[1].split(":")[0].trim());
			minusMinute = (-1)*Integer.parseInt(eTime.split("-")[1].split(":")[1].trim());
			minusSecond = (-1)*Integer.parseInt(eTime.split("-")[1].split(":")[2].trim());
		// '시'까지 있는경우
		}else if(eTime.length()>5){
			minusHour 	= (-1)*Integer.parseInt(eTime.split(":")[0].trim());
			minusMinute = (-1)*Integer.parseInt(eTime.split(":")[1].trim());
			minusSecond = (-1)*Integer.parseInt(eTime.split(":")[2].trim());
		// '분,초'까지 있는경우	
		}else{
			minusMinute = (-1)*Integer.parseInt(eTime.split(":")[0].trim());
			minusSecond = (-1)*Integer.parseInt(eTime.split(":")[1].trim());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		
		if(minusDay < 0){
			cal.add(Calendar.DATE, minusDay);
		}
		
		if(minusHour < 0){
			cal.add(Calendar.HOUR_OF_DAY, minusHour);
		}
		
		if(minusMinute < 0){
			cal.add(Calendar.MINUTE, minusMinute);
		}
		
		if(minusSecond < 0){
			cal.add(Calendar.SECOND, minusSecond);
		}
		
		//return sdf.format(cal.getTime());
		return Long.toString(cal.getTimeInMillis()/1000);
	}
	
	String changeTimeStamp(String time){
		int year = Integer.parseInt(time.split(" ")[0].split("-")[0]);
		int month = Integer.parseInt(time.split(" ")[0].split("-")[1])-1;
		int day = Integer.parseInt(time.split(" ")[0].split("-")[2]);
		
		int hour = Integer.parseInt(time.split(" ")[1].split(":")[0]);
		int minute = Integer.parseInt(time.split(" ")[1].split(":")[1]);
		int second = Integer.parseInt(time.split(" ")[1].split(":")[2]);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		
		
		return Long.toString(cal.getTimeInMillis()/1000);
	}

%>
IndexStamp : <%=getTimeStamp(prc)%>