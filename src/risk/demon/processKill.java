package risk.demon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import risk.util.DateUtil;

//import risk.util.Log;

public class processKill {
	public static void main(String[] args){
		DateUtil du = new DateUtil();
		String date = du.getCurrentDate("yyyy-MM-dd");
		String ps = "ps -ef | grep root | grep java | grep demon | grep DAEGU";
		//String charset = "euc-kr";
		String charset = "utf-8";
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] cmd = {"/bin/sh", "-c", ps};
		Date sdate = null;
		Date edate = null;
		try{
			
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(cmd);
			p.waitFor();
			
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
			String processList = "";
			for(String str; (str = br.readLine()) != null;){
				processList += str+"\n";
			}
			String targetProcess = "";
			for(int i = 0; i < processList.split("\n").length; i++){
				if(targetProcess.equals("")){
					targetProcess = processList.split("\n")[i];
				}else{
					targetProcess += ","+processList.split("\n")[i];
				}
			}

			String pid = "";
			if(!targetProcess.equals("")){
				for(int i = 0; i < targetProcess.split(",").length; i++){
					String element = targetProcess.split(",")[i].replaceAll("\\s{1,}","!@#");
					String executeTime = element.split("!@#")[6];
					
					String pTime = element.split("!@#")[4];
					Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}");
					Matcher matcher = pattern.matcher(pTime);
					
					if(!matcher.matches()){
						pTime = "00:00";
					}
					
					String time = date+" "+pTime+":00";
//					Log.crond("processKill" ,element.split("!@#")[1]+" : "+time+" :: "+element.split("!@#")[6]+" ==>> "+element.split("!@#")[8]);
					
					sdate = fmt.parse(time);
					edate = new Date();
					int min = (int) (((edate.getTime() - sdate.getTime()) / 1000) / 60);
//					Log.crond("processKill" ,"min : "+min);
					if(min > 60){
						if(pid.equals("")){
							pid = element.split("!@#")[1];
						}else{
							pid += " "+element.split("!@#")[1];
						}
					}
					
//					Log.crond("processKill" ,"executeTime : "+executeTime);
//					Log.crond("processKill" ,"check time : "+Integer.parseInt(executeTime.split(":")[1]));
					if(Integer.parseInt(executeTime.split(":")[1]) >= 60){
						if(pid.equals("")){
							pid = element.split("!@#")[1];
						}else{
							pid += " "+element.split("!@#")[1];
						}
					}
				}
			}else{
//				Log.crond("processKill" ,"Process not execute..");
				System.out.println("Process not execute..");
			}
//			Log.crond("processKill" ,"pid : "+pid);
			
			if(!pid.equals("")){
					Runtime rt1 = Runtime.getRuntime();
					Process p1 = rt1.exec(cmd);
					String[] kill = {"/bin/sh", "-c", "kill -9 "+pid};
					p1 = rt1.exec(kill);
					p1.waitFor();
					
					InputStream is1 = p1.getInputStream();
					BufferedReader br1 = new BufferedReader(new InputStreamReader(is1, charset));
					String killList = "";
					for(String str; (str = br.readLine()) != null;){
						killList += str+"\n";
					
					System.out.println("killList : "+killList);
				}
			}
		}catch(Exception e){
			System.out.println( e.toString());
			e.printStackTrace();
		}
	}
}