/**
========================================================
주 시 스 템 : RSN
서브 시스템 : 문자열 관련 함수 모음
프로그램 ID : StringUtil.class
프로그램 명 : StringUtil
프로그램개요 : 문자열 관련 유틸 메서드
작 성 자 : 윤석준
작 성 일 : 2006. 4. 12
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 박도형
 *
 */
public class StringUtil {
    public static int getDistance(String s1, String s2) { 
        int longStrLen = s1.length() + 1;
        int shortStrLen = s2.length() + 1;
        
        // 긴 단어 만큼 크기가 나올 것이므로, 가장 긴단어 에 맞춰 Cost를 계산
        int[] cost = new int[longStrLen];
        int[] newcost = new int[longStrLen];
        
        // 초기 비용을 가장 긴 배열에 맞춰서 초기화 시킨다.
        for (int i = 0; i < longStrLen; i++) {
            cost[i] = i;
        }
        
        // 짧은 배열을 한바퀴 돈다.
        for (int j = 1; j < shortStrLen; j++) {
            // 초기 Cost는 1, 2, 3, 4...
            newcost[0] = j;
            
            // 긴 배열을 한바퀴 돈다.
            for (int i = 1; i < longStrLen; i++) {
                // 원소가 같으면 0, 아니면 1
            	int match = 0;
            	if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
            		match = 1;
            	}
            	
                // 대체, 삽입, 삭제의 비용을 계산한다.
                int replace = cost[i - 1] + match;
                int insert = cost[i] + 1;
                int delete = newcost[i - 1] + 1;
                
                // 가장 작은 값을 비용에 넣는다.
                newcost[i] = Math.min(Math.min(insert, delete), replace);
            }
            
            // 기존 코스트 & 새 코스트 스위칭
            int[] temp = cost;
            cost = newcost;
            newcost = temp;
        }
        // 가장 마지막값 리턴
        return cost[longStrLen - 1];
    }
    
    public static int StrCompare3(String sentance1, String sentance2) {
    	/*sentance1 = "맥주/집에서/학교/가게/편의점/아침/저녁/점심/새벽/오후/언니/친구/오빠/엄마/아이/사진/쉴때/영화/일어나서/";// 데이/";
    	sentance2 = "집에서/산에서/학교/방에서/바다/아침/낮에/점심/저녁/식후/자기/친구/할머니/어머니/아이/치료/쉴때/사진/이야기/자기전/";*/
    	
    	String[] sentance1_array = sentance1.split("/");
    	String[] sentance2_array = sentance2.split("/");
    	
    	// System.out.println("sentance1 >>> " + sentance1 );
    	// System.out.println("sentance2 >>> " + sentance2 );
    	
    	// System.out.println("sentance1_array.length >>> " + sentance1_array.length );
    	// System.out.println("sentance2_array.length >>> " + sentance2_array.length ); 
    	
    	/*for (int i=0;i<sentance1_array.length;i++) {
    		System.out.println("sentance1_array >>> " + sentance1_array[i] );
    	}
    	
    	for (int i=0;i<sentance2_array.length;i++) {
    		System.out.println("sentance2_array >>> " + sentance2_array[i] );
    	}*/
    	
    	int loop_size = sentance1_array.length;
    	
    	if ( loop_size > sentance2_array.length) {
    		loop_size = sentance2_array.length;
    	}
    	
    	int similar = 0;
    	for (int i=0;i< loop_size ;i++) {
    		if ( sentance1_array[i].equals(sentance2_array[i])) {
    			similar++;
    		}
    	}
    	return (int)Math.round( ((double)similar/sentance1_array.length) * 100 );
    }
    
    public static int StrCompare2(String sentance1, String sentance2) {
        double euqalCnt = 0d;
        for (int  i = 0; i < sentance1.length(); i++) {
        	if ( sentance2.length() >= (i+1)) {
        		if (sentance1.substring(i, i + 1).equals(sentance2.substring(i, i + 1))) {
    	        	euqalCnt += 1;
    	        }
        	}
        }
//        System.out.println ("유사도 : " + euqalCnt / sentance1.length() * 100.0 + "%");
        return (int) ( euqalCnt / sentance1.length() * 100.0 );
    }
    
	// 	문자열의 유사도 비교
	public static int StrCompare4(String s1, String s2) {
		int repercent = 0;
		
		try {
			// 공백 제거
			s1 = s1.replaceAll(" ","");
			s2 = s2.replaceAll(" ","");
			
			// 소문자로 변환
			s1 = s1.toLowerCase();
			s2 = s2.toLowerCase();
			
			int lcs = 0;
			int ms = 0;
			int i = 0;
			int j = 0;
			
			// 바이트로 읽어들임
// 			byte[] b1 = s1.getBytes();
// 			byte[] b2 = s2.getBytes();
			String [] b1 = s1.split("/");
			String [] b2 = s2.split("/");
			
			// 바이트의 크기가져오기
			int m = b1.length;
			int n = b2.length;
			
			// [128][128]의 배열 생성
			int [][] LCS_Length_Table = new int[m+1][n+1];
			
			// 배열 초기화
			for (i=1; i <  m; i++) LCS_Length_Table[i][0]=0;
			for (j=0; j < n; j++) LCS_Length_Table[0][j]=0;
			
			// 루프를 돌며 바이트를 비교
			for (i=1; i <= m; i++) {
				// System.out.println(s1 + "\n"+s2);
				for (j=1; j <= n; j++) {
					System.out.println("i=> " + i + ", j=> " + j + ", LCS_Length_Table[i][j]=> " + LCS_Length_Table[i][j] + ", (b1[i-1]=> " + (b1[i-1]) + ", (b1[i-1]=> " + (b2[j-1]));
					
					// System.out.println(b1[i-1] + " : "+b2[j-1]);
					if ((b1[i-1]).equals( b2[j-1] )) {
						System.out.println(b1[i-1] + "1 : " + LCS_Length_Table[i-1][j-1] + 1);
						// System.out.println("LCS_Length_Table[i][j] : "+LCS_Length_Table[i][j] + " >>> " + (LCS_Length_Table[i-1][j-1] + 1));
						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j-1] + 1;
					} else if (LCS_Length_Table[i-1][j] >= LCS_Length_Table[i][j-1]) {
						System.out.println(b1[i-1] + "2 : " + LCS_Length_Table[i-1][j]);
						// System.out.println("else if - "+ LCS_Length_Table[i-1][j] +" >= " + LCS_Length_Table[i][j-1] + " | LCS_Length_Table[i][j] : "+LCS_Length_Table[i][j] + " >>> " + LCS_Length_Table[i-1][j]);
						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j];
					} else {
						System.out.println(b1[i-1] + "3 : " + LCS_Length_Table[i][j-1]);
						// System.out.println("else - LCS_Length_Table[i][j] : "+LCS_Length_Table[i][j] + " >>> " + LCS_Length_Table[i][j-1]);
						LCS_Length_Table[i][j] = LCS_Length_Table[i][j-1];
					}
				}
			}
			
			// percent를 리턴
			ms = (m + n) / 2;
			lcs = LCS_Length_Table[m][n];
			repercent = ((lcs * 100) / ms); 
			
			System.out.println("ms=> " + ms + ", lcs=> " + lcs + ", repercent==> " + repercent);
		} catch (ArithmeticException ex) {
			
		}
		return repercent;
	}
    
// 	문자열의 유사도 비교
	public static int StrCompare5(String s1, String s2) {
		System.out.println("s1=> " + s1 + ", s2=> " + s2);
		int repercent = 0;
		
		try {
			// 공백 제거
			s1 = s1.replaceAll(" ","");
			s2 = s2.replaceAll(" ","");
			
			// 소문자로 변환
			s1 = s1.toLowerCase();
			s2 = s2.toLowerCase();
			
			int lcs = 0;
			int ms = 0;
			int i = 0;
			int j = 0;
			
			// 바이트로 읽어들임
// 			byte[] b1 = s1.getBytes();
// 			byte[] b2 = s2.getBytes();
			String [] b1 = s1.split("/");
			String [] b2 = s2.split("/");
			
			// 바이트의 크기가져오기
			int m = b1.length;
			int n = b2.length;
			
			// [128][128]의 배열 생성
			int [][] LCS_Length_Table = new int[m+1][n+1];
			
			// 배열 초기화
			for (i=1; i <  m; i++) LCS_Length_Table[i][0]=0;
			for (j=0; j < n; j++) LCS_Length_Table[0][j]=0;
			
			int summary = 0;
			final int MAX_SCORE = 5;
			
			// 루프를 돌며 바이트를 비교
			for (i=1; i <= m; i++) {
				int tmpIdx = 1;
				// System.out.println(s1 + "\n"+s2);
				for (j=1; j <= n; j++) {
					String oriStr = (b1[i-1]);
					String compareStr = (b2[j-1]);
					
					if (oriStr.equals(compareStr)) {
//						if (Math.abs(i - j) == 0) {
//							int calcRank = MAX_SCORE - Math.abs(i - j);
//						} else {
//							int calcRank = MAX_SCORE - Math.abs(i - j);
//						}
						
						int calcRank = MAX_SCORE - Math.abs(i - j);
						summary += calcRank;
						
						System.out.println("i idx=> " + i + ", j idx=> " + j + ", tmpIdx=> " + tmpIdx + ", oriStr=> " + oriStr + ", compareStr=> " + compareStr + ", calcRank=> " + calcRank + ", s1=> " + s1 + ", s2=> " + s2);
					}
				}
				tmpIdx++;
			}
			System.out.println("summary=> " + summary);
			
			// percent를 리턴
			ms = (m + n) / 2;
			lcs = LCS_Length_Table[m][n];
//			repercent = ((summary * 100) / ms); 
			repercent = summary;
			
			System.out.println("ms=> " + ms + ", lcs=> " + lcs + ", repercent==> " + repercent);
		} catch (ArithmeticException ex) {
			
		}
		return repercent;
	}
    
	// 	문자열의 유사도 비교
	public static int StrCompare(String s1, String s2) {
		int repercent = 0;
		
		try {
			// 공백 제거
			s1 = s1.replaceAll(" ","");
			s2 = s2.replaceAll(" ","");

			// 소문자로 변환
			s1 = s1.toLowerCase();
			s2 = s2.toLowerCase();

			int lcs = 0;
			int ms = 0;
			int i = 0;
			int j = 0;

			// 바이트로 읽어들임
			byte[] b1 = s1.getBytes();
			byte[] b2 = s2.getBytes();

			// 바이트의 크기가져오기
			int m = b1.length;
			int n = b2.length;

			// [128][128]의 배열 생성
			int [][] LCS_Length_Table = new int[m+1][n+1];

			// 배열 초기화
			for (i=1; i <  m; i++) LCS_Length_Table[i][0]=0;
			for (j=0; j < n; j++) LCS_Length_Table[0][j]=0;

			// 루프를 돌며 바이트를 비교
			for (i=1; i <= m; i++) {
				for (j=1; j <= n; j++) {
					if ((b1[i-1]) == (b2[j-1])) {
						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j-1] + 1;
					} else if (LCS_Length_Table[i-1][j] >= LCS_Length_Table[i][j-1]) {
						LCS_Length_Table[i][j] = LCS_Length_Table[i-1][j];
					} else {
						LCS_Length_Table[i][j] = LCS_Length_Table[i][j-1];
					}
				}
			}
			// percent를 리턴
			ms = (m + n) / 2;
			lcs = LCS_Length_Table[m][n];
			repercent = ((lcs*100)/ms);
		} catch (ArithmeticException ex) {
			
		}
		return repercent;
	}
    
    /**
     * 특정 날짜를 특정 Format의 String으로 리턴한다
     * 단, 포맷을 yyyy-mm-dd로 하면 date 도 20060116
     * 으로 실제 파싱될 값의 길이가 같아야 한다
     * @param dFormat
     * @param date
     * @return
     * @throws Exception
     */
    public String getDateFormat(String dFormat, String date) throws Exception {
        int fPos = 0; // 첫번째 구분자의 위치
        int sPos = 0; // 두번째 구분자의 위치
        String fTag = ""; // 천번째 구분자
        String sTag = ""; // 두번째 구분자
        String rtnVal = "";
        
        // 첫번째 구분자의 위치와 구분자를 찻는다
        for (int i = 0; i < dFormat.length(); i++) {
            if ( Character.getType(dFormat.charAt(i)) != 1 && Character.getType(dFormat.charAt(i)) != 2) {
                fPos = i;
                fTag = dFormat.substring(i,i+1);
                break;
            }
        }
        
        // 두번째 구분자의 위치와 구분자를 찻는다
        for (int i = fPos+1; i < dFormat.length(); i++) {
            if ( Character.getType(dFormat.charAt(i)) != 1 && Character.getType(dFormat.charAt(i)) != 2) {
                sPos = i;
                sTag = dFormat.substring(i,i+1);
                break;
            }
        }
        
        // 입력받은 Date를 Format에 맞게 변환한다
        if ( sPos != 0) {
            rtnVal = date.substring(0,fPos) + fTag + date.substring(fPos,sPos-1) + sTag + date.substring(sPos-1);
        } else if ( fPos != 0) {
            rtnVal = date.substring(0,fPos) + fTag + date.substring(fPos);
        }
        return rtnVal;
    }

    /**
     * 해당 String에서 format을 적용하여 리턴
     * @param psValue
     * @return
     */
    public String digitFormat(String psValue) {
        String str = new DecimalFormat("###,###,###").format( Long.parseLong(psValue));
        return str;
    }
    
    /**
     * 해당 String에서 format을 적용하여 리턴
     * @param psValue
     * @return
     */
    public String digitFormatK(String psValue) {
    	long psValue_long = Long.parseLong(psValue);
        String str = new DecimalFormat("###,###,###").format( psValue_long );
        return str;
    }
    
    public String digitFormat(String psValue ,String psFormat) {
        String str = new DecimalFormat(psFormat).format( Long.parseLong(psValue));
        return str;
    }
    
    public String digitFormatDouble(String psValue ,String psFormat) {
        String str = new DecimalFormat(psFormat).format( Double.parseDouble(psValue));
        return str;
    }

    /**
     * 해당 long에서 format을 적용하여 리턴
     * @param plValue
     * @return
     */
    public String digitFormat(long plValue) {
        String str = new DecimalFormat("###,###,###").format( plValue );
        return str;
    }
    
    public String digitFormat(long plValue ,String psFormat) {
        String str = new DecimalFormat(psFormat).format(plValue);
        return str;
    }

    /**
     * 해당 int 에서 format을 적용하여 리턴
     * @param piValue
     * @return
     */
    public String digitFormat(int piValue) {
        String str = new DecimalFormat("###,###,###").format( piValue );
        return str;
    }
    
    public String digitFormat(int piValue ,String psFormat) {
        String str = new DecimalFormat(psFormat).format(piValue);
        return str;
    }
    
    public String digitFormatByUnit(int num, int limit) {
		String resultNum = "";
		String formatNum = "";
		if (num > limit) {
			if (num > 1000000000) {  // 10억이 넘었을 경우, M을 붙이기
				int[] tmp = new int[2];
				tmp[0] = 1000;
				tmp[1] = 1000000;
				
				String[] tmp2 = new String[2];
				tmp2[0] = "K";
				tmp2[1] = "M";
				
				for (int i = 0; i < tmp.length; i++) {
					if (num > tmp[i]) {
						resultNum = (num / tmp[i]) + tmp2[i];
					}
				}
				String km = "";
				if (resultNum.indexOf("K") > -1) {
					resultNum = resultNum.replaceAll("K", "");
					km = "K";
				}
				if (resultNum.indexOf("M") > -1) {
					resultNum = resultNum.replaceAll("M", "");
					km = "M";
				}
				DecimalFormat df = new DecimalFormat("###,###,###");
				formatNum = df.format(Integer.parseInt(resultNum));			
				formatNum += km;
			} else { // 100만이 넘었을 경우, K을 붙이기
				int[] tmp = new int[1];
				tmp[0] = 1000;
				
				String[] tmp2 = new String[1];
				tmp2[0] = "K";
				
				for (int i = 0; i < tmp.length; i++) {
					if (num > tmp[i]) {
						resultNum = (num / tmp[i]) + tmp2[i];
					}
				}
				String km = "";
				if (resultNum.indexOf("K") > -1) {
					resultNum = resultNum.replaceAll("K", "");
					km = "K";
				}
				DecimalFormat df = new DecimalFormat("###,###,###");
				formatNum = df.format(Integer.parseInt(resultNum));			
				formatNum += km;
			}
		} else {
			DecimalFormat df = new DecimalFormat("###,###,###");
			formatNum = df.format(num);			
		}
		return formatNum;
	}

    /**
     * 
     * @param num 입력 값
     * @param limit 제한 값
     * @param unit 단위 값
     * @param literal 뒤에 붙을 기호
     * @return
     */
    public String digitFormatByUnit_v2(int num, int limit, int unit, String literal) {
		String resultNum = "";
		String formatNum = "";
		
		if (num > limit) {
			int[] tmp = new int[1];
			tmp[0] = unit;
			
			String[] tmp2 = new String[1];
			tmp2[0] = literal;
			
			for (int i = 0; i < tmp.length; i++) {
				if (num > tmp[i]) {
					resultNum = (num / tmp[i]) + tmp2[i];
				}
			}
			String km = "";
			if (resultNum.indexOf(literal) > -1) {
				resultNum = resultNum.replaceAll(literal, "");
				km = literal;
			}
			DecimalFormat df = new DecimalFormat("###,###,###");
			formatNum = df.format(Integer.parseInt(resultNum));			
			formatNum += km;
		} else {
			DecimalFormat df = new DecimalFormat("###,###,###");
			formatNum = df.format(num);			
		}
		return formatNum;
	}
    
    /**
     * 해당 String에서 해당 char의 갯수를 리턴한다.
     * @param value
     * @param aChar
     * @return
     */
    public int charCount(String value, char aChar) {
        int rtnVal = 0;

        for (int i = 0; i < value.length(); i++) {
            if ( value.charAt(i) == aChar) {
                rtnVal++;
            }
        }
        return rtnVal;
    }

    /**
     * 타이틀 길이를 정리하는 함수
     * @param title
     * @param location
     * @return
     */
    public static String subStrTitle(String title, int location) {
        if (title.length() > location) {
            return title.substring(0, location) + "…";
        } else {
            return title;
        }
    }

    /**
     * String값을 Int로 변환한다
     * key의 값은 numeric으로 convert가능해야 한다
     * @param key
     * @return
     */
    public int getInt(String key) {
        int rtnVal = Integer.parseInt(key);
        return rtnVal;
    }

    /**
     * String값을 Double로 변환한다
     * @param key
     * @return
     */
    public double getDouble(String key) {
        double rtnVal = Double.parseDouble(key);
        return rtnVal;
    }

    /**
     * String값을 float로 변환한다
     * @param key
     * @return
     */
    public float getFloat(String key) {
        float rtnVal = Float.parseFloat(key);
        return rtnVal;
    }

    /**
     * String값을 long로 변환한다
     * @param key
     * @return
     */
    public long getLong(String key) {
        long rtnVal = Long.parseLong(key);
        return rtnVal;
    }

    /**
     * 스트링이 숫자와 알파벳으로만 이루어져
     * @param value
     * @return
     */
    public boolean isAlphaNumeric(String value) {
        boolean rtnVal = true;
        for (int i=0; i<value.length(); i++) {
            if ( Character.getType(value.charAt(i)) != 1 && Character.getType(value.charAt(i)) != 2 && Character.getType(value.charAt(i)) != 9) {
                rtnVal = false;
                break;
            }
        }
        return rtnVal;
    }

    /**
     * 주어진 스트링이 null일 경우 다른 스트링으로 대체
     * @param value
     * @param replace
     * @return
     */
    public String nvl(String value, String replace) {
        String rtnVal = (value == null || value.equals("")) ? replace : value;
        return rtnVal;
    }

    /**
     * 주어진 스트링이 null일 경우 다른 스트링으로 대체
     * @param value
     * @return
     */
    public String nvl(String value) {
        String rtnVal = nvl(value,"");
        return rtnVal;
    }

    /**
     * 전체 스트링 value에서 oldWord를 newWord로 변환하여 리턴한다
     * @param oldWord
     * @param newWord
     * @param value
     * @return
     */
    public String replaceWord(String oldWord, String newWord, String value) {
        int sPostion = 0;
        int ePostion = 0;
        StringBuffer result = new StringBuffer();

        while ((ePostion = value.indexOf(oldWord, sPostion)) >= 0) {
            result.append(value.substring(sPostion, ePostion));
            result.append(newWord);
            sPostion = ePostion + oldWord.length();
        }
        String rtnVal = result.append(value.substring(sPostion)).toString();

        return rtnVal;
    }

    /**
     * 해당 스트링에 값이 없을경우 원하는 형태로 변화
     * @param targetStr
     * @param changeChr
     * @param cnt
     * @return
     */
    public String nullToChange(String targetStr, String changeChr, int cnt) {
        if ( targetStr == null || targetStr.equals("")) {
            targetStr = "";
            for (int i = 0; i < cnt; i++) {
                targetStr += changeChr;
            }
        }
        String rtnVal = targetStr;
        
        return rtnVal;
    }

    /**
     * 문자열의 앞 또는 뒤로 psFillStr를 전체 길이가  piSize 될때까지  채워서    리턴한다.          변환하여 리턴한다
     * @param psStr
     * @param psFillStr
     * @param piSize
     * @param pbLR
     * @return
     */
    public String fillString(String psStr, String psFillStr, int piSize , boolean pbLR ) {
        if (piSize == 0 ) {
            return psStr;
        }
        if (psFillStr.equals("") ) {
            psFillStr = " ";
        }
        for (int i = psStr.length() + 1 ; i <= piSize ; i ++ ) {
            if ( pbLR == true ) {
                // 좌측 기준 우측 채움.Fill
                psStr = psStr + psFillStr;
            } else {
                // 우측 기준 좌측 채움,Fill
                psStr = psFillStr + psStr ;
            }
        }
        return psStr;
    }

   /****************************************************************************
	   주요기능 : Left Fill : 원본문자열 좌측에 원하는 문자열을 채웁니다.
	   테 이 블 :
	   입력패럼 :
	   출력패럼 :
	   비    고 : fillString()을 기준합니다.
   ****************************************************************************/
	public String LFill(String psSrc, String psFillStr, int piSize) {
		return fillString(psSrc, psFillStr, piSize, false);
	}
   /****************************************************************************
	   주요기능 : Right Fill : 원본문자열 우측에 원하는 문자열을 채웁니다.
	   테 이 블 :
	   입력패럼 :
	   출력패럼 :
	   비    고 : fillString()을 기준합니다.
   ****************************************************************************/
	public String RFill(String psSrc, String psFillStr, int piSize) {
		return fillString(psSrc, psFillStr, piSize, true);
	}

   /***********************************************************
        주요기능        문자열을 (시작점, 길이)로 주면은 바이트크기로 짤라준다
        입력 파라미터   para    : 입력 문자열
                           start : 시작점 (0부터 계산)
                           len  : 짜를 길이
        출력 파라미터   retval: 재단된 값
     ***********************************************************/
	public String cutByte(String para, int start, int len) {
		String retval = "";
		int end = start + len;
		try {
			byte[] b = para.getBytes();
			if (b.length <= start) return "";// 시작위치가 문자열 길이보다 클경우을 위해서 추가합니다.
			// if (b.length < len) end = start + b.length;// 
			if (b.length < end) end = b.length;// 추가

			byte[] ret = new byte[len];

			for (int i = start, j = 0; i < end; i++, j++) {
				ret[j] = b[i];
			}
			retval = new String(ret);
			// retval = retval.trim();
		} catch (ArrayIndexOutOfBoundsException e) {
			if (para == null) para = "null value";
			System.out.println(".java cutByte()\nlength=[" + para.getBytes().length + "]\nstart=[" + start + "]\nlen=[" + len + "]\nend=[" + end + "]\npara=[" + para.replace(' ', '_') + "]");
			throw e;
		}
		return retval;
	}

    /***********************************************************
	        주요기능        문자열을 (문자열, 원하는바이트길이)로 주면은 바이트 크기로 리턴
	        입력 파라미터   para    : 입력 문자열
	                           wantlen  : 원하는길이
	        출력 파라미터   para
     ***********************************************************/
	public String addByte(String para, int wantlen) {
		if (para == null) para = "";
		para = para.trim();
		String temp = "";
		byte b[] = para.getBytes();
		int addlen = wantlen - b.length;

		for (int i = 0; i < addlen; i++) {
			temp += " ";
		}
		para = para + temp;

		return para;
	}

    /***********************************************************
	        주요기능        문자열을 바이트 단위로 더붙힘
	        입력 파라미터   para    : 입력 문자열
	                           wantlen  : 원하는길이
	                     pattern : 채울문자
	        출력 파라미터   para
     ***********************************************************/
	public String addByte(String para, int wantlen, String pattern) {
		if (para == null) para = "";
		para = para.trim();
		String temp = "";
		byte b[] = para.getBytes();
		int addlen = wantlen - b.length;

		for (int i = 0; i < addlen; i++) {
			temp += pattern;
		}
		para = para + temp;

		return para;
	}

    /***********************************************************
	        주요기능        문자열을 바이트 단위로 더붙힘
	        입력 파라미터   para    : 입력 문자열
	                           wantlen  : 원하는길이
	                     pattern : 채울문자
	                     pos : 왼쪽으로 붙일지 오른쪽으로 붙힐지 결정
	        출력 파라미터   para
     ***********************************************************/
	public String addByte(String para, int wantlen, String pattern, String pos) {
		if (para == null) para = "";
		para = para.trim();
		String temp = "";
		byte b[] = para.getBytes();
		int addlen = wantlen - b.length;

		for (int i = 0; i < addlen; i++) {
			temp += pattern;
		}

		if (pos.toUpperCase().equals("RIGHT")) {
			para = para + temp;
		} else {
			para = temp + para;
		}
		return para;
	}

    /***********************************************************
	        주요기능        문자열 사이의 원하는 문자를 짜름
	        입력 파라미터   arg : 입력 문자열
	                           patt     : 짜를 문자열
	        출력 파라미터   rtn
     ***********************************************************/
	public String removeStr(String arg, String patt) {
		if (arg == null) return "";

		int len = patt.length();

		for (;;) {
			int i = arg.indexOf(patt);
			if (i < 0) break;
			arg = arg.substring(0, i) + arg.substring(i + len);
		}
		return arg;
	}

    /***********************************************************
	        주요기능        문자열 사이에서 숫자만 추출
	        입력 파라미터   arg : 입력 문자열
	        출력 파라미터   rtn
     ***********************************************************/
	public String getOnlyNum(String arg) {
		String bigyo = "0123456789";
		String retval = "";
		if (arg == null || arg.trim().equals(""))
			return "";
		for (int i = 0; i < arg.length(); i++) {
			if (bigyo.indexOf("" + arg.charAt(i)) >= 0) {
				retval += arg.charAt(i);
			}
		}
		return retval;
	}

   /***********************************************************
	        주요기능            입력받은 문자열의 길이를 체크하여 데이타가 있는경우만
	                       substring 을 수행하고 없을경우 "" 을 입력한다.
	        입력 파라미터   pRecvBuff   :   문자열
	                            pEndDate        :  종료일
	                            pFormat     :   포맷
	        출력 파라미터   rtnVal      :   날짜 차이 String
	        비고
     ***********************************************************/
	public String parseData(String pRecvBuff, int pStart, int pEnd) {
		String rtnVal = "";

		byte[] bRecvBuff = pRecvBuff.getBytes();
		if (bRecvBuff.length >= (pStart + pEnd)) {
			rtnVal = cutByte(pRecvBuff, pStart, pEnd);
		} else {
			rtnVal = "";
		}
		return rtnVal;
	}

   /***********************************************************
   기   능  설  명  : 문자열의 좌우측 공백을 제거합니다.
   입   력  변  수  : String psSrc :문자열
   출   력  변  수  : 좌우측 문자열이 제거된 결과
   비           고  : com_app.js에 있는것을 옮겨왔습니다.
   예           제  :
   ************************************************************/
    // 왼쪽 트림
	public String LTrim(String psSrc) throws Exception {
		int iSrcLen = psSrc.length();
		int iLen = iSrcLen;
		int iPos = 0;
		try {
			while ((iPos < iLen) && (psSrc.charAt(iPos) <= ' ')) {
				iPos++;
			}
			return ((iPos > 0) || (iLen < iSrcLen)) ? psSrc.substring(iPos, iLen) : psSrc;
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	// 오른쪽 트림
	public String RTrim(String psSrc) throws Exception {
		int iSrcLen = psSrc.length();
		int iLen = iSrcLen;
		int iPos = 0;

		try {
			while ((iPos < iLen) && (psSrc.charAt(iLen - 1) <= ' ')) {
				iLen--;
			}
			return ((iPos > 0) || (iLen < iSrcLen)) ? psSrc.substring(iPos, iLen) : psSrc;
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

   // 왼쪽 오른쪽 트림
	public String Trim(String psSrc) throws Exception {
		try {
			return LTrim(RTrim(psSrc));
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

   /****************************************************************************
	   주요기능 : Left Fill Byte : 원본문자열 좌측에 원하는 문자열을 Byte단위로 채웁니다.
	   테 이 블 :
	   입력패럼 :
	   출력패럼 :
	   비    고 : addByte()을 기준합니다.
              addByte()와 채울 문자열, 크기 의 순서가 바뀌었습니다.
   ****************************************************************************/
	public String LFillB(String psSrc, String psFillStr, int piSize) {
		return addByte(psSrc, piSize, psFillStr, "LEFT");
	}
   /****************************************************************************
	   주요기능 : Right Fill Byte: 원본문자열 우측에 원하는 문자열을 Byte단위로 채웁니다.
	   테 이 블 :
	   입력패럼 :
	   출력패럼 :
	   비    고 : addByte()을 기준합니다.
	              addByte()와 채울 문자열, 크기 의 순서가 바뀌었습니다.
   ****************************************************************************/
	public String RFillB(String psSrc, String psFillStr, int piSize) {
		return addByte(psSrc, piSize, psFillStr, "RIGHT");
	}

    /***********************************************************
	        주요기능        문자열 사이에서 바이트 단위로 리플레이스
	        입력 파라미터   targetval   : 목적문자열
	        입력 파라미터   startindex  : 시작점 0부터(바이트로계산)
	        입력 파라미터   bytelen  : 길이(byte단위)
	        입력 파라미터   withval  : 바꿀문자
     ***********************************************************/
	public String byteReplace(String targetval, int startindex, int bytelen, String withval) {
		String retval = cutByte(targetval, 0, startindex) + withval + cutByte(targetval, startindex + bytelen, (targetval.getBytes()).length - (startindex + bytelen));
		return retval;
	}

    /***********************************************************
	        주요기능        입력문자열의 양옆에 SOSI를 붙임 0x0E  0x0F
	        입력 파라미터   arg : 목적문자열
     ***********************************************************/
	public String addSoSi(String arg) {
		String retval = "";
		try {
			byte argb[] = arg.getBytes();
			byte temp[] = new byte[argb.length + 2];
			System.arraycopy(argb, 0, temp, 1, argb.length);
			// 양옆에 SOSI붙이기
			temp[0] = 0x0E;
			temp[temp.length - 1] = 0x0F;
			// byte[]을 java string으로 변환
			retval = new String(temp);
		} catch (Exception e) {
		}
		return retval;
	}

    /***********************************************************
	        주요기능        입력문자열의 양옆에 SOSI를 삭제 0x0E  0x0F
	        입력 파라미터   arg : 목적문자열
     ***********************************************************/
	public String delSoSi(String arg) {
		String retval = "";
		for (int i = 0; i < arg.length(); i++) {
			if (((char) arg.charAt(i) != (char) (0x0E)) && ((char) arg.charAt(i) != (char) (0x0F))) {
				retval += arg.charAt(i);
			}
		}
		return retval;
	}

    /***********************************************************
	        주요기능        전자를 삭제
	        입력 파라미터   val : 목적문자열
     ***********************************************************/
	public String removeJunJa(String val) {
		String retval = "";
		for (int i = 0; i < val.length(); i++) {
			int code = (int) val.charAt(i);
			if (code != 12288) {
				retval += (char) code;
			}
		}
		return retval;
	}

   /***************************************************************************
	   기 능 설 명 : 한영 혼합 문자열의 좌측에서부터 원하는 Bytes 만큼 반환합니다.
	   입 력 변 수 : String    psSrc       : 원본 문자열
	                 int       piByteLen   : 잘라낼 길이(Byte단위)
	                 boolean   pbFixedLen  : 잘라낸 결과를 piByteLen에 맞출것인지 여부
	                 char      pcFillChr   : 결과의 길이를 piByteLen에 맞출때 채울 문자
	
	   출 력 변 수 : 좌우측 문자열이 제거된 결과
	   주       의 : 마지막 글자가 한글일때 한글이 깨지지 않는 마지막 까지
	                 반환합니다. 그래서 반환하는 문자열의 실제 크기가
	                 원하는 Byte크기보다 작을 수 있습니다.
	   비       고 :
	   예       제 : substrMix(""abcde우리나라123", 10);        return [abcde우리 ]
	                 substrMix(""abcde우리나라123", 10, '-');   return [abcde우리-]
	                 substrMix(""abcde우리나라123", 10, false); return [abcde우리]
   ***************************************************************************/
	public static String substrMix(String psSrc, int piByteLen, boolean pbFixedLen, char pcFillChr) {
		// if (psSrc == null || piByteLen < 4) return psSrc;
		if (psSrc == null) return psSrc;
		// 채울문자가 2Byte문자라면 ' '(WhiteSpace)로 대체합니다.
		if (pcFillChr >= 256) pcFillChr = ' ';

		int iWordLen = psSrc.length();
		int iByteCnt = 0, iWordIdx = 0;// , iLostByte=0;

		while (iWordIdx < iWordLen && iByteCnt < piByteLen) {
			if (psSrc.charAt(iWordIdx++) < 256) { // 1바이트 문자라면...
				iByteCnt++; // 길이 1 증가
			} else { // 2바이트 문자라면...
				iByteCnt += 2; // 길이 2 증가
			}
		}// end of while

		if ((iWordIdx < iWordLen) && piByteLen < iByteCnt) {
			psSrc = psSrc.substring(0, iWordIdx - 1);
		} else if (iWordIdx < iWordLen) {
			psSrc = psSrc.substring(0, iWordIdx);
		}

		if (pbFixedLen) {
			for (int i = psSrc.getBytes().length; i < piByteLen; i++) {
				psSrc = psSrc + pcFillChr;
			}
		}
		return psSrc;
	}

	public static String substrMix(String psSrc, int piByteLen, char pcFillChr) {
		return substrMix(psSrc, piByteLen, true, pcFillChr);
	}

	public static String substrMix(String psSrc, int piByteLen,
			boolean pbFixedLen) {
		return substrMix(psSrc, piByteLen, pbFixedLen, ' ');
	}

	public static String substrMix(String psSrc, int piByteLen) {
		return substrMix(psSrc, piByteLen, true);
	}
   /***************************************************************************
	   기 능 설 명 : 한영 혼합 문자열의 특정 위치부터 원하는 Byte크기 만큼 반환합니다.
	   입 력 변 수 : String    psSrc          : 원본 문자열
	                 int       piWordStartPos : 시작위치(문자 단위, Byte단위가 아님)
	                 int       piByteLen      : 잘라낼 길이(Byte단위)
	                 boolean   pbFixedLen     : 잘라낸 결과를 piByteLen에 맞출것인지 여부
	                 char      pcFillChr      : 결과의 길이를 piByteLen에 맞출때 채울 문자
	
	   출 력 변 수 : 좌우측 문자열이 제거된 결과
	   주       의 : 마지막 글자가 한글일때 한글이 깨지지 않는 마지막 까지
	                 반환합니다. 그래서 반환하는 문자열의 실제 크기가
	                 원하는 Byte크기보다 작을 수 있습니다.
	   비       고 :
	   예       제 : substrMix(""abcde우리나라123", 4, 8);         return [e우리나 ]
	                 substrMix(""abcde우리나라123", 4, 8, '-');    return [e우리나-]
	                 substrMix(""abcde우리나라123", 4, 8, false);  return [e우리나]
   ***************************************************************************/
	public static String substrMix(String psSrc, int piWordStartPos, int piByteLen, boolean pbFixedLen, char pcFillChr) {
		int iSrcWLen = psSrc.length();
		int iEndPos = 0;
		String sTemp = "";
		if (iSrcWLen < piWordStartPos) {
			// return psSrc;
			return "";
		} else {
			if (iSrcWLen > (piWordStartPos + piByteLen)) sTemp = psSrc.substring(piWordStartPos, piWordStartPos + piByteLen);
			else sTemp = psSrc.substring(piWordStartPos, iSrcWLen);
		}
		return substrMix(sTemp, piByteLen, pbFixedLen, pcFillChr);
	}

	public static String substrMix(String psSrc, int piWordStartPos, int piByteLen, char pcFillChr) {
		return substrMix(psSrc, piWordStartPos, piByteLen, true, pcFillChr);
	}

	public static String substrMix(String psSrc, int piWordStartPos, int piByteLen, boolean pbFixedLen) {
		return substrMix(psSrc, piWordStartPos, piByteLen, pbFixedLen, ' ');
	}

	public static String substrMix(String psSrc, int piWordStartPos, int piByteLen) {
		return substrMix(psSrc, piWordStartPos, piByteLen, true);
	}

   /***************************************************************************
	   기 능 설 명 : 문자열의 Byte크기를 반환합니다.
	   입 력 변 수 : String    psSrc          : 원본 문자열
	
	   출 력 변 수 : 좌우측 문자열이 제거된 결과
	   주       의 : 마지막 글자가 한글일때 한글이 깨지지 않는 마지막 까지
	                 반환합니다. 그래서 반환하는 문자열의 실제 크기가
	                 원하는 Byte크기보다 작을 수 있습니다.
	   비       고 :
	   예       제 :
   ***************************************************************************/
   private int lengthB(String psSrc) {
      int iByteCnt = 0;
      for (int j = 0; j < psSrc.length(); j++) {
         if (psSrc.charAt(j++) < 256) { // 1바이트 문자라면...
            iByteCnt++;     // 길이 1 증가
         }
         else {   // 2바이트 문자라면...
            iByteCnt += 2;  // 길이 2 증가
         }

         /*
         char c = psSrc.charAt(j);
         if ( c  <  0xac00 || 0xd7a3 < c) {// 44032, 55203
            iByteCnt++;
         } else
            iByteCnt+=2;  // 한글이다..
         */
      }
      return iByteCnt;
   }
    /***********************************************************
        주요기능        문자열을 (문자열, 원하는바이트길이)로 주면은 바이트 크기로 리턴
        입력 파라미터   para    : 입력 문자열
                           wantlen  : 원하는길이
        출력 파라미터   para
      작성자 :
     ***********************************************************/
    public String addByteCut(String para, int wantlen) {
      if (para == null) para = "";
      para = para.trim();
        String temp = "";
        byte b[] = para.getBytes();
        int addlen = wantlen - b.length;

      if (addlen >= 0) {
         for (int i=0; i<addlen; i++) {
            temp += " ";
         }
           para = para + temp;
      } else {
         para = cutByte(para, 0, wantlen);
      }

        return para;
    }
    /***********************************************************
	        주요기능        문자열을 바이트 단위로 더붙힘
	        입력 파라미터   para    : 입력 문자열
	                           wantlen  : 원하는길이
	                     pattern : 채울문자
	        출력 파라미터   para
	      작성자 :
     ***********************************************************/
	public String addByteCut(String para, int wantlen, String pattern) {
		if (para == null) para = "";
		para = para.trim();
		String temp = "";
		byte b[] = para.getBytes();
		int addlen = wantlen - b.length;

		if (addlen >= 0) {
			for (int i = 0; i < addlen; i++) {
				temp += pattern;
			}
			para = para + temp;
		} else {
			para = cutByte(para, 0, wantlen);
		}
		para = para + temp;

		return para;
	}

    /***********************************************************
        주요기능        문자열을 바이트 단위로 더붙힘
        입력 파라미터   para    : 입력 문자열
                           wantlen  : 원하는길이
                     pattern : 채울문자
                     pos : 왼쪽으로 붙일지 오른쪽으로 붙힐지 결정
        출력 파라미터   para
      작성자 :
     ***********************************************************/
	public String addByteCut(String para, int wantlen, String pattern, String pos) {
		if (para == null) para = "";
		para = para.trim();
		String temp = "";
		byte b[] = para.getBytes();
		int addlen = wantlen - b.length;

		if (addlen >= 0) {
			for (int i = 0; i < addlen; i++) {
				temp += pattern;
			}
			if (pos.toUpperCase().equals("RIGHT")) {
				para = para + temp;
			} else {
				para = temp + para;
			}
		} else {
			para = cutByte(para, 0, wantlen);
		}
		return para;
	}

   // ************************************************************
   // *  특정문자열에서 구분스트링을 기준으로 양쪽모두를 split한다.
   // *  기존의 자바스크립트의 split 기능과 동일하다.
   // ************************************************************
	public String[] split(String str, String gubun) {
		int arrcnt = 0;
		int index = -99;
		for (;;) {
			if (index == -99) {
				index = str.indexOf(gubun, 0);
			} else {
				index = str.indexOf(gubun, index + gubun.length());
			}
			if (index == -1) break;
			arrcnt++;
		}
		String[] retarr = new String[arrcnt + 1];
		index = 0;
		int i = 0;
		for (;;) {
			index = str.indexOf(gubun);
			if (index < 0) {
				retarr[i] = "" + str;
				break;
			} else {
				retarr[i++] = "" + str.substring(0, index);
				str = str.substring(index + gubun.length(), str.length());
				// System.out.println("str=>"+index+"***"+str.length()+"***["+str+"]");
			}
		}
		return retarr;
	}

   /***************************************************************************
	   기 능 설 명 : 문자열의 Byte단위 길이를 구합니다.
	   입 력 변 수 : String    psSrc       : 원본 문자열
	
	   출 력 변 수 : Byte단위 길이
	   주       의 :
	   비       고 :
	   예       제 :
   ***************************************************************************/
	public int getByteLen(String psSrc) {
		if (psSrc == null) return 0;

		int iWordLen = psSrc.length();
		int iByteLen = 0;

		// while (iWordIdx < iWordLen) {
		for (int i = 0; i < iWordLen; i++) {
			if (psSrc.charAt(i) < 256) { // 1바이트 문자라면...
				iByteLen++; // 길이 1 증가
			} else { // 2바이트 문자라면...
				iByteLen += 2; // 길이 2 증가
			}
		}// end of while
		return iByteLen;
	}

   /**-----------------------------------------------------------------------------
   기 능 설 명 : 문자열내에 한글이 석여있는지 확인합니다.
   입 력 변 수 : psValue = 검사할 문자열
   출 력 변 수 : true/false
   비       고 :
   예       제 :
   public boolean isContainKor(String psValue) throws Exception {
      boolean  bRtn = false;
      try {
         for (int i=0; i<psValue.length(); i++) {
            int c=psValue.charAt(i);
            if ( 0xac00 <= c && c <= 0xd7a3) {
               bRtn = true;
               break;
            }
         }
      }
      finally {
      }
      return bRtn;
   }

   /**-----------------------------------------------------------------------------
	   기 능 설 명 : 문자열이 모두 한글인지 확인합니다.
	   입 력 변 수 : psValue = 검사할 문자열
	                 pbExceptSpace = 공백을 제외해서 검사할것인지 여부(boolean)
	                                   공백 제외시 true, Default=false
	   출 력 변 수 : true/false
	   비       고 :
	   예       제 :                                                                    
   */
	public boolean isAllKor(String psValue) throws Exception {
		return isAllKor(psValue, true);
	}

	public boolean isAllKor(String psValue, boolean pbExceptSpace) throws Exception {
		boolean bRtn = true;
		try {
			if (pbExceptSpace) {
				// 공백은 제외하고 검사:공백이 있어도 true
				for (int i = 0; i < psValue.length(); i++) {
					int c = psValue.charAt(i);
					if (0x20 != c && (c < 0xac00 || 0xd7a3 < c)) {// 가 부터 R 까지
						bRtn = false;
						break;
					}
				}
			} else {
				// 공백도 포함해서 검사: 공백이 있으면 false
				for (int i = 0; i < psValue.length(); i++) {// 가 부터 R 까지
					int c = psValue.charAt(i);
					if (c < 0xac00 || 0xd7a3 < c) {
						bRtn = false;
						break;
					}
				}
			}
		} finally {
			// ..
		}
		return bRtn;
	}

   /**-----------------------------------------------------------------------------
	   기 능 설 명 : 문자열내에 공백이 석여있는지 확인합니다.
	   입 력 변 수 : psValue = 검사할 문자열
	   출 력 변 수 : true/false
	   비       고 :
	   예       제 :
   */
	public boolean isContainSpace(String psValue) throws Exception {
		boolean bRtn = false;
		try {
			if (psValue.indexOf(" ") > -1) bRtn = true;
		} finally {
			// ..
		}
		return bRtn;
	}

   /**-----------------------------------------------------------------------------
		* 기 능 설 명 : 문자열이 원하는 길이 이상이면 자르고 뒤에 '...'을 붙임
		* 입 력 변 수 : psValue = 검사할 문자열
		* 출 력 변 수 : true/false
		* 비       고 :
		* 예       제 :
   */
	public String cutString(String psValue, int piCutSize, String psAddstr) throws Exception {
		String rtnValue = "";
		try {
			if (psValue.length() > piCutSize) {
				rtnValue = psValue.substring(0, piCutSize) + psAddstr;
			} else {
				rtnValue = psValue;
			}
		} finally {
			// ..
		}
		return rtnValue;
	}

    /**
     * 본문을 piLen*2 길이로 자르기하고 키워드 강조
     * @param psBody : 본문
     * @param psKeyword : 키워드 ( 키워드1 키워드2 ... )
     * @param piLen : 자르는 문자열 길이의 절반
     * @param tColor : 강조될 문자의 색상
     * @return : 키워드가 강조된 주요내용
     * 2011.02.11 limSeungChul 소문자 강조 처리 및 구문 키워드("keyword") 처리 수정
     */
    public String cutKey( String psBody, String psKeyword, int piLen, String tColor ) {
    	String result = null;
		StringUtil su = new StringUtil();
		int idxPoint = -1; 

		try {
			if (psBody==null || psBody.length()==0) {
				return "";
			}
			
			psBody = su.ChangeString( psBody.toLowerCase() );
			if (!su.nvl(psKeyword,"").equals("")) {
				String[] arrBFKey = su.getUniqeArray( psKeyword.split(" ") );			// 강조키워드 배열
				arrBFKey = searchGumunKey( arrBFKey );
				String[] arrAFKey = new String[arrBFKey.length];	// 강조키워드 html 배열
				
				for (int i=0 ; i<arrBFKey.length ; i++ ) {
			    	// System.out.println("Keyword : "+arrBFKey[i]);
					arrBFKey[i] = arrBFKey[i].toLowerCase();
					arrAFKey[i] = "<font color='"+tColor+"'><b>"+arrBFKey[i]+"</b></font>";
					
					if ( idxPoint == -1) {
						idxPoint = psBody.indexOf(arrBFKey[i]);
					}
				}
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
		
				if ( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if ( currPoint >= piLen) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if ( psBody.length() >= (startCut + piLen*2)) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length();
				}
				if (startCut<0) {
					startCut = 0;
				}
				if (endCut>psBody.length()-1) {
					endCut = psBody.length();
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
				if ( idxPoint >= 0) {
					if (arrBFKey.length>0) {
						for (int i=0 ; i<arrBFKey.length ; i++  )
						{
							result = result.replaceAll( "(?is)"+arrBFKey[i], arrAFKey[i]);
						}
					}
				}
			} else {
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
				
				if ( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if ( currPoint >= piLen) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if ( psBody.length() >= (startCut + piLen*2)) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length()-1;
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
			}
    	} catch( Exception ex) {
    		Log.writeExpt(ex);
    		System.out.println(psBody+"/psKeyword:"+psKeyword+"/piLen:"+piLen+"/tColor:"+tColor);
		}
		return result;
    }
    
    public String cutKey_issue( String psBody, String psKeyword, int piLen, String tColor ) {
    	String result = null;
		StringUtil su = new StringUtil();
		int idxPoint = -1; 
		
		try {
			if (psBody==null || psBody.length()==0) {
				return "";
			}
			// psBody = su.cleanTag(psBody.toLowerCase());
			
			psBody = su.ChangeString( psBody.toLowerCase() );
			
			if (!su.nvl(psKeyword,"").equals("")) {
				String[] arrBFKey = su.getUniqeArray( psKeyword.split(" ") );			// 강조키워드 배열
				arrBFKey = searchGumunKey( arrBFKey );
				String[] arrAFKey = new String[arrBFKey.length];	// 강조키워드 html 배열
			
				for (int i=0 ; i<arrBFKey.length ; i++ ) {
			    	// System.out.println("Keyword : "+arrBFKey[i]);
					arrBFKey[i] = arrBFKey[i].toLowerCase();
					arrAFKey[i] = "<font color='"+tColor+"'><b>"+arrBFKey[i]+"</b></font>";
					
					if ( idxPoint == -1) {
						idxPoint = psBody.indexOf(arrBFKey[i]);
					}
				}
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
				
				if ( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if ( currPoint >= piLen) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if ( psBody.length() >= (startCut + piLen*2)) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length();
				}
				if (startCut<0) {
					startCut = 0;
				}
				if (endCut>psBody.length()-1) {
					endCut = psBody.length();
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
				if ( idxPoint >= 0) {
					if (arrBFKey.length>0) {
						for (int i=0 ; i<arrBFKey.length ; i++  )
						{
							result = result.replaceAll( "(?is)"+arrBFKey[i], arrAFKey[i]);
						}
					}
				}
			} else {
				// piLen*2 길이만큼 본문내용 자르기
				int currPoint = 0;
				int startCut = 0;
				int endCut = 0;
				String startDot = "";
				String endDot = "";
				
				if ( idxPoint >= 0 ) currPoint = idxPoint;
				// 내용자르기의 시작포인트
				if ( currPoint >= piLen) {
					startDot = "...";
					startCut = currPoint - piLen;
				} else {
					startCut = 0;
				}
				
				// 내용자르기의 마지막포인트
				if ( psBody.length() >= (startCut + piLen*2)) {
					endDot = "...";
					endCut = currPoint + piLen;
				} else {
					endCut = psBody.length()-1;
				}
				result = startDot+psBody.substring(startCut, endCut)+endDot;
			}
    	} catch( Exception ex) {
    		Log.writeExpt(ex);
    		System.out.println(psBody+"/psKeyword:"+psKeyword+"/piLen:"+piLen+"/tColor:"+tColor);
		}
		return result;
    }
    
    /**
     * 배열의 문자열을 비교하여 중복된 값을 제거
     * @param arrSource
     * @return
     * 2008.09.10 by dhpark
     */
    public String[] getUniqeArray( String[] arrSource ) {
    	String strResult = "";
    	    	
    	boolean chkVal = false;
    	
    	try {
	    	for (int i=0 ; i<arrSource.length ; i++ ) {
	    		chkVal = false;
	    		for (int j=0; j<i ; j++ ) {
	    			if ( arrSource[i].equals(arrSource[j]) ) chkVal = true;
	    		}
	    		if ( !chkVal && !arrSource[i].equals("") ) strResult += arrSource[i]+"　";
	    	}
			if ( strResult.length() > 2 ) strResult = strResult.substring(0, (strResult.length()-1));
    	} catch( Exception ex) {
    		Log.writeExpt(ex);
    	}
    	return strResult.split("　");
    }
    
    /**
     * 구문 키워드를 찾아 키워드를 재배열한다.
     * @param key
     * @return
     */
    public static String[] searchGumunKey(String[] key) {
    	int indexCnt = 0;
		int firstWordIndex = 0;
		int endWordIndex = 0;
		int gumunCount = 0;
		
		String notGumnunKey = "";
		String gumnunKeyword = "";
		String[] tempKey = null;
		String[] lastKey = null;
		
		// 구문 인덱스 범위를 찾는다.
		for (int i =0 ; i<key.length ; i++) {
			notGumnunKey += notGumnunKey.equals("") ? key[i] : " "+ key[i];
			
			if (key[i].indexOf("\"")>-1) {
				if (indexCnt==0) firstWordIndex = i;					
				if (indexCnt>0) endWordIndex = i;	
				indexCnt++;
			}
		}
		
		if (endWordIndex>0) {
			gumunCount = endWordIndex -	firstWordIndex;	
			for (int i = firstWordIndex ; i<gumunCount+1 ; i++) {
				gumnunKeyword += gumnunKeyword.equals("") ? key[i] : " "+ key[i];	
			}
			
			// 구문 제외한 키워드
			notGumnunKey = notGumnunKey.replaceAll(gumnunKeyword,"");
			notGumnunKey = notGumnunKey.replaceAll("  ", " ");
			
						
			// 구문 제외한 키워드 배열
			if (notGumnunKey.equals(" "))tempKey = notGumnunKey.split(" ");			
			if (tempKey!=null) {
				lastKey = new String[tempKey.length+1];
				for (int i =0 ; i<tempKey.length ; i++) {					
					lastKey[i] = tempKey[i];
				}
				lastKey[tempKey.length] = gumnunKeyword;
			} else {
				lastKey = new String[1];
				lastKey[0] = gumnunKeyword;
			}
		} else {
			lastKey = key;
		}
		for (int i =0 ; i<lastKey.length ; i++) {
			lastKey[i] = lastKey[i].replaceAll("\"","");
		}
    	return lastKey;
    }

    /**
     * @param arrval
     * @param val
     * @return
     */
    public String dissplit(String[] arrval, String val) {
    	int i;
    	String result = "";
    	
    	for (i=0 ; i < arrval.length ; i++) {
    		if ( i != 0 ) result += val;
    		result += arrval[i];
    	}
    	return result;
    }
   
    public boolean inarray(String[] arrval, String val) {
    	int i;
    	if (arrval != null && arrval.length > 0) { 
	    	for (i=0 ; i < arrval.length ; i++) {
	    		if ( arrval[i].equals(val)) {
	    			return true; 
	    		}
	    	}
    	}
    	return false;
    }
    
    /**-----------------------------------------------------------------------------
	    기 능 설 명 : 날짜포맷 char(8) 또는 시간포맷char(6) "20071130" or "112030" 을 보내면 ex)2007/11/30, 2007-11-30 or 11:20:30  등 원하는 구분자를 붙여서 리턴  
	    입 력 변 수 : psValue, 구분자 division
	    출 력 변 수 : transForm 변환된 포맷
	    비       고 :
	    예       제 :
    */
    public String getDateTransFormmat(String psValue, String division) throws Exception {
    	String transForm=null;
    	
    	if (!psValue.equals(null) && !psValue.equals("") && psValue.length()==8) { // 날짜 포맷이면
			if (transForm == null) {
				transForm = psValue.substring(0, 4);
				transForm = transForm + division + psValue.substring(4, 6);
				transForm = transForm + division + psValue.substring(6, 8);
			}
    	} else if (!psValue.equals(null) && !psValue.equals("") && psValue.length()==6) { // 시간 포맷이면
			if (transForm == null) {
				transForm = psValue.substring(0, 2);
				transForm = transForm + division + psValue.substring(2, 4);
				transForm = transForm + division + psValue.substring(4, 6);
			}
    	}
    	return transForm;
    }// getDateTransFormmat End.
    
    /**-----------------------------------------------------------------------------
	    기 능 설 명 : 위의 메서드  getDateTransFormmat 에서 type을 md 로 입력하면 10/04 , 10-04 hm을 입력하면 11:20 4자리에 구분자를 끼워 리턴
	    입 력 변 수 : psValue, 구분자 division, 
	    출 력 변 수 : transForm 변환된 포맷
	    비       고 :
	    예       제 :
    */
    public String getDateTransFormmat(String psValue, String division, String type) throws Exception {
    	String transForm=null;
    	
    	if (!psValue.equals(null) && !psValue.equals("") && psValue.length()==8 && type.equals("md")) { // 날짜 포맷이면
    		if (transForm==null) {
	    		transForm = psValue.substring(4, 6);
	   			transForm = transForm+division+psValue.substring(6, 8);
    		}
    	} else if (!psValue.equals(null) && !psValue.equals("") && psValue.length()==6 && type.equals("hm")) { // 시간 포맷이면
			if (transForm == null) {
				transForm = psValue.substring(0, 2);
				transForm = transForm + division + psValue.substring(2, 4);
			}
    	}	
    	return transForm;
    }// getDateTransFormmat End.

    /**
     * 수집대상 URL을 이용하여 페이지를 수집하여 리턴
     * @param sUrl 수집대상 페이지의 URL
     * @return
     */
    public String requestPageByGet(String sUrl) {
    	StringBuffer html = new StringBuffer();
    	try {
    		String line = null;
    		URL aURL = new URL(sUrl);
    		BufferedReader br = new BufferedReader(new InputStreamReader(aURL.openStream()));
    		while ((line = br.readLine()) != null) {
    			html.append(line);
    		}
    	} catch (MalformedURLException e) {
    		Log.writeExpt("CRONLOG", "MalformedURLException : "+e );
    		// 프로세스 종료
    	    // System.exit(1);
    	} catch (IOException e) {
    	    Log.writeExpt("CRONLOG", "IOException :"+ e );
    	    // 프로세스 종료
    	    // System.exit(1);
    	}
    	return html.toString();
    }
    
    /**
     * 수집대상 URL 인코딩하여  호출 페이지를 리턴
     * @param sUrl 수집대상 페이지의 URL
     * @return
     */
    public String encodingRequestPageByGet(String sUrl,String encoding) {
    	StringBuffer html = new StringBuffer();
    	try {
    		String line = null;
    		URL aURL = new URL(sUrl);
    		BufferedReader br = new BufferedReader(new InputStreamReader(aURL.openStream(),encoding));
    		while ((line = br.readLine()) != null) {
    			html.append(line);
    		}
    	} catch (MalformedURLException e) {
    		Log.writeExpt("CRONLOG", "MalformedURLException : "+e );
    		// 프로세스 종료
    	    // System.exit(1);
    	} catch (IOException e) {
    	    Log.writeExpt("CRONLOG", "IOException :"+ e );
    	    // 프로세스 종료
    	    // System.exit(1);
    	}
    	return html.toString();
    }
    
    /**
     * 수집대상 URL 인코딩하여  호출 페이지를 리턴
     * @param sUrl 수집대상 페이지의 URL
     * @return
     */
    public String encodingRequestPageByPost(String sUrl,String Encoding) {
    	StringBuffer html = new StringBuffer();
    	
    	try {
    		String line = null;
    		URL aURL = new URL(sUrl);
    		HttpURLConnection urlCon = (HttpURLConnection)aURL.openConnection();
    		urlCon.setRequestMethod("POST");    		
    		urlCon.setDoInput(true);
    		urlCon.setDoOutput(true);
    		
    		BufferedReader br = null;
    		
    		if (!Encoding.equals("")) {
    			br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),Encoding));
    		} else {
    			br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
    		}
    		
    		while ((line = br.readLine()) != null) {
    			html.append(line);
    		}
    		
    		aURL = null;
    		urlCon = null;
    		br.close();
    	} catch (MalformedURLException e) {
    		Log.writeExpt("CRONLOG", "MalformedURLException : "+e );
    		// 프로세스 종료
    	    // System.exit(1);
    	} catch (IOException e) {
    	    Log.writeExpt("CRONLOG", "IOException :"+ e );
    	    // 프로세스 종료
    	    // System.exit(1);
    	}
    	return html.toString();
    }
    
    public String GetHtmlPost(String sUrl, String param) {
    	StringBuffer html = new StringBuffer();
    	
    	try {
    		String line = null;
    		URL aURL = new URL(sUrl);
    		HttpURLConnection urlCon = (HttpURLConnection)aURL.openConnection();
    		urlCon.setRequestMethod("POST");
    		
    		urlCon.setDoInput(true);
    		urlCon.setDoOutput(true);
    		OutputStream out = urlCon.getOutputStream();
    		out.write(param.getBytes());
    		out.flush();
    		
    		out.close();
    		BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
    		// BufferedReader br = new BufferedReader(new InputStreamReader(aURL.openStream()));
    		while ((line = br.readLine()) != null) {
    			html.append(line);
    		}
    		aURL = null;
    		urlCon = null;
    		br.close();
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    		Log.writeExpt("CRONLOG", "MalformedURLException : "+e );
    		// 프로세스 종료
    		// System.exit(1);
    	} catch (IOException e) {
    		e.printStackTrace();
    		Log.writeExpt("CRONLOG", "IOException :"+ e );
    		// 프로세스 종료
    		// System.exit(1);
    	}
    	return html.toString();
    }
    
    public static String convert(double doubleObj , String pattern ) throws Exception {
    	DecimalFormat df = new DecimalFormat(pattern) ;
    	return df.format(doubleObj).toString() ;
    }
    
    public String convertFloor(double doubleObj , int position ) throws Exception {  
      String temp =  ""  ;
        String tempTwoDigit =  ""  ;
        int inx = 0 ;    
        String patternAttachedZero = "" ;
        String point ="." ;
        
        if ( position < 0 )
        throw new Exception (" Position 을 0 이상으로 설정 하십시오 " );
        if ( position == 0 )  point="" ; // 소수점이 포함되어 나타나는것 방지 
  
        temp = convert( doubleObj ,  "#.0000000000000000000000000000000000"  ) ;  
        // BigDecimal 에서 표현하는 소수점 가장 끝자리까지 이므로 
        // 반올림되어 오류가 나는 소지를 최소화 한다.
        inx = temp.lastIndexOf( "." ) ;
        tempTwoDigit = temp.substring( 0, (( inx + 1 )  +  position ) ) ;
        
        for (int i =0 ; i < position ; i++) {
        	patternAttachedZero += "0" ;
        }
        return convert(Double.parseDouble( tempTwoDigit ), "#,##0" + point +  patternAttachedZero) ;
    }
    
    public String toHtmlString( String val) {
        String result = "";
        result = replaceWord("<", "&lt;", val);
        result = replaceWord(">", "&gt;", result);
        result = replaceWord("\"", "&#34;", result);
        result = replaceWord("'", "&#39;", result);
        return result;
    }
    
    public String ChangeString(String sVal) {
        String rVal="";
        sVal=sVal.replaceAll("\"", "&quot;");
        sVal=sVal.replaceAll("'", "&#39;");
        sVal=sVal.replaceAll("<", "&lt;");
        sVal=sVal.replaceAll(">","&gt;");
        rVal=sVal;
        return rVal;
    }
    
    public String dbString(String sVal) {
        String rVal = "";
        sVal = sVal.replaceAll("\"", "\\\\\"");
        sVal = sVal.replaceAll("'", "\\\\'");
        rVal = sVal;
        return rVal;
    }
    
    public String changeKeyColor(String psBody, String psKeyword, String tColor )
    {
    	String result = null;
    	
		StringUtil su = new StringUtil();
		
		int idxPoint = -1; 

		try{
			if(psBody==null || psBody.length()==0){
				return "";
			}
				
			psBody = su.ChangeString( psBody.toLowerCase() );

			result = psBody;
			
			if (!su.nvl(psKeyword,"").equals("")) {
				
				String[] arrBFKey = su.getUniqeArray( psKeyword.split(" ") );			// 강조키워드 배열
				arrBFKey = searchGumunKey( arrBFKey );
				String[] arrAFKey = new String[arrBFKey.length];	// 강조키워드 html 배열
			
				for( int i=0 ; i<arrBFKey.length ; i++ )
				{
			    	//System.out.println("Keyword : "+arrBFKey[i]);
					arrBFKey[i] = arrBFKey[i].toLowerCase();
					arrAFKey[i] = "<font color='"+tColor+"'><b>"+arrBFKey[i]+"</b></font>";
					
					if( idxPoint == -1 ) {
						idxPoint = psBody.indexOf(arrBFKey[i]);
					}
				}
				if( idxPoint >= 0 ) {
					if (arrBFKey.length>0) {
						for( int i=0 ; i<arrBFKey.length ; i++  )
						{
							result = result.replaceAll( "(?is)"+arrBFKey[i], arrAFKey[i]);
						}
					}
				}
			}
    	} catch( Exception ex ) {
    		Log.writeExpt(ex);
		}
		
		return result;
    }
     
    public static String cleanTag(String content) {
	   	 Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
	   	 Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL); 
	   	 Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>"); 
	   	 Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s>*"); 
	   	 Pattern ENTITY_REFS = Pattern.compile("&[^;]+;"); 
	   	 Pattern WHITESPACE = Pattern.compile("\\s\\s+"); 
	   	 
	   	 Matcher m;
	   	 
	   	 m = SCRIPTS.matcher(content);
	   	 content = m.replaceAll("");
	   	 m = STYLE.matcher(content);
	   	 content = m.replaceAll("");
	   	 m = TAGS.matcher(content);
	   	 content = m.replaceAll("");
	   	 m = ENTITY_REFS.matcher(content);
	   	 content = m.replaceAll("");
	   	 m = WHITESPACE.matcher(content);
	   	 content = m.replaceAll(" ");
	   	 
	   	 return content; 
	}
    
    /**
	 * <p>str을 URLIncoding 하여 반환한다</p>
	 * @param str code
	 * @return String 인코딩 결과
	 */
    public static final String changeEncode(String str, String code) {
		try {
			return  URLEncoder.encode(str, code);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
    
    // 배열을 한줄로 변경한다.
    public String Join(String[] str, String point) {
    	String result = "";
    	
    	if (str != null && str.length > 0) {
    		for (int i =0; i < str.length; i++) {
    			if (str[i] != null) {
    				if (result.equals("")) {
    					result = str[i];
    				} else {
    					result += point + str[i]; 
    				}
    			}
    		}
    	}
    	return result;
	}
    
    // 배열을 GET방식 파라미터 값으로 변경 
    public String paramGetType(String[] str, String name) {
    	String result = "";
    	if (str != null && str.length > 0) {
    		for (int i =0; i < str.length; i++) {
    			if (str[i] != null) {
    				if (result.equals("")) {
    					result = name + "=" +  str[i];
    				} else {
    					result += "&" + name + "=" + str[i]; 
    				}
    			}
    		}
    	}
    	return result;
	}
    
    public String shortenSentence(String str, int length) {
    	String result = "";
    	if (str.length() > length) {
    		result = str.substring(0, length) + "...";
    	} else {
    		result = str;
    	}
    	return result;
    }
    
    /**
     * @author 2jongmin
     * @param cnt
     * @param totalCnt
     * @return
     */
    public String calcPer(int cnt, int totalCnt) {
    	String result = "";
    	double calcVal = 0.0d;
    	calcVal = (double) (double) ((double) cnt / (double) totalCnt) * 100;
    	
    	if (!Double.isNaN(calcVal)) {
//    		String dispPattern = "0";
//    		DecimalFormat form = new DecimalFormat(dispPattern);
//    		result = form.format(Math.round(calcVal));
    		result = String.valueOf(Math.round(calcVal));
    	} else {
    		result = "0";
    	}
    	return result;
    }
    
    /**
     * 2016. 10. 06. LJM
     * 
     * @author 2jongmin
	 * @param channel
	 * @return
	 */
	public static String GetConvertChannel(String channel) {
		String returnStr = "";
		
		String[] arrStr = channel.replaceAll(" ", "").split(",");
		for (int i=0; i<arrStr.length; i++) {
			if ("".equals(returnStr)) {
				returnStr = arrStr[i];
			} else {
				returnStr += "%20" + arrStr[i];
			}
		}
		return returnStr;
	}

    /**
     * 2016. 10. 06. LJM
     * 
     * @author 2jongmin
	 * @param channel
	 * @return
	 */
	public static List ConvertCommaVal(String var, String reg) {
		List resultList = new ArrayList();
		
		String[] arrStr = var.split(reg);
		for (int i=0; i<arrStr.length; i++) {
			if (!"0".equals(arrStr[i].toString())) {
				resultList.add(arrStr[i]);
			}
		}
		return resultList;
	}
	
	/**
	 * 2016. 10. 06. LJM
	 * LUCY API 호출
	 * 
	 * @author 2jongmin
	 * @param sUrl
	 * @param param
	 * @return
	 */
	public static String GetLucyAPIPost(String sUrl, String param) {
		StringBuffer html = new StringBuffer();
		
		try {
			String line = null;
			URL aURL = new URL(sUrl);
			HttpURLConnection urlCon = (HttpURLConnection) aURL.openConnection();
			urlCon.setRequestMethod("POST");
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			
			OutputStream out = urlCon.getOutputStream();
			out.write(param.getBytes());
			out.flush();
			
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
			// BufferedReader br = new BufferedReader(new InputStreamReader(aURL.openStream()));
			while ((line = br.readLine()) != null) {
				html.append(line);
			}

			aURL = null;
			urlCon = null;
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
// 			Log.writeExpt("LUCY_API", "MalformedURLException : " + e);
		} catch (IOException e) {
			e.printStackTrace();
// 			Log.writeExpt("LUCY_API", "IOException :" + e);
		}
		return html.toString();
	}

	/**
	 * 긍정,부정,중립 형태로 받은 문자열에서 합계를 구하여 리턴한다.
	 * 
	 * @author 2jongmin
	 * @param cnt
	 * @param idx
	 * @return
	 */
	public String getStringCountToSummary(String cnt) {
		String[] arrCnt = cnt.split(",");
		int summary = 0;
		for (int i=0; i<arrCnt.length; i++) {
			summary += Integer.valueOf(arrCnt[i]);
		}
		return String.valueOf(summary);
	}
	
	/**
	 * 긍정,부정,중립 형태로 받은 문자열에서 입력 받은 파라메터 위치의 숫자(문자열)로 리턴한다.
	 * 
	 * @author 2jongmin
	 * @param cnt
	 * @param idx
	 * @return
	 */
	public String getCountToIndexString(String cnt, int idx) {
		String[] arrCnt = cnt.split(",");
		String resultVal = "";
		for (int i=0; i<arrCnt.length; i++) {
			if (idx == i) {
				resultVal = arrCnt[i];
				break;
			}
		}
		return resultVal;
	}
	
	/**
	 * 영문 채널 -> 한글 채널 변환
	 * 
	 * @author 2jongmin
	 * @param cnt
	 * @param idx
	 * @return
	 */
	public String getChannelName(String str) {
		String resultVal = "";
		if ("TW".equals(str.toUpperCase())) {
			resultVal = "트위터";
		} else if ("FA".equals(str.toUpperCase())) {
			resultVal = "페이스북";
		} else if ("BL".equals(str.toUpperCase())) {
			resultVal = "블로그";
		} else if ("DC".equals(str.toUpperCase())) {
			resultVal = " 커뮤니티";
		} else if ("CF".equals(str.toUpperCase())) {
			resultVal = "카페";
		} else if ("DN".equals(str.toUpperCase())) {
			resultVal = "언론";
		} else if ("PR".equals(str.toUpperCase())) {
			resultVal = "포탈댓글";
		}
		return resultVal;
	}
	
	/**
	 * AI_TEXT(str), 찾을 숫자(findStr)를 입력 받아 해당 되는 찾아 숫자를 String 형태로 반환한다.
	 * 
	 * @author 2jongmin
	 * @param str
	 * @param findStr
	 * @return
	 */
	public String findAITextToCount(String str, String findStr) {
		String resultStr = "";
		String[] arrStr = str.split(",");
		for (int i=0; i<arrStr.length; i++) {
			if (arrStr[i].startsWith(findStr)) {
				resultStr = arrStr[i].replaceAll(findStr + ":", "");
// 				System.out.println("findStr=> " + findStr + ", resultStr=> " + resultStr);
				break;
			}
		}
		return resultStr;
	}
	
	// 특수문자 제거 하기 add LJM. site_menu 용
	public String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, " ");
		return str;
	}
	
	/**
	 * Instagram GET HashTag List
	 * 
	 * @author 2jongmin
	 * @param str
	 * @return
	 */
	public List<String> getHashTagList(String str) {
		String regexpStr = "(#[^ #\"]+)";
		
		List<String> resultList = new ArrayList<String>();
		Pattern p = Pattern.compile(regexpStr); 
		Matcher m = p.matcher(str);
		while (m.find()) {
			resultList.add(m.group());
		}
		return resultList;
	}
	
	/**
	 * 
	 * @author 2jongmin
	 * @param inputStr
	 * @return
	 */
	public String convertFacebookURL(String inputStr) {
    	String returnStr = "http://www.facebook.com/";
    	String middleURL = "/posts/";
    	
    	String[] arrStr = inputStr.split("_");
    	
    	for (int i=0; i<arrStr.length; i++) {
    		if (i == 0) {
    			returnStr += arrStr[i].toString();
    		} else if (i == 1) {
    			returnStr += middleURL + arrStr[i].toString();
    		}
    	}
// 	    	System.out.println("returnStr=> " + returnStr);
		return returnStr;
    }
	
	 public String addComma(String data){
	    	int num = Integer.parseInt(data);
	    	return new java.text.DecimalFormat("#,###").format(num);
	 }
	
}