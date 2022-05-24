package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {
	
	private String id = "phonedb";
	private String pw = "phonedb";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//DB 연결 메소드
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	//PhoneDB 전체 출력 [select] 메소드
	public List<PersonVo> phoneSelect() {
		
		//리스트 준비
		List<PersonVo> personList = new ArrayList<PersonVo>();
		try {
			//DB 연결
			getConnection();
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query += " select * ";
			query += " from person ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int personId = rs.getInt(1);
				String name = rs.getString(2);
				String hp = rs.getString(3);
				String company = rs.getString(4);
				
				personList.add(new PersonVo(personId, name, hp, company));
			}
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			//자원 정리
			close();
		}
		
		return personList;
	}
	
	//자원 정리 메소드
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

}
