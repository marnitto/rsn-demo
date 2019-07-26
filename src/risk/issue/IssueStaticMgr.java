package risk.issue;
//MCD
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.StringUtil;
import risk.JfreeChart.ChartBean;
import risk.issue.IssueStaticBean;



public class IssueStaticMgr {
	
	 private DBconn dbconn   = null;
	 private Statement stmt  = null; 
	 private ResultSet rs    = null;
	 private StringBuffer sb = null;
	
	
// 세로축 가로축 바꾸기
	 public ArrayList shakeData(ArrayList arrSrc) {
			ArrayList arrRtn = new ArrayList();
			
			int i,j = 0;
			String tmp;
			TrendDataBean Tr1 = (TrendDataBean) arrSrc.get(0);
			
			for(j=-1; j<Tr1.getData().size(); j++){
				
				ArrayList arrTmp = new ArrayList();
				TrendDataBean trTmp = new TrendDataBean("", arrTmp);
				
				for(i=0; i<arrSrc.size(); i++){
					TrendDataBean Tr2 = (TrendDataBean) arrSrc.get(i);
					
					if (j<0){
						if (i==0){
							trTmp.setName(Tr2.getName());
						}
						else{
							trTmp.addData(Tr2.getName());
						}
					}
					else{
						arrTmp = Tr2.getData();
						tmp = arrTmp.get(j).toString();
						if (i==0){
							trTmp.setName( tmp );
						}
						else{
							trTmp.addData( Double.valueOf(tmp) );
						}					
					}
				}
				arrRtn.add(trTmp);
			}
			
			return arrRtn;
			
		}
// Sum을 추가 해준다.
		public ArrayList makeSum(ArrayList arrSrc) {
			
			ArrayList arrRtn = new ArrayList();
			ArrayList arrTotal = new ArrayList();
			
			int i,j = 0;
			String tmp;
			
			TrendDataBean Tr1 = (TrendDataBean) arrSrc.get(0);
				
			double[] tmpTotal = new double[Tr1.getData().size()];
			
			ArrayList arrTmp = new ArrayList();
			for(i=0; i<arrSrc.size(); i++){
				
				TrendDataBean Tr2 = (TrendDataBean) arrSrc.get(i);
				
				for(j=0; j<Tr1.getData().size(); j++){
					
					if (i==0){
						
					}
					else{
						arrTmp = Tr2.getData();
						tmp = arrTmp.get(j).toString();
						tmpTotal[j] += Double.valueOf(tmp).doubleValue() ;
					}
				}
				if (i==0) {
					arrRtn.add(Tr2);
				}
				
			}
			for(j=0; j<Tr1.getData().size(); j++){
				arrTotal.add(new Double (tmpTotal[j]) );
			}
			arrRtn.add( new TrendDataBean("Total",arrTotal ) );
			return arrRtn;
			
		}
// Percent로 변환		
		public ArrayList makeAvg(ArrayList arrSrc, String type) {
			
			ArrayList arrRtn = new ArrayList();
			
			int i,j = 0;
			String tmp;
			
			if(!type.equals("date")){
				int[] Tr1 = (int[]) arrSrc.get(0);
					
				double total = 0;
				
				for(i=0; i<arrSrc.size(); i++){
					double[] tmpTotal = new double[Tr1.length];
					
					int[] Tr2 = (int[]) arrSrc.get(i);
					for(j=0; j<Tr2.length; j++){
						tmp = Integer.toString(Tr2[j]);
						tmpTotal[j] = Double.valueOf(tmp).doubleValue();
					}
					total += tmpTotal[2];
				}
				if(total==0.0){
					total = 1.0;
				} 
				
				for(i=0; i<arrSrc.size(); i++){
					double[] tmpTotal2 = new double[Tr1.length];
					
	
					int[] Tr2 = (int[]) arrSrc.get(i);
					for(j=0; j<Tr2.length; j++){
						tmp = Integer.toString(Tr2[j]);
						tmpTotal2[j] = Double.valueOf(tmp).doubleValue();
					}
					tmpTotal2[2] = ((tmpTotal2[2]/total)*100);
					
					arrRtn.add( tmpTotal2 );
				}
			}else{
				double total = 0;
				//System.out.println("arrSrc.size() : "+arrSrc.size());
				for(i=0; i<arrSrc.size(); i++){
					ChartBean chBean =(ChartBean)arrSrc.get(i);
					
					//System.out.println("chBean.getMiVal() : "+chBean.getMiVal());
					
					total += Double.valueOf(Integer.toString(chBean.getMiVal())).doubleValue();
				}
				if(total==0.0){
					total = 1.0;
				}
				for(j=0; j<arrSrc.size(); j++){
					ChartBean chBean =(ChartBean)arrSrc.get(j);
					IssueStaticBean isbean = new IssueStaticBean();
					
					
					isbean.setMsxVal(chBean.getMsxVal());
					isbean.setMsyVal(chBean.getMsyVal());
					isbean.setMiVal((chBean.getMiVal()/total)*100);
					
					//System.out.println("temDouble[0]"+isbean.getMsxVal());
					//System.out.println("temDouble[1]"+isbean.getMsyVal());
					System.out.println("temDouble[2]"+isbean.getMiVal());
					
					arrRtn.add( isbean );
				}				
				System.out.println("total :"+total);
			}
			System.out.print("arrSrc.size() : " +arrSrc.size()+"\n");
			return arrRtn;
		}	
		
		public void showList(ArrayList arrSrc){
			int i;
			for(i=0; i<arrSrc.size(); i++){
				TrendDataBean Tr2 = (TrendDataBean) arrSrc.get(i);
				System.out.println(Tr2.getName()+" : "+Tr2.getData());
			}
		}
		
		public int checkList(ArrayList arrSrc){
			int i,j = 0;
			int cnt = 0;
			Integer tmpcnt = null;
			int tmpcnt2 = 0;
			for(i=1; i<arrSrc.size(); i++){
				TrendDataBean Tr2 = (TrendDataBean) arrSrc.get(i);
				ArrayList arrtmp = Tr2.getData();
				for(j=0; j<arrtmp.size(); j++){
					tmpcnt = (Integer)arrtmp.get(j);
					tmpcnt2 = tmpcnt.intValue();
					if( tmpcnt2 > 0 ) {
						cnt = cnt+tmpcnt2;
					}
					
				}
			}
			return cnt;
		}
		
		/**
		 * public float calCorrel(ArrayList arr1, ArrayList arr2)
		 * 
		 * 두개의 ArrayList를 받아서 두 배열간의 상관계수를 구한다.\n
		 * 반올림은 소숫점 2자리에서 시행 변경시
		 * result = (float)Math.round(result*100)/100; 부분 수 
		 * 
		 * @param 
		 *		ArrayList arr1
		 *		ArrayList arr2
		 * @return 
		 * 		float
		 */
		public float calCorrel(ArrayList arr1, ArrayList arr2) {
			float result = 0;
			String tmp1, tmp2;
			float t1, t2;
			
			for(int i=0; i<arr1.size(); i++) {
				
				System.out.println("000");
				
				tmp1 = arr1.get(i).toString();
				tmp2 = arr2.get(i).toString();
				
				System.out.println("111");
				
				t1 = Float.valueOf(tmp1).floatValue() - calAvg(arr1);
				t2 = Float.valueOf(tmp2).floatValue() - calAvg(arr2);
				
				System.out.println("222");
				
				result = result + ( t1 * t2 );
			}
			
			t1 = calStDev(arr1);
			t2 = calStDev(arr2);
			
			result = (result / arr1.size()) / ( t1 * t2 );
			
			System.out.println("calCorrel: "+result);
			result = (float)Math.round(result*100)/100;
			return result;
		}

		/**
		 * public float calAvg(ArrayList arr1)
		 * 
		 * 한개의 ArrayList를 받아서 평균을 계산한다.
		 * calSum function 사용
		 * 
		 * @param 
		 *		ArrayList arr1
		 *		
		 * @return 
		 * 		float
		 */
		public float calAvg(ArrayList arr1){
			float result = 0;
			result = calSum(arr1) / arr1.size();
			System.out.println("calAvg: "+result);
			return result;
		}
		
		/**
		 * public float calSum(ArrayList arr1)
		 * 
		 * 한개의 ArrayList를 받아서 리스트의 합계를 계산한다.
		 *  
		 * @param 
		 *		ArrayList arr1
		 *		
		 * @return 
		 * 		float
		 */	
		public float calSum(ArrayList arr1){
			float result = 0;
			String tmp;
			
			for(int i=0; i<arr1.size(); i++) {
				tmp = arr1.get(i).toString();
				result = result + Float.valueOf(tmp).floatValue();
			}
			System.out.println("calSum: "+result);
			return result;
		}
		
		/**
		 * public float calStDev(ArrayList arr1)
		 * 
		 * 한개의 ArrayList를 받아서 리스트의 표준편차를 계산한다.
		 * calAvg function 사
		 *  
		 * @param 
		 *		ArrayList arr1
		 *		
		 * @return 
		 * 		float
		 */	
		public float calStDev(ArrayList arr1){
			float result = 0;
			String tmp;
			float tmpD = 0;
			
			for(int i=0; i<arr1.size(); i++) {
				tmp = arr1.get(i).toString();
				tmpD = Float.valueOf(tmp).floatValue() - calAvg(arr1);
				result = result + (float)Math.pow( tmpD , 2);
			}
			result = (float)Math.sqrt(result / arr1.size());
			System.out.println("calStDev: "+result);
			
			return result;
		}
	 
	 
	 
	 
	 
	 
    /**
     * 검색 조건에 따른 단일 TYPE에 CODE별 카운트
     * @param sDate 검색 시작일 '2007-11-01'
     * @param eDate 검색 종료일 '2007-11-30'
     * @param arrCode 검색코드 배열 {{type, code}, {type, code}, ...}
     * @param codeType 단일 TYPE 값
     * @return
     */
    public ArrayList getUnitCodeStatic( String sDate, String eDate, ArrayList arrCode, String codeType )
    {
    	ArrayList result = null;
    	ArrayList arrTemp = null;
    	/*for(int i=0; i<arrCode.size(); i++){
    		System.out.println("arrcode : "+arrCode.get(i).toString());
    		
    	}*/
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sDate = sDate.replaceAll("-", "");
			eDate = eDate.replaceAll("-", "");

			if( arrCode.size() > 0 )
			{
				sb.append("SELECT B1.IC_CODE,  CASE WHEN B2.CT > 0 THEN B2.CT ELSE 0 END AS TT  \n");
				sb.append("FROM \n");
				sb.append("	(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType+" AND IC_CODE>0) B1 LEFT OUTER JOIN \n");
				sb.append("	( \n");
				sb.append("	SELECT A3.IC_CODE, COUNT(A1.ID_SEQ) AS CT \n");
				sb.append("	FROM ISSUE_DATA A1,  \n");
				sb.append("		 (  \n");
				sb.append("		SELECT ID_SEQ  \n");
				sb.append("		FROM ISSUE_DATA_CODE  \n");
				for( int i=0 ; i<arrCode.size(); i++ )
				{
					arrTemp = (ArrayList)arrCode.get(i);
					
					if( i == 0 )
						sb.append("		WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					else
						sb.append("		OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
				}
				sb.append("		GROUP BY ID_SEQ   \n");
				sb.append("		 ) A2, \n");
				sb.append("		 (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType+") A3 \n");
				sb.append("	WHERE A1.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+"' AND A1.ID_SEQ=A2.ID_SEQ AND A2.ID_SEQ=A3.ID_SEQ \n");
				sb.append(" 	GROUP BY A3.IC_CODE \n");
				sb.append("	) B2  \n");
				sb.append("	ON B1.IC_CODE=B2.IC_CODE \n");
				sb.append("ORDER BY B1.IC_CODE ASC \n");
			} else {
				sb.append("SELECT B1.IC_CODE,  CASE WHEN B2.CT > 0 THEN B2.CT ELSE 0 END AS TT  \n");
				sb.append("FROM \n");
				sb.append("	(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType+" AND IC_CODE>0) B1 LEFT OUTER JOIN \n");
				sb.append("	( \n");
				sb.append("	SELECT A3.IC_CODE, COUNT(A1.ID_SEQ) AS CT \n");
				sb.append("	FROM ISSUE_DATA A1,  \n");
				sb.append("		 (SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType+") A3 \n");
				sb.append("	WHERE A1.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' AND A1.ID_SEQ=A3.ID_SEQ \n");
				sb.append(" 	GROUP BY A3.IC_CODE \n");
				sb.append("	) B2  \n");
				sb.append("	ON B1.IC_CODE=B2.IC_CODE \n");
				sb.append("ORDER BY B1.IC_CODE ASC \n");
			}

			System.out.println("getUnitCodeStatic \n"+sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			result = new ArrayList();
			while( rs.next() )
			{
				result.add( rs.getString("TT") );
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }

    /**
     *  통계 리포트를 총 카운트를 구하는 메소드
     * @param sDate 검색 시작일 '2007-11-01'
     * @param eDate 검색 종료일 '2007-11-30'
     * @param arrCode 검색코드 배열 {{type, code}, {type, code}, ...}
     * @return
     */
    public int getTotalCount( String sDate, String eDate, ArrayList arrCode )
    {
    	int result = 0;
    	
    	ArrayList arrTemp = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
		
			if( arrCode.size() > 0 )
			{
				sb.append("SELECT COUNT(A1.ID_SEQ) AS TT  \n");
				sb.append("FROM ISSUE_DATA A1, \n");
				sb.append("	 ( \n");
				sb.append("	SELECT ID_SEQ \n");
				sb.append("	FROM ISSUE_DATA_CODE \n");
				for( int i=0 ; i<arrCode.size(); i++ )
				{
					arrTemp = (ArrayList)arrCode.get(i);
					
					if( i == 0 )
						sb.append("	WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					else
						sb.append("	OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
				}
				sb.append("	GROUP BY ID_SEQ  \n");
				sb.append("	 ) A2 \n");
				sb.append("WHERE A1.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' AND A1.ID_SEQ=A2.ID_SEQ \n");
			} else {
				sb.append("SELECT COUNT(ID_SEQ) AS TT FROM ISSUE_DATA WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' \n");
			}

			System.out.println("getTotalCount \n"+sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			if( rs.next() )
			{
				result =rs.getInt("TT");
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }

    
    /**
     * 검색 조건에 따른 두개의 TYPE에대한 CODE별 카운트
     * @param sDate 검색 시작일 '2007-11-01'
     * @param eDate 검색 종료일 '2007-11-30'
     * @param arrCode 검색코드 배열 {{type, code}, {type, code}, ...}
     * @param codeType1 첫번째 TYPE 값
     * @param codeType2 두번째 TYPE 값
     * @return
     * mcd
     */
    public ArrayList getDualCodeStatic( String sDate, String eDate, ArrayList arrCode, String codeType1, String codeType2 )
    {
    	ArrayList result = null;
    	ArrayList arrTemp = null;  
    	
    	int[] arrInt = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();

			if( arrCode.size() > 1 ) 
			{   
				sb.append("SELECT  A1_CODE,  \n");
				sb.append("		   A2_CODE,  \n");
				sb.append(" 	   SUM(CT) AS TT								  \n");
				sb.append("FROM \n");
				sb.append("	( \n");
				sb.append("	SELECT A1_CODE, A2_CODE, CASE WHEN ID_SEQ > 0 THEN 1 ELSE 0 END AS CT \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append("		SELECT A1.IC_CODE AS A1_CODE, A2.IC_CODE AS A2_CODE \n");
				sb.append("		FROM \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE>0) A1, \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE>0) A2 \n");
				sb.append("		) B1  LEFT OUTER JOIN \n");
				sb.append("		( \n");
				sb.append("		SELECT A4.IC_CODE AS A4_CODE, A5.IC_CODE AS A5_CODE, A3.ID_SEQ \n");
				sb.append("		FROM \n");
				sb.append("			( \n");
				sb.append("				SELECT ID_SEQ \n");
				sb.append("				FROM ISSUE_DATA \n");
				sb.append("				WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' \n");
				sb.append("			) A3, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType1+") A4, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType2+") A5, \n");
				sb.append("			( \n");
				sb.append("			SELECT ID_SEQ \n");
				sb.append("			FROM ISSUE_DATA_CODE \n");
				for( int i=0 ; i<arrCode.size(); i++ )
				{
					arrTemp = (ArrayList)arrCode.get(i);
					
					if( i == 0 )
						sb.append("			WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					else
						sb.append("			OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
				}
				sb.append("			GROUP BY ID_SEQ  \n");
				sb.append("			) A6 \n");
				sb.append("		WHERE A3.ID_SEQ=A4.ID_SEQ AND A3.ID_SEQ=A5.ID_SEQ AND A3.ID_SEQ=A6.ID_SEQ \n");
				sb.append("		) B2 \n");
				sb.append("		ON A1_CODE=A4_CODE AND A2_CODE=A5_CODE \n");
				sb.append("	) C1 \n");
				sb.append("GROUP BY A1_CODE, A2_CODE \n");
				sb.append("ORDER BY A1_CODE, A2_CODE \n");
			} else {
				sb.append("SELECT A1_CODE,  \n");
				sb.append("		  A2_CODE,  \n");
				sb.append(" 	   SUM(CT) AS TT								  \n");
				sb.append("FROM \n");
				sb.append("	( \n");
				sb.append("	SELECT A1_CODE, A2_CODE, CASE WHEN ID_SEQ > 0 THEN 1 ELSE 0 END AS CT \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append("		SELECT A1.IC_CODE AS A1_CODE, A2.IC_CODE AS A2_CODE \n");
				sb.append("		FROM \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE>0) A1, \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE>0) A2 \n");
				sb.append("		) B1  LEFT OUTER JOIN \n");
				sb.append("		( \n");
				sb.append("		SELECT A4.IC_CODE AS A4_CODE, A5.IC_CODE AS A5_CODE, A3.ID_SEQ \n");
				sb.append("		FROM \n");
				sb.append("			( \n");
				sb.append("				SELECT ID_SEQ \n");
				sb.append("				FROM ISSUE_DATA \n");
				sb.append("				WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' \n");
				sb.append("			) A3, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType1+") A4, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType2+") A5 \n");
				sb.append("		WHERE A3.ID_SEQ=A4.ID_SEQ AND A3.ID_SEQ=A5.ID_SEQ \n");
				sb.append("		) B2 \n");
				sb.append("		ON A1_CODE=A4_CODE AND A2_CODE=A5_CODE \n");
				sb.append("	) C1 \n");
				sb.append("GROUP BY A1_CODE, A2_CODE \n");
				sb.append("ORDER BY A1_CODE, A2_CODE \n");
			}

			System.out.println("getDualCodeStatic \n"+sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			result = new ArrayList();
			while( rs.next() )
			{
				arrInt = new int[3];
				
				arrInt[0] = rs.getInt("A1_CODE");
				arrInt[1] = rs.getInt("A2_CODE");
				arrInt[2] = rs.getInt("TT");
				
				result.add( arrInt );
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    
    // 코드 BeanArray형태로 반환
    public ArrayList getDualCodeStatic2( String sDate, String eDate, ArrayList arrCode, String codeType1, String codeType2 )
    {
    	ArrayList result = null;
    	ArrayList arrTemp = null;  
    	
    	int[] arrInt = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
		
			if( arrCode.size() > 1 )
			{
				sb.append("SELECT IC_NAME1,A1_CODE,IC_NAME2,A2_CODE,SUM(CT) AS TT	\n");
				sb.append("FROM                                            \n");
				sb.append("(                                               \n");
				sb.append("SELECT (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE = A1_CODE)AS IC_NAME1, A1_CODE,  \n");
				sb.append("		  (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE = A2_CODE)AS IC_NAME2, A2_CODE,  \n");
				sb.append(" 	   CT 							  \n");
				sb.append("FROM \n");
				sb.append("	( \n");
				sb.append("	SELECT A1_CODE, A2_CODE, CASE WHEN ID_SEQ > 0 THEN 1 ELSE 0 END AS CT \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append("		SELECT A1.IC_CODE AS A1_CODE, A2.IC_CODE AS A2_CODE \n");
				sb.append("		FROM \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE>0) A1, \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE>0) A2 \n");
				sb.append("		) B1  LEFT OUTER JOIN \n");
				sb.append("		( \n");
				sb.append("		SELECT A4.IC_CODE AS A4_CODE, A5.IC_CODE AS A5_CODE, A3.ID_SEQ \n");
				sb.append("		FROM \n");
				sb.append("			( \n");
				sb.append("				SELECT ID_SEQ \n");
				sb.append("				FROM ISSUE_DATA \n");
				sb.append("				WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' \n");
				sb.append("			) A3, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType1+") A4, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType2+") A5, \n");
				sb.append("			( \n");
				sb.append("			SELECT ID_SEQ \n");
				sb.append("			FROM ISSUE_DATA_CODE \n");
				for( int i=0 ; i<arrCode.size(); i++ )
				{
					arrTemp = (ArrayList)arrCode.get(i);
					
					if( i == 0 )
						sb.append("			WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					else
						sb.append("			OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
				}
				sb.append("			GROUP BY ID_SEQ  \n");
				sb.append("			) A6 \n");
				sb.append("		WHERE A3.ID_SEQ=A4.ID_SEQ AND A3.ID_SEQ=A5.ID_SEQ AND A3.ID_SEQ=A6.ID_SEQ \n");
				sb.append("		) B2 \n");
				sb.append("		ON A1_CODE=A4_CODE AND A2_CODE=A5_CODE \n");
				sb.append("	) C1 \n");
				sb.append(") \n");
				sb.append("GROUP BY IC_NAME1,IC_NAME2,A1_CODE, A2_CODE  \n");
				sb.append("ORDER BY A1_CODE, A2_CODE \n");
			} else {
				sb.append("SELECT IC_NAME1,A1_CODE,IC_NAME2,A2_CODE,SUM(CT) AS TT	\n");
				sb.append("FROM                                            \n");
				sb.append("(                                               \n");
				sb.append("SELECT (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE = A1_CODE)AS IC_NAME1, A1_CODE,  \n");
				sb.append("		  (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE = A2_CODE)AS IC_NAME2, A2_CODE,  \n");
				sb.append(" 	   CT 							  \n");
				sb.append("FROM \n");
				sb.append("	( \n");
				sb.append("	SELECT A1_CODE, A2_CODE, CASE WHEN ID_SEQ > 0 THEN 1 ELSE 0 END AS CT \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append("		SELECT A1.IC_CODE AS A1_CODE, A2.IC_CODE AS A2_CODE \n");
				sb.append("		FROM \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE>0) A1, \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE>0) A2 \n");
				sb.append("		) B1  LEFT OUTER JOIN \n");
				sb.append("		( \n");
				sb.append("		SELECT A4.IC_CODE AS A4_CODE, A5.IC_CODE AS A5_CODE, A3.ID_SEQ \n");
				sb.append("		FROM \n");
				sb.append("			( \n");
				sb.append("				SELECT ID_SEQ \n");
				sb.append("				FROM ISSUE_DATA \n");
				sb.append("				WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59' \n");
				sb.append("			) A3, \n");
				sb.append("			(SELECT /*+INDEX(ISSUE_DATA_CODE IDX_IDC_IC_TYPE)*/ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType1+") A4, \n");
				sb.append("			(SELECT /*+INDEX(ISSUE_DATA_CODE IDX_IDC_IC_TYPE)*/ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType2+") A5 \n");
				sb.append("		WHERE A3.ID_SEQ=A4.ID_SEQ AND A3.ID_SEQ=A5.ID_SEQ \n");
				sb.append("		) B2 \n");
				sb.append("		ON A1_CODE=A4_CODE AND A2_CODE=A5_CODE \n");
				sb.append("	) C1 \n");
				sb.append(") \n");
				sb.append("GROUP BY IC_NAME1,IC_NAME2,A1_CODE, A2_CODE  \n");
				sb.append("ORDER BY A1_CODE, A2_CODE \n");
			}

			System.out.println("getDualCodeStatic \n"+sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			result = new ArrayList();
			while( rs.next() )
			{
				//System.out.println(rs.getString("IC_NAME1")+rs.getString("IC_NAME2")+rs.getDouble("TT"));
				
				IssueStaticBean isb = new IssueStaticBean(
				rs.getString("IC_NAME1"),
				rs.getInt("A1_CODE"),
				rs.getString("IC_NAME2"),
				rs.getInt("A2_CODE"),
				rs.getDouble("TT")
				);
				
				result.add( isb );
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    // 코드 BeanArray형태로 반환
    public ArrayList getDualCodeStatic3( String sDate, String eDate, ArrayList arrCode, String codeType1, String codeType2, String codeType3 )
    {
    	ArrayList result = null;
    	ArrayList arrTemp = null;  
    	String[] temp=null;
    	if(codeType3.length()>0){
    		temp = codeType3.split(",");
    	}
    	
    	int[] arrInt = null;
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sDate = sDate.replaceAll("-", "");
			eDate = eDate.replaceAll("-", "");

			if( arrCode.size() > 1 )
			{
				sb.append("SELECT (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE = A1_CODE)AS IC_NAME1, A1_CODE,  \n");
				sb.append("		  (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE = A2_CODE)AS IC_NAME2, A2_CODE,  \n");
				sb.append(" 	   SUM(CT) AS TT								  \n");
				sb.append("FROM \n");
				sb.append("	( \n");
				sb.append("	SELECT A1_CODE, A2_CODE, CASE WHEN ID_SEQ > 0 THEN 1 ELSE 0 END AS CT \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append("		SELECT A1.IC_CODE AS A1_CODE, A2.IC_CODE AS A2_CODE \n");
				sb.append("		FROM \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE>0) A1, \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE>0) A2 \n");
				sb.append("		) B1  LEFT OUTER JOIN \n");
				sb.append("		( \n");
				sb.append("		SELECT A4.IC_CODE AS A4_CODE, A5.IC_CODE AS A5_CODE, A3.ID_SEQ \n");
				sb.append("		FROM \n");
				sb.append("			( \n");
				sb.append("				SELECT ID_SEQ \n");
				sb.append("				FROM ISSUE_DATA \n");
				sb.append("				WHERE MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' \n");
				sb.append("			) A3, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType1+") A4, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType2+") A5, \n");
				if(temp != null){
					sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+temp[0]+" AND IC_CODE="+temp[1]+") A7, \n");
				}
				sb.append("			( \n");
				sb.append("			SELECT ID_SEQ \n");
				sb.append("			FROM ISSUE_DATA_CODE \n");
				for( int i=0 ; i<arrCode.size(); i++ )
				{
					arrTemp = (ArrayList)arrCode.get(i);
					
					if( i == 0 )
						sb.append("			WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					else
						sb.append("			OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
				}
				sb.append("			GROUP BY ID_SEQ  \n");
				sb.append("			) A6 \n");
				sb.append("		WHERE A3.ID_SEQ=A4.ID_SEQ AND A3.ID_SEQ=A5.ID_SEQ AND A3.ID_SEQ=A6.ID_SEQ \n");
				if(temp != null){
					sb.append("	AND A3.ID_SEQ=A7.ID_SEQ  \n");
				}
				sb.append("		) B2 \n");
				sb.append("		ON A1_CODE=A4_CODE AND A2_CODE=A5_CODE \n");
				sb.append("	) C1 \n");
				sb.append("GROUP BY A1_CODE, A2_CODE \n");
				sb.append("ORDER BY A1_CODE, A2_CODE \n");
			} else {
				sb.append("SELECT (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE = A1_CODE)AS IC_NAME1, A1_CODE,  \n");
				sb.append("		  (SELECT IC_NAME FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE = A2_CODE)AS IC_NAME2, A2_CODE,  \n");
				sb.append(" 	   SUM(CT) AS TT								  \n");
				sb.append("FROM \n");
				sb.append("	( \n");
				sb.append("	SELECT A1_CODE, A2_CODE, CASE WHEN ID_SEQ > 0 THEN 1 ELSE 0 END AS CT \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append("		SELECT A1.IC_CODE AS A1_CODE, A2.IC_CODE AS A2_CODE \n");
				sb.append("		FROM \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType1+" AND IC_CODE>0) A1, \n");
				sb.append("			(SELECT IC_CODE FROM ISSUE_CODE WHERE IC_TYPE="+codeType2+" AND IC_CODE>0) A2 \n");
				sb.append("		) B1  LEFT OUTER JOIN \n");
				sb.append("		( \n");
				sb.append("		SELECT A4.IC_CODE AS A4_CODE, A5.IC_CODE AS A5_CODE, A3.ID_SEQ \n");
				sb.append("		FROM \n");
				sb.append("			( \n");
				sb.append("				SELECT ID_SEQ \n");
				sb.append("				FROM ISSUE_DATA \n");
				sb.append("				WHERE MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' \n");
				sb.append("			) A3, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType1+") A4, \n");
				sb.append("			(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+codeType2+") A5 \n");
				if(temp != null){
					sb.append("			,(SELECT ID_SEQ, IC_CODE FROM ISSUE_DATA_CODE WHERE IC_TYPE="+temp[0]+" AND IC_CODE="+temp[1]+") A7 \n");
				}
				sb.append("		WHERE A3.ID_SEQ=A4.ID_SEQ AND A3.ID_SEQ=A5.ID_SEQ \n");
				if(temp != null){
					sb.append("	AND A3.ID_SEQ=A7.ID_SEQ  \n");
				}
				sb.append("		) B2 \n");
				sb.append("		ON A1_CODE=A4_CODE AND A2_CODE=A5_CODE \n");
				sb.append("	) C1 \n");
				sb.append("GROUP BY A1_CODE, A2_CODE \n");
				sb.append("ORDER BY A1_CODE, A2_CODE \n");
			}

			System.out.println("getDualCodeStatic \n"+sb.toString());
			
			rs = stmt.executeQuery( sb.toString() );
			
			result = new ArrayList();
			result = new ArrayList();
			while( rs.next() )
			{
				arrInt = new int[3];
				
				arrInt[0] = rs.getInt("A1_CODE");
				arrInt[1] = rs.getInt("A2_CODE");
				arrInt[2] = rs.getInt("TT");
				
				result.add( arrInt );
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    
    public ArrayList getDateStatic( String sDate, String eDate, ArrayList arrCode, String chartUnit )
    {
    	ArrayList result = null;
    	
    	ArrayList arrTemp = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sDate = sDate.replaceAll("-", "");
			eDate = eDate.replaceAll("-", "");
			
			String unit = "";
			if( chartUnit.equals("w") ) unit = "ID_WEEK";
			else if( chartUnit.equals("m") ) unit = "ID_MONTH";
			else unit = "DATE_FORMAT(MD_DATE,'%Y%m%d')";
			
			if( arrCode.size() > 0 )
			{
				sb.append("SELECT A1."+unit+", COUNT(A1.ID_SEQ) AS TT \n");
				sb.append("FROM ISSUE_DATA A1, \n");
				sb.append("	 ( \n");
				sb.append("	SELECT ID_SEQ  \n");
				sb.append(" 	FROM ISSUE_DATA_CODE \n");
				for( int i=0 ; i<arrCode.size(); i++ )
				{
					arrTemp = (ArrayList)arrCode.get(i);
					
					if( i == 0 )
						sb.append("	WHERE (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
					else
						sb.append("	OR (IC_TYPE="+(String)arrTemp.get(0)+" AND IC_CODE="+(String)arrTemp.get(1)+") \n");
				}
				sb.append("	GROUP BY ID_SEQ HAVING COUNT(ID_SEQ)="+arrCode.size()+" \n");
				sb.append("	 ) A2 \n");
				sb.append("WHERE A1.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' AND A1.ID_SEQ=A2.ID_SEQ \n");
				sb.append("GROUP BY A1."+unit+" \n");
				sb.append("ORDER BY A1."+unit+" \n");
			} else {
				sb.append("SELECT A1."+unit+", COUNT(A1.ID_SEQ) AS TT \n");
				sb.append("FROM ISSUE_DATA A1 \n");
				sb.append("WHERE A1.MD_DATE BETWEEN '"+sDate+"' AND '"+eDate+"' \n");
				sb.append("GROUP BY A1."+unit+" \n");
				sb.append("ORDER BY A1."+unit+" \n");
			}

			System.out.println(sb);
			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				System.out.println("chart : "+ rs.getString(unit)+" - "+rs.getString("TT"));
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }

    public ArrayList getDateStatic( String sDate, String eDate, String code, String chartUnit )
    {
    	ArrayList result = null;
    	
    	ArrayList arrTemp = null;
    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			sb = new StringBuffer();
			
			sDate = sDate.replaceAll("-", "");
			eDate = eDate.replaceAll("-", "");
			
			String unit = "";
			if( chartUnit.equals("w") ) unit = "ID_WEEK";
			else if( chartUnit.equals("m") ) unit = "ID_MONTH";
			else unit = "MD_DATE";
			
			sb.append("SELECT A1."+unit+", COUNT(A1.ID_SEQ) AS TT \n");
			sb.append("FROM ISSUE_DATA A1, \n");
			sb.append("	 ( \n");
			sb.append("	SELECT ID_SEQ  \n");
			sb.append(" 	FROM ISSUE_DATA_CODE \n");
				

			//System.out.println(sb);
			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				System.out.println("chart : "+ rs.getString(unit)+" - "+rs.getString("TT"));
			}

        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        
        return result;
    }
    
    public ArrayList getCodeList(String sdate,String edate,String stime,String etime) {
    	
    	ArrayList result = null;
    	
    	try {
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		sb = new StringBuffer();
            
    		sb.append("		SELECT C.IC_NAME AS NAME1,                                                    		\n");
    		sb.append("					 C.IC_TYPE AS TYPE1,                                                    \n");
    		sb.append("					 C.IC_CODE AS CODE1,                                                    \n");
    		sb.append("					 D.IC_NAME AS NAME2,                                                    \n");
    		sb.append("					 D.IC_TYPE AS TYPE2,                                                    \n");
    		sb.append("					 D.IC_CODE AS CODE2,                                                    \n");
    		sb.append("					 COUNT(E.ID_SEQ) AS CNT                                                  \n");
    		sb.append("		FROM                                                                          		\n");
    		sb.append("		(                                                                             		\n");
    		sb.append("					 SELECT A.IC_NAME,                                                      \n");
    		sb.append("					 A.IC_TYPE,                                                             \n");
    		sb.append("					 A.IC_CODE,                                                             \n");
    		sb.append("					 B.ID_SEQ                                                                \n");
    		sb.append("					 FROM ISSUE_CODE A, ISSUE_DATA_CODE B                                   \n");
    		sb.append("					 WHERE A.IC_TYPE=3                                                      \n");
    		sb.append("					 AND A.IC_CODE>0                                                        \n");
    		sb.append("					 AND A.IC_TYPE=B.IC_TYPE                                                \n");
    		sb.append("					 AND A.IC_CODE=B.IC_CODE                                                \n");
    		sb.append("		) C,                                                                          		\n");
    		sb.append("		(                                                                             		\n");
    		sb.append("					 SELECT A.IC_NAME,                                                      \n");
    		sb.append("					 A.IC_TYPE,                                                             \n");
    		sb.append("					 A.IC_CODE,                                                             \n");
    		sb.append("					 B.ID_SEQ                                                                \n");
    		sb.append("					 FROM ISSUE_CODE A, ISSUE_DATA_CODE B                                   \n");
    		sb.append("					 WHERE ((A.IC_TYPE=6 AND A.IC_CODE>0) OR (A.IC_TYPE=5 AND A.IC_CODE=2)) \n");
    		sb.append("					 AND A.IC_TYPE=B.IC_TYPE                                                \n");
    		sb.append("					 AND A.IC_CODE=B.IC_CODE                                                \n");
    		sb.append("		) D, ISSUE_DATA E                                                             		\n");
    		sb.append("		WHERE C.ID_SEQ=D.ID_SEQ                                                         		\n");
    		sb.append("			AND C.ID_SEQ=E.ID_SEQ                                                         	\n");
    		sb.append("			AND E.MD_DATE BETWEEN '"+sdate+"' AND '"+edate+"'                             	\n");
    		sb.append("			AND E.ID_TIME BETWEEN '"+stime+"' AND '"+etime+"'								\n");
    		sb.append("		GROUP BY C.IC_NAME, C.IC_TYPE, C.IC_CODE, D.IC_NAME, D.IC_TYPE, D.IC_CODE			\n");
    		
    		rs = stmt.executeQuery(sb.toString());
    		Log.writeExpt(sb.toString());
    		result = new ArrayList();
    		while(rs.next()) {
//    			result.add( new IssueDataCodeBean(rs.getString("NAME1"),
//												  rs.getString("TYPE1"),
//												  rs.getString("CODE1"),
//												  rs.getString("NAME2"),
//												  rs.getString("TYPE2"),
//												  rs.getString("CODE2"),
//												  rs.getInt("CNT")
//    											 )
//    					  );
    		}
    		
    	} catch(SQLException ex) {
    		Log.writeExpt(ex,sb.toString());
    	} catch(Exception ex) {
    		Log.writeExpt(ex);
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    	}
    	
    	return result;
    }
    
    public ArrayList getIssueData(String sdate,String edate,String stime,String etime) {
    	ArrayList result = null;
    	try {
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		stmt = dbconn.createStatement();
    		sb = new StringBuffer();
    		
    		sb.append("		SELECT    A.IC_TYPE AS IC_TYPE                              \n");
    		sb.append("			   	 ,A.IC_CODE AS IC_CODE                              \n");
    		sb.append("			   	 ,B.IC_TYPE AS IC_TYPE1                             \n");
    		sb.append("			   	 ,B.IC_CODE AS IC_CODE1                             \n");
    		sb.append("			   	 ,C.ICOM_MEMO AS ICOM_MEMO                          \n");
    		sb.append("			   	 ,D.MT_TITLE AS MT_TITLE                            \n");
    		sb.append("			   	 ,D.SD_NAME AS SD_NAME                              \n");
    		sb.append("				 ,D.MT_DATE AS MT_DATE								\n");
    		sb.append("				 ,D.MT_URL AS MT_URL								\n");
    		sb.append("			   	 ,D.MD_DATE AS MD_DATE                              \n");
    		sb.append("			   	 ,D.ID_TIME AS ID_TIME                              \n");
    		sb.append("				 ,E.IC_NAME	AS IC_NAME								\n");
    		sb.append("		FROM ISSUE_DATA_CODE A,                               		\n");
    		sb.append("			 ISSUE_DATA_CODE B,                                 	\n");
    		sb.append("			 ISSUE_COMMENT C,                                   	\n");
    		sb.append("			 ISSUE_DATA D,                                      	\n");
    		sb.append("			 ISSUE_CODE E											\n");
    		sb.append("		WHERE A.IC_TYPE = '3' AND B.IC_TYPE = '1'             		\n");
    		sb.append("			  AND B.IC_CODE >0 AND C.ICOM_TYPE = '1'            	\n");
    		sb.append("			  AND D.MD_DATE BETWEEN '"+sdate+"' AND '"+edate+"' 	\n");
    		sb.append("			  AND D.ID_TIME BETWEEN '"+stime+"' AND '"+etime+"'		\n");
    		sb.append("			  AND E.IC_TYPE = B.IC_TYPE AND E.IC_CODE = B.IC_CODE	\n");
    		sb.append("			  AND A.ID_SEQ = B.ID_SEQ                             	\n");
    		sb.append("			  AND A.ID_SEQ = C.ID_SEQ                             	\n");
    		sb.append("			  AND A.ID_SEQ = D.ID_SEQ                             	\n");
    		sb.append("		ORDER BY B.IC_CODE ASC,D.MT_DATE DESC 				 		\n");
    		
    		result = new ArrayList();
    		rs = stmt.executeQuery(sb.toString());
    		Log.writeExpt(sb.toString());
    		while(rs.next()){
//    			result.add( new IssueDataCodeBean(rs.getString("IC_TYPE"),
//												  rs.getString("IC_CODE"),
//												  rs.getString("IC_TYPE1"),
//												  rs.getString("IC_CODE1"),
//												  rs.getString("ICOM_MEMO"),
//												  rs.getString("MT_TITLE"),
//												  rs.getString("SD_NAME"),
//												  rs.getString("MD_DATE"),
//												  rs.getString("ID_TIME"),
//												  rs.getString("IC_NAME"),
//												  rs.getString("MT_DATE"),
//												  rs.getString("MT_URL")
//												 )
//						 );
    			
    		}
    		

    	} catch(SQLException ex) {
    		Log.writeExpt(ex,sb.toString());
    	} catch(Exception ex) {
    		Log.writeExpt(ex);
    	} finally {
    		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
    	}
    	return result;
    }
}
