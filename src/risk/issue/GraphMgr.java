package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import risk.JfreeChart.ChartBean;
import risk.JfreeChart.GetChartData;


import risk.DBconn.DBconn;
import risk.util.ConfigUtil;
import risk.util.Log;
import risk.issue.GraphDataInfo;
import risk.admin.keyword.KeywordBean;
import risk.issue.IssueCodeBean;


public class GraphMgr {
	public ArrayList arrName1 = new ArrayList();
	public ArrayList arrName3 = new ArrayList();
	public ArrayList arrName4 = new ArrayList();
	private IssueCodeBean icBean = new IssueCodeBean();
	
	public ArrayList getIssueData( String psSdate,
				  				   String psEdate,
				  				   String psIcType
								 )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		IssueCodeBean icb; 

		ArrayList arrGdatas = new ArrayList();
		
		Timestamp sStamp = null;
		Timestamp eStamp = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
		
		sStamp = new Timestamp( (Integer.parseInt(sDate.substring(0,4))-1900), (Integer.parseInt(sDate.substring(4,6))-1), Integer.parseInt(sDate.substring(6,8)), 0, 0, 0, 0);
		eStamp = new Timestamp( (Integer.parseInt(eDate.substring(0,4))-1900), (Integer.parseInt(eDate.substring(4,6))-1), Integer.parseInt(eDate.substring(6,8)), 23, 59, 59, 999);
		
		
		arrReturnList = new ArrayList(); 


		int loopCheck = 0;
		ArrayList arrTempDate = new ArrayList();
		
		while( sStamp.getTime() < eStamp.getTime()  )
		{
			
			arrGdatas.add( sdf2.format(sStamp) );
			arrTempDate.add(sdf.format(sStamp));
			sStamp.setTime( (sStamp.getTime()+86400000) );
		}
		
		arrReturnList.add(new GraphDataInfo("날짜",arrGdatas ));
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_CODE, 	\n");
		sbSql.append(" 		  IC_NAME 	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE=?	\n");
		sbSql.append(" 		AND IC_CODE > 0	\n");
		sbSql.append(" ORDER BY IC_CODE ASC	\n");
		
		ArrayList arrTempKeyword = new ArrayList();

		int tempCode = 0;
		String tempName = "";
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psIcType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				tempCode = rs.getInt(1);
				tempName = rs.getString(2);
				icBean = new IssueCodeBean();
				icBean.setIc_name(tempName);
				icBean.setIc_type(Integer.parseInt(psIcType));
				icBean.setIc_code(tempCode);				
				arrTempKeyword.add(icBean);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		int arrDataTemp[][] = new int[arrTempKeyword.size()][arrTempDate.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempKeyword.size(); i++ ) {
			for( j=0; j<arrTempDate.size(); j++ ) {
				arrDataTemp[i][j] = 0;
			}
		}

		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT C.ID_DATE, 		\n");
		sbSql.append(" 		  A.IC_CODE, 		\n");
		sbSql.append(" 		  A.IC_NAME, 		\n");
		sbSql.append(" 		  COUNT(B.MT_NO) AS CNT		\n");
		sbSql.append(" FROM IMS_ISSUE_CODE A,		\n");
		sbSql.append(" 		(SELECT MT_NO, 		\n");
		sbSql.append(" 		 		IC_CODE		\n");
		sbSql.append(" 		 FROM IMS_ISSUE_DATA_CODE		\n");
		sbSql.append(" 		 WHERE IC_TYPE=? 		\n");
		sbSql.append(" 		) B,		\n");
		sbSql.append(" 		(SELECT MT_NO, 		\n");
		sbSql.append(" 				ID_DATE		\n");
		sbSql.append(" 		 FROM IMS_ISSUE_DATA		\n");
		sbSql.append(" 		 WHERE ID_DATE BETWEEN ? AND ?		\n");
		sbSql.append(" 		) C		\n");
		sbSql.append(" WHERE A.IC_TYPE=?		\n");
		sbSql.append(" AND A.IC_CODE=B.IC_CODE		\n");
		sbSql.append(" AND B.MT_NO=C.MT_NO		\n");
		sbSql.append(" GROUP BY C.ID_DATE, A.IC_CODE, A.IC_NAME		\n");
		sbSql.append(" ORDER BY C.ID_DATE ASC, A.IC_CODE ASC 		\n");
		
		System.out.println(sbSql.toString());

		String mtDate = "";
		String iName = "";
		int iCount = 0;
		int iCode = 0;
		
		int YpCheck = 0;
		
		IssueCodeBean icTemp = null;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psIcType);
			pstmt.setString(2, sDate);
			pstmt.setString(3, eDate);
			pstmt.setString(4, psIcType);

			rs = pstmt.executeQuery();
			
			loopCheck = 0;
			YpCheck = 0;
			
			while(rs.next()) {
				mtDate = rs.getString(1);
				iCode = rs.getInt(2);
				iName = rs.getString(3);
				iCount = rs.getInt(4);
				

				//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
				
				// 해당 값과 날짜가 있는지 체크 
				for( i=0; i<arrTempKeyword.size(); i++ ) {
					icTemp = (IssueCodeBean) arrTempKeyword.get(i);
					for( j=0; j<arrTempDate.size(); j++ ) {
						if( arrTempDate.get(j).equals(mtDate) && icTemp.getIc_code() == iCode ) {
							arrDataTemp[i][j] = iCount;
						}
					}
				}
			}

			// 뽑아낸 결과를 ArrayList 에다가 넣는다.
			for( i=0; i<arrTempKeyword.size(); i++ ) {
				icTemp = (IssueCodeBean) arrTempKeyword.get(i);
				ArrayList arrTemp = new ArrayList();

				ArrayList listTmp2 = new ArrayList();			//임시리스트
				GraphDataInfo gdDatasSub = new GraphDataInfo("", listTmp2);
				gdDatasSub.setName(icTemp.getIc_name());

				for( j=0; j<arrTempDate.size(); j++ ) {
					gdDatasSub.addData( arrDataTemp[i][j]);
				}
				arrReturnList.add(gdDatasSub);
				arrTemp = null;
			}

		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	
	public ArrayList getIssueData2( String psSdate,
								    String psEdate,
								    String psIcType
								   )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;

		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		IssueCodeBean icb; 
		
		ArrayList arrGdatas = new ArrayList();
		
		Timestamp sStamp = null;
		Timestamp eStamp = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		arrReturnList = new ArrayList(); 


		int loopCheck = 0;
		ArrayList arrTempCode = new ArrayList();		
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_TYPE, IC_CODE, IC_NAME		\n");
		sbSql.append(" FROM IMS_ISSUE_CODE		\n");
		sbSql.append(" WHERE ((IC_TYPE=6 AND IC_CODE > 0) OR (IC_TYPE=5 AND IC_CODE=2))		\n");
		sbSql.append(" ORDER BY IC_TYPE DESC, IC_CODE ASC		\n");
		
		System.out.println("GraphMgr.java getIssueData2() \n"+ sbSql.toString());
		
		int tempCode = 0;
		int tempType = 0;
		String tempName = "";
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				tempCode = 0;
				tempType = 0;
				tempName = "";
				
				tempType = rs.getInt(1);
				tempCode = rs.getInt(2);
				tempName = rs.getString(3);
				
				icBean = new IssueCodeBean();
				icBean.setIc_name(tempName);
				icBean.setIc_type(tempType);
				icBean.setIc_code(tempCode);				
				
				arrTempCode.add(icBean);
				arrGdatas.add(tempName);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
				
		arrReturnList.add(new GraphDataInfo("Type",arrGdatas ));
		
		ArrayList arrTempGrade = new ArrayList();
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_TYPE, IC_CODE, IC_NAME	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE=3 AND IC_CODE > 0	\n");
		sbSql.append(" ORDER BY IC_TYPE DESC, IC_CODE ASC	\n");
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				tempCode = 0;
				tempType = 0;
				tempName = "";
				
				tempType = rs.getInt(1);
				tempCode = rs.getInt(2);
				tempName = rs.getString(3);
				
				icBean = new IssueCodeBean();
				icBean.setIc_name(tempName);
				icBean.setIc_type(tempType);
				icBean.setIc_code(tempCode);
				
				arrTempGrade.add(icBean);
				
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		
		
		
		int arrDataTemp[][] = new int[arrTempGrade.size()][arrTempCode.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempGrade.size(); i++ ) {
			for( j=0; j<arrTempCode.size(); j++ ) {
				arrDataTemp[i][j] = 0;
			}
		}

		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT C.IC_TYPE AS TYPE1,	\n");
		sbSql.append(" 		  C.IC_CODE AS CODE2,	\n");
		//sbSql.append(" 		  C.IC_NAME AS NAME1,	\n");
		sbSql.append(" 		  D.IC_TYPE AS TYPE2,	\n");
		sbSql.append(" 		  D.IC_CODE AS CODE2, 		\n");	
		//sbSql.append(" 		  D.IC_NAME AS NAME2,	\n");
		sbSql.append(" 		  COUNT(E.MT_NO) AS CNT		\n");	
		sbSql.append(" FROM (SELECT A.MT_NO,	\n");
		sbSql.append(" 				A.IC_TYPE, 			\n");
		sbSql.append(" 				A.IC_CODE,	\n");
		sbSql.append(" 				B.IC_NAME			\n");
		sbSql.append(" 		 FROM IMS_ISSUE_DATA_CODE A, IMS_ISSUE_CODE B	\n");		
		sbSql.append(" 		 WHERE ((A.IC_TYPE=6 AND A.IC_CODE > 0) OR (A.IC_TYPE=5 AND A.IC_CODE=2))	\n");
		sbSql.append(" 			AND A.IC_TYPE=B.IC_TYPE	\n");
		sbSql.append(" 			AND A.IC_CODE=B.IC_CODE 			\n");
		sbSql.append(" 		) C,	\n");
		sbSql.append(" 		(SELECT A.MT_NO,	\n");
		sbSql.append(" 				A.IC_TYPE, 			\n");
		sbSql.append(" 				A.IC_CODE,	\n");
		sbSql.append(" 				B.IC_NAME			\n");
		sbSql.append(" 		 FROM IMS_ISSUE_DATA_CODE A, IMS_ISSUE_CODE B	\n");				
		sbSql.append(" 		 WHERE A.IC_TYPE=3 AND A.IC_CODE > 0 		\n");
		sbSql.append(" 			AND A.IC_TYPE=B.IC_TYPE	\n");
		sbSql.append(" 			AND A.IC_CODE=B.IC_CODE 		\n");
		sbSql.append(" 		) D,			\n");
		sbSql.append(" 		(SELECT MT_NO, 			\n");
		sbSql.append(" 				ID_DATE			\n");
		sbSql.append(" 		 FROM IMS_ISSUE_DATA			\n");
		sbSql.append(" 		 WHERE ID_DATE BETWEEN ? AND ?	\n");		
		sbSql.append(" 		) E			\n");
		sbSql.append(" WHERE C.MT_NO=E.MT_NO	\n");
		sbSql.append(" 		AND D.MT_NO=E.MT_NO		\n");	
		sbSql.append(" GROUP BY C.IC_TYPE,	\n");
		sbSql.append(" 			C.IC_CODE, 			\n");
		sbSql.append(" 			C.IC_NAME,	\n");
		sbSql.append(" 			D.IC_TYPE,	\n");
		sbSql.append(" 			D.IC_CODE, 			\n");
		sbSql.append(" 			D.IC_NAME	\n");
		sbSql.append(" ORDER BY C.IC_TYPE ASC,	\n");
		sbSql.append(" 			C.IC_CODE ASC ,	\n");
		sbSql.append(" 			C.IC_NAME ASC ,	\n");
		sbSql.append(" 			D.IC_TYPE ASC ,	\n");
		sbSql.append(" 			D.IC_CODE ASC , 			\n");
		sbSql.append(" 			D.IC_NAME ASC	\n");
	 
		
		
		
		System.out.println(sbSql.toString());
		
		String mtDate = "";
		String iName = "";
		int iCount = 0;
		int iCode = 0;
		
		int YpCheck = 0;
		
		int tmpType1 = 0;
		int tmpType2 = 0;
		
		int tmpCode1 = 0;
		int tmpCode2 = 0;
		
		IssueCodeBean icTemp = null;
		IssueCodeBean icTemp2 = null;
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, sDate);
			pstmt.setString(2, eDate);
		
			rs = pstmt.executeQuery();
		
			loopCheck = 0;
			YpCheck = 0;
		
			while(rs.next()) {
				tmpType1 = rs.getInt(1);
				tmpCode1 = rs.getInt(2);
				tmpType2 = rs.getInt(3);
				tmpCode2 = rs.getInt(4);
				iCount = rs.getInt(5);
		
				//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
				
				//System.out.println("tmpType2 = "+tmpType2+", tmpCode2 = "+tmpCode2+", tmpType1 = "+tmpType1+", tmpCode1 = "+tmpCode1+"");
		
				// 해당 값과 날짜가 있는지 체크 
				for( i=0; i<arrTempGrade.size(); i++ ) {
					icTemp = (IssueCodeBean) arrTempGrade.get(i);
					for( j=0; j<arrTempCode.size(); j++ ) {
						icTemp2 = (IssueCodeBean) arrTempCode.get(j);
						
						//System.out.println("icTemp.getIc_type() = "+icTemp.getIc_type()+", icTemp.getIc_code() = "+icTemp.getIc_code()+", icTemp2.getIc_type() = "+icTemp2.getIc_type()+", icTemp2.getIc_code() = "+icTemp2.getIc_code()+"");
						
						if( tmpType2 == icTemp.getIc_type() && 
							tmpCode2 == icTemp.getIc_code() && 
							tmpType1 == icTemp2.getIc_type() &&
							tmpCode1 == icTemp2.getIc_code() ) {
							
							arrDataTemp[i][j] = iCount;
						}
					}
				}
			}
			
			System.out.println("arrTempCode.size() = "+arrTempCode.size());
		
			// 뽑아낸 결과를 ArrayList 에다가 넣는다.
			for( i=0; i< arrTempGrade.size(); i++ ) {
				icTemp = (IssueCodeBean) arrTempGrade.get(i);
				ArrayList arrTemp = new ArrayList();
		
				ArrayList listTmp2 = new ArrayList();			//임시리스트
				GraphDataInfo gdDatasSub = new GraphDataInfo("", listTmp2);
				gdDatasSub.setName(icTemp.getIc_name());
		
				for( j=0; j<arrTempCode.size(); j++ ) {
					gdDatasSub.addData( arrDataTemp[i][j]);
				}
				arrReturnList.add(gdDatasSub);
				arrTemp = null;
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return arrReturnList;
	}
	
	
	public ArrayList getBrendData( String psSdate,
			   					   String psEdate,
			   					   String psXp
			   					  )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		
		ArrayList arrGdatas = new ArrayList();
		
		Timestamp sStamp = null;
		Timestamp eStamp = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
		
		sStamp = new Timestamp( (Integer.parseInt(sDate.substring(0,4))-1900), (Integer.parseInt(sDate.substring(4,6))-1), Integer.parseInt(sDate.substring(6,8)), 0, 0, 0, 0);
		eStamp = new Timestamp( (Integer.parseInt(eDate.substring(0,4))-1900), (Integer.parseInt(eDate.substring(4,6))-1), Integer.parseInt(eDate.substring(6,8)), 23, 59, 59, 999);
		
		
		arrReturnList = new ArrayList(); 
		
		
		int loopCheck = 0;
		ArrayList arrTempDate = new ArrayList();
		
		while( sStamp.getTime() < eStamp.getTime()  )
		{
			arrGdatas.add( sdf2.format(sStamp) );
			arrTempDate.add(sdf.format(sStamp));
			sStamp.setTime( (sStamp.getTime()+86400000) );
		}
		
		arrReturnList.add(new GraphDataInfo("날짜",arrGdatas ));
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT K_XP,				\n");
		sbSql.append(" 		  K_YP,				\n");
		sbSql.append(" 	      K_VALUE 			\n");
		sbSql.append(" FROM IMS_KEYWORD 			\n");
		sbSql.append(" WHERE K_XP IN( ? )		\n");
		sbSql.append("   AND K_YP > 0	  		\n");
		sbSql.append(" 	 AND K_ZP = 0			\n");
		sbSql.append(" 	 AND K_TYPE = 1			\n");
		sbSql.append(" ORDER BY  K_XP, K_YP		\n");
		
		ArrayList arrTempKeyword = new ArrayList();
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psXp);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				KeywordBean kb = new KeywordBean();
				kb.setKGxp(rs.getString(1));
				kb.setKGyp(rs.getString(2));
				kb.setKGvalue(rs.getString(3));
				arrTempKeyword.add(kb);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		int arrDataTemp[][] = new int[arrTempKeyword.size()][arrTempDate.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempKeyword.size(); i++ ) {
			for( j=0; j<arrTempDate.size(); j++ ) {
				arrDataTemp[i][j] = 0;
			}
		}
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT CONVERT(CHAR(8),M.MT_DATE,112) AS MT_DATE,				\n");
		sbSql.append(" 		  K.K_XP, 	\n");
		sbSql.append(" 		  K.K_YP, 	\n");
		sbSql.append(" 	      K.K_VALUE, 	\n");
		sbSql.append(" 		  ISNULL(COUNT(1),0)  AS CNT	\n"); 
		sbSql.append(" FROM IMS_META M, IMS_IDX I, IMS_KEYWORD K 	\n");
		sbSql.append(" WHERE I.K_XP IN( ? ) 	\n");
		sbSql.append(" 		AND CONVERT(CHAR(8),M.MT_DATE,112) BETWEEN ? AND ?	\n");
		sbSql.append(" 		AND M.MT_NO = I.MT_NO	\n");
		sbSql.append(" 		AND I.K_XP = K.K_XP 	\n");
		sbSql.append(" 		AND I.K_YP = K.K_YP 	\n");
		sbSql.append(" 		AND K.K_ZP = 0	\n");
		sbSql.append(" 		AND K.K_TYPE = 1	\n");
		sbSql.append(" GROUP BY  CONVERT(CHAR(8),M.MT_DATE,112), K.K_XP, K.K_YP, K.K_VALUE	\n");
		sbSql.append(" ORDER BY  MT_DATE, K.K_VALUE	\n");

		System.out.println(sbSql.toString());

		String mtDate = "";
		String kValue = "";
		int kCount = 0;
		String kXp = "";
		String kYp = "";
		int YpCheck = 0;
		
		KeywordBean kbTemp = null;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psXp);
			pstmt.setString(2, sDate);
			pstmt.setString(3, eDate);
			rs = pstmt.executeQuery();
			
			loopCheck = 0;
			YpCheck = 0;

			while(rs.next()) {
				mtDate = rs.getString(1);
				kXp = rs.getString(2);
				kYp = rs.getString(3);
				kValue = rs.getString(4);
				kCount = rs.getInt(5);
				
				//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
				
				// 해당 값과 날짜가 있는지 체크 
				for( i=0; i<arrTempKeyword.size(); i++ ) {
					kbTemp = (KeywordBean) arrTempKeyword.get(i);
					for( j=0; j<arrTempDate.size(); j++ ) {
						if( arrTempDate.get(j).equals(mtDate) && kbTemp.getKGxp().equals(kXp) && kbTemp.getKGyp().equals(kYp)  ) {
							arrDataTemp[i][j] = kCount;
						}
					}
				}
			}
			
			// 뽑아낸 결과를 ArrayList 에다가 넣는다.
			for( i=0; i<arrTempKeyword.size(); i++ ) {
				kbTemp = (KeywordBean) arrTempKeyword.get(i);
				ArrayList arrTemp = new ArrayList();
				
				ArrayList listTmp2 = new ArrayList();			//임시리스트
				GraphDataInfo gdDatasSub = new GraphDataInfo("", listTmp2);
				gdDatasSub.setName(kbTemp.getKGvalue());
	        	
				for( j=0; j<arrTempDate.size(); j++ ) {
					gdDatasSub.addData( arrDataTemp[i][j]);
				}
				arrReturnList.add(gdDatasSub);
				arrTemp = null;
			}

		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	

	public ArrayList getTopicData( String psSdate,
				   				   String psEdate,
				   				   String psXp
								  )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		arrReturnList = new ArrayList();
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT BB.K_VALUE, BB.CNT	\n");
		sbSql.append(" FROM (	\n");
		sbSql.append(" SELECT TOP 5 K.K_VALUE,				\n");
		sbSql.append(" 		  ISNULL(COUNT( A.MT_NO), 0 ) AS CNT			\n");
		sbSql.append(" FROM (SELECT K_XP, K_YP, K_VALUE	\n");
		sbSql.append(" 		 FROM IMS_KEYWORD			\n");
		sbSql.append(" 		 WHERE  K_XP IN (?)		\n");
		sbSql.append(" 			 AND K_ZP=0			\n");
		sbSql.append(" 		 ) K LEFT OUTER JOIN (SELECT I.K_XP, I.K_YP, I.MT_NO			\n");
		sbSql.append(" 							  FROM IMS_META M, IMS_IDX I			\n");
		sbSql.append(" 							  WHERE I.K_XP IN( ? ) 			\n");
		sbSql.append(" 							    AND CONVERT(CHAR(8),M.MT_DATE,112) BETWEEN ?			\n"); 
		sbSql.append(" 								AND ?			\n");
		sbSql.append(" 								AND M.MT_NO=I.MT_NO			\n");
		sbSql.append(" 							  ) A			\n");
		sbSql.append(" 		 ON ( K.K_XP=A.K_XP AND K.K_YP=A.K_YP )			\n");
		sbSql.append(" GROUP BY K.K_VALUE 			\n");
		sbSql.append(" ORDER BY CNT DESC			\n");
		sbSql.append(" ) BB		\n");
		sbSql.append(" ORDER BY BB.CNT ASC		\n");
		
		System.out.println(sbSql.toString());
			
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psXp);
			pstmt.setString(2, psXp);
			pstmt.setString(3, sDate);
			pstmt.setString(4, eDate);
			rs = pstmt.executeQuery();
			
			ArrayList arrTemp = new ArrayList();
			ArrayList arrTemp2 = new ArrayList();
			
			GraphDataInfo gdDatasSub = new GraphDataInfo("title", arrTemp);
			GraphDataInfo gdDatasSub2 = new GraphDataInfo("data", arrTemp2);
			while(rs.next()) {
				
				gdDatasSub.addData( rs.getString(1));
				gdDatasSub2.addData( rs.getInt(2));
			}
			
			arrReturnList.add(gdDatasSub);
			arrReturnList.add(gdDatasSub2);
			
//			while(rs.next()) {
//				ArrayList arrTemp = new ArrayList();
//				
//				GraphDataInfo gdDatasSub = new GraphDataInfo("", arrTemp);
//				gdDatasSub.setName(rs.getString(1));
//				gdDatasSub.addData( rs.getInt(2));
//				arrReturnList.add(gdDatasSub);
//			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	
	public ArrayList getSourceData( String psSdate,
								    String psEdate,
								    String psXp,
								    String psYp,
								    String psZp
								   )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		arrReturnList = new ArrayList();
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT BB.SG_NAME, BB.CNT	\n");
		sbSql.append(" FROM (	\n");
		sbSql.append(" SELECT TOP 5 SG1.SG_NAME, 			\n");
		sbSql.append(" 		  ISNULL(SG2.CNT,0) AS CNT 	\n");
		sbSql.append(" FROM							\n");
		sbSql.append(" 	(SELECT SG_SEQ, SG_NAME FROM IMS_SITE_GROUP) SG1 LEFT OUTER JOIN	\n");
		sbSql.append(" 		(							\n");
		sbSql.append(" 		SELECT SG_NAME, SG.SG_SEQ,  ISNULL(COUNT(1),0)  AS CNT	\n"); 
		sbSql.append(" 		FROM IMS_IDX I, IMS_META M, IMS_SITE_GROUP SG 	\n");
		sbSql.append(" 		WHERE I.K_XP = ? AND I.K_YP = ? 	\n");
		sbSql.append(" 			AND I.K_ZP = ?  	\n");
		sbSql.append(" 			AND CONVERT(CHAR(8),M.MT_DATE,112) BETWEEN ? AND ?	\n");
		sbSql.append(" 			AND I.MT_NO = M.MT_NO	\n");
		sbSql.append(" 			AND SG.SG_SEQ = M.SG_SEQ	\n");
		sbSql.append(" 		GROUP BY  SG.SG_NAME, SG.SG_SEQ	\n");
		sbSql.append(" 		) 	\n");
		sbSql.append(" SG2 ON(SG1.SG_SEQ=SG2.SG_SEQ)	\n");
		sbSql.append(" ORDER BY CNT DESC	\n");
		sbSql.append(" ) BB		\n");
		
		
		System.out.println(sbSql.toString());

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psXp);
			pstmt.setString(2, psYp);
			pstmt.setString(3, psZp);
			pstmt.setString(4, sDate);
			pstmt.setString(5, eDate);
			rs = pstmt.executeQuery();
			
			ArrayList arrTemp = new ArrayList();
			ArrayList arrTemp2 = new ArrayList();
			
			GraphDataInfo gdDatasSub = new GraphDataInfo("title", arrTemp);
			GraphDataInfo gdDatasSub2 = new GraphDataInfo("data", arrTemp2);
			while(rs.next()) {
				
				gdDatasSub.addData( rs.getString(1));
				gdDatasSub2.addData( rs.getInt(2));
			}
			
			arrReturnList.add(gdDatasSub);
			arrReturnList.add(gdDatasSub2);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	
	public ArrayList getTopicPieData( String psSdate,
									  String psEdate,
									  String psXp
									 )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		arrReturnList = new ArrayList();
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT BB.K_VALUE, BB.CNT	\n");
		sbSql.append(" FROM (	\n");
		sbSql.append(" SELECT TOP 5 K.K_VALUE,				\n");
		sbSql.append(" 		  ISNULL(COUNT( A.MT_NO), 0 ) AS CNT			\n");
		sbSql.append(" FROM (SELECT K_XP, K_YP, K_VALUE	\n");
		sbSql.append(" 		 FROM IMS_KEYWORD			\n");
		sbSql.append(" 		 WHERE  K_XP IN (?)		\n");
		sbSql.append(" 			 AND K_ZP=0			\n");
		sbSql.append(" 		 ) K LEFT OUTER JOIN (SELECT I.K_XP, I.K_YP, I.MT_NO			\n");
		sbSql.append(" 							  FROM IMS_META M, IMS_IDX I			\n");
		sbSql.append(" 							  WHERE I.K_XP IN( ? ) 			\n");
		sbSql.append(" 							    AND CONVERT(CHAR(8),M.MT_DATE,112) BETWEEN ?			\n"); 
		sbSql.append(" 								AND ?			\n");
		sbSql.append(" 								AND M.MT_NO=I.MT_NO			\n");
		sbSql.append(" 							  ) A			\n");
		sbSql.append(" 		 ON ( K.K_XP=A.K_XP AND K.K_YP=A.K_YP )			\n");
		sbSql.append(" GROUP BY K.K_VALUE 			\n");
		sbSql.append(" ORDER BY CNT DESC			\n");
		sbSql.append(" ) BB		\n");
		//sbSql.append(" ORDER BY BB.CNT ASC		\n");
		
		System.out.println(sbSql.toString());

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psXp);
			pstmt.setString(2, psXp);
			pstmt.setString(3, sDate);
			pstmt.setString(4, eDate);
			rs = pstmt.executeQuery();
			
			ArrayList arrTemp = new ArrayList();
			ArrayList arrTemp2 = new ArrayList();
			
			GraphDataInfo gdDatasSub = new GraphDataInfo("title", arrTemp);
			GraphDataInfo gdDatasSub2 = new GraphDataInfo("data", arrTemp2);
			while(rs.next()) {
				
				gdDatasSub.addData( rs.getString(1));
				gdDatasSub2.addData( rs.getInt(2));
			}
			
			arrReturnList.add(gdDatasSub);
			arrReturnList.add(gdDatasSub2);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	
	public ArrayList getEvocData( String psSdate,
			   					  String psEdate,
								  String psIcType,
								  String psDateType
								)
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		IssueCodeBean icb; 
		
		ArrayList arrGdatas = new ArrayList();
		
		Timestamp sStamp = null;
		Timestamp eStamp = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		SimpleDateFormat sdf = null;
		SimpleDateFormat sdf2 = null;
		
		if (psDateType.equals("d")) {
			sdf = new SimpleDateFormat("yyyyMMdd");
			sdf2 = new SimpleDateFormat("MM/dd");
		}
		else if (psDateType.equals("w")) {
			sdf = new SimpleDateFormat("yyyyww");
			sdf2 = new SimpleDateFormat("yyyy/ww");
		}
		else if (psDateType.equals("m")) {
			sdf = new SimpleDateFormat("yyyyMM");
			sdf2 = new SimpleDateFormat("yyyy/MM");
		}
		
		sStamp = new Timestamp( (Integer.parseInt(sDate.substring(0,4))-1900), (Integer.parseInt(sDate.substring(4,6))-1), Integer.parseInt(sDate.substring(6,8)), 0, 0, 0, 0);
		eStamp = new Timestamp( (Integer.parseInt(eDate.substring(0,4))-1900), (Integer.parseInt(eDate.substring(4,6))-1), Integer.parseInt(eDate.substring(6,8)), 23, 59, 59, 999);
		
		
		arrReturnList = new ArrayList(); 
		
		
		int loopCheck = 0;
		ArrayList arrTempDate = new ArrayList();
		
		String tempDate = "";
		
		while( sStamp.getTime() < eStamp.getTime()  )
		{
			if( !tempDate.equals(sdf.format(sStamp)) ) {
				arrGdatas.add( sdf2.format(sStamp) );
				arrTempDate.add(sdf.format(sStamp));
				tempDate = sdf.format(sStamp);
			}
			sStamp.setTime( (sStamp.getTime()+86400000) );
		}

		arrReturnList.add(new GraphDataInfo("날짜",arrGdatas ));
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_CODE, 	\n");
		sbSql.append(" 		  IC_NAME 	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE=?	\n");
		sbSql.append(" 		AND IC_CODE > 0	\n");
		
		ArrayList arrTempKeyword = new ArrayList();
		
		int tempCode = 0;
		String tempName = "";

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psIcType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				tempCode = rs.getInt(1);
				tempName = rs.getString(2);
				//IssueCodeBean icb = new IssueCodeBean();
				//kb.setKGxp(rs.getString(1));
				//kb.setKGyp(rs.getString(2));
				//kb.setKGvalue(rs.getString(3));
				
				icBean = new IssueCodeBean();
				icBean.setIc_name(tempName);
				icBean.setIc_type(Integer.parseInt(psIcType));
				icBean.setIc_code(tempCode);
				arrTempKeyword.add(icBean);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		int arrDataTemp[][] = new int[arrTempKeyword.size()][arrTempDate.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempKeyword.size(); i++ ) {
			for( j=0; j<arrTempDate.size(); j++ ) {
				arrDataTemp[i][j] = 0;
			}
		}
		
		String dateColum = "";
		if (psDateType.equals("d")) {
			dateColum = "ID_DATE";
		}else if (psDateType.equals("w")) {
			dateColum = "ID_WEEK";
		}else if (psDateType.equals("m")) {
			dateColum = "ID_MONTH";
		}else {
			dateColum = "ID_DATE";
		}

		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT ID."+dateColum+" xVal ,IDC.IC_CODE, COUNT(0) as cnt , IC.IC_NAME		\n");
		sbSql.append(" FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC, IMS_ISSUE_CODE IC						\n");
		sbSql.append(" WHERE ID.MT_NO = IDC.MT_NO									\n");
		sbSql.append(" 		AND IDC.MT_NO IN (SELECT MT_NO  	\n");
		sbSql.append(" 						  FROM IMS_ISSUE_DATA_CODE  	\n");
		sbSql.append(" 						  GROUP BY MT_NO )  	\n");
		sbSql.append(" 		AND ID.ID_DATE BETWEEN ? AND ?	\n"); 		
		sbSql.append(" 		AND IDC.IC_TYPE = ?	\n");
		sbSql.append(" 		AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");								
		sbSql.append(" GROUP BY ID."+dateColum+", IDC.IC_CODE, IC.IC_NAME	\n");
		
		System.out.println(sbSql.toString());

		String mtDate = "";
		String iName = "";
		int iCount = 0;
		int iCode = 0;
		
		int YpCheck = 0;
		
		IssueCodeBean icTemp = null;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, sDate);
			pstmt.setString(2, eDate);
			pstmt.setString(3, psIcType);
			
			rs = pstmt.executeQuery();
			
			loopCheck = 0;
			YpCheck = 0;
		
			while(rs.next()) {
				mtDate = rs.getString(1);
				iCode = rs.getInt(2);
				iCount = rs.getInt(3);
				iName = rs.getString(4);
		
				//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
		
				// 해당 값과 날짜가 있는지 체크 
				for( i=0; i<arrTempKeyword.size(); i++ ) {
					icTemp = (IssueCodeBean) arrTempKeyword.get(i);
					for( j=0; j<arrTempDate.size(); j++ ) {
						if( arrTempDate.get(j).equals(mtDate) && icTemp.getIc_code() == iCode ) {
							arrDataTemp[i][j] = iCount;
						}
					}
				}
			}
		
			// 뽑아낸 결과를 ArrayList 에다가 넣는다.
			for( i=0; i<arrTempKeyword.size(); i++ ) {
				icTemp = (IssueCodeBean) arrTempKeyword.get(i);
				ArrayList arrTemp = new ArrayList();
		
				ArrayList listTmp2 = new ArrayList();			//임시리스트
				GraphDataInfo gdDatasSub = new GraphDataInfo("", listTmp2);
				gdDatasSub.setName(icTemp.getIc_name());
		
				for( j=0; j<arrTempDate.size(); j++ ) {
					gdDatasSub.addData( arrDataTemp[i][j]);
				}
				arrReturnList.add(gdDatasSub);
				arrTemp = null;
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	
	public ArrayList getEvocData2( String psIcType,
					  			   String psIcType2,
					  			   String sDate,
					  			   String eDate,
					  			   String typecode
								 )
	{
		System.out.println("==============================================================");
		System.out.println("psIcType = "+psIcType+", psIcType2 = "+psIcType2+" ");
		System.out.println("==============================================================");
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		IssueCodeBean icb; 
		
		ArrayList arrGdatas = new ArrayList();
		
		sDate = sDate.replaceAll("-", "");
		eDate = eDate.replaceAll("-", "");
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_CODE, 	\n");
		sbSql.append(" 		  IC_NAME 	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE="+psIcType2+"	\n");
		sbSql.append(" 		AND IC_CODE > 0	\n");
		
		System.out.println(sbSql.toString());
		
		ArrayList arrTempRow = new ArrayList();
		
		int tempCode2 = 0;
		String tempName2 = "";
		
		arrReturnList = new ArrayList();

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			//pstmt.setString(1, psIcType2);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				tempCode2 = rs.getInt(1);
				tempName2 = rs.getString(2);
				
				icBean = new IssueCodeBean();
				icBean.setIc_name(tempName2);
				icBean.setIc_type(Integer.parseInt(psIcType));
				icBean.setIc_code(tempCode2);
				arrTempRow.add(icBean);
				arrGdatas.add(tempName2);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		arrReturnList.add(new GraphDataInfo("X축",arrGdatas ));

		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_CODE, 	\n");
		sbSql.append(" 		  IC_NAME 	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE="+psIcType+"	\n");
		sbSql.append(" 		AND IC_CODE > 0	\n");
		
		System.out.println(sbSql.toString());
		
		ArrayList arrTempCol = new ArrayList();
		
		int tempCode = 0;
		String tempName = "";

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			//pstmt.setString(1, psIcType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				tempCode = rs.getInt(1);
				tempName = rs.getString(2);
				
				icBean = new IssueCodeBean();
				icBean.setIc_name(tempName);
				icBean.setIc_type(Integer.parseInt(psIcType));
				icBean.setIc_code(tempCode);
				
				arrTempCol.add(icBean);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		int arrDataTemp[][] = new int[arrTempCol.size()][arrTempRow.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempCol.size(); i++ ) {
			for( j=0; j<arrTempRow.size(); j++ ) {
				arrDataTemp[i][j] = 0;
			}
		}

		sbSql = new StringBuffer();
		
		
		sbSql.append(" SELECT A1_CODE,  	\n");
		sbSql.append(" 		  A2_CODE,  	\n");
		sbSql.append(" 		  SUM(CT) AS TT		\n");							  
		sbSql.append(" FROM 	\n");
		sbSql.append(" 	  ( 	\n");
		sbSql.append(" 	  SELECT A1_CODE, 	\n");
		sbSql.append(" 			 A2_CODE, 	\n");
		sbSql.append(" 			 CASE WHEN MT_NO > 0 THEN 1 ELSE 0 END AS CT	\n"); 
		sbSql.append(" 	  FROM 	\n");
		sbSql.append(" 		 ( 	\n");
		sbSql.append(" 		  SELECT A1.IC_CODE AS A1_CODE,	\n");
		sbSql.append(" 				 A2.IC_CODE AS A2_CODE 	\n");
		sbSql.append(" 		  FROM 	\n");
		sbSql.append(" 			(SELECT IC_CODE FROM IMS_ISSUE_CODE WHERE IC_TYPE=? AND IC_CODE>0) A1,	\n"); 
		sbSql.append(" 			(SELECT IC_CODE FROM IMS_ISSUE_CODE WHERE IC_TYPE=? AND IC_CODE>0) A2 	\n");
		sbSql.append(" 		 ) B1  LEFT OUTER JOIN 	\n");
		sbSql.append(" 		 ( 	\n");
		sbSql.append(" 		  SELECT A4.IC_CODE AS A4_CODE,	\n");
		sbSql.append(" 				 A5.IC_CODE AS A5_CODE,	\n");
		sbSql.append(" 				 A3.MT_NO 	\n");
		sbSql.append(" 		  FROM 	\n");
		sbSql.append(" 			 ( 	\n");
		sbSql.append(" 			  SELECT MT_NO	\n"); 
		sbSql.append(" 			  FROM IMS_ISSUE_DATA 	\n");
		sbSql.append(" 			  WHERE ID_DATE BETWEEN ? AND ?	\n"); 
		sbSql.append(" 			 ) A3, 	\n");
		sbSql.append(" 		  (SELECT MT_NO, IC_CODE FROM IMS_ISSUE_DATA_CODE WHERE IC_TYPE=?) A4,	\n"); 
		sbSql.append(" 		  (SELECT MT_NO, IC_CODE FROM IMS_ISSUE_DATA_CODE WHERE IC_TYPE=?) A5, 	\n");
		sbSql.append("		  (SELECT MT_NO FROM IMS_ISSUE_DATA_CODE WHERE IC_TYPE = ? AND IC_CODE = ? ) A6 \n");
		sbSql.append(" 		  WHERE A3.MT_NO=A4.MT_NO AND A3.MT_NO=A5.MT_NO 	\n");
		sbSql.append(" 		  AND A6.MT_NO=A3.MT_NO \n");
		sbSql.append(" 		 ) B2 	\n");
		sbSql.append(" 		 ON A1_CODE=A4_CODE AND A2_CODE=A5_CODE	\n"); 
		sbSql.append(" 	  ) C1 	\n");
		sbSql.append(" GROUP BY A1_CODE, A2_CODE 	\n");
		sbSql.append(" ORDER BY A1_CODE, A2_CODE 	\n");
		
		System.out.println(sbSql.toString());

		int icCode1 = 0;
		int icCode2 = 0;
		int icCount = 0;
		String iName = "";
		
		int YpCheck = 0;
		
		IssueCodeBean icTemp = null;
		IssueCodeBean icTemp2 = null;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psIcType);
			pstmt.setString(2, psIcType2);
			pstmt.setString(3, sDate);
			pstmt.setString(4, eDate);
			pstmt.setString(5, psIcType);
			pstmt.setString(6, psIcType2);
			pstmt.setString(7, typecode.split(",")[0]);
			pstmt.setString(8, typecode.split(",")[1]);
			
			rs = pstmt.executeQuery();
			
			//loopCheck = 0;
			YpCheck = 0;
		
			while(rs.next()) {
				icCode1 = rs.getInt(1);
				icCode2 = rs.getInt(2);
				icCount = rs.getInt(3);
				//iCount = rs.getInt(4);
		
				//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
				
				
		
				// 해당 값과 날짜가 있는지 체크 
				for( i=0; i<arrTempCol.size(); i++ ) {
					icTemp = (IssueCodeBean) arrTempCol.get(i);
					for( j=0; j<arrTempRow.size(); j++ ) {
						icTemp2 = (IssueCodeBean) arrTempRow.get(j);
						if( (icTemp.getIc_code() == icCode1) && (icTemp2.getIc_code() == icCode2) ) {
							arrDataTemp[i][j] = icCount;
						}
					}
				}
			}
		
			// 뽑아낸 결과를 ArrayList 에다가 넣는다.
			for( i=0; i<arrTempCol.size(); i++ ) {
				icTemp = (IssueCodeBean) arrTempCol.get(i);
				ArrayList arrTemp = new ArrayList();
						//임시리스트
				GraphDataInfo gdDatasSub = new GraphDataInfo("", arrTemp);
				gdDatasSub.setName(icTemp.getIc_name());
		
				for( j=0; j<arrTempRow.size(); j++ ) {
					gdDatasSub.addData( arrDataTemp[i][j]);
				}
				arrReturnList.add(gdDatasSub);
				arrTemp = null;
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	
	public ArrayList getEvocData3( String psSdate,
								   String psEdate,
								   String psIcType,
								   String psDateType
								 )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;

		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		IssueCodeBean icb; 

		ArrayList arrGdatas = new ArrayList();
		
		Timestamp sStamp = null;
		Timestamp eStamp = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;

		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		SimpleDateFormat sdf = null;
		SimpleDateFormat sdf2 = null;
		
		if (psDateType.equals("d")) {
			sdf = new SimpleDateFormat("yyyyMMdd");
			sdf2 = new SimpleDateFormat("MM/dd");
		}
		else if (psDateType.equals("w")) {
			sdf = new SimpleDateFormat("yyyyww");
			sdf2 = new SimpleDateFormat("yyyy/ww");
		}
		else if (psDateType.equals("m")) {
			sdf = new SimpleDateFormat("yyyyMM");
			sdf2 = new SimpleDateFormat("yyyy/MM");
		}
		
		sStamp = new Timestamp( (Integer.parseInt(sDate.substring(0,4))-1900), (Integer.parseInt(sDate.substring(4,6))-1), Integer.parseInt(sDate.substring(6,8)), 0, 0, 0, 0);
		eStamp = new Timestamp( (Integer.parseInt(eDate.substring(0,4))-1900), (Integer.parseInt(eDate.substring(4,6))-1), Integer.parseInt(eDate.substring(6,8)), 23, 59, 59, 999);
		
		
		arrReturnList = new ArrayList(); 
		
		
		int loopCheck = 0;
		ArrayList arrTempDate = new ArrayList();
		
		String tempDate = "";
		
		while( sStamp.getTime() < eStamp.getTime()  )
		{
			if( !tempDate.equals(sdf.format(sStamp)) ) {
				arrGdatas.add( sdf2.format(sStamp) );
				arrTempDate.add(sdf.format(sStamp));
				tempDate = sdf.format(sStamp);
			}
			sStamp.setTime( (sStamp.getTime()+86400000) );
		}

		arrReturnList.add(new GraphDataInfo("날짜",arrGdatas ));
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_CODE, 	\n");
		sbSql.append(" 		  IC_NAME 	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE=?	\n");
		sbSql.append(" 		AND IC_CODE > 0	\n");

		ArrayList arrTempKeyword = new ArrayList();
		
		int tempCode = 0;
		String tempName = "";

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psIcType);
			rs = pstmt.executeQuery();
			while(rs.next()) {

			tempCode = rs.getInt(1);
			tempName = rs.getString(2);
			//IssueCodeBean icb = new IssueCodeBean();
			//kb.setKGxp(rs.getString(1));
			//kb.setKGyp(rs.getString(2));
			//kb.setKGvalue(rs.getString(3));
			icBean = new IssueCodeBean();
			icBean.setIc_name(tempName);
			icBean.setIc_type(Integer.parseInt(psIcType));
			icBean.setIc_code(tempCode);
						
			arrTempKeyword.add(icBean);
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		int arrDataTemp[][] = new int[arrTempKeyword.size()][arrTempDate.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempKeyword.size(); i++ ) {
			for( j=0; j<arrTempDate.size(); j++ ) {
				arrDataTemp[i][j] = 0;
			}
		}
		
		String dateColum = "";
		if (psDateType.equals("d")) {
			dateColum = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD')";
		}else if (psDateType.equals("w")) {
			dateColum = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYWW')";
		}else if (psDateType.equals("m")) {
			dateColum = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMM')";
		}else {
			dateColum = "TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD')";
		}

		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT "+dateColum+" xVal ,IDC.IC_CODE, COUNT(0) as cnt , IC.IC_NAME		\n");
		sbSql.append(" FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC, IMS_ISSUE_CODE IC						\n");
		sbSql.append(" WHERE ID.MT_NO = IDC.MT_NO									\n");
		sbSql.append(" 		AND IDC.MT_NO IN (SELECT MT_NO  	\n");
		sbSql.append(" 						  FROM IMS_ISSUE_DATA_CODE  	\n");
		sbSql.append(" 						  GROUP BY MT_NO )  	\n");
		sbSql.append(" 		AND TO_CHAR(TO_DATE(ID.MT_DATE, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD') BETWEEN ? AND ?	\n"); 		
		sbSql.append(" 		AND IDC.IC_TYPE = ?	\n");
		sbSql.append(" 		AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");								
		sbSql.append(" GROUP BY "+dateColum+", IDC.IC_CODE, IC.IC_NAME	\n");
		
		System.out.println(sbSql.toString());

		String mtDate = "";
		String iName = "";
		int iCount = 0;
		int iCode = 0;
		
		int YpCheck = 0;
		
		IssueCodeBean icTemp = null;

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();

			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, sDate);
			pstmt.setString(2, eDate);
			pstmt.setString(3, psIcType);

			rs = pstmt.executeQuery();
			
			loopCheck = 0;
			YpCheck = 0;

			while(rs.next()) {
				mtDate = rs.getString(1);
				iCode = rs.getInt(2);
				iCount = rs.getInt(3);
				iName = rs.getString(4);
			
				//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
			
				// 해당 값과 날짜가 있는지 체크 
				for( i=0; i<arrTempKeyword.size(); i++ ) {
					icTemp = (IssueCodeBean) arrTempKeyword.get(i);
					for( j=0; j<arrTempDate.size(); j++ ) {
						if( arrTempDate.get(j).equals(mtDate) && icTemp.getIc_code() == iCode ) {
							arrDataTemp[i][j] = iCount;
						}
					}
				}
			}

			// 뽑아낸 결과를 ArrayList 에다가 넣는다.
			for( i=0; i<arrTempKeyword.size(); i++ ) {
				icTemp = (IssueCodeBean) arrTempKeyword.get(i);
				ArrayList arrTemp = new ArrayList();

				ArrayList listTmp2 = new ArrayList();			//임시리스트
				GraphDataInfo gdDatasSub = new GraphDataInfo("", listTmp2);
				gdDatasSub.setName(icTemp.getIc_name());

				for( j=0; j<arrTempDate.size(); j++ ) {
					gdDatasSub.addData( arrDataTemp[i][j]);
				}
				arrReturnList.add(gdDatasSub);
				arrTemp = null;
			}

		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}
	public ArrayList getEvocData( String psSdate,
				  String psEdate,
			  String psIcType
			)
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		IssueCodeBean icb; 
		
		ArrayList arrGdatas = new ArrayList();
		
		Timestamp sStamp = null;
		Timestamp eStamp = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
		
		sStamp = new Timestamp( (Integer.parseInt(sDate.substring(0,4))-1900), (Integer.parseInt(sDate.substring(4,6))-1), Integer.parseInt(sDate.substring(6,8)), 0, 0, 0, 0);
		eStamp = new Timestamp( (Integer.parseInt(eDate.substring(0,4))-1900), (Integer.parseInt(eDate.substring(4,6))-1), Integer.parseInt(eDate.substring(6,8)), 23, 59, 59, 999);
		
		
		arrReturnList = new ArrayList(); 
		
		
		int loopCheck = 0;
		ArrayList arrTempDate = new ArrayList();
		
		while( sStamp.getTime() < eStamp.getTime()  )
		{
			arrGdatas.add( sdf2.format(sStamp) );
			arrTempDate.add(sdf.format(sStamp));
			sStamp.setTime( (sStamp.getTime()+86400000) );
		}
		
		arrReturnList.add(new GraphDataInfo("날짜",arrGdatas ));
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_CODE, 	\n");
		sbSql.append(" 		  IC_NAME 	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE=?	\n");
		sbSql.append(" 		AND IC_CODE > 0	\n");
		
		ArrayList arrTempKeyword = new ArrayList();
		
		int tempCode = 0;
		String tempName = "";
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psIcType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				tempCode = rs.getInt(1);
				tempName = rs.getString(2);
				
				icBean = new IssueCodeBean();
				icBean.setIc_name(tempName);
				icBean.setIc_type(Integer.parseInt(psIcType));
				icBean.setIc_code(tempCode);				
				arrTempKeyword.add(icBean);
				
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		int arrDataTemp[][] = new int[arrTempKeyword.size()][arrTempDate.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempKeyword.size(); i++ ) {
			for( j=0; j<arrTempDate.size(); j++ ) {
				arrDataTemp[i][j] = 0;
			}
		}
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT ID.ID_DATE xVal ,IDC.IC_CODE, COUNT(0) as cnt , IC.IC_NAME		\n");
		sbSql.append(" FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC, IMS_ISSUE_CODE IC						\n");
		sbSql.append(" WHERE ID.MT_NO = IDC.MT_NO									\n");
		sbSql.append(" 		AND IDC.MT_NO IN (SELECT MT_NO  	\n");
		sbSql.append(" 						  FROM IMS_ISSUE_DATA_CODE  	\n");
		sbSql.append(" 						  GROUP BY MT_NO )  	\n");
		sbSql.append(" 		AND ID.ID_DATE BETWEEN ? AND ?	\n"); 		
		sbSql.append(" 		AND IDC.IC_TYPE = ?	\n");
		sbSql.append(" 		AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");								
		sbSql.append(" GROUP BY ID.ID_DATE, IDC.IC_CODE, IC.IC_NAME	\n");
		
		System.out.println(sbSql.toString());
		
		String mtDate = "";
		String iName = "";
		int iCount = 0;
		int iCode = 0;
		
		int YpCheck = 0;
		
		IssueCodeBean icTemp = null;
		
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, sDate);
			pstmt.setString(2, eDate);
			pstmt.setString(3, psIcType);
			
			rs = pstmt.executeQuery();
			
			loopCheck = 0;
			YpCheck = 0;
		
			while(rs.next()) {
				mtDate = rs.getString(1);
				iCode = rs.getInt(2);
				iCount = rs.getInt(3);
				iName = rs.getString(4);
				
				//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
				
				// 해당 값과 날짜가 있는지 체크 
				for( i=0; i<arrTempKeyword.size(); i++ ) {
					icTemp = (IssueCodeBean) arrTempKeyword.get(i);
					for( j=0; j<arrTempDate.size(); j++ ) {
						if( arrTempDate.get(j).equals(mtDate) && icTemp.getIc_code() == iCode ) {
							arrDataTemp[i][j] = iCount;
						}
					}
				}
			}
		
			// 뽑아낸 결과를 ArrayList 에다가 넣는다.
			for( i=0; i<arrTempKeyword.size(); i++ ) {
				icTemp = (IssueCodeBean) arrTempKeyword.get(i);
				ArrayList arrTemp = new ArrayList();
				
				ArrayList listTmp2 = new ArrayList();			//임시리스트
				GraphDataInfo gdDatasSub = new GraphDataInfo("", listTmp2);
				gdDatasSub.setName(icTemp.getIc_name());
			
				for( j=0; j<arrTempDate.size(); j++ ) {
					gdDatasSub.addData( arrDataTemp[i][j]);
				}
				arrReturnList.add(gdDatasSub);
				arrTemp = null;
			}
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return arrReturnList;
	}
		
		
	public ArrayList getEvocData( String psSdate,
				  				  String psEdate,
				  				  int psIcType,
				  				  String typecode
			)
{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		Statement stmt  = null;
		ResultSet rs = null;
		IssueCodeBean icb; 
		
		ArrayList arrGdatas = new ArrayList();
		
		Timestamp sStamp = null;
		Timestamp eStamp = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
		
		sStamp = new Timestamp( (Integer.parseInt(sDate.substring(0,4))-1900), (Integer.parseInt(sDate.substring(4,6))-1), Integer.parseInt(sDate.substring(6,8)), 0, 0, 0, 0);
		eStamp = new Timestamp( (Integer.parseInt(eDate.substring(0,4))-1900), (Integer.parseInt(eDate.substring(4,6))-1), Integer.parseInt(eDate.substring(6,8)), 23, 59, 59, 999);
		
		
		arrReturnList = new ArrayList(); 
		
		
		int loopCheck = 0;
		ArrayList arrTempDate = new ArrayList();
		
		while( sStamp.getTime() < eStamp.getTime()  )
		{
		arrGdatas.add( sdf2.format(sStamp) );
		arrTempDate.add(sdf.format(sStamp));
		sStamp.setTime( (sStamp.getTime()+86400000) );
		}
		
		arrReturnList.add(new GraphDataInfo("날짜",arrGdatas ));
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC_CODE, 	\n");
		sbSql.append(" 		  IC_NAME 	\n");
		sbSql.append(" FROM IMS_ISSUE_CODE	\n");
		sbSql.append(" WHERE IC_TYPE=?	\n");
		sbSql.append(" 		AND IC_CODE > 0	\n");
		
		ArrayList arrTempKeyword = new ArrayList();
		
		int tempCode = 0;
		String tempName = "";
		
		try {
		conn  = new DBconn();
		conn.getDBCPConnection();
		
		pstmt = conn.createPStatement(sbSql.toString());
		pstmt.setInt(1, psIcType);
		rs = pstmt.executeQuery();
		while(rs.next()) {
		
		tempCode = rs.getInt(1);
		tempName = rs.getString(2);
		
		icBean = new IssueCodeBean();
		icBean.setIc_name(tempName);
		icBean.setIc_type(psIcType);
		icBean.setIc_code(tempCode);
		arrTempKeyword.add(icBean);
		}
		} catch(SQLException ex) {
		Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
		Log.writeExpt(ex);
		} finally {
		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		int arrDataTemp[][] = new int[arrTempKeyword.size()][arrTempDate.size()];
		int i=0;
		int j=0;
		// 데이터 배열 초기값 0으로 초기화
		for( i=0; i<arrTempKeyword.size(); i++ ) {
		for( j=0; j<arrTempDate.size(); j++ ) {
		arrDataTemp[i][j] = 0;
		}
		}
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT ID.ID_DATE xVal ,IDC.IC_CODE, COUNT(0) as cnt , IC.IC_NAME		\n");
		sbSql.append(" FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC, IMS_ISSUE_CODE IC						\n");
		sbSql.append(" WHERE ID.MT_NO = IDC.MT_NO									\n");
		sbSql.append(" 		AND IDC.MT_NO IN (SELECT MT_NO  	\n");
		sbSql.append(" 						  FROM IMS_ISSUE_DATA_CODE  	\n");
		sbSql.append(" 						  GROUP BY MT_NO )  	\n");
		sbSql.append(" 		AND ID.ID_DATE BETWEEN '"+sDate+"' AND '"+eDate+"'	\n"); 		
		sbSql.append(" 		AND IDC.IC_TYPE = '"+psIcType+"'	\n");
		sbSql.append(" 		AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");								
		sbSql.append(" GROUP BY ID.ID_DATE, IDC.IC_CODE, IC.IC_NAME	\n");
		
		System.out.println(sbSql.toString());
		
		String mtDate = "";
		String iName = "";
		int iCount = 0;
		int iCode = 0;
		
		int YpCheck = 0;
		
		IssueCodeBean icTemp = null;
		
		try {
		conn  = new DBconn();
		conn.getDBCPConnection();
		
		pstmt = conn.createPStatement(sbSql.toString());
		
		rs = pstmt.executeQuery();
		
		loopCheck = 0;
		YpCheck = 0;
		
		while(rs.next()) {
		mtDate = rs.getString(1);
		iCode = rs.getInt(2);
		iCount = rs.getInt(3);
		iName = rs.getString(4);
		
		//System.out.println(mtDate+", "+kValue+", "+kXp+", "+kYp+", "+kCount);
		
		// 해당 값과 날짜가 있는지 체크 
		for( i=0; i<arrTempKeyword.size(); i++ ) {
		icTemp = (IssueCodeBean) arrTempKeyword.get(i);
		for( j=0; j<arrTempDate.size(); j++ ) {
			if( arrTempDate.get(j).equals(mtDate) && icTemp.getIc_code() == iCode ) {
				arrDataTemp[i][j] = iCount;
			}
		}
		}
		}
		
		// 뽑아낸 결과를 ArrayList 에다가 넣는다.
		for( i=0; i<arrTempKeyword.size(); i++ ) {
		icTemp = (IssueCodeBean) arrTempKeyword.get(i);
		ArrayList arrTemp = new ArrayList();
		
		ArrayList listTmp2 = new ArrayList();			//임시리스트
		GraphDataInfo gdDatasSub = new GraphDataInfo("", listTmp2);
		gdDatasSub.setName(icTemp.getIc_name());
		
		for( j=0; j<arrTempDate.size(); j++ ) {
		gdDatasSub.addData( arrDataTemp[i][j]);
		}
		arrReturnList.add(gdDatasSub);
		arrTemp = null;
		}
		
		} catch(SQLException ex) {
		Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
		Log.writeExpt(ex);
		} finally {
		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
		if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return arrReturnList;
}
	
	public IssueCodeBean getPieData(String psSdate,String psEdate, int type) {
		DBconn dbconn   = null;
		Statement stmt  = null;
		ResultSet rs    = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = null;
		IssueCodeBean icb = new IssueCodeBean();
		
        try {
    		ArrayList Arrname = new ArrayList();
    		ArrayList Arrcnt = new ArrayList();
    		
    		String sDate = "";
    		String eDate = "";
    		sDate = psSdate;
    		eDate = psEdate;
    		sDate = sDate.replaceAll("-","");
    		eDate = eDate.replaceAll("-","");
            sb = new StringBuffer();            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
          
            sb.append(" SELECT COUNT(0) as cnt , IC.IC_NAME		\n");
    		sb.append(" FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC, IMS_ISSUE_CODE IC						\n");
    		sb.append(" WHERE ID.MT_NO = IDC.MT_NO									\n");
    		sb.append(" 		AND IDC.MT_NO IN (SELECT MT_NO  	\n");
    		sb.append(" 						  FROM IMS_ISSUE_DATA_CODE  	\n");
    		sb.append(" 						  GROUP BY MT_NO )  	\n");
    		sb.append(" 		AND ID.ID_DATE BETWEEN '"+sDate+"' AND '"+eDate+"'	\n"); 		
    		sb.append(" 		AND IDC.IC_TYPE = '"+type+"'	\n");
    		sb.append(" 		AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");								
    		sb.append(" GROUP BY IC.IC_NAME	\n");
           

    		System.out.println(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	Arrname.add(rs.getString("IC_NAME"));
            	Arrcnt.add(new Integer(rs.getString("cnt")));
            }
            
            //icb.setXData(Arrname);
            //icb.setYData(Arrcnt);
    		
    		
                 
        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return icb;
    }
	
	public ArrayList getBarData(String psSdate,String psEdate, int type) {
		DBconn dbconn   = null;
		Statement stmt  = null;
		ResultSet rs    = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = null;
		IssueCodeBean icb = new IssueCodeBean();
		ArrayList paList = new ArrayList();
		
        try {
    		String sDate = "";
    		String eDate = "";
    		sDate = psSdate;
    		eDate = psEdate;
    		sDate = sDate.replaceAll("-","");
    		eDate = eDate.replaceAll("-","");
            sb = new StringBuffer();            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
          
            sb.append(" SELECT ID.ID_DATE,COUNT(0) as cnt , IC.IC_NAME		\n");
    		sb.append(" FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC, IMS_ISSUE_CODE IC						\n");
    		sb.append(" WHERE ID.MT_NO = IDC.MT_NO									\n");
    		sb.append(" 		AND IDC.MT_NO IN (SELECT MT_NO FROM IMS_ISSUE_DATA_CODE WHERE IC_TYPE = '1' AND IC_CODE = '1' GROUP BY MT_NO ) 	\n");
    		sb.append(" 		AND ID.ID_DATE BETWEEN '"+sDate+"' AND '"+eDate+"'	\n"); 		
    		sb.append(" 		AND IDC.IC_TYPE = '"+type+"'	\n");
    		sb.append(" 		AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");								
    		sb.append(" GROUP BY ID.ID_DATE,IC.IC_NAME	\n");
           

    		System.out.println(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	
            	icBean = new IssueCodeBean();
        		icBean.setIc_name(rs.getString("ID_DATE"));
        		icBean.setIc_type(rs.getInt("cnt"));
//        		icBean.setIc_code(tempCode);
//            	paList.add(new IssueCodeBean(rs.getString("ID_DATE"),
//            								 rs.getInt("cnt"),
//            								 rs.getString("IC_NAME")
//            								 )
//            	);
            }
            ArrayList arrlegend = new ArrayList();
            sb = new StringBuffer();
            sb.append("select IC_NAME from IMS_ISSUE_CODE where ic_type = 5 and ic_code != 0 \n");
            System.out.println(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	arrName1.add(rs.getString("IC_NAME"));
            }
            
        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return paList;
    }
	
	public ArrayList getBarData1(String psSdate,String psEdate, int type) {
		DBconn dbconn   = null;
		Statement stmt  = null;
		ResultSet rs    = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = null;
		IssueCodeBean icb = new IssueCodeBean();
		ArrayList paList = new ArrayList();
		
        try {
    		String sDate = "";
    		String eDate = "";
    		sDate = psSdate;
    		eDate = psEdate;
    		sDate = sDate.replaceAll("-","");
    		eDate = eDate.replaceAll("-","");
            sb = new StringBuffer();            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
          
            sb.append(" SELECT CONVERT(CHAR(6),ID.ID_DATE,112) AS ID_DATE,COUNT(0) as cnt , IC.IC_NAME		\n");
    		sb.append(" FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC, IMS_ISSUE_CODE IC						\n");
    		sb.append(" WHERE ID.MT_NO = IDC.MT_NO									\n");
    		sb.append(" 		AND IDC.MT_NO IN (SELECT MT_NO  	\n");
    		sb.append(" 						  FROM IMS_ISSUE_DATA_CODE  	\n");
    		sb.append(" 						  GROUP BY MT_NO )  	\n");
    		sb.append(" 		AND CONVERT(CHAR(6),ID.ID_DATE,112) BETWEEN '"+sDate+"' AND '"+eDate+"'	\n"); 		
    		sb.append(" 		AND IDC.IC_TYPE = '"+type+"'	\n");
    		sb.append(" 		AND IDC.IC_TYPE = IC.IC_TYPE AND IDC.IC_CODE = IC.IC_CODE	\n");								
    		sb.append(" GROUP BY CONVERT(CHAR(6),ID.ID_DATE,112),IC.IC_NAME	\n");
           

    		System.out.println(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
//            	paList.add(new IssueCodeBean(rs.getString("ID_DATE"),
//            								 rs.getInt("cnt"),
//            								 rs.getString("IC_NAME")
//            								 )
//            	);
            }
            sb = new StringBuffer();
            sb.append("select IC_NAME from IMS_ISSUE_CODE where ic_type = 6 and ic_code != 0 \n");
            System.out.println(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	arrName3.add(rs.getString("IC_NAME"));
            }
            
        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return paList;
    }
	
	public ArrayList getLineData(String psSdate,String psEdate) {
		DBconn dbconn   = null;
		Statement stmt  = null;
		ResultSet rs    = null;
		PreparedStatement pstmt = null;
		StringBuffer sb = null;
		IssueCodeBean icb = new IssueCodeBean();
		ArrayList paList = new ArrayList();
		ConfigUtil cu = new ConfigUtil();
		
        try {
    		String sDate = "";
    		String eDate = "";
    		sDate = psSdate.replaceAll("-","");
    		eDate = psEdate.replaceAll("-","");
    		String sMt_no = "";
    		String eMt_no = "";
            sb = new StringBuffer();            
            dbconn = new DBconn();
            dbconn.getDBCPConnection();
            sb.append("SELECT CASE WHEN CONVERT(VARCHAR(8),GETDATE(),112) = '"+sDate+"' THEN		\n");    
            sb.append("ISNULL(                                                              		\n");
            sb.append("(SELECT DN_ENO + 1                                            		\n");
            sb.append("FROM IMS_DATES_NO                                               		\n");
            sb.append("WHERE DN_DATE = (SELECT MAX(DN_DATE) FROM IMS_DATES_NO  )       		\n");
            sb.append("),1)                                                              		\n");
            sb.append("ELSE                                                                		\n");
            sb.append("ISNULL(                                                              		\n");
            sb.append("(SELECT DN_SNO                                                   		\n");
            sb.append("FROM IMS_DATES_NO                                                 		\n");
            sb.append("WHERE DN_DATE = '"+sDate+"')                             		\n");
            sb.append(",1)                                                              		\n");
            sb.append("END AS  MIN_NO     ,                                                		\n");
            sb.append("          CASE WHEN CONVERT(VARCHAR(8),GETDATE(),112) = '"+eDate+"' THEN      		\n");
            sb.append("(                                                                   		\n");
            sb.append("SELECT /*+INDEX(META IDX_META_NO_DATE) FIRSTROWS */                		\n");
            sb.append("max(MT_NO)                                                       		\n");
            sb.append("FROM IMS_META                                                         		\n");
            sb.append(")                                                                   		\n");
            sb.append("ELSE                                                                		\n");
            sb.append("ISNULL(                                                              		\n");
            sb.append("(                                                               		\n");
            sb.append("SELECT DN_ENO                                                  		\n");
            sb.append("FROM IMS_DATES_NO                                                		\n");
            sb.append("WHERE DN_DATE = '"+eDate+"'                             		\n");
            sb.append("),1)                                                            		\n");
            sb.append("END AS  MAX_NO		\n");
            System.out.println(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            if(rs.next()){
            	sMt_no = rs.getString("MIN_NO");
            	eMt_no = rs.getString("MAX_NO");
            }
            
//            sMt_no="134997869";
//            eMt_no="135481586";
            String CompanyName = cu.getConfig("COMPANYNAME");
            sb = new StringBuffer();
    		sb.append("SELECT ISNULL(CONVERT(CHAR(8),MT_DATE,112),0) AS MT_DATE,	ISNULL(B2.CNT,0) AS CNT ,B1.K_VALUE	\n");
			sb.append("FROM 	\n");
			sb.append("(SELECT K_POS, K_XP, K_YP, K_ZP, K_VALUE FROM IMS_KEYWORD WHERE K_TYPE<3  AND K_XP = '"+CompanyName+"' AND K_YP! = 0 AND K_ZP = 0  ) B1 LEFT OUTER JOIN	\n");  
			sb.append("( 	\n");
			sb.append("SELECT K_XP, K_YP, 0 AS K_ZP, COUNT(DISTINCT A2.MT_PNO) AS CNT ,CONVERT(CHAR(8),MT_DATE,112) AS MT_DATE	\n");
			sb.append("FROM 	\n");
			sb.append("(SELECT MT_NO, K_XP, K_YP FROM IMS_IDX WHERE MT_NO BETWEEN '"+sMt_no+"' AND '"+eMt_no+"') A1,	\n"); 
			sb.append("(SELECT MT_NO, MT_DATE, MT_PNO FROM IMS_META WHERE MT_NO BETWEEN '"+sMt_no+"' AND '"+eMt_no+"') A2 	\n");
			sb.append("WHERE A1.MT_NO=A2.MT_NO 	\n");
			sb.append("GROUP BY K_XP, K_YP ,CONVERT(CHAR(8),MT_DATE,112)	\n");
			sb.append(") B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP	\n");
			sb.append("ORDER BY K_VALUE,MT_DATE	\n");

    		System.out.println(sb.toString() );
            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            while(rs.next()){
            	//if(rs.getInt("CNT")!=0){
//	            	paList.add(new IssueCodeBean(rs.getString("MT_DATE"),
//	            								 rs.getInt("CNT"),
//	            								 rs.getString("K_VALUE")
//	            								 )
//	            	);
            	//}
            }
            
            
        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
        return paList;
    }
	
	public ArrayList getEvocSourceData( String psSdate,
									    String psEdate,
									    String psIcType
									   )
	{
		ArrayList arrReturnList = null;
		StringBuffer sbSql = null;
		
		DBconn conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sDate = "";
		String eDate = "";
		sDate = psSdate;
		eDate = psEdate;
		
		sDate = sDate.replaceAll("-","");
		eDate = eDate.replaceAll("-","");
		
		arrReturnList = new ArrayList();
		
		sbSql = new StringBuffer();
		
		sbSql.append(" SELECT IC1.IC_NAME,						\n");
		sbSql.append(" 		  ISNULL(IC2.CNT,0) AS CNT			\n"); 
		sbSql.append(" FROM (									\n");
		sbSql.append(" 		SELECT IC_NAME,						\n"); 
		sbSql.append(" 			   IC_TYPE, 					\n");
		sbSql.append(" 			   IC_CODE 						\n");
		sbSql.append(" 		FROM IMS_ISSUE_CODE 					\n");
		sbSql.append(" 		WHERE IC_TYPE = ? AND IC_CODE !=0					\n");
		sbSql.append(" 		)  IC1 LEFT OUTER JOIN				\n");
		sbSql.append(" 		(SELECT IDC.IC_TYPE, 				\n");
		sbSql.append(" 				IDC.IC_CODE, 				\n");
		sbSql.append(" 				COUNT(1) AS CNT 			\n");
		sbSql.append(" 		 FROM IMS_ISSUE_DATA ID, IMS_ISSUE_DATA_CODE IDC	\n");
		sbSql.append(" 		 WHERE ID.MT_NO = IDC.MT_NO 				\n");
		sbSql.append(" 			AND IDC.MT_NO IN (SELECT MT_NO 			\n");
		sbSql.append(" 							  FROM IMS_ISSUE_DATA_CODE	\n");
		sbSql.append(" 							  GROUP BY MT_NO		\n");
		sbSql.append(" 							  )						\n");
		sbSql.append(" 			AND ID.ID_DATE BETWEEN ? AND ?	\n");
		sbSql.append(" 			AND IDC.IC_TYPE = ?					\n");
		sbSql.append(" 		GROUP BY IDC.IC_TYPE, IDC.IC_CODE			\n");
		sbSql.append(" 		)IC2 ON(IC1.IC_TYPE = IC2.IC_TYPE 			\n");
		sbSql.append(" 		AND IC1.IC_CODE = IC2.IC_CODE)				\n");
		sbSql.append(" ORDER BY IC1.IC_CODE desc							\n");
		
		
		
		System.out.println(sbSql.toString());

		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			
			pstmt = conn.createPStatement(sbSql.toString());
			pstmt.setString(1, psIcType);
			pstmt.setString(2, sDate);
			pstmt.setString(3, eDate);
			pstmt.setString(4, psIcType);
			
			rs = pstmt.executeQuery();
			
			ArrayList arrTemp = new ArrayList();
			ArrayList arrTemp2 = new ArrayList();
			
			GraphDataInfo gdDatasSub = new GraphDataInfo("title", arrTemp);
			GraphDataInfo gdDatasSub2 = new GraphDataInfo("data", arrTemp2);
			while(rs.next()) {
			
				gdDatasSub.addData( rs.getString(1));
				gdDatasSub2.addData( rs.getInt(2));
			}
		
			arrReturnList.add(gdDatasSub);
			arrReturnList.add(gdDatasSub2);
		
		} catch(SQLException ex) {
			Log.writeExpt(ex, sbSql.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		return arrReturnList;
	}

	
	public void showList(ArrayList arrSrc){
		int i;
		for(i=0; i<arrSrc.size(); i++){
			GraphDataInfo Gd2 = (GraphDataInfo) arrSrc.get(i);
			System.out.println(Gd2.getName());
			for(int y=0;y<Gd2.getData().size();y++){
				System.out.println(Gd2.getData().get(y));
			}
		}	
	}
	
	/*
	public ChartServer dataSet( ChartServer chart1, 
							     ArrayList paList,			 				  
							     int chartType,
							     String sdate,
							     String edate
							    ) 
	{
			IssueCodeBean icb = new IssueCodeBean();
			
			ArrayList top = new ArrayList();
			ArrayList center = new ArrayList();
			ArrayList bottom = new ArrayList();
			ArrayList arrData = null;
			GetChartData GCdata = new GetChartData();
			String Input_sDate = sdate.replaceAll("-", "");
			String Input_eDate = edate.replaceAll("-", "");
			
			arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, "d", "yyyyMMdd");
			
			java.util.List list = new ArrayList();
			
			String tempName = "";
			ArrayList tmpTop = new ArrayList();
			ArrayList tmpCenter = new ArrayList();
			ArrayList tmpBottom = new ArrayList();
			for(int i=0;i<paList.size();i++){
				icb = (IssueCodeBean)paList.get(i);
				if(icb.getName().equals("상")){
					tmpTop.add(icb);
				}else if(icb.getName().equals("중")){
					tmpCenter.add(icb);
				}else if(icb.getName().equals("하")){
					tmpBottom.add(icb);
				}
			}
			System.out.println("tmpTop.size() : "+tmpTop.size());
			System.out.println("tmpCenter.size() : "+tmpCenter.size());
			System.out.println("tmpBottom.size() : "+tmpBottom.size());
			int cnt = 0;
			for(int i=0;i<arrData.size();i++){
				String tmpDate = (String)arrData.get(i);
				if(tmpTop.size()!=0){
					icb = (IssueCodeBean)tmpTop.get(cnt);
					if(tmpDate.equals(icb.getDate())){
						top.add(new Integer(icb.getCnt()));
						if(cnt<tmpTop.size()-1){
							cnt++;
						}
					}else{
						top.add(new Integer(0));
					}
				}else{
					top.add(new Integer(0));
				}
			}
			int cnt1 = 0;
			for(int i=0;i<arrData.size();i++){
				String tmpDate = (String)arrData.get(i);
				if(tmpCenter.size()!=0){
					icb = (IssueCodeBean)tmpCenter.get(cnt1);
					if(tmpDate.equals(icb.getDate())){
						center.add(new Integer(icb.getCnt()));
						if(cnt1<tmpCenter.size()-1){
							cnt1++;
						}
					}else{
						center.add(new Integer(0));
					}
				}else{
					center.add(new Integer(0));
				}
			}
			int cnt2 = 0;
			for(int i=0;i<arrData.size();i++){
				String tmpDate = (String)arrData.get(i);
				if(tmpBottom.size()!=0){
					icb = (IssueCodeBean)tmpBottom.get(cnt2);
					if(tmpDate.equals(icb.getDate())){
						bottom.add(new Integer(icb.getCnt()));
						if(cnt2<tmpBottom.size()-1){
							cnt2++;
						}
					}else{
						bottom.add(new Integer(0));
					}
				}else{
					bottom.add(new Integer(0));
				}
			}
			
			System.out.println("arrData : "+arrData);
			System.out.println("top : "+top);
			System.out.println("center : "+center);
			System.out.println("bottom : "+bottom);
//			for(int i=1;i<9;i++){
//				top.add(new Integer(i));
//				center.add(new Integer(i));
//				bottom.add(new Integer(i));
//			}
			ArrayList arrdate = new ArrayList();
			for(int i=0;i<arrData.size();i++){
				String tmparrData = (String) arrData.get(i);
				arrdate.add(tmparrData.substring(4, 6)+"/"+tmparrData.substring(6, 8));
			}
			list.add(arrdate);
			list.add(top);
			list.add(center);
			list.add(bottom);
			
			SoftwareFX.ChartFX.ListProvider lstDataProvider = new SoftwareFX.ChartFX.ListProvider(list);
			
			chart1.setDataSource(lstDataProvider);
			
			
			return chart1;
	}
	public ChartServer dataSet1( ChartServer chart1, 
							     ArrayList paList,			 				  
							     int chartType,
							     String sdate,
							     String edate
							    ) 
	{
		IssueCodeBean icb = new IssueCodeBean();
		
		ArrayList top = new ArrayList();
		ArrayList center = new ArrayList();
		ArrayList bottom = new ArrayList();
		ArrayList arrData = null;
		GetChartData GCdata = new GetChartData();
//		String Input_sDate = sdate.replaceAll("-", "");
//		String Input_eDate = edate.replaceAll("-", "");
		System.out.println("sdate : "+sdate);
		System.out.println("edate : "+edate);
		
		String Input_sDate = sdate.replaceAll("-", "");
		String Input_eDate = edate.replaceAll("-", "");
		arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, "m", "yyyyMMdd");
		System.out.println(arrData);
		java.util.List list = new ArrayList();
		
		String tempName = "";
		ArrayList tmpTop = new ArrayList();
		ArrayList tmpCenter = new ArrayList();
		ArrayList tmpBottom = new ArrayList();
		System.out.println("paList.size() => "+paList.size());
		for(int i=0;i<paList.size();i++){
			icb = (IssueCodeBean)paList.get(i);
			if(icb.getName().equals("긍정")){
				tmpTop.add(icb);
			}else if(icb.getName().equals("부정")){
				tmpCenter.add(icb);
			}else if(icb.getName().equals("기타")){
				tmpBottom.add(icb);
			}
		}
		System.out.println("arrData.size() => "+arrData.size());
		
		int cnt = 0;
		
		for(int i=0;i<arrData.size();i++){
			icb = (IssueCodeBean)tmpTop.get(cnt);
			String tmpDate = (String)arrData.get(i);
			if(tmpDate.equals(icb.getDate())){
				top.add(new Integer(icb.getCnt()));
				if(cnt<tmpTop.size()-1){
					cnt++;
				}
			}else{
				top.add(new Integer(0));
			}
		}
		int cnt1 = 0;
		System.out.println("arrData.size() => "+arrData.size());
		for(int i=0;i<arrData.size();i++){
			icb = (IssueCodeBean)tmpCenter.get(cnt1);
			String tmpDate = (String)arrData.get(i);
			if(tmpDate.equals(icb.getDate())){
				center.add(new Integer(icb.getCnt()));
				if(cnt1<tmpCenter.size()-1){
					cnt1++;
				}
			}else{
				center.add(new Integer(0));
			}
		}
		int cnt2 = 0;
		System.out.println("arrData.size() => "+arrData.size());
		for(int i=0;i<arrData.size();i++){
			icb = (IssueCodeBean)tmpBottom.get(cnt2);
			String tmpDate = (String)arrData.get(i);
			if(tmpDate.equals(icb.getDate())){
				bottom.add(new Integer(icb.getCnt()));
				if(cnt2<tmpBottom.size()-1){
					cnt2++;
				}
			}else{
				bottom.add(new Integer(0));
			}
		}
		
		System.out.println("arrData : "+arrData);
		System.out.println("top : "+top);
		System.out.println("center : "+center);
		System.out.println("bottom : "+bottom);
		//for(int i=1;i<9;i++){
		//top.add(new Integer(i));
		//center.add(new Integer(i));
		//bottom.add(new Integer(i));
		//}
		ArrayList arrdate = new ArrayList();
		for(int i=0;i<arrData.size();i++){
		String tmparrData = (String) arrData.get(i);
		arrdate.add(tmparrData.substring(4, 6)+"월");
		}
		list.add(arrdate);
		list.add(top);
		list.add(center);
		list.add(bottom);
		
		SoftwareFX.ChartFX.ListProvider lstDataProvider = new SoftwareFX.ChartFX.ListProvider(list);
		
		chart1.setDataSource(lstDataProvider);
		
		
		return chart1;
	}
	public ChartServer dataSet2( ChartServer chart1, 
		     ArrayList paList,			 				  
		     int chartType,
		     String sdate,
		     String edate
		    ) 
		{
				IssueCodeBean icb = new IssueCodeBean();
				
				ArrayList arrData = null;
				GetChartData GCdata = new GetChartData();
				//String Input_sDate = sdate.replaceAll("-", "");
				//String Input_eDate = edate.replaceAll("-", "");
				System.out.println("sdate : "+sdate);
				System.out.println("edate : "+edate);
				
				String Input_sDate = sdate.replaceAll("-", "");
				String Input_eDate = edate.replaceAll("-", "");
				arrData = GCdata.getXvalDate(Input_sDate, Input_eDate, "d", "yyyyMMdd");
				System.out.println(arrData);
				java.util.List list = new ArrayList();
				
				
				ArrayList arrTemp = new ArrayList();
				ArrayList arrTotal = new ArrayList();
				ArrayList arrTemp1 = new ArrayList();
				ArrayList arrlist = new ArrayList();
				String tmpName = "";
				System.out.println("paList.size() : "+paList.size());
				ArrayList arrdate = new ArrayList();
				System.out.println("arrData.size() => "+arrData.size());
				for(int i=0;i<arrData.size();i++){
					String tmparrData = (String) arrData.get(i);
					arrdate.add(tmparrData.substring(4, 6)+"/"+tmparrData.substring(6,8));
				}
				list.add(arrdate);		//차트에 들어갈 날짜 등록
				for(int i=0;i<paList.size();i++){
				icb = (IssueCodeBean)paList.get(i);
					if(!tmpName.equals(icb.getName())){		//K_value종류가 바뀌면 arrTemp초기화
						if(arrTemp.size() > 0){
							arrTotal.add(arrTemp);			//K_value종류별로 나눈ArrayList를 arrTotal에 저장 
						}
						tmpName = (String)icb.getName();
						arrName4.add(tmpName);
						arrTemp = new ArrayList();
					}
					arrTemp.add(icb);
				}
				arrTotal.add(arrTemp);
				
				System.out.println("arrTotal.size() : "+arrTotal.size());
				
				int cnt = 0;
				
				for(int i = 0; i < arrTotal.size(); i++){
					arrTemp1 = (ArrayList) arrTotal.get(i);
					for(int j=0;j<arrData.size();j++){
						String tmpDate = (String)arrData.get(j);
						icb = (IssueCodeBean)arrTemp1.get(cnt);
						if(tmpDate.equals(icb.getDate())){
							arrlist.add(new Integer(icb.getCnt()));
							if(cnt<arrTemp1.size()-1){
								cnt++;
							}
						}else{
							arrlist.add(new Integer(0));
						}
					}
					System.out.println("arrlist : "+arrlist);
					list.add(arrlist);
					arrlist = new ArrayList();
					cnt=0;
				}
				
				
				
				
				SoftwareFX.ChartFX.ListProvider lstDataProvider = new SoftwareFX.ChartFX.ListProvider(list);
				
				chart1.setDataSource(lstDataProvider);
				
				
				return chart1;
		}
		*/
}
