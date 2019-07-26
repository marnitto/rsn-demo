package risk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.*;

public class PersonalInfoUtil {
	
	public static void main(String[] args) {
		
		PersonalInfoUtil piUtil = new PersonalInfoUtil();
		
		String targetTitle = "111-111-1111 matt.seo@realsn.com  mattseo@realsn.com #서영진,#서영진1 #mattwww #matt,#mattseq @abcdefg,@abcdefg @abcd_efg @abcde-fg @abss333cdefg @abcdefg222  58.180.17.2 01055052570 010-5505-2570 031)965-6978";
		//String targetTitle ="One can not afford to miss to shop from Shop CJ to get best discount on every product! #ShopCJFashionExpress999 @ShopCJIndia ";
		
		targetTitle = piUtil.removeUrl(piUtil.removeHashTag(piUtil.removeTwitterID(targetTitle)) );
		System.out.println(targetTitle);
		//System.out.println(piUtil.removeTwitterID(targetTitle));
		//System.out.println(piUtil.removeHashTag(targetTitle));
		
	}
	
	private String[] regexpStr = null;
	private String[] regexpReplaceStr = null;
	
	public PersonalInfoUtil() {
		regexpStr = new String[32];
		regexpStr[0] = "\\d{18}";												//******************	[18]
		regexpStr[1] = "[(]\\d{3}[)][\\s-:.]\\d{4}[\\s-:.]\\d{4}";				//(***)-****-****	[15]
		regexpStr[2] = "[(]\\d{4}[\\s]\\d{2}[)][\\s-:.]\\d{5}";					//(**** **)-*****	[15]
		regexpStr[3] = "\\d{15}";												//***************	[15]
		regexpStr[4] = "\\d{1}[\\s-:.]\\d{3}[\\s-:.]\\d{3}[\\s-:.]\\d{4}";		//*-***-***-****	[14]
		regexpStr[5] = "[(]\\d{3}[)][\\s-:.]\\d{3}[\\s-:.]\\d{4}";				//(***)-***-****	[14]
		regexpStr[6] = "[(]\\d{5}[)][\\s-:.]\\d{6}";							//(*****)-******	[14]
		regexpStr[7] = "[(]\\d{4}[\\s]\\d{2}[)][\\s-:.]\\d{4}";					//(**** **)-****	[14]
		regexpStr[8] = "[(]\\d{2}[)][\\s-:.]\\d{4}[\\s-:.]\\d{4}";				//(**)-****-****	[14]
		regexpStr[9] = "\\d{6}[\\s-:.]\\d{7}";									//******-*******	[14]
		regexpStr[10] = "\\d{3}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					//***-****-****	[13]
		regexpStr[11] = "[(]\\d{5}[)][\\s-:.]\\d{5}";							//(*****)-*****	[13]
		regexpStr[12] = "\\d{3}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					//*** **** ****	[13]
		regexpStr[13] = "\\d{13}";												//*************	[13]
		regexpStr[14] = "\\d{3}[\\s-:.]\\d{3}[\\s-:.]\\d{4}";					//***-***-****	[12]
		regexpStr[15] = "\\d{2}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					//**-****-****	[12]
		regexpStr[16] = "\\d{5}[\\s-:.]\\d{6}";									//*****-******	[12]
		regexpStr[17] = "\\d{4}[\\s-:.]\\d{3}[\\s-:.]\\d{3}";					//****-***-***	[12]
		regexpStr[18] = "\\d{12}";												//************	[12]
		regexpStr[19] = "\\d{2}[\\s-:.]\\d{3}[\\s-:.]\\d{4}";					//**-***-****	[11]
		regexpStr[20] = "\\d{4}[\\s-:.]\\d{6}";									//****-******	[11]
		regexpStr[21] = "\\d{1}[\\s-:.]\\d{4}[\\s-:.]\\d{4}";					//*-****-****	[11]
		regexpStr[22] = "\\d{3}[\\s-:.]\\d{3}[\\s-:.]\\d{3}";					//***-***-***	[11]
		regexpStr[23] = "\\d{11}";												//***********	[11]
		regexpStr[24] = "\\d{6}[\\s-:.]\\d{4}";									//******-****	[11]
		regexpStr[25] = "\\d{10}";												//**********	[10]
		regexpStr[26] = "\\d{4}[\\s-:.]\\d{4}";									//****-****	[9]
		regexpStr[27] = "\\d{9}";												//*********	[9]
		regexpStr[28] = "\\d{3}[\\s-:.]\\d{4}";									//***-****	[8]
		regexpStr[29] = "\\d{8}";												//********	[8]
		regexpStr[30] = "\\d{7}";												//*******	[7]
		regexpStr[31] = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";			//ip

		regexpReplaceStr = new String[32];
		regexpReplaceStr[0]  = "******************";
		regexpReplaceStr[1]  = "(***)-****-****";
		regexpReplaceStr[2]  = "(**** **)-*****";
		regexpReplaceStr[3]  = "***************";
		regexpReplaceStr[4]  = "*-***-***-****";
		regexpReplaceStr[5]  = "(***)-***-****";
		regexpReplaceStr[6]  = "(*****)-******";	
		regexpReplaceStr[7]  = "(**** **)-****";	
		regexpReplaceStr[8]  = "(**)-****-****";	
		regexpReplaceStr[9]  = "******-*******";	
		regexpReplaceStr[10] = "***-****-****";	
		regexpReplaceStr[11] = "(*****)-*****";	
		regexpReplaceStr[12] = "*** **** ****";	
		regexpReplaceStr[13] = "*************";	
		regexpReplaceStr[14] = "***-***-****";	
		regexpReplaceStr[15] = "**-****-****";	
		regexpReplaceStr[16] = "*****-******";	
		regexpReplaceStr[17] = "****-***-***";	
		regexpReplaceStr[18] = "************";	
		regexpReplaceStr[19] = "**-***-****";	
		regexpReplaceStr[20] = "****-******";	
		regexpReplaceStr[21] = "*-****-****";	
		regexpReplaceStr[22] = "***-***-***";	
		regexpReplaceStr[23] = "***********";	
		regexpReplaceStr[24] = "******-****";	
		regexpReplaceStr[25] = "**********";	
		regexpReplaceStr[26] = "****-****";	
		regexpReplaceStr[27] = "*********";	
		regexpReplaceStr[28] = "***-****";	
		regexpReplaceStr[29] = "********";	
		regexpReplaceStr[30] = "*******";
		regexpReplaceStr[31] = "***.***.***.***";
	}
	
	public String replaceNumberInfo(String text) {
		// 전화번호, 주민번호 등등...
		for(int i = 0; i < regexpStr.length; i++){
			Pattern regex = Pattern.compile(regexpStr[i], Pattern.MULTILINE);
			Matcher matcher = regex.matcher(text);
			while(matcher.find()){
				text = replace(text, matcher.group(), regexpReplaceStr[i]);
			}
		}
	
		return text;
	}
	
	public String replaceEmailInfo(String text) {
		Pattern regex = Pattern.compile("([\\.0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+).([0-9a-zA-Z_-]+)", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		while(matcher.find()){
			int posAt = matcher.group().indexOf("@");
			int posDot = matcher.group().indexOf(".", posAt);
			
			String tmpID = "";
			String tmpDomain1 = "";
			String tmpDomain2 = "";
			
			for(int i=0 ; i < posAt ; i++){
				tmpID += "*";
			}
			
			for(int i=posAt+1 ; i < posDot ; i++){
				tmpDomain1 += "*";
			}
			
			for(int i=posDot+1 ; i < matcher.group().length() ; i++){
				tmpDomain2 += "*";
			}
			
			text = replace(text, matcher.group(), tmpID+"@"+tmpDomain1+"."+tmpDomain2);
		}
		
		return text;
	}
	
	public String replaceTwitterID(String text) {
		Pattern regex = Pattern.compile("@[\\d\\w_-]+\\s+", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			text = replace(text, matcher.group(), "@"+replaceMD5(matcher.group()));
		}
		
		return text;
	}
	
	public String removeTwitterID(String text) {
		//Pattern regex = Pattern.compile("@[\\d\\w_-]+[\\s+|,|:]", Pattern.MULTILINE);
		Pattern regex = Pattern.compile("(?<=@)((\\w+))", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			String target = "@"+matcher.group()+" ";
			String target2 = "@"+matcher.group()+": ";
			text = text.replace(target, "");
			text = text.replace(target2, "");
			
		}
		
		return text;
	}
	
	public String removeUrl(String text) {
    	String pattern = "(http|https|ftp)://[^\\s^\\.]+(\\.[^\\s^\\.^\"^\']+)*";
		Pattern regex = Pattern.compile(pattern, Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			text = replace(text, matcher.group(), "");
		}
		
		return text;
    }
	
	
	
	public String removeHashTag(String text) {
		Pattern regex = Pattern.compile("#[\\d\\wㄱ-힣_-]+[\\s+|,]", Pattern.MULTILINE);
		Matcher matcher = regex.matcher(text);
		int chk = text.length();
		while(matcher.find()){
			if(text.length() > (chk * 2)){
				break;
			}
			text = replace(text, matcher.group(), " ");
		}
		
		return text;
	}

	/**
	 * replacePInfo
	 * 전화번호, 아이피 주소등의 개인정보를 *처리한다.
	 * 이메일주소를 *처리한다
	 * @param text
	 * @return 개인정보 처리된 text
	 */
	public String replacePInfo(String text) {
		text = replaceNumberInfo(text);
		text = replaceEmailInfo(text);

		return text;
	}
	
	/**
	 * replacePInfoForTwitter
	 * 전화번호, 아이피 주소등의 개인정보를 *처리한다.
	 * 이메일주소를 *처리한다
	 * 트위터 아이디를 암화화(MD5) 한다.
	 * 
	 * @param text
	 * @return 개인정보 처리된 text
	 */
	public String replacePInfoForTwitter(String text) {
		text = replaceNumberInfo(text);
		text = replaceEmailInfo(text);
		text = replaceTwitterID(text);

		return text;
	}
	
	private String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }
    
	private String replace(String text, String searchString, String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }
    
	private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

	private String replaceMD5(String str){
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}
}
