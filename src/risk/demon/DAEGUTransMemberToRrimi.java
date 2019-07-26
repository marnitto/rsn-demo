package risk.demon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import risk.DBconn.DBconn;
import risk.json.JSONArray;
import risk.json.JSONObject;
import risk.util.ConfigUtil;
import risk.util.Log;

/*
 * TODO 알리미 고객사 이름 교체 line 127 참조  risk.properties 고객사 이름을 유니코드로 저장   
 * transMember.put("organizationName", cu.getConfig("COMPANY"));
 */
public class DAEGUTransMemberToRrimi{
	static DBconn dbconn = null;
	
	public static void main(String[] args){
		Log.writeExpt("RRIMILOG", "TransMemberToRrimi Start..");
		try{
			dbconn = new DBconn();
			dbconn.getSubDirectConnection();
			
			//1. 전체 member를 가져온다
			//	 getMember();
			ArrayList member = getMember();
			Log.writeExpt("RRIMILOG", "getMember - ok!");
			
			//2. 가져온 member로 json data만든다
			String sendData = makeJson(member);
			Log.writeExpt("RRIMILOG", "makeJson - ok!");
			
			//3. 보낸다.
			String result = send(sendData);
			Log.writeExpt("RRIMILOG", "send - ok!");
			Log.writeExpt("RRIMILOG", "전송결과 : "+result);
			
        }catch(Exception ex) {
        	ex.printStackTrace();
			Log.writeExpt("RRIMILOG", ex.toString());
        }finally{
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
		Log.writeExpt("RRIMILOG", "TransMemberToRrimi Terminate");
	}
	
	public static ArrayList getMember(){
		
		Statement stmt  = null;
        ResultSet rs    = null;
        StringBuffer sb = null;

        ArrayList result = new ArrayList();
        
    	try
    	{
			stmt = dbconn.createStatement();			
            sb = new StringBuffer();
            sb.append("	SELECT AB_MAIL MAIL, AB_NAME NAME, AB_MOBILE HP  								\n");
            sb.append("	FROM ADDRESS_BOOK                                                           	\n");
            sb.append("	WHERE AB_MAIL IS NOT NULL AND AB_NAME IS NOT NULL AND AB_MOBILE IS NOT NULL 	\n");
            sb.append("	  AND AB_MAIL <> '' AND AB_NAME <> '' AND AB_MOBILE <> ''                   	\n");
          
            System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				if(isValidEmail(rs.getString("MAIL")) && isValidPhoneNo(rs.getString("HP"))){
					String[] data = new String[3];
					data[0] = rs.getString("MAIL");
					//data[1] = rs.getString("PASS");
					data[1] = rs.getString("NAME");
					data[2] = rs.getString("HP");
					result.add(data);				
				}
			}
			
    	 } catch (SQLException ex ) {
    		 ex.printStackTrace();
    		 Log.writeExpt("RRIMILOG", ex.toString());
         } catch (Exception ex ) {
        	 ex.printStackTrace();
        	 Log.writeExpt("RRIMILOG", ex.toString());
         } finally {
        	 sb = null;
             try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
             try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
         }   
         
         return result;
	}
	
	public static String makeJson(ArrayList member){
		
		JSONObject transMember = new JSONObject();
		JSONArray userArray = new JSONArray();
		
		ConfigUtil cu = new ConfigUtil();
		
		
		try{
			
			for (int i=0; i<member.size(); i++) {
				String[] data = (String[])member.get(i);
				
				JSONObject userInfo = new JSONObject();
				userInfo.put("email", data[0]);
				//userInfo.put("password", data[1]);
				userInfo.put("userName", data[1]);
				userInfo.put("mobileNo", data[2].replaceAll("-", ""));
				userArray.put(userInfo);
			}
			
			transMember.put("users", userArray);
			transMember.put("organizationName", cu.getConfig("COMPANY"));
			
			System.out.println("고객사 : "+transMember.get("organizationName"));
			
		}catch(Exception e){
			e.printStackTrace();
			Log.writeExpt("RRIMILOG", e.toString());
		}
		
		return transMember.toString();
	}
	
    public static String send(String data){
    	
    	StringBuffer result = new StringBuffer();
    	
		try {
			URL postUrl = new URL("http://rrimi.realsn.com/web/ajax/syncUserData.do");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
	    	connection.setDoOutput(true); 				// xml내용을 전달하기 위해서 출력 스트림을 사용
	    	connection.setInstanceFollowRedirects(false);  //Redirect처리 하지 않음
	    	connection.setRequestMethod("POST");
	    	connection.setRequestProperty("Content-Type", "application/json");
	    	OutputStream os= connection.getOutputStream();
	    	os.write(data.getBytes("UTF-8"));
	    	os.flush();
//	    	System.out.println("Location: " + connection.getHeaderField("Location"));
	    	BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
	    		
	    	String output;
//	    	System.out.println("Output from Server .... \n");

	    	while ((output = br.readLine()) != null) {
	    		result.append(output);
	    	}
	    	connection.disconnect();
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.writeExpt("RRIMILOG", e.toString());
		}
		
		return result.toString();
    }
    
    public static boolean isValidEmail(String inputStr) {
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputStr);
    
		return m.matches();
    }
    
    public static boolean isValidPhoneNo(String inputStr) {
    	String regex = "^[0-9]+";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputStr.replaceAll("-", "").replaceAll("\\)", ""));
    
		return m.matches() & (inputStr.replaceAll("-", "").length() > 9);
    }    
}