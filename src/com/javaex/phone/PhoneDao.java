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
	
	
	//PhoneDB 추가 등록 [insert] 메소드
	public int phoneInsert(PersonVo personVo) {
		int count = -1;
		
		try {
			//DB 연결
			getConnection();
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query += " insert into person ";
			query += " values (seq_person_id.nextval, ?, ?, ?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			
			//실행
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("[" + count + "건 등록 되었습니다.]");
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			//자원 정리
			close();
		}
		
		return count;
	}
	
	
	//PhoneDB 수정 [update] 메소드
	public int phoneUpdate(PersonVo personVo) {
		int count = -1;
		
		try {
			//DB 연결
			getConnection();
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query += " update person ";
			query += " set name = ?, ";
			query += "     hp = ?, ";
			query += "     company = ? ";
			query += " where person_id = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());
			
			//실행
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("[" + count + "건 수정 되었습니다.]");
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			//자원 정리
			close();
		}
		
		return count;
	}
	
	
	//PhoneDB 삭제 [delete] 메소드
	public int phoneDelete(int idNum) {
		int count = -1;
		
		try {
			//DB 연결
			getConnection();
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query += " delete person ";
			query += " where person_id = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idNum);
			
			//실행
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("[" + count + "건 삭제 되었습니다.]");
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			//자원 정리
			close();
		}
		
		return count;
	}
	
	
	//PhoneDB 검색 메소드
	public List<PersonVo> phoneSelect(String keyword) {

		//리스트 준비
		List<PersonVo> personList = new ArrayList<PersonVo>();
		
		try {
			//DB 연결
			getConnection();
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query += " select person_id ";
			query += "         ,name ";
			query += "         ,hp ";
			query += "         ,company ";
			query += " from person ";
			query += " where name like '%"+keyword+"%' ";
			query += " or hp like '%"+keyword+"%' ";
			query += " or company like '%"+keyword+"%' ";
			
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
